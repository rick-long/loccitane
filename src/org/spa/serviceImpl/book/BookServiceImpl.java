package org.spa.serviceImpl.book;

import com.spa.constant.CommonConstant;
import com.spa.email.EmailAddress;
import com.spa.freemarker.EmailTemplate;
import com.spa.runtime.exception.ResourceException;
import com.spa.helper.RoomHelper;
import com.spa.job.EmailJob;
import com.spa.thread.CommonSendEmailThread;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.quartz.JobDataMap;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.book.*;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.OpeningHours;
import org.spa.model.shop.Shop;
import org.spa.model.user.Guest;
import org.spa.model.user.User;
import org.spa.service.book.BlockService;
import org.spa.service.book.BookItemService;
import org.spa.service.book.BookService;
import org.spa.service.marketing.MktMailShotService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.shop.RoomService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.GuestService;
import org.spa.service.user.UserService;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.app.book.*;
import org.spa.vo.app.callback.BookingItemSucessCallBackVO;
import org.spa.vo.app.callback.BookingSuccessCallBackVO;
import org.spa.vo.book.BookFirstStepRecordVO;
import org.spa.vo.book.BookItemVO;
import org.spa.vo.book.BookVO;
import org.spa.vo.book.RequestTherapistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.spa.model.book.Block;
import org.spa.model.book.Book;
import org.spa.model.book.BookFirstStepRecord;
import org.spa.model.book.BookItem;
import org.spa.model.book.BookItemTherapist;
import org.spa.model.shop.Room;
import org.spa.utils.DateUtil;
import org.spa.utils.PropertiesUtil;
import org.spa.utils.Results;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl extends BaseDaoHibernate<Book> implements BookService {
	
    private static final int EMAIL_BOOKING_DELAY_START =2;//发送时间设置

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private BookItemService bookItemService;

    @Autowired
    private MktMailShotService mktMailShotService;

    public Book add(BookVO bookVO) {
        Date now = new Date();
        String currentUserName = WebThreadLocal.getUser().getUsername();
        Book book = new Book();
        Shop shop = shopService.get(bookVO.getShopId());
        book.setShop(shop);
        book.setReference(RandomUtil.generateRandomNumberWithDate(null));
        User member = userService.get(bookVO.getMemberId());
        book.setUser(member);
        book.setIsActive(true);
        book.setCreated(now);
        book.setCreatedBy(currentUserName);
        book.setWalkIn(bookVO.getWalkIn());
        book.setPregnancy(bookVO.getPregnancy());
        book.setAppointmentTime(bookVO.getStartAppointmentTime());
        book.setGuestAmount(bookVO.getBookItemVOs().size());
        book.setRemarks(bookVO.getRemarks());
        book.setStatus(CommonConstant.BOOK_STATUS_CONFIRM);
        book.setLastUpdated(now);
        book.setLastUpdatedBy(currentUserName);
        book.setCompany(WebThreadLocal.getCompany());
        // 先默认false
        if(Objects.isNull(bookVO.getSameTimeToShareRoom() )) {
        	 book.setAllShareSingleRoom(false);
        }else {
        	if(bookVO.getSameTimeToShareRoom()) {
        		book.setAllShareSingleRoom(true);
        	}else {
        		book.setAllShareSingleRoom(false);
        	}
        }
        book.setMobilePrepaid(false);
        book.setBookingChannel(StringUtils.isNoneBlank(bookVO.getBookingChannel()) ? bookVO.getBookingChannel() : CommonConstant.BOOKING_CHANNEL_STAFF);
        //sharing room base on booking
//        book.setShareRoomForBooking(bookVO.getShareRoomForBooking());
        
        // guest用户保存guest信息
        if(CommonConstant.USER_ACCOUNT_TYPE_GUEST.equals(member.getAccountType())) {
           Guest guest = guestService.saveOrUpdate(bookVO); // 保存guest
           book.setGuest(guest);
        }

        Set<BookItem> bookItems = book.getBookItems();

        // 所有root book item vo
        List<BookItemVO> bookItemVOs = bookVO.getBookItemVOs().stream().filter(e->e.getTempParentId() == null).collect(Collectors.toList());

        /*为room的name做一个组合，name一致的为一个组合  update by Rick 2018..9.4*/
        List<List<BookItemVO>> lists = comibeBookItemVo(bookItemVOs);
        //判断房间里面是否有相连的时间段，如果有，则进行处理
        List<BookItemVO> bookItemVOList = timeDealWith(lists);
        //用新的bookItemVOList遍历
        /*以上*/
        for (BookItemVO bookItemVO : bookItemVOList) {
            BookItem bookItem = getBookItem(book, bookItemVO);
            bookItem.setBookItem(null);
            bookItems.add(bookItem);
            // 设置Children
            List<BookItemVO> children = bookVO.getBookItemVOs().stream().filter(e -> bookItemVO.getTempId().equals(e.getTempParentId())).collect(Collectors.toList());
            for (BookItemVO child : children) {
                BookItem childItem = getBookItem(book, child);
                // waiting 状态取消父子关系
                if (!CommonConstant.BOOK_STATUS_WAITING.equals(child.getStatus())) {
                    childItem.setBookItem(bookItem); // 设置父节点
                    bookItem.getBookItems().add(childItem);
                }
                bookItems.add(childItem);
            }
            //set the parent of double booking
            if(bookItemVO.getDoubleBookingParentId() !=null){
            	BookItem parentBI = bookItemService.get(bookItemVO.getDoubleBookingParentId());
            	parentBI.setIsDoubleBooking(true);
            	bookItemService.saveOrUpdate(parentBI);
            }
        }

        // 保存 book first step record
        Set<BookFirstStepRecord> recordSet = book.getBookFirstStepRecords();
        for (BookFirstStepRecordVO recordVO : bookVO.getFirstStepRecordVOList()) {
            BookFirstStepRecord record = new BookFirstStepRecord();
            record.setBook(book);
            record.setProductOption(recordVO.getProductOption());
            record.setGuestAmount(recordVO.getGuestAmount());
            record.setShareRoom(recordVO.getShareRoom() != null && recordVO.getShareRoom());
            record.setStartTime(recordVO.getStartTime());
            record.setEndTime(recordVO.getEndTime());
            record.setDisplayOrder(recordVO.getDisplayOrder());
            record.setIsActive(true);
            record.setCreated(now);
            record.setCreatedBy(currentUserName);
            record.setLastUpdated(now);
            record.setLastUpdatedBy(currentUserName);
            record.setBundleId(recordVO.getBundleId());
            recordSet.add(record);
        }
        saveOrUpdate(book);
        getSession().flush();

        for (BookItem bookItem : book.getBookItems()) {
            // 不是waiting的bookItem再次检查是否被block
            if (!bookItem.isWaiting() && bookItemService.checkBlock(bookItem)) {
                throw ResourceException.TIME_BLOCK;
            }
            // 检测所有therapist技能
            for (User therapist : bookItem.getTherapistList()) {
                if (!userService.checkTherapistSkill(therapist, bookItem.getProductOption())) {
                    throw new ResourceException("[" + therapist.getFullName() + "] cannot perform [" + bookItem.getProductName() + "]. Please choose another therapist.");
                }
            }

            // 时间超过店的关门时间
            if(bookVO.isCheckOpenCloseTime()) {
                OpeningHours openingHours = shop.getOpeningHour(bookVO.getStartAppointmentTime());
                DateTime openTime = openingHours.getOpenTimeObj();
                DateTime closeTime = openingHours.getCloseTimeObj(); // 关门时间
                DateTime startAppointment = new DateTime(bookItem.getAppointmentTime());
//                DateTime endAppointment = startAppointment.plusMinutes(bookItem.getDuration());
                DateTime endAppointment = new DateTime(bookItem.getAppointmentEndTime());
                if (startAppointment.isBefore(openTime) || endAppointment.isAfter(closeTime)) {
                    throw new RuntimeException("Appointment time is out of shop service time!");
                }
            }

        }
        // 不生成resource，直接使用bookItem
        /*ResourceVO resourceVO = new ResourceVO();
        resourceVO.setResourceRoomMap(new HashMap<>());
        resourceVO.setShopId(shop.getId());
        // 只要不是release状态的都生成resource
        book.getBookItems().stream().filter(bookItem -> !CommonConstant.releaseResourceStatusList.contains(bookItem.getStatus())).forEach(bookItem -> {
            resourceVO.setBookItem(bookItem);
            resourceVO.setAppointmentTimeStamp(bookItem.getAppointmentTime().getTime());
            resourceService.add(resourceVO);// 添加resource
        });*/
        return book;
    }


    @Override
    public Book edit(BookVO bookVO) {
        Date now = new Date();
        String currentUserName = WebThreadLocal.getUser().getUsername();
        Book book = get(bookVO.getId());
        if(bookVO.getMember() !=null){
        	book.setUser(bookVO.getMember());
        }
        
        Shop shop = book.getShop();
        // 先默认false
        book.setWalkIn(bookVO.getWalkIn());
        book.setPregnancy(bookVO.getPregnancy() );
        book.setAppointmentTime(bookVO.getStartAppointmentTime());
        book.setGuestAmount(bookVO.getBookItemVOs().size());
        book.setRemarks(bookVO.getRemarks());
        book.setStatus(CommonConstant.BOOK_STATUS_CONFIRM);
        book.setLastUpdated(now);
        book.setLastUpdatedBy(currentUserName);
        if(Objects.isNull(bookVO.getSameTimeToShareRoom() )) {
        	 book.setAllShareSingleRoom(false);
        }else {
        	if(bookVO.getSameTimeToShareRoom()) {
        		book.setAllShareSingleRoom(true);
        	}else {
        		book.setAllShareSingleRoom(false);
        	}
        }
        if(CommonConstant.USER_ACCOUNT_TYPE_GUEST.equals(book.getUser().getAccountType())) {
            Guest guest = guestService.saveOrUpdate(bookVO); // 保存guest
            getSession().flush();
            book.setGuest(guest);
        }
        Set<BookItem> bookItems = book.getBookItems();
        Set<BookFirstStepRecord> recordSet = book.getBookFirstStepRecords();
        bookItems.clear(); // 清除之前bookItem
        recordSet.clear();
        saveOrUpdate(book);
        getSession().flush();

        // 重新生成bookItem
        List<BookItemVO> bookItemVOs = bookVO.getBookItemVOs().stream().filter(e->e.getTempParentId() == null).collect(Collectors.toList());

        /*为room的name做一个组合，name一致的为一个组合  update by Rick 2018..9.4*/
        List<List<BookItemVO>> lists = comibeBookItemVo(bookItemVOs);
        //判断房间里面是否有相连的时间段，如果有，则进行处理
        List<BookItemVO> bookItemVOList = timeDealWith(lists);
        //用新的bookItemVOList遍历
        /*以上*/
        for (BookItemVO bookItemVO : bookItemVOList) {
            BookItem bookItem = getBookItem(book, bookItemVO);
            bookItem.setBookItem(null);
            bookItems.add(bookItem);
            // 设置Children
            List<BookItemVO> children = bookVO.getBookItemVOs().stream().filter(e -> bookItemVO.getTempId().equals(e.getTempParentId())).collect(Collectors.toList());
            for (BookItemVO child : children) {
                BookItem childItem = getBookItem(book, child);
                // waiting 状态取消父子关系
                if (!CommonConstant.BOOK_STATUS_WAITING.equals(child.getStatus())) {
                    childItem.setBookItem(bookItem); // 设置父节点
                    bookItem.getBookItems().add(childItem);
                }
                bookItems.add(childItem);
            }
        }

        // 保存 book first step record
        for (BookFirstStepRecordVO recordVO : bookVO.getFirstStepRecordVOList()) {
            BookFirstStepRecord record = new BookFirstStepRecord();
            record.setBook(book);
            record.setProductOption(recordVO.getProductOption());
            record.setGuestAmount(recordVO.getGuestAmount());
            record.setShareRoom(recordVO.getShareRoom() != null && recordVO.getShareRoom());
            record.setStartTime(recordVO.getStartTime());
            record.setEndTime(recordVO.getEndTime());
            record.setDisplayOrder(recordVO.getDisplayOrder());
            record.setIsActive(true);
            record.setCreated(now);
            record.setCreatedBy(currentUserName);
            record.setLastUpdated(now);
            record.setLastUpdatedBy(currentUserName);
            record.setBundleId(recordVO.getBundleId());
            recordSet.add(record);
        }
        saveOrUpdate(book);
        getSession().flush();

        for (BookItem bookItem : book.getBookItems()) {
            // 不是waiting的bookItem再次检查是否被block
            if (!bookItem.isWaiting() && bookItemService.checkBlock(bookItem)) {
                throw ResourceException.TIME_BLOCK;
            }
            // 检测所有therapist技能
            for (User therapist : bookItem.getTherapistList()) {
                if (!userService.checkTherapistSkill(therapist, bookItem.getProductOption())) {
                    throw new ResourceException("[" + therapist.getFullName() + "] cannot perform [" + bookItem.getProductName() + "]. Please choose another therapist.");
                }
            }

            // 时间超过店的关门时间
            if (bookVO.isCheckOpenCloseTime()) {
                OpeningHours openingHours = shop.getOpeningHour(bookVO.getStartAppointmentTime());
                DateTime openTime = openingHours.getOpenTimeObj();
                DateTime closeTime = openingHours.getCloseTimeObj(); // 关门时间
                DateTime startAppointment = new DateTime(bookItem.getAppointmentTime());
                DateTime endAppointment = startAppointment.plusMinutes(bookItem.getDuration());
                if (startAppointment.isBefore(openTime) || endAppointment.isAfter(closeTime)) {
                    throw new RuntimeException("Appointment time is out of shop service time!");
                }
            }
        }
        return book;
    }

    private BookItem getBookItem(Book book, BookItemVO bookItemVO) {
        ProductOption productOption = productOptionService.get(bookItemVO.getProductOptionId());
        Integer duration = productOption.getDuration();
        Double price = productOption.getFinalPrice(book.getShop().getId());
        // bookItem
        BookItem bookItem = new BookItem();
        bookItem.setBook(book);
        bookItem.setStatus(bookItemVO.getStatus()); // 设置状态
        bookItem.setAppointmentTime(bookItemVO.getAppointmentTime());
        bookItem.setDuration(duration);
        bookItem.setProcessTime(productOption.getProcessTime());
//        bookItem.setAppointmentEndTime(new DateTime(bookItemVO.getAppointmentTime()).plusMinutes(duration + productOption.getProcessTime()).toDate());
        bookItem.setAppointmentEndTime(new DateTime(bookItemVO.getEndAppointmentTime()).toDate());
        bookItem.setPrice(price);
       
        bookItem.setProductOption(productOption);
        bookItem.setIsActive(true);
        bookItem.setCreated(book.getCreated());
        bookItem.setCreatedBy(book.getCreatedBy());
        bookItem.setLastUpdated(book.getCreated());
        bookItem.setLastUpdatedBy(book.getCreatedBy());
        bookItem.setOnRequest(bookItemVO.getOnRequest());
        bookItem.setIsDoubleBooking(bookItemVO.getIsDoubleBooking() !=null ? bookItemVO.getIsDoubleBooking() :false );
        bookItem.setDoubleBookingParentId(bookItemVO.getDoubleBookingParentId());
        bookItem.setProductName(bookItem.getProdNameWithFinalPrice());
        bookItem.setBundleId(bookItemVO.getBundleId());
        
        if (bookItemVO.getRoomId() != null) {
            bookItem.setRoom(roomService.get(bookItemVO.getRoomId()));
        }

        // 设置技师
        for (RequestTherapistVO therapistVO : bookItemVO.getRequestTherapistVOs()) {
            if (therapistVO != null && therapistVO.getTherapistId() != null) {
                BookItemTherapist bookItemTherapist = new BookItemTherapist();
                bookItemTherapist.setBookItem(bookItem);
                bookItemTherapist.setUser(userService.get(therapistVO.getTherapistId()));
                bookItemTherapist.setOnRequest(therapistVO.getOnRequest());
                bookItem.getBookItemTherapists().add(bookItemTherapist);
            }
        }
        return bookItem;
    }

    /**
     * 根据book item的状态修改book的状态
     *
     * @param bookId
     */
    public void updateStatus(Long bookId) {
        Book book = get(bookId);
        if (book == null) {
            return;
        }
        List<String> statusList = book.getBookItems().stream().map(BookItem::getStatus).collect(Collectors.toList());
        // 不用更新
        // CommonConstant.BOOK_STATUS_CONFIRM
        // CommonConstant.BOOK_STATUS_CHECKIN_SERVICE
        // CommonConstant.BOOK_STATUS_WAITING
        if (statusList.contains(CommonConstant.BOOK_STATUS_CONFIRM)
                || statusList.contains(CommonConstant.BOOK_STATUS_CHECKIN_SERVICE)
                || statusList.contains(CommonConstant.BOOK_STATUS_WAITING)) {
            logger.debug("no need to update book status, id:{}, status list:{}", bookId, statusList);
        } else {
            if (statusList.contains(CommonConstant.BOOK_STATUS_COMPLETE)) {
                book.setStatus(CommonConstant.BOOK_STATUS_COMPLETE);
                
            } else if(statusList.contains(CommonConstant.BOOK_STATUS_RECOVER)){
                book.setStatus(CommonConstant.BOOK_STATUS_RECOVER);
                
            }else{
            	 book.setStatus(CommonConstant.BOOK_STATUS_CANCEL);
            }
            Date now = new Date();
            String currentUserName = WebThreadLocal.getUser().getUsername();
            book.setLastUpdated(now);
            book.setLastUpdatedBy(currentUserName);
            saveOrUpdate(book);
        }
    }

    /**
     * cancel book
     *
     * @param bookId
     */
    public void cancel(Long bookId) {
        Book book = get(bookId);
        if (book == null) {
            return;
        }
        Date now = new Date();
        String currentUserName = WebThreadLocal.getUser().getUsername();
        book.setStatus(CommonConstant.BOOK_STATUS_CANCEL);
        book.setLastUpdated(now);
        book.setLastUpdatedBy(currentUserName);
        Set<BookItem> bookItemSet = book.getBookItems();
        for (BookItem bookItem : bookItemSet) {
            Set<Resource> resourceSet = bookItem.getResources();
            resourceSet.clear(); // 清除所有的resource
            bookItem.setStatus(CommonConstant.BOOK_STATUS_CANCEL);
            bookItem.setLastUpdated(now);
            bookItem.setLastUpdatedBy(currentUserName);
        }
        saveOrUpdate(book);
    }
    
    private List<Room> getNotBlockRoomList(List<Room> roomList, Set<Block> blockRoomSet, Date start, Date end) {
        List<Room> notBlockRoomList = new ArrayList<>();
        for (Room room : roomList) {
            boolean isBlock = false;
            for (Block block : blockRoomSet) {
                if (block.getRoom().getId().equals(room.getId())) {
                    isBlock = isBlock || !(start.after(block.getEndDate()) || end.before(block.getStartDate()));
                }
            }
            if (!isBlock) {
                notBlockRoomList.add(room);
            }
        }
        return notBlockRoomList;
    }
    
    private Boolean checkBlockTherapist(User staff, Set<Block> blockTherapistSet, Date start, Date end) {
        boolean isBlock = false;
        for (Block block : blockTherapistSet) {
            if (block.getUser().getId().equals(staff.getId())) {
                isBlock = isBlock || !(start.after(block.getEndDate()) || end.before(block.getStartDate()));
            }
        }
        return isBlock;
    }
    
    private List<User> getAvaliableTherapist(Shop shop,ProductOption productOption,Long timestamp,Boolean isOnline){
    	List<User> allTherapists = userService.getTherapistsBySkill(shop.getId(), productOption,isOnline);
        System.out.println("---allTherapists---"+allTherapists);
        Date startTime = new Date(timestamp);
        Date endTime = new DateTime(startTime).plusMinutes(productOption.getDuration() + productOption.getProcessTime()).toDate(); // end appointment time
        Iterator<User> userIterator = allTherapists.iterator();
        boolean isBlock;
        while (userIterator.hasNext()) {
            isBlock = false;
            // 计算user 是否被block
            User user = userIterator.next();

            if (isBlock) {
                userIterator.remove();  // 删除block的技师
                continue;
            }

            if (bookItemService.checkBlock(user, startTime, endTime, null)) {
                isBlock = true;
            }
            if (isBlock) {
                userIterator.remove();
                continue;
            }
            if (blockService.checkBlock(shop, user, startTime, endTime)) {
                userIterator.remove();
            }
        }
        return allTherapists;
    }
    
    @Override
    public Results saveAppsBook(BookRequestVO bookRequestVO3) {

        try {
        boolean isParameterError= false;
        Results results = Results.getInstance();
        BookingSuccessCallBackVO bookCallBack2 =new BookingSuccessCallBackVO();
        Date now = new Date();
        User member = userService.get(bookRequestVO3.getMemberId());
        if(member == null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Member does not exist!");
        }

        Shop shop = shopService.get(bookRequestVO3.getShopId());
        if(shop == null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Shop does not exist! ");
        }


        bookCallBack2.setShopName(shop.getName());
        bookCallBack2.setShopPhone(shop.getPhoneNumber());
        bookCallBack2.setShopRef(shop.getReference());
        bookCallBack2.setShopPrefix(shop.getPrefix());
        try {
            bookCallBack2.setDate(DateUtil.dateToString(bookRequestVO3.getAppointmentDate(), "yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bookCallBack2.setTotalPrice(CommonConstant.CURRENCY_TYPE+"0");

        Book book = new Book();
        book.setShop(shop);

        book.setReference(RandomUtil.generateRandomNumberWithDate(null));
        bookCallBack2.setBookingNumber(book.getReference());

        book.setUser(member);
        book.setIsActive(true);
        book.setWalkIn(false);
        book.setRemarks(null);
        book.setStatus(CommonConstant.BOOK_STATUS_CONFIRM);
        book.setCompany(shop.getCompany());

        book.setCreated(now);
        book.setCreatedBy(member.getUsername());
        book.setLastUpdated(now);
        book.setLastUpdatedBy(member.getUsername());
        
        book.setMobilePrepaid(false);
        book.setBookingChannel(CommonConstant.BOOKING_CHANNEL_MOBILE);
        Date appointmentDate = bookRequestVO3.getAppointmentDate();
        book.setAppointmentTime(appointmentDate);

        Set<BookItem> bookItemList = book.getBookItems();

        // 当前booking选择的block集合
        Set<Block> blockRoomSet = new HashSet<>();
        Set<Block> blockTherapistSet = new HashSet<>();
        Set<Long> blockTherapistIds = new HashSet<Long>();
        List<BookItemRequestVO> bookItemRequestVOs = bookRequestVO3.getBookItems();
        //判断房间的名称是否一样，如果一样processTime只需要计算一次
//        comibeBookItemRequestVo(bookItemRequestVOs);

        if(bookItemRequestVOs ==null || bookItemRequestVOs.size()<=0){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Booing Item does not exist!");
        }
        Integer guestNum = bookRequestVO3.getGuestNum() !=null ? bookRequestVO3.getGuestNum() :0;
        if(bookItemRequestVOs.size() < guestNum){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Booing Item is less than No.of Guest!");
        }
        book.setGuestAmount(guestNum);

        List<BookingItemSucessCallBackVO> bookingItemSucessCallBackVOList = new ArrayList<BookingItemSucessCallBackVO>();

        Double totalPrice = 0d;
        BookingItemSucessCallBackVO itemCallBack =null;
        for (BookItemRequestVO itemRequestVO : bookItemRequestVOs) {

            itemCallBack =new BookingItemSucessCallBackVO();
            bookingItemSucessCallBackVOList.add(itemCallBack);
            
            ProductOption productOption = productOptionService.get(itemRequestVO.getProductOptionId());
            if(productOption ==null){
                itemCallBack.setStatusCode("0");
                itemCallBack.setProductName("");
                itemCallBack.setMins("");
                itemCallBack.setErrorMsg("Product does not exist!");
                isParameterError=true;
                continue;
            }
            itemCallBack.setProductName(productOption.getProduct().getName());
            itemCallBack.setMins(productOption.getMins());

            Integer duration = productOption.getDuration();
            Double price = productOption.getFinalPrice(book.getShop().getId());
            // bookItem
            BookItem bookItem = new BookItem();
            bookItem.setBook(book);
            bookItem.setStatus(CommonConstant.BOOK_STATUS_CONFIRM); // 设置状态

            if(itemRequestVO.getTimestamp() ==null){
                itemCallBack.setStatusCode("0");
                itemCallBack.setStartTime("");
                itemCallBack.setErrorMsg("Time does not exist! ");
                isParameterError=true;
                continue;
            }

            Date startAppointmentDate = new Date(itemRequestVO.getTimestamp());
            DateTime startAppointment = new DateTime(startAppointmentDate);
            try {
                itemCallBack.setStartTime(DateUtil.dateToString(startAppointmentDate, "HH:mm"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DateTime endAppointment = new DateTime(itemRequestVO.getEndAppointmentTime());
            // 时间超过店的关门时间
            OpeningHours openingHours = shop.getOpeningHour(startAppointment.toDate());
            DateTime openTime = openingHours.getOpenTimeObj();
            DateTime closeTime = openingHours.getCloseTimeObj(); // 关门时间

            if (startAppointment.isBefore(openTime) || endAppointment.isAfter(closeTime)) {
                itemCallBack.setStatusCode("0");
                itemCallBack.setErrorMsg("Appointment time is out of shop service time!");
                isParameterError=true;
                continue;
            }

            bookItem.setAppointmentTime(startAppointment.toDate());//start time
            bookItem.setAppointmentEndTime(endAppointment.toDate());//end time
            try {
                itemCallBack.setEndTime(DateUtil.dateToString(endAppointment.toDate(), "HH:mm"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            bookItem.setDuration(duration);
            bookItem.setProcessTime(productOption.getProcessTime());

            bookItem.setPrice(price);

            itemCallBack.setPrice(CommonConstant.CURRENCY_TYPE+price.toString());
            totalPrice += price;
            
            bookItem.setProductOption(productOption);
            bookItem.setIsActive(true);
            bookItem.setCreated(book.getCreated());
            bookItem.setCreatedBy(book.getCreatedBy());
            bookItem.setIsDoubleBooking(false);
            bookItem.setLastUpdated(book.getCreated());
            bookItem.setLastUpdatedBy(book.getCreatedBy());

            bookItem.setProductName(bookItem.getProdNameWithFinalPrice());

            // 设置技师
            String therapistIdsStr=itemRequestVO.getTherapistIds();
            if (StringUtils.isBlank(therapistIdsStr)){
                itemCallBack.setStatusCode("0");
                itemCallBack.setErrorMsg(" No selection  Therapist  ");
                isParameterError = true;
                continue;
            }

            String[] therapistIds=therapistIdsStr.split(",");
            List<String> therapistList = new ArrayList<>(therapistIds.length);
            Collections.addAll(therapistList,therapistIds);
            Integer capacity=productOption.getCapacity();
            if (therapistList.size()<capacity){
                Integer size=capacity-therapistIds.length;
                for (int i=0;i<size;i++){
                    therapistList.add("99999999");
                }
            }
            String staffNames="";
            boolean status=false;
            for (int i=0;i<therapistList.size();i++) {
                User staff = null;
                Boolean isOnRequest = true;
                List<User> avaliableTherapists = getAvaliableTherapist(shop, productOption, itemRequestVO.getTimestamp(), true);
                if (avaliableTherapists != null && avaliableTherapists.size() > 0) {
                    if (CommonConstant.anyTherapist.equals(therapistList.get(i))) {
                        for (User theraist : avaliableTherapists) {
                            if (!blockTherapistIds.contains(theraist.getId())&&!therapistList.contains(String.valueOf(theraist.getId()))) {
                                staff = theraist;
                                isOnRequest = false;
                                break;
                            }
                        }
                    } else {
                        staff = userService.get(Long.valueOf(therapistList.get(i)));
                        if (staff == null ) {
                            itemCallBack.setStatusCode("0");
                            itemCallBack.setErrorMsg("Therapist is unavailable! ");
                            isParameterError = true;
                            continue;
                        }


                        boolean isContain = false;
                        for (User theraist : avaliableTherapists) {
                            if (theraist.getId().longValue() == staff.getId().longValue()) {
                                isContain = true;
                                break;
                            }
                        }
                        if (!isContain) {
                            itemCallBack.setStatusCode("0");
                            itemCallBack.setErrorMsg("Therapist " + staff.getDisplayName() + " is blocked! ");
                            isParameterError = true;
                            continue;
                        }
                    }


                } else {
                    System.out.println("--00000000----");
                    status=true;
                    itemCallBack.setStatusCode("0");
                    itemCallBack.setErrorMsg("Therapist is unavailable! ");
                    isParameterError = true;
                    continue;
                }
                if (staff!=null){
                    staffNames+=staff.getDisplayName()+",";
                }
                bookItem.setOnRequest(isOnRequest);

                if (staff == null || !staff.getAccountType().equals(CommonConstant.USER_ACCOUNT_TYPE_STAFF)) {
                    status=true;
                    itemCallBack.setStatusCode("0");
                    itemCallBack.setErrorMsg("Therapist is unavailable! ");
                    isParameterError = true;
                    continue;
                }


                boolean isTerapistBlock = blockService.checkBlock(book.getShop(), staff, startAppointment.toDate(), endAppointment.toDate());
                if (isTerapistBlock) {
                    status=true;
                    itemCallBack.setStatusCode("0");
                    itemCallBack.setErrorMsg("Therapist " + staff.getDisplayName() + " is blocked! ");
                    isParameterError = true;
                    continue;
                }
                if (!userService.checkTherapistSkill(staff, productOption)) {
                    status=true;
                    itemCallBack.setStatusCode("0");
                    itemCallBack.setErrorMsg("[" + staff.getFullName() + "] cannot perform [" + bookItem.getProductName() + "]. Please choose another therapist.");
                    isParameterError = true;
                    continue;
                }

                isTerapistBlock = checkBlockTherapist(staff, blockTherapistSet, startAppointment.toDate(), endAppointment.toDate());
                if (isTerapistBlock) {
                    status=true;
                    itemCallBack.setStatusCode("0");
                    itemCallBack.setErrorMsg("Therapist " + staff.getDisplayName() + " is blocked! ");
                    isParameterError = true;
                    continue;
                }
                BookItemTherapist bookItemTherapist = new BookItemTherapist();
                bookItemTherapist.setBookItem(bookItem);
                bookItemTherapist.setUser(staff);



                bookItemTherapist.setOnRequest(isOnRequest);
                bookItem.getBookItemTherapists().add(bookItemTherapist);
                blockTherapistSet.add(new Block(staff, book.getAppointmentTime(), endAppointment.toDate()));
                blockTherapistIds.add(staff.getId());
            }


            if (StringUtils.isNotBlank(staffNames)){
                staffNames = staffNames.substring(0,staffNames.length()-1);
            }
            itemCallBack.setStaffName(staffNames);
            if (status){
                continue;
            }
            // 分配room
            List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), book.getShop().getId(), startAppointment.toDate(), endAppointment.toDate());
            logger.debug("Available rooms: {}", availableRoomList);
            if (availableRoomList == null || availableRoomList.size() == 0) {
                itemCallBack.setStatusCode("0");
                itemCallBack.setErrorMsg("No more room! ");
                isParameterError = true;
                continue;
            }
            availableRoomList = getNotBlockRoomList(availableRoomList, blockRoomSet, startAppointment.toDate(), endAppointment.toDate());
            if (availableRoomList == null || availableRoomList.size() == 0) {
                itemCallBack.setStatusCode("0");
                itemCallBack.setErrorMsg("No more room! ");
                isParameterError = true;
                continue;
            }
            Room assignRoom = RoomHelper.getAssignRoom(availableRoomList, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
            if (assignRoom == null) {
                itemCallBack.setStatusCode("0");
                itemCallBack.setErrorMsg("No more room! ");
                isParameterError = true;
                continue;
            }
            blockRoomSet.add(new Block(assignRoom, book.getAppointmentTime(), endAppointment.toDate()));
            bookItem.setRoom(assignRoom);

            bookItem.setBookItem(null);
            bookItemList.add(bookItem);


            if (!bookItem.isWaiting() && this.bookItemService.checkBlock(bookItem)) {
                itemCallBack.setStatusCode("0");
                itemCallBack.setErrorMsg("Timeslot is unavailable");
                isParameterError=true;
                continue;
            }

            itemCallBack.setStatusCode("1");

        }
        bookCallBack2.setBookingItems(bookingItemSucessCallBackVOList);
        bookCallBack2.setGuestNum(guestNum);
        if(isParameterError){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg",bookCallBack2);
        }

        book.setGuestAmount(guestNum);
        bookCallBack2.setTotalPrice(CommonConstant.CURRENCY_TYPE+totalPrice.toString());

        // 保存 book first step record
        int i=0;
        Set<BookFirstStepRecord> recordSet = book.getBookFirstStepRecords();
        for (BookItemRequestVO recordVO2 : bookRequestVO3.getBookItems()) {
            BookFirstStepRecord record = new BookFirstStepRecord();
            record.setBook(book);
            ProductOption productOption = productOptionService.get(recordVO2.getProductOptionId());

            record.setProductOption(productOption);
            record.setGuestAmount(1);
            record.setShareRoom(recordVO2.getShareRoom() != null && recordVO2.getShareRoom());
            Date startAppointmentDate = new Date(recordVO2.getTimestamp());
            DateTime endAppointment = new DateTime(startAppointmentDate).plusMinutes(productOption.getDuration() + productOption.getProcessTime());

            try {
                record.setStartTime(DateUtil.dateToString(startAppointmentDate, "HH:mm"));
                record.setEndTime(DateUtil.dateToString(endAppointment.toDate(), "HH:mm"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            record.setDisplayOrder(i++);
            record.setIsActive(true);
            record.setCreated(now);
            record.setCreatedBy(member.getUsername());
            record.setLastUpdated(now);
            record.setLastUpdatedBy(member.getUsername());
            recordSet.add(record);
        }
        saveOrUpdate(book);
        if (PropertiesUtil.getBooleanValueByName("BOOKING_NOTIFICATION")) {
        	sendBookingNotification(book, CommonConstant.SEND_BOOKING_NOTIFICATION_EMAIL);
        }
        getSession().flush();
        results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", bookCallBack2);


        return results;
        }catch (Exception e){
            Results results = Results.getInstance();
            return results.setCode(Results.CODE_SERVER_ERROR).addMessage("successMsg", e.getMessage());
        }

    }
    
    
    @Override
    public void saveBookingsByBatch(BookRequestVO bookRequestVO) {
    
    String bookStatus = CommonConstant.BOOK_STATUS_CONFIRM;
    try {
        
        Date now = new Date();
        User member = userService.get(bookRequestVO.getMemberId());
        Shop shop = shopService.get(bookRequestVO.getShopId());
        
        Book book = new Book();
        book.setShop(shop);

        book.setReference(RandomUtil.generateRandomNumberWithDate(null));

        book.setUser(member);
        book.setIsActive(true);
        book.setWalkIn(false);
        book.setRemarks(bookRequestVO.getRemarks());
        book.setStatus(bookStatus);
        book.setCompany(shop.getCompany());
        
        if(bookRequestVO.getBookBatch() !=null){
        	book.setBookBatch(bookRequestVO.getBookBatch());
        }
        book.setCreated(now);
        book.setCreatedBy(StringUtils.isNotBlank(bookRequestVO.getCreateBy()) ? bookRequestVO.getCreateBy() : member.getUsername());
        book.setLastUpdated(now);
        book.setLastUpdatedBy(StringUtils.isNotBlank(bookRequestVO.getCreateBy()) ? bookRequestVO.getCreateBy() : member.getUsername());
        
        book.setMobilePrepaid(false);
        book.setBookingChannel(StringUtils.isNotBlank(bookRequestVO.getBookingChannel()) ? bookRequestVO.getBookingChannel() : CommonConstant.BOOKING_CHANNEL_MOBILE);
        Date appointmentDate = bookRequestVO.getAppointmentDate();
        book.setAppointmentTime(appointmentDate);

        Set<BookItem> bookItemList = book.getBookItems();

        // 当前booking选择的block集合
        Set<Block> blockRoomSet = new HashSet<>();
        Set<Block> blockTherapistSet = new HashSet<>();
        Set<Long> blockTherapistIds = new HashSet<Long>();
        List<BookItemRequestVO> bookItemRequestVOs = bookRequestVO.getBookItems();
        //判断房间的名称是否一样，如果一样processTime只需要计算一次
//        comibeBookItemRequestVo(bookItemRequestVOs);

        
        Integer guestNum = bookRequestVO.getGuestNum() !=null ? bookRequestVO.getGuestNum() :0;
        book.setGuestAmount(guestNum);

        Double totalPrice = 0d;
        for (BookItemRequestVO itemRequestVO : bookItemRequestVOs) {
        	System.out.println("--Booking serviceimpl-------bookItemRequestVOs---size ---"+bookItemRequestVOs.size());
        	BookItem bookItem = new BookItem();
            ProductOption productOption = productOptionService.get(itemRequestVO.getProductOptionId());

            Integer duration = productOption.getDuration();
            Double price = productOption.getFinalPrice(book.getShop().getId());
            // bookItem
            bookItem.setBook(book);
            bookItem.setStatus(CommonConstant.BOOK_STATUS_CONFIRM); // 设置状态

            Date startAppointmentDate = new Date(itemRequestVO.getTimestamp());
            DateTime startAppointment = new DateTime(startAppointmentDate);

            DateTime endAppointment = new DateTime(itemRequestVO.getEndAppointmentTime());
            // 时间超过店的关门时间
            OpeningHours openingHours = shop.getOpeningHour(startAppointment.toDate());
            DateTime openTime = openingHours.getOpenTimeObj();
            DateTime closeTime = openingHours.getCloseTimeObj(); // 关门时间
            if (startAppointment.isBefore(openTime) || endAppointment.isAfter(closeTime)) {
            	bookItem.setStatus(CommonConstant.BOOK_STATUS_WAITING);
            }
            bookItem.setAppointmentTime(startAppointment.toDate());//start time
            bookItem.setAppointmentEndTime(endAppointment.toDate());//end time

            bookItem.setDuration(duration);
            bookItem.setProcessTime(productOption.getProcessTime());

            bookItem.setPrice(price);
            totalPrice += price;
            
            bookItem.setProductOption(productOption);
            bookItem.setIsActive(true);
            bookItem.setCreated(book.getCreated());
            bookItem.setCreatedBy(book.getCreatedBy());
            bookItem.setIsDoubleBooking(false);
            bookItem.setLastUpdated(book.getCreated());
            bookItem.setLastUpdatedBy(book.getCreatedBy());

            bookItem.setProductName(bookItem.getProdNameWithFinalPrice());

            // 设置技师
            String therapistIdsStr=itemRequestVO.getTherapistIds();

            String[] therapistIds=therapistIdsStr.split(",");
            List<String> selectedTherapistList = new ArrayList<>(therapistIds.length);
            Collections.addAll(selectedTherapistList,therapistIds);
            Integer capacity=productOption.getCapacity();
            if (selectedTherapistList.size()<capacity){
                Integer size=capacity-therapistIds.length;
                for (int i=0;i<size;i++){
                	selectedTherapistList.add(CommonConstant.anyTherapist);
                }
            }
            for (int i=0;i<selectedTherapistList.size();i++) {
            	System.out.println("--Booking serviceimpl---line 953-----selectedTherapistList---size ---"+selectedTherapistList.size());
                User selectedTherapist = null;
                Boolean isOnRequest = true;
                List<User> avaliableTherapistList = getAvaliableTherapist(shop, productOption, itemRequestVO.getTimestamp(), null);
                //
                if (avaliableTherapistList != null && avaliableTherapistList.size() > 0) {
                    if (CommonConstant.anyTherapist.equals(selectedTherapistList.get(i))) {
                        for (User avaliableTherapist : avaliableTherapistList) {
                            if (!blockTherapistIds.contains(avaliableTherapist.getId()) &&!selectedTherapistList.contains(String.valueOf(avaliableTherapist.getId()))) {
                            	selectedTherapist = avaliableTherapist;
                                isOnRequest = false;
                                break;
                            }
                        }
                    } else {
                    	selectedTherapist = userService.get(Long.valueOf(selectedTherapistList.get(i)));
                        if (selectedTherapist == null ) {
                        	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
                        }else{
                        	boolean isContain = false;
                            for (User avaliableTherapist : avaliableTherapistList) {
                                if (avaliableTherapist.getId().longValue() == selectedTherapist.getId().longValue()) {
                                    isContain = true;
                                    break;
                                }
                            }
                            if (!isContain) {
                            	// therapist is blocked
                            	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
                            }
                        }
                    }
                }else{
                	
                	selectedTherapist = userService.get(Long.valueOf(selectedTherapistList.get(i)));
                	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
                }
               
                if (selectedTherapist!=null){
                	System.out.println("--Booking serviceimpl---line 987-----selectedTherapist---id ---"+selectedTherapist.getId());
                	
                    bookItem.setOnRequest(isOnRequest);
	                boolean isTerapistBlock = blockService.checkBlock(book.getShop(), selectedTherapist, startAppointment.toDate(), endAppointment.toDate());
	                if (isTerapistBlock) {
	                	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
	                }
	                if (!userService.checkTherapistSkill(selectedTherapist, productOption)) {
	                	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
	                }
	
	                isTerapistBlock = checkBlockTherapist(selectedTherapist, blockTherapistSet, startAppointment.toDate(), endAppointment.toDate());
	                if (isTerapistBlock) {
	                	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
	                }
	                BookItemTherapist bookItemTherapist = new BookItemTherapist();
	                bookItemTherapist.setBookItem(bookItem);
	                bookItemTherapist.setUser(selectedTherapist);
	                
	                bookItemTherapist.setOnRequest(isOnRequest);
	                bookItem.getBookItemTherapists().add(bookItemTherapist);
	                blockTherapistSet.add(new Block(selectedTherapist, book.getAppointmentTime(), endAppointment.toDate()));
	                blockTherapistIds.add(selectedTherapist.getId());
	                
	            }else{
	            	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
	            }
                
	            // 分配room
	            List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), book.getShop().getId(), startAppointment.toDate(), endAppointment.toDate());
	            logger.debug("Available rooms: {}", availableRoomList);
	            if (availableRoomList == null || availableRoomList.size() == 0) {
	            	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
	            }else{
		            availableRoomList = getNotBlockRoomList(availableRoomList, blockRoomSet, startAppointment.toDate(), endAppointment.toDate());
		            if (availableRoomList == null || availableRoomList.size() == 0) {
		            	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
		            }else{
			            Room assignRoom = RoomHelper.getAssignRoom(availableRoomList, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
			            if (assignRoom == null) {
			            	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
			            }else{
				            blockRoomSet.add(new Block(assignRoom, book.getAppointmentTime(), endAppointment.toDate()));
				            bookItem.setRoom(assignRoom);
				            bookItem.setBookItem(null);
				            bookItemList.add(bookItem);
			            }
		            }
	            }
	            if (!bookItem.isWaiting() && this.bookItemService.checkBlock(bookItem)) {
	            	bookStatus=CommonConstant.BOOK_STATUS_WAITING;
	            }
            }
            bookItem.setStatus(bookStatus);
        }
        
        book.setGuestAmount(guestNum);
        // 保存 book first step record
        int i=0;
        Set<BookFirstStepRecord> recordSet = book.getBookFirstStepRecords();
        for (BookItemRequestVO recordVO2 : bookRequestVO.getBookItems()) {
            BookFirstStepRecord record = new BookFirstStepRecord();
            record.setBook(book);
            ProductOption productOption = productOptionService.get(recordVO2.getProductOptionId());

            record.setProductOption(productOption);
            record.setGuestAmount(1);
            record.setShareRoom(recordVO2.getShareRoom() != null && recordVO2.getShareRoom());
            Date startAppointmentDate = new Date(recordVO2.getTimestamp());
            DateTime endAppointment = new DateTime(startAppointmentDate).plusMinutes(productOption.getDuration() + productOption.getProcessTime());

            try {
                record.setStartTime(DateUtil.dateToString(startAppointmentDate, "HH:mm"));
                record.setEndTime(DateUtil.dateToString(endAppointment.toDate(), "HH:mm"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            record.setDisplayOrder(i++);
            record.setIsActive(true);
            record.setCreated(now);
            record.setCreatedBy(member.getUsername());
            record.setLastUpdated(now);
            record.setLastUpdatedBy(member.getUsername());
            recordSet.add(record);
        }
        saveOrUpdate(book);
        
        if (PropertiesUtil.getBooleanValueByName("BOOKING_NOTIFICATION") && bookRequestVO.getSendBookingConfirmation()) {
        	sendBookingNotification(book, CommonConstant.SEND_BOOKING_NOTIFICATION_EMAIL);
        }
        getSession().flush();
    }catch (Exception e){
    	 e.printStackTrace();
    }
}

    @Override
    public void sendBookingNotification(Book book, String bookTemplate) {

        String urlRoot = WebThreadLocal.getUrlRoot();
        User user = book.getUser();
        Guest guest = book.getGuest();
        String userName = CommonSendEmailThread.class.getSimpleName();
        DateTime dateTime = new DateTime().plusSeconds(EMAIL_BOOKING_DELAY_START);
        Map<String, Object> parameterMap = new HashMap<>();

        parameterMap.put("urlRoot", urlRoot);
        parameterMap.put("book", book);
        parameterMap.put("user", user);
        Boolean isSend= false;
        if (book.getUser().isMember() && StringUtils.isNoneBlank(book.getUser().getEmail())) {
        	isSend =true;
            parameterMap.put("emailAddress", new EmailAddress(user.getEmail(), user.getFullName()));
        } else {
            if (StringUtils.isNoneBlank(book.getGuest().getEmail())) {
            	isSend =true;
                parameterMap.put("emailAddress", new EmailAddress(guest.getEmail(), guest.getFullName()));
            }
        }

        if (isSend) {
            MktMailShot mktMailShot = mktMailShotService.saveCommonMktMailShot(bookTemplate, user, WebThreadLocal.getCompany());
            EmailTemplate emailTemplate = EmailTemplate.getInstance(mktMailShot);
            emailTemplate.setContentData(parameterMap);
            // job 运行参数
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("user", user);
            jobDataMap.put("mktMailShot", mktMailShot);
            jobDataMap.put("emailTemplate", emailTemplate);
            jobDataMap.put("executor", userName);
            jobDataMap.put("emailAddress", parameterMap.get("emailAddress"));
            EmailJob.scheduleJob(dateTime.toDate(), jobDataMap, mktMailShot.getType());
        }
    }

    @Override
    public void sendBookingReminderNotification(User user, Date startDate, Date endDate, String bookTemplate) {

        List<BookItem> bookItems2 = bookItemService.getBookItemsByDateAndMember(startDate, endDate, user.getId());
        List<BookItemVO> bookItemVOS = null;
        List<BookVO> bookVOS = new ArrayList<>();

        BookVO bookVO = null;
        BookItemVO bookItemVO = null;
        for (BookItem bi : bookItems2) {
            bookItemVO = new BookItemVO();
            bookVO = new BookVO();
            bookItemVOS = new ArrayList<>();

            //  set value into bookVO
            bookVO.setAppointmentDateFormat(bi.getBook().getAppointmentDateFormat());
            bookVO.setBookShopName(bi.getBook().getShop().getName());

            //  set value into bookItemVO
            bookItemVO.setBookVoByBook(bi);
            bookItemVOS.add(bookItemVO);
            bookVO.setBookItemVOs(bookItemVOS);

            bookVOS.add(bookVO);
        }

        String urlRoot = WebThreadLocal.getUrlRoot();
        String userName = CommonSendEmailThread.class.getSimpleName();
        DateTime dateTime = new DateTime().plusSeconds(EMAIL_BOOKING_DELAY_START);
        Map<String, Object> parameterMap = new HashMap<>();

        parameterMap.put("urlRoot", urlRoot);
        if (user.isMember() && StringUtils.isNoneBlank(user.getEmail())) {
            parameterMap.put("books", bookVOS);
            parameterMap.put("user", user);
            parameterMap.put("emailAddress", new EmailAddress(user.getEmail(), user.getFullName()));
            sendEmail(user, bookTemplate, dateTime, parameterMap, userName);
            
        } else {// user is a guest,user id is 2.
        	
        	Map<String,List<BookVO>> guestBookItemMap = new HashMap<String,List<BookVO>>();
            List<BookVO> bVOS = null;
            for (BookItem bi : bookItems2) {
                if(bi.getBook().getGuest()==null || StringUtils.isBlank(bi.getBook().getGuest().getEmail()) ){
                    continue;
                }
                String email = bi.getBook().getGuest().getEmail();
                bookItemVO = new BookItemVO();
                bookVO = new BookVO();
                bookItemVOS = new ArrayList<>();
                bVOS = new ArrayList<>();

                bookVO.setAppointmentDateFormat(bi.getBook().getAppointmentDateFormat());
                bookVO.setBookShopName(bi.getBook().getShop().getName());

                bookItemVO.setBookVoByBook(bi);
                bookItemVOS.add(bookItemVO);

                bookVO.setBookItemVOs(bookItemVOS);
                if(guestBookItemMap.get(email) == null){
                    bVOS.add(bookVO);
                    guestBookItemMap.put(email, bVOS);
                }else{
                    bVOS = guestBookItemMap.get(email);
                    bVOS.add(bookVO);
                    guestBookItemMap.put(email, bVOS);
                }
            }
            Set<String> emailSet = guestBookItemMap.keySet();
            Iterator<String> emailIt =emailSet.iterator();
            while(emailIt.hasNext()){
            	String emailAddr = emailIt.next();
            	if(guestBookItemMap.get(emailAddr) == null){
            		continue;
            	}
                parameterMap.put("user", user);
            	parameterMap.put("username",emailAddr);
                parameterMap.put("books", guestBookItemMap.get(emailAddr));
                parameterMap.put("emailAddress", new EmailAddress(emailAddr,emailAddr));
                sendEmail(user, bookTemplate, dateTime, parameterMap, userName);
            }
        }
    }

	private void sendEmail(User user,String bookTemplate,DateTime dateTime,Map<String, Object> parameterMap,String userName){
		MktMailShot mktMailShot = mktMailShotService.saveCommonMktMailShot(bookTemplate, user);
	    EmailTemplate emailTemplate = EmailTemplate.getInstance(mktMailShot);
	    emailTemplate.setContentData(parameterMap);
	    // job 运行参数
	    JobDataMap jobDataMap = new JobDataMap();
	    jobDataMap.put("mktMailShot", mktMailShot);
	    jobDataMap.put("emailTemplate", emailTemplate);
	    jobDataMap.put("executor", userName);
	    jobDataMap.put("emailAddress", parameterMap.get("emailAddress"));
	    jobDataMap.put("user", user);
	    EmailJob.scheduleJob(dateTime.toDate(), jobDataMap, mktMailShot.getType());
	}
    @Override
    public void updateMobilePrepaid(Long bookId) {
        Book book = get(bookId);
        if (book == null) {
            return;
        }
            book.setMobilePrepaid(true);
            Date now = new Date();
            book.setLastUpdated(now);
            book.setLastUpdatedBy( WebThreadLocal.getUser().getUsername());
            saveOrUpdate(book);
    }

    /**
     * create by rick --2018.9.4
     *
     * 同一个房间的treatment只需要一个processTime；
     * @param bookItemVOs
     */
    public List<List<BookItemVO>> comibeBookItemVo(List<BookItemVO> bookItemVOs)
    {
        List bookItemVoNew = new ArrayList();
        if(bookItemVOs != null ||bookItemVOs.size() > 1)
        {
            for (int i = 0; i < bookItemVOs.size() - 1; i++)
            {
                List<Integer> integers = null;
                boolean b = true;
                int isFirst = 0;
                for (int j = i + 1; j <  bookItemVOs.size();j++)
                {

                    Long roomId = bookItemVOs.get(i).getRoomId();
                    Long roomId1 = bookItemVOs.get(j).getRoomId();
                    System.out.println(roomId);
                    System.out.println(roomId1);
                    System.out.println(roomId == roomId1);
                    if(bookItemVOs.get(i).getRoomId() == bookItemVOs.get(j).getRoomId())
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
                    List<BookItemVO> bookItemVOS = new ArrayList<>();
                    bookItemVOS.add(bookItemVOs.get(i));
                    bookItemVoNew.add(bookItemVOS);
                    bookItemVOs.remove(i);
                    b = true;
                    isFirst = 0;
                }
                else
                {
                    List<BookItemVO> bookItemVOS = new ArrayList<>();
                    for (Integer integer:integers)
                    {
                        bookItemVOS.add(bookItemVOs.get(integer));
                    }
                    for (int x = 0;i < integers.size();i++)
                    {
                        bookItemVOs.remove(0);
                    }
                    bookItemVoNew.add(bookItemVOS);
                    b = true;
                    isFirst = 0;
                }
                i = -1;
            }

            if(bookItemVOs.size() == 1)
            {
                List<BookItemVO> bookItemVOS = new ArrayList<>();
                bookItemVOS.add(bookItemVOs.get(0));
                bookItemVoNew.add(bookItemVOS);
                bookItemVOs.remove(0);
            }
        }
        return bookItemVoNew;
    }

    //时间处理
    private List<BookItemVO> timeDealWith(List<List<BookItemVO>> list)
    {
        List<BookItemVO> newBookItemVo = new ArrayList<>();
        //对组的时间进行一个排序，符合条件的进行时间处理
        List< List<BookItemVO>> sortList = new ArrayList<>();
        BookItemVO bookItemVO;
        for(List<BookItemVO> newGroupList: list)
        {
            if(newGroupList.size() > 1)
            {
                BookItemVO[] itemVOS = newGroupList.toArray(new BookItemVO[newGroupList.size()]);
                for(int i = 0;i < newGroupList.size() - 1;i++)
                {
                    for(int j = i + 1;j < newGroupList.size();j++)
                    {
                        if(itemVOS[i].getAppointmentTime().getTime() > itemVOS[j].getAppointmentTime().getTime())
                        {
                            bookItemVO = itemVOS[i];
                            itemVOS[i] = itemVOS[j];
                            itemVOS[j] = bookItemVO;
                        }
                    }
                }
                List<BookItemVO> bookItemVOList = Arrays.asList(itemVOS);
                sortList.add(bookItemVOList);
            }
            else
            {
                sortList.add(newGroupList);
            }
        }
        for (List<BookItemVO> groupList:sortList)
        {
            //大于1的组合才进行判断是否时间相连；
            if(groupList.size() > 1)
            {
                //封装所有时间相连的组合
                List<List<Integer>> integerList = new ArrayList<>();
                List<Integer> integers = null;
                Boolean flag = true;
                int isFirst = 0;
                for (int i = 0;i < groupList.size() - 1; i++)
                {
                    if(groupList.get(i).getEndAppointmentTime().getTime() == groupList.get(i + 1).getAppointmentTime().getTime())
                    {
                        if(flag)
                        {
                            integers = new ArrayList<>();
                        }
                        flag = false;
                        if(isFirst == 0)
                        {
                            integers.add(i);
                            integers.add(i + 1);
                            isFirst++;
                        }
                        else
                        {
                            integers.add(i + 1);
                        }

                        if(i == (groupList.size() - 2))
                        {
                            integerList.add(integers);
                        }

                    }
                    else
                    {
                        if(integers != null && integers.size() > 0)
                        {
                            integerList.add(integers);
                            integers = null;
                            isFirst = 0;
                            flag = true;
                        }
                    }

                }
                //进行时间的处理
                if(integerList.size() > 0)
                {
                    for (List<Integer> indexList : integerList)
                    {
                        Integer maxProcessTime = 0;
                        Integer processTime = 0;
                        for(int i = 0;i < indexList.size();i++)
                        {
                            processTime += productOptionService.get(groupList.get(i).getProductOptionId()).getProcessTime();
                            Integer newTime = productOptionService.get(groupList.get(i).getProductOptionId()).getProcessTime();
                            if(newTime > maxProcessTime)
                            {
                                maxProcessTime = newTime;
                            }
                            BookItemVO bookItemVO1 = groupList.get(indexList.get(i));
                            if(i == 0)
                            {
                                bookItemVO1.setEndAppointmentTime(new Date(bookItemVO1.getEndAppointmentTime().getTime() - processTime*1000*60));
                            }
                            else
                            {
                                Integer ownTime = productOptionService.get(groupList.get(i).getProductOptionId()).getProcessTime();
                                if(i == (indexList.size() - 1))
                                {

                                    bookItemVO1.setAppointmentTime(new Date(bookItemVO1.getAppointmentTime().getTime() - processTime*1000*60 + ownTime*1000*60));
                                    bookItemVO1.setEndAppointmentTime(new Date(bookItemVO1.getEndAppointmentTime().getTime() - processTime*1000*60 + maxProcessTime*1000*60));
                                }
                                else
                                {
                                    bookItemVO1.setAppointmentTime(new Date(bookItemVO1.getAppointmentTime().getTime() - processTime*1000*60 + ownTime*1000*60));
                                    bookItemVO1.setEndAppointmentTime(new Date(bookItemVO1.getEndAppointmentTime().getTime() - processTime*1000*60));
                                }

                            }
                        }
                    }
                }
            }
            newBookItemVo.addAll(groupList);
        }

        return newBookItemVo;
    }
}
