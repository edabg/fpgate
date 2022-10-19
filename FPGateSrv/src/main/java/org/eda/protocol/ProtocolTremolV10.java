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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;

/**
 * 1. COMMUNICATION PROTOCOL
 * The type of the protocol is Master / Slave. The communication session is always initiated by the Application Software. 
 * FD caries out the commands send by the software application and provides a feedback depending on the result. 
 * FD sends back an „Acknowledgement response” or „message response”. All messages of the protocol are either packed or single-byte. 
 * FD supports communication standard RS232, USB, BT, TCP (WiFi, LAN).
 * Serial port adjustment parameters:
 *   Speed: 115200 bit/s (or 19200, 38400, 57600 and 9600 if such is set for the FD)
 *   8 bits word
 *   No parity
 *   1 stop bit
 * 1.1. MESSAGE FORMAT FROM THE SOFTWARE APPLICATION TO THE FD
 * All messages except those described in section 4., sent to the FD by the PC have the following structure:
 * {STX}{LEN}{NBL}{CMD}{DATA…DATA}{CS}{CS}{ETX}
 * The table below contains description of the field enclosed between the symbols { and }: 
 * ----------------------------------------------------------------
 *  Field     | No. of bytes | Value    | Comment
 * ----------------------------------------------------------------
 *  STX        1               02h       Message start – always 02h
 *  LEN        1               __h       Message length (number of bytes including LEN, NBL, CMD, DATA)
 *                                       increased by 20h i.e. a number in the 20h - 9Fh range
 *  NBL        1               __h       Message number increased by 20h i.e. a number in the 20h - 9Fh range
 *  CMD        1               __h       Command - a number in the 20h - 7Fh range(see the description of commands)
 *  DATA..DATA 0-3902          ...h      Additional data – a group of data fields separated with the symbols ';', 
 *                                       giving additional information needed for execution of the command 
 *                                       (see the description of commands)
 *  CS CS      2               ____h     Checksum, compiled as follows:
 *                                       1) Operation XOR of all bytes from LEN to DATA inclusive = 0 .. FFh
 *                                       2) Conversion of 2 bytes by adding 30h, for example:
 *                                       B5h -> 3Bh 35h
 *  ETX        1               0Ah       End of message – always 0Ah (LF)
 * ----------------------------------------------------------------
 * The texts data of the message is sent as ASCII text with code table cp1251 (Windows 1251).
 * 
 * 1.2. MESSAGE FORMAT FROM THE FD TO THE SOFTWARE APPLICATION
 * There are several types of response depending on the message received.
 * 1.2.1. Acknowledgement response
 * Positive acknowledgement – when package format is correct. It is sent when the command is acknowledged 
 * as well as when it is rejected (errors in the data sent (field {DATA…DATA}) 
 * or the command cannot be executed or the command is illegal depending on the current status of the FD 
 * indicated by the two status bytes). It is a package message with the following format:
 * {ACK}{NBL}{STE}{STE}{CS}{CS}{ETX}
 * Fields description:
 * ----------------------------------------------------------------
 *  Field     | No. of bytes | Value    | Comment
 * ----------------------------------------------------------------
 * ACK         1               06h      
 * NBL         1               __h        No. of message = NBL of message related to receipt
 * STE STE     2               ____h      2 error status-bytes. A two-digit ASCII number. (see Table Errors)
 * CS CS       2               ____h      Checksum, compiled as follows:
 *                                        1) Operation XOR on NBL STE и STE = 00h .. FFh
 *                                        2) Conversion of 2 bytes by adding 30h, for example:B5h -> 3Bh 35h
 * ETX         1               0Ah        (LF)
 * ----------------------------------------------------------------
 * 
 * The two status-bytes are a two-digit ASCII number, in which the first digit provides information 
 * about the error in the FD, and the second one – about a command error.
 * 
 * Table Errors:
 * ------------------------------------------------------------------------------
 * Byte value | FD errors                           | Byte value | Command errors
 * ------------------------------------------------------------------------------
 * 30           OK                                    30           OK
 * 31           Out of paper, printer failure         31           Invalid command
 * 32           Registers overflow                    32           Illegal command
 * 33           Clock failure or incorrect date&time  33           Z daily report is not zero
 * 34           Opened fiscal receipt                 34           Syntax error
 * 35           Payment residue account               35           Input registers overflow
 * 36           Opened non-fiscal receipt             36           Zero input registers
 * 37           Registered payment but receipt        37           Unavailable transaction for correction
 *              is not closed
 * 38           Fiscal memory failure                 38           Insufficient amount on hand
 * 39           Incorrect password     
 * 3a           Missing external display
 *              24hours block – missing Z report
 * 3c           Overheated printer thermal head.
 * 3d           Interrupt power supply in fiscal receipt (one time until status is read)
 * 3e           Overflow EJ
 * 3f           Insufficient conditions
 * ----------------------------------------------------------------
 * A two-digit number is compiled depending on the type of error.
 * Example: Error 32 – Illegal command due to clock failure
 * Negative acknowledgement – It is sent when the package format is incorrect.
 * Repetition request – It is sent when the FD is busy executing the preceding command.
 * It is 1 byte RETRY = 0Eh without checksum.
 * 1.2.2. Message response
 * It has the format of the packed message sent by the SA to the FD (see 3.1.)
 * but is returned by the FD to the SA and contains information – response to the query (see description of commands).
 * 

 *
 * @author Dimitar Angelov
 */
public class ProtocolTremolV10 extends AbstractProtocol {
    /*
// general
#define ZFP_STX						0x02	// start transaction
#define ZFP_PING					0x04	// ping!
#define ZFP_BUSY					0x05	// is busy
#define ZFP_OUTOFPAPER				0x07	// No Paper - Romanian ///
#define ZFP_PING2					0x09
#define ZFP_ETX						0x0A	// end transaction
#define ZFP_ACK						0x06    
#define ZFP_NACK					0x15
#define ZFP_RETRY					0x0E
#define ZFP_ANTIECHO				0x03	// prevent working with echo modems

#define ZFP_BASEDATA				0x20    // the ' ' base
#define ZFP_INFINITE				0xFFFFFFFF // infinite timeout
#define ZFP_NOBUSYWAIT				0		// no auto busy wait

// lengths
#define ZFP_PRIVATELEN				0x03    // length of LEN + NBL + CMD
#define ZFP_ENDLEN					0x03    // length of CS + ETX
#define ZFP_FULLPRIVATELEN			0x07    // length of LEN + NBL + CMD + CS + ETX + STX
#define ZFP_MAXDATALEN				(0xFF - ZFP_BASEDATA)
#define ZFP_MAXBUFLEN				0xFF
#define ZFP_MAXHEXBUFLEN			(ZFP_MAXBUFLEN * 6)
#define ZFP_MAXERRORBUFLEN			0x200
#define ZFP_RECEIPTLEN				0x07

// errors
#define ZFPE_SUCCESS				0
#define ZFPE_ERRORBASE				0x100
#define ZFPE_BADINPUTDATA			(ZFPE_ERRORBASE + 1)
#define ZFPE_TIMEOUT				(ZFPE_ERRORBASE + 2)
#define ZFPE_NACKRECEIVED			(ZFPE_ERRORBASE + 3)
#define ZFPE_CRCERROR				(ZFPE_ERRORBASE + 4)
#define ZFPE_BADRECEIPTDATA			(ZFPE_ERRORBASE + 5)
#define ZFPE_BADRESPONSEDATA		(ZFPE_ERRORBASE + 6)
#define ZFPE_RETRIED				(ZFPE_ERRORBASE + 7)
#define ZFPE_BADLOGOFILE			(ZFPE_ERRORBASE + 8)
#define ZFPE_NOFPRINTER				(ZFPE_ERRORBASE + 9)
#define ZFPE_FPRINTERBUSY			(ZFPE_ERRORBASE + 10)
#define ZFPE_NBLNOTSAME				(ZFPE_ERRORBASE + 11)
#define ZFPE_FILEIOERROR			(ZFPE_ERRORBASE + 12)
#define ZFPE_FPBUSYTIMEOUT			(ZFPE_ERRORBASE + 13)
#define ZFPE_INVALIDDEVICE			(ZFPE_ERRORBASE + 14)
    
    
    */
    
    protected static byte ZFP_STX      = (byte)0x02; // start transaction
    protected static byte ZFP_ETX      = (byte)0x0A; // end transaction
    protected static byte ZFP_ACK      = (byte)0x06; // 
    protected static byte ZFP_NACK     = (byte)0x15; // 
    protected static byte ZFP_RETRY    = (byte)0x0E; // 
    protected static byte ZFP_ANTIECHO = (byte)0x03; // 
    protected static byte ZFP_PING     = (byte)0x04; //	// ping!
    protected static byte ZFP_BUSY     = (byte)0x05; // is busy
    protected static byte ZFP_OUTOFPAPER = (byte)0x07;	// No Paper
    protected static byte ZFP_PING2      = (byte)0x09; 
    
    protected static int ZFP_TIMEOUT = 2000;
    protected static int ZFP_PINGTIMEOUT = 200;
    
    /*
    Error codes
    */
    protected static int ZFPE_SUCCESS         = 0x0000;
    protected static int ZFPE_ERRORBASE       = 0x0100;
    protected static int ZFPE_BADINPUTDATA    = 0x0100+1;
    protected static int ZFPE_TIMEOUT         = 0x0100+2;
    protected static int ZFPE_NACKRECEIVED    = 0x0100+3;
    protected static int ZFPE_CRCERROR        = 0x0100+4;
    protected static int ZFPE_BADRECEIPTDATA  = 0x0100+5;
    protected static int ZFPE_BADRESPONSEDATA = 0x0100+6;
    protected static int ZFPE_RETRIED         = 0x0100+7;
    protected static int ZFPE_BADLOGOFILE     = 0x0100+8;
    protected static int ZFPE_NOFPRINTER      = 0x0100+9;
    protected static int ZFPE_FPRINTERBUSY    = 0x0100+10;
    protected static int ZFPE_NBLNOTSAME      = 0x0100+11;
    protected static int ZFPE_FILEIOERROR     = 0x0100+12;
    protected static int ZFPE_FPBUSYTIMEOUT   = 0x0100+13;
    protected static int ZFPE_INVALIDDEVICE   = 0x0100+14;
    protected static int ZFPE_ACKERR          = 0x0100+15;
    protected static int ZFPE_INVALIDRESPONSE = 0x0100+16;

    // lengths
    protected static int ZFP_PRIVATELEN     = 0x03;  // length of LEN + NBL + CMD
    protected static int ZFP_ENDLEN         = 0x03;  // length of CS + ETX
    protected static int ZFP_FULLPRIVATELEN = 0x07;  // length of LEN + NBL + CMD + CS + ETX + STX
    protected static int ZFP_MAXDATALEN     = (0xFF - 0x20); //
    protected static int ZFP_MAXBUFLEN      = 0xFF;
    protected static int ZFP_MAXHEXBUFLEN   = (ZFP_MAXBUFLEN * 6);
    protected static int ZFP_MAXERRORBUFLEN = 0x200;
    protected static int ZFP_RECEIPTLEN     = 0x07;
    
    /**
     * Maximum send packed size 
     * {STX}{LEN}{NBL}{CMD}{DATA…DATA}{CS}{CS}{ETX}
     *   1  + 1 +  1  + 1 + 220       + 1 + 1 + 1 = 227
     */
    protected static int MAX_SEND_PACKET_SIZE = 227;
    
    /* Maximum receive packed size 
     * {STX}{LEN}{NBL}{CMD}{DATA…DATA}{CS}{CS}{ETX}
     *   1  + 1 +  1  + 1 + 220       + 1 + 1 + 1 = 227
    */
    protected static int MAX_RECEIVE_PACKET_SIZE = 227;
    
    protected static int MAX_SEND_DATASIZE = 220;
    protected static int MAX_RECEIVE_DATASIZE = 220;

    protected static int MAX_RAW_READ_BUFSIZE = 8*1024*1024; // 8M

    /**
     * Start packed sequence number
     */
    protected static int SEQ_START = 0x20;
    /**
     * End packed sequence number
     */
    protected static int SEQ_END = 0x7F; // 0x9F-0x20=0x7F
    
    /**
     * Current packed Sequence number
     */
    protected int mSEQ;
    
    protected int sendRetryCount = 2;
    
    protected byte[] mSB = new byte[7]; // STATUS Bytes
    protected byte[] mCMDSB = new byte[2]; // Last command ACK packet Status Bytes
    
    protected int lastCommand = 0;

    /**
     * ACK FD Status Byte Definition
     */
    final static protected LinkedHashMap<Byte, String> FD_CODE_ERROR;
    /**
     * ACK Cmd Status Byte Definition
     */
    final static protected LinkedHashMap<Byte, String> CMD_CODE_ERROR;
    
    static {
        FD_CODE_ERROR = new LinkedHashMap<>();

        FD_CODE_ERROR.put((byte)0x30, ""); // OK
        FD_CODE_ERROR.put((byte)0x31, "Out of paper, printer failure"); 
        FD_CODE_ERROR.put((byte)0x32, "Registers overflow"); 
        FD_CODE_ERROR.put((byte)0x33, "Clock failure or incorrect date&time");
        FD_CODE_ERROR.put((byte)0x34, "Opened fiscal receipt");
        FD_CODE_ERROR.put((byte)0x35, "Payment residue account"); 
        FD_CODE_ERROR.put((byte)0x36, "Opened non-fiscal receipt"); 
        FD_CODE_ERROR.put((byte)0x37, "Registered payment but receipt is not closed"); 
        FD_CODE_ERROR.put((byte)0x38, "Fiscal memory failure");
        FD_CODE_ERROR.put((byte)0x39, "Incorrect password"); 
        FD_CODE_ERROR.put((byte)0x3A, "Missing external display"); 
        FD_CODE_ERROR.put((byte)0x3B, "24hours block – missing Z report"); 
        FD_CODE_ERROR.put((byte)0x3C, "Overheated printer thermal head."); 
        FD_CODE_ERROR.put((byte)0x3D, "Interrupt power supply in fiscal receipt (one time until status is read)"); 
        FD_CODE_ERROR.put((byte)0x3E, "Overflow EJ"); 
        FD_CODE_ERROR.put((byte)0x3F, "Insufficient conditions"); 
    
        CMD_CODE_ERROR = new LinkedHashMap<>();
        CMD_CODE_ERROR.put((byte)0x30, ""); // OK
        CMD_CODE_ERROR.put((byte)0x31, "Invalid command"); 
        CMD_CODE_ERROR.put((byte)0x32, "Illegal command"); 
        CMD_CODE_ERROR.put((byte)0x33, "Z daily report is not zero");
        CMD_CODE_ERROR.put((byte)0x34, "Syntax error");
        CMD_CODE_ERROR.put((byte)0x35, "Input registers overflow"); 
        CMD_CODE_ERROR.put((byte)0x36, "Zero input registers"); 
        CMD_CODE_ERROR.put((byte)0x37, "Unavailable transaction for correction"); 
        CMD_CODE_ERROR.put((byte)0x38, "Insufficient amount on hand");
    }
    
    protected void checkACKResult(byte sbFD, byte sbCMD) throws IOException {
        List<String> sb = new ArrayList<String>();
        // check FD Status bytes
        for (byte bval : FD_CODE_ERROR.keySet()) {
            if ((bval == sbFD) && (FD_CODE_ERROR.get(bval).length() > 0))
                sb.add("FD Error:"+FD_CODE_ERROR.get(bval));
        }
        // check CMD Status bytes
        for (byte bval : CMD_CODE_ERROR.keySet()) {
            if ((bval == sbCMD) && (CMD_CODE_ERROR.get(bval).length() > 0))
                sb.add("CMD Error:"+CMD_CODE_ERROR.get(bval));
        }
        if (sb.size() > 0)
            throw new IOException("Cmd: "+Integer.toString(lastCommand)+" "+String.join(",", sb));
    }
    
    public ProtocolTremolV10(InputStream in, OutputStream out, EncodingType encoding) {
        super(in, out, encoding);
        mSEQ = SEQ_START;
    }

    public ProtocolTremolV10(FiscalSocket socket, EncodingType encoding) {
        super(socket, encoding);
        mSEQ = SEQ_START;
    }

    public ProtocolTremolV10(FiscalSocket socket) {
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
    public String customCommand(int command, String paramString) throws IOException {
        byte[] paramData = null;
        if (paramString.length() > 0) {
            paramData = new byte[paramString.length()];
            toAnsi(paramString, paramData, 0, mEncoding);
        }
        byte[] data = sendPacket(command, paramData, true);
        if (data != null) {
            return toUnicode(data, 0, data.length, mEncoding);
        } else
            return "";
    }

    public String customCommandB(int command, byte[] paramData) throws IOException {
        byte[] data = sendPacket(command, paramData, true);
        if (data != null) {
            return toUnicode(data, 0, data.length, mEncoding);
        } else
            return "";
    }
    
    /**
     * Sends one way command to device without response
     * @param command
     * @param paramString
     * @throws IOException 
     */
    public void customSimpleCommand(int command, String paramString) throws IOException {
        byte[] paramData = null;
        if (paramString.length() > 0) {
            paramData = new byte[paramString.length()];
            toAnsi(paramString, paramData, 0, mEncoding);
        }
        sendPacket(command, paramData, false);
    }

    public String rawRead(int byteCount, byte stopByte) throws IOException {
        byte[] readBuf = new byte[MAX_RAW_READ_BUFSIZE];
        int OLD_READ_TIMEOUT = READ_TIMEOUT;
        READ_TIMEOUT = 120000; // ms
        LOGGER.finest("RAW READ Byte_Count="+Integer.toString(byteCount)+" Stop_Byte="+byteArrayToHex(new byte[] {stopByte}));
        int offset = 0;
        try {
            int maxBytes = byteCount;
            if (maxBytes < 1)
                maxBytes = readBuf.length;
            maxBytes = Math.min(maxBytes, readBuf.length);
            do {
                byte b = (byte)read();
                if (b == stopByte) break;
                readBuf[offset] = b;
                offset++;
            } while (offset < maxBytes);
        } catch (Exception e) {
			LOGGER.severe(e.getMessage());
        }
		READ_TIMEOUT = OLD_READ_TIMEOUT;
        LOGGER.finest("Count of bytes read="+Integer.toString(offset));
		if (offset > 0) {
			// Log first 100 bytes
	        LOGGER.finest("First 100 bytes read: "+byteArrayToHex(Arrays.copyOfRange(readBuf, 0, Integer.min(100, offset))));
		}
        return toUnicode(readBuf, 0, offset, mEncoding);
    }

    @Override
    public boolean isStatusBitTriggered(int paramInt1, int paramInt2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] getStatusBytes() {
        byte[] sBytes = new byte[mSB.length];
        try {
            sBytes = sendPacket(0x20, null, true);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return sBytes;
    }
    
    
    protected boolean doPingAE() throws IOException { // ping via antiecho
        byte[] buf = new byte[2];
        buf[0]= ZFP_ANTIECHO;
        buf[1]= ZFP_PING; // 04
        LOGGER.finest("PINGAE > "+byteArrayToHex(buf));
        write(buf, 0, 2);
        byte b = (byte)read();
        LOGGER.finest("PONGGAE > "+byteArrayToHex(new byte[] {b}));
        if (b != buf[0])
            throw new IOException("PING_AE: Antiecho not received!");
        b = (byte)read();
        if (b != buf[1])
            throw new IOException("PING_AE: Antiecho not received match pong!");
        return true;
    }

    protected boolean doPing() throws IOException { // simple ping to check if device is power on
        clear();
        LOGGER.finest("PING > "+byteArrayToHex(new byte[] {ZFP_PING}));
        write(ZFP_PING);
        byte b = (byte)read();
        LOGGER.finest("PONG < "+byteArrayToHex(new byte[] {b}));
        if (b != ZFP_PING)
            throw new IOException("PING: Not received match pong!");
        return true; // FD is on
    }

    protected boolean doPing2() throws IOException { // ping via extended device status
        return doPing2(3);
    }
    protected boolean doPing2(int retryIfBusy) throws IOException { // ping via extended device status
        // PC sends single 09 byte
        // FP/ECR replies with one byte:
        // 40 - Device is ready OK
        // Bit 0 = 1 - device is busy
        // Bit 1 = 1 - paper out
        // Bit 2 = 1 - printer is overheated
        // Bit 3 = 1 - missing external display
        // 50 - FD is waiting for password (LAN or WiFi connection only)
        // 60 - FD is already busy with another connection (LAN or WiFi connection only)
        // 70 - Wrong password (LAN or WiFi connection only)!
        byte b = 0;
        int OLD_READ_TIMEOUT = READ_TIMEOUT;
        READ_TIMEOUT = 500; // ms
        do {
            try {
                clear();
                LOGGER.finest("PING > "+byteArrayToHex(new byte[] {ZFP_PING2}));
                write(ZFP_PING2);
                b = (byte)read();
            } catch (Exception ex) {
                LOGGER.finest("Pong read timeout reached!");
                b = (byte)0x41; // Fake busy
            }
            LOGGER.finest("PONG < "+byteArrayToHex(new byte[] {b}));
            if (b == (byte)0x40)
                return true; // FD is READY
            if (b == (byte)0x41) {
                try {
                    // FD is busy only no other errors
                    // wait and try again
                    LOGGER.finest("Device is Busy Retry_Count="+Integer.toString(retryIfBusy));
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                } 
            } else
                retryIfBusy = 0; // break;
            retryIfBusy--;
        } while (retryIfBusy >= 0);    
        READ_TIMEOUT = OLD_READ_TIMEOUT; // ms
        StringBuilder sb = new StringBuilder();
        if ((b & (byte)0x01) != 0)
            sb.append("Device is busy!"); // FD is busy
        if ((b & (byte)0x02) != 0)
            sb.append("Device is out of paper!"); // FD is busy
        if ((b & (byte)0x04) != 0)
            sb.append("Device is overheated!");   // FD is overheated
        if ((b & (byte)0x08) != 0)
            sb.append("Missing external display!");   // FD is overheated
        if (b == (byte)0x50)
            sb.append("FD is waiting for password (LAN or WiFi connection only)!");   //
        if (b == (byte)0x60)
            sb.append("FD is already busy with another connection (LAN or WiFi connection only)!");   //
        if (b == (byte)0x70)
            sb.append("Wrong password (LAN or WiFi connection only)!");   //
        if (sb.length() == 0) {
            sb.append("Unknown response from the device!");
        }
        throw new IOException("PING2: "+sb.toString());
    }
    
    protected byte[] getReceiptPacket(byte[] readBuf) throws IOException {
        // expecting to receive ACK packet
        //   0    1    2   3    4   5    6
        // {ACK}{NBL}{STE}{STE}{CS}{CS}{ETX}
        
        // First ACK byte was already read offset in buffer is 1
        // Read rest of ACK packet bytes
        read(readBuf, 1, ZFP_RECEIPTLEN-1);
        LOGGER.finest("RCP < "+byteArrayToHex(readBuf, 0, ZFP_RECEIPTLEN));
        mCMDSB[0] = 0; mCMDSB[1] = 0;
		if (ZFP_ETX != readBuf[ZFP_RECEIPTLEN - 1]) {// missing ETX
            throw new IOException("Bad Receive data!");
		}	
        byte[] crc = calcCRC(readBuf, 1, 4);
        if ((crc[0] != readBuf[4]) || (crc[1] != readBuf[5]))
            throw new IOException("CRC Error!");
        // Check Status Bytes 
        mCMDSB[0] = readBuf[2]; // Device Status byte
        mCMDSB[1] = readBuf[3]; // Command Status byte
        if ((mCMDSB[0] != (byte)0x30) || (mCMDSB[1] != (byte)0x30)) {
            // there is error in FD or error in command
            // TODO: Explain detailed information about error
            checkACKResult(mCMDSB[0], mCMDSB[1]);
//            throw new IOException("Ther is error in command or FD!");
        }
        return Arrays.copyOfRange(readBuf, 2, 4);
    }

    protected byte[] getResponsePacket(byte[] readBuf) throws IOException {
        // TODO: Transform error responses to exceptions
        // First byte STX was already read, offset in buffer is 1
        // read until ETX or max buffer len
        int offs = 0; 
        while ((offs < MAX_RECEIVE_PACKET_SIZE) && (readBuf[offs] != ZFP_ETX)) {
            read(readBuf, ++offs, 1);
        }
        LOGGER.finest("RES < "+byteArrayToHex(readBuf, 0, offs));
        if (offs >= MAX_RECEIVE_PACKET_SIZE) 
            throw new IOException("Invalid response! Max receive packet size exceeded.");
        // Double ensurance
        if (readBuf[offs] != ZFP_ETX) 
            throw new IOException("Invalid response! ETX was expected.");
        //   0    1    2    3          offs-2 offs-1
        // {STX}{LEN}{NBL}{CMD}{DATA…DATA}{CS}{CS}{ETX}
        if (offs < 6) {
            // Invalid packet size received
            throw new IOException("Invalid response! Packet too short!");
        }
        // check mSEQ matching
        if (readBuf[2] != mSEQ) 
            throw new IOException("Invalid packed se`uence numner (NBL)!");
        // check CRC
        byte[] crc = calcCRC(readBuf, 1, offs-2);
        if ((crc[0] != readBuf[offs-2]) || (crc[1] != readBuf[offs-1])) {
            throw new IOException("CRC Error!");
        }
        int dataLen = offs-6;
        return Arrays.copyOfRange(readBuf, 4, dataLen+4);
//        String data = toUnicode(readBuf, 4, dataLen, mEncoding);
        
//        return data;
    }
    
    protected byte[] calcCRC(byte[] buf, int spos, int epos) {
        byte crc = 0;
        for (int i = spos; i < epos; i++) {
            crc ^= buf[i];
        }
        byte[] crcbuf = new byte[2];
        crcbuf[0] = ((byte) ((crc >>  4 & 0xF) + 0x30));
        crcbuf[1] = ((byte) ((crc >>  0 & 0xF) + 0x30));
        return crcbuf;
    }
    
    protected byte[] sendPacket(int command, byte[] data, boolean getResponse) throws IOException {
        // {STX}{LEN}{NBL}{CMD}{DATA…DATA}{CS}{CS}{ETX}
        lastCommand = command;
        doPing2(100); // check and wait device to be ready!
        clear();
        byte[] buf = new byte[MAX_SEND_PACKET_SIZE];
        int offs = 0;
        int len;

        len = data != null ? data.length : 0;
        if (len > MAX_SEND_DATASIZE) {
            throw new IllegalArgumentException("Lenght of data exceeds the limits ("+Integer.toString(MAX_SEND_DATASIZE)+"!)");
        }

        buf[(offs++)] = ZFP_STX;
        // 0x20 + 3 + len(data) = 1(LEN)+1(NBL)+1(CMD)+len
        buf[(offs++)] = ((byte) (0x20 + 3 + len));

        buf[(offs++)] = ((byte) mSEQ);

        buf[(offs++)] = ((byte) command);
        for (int i = 0; i < len; i++)
            buf[(offs++)] = data[i];
//        offs += len;

        // Calc CRC
        byte[] crc = calcCRC(buf, 1, offs);
        buf[(offs++)] = crc[0];
        buf[(offs++)] = crc[1];

        buf[(offs++)] = ZFP_ETX;
        int retryCount = sendRetryCount;
        byte[] returnResult = null;
        byte[] readBuf = new byte[MAX_RECEIVE_PACKET_SIZE];
        do {
            // Send packet
            LOGGER.finest(" > "+byteArrayToHex(buf, 0, offs));
            write(buf, 0, offs);
            if (!getResponse) {
                //Command will not expect response
              	returnResult = null;
                break; 
            }    
            // read until receive recognized response byte
            // or Exception is occured (particular timeout)
            do {
                read(readBuf, 0, 1);
            } while ( 
               (readBuf[0] != ZFP_NACK)
               && (readBuf[0] != ZFP_ACK)
               && (readBuf[0] != ZFP_RETRY)
               && (readBuf[0] != ZFP_ANTIECHO)
               && (readBuf[0] != ZFP_STX)
            ); 
            // NACK Handling
            if ((readBuf[0] == ZFP_NACK) && (retryCount > 0)) {
                LOGGER.finest(" NACK: "+byteArrayToHex(readBuf, 0, 1)+" retry count="+Integer.toString(retryCount));
               retryCount--;
               if (retryCount > 0) continue; // resend packet
            }
            if (readBuf[0] == ZFP_NACK) {
                // Max retries reached
                throw new IOException("NACK received! Rety count was reached.");
            }
            // ACK handling
            if (readBuf[0] == ZFP_ACK) {
                // Expecting ACK packet
               LOGGER.finest(" ACK: "+byteArrayToHex(readBuf, 0, 1));
                returnResult = getReceiptPacket(readBuf);
                break;
            }
            // ZFP_RETRY Handling
            if (readBuf[0] == ZFP_RETRY) {
               LOGGER.finest(" RETRY: "+byteArrayToHex(readBuf, 0, 1)+" retry count="+Integer.toString(retryCount));
               retryCount--;
               if (retryCount > 0) continue; // resend packet
               throw new IOException("Number of retries was reached!");
            }
            // ZFP_ANTIECHO handling
            if (readBuf[0] == ZFP_ANTIECHO) {
               LOGGER.finest(" ANTIECHO: "+byteArrayToHex(readBuf, 0, 1)+" retry count="+Integer.toString(retryCount));
               throw new IOException("Ivalid device! ANTIECHO received.");
            }
            // Expecting data packet have to be ZFP_STX
            if (readBuf[0] == ZFP_STX) {
               LOGGER.finest(" STX: "+byteArrayToHex(readBuf, 0, 1));
               returnResult = getResponsePacket(readBuf);
               break;
            }
            // Error: not expected response
            LOGGER.finest(" RESPONSE BYTE: "+byteArrayToHex(readBuf, 0, 1));
            throw new IOException("Ivalid response!");
        } while (false);    
		return returnResult;
    }
    
    protected String receivePacket() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public String auxCommand(byte [] data) throws IOException {
        /*
        <CR><LF> command  [parameter 1] [parameter 2]<CR><LF>
        The commands are presented by line of ASCII characters, which must begin and end with the sequence <CR><LF> (0x0d, 0x0a). 
        The terminal monitor this sequence thus recognizes that data are intended for him 
        (in fiscal device the format command type is 0xAA, 0x55 ...). 
        In transmission of parameters for dividers is used interval (0x20) or tab (0x09). 
        After processing of command, the terminal always returns an answer for the result of its implementation.

        NOTE: The names of commands, parameters, files and directories are case sensitive.
        */
        nextSEQ();
        mSocket.clear();

        LOGGER.finest(" AUX > "+byteArrayToHex(data));
        StopWatch go = new StopWatch();
        
        clear();
        write(data, 0, data.length);
        // read answer
        int readBuffSize = 8*1024*1024;// 8M
        byte[] readBuf = new byte[readBuffSize]; 
        int b;
        int offset = 0;
        // read with minimized timeout and ignore timeout eceptions
        int OLD_READ_TIMEOUT = READ_TIMEOUT;
        READ_TIMEOUT = 500; // ms
        try {
            do {
                try {
                    b = read();
                } catch (IOException ioe) {
                    // no data timeout reached
                    break;
                }    
                if (offset < readBuffSize) {
                   readBuf[offset++] = (byte)(b & 0xFF);
                } else {
                  LOGGER.warning("Receive buffer overflow!");
                  break;
                }  
            } while (true); 
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
        READ_TIMEOUT = OLD_READ_TIMEOUT; // ms
        LOGGER.finest(" AUX < "+byteArrayToHex(readBuf, 0, offset));
        String result = toUnicode(readBuf, 0, offset, mEncoding);
        return result;
    }
    
}
