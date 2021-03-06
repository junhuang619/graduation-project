package com.jh.xysp.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class FrontEndController {
	@RequestMapping(value = "/index" ,method = RequestMethod.GET)
    public String index(){
        return "frontend/index";
    }
	@RequestMapping(value = "/shoplist", method = RequestMethod.GET)
    public String shopList() {
        return "frontend/shoplist";
    }
	@RequestMapping(value = "/shopdetail", method = RequestMethod.GET)
    public String shopDetail() {
        return "frontend/shopdetail";
    }
	@RequestMapping(value = "/localauthlogin", method = RequestMethod.GET)
    public String localAuthLogin() {
        return "frontend/localauthlogin";
    }
	@RequestMapping(value="/changepwd",method= RequestMethod.GET)
	public String changepwd() {
		return "frontend/changepwd";
	}
}
