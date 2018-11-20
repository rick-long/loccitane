package org.spa.service.shop;

import java.util.List;

import org.spa.dao.base.BaseDao;
import org.spa.model.shop.RoomTreatments;

/**
 * Created by ken on 2018/10/26.
 */
public interface RoomTreatmentService extends BaseDao<RoomTreatments>{
    List<RoomTreatments> getRoomByShopAndProductOptionIds(Long shopId,List<Long> productOptionIds);
}
