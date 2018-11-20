package org.spa.serviceImpl.awardRedemption;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.awardRedemption.AwardRedemption;
import org.spa.model.awardRedemption.AwardRedemptionTransaction;
import org.spa.model.payment.PaymentMethod;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.service.awardRedemption.AwardRedemptionService;
import org.spa.service.awardRedemption.AwardRedemptionTransactionService;
import org.spa.service.inventory.InventoryTransactionService;
import org.spa.service.payment.PaymentMethodService;
import org.spa.service.points.LoyaltyPointsTransactionService;
import org.spa.service.prepaid.PrepaidService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.utils.DateUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.common.KeyAndValueVO;
import org.spa.vo.loyalty.AwardRedeemVO;
import org.spa.vo.prepaid.PrepaidAddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/05/23.
 */
@Service
public class AwardRedemptionTransactionServiceImpl extends BaseDaoHibernate<AwardRedemptionTransaction> implements AwardRedemptionTransactionService{

	@Autowired
	public AwardRedemptionService awardRedemptionService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public PrepaidService prepaidService;
	
	@Autowired
	public PaymentMethodService paymentMethodService;
	
	@Autowired
	public InventoryTransactionService inventoryTransactionService;
	
	@Autowired
	public ShopService shopService;
	@Autowired
	LoyaltyPointsTransactionService loyaltyPointsTransactionService;

	@Override
	public Long saveAwardRedemptionTransaction(AwardRedeemVO awardRedeemVO) {

		AwardRedemptionTransaction art=new AwardRedemptionTransaction();
		Prepaid prepaid=null;
		Date now=new Date();
		User member=userService.get(awardRedeemVO.getMemberId());
		Long awardRedemptionId=awardRedeemVO.getId();
		AwardRedemption ar=awardRedemptionService.get(awardRedemptionId);
		if(ar.getRequiredLp()<=member.getRemainLoyaltyPoints()&&member.getRemainLoyaltyPoints()>0 &&ar.getStartDate().getTime()<=new Date().getTime() &&new Date().getTime()<=ar.getEndDate().getTime()){
			art.setAwardRedemption(ar);
			art.setRedeemMember(member);
			art.setRedeemDate(now);
			art.setRedeemAmount(ar.getRequiredAmount());
			art.setRedeemLp(ar.getRequiredLp());
			art.setRedeemType(ar.getRedeemType());

			Shop onlineShop = shopService.get("reference", "ONLINE");

			if(ar.getRedeemType().equals(CommonConstant.REDEEM_TYPE_GOODS)){
				art.setRedeemGoods(ar.getProductOption());
			}else if(ar.getRedeemType().equals("VOUCHER_CASH") || ar.getRedeemType().equals("VOUCHER_TREATMENT")){
				//new a gift voucher
				PrepaidAddVO prepaidAddVO=new PrepaidAddVO();

				prepaidAddVO.setMember(member);
				prepaidAddVO.setIsAllCompanyUse(true);
				prepaidAddVO.setIsTransfer("true");

				if(ar.getProductOption() !=null){
					//redeem treatment vouchers
					System.out.println(ar.getProductOption().getProduct().getName());
					prepaidAddVO.setPo(ar.getProductOption());
					prepaidAddVO.setPrepaidType(CommonConstant.PREPAID_TYPE_TREATMENT_VOUCHER);
					prepaidAddVO.setPrepaidName(ar.getProductOption().getProduct().getName()+" Treatment Voucher");
				}else{
					prepaidAddVO.setPrepaidType(CommonConstant.PREPAID_TYPE_CASH_VOUCHER);
					prepaidAddVO.setPrepaidName("$ "+ar.getBeWorth()+" Cash Voucher");
				}

				prepaidAddVO.setPurchaseDate(now);
				try {
					prepaidAddVO.setExpiryDate(DateUtil.getLastDayAfterNumOfMonths(now, 12));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				prepaidAddVO.setPrepaidValue(ar.getBeWorth());

				List<KeyAndValueVO> paymentMethods=new ArrayList<KeyAndValueVO>();
				DetachedCriteria dc = DetachedCriteria.forClass(PaymentMethod.class);
				PaymentMethod pm=paymentMethodService.getActiveListByRefAndCompany(dc, CommonConstant.PAYMENT_METHOD_REF_NO_PAYMENT, WebThreadLocal.getCompany().getId()).get(0);
				KeyAndValueVO kv=new KeyAndValueVO();
				kv.setId(pm.getId());
				kv.setKey("1");
				kv.setValue(String.valueOf(ar.getBeWorth()));
				paymentMethods.add(kv);
				prepaidAddVO.setPaymentMethods(paymentMethods);
				prepaidAddVO.setTherapists(null);

				prepaidAddVO.setCommissionRate(0d);
				prepaidAddVO.setShopId(onlineShop.getId());
				String remarks="";
				if(ar.getRequiredLp() >0){
					remarks="redeem "+ar.getRequiredLp() +" loyalty points;";
				}
				/*if(ar.getRequiredAmount() >0){
					remarks=remarks+"redeem "+ar.getRequiredAmount()+" money;";
				}*/
				prepaidAddVO.setRemarks(remarks);
				prepaidAddVO.setIsRedeem(true);
				loyaltyPointsTransactionService.resetLoyaltyPointsTransaction(ar.getRequiredLp(),awardRedeemVO.getMemberId(),new Date(),true);
				prepaid=prepaidService.returnPrepaid(prepaidAddVO);
				//send email
			}
			art.setCreated(now);
			art.setCreatedBy(WebThreadLocal.getUser().getUsername());
			art.setIsActive(true);
			art.setRedeemPrepaid(prepaid);
			if(ar.getRedeemType().equals("VOUCHER_TREATMENT")){
				art.setRedeemGoods(ar.getProductOption());
			}
			saveOrUpdate(art);
		}

/*
		if(ar.getRedeemType().equals(CommonConstant.REDEEM_TYPE_GOODS)){
			ProductOption po = art.getRedeemGoods();
			InventoryTransactionVO transactionVO=new InventoryTransactionVO();
			transactionVO.setCompany(WebThreadLocal.getCompany());
			transactionVO.setProductOption(po);
			transactionVO.setProductOptionId(po.getId());
			transactionVO.setQty(1);
			transactionVO.setItem(null);
			transactionVO.setShop(onlineShop);
			transactionVO.setShopId(onlineShop.getId());
			transactionVO.setTransactionType(CommonConstant.INVENTORY_TRANSACTION_TYPE_REDEEMED);
			transactionVO.setDirection(CommonConstant.INVENTORY_TRANSACTION_DIRECTION_OUT);
			transactionVO.setEntryDate(now);
			inventoryTransactionService.save(transactionVO);
		}*/
    return prepaid.getId();
	}


}
