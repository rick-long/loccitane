package org.spa.vo.front.book;

import com.spa.constant.CommonConstant;
import org.joda.time.DateTime;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.service.product.ProductOptionService;
import org.spa.service.shop.RoomService;
import org.spa.serviceImpl.product.ProductOptionServiceImpl;
import org.spa.serviceImpl.shop.RoomServiceImpl;
import org.spa.utils.SpringUtil;
import org.spa.vo.book.BookItemVO;
import org.spa.vo.book.BookVO;
import org.spa.vo.book.RequestTherapistVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ivy 2016-6-16
 */
public class FrontBookVO implements Serializable {

    private Long id;

    private Long shopId;

    private Shop shop;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;

    private String remarks;

    private List<FrontBookItemVO> frontBookItemVOs;

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

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<FrontBookItemVO> getFrontBookItemVOs() {
        return frontBookItemVOs;
    }

    public void setFrontBookItemVOs(List<FrontBookItemVO> frontBookItemVOs) {
        this.frontBookItemVOs = frontBookItemVOs;
    }

    /**
     * 转换为bookVO对象
     *
     * @param member
     */
    public BookVO transferToBookVO(User member) {
        BookVO bookVO = new BookVO();
        bookVO.setShopId(shopId);
        bookVO.setShop(shop);
        bookVO.setMemberId(member.getId());
        bookVO.setMember(member);
        bookVO.setStartAppointmentTime(new DateTime(appointmentDate).withTimeAtStartOfDay().toDate());
        bookVO.setState(CommonConstant.STATE_ADD);

        bookVO.setBookingChannel(CommonConstant.BOOKING_CHANNEL_ONLINE);
        
        int itemSize = frontBookItemVOs.size();
        List<BookItemVO> bookItemVOs = new ArrayList<>();
        bookVO.setBookItemVOs(bookItemVOs);
        ProductOptionService productOptionService = SpringUtil.getBean(ProductOptionServiceImpl.class);
        RoomService roomService = SpringUtil.getBean(RoomServiceImpl.class);
        for (int i = 0; i < itemSize; i++) {
            FrontBookItemVO frontBookItemVO = frontBookItemVOs.get(i);
            ProductOption productOption = productOptionService.get(frontBookItemVO.getProductOptionId());
            BookItemVO bookItemVO = new BookItemVO();
            bookItemVO.setTempId(frontBookItemVO.getId());
            bookItemVO.setTempParentId(frontBookItemVO.getParentId());
            bookItemVO.setAppointmentTime(frontBookItemVO.getAppointmentTime());
            Date endAppointment = new DateTime((frontBookItemVO.getAppointmentTime())).plusMinutes(productOption.getDuration()).toDate();
            bookItemVO.setEndAppointmentTime(endAppointment);
            bookItemVO.setDuration(productOption.getDuration());
            bookItemVO.setProductOptionId(frontBookItemVO.getProductOptionId());
            bookItemVO.setProductOption(productOption);

            // setting request therapists
            if (frontBookItemVO.getTherapistVOS() != null && !frontBookItemVO.getTherapistVOS().isEmpty()) {
                /*List<RequestTherapistVO> requestTherapistVOs = new ArrayList<>();
                for (RequestTherapistVO requestTherapistVO : frontBookItemVO.getTherapistVOS()) {
                    if (therapist == null) {
                        continue;
                    }
                    RequestTherapistVO therapistVO = new RequestTherapistVO();
                    therapistVO.setOnRequest(true);
                    therapistVO.setTherapist(requestTherapistVO.getTherapist());
                    therapistVO.setTherapistId(therapist.getId());
                    requestTherapistVOs.add(therapistVO);
                }

                // 没有技师，转为waiting的bookItem
                if (requestTherapistVOs.isEmpty()) {
                    frontBookItemVO.setStatus(CommonConstant.BOOK_STATUS_WAITING);
                }*/
                /*for (RequestTherapistVO requestTherapistVO : frontBookItemVO.getTherapistVOS()) {
                    requestTherapistVO.setOnRequest(true);
                }*/
                bookItemVO.setRequestTherapistVOs(frontBookItemVO.getTherapistVOS());
            }

            if (frontBookItemVO.getRoomId() != null) {
                bookItemVO.setRoomId(frontBookItemVO.getRoomId());
                bookItemVO.setRoom(roomService.get(frontBookItemVO.getRoomId()));
            }

            bookItemVO.setStatus(frontBookItemVO.getStatus());
            bookItemVO.setGuestAmount(1);
            bookItemVO.setBookVO(bookVO);
            bookItemVOs.add(bookItemVO);
        }
        return bookVO;
    }
}
