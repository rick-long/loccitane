package org.spa.service.book;

import org.spa.dao.base.BaseDao;
import org.spa.model.book.Book;
import org.spa.model.book.BookItem;
import org.spa.model.user.User;
import org.spa.vo.app.book.BookRequestVO;
import org.spa.vo.book.BookVO;
import org.spa.utils.Results;

import java.util.Date;

public interface BookService extends BaseDao<Book>{

    public Book add(BookVO bookVO);

    Book edit(BookVO bookVO);

    public void updateStatus(Long bookId);

    public void cancel(Long bookId);

    public void updateMobilePrepaid(Long bookId);
    
    public Results saveAppsBook(BookRequestVO bookRequestVO3);
    public void saveBookingsByBatch(BookRequestVO bookRequestVO);

    void sendBookingNotification(Book book, String bookingTemplate);

    void sendBookingReminderNotification(User user, Date startDate, Date endDate, String bookingTemplate);
}
