package org.spa.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码加密工具类
 *
 *  Created by Ivy on 2016/01/16.
 */
public class EncryptUtil {

    private static final byte[] SALT = new byte[]{73, 66, 83, 95, 50, 48, 49, 53};

    public static String SHA1(String passwordToHash) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        md.update(SALT);
        byte[] bytes = md.digest(passwordToHash.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(SHA1("DION12345"));
        System.out.println(SHA1("JESSICA12345"));
        System.out.println(SHA1("JIMMY12345"));
        System.out.println(SHA1("1qazxsw2"));
        System.out.println(SHA1("12qwaszx"));
    }
}
