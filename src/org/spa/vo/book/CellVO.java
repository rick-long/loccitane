package org.spa.vo.book;

import org.joda.time.DateTime;
import org.spa.model.book.Block;
import org.spa.model.book.BookItem;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 显示在book calendar上的一个bookItem
 *
 * @author Ivy 2016-11-2
 */
public class CellVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private DateTime time; // 日期
    private User therapist;
    private Shop shop;
    private Room room;
    private BookItem bookItem;

    private Block block;

    private CellVO parent;
    private List<CellVO> children = new ArrayList<>();
    private boolean onRequest;

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public BookItem getBookItem() {
        return bookItem;
    }

    public void setBookItem(BookItem bookItem) {
        this.bookItem = bookItem;
    }

    public CellVO getParent() {
        return parent;
    }

    public void setParent(CellVO parent) {
        this.parent = parent;
    }

    public List<CellVO> getChildren() {
        return children;
    }

    public void setChildren(List<CellVO> children) {
        this.children = children;
    }

    public boolean isOnRequest() {
        return onRequest;
    }

    public void setOnRequest(boolean onRequest) {
        this.onRequest = onRequest;
    }

    public User getTherapist() {
        return therapist;
    }

    public void setTherapist(User therapist) {
        this.therapist = therapist;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Transient
    public int getSize() {
        return children.size() + 1;
    }
}
