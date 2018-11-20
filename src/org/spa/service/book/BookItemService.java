package org.spa.service.book;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.spa.dao.base.BaseDao;
import org.spa.model.book.BookItem;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.vo.book.CellVO;
import org.spa.vo.book.TherapistKeyVO;
import org.spa.vo.book.ViewVO;

/**
 * Created by Ivy on 2017/3/28.
 */
public interface BookItemService extends BaseDao<BookItem>{

    public void updateStatus(Long bookItemId, String status);

    public List<BookItem> getAllWaitingBookItem(Shop shop, Date startTime);

    public void updateAllStatus(Long bookId, String status);

    List<CellVO> getCellList(ViewVO viewVO);
    
    public List<CellVO> transferBookItemsToCell(ViewVO viewVO);

    List<CellVO> getAllBlockCellList(ViewVO viewVO);

    Map<TherapistKeyVO, CellVO> getTherapistViewData(ViewVO viewVO);

    boolean checkBlock(BookItem bookItem);

    boolean checkBlock(User therapist, Date startTime, Date endTime, Set<BookItem> excludeBookItems);

    boolean checkBlock(Room room, Date startTime, Date endTime, Set<BookItem> excludeBookItems);

    List<BookItem> getBookItemList(Long shopId, Date startTime, Date endTime);

    List<BookItem> getBookItemList(Long shopId, Date startTime, Date endTime, List<String> statusList);

    void updateBookItem(ViewVO viewVO);

    List<BookItem> getStaffBookItemList(Long shopId, Long staffId, Date startTime,Long companyId);
    List<BookItem> getStaffBookItemList(Long shopId, Long staffId, Date startTime);

    long countBookItems(Long shopId, Date startTime, Date endTime, List<String> statusList,Long companyId);

    long countWalkInBookItems(Long shopId, Date startTime, Date endTime, List<String> statusList, boolean walkIn,Long companyId);
    
    public List<BookItem> getChildrenOfDoubleBooking(Long parentBookItemId);

    public void updateCancelStatus(Long bookItemId, String status);

    List<BookItem> getBookItemsByDateAndMember(Date date, Date time, Long memberId);

    List<BookItem> getBookItemsByBookId(Long bookId);
    
    List<BookItem> getBookItemsByRoomId(Long shopId,Long roomId, Date startTime, Date endTime);
    
}
