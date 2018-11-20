package org.spa.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class MobileNumberUtils {

    static Pattern pattern = Pattern.compile("(-?\\d+\\.?\\d*)[Ee]{1}[\\+-]?[0-9]*");
    static DecimalFormat ds = new DecimalFormat("0");
    static boolean isENum(String input) {//判断输入字符串是否为科学计数法
        return pattern.matcher(input).matches();
    }

    private static String HONG_KONG_KEY = "+852";
    private static String MAIN_LAND_KEY = "+86";

    public static void main(String[] args) {
        String s = verifyAddPrefixMobileNumber("13578111");
        System.out.println(s);
    }

    public static String verifyAddPrefixMobileNumber(String mobileNumber){
        if(StringUtils.isBlank(mobileNumber))
            return null;
        if(isENum(mobileNumber)){
            mobileNumber = ds.format(Double.parseDouble(mobileNumber)).trim();
        }
        try{
            Long.valueOf(mobileNumber);
        }catch (Exception e){
            return null;
        }
       return virifyPhoneNumber(mobileNumber);
    }

    public static String verifyHomePhoneNumber(String homeNumber){
        if(StringUtils.isNotBlank(homeNumber)){
            try{
                Long.valueOf(homeNumber);
            }catch (Exception e){
                return null;
            }

            return virifyPhoneNumber(homeNumber);
        }
        return "";
    }

    private static String virifyPhoneNumber(String phoneNumber){
        if(phoneNumber.length() == 8){
            phoneNumber = HONG_KONG_KEY + phoneNumber;
            return phoneNumber;
        }else if(phoneNumber.length() == 11){
            boolean matches = phoneNumber.matches("1[345678][\\d]{9}");
            if(!matches){
                return null;
            }
            phoneNumber = MAIN_LAND_KEY + phoneNumber;
            return phoneNumber;
        }
        return null;
    }
}
