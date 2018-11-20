package com.spa.controller.app;

import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.spa.model.shop.OpeningHours;
import org.spa.model.shop.Shop;
import org.spa.utils.OpeningTimeUtil;
import org.spa.utils.Results;
import org.spa.vo.app.callback.OpeningHoursCallBackVO;
import org.spa.vo.app.callback.OpeningHoursVO;
import org.spa.vo.app.callback.ShopCallBackVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("apps/shop")
public class AppsShopController extends BaseController {
    @RequestMapping("list")
    @ResponseBody
    public Results list(){
        Results results = Results.getInstance();
        List<Shop> shopList = shopService.getListByCompany(null, false, false, true,true);
        List <ShopCallBackVO>shopCallBackVOS=new ArrayList<>();
        if(shopList!=null&&shopList.size()>0){
           for(Shop shop:shopList){
               ShopCallBackVO shopCallBackVO=new ShopCallBackVO(shop);
               shopCallBackVOS.add(shopCallBackVO);
           }
       }
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", shopCallBackVOS);

    }

 /*   @RequestMapping("openingHours")
    @ResponseBody
    public Results openingHours(@RequestBody OpeningHoursVO openingHoursVO){
        Results results = Results.getInstance();
        if(StringUtils.isBlank(openingHoursVO.getDatetime())){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "datetime  required");

        }
        if(openingHoursVO.getShopId()==null){
            return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "shopId  required");
        }
        SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1=new Date();
          Shop shop=shopService.get(openingHoursVO.getShopId());
        try {
            date1 = sdf.parse(openingHoursVO.getDatetime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        OpeningHours openingHours = shop.getOpeningHour(date1);

        OpeningHoursCallBackVO openingHoursCallBackVO=new OpeningHoursCallBackVO(openingHours);

        DateTime openTime = openingHours.getOpenTimeObj();
        DateTime closeTime = openingHours.getCloseTimeObj();

        Map<String, String> morningMap = new LinkedHashMap<>();
        Map<String, String> afternoonMap = new LinkedHashMap<>();
        Map<String, String> eveningMap = new LinkedHashMap<>();
        int timeUnit = 15;

        DateTime current = openTime;
        DateTime morningEnd = openTime.withTimeAtStartOfDay().plusHours(13);

        while (current.isBefore(morningEnd)) {
            if (current.isBefore(closeTime)) {
                morningMap.put(String.valueOf(current.getMillis()), current.toString("HH:mm"));
            }
            current = current.plusMinutes(timeUnit);
        }
        if (openTime.isBefore(morningEnd)){
            current = morningEnd;
        }
        DateTime afternoonEnd = morningEnd.plusHours(5);
        while (current.isBefore(afternoonEnd)) {
            if (current.isBefore(closeTime)) {
                afternoonMap.put(String.valueOf(current.getMillis()), current.toString("HH:mm"));
            }
            current = current.plusMinutes(timeUnit);
        }

        if (openTime.isBefore(afternoonEnd)){
            current = afternoonEnd;
        }
        while (current.isBefore(closeTime)) {
            if (current.isBefore(closeTime)) {
                eveningMap.put(String.valueOf(current.getMillis()), current.toString("HH:mm"));
            }
            current = current.plusMinutes(timeUnit);
        }
        openingHoursCallBackVO.setMorningMap(morningMap);
        openingHoursCallBackVO.setAfternoonMap(afternoonMap);
        openingHoursCallBackVO.setEveningMap(eveningMap);

        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", openingHoursCallBackVO);
    }*/
 @RequestMapping("openingHours")
 @ResponseBody
 public Results openingHours(@RequestBody OpeningHoursVO openingHoursVO){
     Results results = Results.getInstance();
     if(StringUtils.isBlank(openingHoursVO.getDatetime())){
         return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "datetime  required");

     }
     if(openingHoursVO.getShopId()==null){
         return results.setCode(Results.CODE_PARAMETER_ERROR).addMessage("errorMsg", "shopId  required");
     }
     SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
     Date date1=new Date();
     Shop shop=shopService.get(openingHoursVO.getShopId());
     try {
         date1 = sdf.parse(openingHoursVO.getDatetime());
     } catch (ParseException e) {
         e.printStackTrace();
     }

     OpeningHours openingHours = shop.getOpeningHour(date1);

     OpeningHoursCallBackVO openingHoursCallBackVO=new OpeningHoursCallBackVO(openingHours);

     DateTime openTime = openingHours.getOpenTimeObj();
     DateTime closeTime = openingHours.getCloseTimeObj();
     OpeningTimeUtil.getValidBookingTime(openingHoursCallBackVO, openTime, closeTime,new DateTime(new Date()), 2);

     return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", openingHoursCallBackVO);
 }


}

