package org.spa.utils;

import java.util.Map;
import java.util.StringTokenizer;

public final class HtmlUtil {

    public HtmlUtil() {
        super();
    }

   
    public static final Map getParameterMap(String queryString) {
    	Map m = CollectionUtils.getLightWeightMap();
    	if (queryString != null && queryString.length() > 0) {
    		StringTokenizer tokenizer = new StringTokenizer(queryString, "&");
    		while (tokenizer.hasMoreTokens()) {
        		String keyValueStr = tokenizer.nextToken();
            	if (keyValueStr != null && keyValueStr.length() > 0) {
            		String[] keyValuePair = keyValueStr.split("=");
            		String key = keyValuePair[0];
            		String value = (keyValuePair.length > 1) ? keyValuePair[1] : null;
            		if (m.containsKey(key)) {
            			if (value != null) {
                			Object oldEntry = m.get(key);
                			if (oldEntry != null) {
                    			if (oldEntry instanceof String[]) {
                    				String[] newEntry = new String[((String[]) oldEntry).length+1];
                    				CollectionUtils.copy((Object[]) oldEntry, newEntry);
                    				newEntry[newEntry.length-1] = value;
                    				m.put(key, newEntry);
                    			}
                    			else {
                    				String[] newEntry = new String[2];
                    				newEntry[0] = (String) oldEntry;
                    				newEntry[1] = value;
                    				m.put(key, newEntry);
                    			}
                			}
                			else {
                				m.put(key, value);
                			}
            			}
            		}
            		else {
                		m.put(key, value);
            		}
            	}
    		}
    	}
    	return m;
    }
    
}
