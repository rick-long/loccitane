package org.spa.model.book;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.spa.model.company.Company;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;

@Entity
@Table(name = "BOOK_BATCH", catalog = "loccitane")
public class BookBatch implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	
	private String batchNumber;
	private User member;
	private User therapist;
	private Shop shop;
	private ProductOption productOption;
	
	private String repeatType;// 0: daily ,1:weekly,2:monthly
	private Date startDate;//yyyy-MM-dd
    private Date endDate;//yyyy-MM-dd
    private String startTime;//HH:mm
    private String endTime;//HH:mm
    
    private String months;//1,2,3...,12
    private String weeks;//1,2,3,4,5,6,7
    private String days;//1,2,3...,31
    private String cronExpression;
    
    private boolean isActive;
    private Date created;
    private String createdBy;
    private Date lastUpdated;
    private String lastUpdatedBy;
    private String remarks;

    private Company company;
    
    private Set<Book> books =new HashSet<Book>();
    
    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name = "batch_number", length = 100, nullable = false)
    public String getBatchNumber() {
		return batchNumber;
	}
    public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id", nullable = false)
    public ProductOption getProductOption() {
		return productOption;
	}
    public void setProductOption(ProductOption productOption) {
		this.productOption = productOption;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    public Shop getShop() {
        return this.shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    public User getMember() {
		return member;
	}
    public void setMember(User member) {
		this.member = member;
	}
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id")
    public User getTherapist() {
		return therapist;
	}
    public void setTherapist(User therapist) {
		this.therapist = therapist;
	}
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    public Company getCompany() {
		return company;
	}
    public void setCompany(Company company) {
		this.company = company;
	}
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date", nullable = false, length = 19)
    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date", nullable = false, length = 19)
    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    @Column(name = "start_time", nullable = false, length = 45)
    public String getStartTime() {
		return startTime;
	}
    public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
    
    @Column(name = "end_time", nullable = false, length = 45)
    public String getEndTime() {
		return endTime;
	}
    public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    @Column(name = "cron_expression", length = 45)
    public String getCronExpression() {
        return this.cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    @Column(name = "months", length = 100)
    public String getMonths() {
		return months;
	}
    public void setMonths(String months) {
		this.months = months;
	}
    @Column(name = "weeks", length = 100)
    public String getWeeks() {
		return weeks;
	}
    public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
    @Column(name = "days", length = 100)
    public String getDays() {
		return days;
	}
    public void setDays(String days) {
		this.days = days;
	}
    
    @Column(name = "remarks")
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "is_active", nullable = false)
    public boolean isIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", length = 19, nullable = false)
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "created_by", length = 100, nullable = false)
    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated", length = 19)
    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Column(name = "last_updated_by", length = 100)
    public String getLastUpdatedBy() {
        return this.lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Column(name = "repeat_type", length = 45, nullable = false)
    public String getRepeatType() {
        return this.repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookBatch")
    public Set<Book> getBooks() {
		return books;
	}
    public void setBooks(Set<Book> books) {
		this.books = books;
	}
    
}
