package org.spa.serviceImpl.loyalty;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.service.loyalty.LoyaltyLevelService;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.loyalty.LoyaltyLevelAddVO;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/05/17.
 */
@Service
public class LoyaltyLevelServiceImpl extends BaseDaoHibernate<LoyaltyLevel> implements LoyaltyLevelService {

    @Override
    public void saveLoyaltyLevel(LoyaltyLevelAddVO loyaltyLevelAddVO) {
        Date now = new Date();
        String loginUser = WebThreadLocal.getUser().getUsername();
        Long id = loyaltyLevelAddVO.getId();
        LoyaltyLevel ll = null;
        if (id != null && id.longValue() > 0) {
            ll = get(id);
            ll.setLastUpdated(now);
            ll.setLastUpdatedBy(loginUser);
            ll.setIsActive(loyaltyLevelAddVO.getIsActive());
        } else {
            ll = new LoyaltyLevel();
            ll.setCreated(now);
            ll.setCreatedBy(loginUser);
            ll.setIsActive(true);
        }
        ll.setName(loyaltyLevelAddVO.getName());
        ll.setReference(loyaltyLevelAddVO.getReference());
        ll.setRank(loyaltyLevelAddVO.getRank());
        ll.setMonthLimit(loyaltyLevelAddVO.getMonthLimit());
        ll.setDiscountMonthLimit(loyaltyLevelAddVO.getDiscountMonthLimit());
        ll.setRequiredSpaPoints(loyaltyLevelAddVO.getRequiredSpaPoints());
        ll.setDiscountRequiredSpaPoints(loyaltyLevelAddVO.getDiscountRequiredSpaPoints());
        ll.setRemarks(loyaltyLevelAddVO.getRemarks());
        saveOrUpdate(ll);
    }

    @Override
    public LoyaltyLevel getActiveLoyaltyLevelByRank(Integer rank) {
        DetachedCriteria dc = DetachedCriteria.forClass(LoyaltyLevel.class);
        dc.add(Restrictions.eq("isActive", true));
        dc.add(Restrictions.eq("rank", rank));

        List<LoyaltyLevel> llList = list(dc);
        if (llList != null && llList.size() > 0) {
            return llList.get(0);
        }
        return null;
    }

    @Override
    public LoyaltyLevel getNextLoyaltyLevel(LoyaltyLevel currentLL) {
        LoyaltyLevel nextLL = getActiveLoyaltyLevelByRank(currentLL.getRank() + 1);
        if (nextLL == null) {
            nextLL = getHighestLoyaltyLevel();
        }
        return nextLL;
    }

    @Override
    public LoyaltyLevel getPrevLoyaltyLevel(LoyaltyLevel currentLL) {
        LoyaltyLevel prevLL = getActiveLoyaltyLevelByRank(currentLL.getRank() - 1);
        if (prevLL == null) {
            prevLL = getActiveLoyaltyLevelByRank(1);
        }
        return prevLL;
    }

    @Override
    public LoyaltyLevel getHighestLoyaltyLevel() {
        DetachedCriteria dc = DetachedCriteria.forClass(LoyaltyLevel.class);
        dc.add(Restrictions.eq("isActive", true));
        dc.addOrder(Order.desc("rank"));
        List<LoyaltyLevel> llList = list(dc);
        if (llList != null && llList.size() > 0) {
            return llList.get(0);
        }
        return null;
    }
}
