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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Fiscal device layer2 protocol independent socket working on input/output streams.
 * The sockets directly write to the output stream and creates separate 
 * reading thread from the input stream and queue received data.
 * 
 * @author Dimitar Angelov
 */
public class FiscalSocket {

    protected static final Logger LOGGER = Logger.getLogger(FiscalSocket.class.getName());
    
    private final InputStream in;
    private final OutputStream out;
    private Thread readThread = null;

    private final List<Byte> queue; 
    
    private Listener listener;

    public static abstract interface Listener {
        public abstract void onDisconnect();
    }
    

    private static String byteArrayToHexString(byte[] data, int offset, int length) {
        char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        char[] buf = new char[length * 3];
        int offs = 0;

        for (int i = 0; i < length; i++) {
            buf[(offs++)] = hex[(data[(offset + i)] >> 4 & 0xF)];
            buf[(offs++)] = hex[(data[(offset + i)] >> 0 & 0xF)];
            buf[(offs++)] = ' ';
        }

        return new String(buf, 0, offs);
    }

    public FiscalSocket(final InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        queue = new java.util.LinkedList();
    }

    public void open() throws IOException {
        //TODO: It is not good idea to start thread immediately.
        // It is better to start after first write for example or in separate method Start!
        LOGGER.fine("Opening fiscal device socket");
        clear();
        readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.fine("Read thread begin");
                boolean isInterrupted = false;
                byte[] buffer = new byte[1024]; // 1K read buffer 
                try {
                    do {
                        int readed = 0;
                        try {
                            // read timeouts are not handled here
                            // In SerialComm timeouts have to be confgured correctly
                            readed = in.read(buffer);
                        } catch (IOException ioe) {
                            LOGGER.finest("Read thread IO:"+ioe.getMessage());
                        }
                        if (readed == 0) { // No bytes read
                            try {
                                Thread.sleep(10L); // release for other threads for 10 miliseconds
                            } catch (InterruptedException lie) {
                                // Until sleeps can be interrupted by another 
                                isInterrupted = true;
                                LOGGER.fine("Read thread interrupted. "+lie.getMessage());
                            }
                        } else {
                            if (readed < 0) {
                                throw new IOException("The end of the stream has been reached");
                            }
                            if (readed > 0) {
                                LOGGER.fine("receive(" + readed + "): " + FiscalSocket.byteArrayToHexString(buffer, 0, readed));
                                // Append packet bytes to the queue and notify it
                                synchronized (queue) {
                                    for (int el = 0; el < readed; el++) {
                                        queue.add(Byte.valueOf(buffer[el]));
                                    }
                                    queue.notify();
                                }
                            }
                        }
                    } while (!isInterrupted);
                } catch (IOException ioe) {
                    LOGGER.fine("Read thread:"+ioe.getMessage());
                } finally {
                    FiscalSocket.Listener l = listener;
                    if (l != null) {
                        l.onDisconnect();
                    }
                }
                LOGGER.fine("Read thread end");
            }
        });
        readThread.start();
    }
    
    public void close() throws IOException {
        LOGGER.fine("Closing fiscal device socket");
        if ((readThread != null) && readThread.isAlive()) {
            readThread.interrupt();
            try {
                readThread.join();
            } catch (InterruptedException iex) {
                LOGGER.log(Level.FINE, null, iex);
            }
            readThread = null;
        }    
        clear();
    }
    
    public boolean isOpen() {
       return ((readThread != null) && readThread.isAlive()); 
    }

    /**
     * Writes 1 byte to the output
     * @param b - byte value
     * @throws IOException
     */
    public void write(int b) throws IOException {
        out.write(b);
    }

    /**
     * Writes whole array of bytes to the output
     * @param b - array of bytes
     * @throws IOException
     */
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    /**
     * Write len bytes from array b at offset off.
     * @param b - array of bytes
     * @param off - offset in array. From 0 to b.length-1
     * @param len - number of bytes
     * @throws IOException
     */
    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        LOGGER.fine("send(" + len + "): " + byteArrayToHexString(b, off, len));
    }

    /**
     * Flushes written data to the output
     * @throws IOException
     */
    public void flush() throws IOException {
        out.flush();
    }

    /**
     * Clears receive queue
     * @throws IOException 
     */
    public void clear() throws IOException {
        synchronized (queue) {
            queue.clear();
        }
    }

    /**
     * Reads one byte from the input or reach timeout. 
     * In case of timeout method raise exception.
     * @param timeout - data receive wait timeout in ms
     * @return Next read byte from the input
     * @throws IOException
     */
    public int read(int timeout) throws IOException {
        synchronized (queue) {
            if (queue.isEmpty()) {
                try {
                    queue.wait(timeout);
                } catch (InterruptedException localInterruptedException) {
                    // In case of read thread interruption/termination
                }

                if (queue.isEmpty()) {
                    throw new IOException("Timeout");
                }
            }

            return ((Byte) queue.remove(0)).byteValue() & 0xFF;
        }
    }

    /**
     * Reads next `bytes` bytes from input.
     * In case of timeout method raise exception.
     * @param bytes - number of bytes to read
     * @param timeout - data receive wait timeout in ms
     * @return - array of bytes read
     * @throws IOException 
     */
    public byte[] read(int bytes, int timeout)
            throws IOException {
        byte[] buf = new byte[bytes];

        for (int i = 0; i < bytes; i++) {
            buf[i] = ((byte) read(timeout));
        }

        return buf;
    }

    /**
     * Reads `len` next bytes from input and store it in `buf` at offset `offset`.
     * In case of timeout method raise exception.
     * @param buf - read buffer
     * @param offset - offset in buffer
     * @param len - bytes to read
     * @param timeout - read timeout in ms
     * @throws IOException 
     */
    public void read(byte[] buf, int offset, int len, int timeout)
            throws IOException {
        for (int i = 0; i < len; i++) {
            buf[(offset + i)] = ((byte) read(timeout));
        }
    }

}
