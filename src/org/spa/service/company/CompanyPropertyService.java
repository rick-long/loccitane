package org.spa.service.company;

import java.util.Date;

import org.spa.dao.base.BaseDao;
import org.spa.model.company.CompanyProperty;

/**
 * Created by Ivy on 2016/07/11.
 */
public interface CompanyPropertyService extends BaseDao<CompanyProperty>{
	
	public String getValueByName(String name);
	
	public boolean getBooleanValueByName(String name);
	
	public Date getDateValueByName(String name, String format);
	
	public long getLongValueByName(String name);
	
	public int getIntegerValueByName(String name);
	
	public Double getDoubleValueByName(String name);
}
