package com.spa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(".well-known")
public class SSLController{
	//http://ssl2.senseoftouch.com.hk/.well-known/pki-validation/fileauth.txt
	@RequestMapping("pki-validation/fileauth.txt")
    public String testManagement() {
        return "/fileauth";
    }
}
