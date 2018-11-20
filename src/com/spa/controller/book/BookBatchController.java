package com.spa.controller.book;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.model.book.BookBatch;
import org.spa.model.product.ProductOption;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.utils.Results;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.book.BookBatchListVO;
import org.spa.vo.book.BookBatchVO;
import org.spa.vo.page.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;

import static org.spa.utils.Results.CODE_SERVER_ERROR;

@Controller
@RequestMapping("bookBatch")
public class BookBatchController extends BaseController {

    @RequestMapping("toListBookBatch")
    public String toListBookBatch(Model model, String flag, BookBatchListVO bookBatchListVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        model.addAttribute("fromDate", new Date());

        model.addAttribute("endDate", DateUtil.getDateAfter(new Date(), 30));
        return "book/bookBatchManagement";
    }

    @RequestMapping("listBookBatch")
    public String listBookBatch(Model model, BookBatchListVO bookBatchListVO) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BookBatch.class);

        if (StringUtils.isNotBlank(bookBatchListVO.getBatchNumber())) {
            detachedCriteria.add(Restrictions.eq("batchNumber", bookBatchListVO.getBatchNumber()));
        }
        if (bookBatchListVO.getShopId() != null && bookBatchListVO.getShopId().longValue() > 0) {
            detachedCriteria.add(Restrictions.eq("shop.id", bookBatchListVO.getShopId()));
        } else {
            detachedCriteria.add(Restrictions.in("shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
        }
        if (bookBatchListVO.getMemberId() != null) {
            detachedCriteria.add(Restrictions.eq("member.id", bookBatchListVO.getMemberId()));
        }
        if (StringUtils.isNotBlank(bookBatchListVO.getFromDate())) {
            detachedCriteria.add(Restrictions.ge("startDate", DateUtil.getFirstMinuts(DateUtil.stringToDate(bookBatchListVO.getFromDate(), "yyyy-MM-dd"))));
        }
        if (StringUtils.isNotBlank(bookBatchListVO.getToDate())) {
            detachedCriteria.add(Restrictions.le("startDate", DateUtil.getLastMinuts(DateUtil.stringToDate(bookBatchListVO.getToDate(), "yyyy-MM-dd"))));
        }

        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.addOrder(Order.desc("id"));
        Page<BookBatch> page = bookBatchService.list(detachedCriteria, bookBatchListVO);
        if (page.getList() != null && page.getList().size() > 0) {
            for (BookBatch bookBatch : page.getList()) {
                if (bookBatch.getDays().length() != 0) {
                    String days = bookBatch.getDays();
                    days = bookBatch.getDays().substring(0, (days.length() - 1));
                    days = days.replaceAll(",", " / ");
                    bookBatch.setDays(days);
                }
                String month = "";
                if (bookBatch.getMonths().length() != 0) {
                    String months = bookBatch.getMonths();
                    String[] split = months.split(",");
                    for (int i = 0; i < split.length; i++) {
                        switch (split[i]) {
                            case "1":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.1") + " /";
                                break;
                            case "2":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.2") + " /";
                                break;
                            case "3":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.3") + " /";
                                break;
                            case "4":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.4") + " /";
                                break;
                            case "5":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.5") + " /";
                                break;
                            case "6":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.6") + " /";
                                break;
                            case "7":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.7") + " /";
                                break;
                            case "8":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.8") + " /";
                                break;
                            case "9":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.9") + " /";
                                break;
                            case "10":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.10") + " /";
                                break;
                            case "11":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.11") + " /";
                                break;
                            case "12":
                                month += I18nUtil.getMessageKey("label.repeat.booking.month.12") + " /";
                                break;

                        }
                    }
                    month = month.substring(0,month.length() - 2);
                    bookBatch.setMonths(month);
                }
                String week = "";
                if (bookBatch.getWeeks().length() != 0) {
                    String weeks = bookBatch.getWeeks();
                    String[] split = weeks.split(",");
                    for (int i = 0; i < split.length; i++) {
                        switch (split[i]) {
                            case "1":
                                week += I18nUtil.getMessageKey("label.repeat.booking.week.1") + " /";
                                break;
                            case "2":
                                week += I18nUtil.getMessageKey("label.repeat.booking.week.2") + " /";
                                break;
                            case "3":
                                week += I18nUtil.getMessageKey("label.repeat.booking.week.3") + " /";
                                break;
                            case "4":
                                week += I18nUtil.getMessageKey("label.repeat.booking.week.4") + " /";
                                break;
                            case "5":
                                week += I18nUtil.getMessageKey("label.repeat.booking.week.5") + " /";
                                break;
                            case "6":
                                week += I18nUtil.getMessageKey("label.repeat.booking.week.6") + " /";
                                break;
                            case "7":
                                week += I18nUtil.getMessageKey("label.repeat.booking.week.7") + " /";
                                break;
                        }
                    }
                    week = week.substring(0,week.length() - 2);
                    bookBatch.setWeeks(week);
                }


            }
        }

        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(bookBatchListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "book/bookBatchList";
    }

    @RequestMapping("toAddBookBatch")
    public String toAddBookBatch(Model model) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));

        Date now = new Date();
        model.addAttribute("startDate", now);
        model.addAttribute("endDate", now);
        return "book/bookBatchAdd";
    }

    @RequestMapping("confirmBookBatch")
    public String confirmBookBatch(BookBatchVO bookBatchVO, Model model) {

        String res = validate(bookBatchVO);
        if (StringUtils.isNotBlank(res)) {
            model.addAttribute("error", res);
        } else {
            ProductOption po = productOptionService.get(bookBatchVO.getProductOptionId());
            if (!CommonConstant.anyTherapist.equals(bookBatchVO.getTherapistId())) {
                User therapist = userService.get(Long.parseLong(bookBatchVO.getTherapistId()));
                // 检测therapist技能 在confirm层面必须告知，但是继续操作的情况下，booking status will be waiting
                if (bookBatchVO.getTherapist() != null) {
                    if (!userService.checkTherapistSkill(therapist, po)) {
                        res = I18nUtil.getMessageKey("label.book.schedule.therapist.no.skill");
                        //" [" + therapist.getFullName() + "] do not have skill for product [" + bookItem.getProductName() + "]!");
                    }
                }
                if (StringUtils.isNotBlank(res)) {
                    model.addAttribute("error", res);
                }
                model.addAttribute("therapist", therapist);
            }

            User member = userService.get(bookBatchVO.getMemberId());
            Shop shop = shopService.get(bookBatchVO.getShopId());
            model.addAttribute("shop", shop);
            model.addAttribute("member", member);

            model.addAttribute("po", po);
        }

        model.addAttribute("bookBatchVO", bookBatchVO);

        return "book/bookBatchConfirm";
    }

    @RequestMapping("addBookBatch")
    @ResponseBody
    public AjaxForm addBookBatch(BookBatchVO bookBatchVO) {

        String res = validate(bookBatchVO);
        if (StringUtils.isNotBlank(res)) {
            return AjaxFormHelper.error().addAlertError(res);
        }
        try {
            Results results = bookBatchService.saveOrUpdateBookBatch(bookBatchVO);
            if (results.getCode() == CODE_SERVER_ERROR) {
                return AjaxFormHelper.error().addAlertError(results.getMessages().get("errorMsg").toString());
            }
            return AjaxFormHelper.success(results.getMessages().get("successMsg").toString());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }
    }

    @RequestMapping("toEditBookBatch")
    public String toEditBookBatch(Model model, BookBatchVO bookBatchVO) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        Long batchId = bookBatchVO.getId();
        BookBatch bookBatch = bookBatchService.get(batchId);
        model.addAttribute("bookBatch", bookBatch);

        String monthStr = bookBatch.getMonths();
        String weekStr = bookBatch.getWeeks();
        String dayStr = bookBatch.getDays();

        if (StringUtils.isNotBlank(monthStr)) {
            List<Integer> months = new ArrayList<Integer>();
            String[] strs = monthStr.split(",");
            for (String str : strs) {
                months.add(Integer.parseInt(str));
            }
            model.addAttribute("months", months);
        }

        if (StringUtils.isNotBlank(weekStr)) {
            List<Integer> weeks = new ArrayList<Integer>();
            String[] strs = weekStr.split(",");
            for (String str : strs) {
                weeks.add(Integer.parseInt(str));
            }
            model.addAttribute("weeks", weeks);
        }

        if (StringUtils.isNotBlank(dayStr)) {
            List<Integer> days = new ArrayList<Integer>();
            String[] strs = dayStr.split(",");
            for (String str : strs) {
                days.add(Integer.parseInt(str));
            }
            model.addAttribute("days", days);
        }
        return "book/bookBatchEdit";
    }

    @RequestMapping("editBookBatch")
    @ResponseBody
    public AjaxForm editBookBatch(BookBatchVO bookBatchVO) {

        String res = validate(bookBatchVO);
        if (StringUtils.isNotBlank(res)) {
            return AjaxFormHelper.error().addAlertError(res);
        }
        try {
            bookBatchService.saveOrUpdateBookBatch(bookBatchVO);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return AjaxFormHelper.error().addAlertError(e.getMessage());
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
    }

    @RequestMapping("removeBookBatch")
    @ResponseBody
    public AjaxForm removeBookBatch(BookBatchVO bookBatchVO) {
        Date now = new Date();
        String loginer = WebThreadLocal.getUser().getUsername();
        bookBatchService.removeBookBatch(bookBatchVO, loginer, now);
        return AjaxFormHelper.success("success");
    }

    public String validate(BookBatchVO bookBatchVO) {
        String res = "";
        if (bookBatchVO.getMemberId() == null) {
            res = I18nUtil.getMessageKey("label.book.schedule.member.null");
        }
        if (bookBatchVO.getShopId() == null) {
            res = I18nUtil.getMessageKey("label.book.schedule.shop.null");
        }
        if (bookBatchVO.getProductOptionId() == null) {
            res = I18nUtil.getMessageKey("label.book.schedule.treatment.null");
        }
        if (bookBatchVO.getRepeatStartDate() == null || bookBatchVO.getRepeatEndDate() == null) {
            res = I18nUtil.getMessageKey("label.book.schedule.repeat.date.null");//Repeat Start Date or Repeat End Date can not be null!";
        } else if (bookBatchVO.getRepeatEndDate().before(bookBatchVO.getRepeatStartDate())) {
            res = I18nUtil.getMessageKey("label.book.schedule.startday.after.endday");//Repeat End Date must after Repeat Start Date!";
        }

        if (StringUtils.isBlank(bookBatchVO.getRepeatStartTime()) || StringUtils.isBlank(bookBatchVO.getRepeatEndTime())) {
            res = I18nUtil.getMessageKey("label.book.schedule.repeat.time.null");//Repeat Start Time or Repeat End Time can not be null!";
        } else {
            String[] startTimes = bookBatchVO.getRepeatStartTime().split(":");
            String[] endTimes = bookBatchVO.getRepeatEndTime().split(":");
            int startHour = Integer.parseInt(startTimes[0]);
            int endHour = Integer.parseInt(endTimes[0]);
            if (startHour > endHour || ((startHour == endHour) && Integer.parseInt(startTimes[1]) >= Integer.parseInt(endTimes[1]))) {
                res = I18nUtil.getMessageKey("label.book.schedule.starttime.after.endtime");//"Repeat End Time must after Repeat Start Time!";
            }
        }

        return res;
    }
}
