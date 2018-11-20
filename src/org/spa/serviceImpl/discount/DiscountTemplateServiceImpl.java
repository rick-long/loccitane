package org.spa.serviceImpl.discount;

import java.util.Date;
import java.util.Set;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.discount.DiscountAttributeKey;
import org.spa.model.discount.DiscountTemplate;
import org.spa.service.discount.DiscountAttributeService;
import org.spa.service.discount.DiscountTemplateService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.discount.DiscountAttributeKeyVO;
import org.spa.vo.discount.DiscountTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * @author Ivy 2016-5-12
 */
@Service
public class DiscountTemplateServiceImpl extends BaseDaoHibernate<DiscountTemplate> implements DiscountTemplateService {


    @Autowired
    private DiscountAttributeService discountAttributeService;

    public void saveOrUpdate(DiscountTemplateVO templateVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        DiscountTemplate discountTemplate;
        if (templateVO.getId() != null) {
            discountTemplate = get(templateVO.getId());
        } else {
            discountTemplate = new DiscountTemplate();
            discountTemplate.setCompany(templateVO.getCompany());
            discountTemplate.setIsActive(true);
            discountTemplate.setCreated(now);
            discountTemplate.setCreatedBy(userName);
        }

        discountTemplate.setName(templateVO.getName());
        discountTemplate.setDescription(templateVO.getDescription());

        Set<DiscountAttributeKey> attributeKeySet = discountTemplate.getDiscountAttributeKeys();
        Iterator<DiscountAttributeKey> keyIterator = attributeKeySet.iterator();
        while (keyIterator.hasNext()) {
            DiscountAttributeKey key = keyIterator.next();
            if (discountAttributeService.count(key.getId()) == 0) {
                keyIterator.remove();
            }
        }
        // attributeKeySet.clear();
        getSession().flush(); // 先删除以存在的attribute
        if (templateVO.getDiscountAttributeKeyVO() != null) {

            for (DiscountAttributeKeyVO keyVO : templateVO.getDiscountAttributeKeyVO()) {
                DiscountAttributeKey attributeKey = new DiscountAttributeKey();

                for (DiscountAttributeKey ak : attributeKeySet) {
                    if (ak.getReference().equals(keyVO.getReference())) {
                        attributeKey = ak;
                        break;
                    }
                }

                attributeKey.setDiscountTemplate(discountTemplate);
                attributeKey.setReference(keyVO.getReference());
                attributeKey.setName(keyVO.getName());
                attributeKey.setDescription(keyVO.getDescription());
                attributeKey.setIsActive(true);
                attributeKey.setCreated(now);
                attributeKey.setCreatedBy(userName);
                attributeKey.setLastUpdated(now);
                attributeKey.setLastUpdatedBy(userName);
                attributeKeySet.add(attributeKey);
            }
        }

        discountTemplate.setContent(templateVO.getContent());
        discountTemplate.setLastUpdated(now);
        discountTemplate.setLastUpdatedBy(userName);
        saveOrUpdate(discountTemplate); // 保存
    }

}
