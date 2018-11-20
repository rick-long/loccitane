package org.spa.serviceImpl.user;

import com.spa.constant.CommonConstant;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.shop.Address;
import org.spa.model.shop.Phone;
import org.spa.model.user.Guest;
import org.spa.service.book.BookService;
import org.spa.service.user.GuestService;
import org.spa.service.user.UserService;
import org.spa.utils.EncryptUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.book.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class GuestServiceImpl extends BaseDaoHibernate<Guest> implements GuestService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Override
    public Guest saveOrUpdate(BookVO bookVO) {
        Guest guest = null;
        Date now = new Date();
        String currentUserName = WebThreadLocal.getUser().getUsername();
        if (bookVO.getId() != null) {
            guest = bookService.get(bookVO.getId()).getGuest();
        }

        if (guest == null) {
            guest = new Guest();
            // 新的才生成userName
            String userName = generateNextUsername();
            guest.setUsername(userName);
            guest.setEmail(bookVO.getEmail());
            Phone phone = new Phone();
            phone.setNumber(bookVO.getMobilePhone());
            phone.setGuest(guest);
            phone.setType(CommonConstant.PHONE_TYPE_MOBILE);
            guest.getPhones().add(phone);

            Address address = new Address();
            address.setCountry(bookVO.getCountry());
            address.setIsActive(true);
            address.setCreated(now);
            address.setCreatedBy(currentUserName);
            address.setLastUpdated(now);
            address.setLastUpdatedBy(currentUserName);
            address.setGuest(guest);
            guest.getAddresses().add(address);
        } else {
            guest.setEmail(bookVO.getEmail());
            Set<Phone> phoneSet = guest.getPhones();
            if (phoneSet.size() > 0) {
                for (Phone phone : phoneSet) {
                    if (CommonConstant.PHONE_TYPE_MOBILE.equals(phone.getType())) {
                        phone.setNumber(bookVO.getMobilePhone());
                        break;
                    }
                }
            } else {
                Phone phone = new Phone();
                phone.setNumber(bookVO.getMobilePhone());
                phone.setGuest(guest);
                phone.setType(CommonConstant.PHONE_TYPE_MOBILE);
                phoneSet.add(phone);
            }

            Set<Address> addressSet = guest.getAddresses();
            if (addressSet.size() > 0) {
                Address address = addressSet.iterator().next();
                address.setCountry(bookVO.getCountry());
                address.setLastUpdated(now);
                address.setLastUpdatedBy(currentUserName);
                addressSet.add(address);
            } else {
                Address address = new Address();
                address.setCountry(bookVO.getCountry());
                address.setIsActive(true);
                address.setCreated(now);
                address.setCreatedBy(currentUserName);
                address.setLastUpdated(now);
                address.setLastUpdatedBy(currentUserName);
                address.setGuest(guest);
                addressSet.add(address);
            }
        }

        guest.setFirstName(bookVO.getFirstName());
        guest.setLastName(bookVO.getLastName());
        guest.setFullName(bookVO.getFirstName() + " " + bookVO.getLastName());
        guest.setPassword(EncryptUtil.SHA1(RandomStringUtils.randomAlphanumeric(8)));    // 随机生成一个密码
        guest.setJoinDate(now);
        guest.setIsActive(true);
        guest.setCreated(now);
        guest.setCreatedBy(currentUserName);
        guest.setLastUpdated(now);
        guest.setLastUpdatedBy(currentUserName);
        saveOrUpdate(guest);
        return guest;
    }

    @Override
    public String generateNextUsername() {
        DetachedCriteria criteria = DetachedCriteria.forClass(Guest.class);
        criteria.setProjection(Projections.max("id"));
        Long maxId = getId(criteria);

        Long nextUsername = 100000L;
        if (maxId != null) {
            Guest guest = get(maxId);
            try {
                nextUsername = Long.parseLong(guest.getUsername()) + 1;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                nextUsername = guest.getId() + 20000L;
            }
            boolean hasGuest = hasGuest(nextUsername.toString());
            while (hasGuest) {
                nextUsername += 1;
                hasGuest = hasGuest(nextUsername.toString());
            }
        }
        return nextUsername.toString();
    }

    @Override
    public boolean hasGuest(String username) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Guest.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("username", username));
        return getCount(criteria) > 0;
    }

    @Override
    public Guest getGuestByUsername(String username) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Guest.class);
        criteria.add(Restrictions.eq("isActive", true));
        criteria.add(Restrictions.eq("username", username));
        return get(criteria);
    }

}
