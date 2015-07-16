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
package org.eda.fpsrv;

import java.util.ArrayList;

/**
 *
 * @author Dimitar Angelov
 */
public class PrintResponse {

    private long ErrorCode;
    private ArrayList<String> Errors;
    private ArrayList<String> Messages;
    private ArrayList<String> Log;

    private StrTable ResultTable;

    public PrintResponse() {
        this.ErrorCode = 0;
        this.ResultTable = new StrTable();
        this.Errors = new ArrayList<>();
        this.Messages = new ArrayList<>();
        this.Log = new ArrayList<>();
    }

    /**
     * Get the value of ErrorCode
     *
     * @return the value of ErrorCode
     */
    public long getErrorCode() {
        return ErrorCode;
    }

    /**
     * Set the value of ErrorCode
     *
     * @param ErrorCode new value of ErrorCode
     */
    public void setErrorCode(long ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    /**
     * Get the value of Errors
     *
     * @return the value of Errors
     */
    public ArrayList<String> getErrors() {
        return Errors;
    }

    /**
     * Set the value of Errors
     *
     * @param Errors new value of Errors
     */
    public void setErrors(ArrayList<String> Errors) {
        this.Errors = Errors;
    }
    
    /**
     * Get the value of Messages
     *
     * @return the value of Messages
     */
    public ArrayList<String> getMessages() {
        return Messages;
    }

    /**
     * Set the value of Messages
     *
     * @param Messages new value of Messages
     */
    public void setMessages(ArrayList<String> Messages) {
        this.Messages = Messages;
    }
    
    /**
     * Get the value of ResultTable
     *
     * @return the value of ResultTable
     */
    public StrTable getResultTable() {
        return ResultTable;
    }

    /**
     * Get the value of Log
     *
     * @return the value of Log
     */
    public ArrayList<String> getLog() {
        return Log;
    }

    /**
     * Set the value of Log
     *
     * @param Log new value of Log
     */
    public void setLog(ArrayList<String> Log) {
        this.Log = Log;
    }
}
