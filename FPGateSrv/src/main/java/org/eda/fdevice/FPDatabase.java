/*
 * Copyright (C) 2015 EDA Ltd.
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

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.eda.fpsrv.FPServer;
/**
 *
 * @author Nikola Bintev
 */

public class FPDatabase {
    protected static final Logger LOGGER = Logger.getLogger(FPCBase.class.getName());
    
    private final String DATABASE_URL;
    
    private final ConnectionSource connectionSource;
    private final Dao<FUser, Integer> fUserDao;
    private final Dao<FPrinter, Integer> fPrinterDao;
    
    public FPDatabase() throws SQLException {

//        System.setProperty(com.j256.ormlite.logger.LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "WARNING");
//        DATABASE_URL = "jdbc:hsqldb:"+FPServer.application.getAppBasePath()+"/fpgate.db";
        DATABASE_URL = "jdbc:h2:"+FPServer.application.getAppBasePath()+"/fpgate.db";
        connectionSource = new JdbcConnectionSource(DATABASE_URL);
        TableUtils.createTableIfNotExists(connectionSource, FPrinter.class);
        TableUtils.createTableIfNotExists(connectionSource, FUser.class);
        //Create a DataAccessObject for a given class;
        fPrinterDao = DaoManager.createDao(connectionSource, FPrinter.class);
        fUserDao = DaoManager.createDao(connectionSource, FUser.class);
//        checkDBAndConvert();
    }

/*
    protected void checkDBAndConvert() {
        String OLD_DB_FILE_PATH = FPServer.application.getAppBasePath()+"/fprinters.db";
        File OLD_DB_FILE = new File(OLD_DB_FILE_PATH);
        if (!OLD_DB_FILE.isFile()) return ; // There id no old DB file
        LOGGER.info("Old database exists check and convert ...");
        String OLD_DATABASE_URL = "jdbc:sqlite:"+FPServer.application.getAppBasePath()+"/fprinters.db";
        ConnectionSource oldConnectionSource;
        Dao<FUser, Integer> oldUserDao;
        Dao<FPrinter, Integer> oldPrinterDao;
        try {
            oldConnectionSource = new JdbcConnectionSource(OLD_DATABASE_URL);
            oldPrinterDao = DaoManager.createDao(oldConnectionSource, FPrinter.class);
            oldUserDao = DaoManager.createDao(oldConnectionSource, FUser.class);
            LOGGER.info("Migrating printers ...");
            int countPrinters = 0;
            for(FPrinter printer : oldPrinterDao.queryForAll()) {
                try {
                    addPrinter(printer);
                    countPrinters++;
                } catch (Exception exp) {
                    LOGGER.severe(exp.getMessage());
                }
            }
            LOGGER.info(Integer.toString(countPrinters)+" Printers converted.");
            LOGGER.info("Migrating Users ...");
            int countUsers = 0;
            for(FUser user : oldUserDao.queryForAll()) {
                try {
                    addUser(user);
                    countUsers++;
                } catch (Exception exu) {
                    LOGGER.severe(exu.getMessage());
                }
            }
            LOGGER.info(Integer.toString(countUsers)+" Users converted.");
            // Close DB
            oldPrinterDao = null;
            oldUserDao = null;
            oldConnectionSource.close();
            oldConnectionSource = null;
        } catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
        // Rename Old DB
        LOGGER.info("Renaming old DB file.");
        File OLD_DB_FILE_REN = new File(OLD_DB_FILE_PATH+".old");
        OLD_DB_FILE.renameTo(OLD_DB_FILE_REN);
    }
*/
	
    public FPrinter getPrinterByRefId(String refId) throws SQLException {
        List<FPrinter> printers = fPrinterDao.queryForEq("RefId", refId);
        
        if(printers.size() > 0) {
            return printers.get(0);
        } else {
            return null;
        }
    }
    
    public long getPrinterCountAll() throws SQLException {
        return getPrinterCount(true);
    }

    public long getPrinterCountActive() throws SQLException {
        return getPrinterCount(false);
    }

    public long getPrinterCount() throws SQLException {
        return getPrinterCount(true);
    }
    
    public long getPrinterCount(boolean all) throws SQLException {
        if (all)
            return fPrinterDao.countOf();
        else
            return fPrinterDao.queryBuilder().where().gt("IsActive", 0).countOf();
    }
    
    public FPrinter getPrinter(int id) throws SQLException { 
        FPrinter printer = fPrinterDao.queryForId(id);
        return printer;
    }

    
    public void addPrinter(FPrinter printer) throws SQLException {
        fPrinterDao.create(printer);
    }
    
    public List<FPrinter> getPrinters() throws SQLException{
        List<FPrinter> printers = new ArrayList();

            for(FPrinter printer : fPrinterDao.queryForAll()) {
                printers.add(printer);
            }
       
        return printers;
    }
    
    public void updatePrinter(FPrinter printer) throws SQLException {
        fPrinterDao.update(printer);
    }
    
    public void deletePrinter(int idPrinter) throws SQLException {
        fPrinterDao.deleteById(idPrinter);
    }

    public long getUserCountAll() throws SQLException {
        return getUserCount(true);
    }

    public long getUserCountValid() throws SQLException {
        return getUserCount(false);
    }
    
    public long getUserCount(boolean all) throws SQLException {
        if (all)
            return fUserDao.countOf();
        else
            return fUserDao.queryBuilder().where().gt("ValidUser", 0).countOf();
    }
    
    public List<FUser> getUsers() throws SQLException{
        List<FUser> users = new ArrayList();

            for(FUser user : fUserDao.queryForAll()) {
                users.add(user);
            }
       
        return users;
    }
    
    public FUser getUser(int idUser) throws SQLException { 
        FUser user = fUserDao.queryForId(idUser);
        return user;
    }
    
    public void deleteUser(int idUser) throws SQLException {
        fUserDao.deleteById(idUser);
    }

    public void updateUser(FUser user) throws SQLException {
        // update all except password
        FUser user_ = fUserDao.queryForId(user.getIdUser());
        if (user_ != null) {
            user_.setUserName(user.getUserName());
            if (user.getUserPass() != null)
                user_.setUserPass(user.getUserPass());
            user_.setFullName(user.getFullName());
            user_.setValidUser(user.getValidUser());
            user_.setRole(user.getRole());
        } else {
            user_ = user;
        }
        fUserDao.update(user_);
    }
    

    public void addUser(FUser user) throws SQLException {
        fUserDao.create(user);
    }
    
    public FUser getUserByName(String userName) throws SQLException {
        List<FUser> users = fUserDao.queryForEq("UserName", userName);
        
        if(users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
    
    
}

