package org.spa.serviceImpl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.Holiday;
import org.spa.service.HolidayService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Ivy 2016-8-4
 */
@Service
public class HolidayServiceImpl extends BaseDaoHibernate<Holiday> implements HolidayService {

    @Override
    public Holiday getHoliday(Date date) {
        if (date == null) {
            return null;
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(Holiday.class);
        criteria.add(Restrictions.le("startDate", date));
        criteria.add(Restrictions.gt("endDate", date));
        criteria.add(Restrictions.eq("isActive", true));

        Holiday holiday = get(criteria);
        if (holiday != null) {
            logger.debug("Found holiday:{}", holiday);
            return holiday;
        }
        return null;
    }

}
