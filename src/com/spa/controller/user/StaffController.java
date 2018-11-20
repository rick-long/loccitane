package com.spa.controller.user;

import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.spa.jxlsBean.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.subject.Subject;
import org.hibernate.criterion.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jxls.common.Context;
import org.spa.model.payroll.PayrollTemplate;
import org.spa.model.company.Company;
import org.spa.model.privilege.SysRole;
import org.spa.model.product.Category;
import org.spa.model.product.Product;
import org.spa.model.shop.Address;
import org.spa.model.shop.OpeningHours;
import org.spa.model.shop.Shop;
import org.spa.model.staff.StaffInOrOut;
import org.spa.model.staff.StaffPayrollAttribute;
import org.spa.model.staff.StaffTreatments;
import org.spa.model.user.User;
import org.spa.model.user.UserGroup;
import org.spa.service.shop.AddressService;
import org.spa.utils.*;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.common.SelectOptionVO;
import org.spa.vo.page.Page;
import org.spa.vo.staff.*;
import org.spa.vo.user.AddressVO;
import org.spa.vo.user.UserGroupListVO;
import org.spa.vo.user.UserGroupVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Ivy on 2016/03/30.
 */
@Controller
@RequestMapping("staff")
public class StaffController extends BaseController {

    @RequestMapping("toView")
    public String memberManagement(Model model) {
        StaffListVO staffListVO = new StaffListVO();
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, false));

        DetachedCriteria uldc = DetachedCriteria.forClass(SysRole.class);
        List<SysRole> sysRoleList = sysRoleService.getActiveListByRefAndCompany(uldc, null, WebThreadLocal.getCompany().getId());
        model.addAttribute("sysRoleList", sysRoleList);
        model.addAttribute("staffListVO", staffListVO);
        return "staff/staffManagement";
    }

    @RequestMapping("staffCurrentShopSelect")
    public void staffCurrentShopSelect(Long shopId) {
        Shop shop = shopService.get(shopId);
        WebThreadLocal.setCurrentShop(shop);
    }

    @RequestMapping("list")
    public String memberList(Model model, StaffListVO staffListVO) {

        String username = staffListVO.getUsername();
        String email = staffListVO.getEmail();
        String enabled = staffListVO.getEnabled();
        Long sysRoleId = staffListVO.getSysRoleId();
        Long homeShopId = staffListVO.getHomeShopId();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);

        if (StringUtils.isNotBlank(username)) {
            detachedCriteria.createAlias("phones", "phone");
//			3个or以上也可以这么实现		
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("firstName", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("lastName", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("phone.number", username, MatchMode.ANYWHERE));
            detachedCriteria.add(disjunction);
        }
        if (StringUtils.isNotBlank(email)) {
            detachedCriteria.add(Restrictions.like("email", email, MatchMode.ANYWHERE));
        }
        if (sysRoleId != null && sysRoleId.longValue() > 0) {
            detachedCriteria.createAlias("sysRoles", "sysRole");
            detachedCriteria.add(Restrictions.eq("sysRole.id", sysRoleId));
        }
        if (homeShopId != null && homeShopId.longValue() > 0) {
            detachedCriteria.createAlias("staffHomeShops", "shop");
            detachedCriteria.add(Restrictions.eq("shop.id", homeShopId));
        } else {
            detachedCriteria.createAlias("staffHomeShops", "shop");
            detachedCriteria.add(Restrictions.in("shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
        }

        if (StringUtils.isNotBlank(enabled)) {
            detachedCriteria.add(Restrictions.eq("enabled", Boolean.parseBoolean(enabled)));
        }
        if (WebThreadLocal.getCompany() != null) {
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
        detachedCriteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));

        //page start
        Page<User> staffPage = userService.list(detachedCriteria, staffListVO.getPageNumber(), staffListVO.getPageSize());
        model.addAttribute("page", staffPage);

        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(staffListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "staff/staffList";
    }

    @RequestMapping("toAdd")
    public String toAddStaff(StaffVO staffVO, Model model) {

        model.addAttribute("shopList", shopService.getAllShop(WebThreadLocal.getCompany().getId()));
        model.addAttribute("districtData", CommonConstant.DISTRICT_DATA);

        DetachedCriteria uldc = DetachedCriteria.forClass(SysRole.class);
        List<SysRole> sysRoleList = sysRoleService.getActiveListByRefAndCompany(uldc, null, WebThreadLocal.getCompany().getId());
        model.addAttribute("sysRoleList", sysRoleList);

        List<UserGroup> staffGroupsForCommission = userGroupService.getUserGroupByTypeAndModule(CommonConstant.USER_GROUP_TYPE_STAFF, CommonConstant.USER_GROUP_MODULE_COMMISSION, WebThreadLocal.getCompany().getId());
        model.addAttribute("staffGroupsForCommission", staffGroupsForCommission);

        model.addAttribute("now", new Date());
        //tab2

        //tab3
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PayrollTemplate.class);
        model.addAttribute("payrollTemplateList", payrollTemplateService.getActiveListByRefAndCompany(detachedCriteria, null, WebThreadLocal.getCompany().getId()));

        return "staff/staffAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm addStaff(@Valid StaffVO staffVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {

            AjaxForm errorAjaxForm = AjaxFormHelper.error();

            //check email
            User staffCheckByEmail = userService.getUserByEmail(staffVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_STAFF);
            if (staffCheckByEmail != null) {
                errorAjaxForm.addErrorFields(new ErrorField("email", I18nUtil.getMessageKey("label.errors.duplicate")));
            }

            if (StringUtils.isNotBlank(staffVO.getMobilePhone()) && !NumberUtil.isNumeric(staffVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.phone.should.be.number")));
            }
            if (StringUtils.isBlank(staffVO.getAddressVO().getDistrict())) {
                errorAjaxForm.addErrorFields(new ErrorField("addressVO.district", I18nUtil.getMessageKey("label.errors.address.district.required")));
            }
            //check password and confirm password
            String password = staffVO.getPassword();
            String confirmPassword = staffVO.getConfirmPassword();
            if (!password.equals(confirmPassword)) {
                errorAjaxForm.addErrorFields(new ErrorField("password", I18nUtil.getMessageKey("label.errors.password.not.same")));
            }
            if(StringUtils.isNotBlank(staffVO.getBarCode())){
                User user = userService.get("barcode", staffVO.getBarCode());
                if(user != null){
                    errorAjaxForm.addErrorFields(new ErrorField("staff number", I18nUtil.getMessageKey("label.clock.barcode.fail1")));
                }
            }
            User user = userService.get("pin", staffVO.getPin());
            if(user != null){
                errorAjaxForm.addErrorFields(new ErrorField("PIN", I18nUtil.getMessageKey("label.clock.pin.fail1")));
            }

            if (!errorAjaxForm.getErrorFields().isEmpty()) {
                return errorAjaxForm;

            } else {
                userService.saveOrUpdateStaff(staffVO);

                //email send to change password

                return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
            }
        }

    }

    @RequestMapping("toEdit")
    public String toEditStaff(StaffVO staffVO, Model model) {

        User staff = userService.get(staffVO.getId());
        model.addAttribute("staff", staff);

        model.addAttribute("staffHomeShops", staff.getStaffHomeShops());

        if (staff.getAddresses() != null && staff.getAddresses().size() > 0) {
            Address addr = staff.getAddresses().iterator().next();
            AddressVO addressVO = new AddressVO();
            addressVO.setId(addr.getId());
            addressVO.setAddressExtention(addr.getAddressExtention());
            addressVO.setCity(addr.getCity());
            addressVO.setCountry(addr.getCountry());
            addressVO.setDistrict(addr.getDistrict());
            staffVO.setAddressVO(addressVO);
        }
        model.addAttribute("staffVO", staffVO);
        model.addAttribute("districtData", CommonConstant.DISTRICT_DATA);

//		model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true,false));
        model.addAttribute("shopList", shopService.getAllShop(WebThreadLocal.getCompany().getId()));

        DetachedCriteria uldc = DetachedCriteria.forClass(SysRole.class);
        List<SysRole> sysRoleList = sysRoleService.getActiveListByRefAndCompany(uldc, null, WebThreadLocal.getCompany().getId());
        model.addAttribute("sysRoleList", sysRoleList);

        UserGroup currentStaffGroup = userGroupService.getStaffGroupByFilters(staff.getId(),
                CommonConstant.USER_GROUP_TYPE_STAFF, CommonConstant.USER_GROUP_MODULE_COMMISSION, WebThreadLocal.getCompany().getId());
        List<UserGroup> staffGroupsForCommission = userGroupService.getUserGroupByTypeAndModule(CommonConstant.USER_GROUP_TYPE_STAFF,
                CommonConstant.USER_GROUP_MODULE_COMMISSION, WebThreadLocal.getCompany().getId());
        model.addAttribute("staffGroupsForCommission", staffGroupsForCommission);
        model.addAttribute("currentStaffGroup", currentStaffGroup);
        ///tab2

        //tab3
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PayrollTemplate.class);
        List<PayrollTemplate> payrollTemplateList = payrollTemplateService.getActiveListByRefAndCompany(detachedCriteria, null, WebThreadLocal.getCompany().getId());
        model.addAttribute("payrollTemplateList", payrollTemplateList);

        Long payrollTemplateIdHidden = null;
        if (staff.getPayrollTemplate() != null) {
            payrollTemplateIdHidden = staff.getPayrollTemplate().getId();
        } else {
            if (payrollTemplateList != null && payrollTemplateList.size() > 0) {
                payrollTemplateIdHidden = payrollTemplateList.get(0).getId();
            }
        }
        model.addAttribute("payrollTemplateIdHidden", payrollTemplateIdHidden);
        return "staff/staffEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm editStaff(@Valid StaffVO staffVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            User staff = userService.get(Long.valueOf(staffVO.getId()));
            AjaxForm errorAjaxForm = AjaxFormHelper.error();
            if (!staffVO.getPin().equals(staff.getPin())) {
                DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
                criteria.add(Restrictions.eq("enabled", true));
                criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
                criteria.add(Restrictions.eq("pin", staffVO.getPin()));
                List<User> userListWithSamePin = userService.list(criteria);
                if (userListWithSamePin != null && 0 < userListWithSamePin.size()) {
                    errorAjaxForm.addErrorFields(new ErrorField("PIN", I18nUtil.getMessageKey("label.clock.pin.fail1")));
                }
            }
            if(StringUtils.isNotBlank(staffVO.getBarCode())){
                if(!staffVO.getBarCode().equals(staff.getBarcode())){
                    DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
                    criteria.add(Restrictions.eq("enabled", true));
                    criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));
                    criteria.add(Restrictions.eq("barcode", staffVO.getBarCode()));
                    List<User> userListWithSamePin = userService.list(criteria);
                    if (userListWithSamePin != null && 0 < userListWithSamePin.size()) {
                        errorAjaxForm.addErrorFields(new ErrorField("staff number", I18nUtil.getMessageKey("label.clock.barcode.fail1")));
                    }
                }
            }
            //check email
            if (!staff.getEmail().equals(staffVO.getEmail())) {
                User staffCheckByEmail = userService.getUserByEmail(staffVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_STAFF);
                if (staffCheckByEmail != null) {
                    errorAjaxForm.addErrorFields(new ErrorField("email", I18nUtil.getMessageKey("label.errors.duplicate")));
                }
            }
            //check tel#
            if (StringUtils.isNotBlank(staffVO.getMobilePhone()) && !NumberUtil.isNumeric(staffVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.phone.should.be.number")));
            }
            if (!errorAjaxForm.getErrorFields().isEmpty()) {
                return errorAjaxForm;

            } else {
                userService.saveOrUpdateStaff(staffVO);

                return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
            }
        }
    }

    @RequestMapping("toGroupView")
    public String toGroupView(UserGroupListVO userGroupListVO, Model model) {
        model.addAttribute("moduleList", CommonConstant.USER_GROUP_MODULE_LIST1);
        return "staff/staffGroupManagement";
    }

    @RequestMapping("groupList")
    public String groupList(Model model, UserGroupListVO userGroupListVO) {
        String name = userGroupListVO.getName();
        String isActive = userGroupListVO.getIsActive();
//	        String type=userGroupListVO.getType();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserGroup.class);
        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        if (WebThreadLocal.getCompany() != null) {
            detachedCriteria.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        }
//	        if (StringUtils.isNotBlank(type)) {
        detachedCriteria.add(Restrictions.eq("type", CommonConstant.USER_GROUP_TYPE_STAFF));
//	        }
        //page start
        Page<UserGroup> page = userGroupService.list(detachedCriteria, userGroupListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(userGroupListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "staff/staffGroupList";
    }

    @RequestMapping("toGroupAdd")
    public String toGroupAdd(UserGroupVO userGroupVO, Model model) {
        model.addAttribute("moduleList", CommonConstant.USER_GROUP_MODULE_LIST1);
        return "staff/staffGroupAdd";
    }

    @RequestMapping("groupAdd")
    @ResponseBody
    public AjaxForm groupAdd(@Valid UserGroupVO userGroupVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Company company = WebThreadLocal.getCompany();
            userGroupVO.setCompany(company);
            userGroupVO.setType(CommonConstant.USER_GROUP_TYPE_STAFF);
            userGroupService.saveOrUpdate(userGroupVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    @RequestMapping("toGroupEdit")
    public String toGroupEdit(UserGroupVO userGroupVO, Model model) {
        UserGroup userGroup = userGroupService.get(userGroupVO.getId());
        userGroupVO.setName(userGroup.getName());
        userGroupVO.setRemarks(userGroup.getRemarks());
//	        userGroupVO.setType(userGroup.getType());
        userGroupVO.setModule(userGroup.getModule());

//	        userGroupVO.setIsActive(userGroup.isIsActive());
        userGroupVO.setUserIds(userGroup.getUsers().stream().map(User::getId).toArray(Long[]::new));
        model.addAttribute("userGroup", userGroup);
        model.addAttribute("typeList", CommonConstant.USER_GROUP_TYPE_LIST);
        model.addAttribute("moduleList", CommonConstant.USER_GROUP_MODULE_LIST1);

        return "staff/staffGroupEdit";
    }

    @RequestMapping("groupEdit")
    @ResponseBody
    public AjaxForm groupEdit(@Valid UserGroupVO userGroupVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            userGroupVO.setType(CommonConstant.USER_GROUP_TYPE_STAFF);
            userGroupService.saveOrUpdate(userGroupVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        }
    }

    @RequestMapping("groupRemove")
    @ResponseBody
    public AjaxForm groupRemove(UserGroupVO userGroupVO) {
        UserGroup ug = userGroupService.get(userGroupVO.getId());
        if (ug != null) {
            try {
                ug.setIsActive(false);
                userGroupService.saveOrUpdate(ug);

            } catch (Exception e) {
                e.printStackTrace();
                return AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
            }
        } else {
            return AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }

    @RequestMapping("staffAddPayrollAttribute")
    public String staffAddPayrollAttribute(Model model, StaffVO staffVO) {
        PayrollTemplate payrollTemplate = payrollTemplateService.get(staffVO.getPayrollTemplateId());

        model.addAttribute("staffVO", staffVO);
        StaffPayrollAttributeVO[] attributeVOs = payrollTemplate.getPayrollAttributeKeys().stream().map(attribute -> {
            StaffPayrollAttributeVO attributeVO = new StaffPayrollAttributeVO();
            attributeVO.setPayrollAttributeKeyId(attribute.getId());
            attributeVO.setReference(attribute.getReference());
            attributeVO.setName(attribute.getName());
            attributeVO.setDescription(attribute.getDescription());
            attributeVO.setPayrollAttributeKeyId(attribute.getId());
            return attributeVO;
        }).toArray(size -> new StaffPayrollAttributeVO[size]);

        staffVO.setStaffPayrollAttributeVOs(attributeVOs);

        if (staffVO.getId() != null) {
            User user = userService.get(staffVO.getId());
            Set<StaffPayrollAttribute> staffPayrollAttributeSet = user.getStaffPayrollAttributes();
            for (StaffPayrollAttributeVO attributeVO : attributeVOs) {
                for (StaffPayrollAttribute attribute : staffPayrollAttributeSet) {
                    if (attribute.getPayrollAttributeKey().getId().equals(attributeVO.getPayrollAttributeKeyId())) {
                        attributeVO.setValue(attribute.getValue());
                        break;
                    }
                }
            }
        }
        return "staff/staffAddPayrollAttribute";
    }

    @RequestMapping("staffSelectList")
    public String staffSelectList(Long currentMemberId,Long shopId, Long initialValue, Boolean showAll, Model model,String preferreTherapist_0,
    		String preferreTherapist_1,String preferreTherapist_2) {

        DetachedCriteria dc = DetachedCriteria.forClass(User.class);
        dc.add(Restrictions.eq("enabled", true));
        dc.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        dc.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));

        if (shopId != null && shopId.longValue() > 0) {
            dc.createAlias("staffHomeShops", "shop");
            dc.add(Restrictions.eq("shop.id", shopId));
        }
        List<User> staffList = userService.list(dc);

        if(Objects.nonNull(currentMemberId)) {
       	 User currentMember = userService.get(currentMemberId);
       	 if(StringUtils.isNoneBlank(preferreTherapist_0) && Objects.nonNull(currentMember.getUser())) {
       		 initialValue = currentMember.getUser().getId();
       	 }else if (StringUtils.isNoneBlank(preferreTherapist_1) && Objects.nonNull(currentMember.getTherapist2())) {
       		 initialValue = currentMember.getTherapist2().getId();
			}else if(StringUtils.isNoneBlank(preferreTherapist_2) && Objects.nonNull(currentMember.getTherapist3())) {
				initialValue = currentMember.getTherapist3().getId();
			}
       }
        
        model.addAttribute("staffList", staffList);
        model.addAttribute("initialValue", initialValue);
        model.addAttribute("showAll", showAll);

        return "staff/staffSelectList";
    }

    @RequestMapping("therapistSelectList")
    public String therapistSelectList(Long currentMemberId,Long shopId, Long initialValue, Boolean showAll, Model model,String preferreTherapist_0,
    		String preferreTherapist_1,String preferreTherapist_2) {
        DetachedCriteria dc = DetachedCriteria.forClass(User.class);
        dc.add(Restrictions.eq("enabled", true));
        dc.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        dc.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_STAFF));

        if (shopId != null && shopId.longValue() > 0) {
            dc.createAlias("staffHomeShops", "shop");
            dc.add(Restrictions.eq("shop.id", shopId));
            dc.createAlias("sysRoles", "sysRole");
            dc.add(Restrictions.eq("sysRole.reference", "THERAPIST"));
        }
        if(Objects.nonNull(currentMemberId)) {
        	 User currentMember = userService.get(currentMemberId);
        	 if(StringUtils.isNoneBlank(preferreTherapist_0) && Objects.nonNull(currentMember.getUser())) {
        		 initialValue = currentMember.getUser().getId();
        	 }else if (StringUtils.isNoneBlank(preferreTherapist_1) && Objects.nonNull(currentMember.getTherapist2())) {
        		 initialValue = currentMember.getTherapist2().getId();
			}else if(StringUtils.isNoneBlank(preferreTherapist_2) && Objects.nonNull(currentMember.getTherapist3())) {
				initialValue = currentMember.getTherapist3().getId();
			}
        }
        List<User> staffList = userService.list(dc);
        model.addAttribute("staffList", staffList);
        model.addAttribute("initialValue", initialValue);
        model.addAttribute("showAll", showAll);

        return "staff/staffSelectList";
    }


    @RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
        User user = userService.get(id);
        if (user != null) {
            try {
                staffHomeShopDetailsService.init(id);
                user.setEnabled(false);
                userService.save(user);
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxFormHelper.error("Errors happened!");
            }
        } else {
            return AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success("success");
    }

    @RequestMapping("enable")
    @ResponseBody
    public AjaxForm enable(Long id) {
        User user = userService.get(id);
        if (user != null) {
            try {
                user.setEnabled(true);
                userService.save(user);
            } catch (Exception e) {
                e.printStackTrace();
                return AjaxFormHelper.error("Errors happened!");
            }
        } else {
            return AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success("success");
    }

    @RequestMapping("toSchedule")
    public String toSchedule(Long id, Model model) {
        User staff = userService.get(id);
        model.addAttribute("staff", staff);
        List<SelectOptionVO> selectOptionVOList = new ArrayList<>();

        DateTime dateTime = new DateTime();
        DateTime currentWeekStart = dateTime.withTimeAtStartOfDay().minusDays(dateTime.getDayOfWeek() - 1); // 回到周一
        dateTime = currentWeekStart.minusDays(42);// 前42天前的周一

        // 显示15周
        for (int i = 0; i < 15; i++) {
            DateTime weekend = dateTime.plusDays(6).millisOfDay().withMaximumValue().withMillisOfSecond(0);
            SelectOptionVO optionVO = new SelectOptionVO(dateTime.getMillis(), dateTime.toString("yyyy-MM-dd") + " ~ " + weekend.toString("yyyy-MM-dd"));
            optionVO.setSelected(currentWeekStart.equals(dateTime));
            selectOptionVOList.add(optionVO);
            dateTime = dateTime.plusDays(7); // 下一周的周一
        }

        model.addAttribute("weeks", selectOptionVOList);
        return "staff/staffSchedule";
    }

    @RequestMapping("toServiceSettings")
    public String toServiceSettings(Long staffId, Model model) {
        User staff = userService.get(staffId);
        model.addAttribute("staff", staff);
        return "staff/serviceSettings";
    }

    @RequestMapping("serviceSettingsConfirm")
    public String serviceSettingsConfirm(StaffServiceSettingsVO staffServiceSettingsVO, Model model) {
        List<Category> cList = new ArrayList<Category>();
        List<Product> pList = new ArrayList<Product>();
        User staff = userService.get(staffServiceSettingsVO.getId());
        staff.getStaffTreatmentses().clear();
        Set<StaffTreatments> staffTreatmentsSet = staff.getStaffTreatmentses();
        Long[] categoryIds = staffServiceSettingsVO.getCategoryIds();
        for (Long categoryID : categoryIds) {
            if (staffTreatmentsSet.stream().filter(e -> e.getCategory() != null && e.getCategory().getId().equals(categoryID)).count() > 0) {
                // 已经包含该技能
                continue;
            }
            Category category = categoryService.get(categoryID);
            if (!category.isIsActive()) {
                continue;
            }
            StaffTreatments staffTreatments = new StaffTreatments();
            staffTreatments.setCategory(category);
            staffTreatments.setStaff(staff);
            staffTreatmentsSet.add(staffTreatments);
            cList.add(category);
            if (category.getCategories().size() > 0) {
                saveTreatment(category.getCategories(), staff, staffTreatmentsSet, cList, pList);
            } else if (category.getProducts().size() > 0) {
                setStaffProduct(category.getProducts().stream().map(Product::getId).toArray(Long[]::new), staff, pList);
            }
        }
        setStaffProduct(staffServiceSettingsVO.getProductIds(), staff, pList);

        model.addAttribute("staff", staff);
        model.addAttribute("cList", cList);
        model.addAttribute("pList", pList);
        model.addAttribute("productIds", staffServiceSettingsVO.getProductIds());
        model.addAttribute("categoryIds", staffServiceSettingsVO.getCategoryIds());
        return "staff/serviceSettingsConfirm";
    }

    private void saveTreatment(Set<Category> categories, User staff, Set<StaffTreatments> staffTreatmentsSet, List<Category> cList, List<Product> pList) {

        for (Category category : categories) {
            if (!category.isIsActive()) {
                continue;
            }
            StaffTreatments staffTreatments = new StaffTreatments();
            staffTreatments.setCategory(category);
            cList.add(category);
            staffTreatments.setStaff(staff);
            staffTreatmentsSet.add(staffTreatments);
            if (category.getCategories().size() > 0) {
                saveTreatment(category.getCategories(), staff, staffTreatmentsSet, cList, pList);
            } else if (category.getProducts().size() > 0) {
                setStaffProduct(category.getProducts().stream().map(Product::getId).toArray(Long[]::new), staff, pList);
            }
        }
    }

    private void setStaffProduct(Long[] productId, User staff, List<Product> pList) {
        if (productId == null || productId.length == 0) {
            return;
        }
        Set<StaffTreatments> staffTreatmentsSet = staff.getStaffTreatmentses();
        for (Long productID : productId) {
            if (staffTreatmentsSet.stream().filter(e -> e.getProduct() != null && e.getProduct().getId().equals(productID)).count() > 0) {
                // 已经包含该技能
                continue;
            }
            Product product = productService.get(productID);
            if (!product.isIsActive()) {
                continue;
            }
            StaffTreatments staffTreatments = new StaffTreatments();
            staffTreatments.setProduct(product);
            staffTreatments.setStaff(staff);
            staffTreatmentsSet.add(staffTreatments);
            pList.add(product);
        }
    }

    @RequestMapping("saveServiceSettings")
    @ResponseBody
    public AjaxForm saveServiceSettings(StaffServiceSettingsVO staffServiceSettingsVO) {

        userService.saveServiceSettingsForStaff(staffServiceSettingsVO);
        return AjaxFormHelper.success("success");
    }

    @RequestMapping("toScheduleWeek")
    public String toScheduleWeek(Long shopId, Long staffId, Long millisWeekStart, Model model) {
        DateTime weekStart;
        DateTime weekEnd;
        if (millisWeekStart == null) {
            DateTime dateTime = new DateTime();
            dateTime = dateTime.withTimeAtStartOfDay();
            weekStart = dateTime.minusDays(dateTime.getDayOfWeek() - 1); // 回到周一
            weekEnd = weekStart.plusDays(6).millisOfDay().withMaximumValue().withMillisOfSecond(0);
        } else {
            weekStart = new DateTime(millisWeekStart).withTimeAtStartOfDay();
            weekEnd = weekStart.plusDays(6).millisOfDay().withMaximumValue().withMillisOfSecond(0);
        }

        ScheduleWeekVO scheduleWeekVO = blockService.listSchedule(shopId, staffId, weekStart.toDate(), weekEnd.toDate());
        model.addAttribute("scheduleWeekVO", scheduleWeekVO);
        return "staff/staffScheduleWeek";
    }

    @RequestMapping("schedule")
    @ResponseBody
    public AjaxForm schedule(ScheduleWeekVO scheduleWeekVO) {
        if (scheduleWeekVO.getStaffId() == null || scheduleWeekVO.getShopId() == null
                || scheduleWeekVO.getWeekStartMillis() == null || scheduleWeekVO.getScheduleDayVOList().size() != 7) {
            logger.error("Parameter Error!");
            AjaxFormHelper.error().addAlertError("Parameter Error");
        }
        DateTime start = new DateTime(scheduleWeekVO.getWeekStartMillis());
        scheduleWeekVO.setStartDate(start.toDate());
        scheduleWeekVO.setEndDate(start.plusDays(6).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate());

        // 之前的数据都不可以修改
        if (scheduleWeekVO.getStartDate().before(new Date())) {
            return AjaxFormHelper.error().addAlertError("Schedule has been past cannot updated!");
        }
        StringBuilder errors = new StringBuilder();

        List<ScheduleDayVO> scheduleDayVOList = scheduleWeekVO.getScheduleDayVOList();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        for (ScheduleDayVO scheduleDayVO : scheduleDayVOList) {
            if (CommonConstant.WORKDAY.equals(scheduleDayVO.getType())) {
                // 检查日期是否正确
                ScheduleTimeVO workHours = scheduleDayVO.getScheduleTimeVOMap().get(CommonConstant.WORK_HOURS);
                if (workHours == null || StringUtils.isBlank(workHours.getStartTime())
                        || StringUtils.isBlank(workHours.getEndTime())) {
                    errors.append(scheduleDayVO.getName()).append(" work hours required!<br>");
                } else if (!formatter.parseDateTime(workHours.getStartTime()).isBefore(formatter.parseDateTime(workHours.getEndTime()))) {
                    errors.append(scheduleDayVO.getName()).append("work hours start time must before end time!<br>");
                }

                ScheduleTimeVO lunch = scheduleDayVO.getScheduleTimeVOMap().get(CommonConstant.LUNCH);
                boolean hasStart = StringUtils.isNotBlank(lunch.getStartTime());
                boolean hasEnd = StringUtils.isNotBlank(lunch.getEndTime());
                if (hasStart && hasEnd) {
                    if (!formatter.parseDateTime(lunch.getStartTime()).isBefore(formatter.parseDateTime(lunch.getEndTime()))) {
                        errors.append(scheduleDayVO.getName()).append(" lunch start time must before end time!<br>");
                    }
                } else if (hasStart) {
                    errors.append(scheduleDayVO.getName()).append("lunch end time required!<br>");
                } else if (hasEnd) {
                    errors.append(scheduleDayVO.getName()).append("lunch start time required!<br>");
                }
            }
        }

        if (errors.length() > 0) {
            return AjaxFormHelper.error().addAlertError(errors.toString());
        } else {
            // 保存
            String res = blockService.saveSchedule(scheduleWeekVO);
            if (StringUtils.isBlank(res)) {
                return AjaxFormHelper.success(I18nUtil.getMessageKey("label.save.successfully"));
            } else {
                return AjaxFormHelper.error().addAlertError(res);

            }
        }
    }

    /**
     * ==============================================以下控制器 created by rick============================================================================
     */
    @RequestMapping(value = "toClockView")
    public String shopManagement(Model model) {
        StaffClockListVo staffClockListVo = new StaffClockListVo();
        model.addAttribute("toDate",new Date());
        model.addAttribute("fromDate",new Date());
        model.addAttribute("staffClockListVo", staffClockListVo);
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true, true));
        return "staff/staffClockManagement";
    }

    /**
     * created by rick 2018.8.29
     * <p>
     * 打卡记录
     *
     * @return
     */
    @RequestMapping(value = "staffClockList")
    public String staffClockList(Model model, StaffClockListVo staff) {
        Page<StaffInOrOut> page = new Page();
        StaffInOrOut staffInOrOut = new StaffInOrOut();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StaffInOrOut.class);
        if (staff.getType() != -1) {
            detachedCriteria.add(Restrictions.eq("type", staff.getType()));
        }
        if (StringUtils.isNotBlank(staff.getBarcode())) {
            User user = userService.get("barcode", staff.getBarcode());
            if (user == null) {
                model.addAttribute("page", page);
                try {
                    model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(page), "utf-8"));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return "staff/staffClockList";
            }
            detachedCriteria.add(Restrictions.eq("staffId", user.getId()));

        }

        //from date
        if (StringUtils.isNotBlank(staff.getFromDate())) {
            detachedCriteria.add(Restrictions.ge("dateTime",
                    DateUtil.stringToDate(staff.getFromDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss")));
        }
        //to date
        if (StringUtils.isNotBlank(staff.getToDate())) {
            detachedCriteria.add(Restrictions.le("dateTime",
                    DateUtil.stringToDate(staff.getToDate() + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
        }

        //如何员工为则查询全部的id
        if (staff.getShopId() != null && staff.getStaffId() == null) {
            detachedCriteria.add(Restrictions.eq("shopId", staff.getShopId()));
        }
        //查询某一个人的打卡记录
        if (staff.getShopId() != null && staff.getStaffId() != null || staff.getShopId() == null && staff.getStaffId() != null) {
            detachedCriteria.add(Restrictions.eq("staffId", staff.getStaffId()));
        }
        //查询所有人的打卡记录
        page = staffInOrOutService.list(detachedCriteria, staffInOrOut.getPageNumber(), staffInOrOut.getPageSize());
        model.addAttribute("page", page);

        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(page), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "staff/staffClockList";
    }

    @RequestMapping(value = "toAddClock")
    public String toAddClock(Model model, StaffInOrOutVo staffInOrOutVo) {
        User user = userService.get(WebThreadLocal.getUser().getId());
        model.addAttribute("staffInOrOutVo", staffInOrOutVo);
        model.addAttribute("user", user);
        return "staff/staffClockAdd";
    }

    /**
     * created by rick 2018.8.29
     * 上下班打卡
     *
     * @return
     */
    @RequestMapping(value = "clockInOrOut")
    @ResponseBody
    public AjaxForm clockInOrOut(StaffInOrOutVo staffInOrOutVo) throws Exception {

        //默认失败的验证码
        AjaxForm error = AjaxFormHelper.error();
        if(StringUtils.isBlank(staffInOrOutVo.getPin())){
            return error.addErrorFields(new ErrorField("PIN", I18nUtil.getMessageKey("label.clock.pin.empty")));
        }
        boolean matches = staffInOrOutVo.getPin().matches("\\d{4}");
        if(!matches){
            return error.addErrorFields(new ErrorField("PIN", I18nUtil.getMessageKey("label.clock.pin.empty")));
        }
        if(StringUtils.isBlank(staffInOrOutVo.getStaffNumber())){
            return error.addErrorFields(new ErrorField("Staff Number", I18nUtil.getMessageKey("label.clock.barcode.fail2")));
        }
        Object[] clock = userService.clock(staffInOrOutVo);
        if ((int) clock[0] == 0) {
            error.addErrorFields(new ErrorField("Staff Number", I18nUtil.getMessageKey("label.clock.barcode.fail")));
            return error;
        } else if ((int) clock[0] == 1) {
            error.addErrorFields(new ErrorField("PIN", I18nUtil.getMessageKey("label.clock.pin.fail")));
            return error;
        } else {
            StaffInOrOut staffInOrOuts = (StaffInOrOut) clock[1];
            if (staffInOrOuts.getType() == 0) {
                String returnMsg = I18nUtil.getMessageKey("label.clock.success1") + " " + staffInOrOuts.getName() + ", your Clock In " + I18nUtil.getMessageKey("label.clock.success2");
                if(StringUtils.isNotBlank(staffInOrOuts.getWarningMsg())){
                    returnMsg +=" </br><font color='red'>"+I18nUtil.getMessageKey("label.warning.msg")+staffInOrOuts.getWarningMsg()+"</font>";
                }
                return AjaxFormHelper.success(returnMsg);
            } else {
                return AjaxFormHelper.success(I18nUtil.getMessageKey("label.clock.success1") + " " + staffInOrOuts.getName() + ", your Clock Out " + I18nUtil.getMessageKey("label.clock.success2"));
            }
        }

    }

    @RequestMapping(value = "toEditClock")
    public String toEditClock(Long id, Model model) {
        StaffInOrOut staffInOrOut = staffInOrOutService.get(id);
        model.addAttribute("staffInOrOut", staffInOrOut);
        return "staff/staffClockEdit";
    }

    @RequestMapping(value = "editClock")
    @ResponseBody
    public AjaxForm toSaveClock(Long id, String dateTime, Long type) throws ParseException {
        //默认是错误的返回代码
        AjaxForm ajaxForm = AjaxFormHelper.error();

        int i = userService.editClock(id, dateTime, type);
        if (i == 0) {
            return ajaxForm.addErrorFields(new ErrorField("date", I18nUtil.getMessageKey("label.errors.is.not.valid")));
        } else if (i == 1) {
            if (type == 0) {
                return ajaxForm.addErrorFields(new ErrorField("type", I18nUtil.getMessageKey("label.errors.clock.in")));
            } else {
                return ajaxForm.addErrorFields(new ErrorField("type", I18nUtil.getMessageKey("label.errors.clock.out")));
            }
        }

        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
    }

    @RequestMapping(value = "deleteClock")
    @ResponseBody
    public AjaxForm toDeleteClock(Long id) {
        if (id == null) {
            return AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.is.required"));
        }
        StaffInOrOut staffInOrOut = staffInOrOutService.get(id);
        if (staffInOrOut == null) {
            return AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        staffInOrOutService.delete(id);
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.delete.successfully"));
    }

    @RequestMapping(value = "export")
    public void export(HttpServletResponse response, StaffClockListVo staff) {
        Context context = new Context();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StaffInOrOut.class);
        if (staff.getType() != -1) {
            detachedCriteria.add(Restrictions.eq("type", staff.getType()));
            if(staff.getType() == 0){
                context.putVar("clockType","Clock Type: Clock In");
            }else if(staff.getType() == 1){
                context.putVar("clockType","Clock Type: Clock Out");
            }
        }else {
            context.putVar("clockType","Clock Type: ALL");
        }
        if (StringUtils.isNotBlank(staff.getBarcode())) {
            User user = userService.get("barcode", staff.getBarcode());
            if (user == null) {
                detachedCriteria.add(Restrictions.eq("staffId", 0l));
            } else {
                detachedCriteria.add(Restrictions.eq("staffId", user.getId()));
                context.putVar("staffNumber","Staff Number: "+staff.getBarcode());
            }
        }else{
            context.putVar("staffNumber","Staff Number: ");
        }

        //from date
        if (StringUtils.isNotBlank(staff.getFromDate())) {
            detachedCriteria.add(Restrictions.ge("dateTime",
                    DateUtil.stringToDate(staff.getFromDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss")));
            context.putVar("from","From: "+staff.getFromDate());
        }
        //to date
        if (StringUtils.isNotBlank(staff.getToDate())) {
            detachedCriteria.add(Restrictions.le("dateTime",
                    DateUtil.stringToDate(staff.getToDate() + " 23:59:59", "yyyy-MM-dd HH:mm:ss")));
            context.putVar("to","To: "+staff.getToDate());
        }

        if(staff.getShopId() != null && staff.getShopId() > 0){
            Shop shop = shopService.get(staff.getShopId());
            context.putVar("shopName","Shop Name: "+shop.getName());
        }else {
            context.putVar("shopName","Shop Name: ");
        }

        //如果员工为空则查询全部的id
        if (staff.getShopId() != null && staff.getStaffId() == null) {
            detachedCriteria.add(Restrictions.eq("shopId", staff.getShopId()));

        }
        //查询某一个人的打卡记录
        if ((staff.getShopId() != null && staff.getStaffId() != null) || (staff.getShopId() == null && staff.getStaffId() != null)) {
            detachedCriteria.add(Restrictions.eq("staffId", staff.getStaffId()));
        }
        List<StaffInOrOut> list = staffInOrOutService.list(detachedCriteria);
        List<Map> maps = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (StaffInOrOut staffInOrOut : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("date", staffInOrOut.getDate());
                if (staffInOrOut.getType() == 0) {
                    map.put("type", "Clock In");
                } else {
                    map.put("type", "Clock Out");
                }
                if(staffInOrOut.getAddress() != null){
                    map.put("address",staffInOrOut.getAddress().getAddressExtention());
                }else{
                    map.put("address","");
                }
                map.put("createBy",staffInOrOut.getCreatedBy());
                String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(staffInOrOut.getDateTime());
                map.put("createTime",createTime);
                map.put("shopName", staffInOrOut.getShopName());
                map.put("name", staffInOrOut.getName());
                if (staffInOrOut.getDuration() != null) {
                    map.put("duration", staffInOrOut.getDuration());
                }
                if (staffInOrOut.getOt() != null) {
                    map.put("duration", staffInOrOut.getOt());
                }
                maps.add(map);
            }

        }
        context.putVar("clockList", maps);
        File downloadFile = ExcelUtil.write("clockRecordExportTemplate.xls", context);
        ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Attendance-Records-Export-") + ".xls", response);
    }
}
/**
 * 以上
 */