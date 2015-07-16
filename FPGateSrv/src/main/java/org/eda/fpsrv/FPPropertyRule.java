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

import java.util.LinkedHashMap;
import jdk.nashorn.internal.objects.NativeArray;

/**
 *
 * @author Dimitar Angelov
 * @param <T>
 */
public class FPPropertyRule<T> {
    protected T min = null;
    protected T max = null;
    protected LinkedHashMap<T, String> list = null;
    protected boolean strict = false;

    public FPPropertyRule(T pMin, T pMax) {
        this(pMin, pMax, false, null);
    }

    public FPPropertyRule(T pMin, T pMax, boolean pStrict, LinkedHashMap<T, String> pList) {
        min = pMin;
        max = pMax;
        strict = pStrict;
        list = pList;
    }
    
    
    public LinkedHashMap<T, String> getList() {
        return list;
    }

    public LinkedHashMap<String, String> getStringList() {
        LinkedHashMap<String, String> stringList  = new LinkedHashMap<>();
        if (list != null) {
            for (Object key : list.keySet()) 
                stringList.put(key.toString(), list.get(key));
        }    
        return stringList;
    }

    
    public boolean isValid(T value) throws Exception {
        if (!(value instanceof Comparable)) return true;
        if ((min != null) && (((Comparable)min).compareTo(value) > 0)) 
            throw new Exception("The value is less than min value:"+min);
        if ((max != null) && (((Comparable)max).compareTo(value) > 0)) 
            throw new Exception("The value is greater than max value:"+max);
        if (strict && (list != null) && !list.containsKey(value)) 
            throw new Exception("The value is not in list of valid values!");
        return true;
    }
    
}
