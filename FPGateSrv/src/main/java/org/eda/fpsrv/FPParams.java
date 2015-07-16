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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 *
 * @author Dimitar Angelov
 */
public class FPParams {

    private final LinkedHashMap<String, FPPropertyGroup> groups = new LinkedHashMap<>();
    private final FPPropertyList properties = new FPPropertyList();


    public FPParams() {
    }

    public void setFromJSONString(String jsonString) throws IOException {
        LinkedHashMap<String,String> h_map;
        h_map = new ObjectMapper().readValue(jsonString, new TypeReference<LinkedHashMap<String,Object>>(){});
        FPProperty property;
        for(String key : properties.keySet()) {
            if (h_map.containsKey(key)) {
                property = properties.get(key);
                property.setValue(h_map.get(key));
            }
        }    
    }

    public String toJSONString() throws JsonProcessingException {
        LinkedHashMap<String,String> h_map = new LinkedHashMap<>();
        for(String key : properties.keySet()) 
            h_map.put(key, properties.get(key).stringValue());
//        return new Gson().toJson(h_map, h_map.getClass());
        return new ObjectMapper().writeValueAsString(h_map);
    }
    
    public void setFromList(HashMap<String,String> params_) {
        FPProperty property;
        for(String key : properties.keySet()) {
            if (params_.containsKey(key)) {
                property = properties.get(key);
                property.setValue(params_.get(key));
            }
        }    
    }
    
    public StrTable getList() {
        StrTable list = new StrTable();
        for(String key : properties.keySet()) 
            list.put(key, properties.get(key).stringValue());
        return list;
    }

    public void addGroups(FPPropertyGroup... groupList) {
        for(FPPropertyGroup group: groupList) {
            groups.put(group.getID(), group);
            properties.keySet().removeAll(group.getProperties().keySet());
            properties.putAll(group.getProperties());
        }    
    }
    
    
    /**
     * Get the value of param
     *
     * @return the value of param
     */
    public String get(String key, String defValue) {
        String val = defValue;
        try {
            val = this.properties.get(key).stringValue();
        } catch (Exception e) {
        }
        return val;
    }

    public String get(String key) {
        return this.get(key, "");
    }
    
    /**
     * 
     * @param key
     * @param defValue
     * @return 
     */
    public Integer getInt(String key, Integer defValue) {
        int val = defValue;
        try {
            val = this.properties.get(key).intValue();
        } catch (Exception e) {
        }
        return val;
    }

    /**
     * 
     * @param key
     * @return 
     */
    public Integer getInt(String key) {
        return this.getInt(key, 0);
    }
    
    /**
     *
     * @param key
     * @param defValue
     * @return
     */
    public Double  getDouble(String key, Double defValue) {
        double val = defValue;
        try {
            val = this.properties.get(key).doubleValue();
        } catch (Exception e) {
        }
        return val;
    }

    /**
     *
     * @param key
     * @return
     */
    public Double getDouble(String key) {
        return this.getDouble(key, (double)0);
    }
    
    public <T> void setValue(String key, T value) {
        if (properties.containsKey(key))
            properties.get(key).setValue(value);
    }

    public FPPropertyList getProperties() {
        return properties;
    }
    
    /**
     * Get the value of groups
     *
     * @return the value of groups
     */
    public LinkedHashMap<String, FPPropertyGroup> getGroups() {
        return groups;
    }
    
}
