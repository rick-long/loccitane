package org.spa.serviceImpl.order;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.order.OrderSurvey;
import org.spa.service.order.OrderSurveyService;
import org.springframework.stereotype.Service;

/**
 * Created by ivy 2018-1-25
 */
@Service
public class OrderSurveyServiceImpl extends BaseDaoHibernate<OrderSurvey> implements OrderSurveyService {
	
	@Override
    public OrderSurvey getOrderSurveyByOrderId(Long orderId){
        if(orderId==null){
            return null;
        }
        DetachedCriteria dc = DetachedCriteria.forClass(OrderSurvey.class);
            dc.add(Restrictions.eq("purchaseOrder.id", orderId));
        return get(dc);
    }
}
