package org.spa.service.order;

import org.spa.dao.base.BaseDao;
import org.spa.model.order.OrderSurvey;

/**
 * Created by ivy 2018-1-25
 */
public interface OrderSurveyService extends BaseDao<OrderSurvey>{

	 public OrderSurvey getOrderSurveyByOrderId(Long orderId);
}
