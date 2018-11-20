package org.spa.serviceImpl.user;

import com.spa.constant.CommonConstant;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.staff.StaffHomeShopDetails;
import org.spa.model.user.User;
import org.spa.service.user.StaffHomeShopDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2018/05/02.
 */
@Service
public class StaffHomeShopDetailsServiceImpl extends BaseDaoHibernate<StaffHomeShopDetails> implements StaffHomeShopDetailsService {
    @Override
    public void init(Long staffId){
        List <StaffHomeShopDetails> staffHomeShopDetailsList=getList("user.id",staffId);
        if (staffHomeShopDetailsList!=null&&staffHomeShopDetailsList.size()>0){
            for (StaffHomeShopDetails staffHomeShopDetails:staffHomeShopDetailsList){
                staffHomeShopDetails.setIsActive(false);
                saveOrUpdate(staffHomeShopDetails);
            }
        }
    }

    @Override
    public List<User> getTherapistByShop(Long shopId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(StaffHomeShopDetails.class);
        criteria.add(Restrictions.eq("shop.id", shopId));
        criteria.add(Restrictions.eq("isActive",true));
        criteria.createAlias("user", "staff");
        criteria.add(Restrictions.eq("staff.enabled", true));
        criteria.add(Restrictions.eq("staff.accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
        criteria.createAlias("staff.sysRoles", "sr");
        criteria.add(Restrictions.in("sr.reference", new String[]{CommonConstant.STAFF_ROLE_REF_THERAPIST, CommonConstant.STAFF_ROLE_REF_SHOP_MANAGER_T, CommonConstant.STAFF_ROLE_REF_RECEPTION_T}));
        criteria.addOrder(Order.asc("sort"));
        List<StaffHomeShopDetails>staffHomeShopDetailsList=list(criteria);
        List<User> therapistList=new ArrayList<>();
        if (staffHomeShopDetailsList!=null&&staffHomeShopDetailsList.size()>0){
            for (StaffHomeShopDetails staffHomeShopDetails:staffHomeShopDetailsList){
                if (staffHomeShopDetails.getUser()!=null){
                    therapistList.add(staffHomeShopDetails.getUser());
                }
            }

        }
        return therapistList;
    }

    @Override
    public List<StaffHomeShopDetails> getStaffHomeShopDetailsList(Long shopId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(StaffHomeShopDetails.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("shop.id", shopId));
        criteria.createAlias("user", "staff");
        criteria.add(Restrictions.eq("staff.enabled", true));
        criteria.add(Restrictions.eq("staff.accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
        criteria.createAlias("staff.sysRoles", "sr");
        criteria.add(Restrictions.in("sr.reference", new String[]{CommonConstant.STAFF_ROLE_REF_THERAPIST, CommonConstant.STAFF_ROLE_REF_SHOP_MANAGER_T, CommonConstant.STAFF_ROLE_REF_RECEPTION_T}));
        criteria.addOrder(Order.asc("sort"));
        List<StaffHomeShopDetails>staffHomeShopDetailsList=list(criteria);


        return staffHomeShopDetailsList;
    }
}
