package org.spa.serviceImpl.marketing;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.marketing.MktChannel;
import org.spa.service.marketing.MktChannelService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.marketing.MktChannelVO;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016-6-1
 */
@Service
public class MktChannelServiceImpl extends BaseDaoHibernate<MktChannel> implements MktChannelService {


    public void saveOrUpdate(MktChannelVO channelVO) {
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        MktChannel mktChannel;
        if (channelVO.getId() != null) {
            mktChannel = get(channelVO.getId());
            mktChannel.setIsActive(channelVO.getIsActive());
        } else {
            mktChannel = new MktChannel();
            mktChannel.setReference(channelVO.getReference());
            mktChannel.setCompany(channelVO.getCompany());
            mktChannel.setIsActive(true);
            mktChannel.setCreated(now);
            mktChannel.setCreatedBy(userName);
        }
        mktChannel.setName(channelVO.getName());
        mktChannel.setLastUpdated(now);
        mktChannel.setLastUpdatedBy(userName);
        saveOrUpdate(mktChannel);
    }

    @Override
    public List<MktChannel> getActiveListByCompany(Long companyId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MktChannel.class);
        criteria.addOrder(Order.asc("name"));
        return getActiveListByRefAndCompany(criteria, null, companyId);
    }

    public MktChannel getByRef(String reference, Long companyId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MktChannel.class);
        criteria.add(Restrictions.eq("reference", reference));
        criteria.add(Restrictions.eq("isActive", true));
        Disjunction disjunction = Restrictions.disjunction();
        if(companyId != null) {
            disjunction.add(Restrictions.eq("company.id", companyId));
        }
        disjunction.add(Restrictions.isNull("company.id"));
        criteria.add(disjunction);
        criteria.addOrder(Order.desc("company.id"));
        return get(criteria);
    }

    @Override
    public MktChannel getByRef(String reference) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MktChannel.class);
        criteria.add(Restrictions.eq("reference", reference));
        criteria.add(Restrictions.eq("isActive", true));
        Disjunction disjunction = Restrictions.disjunction();
        criteria.add(disjunction);
        criteria.addOrder(Order.desc("company.id"));
        return get(criteria);
    }
}
