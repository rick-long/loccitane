package com.spa.tools.land;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MathUtils {

	public static final String CLASSNAME = StringUtils.substringAfterLast(MathUtils.class.getName(),".");

	public static double round(double value)  {
		return round(value, 1);
	}
	/**
	 * i.e. 
	 * round(10.12345678, 4) == 10.1235
	 * round(10.12345678, 3) == 10.123
	 */
	public static double round(double value, int decimalPoint)  {
		if (decimalPoint <= 0) {
			value =  (double) Math.round(value);
		}
		else {
			double x = Math.pow(10, decimalPoint);
			value =  value * x;
			value =  (double) Math.round(value);
			value =  value / x;
		}
		return value;
	}

	/**
	 * i.e. 
	 * ceil(6.0, 4.0) == 8.0
	 * ceil(7.0, 2.0) == 8.0
	 */
	public static double ceil (double a, double floorScale) {
		if (floorScale >= 1) {
			a = Math.ceil(a);
		}
		else {
			a = a / floorScale;
			a = Math.ceil(a);
			a = a * floorScale;
		}
		return  a;
	}
	public static double ceil (double a) {
		return ceil(a, 1);
	}

    /**
     * Covert <code>num</code> to <code>digit</code> digits in string format.
     * E.g. zeroPaddingNumber(3, 5) returns "00003".
     *
     * @param num The integer for conversion
     * @param digit The number of digit in the output string
     */
    public static String zeroPaddingNumber(int num, int digit) {
    	String returnStr = String.valueOf(num);
    	if ( returnStr.length() < digit )
    		return "0" + zeroPaddingNumber(num, digit-1);
    	else
    		return returnStr;
    }

    private static void _testRound() {
    	NumberFormat numberFormat = new DecimalFormat("###########.00000000000000");
    	double value;
    	int decimalPoint;
    	double result;

    	value = 12345678.8812345678;
    	for (int i=9; i>=0; i--) {
        	decimalPoint = i;
        	result = round(value, decimalPoint);
        	System.out.println(CLASSNAME+" : test round() : "+numberFormat.format(value)+" , "+decimalPoint+" : "+numberFormat.format(result));
    	}
    }

    private static void _testSmallNumber() {
    	NumberFormat numberFormat1 = new DecimalFormat("00");
    	NumberFormat numberFormat2 = new DecimalFormat("#########00.00000000000000000000");
    	double multFactor = 1.1234567891234;
    	double n1;
    	double n2;

    	n1 = 1; n2 = n1;
    	for (int i=0; i<30; i++) {
        	n2 *= multFactor;
        	System.out.print(CLASSNAME+" : test smallnumber : #"+numberFormat1.format((i+1))+" : value : "+numberFormat2.format(n2)+" : "+numberFormat2.format(n1));
        	if ((n2 / multFactor) == n1) {
            	System.out.print(" : equal test : SUCCESS");
        	}
        	else {
            	System.out.print(" : equal test : FAILED");
            	if (round((n2 / multFactor),10) == round(n1,10)) {
                	System.out.print(" : equal test 2 : SUCCESS");
            	}
            	else {
                	System.out.print(" : equal test 2 : FAILED");
            	}
        	}
        	System.out.println();
        	n1 = n2;
    	}
    }

    public static void main (String [] args) {
    	_testRound();
    	_testSmallNumber();
    }

}
