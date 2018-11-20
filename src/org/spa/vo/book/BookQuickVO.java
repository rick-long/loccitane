package org.spa.vo.book;

import com.spa.constant.CommonConstant;
import org.joda.time.DateTime;
import org.spa.model.book.Book;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.serviceImpl.product.ProductOptionServiceImpl;
import org.spa.serviceImpl.shop.RoomServiceImpl;
import org.spa.serviceImpl.shop.ShopServiceImpl;
import org.spa.serviceImpl.user.UserServiceImpl;
import org.spa.utils.SpringUtil;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 快速添加book
 *
 * @author Ivy 2016-9-5
 */
public class BookQuickVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long startTimeStamp;

    private Date startAppointmentTime;

    private Long memberId;

    private User member;

    private Long therapistId;

    private User therapist;

    private Long productOptionId;

    private String productOptionName;

    private ProductOption productOption;

    private Integer duration;

    private String status;

    private Long shopId;

    private Long roomId;
    
    private Room assignRoom; // 分配的房间

    private Boolean walkIn;

    private Boolean pregnancy;

    private String remarks;

    private Boolean requested;
    /**
     * For guest user
     */
    private String firstName;

    private String lastName;

    private String email;

    private String mobilePhone;

    private String country;

    private Boolean doubleBooking;
    private Long originalBookItemId;
    
    private String startTime;
    private String bookingDate;

    private Long bookItemId;

    private Long bookId;

    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookItemId() {
        return bookItemId;
    }

    public void setBookItemId(Long bookItemId) {
        this.bookItemId = bookItemId;
    }

    public String getBookingDate() {
	return bookingDate;
}
   public void setBookingDate(String bookingDate) {
	this.bookingDate = bookingDate;
}
    public String getStartTime() {
		return startTime;
	}
    public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
    public Long getOriginalBookItemId() {
		return originalBookItemId;
	}
    public void setOriginalBookItemId(Long originalBookItemId) {
		this.originalBookItemId = originalBookItemId;
	}
    public Boolean getDoubleBooking() {
		return doubleBooking;
	}
    public void setDoubleBooking(Boolean doubleBooking) {
		this.doubleBooking = doubleBooking;
	}
    public Boolean getRequested() {
		return requested;
	}
    public void setRequested(Boolean requested) {
		this.requested = requested;
	}
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getWalkIn() {
        return walkIn;
    }

    public void setWalkIn(Boolean walkIn) {
        this.walkIn = walkIn;
    }

    public Boolean getPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(Boolean pregnancy) {
        this.pregnancy = pregnancy;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public Long getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(Long therapistId) {
        this.therapistId = therapistId;
    }

    public User getTherapist() {
        return therapist;
    }

    public void setTherapist(User therapist) {
        this.therapist = therapist;
    }

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getProductOptionName() {
        return productOptionName;
    }

    public void setProductOptionName(String productOptionName) {
        this.productOptionName = productOptionName;
    }

    public ProductOption getProductOption() {
        return productOption;
    }

    public void setProductOption(ProductOption productOption) {
        this.productOption = productOption;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Date getStartAppointmentTime() {
        return startAppointmentTime;
    }

    public void setStartAppointmentTime(Date startAppointmentTime) {
        this.startAppointmentTime = startAppointmentTime;
    }

    public Room getAssignRoom() {
        return assignRoom;
    }

    public void setAssignRoom(Room assignRoom) {
        this.assignRoom = assignRoom;
    }
    
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * 将bookQuickVO转换为bookVO
     *
     * @return
     */
    @Transient
    public BookVO transferToBookVO() {
        UserService userService = SpringUtil.getBean(UserServiceImpl.class);
        BookVO bookVO = new BookVO();
        bookVO.setState(CommonConstant.STATE_ADD);
        User therapist = userService.get(getTherapistId());
        ShopService shopService = SpringUtil.getBean(ShopServiceImpl.class);
        Shop shop = shopService.get(getShopId());
        bookVO.setShop(shop);
        bookVO.setShopId(shop.getId());
        User member = userService.get(getMemberId());
        bookVO.setMember(member);
        bookVO.setMemberId(getMemberId());
        Date startAppointmentTime = new Date(getStartTimeStamp());
        bookVO.setStartAppointmentTime(startAppointmentTime);
        bookVO.setGuestAmount(1);
        ProductOption productOption = SpringUtil.getBean(ProductOptionServiceImpl.class).get(getProductOptionId());
        bookVO.setProductOption(productOption);
        int duration = productOption.getDuration();
        bookVO.setDuration(duration);
        DateTime startDateTime = new DateTime(startAppointmentTime);
//        Date endAppointmentTime = startDateTime.plusMinutes(duration).toDate();
        Date endAppointmentTime = startDateTime.plusMinutes(duration + productOption.getProcessTime()).toDate();
        bookVO.setEndAppointmentTime(endAppointmentTime);

        BookItemVO bookItemVO = new BookItemVO();
        List<BookItemVO> bookItemVOList = new ArrayList<>();
        bookItemVOList.add(bookItemVO);
        bookVO.setBookItemVOs(bookItemVOList);

        bookItemVO.setAppointmentTime(startAppointmentTime);
        bookItemVO.setEndAppointmentTime(endAppointmentTime);
        bookItemVO.setDuration(duration);
        bookItemVO.setProductOptionId(getProductOptionId());
        bookItemVO.setProductOption(productOption);

        List<RequestTherapistVO> requestTherapistVOList = bookItemVO.getRequestTherapistVOs();
        RequestTherapistVO therapistVO = new RequestTherapistVO();
        therapistVO.setOnRequest(getRequested() !=null ? getRequested() : false);
        therapistVO.setTherapistId(therapistId);
        therapistVO.setTherapist(therapist);
        requestTherapistVOList.add(therapistVO);

        if (getRoomId() != null) {
            assignRoom = SpringUtil.getBean(RoomServiceImpl.class).get(getRoomId());
            bookItemVO.setRoomId(assignRoom.getId());
            bookItemVO.setRoom(assignRoom);
            bookItemVO.setStatus(CommonConstant.BOOK_STATUS_CONFIRM);
            bookVO.setStatus(CommonConstant.BOOK_STATUS_CONFIRM);
        } else {
            bookItemVO.setRoomId(null);
            bookItemVO.setRoom(null);
            bookItemVO.setStatus(CommonConstant.BOOK_STATUS_WAITING);
            bookVO.setStatus(CommonConstant.BOOK_STATUS_WAITING);
        }
        bookItemVO.setShareRoom(false);
        bookItemVO.setStartTime(startDateTime.toString("HH:mm"));
        bookItemVO.setGuestAmount(1);
        bookItemVO.setOnRequest(getRequested() !=null ? getRequested() : false);
        bookItemVO.setBookVO(bookVO);
        bookItemVO.setTempId(1L);
        bookItemVO.setIsDoubleBooking(getDoubleBooking());
        bookItemVO.setDoubleBookingParentId(getOriginalBookItemId());
        
        bookVO.setShareRoom(false);

        // 创建first step record vo
        BookFirstStepRecordVO firstStepRecordVO = new BookFirstStepRecordVO();
        firstStepRecordVO.setProductOptionId(bookItemVO.getProductOptionId());
        firstStepRecordVO.setProductOption(productOption);
        firstStepRecordVO.setGuestAmount(1);
        firstStepRecordVO.setShareRoom(false);
        firstStepRecordVO.setStartTime(bookItemVO.getStartTime());
        firstStepRecordVO.setEndTime(startDateTime.plusMinutes(duration + productOption.getProcessTime()).toString("HH:mm"));
        firstStepRecordVO.setDisplayOrder(1);
        bookVO.getFirstStepRecordVOList().add(firstStepRecordVO);
        // guest
        bookVO.setWalkIn(walkIn);
        bookVO.setPregnancy(pregnancy);
        bookVO.setRemarks(remarks);
        bookVO.setFirstName(firstName);
        bookVO.setLastName(lastName);
        bookVO.setEmail(email);
        bookVO.setMobilePhone(mobilePhone);
        bookVO.setCountry(country);
        return bookVO;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
