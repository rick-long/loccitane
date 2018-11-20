package org.spa.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	
	public static boolean isNumeric(final String str){ 
	   Pattern pattern = Pattern.compile("[0-9]*"); 
	   Matcher isNum = pattern.matcher(str);
	   if( !isNum.matches() ){
	       return false; 
	   } 
	   return true; 
	}
	
	/*
	 * 四舍五入
	 * */
	public static double mathRoundUp(int newScale,double val1){
		return mathRoundingMode(newScale, val1,BigDecimal.ROUND_UP);
	}
	/*
	 * 四舍五入
	 * */
	public static double mathRoundHalfUp(int newScale,double val1){
		return mathRoundingMode(newScale, val1,BigDecimal.ROUND_HALF_UP);
	}
	
	public static double mathRoundingMode(int newScale,double val1,int roundingMode){
		BigDecimal bg = new BigDecimal(val1);  
        double val2 = bg.setScale(newScale, roundingMode).doubleValue();  
        return val2;
	}
}
