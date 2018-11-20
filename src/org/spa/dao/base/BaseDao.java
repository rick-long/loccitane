package org.spa.dao.base;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.spa.vo.page.Page;
import java.util.List;

/**
 * Created by Ivy on 2016/01/16.
 */

public interface BaseDao<T> {
	
	public void setEntityClass(Class<T> entityClass);
	
	public Session getSession();
	
	public Criteria getCriteria();
	
	public T load(Long id);
	
	public T get(Long id);

	public List<T> get(Long[] ids);
	
	public T get(String propertyName, Object value);
	
	public T get(DetachedCriteria criteria);
	
	public List<T> getList(String propertyName, Object value);
	
	public Long getCount();
	
	public Long getCount(DetachedCriteria criteria);

    Long getId(DetachedCriteria criteria);

    public Long save(T entity);
	
	public void saveOrUpdate(T entity);
	
	public void update(T entity);
	
	public void merge(T entity);
	
	public void delete(T entity);
	
	public void delete(Long id);
	
	public void delete(Long[] ids);
	
	public void delete(List<T> list);
	
	public List<T> list();
	
	public Object findUniqueObject(DetachedCriteria criteria);
	
	public List<T> list(DetachedCriteria criteria);
//	public Page<T> list(DetachedCriteria criteria, Integer pageNumber);
	
//	public Page<T> list(DetachedCriteria criteria, Page<T> page);
	
	public Page<T> list(DetachedCriteria criteria, Integer pageNumber, Integer pageSize);
	
	public List<T> listData(DetachedCriteria criteria, Integer pageNumber, Integer pageSize);

    public List<T> listData(DetachedCriteria criteria, int firstResult, int maxResults);

    List<Long> listIds(DetachedCriteria criteria, int firstResult, int maxResults);

    @SuppressWarnings("unchecked")
    List<Long> listIds(DetachedCriteria criteria);

    public Page<T> list(DetachedCriteria criteria, Page<T> page);

	public T refresh(T entity);
	
	//
	public List<T> getActiveListByRefAndCompany(DetachedCriteria criteria,String ref, Long companyId);
	
	public void refreshCache();

	public String getNextIDForObject(String fieldName, String tableName, Integer number);
}
