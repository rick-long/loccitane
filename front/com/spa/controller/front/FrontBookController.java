package com.spa.controller.front;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.helper.RoomHelper;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.spa.model.book.Block;
import org.spa.model.book.Book;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.utils.DateUtil;
import org.spa.utils.PropertiesUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.book.BookListVO;
import org.spa.vo.book.RequestTherapistVO;
import org.spa.vo.front.book.FrontBookItemVO;
import org.spa.vo.front.book.FrontBookVO;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("front/book")
public class FrontBookController extends BaseController {

    public static final String FRONT_BOOK_ITEM_LIST = "FRONT_BOOK_ITEM_LIST";

    @RequestMapping("list")
    public String list(Model model, BookListVO bookListVO) {
        String status = bookListVO.getStatus();
        Long memberId = WebThreadLocal.getUser().getId();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Book.class);
        detachedCriteria.add(Restrictions.eq("user.id", memberId));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        if (StringUtils.isNotBlank(status)) {
            detachedCriteria.createAlias("bookItems", "bi");
            detachedCriteria.add(Restrictions.eq("bi.status", status));
        }
        Page<Book> page = bookService.list(detachedCriteria, bookListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(bookListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "front/book/bookList";
    }

    @RequestMapping(value = "toAdd")
    public String toAdd(FrontBookItemVO frontBookItemVO, Model model, HttpSession session) {
        List<Shop> shopList = shopService.getListByCompany(null, false, false, true,true);
        Long shopId = frontBookItemVO.getShopId();
        if (shopId == null) {
            /*frontBookItemVO.setShopId(WebThreadLocal.getUser().getShop().getId());*/
            frontBookItemVO.setShopId(shopList.get(0).getId());
        }
        
        if (frontBookItemVO.getAppointmentDate() == null) {
            frontBookItemVO.setAppointmentDate(new Date());
        }
        model.addAttribute("shopList", shopList);
        model.addAttribute("frontBookItemVO", frontBookItemVO);
        Shop currentShop = shopService.get(frontBookItemVO.getShopId());
        model.addAttribute("currentShop", currentShop);

        List<FrontBookItemVO> bookItemVOList = (List<FrontBookItemVO>) session.getAttribute(FRONT_BOOK_ITEM_LIST);
        Boolean submitStatus=false;
        if (bookItemVOList!=null&&bookItemVOList.size()>0){
            for (FrontBookItemVO frontBookItemVO1:bookItemVOList){
                if(frontBookItemVO1.getStatus().equals("WAITING")){
                    submitStatus=true;
                    break;
                }
            }
        }
        model.addAttribute("submitStatus", submitStatus);
        model.addAttribute("bookItemVOList", bookItemVOList);
        return "front/book/bookAdd";
    }

    /**
     * 加载 book item list page
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("ajaxBookItemList")
    public String ajaxBookItemList(Model model, HttpSession session) {
        List<FrontBookItemVO> bookItemVOList = (List<FrontBookItemVO>) session.getAttribute(FRONT_BOOK_ITEM_LIST);
        Set<Block> blockTherapistSet = new HashSet<>();

        if (bookItemVOList == null) {
            return "front/book/ajax/bookItemList";
        }

        // 计算已经选择了block therapist set
        for (FrontBookItemVO bookItemVO : bookItemVOList) {
            List<RequestTherapistVO> therapistVOS = bookItemVO.getTherapistVOS();
            if (therapistVOS != null && !therapistVOS.isEmpty()) {
                for (RequestTherapistVO therapistVO : therapistVOS) {
                    if (therapistVO == null || therapistVO.getTherapist() == null) {
                        continue;
                    }
                    blockTherapistSet.add(new Block(therapistVO.getTherapist(),
                            bookItemVO.getAppointmentTime(),
                            bookItemVO.getEndAppointmentTime()));
                }
            }
        }

        // 计算可用的集合和不可用的集合
        for (FrontBookItemVO bookItemVO : bookItemVOList) {
            if (!bookItemVO.getRequireSelectTherapist()) {
                continue;
            }
            calBlockAndNotBlockTherapistList(bookItemVO, blockTherapistSet);
        }

        System.out.println("bookItem:" + bookItemVOList);
        model.addAttribute("bookItemVOList", bookItemVOList);
        return "front/book/ajax/bookItemList";
    }

    @RequestMapping(value = "ajaxSelectTherapist")
    public String ajaxSelectTherapist(Long bookItemId, Long therapistId, Integer therapistIndex, Boolean available, Model model, HttpSession session) {
        List<FrontBookItemVO> bookItemVOList = (List<FrontBookItemVO>) session.getAttribute(FRONT_BOOK_ITEM_LIST);

        System.out.println("bookItemId:" + bookItemId + ", therapistID:" + therapistId + ", therapistIndex:" + therapistIndex);
        for (FrontBookItemVO bookItemVO : bookItemVOList) {
            if (!bookItemVO.getId().equals(bookItemId)) {
                continue;
            }
            List<RequestTherapistVO> requestTherapistVOS = bookItemVO.getTherapistVOS();
            RequestTherapistVO therapistVO = requestTherapistVOS.get(therapistIndex);
            if(therapistVO == null) {
                therapistVO = new RequestTherapistVO();
                requestTherapistVOS.set(therapistIndex, therapistVO);
            }

            if (therapistId != null) {
                User user = userService.get(therapistId);
                therapistVO.setAvailable(available);
                therapistVO.setTherapist(user);
                therapistVO.setOnRequest(true);
                therapistVO.setTherapistId(user.getId());
            } else {
                requestTherapistVOS.set(therapistIndex, null);
            }

            updateBookItemStatus(bookItemVO, bookItemVOList);
            break;
        }

        // 计算可用的集合和不可用的集合
        // 计算所有selected block 的技师集合
        Set<Block> selectedBlockTherapistSet = new HashSet<>();
        for (FrontBookItemVO bookItemVO : bookItemVOList) {
            for (RequestTherapistVO requestTherapistVO : bookItemVO.getTherapistVOS()) {
                if (requestTherapistVO != null && requestTherapistVO.getTherapist() != null) {
                    selectedBlockTherapistSet.add(new Block(requestTherapistVO.getTherapist(), bookItemVO.getAppointmentTime(), bookItemVO.getEndAppointmentTime()));
                }
            }
        }


        for (FrontBookItemVO bookItemVO : bookItemVOList) {
            if (!bookItemVO.getRequireSelectTherapist()) {
                continue;
            }
            calBlockAndNotBlockTherapistList(bookItemVO, selectedBlockTherapistSet);
        }

        model.addAttribute("bookItemVOList", bookItemVOList);
        return "front/book/ajax/bookItemList";
    }

    private void calBlockAndNotBlockTherapistList(FrontBookItemVO frontBookItemVO, Set<Block> selectedBlockSet) {
        Set<User> blockedTherapists = new LinkedHashSet<>();   // 不可用的技师集合
        List<User> allTherapists = userService.getTherapistsBySkill(frontBookItemVO.getShopId(), frontBookItemVO.getProductOption(),true);

        // 可用的 therapistList
        Shop shop = frontBookItemVO.getShop();
        ProductOption productOption = frontBookItemVO.getProductOption();
        Date startTime = new Date(frontBookItemVO.getTimestamp());
        Date endTime = new DateTime(startTime).plusMinutes(productOption.getDuration() + productOption.getProcessTime()).toDate(); // end appointment time

        Iterator<User> userIterator = allTherapists.iterator();
        boolean isBlock;
        while (userIterator.hasNext()) {
            isBlock = false;
            // 计算user 是否被block
            User user = userIterator.next();

          /*  for (Block block : selectedBlockSet) {
                if (block.getUser().getId().equals(user.getId())) {
                    // 是不是自己选择的? 如果是不是自己选择的，则block这个user, 否认继续
                    boolean isSelfSelected = false;
                    // 循环当前选择的therapist
                    for (RequestTherapistVO requestTherapistVO : frontBookItemVO.getTherapistVOS()) {
                        if (requestTherapistVO == null || requestTherapistVO.getTherapist() == null) {
                            continue;
                        }
                        if (user.getId().equals(requestTherapistVO.getTherapist().getId())) {
                            isSelfSelected = true;  // 自己选择的技师
                            break;
                        }
                    }
                    isBlock = !isSelfSelected;
                }
            }*/

            if (isBlock) {
                blockedTherapists.add(user); // 记录block的技师集合
                userIterator.remove();  // 删除block的技师
                continue;
            }

            if (bookItemService.checkBlock(user, startTime, endTime, null)) {
                isBlock = true;
            }
            if (isBlock) {
                blockedTherapists.add(user);
                userIterator.remove();
                continue;
            }
            if (blockService.checkBlock(shop, user, startTime, endTime)) {
                blockedTherapists.add(user);
                userIterator.remove();
            }
        }

        // 记录可用和不可用的技师集合
        frontBookItemVO.setAvailableTherapists(allTherapists);
        frontBookItemVO.setNotAvailableTherapists(new ArrayList<>(blockedTherapists));
    }

    @RequestMapping(value = "ajaxTherapistList")
    public String ajaxTherapistList(FrontBookItemVO frontBookItemVO, Model model, HttpSession session) {
        if (frontBookItemVO.getTimestamp() == null || frontBookItemVO.getProductOptionId() == null) {
            logger.error("Parameter Error!");
            return "front/book/ajax/therapistList";
        }

        Set<Block> blockTherapistSet = new HashSet<>();

        // 保存到会话中
        List<FrontBookItemVO> frontBookItemVOList = (List<FrontBookItemVO>) session.getAttribute(FRONT_BOOK_ITEM_LIST);
        if (frontBookItemVOList != null) {
            // 计算已经分配的room和therapist
            for (FrontBookItemVO itemVO : frontBookItemVOList) {
                for (RequestTherapistVO therapistVO : itemVO.getTherapistVOS()) {
                    if (therapistVO == null || therapistVO.getTherapist() == null) {
                        continue;
                    }
                    blockTherapistSet.add(new Block(therapistVO.getTherapist(), itemVO.getAppointmentDate(), itemVO.getEndAppointmentTime()));
                }
            }
        }


        // 可用的 therapistList
        Long shopId = frontBookItemVO.getShopId();
        Shop shop = shopService.get(shopId);
        ProductOption productOption = productOptionService.get(frontBookItemVO.getProductOptionId());

        frontBookItemVO.setShop(shop);
        frontBookItemVO.setProductOption(productOption);

        calBlockAndNotBlockTherapistList(frontBookItemVO, blockTherapistSet);

        model.addAttribute("frontBookItemVO", frontBookItemVO);
        // 可用的技师集合
        return "front/book/ajax/therapistList";
    }

    @RequestMapping(value = "addItem", method = RequestMethod.POST)
    public String addItem(FrontBookItemVO frontBookItemVO, Model model, HttpSession session, final RedirectAttributes redirectAttrs) {
       Date date= DateUtil.getAfterNumOfMinutes(new Date(frontBookItemVO.getTimestamp()), 1);
       Date now=new Date();
        List<String> errors = new ArrayList<>();
        if(date.getTime()<now.getTime()){
            errors.add("Sorry that the time you choose is invalid, please choose again!");
        }
        if (frontBookItemVO.getTimestamp() == null) {
            errors.add("Time is required!");
        }
        if (frontBookItemVO.getProductOptionId() == null) {
            errors.add("Treatment is required!");
        }

        if (!errors.isEmpty()) {
            redirectAttrs.addFlashAttribute("errors", errors);
            return "redirect:/front/book/toAdd";
        }
        // 当前booking选择的block集合
        Set<Block> blockRoomSet = new HashSet<>();
        Set<Block> blockTherapistSet = new HashSet<>();

        // 保存到会话中
        List<FrontBookItemVO> frontBookItemVOList = (List<FrontBookItemVO>) session.getAttribute(FRONT_BOOK_ITEM_LIST);
        if (frontBookItemVOList == null) {
            frontBookItemVOList = new ArrayList<>();
            session.setAttribute(FRONT_BOOK_ITEM_LIST, frontBookItemVOList);
        } else {
            for (FrontBookItemVO itemVO : frontBookItemVOList) {
                if (itemVO.getRoom() != null) {
                    blockRoomSet.add(new Block(itemVO.getRoom(), itemVO.getAppointmentDate(), itemVO.getEndAppointmentTime()));
                }

                for (RequestTherapistVO therapistVO : itemVO.getTherapistVOS()) {
                    if (therapistVO == null || therapistVO.getTherapist() == null) {
                        continue;
                    }
                    blockTherapistSet.add(new Block(therapistVO.getTherapist(), itemVO.getAppointmentDate(), itemVO.getEndAppointmentTime()));
                }
            }
        }

        ProductOption productOption = productOptionService.get(frontBookItemVO.getProductOptionId());
        frontBookItemVO.setProductOption(productOption);
        frontBookItemVO.setProductName(productOption.getLabe66());

        DateTime startTime = new DateTime(frontBookItemVO.getTimestamp());
        Date startAppointment = startTime.toDate();
        Date endAppointment = new DateTime(startTime).plusMinutes(productOption.getDuration() + productOption.getProcessTime()).toDate(); // end appointment time
        frontBookItemVO.setAppointmentTime(startAppointment);
        frontBookItemVO.setEndAppointmentTime(endAppointment);

        long shopId = frontBookItemVO.getShopId();
        Shop shop = shopService.get(shopId);
        frontBookItemVO.setShop(shop);
        // 分配房间
        // guest > 1
        // 根据 frontBookItemVO 的guestAmount 拆分多个bookItem
        // guest == 1
        if (frontBookItemVO.getGuestAmount() == 1) {
            //List<User> therapistList = new ArrayList<>();
            List<RequestTherapistVO> requestTherapistVOS = new ArrayList<>();
            frontBookItemVO.setTherapistVOS(requestTherapistVOS);
            if (frontBookItemVO.getTherapistInfo() != null && !frontBookItemVO.getTherapistInfo().isEmpty()) {
                for (String info : frontBookItemVO.getTherapistInfo()) {
                    String[] idAndBlock = info.split("_");
                    if (idAndBlock.length == 2) {
                        User therapist = userService.get(Long.parseLong(idAndBlock[0]));
                        RequestTherapistVO therapistVO = new RequestTherapistVO();
                        therapistVO.setTherapist(therapist);
                        therapistVO.setAvailable(!"block".equals(idAndBlock[1]));
                        therapistVO.setOnRequest(false);
                        therapistVO.setTherapistId(therapist.getId());
                        requestTherapistVOS.add(therapistVO);
                    } else {
                        logger.error("Parameter Error!");
                    }
                }
            } else {
                // 随机分配 therapist
                calBlockAndNotBlockTherapistList(frontBookItemVO, blockTherapistSet);
                List<User> availableTherapists = frontBookItemVO.getAvailableTherapists();
                if (availableTherapists != null && !availableTherapists.isEmpty()) {
                    User therapist = availableTherapists.get(RandomUtils.nextInt(0, availableTherapists.size()));
                    RequestTherapistVO therapistVO = new RequestTherapistVO();
                    therapistVO.setTherapist(therapist);
                    therapistVO.setAvailable(true);
                    therapistVO.setOnRequest(false);
                    therapistVO.setTherapistId(therapist.getId());
                    requestTherapistVOS.add(therapistVO);
                }
            }

            frontBookItemVO.setId(System.nanoTime());
            // 分配room
            List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), shopId, startAppointment, endAppointment);
            logger.debug("Available rooms: {}", availableRoomList);
            availableRoomList = getNotBlockRoomList(availableRoomList, blockRoomSet, startAppointment, endAppointment);
            Room assignRoom = RoomHelper.getAssignRoom(availableRoomList, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
            if (assignRoom != null) {
                frontBookItemVO.setRoom(assignRoom);
                frontBookItemVO.setRoomId(assignRoom.getId());
            } else {
                frontBookItemVO.setRoomId(null);
                frontBookItemVO.setRoom(null);
            }

            frontBookItemVO.setRequireSelectTherapist(false);
            updateBookItemStatus(frontBookItemVO, frontBookItemVOList); // 更新bookItem状态
            frontBookItemVOList.add(frontBookItemVO);
        } else {
            int guestAmount = frontBookItemVO.getGuestAmount();
            Room assignRoom = null;
            List<Room> availableRoomList = roomService.getNotBlockRoomList(productOption.getId(), shopId, startAppointment, endAppointment);
            availableRoomList = getNotBlockRoomList(availableRoomList, blockRoomSet, startAppointment, endAppointment);
            logger.debug("Available rooms: {}", availableRoomList);

            // 分配share room的房间
            boolean shareRoom = frontBookItemVO.getShareRoom() != null && frontBookItemVO.getShareRoom();
            if (shareRoom) {
                assignRoom = RoomHelper.getAssignRoom(availableRoomList, guestAmount, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
            }
            System.out.println("assignRoom:" + assignRoom);
            System.out.println("frontBookItemVO.getShareRoom():" + frontBookItemVO.getShareRoom());
            // front book item 拆分成多个
            Long parentId = null;
            for (int i = 0; i < guestAmount; i++) {
                FrontBookItemVO newItemVO = new FrontBookItemVO();
                newItemVO.setId(System.nanoTime());
                newItemVO.setShopId(shopId);
                newItemVO.setShop(shop);
                newItemVO.setTimestamp(frontBookItemVO.getTimestamp());
                newItemVO.setProductOptionId(frontBookItemVO.getProductOptionId());
                newItemVO.setProductOption(productOption);
                newItemVO.setProductName(frontBookItemVO.getProductName());
                newItemVO.setRequireSelectTherapist(true);
                newItemVO.setAppointmentTime(startAppointment);
                newItemVO.setEndAppointmentTime(endAppointment);
                newItemVO.setProductOption(productOption);
                newItemVO.setStatus(CommonConstant.BOOK_STATUS_WAITING);

                List<RequestTherapistVO> requestTherapistVOS = new ArrayList<>();
                // 初始化users
                newItemVO.setTherapistVOS(requestTherapistVOS);
                for (int index = 0; index < productOption.getCapacity(); index++) {
                    requestTherapistVOS.add(null);
                }

                if (assignRoom != null) {
                    newItemVO.setRoom(assignRoom);
                    newItemVO.setRoomId(assignRoom.getId());
                    if (i == 0) {
                        parentId = newItemVO.getId();
                    } else {
                        newItemVO.setParentId(parentId);
                    }
                } else {
                    // 重新分配单独的房间
                    availableRoomList = getNotBlockRoomList(availableRoomList, blockRoomSet, startAppointment, endAppointment);
                    Room singleRoom = RoomHelper.getAssignRoom(availableRoomList, 1, CommonConstant.ROOM_CAPACITY_RANGE); // 分配房间
                    if (singleRoom != null) {
                        newItemVO.setRoom(singleRoom);
                        newItemVO.setRoomId(singleRoom.getId());
                        blockRoomSet.add(new Block(singleRoom, startAppointment, endAppointment));
                    }
                }

                frontBookItemVOList.add(newItemVO);
            }
        }
        return "redirect:/front/book/toAdd";
    }

    /**
     * 更新bookItem的状态
     *
     * @param frontBookItemVO
     */
    private void updateBookItemStatus(FrontBookItemVO frontBookItemVO, List<FrontBookItemVO> frontBookItemVOList) {
        if (frontBookItemVO.getRoom() == null) {
            frontBookItemVO.setStatus(CommonConstant.BOOK_STATUS_WAITING);
            return;
        }
        String res = checkBlock(frontBookItemVOList);
        if (StringUtils.isNotBlank(res)) {
            logger.warn("update status reason: {}", res);
            frontBookItemVO.setStatus(CommonConstant.BOOK_STATUS_WAITING); // 产生冲突，更新为waiting状态
            return;
        }
        for (RequestTherapistVO therapistVO : frontBookItemVO.getTherapistVOS()) {
            if (therapistVO != null
                    && therapistVO.getTherapist() != null
                    && therapistVO.getAvailable() != null
                    && therapistVO.getAvailable()) {

                frontBookItemVO.setStatus(CommonConstant.BLOCK_ITEM_STATUS_PENDING);
                return;
            }
        }
        frontBookItemVO.setStatus(CommonConstant.BOOK_STATUS_WAITING);
    }

    @RequestMapping(value = "removeItem", method = RequestMethod.POST)
    @ResponseBody
    public String removeItem(Long id, HttpSession session) {
        List<FrontBookItemVO> frontBookItemVOList = (List<FrontBookItemVO>) session.getAttribute(FRONT_BOOK_ITEM_LIST);
        if (id != null && frontBookItemVOList != null) {
            Iterator<FrontBookItemVO> iterator = frontBookItemVOList.iterator();
            while (iterator.hasNext()) {
                if (id.equals(iterator.next().getId())) {
                    iterator.remove();
                    break;
                }
            }
        }
        return "success";
    }


    /**
     * 在 start 到 end 的时间范围内，把 roomList里剔除被 blockRoomSet包含的room, 返回没有被block的room集合
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

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String saveOrUpdate(HttpSession session, final RedirectAttributes redirectAttrs) {
        List<FrontBookItemVO> frontBookItemVOList = (List<FrontBookItemVO>) session.getAttribute(FRONT_BOOK_ITEM_LIST);
        List<String> errors = new ArrayList<>();
        redirectAttrs.addFlashAttribute("errors", errors);
        if (frontBookItemVOList == null || frontBookItemVOList.isEmpty()) {
            errors.add("Appointment item is required!");
            return "redirect:/front/book/toAdd";
        }

        /*String errorMsg = checkBlock(frontBookItemVOList);
        if (StringUtils.isNotBlank(errorMsg)) {
            errors.add(errorMsg);
            return "redirect:/front/book/toAdd";
        }*/

        // 验证therapist
       /* for (FrontBookItemVO itemVO : frontBookItemVOList) {
            boolean hasTherapist = false;
            for (RequestTherapistVO requestTherapistVO : itemVO.getTherapistVOS()) {
                if (requestTherapistVO != null && requestTherapistVO.getTherapist() != null) {
                    hasTherapist = true;
                    break;
                }
            }
            if (!hasTherapist) {
                errors.add("Therapist(s) required!");
                return "redirect:/front/book/toAdd";
            }
        }*/

        FrontBookItemVO firstItemVO = frontBookItemVOList.get(0);
        FrontBookVO frontBookVO = new FrontBookVO();
        frontBookVO.setShopId(firstItemVO.getShopId());
        frontBookVO.setShop(shopService.get(firstItemVO.getShopId()));
        frontBookVO.setAppointmentDate(firstItemVO.getAppointmentTime());
        frontBookVO.setFrontBookItemVOs(frontBookItemVOList);
        try {
           Book book = bookService.add(frontBookVO.transferToBookVO(WebThreadLocal.getUser()));
           if (PropertiesUtil.getBooleanValueByName("BOOKING_NOTIFICATION")) {
        	   bookService.sendBookingNotification(book, CommonConstant.SEND_BOOKING_NOTIFICATION_EMAIL);
           }
            session.removeAttribute(FRONT_BOOK_ITEM_LIST);
        } catch (Exception e) {
            e.printStackTrace();
            errors.add("Time Has been block, Please check!");
            return "redirect:/front/book/toAdd";
        }

        return "redirect:/front/book/myBookings";
    }

    private String checkBlock(List<FrontBookItemVO> frontBookItemVOList) {
        // 验证 bookItem的therapist是否冲突
        Set<Block> blocks = new HashSet<>();
        for (FrontBookItemVO bookItemVO : frontBookItemVOList) {
            for (RequestTherapistVO therapistVO : bookItemVO.getTherapistVOS()) {
                if (therapistVO == null || therapistVO.getTherapist() == null) {
                    continue;
                }
                Date startTime = bookItemVO.getAppointmentTime();
                Date endTime = bookItemVO.getEndAppointmentTime();
                Block block = new Block(therapistVO.getTherapist(), startTime, endTime);
                for (Block otherBlock : blocks) {
                    if (otherBlock.getUser().getId().equals(block.getUser().getId())
                            && DateUtil.overlaps(startTime, endTime, otherBlock.getStartDate(), otherBlock.getEndDate())) {
                        String startTimeStr = new DateTime(otherBlock.getStartDate()).toString("HH:mm");
                        String endTimeStr = new DateTime(otherBlock.getEndDate()).toString("HH:mm");
                        return "Therapist " + block.getUser().getDisplayName() + " has been block from " + startTimeStr + " to " + endTimeStr + "!";
                    }
                }
                blocks.add(block); // 保存block
            }
        }
        return "";
    }

    @RequestMapping("cancel")
    @ResponseBody
    public AjaxForm cancel(Long bookId) {
        if (bookId != null) {
            bookService.cancel(bookId);
        }
        return AjaxFormHelper.success("success");
    }
    @RequestMapping("myBookings")
    public String myBookings(Model model){
        Long memberId = WebThreadLocal.getUser().getId();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Book.class);
        if (memberId != null) {
            detachedCriteria.add(Restrictions.eq("user.id", memberId));
        }
        List<Book> bookList= bookService.list(detachedCriteria);
        model.addAttribute("bookList", bookList);
        return "front/book/myBookings";
    }

}
