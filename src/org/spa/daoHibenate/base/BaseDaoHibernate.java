package org.spa.daoHibenate.base;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.dao.base.BaseDao;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */
@Repository
@Transactional
public class BaseDaoHibernate<T> implements BaseDao<T> {

	final protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private SessionFactory sessionFactory;

	private Class<T> entityClass;

	@SuppressWarnings({ "unchecked" })
	public BaseDaoHibernate() {
		this.entityClass = null;
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * {@inheritDoc}
	 */
	public Criteria getCriteria() {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.setCacheable(true);
		return criteria;
	}

	/**
	 * {@inheritDoc}
	 */
	public T load(Long id) {
		return getSession().load(this.entityClass, id);
	}

	/**
	 * {@inheritDoc}
	 */
	
	public T get(Long id) {
		return getSession().get(this.entityClass, id);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> get(Long[] ids) {
		return getSession().createQuery("from " + entityClass.getName() + " as model where model.id in (:ids) order by model.id desc").setParameterList("ids", ids).setCacheable(true).list();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public T get(String propertyName, Object value) {
		return (T) getSession().createQuery("from " + entityClass.getName() + " as model where model." + propertyName + " = ?").setParameter(0, value).setCacheable(true).uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public T get(DetachedCriteria criteria) {
		List<T> list = list(criteria);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> getList(String propertyName, Object value) {
		return getSession().createQuery("from " + entityClass.getName() + " as model where model." + propertyName + " = ? order by model.id desc").setParameter(0, value).setCacheable(true).list();
	}

	/**
	 * {@inheritDoc}
	 */
	public Long getCount() {
		return (Long) getCriteria().setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	public Long getCount(DetachedCriteria criteria) {
		/*Long count = (Long) criteria.setProjection(Projections.rowCount()).getExecutableCriteria(getSession()).uniqueResult();
		criteria.setProjection(null);*/
        Long count = (Long) criteria.setProjection(Projections.countDistinct("id")).getExecutableCriteria(getSession()).uniqueResult();
        criteria.setProjection(null);
		return count;
	}

    @Override
    public Long getId(DetachedCriteria criteria) {
        return (Long) criteria.getExecutableCriteria(getSession()).uniqueResult();
    }

	/**
	 * {@inheritDoc}
	 */
	public Long save(T entity) {
		return (Long) getSession().save(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(T entity) {
		getSession().update(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void merge(T entity) {
		getSession().merge(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(T entity) {
		getSession().delete(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Long id) {
		getSession().delete(load(id));
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Long[] ids) {
		for (Long id : ids) {
			delete(id);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(List<T> list) {
		if (list != null && !list.isEmpty()) {
			for (T t : list) {
				getSession().delete(t);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> list() {
		return getCriteria().addOrder(Order.desc("id")).list();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object findUniqueObject(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getSession()).setCacheable(true).uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<T> list(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getSession()).setCacheable(true).addOrder(Order.desc("id")).list();
	}

	/**
	 * {@inheritDoc}
	 */
//	public Page<T> list(DetachedCriteria criteria, Integer pageNumber) {
//		return list(criteria, pageNumber, Page.DEFAULT_PAGE_SIZE);
//	}
//
//	public Page<T> list(DetachedCriteria criteria, Page<T> page) {
//		return list(criteria, page.getPageNumber() == null ? 1 : page.getPageNumber(), page.getPageSize() == null ? Page.DEFAULT_PAGE_SIZE : page.getPageSize());
//	}

	/**
	 * {@inheritDoc}
	 */
    @SuppressWarnings("unchecked")
    public Page<T> list(DetachedCriteria criteria, Integer pageNumber, Integer pageSize) {
        Criteria executeCriteria = criteria.getExecutableCriteria(getSession());
        // 获取总记录数
        Long totalCount = (Long) executeCriteria.setProjection(Projections.countDistinct("id")).uniqueResult();
        // 获取分页内的id集合
        List<Long> ids = executeCriteria.setProjection(Projections.distinct(Projections.property("id")))
                .addOrder(Order.desc("id"))
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                .setCacheable(true)
                .list();
        // 查询具体一页的list数据
        List<T> res;
        if (ids.size() > 0) {
            // 防止ids为空的in集合查询
            res = executeCriteria.add(Restrictions.in("id", ids))
                    .setProjection(null)
                    .setFirstResult(0)
                    .setMaxResults(0)
                    .setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                    .list();
        } else {
            res = new ArrayList<>();
        }
        return new Page<>(res, totalCount, pageNumber, pageSize);
    }

    @SuppressWarnings("unchecked")
    public List<T> listData(DetachedCriteria criteria, Integer pageNumber, Integer pageSize) {
        return criteria.getExecutableCriteria(getSession()).setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                .setCacheable(true).addOrder(Order.desc("id")).list();
    }

    @SuppressWarnings("unchecked")
    public List<T> listData(DetachedCriteria criteria, int firstResult, int maxResults) {
        return criteria.getExecutableCriteria(getSession()).setFirstResult(firstResult).setMaxResults(maxResults).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                .setCacheable(true).addOrder(Order.desc("id")).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> listIds(DetachedCriteria criteria, int firstResult, int maxResults) {
        return criteria.getExecutableCriteria(getSession()).setFirstResult(firstResult).setMaxResults(maxResults).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                .setCacheable(true).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Long> listIds(DetachedCriteria criteria) {
        return criteria.getExecutableCriteria(getSession()).setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY)
                .setCacheable(true).list();
    }

    @SuppressWarnings("unchecked")
    public Page<T> list(DetachedCriteria criteria, Page<T> page) {
        Integer pageNumber = page.getPageNumber();
        Integer pageSize = page.getPageSize();
        return list(criteria, pageNumber, pageSize);
    }

	public T refresh(T entity) {
		getSession().refresh(entity);
		return entity;
	}

	@Override
	public List<T> getActiveListByRefAndCompany(DetachedCriteria criteria,String ref, Long companyId) {
		
		criteria.add(Restrictions.eq("isActive",true));
		if(StringUtils.isNotBlank(ref)){
			criteria.add(Restrictions.eq("reference", ref));
		}
		if(companyId !=null && companyId.longValue()>0){
			criteria.add(Restrictions.eq("company.id", companyId));
		}
		return list(criteria);
	}
	
	public void refreshCache() {
        CacheManager manager = CacheManager.create();
        Cache cache = manager.getCache(entityClass.getName());
        if(cache != null) {
            cache.removeAll();
            logger.debug("refresh cache for {}", cache.getName());
        }
    }

	@Override
	public String getNextIDForObject(String fieldName, String tableName, Integer number) {
		Integer size = number -1;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT IFNULL(( select ").append(fieldName).append(" from ").append(tableName.toUpperCase());
		sql.append(" where cast(").append(fieldName).append(" as SIGNED) > "+size);
		sql.append(" order by cast(").append(fieldName).append(" as SIGNED) desc limit 1),0)" );
		Session session = getSession();
		System.out.println("sql-----"+sql);
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString());

		List result = (List) sqlQuery.list();
		if(result == null && result.size() == 0){
			return null;
		}

		String str = null;
		if ("0".equals(String.valueOf(result.get(0)))) {
			str = String.valueOf(result.get(0));
		} else {
			str = String.valueOf(result.get(0)).substring(3 , 7);
		}
		return str;

	}
}
