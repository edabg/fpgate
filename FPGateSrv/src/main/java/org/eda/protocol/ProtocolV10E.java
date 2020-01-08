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
import static org.eda.protocol.AbstractProtocol.LOGGER;

/**
 * This is enhanced version of ProtocolV10 for Eltrade devices.
 * Implements RAW terminal commands and stream reading.
 * 
 * @author Dimitar Angelov
 */
public class ProtocolV10E extends ProtocolV10 {
    
    public ProtocolV10E(InputStream in, OutputStream out, AbstractProtocol.EncodingType encoding) {
        super(in, out, encoding);
    }

    public ProtocolV10E(FiscalSocket socket, AbstractProtocol.EncodingType encoding) {
        super(socket, encoding);
    }

    public ProtocolV10E(FiscalSocket socket) {
        super(socket);
    }
    
    public String customRawCommand(String command) throws IOException {
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

        LOGGER.finest(String.format("> %s", new Object[]{command}));
        StopWatch go = new StopWatch();
        // prepare out
        int bufLen = command.length()+4;
        byte[] sendBuf = new byte[bufLen];
        sendBuf[0] = (byte)0x0D;
        sendBuf[1] = (byte)0x0A;
        toAnsi(command, sendBuf, 2, mEncoding);
        sendBuf[bufLen-2] = (byte)0x0D;
        sendBuf[bufLen-1] = (byte)0x0A;
        // send data
        write(sendBuf, 0, bufLen);
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
        String result = toUnicode(readBuf, 0, offset, mEncoding);
        
        LOGGER.finest(String.format("< %s (%d bytes) in %dms", new Object[]{command, result.length(), Integer.valueOf((int) go.getElapsedTime())}));
        return result;
    }
    
}
