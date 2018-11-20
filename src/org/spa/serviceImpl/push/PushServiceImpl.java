package org.spa.serviceImpl.push;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.push.Push;
import org.spa.service.push.PushService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.push.PushVO;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by jason on 2018/04/16.
 *
 */
@Service
public class PushServiceImpl extends BaseDaoHibernate<Push> implements PushService {
@Override
public void save (PushVO pushVO){
    Push push=new Push();
    push.setTitle(pushVO.getTitle());
    push.setFacilityType(pushVO.getFacilityType());
    push.setImageUrl(pushVO.getImageUrl());
    push.setUrl(pushVO.getUrl());
    push.setLabel(pushVO.getLabel());
    push.setSendDate(pushVO.getSendDate());
    push.setSendType(pushVO.getSendType());
    push.setStatus(pushVO.getStatus());
    push.setIsActive(true);
    push.setCreated(new Date());
    push.setCreatedBy(WebThreadLocal.getUser().getUsername());
    push.setLastUpdated(new Date());
    push.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
    save(push);
}



}

