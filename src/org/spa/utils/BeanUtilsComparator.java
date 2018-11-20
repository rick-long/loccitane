package org.spa.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.lang3.StringUtils;

public class BeanUtilsComparator implements Comparator {

	private String propertyName = null;
	private boolean asc = true;
	private boolean asNumber = false;
	private Comparator comparableComparator = null;

	public BeanUtilsComparator(String property) {
		this(property, true);
	}

	public BeanUtilsComparator(String property, boolean asc) {
		this(property, asc, false);
	}

	public BeanUtilsComparator(String property, boolean asc, boolean asNumber) {
		this.propertyName = property;
		this.asc = asc;
		this.asNumber = asNumber;
		this.comparableComparator = new ComparableComparator();
	}

	public BeanUtilsComparator(String property, boolean asc, boolean asNumber, boolean caseIgnore) {
		this.propertyName = property;
		this.asc = asc;
		this.asNumber = asNumber;
		if(caseIgnore){
			this.comparableComparator = String.CASE_INSENSITIVE_ORDER;
		} else {
			this.comparableComparator = new ComparableComparator();
		}	
	}
	
	public int compare(Object o1, Object o2) {
		try {
			Object lhs = null;
			Object rhs = null;
			if (StringUtils.isEmpty(propertyName)) {
				lhs = o1;
				rhs = o2;
			} else {
				lhs = PropertyUtils.getNestedProperty(o1, propertyName);
				rhs = PropertyUtils.getNestedProperty(o2, propertyName);
			}

			if (asNumber) {
				lhs = (lhs==null) ? new Double(0.0) : new Double(lhs.toString()); 
				rhs = (rhs==null) ? new Double(0.0) : new Double(rhs.toString()); 
			}

			// special handling for Timestamp 
			if (lhs instanceof java.sql.Timestamp) {
				java.util.Date newlhs = new java.util.Date();
				newlhs.setTime(((java.sql.Timestamp)lhs).getTime());
				lhs = newlhs;
			}
			if (rhs instanceof java.sql.Timestamp) {
				java.util.Date newrhs = new java.util.Date();
				newrhs.setTime(((java.sql.Timestamp)rhs).getTime());
				rhs = newrhs;
			}

			int result;
			if (lhs == null && rhs == null) {
				result = 0;
			}
			else if (lhs == null) {
				result = -1;
			}
			else if (rhs == null) {
				result = 1;
			}
			else {
				result = comparableComparator.compare(lhs, rhs);
			}
			return (asc) ? result : (result * -1);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

}
