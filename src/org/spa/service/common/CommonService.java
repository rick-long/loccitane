package org.spa.service.common;

import org.spa.dao.base.BaseDao;

/**
 * Created by wz832 on 2018/6/21.
 */
public interface CommonService extends BaseDao {

    public String getIDBySql(String fieldName, String tableName, Integer number);
}
