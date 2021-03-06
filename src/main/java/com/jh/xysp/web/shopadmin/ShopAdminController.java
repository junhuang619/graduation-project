package com.jh.xysp.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="shopadmin",method= {RequestMethod.GET})
public class ShopAdminController {
	@RequestMapping(value="/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}
	@RequestMapping(value="/shoplist")
	public String shopList() {
		return "shop/shoplist";
	}
	@RequestMapping(value = "/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }
	@RequestMapping(value = "/productcategorymanage", method = RequestMethod.GET)
    public String productcategoryManage() {
        return "shop/productcategorymanage";
    }
	@RequestMapping(value = "/productoperation", method = RequestMethod.GET)
	public String productOperation() {
		return "shop/productoperation";
	}
	@RequestMapping(value = "/productmanagement", method = RequestMethod.GET)
	public String productManagement() {
		return "shop/productmanagement";
	}
}
