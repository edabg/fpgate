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

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dimitar Angelov
 */
@DatabaseTable(tableName = "fprinter")
public class FPrinter {
    @DatabaseField(columnName = "idPrinter", generatedId = true, canBeNull = false)
    protected int idPrinter;
    @DatabaseField(columnName = "RefID", unique = true, canBeNull = false)
    protected String refID;
    @DatabaseField(columnName = "Name", canBeNull = false)
    protected String name;
    @DatabaseField(columnName = "ModelID", canBeNull = false)
    protected String modelID;
    @DatabaseField(columnName = "Description")
    protected String description;
    @DatabaseField(columnName = "Location", canBeNull = false)
    protected String location;
    @DatabaseField(columnName = "IsActive", canBeNull = false)
    protected int isActive;
    @DatabaseField(columnName = "Properties", dataType = DataType.LONG_STRING)
    private String properties;

    
    public FPrinter() {
        idPrinter = 0;
        refID = "";
        name = "";
        modelID = "";
        description = "";
        location = "";
        isActive = 0;
        properties = "";
    }

    /**
     * Get the value of idPrinter
     *
     * @return the value of idPrinter
     */
    public int getIdPrinter() {
        return idPrinter;
    }
    
    public String getRefId() {
        return refID;
    }
    
    public void setRefId(String value) {
        refID = value;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }
    
    public String getModelID() {
        return modelID;
    }

    public void setModelID(String value) {
        modelID = value;
    }

    public String getModelDesctiption() {
        String Description = "";
        if (modelID.length() > 0) {
            try {
                Description = FPCBase.getClassDecription(modelID);
            } catch (Exception ex) {
                Description = "Can't get model decription!";
            }
        }    
        return Description;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        description = value;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String value) {
        location = value;
    }
    
    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int value) {
        isActive = value;
    }
    
    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
    
    public StrTable getPropertyList() {
        StrTable list = null;
        try {
            FPParams params = FPCBase.getDefinedParams(modelID);
            list = params.getList();
            list.setFromJSONString(properties);
        } catch (Exception E) {
            list = new StrTable();
        }   
        return list;
    }

    public FPParams getParams() {
        FPParams params = null;
        try {
            params = FPCBase.getDefinedParams(modelID);
            params.setFromJSONString(properties);
        } catch (Exception E) {
            
        }   
        return params;
    }
    
    public void setPropertyList(StrTable list) {
        try {
            this.properties = list.toJSONString();
        } catch (Exception E) {
            
        }   
    }
}
