package org.spa.serviceImpl.shop;

import com.spa.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.shop.Address;
import org.spa.model.shop.OpeningHours;
import org.spa.model.shop.Phone;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.service.outSource.OutSourceTemplateService;
import org.spa.service.shop.AddressService;
import org.spa.service.shop.OpeningHoursService;
import org.spa.service.shop.PhoneService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.shop.OpeningHoursVO;
import org.spa.vo.shop.ShopAddVO;
import org.spa.vo.shop.ShopEditVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class ShopServiceImpl extends BaseDaoHibernate<Shop> implements ShopService{

	@Autowired
	public PhoneService phoneService;
	
	@Autowired
	public AddressService addressService;
	
	@Autowired
	public OpeningHoursService openingHoursService;
	
	@Autowired
	public OutSourceTemplateService outSourceTemplateService;

	@Autowired
	private UserService userService;
	
	public void saveShop(ShopAddVO shopAddVO){
		Shop shop=new Shop();
		shop.setName(shopAddVO.getName());
		shop.setReference(shopAddVO.getReference());
		shop.setIsActive(true);
		shop.setIsOnline(Boolean.valueOf(shopAddVO.getIsOnline()));
		shop.setShowOnlineBooking(Boolean.valueOf(shopAddVO.getShowOnlineBooking()));
		shop.setRemarks(shopAddVO.getRemarks());
		shop.setEmail(shopAddVO.getEmail());
		shop.setPrefix(shopAddVO.getPrefix());
		//
		shop.setCompany(WebThreadLocal.getCompany());
		shop.setCreated(new Date());
		shop.setCreatedBy(WebThreadLocal.getUser().getUsername());
		
		if(shopAddVO.getOutSourceTemplateId() !=null && shopAddVO.getOutSourceTemplateId().longValue()>0){
			shop.setOutSourceTemplate(outSourceTemplateService.get(shopAddVO.getOutSourceTemplateId()));
		}else{
			shop.setOutSourceTemplate(null);
		}
		
		save(shop);
		
		if(StringUtils.isNotBlank(shopAddVO.getBusinessPhone())){
			Phone mPhone=new Phone();
			mPhone.setNumber(shopAddVO.getBusinessPhone());
			mPhone.setType(CommonConstant.PHONE_TYPE_BUSINESS);
			mPhone.setShop(shop);
			phoneService.save(mPhone);
		}
		if(StringUtils.isNotBlank(shopAddVO.getAddress())){
			Address addr=new Address();
			addr.setAddressExtention(shopAddVO.getAddress());
			
			addr.setIsActive(true);
			addr.setShop(shop);
			addr.setCreated(new Date());
			addr.setCreatedBy(WebThreadLocal.getUser().getUsername());
			addressService.save(addr);
		}
		List<OpeningHoursVO> openingHoursList=shopAddVO.getOpeningHoursList();
		OpeningHours openingHours=null;
		for(OpeningHoursVO vo:openingHoursList){
			openingHours=new OpeningHours();
			openingHours.setIsActive(true);
			openingHours.setCreated(new Date());
			openingHours.setCreatedBy(WebThreadLocal.getUser().getUsername());
			openingHours.setOnlineBooking(Boolean.valueOf(vo.getIsOnlineBooking()));
			if(StringUtils.isNotBlank(vo.getCloseTime())){
				openingHours.setCloseTime(vo.getCloseTime());
			}else{
				openingHours.setCloseTime(CommonConstant.CLOSE_HOUR_DEFAULT);
			}
			
			if(StringUtils.isNotBlank(vo.getOpenTime())){
				openingHours.setOpenTime(vo.getOpenTime());
			}else{
				openingHours.setOpenTime(CommonConstant.OPENING_HOUR_DEFAULT);
			}
			openingHours.setDayOfWeek(vo.getDayOfWeek());
			openingHours.setShop(shop);
			openingHours.setCompany(WebThreadLocal.getCompany());
			
			openingHoursService.save(openingHours);
		}

		// add shop to admin default shop
		List<User> userList = userService.getStaffListByRole(CommonConstant.STAFF_ROLE_REF_ADMIN);
		for (User user : userList) {
			user.getStaffHomeShops().add(shop);
			userService.saveOrUpdate(user);
		}
	}
	
	public void updateShop(ShopEditVO shopEditVO){
		Long id=shopEditVO.getId();
		Shop shop=get(id);
		shop.setName(shopEditVO.getName());
		shop.setIsActive(Boolean.valueOf(shopEditVO.getIsActive()));
		shop.setIsOnline(Boolean.valueOf(shopEditVO.getIsOnline()));
		shop.setShowOnlineBooking(Boolean.valueOf(shopEditVO.getShowOnlineBooking()));
		shop.setRemarks(shopEditVO.getRemarks());
		shop.setEmail(shopEditVO.getEmail());
		shop.setPrefix(shopEditVO.getPrefix());
		shop.setLastUpdated(new Date());
		shop.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
		if(shopEditVO.getOutSourceTemplateId() !=null && shopEditVO.getOutSourceTemplateId().longValue()>0){
			shop.setOutSourceTemplate(outSourceTemplateService.get(shopEditVO.getOutSourceTemplateId()));
		}else{
			shop.setOutSourceTemplate(null);
		}
		
		saveOrUpdate(shop);
		
		if(StringUtils.isNotBlank(shopEditVO.getBusinessPhone())){
			List<Phone> bpList=phoneService.getPhonesByShopIdAndType(shop.getId(), CommonConstant.PHONE_TYPE_BUSINESS);
			Phone bPhone=null;
			if(bpList !=null && bpList.size()>0){
				bPhone=bpList.get(0);
			}else{
				bPhone=new Phone();
				bPhone.setShop(shop);
				bPhone.setType(CommonConstant.PHONE_TYPE_BUSINESS);
			}
			bPhone.setNumber(shopEditVO.getBusinessPhone());
			phoneService.save(bPhone);
		}
		
		if(StringUtils.isNotBlank(shopEditVO.getAddress())){
			List<Address> addrList=addressService.getAddressByShopId(shop.getId());
			Address addr=null;
			if(addrList !=null && addrList.size()>0){
				addr=addrList.get(0);
				addr.setLastUpdated(new Date());
				addr.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
			}else{
				addr=new Address();
				addr.setIsActive(true);
				addr.setCreated(new Date());
				addr.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
				addr.setShop(shop);
			}
			
			addr.setAddressExtention(shopEditVO.getAddress());
			addressService.save(addr);
		}
		List<OpeningHoursVO> openingHoursList=shopEditVO.getOpeningHoursList();
		OpeningHours openingHours=null;
		for(OpeningHoursVO vo:openingHoursList){
			if(vo !=null && vo.getId() !=null){
				openingHours=openingHoursService.get(vo.getId());
				openingHours.setLastUpdated(new Date());
				openingHours.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
				openingHours.setOnlineBooking(Boolean.valueOf(vo.getIsOnlineBooking()));
			}else{
				openingHours=new OpeningHours();
				openingHours.setIsActive(true);
				openingHours.setCreated(new Date());
				openingHours.setCreatedBy(WebThreadLocal.getUser().getUsername());
				openingHours.setShop(shop);
				openingHours.setCompany(WebThreadLocal.getCompany());
				openingHours.setDayOfWeek(vo.getDayOfWeek());
			}
			if(StringUtils.isNotBlank(vo.getCloseTime())){
				openingHours.setCloseTime(vo.getCloseTime());
			}else{
				openingHours.setCloseTime(CommonConstant.CLOSE_HOUR_DEFAULT);
			}
			
			if(StringUtils.isNotBlank(vo.getOpenTime())){
				openingHours.setOpenTime(vo.getOpenTime());
			}else{
				openingHours.setOpenTime(CommonConstant.OPENING_HOUR_DEFAULT);
			}
			
			openingHoursService.save(openingHours);
		}
	}

    @Override
    public List<Shop> getListByCompany(Long companyId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
        criteria.addOrder(Order.asc("name"));
        return getActiveListByRefAndCompany(criteria, null, companyId);
    }

    @Override
    public List<Shop> getListByCompany(Long companyId, boolean needOnlineShop) {
    	// 过滤当前登录者的home shop
    	return getListByCompany(companyId, needOnlineShop, true);
    }

	@Override
	public List<Shop> getListByCompany(Long companyId, boolean needOnlineShop, boolean filterByLoginStaff) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
        if(!needOnlineShop) {
            criteria.add(Restrictions.ne("reference", CommonConstant.SHOP_ONLINE_REF));
        }
        if(filterByLoginStaff){
        	User loginStaff=WebThreadLocal.getUser();
        	criteria.createAlias("staffs", "staff");
        	criteria.add(Restrictions.eq("staff.id", loginStaff.getId()));
        }
        criteria.addOrder(Order.asc("name"));
        return getActiveListByRefAndCompany(criteria, null, companyId);
	}
	
	@Override
	public List<Shop> getListByCompany(Long companyId, boolean needOnlineShop, boolean filterByLoginStaff,boolean isOnline) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
        if(!needOnlineShop) {
            criteria.add(Restrictions.ne("reference", CommonConstant.SHOP_ONLINE_REF));
        }
        if(isOnline){
        	criteria.add(Restrictions.eq("isOnline",isOnline));
        }
        if(filterByLoginStaff){
        	User loginStaff=WebThreadLocal.getUser();
        	criteria.createAlias("staffs", "staff");
        	criteria.add(Restrictions.eq("staff.id", loginStaff.getId()));
        }
        criteria.addOrder(Order.asc("name"));
        return getActiveListByRefAndCompany(criteria, null, companyId);
	}
	@Override
	public List<Shop> getListByCompany(Long companyId, boolean needOnlineShop, boolean filterByLoginStaff,boolean isOnline,boolean showOnlineBooking) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
		if(!needOnlineShop) {
			criteria.add(Restrictions.ne("reference", CommonConstant.SHOP_ONLINE_REF));
		}
		if(isOnline){
			criteria.add(Restrictions.eq("isOnline",isOnline));
		}
		if (showOnlineBooking){
			criteria.add(Restrictions.eq("showOnlineBooking",showOnlineBooking));
		}
		if(filterByLoginStaff){
			User loginStaff=WebThreadLocal.getUser();
			criteria.createAlias("staffs", "staff");
			criteria.add(Restrictions.eq("staff.id", loginStaff.getId()));
		}
		criteria.addOrder(Order.asc("name"));
		return getActiveListByRefAndCompany(criteria, null, companyId);
	}
	
	@Override
    public List<Shop> getAllShop() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
//        criteria.add(Restrictions.eq("isActive", true));
        criteria.addOrder(Order.asc("name"));
        return list(criteria);
    }

	@Override
	public List<Shop> getAllShop(Long companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Shop.class);
		criteria.add(Restrictions.eq("isActive", true));
		if(companyId !=null){
			criteria.add(Restrictions.eq("company.id",companyId));
		}
		criteria.addOrder(Order.asc("name"));
        return list(criteria);
	}
}
