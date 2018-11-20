package org.spa.service.marketing;

import com.spa.email.EmailSender;
import org.spa.dao.base.BaseDao;
import org.spa.model.marketing.MktOutbox;

/**
 * Created by Ivy on 2016-6-1
 */
public interface MktOutboxService extends BaseDao<MktOutbox>{

    public void save(EmailSender emailSender, String status);

    public Long getCount(Long mailShotId);
}
