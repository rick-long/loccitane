package org.spa.serviceImpl.marketing;

import java.util.Date;

import com.spa.constant.CommonConstant;
import com.spa.email.EmailSender;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.marketing.MktMailShot;
import org.spa.model.marketing.MktOutbox;
import org.spa.service.marketing.MktMailShotService;
import org.spa.service.marketing.MktOutboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016-6-1
 */
@Service
public class MktOutboxServiceImpl extends BaseDaoHibernate<MktOutbox> implements MktOutboxService {

    @Autowired
    private MktMailShotService mktMailShotService;

    public void save(EmailSender emailSender, String status) {
        Date date = new Date();
        MktOutbox mktOutbox = new MktOutbox();
        MktMailShot mktMailShot = emailSender.getMktMailShot();
        mktOutbox.setMktMailShot(mktMailShot);

        mktOutbox.setUser(emailSender.getUser());
        mktOutbox.setStatus(status);
        mktOutbox.setEmail(emailSender.getToAddressesStr());
        mktOutbox.setIsActive(true);
        mktOutbox.setCreated(date);
        mktOutbox.setCreatedBy(emailSender.getExecutor());
        save(mktOutbox);

        if (mktMailShot.getSendAmount() == getCount(mktMailShot.getId()).intValue()) {
            // 已结发送完成，更新mailshot状态
            mktMailShot.setStatus(CommonConstant.MAIL_SHOT_STATUS_COMPLETE);
            mktMailShot.setLastUpdated(date);
            mktMailShot.setLastUpdatedBy(emailSender.getExecutor());
            mktMailShotService.saveOrUpdate(mktMailShot);
        }
    }

    public Long getCount(Long mailShotId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MktOutbox.class);
        criteria.add(Restrictions.eq("mktMailShot.id", mailShotId));
        return getCount(criteria);
    }
}
