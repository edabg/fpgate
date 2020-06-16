/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eda.protocol;

import java.io.IOException;

/**
 *
 * @author Dimitar Angelov
 */
public class FDException extends IOException {
    protected long ErrorCode = -1L;

    public FDException(String message) {
        this(-1L, message);
    }
    public FDException(long ErrorCode, String message) {
        super(message);
        this.ErrorCode = ErrorCode;
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
    
}
