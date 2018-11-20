package org.spa.serviceImpl.outSource;

import java.util.Date;
import java.util.Set;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.shop.OutSourceAttributeKey;
import org.spa.model.shop.OutSourceTemplate;
import org.spa.service.outSource.OutSourceAttributeKeyService;
import org.spa.service.outSource.OutSourceTemplateService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.shop.OutSourceAttributeKeyVO;
import org.spa.vo.shop.OutSourceTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class OutSourceTemplateServiceImpl extends BaseDaoHibernate<OutSourceTemplate> implements OutSourceTemplateService {

	@Autowired
	protected OutSourceAttributeKeyService outSourceAttributeKeyService;
	
    public void saveOrUpdate(OutSourceTemplateVO templateVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        OutSourceTemplate outSourceTemplate;
        if (templateVO.getId() != null) {
            outSourceTemplate = get(templateVO.getId());
        } else {
            outSourceTemplate = new OutSourceTemplate();
            outSourceTemplate.setCompany(templateVO.getCompany());
            outSourceTemplate.setIsActive(true);
            outSourceTemplate.setCreated(now);
            outSourceTemplate.setCreatedBy(userName);
        }
        
        outSourceTemplate.setName(templateVO.getName());
        outSourceTemplate.setDescription(templateVO.getDescription());

        Set<OutSourceAttributeKey> attributeKeySet = outSourceTemplate.getOutSourceAttributeKeys();
        if(templateVO.getOutSourceAttributeKeyVO() != null) {
        	OutSourceAttributeKey attributeKey=null;
        	boolean createTemplate=true;
            for(OutSourceAttributeKeyVO keyVO : templateVO.getOutSourceAttributeKeyVO()) {
            	if (templateVO.getId() != null) {
            		//edit
            		attributeKey=outSourceAttributeKeyService.getOutSourceAttributeKeyByRefAndTemplateId(keyVO.getReference(), templateVO.getId());
            		if(attributeKey !=null){
            			createTemplate=false;
            		}
            	}
                if(createTemplate){
                	attributeKey= new OutSourceAttributeKey();
            		attributeKey.setOutSourceTemplate(outSourceTemplate);
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
        outSourceTemplate.setLastUpdated(now);
        outSourceTemplate.setLastUpdatedBy(userName);
        saveOrUpdate(outSourceTemplate); // 保存
    }
}
