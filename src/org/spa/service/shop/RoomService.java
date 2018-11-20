package org.spa.service.shop;

import org.hibernate.criterion.DetachedCriteria;
import org.spa.dao.base.BaseDao;
import org.spa.model.book.BookItem;
import org.spa.model.shop.Room;
import org.spa.vo.shop.RoomAddVO;
import org.spa.vo.shop.RoomEditVO;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivy on 2016/3/28.
 */
public interface RoomService extends BaseDao<Room>{

    public void save(RoomAddVO roomAddVO);

    public List<Room> getRoomByShop(Long shopId, DetachedCriteria criteria);

    public void update(RoomEditVO roomEditVO);

    public List<Room> getAvailableRoomList(Long productOptionId, Long shopId);

    List<Room> getNotBlockRoomList(Long productOptionId, Long shopId, Date startTime, Date endTime);
    
    public List<Room> getNotBlockRoomList(Long productOptionId, Long shopId, Date startTime, Date endTime, BookItem excludeBookItem);

    List<Room> getNotBlockRoomList(Long productOptionId, Long shopId, Date startTime, Date endTime, Set<BookItem> excludeBookItems);

    List<Room> getShopAvailableRoomList(Long id);
}
