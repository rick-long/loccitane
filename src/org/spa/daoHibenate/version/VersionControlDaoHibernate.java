package org.spa.daoHibenate.version;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.spa.dao.version.VersionControlDao;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.version.VersionControl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VersionControlDaoHibernate extends BaseDaoHibernate<VersionControl> implements VersionControlDao{
@Override
   public Map<String ,Object> getVersionControlTopOne(String type){
       Map<String ,Object> version=new HashMap<>();
    StringBuffer hql=new StringBuffer();
       hql.append(" SELECT");
       hql.append(" name,version_code,description,url,size");
       hql.append(" from version_control");
       hql.append(" where is_active=1");
       if(StringUtils.isNotBlank(type)){
           hql.append(" and type='"+type+"'");
       }
       hql.append(" order by created desc limit 1;");
       Session session=getSession();
       SQLQuery query=session.createSQLQuery(hql.toString());
       List<Object[]> list=query.list();
       for(Object[] object :list){
           version.put("name",object[0]);
           version.put("versionCode",object[1]);
           version.put("description",object[2]);
           version.put("url",object[3]);
           version.put("size",object[4]);
       }
        return version;
    }


}
