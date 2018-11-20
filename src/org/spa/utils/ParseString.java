package org.spa.utils;

public class ParseString {

    public static Boolean parse(String s){
        if(s.equals("false") || s.equals("true")){
            Boolean aBoolean = Boolean.valueOf(s);
            return aBoolean;
        }else {
            throw new IllegalArgumentException("s should be true or false");
        }
    }
}
