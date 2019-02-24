package com.jh.xysp.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jh.xysp.dto.ProductCategoryExecution;
import com.jh.xysp.dto.Result;
import com.jh.xysp.entity.ProductCategory;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.enums.ProductCategoryStateEnum;
import com.jh.xysp.exception.ProductCategoryOperationException;
import com.jh.xysp.service.ProductCategoryService;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryController {
	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/getproductcategorybyshopId", method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryByShopId(HttpServletRequest request) {
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true, list);
		} else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
		}
	}

	@RequestMapping(value = "/addproductcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategory(@RequestBody List<ProductCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productCategoryList != null && productCategoryList.size() > 0) {
			// ��session�л�ȡshop����Ϣ
			Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
			if (currentShop != null && currentShop.getShopId() != null) {
				// ΪProductCategory����shopId
				for (ProductCategory productCategory : productCategoryList) {
					productCategory.setShopId(currentShop.getShopId());
				}
				try {
					// ��������
					ProductCategoryExecution pce = productCategoryService.addProductCategory(productCategoryList);
					if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
						modelMap.put("success", true);
						// ͬʱҲ�������ɹ����������ظ�ǰ̨
						modelMap.put("effectNum", pce.getCount());
					} else {
						modelMap.put("success", false);
						modelMap.put("errMsg", pce.getStateInfo());
					}
				} catch (ProductCategoryOperationException e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.getMessage());
					return modelMap;
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", ProductCategoryStateEnum.NULL_SHOP.getStateInfo());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "��������һ������Ŀ¼��Ϣ");
		}
		return modelMap;
	}
	@RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productCategoryId != null && productCategoryId > 0) {
            // ��session�л�ȡshop����Ϣ
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            if (currentShop != null && currentShop.getShopId() != null) {
                try {
                    // ɾ��
                    Long shopId = currentShop.getShopId();
                    ProductCategoryExecution pce = productCategoryService.deleteProductCategory(productCategoryId, shopId);
                    if (pce.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                        modelMap.put("success", true);
                    } else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", pce.getStateInfo());
                    }
                } catch (ProductCategoryOperationException e) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", e.getMessage());
                    return modelMap;
                }
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", ProductCategoryStateEnum.NULL_SHOP.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "��ѡ����Ʒ���");
        }
        return modelMap;
    }
}
