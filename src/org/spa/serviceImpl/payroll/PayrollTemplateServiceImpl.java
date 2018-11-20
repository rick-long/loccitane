package org.spa.serviceImpl.payroll;

import java.util.Date;
import java.util.Set;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.payroll.PayrollAttributeKey;
import org.spa.model.payroll.PayrollTemplate;
import org.spa.service.payroll.PayrollAttributeKeyService;
import org.spa.service.payroll.PayrollTemplateService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.payroll.PayrollAttributeKeyVO;
import org.spa.vo.payroll.PayrollTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ivy 2016-7-25
 */
@Service
public class PayrollTemplateServiceImpl extends BaseDaoHibernate<PayrollTemplate> implements PayrollTemplateService {

	@Autowired
	protected PayrollAttributeKeyService payrollAttributeKeyService;
	
    public void saveOrUpdate(PayrollTemplateVO templateVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        PayrollTemplate payrollTemplate;
        if (templateVO.getId() != null) {
            payrollTemplate = get(templateVO.getId());
        } else {
            payrollTemplate = new PayrollTemplate();
            payrollTemplate.setCompany(templateVO.getCompany());
            payrollTemplate.setIsActive(true);
            payrollTemplate.setCreated(now);
            payrollTemplate.setCreatedBy(userName);
        }
        
        payrollTemplate.setName(templateVO.getName());
        payrollTemplate.setDescription(templateVO.getDescription());

        Set<PayrollAttributeKey> attributeKeySet = payrollTemplate.getPayrollAttributeKeys();
        if(templateVO.getPayrollAttributeKeyVO() != null) {
        	PayrollAttributeKey attributeKey=null;
        	boolean createTemplate=true;
            for(PayrollAttributeKeyVO keyVO : templateVO.getPayrollAttributeKeyVO()) {
            	if (templateVO.getId() != null) {
            		//edit
            		attributeKey=payrollAttributeKeyService.getPayrollAttributeKeyByRefAndTemplateId(keyVO.getReference(), templateVO.getId());
            		if(attributeKey !=null){
            			createTemplate=false;
            		}
            	}
                if(createTemplate){
                	attributeKey= new PayrollAttributeKey();
            		attributeKey.setPayrollTemplate(payrollTemplate);
            		attributeKey.setIsActive(true);
            		attributeKey.setCreated(now);
            		attributeKey.setCreatedBy(userName);
                }
                attributeKey.setReference(keyVO.getReference());
                attributeKey.setName(keyVO.getName());
                attributeKey.setDescription(keyVO.getDescription());
                attributeKey.setDisplayOrder(keyVO.getDisplayOrder());
                attributeKey.setLastUpdated(now);
                attributeKey.setLastUpdatedBy(userName);
                attributeKeySet.add(attributeKey);
            }
        }
        payrollTemplate.setContent(templateVO.getContent());
        payrollTemplate.setLastUpdated(now);
        payrollTemplate.setLastUpdatedBy(userName);
        saveOrUpdate(payrollTemplate); // 保存
    }
}
