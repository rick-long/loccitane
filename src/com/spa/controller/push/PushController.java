package com.spa.controller.push;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;
import org.hibernate.criterion.DetachedCriteria;
import org.spa.FireBase.FireBase;
import org.spa.model.product.Product;
import org.spa.model.push.Push;
import org.spa.utils.I18nUtil;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.page.Page;
import org.spa.vo.push.PushVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by jason on 2018/04/16.
 */
@Controller
@RequestMapping("push")
public class PushController extends BaseController {

	
	@RequestMapping("toView")
	public String brandManagement(Model model) {
		
		return "push/pushManagement";
	}
	
	@RequestMapping("list")
	public String brandList(Model model, PushVO pushVO) {
		//page start
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Push.class);
		Page<Push> pushPage = pushService.list(detachedCriteria, pushVO.getPageNumber(), pushVO.getPageSize());
		try {
			model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(pushVO), "utf-8"));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		model.addAttribute("page", pushPage);

		return "push/pushList";
	}
	
	@RequestMapping("toAdd")
	public String toAddBrand() {
		return "push/pushAdd";
	}
	@RequestMapping("add")
	@ResponseBody
	public AjaxForm addBrand(@Valid PushVO pushVO) {
		JSONObject json = new JSONObject();
		json.put("to","eq2CTOeb7eI:APA91bGloGBYsp884-K3Sq4BW5VVZwBPggSaHFsGghqyViJwOxziN8imKDnInOfOYxF4VCjgHb5bE868pq3LczFjmjaVbEuE2A5RA-RpJCloJtJVdhBxbcws16igvWMNib75P2n0Ahqw");//推送到哪台客户端机器，方法一推一个token,
		//批量推送 ，最多1000个token ，此处的tokens是一个token JSONArray数组json.put("registration_ids", tokens);
		JSONObject info = new JSONObject();
		info.put("title",pushVO.getTitle());
		info.put("body", pushVO.getLabel());
		info.put("icon", pushVO.getImageUrl());
		json.put("notification", info);
		//发送推送
		FireBase.pushFCMNotification(json);
		if (pushVO.getSendDate()==null){
			pushVO.setSendDate(new Date());
		}
		pushService.save(pushVO);
			return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));

	}

}
