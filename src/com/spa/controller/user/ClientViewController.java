package com.spa.controller.user;

import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.utils.WebThreadLocal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/10/19
 */
@Controller
@RequestMapping("clientView")
public class ClientViewController extends BaseController {
	
	
	@RequestMapping("toSingleView")
	public String toSingleView(Model model) {
		
		return "clientView/singleView";
	}
	
	@RequestMapping("singleView")
	public String singleView(Model model,Long memberId) {
        User client=userService.get(memberId);
        model.addAttribute("client", client);
        
        Shop currentShop=WebThreadLocal.getHomeShop();
        Boolean isHomeShop=Boolean.TRUE;
        if(client.getShop().getId() !=currentShop.getId() ){
        	isHomeShop=Boolean.FALSE;
        }
        model.addAttribute("isHomeShop", isHomeShop);
        
		return "clientView/singleViewDetails";
	}
}
