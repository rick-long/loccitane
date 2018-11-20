package com.spa.controller.app;

import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.spa.model.book.Book;
import org.spa.model.book.BookItem;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.utils.Results;
import org.spa.vo.app.book.*;
import org.spa.vo.app.callback.*;
import org.spa.vo.book.BookListVO;
import org.spa.vo.page.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("apps/booking")
public class AppsBookingController extends BaseController {
	
    @RequestMapping("therapist")
    @ResponseBody
    public Results therapist(@RequestBody BookItemRequestVO bookItemRequestVO){
        Results results = Results.getInstance();
        if (bookItemRequestVO.getTimestamp() == null) {
        	return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Time is required");
        }
        if (bookItemRequestVO.getProductOptionId() == null) {
        	return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Treatment is required");
        }
        // 可用的 therapistList
        Long shopId = bookItemRequestVO.getShopId();
        Shop shop = shopService.get(shopId);
        ProductOption productOption = productOptionService.get(bookItemRequestVO.getProductOptionId());

        bookItemRequestVO.setShop(shop);
        bookItemRequestVO.setProductOption(productOption);

        List<User> allTherapists = userService.getTherapistsBySkill(bookItemRequestVO.getShopId(), bookItemRequestVO.getProductOption(),true);
        Date startTime = new Date(bookItemRequestVO.getTimestamp());
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
        Long memberId =bookItemRequestVO.getMemberId();
        User member = userService.get(memberId);
        List <TherapistCallBackVO> allTherapistVOs=new ArrayList<TherapistCallBackVO>();
        if(allTherapists !=null && allTherapists.size()>0){
        	int i =2;
	        for(User therapist : allTherapists){
	        	TherapistCallBackVO callback=  null;
	        	if(member.getUser() !=null && (therapist.getId() == member.getUser().getId())){
	        		//preferred_therapist_id
	        		callback= new TherapistCallBackVO(therapist,1);
	        	}else{
	        		callback= new TherapistCallBackVO(therapist,i);
	        		i++;
	        	}
	        	allTherapistVOs.add(callback);
	        }
	        Collections.sort(allTherapistVOs, (e1, e2) -> e1.getSortId().compareTo(e2.getSortId()));
        }
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", allTherapistVOs);

    }
    
   /** version 1.1
    * booking选择多技师save新接口
    * */
    @RequestMapping("save")
    @ResponseBody
    public synchronized Results save(@RequestBody BookRequestVO bookRequestVO){
        Results results = Results.getInstance();
        if (bookRequestVO.getBookItems() == null || bookRequestVO.getBookItems().isEmpty()) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Appointment item is required!");
        }
        if (bookRequestVO.getShopId() == null || bookRequestVO.getShopId().longValue()<=0) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Shop is required!");
        }
        if (bookRequestVO.getMemberId() == null || bookRequestVO.getMemberId().longValue()<=0) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Member is missing.");
        }
        if (bookRequestVO.getAppointmentDate() == null) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Appointment Date is required!");
        }
        try {
            results =bookService.saveAppsBook(bookRequestVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;

    }
    @RequestMapping("calAmt")
    @ResponseBody
    public Results calAmt(@RequestBody PaymentVO paymentVO){
    	  Results results = Results.getInstance();
    	  if(StringUtils.isBlank(paymentVO.getBookingNumber())){
    		  return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Booking Number is required!");
    	  }
    	  Book book =bookService.get("reference", paymentVO.getBookingNumber());
    	  if(book ==null){
    		  return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Booking is  not exist.");
    	  }
    	  try {
    		  PurchaseOrder po = purchaseOrderService.saveAppsPurchaseOrder(book.getId(),false);
    		  OrderCalAmtCallBackVO vo =new OrderCalAmtCallBackVO();
    		  vo.setCurrency(I18nUtil.getMessageKey("label.currency"));
    		  vo.setOnlineAmt(po.getTotalDiscount());
    		  vo.setOnlineAmtStr(CommonConstant.CURRENCY_TYPE+po.getTotalDiscount());
    		  vo.setShopAmt(po.getTotalAmount());
    		  vo.setShopAmtStr(CommonConstant.CURRENCY_TYPE+po.getTotalAmount());

    		  return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", vo);
    	  } catch (Exception e) {
    		  e.printStackTrace();
		  }
          return results;
    }

    
    @RequestMapping("beforePay")
    @ResponseBody
    public Results beforePay(@RequestBody PaymentVO paymentVO){
    	Results results = Results.getInstance();
        if(StringUtils.isBlank(paymentVO.getBookingNumber())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Booking Number is required!");
        }

        Book book =bookService.get("reference", paymentVO.getBookingNumber());
        if(book ==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Booking is  not exist.");
        }
        if(book.getBookItems()!=null &&book.getBookItems().size()>0){
            for(BookItem bookItem:book.getBookItems()){
                Date now =  DateUtil.getAfterNumOfMinutes(new Date(),-60);
                if(bookItem.getAppointmentTime().before(now)){
                    return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", false);
                }
                if(bookItem.getStatus().equals(CommonConstant.BOOK_STATUS_CANCEL)){
               	 	return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", false);
                }
            }
        }else{
        	 return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", false);
        }
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", true);
    }

    
    @RequestMapping("list")
    @ResponseBody
    public Results list(@RequestBody BookListVO bookListVO){
        Results results = Results.getInstance();
        if(bookListVO.getMemberId() ==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Member is required");
        }
        String status = bookListVO.getStatus();
        if(StringUtils.isBlank(status)){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Status is required");
        }

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Book.class);
        detachedCriteria.add(Restrictions.eq("user.id", bookListVO.getMemberId()));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.createAlias("bookItems", "bi");
        String[] statusList = new String[5];

        if(status.equals("PROCESSING")){
            statusList[0] = CommonConstant.BOOK_STATUS_CONFIRM;
            statusList[1] = CommonConstant.BOOK_STATUS_CHECKIN_SERVICE;
            statusList[2] = CommonConstant.BOOK_STATUS_WAITING;
            detachedCriteria.add(Restrictions.in("bi.status", statusList));
        }else if(status.equals(CommonConstant.BOOK_STATUS_COMPLETE)){
            Page page=new Page();
            page.setTotalPages(page.getTotalPages());
            page.setTotalRecords(bookListVO.getTotalRecords());
            page.setPageSize(bookListVO.getPageSize());
            page.setPageNumber( bookListVO.getPageNumber());
            List<PurchaseOrderCallBackVO> purchaseOrderVO=new ArrayList<>();
            Page<PurchaseOrderCallBackVO> pagePurchaseOrderVO=new Page<>();
            DetachedCriteria orderCriteria = DetachedCriteria.forClass(PurchaseOrder.class);
            orderCriteria.add(Restrictions.eq("user.id", bookListVO.getMemberId()));
            orderCriteria.add(Restrictions.eq("isActive", true));
            orderCriteria.createAlias("purchaseItems", "pi");
            orderCriteria.add(Restrictions.isNotNull("pi.productOption"));
            Page<PurchaseOrder> purchaseOrders= purchaseOrderService.list(orderCriteria,page);
            if(purchaseOrders!=null&&purchaseOrders.getList().size()>0){
                for (PurchaseOrder purchaseOrder:purchaseOrders.getList()) {
                    List<PurchaseItemCallBackVO> purchaseItemListVO=new ArrayList<>();
                    PurchaseOrderCallBackVO purchaseOrderCallBackVO=new PurchaseOrderCallBackVO(purchaseOrder);
                    if(purchaseOrder.getPurchaseItems()!=null&&purchaseOrder.getPurchaseItems().size()>0) {
                        for (PurchaseItem purchaseItem : purchaseOrder.getPurchaseItems()) {
                            if(purchaseItem!=null &&purchaseItem.getProductOption()!=null){
                                if(purchaseItem.getProductOption().getProductType().equals(CommonConstant.CATEGORY_REF_TREATMENT)&&purchaseItem.getStatus().equals(CommonConstant.PURCHASE_ORDER_STATUS_COMPLETED)){
                                    PurchaseItemCallBackVO purchaseItemVO = new PurchaseItemCallBackVO(purchaseItem);
                                    purchaseItemListVO.add(purchaseItemVO);
                                }
                            }
                        }
                    }
                    if(purchaseItemListVO!=null&&purchaseItemListVO.size()!=0){
                        purchaseOrderCallBackVO.setOrderItemList(purchaseItemListVO);
                        purchaseOrderVO.add(purchaseOrderCallBackVO);
                    }

                }

            }
            pagePurchaseOrderVO.setList(purchaseOrderVO);
            pagePurchaseOrderVO.setPageNumber(purchaseOrders.getPageNumber());
            pagePurchaseOrderVO.setTotalRecords(purchaseOrders.getTotalRecords());
            pagePurchaseOrderVO.setPageSize(purchaseOrders.getPageSize());
            pagePurchaseOrderVO.setTotalPages(purchaseOrders.getTotalPages());
            return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", pagePurchaseOrderVO);

        }else{
            detachedCriteria.add(Restrictions.eq("bi.status",CommonConstant.BOOK_STATUS_CANCEL));
        }
        detachedCriteria.setResultTransformer(detachedCriteria.DISTINCT_ROOT_ENTITY);
        Page<Book> books =bookService.list(detachedCriteria,bookListVO);

        Page<BookingCallBackVO> pageBooking = new Page<>();
        List<BookingCallBackVO> bookingList = new ArrayList<>();
        if(books !=null && books.getList().size()>0)
            for(Book book : books.getList()){
                BookingCallBackVO callback= new BookingCallBackVO(book);
                bookingList.add(callback);
            }
        pageBooking.setList(bookingList);
        pageBooking.setPageNumber(books.getPageNumber());
        pageBooking.setTotalRecords(books.getTotalRecords());
        pageBooking.setPageSize(books.getPageSize());
        pageBooking.setTotalPages(books.getTotalPages());
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", pageBooking);

    }
    @RequestMapping("createOrder")
    @ResponseBody
    public Results createOrder(@RequestBody PaymentVO paymentVO){
    	  Results results = Results.getInstance();
    	  if(StringUtils.isBlank(paymentVO.getBookingNumber())){
    		  return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Booking Number is required!");
    	  }
    	  Book book =bookService.get("reference", paymentVO.getBookingNumber());

    	  if(book ==null){
    		  return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Booking is  not exist.");
    	  }
    	  try {
    		  PurchaseOrder po = purchaseOrderService.saveAppsPurchaseOrder2(book.getId());
    		  //更新MobilePrepaid为true
              bookService.updateMobilePrepaid(book.getId());
    		  return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", "Order created successfully.The number is "+po.getReference());
    	  } catch (Exception e) {
    		  e.printStackTrace();
		  }
          return results;
    }

}
