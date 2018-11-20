package com.spa.controller.front;

import com.spa.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("front/region")
public class RegionTestController extends BaseController{


	@RequestMapping("test")
	 public String test(Model model,HttpServletRequest request) {

		return "regionTest";

	}
	@RequestMapping("saveTest")
	public String saveTest(Model model,HttpServletRequest request,String  province, String  city,String  town) {
		System.out.println(province);
		System.out.println(city);
		System.out.println(town);
		return "";

	}
}
