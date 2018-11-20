package org.spa.serviceImpl.shop;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.shop.Phone;
import org.spa.service.shop.PhoneService;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class PhoneServiceImpl extends BaseDaoHibernate<Phone> implements PhoneService{

	@Override
	public List<Phone> getPhonesByUserIdAndType(Long userId, String type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Phone.class);
		criteria.add(Restrictions.eq("type", type));
		criteria.add(Restrictions.eq("user.id", userId));
		return list(criteria);
	}
	
	@Override
	public List<Phone> getPhonesByShopIdAndType(Long shopId, String type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Phone.class);
		criteria.add(Restrictions.eq("type", type));
		criteria.add(Restrictions.eq("shop.id", shopId));
		return list(criteria);
	}
	
}
