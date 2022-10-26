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
package org.eda.fdevice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.restlet.ext.crypto.DigestUtils;

/**
 *
 * @author Dimitar Angelov
 */
public class FPPrinterPool {
    protected static final Logger LOGGER = FPCBase.getLogger();
    
    private static long printerDeadTime = 5*60*1000; // 5 min

    private static HashMap<String, FPCBase> FPPool = new HashMap<>();
    
    private static boolean poolEnabled = true;
    
    private static ScheduledExecutorService poolCleanScheduler;

    public static boolean isPoolEnabled() {
        return poolEnabled;
    }

    public static void setPoolEnabled(boolean poolEnabled) {
        FPPrinterPool.poolEnabled = poolEnabled;
        if (FPPrinterPool.poolEnabled) {
            startPoolCleanScheduler();
        }  else {
            stopPoolCleanScheduler();
            clear();
        }  
    }

    public static long getPrinterDeadTime() {
        return printerDeadTime;
    }

    public static void setPrinterDeadTime(long printerDeadTime) {
        FPPrinterPool.printerDeadTime = printerDeadTime;
    }
    
    protected static String getUID(String ID, String Model, HashMap<String, String> Params) {
        HashMap<String, String> ToHash = new HashMap<>();
        ToHash.put("ID", ID);
        ToHash.put("Model", Model);
        ToHash.putAll(Params);
        String json = ID+"/"+Model+"/"+Params.toString(); // for fallback only in case of exception next
        try {
            json = new ObjectMapper().writeValueAsString(ToHash);
        } catch (JsonProcessingException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return DigestUtils.toMd5(json);
    }
    
    public static synchronized FPCBase requestPrinter(String ID, String Model, HashMap<String, String> Params) {
        LOGGER.info("Requesting printer object ID:"+ID+" Model:"+Model+" Params: "+((Params.size() > 0)?Params.keySet().toString():"none"));
        String UID = getUID(ID, Model, Params);
        FPCBase FP = null;
        try {
            // find object in Pool
            if (FPPool.containsKey(UID)) {
                LOGGER.info("Printer found in cache pool");
                FP = FPPool.get(UID);
                if ((FP.getLifetime() > printerDeadTime) && !FP.isLocked()) {
                    // instance is outdated have to release
                    LOGGER.info("Printer instance it too old. Releasing printer object.");
                    FP.destroy();
                    FPPool.remove(UID);
                    FP = null;
                } else
                    FP.prolongLifetime();
            }
            if (FP == null) {
                LOGGER.info("Requesting new printer object");
                FP = FPCBase.getFPCObjectByParams(ID, Model, Params);
                try {
                    FP.setUID(UID);
                    FP.init();
                    FPPool.put(UID, FP);
                } catch (Exception E) {
                    FP.destroy();
                    FP = null;
                    throw E;
                }
            }    
        } catch (Exception E) {
            LOGGER.log(Level.SEVERE, E.getMessage(), E);
        }
        return FP;
    }
    
    public static synchronized void releasePrinter(FPCBase FP) {
        if (FP != null) {
            if (!poolEnabled && !FP.isLocked()) {
                LOGGER.info("Pool is disabled. Releasing the printer object.");
                if (FPPool.containsKey(FP.getUID()))
                    FPPool.remove(FP.getUID());
                FP.destroy();
            }
        }
    }

    public static synchronized int getPoolSize() {
        return FPPool.keySet().size();
    }
    
    protected static synchronized void cleanPool() {
        LOGGER.info("Autoclean printer pool started. Size="+Integer.toString(FPPool.keySet().size()));
        Iterator<String> it = FPPool.keySet().iterator();
        while (it.hasNext()) {
          String key = it.next();
          FPCBase fp_ = FPPool.get(key);
          try {
              if ((fp_.getLifetime() > printerDeadTime)) {
                LOGGER.fine(fp_.getUID()+" was expired and vahe to be cleaned.");
                if (!fp_.isLocked()) {
                    fp_.destroy();
                    FPPool.remove(key);
                    LOGGER.info(fp_.getUID()+" was cleaned.");
  //                  it.remove();
                } else
                    LOGGER.info(key+" is locked and can't be removed.");
              } else {
                  LOGGER.fine(fp_.getUID()+" is in time.");
              } 
          } catch (Exception E) {
            E.printStackTrace();
          }
//          fp_ = null;
        }   
        LOGGER.info("Autoclean printer pool finished. Size="+Integer.toString(FPPool.keySet().size()));
    } 
    
    public static synchronized void clear() {
        LOGGER.info("Begin clearing printer pool. Size="+Integer.toString(FPPool.size()));
        String[] keys = FPPool.keySet().toArray(new String[FPPool.size()]);
		for(int i = 0; i < keys.length; i++) {
          String key = keys[i];
          FPCBase fp_ = FPPool.get(key);
          try {
              if (!fp_.isLocked()) {
                  fp_.destroy();
                  FPPool.remove(key);
//                  it.remove();
              } else
                  LOGGER.info(key+" is locked and can't be removed.");
          } catch (Exception E) {
            E.printStackTrace();
          }
//          fp_ = null;
        }   
        LOGGER.info("End clearing printer pool. Size="+Integer.toString(FPPool.size()));
    }
    
    public static void startPoolCleanScheduler() {
        startPoolCleanScheduler(printerDeadTime, TimeUnit.MILLISECONDS);
    }
    
    public static void startPoolCleanScheduler(long interval, TimeUnit timeU) {
        // Initiate Colibri ERP Session maintain
        if (poolCleanScheduler != null)
            stopPoolCleanScheduler();
        LOGGER.info("Starting printer pool autoclean.");
        poolCleanScheduler = Executors.newScheduledThreadPool(1);
        poolCleanScheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    cleanPool();
//                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }
            , interval
            , interval
            , timeU
        );
    }

    public static void stopPoolCleanScheduler() {
        if (poolCleanScheduler != null) {
            LOGGER.info("Stopping printer pool autoclean.");
            poolCleanScheduler.shutdownNow();
        }
        poolCleanScheduler = null;
    }
    
    
}
