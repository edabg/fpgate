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

/**
 *
 * @author Dimitar Angelov
 */
public class FPException extends Exception {

    private Long ErrorCode;

    public FPException(Long ErrorCode, String message) {
        super(message);
        this.ErrorCode = ErrorCode;
    }

    public FPException(String message) {
        this(-1L, message);
    }
    

    /**
     * Get the value of ErrorCode
     *
     * @return the value of ErrorCode
     */
    public Long getErrorCode() {
        return ErrorCode;
    }

    /**
     * Set the value of ErrorCode
     *
     * @param ErrorCode new value of ErrorCode
     */
    public void setErrorCode(Long ErrorCode) {
        this.ErrorCode = ErrorCode;
    }
    
    
}
