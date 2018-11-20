package org.spa.service.marketing;

import org.spa.dao.base.BaseDao;
import org.spa.model.marketing.MktChannel;
import org.spa.vo.marketing.MktChannelVO;

import java.util.List;

/**
 * Created by Ivy on 2016-6-1
 */
public interface MktChannelService extends BaseDao<MktChannel>{

    public void saveOrUpdate(MktChannelVO channelVO);

    public List<MktChannel> getActiveListByCompany(Long companyId);

    public MktChannel getByRef(String reference, Long companyId);

    public MktChannel getByRef(String reference);
}
