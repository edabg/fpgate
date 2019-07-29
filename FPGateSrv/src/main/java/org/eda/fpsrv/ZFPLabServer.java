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
package org.eda.fpsrv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eda.fdevice.FPCBase;

/**
 * This class stats Tremol ZFPLab Server
 * @author Dimitar Angelov
 */
public class ZFPLabServer {
    protected static final Logger LOGGER = Logger.getLogger(FPCBase.class.getName());

    public static Logger getLogger() {
        return LOGGER;
    }
    protected static String SERVER_PATH = "lib/tremol";
    protected static String SERVER_WINDOWS = "ZFPLabServer.exe";
    protected static String SERVER_LINUX = "zfplabserver";
//    protected final String SERVER_ARM = "zfplabserver_arm";
    
    protected static ProcessBuilder pBuilder = null;
    protected static Process pProcess = null;
    protected static ShutdownProcessHook shutdownHook = null;
    
    public static class ShutdownProcessHook extends Thread {
        @Override
        public void run(){
            System.out.println("Shutdown hook processed stopping server");
            ZFPLabServer.stop();
        }
    }    

    protected static String getServerExecutable() {
        return FPServer.application.getAppBasePath()+"/"+SERVER_PATH+"/"+(System.getProperty("os.name").startsWith("Windows")?SERVER_WINDOWS:SERVER_LINUX);
    }
    
    public static void start() throws Exception{
        List<String> args = new ArrayList<String>();
        args.add (getServerExecutable());
//        args.add ("nogui");
        LOGGER.info("Starting "+String.join(" ", args));
        ProcessBuilder pb = new ProcessBuilder (args);
        try {
            pProcess = pb.start();
            if (shutdownHook == null) {
                shutdownHook = new ShutdownProcessHook();
                Runtime.getRuntime().addShutdownHook(shutdownHook);
            }
            LOGGER.info("ZFPLabServer started successfully.");
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            throw new Exception("Error while starting ZFPLabServer!");
        }
    }

    public static boolean isStarted() {
        return (pProcess != null);
    }
    
    public static void stop() {
        if (pProcess != null) {
            LOGGER.info("Stopping ZFPLabServer.");
            pProcess.destroy();
            pProcess = null;
        }
        if (pBuilder != null) {
            pBuilder = null;
        }
//        if (shutdownHook != null) {
//            Runtime.getRuntime().removeShutdownHook(shutdownHook);
//            shutdownHook = null;
//        }
    }
}
