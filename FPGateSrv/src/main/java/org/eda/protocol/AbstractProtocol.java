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
import java.util.logging.Logger;

/**
 * Defines abstract transport protocol to the fiscal device.
 * The communication to the device is via FiscalSocket object.
 * 
 * @author Dimitar Angelov
 */
public abstract class AbstractProtocol {

    protected static final Logger logger = Logger.getLogger(AbstractProtocol.class.getName());
    
    protected EncodingType mEncoding; 
    protected final FiscalSocket mSocket;

    public static enum EncodingType {
        CP_866, CP_1250, CP_1251, ANSI
    };
    
    protected static final int DEFAULT_TIMEOUT = 6000;
    protected int READ_TIMEOUT = 6000;
    protected static final int MAX_RETRIES = 2;
    public static final int[] CP_866 = {1040, 128, 1041, 129, 1042, 130, 1043, 131, 1044, 132, 1045, 133, 1046, 134, 1047, 135, 1048, 136, 1049, 137, 1050, 138, 1051, 139, 1052, 140, 1053, 141, 1054, 142, 1055, 143, 1056, 144, 1057, 145, 1058, 146, 1059, 147, 1060, 148, 1061, 149, 1062, 150, 1063, 151, 1064, 152, 1065, 153, 1066, 154, 1067, 155, 1068, 156, 1069, 157, 1070, 158, 1071, 159, 1072, 160, 1073, 161, 1074, 162, 1075, 163, 1076, 164, 1077, 165, 1078, 166, 1079, 167, 1080, 168, 1081, 169, 1082, 170, 1083, 171, 1084, 172, 1085, 173, 1086, 174, 1087, 175, 9617, 176, 9618, 177, 9619, 178, 9474, 179, 9508, 180, 9569, 181, 9570, 182, 9558, 183, 9557, 184, 9571, 185, 9553, 186, 9559, 187, 9565, 188, 9564, 189, 9563, 190, 9488, 191, 9492, 192, 9524, 193, 9516, 194, 9500, 195, 9472, 196, 9532, 197, 9566, 198, 9567, 199, 9562, 200, 9556, 201, 9577, 202, 9574, 203, 9568, 204, 9552, 205, 9580, 206, 9575, 207, 9576, 208, 9572, 209, 9573, 210, 9561, 211, 9560, 212, 9554, 213, 9555, 214, 9579, 215, 9578, 216, 9496, 217, 9484, 218, 9608, 219, 9604, 220, 9612, 221, 9616, 222, 9600, 223, 1088, 224, 1089, 225, 1090, 226, 1091, 227, 1092, 228, 1093, 229, 1094, 230, 1095, 231, 1096, 232, 1097, 233, 1098, 234, 1099, 235, 1100, 236, 1101, 237, 1102, 238, 1103, 239, 1025, 240, 1105, 241, 1028, 242, 1108, 243, 1031, 244, 1111, 245, 1038, 246, 1118, 247, 176, 248, 8729, 249, 183, 250, 8730, 251, 8470, 252, 164, 253, 9632, 254, 160, 255};
    public static final int[] CP_1250 = {128, 8364, 130, 8218, 132, 8222, 133, 8230, 134, 8224, 135, 8225, 137, 8240, 138, 352, 139, 8249, 140, 346, 141, 356, 142, 381, 143, 377, 145, 8216, 146, 8217, 147, 8220, 148, 8221, 149, 8226, 150, 8211, 151, 8212, 153, 8482, 154, 353, 155, 8250, 156, 347, 157, 357, 158, 382, 159, 378, 160, 160, 161, 711, 162, 728, 163, 321, 164, 164, 165, 260, 166, 166, 167, 167, 168, 168, 169, 169, 170, 350, 171, 171, 172, 172, 173, 173, 174, 174, 175, 379, 176, 176, 177, 177, 178, 731, 179, 322, 180, 180, 181, 181, 182, 182, 183, 183, 184, 184, 185, 261, 186, 351, 187, 187, 188, 317, 189, 733, 190, 318, 191, 380, 192, 340, 193, 193, 194, 194, 195, 258, 196, 196, 197, 313, 198, 262, 199, 199, 200, 268, 201, 201, 202, 280, 203, 203, 204, 282, 205, 205, 206, 206, 207, 270, 208, 272, 209, 323, 210, 327, 211, 211, 212, 212, 213, 336, 214, 214, 215, 215, 216, 344, 217, 366, 218, 218, 219, 368, 220, 220, 221, 221, 222, 354, 223, 223, 224, 341, 225, 225, 226, 226, 227, 259, 228, 228, 229, 314, 230, 263, 231, 231, 232, 269, 233, 233, 234, 281, 235, 235, 236, 283, 237, 237, 238, 238, 239, 271, 240, 273, 241, 324, 242, 328, 243, 243, 244, 244, 245, 337, 246, 246, 247, 247, 248, 345, 249, 367, 250, 250, 251, 369, 252, 252, 253, 253, 254, 355, 255, 729};
    public static final int[] CP_1251 = {1026, 128, 1027, 129, 8218, 130, 1107, 131, 8222, 132, 8230, 133, 8224, 134, 8225, 135, 8364, 136, 8240, 137, 1033, 138, 8249, 139, 1034, 140, 1036, 141, 1035, 142, 1039, 143, 1106, 144, 8216, 145, 8217, 146, 8220, 147, 8221, 148, 8226, 149, 8211, 150, 8212, 151, 8482, 153, 1113, 154, 8250, 155, 1114, 156, 1116, 157, 1115, 158, 1119, 159, 160, 160, 1038, 161, 1118, 162, 1032, 163, 164, 164, 1168, 165, 166, 166, 167, 167, 1025, 168, 169, 169, 1028, 170, 171, 171, 172, 172, 173, 173, 174, 174, 1031, 175, 176, 176, 177, 177, 1030, 178, 1110, 179, 1169, 180, 181, 181, 182, 182, 183, 183, 1105, 184, 8470, 185, 1108, 186, 187, 187, 1112, 188, 1029, 189, 1109, 190, 1111, 191, 1040, 192, 1041, 193, 1042, 194, 1043, 195, 1044, 196, 1045, 197, 1046, 198, 1047, 199, 1048, 200, 1049, 201, 1050, 202, 1051, 203, 1052, 204, 1053, 205, 1054, 206, 1055, 207, 1056, 208, 1057, 209, 1058, 210, 1059, 211, 1060, 212, 1061, 213, 1062, 214, 1063, 215, 1064, 216, 1065, 217, 1066, 218, 1067, 219, 1068, 220, 1069, 221, 1070, 222, 1071, 223, 1072, 224, 1073, 225, 1074, 226, 1075, 227, 1076, 228, 1077, 229, 1078, 230, 1079, 231, 1080, 232, 1081, 233, 1082, 234, 1083, 235, 1084, 236, 1085, 237, 1086, 238, 1087, 239, 1088, 240, 1089, 241, 1090, 242, 1091, 243, 1092, 244, 1093, 245, 1094, 246, 1095, 247, 1096, 248, 1097, 249, 1098, 250, 1099, 251, 1100, 252, 1101, 253, 1102, 254, 1103, 255};


    /**
     * Instantiate via input and output stream.
     * The FiscalSocked object is created internally over input/output stream.
     * @param in
     * @param out 
     * @param encoding - Defines message data encoding CP_*
     */
    protected AbstractProtocol(InputStream in, OutputStream out, EncodingType encoding) {
        mSocket = new FiscalSocket(in, out);
        mEncoding = encoding;
    }

    /**
     * Instantiate over preliminary created FiscalSocket object.
     * @param socket
     * @param encoding - Defines message data encoding CP_*
     */
    protected AbstractProtocol(FiscalSocket socket, EncodingType encoding) {
        mSocket = socket;
        mEncoding = encoding;
    }

    protected AbstractProtocol(FiscalSocket socket) {
        this(socket, EncodingType.CP_1251);
    }

    public static void toAnsi(String str, byte[] data, int offset, EncodingType encoding) {
        if (str != null) {
            for (int s = 0; s < str.length(); s++) {
                char c = str.charAt(s);
                data[(offset + s)] = ((byte) c);
                if (c >= '\u0080') {
                    switch (encoding) {
                        case CP_1250:
                            for (int i = 0; i < CP_1250.length; i += 2) {
                                if (CP_1250[i] == c) {
                                    data[(offset + s)] = ((byte) CP_1250[(i + 1)]);
                                    break;
                                }
                            }

                        case CP_1251:
                            for (int i = 0; i < CP_1251.length; i += 2) {
                                if (CP_1251[i] == c) {
                                    data[(offset + s)] = ((byte) CP_1251[(i + 1)]);
                                    break;
                                }
                            }
                            break;
                        case CP_866:
                            for (int i = 0; i < CP_866.length; i += 2) {
                                if (CP_866[i] == c) {
                                    data[(offset + s)] = ((byte) CP_866[(i + 1)]);
                                    break;
                                }
                            }

                            break;
                        default:
                            data[(offset + s)] = ((byte) c);
                    }
                }
            }
        }
    }

    protected static String toUnicode(byte[] data, int offset, int length, EncodingType encoding) {
        StringBuilder sb = new StringBuilder(length);

        for (int s = 0; s < length; s++) {
            char c = (char) (data[(offset + s)] & 0xFF);
            if (c < '\u0080') {
                sb.append(c);
            } else {
                switch (encoding) {
                    case CP_1250:
                        for (int i = 0; i < CP_1250.length; i += 2) {
                            if (CP_1250[(i + 1)] == c) {
                                sb.append((char) CP_1250[i]);
                                break;
                            }
                        }

                    case CP_1251:
                        for (int i = 0; i < CP_1251.length; i += 2) {
                            if (CP_1251[(i + 1)] == c) {
                                sb.append((char) CP_1251[i]);
                                break;
                            }
                        }

                        break;

                    case CP_866:
                        for (int i = 0; i < CP_866.length; i += 2) {
                            if (CP_866[(i + 1)] == c) {
                                sb.append((char) CP_866[i]);
                                break;
                            }
                        }

                        break;

                    default:
                        sb.append(c);
                }

            }
        }

        return sb.toString();
    }

    protected int read() throws IOException {
        return mSocket.read(READ_TIMEOUT);
    }

    protected void read(byte[] buf, int offset, int len) throws IOException {
        mSocket.read(buf, offset, len, READ_TIMEOUT);
    }
    
    protected void write(byte[] buf, int offset, int len) throws IOException {
        mSocket.write(buf, offset, len);
        mSocket.flush();
        
    }

    protected void write(byte b) throws IOException {
        mSocket.write(b);
        mSocket.flush();
        
    }
    
    public void setEncoding(EncodingType encoding) {
        mEncoding = encoding;
    }

    public void open() throws IOException {
        mSocket.open();
    }
    public void close() throws IOException {
        mSocket.close();
    }
    
    public boolean isOpen() {
        return mSocket.isOpen();
    }

    public abstract String customCommand(int paramInt, String paramString) throws IOException;

    public abstract boolean isStatusBitTriggered(int paramInt1, int paramInt2);
    public abstract byte[] getStatusBytes();

    protected abstract void sendPacket(int paramInt, String paramString) throws IOException;

    protected abstract String receivePacket() throws IOException;

}
