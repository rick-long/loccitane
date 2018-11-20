package org.spa.serviceImpl.book;

import java.text.ParseException;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.book.Book;
import org.spa.model.book.BookItem;
import org.spa.model.book.BookBatch;
import org.spa.model.company.Company;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.service.book.BookBatchService;
import org.spa.service.book.BookItemService;
import org.spa.service.book.BookService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.Results;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.app.book.BookItemRequestVO;
import org.spa.vo.app.book.BookRequestVO;
import org.spa.vo.book.BookBatchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * @author Ivy 2018-9-14
 */
@Service
public class BookBatchServiceImpl extends BaseDaoHibernate<BookBatch> implements BookBatchService {

	 @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private BookItemService bookItemService;
    
    @Autowired
    private BookService bookService;
    
	@Override
	public Results saveOrUpdateBookBatch(BookBatchVO bookBatchVO) {
		
		Results results = Results.getInstance();
		 
		List<Date> bookingDates = DateUtil.getDaysByDateType(bookBatchVO.getRepeatType(),bookBatchVO.getMonths(), bookBatchVO.getWeeks(), bookBatchVO.getDays(),bookBatchVO.getRepeatStartDate(), bookBatchVO.getRepeatEndDate());
	    if(bookingDates ==null || bookingDates.size()==0){
	    	return results.setCode(Results.CODE_SERVER_ERROR).addMessage("errorMsg", I18nUtil.getMessageKey("label.book.batch.no.dates.tobook"));
	    }
		
    	Date now= new Date();
    	String loginer = WebThreadLocal.getUser().getUsername();
    	Company company = WebThreadLocal.getCompany();
    	
    	Shop shop =shopService.get(bookBatchVO.getShopId());
    	User member= userService.get(bookBatchVO.getMemberId());
    	ProductOption productOption = productOptionService.get(bookBatchVO.getProductOptionId());
        
    	String remarks = bookBatchVO.getRemarks();
        BookBatch bookBatch =null;
        if(bookBatchVO.getId() !=null){
        	// edit 
        	removeBookBatch(bookBatchVO, loginer, now);
        }
        bookBatch =  new BookBatch();
        bookBatch.setBatchNumber(RandomUtil.generateRandomNumberWithTime(""));
        bookBatch.setMember(member);
        if(!CommonConstant.anyTherapist.equals(bookBatchVO.getTherapistId())){
        	User therapist =userService.get(Long.parseLong(bookBatchVO.getTherapistId()));
        	bookBatch.setTherapist(therapist);
        }
        bookBatch.setProductOption(productOption);
        bookBatch.setShop(shop);
        bookBatch.setRemarks(bookBatchVO.getRemarks());
        bookBatch.setRepeatType(bookBatchVO.getRepeatType());
        bookBatch.setStartDate(bookBatchVO.getRepeatStartDate());
        bookBatch.setEndDate(bookBatchVO.getRepeatEndDate());
        bookBatch.setStartTime(bookBatchVO.getRepeatStartTime());
        bookBatch.setEndTime(bookBatchVO.getRepeatEndTime());
        
        String months="";
        if(bookBatchVO.getMonths() !=null && bookBatchVO.getMonths().size()>0){
        	for(int m : bookBatchVO.getMonths()){
        		months +=m+",";
        	}
        }
        String weeks="";
        if(bookBatchVO.getWeeks() !=null && bookBatchVO.getWeeks().size()>0){
        	for(int w : bookBatchVO.getWeeks()){
        		weeks +=w+",";
        	}
        }
        String days="";
        if(bookBatchVO.getDays() !=null && bookBatchVO.getDays().size()>0){
        	for(int d : bookBatchVO.getDays()){
        		days +=d+",";
        	}
        }
        bookBatch.setMonths(months);
        bookBatch.setWeeks(weeks);
        bookBatch.setDays(days);
        bookBatch.setCronExpression(generateCronExpression(bookBatchVO));
        
        bookBatch.setRemarks(remarks);
        bookBatch.setCompany(company);
        bookBatch.setCreated(now);
        bookBatch.setCreatedBy(loginer);
        bookBatch.setLastUpdated(now);
        bookBatch.setLastUpdatedBy(loginer);
        bookBatch.setIsActive(true);
        saveOrUpdate(bookBatch);
        
    	
    	BookRequestVO bookRequestVO;
    	BookItemRequestVO bookItemRequestVO;
    	List<BookItemRequestVO> bookItemRequestVOs;
    	for(Date bookingDate : bookingDates){
    		String bDateSTr ="";
			try {
				bDateSTr = DateUtil.dateToString(bookingDate, "yyyy-MM-dd");
			} catch (ParseException e) {
				e.printStackTrace();
			}
    		DateTime startTime = new DateTime(DateUtil.stringToDate(bDateSTr +" "+bookBatchVO.getRepeatStartTime(), "yyyy-MM-dd HH:mm"));
        	DateTime endTime =new DateTime(DateUtil.stringToDate(bDateSTr +" "+bookBatchVO.getRepeatEndTime(), "yyyy-MM-dd HH:mm"));
        	
    		bookRequestVO = new BookRequestVO();
    		bookRequestVO.setShopId(shop.getId());
    		bookRequestVO.setMemberId(member.getId());
    		bookRequestVO.setGuestNum(1);
    		bookRequestVO.setAppointmentDate(DateUtil.stringToDate(bDateSTr, "yyyy-MM-dd"));
    		bookRequestVO.setBookingChannel(CommonConstant.BOOKING_CHANNEL_STAFF);
    		bookRequestVO.setSendBookingConfirmation(false);
    		bookRequestVO.setBookBatch(bookBatch);
    		bookRequestVO.setCreateBy(loginer);
    		
    		bookItemRequestVO = new BookItemRequestVO();
    		bookItemRequestVO.setAppointmentDate(DateUtil.stringToDate(bDateSTr, "yyyy-MM-dd"));
    		bookItemRequestVO.setAppointmentTime(startTime.toDate());
    		bookItemRequestVO.setEndAppointmentTime(endTime.toDate());
    		bookItemRequestVO.setTimestamp(startTime.getMillis());
    		
    		bookItemRequestVO.setProductOptionId(productOption.getId());
    		bookItemRequestVO.setTherapistIds(bookBatchVO.getTherapistId().toString());
    		
    		bookItemRequestVOs = new ArrayList<BookItemRequestVO>();
    		bookItemRequestVOs.add(bookItemRequestVO);
    		bookRequestVO.setBookItems(bookItemRequestVOs);
    		
      	   	bookService.saveBookingsByBatch(bookRequestVO);
    	}
    	return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", I18nUtil.getMessageKey("label.save.successfully"));
    }
	
	private String generateCronExpression(BookBatchVO bookBatchVO) {
        String repeatType = bookBatchVO.getRepeatType();
        StringBuilder cronBuilder = new StringBuilder("* * * ");
        if ("0".equals(repeatType)) {//daily
            if (bookBatchVO.getDays() != null && bookBatchVO.getDays().size() > 0) {
                cronBuilder.append(StringUtils.join(bookBatchVO.getDays(), ",")).append(" * ?");
                //
            } else {
                logger.error("repeat type:{} generate day cron expression parameter error!", repeatType);
            }
        }
        if ("1".equals(repeatType)) {//weekly
            if (bookBatchVO.getWeeks() != null && bookBatchVO.getWeeks().size() > 0) {
                cronBuilder.append(" ? * ").append(StringUtils.join(bookBatchVO.getWeeks(), ","));
            } else {
                logger.error("repeat type:{} generate week cron expression parameter error!", repeatType);
            }
        }
        if ("2".equals(repeatType)) {//monthly
            if (bookBatchVO.getDays() != null && bookBatchVO.getDays().size() > 0) {
                cronBuilder.append(StringUtils.join(bookBatchVO.getDays(), ",")).append(" ");
                if (bookBatchVO.getMonths() != null && bookBatchVO.getMonths().size() > 0) {
                    cronBuilder.append(StringUtils.join(bookBatchVO.getMonths(), ",")).append(" ? ");
                } else {
                    logger.error("repeat type:{} generate month cron expression parameter error!", repeatType);
                }
            } else {
                logger.error("repeat type:{} generate day cron expression parameter error!", repeatType);
            }
        }
        
        return cronBuilder.toString();
    }
	
	@Override
	public void removeBookBatch(BookBatchVO bookBatchVO,String loginer,Date now){
		
		List<String> bookBtachStatus = new ArrayList<String>();
		bookBtachStatus.add(CommonConstant.BOOK_STATUS_CHECKIN_SERVICE);
		bookBtachStatus.add(CommonConstant.BOOK_STATUS_COMPLETE);
		
		BookBatch bookBatch = get(bookBatchVO.getId());
    	if(bookBatch !=null){
    		// find all available bookings by batch Id and remove all related bookings.
       	 	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Book.class);
       	 	detachedCriteria.createAlias("bookItems", "bi");
            detachedCriteria.add(Restrictions.eq("bookBatch.id", bookBatch.getId()));
            detachedCriteria.add(Restrictions.not(Restrictions.in("bi.status", bookBtachStatus)));
            detachedCriteria.add(Restrictions.eq("isActive", true));
            detachedCriteria.addOrder(Order.desc("id"));
            List<Book> bookings = bookService.list(detachedCriteria);
            if(bookings !=null && bookings.size()>0){
            	BookItem bookItem;
            	for(Book book : bookings){
            		book.setIsActive(false);
            		book.setRemarks("Removed by editing a Booking Batch!");
            		bookService.save(book);
            		
            		bookItem = book.getBookItems().iterator().next();
            		bookItem.setIsActive(false);
            		bookItemService.save(bookItem);
            	}
            }
            bookBatch.setIsActive(false);
            bookBatch.setLastUpdated(now);
            bookBatch.setLastUpdatedBy(loginer);
            bookBatch.setRemarks("Removed by editing a Booking Batch!");
            saveOrUpdate(bookBatch);
    	}
	}
}
