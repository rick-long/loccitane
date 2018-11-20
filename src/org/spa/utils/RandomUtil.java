package org.spa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
/**
 * Created by Ivy on 2016/03/13.
 */
public class RandomUtil {

	/**
     * generate number: prefix + 20160313-xxxx
     *
     * @return
     */
    public static String generateRandomNumberWithDate(String prefix) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if(prefix !=null){
        	 sb.append(prefix);
        }
        sb.append(sdf.format(new Date())).append("-").append(RandomStringUtils.randomNumeric(4));
        return sb.toString();
    }

    /**
     * generate number: prefix + 20160313000000-xxxx
     *
     * @return
     */
    public static String generateRandomNumberWithTime(String prefix) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sb.append(prefix).append(sdf.format(new Date())).append("-").append(RandomStringUtils.randomNumeric(4));
        return sb.toString();
    }

    /**
     * generate number: prefix +xxxx
     *
     * @return
     */
    public static String generateRandomNumber(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(RandomStringUtils.randomNumeric(4));
        return sb.toString();
    }

    public static String generateRandomNumber2(String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append(prefix).append(RandomStringUtils.randomNumeric(6));
        return sb.toString();
    }
}
