package com.jh.xysp.web.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jh.xysp.entity.HeadLine;
import com.jh.xysp.entity.ShopCategory;
import com.jh.xysp.service.HeadLineService;
import com.jh.xysp.service.ShopCategoryService;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
	@Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    
    @RequestMapping(value = "/listmainpage", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> listMainPage() {
    	Map<String, Object> modelMap = new HashMap<String, Object>();
    	List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
    	List<HeadLine> headLineList = new ArrayList<HeadLine>();
    	try{
            // 查询parentId为null的一级类别
            shopCategoryList = shopCategoryService.getShopCategoryList(null);
            modelMap.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
    	try {
            // 查询状态为1的可见的headLine信息
            HeadLine headLineConditon = new HeadLine();
            headLineConditon.setLineEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineConditon);
            modelMap.put("headLineList", headLineList);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("errMsg", e.getMessage());
        }
    	modelMap.put("success", true);
        return modelMap;
    }
}
