package org.spa.serviceImpl.bonus;

import java.util.Date;
import java.util.Set;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.bonus.BonusAttributeKey;
import org.spa.model.bonus.BonusTemplate;
import org.spa.service.bonus.BonusAttributeKeyService;
import org.spa.service.bonus.BonusTemplateService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.bonus.BonusAttributeKeyVO;
import org.spa.vo.bonus.BonusTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class BonusTemplateServiceImpl extends BaseDaoHibernate<BonusTemplate> implements BonusTemplateService {

	@Autowired
	protected BonusAttributeKeyService bonusAttributeKeyService;
	
    public void saveOrUpdate(BonusTemplateVO templateVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        BonusTemplate bonusTemplate;
        if (templateVO.getId() != null) {
            bonusTemplate = get(templateVO.getId());
        } else {
            bonusTemplate = new BonusTemplate();
            bonusTemplate.setCompany(templateVO.getCompany());
            bonusTemplate.setIsActive(true);
            bonusTemplate.setCreated(now);
            bonusTemplate.setCreatedBy(userName);
        }
        
        bonusTemplate.setName(templateVO.getName());
        bonusTemplate.setDescription(templateVO.getDescription());

        Set<BonusAttributeKey> attributeKeySet = bonusTemplate.getBonusAttributeKeys();
        if(templateVO.getBonusAttributeKeyVO() != null) {
        	BonusAttributeKey attributeKey=null;
        	boolean createTemplate=true;
            for(BonusAttributeKeyVO keyVO : templateVO.getBonusAttributeKeyVO()) {
            	if (templateVO.getId() != null) {
            		//edit
            		attributeKey=bonusAttributeKeyService.getBonusAttributeKeyByRefAndTemplateId(keyVO.getReference(), templateVO.getId());
            		if(attributeKey !=null){
            			createTemplate=false;
            		}
            	}
                if(createTemplate){
                	attributeKey= new BonusAttributeKey();
            		attributeKey.setBonusTemplate(bonusTemplate);
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
        bonusTemplate.setContent(templateVO.getContent());
        bonusTemplate.setLastUpdated(now);
        bonusTemplate.setLastUpdatedBy(userName);
        saveOrUpdate(bonusTemplate); // 保存
    }
}
