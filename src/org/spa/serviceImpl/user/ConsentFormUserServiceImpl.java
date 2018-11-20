package org.spa.serviceImpl.user;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.Document;
import org.spa.model.company.Company;
import org.spa.model.user.ConsentFormUser;
import org.spa.model.user.User;
import org.spa.service.DocumentService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.ConsentFormService;
import org.spa.service.user.ConsentFormUserService;
import org.spa.service.user.UserService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.user.ConsentFormUserSignVO;
import org.spa.vo.user.ConsentFormUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/08/29.
 */
@Service
public class ConsentFormUserServiceImpl extends BaseDaoHibernate<ConsentFormUser> implements ConsentFormUserService {

	@Autowired
	private UserService userService;
    
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ConsentFormService consentFormService;

    @Autowired
    private DocumentService documentService;
	
	
    @Override
    public void saveOrUpdate(ConsentFormUserSignVO consentFormUserSignVO) {
        Date now = new Date();
        String loginUser = WebThreadLocal.getUser().getUsername();

        Long userId=consentFormUserSignVO.getUserId();
        if(userId ==null){
            return;
        }

        User user=userService.get(userId);

        List<ConsentFormUserVO> consentFormUserVOs=consentFormUserSignVO.getConsentFormUserVOs();
        if(consentFormUserVOs ==null || (consentFormUserVOs !=null && consentFormUserVOs.size()==0)){
            return;
        }
        ConsentFormUser consentFormUser;
        Company company = WebThreadLocal.getCompany();
        for(ConsentFormUserVO vo : consentFormUserVOs){
            Long shopId=vo.getShopId();
            Long consentFormId=vo.getConsentFormId();
            if(shopId !=null && shopId >0){
                List<ConsentFormUser> consentFormUsers=getConsentFormSignedByFilter(userId, consentFormId, null);
                if(consentFormUsers !=null && consentFormUsers.size()>0){
                    consentFormUser=consentFormUsers.get(0);
                    consentFormUser.setLastUpdated(now);
                    consentFormUser.setLastUpdatedBy(loginUser);
                }else{
                    consentFormUser=new ConsentFormUser();
                    consentFormUser.setIsActive(true);
                    consentFormUser.setCreated(now);
                    consentFormUser.setCreatedBy(loginUser);
                    consentFormUser.setCompany(WebThreadLocal.getCompany());
                }
                consentFormUser.setConsentForm(consentFormService.get(consentFormId));
                consentFormUser.setShop(shopService.get(shopId));
                consentFormUser.setUser(user);
                System.out.println("company:" + company + ",coe:" + company.getCode());
                System.out.println(vo.getDocument());
                Document document = documentService.save(vo.getDocument(), company, CommonConstant.DOCUMENT_TYPE_CONSENT_FORM, CommonConstant.UPLOAD_DOCUMENT_CONSENT_FORM_PATH);
                if (document != null) {
                    consentFormUser.setDocumentId(document.getId());
                }
                saveOrUpdate(consentFormUser);
            }
        }
    }

	@Override
	public List<ConsentFormUser> getConsentFormSignedByFilter(Long userId,Long consentFormId,Long shopId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ConsentFormUser.class);
		criteria.add(Restrictions.eq("isActive", true));
		
		if(WebThreadLocal.getCompany() !=null){
			criteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
		}
		
		if(userId !=null && userId.longValue()>0){
			criteria.add(Restrictions.eq("user.id", userId));
		}
		
		if(consentFormId !=null && consentFormId.longValue()>0){
			criteria.add(Restrictions.eq("consentForm.id", consentFormId));
		}
		
		if(shopId !=null && shopId.longValue()>0){
			criteria.add(Restrictions.eq("shop.id", shopId));
		}
		
		List<ConsentFormUser> list=list(criteria);
		return list;
	}
}
