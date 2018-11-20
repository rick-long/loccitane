package org.spa.serviceImpl.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Room;
import org.spa.model.shop.RoomTreatments;
import org.spa.service.product.ProductOptionService;
import org.spa.service.shop.RoomTreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ken on 2018/10/28.
 */
@Service
public class RoomTreatmentServiceImpl extends BaseDaoHibernate<RoomTreatments> implements RoomTreatmentService {

	@Autowired
	private ProductOptionService productOptionService;

	public List<Room> getAvailableRoomList(Long productOptionId, Long shopId) {
		ProductOption productOption = productOptionService.get(productOptionId);
		if (productOption == null) {
			return new ArrayList<>();
		}

		Product product = productOption.getProduct();
		List<Long> categoryIds = product.getAllCategoryIds();

		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select DISTINCT t1.* from ROOM t1");
		queryBuilder.append(" left join ROOM_TREATMENTS rt on rt.room_id = t1.id");
		queryBuilder.append(" where t1.shop_id=?");
		queryBuilder.append(" and t1.is_active=?");
		queryBuilder.append(" and ( ");
		queryBuilder.append(" rt.category_id in (");
		queryBuilder.append(" ?");
		for (int i = 1; i < categoryIds.size(); i++) {
			queryBuilder.append(",?");
		}
		queryBuilder.append(" ) or rt.product_id=?");
		queryBuilder.append(" ) order by t1.capacity asc");
		Session session = getSession();
		String sql = queryBuilder.toString();
		System.out.println("sql:" + sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Room.class);

		query.setLong(0, shopId);
		query.setBoolean(1, true);
		int index = 2;
		for (Long categoryId : categoryIds) {
			query.setLong(index, categoryId);
			index++;
		}
		query.setLong(index, product.getId());
		return query.list();
	}

	@Override
	public List<RoomTreatments> getRoomByShopAndProductOptionIds(Long shopId, List<Long> productIds) {
//		DetachedCriteria criteria = DetachedCriteria.forClass(RoomTreatments.class);
//		criteria.createAlias("room", "r");
//		
//		if (1 < productOptionIds.size()) {
//			criteria.add(Restrictions.in("product.productOptions", productOptionIds));
//		} else {
//			criteria.add(Restrictions.eq("product.productOptions", productOptionIds.get(0)));
//		}
//		criteria.add(Restrictions.eq("product.id", "product.id"));
//		criteria.add(Restrictions.eq("isActive", true));
//		criteria.add(Restrictions.eq("r.shop.id", shopId));
//		criteria.add(Restrictions.eq("r.isActive", true));
//		criteria.addOrder(Order.asc("sort"));
//		List<RoomTreatments> roomList = list(criteria);
		DetachedCriteria criteria = DetachedCriteria.forClass(RoomTreatments.class);
		criteria.createAlias("room", "r");
		criteria.createAlias("product", "p");
		criteria.add(Restrictions.eq("isActive", true));
		criteria.add(Restrictions.eq("r.shop.id", shopId));
		criteria.add(Restrictions.eq("r.isActive", true));
		criteria.addOrder(Order.asc("r.sort"));
		
		if(Objects.nonNull(productIds) && 0 < productIds.size()) {
			if(Objects.equals(1, productIds.size())) {
				criteria.add(Restrictions.eq("p.id", productIds.get(0)));
			}else {
				criteria.add(Restrictions.in("p.id", productIds));
			}
			List<RoomTreatments> people = criteria.getExecutableCriteria(getSession()).list();
		    return people;
		}
		return new ArrayList<>();
	}
}
