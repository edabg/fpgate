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
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Dimitar Angelov
 */
@JsonAutoDetect
public class PrintRequest {
    
    @JsonAutoDetect
    public class PrinterDescription {

        @JsonProperty
        private String ID;
        @JsonProperty
        private String Model;
        @JsonProperty
        private HashMap<String, String> Params;

        /**
         * Get the value of ID
         *
         * @return the value of ID
         */
        public String getID() {
            return ID;
        }

        /**
         * Set the value of ID
         *
         * @param ID new value of ID
         */
        public void setID(String ID) {
            this.ID = ID;
        }

        /**
         * Get the value of Model
         *
         * @return the value of Model
         */
        public String getModel() {
            return Model;
        }

        /**
         * Set the value of Model
         *
         * @param Model new value of Model
         */
        public void setModel(String Model) {
            this.Model = Model;
        }
        /**
         * Get the value of Params
         *
         * @return the value of Params
         */
        public HashMap<String, String> getParams() {
            return Params;
        }

        /**
         * Set the value of Params
         *
         * @param Params new value of Params
         */
        public void setParams(HashMap<String, String> Params) {
            this.Params = Params;
        }
    }

    @JsonProperty
    private PrinterDescription Printer;

    @JsonProperty
    private String Command;

    @JsonProperty
    private List<String> Arguments;
    
    /**
     * Get the value of Printer
     *
     * @return the value of Printer
     */
    public PrinterDescription getPrinter() {
        return Printer;
    }

    /**
     * Set the value of Printer
     *
     * @param Printer new value of Printer
     */
    public void setPrinter(PrinterDescription Printer) {
        this.Printer = Printer;
    }
    
    /**
     * Get the value of Command
     *
     * @return the value of Command
     */
    public String getCommand() {
        return Command;
    }

    /**
     * Set the value of Command
     *
     * @param Command new value of Command
     */
    public void setCommand(String Command) {
        this.Command = Command;
    }


    /**
     * Get the value of Arguments
     *
     * @return the value of Arguments
     */
    public List<String> getArguments() {
        return Arguments;
    }
    
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

    /**
     * Set the value of Arguments
     *
     * @param Arguments new value of Arguments
     */
    public void setArguments(List<String> Arguments) {
        this.Arguments = Arguments;
    }

}
