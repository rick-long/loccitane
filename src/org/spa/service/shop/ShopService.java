package org.spa.service.shop;

import org.spa.dao.base.BaseDao;
import org.spa.model.shop.Shop;
import org.spa.vo.shop.ShopAddVO;
import org.spa.vo.shop.ShopEditVO;

import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
public interface ShopService extends BaseDao<Shop>{
	//
	public void saveShop(ShopAddVO shopAddVO);
	
	public void updateShop(ShopEditVO shopEditVO);

    public List<Shop> getListByCompany(Long companyId);

    public List<Shop> getListByCompany(Long companyId, boolean needOnlineShop);
    
    public List<Shop> getListByCompany(Long companyId, boolean needOnlineShop,boolean filterByLoginStaff);
    
    public List<Shop> getListByCompany(Long companyId, boolean needOnlineShop, boolean filterByLoginStaff,boolean isOnline);
    public List<Shop> getListByCompany(Long companyId, boolean needOnlineShop, boolean filterByLoginStaff,boolean isOnline,boolean showOnlineBooking);
    
    public List<Shop> getAllShop();
    
    public List<Shop> getAllShop(Long companyId);
}
