package org.spa.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Ivy on 2016/01/16.
 */
public class DateUtil {
	/**
	 * 日期格式: yyyy-MM-dd
	 */
	public static final String DATE = "yyyy-MM-dd";

	public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";


	// public final static SimpleDateFormat dateMillTimeFormat = new
	// SimpleDateFormat("yyyyMMddHHmmssSSS");
	public final static Pattern pattern = Pattern.compile("^[0-9]{4}[/-][0-9]{1,2}[/-][0-9]{1,2}( +[0-9]{2}(:[0-9]{2}(:[0-9]{2}\\.?[0-9]{0,3})?)?)?$");

	//create by rick 2018-9-14
	public static Date stringToDateThrowException(String date,String format) throws ParseException {
		Date d = null;
		if (StringUtils.isNotBlank(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			d= sdf.parse(date);
		}
		return d;
	}

	public static Date stringToDate(String date,String format){
		Date d = null;
		if (StringUtils.isNotBlank(date)) {
			 SimpleDateFormat sdf = new SimpleDateFormat(format);                
			 try {
				d= sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}   
		}
		return d;
	}
	public static String dateToString(Date d,String format) throws ParseException{
		String date = null;
		if (d !=null) {
			 SimpleDateFormat sdf = new SimpleDateFormat(format);                
			 date=sdf.format(d);   
		}
		return date;
	}

	public static  Date getAfterNumOfMinutes(Date date,int minutes){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		date= calendar.getTime();
		return date;
	}
	public static Date getLastDayAfterNumOfMonths(Date date,int months) throws ParseException{
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.MONTH, months);
	    date = calendar.getTime();
	    
	    return getLastDay(date);
	}
	
	public static Date getLastMinutsOfLastDayAfterNumOfMonths(Date date,int months) throws ParseException{
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.add(Calendar.MONTH, months);
	    date = calendar.getTime();
	    
	    date=getLastDay(date);
	    
		return getLastMinuts(date);
	}
	
	public static Date getFirstDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));  
		date = calendar.getTime();
		return date;
	}
	public static Date getLastDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		date = calendar.getTime();
		return date;
	}

	public static Date getFirstDayOfYear(){
		Calendar currCal = Calendar.getInstance();
		int year = currCal.get(Calendar.YEAR);
		currCal.clear();
		currCal.set(Calendar.YEAR, year);
		Date currYearFirst = currCal.getTime();
		return currYearFirst;
	}

	public static Date getLastDayOfYear(){
		Calendar currCal = Calendar.getInstance();
		int year = currCal.get(Calendar.YEAR);
		currCal.clear();
		currCal.set(Calendar.YEAR, year);
		currCal.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = currCal.getTime();
		return currYearLast;
	}

	public static final String convertDateToString(String aMask, Date aDate) {
        return getDateTime(aMask, aDate);
    }
	public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
        	 return (returnValue);
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }
	public static Date getLastMinuts(Date date){
		try {
			String dateStr=dateToString(date, DATE);
			return stringToDate(dateStr+" 23:59:59",DATE_TIME);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getFirstMinuts(Date date){
		try {
			String dateStr=dateToString(date, DATE);
			return stringToDate(dateStr+" 00:00:00",DATE_TIME);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

    /**
     * 判断是否同年同月同日
     *
     * @param dateTime1
     * @param dateTime2
     * @return
     */
    public static boolean sameDay(DateTime dateTime1, DateTime dateTime2) {
        return dateTime1.withTimeAtStartOfDay().getMillis() == dateTime2.withTimeAtStartOfDay().getMillis();
    }

    public static int betweenDays(DateTime dateTime1, DateTime dateTime2) {
        return Math.abs(Days.daysBetween(dateTime1.withTimeAtStartOfDay(), dateTime2.withTimeAtStartOfDay()).getDays());
    }
	
	public static String toString(Date value, String pattern) {
		return new SimpleDateFormat(pattern).format(value);
	}

    /**
     * 检查两组时间是否存在交叉重叠，如果存在返回true
     *
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    public static boolean overlaps(Date start1, Date end1, Date start2, Date end2) {
        return overlaps(start1.getTime(), end1.getTime(), start2.getTime(), end2.getTime());
    }

    public static boolean overlaps(DateTime firstStart, DateTime firstEnd, DateTime secondStart, DateTime secondEnd) {
        return new Interval(firstStart, firstEnd).overlaps(new Interval(secondStart, secondEnd));
    }

    public static boolean overlaps(long firstStart, long firstEnd, long secondStart, long secondEnd) {
        return new Interval(firstStart, firstEnd).overlaps(new Interval(secondStart, secondEnd));
    }



    private static boolean checkBlock(Date currentDate, Date start, Date end) {
        return currentDate.after(start) && currentDate.before(end);
    }

    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
//            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
	public static void main(String[] args) {
		/*
		 * System.out.println("1:" + DateUtil.toDate("2011"));
		 * System.out.println("2:" + DateUtil.toDate("2011/12/20"));
		 * System.out.println("3:" + DateUtil.toDate("2011/12/20 17:20:30"));
		 * System.out.println("4:" + DateUtil.toDate("2011/12/20 17:20"));
		 * System.out.println("5:" + DateUtil.toDate(new
		 * Timestamp(Calendar.getInstance().getTime().getTime())));
		 * System.out.println("6:" + DateUtil.format(new
		 * Timestamp(Calendar.getInstance().getTime().getTime())));
		 * 
		 * System.out.println("10:" + DateUtil.toDate("2010/12/01"));
		 * System.out.println("11:" + DateUtil.toDate("2011-01-01"));
		 * System.out.println("12:" + DateUtil.toDate("2010-10-11 15:20:15"));
		 * System.out.println("13:" + DateUtil.toDate("2010-10-11 15:20"));
		 * System.out.println("14:" + DateUtil.toDate("2010-10-11 15"));
		 * System.out.println("15:" +
		 * DateUtil.toDate("2010-10-11 15:20:15.235")); System.out.println("16:"
		 * + DateUtil.toDate("2010-10-11 15:20:15")); System.out.println("17:" +
		 * DateUtil.toDate("2010-10-11 15:20:15")); System.out.println("18:" +
		 * DateUtil.toDate("15:12:30")); System.out.println("19:" +
		 * DateUtil.toDate("15:12:30.563")); System.out.println("20:" +
		 * DateUtil.toDate("2011/05/1"));
		 */
		
		System.out.println("相差的天数:" + DateUtil.differentDays(
				DateUtil.stringToDate("2017-8-23","yyyy-MM-dd"),DateUtil.stringToDate("2017-12-20","yyyy-MM-dd")));
//		List<Integer> dayLists = new ArrayList<Integer>();
//		for(Integer i=1 ;i<32;i++) {        1号到31
//		dayLists.add(i);
//	}
//		dayLists.add(1);
//		dayLists.add(2);
//		List<Integer> monthList = new ArrayList<>();
//		for (Integer i = 1; i < 13; i++) {  1月到12月
//			monthList.add(i);
//		}
//		monthList.add(1);
//		monthList.add(3);
//		Date now = null;
//        SimpleDateFormat s = new SimpleDateFormat(DATE);
//        Date after = null;
//		try {
//			now = s.parse("2018-10-1"); 
//			after = s.parse("2019-12-2");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		List<Integer> weekList = new ArrayList<>();
//		weekList.add(1);
//		weekList.add(3);
//		//   加入星期天
//		List<Date> ll = getDaysByDateType("month", monthList, weekList,dayLists , now, after);
//	    System.out.println(ll.size());
//	    for (Date date : ll) {
//			System.out.println(date.toString());
//		}
	}
	
	
	public static Date getDateAfter(Date d,int day){
		Calendar now =Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
		return now.getTime();
	}
	
	/**
	 * 
	 * @param dateType  日期类型 (month,week,day)
	 * @param monthList 月份 [1,2,3...10,11,12]
	 * @param weeksList 星期号 [1,2,3,4,5,6,7] 7 号代表星期天
	 * @param days      月份的天数 [1,2,3...29,30,31]
	 * @param startDate 开始日期
	 * @param endDate   截止日期
	 * @return List<Date>
	 */
	public static List<Date> getDaysByDateType(String dateType,List<Integer> monthList,List<Integer> weeksList,List<Integer> days,Date startDate,Date endDate){
		
		List<Date> result = new ArrayList<Date>();
		// 日期类型判空,数据返空
		if(StringUtils.isBlank(dateType)) {
			return result;
		}
		if(null!=startDate && null!=endDate) {
			DateTime startTime = new DateTime(startDate.getTime());
			DateTime endTime = new DateTime(endDate.getTime());
			// 如果起止日期均为同一天,则有效时间范围仍然存在,但范围只有当天
			if(sameDay(startTime, endTime)||startDate.before(endDate)) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				boolean bool = true;
				switch (dateType) {
				
					case "2":// monthly
						//不指明具体月份或不指明具体某天,命中条件不完整,默认不存在具体的日期
					    if((null != monthList && 1 > monthList.size()) ||
					    	null != days && 1 > days.size()) {
					    	return result;
					    }
					    while (bool) {
						   DateTime dt = new DateTime(startDate.getTime());
						   if(monthList.contains(dt.getMonthOfYear()) && days.contains(dt.getDayOfMonth())) {
							   result.add(startDate);
						   }
	                       	cal.add(Calendar.DAY_OF_YEAR, 1);
	                       	startDate = cal.getTime();
	                       	if(startDate.after(endDate)) {
	                       		break;
	                       	}
					    }
						break;
						
					case "1":// weekly
						// 不指明具体星期,命中条件不完整,默认不存在具体的日期
						if(null!=weeksList && 1 > weeksList.size()) {
							return result;
						}
						while (bool) {
							DateTime dt = new DateTime(startDate.getTime());
                        	if(weeksList.contains(dt.getDayOfWeek())) {
                        		result.add(startDate);
                        	}
                        	cal.add(Calendar.DAY_OF_YEAR, 1);
                        	startDate = cal.getTime();
                        	if(startDate.after(endDate)) {
                        		break;
                        	}
						}
						break;
						
					case "0":// daily
						// 不指明具体某天,命中条件不完整,默认不存在具体的日期
						if(null!=days && 1 > days.size()) {
							return result;
						}
                        while (bool) {
                        	DateTime dt = new DateTime(startDate.getTime());
                        	if(days.contains(dt.getDayOfMonth())) {
                        		result.add(startDate);
                        	}
                        	cal.add(Calendar.DAY_OF_YEAR, 1);
                        	startDate = cal.getTime();
                        	if(startDate.after(endDate)) {
                        		break;
                        	}
                        }
						break;
					default:
						break;
				}
			}
		}
		return result;
	}

public static Date getEndTime(Date date) {
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.setTime(date);
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime();
	}
}
