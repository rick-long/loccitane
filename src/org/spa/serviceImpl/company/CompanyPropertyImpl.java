package org.spa.serviceImpl.company;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.company.CompanyProperty;
import org.spa.service.company.CompanyPropertyService;
import org.springframework.stereotype.Service;

import com.spa.exception.ChainedRuntimeException;

@Service
public class CompanyPropertyImpl extends BaseDaoHibernate<CompanyProperty> implements CompanyPropertyService {
	
    public String getValueByName(String name){
		DetachedCriteria dc = DetachedCriteria.forClass(CompanyProperty.class);
		dc.add(Restrictions.eq("isActive", true));
		dc.createAlias("companyPropertyKey", "cpk");
		dc.add(Restrictions.eq("cpk.name", name));
		dc.add(Restrictions.eq("cpk.isActive", true));
		
		List<CompanyProperty> list=list(dc);
		if(list !=null && list.size()>0){
			return list.get(0).getValue();
		}else{
			DetachedCriteria dc2 = DetachedCriteria.forClass(CompanyProperty.class);
			dc2.add(Restrictions.eq("isActive", true));
			dc2.createAlias("companyPropertyKey", "cpk");
			dc2.add(Restrictions.eq("cpk.name", name));
			dc2.add(Restrictions.eq("cpk.isActive", true));
//			dc2.add(Restrictions.eq("company",null));
			List<CompanyProperty> list2=list(dc2);
			if(list2 !=null && list2.size()>0){
				return list2.get(0).getValue();
			}
		}
    	return null;
    }

	public boolean getBooleanValueByName(String name) {
		String value = getValueByName(name);
		return (StringUtils.isNotEmpty(value)) ? 
				BooleanUtils.toBoolean(value) : false;
	}

	public Date getDateValueByName(String name, String format) {
		String value = getValueByName(name);
		if (StringUtils.isNotEmpty(value)) {
			DateFormat df = new SimpleDateFormat(format);
			try {
				return df.parse(value);
			} 
			catch (ParseException e) {
				String msg = "Exception during parsing date property, name="+name+", value="+value+".";
				ChainedRuntimeException cre = new ChainedRuntimeException(msg);
				throw cre;
			}
		}
		else {
			return null;
		}
	}

	public long getLongValueByName(String name) {
		String value = getValueByName(name);
		return (StringUtils.isNotEmpty(value)) ? 
				Long.parseLong(value) : 0;
	}

	public int getIntegerValueByName(String name) {
		String value = getValueByName(name);
		return (StringUtils.isNotEmpty(value)) ? 
				Integer.parseInt(value) : 0;
	}

	@Override
	public Double getDoubleValueByName(String name) {
		String value = getValueByName(name);
		return (StringUtils.isNotEmpty(value)) ? 
				Double.parseDouble(value) : 0d;
	}
}