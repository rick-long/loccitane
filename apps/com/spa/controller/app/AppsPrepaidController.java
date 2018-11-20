package com.spa.controller.app;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.spa.model.prepaid.Prepaid;
import org.spa.model.prepaid.PrepaidTopUpTransaction;
import org.spa.model.shop.Shop;
import org.spa.utils.I18nUtil;
import org.spa.utils.Results;
import org.spa.vo.app.callback.PrepaidTopUpTransactionCallBackVO;
import org.spa.vo.app.callback.ShopCallBackVO;
import org.spa.vo.prepaid.PrepaidListVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("apps/prepaid")
public class AppsPrepaidController extends BaseController {
    @RequestMapping("voucherValueList")
    @ResponseBody
    public Results voucherValueList() {
        Results results = Results.getInstance();
        Map<String ,Object> voucherValueList=new HashMap<>();
        String currencyPrefix = I18nUtil.getMessageKey("label.money.currency");
        voucherValueList.put(currencyPrefix+"250",250);
        voucherValueList.put(currencyPrefix+"500",500);
        voucherValueList.put(currencyPrefix+"750",750);
        voucherValueList.put(currencyPrefix+"1000",1000);
        voucherValueList.put(currencyPrefix+"1250",1250);
        voucherValueList.put(currencyPrefix+"2000",2000);
        voucherValueList.put(currencyPrefix+"2500",2500);
        voucherValueList.put(currencyPrefix+"3000",3000);
        voucherValueList.put(currencyPrefix+"3500",3500);
        voucherValueList.put(currencyPrefix+"4000",4000);
        voucherValueList.put(currencyPrefix+"5000",5000);
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", voucherValueList);


    }
    @RequestMapping("package")
    public Results PackageList(@RequestBody PrepaidListVO prepaidListVO) {
        Long shopId;
        String [] prepaidTypes={CommonConstant.PREPAID_TYPE_CASH_PACKAGE,CommonConstant.PREPAID_TYPE_TREATMENT_PACKAGE};
        Results results = Results.getInstance();
        List<Shop> shopList = shopService.getListByCompany(null, false, false, true);
        List <ShopCallBackVO>shopCallBackVOS=new ArrayList<>();
        if(shopList!=null&&shopList.size()>0){
            for(Shop shop:shopList){
                ShopCallBackVO shopCallBackVO=new ShopCallBackVO(shop);
                shopCallBackVOS.add(shopCallBackVO);
            }
        }
         shopId=shopList.get(0).getId();
        if(prepaidListVO.getShopId()!=null){
            shopId=prepaidListVO.getShopId();
        }
        List <PrepaidTopUpTransactionCallBackVO> PrepaidTopUpTransactionCallBackVOList=new ArrayList<>();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Prepaid.class);
        //prepaid type
    /*    if (StringUtils.isBlank(prepaidListVO.getPrepaidType())) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Prepaid Type  required");
        }*/
        if (prepaidListVO.getMemberId()==null) {
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "Member Id  required");
        }
       /* detachedCriteria.add(Restrictions.eq("prepaidType", prepaidListVO.getPrepaidType()));*/
        detachedCriteria.add(Restrictions.in("prepaidType",prepaidTypes));

        //member
            detachedCriteria.add(Restrictions.eq("user.id", prepaidListVO.getMemberId()));
        //shop
          if(shopId!=null){
              detachedCriteria.add(Restrictions.eq("shop.id",shopId));
          }
        List<Prepaid>prepaidList=prepaidService.list(detachedCriteria);
        if(prepaidList!=null&&prepaidList.size()>0){

            for(Prepaid prepaid:prepaidList){
                if(prepaid.getPrepaidTopUpTransactions()!=null&&prepaid.getPrepaidTopUpTransactions().size()>0){
                    for(PrepaidTopUpTransaction prepaidTopUpTransaction:prepaid.getPrepaidTopUpTransactions()){
                        PrepaidTopUpTransactionCallBackVO prepaidTopUpTransactionCallBackVO=new PrepaidTopUpTransactionCallBackVO(prepaidTopUpTransaction);
                        PrepaidTopUpTransactionCallBackVOList.add(prepaidTopUpTransactionCallBackVO);
                    }
                }

            }
        }
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", PrepaidTopUpTransactionCallBackVOList);
    }

}
