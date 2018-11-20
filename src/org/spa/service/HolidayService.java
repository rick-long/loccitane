package org.spa.service;

import org.spa.dao.base.BaseDao;
import org.spa.model.Holiday;

import java.util.Date;

/**
 * Created by Ivy on 2016-8-4
 */
public interface HolidayService extends BaseDao<Holiday>{

    Holiday getHoliday(Date date);
}
