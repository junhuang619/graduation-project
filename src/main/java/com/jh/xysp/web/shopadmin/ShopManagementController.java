package com.jh.xysp.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.xysp.dto.ImageHolder;
import com.jh.xysp.dto.ShopExecution;
import com.jh.xysp.entity.Area;
import com.jh.xysp.entity.PersonInfo;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.entity.ShopCategory;
import com.jh.xysp.enums.ShopStateEnum;
import com.jh.xysp.exception.ShopOperationException;
import com.jh.xysp.service.AreaService;
import com.jh.xysp.service.ShopCategoryService;
import com.jh.xysp.service.ShopService;
import com.jh.xysp.util.CodeUtil;
import com.jh.xysp.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;

	@RequestMapping(value = "/getshopmanageInfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取shopId
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        // 如果shopId不合法
        if (shopId <= 0) {
        	// 尝试从当前session中获取
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            if (currentShop == null) {
                // 如果当前session中也没有shop信息,告诉view层 重定向
                modelMap.put("redirect", true);
                modelMap.put("url", "/xysp/shopadmin/shoplist");
            }else{
            	// 告诉view层 进入该页面
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        }else { 
        	// shopId合法的话
            Shop shop = new Shop();
            shop.setShopId(shopId);
            // 将currentShop放到session中
            request.getSession().setAttribute("currentShop", shop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }
	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		PersonInfo user = new PersonInfo();
		user.setUserId(1L);
		user.setUserName("usertest");
		request.getSession().setAttribute("user",user);
		user = (PersonInfo) request.getSession().getAttribute("user");
		List<Shop> shopList = new ArrayList<Shop>();
		try {
			Shop shopCondition = new Shop();
			shopCondition.setShopowner(user);
			ShopExecution se=shopService.getShopList(shopCondition, 0, 100);
            modelMap.put("shopList", se.getShopList());
            modelMap.put("user", user);
			modelMap.put("success",true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
		try {
			if (shopId >= 0) {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("success", true);
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "shopId不合法");
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}

	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getshopinitinfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;

	}

	@RequestMapping(value = "/registshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 0. 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			return modelMap;
		}
		// 1.接收并转换相应的参数，包括shop信息和图片信息
		// shopStr 是和前端约定好的参数值，后端从request中获取request这个值来获取shop的信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// 使用jackson-databind 将json转换为pojo
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			// 将json转换为pojo
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			e.printStackTrace();
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// 1.2 图片信息 基于Apache Commons FileUpload的文件上传
		// Spring MVC中的 图片存在CommonsMultipartFile中
		CommonsMultipartFile shopImg = null;
		// 从request的本次会话中的上线文中获取图片的相关内容
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// shopImg是和前端约定好的变量名
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
		} else {
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", "图片不能为空");
		}

		// 2. 注册店铺
		if (shop != null && shopImg != null) {
			// Session TODO
			// 店主persionInfo的信息，肯定要登录才能注册店铺。
			// 所以这部分信息我们从session中获取，尽量不依赖前端,这里暂时时不具备条件，后续改造，先硬编码，方便单元测试
			PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
			shop.setShopowner(personInfo);
			// 注册店铺
			ShopExecution se = null;
			try {
				ImageHolder imageHolder=new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
				se = shopService.addShop(shop, imageHolder);
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					// 该用户可以操作的店铺列表
					List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
					if (shopList == null || shopList.size() == 0) {
						shopList = new ArrayList<Shop>();
					}
					shopList.add(se.getShop());
					request.getSession().setAttribute("shopList", shopList);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				e.printStackTrace();
				modelMap.put("success", false);
				modelMap.put("errMsg", "addShop Error");
			}
		} else {
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> midifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 0. 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			return modelMap;
		}
		// 1.接收并转换相应的参数，包括shop信息和图片信息
		// shopStr 是和前端约定好的参数值，后端从request中获取request这个值来获取shop的信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// 使用jackson-databind 将json转换为pojo
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			// 将json转换为pojo
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			e.printStackTrace();
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// 1.2 图片信息 基于Apache Commons FileUpload的文件上传
		// Spring MVC中的 图片存在CommonsMultipartFile中
		CommonsMultipartFile shopImg = null;
		// 从request的本次会话中的上线文中获取图片的相关内容
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// shopImg是和前端约定好的变量名
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
		}

		// 2. 修改店铺
		if (shop != null && shop.getShopId() != null) {
			ShopExecution se = null;
			try {
				if (shopImg == null) {
					se = shopService.modifyShop(shop,null);
				} else {
					ImageHolder imageHolder=new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
					se = shopService.modifyShop(shop, imageHolder);
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				e.printStackTrace();
				modelMap.put("success", false);
				modelMap.put("errMsg", "addShop Error");
			}
		} else {
			// 将错误信息返回给前台
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺Id");
		}
		return modelMap;
	}
}
