package org.spa.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.FastArrayList;
import org.apache.commons.collections.FastHashMap;
import org.apache.commons.collections.FastTreeMap;
import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.list.SetUniqueList;
import org.apache.commons.collections.list.TreeList;
import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.set.MapBackedSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class CollectionUtils {

	private static final Logger logger = Logger.getLogger(CollectionUtils.class);
	
    public CollectionUtils() {
        super();
    }

    public static Map getLightWeightMap() {
        //return new HashMap();
    	FastHashMap m = new FastHashMap();
        //m.setFast(true);
        return m;
    }

    public static Map getSizeLimitedMap(int sizeLimit) {
    	Map m = new LRUMap(sizeLimit);
    	return Collections.synchronizedMap(m);
    }

    public static Map getLightWeightSortedMap() {
        //return new HashMap();
    	FastTreeMap m = new FastTreeMap();
        //m.setFast(true);
        return m;
    }

    public static Map getOrderedMap() {
        //return new HashMap();
    	OrderedMap map = new LinkedMap();
        //m.setFast(true);
        return map;
    }   

    public static List getLightWeightList() {
        //return new Vector();
    	FastArrayList l = new FastArrayList();
        //l.setFast(true);
        return l;
    }

    public static List getLightWeightSortedList() {
        //return new HashMap();
    	TreeList l = new TreeList();
        //m.setFast(true);
        return l;
    }

    public static List getUniqueList() {
        //return new Vector();
    	List arrayList = new FastArrayList();
        //arrayList.setFast(true);
    	List uniqueList = SetUniqueList.decorate(arrayList);
        return uniqueList;
    }

    public static Set getLightWeightSet() {
        //return new HashSet();
        return MapBackedSet.decorate(getLightWeightMap());
    }   

    public static void sort(List list, String property, boolean asc, boolean asNumber) {
    	if (StringUtils.isEmpty(property)) {
    		sort(list, asc);
    	}
    	else {
        	Comparator comparator = new BeanUtilsComparator(property, asc, asNumber);
        	Collections.sort(list, comparator);
    	}
    }
    public static void sort(Object[] objs, String property, boolean asc, boolean asNumber) {
    	if (StringUtils.isEmpty(property)) {
    		sort(objs, asc);
    	}
    	else {
        	Comparator comparator = new BeanUtilsComparator(property, asc, asNumber);
        	Arrays.sort(objs, comparator);
    	}
    }
    
    public static void sort(List list, String property, boolean asc, boolean asNumber, boolean ignoreCase) {
    	if (StringUtils.isEmpty(property)) {
    		sort(list, asc);
    	}
    	else {
        	Comparator comparator = new BeanUtilsComparator(property, asc, asNumber, ignoreCase);
        	Collections.sort(list, comparator);
    	}
    }
    
    public static void sort(List list, String property, boolean asc) {
    	sort(list, property, asc, false);
    }
    public static void sort(Object[] objs, String property, boolean asc) {
    	sort(objs, property, asc, false);
    }

    public static void sort(List list, boolean asc) {
    	Collections.sort(list);
    	if (!asc) {
    		Collections.reverse(list);
    	}
    }
    public static void sort(Object[] objs, boolean asc) {
    	if (objs == null || objs.length <= 1) {
    		return;
    	}
    	Arrays.sort(objs);
    	if (!asc) {
    		reverse(objs);
    	}
    }

    public static void reverse(Object[] objs) {
    	if (objs == null || objs.length <= 1) {
    		return;
    	}

    	Object[] tmpArray = new Object[objs.length];
    	int i;

    	for (i=0; i<objs.length; i++) {
    		tmpArray[i] = objs[i];
    	}
    	for (i=0; i<objs.length; i++) {
    		 objs[i] = tmpArray[objs.length-1-i];
    	}
    }

    public static void copy(Map from, Map to) {
    	if (from == null || to == null) {
    		return;
    	}

    	Iterator it = from.keySet().iterator();
    	while (it.hasNext()) {
    		String key = (String) it.next();
    		Object value = from.get(key);
    		to.put(key, value);
    	}
    }

    public static void copy(Object[] array1, Object[] array2) {
    	if (array1 == null || array2 == null) {
    		return;
    	}
    	for (int i=0; i<array1.length && i<array2.length; i++) {
    		array2[i] = array1[i];
    	}
    }

    public static void putAll(Map map, Object[] keys) {
    	putAll(map, keys, null);
    }
    public static void putAll(Map map, Object[] keys, Object[] values) {
    	if (map == null || keys == null) {
    		return;
    	}
    	if (values == null) {
        	for (int i=0; i<keys.length; i++) {
        		map.put(keys[i], keys[i]);
        	}
    	}
    	else {
        	for (int i=0; i<keys.length; i++) {
        		map.put(keys[i], values[i]);
        	}
    	}
    }
    public static void putAll(Map map, Collection collection) {
    	Object[] items = collection.toArray();
    	putAll(map, items);
    }
    public static void putAll(Map map, Collection collection, String keyPropertyName, String valuePropertyName) {
    	if (map == null) {
    		return;
    	}
    	if (!collection.isEmpty()) {
    		try {
    			Iterator it = collection.iterator();
    			while (it.hasNext()) {
    	    		Object obj = it.next();
    	    		Object key = (StringUtils.isEmpty(keyPropertyName)) ? 
        	    			obj : PropertyUtils.getNestedProperty(obj, keyPropertyName);
    	    		Object value = (StringUtils.isEmpty(valuePropertyName)) ? 
        	    			obj : PropertyUtils.getNestedProperty(obj, valuePropertyName);
    	    		map.put(key, value);
    			}
    		} 
    		catch (Exception e) {
    			/*String msg = "Exception throw during put property.";
    			logger.error(msg, e);
    			ChainedRuntimeException cre = new ChainedRuntimeException(msg);
    			cre.initCause(e);
    			throw cre;*/
    		}
    	}
    }

    public static Collection extract(Collection collection, String propertyName) {
    	return extract(collection, propertyName, false);
    }
    public static Collection extract(Collection collection, String propertyName, boolean allowNull) {
    	if (collection == null) {
    		return null;
    	}
    	List newList = CollectionUtils.getLightWeightList();
    	if (!collection.isEmpty()) {
    		try {
    			Iterator it = collection.iterator();
    			while (it.hasNext()) {
    	    		Object obj = it.next();
    	    		Object nestedObj = null;
    	    		if (allowNull) {
        	    		try {
            	    		nestedObj = PropertyUtils.getNestedProperty(obj, propertyName);
        	    		}
        	    		catch (NestedNullException e) {
        	    			// ...
        	    		}
    	    		}
    	    		else {
        	    		nestedObj = PropertyUtils.getNestedProperty(obj, propertyName);
    	    		}
    	    		if (nestedObj != null) {
        	    		newList.add(nestedObj);
    	    		}
    			}
    		} 
    		catch (Exception e) {
    			/*String msg = "Exception throw during extract property.";
    			logger.error(msg, e);
    			ChainedRuntimeException cre = new ChainedRuntimeException(msg);
    			cre.initCause(e);
    			throw cre;*/
    		}
    	}
    	return newList;
    }

    public static void addAll(Collection c, Object[] arr) {
    	if (c != null && arr != null) {
    		for (int i=0; i<arr.length; i++) {
    			if (arr[i] != null) {
        			c.add(arr[i]);
    			}
    		}
    	}
    }

    public static void setAll(Collection c, String propertyName, Object value) {
    	if (c == null || c.size() == 0) {
    		setAll(c.iterator(), propertyName, value);
    	}
    }

    public static void setAll(Iterator it, String propertyName, Object value) {
		try {
			while (it.hasNext()) {
				PropertyUtils.setProperty(it.next(), propertyName, value);
			}
		} 
		catch (IllegalAccessException e) {
			/*String msg = "Exception during set collection property";
			logger.error(msg, e);
			ChainedRuntimeException cre = new ChainedRuntimeException(msg);
			cre.initCause(e);
			throw cre;*/
		} 
		catch (InvocationTargetException e) {
			/*String msg = "Exception during set collection property";
			logger.error(msg, e);
			ChainedRuntimeException cre = new ChainedRuntimeException(msg);
			cre.initCause(e);
			throw cre;*/
		} 
		catch (NoSuchMethodException e) {
			/*String msg = "Exception during set collection property";
			logger.error(msg, e);
			ChainedRuntimeException cre = new ChainedRuntimeException(msg);
			cre.initCause(e);
			throw cre;*/
		}
    }

}
