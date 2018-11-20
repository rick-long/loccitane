package org.spa.service.book;

import java.util.Date;

import org.spa.dao.base.BaseDao;
import org.spa.model.book.BookBatch;
import org.spa.utils.Results;
import org.spa.vo.book.BookBatchVO;

public interface BookBatchService extends BaseDao<BookBatch>{

	Results saveOrUpdateBookBatch(BookBatchVO bookBatchVO);
	
	void removeBookBatch(BookBatchVO bookBatchVO,String loginer,Date now);
}
