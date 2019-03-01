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
public class FPPropertyGroup {

    public FPPropertyGroup(String ID, String name) {
        this.name = name;
        this.ID = ID;
        this.properties = new FPPropertyList();
    }

    public FPPropertyGroup addProperty(FPProperty property) {
       property.setGroup(this);
       properties.put(property.getID(), property);
       return this;
    }
 
    private final FPPropertyList properties;

    /**
     * Get the value of properties
     *
     * @return the value of properties
     */
    public FPPropertyList getProperties() {
        return properties;
    }

    private final String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }
   
    private final String ID;

    /**
     * Get the value of ID
     *
     * @return the value of ID
     */
    public String getID() {
        return ID;
    }
}
