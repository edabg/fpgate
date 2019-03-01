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

import org.eda.fdevice.StrTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;

/**
 * JSON-RPC 2.0 compliant response object
 * @author Dimitar Angelov
 */
@JsonInclude(Include.NON_NULL)
public class PrintResponse {

    public class ResponseError { 
        
        private long code;
        
        private String message;
        
        private ResponseData data;

        /**
         * Get the value of data
         *
         * @return the value of data
         */
        public ResponseData getData() {
            return data;
        }

        /**
         * Set the value of data
         *
         * @param data new value of data
         */
        public void setData(ResponseData data) {
            this.data = data;
        }


        /**
         * Get the value of code
         *
         * @return the value of code
         */
        public long getCode() {
            return code;
        }

        /**
         * Set the value of code
         *
         * @param code new value of code
         */
        public void setCode(long code) {
            this.code = code;
        }

        /**
         * Get the value of message
         *
         * @return the value of message
         */
        public String getMessage() {
            return message;
        }

        /**
         * Set the value of message
         *
         * @param message new value of message
         */
        public void setMessage(String message) {
            this.message = message;
        }

    }
    
    public class ResponseData {
        private long ErrorCode;
        private ArrayList<String> Errors;
        private ArrayList<String> Messages;
        private ArrayList<String> Log;

        private StrTable ResultTable;

        public ResponseData() {
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

    @JsonPropertyOrder({ "jsonrpc", "result", "error", "id" })    
    private String jsonrpc = "2.0";
    @JsonIgnore
    private ResponseData ResponseResult;
    private String id;

    public PrintResponse() {
        this.ResponseResult = new ResponseData();
    }

    /**
     * Get the value of jsonrpc
     *
     * @return the value of jsonrpc
     */
    public String getJsonrpc() {
        return jsonrpc;
    }

    /**
     * Set the value of jsonrpc
     *
     * @param jsonrpc new value of jsonrpc
     */
    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    /**
     * Get the value of ResultTable
     *
     * @return the value of ResultTable
     */
    @JsonProperty("result")
    public ResponseData getResult() {
        return  (ResponseResult.getErrorCode() == 0)?ResponseResult:null;
    }

    
    /**
     * Get the value of ErrorCode
     *
     * @return the value of ErrorCode
     */
    @JsonIgnore
    public long getErrorCode() {
        return ResponseResult.getErrorCode();
    }

    /**
     * Set the value of ErrorCode
     *
     * @param ErrorCode new value of ErrorCode
     */
    @JsonIgnore
    public void setErrorCode(long ErrorCode) {
        ResponseResult.setErrorCode(ErrorCode);
    }

    /**
     * Get the value of Errors
     *
     * @return the value of Errors
     */
    @JsonIgnore
    public ArrayList<String> getErrors() {
        return ResponseResult.getErrors();
    }

    /**
     * Get the value of Messages
     *
     * @return the value of Messages
     */
    @JsonIgnore
    public ArrayList<String> getMessages() {
        return ResponseResult.getMessages();
    }
    
    /**
     * Get the value of ResultTable
     *
     * @return the value of ResultTable
     */
    @JsonIgnore
    public StrTable getResultTable() {
        return ResponseResult.getResultTable();
    }

    /**
     * Get the value of Log
     *
     * @return the value of Log
     */
    @JsonIgnore
    public ArrayList<String> getLog() {
        return ResponseResult.getLog();
    }

    /**
     * Get the value of error
     *
     * @return the value of error
     */
    @JsonProperty("error")
    public ResponseError getError() {
        if (ResponseResult.getErrorCode() == 0)
            return null;
        else {
            ResponseError error = new ResponseError();
            error.setCode(ResponseResult.getErrorCode());
            error.setData(ResponseResult);
            if (ResponseResult.getErrors().size() > 0)
                error.setMessage(ResponseResult.getErrors().get(0));
            return error;
        }
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(String id) {
        this.id = id;
    }
}
