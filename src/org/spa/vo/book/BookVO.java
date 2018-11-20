package org.spa.vo.book;

import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private Long shopId;

    private Shop shop;

    @NotNull
    private Long memberId;

    private User member;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startAppointmentTime;

    // 开始的时间:比如09:00
    private String startTimeString;

    private Date endAppointmentTime;

    private Integer guestAmount;

    private List<BookItemVO> bookItemVOs;
    
    //private Long productOptionId;
    
    private String productOptionName;

    private ProductOption productOption;

    private Integer duration;

    private String status;

    // ADD or EDIT
    private String state;

    // 是否共享房间
    private Boolean shareRoom;

    private String forward;

    private String remarks;

    // 是否检查开关门的时间
    private boolean checkOpenCloseTime;

    private Boolean walkIn;
    private Boolean pregnancy;
    
    private Boolean sameTimeToShareRoom;

    public Boolean getSameTimeToShareRoom() {
		return sameTimeToShareRoom;
	}
	public void setSameTimeToShareRoom(Boolean sameTimeToShareRoom) {
		this.sameTimeToShareRoom = sameTimeToShareRoom;
	}

    private List<BookFirstStepRecordVO> firstStepRecordVOList = new ArrayList<>();

    /**
     * For guest user
     */
    private String firstName;

    private String lastName;

    private String email;

    private String mobilePhone;

    private String country;

    private String bookingChannel;

    private Long bundleId;
    private Long bundleGroup1Id;
    private Long bundleGroup2Id;

    private String appointmentDateFormat;
    private String bookShopName;


    public String getBookingChannel() {
		return bookingChannel;
	}
	public void setBookingChannel(String bookingChannel) {
		this.bookingChannel = bookingChannel;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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

    public Date getStartAppointmentTime() {
        return startAppointmentTime;
    }

    public void setStartAppointmentTime(Date startAppointmentTime) {
        this.startAppointmentTime = startAppointmentTime;
    }

    public Date getEndAppointmentTime() {
        return endAppointmentTime;
    }

    public void setEndAppointmentTime(Date endAppointmentTime) {
        this.endAppointmentTime = endAppointmentTime;
    }

    public Integer getGuestAmount() {
        return guestAmount;
    }

    public void setGuestAmount(Integer guestAmount) {
        this.guestAmount = guestAmount;
    }

    public List<BookItemVO> getBookItemVOs() {
        return bookItemVOs;
    }

    public void setBookItemVOs(List<BookItemVO> bookItemVOs) {
        this.bookItemVOs = bookItemVOs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getShareRoom() {
        return shareRoom;
    }

    public void setShareRoom(Boolean shareRoom) {
        this.shareRoom = shareRoom;
    }

    /*public Long getProductOptionId() {
        return productOptionId;
    }*/

    public String getProductOptionName() {
        return productOptionName;
    }

    /*public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }*/

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

    public String getForward() {
        return forward;
    }

    public void setForward(String forward) {
        this.forward = forward;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isCheckOpenCloseTime() {
        return checkOpenCloseTime;
    }

    public void setCheckOpenCloseTime(boolean checkOpenCloseTime) {
        this.checkOpenCloseTime = checkOpenCloseTime;
    }

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public List<BookFirstStepRecordVO> getFirstStepRecordVOList() {
        return firstStepRecordVOList;
    }

    public void setFirstStepRecordVOList(List<BookFirstStepRecordVO> firstStepRecordVOList) {
        this.firstStepRecordVOList = firstStepRecordVOList;
    }

    public Long getBundleId() {
        return bundleId;
    }

    public void setBundleId(Long bundleId) {
        this.bundleId = bundleId;
    }

    public Long getBundleGroup1Id() {
        return bundleGroup1Id;
    }

    public void setBundleGroup1Id(Long bundleGroup1Id) {
        this.bundleGroup1Id = bundleGroup1Id;
    }

    public Long getBundleGroup2Id() {
        return bundleGroup2Id;
    }

    public void setBundleGroup2Id(Long bundleGroup2Id) {
        this.bundleGroup2Id = bundleGroup2Id;
    }

    public String getAppointmentDateFormat() {
        return appointmentDateFormat;
    }

    public void setAppointmentDateFormat(String appointmentDateFormat) {
        this.appointmentDateFormat = appointmentDateFormat;
    }

    public String getBookShopName() {
        return bookShopName;
    }

    public void setBookShopName(String bookShopName) {
        this.bookShopName = bookShopName;
    }

}
