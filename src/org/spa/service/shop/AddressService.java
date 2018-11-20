package org.spa.service.shop;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.shop.Address;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface AddressService extends BaseDao<Address>{
	//
	public List<Address> getAddressByUserId(Long userId);
	
	public List<Address> getAddressByShopId(Long shopId);
}
