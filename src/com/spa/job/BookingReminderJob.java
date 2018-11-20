package com.spa.job;

import com.spa.constant.CommonConstant;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.spa.model.book.BookItem;
import org.spa.model.user.User;
import org.spa.service.book.BookItemService;
import org.spa.service.book.BookService;
import org.spa.utils.DateUtil;
import org.spa.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class BookingReminderJob extends OpenSessionQuartzJobBean{

	public static final String BOOKING_REMINDER_HOUR = "BOOKING_REMINDER";
	
	@Autowired
	private BookItemService bookItemService;

	@Autowired
	private BookService bookService;
	
	@Override
	protected void executeJob(JobExecutionContext ctx) throws JobExecutionException {
		System.out.println(" run ----BookingReminderJob---");

		Date date = new Date();
		String hour = PropertiesUtil.getValueByName(BOOKING_REMINDER_HOUR);
		Date after1DayTime = DateUtil.getDateAfter(date, Integer.valueOf(hour));

		Date startDate = DateUtil.getFirstMinuts(after1DayTime);
		Date endDate = DateUtil.getLastMinuts(after1DayTime);
		// the first find book items
		List<BookItem> bookItems = bookItemService.getBookItemsByDateAndMember(startDate, endDate, null);
		if(bookItems ==null || bookItems.size() == 0){
			
			return;
		}
		Set<User> userSet = new HashSet<>();
		System.out.println("run ----BookingReminderJob---send email--");
		for (BookItem bi : bookItems) {
			userSet.add(bi.getBook().getUser());
		}
		
		for (User u : userSet) {
			bookService.sendBookingReminderNotification(u, startDate, endDate, CommonConstant.SEND_BOOKING_REMINDER_NOTIFICATION_EMAIL);
		}

	}

}