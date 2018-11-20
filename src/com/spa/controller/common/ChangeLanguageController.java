package com.spa.controller.common;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Created by Ivy on 2016/01/16.
 */
@Controller
public class ChangeLanguageController{

	@RequestMapping("/lang")
	public String lang(HttpServletRequest request,Model model) {
	    String langType = request.getParameter("langType");
	    Locale locale =LocaleContextHolder.getLocale();
	    if ("zh".equals(langType)) {
	        locale = new Locale("zh", "CN");
	        
	    } else if ("en".equals(langType)) {
	        locale = new Locale("en", "US");
	       
	        
	    } else if ("ch".equals(langType)) {
	        locale = new Locale("ch", "CN");
	        
	    }
	    request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
//	    String url = "";
//	    url = request.getScheme() +"://" + request.getServerName()  + ":" +request.getServerPort()  + request.getServletPath();
//	    if (request.getQueryString() != null){
//	    	url += "?" + request.getQueryString();
//	    }
	    model.addAttribute("localeCode", locale.toString());
	    return "index";
	}
}
