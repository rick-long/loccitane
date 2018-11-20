package com.spa.controller.shop;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.spa.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.spa.model.shop.*;
import org.spa.model.staff.StaffHomeShopDetails;
import org.spa.utils.I18nUtil;
import org.spa.utils.NumberUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.page.Page;
import org.spa.vo.shop.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/01/16.
 */
@Controller
@RequestMapping("shop")
public class ShopController extends BaseController {

    @RequestMapping("toView")
    public String shopManagement(Model model, ShopListVO shopListVO) {
        return "shop/shopManagement";
    }

    @RequestMapping("list")
    public String shopList(Model model, ShopListVO shopListVO) {

        String name = shopListVO.getName();
        String isActive = shopListVO.getIsActive();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Shop.class);
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
        }
        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }

        if (WebThreadLocal.getCompany() != null) {
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        //page start
        Page<Shop> shopPage = shopService.list(detachedCriteria, shopListVO.getPageNumber(), shopListVO.getPageSize());
        model.addAttribute("page", shopPage);

        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(shopListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "shop/shopList";
    }

    @RequestMapping("toAdd")
    public String toAddShop(Model model, ShopAddVO shopAddVO) {

        List<String> weeks = new ArrayList<String>();
        weeks.add("sunday");
        weeks.add("monday");
        weeks.add("tuesday");
        weeks.add("wednesday");
        weeks.add("thursday");
        weeks.add("friday");
        weeks.add("saturday");
        model.addAttribute("weeks", weeks);

        DetachedCriteria dc = DetachedCriteria.forClass(OutSourceTemplate.class);
        List<OutSourceTemplate> outSourceTemplates = outSourceTemplateService.getActiveListByRefAndCompany(dc, null, WebThreadLocal.getCompany().getId());
        model.addAttribute("outSourceTemplates", outSourceTemplates);

        return "shop/shopAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm addShop(@Valid ShopAddVO shopAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {

            String reference = shopAddVO.getReference();
            AjaxForm errorAjaxForm = AjaxFormHelper.error();

            DetachedCriteria dc = DetachedCriteria.forClass(Shop.class);
            List<Shop> list = shopService.getActiveListByRefAndCompany(dc, reference, WebThreadLocal.getCompany().getId());
            if (list != null && list.size() > 0) {
                errorAjaxForm.addErrorFields(new ErrorField("reference", I18nUtil.getMessageKey("label.errors.duplicate")));
            }

            if (StringUtils.isNotBlank(shopAddVO.getBusinessPhone()) && !NumberUtil.isNumeric(shopAddVO.getBusinessPhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("businessPhone", I18nUtil.getMessageKey("label.errors.home.phone.should.be.number")));
            }

            validateOpenCloseTime(errorAjaxForm, shopAddVO.getOpeningHoursList());

            if (!errorAjaxForm.getErrorFields().isEmpty() || StringUtils.isNotBlank(errorAjaxForm.getAlertErrorMsg())) {
                return errorAjaxForm;
            } else {
                shopService.saveShop(shopAddVO);
                return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
            }
        }

    }

    /**
     * 校验opening hour format
     *
     * @param errorAjaxForm
     * @param openingHoursVOList
     */
    private void validateOpenCloseTime(AjaxForm errorAjaxForm, List<OpeningHoursVO> openingHoursVOList) {
        if (!openingHoursVOList.isEmpty()) {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
            DateTime openTime;
            DateTime closeTime;
            for (OpeningHoursVO hoursVO : openingHoursVOList) {
                if (StringUtils.isNotBlank(hoursVO.getOpenTime())) {
                    try {
                        openTime = dtf.parseDateTime(hoursVO.getOpenTime().trim());
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        errorAjaxForm.addAlertError("Invalid format:" + hoursVO.getOpenTime());
                        break;
                    }
                } else {
                    openTime = dtf.parseDateTime(CommonConstant.DEFAULT_OPEN_TIME);
                }

                if (StringUtils.isNotBlank(hoursVO.getCloseTime())) {
                    try {
                        closeTime = dtf.parseDateTime(hoursVO.getCloseTime().trim());
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        errorAjaxForm.addAlertError("Invalid format:" + hoursVO.getCloseTime());
                        break;
                    }
                } else {
                    closeTime = dtf.parseDateTime(CommonConstant.DEFAULT_CLOSE_TIME);
                }

                // 开门时间比关门时间大
                if (!openTime.isBefore(closeTime)) {
                    errorAjaxForm.addAlertError("Open time[" + hoursVO.getOpenTime() + "] must before close time[" + hoursVO.getCloseTime() + "]!");
                    break;
                }
            }
        }
    }

    @RequestMapping("toEdit")
    public String toEditShop(ShopEditVO shopEditVO, Model model) {
        Shop shop = shopService.get(shopEditVO.getId());
        shopEditVO.setName(shop.getName());
        shopEditVO.setIsActive(String.valueOf(shop.isIsActive()));
        shopEditVO.setIsOnline(String.valueOf(shop.isIsOnline()));
        shopEditVO.setRemarks(shop.getRemarks());
        shopEditVO.setEmail(shop.getEmail());
        shopEditVO.setPrefix(shop.getPrefix());
        shopEditVO.setShowOnlineBooking(String.valueOf(shop.isShowOnlineBooking()));

        if (shop.getAddresses() != null && shop.getAddresses().size() > 0) {
            Address address = shop.getAddresses().iterator().next();
            shopEditVO.setAddress(address.getAddressExtention());
        }

        if (shop.getPhones() != null && shop.getPhones().size() > 0) {
            Phone bPhone = shop.getPhones().iterator().next();
            shopEditVO.setBusinessPhone(bPhone.getNumber());
        }
        if (shop.getOutSourceTemplate() != null) {
            shopEditVO.setOutSourceTemplateId(shop.getOutSourceTemplate().getId());
        }
        List<String> weeks = new ArrayList<String>();
        weeks.add("sunday");
        weeks.add("monday");
        weeks.add("tuesday");
        weeks.add("wednesday");
        weeks.add("thursday");
        weeks.add("friday");
        weeks.add("saturday");
        model.addAttribute("weeks", weeks);

        DetachedCriteria dc = DetachedCriteria.forClass(OutSourceTemplate.class);
        List<OutSourceTemplate> outSourceTemplates = outSourceTemplateService.getActiveListByRefAndCompany(dc, null, WebThreadLocal.getCompany().getId());
        model.addAttribute("outSourceTemplates", outSourceTemplates);

        Map<String, OpeningHoursVO> kvMap = new HashMap<>();
        if (shop.getOpeningHourses() != null && shop.getOpeningHourses().size() > 0) {

            Iterator<OpeningHours> sa_it = shop.getOpeningHourses().iterator();
            while (sa_it.hasNext()) {
                OpeningHours openingHours = sa_it.next();
                OpeningHoursVO ohvo = new OpeningHoursVO();
                if (openingHours.getCloseTime() != null) {
                    ohvo.setCloseTime(openingHours.getCloseTime());
                }
                if (openingHours.getOpenTime() != null) {
                    ohvo.setOpenTime(openingHours.getOpenTime());
                }
                ohvo.setDayOfWeek(openingHours.getDayOfWeek());
                ohvo.setId(openingHours.getId());
                ohvo.setIsOnlineBooking(String.valueOf(openingHours.isOnlineBooking()));
                kvMap.put(openingHours.getDayOfWeek(), ohvo);
            }
        }
        model.addAttribute("kvMap", kvMap);

        return "shop/shopEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm editSupplier(@Valid ShopEditVO shopEditVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {

            AjaxForm errorAjaxForm = AjaxFormHelper.error();

            if (StringUtils.isNotBlank(shopEditVO.getBusinessPhone()) && !NumberUtil.isNumeric(shopEditVO.getBusinessPhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("businessPhone", I18nUtil.getMessageKey("label.errors.home.phone.should.be.number")));
            }

            validateOpenCloseTime(errorAjaxForm, shopEditVO.getOpeningHoursList());

            if (!errorAjaxForm.getErrorFields().isEmpty() || StringUtils.isNotBlank(errorAjaxForm.getAlertErrorMsg())) {
                return errorAjaxForm;

            } else {
                shopService.updateShop(shopEditVO);

                return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
            }
        }
    }

    @RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
        Shop shop = shopService.get(id);
        if (shop != null) {
            try {
                shop.setIsActive(false);
                shopService.saveOrUpdate(shop);
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
            }
        } else {
            return AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }

    @RequestMapping("toSort")
    public String toSort(Long id, Model model) {
        List<StaffHomeShopDetails> staffHomeShop = staffHomeShopDetailsService.getStaffHomeShopDetailsList(id);
        model.addAttribute("shopId", id);
        model.addAttribute("staffHomeShop", staffHomeShop);
        return "shop/shopStaffSort";
    }

    @RequestMapping("toSortRoom")
    public String toSortRoom(Long id, Model model) {
        List<Room> roomList = roomService.getShopAvailableRoomList(id);
        model.addAttribute("shopId", id);
        model.addAttribute("roomList", roomList);
        return "shop/shopRoomSort";
    }

    @RequestMapping("sort")
    @ResponseBody
    public AjaxForm sort(ShopStaffSortVO shopStaffSortVO) {
        try {
            for (StaffSortVO staffSortVO : shopStaffSortVO.getStaffSortVOList()) {
                StaffHomeShopDetails staffHomeShopDetails = staffHomeShopDetailsService.get(staffSortVO.getId());
                staffHomeShopDetails.setSort(staffSortVO.getSort());
                staffHomeShopDetailsService.saveOrUpdate(staffHomeShopDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());

        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.sort.successfully"));
    }

    @RequestMapping("sortRoom")
    @ResponseBody
    public AjaxForm sortRoom(ShopRoomSortVO shopRoomSortVO) {
        try {
            for (RoomSortVO roomSortVO : shopRoomSortVO.getRoomSortVOList()) {
                Room room = roomService.get(roomSortVO.getId());
                room.setSort(roomSortVO.getSort());
                roomService.saveOrUpdate(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());

        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.sort.successfully"));
    }

}
