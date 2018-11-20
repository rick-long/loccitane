package org.spa.serviceImpl.shop;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.shop.Address;
import org.spa.service.shop.AddressService;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class AddressServiceImpl extends BaseDaoHibernate<Address> implements AddressService{
	//
	@Override
	public List<Address> getAddressByUserId(Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Address.class);
		criteria.add(Restrictions.eq("isActive", true));
		criteria.add(Restrictions.eq("user.id", userId));
		return list(criteria);
	}

	@Override
	public List<Address> getAddressByShopId(Long shopId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Address.class);
		criteria.add(Restrictions.eq("isActive", true));
		criteria.add(Restrictions.eq("shop.id", shopId));
		return list(criteria);
	}
}
