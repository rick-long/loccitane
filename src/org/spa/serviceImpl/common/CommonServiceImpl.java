package org.spa.serviceImpl.common;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.service.common.CommonService;
import org.springframework.stereotype.Service;

/**
 * Created by wz832 on 2018/6/21.
 */
@Service
public class CommonServiceImpl extends BaseDaoHibernate implements CommonService {

    @Override
    public String getIDBySql(String fieldName, String tableName, Integer number) {
        return getNextIDForObject(fieldName,tableName,number);
    }
}
