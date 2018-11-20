package com.spa.controller.loyalty;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.model.user.User;
import org.spa.utils.CollectionUtils;
import org.spa.utils.I18nUtil;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.common.SelectOptionVO;
import org.spa.vo.loyalty.LoyaltyLevelAddVO;
import org.spa.vo.loyalty.LoyaltyLevelListVO;
import org.spa.vo.loyalty.UserLoyaltyLevelVO;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/05/17.
 */
@Controller
@RequestMapping("loyalty")
public class LoyaltyController extends BaseController {


    @RequestMapping("toView")
    public String levelManagement(Model model, LoyaltyLevelListVO loyaltyLevelListVO) {

        return "loyalty/loyaltyLevelManagement";
    }


    @RequestMapping("list")
    public String listLevel(Model model, LoyaltyLevelListVO loyaltyLevelListVO) {
        String name = loyaltyLevelListVO.getName();
        Boolean isActive = loyaltyLevelListVO.getIsActive();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LoyaltyLevel.class);


        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (isActive != null) {
            detachedCriteria.add(Restrictions.eq("isActive", isActive));
        }
        detachedCriteria.addOrder(Order.desc("rank"));

        Page<LoyaltyLevel> loyaltyLevelPage = loyaltyLevelService.list(detachedCriteria, loyaltyLevelListVO.getPageNumber(), loyaltyLevelListVO.getPageSize());
        model.addAttribute("page", loyaltyLevelPage);

        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(loyaltyLevelListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end

        return "loyalty/loyaltyLevelList";
    }

    @RequestMapping("toAdd")
    public String toAddLoyaltyLevel(LoyaltyLevelAddVO loyaltyLevelAddVO) {
        return "loyalty/loyaltyLevelAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm addLoyaltyLevel(@Valid LoyaltyLevelAddVO loyaltyLevelAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            //
            AjaxForm errorAjaxForm = AjaxFormHelper.error();

            DetachedCriteria dc = DetachedCriteria.forClass(LoyaltyLevel.class);
            List<LoyaltyLevel> list = loyaltyLevelService.getActiveListByRefAndCompany(dc, loyaltyLevelAddVO.getReference(), null);
            if (list != null && list.size() > 0) {
                errorAjaxForm.addErrorFields(new ErrorField("reference", I18nUtil.getMessageKey("label.errors.duplicate")));
                return errorAjaxForm;
            }
            loyaltyLevelService.saveLoyaltyLevel(loyaltyLevelAddVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    @RequestMapping("toEdit")
    public String toEditLoyaltyLevel(LoyaltyLevelAddVO loyaltyLevelAddVO) {
        Long id = loyaltyLevelAddVO.getId();
        LoyaltyLevel ll = loyaltyLevelService.get(id);
        if (ll != null) {
            loyaltyLevelAddVO.setMonthLimit(ll.getMonthLimit().intValue());
            loyaltyLevelAddVO.setName(ll.getName());
            loyaltyLevelAddVO.setRank(ll.getRank());
//			System.out.println("toEditLoyaltyLevel ---refrence ---"+ll.getReference());
            loyaltyLevelAddVO.setReference(ll.getReference());
            loyaltyLevelAddVO.setRequiredSpaPoints(ll.getRequiredSpaPoints());
            loyaltyLevelAddVO.setDiscountMonthLimit(ll.getDiscountMonthLimit());
            loyaltyLevelAddVO.setDiscountRequiredSpaPoints(ll.getDiscountRequiredSpaPoints());
            loyaltyLevelAddVO.setIsActive(ll.isIsActive());
            loyaltyLevelAddVO.setRemarks(ll.getRemarks());
        }
        return "loyalty/loyaltyLevelEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm editLoyaltyLevel(@Valid LoyaltyLevelAddVO loyaltyLevelAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            //
            AjaxForm errorAjaxForm = AjaxFormHelper.error();
            LoyaltyLevel currLL = loyaltyLevelService.get(loyaltyLevelAddVO.getId());
            if (!currLL.getReference().equals(loyaltyLevelAddVO.getReference())) {
                //
                DetachedCriteria dc = DetachedCriteria.forClass(LoyaltyLevel.class);
                List<LoyaltyLevel> list = loyaltyLevelService.getActiveListByRefAndCompany(dc, loyaltyLevelAddVO.getReference(), null);
                if (list != null && list.size() > 0) {
                    errorAjaxForm.addErrorFields(new ErrorField("reference", I18nUtil.getMessageKey("label.errors.duplicate")));
                    return errorAjaxForm;
                }
            }
            loyaltyLevelService.saveLoyaltyLevel(loyaltyLevelAddVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    @RequestMapping("toAdjustLoyaltyLevel")
    public String toAdjustLoyaltyLevel(UserLoyaltyLevelVO userLoyaltyLevelVO, Model model) {

        User user = userService.get(userLoyaltyLevelVO.getMemberId());
        DetachedCriteria dc = DetachedCriteria.forClass(LoyaltyLevel.class);
        List<LoyaltyLevel> llList = loyaltyLevelService.getActiveListByRefAndCompany(dc, null, null);
        CollectionUtils.sort(llList, "rank", true);

        model.addAttribute("user", user);
        model.addAttribute("llList", llList);
        return "loyalty/loyaltyLevelAdjust";
    }

    @RequestMapping("adjustLoyaltyLevel")
    @ResponseBody
    public AjaxForm adjustLoyaltyLevel(@Valid UserLoyaltyLevelVO userLoyaltyLevelVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            //
            LoyaltyLevel loyaltyLevel = loyaltyLevelService.get(userLoyaltyLevelVO.getLoyaltyLevelId());
            userLoyaltyLevelVO.setLoyaltyLevel(loyaltyLevel);

            userLoyaltyLevelVO.setJoinDate(new Date());
            User member = userService.get(userLoyaltyLevelVO.getMemberId());
            userLoyaltyLevelVO.setUser(member);
            userLoyaltyLevelVO.setType("MANUAL");

            userLoyaltyLevelService.saveUserLoyaltyLevel(userLoyaltyLevelVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    @RequestMapping("selectLoyaltyLevelJson")
    @ResponseBody
    public List<SelectOptionVO> selectLoyaltyLevelJson(String name) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LoyaltyLevel.class);

        if (StringUtils.isNoneBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
//        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.addOrder(Order.asc("name"));
        List<LoyaltyLevel> list = loyaltyLevelService.list(detachedCriteria);

        return list.stream().map(item -> new SelectOptionVO(item.getId(), item.getName())).collect(Collectors.toList());
    }

    @RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
        LoyaltyLevel ll = loyaltyLevelService.get(id);
        if (ll != null) {
            try {
                ll.setIsActive(false);
                loyaltyLevelService.saveOrUpdate(ll);
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
            }
        } else {
            return AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }
}
