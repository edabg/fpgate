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
import static org.eda.protocol.ProtocolV10.MAX_SEND_PACKET_SIZE;

/**
 * This is minor different version of low level serial protocol ProtocolV10.
 * The differences are in Maximum Data size and range for packet sequences
 * This protocol is used from Daisy fiscal devices
 * 
 * @author Dimitar Angelov
 */
public class ProtocolV10D extends ProtocolV10 {

    static {
        MAX_SEND_DATASIZE = 200;
        // Maximum send packed size 
        // <01><LEN><SEQ><CMD><DATA><05><BCC><03>
        //   1 + 1  + 1  + 1 + 200 + 1  + 4 + 1 = 210
        MAX_SEND_PACKET_SIZE = 210;

        MAX_RECEIVE_DATASIZE = 200;
        // Maximum receive packed size 
        // <01><LEN><SEQ><CMD><DATA><04><STATUS><05><BCC><03>
        //   1 + 1  + 1  + 1 + 200 + 1  +  6    + 1 + 4 + 1 = 217
        MAX_RECEIVE_PACKET_SIZE = 217;

        SEQ_START = 0x20;
        SEQ_END = 0xFF;
    }
    
    public ProtocolV10D(InputStream in, OutputStream out, EncodingType encoding) {
        super(in, out, encoding);
    }

    public ProtocolV10D(FiscalSocket socket, EncodingType encoding) {
        super(socket, encoding);
    }

    public ProtocolV10D(FiscalSocket socket) {
        super(socket);
    }
    
    protected void sendPacketReceiveStream(int command, String data) throws IOException {
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
                // Next follow to receive stream
                return;
            }
        } // tries
        throw new IOException("Cant communicate with the device! Max retry count reached");
    }
    
    protected String receiveStream() throws IOException {
        int buffSize = 4*1024*1024;// 4M
        byte[] bb = new byte[buffSize]; 
        int b;
        int offset = 0;
        do {
            do {
                b = read();
                if (((b & 0xFF) != 0x01) && ((b & 0xFF) != 0x16) && ((b & 0xFF) != 0x1A))  {
                    if (offset < buffSize)
                        bb[offset++] = (byte)(b & 0xFF);
                    else
                        throw new IOException("Receive buffer overflow!");
                }
            } while (((b & 0xFF) != 0x0A) && ((b & 0xFF) != 0x01)); // end of line or start of packet
            if ((b & 0xFF) != 0x01)
                write((byte)0x11); // next line
        } while ((b & 0xFF) != 0x01); // expects start of packet
        if ((b & 0xFF) != 0x01) {
            receivePacket();
        }
        return toUnicode(bb, 0, offset, mEncoding);
    }

    public String customCommandStream(int command, String data) throws IOException {
        nextSEQ();
        mSocket.clear();

        LOGGER.finest(String.format("> (%d) %s", new Object[]{Integer.valueOf(command), data}));
        StopWatch go = new StopWatch();
        sendPacketReceiveStream(command, data);

        String result = receiveStream();

        LOGGER.finest(String.format("< (%d) \"%s\" in %dms", new Object[]{Integer.valueOf(command), result, Integer.valueOf((int) go.getElapsedTime())}));
        return result;
    }
    
}
