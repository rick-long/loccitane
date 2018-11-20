package com.spa.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.spa.model.book.BookItem;
import org.spa.model.user.User;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.common.TotalFigureVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spa.constant.CommonConstant;

/**
 * Created by Ivy on 2016/08/23.
 */
@Controller
@RequestMapping("dashboard")
public class DashboardController extends BaseController {

    @RequestMapping("/toShopManagerHome")
    public String toShopManagerHome(Long shopId, Model model) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("shopId", shopId);
        
        User currentLoginUser = WebThreadLocal.getUser();
        Boolean showAll=Boolean.FALSE;
        
        if(currentLoginUser.getFirstRoleForStaff().getReference().equals(CommonConstant.STAFF_ROLE_REF_ADMIN)){
        	showAll = Boolean.TRUE;
        }
        model.addAttribute("showAll", showAll);
        return "dashboard/shopManager/shopManagerHome";
    }

    // upcoming bookings

    @RequestMapping("upComingBookings")
    public String upComingBookings(Long shopId, Long dateMillis, Model model) {
        DateTime dateTime = new DateTime(dateMillis);
        Date startDate = dateTime.withTimeAtStartOfDay().toDate();
        Date endDate = dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate();
        List<String> comingStatusList = new ArrayList<>();
//        comingStatusList.add(CommonConstant.BOOK_STATUS_PENDING);
        comingStatusList.add(CommonConstant.BOOK_STATUS_CONFIRM);
        List<BookItem> bookItemList = bookItemService.getBookItemList(shopId, startDate, endDate, comingStatusList);

        List<String> waitingStatusList = new ArrayList() {{
            add(CommonConstant.BOOK_STATUS_WAITING);
        }};

        List<BookItem> waitingList = bookItemService.getBookItemList(shopId, startDate, endDate, waitingStatusList);

        model.addAttribute("bookItemList", bookItemList);
        model.addAttribute("waitingList", waitingList);

        return "dashboard/shopManager/upComingBookings";

    }

    @RequestMapping("/toTotalFigures")
    public String toTotalFigures(Long shopId, Long dateMillis, Model model) {
        DateTime dateTime = new DateTime(dateMillis);
        Date startDate = dateTime.withTimeAtStartOfDay().toDate();
        Date endDate = dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate();
        TotalFigureVO totalFigureVO = new TotalFigureVO();
        model.addAttribute("totalFigureVO", totalFigureVO);

        Long companyId=null;
        if(WebThreadLocal.getCompany() !=null){
        	companyId = WebThreadLocal.getCompany().getId();
        }
        //total revenue
        Double totalRevenue=purchaseOrderService.getTotalRevenueByFilters(startDate, endDate, shopId, companyId);
        totalFigureVO.setTotalRevenue(totalRevenue);
        
        // total service
        Double totalServices=purchaseOrderService.getTotalServiceByFilters(startDate, endDate, shopId, companyId);
        totalFigureVO.setTotalServices(totalServices);
        
        //total package
        Double totalPackages=purchaseOrderService.getTotalPackageByFilters(startDate, endDate, shopId, companyId);
        totalFigureVO.setTotalPackages(totalPackages);
        
        //total retail
        Double totalRetail=purchaseOrderService.getTotalRetailByFilters(startDate, endDate, shopId, companyId);
        totalFigureVO.setTotalRetail(totalRetail);
        // bookings
        List<String> bookingsStatus = new ArrayList<>();
        
        long bookings = bookItemService.countBookItems(shopId, startDate, endDate, bookingsStatus,companyId);
        totalFigureVO.setBookings(bookings);

        // walkIns
        long walkIns = bookItemService.countWalkInBookItems(shopId, startDate, endDate, bookingsStatus, true,companyId);
        totalFigureVO.setWalkIns(walkIns);

        // waitingList
        List<String> waitingStatusList = new ArrayList<>();
        waitingStatusList.add(CommonConstant.BOOK_STATUS_WAITING);
        long waitings = bookItemService.countBookItems(shopId, startDate, endDate, waitingStatusList,companyId);
        totalFigureVO.setWaitingList(waitings);

        // no show
        List<String> noShowStatusList = new ArrayList<>();
        noShowStatusList.add(CommonConstant.BOOK_STATUS_NOT_SHOW);
        long noShows = bookItemService.countBookItems(shopId, startDate, endDate, noShowStatusList,companyId);
        totalFigureVO.setNoShows(noShows);


        return "dashboard/shopManager/totalFigures";
    }

}
