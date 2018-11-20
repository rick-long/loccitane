package org.spa.serviceImpl.shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.book.BookItem;
import org.spa.model.company.Company;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Room;
import org.spa.model.shop.RoomTreatments;
import org.spa.model.shop.Shop;
import org.spa.service.book.BlockService;
import org.spa.service.book.BookItemService;
import org.spa.service.product.CategoryService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.product.ProductService;
import org.spa.service.shop.RoomService;
import org.spa.service.shop.ShopService;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.shop.RoomAddVO;
import org.spa.vo.shop.RoomEditVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/01/16.
 */
@Service
public class RoomServiceImpl extends BaseDaoHibernate<Room> implements RoomService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private BookItemService bookItemService;

    @Autowired
    private BlockService blockService;

    @Override
    public void save(RoomAddVO roomAddVO) {
        Date now = new Date();
        String currentUserName = WebThreadLocal.getUser().getUsername();
        Room room = new Room();
        room.setIsActive(true);
        room.setCreated(now);
        room.setCreatedBy(currentUserName);
        room.setLastUpdated(now);
        room.setLastUpdatedBy(currentUserName);
        room.setName(roomAddVO.getName());
        room.setReference(RandomUtil.generateRandomNumberWithDate(CommonConstant.ROOM_REF_PREFIX));
        room.setRemarks(roomAddVO.getRemarks());
        Company company = WebThreadLocal.getCompany();
        room.setCompany(company);
        room.setCapacity(roomAddVO.getCapacity());
        room.setShop(shopService.get(roomAddVO.getShopId()));
        setRoomCategory(roomAddVO.getCategoryIds(), room);
        setRoomProduct(roomAddVO.getProductIds(), room);
        save(room);
    }

    public void setRoomCategory(Long[] categoryIds, Room room) {
        if(categoryIds == null || categoryIds.length == 0) {
            return;
        }
        Set<RoomTreatments> roomTreatmentsSet = room.getRoomTreatmentses();
        for (Long categoryID : categoryIds) {
            Category category = categoryService.get(categoryID);
            RoomTreatments roomTreatments = new RoomTreatments();
            roomTreatments.setCategory(category);
            roomTreatments.setRoom(room);
            roomTreatments.setIsActive(true);
            roomTreatments.setCreated(room.getLastUpdated());
            roomTreatments.setCreatedBy(room.getLastUpdatedBy());
            roomTreatments.setLastUpdated(room.getLastUpdated());
            roomTreatments.setLastUpdatedBy(room.getLastUpdatedBy());
            roomTreatmentsSet.add(roomTreatments);
            if(category.getCategories().size() > 0) {
                saveTreatment(category.getCategories(), room, roomTreatmentsSet);
            } else if(category.getProducts().size() > 0) {
                setRoomProduct(category.getProducts().stream().map(Product::getId).toArray(Long[]::new), room);
            }
        }
    }

    public void saveTreatment(Set<Category> categories, Room room, Set<RoomTreatments> roomTreatmentsSet) {
        for (Category category : categories) {
            RoomTreatments roomTreatments = new RoomTreatments();
            roomTreatments.setCategory(category);
            roomTreatments.setRoom(room);
            roomTreatments.setIsActive(true);
            roomTreatments.setCreated(room.getLastUpdated());
            roomTreatments.setCreatedBy(room.getLastUpdatedBy());
            roomTreatments.setLastUpdated(room.getLastUpdated());
            roomTreatments.setLastUpdatedBy(room.getLastUpdatedBy());
            roomTreatmentsSet.add(roomTreatments);
            if (category.getCategories().size() > 0) {
                saveTreatment(category.getCategories(), room, roomTreatmentsSet);
            } else if(category.getProducts().size() > 0) {
                setRoomProduct(category.getProducts().stream().map(Product::getId).toArray(Long[]::new), room);
            }
        }
    }

    public void setRoomProduct(Long[] productId, Room room) {
        if (productId == null || productId.length == 0) {
            return;
        }
        Set<RoomTreatments> roomTreatmentsSet = room.getRoomTreatmentses();
        for (Long productID : productId) {
            Product product = productService.get(productID);
            RoomTreatments roomTreatments = new RoomTreatments();
            roomTreatments.setProduct(product);
            roomTreatments.setRoom(room);
            roomTreatments.setIsActive(true);
            roomTreatments.setCreated(room.getLastUpdated());
            roomTreatments.setCreatedBy(room.getLastUpdatedBy());
            roomTreatments.setLastUpdated(room.getLastUpdated());
            roomTreatments.setLastUpdatedBy(room.getLastUpdatedBy());
            roomTreatmentsSet.add(roomTreatments);
        }
    }

    @Override
    public void update(RoomEditVO roomEditVO) {
        Room room = get(roomEditVO.getId());
        if(room == null) {
            return;
        }

        Date now = new Date();
        String currentUserName = WebThreadLocal.getUser().getUsername();
//        room.setIsActive(Boolean.parseBoolean(roomEditVO.getIsActive()));
//        room.setCreated(now);
//        room.setCreatedBy(currentUserName);
        room.setLastUpdated(now);
        room.setLastUpdatedBy(currentUserName);
        room.setName(roomEditVO.getName());
        room.setRemarks(roomEditVO.getRemarks());
        room.setCapacity(roomEditVO.getCapacity());
        Set<RoomTreatments> roomTreatmentsSet = room.getRoomTreatmentses();
        roomTreatmentsSet.clear();
        saveOrUpdate(room);
        getSession().flush();
        setRoomCategory(roomEditVO.getCategoryIds(), room);
        setRoomProduct(roomEditVO.getProductIds(), room);
        saveOrUpdate(room);
    }

    public List<Room> getRoomByShop(Long shopId, DetachedCriteria criteria) {
        if (criteria == null) {
            criteria = DetachedCriteria.forClass(Room.class);
        }
        criteria.add(Restrictions.eq("shop.id", shopId));
        criteria.add(Restrictions.eq("isActive", true));
        criteria.addOrder(Order.asc("sort"));
        return list(criteria);
    }


    public List<Room> getAvailableRoomList(Long productOptionId, Long shopId) {
        ProductOption productOption =  productOptionService.get(productOptionId);
        if(productOption == null) {
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
    public List<Room> getNotBlockRoomList(Long productOptionId, Long shopId, Date startTime, Date endTime) {
        return getNotBlockRoomList(productOptionId, shopId, startTime, endTime, new HashSet<>());
    }
    
    /**
     * 获取可用的room集合
     *
     * @param productOptionId
     * @param shopId
     * @param startTime
     * @param endTime
     * @param excludeBookItem
     * @return
     */
    @Override
    public List<Room> getNotBlockRoomList(Long productOptionId, Long shopId, Date startTime, Date endTime, BookItem excludeBookItem) {
        return getNotBlockRoomList(productOptionId,shopId,startTime,endTime, Sets.newHashSet(excludeBookItem));
    }

    @Override
    public List<Room> getNotBlockRoomList(Long productOptionId, Long shopId, Date startTime, Date endTime, Set<BookItem> excludeBookItems) {
        List<Room> allRoomList = getAvailableRoomList(productOptionId, shopId);
        Shop shop = shopService.get(shopId);
        Iterator<Room> roomIterator = allRoomList.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            // 删除已经使用的room
            if (bookItemService.checkBlock(room, startTime, endTime, excludeBookItems)) {
                roomIterator.remove(); // 删除已经使用的room
            } else if (blockService.checkBlock(shop, room, startTime, endTime)) {
                roomIterator.remove(); // 删除已经使用的room
            }
        }
        return allRoomList;
    }

    @Override
    public List<Room> getShopAvailableRoomList(Long shopId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Room.class);
        criteria.add(Restrictions.eq("isActive",true));
        criteria.add(Restrictions.eq("shop.id", shopId));
        criteria.addOrder(Order.asc("sort"));
        List<Room> roomList = list(criteria);

        return roomList;
    }
}
