package org.spa.serviceImpl.commission;

import java.util.Date;
import java.util.Set;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.commission.CommissionAttributeKey;
import org.spa.model.commission.CommissionTemplate;
import org.spa.service.commission.CommissionAttributeKeyService;
import org.spa.service.commission.CommissionTemplateService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.commission.CommissionAttributeKeyVO;
import org.spa.vo.commission.CommissionTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class CommissionTemplateServiceImpl extends BaseDaoHibernate<CommissionTemplate> implements CommissionTemplateService {

	@Autowired
	protected CommissionAttributeKeyService commissionAttributeKeyService;
	
    public void saveOrUpdate(CommissionTemplateVO templateVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        CommissionTemplate commissionTemplate;
        if (templateVO.getId() != null) {
            commissionTemplate = get(templateVO.getId());
        } else {
            commissionTemplate = new CommissionTemplate();
            commissionTemplate.setCompany(templateVO.getCompany());
            commissionTemplate.setIsActive(true);
            commissionTemplate.setCreated(now);
            commissionTemplate.setCreatedBy(userName);
        }
        
        commissionTemplate.setName(templateVO.getName());
        commissionTemplate.setDescription(templateVO.getDescription());

        Set<CommissionAttributeKey> attributeKeySet = commissionTemplate.getCommissionAttributeKeys();
        if(templateVO.getCommissionAttributeKeyVO() != null) {
        	CommissionAttributeKey attributeKey=null;
            for(CommissionAttributeKeyVO keyVO : templateVO.getCommissionAttributeKeyVO()) {
                boolean createTemplate=true;
            	if (templateVO.getId() != null) {
            		//edit
            		attributeKey=commissionAttributeKeyService.getCommissionAttributeKeyByRefAndTemplateId(keyVO.getReference(), templateVO.getId());
            		if(attributeKey !=null){
            			createTemplate=false;
            		}
            	}
                if(createTemplate){
                	attributeKey= new CommissionAttributeKey();
            		attributeKey.setCommissionTemplate(commissionTemplate);
            		attributeKey.setIsActive(true);
            		attributeKey.setCreated(now);
            		attributeKey.setCreatedBy(userName);
                }
                attributeKey.setReference(keyVO.getReference());
                attributeKey.setName(keyVO.getName());
                attributeKey.setDescription(keyVO.getDescription());
                attributeKey.setLastUpdated(now);
                attributeKey.setLastUpdatedBy(userName);
                attributeKeySet.add(attributeKey);
            }
        }
        commissionTemplate.setContent(templateVO.getContent());
        commissionTemplate.setLastUpdated(now);
        commissionTemplate.setLastUpdatedBy(userName);
        saveOrUpdate(commissionTemplate); // 保存
    }
}
