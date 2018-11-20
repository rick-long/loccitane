package com.spa.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.spa.constant.CommonConstant;
import com.spa.controller.BaseController;
import com.spa.jxlsBean.ExcelUtil;
import com.spa.jxlsBean.MemberImportJxlsBean;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.*;
import org.jxls.common.Context;
import org.spa.model.book.BookItem;
import org.spa.model.company.Company;
import org.spa.model.loyalty.LoyaltyLevel;
import org.spa.model.loyalty.UserLoyaltyLevel;
import org.spa.model.order.PurchaseItem;
import org.spa.model.order.PurchaseOrder;
import org.spa.model.points.LoyaltyPointsTransaction;
import org.spa.model.shop.Address;
import org.spa.model.shop.Phone;
import org.spa.model.shop.Shop;
import org.spa.model.user.*;
import org.spa.utils.*;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.ajax.ErrorField;
import org.spa.vo.common.SelectOptionVO;
import org.spa.vo.importDemo.ImportDemoVO;
import org.spa.vo.page.Page;
import org.spa.vo.sales.SpendingSummaryVO;
import org.spa.vo.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static com.spa.constant.CommonConstant.USER_GROUP_MODULE_DISCOUNT;
import static com.spa.constant.CommonConstant.USER_GROUP_MODULE_MARKETING;

/**
 * Created by Ivy on 2016/01/16.
 */
@Controller
@RequestMapping("member")
public class MemberController extends BaseController {
	
	
	@RequestMapping("toView")
	public String memberManagement(Model model) {
		MemberListVO memberListVO = new MemberListVO();
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        
        DetachedCriteria lldc = DetachedCriteria.forClass(LoyaltyLevel.class);
        model.addAttribute("loyaltyLevelList", loyaltyLevelService.getActiveListByRefAndCompany(lldc, null,null));
        
        List<UserGroup> userGroups = userGroupService.getUserGroupByFilters(null,CommonConstant.USER_GROUP_TYPE_MEMBER, null, WebThreadLocal.getCompany().getId());
		model.addAttribute("userGroupList", userGroups);
		
        DetachedCriteria ccdc = DetachedCriteria.forClass(CommunicationChannel.class);
        model.addAttribute("communicationChannelList", communicationChannelService.getActiveListByRefAndCompany(ccdc, null, WebThreadLocal.getCompany().getId()));
        model.addAttribute("memberListVO",memberListVO);
		return "member/memberManagement";
	}
	
	@RequestMapping("list")
	public String memberList(Model model, MemberListVO memberListVO) {

		String username=memberListVO.getUsername();
		String email=memberListVO.getEmail();
		String enabled=memberListVO.getEnabled();
		Long shopId=memberListVO.getShopId();
		Long loyaltyLevelId=memberListVO.getLoyaltyLevelId();
		Long userGroupId =memberListVO.getUserGroupId();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
		
		if (StringUtils.isNotBlank(username)) {
			detachedCriteria.createAlias("phones", "phone");
			Disjunction disjunction = Restrictions.disjunction(); 
			disjunction.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("firstName", username, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("phone.number", username, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("lastName", username, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("fullName", username, MatchMode.ANYWHERE));
			detachedCriteria.add(disjunction);
		}
		if (StringUtils.isNotBlank(email)) {
			detachedCriteria.add(Restrictions.like("email", email, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(enabled)) {
			detachedCriteria.add(Restrictions.eq("enabled", Boolean.parseBoolean(enabled)));
		}
		
		if (shopId !=null) {
			detachedCriteria.add(Restrictions.eq("shop.id",shopId));
		}
		if (userGroupId !=null) {
			detachedCriteria.createAlias("userGroups", "ug");
			detachedCriteria.add(Restrictions.eq("ug.id",userGroupId));
		}
		if (loyaltyLevelId !=null) {
			detachedCriteria.createAlias("userLoyaltyLevels", "ull");
			detachedCriteria.add(Restrictions.eq("ull.isActive",true));
			detachedCriteria.add(Restrictions.eq("ull.loyaltyLevel.id",loyaltyLevelId));
		}
		detachedCriteria.add(Restrictions.eq("accountType",CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
		detachedCriteria.addOrder(Order.desc("created"));
		//page start
		Page<User> memberPage = userService.list(detachedCriteria, memberListVO.getPageNumber(),memberListVO.getPageSize());
		model.addAttribute("page", memberPage);
		
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(memberListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "member/memberList";
	}

    @RequestMapping("memberSelectOptions")
    public String memberSelectOptions(Long shopId, Model model) {
        List<User> memberList = userService.getMemberListByShop(shopId, null);
        model.addAttribute("memberList", memberList);
        return "member/memberSelectOptions";
    }

    @RequestMapping("getMembers")
    @ResponseBody
    public List<MemberVO> getMembers(Long shopId) {
        List<User> users = userService.getMemberListByShop(shopId, null);
        List<MemberVO> memberVOList = new ArrayList<>();
        for (User user : users) {
            MemberVO memberVO = new MemberVO();
            BeanUtils.copyProperties(user, memberVO);
            memberVOList.add(memberVO);
        }
        return memberVOList;
    }

    @RequestMapping("toAdd")
	public String toAddMember(MemberAddVO memberAddVO,Model model) {
		List<Shop> shopList=shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false);
		model.addAttribute("shopList", shopList);
		
		model.addAttribute("now", new Date());
		memberAddVO.setGender("FEMALE");
		
		List<UserGroup> userGroups = userGroupService.getUserGroupByFilters(null,CommonConstant.USER_GROUP_TYPE_MEMBER, USER_GROUP_MODULE_MARKETING, WebThreadLocal.getCompany().getId());
		model.addAttribute("userGroups", userGroups);

		List<UserGroup> userGroups1 = userGroupService.getUserGroupByFilters(null,CommonConstant.USER_GROUP_TYPE_MEMBER, USER_GROUP_MODULE_DISCOUNT, WebThreadLocal.getCompany().getId());
		model.addAttribute("userGroups1", userGroups1);
		 
		DetachedCriteria ccdc = DetachedCriteria.forClass(CommunicationChannel.class);
        model.addAttribute("communicationChannelList", communicationChannelService.getActiveListByRefAndCompany(ccdc, null, WebThreadLocal.getCompany().getId()));

        /* create by william -- 2018-8-14 */
		model.addAttribute("preferredTherapistCount", PropertiesUtil.getValueByName("PREFERRED_THERAPIST_QTY"));

		List<User> preferredTherapistList=userService.getAvalibleUsersByAccountTypeAndRoleRef(CommonConstant.USER_ACCOUNT_TYPE_STAFF, CommonConstant.STAFF_ROLE_REF_THERAPIST);
		model.addAttribute("preferredTherapistList", preferredTherapistList);

		model.addAttribute("preferredShopList", shopList);

		model.addAttribute("districtData", CommonConstant.DISTRICT_DATA);
		return "member/memberAdd";
	}
    
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addMember(@Valid MemberAddVO memberAddVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			//check email
			User memberCheckByEmail=userService.getUserByEmail(memberAddVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
			if(memberCheckByEmail !=null){
				errorAjaxForm.addErrorFields(new ErrorField("email", I18nUtil.getMessageKey("label.errors.duplicate")));
			}
			//check tel#
            if (StringUtils.isBlank(memberAddVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.home.error")));
            } else if (!NumberUtil.isNumeric(memberAddVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.phone.should.be.number")));
            } else if(userService.isMobileUsed(WebThreadLocal.getCompany().getId(), memberAddVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.has.used")));
            }
            if(StringUtils.isBlank(memberAddVO.getAddressVO().getDistrict())){
                errorAjaxForm.addErrorFields(new ErrorField("addressVO.district", I18nUtil.getMessageKey("label.errors.address.district.required")));
            }

			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
				
			}else{
				userService.saveMember(memberAddVO);
				
				//email send to change password
				
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
			}
		}
		
	}
	@RequestMapping("toEdit")
	public String toEditMember(MemberEditVO memberEditVO,Model model) {
		
		List<Shop> shopList=shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false);
		model.addAttribute("shopList", shopList);

		model.addAttribute("preferredShopList", shopList);

		/* create by william -- 2018-8-14 */
		model.addAttribute("preferredTherapistCount", PropertiesUtil.getValueByName("PREFERRED_THERAPIST_QTY"));

		List<User> preferredTherapistList=userService.getAvalibleUsersByAccountTypeAndRoleRef(CommonConstant.USER_ACCOUNT_TYPE_STAFF, CommonConstant.STAFF_ROLE_REF_THERAPIST);
		model.addAttribute("preferredTherapistList", preferredTherapistList);

		DetachedCriteria ccdc = DetachedCriteria.forClass(CommunicationChannel.class);
        model.addAttribute("communicationChannelList", communicationChannelService.getActiveListByRefAndCompany(ccdc, null, WebThreadLocal.getCompany().getId()));

		List<UserGroup> userGroups = userGroupService.getUserGroupByFilters(null,CommonConstant.USER_GROUP_TYPE_MEMBER, USER_GROUP_MODULE_MARKETING, WebThreadLocal.getCompany().getId());
		model.addAttribute("userGroupsMarket", userGroups);

		List<UserGroup> userGroups1 = userGroupService.getUserGroupByFilters(null,CommonConstant.USER_GROUP_TYPE_MEMBER, USER_GROUP_MODULE_DISCOUNT, WebThreadLocal.getCompany().getId());
		model.addAttribute("userGroupsDiscount", userGroups1);
		
		User member=userService.get(Long.valueOf(memberEditVO.getId()));
		memberEditVO.setEmail(member.getEmail());
		memberEditVO.setEnabled(String.valueOf(member.isEnabled()));
		memberEditVO.setUsername(member.getUsername());
		memberEditVO.setFirstName(member.getFirstName());
		memberEditVO.setGender(member.getGender());
		memberEditVO.setLastName(member.getLastName());
		memberEditVO.setOptedOut(member.getOptedOut());
		/*if(member.getUser() != null){
			memberEditVO.setPreferredTherapistId1(member.getUser().getId().toString());
		}
		if(member.getTherapist2() != null){
			memberEditVO.setPreferredTherapistId2(member.getTherapist2().getId().toString());
		}
		if(member.getTherapist3() != null){
			memberEditVO.setPreferredTherapistId3(member.getTherapist3().getId().toString());
		}
		model.addAttribute("memberEditVO",memberEditVO);*/
		model.addAttribute("userGroups",member.getUserGroups());
		
		if(StringUtils.isNotBlank(member.getNotification())){
			String notifications=member.getNotification();
			String[] vals=notifications.split(",");
			memberEditVO.setNotification(Arrays.asList(vals));
		}
		
		memberEditVO.setPreferredContact(member.getPreferredContact());

		/* update by william -- 2018-8-14 */
		if(member.getUser() !=null){
			memberEditVO.setPreferredTherapistId1(member.getUser().getId().toString());
		}

		if (member.getTherapist2() != null) {
			/*memberEditVO.setPreferredTherapist2Id(member.getUser2().getId().toString());*/
			memberEditVO.setPreferredTherapistId2(member.getTherapist2().getId().toString());
		}

		if (member.getTherapist3() != null) {
			memberEditVO.setPreferredTherapistId3(member.getTherapist3().getId().toString());
		}

		if (member.getPreferredShop() != null) {
			memberEditVO.setPreferredShop(member.getPreferredShop().getId().toString());
		}

		if (member.getPreferredRoom() != null) {
			model.addAttribute("roomId", member.getPreferredRoom().getId().toString());
		} else {
			model.addAttribute("roomId", 0);
		}

		memberEditVO.setShopId(member.getShop().getId().toString());
		memberEditVO.setRemarks(member.getRemarks());
		
		if(member.getCommunicationChannels() !=null && member.getCommunicationChannels().size()>0){
			CommunicationChannel cc=member.getCommunicationChannels().iterator().next();
			memberEditVO.setCommunicationChannelId(cc.getId());
		}
		memberEditVO.setAirmilesMembershipNumber(member.getAirmilesMembershipNumber());
		
		//address
		if(member.getAddresses() !=null && member.getAddresses().size()>0){
			Address addr=member.getAddresses().iterator().next();
			AddressVO addressVO=new AddressVO();
			addressVO.setId(addr.getId());
			addressVO.setAddressExtention(addr.getAddressExtention());
			addressVO.setCity(addr.getCity());
			addressVO.setCountry(addr.getCountry());
            addressVO.setDistrict(addr.getDistrict());
			memberEditVO.setAddressVO(addressVO);
		}
		if(member.getPhones() !=null && member.getPhones().size()>0){
			Iterator<Phone> it=member.getPhones().iterator();
			while(it.hasNext()){
				Phone phone=it.next();
				if(phone.getType().equals(CommonConstant.PHONE_TYPE_HOME)){
					memberEditVO.setHomePhone(phone.getNumber());
				}else if(phone.getType().equals(CommonConstant.PHONE_TYPE_MOBILE)){
					memberEditVO.setMobilePhone(phone.getNumber());
				}
			}
		}
		model.addAttribute("currentMemberId", member.getId());
		model.addAttribute("dateOfBirth", member.getDateOfBirth());
        model.addAttribute("districtData", CommonConstant.DISTRICT_DATA);

		return "member/memberEdit";
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public AjaxForm editMember(@Valid MemberEditVO memberEditVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			User member=userService.get(Long.valueOf(memberEditVO.getId()));
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			

			//check email
			if(!member.getEmail().equals(memberEditVO.getEmail())){
				User memberCheckByEmail=userService.getUserByEmail(memberEditVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
				if(memberCheckByEmail !=null){
					errorAjaxForm.addErrorFields(new ErrorField("email", I18nUtil.getMessageKey("label.errors.duplicate")));
				}
			}
			//check mobile
            if (StringUtils.isBlank(memberEditVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.home.error")));
            } else if (!NumberUtil.isNumeric(memberEditVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.phone.should.be.number")));
            } else if(userService.isMobileUsed(WebThreadLocal.getCompany().getId(), memberEditVO.getMobilePhone(), memberEditVO.getId())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.has.used")));
            }
            if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
				
			}else{
				userService.updateMember(memberEditVO);
				
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
			}
		}
	}
	
	@RequestMapping("quicksearch")
	public String quicksearch(Model model, MemberListVO memberListVO) {
		
		return "member/quickSearchMember";
	}
	
	@RequestMapping("quicksearchWithEvent")
	public String quicksearchWithEvent(Model model, MemberListVO memberListVO) {
		
		return "member/quickSearchMemberWithEvent";
	}
	
	@RequestMapping("quicksearchlist")
	public String quicksearchlist(Model model, MemberListVO memberListVO) {

		String username=memberListVO.getUsername();
		if (StringUtils.isNotBlank(username)) {
			
			DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
			criteria.add(Restrictions.eq("enabled", true));
			criteria.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
			criteria.createAlias("phones", "phone");
			
//			3个or以上也可以这么实现		
			Disjunction disjunction = Restrictions.disjunction(); 
			disjunction.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("email", username, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("firstName", username, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("lastName", username, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("fullName", username, MatchMode.ANYWHERE));
			disjunction.add(Restrictions.like("phone.number", username, MatchMode.ANYWHERE));
			criteria.add(disjunction);
			
			//page start
			Page<User> memberPage = userService.list(criteria, memberListVO.getPageNumber(),memberListVO.getPageSize());
			if(memberPage ==null ||  (memberPage !=null && memberPage.getList().size() ==0)){
				DetachedCriteria criteria1 = DetachedCriteria.forClass(User.class);
				criteria1.add(Restrictions.eq("enabled", true));
				criteria1.add(Restrictions.eq("accountType", CommonConstant.USER_ACCOUNT_TYPE_MEMBER));
				
//				3个or以上也可以这么实现		
				Disjunction disjunction2 = Restrictions.disjunction(); 
				disjunction2.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
				disjunction2.add(Restrictions.like("email", username, MatchMode.ANYWHERE));
				disjunction2.add(Restrictions.like("firstName", username, MatchMode.ANYWHERE));
				disjunction2.add(Restrictions.like("lastName", username, MatchMode.ANYWHERE));
				disjunction2.add(Restrictions.like("fullName", username, MatchMode.ANYWHERE));
				criteria1.add(disjunction2);
				
				memberPage = userService.list(criteria1, memberListVO.getPageNumber(),memberListVO.getPageSize());
			}
			
			model.addAttribute("page", memberPage);
			
			try {
				model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(memberListVO),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		//page end
		return "member/quickSearchMemberList";
	}

	@RequestMapping("walkInGuest")
	@ResponseBody
	public AjaxForm walkInGuest() {
		AjaxForm parameters=AjaxFormHelper.error();
		User guest=userService.getUserByUsername(CommonConstant.USER_ACCOUNT_TYPE_GUEST, CommonConstant.USER_ACCOUNT_TYPE_GUEST);
		parameters.addErrorFields(new ErrorField("username", guest.getUsername()));
		parameters.addErrorFields(new ErrorField("id", guest.getId().toString()));
		return parameters;
	}
	
    @RequestMapping("toGroupView")
    public String toGroupView(UserGroupListVO userGroupListVO,Model model) {
    	model.addAttribute("moduleList", CommonConstant.USER_GROUP_MODULE_LIST);
        return "member/memberGroupManagement";
    }

    @RequestMapping("groupList")
    public String groupList(Model model, UserGroupListVO userGroupListVO) {
        String name = userGroupListVO.getName();
        String isActive = userGroupListVO.getIsActive();
//        String type=userGroupListVO.getType();
        
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
//        if (StringUtils.isNotBlank(type)) {
            detachedCriteria.add(Restrictions.eq("type",CommonConstant.USER_GROUP_TYPE_MEMBER));
//        }
        if (StringUtils.isNotBlank(userGroupListVO.getModule())) {
        	detachedCriteria.add(Restrictions.eq("module",userGroupListVO.getModule()));
        }
        //page start
        Page<UserGroup> page = userGroupService.list(detachedCriteria, userGroupListVO);
        model.addAttribute("page", page);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(userGroupListVO), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "member/memberGroupList";
    }

    @RequestMapping("groupUserList")
    public String groupUserList(Model model, Long userGroupId) {
        UserGroup userGroup = userGroupService.get(userGroupId);
        model.addAttribute("userGroup", userGroup);
        return "member/memberGroupUserList";
    }

    @RequestMapping("toGroupAdd")
    public String toGroupAdd(UserGroupVO userGroupVO,Model model) {
    	model.addAttribute("moduleList", CommonConstant.USER_GROUP_MODULE_LIST);
        return "member/memberGroupAdd";
    }

    @RequestMapping("groupAdd")
    @ResponseBody
    public AjaxForm groupAdd(@Valid UserGroupVO userGroupVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            Company company = WebThreadLocal.getCompany();
            userGroupVO.setCompany(company);
            userGroupVO.setType(CommonConstant.USER_GROUP_TYPE_MEMBER);
            userGroupService.saveOrUpdate(userGroupVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    @RequestMapping("toGroupEdit")
    public String toGroupEdit(UserGroupVO userGroupVO, Model model) {
        UserGroup userGroup = userGroupService.get(userGroupVO.getId());
        userGroupVO.setName(userGroup.getName());
        userGroupVO.setRemarks(userGroup.getRemarks());
//        userGroupVO.setType(userGroup.getType());
        userGroupVO.setModule(userGroup.getModule());
        userGroupVO.setActive(userGroup.isIsActive());
        model.addAttribute("userGroupVO",userGroupVO);
//        userGroupVO.setIsActive(userGroup.isIsActive());
        //userGroupVO.setUserIds(userGroup.getUsers().stream().map(User::getId).toArray(Long[]::new));
        /*model.addAttribute("userGroup", userGroup);*/
    	model.addAttribute("moduleList", CommonConstant.USER_GROUP_MODULE_LIST);
    	
        return "member/memberGroupEdit";
    }

    @RequestMapping("groupEdit")
    @ResponseBody
    public AjaxForm groupEdit(@Valid UserGroupVO userGroupVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
        	userGroupVO.setType(CommonConstant.USER_GROUP_TYPE_MEMBER);
            userGroupService.saveOrUpdate(userGroupVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        }
    }


    @RequestMapping("selectUserJson")
    @ResponseBody
    public List<SelectOptionVO> selectUserJson(String userName) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
        Company company = WebThreadLocal.getCompany();
        if (company != null) {
            detachedCriteria.add(Restrictions.eq("company.id", company.getId()));
        }
        if (StringUtils.isNoneBlank(userName)) {
            detachedCriteria.add(Restrictions.like("username", userName, MatchMode.ANYWHERE));
        }
        detachedCriteria.add(Restrictions.eq("enabled", true));
        detachedCriteria.addOrder(Order.asc("username"));
        List<User> userList = userService.list(detachedCriteria);
        return userList.stream().map(user -> new SelectOptionVO(user.getId(), user.getUsername())).collect(Collectors.toList());
    }

    @RequestMapping("selectUserGroupJson")
    @ResponseBody
    public List<SelectOptionVO> selectUserGroupJson(UserGroupListVO userGroupListVO) {
    	
    	String name=userGroupListVO.getName();
    	String type=userGroupListVO.getType();
    	String module=userGroupListVO.getModule();
    	
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserGroup.class);
        Company company = WebThreadLocal.getCompany();
        if (company != null) {
            detachedCriteria.add(Restrictions.eq("company.id", company.getId()));
        }
        if (StringUtils.isNoneBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNoneBlank(type)) {
            detachedCriteria.add(Restrictions.eq("type", type));
        }
        if (StringUtils.isNoneBlank(module)) {
            detachedCriteria.add(Restrictions.eq("module", module));
        }
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.addOrder(Order.asc("name"));
        List<UserGroup> list = userGroupService.list(detachedCriteria);
        return list.stream().map(item -> new SelectOptionVO(item.getId(), item.getName())).collect(Collectors.toList());
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
				return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
			}
        }else{
        	return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }

	/**
	 * member import 转移到了导入模块
	 * @param response
	 */
  /*  @RequestMapping("toImport")
    public String toImport(Model model) {
        return "member/memberImport";
    }

    @RequestMapping("doImport")
    @ResponseBody
    public AjaxForm doImport(@Valid ImportDemoVO importDemoVO, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            if (importDemoVO.getImportFile() == null || importDemoVO.getImportFile().getSize() == 0) {
                return AjaxFormHelper.error().addAlertError("Please select a member excel file!");
            }
            List<MemberImportJxlsBean> errorRecords = userService.importMember(importDemoVO);
            if (errorRecords ==null) {
                return AjaxFormHelper.error("No member in upload excel file!");
            }else if(errorRecords !=null && errorRecords.size()==0){
            	 return AjaxFormHelper.success("Member import successfully.");
            }else {
                return AjaxFormHelper.error("Member import has error records!");
            }
        }
    }*/
    
    @RequestMapping("doExport")
    public void doExport(HttpServletResponse response) {
    	doMemberExport(response, "memberExportTemplate.xls", "Member-Export-");
    }
    
    private void doMemberExport(HttpServletResponse response,String fileTemplate,String writeFilePrefix){
    	Context context = new Context();
//    	context.putVar("member", member);
        File downloadFile = ExcelUtil.write(fileTemplate, context);
        String fileName = RandomUtil.generateRandomNumberWithDate(writeFilePrefix)+".xls";
        ServletUtil.download(downloadFile, fileName, response);
    }
    
    /* @RequestMapping("toFamilyView")
    public String toFamilyView(Model model, Long memberId) {

        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));

        DetachedCriteria lldc = DetachedCriteria.forClass(LoyaltyLevel.class);
        model.addAttribute("loyaltyLevelList", loyaltyLevelService.getActiveListByRefAndCompany(lldc, null,null));

        DetachedCriteria ccdc = DetachedCriteria.forClass(CommunicationChannel.class);
        model.addAttribute("communicationChannelList", communicationChannelService.getActiveListByRefAndCompany(ccdc, null, WebThreadLocal.getCompany().getId()));

        return "member/memberManagement";
    }*/


    // family details start -------------------------------------------------------------------------------------------------

    @RequestMapping("toFamilyView")
    public String toFamilyView(FamilyVO familyVO, Model model) {
        model.addAttribute("familyVO", familyVO);
        return "member/familyManagement";
    }

    @RequestMapping("familyList")
    public String familyList(Model model, FamilyVO familyVO) {
        Long memberId = familyVO.getMemberId();
        if (memberId == null) {
            throw new IllegalArgumentException("memberId cannot be null");
        }
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFamilyDetails.class);
        detachedCriteria.add(Restrictions.eq("user.id", memberId));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        if (StringUtils.isNotBlank(familyVO.getName())) {
            detachedCriteria.add(Restrictions.like("name", familyVO.getName(), MatchMode.START));
        }
        //page start
        Page<UserFamilyDetails> page = new Page<>();
        page = userFamilyDetailsService.list(detachedCriteria, page);
        model.addAttribute("page", page);
        model.addAttribute("curQueryString", "{'memberId':" + memberId + "}");
        model.addAttribute("memberId", memberId);
        return "member/familyList";
    }

    @RequestMapping("toFamilyAdd")
    public String toFamilyAdd(FamilyVO familyVO, Model model) {
        return "member/familyAdd";
    }

    @RequestMapping("familyAdd")
    @ResponseBody
    public AjaxForm familyAdd(FamilyVO familyVO) {
        return saveOrUpdateFamily(familyVO);
    }

    private AjaxForm saveOrUpdateFamily(FamilyVO familyVO) {
        if (StringUtils.isBlank(familyVO.getName())) {
            return AjaxFormHelper.error().addErrorFields(new ErrorField("name", "Name is required!"));
        }
        if (StringUtils.isBlank(familyVO.getEmail())) {
            return AjaxFormHelper.error().addErrorFields(new ErrorField("email", "Email is required!"));
        }
        if (StringUtils.isBlank(familyVO.getTelNum())) {
            return AjaxFormHelper.error().addErrorFields(new ErrorField("telNum", "Mobile Number is required!"));
        }
        if (familyVO.getMemberId() == null) {
            return AjaxFormHelper.error().addAlertError("Parameter Error Exception for member Id!");
        }

        if (userFamilyDetailsService.emailUsed(familyVO)) {
            return AjaxFormHelper.error().addAlertError("Email: " + familyVO.getEmail() + " has been used!");
        }
        userFamilyDetailsService.saveOrUpdate(familyVO);
        String msg = familyVO.getId() == null ? "label.add.successfully" : "label.edit.successfully";
        return AjaxFormHelper.success(I18nUtil.getMessageKey(msg));
    }

    @RequestMapping("toFamilyEdit")
    public String toFamilyEdit(FamilyVO familyVO, Model model) {
        UserFamilyDetails details = userFamilyDetailsService.get(familyVO.getId());
        familyVO.setName(details.getName());
        familyVO.setEmail(details.getEmail());
        familyVO.setTelNum(details.getTelNum());
        familyVO.setMemberId(details.getUser().getId());
        model.addAttribute("familyVO", familyVO);
        return "member/familyEdit";
    }
    
    @RequestMapping("familyEdit")
    @ResponseBody
    public AjaxForm familyEdit(FamilyVO familyVO) {
        if (familyVO.getId() == null) {
            return AjaxFormHelper.error().addAlertError("Id is required!");
        }
        return saveOrUpdateFamily(familyVO);
    }

    @RequestMapping("familyDelete")
    @ResponseBody
    public AjaxForm familyDelete(Long id) {
        if (id == null) {
            return AjaxFormHelper.error().addAlertError("Id is required!");
        }
        userFamilyDetailsService.delete(id);
        return AjaxFormHelper.success("success");
    }

    // family details end ---------
	
	@RequestMapping("singleViewOfClient")
	public String singleViewOfClient(Model model, Long id,String viewTime) {
		
		int viewTimeInt=3;
		if (StringUtils.isNotEmpty(viewTime) && StringUtils.isNumeric(viewTime)&&!viewTime.equals("ALL")){
			viewTimeInt =Integer.parseInt(viewTime);
		}else if (viewTime.equals("ALL")){
			viewTime = "ALL";
		} else {
            viewTime = "3";
        }

		Map parameters=new HashMap();

		
		Date endDate =new Date();
		
		Calendar c = new GregorianCalendar();
        c.setTime(endDate);
        c.add(Calendar.MONTH, -viewTimeInt);
		Date startDate = c.getTime();
		if (viewTime.equals("ALL")){
          startDate=null;
          endDate=null;
      }
		
		parameters.put("startDate",startDate);
		parameters.put("endDate", endDate);	
		
		parameters.put("companyId", WebThreadLocal.getCompany().getId());
		parameters.put("userId", id);

		// create by william -- 2018-9-17
		parameters.put("bookStatus", CommonConstant.BOOK_STATUS_NOT_SHOW);
		List<PurchaseItem> piList = purchaseItemService.getPurchaseItemByBookNotShowStatus(parameters);
		Set<PurchaseItem> purchaseItems = new HashSet<>();
		for (PurchaseItem pi : piList) {
			if (pi.getBookItemId() != null) {
				BookItem bi = bookItemService.get(Long.valueOf(pi.getBookItemId()));
				if (CommonConstant.BOOK_STATUS_NOT_SHOW.equals(bi.getStatus())) {
					purchaseItems.add(pi);
				}
			}
		}
		model.addAttribute("purchaseItemList", purchaseItems);

		parameters.put("prodType", CommonConstant.CATEGORY_REF_TREATMENT);				
		List<ClientViewVO> treatmentSalesAnalysis = purchaseOrderService.getSalesAnalysisByClientAndShop(parameters);
		model.addAttribute("treatmentSalesAnalysis", treatmentSalesAnalysis);
		
		parameters.put("prodType", CommonConstant.CATEGORY_REF_HAIRSALON);
		List<ClientViewVO> hairSalonSalesAnalysis = purchaseOrderService.getSalesAnalysisByClientAndShop(parameters);
		model.addAttribute("hairSalonSalesAnalysis", hairSalonSalesAnalysis);
		
		parameters.put("prodType", CommonConstant.CATEGORY_REF_GOODS);
		List<ClientViewVO> goodsSalesAnalysis = purchaseOrderService.getSalesAnalysisByClientAndShop(parameters);
		model.addAttribute("goodsSalesAnalysis", goodsSalesAnalysis);
		
		parameters.put("prodType", CommonConstant.CATEGORY_REF_OTHERS);
		List<ClientViewVO> othersSalesAnalysis = purchaseOrderService.getSalesAnalysisByClientAndShop(parameters);
		model.addAttribute("othersSalesAnalysis", othersSalesAnalysis);
		
		parameters.put("prodType", CommonConstant.CATEGORY_REF_TIPS);
		List<ClientViewVO> tipsSalesAnalysis = purchaseOrderService.getSalesAnalysisByClientAndShop(parameters);
		model.addAttribute("tipsSalesAnalysis", tipsSalesAnalysis);
		
		List prepaidList = prepaidTopUpTransactionService.getClientPrepaidTopUpTransactionView(parameters);
		model.addAttribute("prepaidList", prepaidList);
		
		List<PurchaseOrder> salesHistoryList = purchaseOrderService.getSalesHistoryList(parameters);
		model.addAttribute("salesHistoryList", salesHistoryList);
		
		List<SpendingSummaryVO> spendingSummaryList = purchaseOrderService.getSpendingSummaryList(parameters);
		model.addAttribute("spendingSummaryList", spendingSummaryList);
		
		User client=userService.get(id);
		model.addAttribute("client", client);
		
		DetachedCriteria ulldc = DetachedCriteria.forClass(UserLoyaltyLevel.class);
//		
		ulldc.add(Restrictions.eq("user.id", id));
		List<UserLoyaltyLevel> ullList=userLoyaltyLevelService.list(ulldc);
		model.addAttribute("ullList", ullList);
		
		List<LoyaltyPointsTransaction> lptList=loyaltyPointsTransactionService.getLoyaltyPointsTransactionByFields(id, startDate, endDate, null, null);
		model.addAttribute("lptList", lptList);
		
		ulldc.add(Restrictions.eq("isActive", true));
		List<UserLoyaltyLevel> ullListTrue=userLoyaltyLevelService.list(ulldc);
		model.addAttribute("llexpiryDate", ullListTrue.get(0).getExpiryDate());
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFamilyDetails.class);
        detachedCriteria.add(Restrictions.eq("user.id", id));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        List<UserFamilyDetails> familyDetailsList = userFamilyDetailsService.list(detachedCriteria);
        model.addAttribute("familyDetailsList", familyDetailsList);
        
        DetachedCriteria consentFormdc = DetachedCriteria.forClass(ConsentForm.class);
        consentFormdc.add(Restrictions.eq("isActive", true));
        consentFormdc.add(Restrictions.eq("company.id", WebThreadLocal.getCompany().getId()));
        List consentFormList=consentFormService.list(consentFormdc);
        model.addAttribute("consentFormList", consentFormList);
        
        model.addAttribute("viewTime", viewTime);
		return "member/singleViewOfClient";
	}

    @RequestMapping("toAdvanceView")
    public String toAdvanceView(Model model, MemberAdvanceVO memberAdvanceVO) {

    	model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), true,false));
        return "member/memberAdvanceManagement";
    }

    @RequestMapping("advanceList")
    public String advanceList(Model model, MemberAdvanceVO memberAdvanceVO) {
        //page start
        Page<User> memberPage = userService.listMember(memberAdvanceVO);
        model.addAttribute("page", memberPage);
        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONStringWithDateFormat(memberAdvanceVO, "yyyy-MM-dd"),"utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "member/memberAdvanceList";
    }

    @RequestMapping("toGroupAddUser")
    public String toGroupAddUser(MemberAdvanceVO memberAdvanceVO, HttpSession session, Model model) {
        List<UserGroup> memberUserGroupList = userGroupService.getUserGroupByTypeAndModule(CommonConstant.USER_GROUP_TYPE_MEMBER,
                null, WebThreadLocal.getCompany().getId());
        model.addAttribute("memberUserGroupList", memberUserGroupList);
        session.setAttribute("memberAdvanceVO", memberAdvanceVO);
        return "member/memberGroupAddUser";
    }

    @RequestMapping("groupAddUser")
    @ResponseBody
    public AjaxForm groupAddUser(Long userGroupId, HttpSession session) {
        MemberAdvanceVO memberAdvanceVO = (MemberAdvanceVO) session.getAttribute("memberAdvanceVO");
        if (memberAdvanceVO == null || userGroupId == null) {
            return AjaxFormHelper.error().addAlertError("Parameter Error");
        }
        userGroupService.addUserToGroup(userGroupId, memberAdvanceVO);
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
    }

    @RequestMapping("toQuickAdd")
    public String toQuickAdd(MemberAddVO memberAddVO, Model model) {
        List<Shop> shopList = shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false);
        model.addAttribute("shopList", shopList);
        memberAddVO.setGender("FEMALE");
        model.addAttribute("districtData", CommonConstant.DISTRICT_DATA);
        model.addAttribute(memberAddVO);
        return "member/memberQuickAdd";
    }

    @RequestMapping("quickAdd")
    @ResponseBody
    public AjaxForm quickAdd(@Valid MemberAddVO memberAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            AjaxForm errorAjaxForm = AjaxFormHelper.error();
            //check email
            User memberCheckByEmail = userService.getUserByEmail(memberAddVO.getEmail(), CommonConstant.USER_ACCOUNT_TYPE_MEMBER);
            if (memberCheckByEmail != null) {
                errorAjaxForm.addErrorFields(new ErrorField("email", I18nUtil.getMessageKey("label.errors.duplicate")));
            }
            //check tel#
            if (StringUtils.isBlank(memberAddVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.home.error")));
            } else if (!NumberUtil.isNumeric(memberAddVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.phone.should.be.number")));
            } else if (userService.isMobileUsed(WebThreadLocal.getCompany().getId(), memberAddVO.getMobilePhone())) {
                errorAjaxForm.addErrorFields(new ErrorField("mobilePhone", I18nUtil.getMessageKey("label.errors.mobile.has.used")));
            }
            if (StringUtils.isBlank(memberAddVO.getAddressVO().getDistrict())) {
                errorAjaxForm.addErrorFields(new ErrorField("addressVO.district", I18nUtil.getMessageKey("label.errors.address.district.required")));
            }
            if (!errorAjaxForm.getErrorFields().isEmpty()) {
                return errorAjaxForm;

            } else {
                User member = userService.saveMember(memberAddVO);
                //email send to change password
                AjaxForm ajaxForm = AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
                ajaxForm.getOtherMessages().put("id", member.getId());
                ajaxForm.getOtherMessages().put("username", member.getUsername());
                ajaxForm.getOtherMessages().put("fullName", member.getFullName());
                return ajaxForm;
            }
        }
    }
    
    @RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
    	User user = userService.get(id);
        if (user != null) {
        	try {
        		user.setEnabled(false);
        		userService.save(user);
			} catch (Exception e) {
				e.printStackTrace();
				return  AjaxFormHelper.error("Errors happened!");
			}
        }else{
        	return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
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
				return  AjaxFormHelper.error("Errors happened!");
			}
        }else{
        	return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success("success");
    }

    /* create by william -- 2018-10-11 */
    /* export member search list */
	@RequestMapping("export")
	public void export(MemberListVO memberListVO, HttpServletResponse response)throws ParseException {

        String username=memberListVO.getUsername();
        String email=memberListVO.getEmail();
        String enabled=memberListVO.getEnabled();
        Long shopId=memberListVO.getShopId();
        Long loyaltyLevelId = memberListVO.getLoyaltyLevelId();
        Long userGroupId = memberListVO.getUserGroupId();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);

        if (StringUtils.isNotBlank(username)) {
            detachedCriteria.createAlias("phones", "phone");
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("firstName", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("phone.number", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("lastName", username, MatchMode.ANYWHERE));
            disjunction.add(Restrictions.like("fullName", username, MatchMode.ANYWHERE));
            detachedCriteria.add(disjunction);
        }
        if (StringUtils.isNotBlank(email)) {
            detachedCriteria.add(Restrictions.like("email", email, MatchMode.ANYWHERE));
        }
        if (StringUtils.isNotBlank(enabled)) {
            detachedCriteria.add(Restrictions.eq("enabled", Boolean.parseBoolean(enabled)));
        }

        if (shopId !=null) {
            detachedCriteria.add(Restrictions.eq("shop.id",shopId));
        }

        if (userGroupId !=null) {
            detachedCriteria.createAlias("userGroups", "ug");
            detachedCriteria.add(Restrictions.eq("ug.id",userGroupId));
        }
        if (loyaltyLevelId !=null) {
            detachedCriteria.createAlias("userLoyaltyLevels", "ull");
            detachedCriteria.add(Restrictions.eq("ull.isActive",true));
            detachedCriteria.add(Restrictions.eq("ull.loyaltyLevel.id",loyaltyLevelId));
        }
        detachedCriteria.add(Restrictions.eq("accountType",CommonConstant.USER_ACCOUNT_TYPE_MEMBER));

        List<User> members = userService.list(detachedCriteria);

		List<MemberVO> memberVOList = new ArrayList<>();
		MemberVO vo =null;

		//int i=1;
		for(User m : members ){
			vo = new MemberVO();
			vo.setHomeShopName(m.getShop().getName());
			vo.setLoyaltyLevel(m.getCurrentLoyaltyLevel().getName());
			vo.setDiva(m.getUsername());
			vo.setFirstName(m.getFirstName());
			vo.setLastName(m.getLastName());
			vo.setEmail(m.getEmail());
			vo.setPhone(m.getMobilePhone());
			vo.setGender(m.getGender());
			vo.setDateOfBirthString(DateUtil.dateToString(m.getDateOfBirth(), "yyyy-MM-dd"));
			vo.setEnabled(m.isEnabled());

			memberVOList.add(vo);
		}

		Context context = new Context();
		context.putVar("volist", memberVOList);
		File downloadFile = ExcelUtil.write("memberExportTemplate.xls", context);

		ServletUtil.download(downloadFile, RandomUtil.generateRandomNumberWithDate("Member-Export-") + ".xls", response);
	}
}
