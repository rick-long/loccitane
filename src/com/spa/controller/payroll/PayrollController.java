package com.spa.controller.payroll;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.spa.model.payroll.PayrollTemplate;
import org.spa.model.payroll.StaffPayroll;
import org.spa.model.user.User;
import org.spa.utils.DateUtil;
import org.spa.utils.I18nUtil;
import org.spa.utils.PDFUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.ServletUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.page.Page;
import org.spa.vo.payroll.PayrollAddVO;
import org.spa.vo.payroll.PayrollListVO;
import org.spa.vo.payroll.PayrollTemplateListVO;
import org.spa.vo.payroll.PayrollTemplateVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/06/17.
 */
@Controller
@RequestMapping("payroll")
public class PayrollController extends BaseController {
	
	public static final String PRINT_PAYSLIPS_URL="/payroll/payslipsTemplate";
	
	
	@RequestMapping("toView")
	public String payrollManagement(Model model,PayrollListVO payrollListVO) {
		
		initPayrollMonths(model);
		
		model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false, false));
		return "payroll/payrollManagement";
	}

	@RequestMapping("list")
	public String listPayroll(Model model,PayrollListVO payrollListVO){	
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StaffPayroll.class);
		
		detachedCriteria.add(Restrictions.eq("isActive", true));
		detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
		//year and month
		String payrollDate=payrollListVO.getPayrollDate();
		if(StringUtils.isNotBlank(payrollDate)){
			detachedCriteria.add(Restrictions.eq("payrollDate", payrollDate));
		}
		//staff
		if (payrollListVO.getStaffId() !=null) {
			detachedCriteria.add(Restrictions.eq("staff.id", payrollListVO.getStaffId()));
		}
		
		if (payrollListVO.getShopId()!=null && payrollListVO.getShopId().longValue()>0) {
			detachedCriteria.createAlias("staff", "staffObj");
			detachedCriteria.createAlias("staffObj.staffHomeShops", "homeshop");
			detachedCriteria.add(Restrictions.eq("homeshop.id", payrollListVO.getShopId()));
		}
		
		detachedCriteria.addOrder(Order.desc("id"));
		
		Page<StaffPayroll> payrollPage = payrollService.list(detachedCriteria, payrollListVO.getPageNumber(), payrollListVO.getPageSize());
		model.addAttribute("page", payrollPage);
		
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(payrollListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "payroll/payrollList";
	}
	
	@RequestMapping("toAdd")
	public String toAddPayroll(PayrollAddVO payrollAddVO,Model model){
		
		initPayrollMonths(model);
		
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("enabled", true));
		dc.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
		dc.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
		
	    List<User> staffList=userService.list(dc);
	    model.addAttribute("staffList", staffList);
		return "payroll/payrollAdd";
	}
	
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addPayroll(@Valid PayrollAddVO payrollAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			payrollService.savePayroll(payrollAddVO);
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
		}
	}
	
	@RequestMapping("toEdit")
	public String toEditPayroll(PayrollAddVO payrollAddVO,Model model){
		
	    Long id=payrollAddVO.getId();
	    StaffPayroll payroll=payrollService.get(id);
	    model.addAttribute("payroll", payroll);
	    
		return "payroll/payrollEdit";
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public AjaxForm editPayroll(@Valid PayrollAddVO payrollAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			User staff=userService.get(payrollAddVO.getStaffId());
			payrollService.saveAndUpdatePayroll(payrollAddVO, staff);
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
		}
	}
	
	@RequestMapping("regenerate")
	@ResponseBody
	public AjaxForm regenerate(@Valid PayrollAddVO payrollAddVO) {
		
		Long id=payrollAddVO.getId();
		StaffPayroll payroll=payrollService.get(id);
		payrollService.regeneratePayrolls(payroll);
		
		return AjaxFormHelper.success(I18nUtil.getMessageKey("label.regenerate.successfully"));
	}
	
	@RequestMapping("print")
	public void print(Long payrollId,HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		map.put("payrollId",payrollId);
		String downloadFileName = RandomUtil.generateRandomNumberWithDate("Payslips-")+".pdf";
	    try {
	        File downloadFile = PDFUtil.convert(PRINT_PAYSLIPS_URL, request, map);
	        ServletUtil.download(downloadFile, downloadFileName, response);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
					 
	@RequestMapping("payslipsTemplate")
	public String payslipsTemplate(Long payrollId,String test,Model model) {
		StaffPayroll payroll=payrollService.get(payrollId);
		Date date=DateUtil.stringToDate(payroll.getPayrollDate(), "yyyy-MM-dd");
		model.addAttribute("payroll", payroll);
		model.addAttribute("date", date);
		return "payroll/payslipsTemplate";
	}
	
	@RequestMapping("toTemplateView")
    public String payrollTemplateManagement(Model model, PayrollTemplateListVO payrollTemplateListVO) {
        return "payroll/payrollTemplateManagement";
    }

    @RequestMapping("payrollTemplateList")
    public String payrollTemplateList(Model model, PayrollTemplateListVO payrollTemplateListVO) {
        String name = payrollTemplateListVO.getName();
        String isActive = payrollTemplateListVO.getIsActive();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PayrollTemplate.class);
        
        if (StringUtils.isNoneBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        
        if(WebThreadLocal.getCompany() !=null){
        	detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        
        Page<PayrollTemplate> page = payrollTemplateService.list(detachedCriteria, payrollTemplateListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(payrollTemplateListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "payroll/payrollTemplateList";
    }

    @RequestMapping("payrollTemplateToAdd")
    public String payrollTemplateToAdd(Model model, PayrollTemplateListVO payrollTemplateVO) {
        model.addAttribute("payrollTemplateVO", payrollTemplateVO);
        return "payroll/payrollTemplateAdd";
    }

    @RequestMapping("payrollTemplateConfirm")
    public String payrollTemplateConfirm(Model model, PayrollTemplateVO payrollTemplateVO) {
        model.addAttribute("payrollTemplateVO", payrollTemplateVO);
        return "payroll/payrollTemplateConfirm";
    }
    
    @RequestMapping("payrollTemplateSave")
    @ResponseBody
    public AjaxForm payrollTemplateSave(@Valid PayrollTemplateVO payrollTemplateVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            payrollTemplateVO.setCompany(WebThreadLocal.getCompany());
            payrollTemplateService.saveOrUpdate(payrollTemplateVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
        }
    }

    @RequestMapping("payrollTemplateView")
    public String payrollTemplateView(Model model, Long id) {
    	PayrollTemplate payrollTemplate = payrollTemplateService.get(id);
        model.addAttribute("payrollTemplate", payrollTemplate);
        return "payroll/payrollTemplateView";
    }

    @RequestMapping("payrollTemplateToEdit")
    public String payrollTemplateToEdit(Model model, PayrollTemplateVO payrollTemplateVO) {
    	PayrollTemplate payrollTemplate = payrollTemplateService.get(payrollTemplateVO.getId());
        model.addAttribute("payrollTemplate", payrollTemplate);
        return "payroll/payrollTemplateEdit";
    }

    @RequestMapping("payrollTemplateSelectList")
    public String payrollTemplateSelectList(Long initialValue, Boolean showAll, Model model) {
    	DetachedCriteria dc = DetachedCriteria.forClass(PayrollTemplate.class);
        List<PayrollTemplate> list = payrollTemplateService.getActiveListByRefAndCompany(dc, null, WebThreadLocal.getCompany().getId());
        if(initialValue !=null && initialValue.longValue()>0){
        	//
        }else{
        	if(list !=null && list.size()>0){
        		initialValue=list.get(0).getId();
        	}
        }
        model.addAttribute("list", list);
        model.addAttribute("initialValue", initialValue);
        model.addAttribute("showAll", showAll);
        return "payroll/payrollTemplateSelectList";
    }
    
    private void initPayrollMonths(Model model){
    	 List<String> allYearAndMonths = new ArrayList<>();
         Date fromDate = new Date();
         Date toDate = new Date();
         try {
             fromDate = new SimpleDateFormat("yyyy-MM").parse("2016-08");
             toDate = new SimpleDateFormat("yyyy-MM").parse("2020-01");//定义结束日期

         } catch (ParseException e) {
             e.printStackTrace();
         }
         Calendar date = Calendar.getInstance();//定义日期实例
         date.setTime(fromDate);//设置日期起始时间
         while (date.getTime().before(toDate)) {//判断是否到结束日期
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
             String yearAndMonthString = sdf.format(date.getTime());
             allYearAndMonths.add(yearAndMonthString);
             date.add(Calendar.MONTH, 1);//进行当前日期月份加1
         }
         model.addAttribute("allYearAndMonths", allYearAndMonths);
         
         Date now = new Date();
         try {
 			String currentMonth=DateUtil.dateToString(now, "yyyy-MM");
 			model.addAttribute("currentMonth", currentMonth);
 			
 		} catch (ParseException e) {
 			e.printStackTrace();
 		}
         
    }
}
