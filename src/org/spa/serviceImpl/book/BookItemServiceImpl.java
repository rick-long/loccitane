package org.spa.serviceImpl.book;

import com.google.common.collect.Sets;
import com.spa.constant.CommonConstant;
import com.spa.helper.RoomHelper;
import com.spa.runtime.exception.ResourceException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.book.*;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.service.book.*;
import org.spa.service.product.ProductOptionService;
import org.spa.service.shop.RoomService;
import org.spa.service.user.UserService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.book.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ivy 2017-3-31
 */
@Service
public class BookItemServiceImpl extends BaseDaoHibernate<BookItem> implements BookItemService {

    @Autowired
    private BookService bookService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private ProductOptionService productOptionService;

    public void updateStatus(Long bookItemId, String status) {
        BookItem bookItem = get(bookItemId);
        if (bookItem == null) {
            return;
        }
        Date now = new Date();
        String currentUserName = WebThreadLocal.getUser().getUsername();
        bookItem.setStatus(status);
        bookItem.setLastUpdated(now);
        bookItem.setLastUpdatedBy(currentUserName);
        saveOrUpdate(bookItem);
        //update by rick 2018-9-18
        Book book = bookService.get(bookItem.getBook().getId());
        Set<BookItem> bookItems = book.getBookItems();
        List<BookItem> itemList = new ArrayList<>();
        bookItems.forEach(e->{
            itemList.add(e);
        });
        //分组
        List<List<BookItem>> lists = comibeBookItem(itemList);
        //进行时间处理
        timeDealWith(lists, bookItemId);
        bookService.updateStatus(bookItem.getBook().getId()); // 更新book的状态
    }

    public List<BookItem> getAllWaitingBookItem(Shop shop, Date startTime) {
        DetachedCriteria criteria = DetachedCriteria.forClass(BookItem.class);
        criteria.add(Restrictions.ge("appointmentTime", new DateTime(startTime).withTimeAtStartOfDay().toDate()));
        criteria.createAlias("book", "b");
        criteria.add(Restrictions.eq("b.shop.id", shop.getId()));
        criteria.add(Restrictions.eq("status", CommonConstant.BOOK_STATUS_WAITING));
        criteria.add(Restrictions.eq("isActive", true));
        criteria.addOrder(Order.asc("appointmentTime"));
        return list(criteria);
    }


    public void updateBookItem(ResourceVO resourceVO) {
        BookItem bookItem = get(resourceVO.getBlockId());
        if (bookItem == null) {
            return;
        }

    }

    @Override
    public void updateAllStatus(Long bookId, String status) {
        Book book = bookService.get(bookId);
        if (book == null) {
            return;
        }

        for (BookItem bookItem : book.getBookItems()) {
            updateStatus(bookItem.getId(), status);
        }
    }

    /*private List<CellVO> transferToCell(BookItem bookItem) {
        List<CellVO> cellVOList = new ArrayList<>();
        for (BookItemTherapist itemTherapist : bookItem.getBookItemTherapists()) {
            DateTime startTime = new DateTime(bookItem.getAppointmentTime());
            DateTime endTime = startTime.plusMinutes(bookItem.getDuration() + bookItem.getProductOption().getProcessTime());
            CellVO parent = null;
            List<CellVO> children = new ArrayList<>();
            while (startTime.isBefore(endTime)) {
                CellVO cellVO = new CellVO();
                cellVO.setBookItem(bookItem);
                cellVO.setTime(startTime);
                cellVO.setOnRequest(itemTherapist.getOnRequest());
                cellVO.setTherapist(itemTherapist.getUser());
                cellVO.setRoom(bookItem.getRoom());
                if (parent == null) {
                    cellVO.setParent(null);
                    cellVO.setChildren(children);
                    parent = cellVO; // 这个是parent
                } else {
                    cellVO.setParent(parent); // set parent
                    children.add(cellVO);
                }
                cellVOList.add(cellVO);
                System.out.println("Cell:" + cellVO.getTime().toString("HH:mm") + "," + cellVO.getTherapist().getDisplayName());
                startTime = startTime.plusMinutes(CommonConstant.TIME_UNIT); // 时间递增
            }

        }
        System.out.println("cellVOList Size:" + cellVOList.size());
        return cellVOList;
    }*/

    @Override
    public List<CellVO> getCellList(ViewVO viewVO) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.createAlias("book", "book");
        detachedCriteria.add(Restrictions.eq("book.shop.id", viewVO.getShopId()));
        DateTime dateTime = new DateTime(viewVO.getAppointmentTimeStamp());
        detachedCriteria.add(Restrictions.ge("appointmentTime", dateTime.withTimeAtStartOfDay().toDate()));
        detachedCriteria.add(Restrictions.le("appointmentTime", dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate()));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.in("status", CommonConstant.bookItemBlockStatusList));
        detachedCriteria.addOrder(Order.desc("appointmentTime"));
        List<BookItem> bookItemList = list(detachedCriteria);
        return bookItemList.stream().map(BookItem::transferToCell).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public List<CellVO> transferBookItemsToCell(ViewVO viewVO) {

		//Ivy hide double booking on 2018-09-11 ==> Boolean handleDoubleBooking =false
		Boolean handleDoubleBooking =false;
    		
    	// common booking
        List<CellVO> cellVOList = new ArrayList<CellVO>();
        List<CellVO> commonList = new ArrayList<CellVO>();
        DetachedCriteria commonDC = DetachedCriteria.forClass(BookItem.class);
        commonDC.createAlias("book", "book");
        commonDC.add(Restrictions.eq("book.shop.id", viewVO.getShopId()));
        
        DateTime dateTime = new DateTime(viewVO.getAppointmentTimeStamp());
        commonDC.add(Restrictions.ge("appointmentTime", dateTime.withTimeAtStartOfDay().toDate()));
        commonDC.add(Restrictions.le("appointmentTime", dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate()));
        commonDC.add(Restrictions.eq("isActive", true));
        if(handleDoubleBooking){
        	commonDC.add(Restrictions.eq("isDoubleBooking", false));
        }
        commonDC.add(Restrictions.in("status", CommonConstant.bookItemBlockStatusList));
        commonDC.addOrder(Order.desc("appointmentTime"));
        List<BookItem> bookItemList = list(commonDC);
        commonList = bookItemList.stream().map(BookItem::transferToCell).flatMap(Collection::stream).collect(Collectors.toList());
        cellVOList.addAll(commonList);
        
        //double booking
        if(handleDoubleBooking){
        	transferDoubleBookItemsToCell(viewVO, cellVOList);
        }
        
        //add clock cell
        cellVOList.addAll(blockService.getCellList(viewVO));
        
        return cellVOList;
    }

    @Override
    public List<BookItem> getChildrenOfDoubleBooking(Long parentBookItemId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(BookItem.class);
        criteria.add(Restrictions.in("status", CommonConstant.bookItemBlockStatusList));
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("isDoubleBooking", true));
        criteria.add(Restrictions.eq("doubleBookingParentId", parentBookItemId));
        criteria.addOrder(Order.desc("appointmentTime"));
        return list(criteria);
    }

    @Override
    public List<CellVO> getAllBlockCellList(ViewVO viewVO) {
        List<CellVO> cellVOList = getCellList(viewVO);
        cellVOList.addAll(blockService.getCellList(viewVO));
        return cellVOList;
    }

    @Override
    public Map<TherapistKeyVO, CellVO> getTherapistViewData(ViewVO viewVO) {
        Map<TherapistKeyVO, CellVO> cellVOMap = new HashMap<>();
        List<CellVO> cellVOList = getCellList(viewVO);
        for (CellVO cellVO : cellVOList) {
            BookItem bookItem = cellVO.getBookItem();
            for (BookItemTherapist itemTherapist : bookItem.getBookItemTherapists()) {
                cellVO.setOnRequest(itemTherapist.getOnRequest());
                cellVOMap.put(new TherapistKeyVO(cellVO.getTime(), itemTherapist.getUser()), cellVO);
            }
        }
        return cellVOMap;
    }

    @Override
    public boolean checkBlock(BookItem bookItem) {
        Date startTime = bookItem.getAppointmentTime();
        Date endTime = bookItem.getAppointmentEndTime();
        Shop shop = bookItem.getBook().getShop();
        logger.info("Current bookItem:{}, start:{}, end:{}, is double booking1:{}, double booking parent id:{}",
                bookItem.getId(), startTime, endTime, bookItem.getIsDoubleBooking(), bookItem.getDoubleBookingParentId());
        for (BookItemTherapist bookItemTherapist : bookItem.getBookItemTherapists()) {
            User therapist = bookItemTherapist.getUser();
            Boolean isDoubleBooking = false;
            if (bookItem.getIsDoubleBooking() != null && bookItem.getIsDoubleBooking()) {
                isDoubleBooking = true;
            }
            if (!isDoubleBooking) {
                if (checkBlock(therapist, startTime, endTime, Sets.newHashSet(bookItem))) {
                    throw new ResourceException("Therapist[booking]:" + therapist.getDisplayName() + " has blocked from " + startTime + " to " + endTime);
                    //return true;
                }
            }
            // check block
            if (blockService.checkBlock(shop, therapist, startTime, endTime)) {
                throw new ResourceException("Therapist[block]:" + therapist.getDisplayName() + " has blocked from " + startTime + " to " + endTime);
                //return true;
            }
        }

        Room room = bookItem.getRoom();
        if (room != null) {
//            System.out.println("bookItem.getId():" + bookItem.getId());
            if (checkBlock(room, startTime, endTime, Sets.newHashSet(bookItem))) {
                throw new ResourceException("Room[booking]:" + room.getName() + " has blocked from " + startTime + " to " + endTime);
            }
            // check block
            if (blockService.checkBlock(shop, room, startTime, endTime)) {
                throw new ResourceException("Room[block]:" + room.getName() + " has blocked from " + startTime + " to " + endTime);
            }
        }
        return false;
    }

    @Override
    public boolean checkBlock(User therapist, Date startTime, Date endTime, Set<BookItem> excludeBookItems) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.createAlias("bookItemTherapists", "bit");
        detachedCriteria.add(Restrictions.eq("bit.user.id", therapist.getId()));
        detachedCriteria.add(Restrictions.lt("appointmentTime", endTime)); // 所有的开始时间都要比当前查询的bookItem结束时间小
        detachedCriteria.add(Restrictions.gt("appointmentEndTime", startTime));  // 所有的结束时间都要比当前查询的bookItem开始时间大
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.in("status", CommonConstant.bookItemBlockStatusList));
        if (excludeBookItems != null && !excludeBookItems.isEmpty()) {
            detachedCriteria.add(Restrictions.not(Restrictions.in("id", excludeBookItems.stream().map(BookItem::getId).collect(Collectors.toList())))); // 排除bookItem
        }
        Long count = getCount(detachedCriteria);
//        System.out.println("checkBlock for therapist:" + count);
        return count > 0;
    }

    @Override
    public boolean checkBlock(Room room, Date startTime, Date endTime, Set<BookItem> excludeBookItems) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.add(Restrictions.eq("room.id", room.getId()));
        detachedCriteria.add(Restrictions.lt("appointmentTime", endTime)); // 所有的开始时间都要比当前查询的bookItem结束时间小
        detachedCriteria.add(Restrictions.gt("appointmentEndTime", startTime));  // 所有的结束时间都要比当前查询的bookItem开始时间大
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.in("status", CommonConstant.bookItemBlockStatusList));
        if (excludeBookItems != null && !excludeBookItems.isEmpty()) {
            Set<Long> excludeIds = new HashSet<>();
            for (BookItem bookItem : excludeBookItems) {
                Set<Long> ids;
                BookItem parent = bookItem.getBookItem();
                if (parent != null) {
                    ids = parent.getBookItems().stream().map(BookItem::getId).collect(Collectors.toSet());
                    ids.add(parent.getId());
                } else {
                    ids = bookItem.getBookItems().stream().map(BookItem::getId).collect(Collectors.toSet());
                    ids.add(bookItem.getId()); // 添加自己
                }
                excludeIds.addAll(ids);
            }
//            System.out.println("excludeIds:" + excludeIds);
            detachedCriteria.add(Restrictions.not(Restrictions.in("id", excludeIds))); // 排除同类的bookItem
        }

        Long count = getCount(detachedCriteria);
//        System.out.println("checkBlock for room:" + count);
        return count > 0;
    }

    /**
     * 获取包含这个时间段的bookItem集合
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<BookItem> getBookItemList(Long shopId, Date startTime, Date endTime) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.createAlias("book", "b");
        if (shopId != null) {
            detachedCriteria.add(Restrictions.eq("b.shop.id", shopId));
        }
        detachedCriteria.add(Restrictions.lt("appointmentTime", endTime)); // 所有的开始时间都要比当前查询的bookItem结束时间小
        detachedCriteria.add(Restrictions.gt("appointmentEndTime", startTime));  // 所有的结束时间都要比当前查询的bookItem开始时间大
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.in("status", CommonConstant.bookItemBlockStatusList));
        detachedCriteria.addOrder(Order.asc("appointmentTime")); // 时间升序排序
        return list(detachedCriteria);
    }

    /**
     * 获取包含这个时间段的bookItem集合
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<BookItem> getBookItemList(Long shopId, Date startTime, Date endTime, List<String> statusList) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.createAlias("book", "b");
        if (shopId != null) {
            detachedCriteria.add(Restrictions.eq("b.shop.id", shopId));
        }
        detachedCriteria.add(Restrictions.lt("appointmentTime", endTime)); // 所有的开始时间都要比当前查询的bookItem结束时间小
        detachedCriteria.add(Restrictions.gt("appointmentEndTime", startTime));  // 所有的结束时间都要比当前查询的bookItem开始时间大
        detachedCriteria.add(Restrictions.eq("isActive", true));
        if (statusList.size() > 0) {
            detachedCriteria.add(Restrictions.in("status", statusList));
        }
        detachedCriteria.addOrder(Order.asc("appointmentTime")); // 时间升序排序
        return list(detachedCriteria);
    }


    /**
     * 更新单个bookItem
     *
     * @param viewVO
     */
    @Override
    public void updateBookItem(ViewVO viewVO) {
        if (viewVO.getBookItemId() == null) {
            return;
        }
        BookItem bookItem = get(viewVO.getBookItemId());
        if (bookItem == null) {
            return;
        }

        // 更新预约时间
        if (viewVO.getAppointmentTimeStamp() != null) {
            bookItem.setAppointmentTime(new Date(viewVO.getAppointmentTimeStamp()));
            bookItem.setAppointmentEndTime(new DateTime(viewVO.getAppointmentTimeStamp()).plusMinutes(bookItem.getDuration() + bookItem.getProcessTime()).toDate());
        }

        // is waiting
        if (bookItem.getStatus().equals(CommonConstant.BOOK_STATUS_WAITING)) {
            updateWaitingBookItem(viewVO, bookItem);
        } else {
            // 是否shareRoom
            if (bookItem.getBookItem() != null || bookItem.getBookItems().size() > 0) {
                // share Room
                updateShareRoom(viewVO, bookItem);
            } else {
                // not share Room Update
                updateNotShareRoomBookItem(viewVO, bookItem);
            }
        }
    }

    private void updateWaitingBookItem(ViewVO viewVO, BookItem bookItem) {
        if (viewVO.getNewTherapistId() == null) {
            throw new ResourceException("Therapist is required!");
        }
        updateTherapist(viewVO, bookItem); // 更新therapist
        updateRoom(viewVO, bookItem);

        bookItem.setStatus(CommonConstant.BOOK_STATUS_CONFIRM); // 修改状态

        // checkBlock
        if (checkBlock(bookItem)) {
            throw ResourceException.TIME_BLOCK;
        }

        bookItem.setLastUpdated(new Date());
        bookItem.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
        saveOrUpdate(bookItem);
    }

    /**
     * 更新therapist
     *
     * @param viewVO
     * @param bookItem
     */
    private void updateTherapist(ViewVO viewVO, BookItem bookItem) {
        if (viewVO.getNewTherapistId() != null) {
            User newTherapist = userService.get(viewVO.getNewTherapistId());
            // 检测therapist技能
            if (!userService.checkTherapistSkill(newTherapist, bookItem.getProductOption())) {
                throw new ResourceException("[" + newTherapist.getFullName() + "] cannot perform [" + bookItem.getProductName() + "]. Please choose another therapist.");
            }
            Set<BookItemTherapist> bookItemTherapistSet = bookItem.getBookItemTherapists();

            if (viewVO.getOldTherapistId() != null) {
                // 在多技师模式下，不可以合并技师
                if (!viewVO.getNewTherapistId().equals(viewVO.getOldTherapistId()) && bookItem.getTherapistList().contains(newTherapist)) {
                    throw new ResourceException("Can not merge multi therapists into one therapist in multi therapist mode!");
                }
                // 置换旧的技师
                for (BookItemTherapist bookItemTherapist : bookItemTherapistSet) {
                    if (bookItemTherapist.getUser().getId().equals(viewVO.getOldTherapistId())) {
                        bookItemTherapist.setUser(newTherapist);
                        break;
                    }
                }
            } else {
                bookItemTherapistSet.clear(); // 删除之前的技师
                saveOrUpdate(bookItem);
                getSession().flush();
                BookItemTherapist bookItemTherapist = new BookItemTherapist();
                bookItemTherapist.setOnRequest(bookItem.getRequestedOfFirstTherapist());
                bookItemTherapist.setUser(newTherapist);
                bookItemTherapist.setBookItem(bookItem);
                bookItemTherapistSet.add(bookItemTherapist);
            }
        }
    }

    private void updateRoom(ViewVO viewVO, BookItem bookItem) {
//        System.out.println("viewVO.getRoomId():" + viewVO.getRoomId());
        if (viewVO.getRoomId() == null) { // 没有指定房间，重新分配
            // 重新分配 room
            Long shopId = bookItem.getBook().getShop().getId();
            Date startTime = bookItem.getAppointmentTime();
            Date endTime = bookItem.getAppointmentEndTime();
            Long productOptionId = bookItem.getProductOption().getId();
            List<Room> availableRoomList = roomService.getNotBlockRoomList(productOptionId, shopId, startTime, endTime, bookItem);
            List<Room> roomList = RoomHelper.assignRoom(availableRoomList, 1); // share room
            if (roomList.size() == 1 && roomList.get(0) != null) {
                bookItem.setRoom(roomList.get(0));
            } else {
                logger.error("assign room empty:{}", roomList);
                throw ResourceException.NO_ROOM_AVAILABLE;
            }
        } else {
            Room room = roomService.get(viewVO.getRoomId());
            if (room.getCapacity() < (bookItem.getBookItems().size() + 1)) {
                throw new ResourceException("Room:" + room.getDisplayName() + " capacity is not enougth!");
            }
            bookItem.setRoom(room);
        }
    }

    private void updateNotShareRoomBookItem(ViewVO viewVO, BookItem bookItem) {
        // 重新分配技师
        updateTherapist(viewVO, bookItem);
        // 重新分配房间
        updateRoom(viewVO, bookItem);
        // checkBlock
        if (checkBlock(bookItem)) {
            throw ResourceException.TIME_BLOCK;
        }
        bookItem.setLastUpdated(new Date());
        bookItem.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
        saveOrUpdate(bookItem);
    }

    private void updateShareRoom(ViewVO viewVO, BookItem bookItem) {
        BookItem parent = bookItem;
        if (bookItem.getBookItem() != null) {
            parent = bookItem.getBookItem();
        }

        if (CommonConstant.THERAPIST_VIEW.equals(viewVO.getViewType())) {
            for (BookItem item : parent.getBookItems()) {
                item.setBookItem(null); // 解除父子关系
                saveOrUpdate(item);
            }
            getSession().flush();
            List<BookItem> otherBookItems = parent.getBookItems().stream().filter(e -> !e.getId().equals(bookItem.getId())).collect(Collectors.toList());
            if (!bookItem.getId().equals(parent.getId())) {
                otherBookItems.add(parent);
            }
            // 合并剩余的
            if (otherBookItems.size() >= 2) {
                // 其余的继续是父子关系
                BookItem subParent = otherBookItems.get(0); // 第一个作为parent
                subParent.setBookItem(null);
                subParent.getBookItems().clear(); // 清空
                BookItem child;
                for (int i = 1; i < otherBookItems.size(); i++) {
                    child = otherBookItems.get(i);
                    subParent.getBookItems().add(child);
                    child.setBookItem(subParent);
                }
                saveOrUpdate(subParent);
            }
            updateNotShareRoomBookItem(viewVO, bookItem); // 更新当前的bookItem
        } else if (CommonConstant.ROOM_VIEW.equals(viewVO.getViewType())) {
            updateRoom(viewVO, parent);
            parent.setLastUpdated(new Date());
            parent.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
            // 更新预约时间
            for (BookItem item : parent.getBookItems()) {
                item.setAppointmentTime(parent.getAppointmentTime());
                item.setAppointmentEndTime(parent.getAppointmentEndTime());
                item.setRoom(parent.getRoom()); // 公用同一个房间
                item.setLastUpdated(parent.getLastUpdated());
                item.setLastUpdatedBy(parent.getLastUpdatedBy());
                // checkBlock
                if (checkBlock(item)) {
                    throw ResourceException.TIME_BLOCK;
                }
            }
            saveOrUpdate(parent); // 级联更新
        }
    }


    /**
     * 获取某一个staff的booking信息
     *
     * @param shopId
     * @param staffId
     * @param startTime
     * @return
     */
    @Override
    public List<BookItem> getStaffBookItemList(Long shopId, Long staffId, Date startTime, Long companyId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.createAlias("book", "b");
        detachedCriteria.createAlias("bookItemTherapists", "bt");
        //bookItemTherapists
        if (companyId != null) {
            detachedCriteria.add(Restrictions.eq("b.company.id", companyId));
        }
        detachedCriteria.add(Restrictions.eq("b.shop.id", shopId));
        detachedCriteria.add(Restrictions.eq("bt.user.id", staffId));
        detachedCriteria.add(Restrictions.ge("appointmentTime", new DateTime(startTime).withTimeAtStartOfDay().toDate()));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.in("status", CommonConstant.bookItemBlockStatusList));
        return list(detachedCriteria);
    }

    @Override
    public List<BookItem> getStaffBookItemList(Long shopId, Long staffId, Date startTime) {
        return getStaffBookItemList(shopId, staffId, startTime, null);
    }

    @Override
    public long countBookItems(Long shopId, Date startTime, Date endTime, List<String> statusList, Long companyId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.createAlias("book", "b");
        if (shopId != null) {
            detachedCriteria.add(Restrictions.eq("b.shop.id", shopId));
        }
        if (companyId != null) {
            detachedCriteria.add(Restrictions.eq("b.company.id", companyId));
        }
        detachedCriteria.add(Restrictions.lt("appointmentTime", endTime)); // 所有的开始时间都要比当前查询的bookItem结束时间小
        detachedCriteria.add(Restrictions.gt("appointmentEndTime", startTime));  // 所有的结束时间都要比当前查询的bookItem开始时间大
        detachedCriteria.add(Restrictions.eq("isActive", true));
        if (statusList.size() > 0) {
            detachedCriteria.add(Restrictions.in("status", statusList));
        }
        return getCount(detachedCriteria);
    }

    @Override
    public long countWalkInBookItems(Long shopId, Date startTime, Date endTime, List<String> statusList, boolean walkIn, Long companyId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.createAlias("book", "b");
        if (shopId != null) {
            detachedCriteria.add(Restrictions.eq("b.shop.id", shopId));
        }
        if (companyId != null) {
            detachedCriteria.add(Restrictions.eq("b.company.id", companyId));
        }
        detachedCriteria.add(Restrictions.eq("b.walkIn", walkIn));
        detachedCriteria.add(Restrictions.lt("appointmentTime", endTime)); // 所有的开始时间都要比当前查询的bookItem结束时间小
        detachedCriteria.add(Restrictions.gt("appointmentEndTime", startTime));  // 所有的结束时间都要比当前查询的bookItem开始时间大
        detachedCriteria.add(Restrictions.eq("isActive", true));
        if (statusList.size() > 0) {
            detachedCriteria.add(Restrictions.in("status", statusList));
        }
        return getCount(detachedCriteria);
    }
     public void updateCancelStatus(Long bookItemId, String status) {
         BookItem bookItem = get(bookItemId);
         if (bookItem == null && !status.equals(CommonConstant.BOOK_STATUS_CANCEL)) {
             return;
         }
         if (bookItem.getIsDoubleBooking()) {
             DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
             if (bookItem.getDoubleBookingParentId() != null) {
                 detachedCriteria.add(Restrictions.ne("status", CommonConstant.BOOK_STATUS_CANCEL));
                 detachedCriteria.add(Restrictions.eq("doubleBookingParentId", bookItem.getDoubleBookingParentId()));
                 List<BookItem> bookItems = list(detachedCriteria);
                 if (bookItems.size() == 1) {
                     Date now = new Date();
                     String currentUserName = WebThreadLocal.getUser().getUsername();

                     BookItem parentBookItem = get(bookItems.get(0).getDoubleBookingParentId());
                     parentBookItem.setLastUpdated(now);
                     parentBookItem.setLastUpdatedBy(currentUserName);
                     parentBookItem.setIsDoubleBooking(false);
                     saveOrUpdate(parentBookItem);
                 }
             }
             Date now = new Date();
             String currentUserName = WebThreadLocal.getUser().getUsername();
             bookItem.setStatus(status);
             bookItem.setLastUpdated(now);
             bookItem.setLastUpdatedBy(currentUserName);
             bookItem.setDoubleBookingParentId(null);
             bookItem.setStatus(CommonConstant.BOOK_STATUS_CANCEL);
             saveOrUpdate(bookItem);
             bookService.updateStatus(bookItem.getBook().getId()); // 更新book的状态
         }
     }
     @Override
     public List<BookItem> getBookItemsByDateAndMember(Date startDate, Date endDate, Long memberId) {
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
         String[] bookItemStatus = {CommonConstant.BOOK_STATUS_CONFIRM, CommonConstant.BOOK_STATUS_WAITING};
         detachedCriteria.add(Restrictions.ge("appointmentTime", startDate));
         detachedCriteria.add(Restrictions.le("appointmentTime", endDate));
         detachedCriteria.add(Restrictions.eq("isActive", true));
         detachedCriteria.createAlias("book", "b");
         detachedCriteria.add(Restrictions.eq("b.isActive", true));
         if (memberId != null) {
             detachedCriteria.add(Restrictions.eq("b.user.id", memberId));
         }
         detachedCriteria.add(Restrictions.in("status", bookItemStatus));
         return list(detachedCriteria);
     }

     @Override
     public List<BookItem> getBookItemsByBookId(Long bookId) {
         DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
         String[] bookItemStatus = {CommonConstant.BOOK_STATUS_CONFIRM, CommonConstant.BOOK_STATUS_WAITING};
         detachedCriteria.add(Restrictions.eq("isActive", true));
         detachedCriteria.createAlias("book", "b");
         if (bookId != null) {
             detachedCriteria.add(Restrictions.eq("b.id", bookId));
         }
         return list(detachedCriteria);
     }
 	private void transferDoubleBookItemsToCell(ViewVO viewVO, List<CellVO> cellVOList){
 		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.createAlias("book", "book");
        detachedCriteria.add(Restrictions.eq("book.shop.id", viewVO.getShopId()));
        DateTime dateTime = new DateTime(viewVO.getAppointmentTimeStamp());
        detachedCriteria.add(Restrictions.ge("appointmentTime", dateTime.withTimeAtStartOfDay().toDate()));
        detachedCriteria.add(Restrictions.le("appointmentTime", dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate()));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.eq("isDoubleBooking", true));
        detachedCriteria.add(Restrictions.in("status", CommonConstant.bookItemBlockStatusList));
        detachedCriteria.addOrder(Order.desc("appointmentTime"));
        List<BookItem> bookItemList = list(detachedCriteria);

        Map<Long, List<BookItem>> doubleMap = new HashMap<Long, List<BookItem>>();

        for (BookItem bi : bookItemList) {
            Long doubleParentId = null;
            BookItem doubleChild = null;
            if (bi.getDoubleBookingParentId() == null) {
                //parent
                doubleParentId = bi.getId();
            } else {
                //children
                doubleParentId = bi.getDoubleBookingParentId();
                doubleChild = bi;

            }
            List<BookItem> doubleChildList = doubleMap.get(doubleParentId);
            if (doubleChildList == null) {
                doubleChildList = new ArrayList<BookItem>();
                doubleChildList.add(doubleChild);
                doubleMap.put(doubleParentId, doubleChildList);
            } else {
                if (doubleChild != null) {
                    doubleChildList.add(doubleChild);
                }
            }
        }
        BookItem parentBI = null;
        for (Long doubleParentId : doubleMap.keySet()) {
            parentBI = get(doubleParentId);
            DateTime startTime = new DateTime(parentBI.getAppointmentTime());
            List<BookItem> doubleChildIdList = doubleMap.get(doubleParentId);
            doubleChildIdList.stream().sorted(Comparator.comparing(BookItem::getAppointmentEndTime).reversed()).collect(Collectors.toList());
            BookItem lastChild = doubleChildIdList.get(0);
            DateTime endTime = null;
            if (lastChild.getAppointmentEndTime().before(parentBI.getAppointmentEndTime())) {
                endTime = new DateTime(parentBI.getAppointmentEndTime()).plusMinutes(lastChild.getProductOption().getProcessTime());
            } else {
                endTime = new DateTime(lastChild.getAppointmentEndTime()).plusMinutes(lastChild.getProductOption().getProcessTime());
            }
            for (BookItemTherapist itemTherapist : parentBI.getBookItemTherapists()) {
                while (startTime.isBefore(endTime)) {
                    CellVO cellVO = new CellVO();
                    cellVO.setBookItem(parentBI);
                    cellVO.setTime(startTime);
                    cellVO.setTherapist(itemTherapist.getUser());
                    cellVOList.add(cellVO);
                    startTime = startTime.plusMinutes(CommonConstant.TIME_UNIT); // 时间递增
                }
            }
        }
 	}

    /**
     * create by rick --2018.9.19
     *
     * 同一个房间的treatment只需要一个processTime；
     * @param bookItems
     */
    public List<List<BookItem>> comibeBookItem(List<BookItem> bookItems)
    {
        List bookItemNew = new ArrayList();
        if(bookItems != null ||bookItems.size() > 1)
        {
            for (int i = 0; i < bookItems.size() - 1; i++)
            {
                List<Integer> integers = null;
                boolean b = true;
                int isFirst = 0;
                for (int j = i + 1; j <  bookItems.size();j++)
                {
                    if(bookItems.get(i).getRoom().getId() == bookItems.get(j).getRoom().getId())
                    {
                        if(b)
                        {
                            integers = new ArrayList();
                        }
                        b = false;
                        if(isFirst == 0)
                        {
                            integers.add(i);
                            integers.add(j);
                            isFirst++;
                        }
                        else
                        {
                            integers.add(j);
                        }
                    }
                }
                if(integers == null)
                {
                    List<BookItem> bookItemVOS = new ArrayList<>();
                    bookItemVOS.add(bookItems.get(i));
                    bookItemNew.add(bookItemVOS);
                    bookItems.remove(i);
                    b = true;
                    isFirst = 0;
                }
                else
                {
                    List<BookItem> bookItemList = new ArrayList<>();
                    for (Integer integer:integers)
                    {
                        bookItemList.add(bookItems.get(integer));
                    }
                    for (int x = 0;i < integers.size();i++)
                    {
                        bookItems.remove(0);
                    }
                    bookItemNew.add(bookItemList);
                    b = true;
                    isFirst = 0;
                }
                i = -1;
            }

            if(bookItems.size() == 1)
            {
                List<BookItem> bookItemVOS = new ArrayList<>();
                bookItemVOS.add(bookItems.get(0));
                bookItemNew.add(bookItemVOS);
                bookItems.remove(0);
            }
        }
        return bookItemNew;
    }

    //取消时间处理
    private void timeDealWith(List<List<BookItem>> list,Long id)
    {
        /*Set<BookItem> newBookItem = new HashSet<>();*/
        //对组的时间进行一个排序，符合条件的进行时间处理
        List< List<BookItem>> sortList = new ArrayList<>();
        BookItem bookItem;
        for(List<BookItem> newGroupList: list)
        {
            if(newGroupList.size() > 1)
            {
                BookItem[] itemVOS = newGroupList.toArray(new BookItem[newGroupList.size()]);
                for(int i = 0;i < newGroupList.size() - 1;i++)
                {
                    for(int j = i + 1;j < newGroupList.size();j++)
                    {
                        if(itemVOS[i].getAppointmentTime().getTime() > itemVOS[j].getAppointmentTime().getTime())
                        {
                            bookItem = itemVOS[i];
                            itemVOS[i] = itemVOS[j];
                            itemVOS[j] = bookItem;
                        }
                    }
                }
                List<BookItem> bookItemVOList = Arrays.asList(itemVOS);
                sortList.add(bookItemVOList);
            }
            else
            {
                sortList.add(newGroupList);
            }
        }

        OUT:
        for (List<BookItem> lists:sortList)
        {
            if(lists.size() > 1)
            {
                for(int i = 0;i < lists.size();i ++)
                {
                    //2个以上item
                   if( lists.get(i).getId() == id)
                   {
                       //一个房间拥有两个预定进行比较
                       if(i != 0 && lists.get(i - 1).getAppointmentEndTime().getTime() == lists.get(i).getAppointmentTime().getTime())
                        {
                            int beforeTime = lists.get(i - 1).getProcessTime();
                            lists.get(i - 1).setAppointmentEndTime(new Date(lists.get(i - 1).getAppointmentEndTime().getTime() + beforeTime*1000*60));
                            //取消预定的book，如果上一条有时间相连就修改上一条book的结束时间
                            update(lists.get(i - 1));
                            break OUT;
                        }
                   }
                }
            }
        }

       /* for (List<BookItem> lists:sortList)
        {
            for(int i = 0;i < lists.size();i ++)
            {
                  newBookItem.add(lists.get(i));
            }
        }
        return newBookItem;*/
    }

	@Override
	public List<BookItem> getBookItemsByRoomId(Long shopId,Long roomId, Date startTime, Date endTime) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
        detachedCriteria.createAlias("book", "b");
        detachedCriteria.createAlias("room", "r");
        detachedCriteria.add(Restrictions.eq("b.shop.id", shopId));
        detachedCriteria.add(Restrictions.eq("r.id", roomId));
        detachedCriteria.add(Restrictions.ge("appointmentTime", new DateTime(startTime).toDate()));
        if(Objects.nonNull(endTime)) {
        	detachedCriteria.add(Restrictions.le("appointmentEndTime", new DateTime(endTime).toDate()));
        }
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.in("status", CommonConstant.bookItemBlockStatusList));
        detachedCriteria.addOrder(Order.asc("appointmentTime"));
        return list(detachedCriteria);
	}
}
