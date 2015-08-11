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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;

/**
 * JSON-RPC compatible request object
 * @author Dimitar Angelov
 */
//@JsonAutoDetect
public class PrintRequest {
    
//    @JsonAutoDetect
    public class PrinterDescription {

        @JsonProperty("ID")
        private String ID;
        
        @JsonProperty("Model")
        private String Model;
        
        @JsonProperty("Params")
        private HashMap<String, String> Params;

        /**
         * Get the value of ID
         *
         * @return the value of ID
         */
        @JsonProperty("ID")
        public String getID() {
            return ID;
        }

        /**
         * Set the value of ID
         *
         * @param ID new value of ID
         */
        @JsonProperty("ID")
        public void setID(String ID) {
            this.ID = ID;
        }

        /**
         * Get the value of Model
         *
         * @return the value of Model
         */
        @JsonProperty("Model")
        public String getModel() {
            return Model;
        }

        /**
         * Set the value of Model
         *
         * @param Model new value of Model
         */
        @JsonProperty("Model")
        public void setModel(String Model) {
            this.Model = Model;
        }
        /**
         * Get the value of Params
         *
         * @return the value of Params
         */
        @JsonProperty("Params")
        public HashMap<String, String> getParams() {
            return Params;
        }

        /**
         * Set the value of Params
         *
         * @param Params new value of Params
         */
        @JsonProperty("Params")
        public void setParams(HashMap<String, String> Params) {
            this.Params = Params;
        }
    }

//    @JsonAutoDetect
    public class MethodParams {
        public class PrinterDescription  extends PrintRequest.PrinterDescription {      
        }
        
        @JsonProperty("arguments")
        private List<String> Arguments;

        @JsonProperty("printer")
        private PrinterDescription Printer;


        /**
         * Get the value of Printer
         *
         * @return the value of Printer
         */
        @JsonProperty("printer")
        public PrinterDescription getPrinter() {
            return Printer;
        }

        /**
         * Set the value of Printer
         *
         * @param printer new value of Printer
         */
        @JsonProperty("printer")
        public void setPrinter(PrinterDescription printer) {
            this.Printer = printer;
        }

        /**
         * Get the value of Arguments
         *
         * @return the value of Arguments
         */
        @JsonProperty("arguments")
        public List<String> getArguments() {
            return Arguments;
        }

        /**
         * Set the value of Arguments
         *
         * @param arguments new value of Arguments
         */
        @JsonProperty("arguments")
        public void setArguments(List<String> arguments) {
            this.Arguments = arguments;
        }

        @JsonIgnore
        public StrTable getNamedArguments() {
            StrTable res = new StrTable();
            String[] argParams;
            for(String arg : Arguments) {
                argParams = arg.split("=");
                if (argParams.length > 1) 
                    res.put(argParams[0], argParams[1]);
            }
            return res;
        }

    }

    @JsonProperty("jsonrpc")
    private String jsonrpc;

    @JsonProperty("method")
    private String Method;
    
    @JsonProperty("params")
    private MethodParams Params;

    @JsonProperty("id")
    private String Id;

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
     * Get the value of Printer
     *
     * @return the value of Printer
     */
    @JsonIgnore
    public PrinterDescription getPrinter() {
        return this.Params.getPrinter();
    }
    
    /**
     * Get the value of Command
     *
     * @return the value of Command
     */
    @JsonIgnore
    public String getCommand() {
        return Method;
    }

    /**
     * Get the value of Params
     *
     * @return the value of Params
     */
    @JsonProperty("params")
    public MethodParams getParams() {
        return Params;
    }

    /**
     * Set the value of Params
     *
     * @param params new value of Params
     */
    @JsonProperty("params")
    public void setParams(MethodParams params) {
        this.Params = params;
    }

    /**
     * Get the value of Method
     *
     * @return the value of Method
     */
    @JsonProperty("method")
    public String getMethod() {
        return Method;
    }

    /**
     * Set the value of Method
     *
     * @param method new value of Method
     */
    @JsonProperty("method")
    public void setMethod(String method) {
        this.Method = method;
    }

    /**
     * Get the value of Id
     *
     * @return the value of Id
     */
    @JsonProperty("id")
    public String getId() {
        return Id;
    }

    /**
     * Set the value of Id
     *
     * @param id new value of Id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.Id = id;
    }

    /**
     * Get the value of Arguments
     *
     * @return the value of Arguments
     */
    @JsonIgnore
    public List<String> getArguments() {
        return Params.getArguments();
    }

    @JsonIgnore
    public StrTable getNamedArguments() {
        return Params.getNamedArguments();
    }

}
