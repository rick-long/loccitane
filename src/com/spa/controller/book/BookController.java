package com.spa.controller.book;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.spa.model.awardRedemption.AwardRedemptionTransaction;
import org.spa.model.book.Block;
import org.spa.model.book.Book;
import org.spa.model.book.BookFirstStepRecord;
import org.spa.model.book.BookItem;
import org.spa.model.book.BookItemTherapist;
import org.spa.model.bundle.ProductBundle;
import org.spa.model.company.Company;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.model.product.ProductOptionSupernumeraryPrice;
import org.spa.model.shop.OpeningHours;
import org.spa.model.shop.Room;
import org.spa.model.shop.RoomTreatments;
import org.spa.model.shop.Shop;
import org.spa.model.user.ConsentForm;
import org.spa.model.user.Guest;
import org.spa.model.user.User;
import org.spa.model.user.UserFamilyDetails;
import org.spa.service.shop.RoomTreatmentService;
import org.spa.serviceImpl.loyalty.UserLoyaltyLevelServiceImpl;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.utils.PDFUtil;
import org.spa.utils.PropertiesUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.ServletUtil;
import org.spa.utils.SpringUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.book.BlockVO;
import org.spa.vo.book.BookFirstStepRecordVO;
import org.spa.vo.book.BookItemEditVO;
import org.spa.vo.book.BookItemVO;
import org.spa.vo.book.BookListVO;
import org.spa.vo.book.BookQuickVO;
import org.spa.vo.book.BookVO;
import org.spa.vo.book.CellVO;
import org.spa.vo.book.DoubleBookingVO;
import org.spa.vo.book.RequestTherapistVO;
import org.spa.vo.book.ViewVO;
import org.spa.vo.common.DateTimeRangeVO;
import org.spa.vo.common.SelectOptionVO;
import org.spa.vo.page.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.helper.RoomHelper;
import com.spa.runtime.exception.ResourceException;

import freemarker.template.utility.StringUtil;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ivy on 2016/03/28.
 */
@Controller
@RequestMapping("book")
public class BookController extends BaseController {

    public static final String BOOK_FIRST_STEP_RECORDS = "BOOK_FIRST_STEP_RECORDS";
    
    public static final String PRINT_THERAPIST_BOOK_TIME_VIEW_URL="/book/bookTimeTherapistViewTemplate";

    @RequestMapping("toView")
    public String management(Model model,String flag, BookListVO bookListVO) {
    	model.addAttribute("shopList",shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
    	model.addAttribute("toDate",new Date());
    	model.addAttribute("fromDate",new Date());
    	if (StringUtils.isNotBlank(flag)) {
            model.addAttribute("flag",flag);
        }

        return "book/bookManagement";
    }

    @RequestMapping("list")
    public String list(Model model,  BookListVO bookListVO) {
        Long memberId = bookListVO.getMemberId();
        String status = bookListVO.getStatus();
        String treatmentName = bookListVO.getTreatmentName();
        String therapistName = bookListVO.getTherapistName();
        String fromDate = bookListVO.getFromDate();
        String toDate=bookListVO.getToDate();
        
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Book.class);
        detachedCriteria.createAlias("bookItems", "bi");
        
        if(bookListVO.getShopId() !=null && bookListVO.getShopId().longValue()>0){
        	detachedCriteria.add(Restrictions.eq("shop.id", bookListVO.getShopId()));
        }else{
        	detachedCriteria.add(Restrictions.in("shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
        }
        
        //bookItemTherapists
        if (memberId != null) {
            detachedCriteria.add(Restrictions.eq("user.id", memberId));
        }
        if (StringUtils.isNotBlank(status)) {
            detachedCriteria.add(Restrictions.eq("bi.status", status));
        }

        if(StringUtils.isNotBlank(treatmentName)) {
            detachedCriteria.createAlias("bi.productOption", "productOption");
            detachedCriteria.createAlias("productOption.product", "product");
            detachedCriteria.add(Restrictions.like("product.name", treatmentName.trim(), MatchMode.START));
        }

        if (StringUtils.isNotBlank(fromDate)) {
            detachedCriteria.add(Restrictions.ge("appointmentTime", DateUtil.getFirstMinuts(DateUtil.stringToDate(fromDate, "yyyy-MM-dd"))));
        }
        if(StringUtils.isNotBlank(toDate)){
        	 detachedCriteria.add(Restrictions.le("appointmentTime", DateUtil.getLastMinuts(DateUtil.stringToDate(toDate, "yyyy-MM-dd"))));
        }

        if (StringUtils.isNotBlank(therapistName)) {
            detachedCriteria.createAlias("bi.bookItemTherapists", "therapists");
            detachedCriteria.createAlias("therapists.user", "therapist");
            String trimName = therapistName.trim();
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("therapist.username", trimName, MatchMode.START));
            disjunction.add(Restrictions.like("therapist.firstName", trimName, MatchMode.START));
            disjunction.add(Restrictions.like("therapist.lastName", trimName, MatchMode.START));
            disjunction.add(Restrictions.like("therapist.fullName", trimName, MatchMode.START));
            disjunction.add(Restrictions.like("therapist.displayName", trimName, MatchMode.START));
            disjunction.add(Restrictions.like("therapist.email", trimName, MatchMode.START));
            detachedCriteria.add(disjunction);
        }

        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.addOrder(Order.desc("id"));
        Page<Book> page = bookService.list(detachedCriteria, bookListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(bookListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "book/bookList";
    }

    @RequestMapping("toAdd")
    public String toAdd(Model model, BookVO bookVO, Long bundleGroup1, Long bundleGroup2) {
    	List<Shop> shopList = shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false);
        model.addAttribute("shopList", shopList);
        bookVO.setState(CommonConstant.STATE_ADD);
        Date startAppointmentTime = bookVO.getStartAppointmentTime();
        if (startAppointmentTime == null) {
            bookVO.setStartAppointmentTime(new Date());
        }
        String startTime = bookVO.getStartTimeString();

        if (StringUtils.isBlank(startTime)) {
//            bookVO.setStartTimeString(new DateTime(bookVO.getStartAppointmentTime()).toString("HH") + ":00");
            Shop shop = null;
            if(Objects.nonNull(shopList) && 0 < shopList.size()) {
            	shop = shopList.get(0);
            	DateTime currentTime = new DateTime();
                OpeningHours openingHours = shop.getOpeningHour(currentTime.toDate());
                Long startTimeMillis = null;
                long openTimeMillis = openingHours.getOpenTimeObj().getMillis();
                if(currentTime.getMillis() <= openTimeMillis) {
                	startTimeMillis = openTimeMillis;
                }else {
                	startTimeMillis = currentTime.getMillis(); 
                }
                // 去掉秒数
                startTimeMillis = startTimeMillis - startTimeMillis % 60000;
                DateTime dateTime = new DateTime(startTimeMillis);
                Integer min = dateTime.getMinuteOfHour();
                Integer differ = min % 5;
                if(!Objects.equals(0, differ)) {
                	dateTime = dateTime.plusMinutes(5 - differ);
                }
                bookVO.setStartTimeString(dateTime.toString("HH:mm"));
            }
        }
        model.addAttribute("timeTreeData", CommonConstant.TIME_DATA);
        model.addAttribute("bookVO", bookVO);
        return "book/bookAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm saveOrUpdate(@Valid BookVO bookVO, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        }
        // create by william -- 2018-8-11
        try {
            bookVO.setFirstStepRecordVOList((List<BookFirstStepRecordVO>) session.getAttribute(BOOK_FIRST_STEP_RECORDS));
            Book book = bookService.add(bookVO);
            if (PropertiesUtil.getBooleanValueByName("BOOKING_NOTIFICATION")) {
            	 bookService.sendBookingNotification(book, CommonConstant.SEND_BOOKING_NOTIFICATION_EMAIL);
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }
//        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.attendance.thank.you")+bookVO.getEmail());
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
    }

    @RequestMapping("cancel")
    @ResponseBody
    public AjaxForm cancel(Long bookId) {
        if (bookId != null) {
            bookService.cancel(bookId);
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.cancel.successfully"));
    }

    @RequestMapping("updateBookItemStatus")
    @ResponseBody
    public AjaxForm updateBookItemStatus(Long bookItemId, String status) {
        if (bookItemId != null) {
            bookItemService.updateStatus(bookItemId, status);
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.update.successfully"));
    }

    @RequestMapping("updateAllBookITemStatus")
    @ResponseBody
    public AjaxForm updateAllBookITemStatus(Long bookId, String status) {
        if (bookId != null) {
            bookItemService.updateAllStatus(bookId, status);
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.update.successfully"));
    }

    /**
     ** * 在 start 到 end 的时间范围内，把 roomList里剔除被 blockRoomSet包含的room, 返回没有被block的room集合
     *
     * @param roomList
     * @param blockRoomSet
     * @param start
     * @param end
     * @return
     */
    private List<Room> getNotBlockRoomList(List<Room> roomList, Set<Block> blockRoomSet, Date start, Date end) {
        List<Room> notBlockRoomList = new ArrayList<>();
        for (Room room : roomList) {
            boolean isBlock = false;
            for (Block block : blockRoomSet) {
                if (block.getRoom().getId().equals(room.getId())){
                    isBlock = isBlock || DateUtil.overlaps(start, end, block.getStartDate(), block.getEndDate());
                }
            }
            if (!isBlock) {
                notBlockRoomList.add(room);
            }
        }
        return notBlockRoomList;
    }

    /**
     **
     * @param roomList
     * @param
     * @param
     * @param
     * @return
     */
    private List<Room> getAvailableRooms(Set<Room> roomList, Map<Long,Set<String>> blockRoomMap, String startTime, String endTime) {
        List<Room> tempRoomList = new ArrayList<>(roomList);
        DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern("HH:mm");
        Iterator<Room> iterators = tempRoomList.iterator();
        while (iterators.hasNext()) {
			Room room = iterators.next();
        	Set<String> strLists = blockRoomMap.get(room.getId());
        	if(Objects.nonNull(strLists)) {
	        	for(String str : strLists) {
	        		String[] timeArr = str.split("~");
	        		String headTime = timeArr[0];
	        		String tailTime = timeArr[1];
	        		// 如果时间相交错,则房间已被占用,移除,跳出当前循环
	        		if(DateUtil.overlaps(formatter.parseDateTime(headTime), formatter.parseDateTime(tailTime), 
	        				formatter.parseDateTime(startTime), formatter.parseDateTime(endTime))) {
	        			iterators.remove();
	        			break;
	        		}
	        	}
        	}
		}

        Collections.sort(tempRoomList, new Comparator<Room>() {
            @Override
            public int compare(Room r1, Room r2) {
                return r2.getSort().compareTo(r1.getSort());
            }
        });

        return tempRoomList;
    }
    private Block addBlockRoom(Room room, Date start, Date end) {
        Block block = new Block();
        block.setRoom(room);
        block.setStartDate(start);
        block.setEndDate(end);
        return block;
    }

    @RequestMapping("getAvailableTherapistList")
    @ResponseBody
    public List<SelectOptionVO> getAvailableTherapistList(BookVO bookVO) {
        BookItemVO assignTherapistItemVO = null;
        List<BookItemVO> bookItemVOs = bookVO.getBookItemVOs();
        Set<Block> blockTherapistSet = new HashSet<>();
        Set<BookItem> excludeBookItems = new HashSet<>();
        if(bookVO.getId() != null) {
            excludeBookItems.addAll(bookService.get(bookVO.getId()).getBookItems());
        }
        Long currentSelectTherapist = null;
        for (BookItemVO bookItemVO : bookItemVOs) {
            for (RequestTherapistVO requestTherapistVO : bookItemVO.getRequestTherapistVOs()) {
                if (requestTherapistVO.getTherapistId() != null) {
                    Block block = new Block();
                    block.setUser(userService.get(requestTherapistVO.getTherapistId()));
                    Date start = bookItemVO.getAppointmentTime();
                    block.setStartDate(start);
                    ProductOption productOption = productOptionService.get(bookItemVO.getProductOptionId());
//                    block.setEndDate(new DateTime(start).plusMinutes(productOption.getDuration() + productOption.getProcessTime()).toDate());
                    block.setEndDate(new DateTime(bookItemVO.getEndAppointmentTime()).plusMinutes(productOption.getProcessTime()).toDate());
                    blockTherapistSet.add(block); // 记录block的集合
                }
                if (requestTherapistVO.getCurrentRequest() != null && requestTherapistVO.getCurrentRequest()) {
                    assignTherapistItemVO = bookItemVO;
                    currentSelectTherapist = requestTherapistVO.getTherapistId();
                }
            }
        }
        if (assignTherapistItemVO == null) {
            throw new RuntimeException("Parameter Error");
        }

        // 可用的 therapistList
        Long shopId = bookVO.getShopId();
        Shop shop = shopService.get(shopId);
        ProductOption productOption = productOptionService.get(assignTherapistItemVO.getProductOptionId());
        Date startTime = assignTherapistItemVO.getAppointmentTime();
//        Date endTime = new DateTime(startTime).plusMinutes(productOption.getDuration() + productOption.getProcessTime()).toDate(); // end appointment time
        Date endTime = new DateTime(assignTherapistItemVO.getEndAppointmentTime()).plusMinutes(productOption.getProcessTime()).toDate(); // end appointment time
        List<User> allTherapists = userService.getTherapistsBySkill(shopId, productOption,null);
        Set<User> blockedTherapists = new HashSet<>();   // 不可用的技师集合
        Iterator<User> userIterator = allTherapists.iterator();
        boolean isBlock;
        while (userIterator.hasNext()) {
            isBlock = false;
            User user = userIterator.next();
            if (user.getId().equals(currentSelectTherapist)) {
                continue; // 当前选择的therapist，不用排除block
            }
            // 被选择block
            for (Block block : blockTherapistSet) {
                // 同一个therapist， 时间冲突
                if (block.getUser().getId().equals(user.getId()) && DateUtil.overlaps(startTime, endTime, block.getStartDate(), block.getEndDate())) {
                    blockedTherapists.add(user);
                    userIterator.remove();
                    isBlock = true;
                    break;
                }
            }
            if (isBlock) {
                continue;
            }
            if (bookItemService.checkBlock(user, startTime, endTime, excludeBookItems)) {
                blockedTherapists.add(user);
                userIterator.remove();
                isBlock = true;
            }
            if (isBlock) {
                continue;
            }
            if (blockService.checkBlock(shop, user, startTime, endTime)) {
                blockedTherapists.add(user);
                userIterator.remove();
            }
        }
        User member = null;
        List<User> preferredTherapists = null;
        if(Objects.nonNull(bookVO.getMemberId())) {
        	member = userService.get(bookVO.getMemberId());
        	preferredTherapists = member.getPreferredTherapists();
        }
        // 可用的技师集合
        List<SelectOptionVO> selectOptionVOList = new ArrayList<>();
        Set<User> notBlockTherapists = new HashSet<>(allTherapists);
        selectOptionVOList.add(new SelectOptionVO("Available Therapist"));
        // 有喜欢的技师,先把喜欢的技师排在前面
        if(Objects.nonNull(preferredTherapists) && 0 < preferredTherapists.size()) {
        	Iterator<User> iterators = preferredTherapists.iterator();
        	while (iterators.hasNext()) {
				User u = iterators.next();
				Iterator<User> notBlockIterators = notBlockTherapists.iterator();
	        	for (;notBlockIterators.hasNext();) {
	        		User user2 = notBlockIterators.next();
					if(Objects.equals(u.getId(), user2.getId())){
						selectOptionVOList.add(new SelectOptionVO("NOT_BLOCKED", user2.getId(), user2.getDisplayName()));
						notBlockIterators.remove();
					}
				}
			}
        }
//        System.out.println("notBlockTherapists:" + notBlockTherapists.size());
        selectOptionVOList.addAll(notBlockTherapists.stream().map(user -> new SelectOptionVO("NOT_BLOCKED", user.getId(), user.getDisplayName())).sorted((e1, e2) -> e1.getLabel().compareTo(e2.getLabel())).collect(Collectors.toList()));
        selectOptionVOList.add(new SelectOptionVO("Not Available Therapists"));
        selectOptionVOList.addAll(blockedTherapists.stream().map(user -> new SelectOptionVO("BLOCKED", user.getId(), user.getDisplayName())).sorted((e1, e2) -> e1.getLabel().compareTo(e2.getLabel())).collect(Collectors.toList()));
        return selectOptionVOList;
    }

    /*private boolean overlaps(Date startTime, Date endTime, Block block) {
        System.out.println("startTime:" + startTime + ",endTime:" + endTime);
        System.out.println("blockStart:" + block.getStartDate() + ",blockEnd:" + block.getEndDate());
        if (startTime.before(block.getEndDate()) && endTime.after(block.getStartDate())) {
            return true;
        }
        // 临界点判断
        //return !(startTime.equals(block.getEndDate()) || endTime.equals(block.getStartDate()));
        return false;
    }*/

    @RequestMapping("selectResource")
    public String selectResource(BookVO bookVO, Model model, HttpSession session) {
        List<BookItemVO> bookItemVOs = bookVO.getBookItemVOs();
        DateTime appointDate = new DateTime(bookVO.getStartAppointmentTime());
        String appointDateStr = appointDate.toString("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        Shop shop = shopService.get(bookVO.getShopId());
        List<BookItemVO> newBookItemVOList = new ArrayList<>(); // 新的bookItem集合
        Set<Block> blockRoomSet = new HashSet<>(); // 记录已经分配的room
        Long tempId = 1L;
        Long tempParentId;
        List<BookFirstStepRecordVO> firstStepRecordList = new ArrayList<>(bookItemVOs.size());
        int firstStepIndex = 1;
        for (BookItemVO bookItemVO : bookItemVOs) {
            tempParentId = null; // 重置为null
            ProductOption productOption = productOptionService.get(bookItemVO.getProductOptionId());
            Integer duration = productOption.getDuration();
            if (duration == null) {
                model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
                return "book/bookSelectResource";
            }
            DateTime startDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + bookItemVO.getStartTime());
            Date startAppointment = startDateTime.toDate();
//            Date endAppointment = startDateTime.plusMinutes(duration).toDate(); // end appointment time
            DateTime endDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + bookItemVO.getEndTime());
            Date endAppointment = endDateTime.toDate();
            int guestAmount = bookItemVO.getGuestAmount();

            // 记录first step record
            BookFirstStepRecordVO firstStepRecordVO = new BookFirstStepRecordVO();
            firstStepRecordVO.setProductOptionId(bookItemVO.getProductOptionId());
            firstStepRecordVO.setProductOption(productOption);
            firstStepRecordVO.setGuestAmount(bookItemVO.getGuestAmount());
            firstStepRecordVO.setShareRoom(bookItemVO.getShareRoom());
            firstStepRecordVO.setStartTime(bookItemVO.getStartTime());
            firstStepRecordVO.setBundleId(bookItemVO.getBundleId());
            //endTime = endAppointment + ProcessTime
            firstStepRecordVO.setEndTime(new DateTime(endAppointment).toString("HH:mm"));
            firstStepRecordVO.setDisplayOrder(firstStepIndex++);
            firstStepRecordList.add(firstStepRecordVO);

            // 由一条记录拆分成多条bookItem
            Room assignRoom; // 分配的房间
            if (guestAmount > 1) {
                boolean isShareRoom = bookItemVO.getShareRoom() != null && bookItemVO.getShareRoom();
                List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), shop.getId(), startAppointment, endAppointment);
                // 全部公用一个room
                Room shareRoom = null;
                if (isShareRoom) {
                    shareRoom = RoomHelper.getAssignRoom(availableRoomList, guestAmount, CommonConstant.ROOM_CAPACITY_RANGE);
                }
                // bookItem 拆分
                for (int i = 0; i < guestAmount; i++) {
                    BookItemVO item = new BookItemVO();
                    item.setTempId(tempId);

                    item.setAppointmentTime(startAppointment);
                    item.setDuration(duration);
                    item.setEndAppointmentTime(endAppointment);
                    item.setBookVO(bookVO);
                    item.setProductOption(productOption);
                    item.setProductOptionId(productOption.getId());
                    if (shareRoom != null) {
                        assignRoom = shareRoom;
                        if(i == 0) {
                            item.setTempParentId(null);
                            tempParentId = item.getTempId();  // 记录父节点id
                        } else {
                            item.setTempParentId(tempParentId);
                        }
                    } else {
                        item.setTempParentId(null);
                        tempParentId = item.getTempId();  // 记录父节点id
                        // 其他都需要单独再分配房间
                        availableRoomList = getNotBlockRoomList(availableRoomList, blockRoomSet, startAppointment, endAppointment);
                        assignRoom = RoomHelper.getAssignRoom(availableRoomList, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
                    }

                    if (assignRoom != null) {
                        item.setRoom(assignRoom);
                        blockRoomSet.add(addBlockRoom(assignRoom, startAppointment, endAppointment));
                    }
                    newBookItemVOList.add(item);
                    tempId++;
                }
            } else {
                bookItemVO.setTempId(tempId);
                tempId++;
                bookItemVO.setTempParentId(null);
                bookItemVO.setAppointmentTime(startAppointment);
                bookItemVO.setDuration(duration);
                bookItemVO.setEndAppointmentTime(endAppointment);
                bookItemVO.setBookVO(bookVO);
                bookItemVO.setProductOption(productOption);
                // 分配room
                List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), shop.getId(), startAppointment, endAppointment);
                logger.debug("Available rooms: {}", availableRoomList);
                availableRoomList = getNotBlockRoomList(availableRoomList, blockRoomSet, startAppointment, endAppointment);
                assignRoom = RoomHelper.getAssignRoom(availableRoomList, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
                if (assignRoom != null) {
                    bookItemVO.setRoom(assignRoom);
                    blockRoomSet.add(addBlockRoom(assignRoom, startAppointment, endAppointment));
                }
                newBookItemVOList.add(bookItemVO);
            }
        }
        // 保存recordVOs到session
        session.setAttribute(BOOK_FIRST_STEP_RECORDS, firstStepRecordList);
        bookVO.setBookItemVOs(newBookItemVOList);
        //model.addAttribute("waiting", waiting);
        model.addAttribute("bookVO", bookVO);
        User user = userService.get(bookVO.getMemberId());
        if (user.getUser() != null) {
            model.addAttribute("preferTherapistId", user.getUser().getId());
        }
        return "book/bookSelectResource";
    }

    @RequestMapping("assignRoomResource")
    public String assignRoomResource(BookVO bookVO, Model model, HttpSession session) {
    	
        DateTime appointDate = new DateTime(bookVO.getStartAppointmentTime());
        String appointDateStr = appointDate.toString("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        List<BookItemVO> bookItemVOs = bookVO.getBookItemVOs();
        List<Long> productIdList = bookItemVOs.stream().map(e -> e.getProductId()).distinct().collect(Collectors.toList());
        List<Long> productOptionIdsList = bookItemVOs.stream().map(e -> e.getProductOptionId()).distinct().collect(Collectors.toList());
        List<BookFirstStepRecordVO> firstStepRecordList = new ArrayList<>(bookItemVOs.size());
        Map<Long, BookItemVO> bookItemVoMap = bookItemVOs.stream().collect(Collectors.toMap(BookItemVO::getBookItemIdex, key -> key,(val1,val2)->val1));
        Map<Long, Long> productOptionIdToProductIdMap = new HashMap<Long,Long>();
        Map<Long,Set<String>> blockRoomMap = new HashMap<Long,Set<String>>();
        Map<Long,Integer> productDurationMap = new HashMap<Long,Integer>();
        Map<String, List<Long>> shareProductIds = new HashMap<String,List<Long>>();
        Map<String,String> timeToBookItemIndexMap = new HashMap<>();
        Map<String,Integer> timeToGuestNumMap = new HashMap<>();
        Long tempId = 1L;
        Long tempParentId;
        int firstStepIndex = 1;
        List<BookItemVO> newBookItemVOList = new ArrayList<>(); // 新的bookItem集合
        Map<Boolean,List<BookItemVO>> isShareRoomMap = bookItemVOs.stream().collect(Collectors.groupingBy(BookItemVO::getShareRoom));
        for(BookItemVO vo:bookItemVOs) {
        	productOptionIdToProductIdMap.put(vo.getProductOptionId(), vo.getProductId());
        	String startTime = vo.getStartTime();
        	if(vo.getShareRoom()) {
        		List<Long> productIds = shareProductIds.get(startTime);
        		String indexStr = timeToBookItemIndexMap.get(startTime);
        		Integer guestNum = timeToGuestNumMap.get(startTime);
        		if(StringUtils.isBlank(indexStr)) {
        			indexStr = vo.getBookItemIdex().toString();
        			guestNum = vo.getGuestAmount();
        		}else {
        			indexStr = indexStr.concat("~").concat(vo.getBookItemIdex().toString());
        			guestNum += vo.getGuestAmount();
        		}
        		timeToGuestNumMap.put(startTime,guestNum);
        		timeToBookItemIndexMap.put(startTime,indexStr);
        		if(Objects.isNull(productIds)) {
        			productIds = new ArrayList<>();
        		}
        		productIds.add(vo.getProductOptionId());
        		shareProductIds.put(startTime, productIds);
//        		productDurationMap.put(vo.getProductId(), vo.getDuration());
        	}
        }
        List<String> productOptionAttributeStr = new ArrayList<String>();
        productOptionAttributeStr.add(CommonConstant.PRODUCT_OPTION_KEY_PROCESS_TIME_REF);
        productOptionAttributeStr.add(CommonConstant.PRODUCT_OPTION_KEY_DURATION_REF);
        productDurationMap = productOptionService.getDurationByProductOptionIds(WebThreadLocal.getCompany().getId(),productOptionIdsList,productOptionAttributeStr);
        List<RoomTreatments> roomTreatments = roomTreatmentService.getRoomByShopAndProductOptionIds(bookVO.getShopId(), productIdList);
        // 能够提供某种 treatment 的房间列表
        Map<Long, Set<Room>> productRoomMap = new HashMap<Long, Set<Room>>(); 
        for(RoomTreatments roomTreatment : roomTreatments) {
        	Long productId = roomTreatment.getProduct().getId();
        	Set<Room> list = productRoomMap.get(productId);
        	if(Objects.isNull(list)) {
        		list = new HashSet<Room>();
        	}
        	list.add(roomTreatment.getRoom());
        	productRoomMap.put(productId, list);
        }

        List<Block> roomBlocks = blockService.checkRoomBlock(bookVO.getShopId(), null, appointDate.toDate(), null);
        for(Block block : roomBlocks) {
        	String startTimeStr = new DateTime(block.getStartDate()).toString("HH:mm");
        	String endTimeStr = new DateTime(block.getEndDate()).toString("HH:mm");
        	Set<String> blockSets = blockRoomMap.get(block.getRoom().getId());
        	if(Objects.isNull(blockSets)) {
        		blockSets = new HashSet<>();
        		blockRoomMap.put(block.getRoom().getId(), blockSets);
        	}
        	blockSets.add(startTimeStr.concat("~").concat(endTimeStr));
        }
        
        
        List<BookItem> bookItemLists = bookItemService.getBookItemList(bookVO.getShopId(), appointDate.toDate(), DateUtil.getEndTime(appointDate.toDate()));
        if(Objects.nonNull(bookItemLists)) {
        	for(BookItem bi : bookItemLists) {
        		Room room = bi.getRoom();
        		if(Objects.nonNull(room)) {
                	String startTimeStr = new DateTime(bi.getAppointmentTime()).toString("HH:mm");
                	String endTimeStr = new DateTime(bi.getAppointmentEndTime()).toString("HH:mm");
                	Set<String> blockSets = blockRoomMap.get(room.getId());
                	if(Objects.isNull(blockSets)) {
                		blockSets = new HashSet<>();
                		blockRoomMap.put(room.getId(), blockSets);
                	}
                	blockSets.add(startTimeStr.concat("~").concat(endTimeStr));
        		}
        	}
        }
        Boolean isSameTimeToShareRoom = bookVO.getSameTimeToShareRoom();
        // 以上是前期数据准备
        // 以下是房间分配实现
        List<BookItemVO> shareRoomBookItemList = isShareRoomMap.get(true);
        if(Objects.nonNull(isSameTimeToShareRoom) && isSameTimeToShareRoom) {
        	bookVO.setSameTimeToShareRoom(true);
        	// 把同一时间的不同treatment尽量安排在同一个房,不能安排就全挂起来
        	if(Objects.nonNull(shareRoomBookItemList)) {
        		for(Entry<String, List<Long>> entry: shareProductIds.entrySet()) {
        			String key = entry.getKey();
        			List<Long> productIds = entry.getValue(); 
                    Integer totalGuestAmount = timeToGuestNumMap.get(key);
                    // 提供各种treatments的房间的交集
        			Set<Room> intersection = null;
        			List<Set<Room>> allRooms = new ArrayList<>();
        			Integer maxDuration = null;
                    for(Long pId : productIds) {
                    	Long productId = productOptionIdToProductIdMap.get(pId);
                    	if(Objects.isNull(intersection)) {
                    		if(Objects.nonNull(productRoomMap.get(productId))) {
                    			intersection = new HashSet<>(productRoomMap.get(productId));
                    		}else {
                    			intersection = new HashSet<>();
                    		}
                    		allRooms.add(intersection);
                    	}else {
                    		allRooms.add(productRoomMap.get(productId));
                    	}
                    	if(null == maxDuration) {
                    		maxDuration = productDurationMap.get(pId);
                    	}else {
                    		// 取出最长treatment耗时
                    		if(maxDuration < productDurationMap.get(pId)) {
                    			maxDuration = productDurationMap.get(pId);
                    		}
                    	}
                    }
                    // productOptionShareRoomList treatment对应的共用的房间的交集
                    Set<Room> productOptionShareRoomList = allRooms.parallelStream()
            				.filter(elementList -> elementList != null && ((Set) elementList).size() != 0).reduce((a, b) -> {
            					a.retainAll(b);
            					return a;
            				}).orElse(Collections.emptySet());
                    
    				Room shareRoom = null;
    				List<Room> usefulRooms = null;
    				
    		        DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern("HH:mm");
    		        DateTime keyDateTime = formatter.parseDateTime(key);
    		        DateTime endDateTime = keyDateTime.plusMinutes(maxDuration);
    				
	        		if(Objects.nonNull(productOptionShareRoomList)) {
	        			usefulRooms = getAvailableRooms(productOptionShareRoomList, blockRoomMap, key, endDateTime.toString("HH:mm"));
	        			shareRoom = RoomHelper.getAssignRoom(usefulRooms, totalGuestAmount, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
	        		}
	        		if(Objects.nonNull(shareRoom)) {
	                	Set<String> sets = blockRoomMap.get(shareRoom.getId());
	                	if(Objects.isNull(sets)) {
	                		sets = new HashSet<>();
	                	}
	                	sets.add(key.concat("~").concat(endDateTime.toString("HH:mm")));
	                	blockRoomMap.put(shareRoom.getId(), sets);
	        		}
	        		String bookItemIndexStr = timeToBookItemIndexMap.get(key);
	        		String[] indexStrs = bookItemIndexStr.split("~");
	        		for (int i = 0; i < indexStrs.length; i++) {
	        			Long bookItemIndex = Long.valueOf(indexStrs[i]);
	        			BookItemVO vo = bookItemVoMap.get(bookItemIndex);
	    	            ProductOption productOption = productOptionService.get(vo.getProductOptionId());
	    	            Integer duration = productOption.getDuration();
	    	            if (null == duration) {
	    	                model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
	    	                return "book/bookSelectResource";
	    	            }
	    	            DateTime beginDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + vo.getStartTime());
	    	            DateTime finishDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + vo.getEndTime());
	    	            BookFirstStepRecordVO bfsrVO = createBookFirstStepRecordVO(vo,productOption,finishDateTime.toDate(),firstStepIndex);
	    	            firstStepRecordList.add(bfsrVO);
	    	            firstStepIndex++;
	    	            Integer guestNum = vo.getGuestAmount();
	    	            tempParentId = null;
	    	            for(Integer j = 0; j < guestNum ; j++) {
	    	            	BookItemVO newVO = new BookItemVO();
	    					newVO.setTempId(tempId);
	    					newVO.setBundleId(vo.getBundleId());
	    					newVO.setProductId(vo.getProductId());
	    	                newVO.setAppointmentTime(beginDateTime.toDate());
	    	                newVO.setDuration(productDurationMap.get(vo.getProductId()));
	    	                newVO.setEndAppointmentTime(finishDateTime.toDate());
	    	                newVO.setBookVO(bookVO);
	    	                newVO.setProductOption(productOption);
	                        if(0 == j) {
	                        	newVO.setTempParentId(null);
	                        	tempParentId = tempId;
	                        }else {
	                        	newVO.setTempParentId(tempParentId);
	                        }
	                        newVO.setRoom(shareRoom);
	    	                newBookItemVOList.add(newVO);
	    	                tempId++;
	    	            }
					}
        		}
        	}
        	
        }else {
        	bookVO.setSameTimeToShareRoom(false);
        	if(Objects.nonNull(shareRoomBookItemList)) {
        		for (BookItemVO bookItemVO : shareRoomBookItemList) {
        			tempParentId = null;
        			Integer guestNums = bookItemVO.getGuestAmount();
    	            ProductOption productOption = productOptionService.get(bookItemVO.getProductOptionId());
    	            Integer duration = productOption.getDuration();
    	            if (duration == null) {
    	                model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
    	                return "book/bookSelectResource";
    	            }
            		String startTimeStr = bookItemVO.getStartTime();
            		String endTimeStr = bookItemVO.getEndTime();
            		DateTime startDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + startTimeStr);
            		DateTime endDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + endTimeStr);

    	            BookFirstStepRecordVO bfsrVO = createBookFirstStepRecordVO(bookItemVO,productOption,endDateTime.toDate(),firstStepIndex);
    	            firstStepRecordList.add(bfsrVO);
    	            firstStepIndex++;
            		
    				Set<Room> rooms = productRoomMap.get(bookItemVO.getProductId());
    				Room shareRoom = null;
    				List<Room> usefulRooms = null;
	        		if(Objects.nonNull(rooms)) {
	        			usefulRooms = getAvailableRooms(rooms, blockRoomMap, startTimeStr, endTimeStr);
	        			shareRoom = RoomHelper.getAssignRoom(usefulRooms, guestNums, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
	        		}
    				Room assignRoom = null;
    				for(Integer i = 0;i < guestNums;i++ ) {
    	                
    					BookItemVO newVO = new BookItemVO();
    					newVO.setBundleId(bookItemVO.getBundleId());
    					newVO.setProductId(bookItemVO.getProductId());
    					newVO.setTempId(tempId);
    	                newVO.setAppointmentTime(startDateTime.toDate());
    	                newVO.setDuration(productDurationMap.get(bookItemVO.getProductId()));
    	                newVO.setEndAppointmentTime(endDateTime.toDate());
    	                newVO.setBookVO(bookVO);
    	                newVO.setProductOption(productOption);
    	                // 第一个bookItemVo不设置父id
                        if(0 == i) {
                        	newVO.setTempParentId(null);
                        	tempParentId = tempId;
                        }else {
                        	newVO.setTempParentId(tempParentId);
                        }
                        // 
		        		if(Objects.nonNull(shareRoom)) {
    	                	assignRoom = shareRoom;
    	                	newVO.setRoom(assignRoom);
    	                }else {
    	                	// 找单个位置的房间分配
    	                	assignRoom = RoomHelper.getAssignRoom(usefulRooms, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
    		        		if(Objects.nonNull(assignRoom)) {
        	                	newVO.setRoom(assignRoom);
        	                	usefulRooms.remove(assignRoom);
    		        		}
    	                }
		        		if(Objects.nonNull(assignRoom)) {
    	                	Set<String> sets = blockRoomMap.get(assignRoom.getId());
    	                	if(Objects.isNull(sets)) {
    	                		sets = new HashSet<>();
    	                	}
    	                	newVO.setRoom(assignRoom);
    	                	sets.add(startTimeStr.concat("~").concat(endTimeStr));
    	                	if(Objects.nonNull(shareRoom)) {
    	                		blockRoomMap.put(shareRoom.getId(), sets);
    	                	}
		        		}
    	                newBookItemVOList.add(newVO);
    	                tempId++;
    				}
    				
				}
        	}
        }        
//--------------------------------------------------------------------------------------------------------//       
        // 处理不共用房间的bookitem , 即单独分一个房间
        List<BookItemVO> noShareRoomList = isShareRoomMap.get(false);
        if(Objects.nonNull(noShareRoomList)) {
        	for (BookItemVO bookItemVO : noShareRoomList) {
	            ProductOption productOption = productOptionService.get(bookItemVO.getProductOptionId());
	            Integer duration = productOption.getDuration();
	            if (duration == null) {
	                model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
	                return "book/bookSelectResource";
	            }
        		String startTimeStr = bookItemVO.getStartTime();
        		String endTimeStr = bookItemVO.getEndTime();
        		DateTime startDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + startTimeStr);
        		DateTime endDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + endTimeStr);

	            BookFirstStepRecordVO bfsrVO = createBookFirstStepRecordVO(bookItemVO,productOption,endDateTime.toDate(),firstStepIndex);
	            firstStepRecordList.add(bfsrVO);
	            firstStepIndex++;
				Set<Room> rooms = productRoomMap.get(productOption.getProduct().getId());
				Room assignRoom = null;
				for(Integer i = 0;i < bookItemVO.getGuestAmount();i++ ) {
					if(Objects.nonNull(rooms)) {
		        		List<Room> usefulRooms = getAvailableRooms(rooms, blockRoomMap, startTimeStr, endTimeStr);
		        		assignRoom = RoomHelper.getAssignRoom(usefulRooms, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
					}
					BookItemVO newVO = new BookItemVO();
					newVO.setTempId(tempId);
	                tempId++;
	                newVO.setTempParentId(null);
	                newVO.setBundleId(bookItemVO.getBundleId());
	                newVO.setAppointmentTime(startDateTime.toDate());
	                newVO.setDuration(productDurationMap.get(bookItemVO.getProductId()));
	                newVO.setEndAppointmentTime(endDateTime.toDate());
	                newVO.setBookVO(bookVO);
	                newVO.setProductId(bookItemVO.getProductId());
	                newVO.setProductOption(productOption);
	                if(Objects.nonNull(assignRoom)) {
	                	Set<String> lists = blockRoomMap.get(assignRoom.getId());
	                	if(Objects.isNull(lists)) {
	                        lists = new HashSet<>();
	                	}
	                	lists.add(startTimeStr.concat("~").concat(endTimeStr));
	                	blockRoomMap.put(assignRoom.getId(), lists);
	                	newVO.setRoom(assignRoom);
	                }
	                newBookItemVOList.add(newVO);
				}
			}
        }
        // 保存recordVOs到session
        session.setAttribute(BOOK_FIRST_STEP_RECORDS, firstStepRecordList);
        bookVO.setBookItemVOs(newBookItemVOList);
        //model.addAttribute("waiting", waiting);
        model.addAttribute("bookVO", bookVO);
        User user = userService.get(bookVO.getMemberId());
        if (user.getUser() != null) {
            model.addAttribute("preferTherapistId", user.getUser().getId());
        }
    	return "book/bookSelectResource";
    }
    private BookFirstStepRecordVO createBookFirstStepRecordVO(BookItemVO bookItemVO,ProductOption productOption,
    		Date endAppointment,Integer firstStepIndex) {
    	BookFirstStepRecordVO firstStepRecordVO = new BookFirstStepRecordVO();
        firstStepRecordVO.setProductOptionId(bookItemVO.getProductOptionId());
        firstStepRecordVO.setProductOption(productOption);
        firstStepRecordVO.setGuestAmount(bookItemVO.getGuestAmount());
        firstStepRecordVO.setShareRoom(bookItemVO.getShareRoom());
        firstStepRecordVO.setStartTime(bookItemVO.getStartTime());
        firstStepRecordVO.setBundleId(bookItemVO.getBundleId());
        //endTime = endAppointment + ProcessTime
        firstStepRecordVO.setEndTime(new DateTime(endAppointment).toString("HH:mm"));
        firstStepRecordVO.setDisplayOrder(firstStepIndex);
    	return firstStepRecordVO;
    }
    @RequestMapping("confirm")
    public String confirm(BookVO bookVO, Model model) {
        Shop shop = shopService.get(bookVO.getShopId());
        User member = userService.get(bookVO.getMemberId());
        model.addAttribute("shop", shop);
        model.addAttribute("member", member);
        for (BookItemVO bookItemVO : bookVO.getBookItemVOs()) {
            bookItemVO.setProductOption(productOptionService.get(bookItemVO.getProductOptionId()));
            bookItemVO.setDuration(bookItemVO.getProductOption().getDuration());

            for(RequestTherapistVO therapistVO : bookItemVO.getRequestTherapistVOs()) {
                if(therapistVO.getTherapistId() != null) {
                    therapistVO.setTherapist(userService.get(therapistVO.getTherapistId()));
                }
            }

            if(bookItemVO.getRoomId() != null) {
                bookItemVO.setRoom(roomService.get(bookItemVO.getRoomId()));
            }
        }
        model.addAttribute("bookVO", bookVO);
        return "book/bookConfirm";
    }

    // 快速添加 ------------------------------------------------------------------------------------
    @RequestMapping("toQuickAdd")
    public String toQuickAdd(Model model, BookQuickVO bookQuickVO) {
        Long therapistId = bookQuickVO.getTherapistId();
        if(therapistId == null) {
            throw new RuntimeException("Parameter error!");
        }
        User therapist = userService.get(therapistId);
        bookQuickVO.setTherapist(therapist);
        Date startAppointmentTime = new Date(bookQuickVO.getStartTimeStamp());
        bookQuickVO.setStartAppointmentTime(startAppointmentTime);
        if(bookQuickVO.getDoubleBooking() !=null && bookQuickVO.getDoubleBooking()){
        	model.addAttribute("timeTreeData", CommonConstant.TIME_DATA);
        	Long originalBookItemId = bookQuickVO.getOriginalBookItemId();
        	BookItem bi = bookItemService.get(originalBookItemId);
             List<BookItem>bookItemChildren=  bi.getChildrenOfDoubleBooking();
                 if (bi!=null&&bi.getAppointmentEndTime()!=null){
                     Date endTime=bi.getAppointmentEndTime();
                     if (bookItemChildren!=null&&bookItemChildren.size()>0){
                         for (BookItem bookItem:bookItemChildren){
                             if (endTime.getTime()<bookItem.getAppointmentEndTime().getTime()){
                                 endTime=bookItem.getAppointmentEndTime();
                             }
                         }
                     }
                     model.addAttribute("currentEndTime",new DateTime(endTime).toString("HH:mm"));
                 }
        	model.addAttribute("currentTime",new DateTime(bi.getAppointmentTime()).toString("HH:mm"));
            model.addAttribute("date",new DateTime(bi.getAppointmentTime()).toString("yyyy-MM-dd"));
        }
        model.addAttribute("bookQuickVO", bookQuickVO);
        return "book/bookQuickAdd";
    }

    @RequestMapping("bookQuickAddConfirm")
    public String bookQuickAddConfirm(BookQuickVO bookQuickVO, Model model) {
        User member = userService.get(bookQuickVO.getMemberId());
        model.addAttribute("member", member);

        User therapist = userService.get(bookQuickVO.getTherapistId());
        bookQuickVO.setTherapist(therapist);
        ProductOption productOption = productOptionService.get(bookQuickVO.getProductOptionId());
        bookQuickVO.setProductOption(productOption);
        Integer duration = productOption.getDuration();
        if (duration == null) {
            model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
            return "book/bookQuickAddConfirm";
        }
        Date startAppointment =null;
        if(bookQuickVO.getDoubleBooking() !=null && bookQuickVO.getDoubleBooking()){
        	startAppointment = DateUtil.stringToDate(bookQuickVO.getBookingDate()+" "+bookQuickVO.getStartTime(), "yyyy-MM-dd HH:mm");
        	bookQuickVO.setStartTimeStamp(new DateTime(startAppointment).getMillis());
        }else{
	        startAppointment = new Date(bookQuickVO.getStartTimeStamp());
	        
        }
        
        bookQuickVO.setStartAppointmentTime(startAppointment);
        DateTime startDateTime = new DateTime(startAppointment);
        Date endAppointment = startDateTime.plusMinutes(duration).toDate(); // end appointment time
        // 分配房间
        List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), bookQuickVO.getShopId(), startAppointment, endAppointment);
        List<Room> assignRoomList = RoomHelper.assignRoom(availableRoomList, 1); // 分配一个人的房间

        if (assignRoomList.size() == 0 || assignRoomList.get(0) == null) {
            logger.debug("No available room found!");
            bookQuickVO.setStatus(CommonConstant.BOOK_STATUS_WAITING);
        } else {
            bookQuickVO.setAssignRoom(assignRoomList.get(0));
            bookQuickVO.setStatus(CommonConstant.BOOK_STATUS_CONFIRM);
        }
        model.addAttribute("bookQuickVO", bookQuickVO);
        return "book/bookQuickAddConfirm";
    }

    @RequestMapping("bookQuickAdd")
    @ResponseBody
    public AjaxForm bookQuickAdd(BookQuickVO bookQuickVO) {
        try {
            Book book = bookService.add(bookQuickVO.transferToBookVO());
            if (PropertiesUtil.getBooleanValueByName("BOOKING_NOTIFICATION")) {
            	 bookService.sendBookingNotification(book, CommonConstant.SEND_BOOKING_NOTIFICATION_EMAIL);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
    }

    @RequestMapping("bookView")
    public String bookView(BookVO bookVO, Model model) {
        Book book = bookService.get(bookVO.getId());
        model.addAttribute("book", book);
        return "book/bookView";
    }
    @RequestMapping("clientView")
    public String clientView(long userId, Model model){
        User client = userService.get(userId);
        String accountType=client.getAccountType();
        if(!accountType.equals("GUEST")){
        DetachedCriteria prepaidDetachedCriteria = DetachedCriteria.forClass(Prepaid.class);
        prepaidDetachedCriteria.add(Restrictions.eq("user.id",client.getId()));
        prepaidDetachedCriteria.add(Restrictions.eq("isActive", true));
        List<Prepaid>prepaidList=prepaidService.list(prepaidDetachedCriteria);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFamilyDetails.class);
        detachedCriteria.add(Restrictions.eq("user.id",client.getId()));
        List<UserFamilyDetails> familyDetailsList = userFamilyDetailsService.list(detachedCriteria);
        DetachedCriteria consentFormdc = DetachedCriteria.forClass(ConsentForm.class);
        consentFormdc.add(Restrictions.eq("isActive", true));
        consentFormdc.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        List consentFormList=consentFormService.list(consentFormdc);
        model.addAttribute("consentFormList", consentFormList);
        model.addAttribute("prepaidList", prepaidList);
        model.addAttribute("familyDetailsList", familyDetailsList);
        model.addAttribute("client", client);
        // expiry date of diva level
        String expiryDateFormat ="N/A";
    	if(client.getCurrentLoyaltyLevel() !=null){
    		DetachedCriteria ulldc = DetachedCriteria.forClass(UserLoyaltyLevel.class);
    		ulldc.add(Restrictions.eq("user.id", client.getId()));
    		ulldc.add(Restrictions.eq("isActive", true));
    		List<UserLoyaltyLevel> ullListTrue=SpringUtil.getBean(UserLoyaltyLevelServiceImpl.class).list(ulldc);
    		if(ullListTrue.size()>0){
                Date expiryDate=ullListTrue.get(0).getExpiryDate();
                if(expiryDate !=null){
                    try {
                        expiryDateFormat =DateUtil.dateToString(expiryDate, "yyyy-MM-dd");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

    	}
            model.addAttribute("expiryDateFormat", expiryDateFormat);

        }
        model.addAttribute("accountType", accountType);
        return "book/clientView";
    }
    @RequestMapping("productView")
    public  String ProductView(Long id, Model model){
        BookItem bookItem=  bookItemService.get(id);
        ProductOption productOption= productOptionService.get(bookItem.getProductOption().getId());
        Shop shop= bookItem.getBook().getShop();
        ProductOptionSupernumeraryPrice productOptionSupernumeraryPrice= productOptionSupernumeraryPriceService.getProductOptionSupernumeraryPriceByShopAndPO(productOption.getId(),shop.getId());
        model.addAttribute("shop", shop);
        model.addAttribute("productOptionSupernumeraryPrice", productOptionSupernumeraryPrice);
        model.addAttribute("productOption", productOption);
        return "book/productView";
    }


    @RequestMapping("bookTimeTherapistView")
    public String bookTimeTherapistView(Model model, ViewVO viewVO,HttpServletRequest request) {
        viewVO.setViewType(CommonConstant.THERAPIST_VIEW);
        try {
            bookItemService.updateBookItem(viewVO);
        } catch (ResourceException re) {
            re.printStackTrace();
            model.addAttribute("error", re.getMessage());
        }

        // 计算最早的和最晚的时间
        // 每个点的开门时间，和关门时间
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        Long currentShopId = null;
        if (Objects.nonNull(currentShop)) {
            currentShopId = currentShop.getId();
        }

        if (Objects.isNull(viewVO.getShopId()) && Objects.nonNull(currentShopId)) {
            viewVO.setShopId(currentShopId);
        }

        Long shopId = viewVO.getShopId();
        // shopList
        List<Shop> shopList = shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false);
        if (shopId == null) {
            shopId = ((Shop)shopList.get(0)).getId();
            viewVO.setShopId(shopId);
        }
        Shop shop = shopService.get(shopId);
        Date currentTime;
        if (viewVO.getAppointmentTimeStamp() != null) {
            currentTime = new Date(viewVO.getAppointmentTimeStamp());
        } else {
            currentTime = new Date();
            viewVO.setAppointmentTimeStamp(currentTime.getTime());
        }
        OpeningHours openingHours = shop.getOpeningHour(currentTime);
        long openTimeMillis = openingHours.getOpenTimeObj().getMillis();
        long closeTimeMillis = openingHours.getCloseTimeObj().getMillis();

        List<CellVO> cellVOList = bookItemService.transferBookItemsToCell(viewVO); // 包括block,common booking and double booking
        Map<String, CellVO> cellVOMap = new HashMap<>();
        
        long currentTimeMillis;
        for (CellVO newCellVO : cellVOList) {
            currentTimeMillis = newCellVO.getTime().getMillis();
            openTimeMillis = (currentTimeMillis < openTimeMillis) ? currentTimeMillis : openTimeMillis;
            closeTimeMillis = (currentTimeMillis > closeTimeMillis) ? currentTimeMillis : closeTimeMillis;
            String key = newCellVO.getTime().getMillis() + "," + newCellVO.getTherapist().getId();
            cellVOMap.put(key, newCellVO);
        }
        model.addAttribute("shopList", shopList);
        model.addAttribute("firstShopId",shopId);
        
        DateTime startDateTime = new DateTime(openTimeMillis);
        DateTime endDateTime  = new DateTime(closeTimeMillis);
        model.addAttribute("startDateTime", startDateTime); // 开始時間
        model.addAttribute("startDateTimeStamp", startDateTime.getMillis());
        model.addAttribute("endDateTime", endDateTime); // 结束時間
        viewVO.setStartTime(startDateTime);
        viewVO.setEndTime(endDateTime);
        // 所有的therapist
/*        List<User> therapistList = userService.getTherapistByShop(shopId, null);*/
        List<User> therapistList=  staffHomeShopDetailsService.getTherapistByShop(shopId);
        
        model.addAttribute("therapistList", therapistList);
        model.addAttribute("viewVO", viewVO);
        model.addAttribute("cellVOMap", cellVOMap);
        return "book/bookTimeTherapistView";
    }

    @RequestMapping("bookTimeRoomView")
    public String bookTimeRoomView(Model model, ViewVO viewVO,HttpServletRequest request) {
        viewVO.setViewType(CommonConstant.ROOM_VIEW);
        try {
            bookItemService.updateBookItem(viewVO);
        } catch (ResourceException re) {
            re.printStackTrace();
            model.addAttribute("error", re.getMessage());
        }

        // 计算最早的和最晚的时间
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        Long currentShopId = null;
        if (Objects.nonNull(currentShop)) {
            currentShopId = currentShop.getId();
        }

        if (Objects.isNull(viewVO.getShopId()) && Objects.nonNull(currentShopId)) {
            viewVO.setShopId(currentShopId);
        }

        Long shopId = viewVO.getShopId();
        // shopList
        List<Shop> shopList = shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false);
        if (shopId == null) {
        	shopId = ((Shop)shopList.get(0)).getId();
            viewVO.setShopId(shopId);
        }
        Shop shop = shopService.get(shopId);
        Date currentTime;
        if (viewVO.getAppointmentTimeStamp() != null) {
            currentTime = new Date(viewVO.getAppointmentTimeStamp());
        } else {
            currentTime = new Date();
            viewVO.setAppointmentTimeStamp(currentTime.getTime());
        }
        OpeningHours openingHours = shop.getOpeningHour(currentTime);
        long openTimeMillis = openingHours.getOpenTimeObj().getMillis();
        long closeTimeMillis = openingHours.getCloseTimeObj().getMillis();

        List<CellVO> cellVOList = bookItemService.getAllBlockCellList(viewVO); // 包括block和bookItem
        Map<String, CellVO> cellVOMap = new HashMap<>();

        long currentTimeMillis;
        String key;
        for (CellVO cellVO : cellVOList) {
            currentTimeMillis = cellVO.getTime().getMillis();
            openTimeMillis = (currentTimeMillis < openTimeMillis) ? currentTimeMillis : openTimeMillis;
            closeTimeMillis = (currentTimeMillis > closeTimeMillis) ? currentTimeMillis : closeTimeMillis;
            key = cellVO.getTime().getMillis() + "," + cellVO.getRoom().getId();
            BookItem bookItem = cellVO.getBookItem();
            if (bookItem != null) {
                // bookItem root 显示，其他的排除
                if (bookItem.getBookItem() == null) {
                    cellVOMap.put(key, cellVO);
                }
            } else {
                cellVOMap.put(key, cellVO);
            }
        }
        
        DateTime startDateTime = new DateTime(openTimeMillis);
        DateTime endDateTime  = new DateTime(closeTimeMillis);
        model.addAttribute("shopList", shopList);
        
        model.addAttribute("firstShopId", (shopList !=null && shopList.size()>0 ? shopList.get(0).getId() : 0l));
        
        model.addAttribute("startDateTime", startDateTime); // 开始時間
        model.addAttribute("endDateTime", endDateTime); // 结束時間
        viewVO.setStartTime(startDateTime);
        viewVO.setEndTime(endDateTime);
        // 所有的room
        List<Room> roomList = roomService.getRoomByShop(shopId, null);

        List<BookItem> bookItemList = bookItemService.getBookItemList(shopId, startDateTime.toDate(), endDateTime.toDate());
        Map<Room, Integer> roomMap = new LinkedHashMap<>();
        for (Room room : roomList) {
            roomMap.put(room, 0);
        }
        for (BookItem bookItem : bookItemList) {
            Room room = bookItem.getRoom();
            Integer count = roomMap.get(room) + 1;
            roomMap.put(room, count);
        }
        roomList = roomMap.entrySet().stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
        model.addAttribute("roomList", roomList);
        model.addAttribute("viewVO", viewVO);
        model.addAttribute("cellVOMap", cellVOMap);
        return "book/bookTimeRoomView";
    }

    @RequestMapping("bookItemWaitingList")
    public String bookItemWaitingList(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, Long shopId, Model model) {
        model.addAttribute("bookItemList", bookItemService.getAllWaitingBookItem(shopService.get(shopId), startTime));
        return "book/bookItemWaitingList";
    }

    @RequestMapping("toBlockTherapistAdd")
    public String toBlockTherapistAdd(Model model, BlockVO blockVO) {
        //DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Shop.class);
        //model.addAttribute("shopList", shopService.getActiveListByRefAndCompany(detachedCriteria, null, WebThreadLocal.getCompany().getId()));
        //Date startTime = blockVO.getStartTime();

        // 计算allowTimes
        //OpeningHours openingHours = shop.getOpeningHour(startTime);
        //DateTime startDateTime = openingHours.getOpenTimeObj();
        //DateTime endDateTime = openingHours.getCloseTimeObj();
        //List<String> allowTimes = new ArrayList<>();
        /*while(startDateTime.isBefore(endDateTime)) {
            allowTimes.add("'" + startDateTime.toString("HH:mm") + "'");
            startDateTime = startDateTime.plusMinutes(CommonConstant.TIME_UNIT);
        }*/
        //model.addAttribute("allowTimes", StringUtils.join(allowTimes, ","));
        //model.addAttribute("currentDateTime", currentAppointmentDateTimeString);
        model.addAttribute("blockVO", blockVO);
        blockVO.setStartDateTime(new Date(blockVO.getStartTimeStamp()));
        blockVO.setEndDateTime(blockVO.getStartDateTime());
        List<User> therapistList = userService.getTherapistByShop(blockVO.getShopId(), null);
        model.addAttribute("therapistList", therapistList);
        return "book/blockTherapistAdd";
    }

    @RequestMapping("blockTherapistAddConfirm")
    public String blockTherapistAddConfirm(BlockVO blockVO, Model model) {
        User therapist = userService.get(blockVO.getTherapistId());
        model.addAttribute("therapist", therapist);
        model.addAttribute("blockVO", blockVO);
        String res = blockVO.validate();
        if (StringUtils.isNotBlank(res)) {
            model.addAttribute("error", res);
        }
        return "book/blockTherapistAddConfirm";
    }

    @RequestMapping("blockAdd")
    @ResponseBody
    public AjaxForm blockAdd(BlockVO blockVO) {
        String res = blockVO.validate();
        if (StringUtils.isNotBlank(res)) {
            return AjaxFormHelper.error().addAlertError(res);
        }
        try {
            blockService.save(blockVO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
    }

    @RequestMapping("removeBlock")
    @ResponseBody
    public AjaxForm removeBlock(BlockVO blockVO) {
        blockService.remove(blockVO);
        return AjaxFormHelper.success("success");
    }

    // block therapist update  ---------------------------------------------------

    @RequestMapping("toBlockTherapistUpdate")
    public String toBlockTherapistUpdate(Model model, BlockVO blockVO) {
        Block block = blockService.get(blockVO.getId());
        model.addAttribute("block", block);
        model.addAttribute("blockVO", blockVO);
        DateTimeRangeVO rangeVO = block.getTimeRange(new Date(blockVO.getStartTimeStamp()));
        blockVO.setRepeatStartTime(rangeVO.getStart().toString("HH:mm"));
        blockVO.setRepeatEndTime(rangeVO.getEnd().toString("HH:mm"));
        boolean isAllDay = false;
        if(CommonConstant.BLOCK_REPEAT_TYPE_NONE.equals(block.getRepeatType())) {
            DateTime startDate =  new DateTime(block.getStartDate());
            DateTime endDate = new DateTime(block.getEndDate());
            isAllDay = (startDate.getHourOfDay() == 0 && startDate.getMinuteOfHour() == 0 && endDate.getHourOfDay() == 23 && endDate.getMinuteOfHour() == 59);
        }
        model.addAttribute("isAllDay", isAllDay);
        return "book/blockTherapistUpdate";
    }

    @RequestMapping("blockTherapistUpdateConfirm")
    public String blockTherapistUpdateConfirm(BlockVO blockVO, Model model) {
        Block block = blockService.get(blockVO.getId());
        blockVO.setRepeatType(block.getRepeatType());
        if(!block.isNotRepeat()) {
            blockVO.setRepeatStartDate(block.getStartDate());
            blockVO.setRepeatEndDate(block.getEndDate());
        }
        model.addAttribute("blockVO", blockVO);
        model.addAttribute("block", block);

        String res = blockVO.validate();
        if (StringUtils.isNotBlank(res)) {
            model.addAttribute("error", res);
        }
        return "book/blockTherapistUpdateConfirm";
    }

    @RequestMapping("blockUpdate")
    @ResponseBody
    public AjaxForm blockUpdate(BlockVO blockVO) {
        Block block = blockService.get(blockVO.getId());
        blockVO.setRepeatType(block.getRepeatType());
        if(!block.isNotRepeat()) {
            blockVO.setRepeatStartDate(block.getStartDate());
            blockVO.setRepeatEndDate(block.getEndDate());
            blockVO.setCronExpression(block.getCronExpression());
        }
        String res = blockVO.validate();
        if (StringUtils.isNotBlank(res)) {
            return AjaxFormHelper.error().addAlertError(res);
        }
        blockVO.setShopId(block.getShop().getId());
        if(block.getUser() != null) {
            blockVO.setTherapistId(block.getUser().getId());
        }
        if(block.getRoom() != null) {
            blockVO.setRoomId(block.getRoom().getId());
        }
        blockVO.setType(block.getType());
        try {
            blockService.update(blockVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }
    }

    @RequestMapping("toBlockRoomUpdate")
    public String toBlockRoomUpdate(Model model, BlockVO blockVO) {
        Block block = blockService.get(blockVO.getId());
        model.addAttribute("block", block);
        model.addAttribute("blockVO", blockVO);
        boolean isAllDay = false;
        DateTime startDate =  new DateTime(block.getStartDate());
        DateTime endDate = new DateTime(block.getEndDate());
        isAllDay = (startDate.getHourOfDay() == 0 && startDate.getMinuteOfHour() == 0 && endDate.getHourOfDay() == 23 && endDate.getMinuteOfHour() == 59);
        model.addAttribute("isAllDay", isAllDay);
        return "book/blockRoomUpdate";
    }

    @RequestMapping("blockRoomUpdateConfirm")
    public String blockRoomUpdateConfirm(BlockVO blockVO, Model model) {
        Block block = blockService.get(blockVO.getId());
        blockVO.setRepeatType(block.getRepeatType());
        model.addAttribute("blockVO", blockVO);
        model.addAttribute("block", block);
        String res = blockVO.validate();
        if (StringUtils.isNotBlank(res)) {
            model.addAttribute("error", res);
        }
        return "book/blockRoomUpdateConfirm";
    }

    // block room add ---------------------------------------------------------------------------

    @RequestMapping("toBlockRoomAdd")
    public String toBlockRoomAdd(Model model, BlockVO blockVO) {
        /*Room room = roomService.get(blockVO.getRoomId());
        Shop shop = room.getShop();
        blockVO.setShopId(shop.getId());
        Date startTime = new Date(blockVO.getStartTimeStamp());
        //blockVO.setStartTime(startTime);
        // 计算allowTimes
        OpeningHours openingHours = shop.getOpeningHour(startTime);
        DateTime startDateTime = openingHours.getOpenTimeObj();
        DateTime endDateTime = openingHours.getCloseTimeObj();
        List<String> allowTimes = new ArrayList<>();
        while(startDateTime.isBefore(endDateTime)) {
            allowTimes.add("'" + startDateTime.toString("HH:mm") + "'");
            startDateTime = startDateTime.plusMinutes(CommonConstant.TIME_UNIT);
        }
        model.addAttribute("allowTimes", StringUtils.join(allowTimes, ","));
        model.addAttribute("blockVO", blockVO);
        model.addAttribute("shop", shop);*/
        model.addAttribute("blockVO", blockVO);
        blockVO.setStartDateTime(new Date(blockVO.getStartTimeStamp()));
        blockVO.setEndDateTime(blockVO.getStartDateTime());
        model.addAttribute("shop", shopService.get(blockVO.getShopId()));

        return "book/blockRoomAdd";
    }

    @RequestMapping("blockRoomAddConfirm")
    public String blockRoomAddConfirm(BlockVO blockVO, Model model) {
        Room room = roomService.get(blockVO.getRoomId());
        blockVO.setRoom(room);
        model.addAttribute("blockVO", blockVO);
        String res = blockVO.validate();
        if (StringUtils.isNotBlank(res)) {
            model.addAttribute("error", res);
        }
        return "book/blockRoomAddConfirm";
    }

    @RequestMapping("toBookItemEdit")
    public String toBookItemEdit(BookItemEditVO bookItemEditVO, Model model) {
        if (bookItemEditVO.getBookItemId() != null) {
            BookItem bookItem = bookItemService.get(bookItemEditVO.getBookItemId());
            DateTime dateTime = new DateTime(bookItemEditVO.getAppointmentTimeStamp());
            Date startTime = dateTime.toDate();
            Date endTime = dateTime.plusMinutes(bookItem.getDuration() + bookItem.getProcessTime()).toDate();

            Shop shop =  bookItem.getBook().getShop();
            List<User> allTherapists = userService.getTherapistsBySkill(shop.getId(), bookItem.getProductOption(),null);
//            System.out.println("allTherapists:" + allTherapists.size());
            Iterator<User> userIterator = allTherapists.iterator();
            boolean isBlock;
            while (userIterator.hasNext()) {
                isBlock = false;
                User user = userIterator.next();
                if (bookItemService.checkBlock(user, startTime, endTime, null)) {
                    userIterator.remove();
                    isBlock = true;
                }
                if (isBlock) {
                    continue;
                }
                if (blockService.checkBlock(shop, user, startTime, endTime)) {
                    userIterator.remove();
                }
            }
            //List<User> notBlockTherapists = allTherapists.stream().filter(therapist -> !blockService.checkBlock(shopId, therapist, startAppointmentTime, endAppointmentTime)).collect(Collectors.toList());   // 可用的技师集合
            for (BookItemTherapist bookItemTherapist : bookItem.getBookItemTherapists()) {
                bookItemEditVO.setTherapist(bookItemTherapist.getUser());
            }

            bookItemEditVO.setBookItem(bookItem);
            bookItemEditVO.setAvailableTherapists(allTherapists);
            bookItemEditVO.setAppointmentTime(startTime);
            bookItemEditVO.setRoom(roomService.get(bookItemEditVO.getRoomId()));
            model.addAttribute("bookItemEditVO", bookItemEditVO);
        }
        return "book/bookItemEdit";
    }

    @RequestMapping("bookItemEditConfirm")
    public String bookItemEditConfirm(BookItemEditVO bookItemEditVO, Model model) {
        if (bookItemEditVO.getBookItemId() != null) {
            BookItem bookItem = bookItemService.get(bookItemEditVO.getBookItemId());
            DateTime dateTime = new DateTime(bookItemEditVO.getAppointmentTimeStamp());
            Date startTime = dateTime.toDate();
            bookItemEditVO.setBookItem(bookItem);
            bookItemEditVO.setTherapist(userService.get(bookItemEditVO.getTherapistId()));
            bookItemEditVO.setAppointmentTime(startTime);
            bookItemEditVO.setRoom(roomService.get(bookItemEditVO.getRoomId()));
            model.addAttribute("bookItemEditVO", bookItemEditVO);
        }
        return "book/bookItemEditConfirm";
    }

    @RequestMapping("bookItemEdit")
    @ResponseBody
    public AjaxForm bookItemEdit(BookItemEditVO bookItemEditVO) {
        if (bookItemEditVO.getBookItemId() == null) {
            return AjaxFormHelper.error().addAlertError("Parameter Error!");
        }

        try {
            bookItemService.updateBookItem(bookItemEditVO.transferToViewVO());
        } catch (Throwable e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }


        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
    }


    // book edit start ------------------------------------------------------------------------------------------

    @RequestMapping("toEdit")
    public String toEdit(Model model, BookVO bookVO) {
        if(bookVO.getId() == null) {
            throw new IllegalArgumentException("Id is required for edit book.");
        }
        Book book = bookService.get(bookVO.getId());
      /*  bookVO.setState(CommonConstant.STATE_EDIT);
        Date startAppointmentTime = bookVO.getStartAppointmentTime();

        bookVO.setStartAppointmentTime(book.getAppointmentTime());
        bookVO.setStartTimeString(new DateTime(book.getAppointmentTime()).toString("HH") + ":00");*/
        model.addAttribute("timeTreeData", CommonConstant.TIME_DATA);
        model.addAttribute("bookVO", bookVO);
        if(book.getBookFirstStepRecords().size() == 0) {
            Set<BookFirstStepRecord> recordSet = book.getBookFirstStepRecords();
            int displayOrder = 1;
            for (BookItem bookItem : book.getBookItems()) {
                BookFirstStepRecord bookFirstStepRecord = new BookFirstStepRecord();
                bookFirstStepRecord.setBook(book);
                bookFirstStepRecord.setProductOption(bookItem.getProductOption());
                bookFirstStepRecord.setGuestAmount(1);
                bookFirstStepRecord.setShareRoom(false);
                bookFirstStepRecord.setStartTime(new DateTime(bookItem.getAppointmentTime()).toString("HH:mm"));
                bookFirstStepRecord.setEndTime(new DateTime(bookItem.getAppointmentEndTime()).toString("HH:mm"));
                bookFirstStepRecord.setDisplayOrder(displayOrder++);
                bookFirstStepRecord.setIsActive(true);
                bookFirstStepRecord.setCreated(bookItem.getCreated());
                bookFirstStepRecord.setCreatedBy(bookItem.getCreatedBy());
                bookFirstStepRecord.setLastUpdated(bookItem.getLastUpdated());
                bookFirstStepRecord.setLastUpdatedBy(bookItem.getLastUpdatedBy());
                bookFirstStepRecord.setBundleId(bookItem.getBundleId());
                recordSet.add(bookFirstStepRecord);
            }
        }
        model.addAttribute("book", book);
        // bookItem合并

        return "book/bookEdit";
    }

    @RequestMapping("editSelectResource")
    public String editSelectResource(BookVO bookVO, Model model, HttpSession session) {
        if(bookVO.getId() == null) {
            throw new IllegalArgumentException("Id is required for edit book.");
        }
        Book book = bookService.get(bookVO.getId());

        List<BookItemVO> bookItemVOs = bookVO.getBookItemVOs();
        DateTime appointDate = new DateTime(bookVO.getStartAppointmentTime());
        String appointDateStr = appointDate.toString("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        Shop shop = shopService.get(bookVO.getShopId());
        List<BookItemVO> newBookItemVOList = new ArrayList<>(); // 新的bookItem集合
        Set<Block> blockRoomSet = new HashSet<>(); // 记录已经分配的room
        Long tempId = 1L;
        Long tempParentId;
        List<BookFirstStepRecordVO> firstStepRecordList = new ArrayList<>(bookItemVOs.size());
        int firstStepIndex = 1;
        for (BookItemVO bookItemVO : bookItemVOs) {
            tempParentId = null; // 重置为null
            ProductOption productOption = productOptionService.get(bookItemVO.getProductOptionId());
            Integer duration = productOption.getDuration();
            if (duration == null) {
                model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
                return "book/bookSelectResource";
            }
            DateTime startDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + bookItemVO.getStartTime());
            Date startAppointment = startDateTime.toDate();
//            Date endAppointment = startDateTime.plusMinutes(duration).toDate(); // end appointment time
            DateTime endDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + bookItemVO.getEndTime());
            Date endAppointment = endDateTime.toDate();
            int guestAmount = bookItemVO.getGuestAmount();
            // 记录first step record
            BookFirstStepRecordVO firstStepRecordVO = new BookFirstStepRecordVO();
            firstStepRecordVO.setProductOptionId(bookItemVO.getProductOptionId());
            firstStepRecordVO.setProductOption(productOption);
            firstStepRecordVO.setGuestAmount(bookItemVO.getGuestAmount());
            firstStepRecordVO.setShareRoom(bookItemVO.getShareRoom());
            firstStepRecordVO.setStartTime(bookItemVO.getStartTime());
//            firstStepRecordVO.setEndTime(startDateTime.plusMinutes(duration + productOption.getProcessTime()).toString("HH:mm"));
            firstStepRecordVO.setEndTime(new DateTime(endAppointment).toString("HH:mm"));
            firstStepRecordVO.setDisplayOrder(firstStepIndex++);
            firstStepRecordVO.setBundleId(bookItemVO.getBundleId());
            firstStepRecordList.add(firstStepRecordVO);

            // 由一条记录拆分成多条bookItem
            Room assignRoom; // 分配的房间
            if (guestAmount > 1) {
                boolean isShareRoom = bookItemVO.getShareRoom() != null && bookItemVO.getShareRoom();
                List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), shop.getId(), startAppointment, endAppointment, book.getBookItems());
                // 全部公用一个room
                Room shareRoom = null;
                if (isShareRoom) {
                    shareRoom = RoomHelper.getAssignRoom(availableRoomList, guestAmount, CommonConstant.ROOM_CAPACITY_RANGE);
                }
                // bookItem 拆分
                for (int i = 0; i < guestAmount; i++) {
                    BookItemVO item = new BookItemVO();
                    item.setTempId(tempId);

                    item.setAppointmentTime(startAppointment);
                    item.setDuration(duration);
                    item.setEndAppointmentTime(endAppointment);
                    item.setBookVO(bookVO);
                    item.setProductOption(productOption);
                    item.setProductOptionId(productOption.getId());
                    if (shareRoom != null) {
                        assignRoom = shareRoom;
                        if(i == 0) {
                            item.setTempParentId(null);
                            tempParentId = item.getTempId();  // 记录父节点id
                        } else {
                            item.setTempParentId(tempParentId);
                        }
                    } else {
                        item.setTempParentId(null);
                        tempParentId = item.getTempId();  // 记录父节点id
                        // 其他都需要单独再分配房间
                        availableRoomList = getNotBlockRoomList(availableRoomList, blockRoomSet, startAppointment, endAppointment);
                        assignRoom = RoomHelper.getAssignRoom(availableRoomList, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
                    }

                    if (assignRoom != null) {
                        item.setRoom(assignRoom);
                        blockRoomSet.add(addBlockRoom(assignRoom, startAppointment, endAppointment));
                    }
                    newBookItemVOList.add(item);
                    tempId++;
                }
            } else {
                bookItemVO.setTempId(tempId);
                tempId++;
                bookItemVO.setTempParentId(null);
                bookItemVO.setAppointmentTime(startAppointment);
                bookItemVO.setDuration(duration);
                bookItemVO.setEndAppointmentTime(endAppointment);
                bookItemVO.setBookVO(bookVO);
                bookItemVO.setProductOption(productOption);
                // 分配room
                List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), shop.getId(), startAppointment, endAppointment, book.getBookItems());
                logger.debug("Available rooms: {}", availableRoomList);
                availableRoomList = getNotBlockRoomList(availableRoomList, blockRoomSet, startAppointment, endAppointment);
                assignRoom = RoomHelper.getAssignRoom(availableRoomList, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
                if (assignRoom != null) {
                    bookItemVO.setRoom(assignRoom);
                    blockRoomSet.add(addBlockRoom(assignRoom, startAppointment, endAppointment));
                }
                newBookItemVOList.add(bookItemVO);
            }
        }
        bookVO.setBookItemVOs(newBookItemVOList);
        //model.addAttribute("waiting", waiting);
        // 保存recordVOs到session
        session.setAttribute(BOOK_FIRST_STEP_RECORDS, firstStepRecordList);
        model.addAttribute("book", book);
        model.addAttribute("bookVO", bookVO);
        User user = userService.get(bookVO.getMemberId());
        if (user.getUser() != null) {
            model.addAttribute("preferTherapistId", user.getUser().getId());
        }
        return "book/bookEditSelectResource";
    }
    @RequestMapping("reallocateRoomResource")
    public String reallocateRoomResource(BookVO bookVO, Model model, HttpSession session) {
        if(bookVO.getId() == null) {
            throw new IllegalArgumentException("Id is required for edit book.");
        }
        Book book = bookService.get(bookVO.getId());
        Set<BookItem> originalSets = book.getBookItems();
        List<BookItemVO> bookItemVOs = bookVO.getBookItemVOs();
        DateTime appointDate = new DateTime(bookVO.getStartAppointmentTime());
        String appointDateStr = appointDate.toString("yyyy-MM-dd");
        DateTimeFormatter dateTimeFormatter = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        List<Long> productIdList = bookItemVOs.stream().map(e -> e.getProductId()).distinct().collect(Collectors.toList());
        List<Long> productOptionIdsList = bookItemVOs.stream().map(e -> e.getProductOptionId()).distinct().collect(Collectors.toList());
        List<BookFirstStepRecordVO> firstStepRecordList = new ArrayList<>(bookItemVOs.size());
        Map<Long, BookItemVO> bookItemVoMap = bookItemVOs.stream().collect(Collectors.toMap(BookItemVO::getBookItemIdex, key -> key,(val1,val2)->val1));
        Map<Long,Set<String>> blockRoomMap = new HashMap<Long,Set<String>>();
        Map<Long,Integer> productDurationMap = new HashMap<Long,Integer>();
        Map<Long, Long> productOptionIdToProductIdMap = new HashMap<Long,Long>();
        Map<String, List<Long>> shareProductIds = new HashMap<String,List<Long>>();
        Map<String,String> timeToBookItemIndexMap = new HashMap<>();
        Map<String,Integer> timeToGuestNumMap = new HashMap<>();
        Long tempId = 1L;
        Long tempParentId;
        int firstStepIndex = 1;
        List<BookItemVO> newBookItemVOList = new ArrayList<>(); // 新的bookItem集合
        Map<Boolean,List<BookItemVO>> isShareRoomMap = bookItemVOs.stream().collect(Collectors.groupingBy(BookItemVO::getShareRoom));
        for(BookItemVO vo:bookItemVOs) {
        	productOptionIdToProductIdMap.put(vo.getProductOptionId(), vo.getProductId());
        	String startTime = vo.getStartTime();
        	if(vo.getShareRoom()) {
        		List<Long> productIds = shareProductIds.get(startTime);
        		String indexStr = timeToBookItemIndexMap.get(startTime);
        		Integer guestNum = timeToGuestNumMap.get(startTime);
        		if(StringUtils.isBlank(indexStr)) {
        			indexStr = vo.getBookItemIdex().toString();
        			guestNum = vo.getGuestAmount();
        		}else {
        			indexStr = indexStr.concat("~").concat(vo.getBookItemIdex().toString());
        			guestNum += vo.getGuestAmount();
        		}
        		timeToGuestNumMap.put(startTime,guestNum);
        		timeToBookItemIndexMap.put(startTime,indexStr);
        		if(Objects.isNull(productIds)) {
        			productIds = new ArrayList<>();
        		}
        		productIds.add(vo.getProductOptionId());
        		shareProductIds.put(startTime, productIds);
//        		productDurationMap.put(vo.getProductId(), vo.getDuration());
        	}
        }
        List<String> productOptionAttributeStr = new ArrayList<String>();
        productOptionAttributeStr.add(CommonConstant.PRODUCT_OPTION_KEY_PROCESS_TIME_REF);
        productOptionAttributeStr.add(CommonConstant.PRODUCT_OPTION_KEY_DURATION_REF);
        productDurationMap = productOptionService.getDurationByProductOptionIds(WebThreadLocal.getCompany().getId(),productOptionIdsList,productOptionAttributeStr);
        List<RoomTreatments> roomTreatments = roomTreatmentService.getRoomByShopAndProductOptionIds(bookVO.getShopId(), productIdList);
        // 能够提供某种 treatment 的房间列表
        Map<Long, Set<Room>> productRoomMap = new HashMap<Long, Set<Room>>(); 
        for(RoomTreatments roomTreatment : roomTreatments) {
        	Long productId = roomTreatment.getProduct().getId();
        	Set<Room> list = productRoomMap.get(productId);
        	if(Objects.isNull(list)) {
        		list = new HashSet<Room>();
        	}
        	list.add(roomTreatment.getRoom());
        	productRoomMap.put(productId, list);
        }

        List<Block> roomBlocks = blockService.checkRoomBlock(bookVO.getShopId(), null, appointDate.toDate(), null);
        for(Block block : roomBlocks) {
        	String startTimeStr = new DateTime(block.getStartDate()).toString("HH:mm");
        	String endTimeStr = new DateTime(block.getEndDate()).toString("HH:mm");
        	Set<String> blockSets = blockRoomMap.get(block.getRoom().getId());
        	if(Objects.isNull(blockSets)) {
        		blockSets = new HashSet<>();
        		blockRoomMap.put(block.getRoom().getId(), blockSets);
        	}
        	blockSets.add(startTimeStr.concat("~").concat(endTimeStr));
        }
        List<BookItem> bookItemLists = bookItemService.getBookItemList(bookVO.getShopId(), appointDate.toDate(), DateUtil.getEndTime(appointDate.toDate()));
        if(Objects.nonNull(bookItemLists)) {
        	for(BookItem bi : bookItemLists) {
        		if(originalSets.contains(bi)) {
        			continue;
        		}
        		Room room = bi.getRoom();
        		if(Objects.nonNull(room)) {
                	String startTimeStr = new DateTime(bi.getAppointmentTime()).toString("HH:mm");
                	String endTimeStr = new DateTime(bi.getAppointmentEndTime()).toString("HH:mm");
                	Set<String> blockSets = blockRoomMap.get(room.getId());
                	if(Objects.isNull(blockSets)) {
                		blockSets = new HashSet<>();
                		blockRoomMap.put(room.getId(), blockSets);
                	}
                	blockSets.add(startTimeStr.concat("~").concat(endTimeStr));
        		}
        	}
        }
        Boolean isSameTimeToShareRoom = bookVO.getSameTimeToShareRoom();
        // 以上是前期数据准备
        // 以下是房间分配实现
        List<BookItemVO> shareRoomBookItemList = isShareRoomMap.get(true);
        if(Objects.nonNull(isSameTimeToShareRoom) && isSameTimeToShareRoom) {
        	bookVO.setSameTimeToShareRoom(true);
        	// 把同一时间的不同treatment尽量安排在同一个房,不能安排就全挂起来
        	if(Objects.nonNull(shareRoomBookItemList)) {
        		for(Entry<String, List<Long>> entry: shareProductIds.entrySet()) {
        			String key = entry.getKey();
        			List<Long> productIds = entry.getValue(); 
                    Integer totalGuestAmount = timeToGuestNumMap.get(key);
                    // 提供各种treatments的房间的交集
        			Set<Room> intersection = null;
        			List<Set<Room>> allRooms = new ArrayList<>();
        			Integer maxDuration = null;
                    for(Long pId : productIds) {
                    	Long productId = productOptionIdToProductIdMap.get(pId);
                    	if(Objects.isNull(intersection)) {
                    		if(Objects.nonNull(productRoomMap.get(productId))) {
                    			intersection = new HashSet<>(productRoomMap.get(productId));
                    		}else {
                    			intersection = new HashSet<>();
                    		}
                    		allRooms.add(intersection);
                    	}else {
                    		allRooms.add(productRoomMap.get(productId));
                    	}
                    	if(null == maxDuration) {
                    		maxDuration = productDurationMap.get(pId);
                    	}else {
                    		// 取出最长treatment耗时
                    		if(maxDuration < productDurationMap.get(pId)) {
                    			maxDuration = productDurationMap.get(pId);
                    		}
                    	}
                    }
                    // productOptionShareRoomList treatment对应的共用的房间的交集
                    Set<Room> productOptionShareRoomList = allRooms.parallelStream()
            				.filter(elementList -> elementList != null && ((Set) elementList).size() != 0).reduce((a, b) -> {
            					a.retainAll(b);
            					return a;
            				}).orElse(Collections.emptySet());
                    
    				Room shareRoom = null;
    				List<Room> usefulRooms = null;
    				
    		        DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern("HH:mm");
    		        DateTime keyDateTime = formatter.parseDateTime(key);
    		        DateTime endDateTime = keyDateTime.plusMinutes(maxDuration);
    				
	        		if(Objects.nonNull(productOptionShareRoomList)) {
	        			usefulRooms = getAvailableRooms(productOptionShareRoomList, blockRoomMap, key, endDateTime.toString("HH:mm"));
	        			shareRoom = RoomHelper.getAssignRoom(usefulRooms, totalGuestAmount, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
	        		}
	        		if(Objects.nonNull(shareRoom)) {
	                	Set<String> sets = blockRoomMap.get(shareRoom.getId());
	                	if(Objects.isNull(sets)) {
	                		sets = new HashSet<>();
	                	}
	                	sets.add(key.concat("~").concat(endDateTime.toString("HH:mm")));
	                	blockRoomMap.put(shareRoom.getId(), sets);
	        		}
	        		String bookItemIndexStr = timeToBookItemIndexMap.get(key);
	        		String[] indexStrs = bookItemIndexStr.split("~");
	        		for (int i = 0; i < indexStrs.length; i++) {
	        			Long bookItemIndex = Long.valueOf(indexStrs[i]);
	        			BookItemVO vo = bookItemVoMap.get(bookItemIndex);
	    	            ProductOption productOption = productOptionService.get(vo.getProductOptionId());
	    	            Integer duration = productOption.getDuration();
	    	            if (null == duration) {
	    	                model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
	    	                return "book/bookSelectResource";
	    	            }
	    	            DateTime beginDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + vo.getStartTime());
	    	            DateTime finishDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + vo.getEndTime());
	    	            BookFirstStepRecordVO bfsrVO = createBookFirstStepRecordVO(vo,productOption,finishDateTime.toDate(),firstStepIndex);
	    	            firstStepRecordList.add(bfsrVO);
	    	            firstStepIndex++;
	    	            Integer guestNum = vo.getGuestAmount();
	    	            tempParentId = null;
	    	            for(Integer j = 0; j < guestNum ; j++) {
	    	            	BookItemVO newVO = new BookItemVO();
	    					newVO.setTempId(tempId);
	    					newVO.setBundleId(vo.getBundleId());
	    					newVO.setProductId(vo.getProductId());
	    	                newVO.setAppointmentTime(beginDateTime.toDate());
	    	                newVO.setDuration(productDurationMap.get(vo.getProductId()));
	    	                newVO.setEndAppointmentTime(finishDateTime.toDate());
	    	                newVO.setBookVO(bookVO);
	    	                newVO.setProductOption(productOption);
	                        if(0 == j) {
	                        	newVO.setTempParentId(null);
	                        	tempParentId = tempId;
	                        }else {
	                        	newVO.setTempParentId(tempParentId);
	                        }
	                        newVO.setRoom(shareRoom);
	    	                newBookItemVOList.add(newVO);
	    	                tempId++;
	    	            }
					}
        		}
        	}
        	
        }else {
        	bookVO.setSameTimeToShareRoom(false);
        	if(Objects.nonNull(shareRoomBookItemList)) {
        		for (BookItemVO bookItemVO : shareRoomBookItemList) {
        			tempParentId = null;
        			Integer guestNums = bookItemVO.getGuestAmount();
    	            ProductOption productOption = productOptionService.get(bookItemVO.getProductOptionId());
    	            Integer duration = productOption.getDuration();
    	            if (duration == null) {
    	                model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
    	                return "book/bookSelectResource";
    	            }
            		String startTimeStr = bookItemVO.getStartTime();
            		String endTimeStr = bookItemVO.getEndTime();
            		DateTime startDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + startTimeStr);
            		DateTime endDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + endTimeStr);

    	            BookFirstStepRecordVO bfsrVO = createBookFirstStepRecordVO(bookItemVO,productOption,endDateTime.toDate(),firstStepIndex);
    	            firstStepRecordList.add(bfsrVO);
    	            firstStepIndex++;
            		
    				Set<Room> rooms = productRoomMap.get(bookItemVO.getProductId());
    				Room shareRoom = null;
    				List<Room> usefulRooms = null;
	        		if(Objects.nonNull(rooms)) {
	        			usefulRooms = getAvailableRooms(rooms, blockRoomMap, startTimeStr, endTimeStr);
	        			shareRoom = RoomHelper.getAssignRoom(usefulRooms, guestNums, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
	        		}
    				Room assignRoom = null;
    				for(Integer i = 0;i < guestNums;i++ ) {
    	                
    					BookItemVO newVO = new BookItemVO();
    					newVO.setBundleId(bookItemVO.getBundleId());
    					newVO.setProductId(bookItemVO.getProductId());
    					newVO.setTempId(tempId);
    	                newVO.setAppointmentTime(startDateTime.toDate());
    	                newVO.setDuration(productDurationMap.get(bookItemVO.getProductId()));
    	                newVO.setEndAppointmentTime(endDateTime.toDate());
    	                newVO.setBookVO(bookVO);
    	                newVO.setProductOption(productOption);
    	                // 第一个bookItemVo不设置父id
                        if(0 == i) {
                        	newVO.setTempParentId(null);
                        	tempParentId = tempId;
                        }else {
                        	newVO.setTempParentId(tempParentId);
                        }
                        // 
		        		if(Objects.nonNull(shareRoom)) {
    	                	assignRoom = shareRoom;
    	                	newVO.setRoom(assignRoom);
    	                }else {
    	                	// 找单个位置的房间分配
    	                	assignRoom = RoomHelper.getAssignRoom(usefulRooms, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
    		        		if(Objects.nonNull(assignRoom)) {
        	                	newVO.setRoom(assignRoom);
        	                	usefulRooms.remove(assignRoom);
    		        		}
    	                }
		        		if(Objects.nonNull(assignRoom)) {
    	                	Set<String> sets = blockRoomMap.get(assignRoom.getId());
    	                	if(Objects.isNull(sets)) {
    	                		sets = new HashSet<>();
    	                	}
    	                	newVO.setRoom(assignRoom);
    	                	sets.add(startTimeStr.concat("~").concat(endTimeStr));
    	                	if(Objects.nonNull(shareRoom)) {
    	                		blockRoomMap.put(shareRoom.getId(), sets);
    	                	}
    	                	
		        		}
    	                newBookItemVOList.add(newVO);
    	                tempId++;
    				}
    				
				}
        	}
        }        
//--------------------------------------------------------------------------------------------------------//       
        // 处理不共用房间的bookitem , 即单独分一个房间
        List<BookItemVO> noShareRoomList = isShareRoomMap.get(false);
        if(Objects.nonNull(noShareRoomList)) {
        	for (BookItemVO bookItemVO : noShareRoomList) {
	            ProductOption productOption = productOptionService.get(bookItemVO.getProductOptionId());
	            Integer duration = productOption.getDuration();
	            if (duration == null) {
	                model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
	                return "book/bookSelectResource";
	            }
        		String startTimeStr = bookItemVO.getStartTime();
        		String endTimeStr = bookItemVO.getEndTime();
        		DateTime startDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + startTimeStr);
        		DateTime endDateTime = dateTimeFormatter.parseDateTime(appointDateStr + " " + endTimeStr);

	            BookFirstStepRecordVO bfsrVO = createBookFirstStepRecordVO(bookItemVO,productOption,endDateTime.toDate(),firstStepIndex);
	            firstStepRecordList.add(bfsrVO);
	            firstStepIndex++;
				Set<Room> rooms = productRoomMap.get(productOption.getProduct().getId());
				Room assignRoom = null;
				for(Integer i = 0;i < bookItemVO.getGuestAmount();i++ ) {
					if(Objects.nonNull(rooms)) {
		        		List<Room> usefulRooms = getAvailableRooms(rooms, blockRoomMap, startTimeStr, endTimeStr);
		        		assignRoom = RoomHelper.getAssignRoom(usefulRooms, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
					}
					BookItemVO newVO = new BookItemVO();
					newVO.setTempId(tempId);
	                tempId++;
	                newVO.setTempParentId(null);
	                newVO.setBundleId(bookItemVO.getBundleId());
	                newVO.setAppointmentTime(startDateTime.toDate());
	                newVO.setDuration(productDurationMap.get(bookItemVO.getProductId()));
	                newVO.setEndAppointmentTime(endDateTime.toDate());
	                newVO.setBookVO(bookVO);
	                newVO.setProductId(bookItemVO.getProductId());
	                newVO.setProductOption(productOption);
	                if(Objects.nonNull(assignRoom)) {
	                	Set<String> lists = blockRoomMap.get(assignRoom.getId());
	                	if(Objects.isNull(lists)) {
	                        lists = new HashSet<>();
	                	}
	                	lists.add(startTimeStr.concat("~").concat(endTimeStr));
	                	blockRoomMap.put(assignRoom.getId(), lists);
	                	newVO.setRoom(assignRoom);
	                }
	                newBookItemVOList.add(newVO);
				}
			}
        }
        // 保存recordVOs到session
        session.setAttribute(BOOK_FIRST_STEP_RECORDS, firstStepRecordList);
        bookVO.setBookItemVOs(newBookItemVOList);
        //model.addAttribute("waiting", waiting);
        model.addAttribute("bookVO", bookVO);
        User user = userService.get(bookVO.getMemberId());
        if (user.getUser() != null) {
            model.addAttribute("preferTherapistId", user.getUser().getId());
        }
        return "book/bookEditSelectResource";
    }
    @RequestMapping("editConfirm")
    public String editConfirm(BookVO bookVO, Model model) {
        Shop shop = shopService.get(bookVO.getShopId());
        User member = userService.get(bookVO.getMemberId());
        model.addAttribute("shop", shop);
        model.addAttribute("member", member);
        for (BookItemVO bookItemVO : bookVO.getBookItemVOs()) {
            bookItemVO.setProductOption(productOptionService.get(bookItemVO.getProductOptionId()));
            bookItemVO.setDuration(bookItemVO.getProductOption().getDuration());

            for(RequestTherapistVO therapistVO : bookItemVO.getRequestTherapistVOs()) {
                if(therapistVO.getTherapistId() != null) {
                    therapistVO.setTherapist(userService.get(therapistVO.getTherapistId()));
                }
            }

            if(bookItemVO.getRoomId() != null) {
                bookItemVO.setRoom(roomService.get(bookItemVO.getRoomId()));
            }
        }
        model.addAttribute("bookVO", bookVO);
        return "book/bookEditConfirm";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm edit(@Valid BookVO bookVO, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        }
        if (bookVO.getId() == null) {
            throw new IllegalArgumentException("Id is required for edit book.");
        }
        try {
            bookVO.setFirstStepRecordVOList((List<BookFirstStepRecordVO>) session.getAttribute(BOOK_FIRST_STEP_RECORDS));
            bookService.edit(bookVO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
    }

    // book edit start ------------------------------------------------------------------------------------------

    @RequestMapping("changeStatusForDoubleBooking")
	public String changeStatusForDoubleBooking(Long bookItemId,String status,Model model) {
		
		BookItem bookItem=bookItemService.get(bookItemId);
		List<BookItem> childrens =bookItem.getChildrenOfDoubleBooking();

		model.addAttribute("parentBookItem",bookItem);
		model.addAttribute("childrens",childrens);
		model.addAttribute("status",status);
	  
		return "book/doubleBookingChangeStatus";
    }
    
    @RequestMapping("saveStatusForDoubleBooking")
    @ResponseBody
    public AjaxForm saveStatusForDoubleBooking(DoubleBookingVO doubleBookingVO) {
    	Long[] bookItemIds = doubleBookingVO.getBookItemIds();
    	String status = doubleBookingVO.getStatus();
    	if(bookItemIds !=null && bookItemIds.length>0){
    		for(Long biId : bookItemIds){
    			try {
    			    if (doubleBookingVO.getStatus().equals(CommonConstant.BOOK_STATUS_CANCEL)){
                     BookItem bookItem= bookItemService.get(biId);

    			        if (bookItem!=null&&bookItem.getIsDoubleBooking()&&bookItem.getDoubleBookingParentId()==null){
                            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookItem.class);
                            detachedCriteria.add(Restrictions.ne("status", CommonConstant.BOOK_STATUS_CANCEL));
                            detachedCriteria.add(Restrictions.eq("doubleBookingParentId", bookItem.getId()));
                            detachedCriteria.addOrder(Order.asc("appointmentTime"));
                            List<BookItem> bookItems =bookItemService.list(detachedCriteria);
                            if (bookItems!=null&&bookItems.size()>0){
                                return AjaxFormHelper.error().addAlertError("Please delete all child nodes of the booking and remove the parent.");
                            }
                        }
                        bookItemService.updateCancelStatus(biId, status);

                    }else {
                        bookItemService.updateStatus(biId, status);

                    }

    			} catch (RuntimeException e) {
	               e.printStackTrace();
	               return AjaxFormHelper.error().addAlertError(e.getMessage());
    			}
    		}
    	}
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
    }
    @RequestMapping("toChooseBundle")
    public String toChooseBundle(Model model, Long shopId,String bookingDate,String startTime) {

    	Date now = DateUtil.stringToDate(bookingDate, "yyyy-MM-dd");
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProductBundle.class);
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.ge("endTime", now));
        detachedCriteria.add(Restrictions.le("startTime", now));
        detachedCriteria.createAlias("shops", "shops");
        detachedCriteria.add(Restrictions.eq("shops.id", shopId));
        List<ProductBundle> bundleList = bundleService.list(detachedCriteria);
        model.addAttribute("bundleList", bundleList);
        model.addAttribute("startTime",startTime);
        model.addAttribute("shopId",shopId);
        return "book/bundleChoose";
    }

    @RequestMapping("chooseBundle")
    public String chooseBundle(String startAppointmentTime,Long shopId,Model model, Long bundleGroup1,
    		Long bundleGroup2, Long bundleId,String startTime,String isSameTimeToShareRoom) {
    	
    	ProductOption po1 = productOptionService.get(bundleGroup1);
    	ProductOption po2 = productOptionService.get(bundleGroup2);
    	
        model.addAttribute("po1", po1);
        model.addAttribute("po2", po2);
        model.addAttribute("bundleId", bundleId);
        DateTime startTimeForBundle; 
        if(StringUtils.isNotBlank(startTime)){
        	Date date = DateUtil.stringToDate(startAppointmentTime.concat(" ").concat(startTime), "yyyy-MM-dd HH:mm");
        	startTimeForBundle = new DateTime(date);
        }else{
        	startTimeForBundle = new DateTime();
        }

        Integer min = startTimeForBundle.getMinuteOfHour();
        Integer differ = min % 5;
        if(!Objects.equals(0, differ)) {
            startTimeForBundle = startTimeForBundle.plusMinutes(5 - differ);
        }
        model.addAttribute("po1StartTime",startTimeForBundle.toString("HH:mm"));
        Integer po1Duration = po1.getDuration() + po1.getProcessTime();
        DateTime po1EndTime = startTimeForBundle.plusMinutes(po1Duration);
        model.addAttribute("po1EndTime",po1EndTime.toString("HH:mm"));
        model.addAttribute("po1Duration",po1Duration);
        model.addAttribute("po1Id",po1.getProduct().getId());
        
        
        model.addAttribute("po2StartTime",po1EndTime.toString("HH:mm"));
        Integer po2Duration = po2.getDuration() + po2.getProcessTime();
        DateTime po2EndTime = po1EndTime.plusMinutes(po2Duration);
        model.addAttribute("po2EndTime",po2EndTime.toString("HH:mm"));
        model.addAttribute("po2Duration",po2Duration);
        model.addAttribute("po2Id",po2.getProduct().getId());
        
        model.addAttribute("timeTreeData", CommonConstant.TIME_DATA);
        model.addAttribute("timestamp", System.currentTimeMillis());
        if(StringUtils.isBlank(isSameTimeToShareRoom) || Objects.equals("NO", isSameTimeToShareRoom)) {
        	model.addAttribute("isSameTimeToShareRoom", "NO");
        }else {
        	model.addAttribute("isSameTimeToShareRoom", "YES");
        }
        
        return "book/bundleChooseList";
    }

    @RequestMapping("toQuickEdit")
    public String toQuickEdit(Model model, BookQuickVO bookQuickVO) {
        Long therapistId = bookQuickVO.getTherapistId();
        if(therapistId == null) {
            throw new RuntimeException("Parameter error!");
        }
        User therapist = userService.get(therapistId);
        bookQuickVO.setTherapist(therapist);
        Date startAppointmentTime = new Date(bookQuickVO.getStartTimeStamp());
        bookQuickVO.setStartAppointmentTime(startAppointmentTime);
        User member = userService.get(bookQuickVO.getMemberId());
        if (member != null && member.isMember()) {
            bookQuickVO.setMember(member);
        }

        Book book = bookService.get(bookQuickVO.getBookId());
        if (book != null) {
            bookQuickVO.setBook(book);
        }

        Long productOptionId = bookQuickVO.getProductOptionId();
        ProductOption productOption = productOptionService.get(productOptionId);
        Guest guest = bookQuickVO.getBook().getGuest();
        model.addAttribute("guest", guest);
        model.addAttribute("productOption", productOption);
        model.addAttribute("bookQuickVO", bookQuickVO);
        model.addAttribute("book", book);
        return "book/bookQuickEdit";
    }

    @RequestMapping("bookQuickEditConfirm")
    public String bookQuickEditConfirm(BookQuickVO bookQuickVO, Model model) {
        User member = userService.get(bookQuickVO.getMemberId());
        model.addAttribute("member", member);
        User therapist = userService.get(bookQuickVO.getTherapistId());
        bookQuickVO.setTherapist(therapist);
        ProductOption productOption = productOptionService.get(bookQuickVO.getProductOptionId());
        bookQuickVO.setProductOption(productOption);
        Integer duration = productOption.getDuration();
        if (duration == null) {
            model.addAttribute("error", "Product[" + productOption.getLabel2() + "] do not has duration!");
            return "book/bookQuickEditConfirm";
        }
        Date startAppointment = null;
        if(bookQuickVO.getDoubleBooking() !=null && bookQuickVO.getDoubleBooking()){
            startAppointment = DateUtil.stringToDate(bookQuickVO.getBookingDate()+" "+bookQuickVO.getStartTime(), "yyyy-MM-dd HH:mm");
            bookQuickVO.setStartTimeStamp(new DateTime(startAppointment).getMillis());
        }else{
            startAppointment = new Date(bookQuickVO.getStartTimeStamp());
        }
        Book book = null;
        Room room = null;
        if(Objects.nonNull(bookQuickVO.getBookId())) {
        	book = bookService.get(bookQuickVO.getBookId());
            BookItem bi = book.getBookItems().iterator().next();
            room = bi.getRoom();
        }
    	Integer newProductOptionCostTime = productOption.getDuration();
    	if(Objects.nonNull(productOption.getProcessTime())) {
    		newProductOptionCostTime = newProductOptionCostTime + productOption.getProcessTime();
    	}
        bookQuickVO.setStartAppointmentTime(startAppointment);
        DateTime startDateTime = new DateTime(startAppointment);
        Date endAppointment = startDateTime.plusMinutes(duration).toDate(); // end appointment time
        // 分配房间
        if(Objects.isNull(room) || !room.getAllTreatmentIds().contains(productOption.getProduct().getId())) {
            List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), bookQuickVO.getShopId(), startAppointment, endAppointment);

            Collections.sort(availableRoomList, new Comparator<Room>() {
                @Override
                public int compare(Room r1, Room r2) {
                    return r2.getSort().compareTo(r1.getSort());
                }
            });

            List<Room> assignRoomList = RoomHelper.assignRoom(availableRoomList, 1); // 分配一个人的房间

            if (assignRoomList.size() == 0 || assignRoomList.get(0) == null) {
                logger.debug("No available room found!"); 
                bookQuickVO.setStatus(CommonConstant.BOOK_STATUS_WAITING);
            } else {
                bookQuickVO.setAssignRoom(assignRoomList.get(0));
                bookQuickVO.setStatus(CommonConstant.BOOK_STATUS_CONFIRM);
            }
        }else {
        	List<BookItem> lists = bookItemService.getBookItemsByRoomId(book.getShop().getId(), room.getId(), startAppointment, null);
        	BookItem bookItem = null;
        	if(Objects.nonNull(lists) && 1 < lists.size()) {
        		bookItem = lists.get(1);
        		Date date = bookItem.getAppointmentTime();
        		Integer min = (int) ((date.getTime() - startAppointment.getTime())/60000);
        		if( newProductOptionCostTime <= min) {
        			bookQuickVO.setAssignRoom(room);
        		}else {
                    List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), bookQuickVO.getShopId(), startAppointment, endAppointment);

                    Collections.sort(availableRoomList, new Comparator<Room>() {
                        @Override
                        public int compare(Room r1, Room r2) {
                            return r2.getSort().compareTo(r1.getSort());
                        }
                    });

                    List<Room> assignRoomList = RoomHelper.assignRoom(availableRoomList, 1); // 分配一个人的房间

                    if (assignRoomList.size() == 0 || assignRoomList.get(0) == null) {
                        logger.debug("No available room found!"); 
                        bookQuickVO.setStatus(CommonConstant.BOOK_STATUS_WAITING);
                    } else {
                        bookQuickVO.setAssignRoom(assignRoomList.get(0));
                        bookQuickVO.setStatus(CommonConstant.BOOK_STATUS_CONFIRM);
                    }
        		}
        		
        	}else {
        		bookQuickVO.setAssignRoom(room);
        	}
    	}
        
        model.addAttribute("bookQuickVO", bookQuickVO);
        return "book/bookQuickEditConfirm";
    }

    @RequestMapping("bookQuickEdit")
    @ResponseBody
    public AjaxForm bookQuickEdit(BookQuickVO bookQuickVO) {
        try {
            BookVO bookVo = bookQuickVO.transferToBookVO();
            bookVo.setId(bookQuickVO.getBookId());
            bookService.edit(bookVo);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
    }
    
    @RequestMapping("printTherapistBookTimeViewToPDF")
    public void printTherapistBookTimeViewToPDF(Long startDateTimeStamp,Long shopId,HttpServletRequest request,HttpServletResponse response) {
		
		String downloadFileName = RandomUtil.generateRandomNumberWithDate("V-")+".pdf";
		Map<String, Object> map = new HashMap<>();
		map.put("startDateTimeStamp", startDateTimeStamp);
		map.put("shopId", shopId);
		try {
	        File downloadFile = PDFUtil.convert(PRINT_THERAPIST_BOOK_TIME_VIEW_URL, request, map);
	        ServletUtil.download(downloadFile, downloadFileName, response);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

    }
	@RequestMapping("bookTimeTherapistViewTemplate")
	public String bookTimeTherapistViewTemplate(Long startDateTimeStamp,Long shopId,String test,Model model,ViewVO viewVO) {
		
		viewVO.setViewType(CommonConstant.THERAPIST_VIEW);
        try {
            bookItemService.updateBookItem(viewVO);
        } catch (ResourceException re) {
            re.printStackTrace();
            model.addAttribute("error", re.getMessage());
        }

        // 计算最早的和最晚的时间
        // 每个点的开门时间,和关门时间
        //  Long shopId = viewVO.getShopId();
        // shopList
        Company company= WebThreadLocal.getCompany();
        List<Shop> shopList = shopService.getListByCompany(company.getId(), false);
        if (shopId == null) {
            shopId = ((Shop)shopList.get(0)).getId();
            viewVO.setShopId(shopId);
        }
        Shop shop = shopService.get(shopId);
        Date currentTime;            
        viewVO.setAppointmentTimeStamp(startDateTimeStamp);
        if (viewVO.getAppointmentTimeStamp() != null) {
            currentTime = new Date(viewVO.getAppointmentTimeStamp());
        } else {
            currentTime = new Date();
            viewVO.setAppointmentTimeStamp(currentTime.getTime());
        }
        OpeningHours openingHours = shop.getOpeningHour(currentTime);
        long openTimeMillis = openingHours.getOpenTimeObj().getMillis();
        long closeTimeMillis = openingHours.getCloseTimeObj().getMillis();

        List<CellVO> cellVOList = bookItemService.transferBookItemsToCell(viewVO); // 包括block,common booking and double booking
        Map<String, CellVO> cellVOMap = new HashMap<>();
        
        long currentTimeMillis;
        for (CellVO newCellVO : cellVOList) {
            currentTimeMillis = newCellVO.getTime().getMillis();
            openTimeMillis = (currentTimeMillis < openTimeMillis) ? currentTimeMillis : openTimeMillis;
            closeTimeMillis = (currentTimeMillis > closeTimeMillis) ? currentTimeMillis : closeTimeMillis;
            String key = newCellVO.getTime().getMillis() + "," + newCellVO.getTherapist().getId();
            cellVOMap.put(key, newCellVO);
        }
        model.addAttribute("shopList", shopList);
        model.addAttribute("firstShopId",shopId);
        
        DateTime startDateTime = new DateTime(openTimeMillis);
        DateTime endDateTime  = new DateTime(closeTimeMillis);
        model.addAttribute("startDateTime", startDateTime); // 开始時間
        model.addAttribute("endDateTime", endDateTime); // 结束時間
        viewVO.setStartTime(startDateTime);
        viewVO.setEndTime(endDateTime);
        List<User> therapistList=  staffHomeShopDetailsService.getTherapistByShop(shopId);
        
        model.addAttribute("therapistList", therapistList);
        model.addAttribute("viewVO", viewVO);
        model.addAttribute("cellVOMap", cellVOMap);
        model.addAttribute("shopInfo", shop);

		return "book/bookTimeTherapistViewTemplate";
	}

}