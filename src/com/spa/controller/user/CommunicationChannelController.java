package com.spa.controller.user;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.user.CommunicationChannel;
import org.spa.utils.I18nUtil;
import org.spa.utils.RandomUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.page.Page;
import org.spa.vo.user.CommunicationChannelListVO;
import org.spa.vo.user.CommunicationChannelVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/08/1.
 */
@Controller
@RequestMapping("communicationChannel")
public class CommunicationChannelController extends BaseController {

	
	@RequestMapping("toView")
	public String communicationChannelManagement(Model model,CommunicationChannelListVO communicationChannelListVO) {
		
		return "communicationChannel/communicationChannelManagement";
	}
	
	@RequestMapping("list")
	public String communicationChannelList(Model model, CommunicationChannelListVO communicationChannelListVO) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CommunicationChannel.class);
		String name=communicationChannelListVO.getName();
		String isActive=communicationChannelListVO.getIsActive();
		
		if (StringUtils.isNotBlank(name)) {
			detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(isActive)) {
			detachedCriteria.add(Restrictions.eq("isActive", Boolean.valueOf(isActive)));
		}
		
		//page start
		Page<CommunicationChannel> brandPage = communicationChannelService.list(detachedCriteria, communicationChannelListVO.getPageNumber(), communicationChannelListVO.getPageSize());
		model.addAttribute("page", brandPage);
		
		try {
			model.addAttribute("curQueryString",URLEncoder.encode(JSONObject.toJSONString(communicationChannelListVO),"utf-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//page end
		return "communicationChannel/communicationChannelList";
	}
	
	@RequestMapping("toAdd")
	public String toAddCommunicationChannel(CommunicationChannelVO communicationChannelVO) {
		return "communicationChannel/communicationChannelAdd";
	}
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addCommunicationChannel(@Valid CommunicationChannelVO communicationChannelVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			String name=communicationChannelVO.getName();
			CommunicationChannel communicationChannel=new CommunicationChannel();
			communicationChannel.setName(name);
			communicationChannel.setReference(RandomUtil.generateRandomNumberWithDate("CC-"));
			communicationChannel.setDescription(communicationChannelVO.getDescription());
			communicationChannel.setIsActive(true);
			communicationChannel.setCreated(new Date());
			communicationChannel.setCompany(WebThreadLocal.getCompany());
			communicationChannel.setCreatedBy(WebThreadLocal.getUser().getUsername());
			communicationChannelService.save(communicationChannel);
			
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
		}
	}
	@RequestMapping("toEdit")
	public String toEditCommunicationChannel(CommunicationChannelVO communicationChannelVO,Model model) {
		
		CommunicationChannel communicationChannel=communicationChannelService.get(communicationChannelVO.getId());
		communicationChannelVO.setName(communicationChannel.getName());
		communicationChannelVO.setDescription(communicationChannel.getDescription());
		communicationChannelVO.setId(communicationChannel.getId());
		
		return "communicationChannel/communicationChannelEdit";
	}
	
	@RequestMapping("edit")
	@ResponseBody
	public AjaxForm editCommunicationChannel(@Valid CommunicationChannelVO communicationChannelVO,BindingResult result) {
		if (result.hasErrors()) {
			return AjaxFormHelper.error(result);
		} else {
			
			CommunicationChannel communicationChannel=communicationChannelService.get(communicationChannelVO.getId());
			String name=communicationChannelVO.getName();
			String description=communicationChannelVO.getDescription();
			AjaxForm errorAjaxForm=AjaxFormHelper.error();
			
			if(!errorAjaxForm.getErrorFields().isEmpty()){
				return errorAjaxForm;
				
			}else{
				communicationChannel.setName(name);
				communicationChannel.setDescription(description);
				communicationChannel.setLastUpdated(new Date());
				communicationChannel.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
				communicationChannelService.update(communicationChannel);
				
				return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
			}
		}
	}
	
	@RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(Long id) {
		CommunicationChannel communicationChannel=communicationChannelService.get(id);
        if (communicationChannel != null) {
        	try {
        		communicationChannel.setIsActive(false);
        		communicationChannelService.saveOrUpdate(communicationChannel);
			} catch (Exception e) {
				e.printStackTrace();
				return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
			}
        }else{
        	return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success("success");
    }
}
