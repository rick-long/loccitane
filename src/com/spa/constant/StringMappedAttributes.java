package com.spa.constant;

import java.util.Map;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: JB
 * Date: Jun 8, 2003
 * Time: 10:17:17 PM
 * This is a generic wrapper for a map that holds string representation of key/value pairs.
 */
public class StringMappedAttributes {
     protected Map sMap;

    public StringMappedAttributes(Map cbMap) {
        this.sMap = cbMap;
    }

    public StringMappedAttributes() {
        this.sMap = new Hashtable();
    }

    public void put (String key, String value) {
        if (value == null)
            value = "";
        sMap.put(key, value);
    }

    public String get (String key) throws KeyNotFoundException {
        String result = (String)sMap.get(key);
        if (result == null)
            throw new KeyNotFoundException("Invalid key (" + key
                    + ") used to retrieve value from " + this.getClass().toString());
        else
            return result;
    }
}
