package org.spa.service.shop;


import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.shop.Phone;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface PhoneService extends BaseDao<Phone>{
	
	public List<Phone> getPhonesByUserIdAndType(Long userId, String type);
	
	public List<Phone> getPhonesByShopIdAndType(Long shopId, String type);
}
