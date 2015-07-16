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
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dimitar Angelov
 */
public class StrTable extends LinkedHashMap<String,String> {
    
    public Integer getInt(String key) {
        return getInt(key, 0);
    }
    public Integer getInt(String key, Integer defValue) {
        Integer val = defValue;
        try {
            val = parseInt(this.get(key), defValue);
        } catch (Exception e) {
        }
        return val;
    }

    public Double getDouble(String key, Double defValue) {
        Double val = defValue;
        try {
            val = parseDouble(this.get(key), defValue);
        } catch (Exception e) {
        }
        return val;
    }
    
    protected Double parseDouble(String sval) {
        try {
            return Double.parseDouble(sval);
        } catch (Exception e) {
            return 0.0;
        }
    }
    protected Double parseDouble(String sval, Double defVal) {
        try {
            return Double.parseDouble(sval);
        } catch (Exception e) {
            return defVal;
        }
    }

    /**
     *
     * @param sval
     * @return Integer value of sval
     */
    protected Integer parseInt(String sval) {
        try {
            return Integer.parseInt(sval);
        } catch (Exception e) {
            return parseDouble(sval).intValue();
        }
    }
    protected Integer parseInt(String sval, Integer defVal) {
        try {
            return Integer.parseInt(sval);
        } catch (Exception e) {
            return parseDouble(sval, defVal.doubleValue()).intValue();
        }
    }
    
    public void setFromJSONString(String jsonString) {
        LinkedHashMap<String,String> h_map;
        try {
            
            h_map = new ObjectMapper().readValue(jsonString, new TypeReference<LinkedHashMap<String,Object>>(){});
            FPProperty property;
            for(String key : keySet()) {
                if (h_map.containsKey(key)) {
                    put(key, h_map.get(key));
                }
            }
        } catch (IOException ex) {    
            Logger.getLogger(StrTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String toJSONString() throws JsonProcessingException {
        LinkedHashMap<String,String> h_map = new LinkedHashMap<>();
        for(String key : keySet()) 
            h_map.put(key, get(key));
//        return new Gson().toJson(h_map, h_map.getClass());
        return new ObjectMapper().writeValueAsString(h_map);
    }
    
}
