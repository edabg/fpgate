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

import java.util.HashMap;

/**
 *
 * @author Dimitar Angelov
 */
public class FPProperty {

    private Class clazz;
    
    private FPPropertyRule rule;
    
    public <T> FPProperty(FPPropertyGroup group, Class<T> clazz, String ID, String name, String description, T defaultValue, FPPropertyRule<T> pRule) throws Exception {
        this(clazz, ID, name, description, defaultValue, pRule);
        this.group = group;
    }

    public <T> FPProperty(Class<T> clazz, String ID, String name, String description, T defaultValue, FPPropertyRule<T> pRule) throws Exception {
        if (!classSupported(clazz))
           throw new Exception("Invalid property class!");
        this.ID = ID;
        this.name = name;
        this.clazz = clazz;
        this.description = description;
        this.defaultValue = defaultValue;
        this.value = this.defaultValue;
        this.rule = pRule;
    }

    public <T> FPProperty(Class<T> clazz, String ID, String name, String description, T defaultValue) throws Exception {
        this(clazz, ID, name, description, defaultValue, null);
    }
    
    public Class getPropertyClass() {
        return clazz;
    }
    
    public FPPropertyRule getRule() {
        return rule;
    }

    public void setRule(FPPropertyRule rule) {
        this.rule = rule;
    }
    
    public static boolean classSupported(Class clazz) {
        return (clazz == Integer.class || clazz == Double.class || clazz == String.class);
    }
    
    private FPPropertyGroup group;

    /**
     * Set the value of group
     *
     * @param group new value of group
     */
    public void setGroup(FPPropertyGroup group) {
        this.group = group;
    }

    /**
     * Get the value of group
     *
     * @return the value of group
     */
    public FPPropertyGroup getGroup() {
        return group;
    }

    private String ID;

    /**
     * Get the value of ID
     *
     * @return the value of ID
     */
    public String getID() {
        return ID;
    }
    
    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    private String description;

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private Object defaultValue;

    /**
     * Get the value of defaultValue
     *
     * @param <T>
     * @return the value of defaultValue
     */
    public <T> T getDefaultValue() {
        return (T)defaultValue;
    }

    /**
     * Set the value of defaultValue
     *
     * @param <T>
     * @param defaultValue new value of defaultValue
     */
    public <T> void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    private Object value;


    /**
     * Get the value of value
     *
     * @param <T>
     * @return the value of V
     */
    public <T> T getValue() {
        return (T)value;
    }
    
    /**
     * Value as integer
     * @return
     */
    public Integer intValue() {
        if (value.getClass() == Integer.class)
            return ((Integer)value);
        else if (value.getClass() == Double.class) {
            return ((Double)value).intValue();
        } else if (value.getClass() == String.class) {
            return parseInt((String)value);
        } else
            return 0;
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

    /**
     * Value as Double
     * @return
     */
    public Double doubleValue() {
        if (value.getClass() == Integer.class)
            return ((Integer)value).doubleValue();
        else if (value.getClass() == Double.class) {
            return ((Double)value);
        } else if (value.getClass() == String.class) {
            return parseDouble((String)value);
        } else
            return 0.0;
    }
    
    public boolean isNumberClass() {
        return (clazz == Integer.class) || (clazz == Double.class);
    }

    public String stringValue() {
        return value.toString();
    }
    
    /**
     * Set the value of value
     *
     * @param <T>
     * @param val new value of value
     */
    public <T> void setValue(T val) {
        switch(value.getClass().toString()) {
            case "Integer" : // toInt
                switch (val.getClass().toString()) {
                    case "Integer" :
                        value = val;
                        break;
                    case "Double" :
                        value = ((Double)val).intValue();
                        break;
                    case "String" :
                    default :  
                        value = parseInt((String)val);
                }
                break;
            case "Double" : // toDouble
                switch (val.getClass().toString()) {
                    case "Integer" :
                        value = ((Integer)val).doubleValue();
                        break;
                    case "Double" :
                        value = (Double)val;
                        break;
                    case "String" :
                    default :  
                        value = parseDouble((String)val);
                }
                break;
            case "String" : // toString
            default:    
                value = val.toString();
        }
    }
    
}
