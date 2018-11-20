package org.spa.serviceImpl.version;


import org.spa.dao.version.VersionControlDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.version.VersionControl;
import org.spa.service.version.VersionControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class VersionControlServiceImpl extends BaseDaoHibernate<VersionControl> implements VersionControlService {
    @Autowired
    VersionControlDao versionControlDao;
   @Override
    public Map<String ,Object> getVersionControlTopOne(String type){

      Map<String ,Object>version= versionControlDao.getVersionControlTopOne(type);

   return  version;
  }
}
