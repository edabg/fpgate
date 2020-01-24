/*
 * Copyright (C) 2020 EDA Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.eda.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This s Protocol implemented in Datecs devices with 'X' suffix in models 
 * +++ Description of the program interface
 * -----------------------------------------
 * The fiscal device operates under the control of an application program, with which communicates
 * via RS232 ( USB or LAN) serial connection. The device executes a previously set of wrapped commands,
 * arranged according to the type of the operations which have to be executed. The application program does not
 * have a direct access to the resources of the fiscal device although it can detect data connected with the status of
 * the fiscal device and the fiscal control unit.
 * +++ Low level protocol
 * -----------------------
 * A) Protocol type - Master (Host) / Slave
 * The fiscal printer performs the commands sent by the Host and returns messages, which depend on the result.
 * The fiscal printer cannot instigate asynchronous communications itself. Only responses to commands
 * from the Host are sent to the Host. These messages are either wrapped or single byte control codes. The fiscal
 * printer maintains the communication via the RS232 serial connection at baud rates of 1200, 2400, 4800, 9600,
 * 19200, 38400, 57600 and 115200 b/s, 8N1.
 * B) Sequence of the messages
 * Host sends a wrapped message, containing a command for the fiscal printer. ECR executes the
 * requested operation and response with a wrapped message. Host has to wait for a response from the fiscal
 * printer before to send another message. The protocol uses non-wrapped messages with a length one byte for
 * processing of the necessary pauses and error conditions.
 * C) Non-wrapped messages – time-out
 * When the transmitting of messages from the Host is normal, Slave answers not later than 60 ms
 * either with a wrapped message or with a 1 byte code. Host must have 500 ms of time-out for receiving a
 * message from Slave. If there is no message during this period of time the Host will transmit the message again
 * with the same sequence number and the same command. After several unsuccessful attempts Host must indicate
 * that there is either no connection to the fiscal printer or there is a hardware fault.
 * Non-wrapped messages consist of one byte and they are:
 *      A) NAK 15H
 * This code is sent by Slave when an error in the control sum or the form of the received message is found. When
 * Host receives a NAK it must again send a message with the same sequence number.
 *      B) SYN 16H
 * This code is sent by Slave upon receiving a command which needs longer processing time. SYN is sent every
 * 60 ms until the wrapped message is not ready for transmitting.
 * D) Wrapped messages
 *      a) Host to fiscal printer (Send)
 * {01}{LEN}{SEQ}{CMD}{DATA}{05}{BCC}{03}
 * Example:
 * 01 30 30 32 3F 24 30 30 32 3A 54 65 73 74 09 05 30 33 36 3F 03
 * b) Fiscal printer to Host (Receive)
 * {01}{LEN}{SEQ}{CMD}{DATA}{04}{STATUS}{05}{BCC}{03}
 * Example:
 * 01 30 30 33 35 24 30 30 32 3A 30 09 04 80 80 A0 80 86 9A 80 80 05 30 36 33 3A 03
 * Where:
 * {01} Preamble. - 1 byte long. Value: 01H.
 * {LEN} Number of bytes from {01} preamble (excluded) to {05} (included) plus the fixed offset of 20H.
 * Length: 4 bytes. Each digit from the two bytes is sent after 30H is added to it. From example – Input
 * have 15(0Fh) bytes - 30 30 32 3F 24 30 30 32 3A 54 65 73 74 09 05
 * Now add 20h →000F + 0020 = 002F. Sum 002F is presented as 30H, 30H, 32H, 3FH
 * {SEQ} Sequence number of the frame.
 * Length : 1 byte. Value: 20H – FFH. The fiscal printer saves the same {SEQ} in the return message. If
 * the ECR gets a message with the same {SEQ} as the last message received it will not perform any operation,
 * but will repeat the last sent message.
 * {CMD} The code of the command.
 * Length: 4 byte. The fiscal printer saves the same {CMD} in the return message. If the fiscal printer
 * receives a non-existing code it returns a wrapped message with zero length in the data field and sets the
 * respective status bit. Each digit from the two bytes is sent after 30H is added to it. From example, used
 * command is 42 (2Ah). Command 002A is presented as 30H, 30H, 32H, 3AH
 * {DATA} Data.
 * Length: 0-213 bytes for Host to fiscal printer, 0-218 bytes for Fiscal printer to Host. Value: 20H – FFH.
 * The format and length of the field for storing data depends on the command. If the command has no data the
 * length of this field is zero. If there is a syntax error the respective status bit is established in the data and a
 * wrapped message is returned with zero field length.
 * From example, input value Text\t is presented as 54H, 65H, 73H, 74H, 09H (ASCII to hex convert)
 * {04} Separator (only for fiscal printer-to-Host massages), - Not used in input
 * Length: 1 byte. Value: 04H.
 * {STATUS} The field with the current status of the fiscal device. - Not used in input
 * Length: 8 bytes. Value: 80H-FFH.
 * {05} Postamble
 * Length: 1 byte. Value:05H.
 * {BCC} Control sum (0000H-FFFFH),
 * Length: 4 bytes. Value of each byte: 30H-3FH. The sum includes between {01} preamble (excluded) to
 * {05} Each digit from the two bytes is sent after 30H is added to it.
 * From example sum of 30 30 32 3F 24 30 30 32 3A 54 65 73 74 09 05 is 036F. 036F is presented as
 * 30H, 33H, 36H, 3FH
 * {03} Terminator, Length: 1 byte. Value: 03H.
 * 
 * 
 *
 * @author Dimitar Angelov
 */
public class ProtocolV10X extends AbstractProtocol {

    /**
     * Maximum send packed size 
     * {01}{LEN}{SEQ}{CMD}{DATA}{05}{BCC}{03}
     *   1 + 4 +  1  + 4 + 213  + 1  + 4 + 1 = 229
     */
    protected static int MAX_SEND_PACKET_SIZE = 229;
    
    /* Maximum receive packed size 
     * {01}{LEN}{SEQ}{CMD}{DATA}{04}{STATUS}{05}{BCC}{03}
     *   1 + 4  + 1  + 4 + 218 + 1  +  8    + 1 + 4 + 1 = 243
    */
    protected static int MAX_RECEIVE_PACKET_SIZE = 243;
    
    protected static int MAX_SEND_DATASIZE = 213;
    protected static int MAX_RECEIVE_DATASIZE = 218;

    /**
     * Start packed sequence number
     */
    protected static int SEQ_START = 0x20;
    /**
     * End packed sequence number
     */
    protected static int SEQ_END = 0xFF;
    
    /**
     * Current packed Sequence number
     */
    protected int mSEQ;
    
    protected int sendRetryCount = 2;
    
    protected byte[] mSB = new byte[8]; // STATUS Bytes
    
    public ProtocolV10X(InputStream in, OutputStream out, EncodingType encoding) {
        super(in, out, encoding);
        mSEQ = SEQ_START;
    }

    public ProtocolV10X(FiscalSocket socket, EncodingType encoding) {
        super(socket, encoding);
        mSEQ = SEQ_START;
    }

    public ProtocolV10X(FiscalSocket socket) {
        super(socket);
        mSEQ = SEQ_START;
    }
    
    protected void nextSEQ() {
        mSEQ += 1;
        if (mSEQ > SEQ_END) {
            mSEQ = SEQ_START;
        }
    }

    @Override
    public String customCommand(int command, String data) throws IOException {
        nextSEQ();
        mSocket.clear();

        LOGGER.finest(String.format("> (%d) %s", new Object[]{Integer.valueOf(command), data}));
        StopWatch go = new StopWatch();
        sendPacket(command, data);

        String result = receivePacket();

        LOGGER.finest(String.format("< (%d) \"%s\" in %dms", new Object[]{Integer.valueOf(command), result, Integer.valueOf((int) go.getElapsedTime())}));
        return result;
    }

    protected void sendPacket(int command, String data) throws IOException {
        for (int retry = 0; retry < sendRetryCount; retry++) {
            // <01><LEN><SEQ><CMD><DATA><05><BCC><03>
            byte[] buf = new byte[MAX_SEND_PACKET_SIZE];
            int offs = 0;
            int dataLen;
            int crc = 0;

            dataLen = data != null ? data.length() : 0;
            if (dataLen > MAX_SEND_DATASIZE) {
                throw new IllegalArgumentException("Lenght of data exceeds the limits ("+Integer.toString(MAX_SEND_DATASIZE)+"!)");
            }

            buf[(offs++)] = 0x01;
            // <LEN> Number of bytes from <01> preamble (excluded) to <05> (included) plus the fixed offset of 20H.
            // Length: 4 bytes. Each digit from the two bytes is sent after 30H is added to it.
            // 0x20 + len + 10 = 4(LEN)+1(SEQ)+4(CMD)+1(05)
            int LEN = (0x20 + 10 + dataLen);
            buf[(offs++)] = ((byte) ((LEN >> 12 & 0xF) + 0x30));
            buf[(offs++)] = ((byte) ((LEN >>  8 & 0xF) + 0x30));
            buf[(offs++)] = ((byte) ((LEN >>  4 & 0xF) + 0x30));
            buf[(offs++)] = ((byte) ((LEN >>  0 & 0xF) + 0x30));

            buf[(offs++)] = ((byte) mSEQ);

            buf[(offs++)] = ((byte) ((command >> 12 & 0xF) + 0x30));
            buf[(offs++)] = ((byte) ((command >>  8 & 0xF) + 0x30));
            buf[(offs++)] = ((byte) ((command >>  4 & 0xF) + 0x30));
            buf[(offs++)] = ((byte) ((command >>  0 & 0xF) + 0x30));
            
            // Convert unicode to ANSI
            toAnsi(data, buf, offs, mEncoding);
            offs += dataLen;

            buf[(offs++)] = 0x05;

            // Calc CRC
            for (int i = 1; i < offs; i++) {
                crc += (buf[i] & 0xFF);
            }

            buf[(offs++)] = ((byte) ((crc >> 12 & 0xF) + 0x30));
            buf[(offs++)] = ((byte) ((crc >>  8 & 0xF) + 0x30));
            buf[(offs++)] = ((byte) ((crc >>  4 & 0xF) + 0x30));
            buf[(offs++)] = ((byte) ((crc >>  0 & 0xF) + 0x30));

            buf[(offs++)] = 0x03;
            // Sends packet
            write(buf, 0, offs);
            do {
                read(buf, 0, 1);
            } while ((buf[0] & 0xFF) == 0x16); // wait until revceives SYN packets from Slave

            if (buf[0] == 0x15) { // NAK
                // TODO: Can be logged
                //continue; // resend packet next try
            } else {
                if (buf[0] == 0x01) { // Start of receive packet!
                    return; // 
                } else
                    throw new IOException("W:Invalid data received from device!");
            }
        } // tries
        throw new IOException("Cant communicate with the device! Max retry count reached");
    }

    protected String receivePacket() throws IOException {
        // <01><LEN><SEQ><CMD><DATA><04><STATUS><05><BCC><03>
        // First byte <01> is already read in sendPacket! 
        // In buf are expected next bytes from <LEN> to the end
        byte[] buf = new byte[MAX_RECEIVE_DATASIZE];

        int b; 
        int LEN = 0;
        int crc = 0; 
        for (int i = 0; i < 4; i++) { // LEN
            b = read(); 
            crc += b;
            LEN = (LEN << 4) + (b - 0x30);
        }    
        // len = data_len + 20H + 4(LEN) + 1(SEQ) + 4(CMD) + 1(04) + 8(STATUS) + 1(05) = 0x20+19
        int dataLen = LEN - (0x20 + 19); // calc data len from <LEN>
        if (dataLen > MAX_RECEIVE_DATASIZE) 
            throw new IOException("Packet exceed maximum data size!");
        
        b = read(); // <SEQ>
        if (b != mSEQ) // Received packed with different SEQ than last sent
            throw new IOException("Invalid packet SEQ received!"); 
        crc += b;
        
        for (int i = 0; i < 4; i++) { // CMD
          b = read();
          crc += b;
        }

        read(buf, 0, dataLen); // <DATA>

        for (int i = 0; i < dataLen; i++) {
            crc += (buf[i] & 0xFF);
        }
        b = read(); // <04>
        if (b != 0x04) {
            throw new IOException("R0:Invalid data received!");
        }
        crc += b;

        read(mSB, 0, mSB.length); // <STATUS> 
        for (byte by : mSB) {
            crc += (by & 0xFF);
        }
        b = read(); // <05>
        if (b != 0x5) {
            throw new IOException("R1:Invalid data received!");
        }
        crc += b;

        b = read(); // <BCC>
        crc -= (b - 0x30 << 12);
        b = read();
        crc -= (b - 0x30 << 8);
        b = read();
        crc -= (b - 0x30 << 4);
        b = read();
        crc -= b - 0x30;
        if (crc != 0) {
            throw new IOException("Received Invalid CRC!");
        }
        b = read(); // <03>
        if (b != 0x03) { 
            throw new IOException("R2:Invalid data received!");
        }
        return toUnicode(buf, 0, dataLen, mEncoding);
    }

    @Override
    public boolean isStatusBitTriggered(int byteIndex, int bitIndex) {
        return (mSB[byteIndex] & 1 << bitIndex) > 0;
    }

    @Override
    public byte[] getStatusBytes() {
        return mSB;
    }

}
