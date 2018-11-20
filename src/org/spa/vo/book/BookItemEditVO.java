package org.spa.vo.book;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.spa.constant.CommonConstant;
import org.spa.model.book.BookItem;
import org.spa.model.shop.Room;
import org.spa.model.user.User;

/**
 * @author Ivy 2016-7-20
 */
public class BookItemEditVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bookItemId;

    private BookItem bookItem;

    private Long appointmentTimeStamp;

    private Date appointmentTime;

    private Long shopId;

    private Long roomId;

    private Room room;

    private Long therapistId;

    private User therapist;

    private List<User> availableTherapists;

    public Long getBookItemId() {
        return bookItemId;
    }

    public void setBookItemId(Long bookItemId) {
        this.bookItemId = bookItemId;
    }

    public Long getAppointmentTimeStamp() {
        return appointmentTimeStamp;
    }

    public void setAppointmentTimeStamp(Long appointmentTimeStamp) {
        this.appointmentTimeStamp = appointmentTimeStamp;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public List<User> getAvailableTherapists() {
        return availableTherapists;
    }

    public void setAvailableTherapists(List<User> availableTherapists) {
        this.availableTherapists = availableTherapists;
    }

    public BookItem getBookItem() {
        return bookItem;
    }

    public void setBookItem(BookItem bookItem) {
        this.bookItem = bookItem;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public ViewVO transferToViewVO() {
        ViewVO viewVO = new ViewVO();
        viewVO.setBookItemId(getBookItemId());
        viewVO.setShopId(getShopId());
        viewVO.setViewType(CommonConstant.ROOM_VIEW);
        viewVO.setAppointmentTimeStamp(getAppointmentTimeStamp());
        viewVO.setRoomId(getRoomId());
        viewVO.setNewTherapistId(getTherapistId());
        return viewVO;
    }
}
