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

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author Dimitar Angelov
 */
@DatabaseTable(tableName = "fuser")
public class FUser {
    
    @DatabaseField(columnName = "idUser", generatedId = true, canBeNull = false)
    private int idUser;
    
    @DatabaseField(columnName = "UserName", unique = true, canBeNull = false)
    private String userName = "";

    @DatabaseField(columnName = "UserPass", canBeNull = false, defaultValue = "")
    private String userPass = "";
    
    @DatabaseField(columnName = "FullName", canBeNull = false)
    private String fullName = "";

    @DatabaseField(columnName = "ValidUser", canBeNull = false)
    private int validUser;
    
    @DatabaseField(columnName = "Role", canBeNull = false)
    private int role;

    
    /**
     * Get the value of idUser
     *
     * @return the value of idUser
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Set the value of idUser
     *
     * @param idUser new value of idUser
     */
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    /**
     * Get the value of userName
     *
     * @return the value of userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the value of userName
     *
     * @param userName new value of userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get the value of userPass
     *
     * @return the value of userPass
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * Set the value of userPass
     *
     * @param UserPass new value of userPass
     */
    public void setUserPass(String UserPass) {
        this.userPass = UserPass;
    }
    
    /**
     * Get the value of fullName
     *
     * @return the value of fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Set the value of fullName
     *
     * @param fullName new value of fullName
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Get the value of validUser
     *
     * @return the value of validUser
     */
    public int getValidUser() {
        return validUser;
    }

    /**
     * Set the value of validUser
     *
     * @param validUser new value of validUser
     */
    public void setValidUser(int validUser) {
        this.validUser = validUser;
    }

    /**
     * Get the value of role
     *
     * @return the value of role
     */
    public int getRole() {
        return role;
    }

    /**
     * Set the value of role
     *
     * @param role new value of role
     */
    public void setRole(int role) {
        this.role = role;
    }
}
