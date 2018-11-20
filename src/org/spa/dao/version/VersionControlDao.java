package org.spa.dao.version;

import java.util.Map;

/**
 * Created by jason on 2018-3-20
 */
public interface VersionControlDao {
 Map<String,Object> getVersionControlTopOne(String type);

}
