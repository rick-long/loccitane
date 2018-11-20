package org.spa.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.service.company.CompanyPropertyService;
import org.spa.serviceImpl.company.CompanyPropertyImpl;

/**
 * 获取company property 工具类
 * <p>
 * Created by Ivy on 2016-8-5
 */
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    public static final CompanyPropertyService companyPropertyService = SpringUtil.getBean(CompanyPropertyImpl.class);

    // 緩存已經獲取的屬性
    //public static final ConcurrentMap<String, Object> CONCURRENT_MAP = new  ConcurrentHashMap<>();
    
    public static String getValueByName(String name) {
        return companyPropertyService.getValueByName(name);
    }

    public static boolean getBooleanValueByName(String name) {
        return companyPropertyService.getBooleanValueByName(name);
    }

    public static Date getDateValueByName(String name, String format) {
        return companyPropertyService.getDateValueByName(name, format);
    }

    public static long getLongValueByName(String name) {
        return companyPropertyService.getLongValueByName(name);
    }

    public static int getIntegerValueByName(String name) {
        return companyPropertyService.getIntegerValueByName(name);
    }
    public static Double getDoubleValueByName(String name) {
        return companyPropertyService.getDoubleValueByName(name);
    }
}
