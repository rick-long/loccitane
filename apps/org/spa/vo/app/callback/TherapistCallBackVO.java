package org.spa.vo.app.callback;

import org.spa.model.user.User;
import org.spa.serviceImpl.order.ReviewRatingTreatmentTherapistServiceImpl;
import org.spa.utils.SpringUtil;

import java.io.Serializable;

public class TherapistCallBackVO implements Serializable {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer sortId;
    private String staffId;
    private String staffName;
    private Double staffStarAvg;
    public TherapistCallBackVO() {
        super();
    }
    public TherapistCallBackVO(User therapist,Integer sortId){
    	this.staffId = therapist.getId().toString();
    	this.staffName = therapist.getDisplayName();
        this.staffStarAvg=getStarAvg(therapist.getId());
        this.sortId = sortId;
    }
  
    public Integer getSortId() {
		return sortId;
	}
    public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
   public String getStaffId() {
	   return staffId;
   }
   public void setStaffId(String staffId) {
	   this.staffId = staffId;
   }
   public String getStaffName() {
	   return staffName;
   }
   public void setStaffName(String staffName) {
	   this.staffName = staffName;
   }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Double getStaffStarAvg() {
        return staffStarAvg;
    }

    public void setStaffStarAvg(Double staffStarAvg) {
        this.staffStarAvg = staffStarAvg;
    }

    private  Double getStarAvg(Long staffId){
      Double StarAvg=0.0;
       ReviewRatingTreatmentTherapistServiceImpl rrts = SpringUtil.getBean(ReviewRatingTreatmentTherapistServiceImpl.class);
       if(rrts.getStaffStarAvgById(staffId)!=null){
           StarAvg=rrts.getStaffStarAvgById(staffId);

       }
    return StarAvg;
   }

}
