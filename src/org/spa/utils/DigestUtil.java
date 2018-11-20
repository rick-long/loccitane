package org.spa.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

// import sun.misc.BASE64Encoder;
import org.apache.commons.codec.binary.Base64;

/**
 * SSL 系统的加密工具类
 * 
 * @author Ivy 2016-7-14
 */
public class DigestUtil {

    private static final Logger logger = Logger.getLogger(DigestUtil.class);

    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String DIGEST_ALGORITHM = "SHA";

    /**
     * 原来的 rapporto 系统密码加密方法
     * 
     * @param text
     * @return
     */
    public static String digest(String text) {
        return digest(text, DIGEST_ALGORITHM);
    }

    public static String digest(String text, String algorithm) {
        return digestWithBase64(text, algorithm);
    }

    public static String digestWithBase64(String text, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(text.getBytes(ENCODING_UTF8));
            byte raw[] = digest.digest();
            // return (new BASE64Encoder()).encode(raw);
            return new Base64().encodeToString(raw);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Exception digesting message " + text + ", with algoritm " + algorithm, e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error("Exception digesting message " + text + ", with algoritm " + algorithm, e);
            e.printStackTrace();
        }
        return null;
    }
/*
    public static String digestWithHexString(String text, String algorithm) {
        return digestWithHexString(text, algorithm, false, false);
    }

    public static String digestWithHexString(String text, String algorithm, boolean toUpperCase, boolean toLowerCase) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(text.getBytes(ENCODING_UTF8));

            byte raw[] = digest.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < raw.length; i++) {

                String tmpStr = "0" + Integer.toHexString((0xff & raw[i]));
                sb.append(tmpStr.substring(tmpStr.length() - 2));
            }

            String digestStr = sb.toString();
            if (toUpperCase) {
                digestStr = digestStr.toUpperCase();
            } else if (toLowerCase) {
                digestStr = digestStr.toLowerCase();
            }

            return digestStr;

        } catch (NoSuchAlgorithmException e) {
            logger.error("Exception digesting message " + text + ", with algoritm " + algorithm, e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            logger.error("Exception digesting message " + text + ", with algoritm " + algorithm, e);
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String algorithm = null;
        String input = null;
        String output = null;

        try {
            input = "admin";
            output = digest(input);
            System.out.println("" + input + "\t---->\t" + output);
            input = "test";
            output = digest(input);
            System.out.println("" + input + "\t---->\t" + output);
            input = "password";
            output = digest(input);
            System.out.println("" + input + "\t---->\t" + output);
            input = "testing";
            output = digest(input);
            System.out.println("" + input + "\t---->\t" + output);
            input = "ibs4web";
            output = digest(input);
            System.out.println("" + input + "\t---->\t" + output);
            input = "all4one";
            output = digest(input);
            System.out.println("" + input + "\t---->\t" + output);
            //
            input = "Test10.00password";
            input = "1234567890100.00development";
            algorithm = "MD5";
            output = digestWithHexString(input, algorithm);
            System.out.println("" + input + "\t---->\t" + output);
            System.out.println(digest("password").equals("W6ph5Mm5Pz8GgiULbPgzG37mj9g="));
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
*/
}
