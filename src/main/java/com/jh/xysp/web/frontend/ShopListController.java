package com.jh.xysp.web.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jh.xysp.dto.ShopExecution;
import com.jh.xysp.entity.Area;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.entity.ShopCategory;
import com.jh.xysp.service.AreaService;
import com.jh.xysp.service.ShopCategoryService;
import com.jh.xysp.service.ShopService;
import com.jh.xysp.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
	@Autowired
    private ShopService shopService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        // parentId��Ϊ�գ���ѯ����ӦparentIdĿ¼�µ�ȫ����ƷĿ¼
        if (parentId != -1) {
            try {
                ShopCategory childCategory = new ShopCategory();
                ShopCategory parentCategory = new ShopCategory();
                parentCategory.setShopCategoryId(parentId);
                childCategory.setShopCategoryParentId(parentCategory);
                shopCategoryList = shopCategoryService.getShopCategoryList(childCategory);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            // parentIdΪ�գ���ѯ��һ����shopCategory
            try {
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }
    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if ((pageIndex > -1) && (pageSize > -1)) {
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            // ��װ��ѯ����
            Shop shopCondition = compactShopConditionToSearch(parentId, shopCategoryId, areaId, shopName);
            // ����service���ṩ�ķ���
            ShopExecution se = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("count", se.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }

        return modelMap;
    }
    private Shop compactShopConditionToSearch(long parentId, long shopCategoryId, int areaId, String shopName) {
		Shop shopCondition = new Shop();
        if (parentId != -1L) {
        	ShopCategory childCategory=new ShopCategory();
    		ShopCategory parentCategory=new ShopCategory();
    		parentCategory.setShopCategoryId(parentId);
    		childCategory.setShopCategoryParentId(parentCategory);
    		shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L) {
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L) {
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setShoparea(area);
        }

        if (shopName != null) {
            shopCondition.setShopName(shopName);
        }
        // ��ѯ״̬Ϊ���ͨ��������
        shopCondition.setShopEnableStatus(1);
        return shopCondition;
    }
}
