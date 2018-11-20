package org.spa.model.staff;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.spa.model.shop.Address;
import org.spa.vo.page.Page;

/**
 * created by rick 2018.8.29
 * 职工考勤记录表
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "STAFF_CLOCK_IN_OUT", catalog = "loccitane")
public class StaffInOrOut extends Page<StaffInOrOut> implements Serializable {
    /**id*/
    private Long id;
    /**职工id*/
    private Long staffId;
    /**职工姓名*/
    private String name;
    /**职工打卡时间*/
    private String date;
    /**打卡类型*/
    private Long type;
    /**创建者是谁*/
    private String createdBy;
    /**打卡保存时间*/
    private Date dateTime;
   /**打卡店铺id*/
    private Long shopId;
    /**上班时间*/
    private String duration;
    /**ot时间*/
    private String ot;
    /**shopName*/
    private String shopName;
    
    private Address address;

    private String warningMsg;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "staff_id", length = 45)
    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
    @Column(name = "date", length = 45)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Column(name = "type")
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    @Column(name = "created_time")
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Column(name = "shop_id")
    public Long getShopId() {
        return this.shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Column(name = "duration")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
}

    @Column(name = "ot")
    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    @Column(name = "shop_name")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="address_id")
    public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

    @Column(name = "warning_msg", length = 255)
    public String getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }
}
