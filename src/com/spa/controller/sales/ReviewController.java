package com.spa.controller.sales;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.spa.model.order.ReviewRatingShop;
import org.spa.model.order.ReviewRatingTreatment;
import org.spa.model.order.ReviewRatingTreatmentTherapist;
import org.spa.model.payment.PaymentMethod;
import org.spa.model.product.Category;
import org.spa.service.order.ReviewRatingShopService;
import org.spa.service.order.ReviewRatingTreatmentService;
import org.spa.service.order.ReviewRatingTreatmentTherapistService;
import org.spa.service.order.ReviewService;
import org.spa.utils.Results;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.page.Page;
import org.spa.vo.prepaid.PrepaidListVO;
import org.spa.vo.report.SalesSearchVO;
import org.spa.vo.sales.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ivy on 2018/01/30.
 */
@Controller
@RequestMapping("review")
public class ReviewController extends BaseController {
    @Autowired
    ReviewRatingShopService reviewRatingShopService;

    @Autowired
    ReviewRatingTreatmentService ratingTreatmentService;
    @Autowired
    ReviewRatingTreatmentTherapistService reviewRatingTreatmentTherapistService;

    @Autowired
    ReviewService reviewService;

    @RequestMapping("toView")
    public String prepaidManagement(Model model) {
        return "sales/evaluationManagement";
    }

    @RequestMapping("/evaluation")
    public String evaluation(Model model, SalesSearchVO salesSearchVO) {
        String date = salesSearchVO.getReviewDate();
        Results results = Results.getInstance();
        if (StringUtils.isEmpty(date)) {
            date = "7Days";
        }
        String fromDate = null;
        String toDate = null;

        if (StringUtils.isNotBlank(salesSearchVO.getFromDate())) {
            date = null;
            fromDate = salesSearchVO.getFromDate() + " 00:00:00";
            toDate = salesSearchVO.getToDate() + " 23:59:59";
        }
        Date from = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(from);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (date != null) {
            switch (date) {
                case "year":
                    toDate = sdf.format(from) + " 23:59:59";
                    calendar.setTime(from);
                    calendar.add(Calendar.YEAR, -1);
                    fromDate = sdf.format(calendar.getTime()) + " 00:00:00";
                    break;
                case "6months":
                    toDate = sdf.format(from) + "23:59:59";
                    calendar.setTime(from);
                    calendar.add(Calendar.MONTH, -6);
                    fromDate = sdf.format(calendar.getTime()) + " 00:00:00";
                    break;
                case "30Days":
                    toDate = sdf.format(from) + " 23:59:59";
                    calendar.setTime(from);
                    calendar.add(Calendar.DATE, -30);
                    fromDate = sdf.format(calendar.getTime()) + " 00:00:00";
                    break;
                case "7Days":
                    toDate = sdf.format(from) + " 23:59:59";
                    calendar.setTime(from);
                    calendar.add(Calendar.DATE, -7);
                    fromDate = sdf.format(calendar.getTime()) + " 00:00:00";
                    break;
                default:
                    break;
            }
        }
        salesSearchVO.setFromDate(fromDate);
        salesSearchVO.setToDate(toDate);
        ReviewStatisticalVO reviewStatisticalVO = ratingTreatmentService.getStatistical(salesSearchVO);
        List<Map<String, Object>> shopTop10List = reviewRatingShopService.getTop10Avg(salesSearchVO);
        List<Map<String, Object>> shopLow10List = reviewRatingShopService.getLow10Avg(salesSearchVO);
        List<Map<String, Object>> treatmentTop10List = ratingTreatmentService.getTop10Avg(salesSearchVO);
        List<Map<String, Object>> treatmentLow10List = ratingTreatmentService.getLow10Avg(salesSearchVO);
        List<Map<String, Object>> treatmentTherapistTop10List = reviewRatingTreatmentTherapistService.getTop10Avg(salesSearchVO);
        List<Map<String, Object>> treatmentTherapistLow10List = reviewRatingTreatmentTherapistService.getLow10Avg(salesSearchVO);
        List<Map<String, Object>> listings = reviewService.getListing(salesSearchVO);
        /*	Map<String,Object> map = new HashMap<String, Object>();*/
	/*	map.put("test","test");
		map.put("listings",listings);
		map.put("reviewStatisticalVO", reviewStatisticalVO);
		map.put("shopLow10List", shopLow10List);
		map.put("shopTop10List", shopTop10List);
		map.put("treatmentTop10List", treatmentTop10List);
		map.put("treatmentLow10List", treatmentLow10List);
		map.put("treatmentTherapistTop10List", treatmentTherapistTop10List);
		map.put("treatmentTherapistLow10List", treatmentTherapistLow10List);*/
        model.addAttribute("listings", listings);
        model.addAttribute("reviewStatisticalVO", reviewStatisticalVO);
        model.addAttribute("shopLow10List", shopLow10List);
        model.addAttribute("shopTop10List", shopTop10List);
        model.addAttribute("treatmentTop10List", treatmentTop10List);
        model.addAttribute("treatmentLow10List", treatmentLow10List);
        model.addAttribute("treatmentTherapistTop10List", treatmentTherapistTop10List);
        model.addAttribute("treatmentTherapistLow10List", treatmentTherapistLow10List);
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        return "sales/evaluation";
    }

    @RequestMapping("listingToView")
    public String listingToView(Model model) {
        //get sub-category under topist category
        List<Category> parentCategoryList = categoryService.getCategoriesByParentCategoryRef(CommonConstant.CATEGORY_REF_ROOT, WebThreadLocal.getCompany().getId());
        model.addAttribute("parentCategoryList", parentCategoryList);
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, false));
        return "sales/listingManagement";
    }

    @RequestMapping("/listing")
    public String listing(Model model, SalesSearchVO salesSearchVO) {
        String date = salesSearchVO.getReviewDate();
        if (StringUtils.isEmpty(date)) {
            date = "7Days";
        }
        /* date=salesSearchVO.getReviewDate();*/
        String fromDate = null;
        String toDate = null;
        Date from = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(from);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(salesSearchVO.getFromDate())) {
            date = null;
            fromDate = salesSearchVO.getFromDate() + " 00:00:00";
            toDate = salesSearchVO.getFromDate() + " 23:59:59";
        }
        if (date != null) {
            switch (date) {
                case "year":
                    toDate = sdf.format(from) + " 23:59:59";
                    calendar.setTime(from);
                    calendar.add(Calendar.YEAR, -1);
                    fromDate = sdf.format(calendar.getTime()) + " 00:00:00";
                    break;
                case "6months":
                    toDate = sdf.format(from) + "23:59:59";
                    calendar.setTime(from);
                    calendar.add(Calendar.MONTH, -6);
                    fromDate = sdf.format(calendar.getTime()) + " 00:00:00";
                    break;
                case "30Days":
                    toDate = sdf.format(from) + " 23:59:59";
                    calendar.setTime(from);
                    calendar.add(Calendar.DATE, -30);
                    fromDate = sdf.format(calendar.getTime()) + " 00:00:00";
                    break;
                case "7Days":
                    toDate = sdf.format(from) + " 23:59:59";
                    calendar.setTime(from);
                    calendar.add(Calendar.DATE, -7);
                    fromDate = sdf.format(calendar.getTime()) + " 00:00:00";
                    break;
                default:
                    break;
            }
        }

        salesSearchVO.setFromDate(fromDate);
        salesSearchVO.setToDate(toDate);
        List<Map<String, Object>> listings = reviewService.getListing(salesSearchVO);
        model.addAttribute("listings", listings);
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        return "sales/listing";
    }

    @RequestMapping("/reviewRatingShopList")
    public String reviewRatingShopList(Model model, ReviewRatingShopVO reviewRatingShopVO) {
        SalesSearchVO salesSearchVO = new SalesSearchVO();
        Long reviewSum = reviewService.getCount();
        Double customerServicesAvg = reviewRatingShopService.getAllShopCustomerServicesAvg(salesSearchVO);
        Double shopCleanlinessAvg = reviewRatingShopService.getAllShopCleanlinessAvg(salesSearchVO);
        Page<Map<String, Object>> reviewRatingShopAvgPage = reviewRatingShopService.getAllReviewRatingShopAvg(reviewRatingShopVO);
        model.addAttribute("customerServicesAvg", customerServicesAvg);
        model.addAttribute("shopCleanlinessAvg", shopCleanlinessAvg);
        model.addAttribute("reviewSum", reviewSum);
        model.addAttribute("page", reviewRatingShopAvgPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(reviewRatingShopVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sales/reviewRatingShopList";
    }

    @RequestMapping("reviewRatingShopDetails")
    public String reviewRatingShopDetails(Model model, HttpServletRequest request, ReviewRatingShopVO reviewRatingShopVO) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReviewRatingShop.class);
        detachedCriteria.add(Restrictions.eq("shop.id", reviewRatingShopVO.getShopId()));
        Page<ReviewRatingShop> reviewRatingShopPage = reviewRatingShopService.list(detachedCriteria, reviewRatingShopVO);
        model.addAttribute("page", reviewRatingShopPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(reviewRatingShopVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sales/reviewRatingShopDetails";

    }

    @RequestMapping("/ratingTreatmentList")
    public String ratingTreatmentList(Model model, ReviewRatingTreatmentVO reviewRatingTreatmentVO) {
        SalesSearchVO salesSearchVO = new SalesSearchVO();
        Long reviewSum = reviewService.getCount();
        Double satisfactionLevelStarAvg = ratingTreatmentService.getAllSatisfactionLevelStarAvg(salesSearchVO);
        Double valueForMoneyStarAvg = ratingTreatmentService.getValueForMoneyStarAvg(salesSearchVO);
        Page<Map<String, Object>> ratingTreatmentPage = ratingTreatmentService.getAllReviewRatingTreatmentAvg(reviewRatingTreatmentVO);
        model.addAttribute("satisfactionLevelStarAvg", satisfactionLevelStarAvg);
        model.addAttribute("valueForMoneyStarAvg", valueForMoneyStarAvg);
        model.addAttribute("reviewSum", reviewSum);
        model.addAttribute("page", ratingTreatmentPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(reviewRatingTreatmentVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sales/ratingTreatmentList";
    }

    @RequestMapping("ratingTreatmentDetails")
    public String ratingTreatmentDetails(Model model, HttpServletRequest request, ReviewRatingTreatmentVO reviewRatingTreatmentVO) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReviewRatingTreatment.class);
        detachedCriteria.add(Restrictions.eq("product.id", reviewRatingTreatmentVO.getProductId()));
        Page<ReviewRatingTreatment> reviewRatingTreatmentPage = ratingTreatmentService.list(detachedCriteria, reviewRatingTreatmentVO);
        model.addAttribute("page", reviewRatingTreatmentPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(reviewRatingTreatmentVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sales/ratingTreatmentDetails";
    }

    @RequestMapping("/ratingTreatmentTherapistList")
    public String ratingTreatmentTherapistList(Model model, ReviewRatingTreatmentTherapistVO reviewRatingTreatmentTherapistVO) {
        SalesSearchVO salesSearchVO = new SalesSearchVO();
        Long reviewSum = reviewService.getCount();
        Double staffStarAvg = reviewRatingTreatmentTherapistService.getAllStaffStarAvg(salesSearchVO);
        Page<Map<String, Object>> ratingTreatmentTherapistPage = reviewRatingTreatmentTherapistService.getAllReviewRatingTreatmentTherapistAvg(reviewRatingTreatmentTherapistVO);
        model.addAttribute("staffStarAvg", staffStarAvg);
        model.addAttribute("reviewSum", reviewSum);
        model.addAttribute("page", ratingTreatmentTherapistPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(reviewRatingTreatmentTherapistVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sales/ratingTreatmentTherapistList";
    }

    @RequestMapping("ratingTreatmentTherapistDetails")
    public String ratingTreatmentTherapistDetails(Model model, HttpServletRequest request, ReviewRatingTreatmentTherapistVO reviewRatingTreatmentTherapistVO) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReviewRatingTreatmentTherapist.class);
        detachedCriteria.add(Restrictions.eq("staff.id", reviewRatingTreatmentTherapistVO.getStaffId()));
        /*List<ReviewRatingTreatmentTherapist>reviewRatingTreatmentTherapistPage=reviewRatingTreatmentTherapistService.list(detachedCriteria);*/
        Page<ReviewRatingTreatmentTherapist> reviewRatingTreatmentTherapistPage = reviewRatingTreatmentTherapistService.list(detachedCriteria, reviewRatingTreatmentTherapistVO);
        model.addAttribute("page", reviewRatingTreatmentTherapistPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(reviewRatingTreatmentTherapistVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sales/ratingTreatmentTherapistDetails";
    }


}
