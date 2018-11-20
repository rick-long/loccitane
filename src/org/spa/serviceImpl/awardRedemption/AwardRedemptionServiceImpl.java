package org.spa.serviceImpl.awardRedemption;

import java.util.Date;
import java.util.Set;

import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.awardRedemption.AwardRedemption;
import org.spa.model.shop.Shop;
import org.spa.service.awardRedemption.AwardRedemptionService;
import org.spa.service.product.ProductOptionService;
import org.spa.service.shop.ShopService;
import org.spa.utils.CollectionUtils;
import org.spa.utils.DateUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.loyalty.AwardRedemptionAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ivy on 2016/05/23.
 */
@Service
public class AwardRedemptionServiceImpl extends BaseDaoHibernate<AwardRedemption> implements AwardRedemptionService{

	@Autowired
	public ProductOptionService productOptionService;
	
	@Autowired
	public ShopService shopService;

	@Override
	public void saveAwardRedemption(AwardRedemptionAddVO awardRedemptionAddVO) {
		Date now=new Date();
		String loginUser=WebThreadLocal.getUser().getUsername();
		
		AwardRedemption ar=null;
		if(awardRedemptionAddVO.getId() !=null && awardRedemptionAddVO.getId().longValue()>0){
			ar=get(awardRedemptionAddVO.getId());
			ar.setLastUpdated(now);
			ar.setLastUpdatedBy(loginUser);
			ar.setIsActive(awardRedemptionAddVO.getIsActive());
		}else{
			ar=new AwardRedemption();
			ar.setCreated(now);
			ar.setCreatedBy(loginUser);
			ar.setIsActive(true);
		}
		ar.setBeWorth(awardRedemptionAddVO.getBeWorth());
		ar.setName(awardRedemptionAddVO.getName());
		ar.setDescription(awardRedemptionAddVO.getDescription());
		ar.setRedeemType(awardRedemptionAddVO.getRedeemType());
		ar.setRedeemChannel(awardRedemptionAddVO.getRedeemChannel());
		ar.setRequiredAmount(awardRedemptionAddVO.getRequiredAmount());
		ar.setRequiredLp(awardRedemptionAddVO.getRequiredLp());
		
		if(awardRedemptionAddVO.getEndDate() !=null){
			ar.setEndDate(DateUtil.getLastMinuts(DateUtil.stringToDate(awardRedemptionAddVO.getEndDate(), "yyyy-MM-dd")));
		}
		ar.setStartDate(DateUtil.stringToDate(awardRedemptionAddVO.getStartDate(), "yyyy-MM-dd"));
		if(awardRedemptionAddVO.getProductOptionId() !=null){
			ar.setProductOption(productOptionService.get(awardRedemptionAddVO.getProductOptionId()));
		}
		ar.setValidAt(awardRedemptionAddVO.getValidAt());

		/*Set<Shop> shops=CollectionUtils.getLightWeightSet();
		if(awardRedemptionAddVO.getShopIds() !=null && awardRedemptionAddVO.getShopIds().size()>0){
			for(Long shopId : awardRedemptionAddVO.getShopIds()){
				shops.add(shopService.get(shopId));
			}
		}
		ar.setShops(shops);*/
		
		saveOrUpdate(ar);
	}
	//
}
