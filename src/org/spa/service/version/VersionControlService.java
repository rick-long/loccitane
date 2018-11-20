package org.spa.service.version;

import org.spa.dao.base.BaseDao;
import org.spa.model.version.VersionControl;

import java.util.Map;

/**
 * Created by jason on 2018-3-20
 */
public interface VersionControlService extends BaseDao<VersionControl> {
  Map<String ,Object> getVersionControlTopOne(String type);

}
