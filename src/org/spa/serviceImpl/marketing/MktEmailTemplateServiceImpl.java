package org.spa.serviceImpl.marketing;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.marketing.MktEmailTemplate;
import org.spa.service.marketing.MktEmailTemplateService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.marketing.MktEmailTemplateVO;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Ivy on 2016-6-3
 */
@Service
public class MktEmailTemplateServiceImpl extends BaseDaoHibernate<MktEmailTemplate> implements MktEmailTemplateService {

    public void saveOrUpdate(MktEmailTemplateVO emailTemplateVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        MktEmailTemplate mktEmailTemplate;
        if (emailTemplateVO.getId() != null) {
            mktEmailTemplate = get(emailTemplateVO.getId());
            mktEmailTemplate.setIsActive(emailTemplateVO.getIsActive());
        } else {
            mktEmailTemplate = new MktEmailTemplate();
            mktEmailTemplate.setType(emailTemplateVO.getType());
            mktEmailTemplate.setCompany(emailTemplateVO.getCompany());
            mktEmailTemplate.setIsActive(true);
            mktEmailTemplate.setCreated(now);
            mktEmailTemplate.setCreatedBy(userName);
        }
        mktEmailTemplate.setSubject(emailTemplateVO.getSubject());
        mktEmailTemplate.setContent(emailTemplateVO.getContent());
        mktEmailTemplate.setLastUpdated(now);
        mktEmailTemplate.setLastUpdatedBy(userName);
        saveOrUpdate(mktEmailTemplate);
    }

    public MktEmailTemplate getByType(String type, Long companyId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MktEmailTemplate.class);
        criteria.add(Restrictions.eq("type", type));
        criteria.add(Restrictions.eq("isActive", true));
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.eq("company.id", companyId));
        disjunction.add(Restrictions.isNull("company.id"));
        criteria.add(disjunction);
        criteria.addOrder(Order.desc("company.id"));
        return get(criteria);
    }
}
