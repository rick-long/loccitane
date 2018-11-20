package org.spa.test;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.DateTime;

public class OpeningTimeTest {

	public static void main(String[] args) throws IOException {

        
		// 开门时间     2018-09-27 10:00:00
		DateTime openTime = new DateTime(new Date());
		openTime = openTime.plusHours(2);
		// 打烊时间     2018-09-27 22:00:00
		DateTime closeTime =new DateTime(1538056800000l);

		Map<String, String> morningMap = new LinkedHashMap<>();
		Map<String, String> afternoonMap = new LinkedHashMap<>();
		Map<String, String> eveningMap = new LinkedHashMap<>();

		int timeUnit = 15;
		
		// 当前时间,这里默认是当前时间的前8个小时 
		DateTime current = new DateTime().plusMinutes(-480);
		
		Integer minOfHour = current.getMinuteOfHour();
		// 取余数
		Integer remainder = minOfHour % 15;
		if(0 != remainder) {
			current = current.plusMinutes(15 - remainder);
		}
		// 有效booking时间要提前两个小时
		current = current.plusHours(2);
		
	    // 上下午临界点
		DateTime morningEnd = openTime.withTimeAtStartOfDay().plusHours(13);
		// 下午与傍晚临界点
		DateTime afternoonEnd = morningEnd.plusHours(5);
		// 当前时间没到开始营业时间
		if(current.isBefore(openTime)) {
            current = openTime;			
		}
		while (current.isBefore(morningEnd)) {
			if (current.isBefore(closeTime)) {
				morningMap.put(String.valueOf(current.getMillis()), current.toString("HH:mm"));
			}
			current = current.plusMinutes(timeUnit);
		}
		
		while (current.isBefore(afternoonEnd)) {
			if (current.isBefore(closeTime)) {
				afternoonMap.put(String.valueOf(current.getMillis()), current.toString("HH:mm"));
			}
			current = current.plusMinutes(timeUnit);
		}
		
		while (current.isBefore(closeTime)) {
			if (current.isBefore(closeTime)) {
				eveningMap.put(String.valueOf(current.getMillis()), current.toString("HH:mm"));
			}
			current = current.plusMinutes(timeUnit);
		}
        
		System.out.println(morningMap);
		System.out.println(afternoonMap);
		System.out.println(eveningMap);
		
	}

}
