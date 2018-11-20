package org.spa.service.user;

import org.spa.dao.base.BaseDao;
import org.spa.model.staff.StaffHomeShopDetails;
import org.spa.model.user.User;

import java.util.List;

/**
 * Created by jason on 2018-5-02
 */
public interface StaffHomeShopDetailsService extends BaseDao<StaffHomeShopDetails> {
    public void init(Long staffId);
    public   List<User>  getTherapistByShop(Long shopId);
    public List<StaffHomeShopDetails> getStaffHomeShopDetailsList(Long shopId);
}
