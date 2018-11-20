package org.spa.service.user;

import org.spa.dao.base.BaseDao;
import org.spa.model.user.Guest;
import org.spa.vo.book.BookVO;

/**
 * Created by Ivy on 2017-1-22
 */
public interface GuestService extends BaseDao<Guest> {

    Guest saveOrUpdate(BookVO bookVO);

    String generateNextUsername();

    boolean hasGuest(String username);

    Guest getGuestByUsername(String username);
}
