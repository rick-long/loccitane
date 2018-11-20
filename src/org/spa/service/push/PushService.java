package org.spa.service.push;

import org.spa.dao.base.BaseDao;
import org.spa.model.push.Push;
import org.spa.vo.push.PushVO;

/**
 * Created by Jason 2018 04 17
 */
public interface PushService extends BaseDao<Push> {
	public void save(PushVO pushVO);

}
