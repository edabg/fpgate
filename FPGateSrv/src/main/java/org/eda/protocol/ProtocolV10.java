/*
 * Copyright (C) 2019 EDA Ltd.
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
 * Serial low level protocol commonly used protocol from fiscal devices in Bulgaria.
 * There is some minor differences in some implementations which are in 
 *   Packet Sequence Range (SEQ_START, SEQ_END), CMD value range, maximum data size (MAX_SEND_DATASIZE, MAX_RECEIVE_DATASIZE)
 * Parameters for MAX_SEND_PACKET_SIZE, MAX_RECEIVE_PACKET_SIZE,MAX_SEND_DATASIZE,MAX_RECEIVE_DATASIZE, SEQ_START,SEQ_END in this class
 * corresponds to following known devices:
 *      Datecs DP-05, DP-25, DP-35, WP-50, DP-150, FP-800, FP-2000, FP-650, SK1-21F, SK1-31F, FMP-10, FP-550
 *      Eltrade Fiscal Devices
 * In description `Host` is the computer and `Slave` is the fiscal device.
 * In normal working conditions for all messages from Host, Slave responds not late than 60 ms with packaged message or single byte.
 * The Host have to implement 500ms timeout for response from the Slave.
 * In case of no response from slave in this timeout, the Host have to resend last message.
 * In case of some retries without success, the host have to initiate slave device connection error.
 * 
 * 1. Non packaged messages
 * - {NACK} 15H
 *  Slave -} Host in case of packed checksum of data format error. In this case Host have to resend last message.
 * - {SYN} 16H
 *  Slave -} Host. Slave notifies Host that the command will consume for execution time more than 60 ms.
 * The Slave have to send {SYN} on every 60ms while processing command and return response with packaged message.
 * 2. Packaged Messages
 * - Host -} Slave (Send)
 *      {01}{LEN}{SEQ}{CMD}{DATA}{05}{BCC}{03}
 * - Slave -} Host (Receive)
 *      {01}{LEN}{SEQ}{CMD}{DATA}{04}{STATUS}{05}{BCC}{03}
 * Where:
 *  {01} Preamble - Constant value 01H - 1 Byte
 *  {LEN} Number of bytes from {01} (without it) to {05} (included) plus offset from 20H - 1 Byte. Values: 20H - FFH
 *  {SEQ} Sequence number of frame - 1 Byte. Values: 20H - 7FH
 *      The slave returns response with same {SEQ}. In case of receiving response with same {SEQ} as previous response
 *      Host have to resend last message.
 *  {CMD} The code of command. 1 Byte. Values: 20H - 7FH
 *      The slave returns same {CMD} in response. In case of received invalid command from slave (not supported).
 *      Slave return packed message with zero length of data and set response status bit for invalid command (in {STATUS}).
 *  {DATA} Data. 0-213 Bytes for Host -} Slave. 0-218 bytes Slave -} Host. Allowed values 20H – FFH and 09H,0AH.
 *      Format and length depends of command. If command have no data the length is 0. In case of syntax error    
 *      the error status bit (in {STATUS}) is set and Slave returns packaged message with zero length.
 *  {04} Separator with value 04H only for Slave -} Host. 
 *  {STATUS} Current state of the Slave. 6 Bytes. Values: 80H - FFH. Values are dependent from Fiscal Device model.
 *  {05} Post-amble value 05H. 1 Byte.
 *  {BCC} Checksum (0000H-FFFFH). 4 Bytes. Values: 30H - 3FH
 *      The checksum is calculated over {01} to {05} included.
 *      To every calculated byte is added offset 30H.
 *      For Example 1AE3H is transmitted as 31H,3AH,3EH,33H 
 *  {03} Terminator value 03H. 1 byte.
 * 
 *
 * @author Dimitar Angelov
 */
public class ProtocolV10 extends AbstractProtocol {

    /**
     * Maximum send packed size 
     * {01}{LEN}{SEQ}{CMD}{DATA}{05}{BCC}{03}
     *   1 + 1 +  1  + 1 + 213  + 1  + 4 + 1 = 223
     */
    protected static int MAX_SEND_PACKET_SIZE = 223;
    
    /* Maximum receive packed size 
     * <01><LEN><SEQ><CMD><DATA><04><STATUS><05><BCC><03>
     *   1 + 1  + 1  + 1 + 218 + 1  +  6    + 1 + 4 + 1 = 235
    */
    protected static int MAX_RECEIVE_PACKET_SIZE = 235;
    
    protected static int MAX_SEND_DATASIZE = 213;
    protected static int MAX_RECEIVE_DATASIZE = 218;

    /**
     * Start packed sequence number
     */
    protected static int SEQ_START = 0x20;
    /**
     * End packed sequence number
     */
    protected static int SEQ_END = 0x7F;
    
    /**
     * Current packed Sequence number
     */
    protected int mSEQ;
    
    protected int sendRetryCount = 2;
    
    protected byte[] mSB = new byte[6]; // STATUS Bytes
    
    static {
        MAX_SEND_DATASIZE = 213;
        // Maximum send packed size 
        // <01><LEN><SEQ><CMD><DATA><05><BCC><03>
        //   1 + 1  + 1  + 1 + 213 + 1  + 4 + 1 = 223
        MAX_SEND_PACKET_SIZE = 223;

        MAX_RECEIVE_DATASIZE = 218;
        // Maximum receive packed size 
        // <01><LEN><SEQ><CMD><DATA><04><STATUS><05><BCC><03>
        //   1 + 1  + 1  + 1 + 218 + 1  +  6    + 1 + 4 + 1 = 235
        MAX_RECEIVE_PACKET_SIZE = 235;

        SEQ_START = 0x20;
        SEQ_END = 0x7F;
    }

    public ProtocolV10(InputStream in, OutputStream out, EncodingType encoding) {
        super(in, out, encoding);
        mSEQ = SEQ_START;
    }

    public ProtocolV10(FiscalSocket socket, EncodingType encoding) {
        super(socket, encoding);
        mSEQ = SEQ_START;
    }

    public ProtocolV10(FiscalSocket socket) {
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
            int len;
            int crc = 0;

            len = data != null ? data.length() : 0;
            if (len > MAX_SEND_DATASIZE) {
                throw new IllegalArgumentException("Lenght of data exceeds the limits ("+Integer.toString(MAX_SEND_DATASIZE)+"!)");
            }

            buf[(offs++)] = 0x01;
            // 0x20 - offset + 4 = 1(LEN)+1(SEQ)+1(CMD)+1(05)
            buf[(offs++)] = ((byte) (0x20 + 4 + len));

            buf[(offs++)] = ((byte) mSEQ);

            buf[(offs++)] = ((byte) command);
            
            // Convert unicode to ANSI
            toAnsi(data, buf, offs, mEncoding);
            offs += len;

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

        int b = read(); // read <LEN> byte
        // len = data_len + 20H + 1(LEN) + 1(SEQ) + 1(CMD) + 1(04) + 6(STATUS) + 1(05) = 0x20+11
        int len = b - (0x20 + 11); // calc data len from <LEN>
        if (len > MAX_RECEIVE_DATASIZE) 
            throw new IOException("Packet exceed maximum data size!");
        
        // read next bytes and calc CRC
        int crc = b; // <LEN>

        b = read(); // <SEQ>
        if (b != mSEQ) // Received packed with different SEQ than last sent
            throw new IOException("Invalid packet SEQ received!"); 
        crc += b;
        b = read(); // <CMD>
        crc += b;

        read(buf, 0, len); // <DATA>

        for (int i = 0; i < len; i++) {
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
        return toUnicode(buf, 0, len, mEncoding);
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
