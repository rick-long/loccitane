package org.spa.utils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.spa.vo.app.callback.OpeningHoursCallBackVO;

public class OpeningTimeUtil {

	public static OpeningHoursCallBackVO getValidBookingTime(OpeningHoursCallBackVO openingHoursCallBackVO,
			DateTime openTime, DateTime closeTime, DateTime current, Integer advanceHour) {

		if (null == openTime || null == closeTime || openTime.isAfter(closeTime)) {
			return new OpeningHoursCallBackVO();
		}
		Integer closeTimeHour = closeTime.getHourOfDay();
		Integer closeTimeMin = closeTime.getMinuteOfHour();
		Map<String, String> morningMap = new LinkedHashMap<>();
		Map<String, String> afternoonMap = new LinkedHashMap<>();
		Map<String, String> eveningMap = new LinkedHashMap<>();
		int timeUnit = 15;
		// 当前时间
		if (null == current) {
			current = new DateTime();
		}
		// 有效booking时间要提前 advanceHour 小时
		current = current.plusHours(advanceHour);
		if (current.isBefore(openTime)) {
			current = openTime;
		}
		Integer minOfHour = current.getMinuteOfHour();
		// 取余数
		Integer remainder = minOfHour % 15;
		Integer remainderSecond = (int) (current.getMillis() % 60000);
		if (0 != remainder) {
			current = current.plusMinutes(15 - remainder);
			// 秒和毫秒清零,方便统计
			current = current.plusMillis(-remainderSecond);
		}
		// 上下午临界点
		DateTime morningEnd = openTime.withTimeAtStartOfDay().plusHours(13);
		// 下午与傍晚临界点
		DateTime afternoonEnd = morningEnd.plusHours(5);

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
        // 
		while (current.isBefore(closeTime)
				|| (closeTimeMin == current.getMinuteOfHour() && closeTimeHour == current.getHourOfDay())) {
			if (current.isBefore(closeTime)
					|| (closeTimeMin == current.getMinuteOfHour() && closeTimeHour == current.getHourOfDay())) {
				eveningMap.put(String.valueOf(current.getMillis()), current.toString("HH:mm"));
			}
			current = current.plusMinutes(timeUnit);
		}

		System.out.println(morningMap);
		System.out.println(afternoonMap);
		System.out.println(eveningMap);
		if (null == openingHoursCallBackVO) {
			openingHoursCallBackVO = new OpeningHoursCallBackVO();
		}
		openingHoursCallBackVO.setMorningMap(morningMap);
		openingHoursCallBackVO.setAfternoonMap(afternoonMap);
		openingHoursCallBackVO.setEveningMap(eveningMap);
		return openingHoursCallBackVO;
	}

	public static void main(String[] args) {
		getValidBookingTime(null, new DateTime(new Date()), new DateTime(1538056800000l), null, 2);
	}
}
