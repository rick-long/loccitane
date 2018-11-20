package org.spa.serviceImpl.user;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.user.ConsentForm;
import org.spa.model.user.ConsentFormUser;
import org.spa.service.user.ConsentFormService;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.user.ConsentFormVO;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/08/29.
 */
@Service
public class ConsentFormServiceImpl extends BaseDaoHibernate<ConsentForm> implements ConsentFormService {

    
    @Override
    public void saveOrUpdate(ConsentFormVO consentFormVo) {
        Date now = new Date();
        String currentUserName = WebThreadLocal.getUser().getUsername();
        ConsentForm consentForm=null;
        if(consentFormVo.getId() !=null){
        	consentForm=get(consentFormVo.getId());
        	consentForm.setLastUpdated(now);
			consentForm.setIsActive(consentFormVo.getActive());
            consentForm.setLastUpdatedBy(currentUserName);
        }else{
        	consentForm=new ConsentForm();
        	consentForm.setIsActive(true);
        	consentForm.setCreated(now);
        	consentForm.setCreatedBy(currentUserName);
        	consentForm.setReference(RandomUtil.generateRandomNumberWithDate("CF"));
        	consentForm.setCompany(WebThreadLocal.getCompany());
        }
        consentForm.setName(consentFormVo.getName());
        consentForm.setRemarks(consentFormVo.getRemarks());
        saveOrUpdate(consentForm);;
    }
    
    @Override
    public ConsentFormUser getconsentFormUserBySignedConsentForm(Long consentFormId,Long userId)
	  {
	    DetachedCriteria dc = DetachedCriteria.forClass(ConsentFormUser.class);
		dc.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
		dc.add(Restrictions.eq("consentForm.id", consentFormId));
		dc.add(Restrictions.eq("user.id", userId));
		dc.add(Restrictions.eq("isActive", true));
		List consentFormUserList =list(dc);
	    if (!consentFormUserList.isEmpty()) {
	      ConsentFormUser consentFormUser = (ConsentFormUser)consentFormUserList.get(0);
	      return consentFormUser;
	    }
	    return null;
	  }
}
