package org.spa.serviceImpl.payment;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.payment.PaymentMethod;
import org.spa.service.payment.PaymentMethodService;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/04/21.
 */
@Service
public class PaymentMethodServiceImpl extends BaseDaoHibernate<PaymentMethod> implements PaymentMethodService{

	@Override
	public List<PaymentMethod> getActivePaymentMethods(Long companyId, Boolean isForPrepaid) {
		DetachedCriteria dc = DetachedCriteria.forClass(PaymentMethod.class);
		dc.add(Restrictions.eq("isActive", true));
		if(companyId !=null && companyId.longValue()>0){
			dc.add(Restrictions.eq("company.id",companyId));
		}
		if(isForPrepaid !=null){//search true or false
			dc.add(Restrictions.eq("isForPrepaid", isForPrepaid));
		}// is null--search all
		
		dc.addOrder(Order.desc("displayOrder"));
		List<PaymentMethod> pmList=list(dc);
		return pmList;
	}

	@Override
	public PaymentMethod getPaymentMethodByRef(String ref,Long companyId) {
		DetachedCriteria dc = DetachedCriteria.forClass(PaymentMethod.class);
		dc.add(Restrictions.eq("isActive", true));
		if(companyId !=null && companyId.longValue()>0){
			dc.add(Restrictions.eq("company.id",companyId));
		}
		if(StringUtils.isNotBlank(ref)){
			dc.add(Restrictions.eq("reference", ref));
		}
		dc.addOrder(Order.desc("displayOrder"));
		List<PaymentMethod> pmList=list(dc);
		if(pmList !=null && pmList.size()>0){
			return pmList.get(0);
		}
		return null;
	}

}
