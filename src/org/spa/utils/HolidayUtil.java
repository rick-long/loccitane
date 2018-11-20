package org.spa.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.Holiday;
import org.spa.service.HolidayService;
import org.spa.serviceImpl.HolidayServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 获取公众假期工具类
 * <p>
 * Created by Ivy on 2016-8-4
 */
public class HolidayUtil {

    private static final Logger logger = LoggerFactory.getLogger(HolidayUtil.class);

    public static final String HOLIDAY_URL = "http://www.1823.gov.hk/common/ical/en.ics";

    public static String getLNewestHoliday() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpGet = new HttpGet(HOLIDAY_URL);
        logger.debug(httpGet.getRequestLine().toString());
        try {
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (200 == statusCode && entity != null) {
                return EntityUtils.toString(entity);
            } else {
                logger.error("call error with status:{}", statusCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close(); // 关闭流并释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void saveHoliday() throws IOException {
        String icsFile = getLNewestHoliday();

        if (StringUtils.isEmpty(icsFile)) {
            logger.error("Can not get newest holiday ics File content!");
            return;
        }

        String beginEvent = "BEGIN:VEVENT";
        String dateStart = "DTSTART;VALUE=DATE:";
        String dateEnd = "DTEND;VALUE=DATE:";
        String summary = "SUMMARY:";
        String endEvent = "END:VEVENT";

        System.out.println("icsFile:" + icsFile);
        StringReader stringReader = new StringReader(icsFile);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        String lineContent;
        Holiday holiday = null;
        List<Holiday> holidayList = new ArrayList<>();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd");
        while ((lineContent = bufferedReader.readLine()) != null) {
            if (lineContent.contains(beginEvent)) {
                holiday = new Holiday(); // 新建holiday
            } else if (lineContent.contains(endEvent)) {
                holidayList.add(holiday);
                holiday = null;
            } else if (holiday != null) {
                if (lineContent.contains(dateStart)) {
                    holiday.setStartDate(dateTimeFormatter.parseDateTime(lineContent.substring(dateStart.length())).toDate());
                } else if (lineContent.contains(dateEnd)) {
                    holiday.setEndDate(dateTimeFormatter.parseDateTime(lineContent.substring(dateEnd.length())).toDate());
                } else if (lineContent.contains(summary)) {
                    holiday.setName(lineContent.substring(summary.length()));
                }
            }
        }

        Date now = new Date();
        String userName = "HolidyaUtilClass";
        System.out.println("size:" + holidayList.size());
        HolidayService holidayService = SpringUtil.getBean(HolidayServiceImpl.class);
        for (Holiday entity : holidayList) {
            // 查找是否存在该公众日期
            DetachedCriteria criteria = DetachedCriteria.forClass(Holiday.class);
            criteria.add(Restrictions.eq("name", entity.getName()));
            criteria.add(Restrictions.eq("startDate", entity.getStartDate()));
            criteria.add(Restrictions.eq("endDate", entity.getEndDate()));
            criteria.add(Restrictions.eq("isActive", true));
            if (holidayService.get(criteria) != null) {
                // 存在该公众假期, 跳过
                System.out.println("数据库已经存在该公众假期：" + entity);
                continue;
            }
            entity.setIsActive(true);
            entity.setCreated(now);
            entity.setCreatedBy(userName);
            entity.setLastUpdated(now);
            entity.setLastUpdatedBy(userName);
            holidayService.save(entity);
            System.out.println("保存假期：" + entity);
        }
    }
}
