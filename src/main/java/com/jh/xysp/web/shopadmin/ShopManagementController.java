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
        // ��ȡshopId
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        // ���shopId���Ϸ�
        if (shopId <= 0) {
        	// ���Դӵ�ǰsession�л�ȡ
            Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
            if (currentShop == null) {
                // �����ǰsession��Ҳû��shop��Ϣ,����view�� �ض���
                modelMap.put("redirect", true);
                modelMap.put("url", "/xysp/shopadmin/shoplist");
            }else{
            	// ����view�� �����ҳ��
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        }else { 
        	// shopId�Ϸ��Ļ�
            Shop shop = new Shop();
            shop.setShopId(shopId);
            // ��currentShop�ŵ�session��
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
				modelMap.put("errMsg", "shopId���Ϸ�");
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
		// 0. ��֤��У��
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "��֤�벻��ȷ");
			return modelMap;
		}
		// 1.���ղ�ת����Ӧ�Ĳ���������shop��Ϣ��ͼƬ��Ϣ
		// shopStr �Ǻ�ǰ��Լ���õĲ���ֵ����˴�request�л�ȡrequest���ֵ����ȡshop����Ϣ
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// ʹ��jackson-databind ��jsonת��Ϊpojo
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			// ��jsonת��Ϊpojo
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			e.printStackTrace();
			// ��������Ϣ���ظ�ǰ̨
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// 1.2 ͼƬ��Ϣ ����Apache Commons FileUpload���ļ��ϴ�
		// Spring MVC�е� ͼƬ����CommonsMultipartFile��
		CommonsMultipartFile shopImg = null;
		// ��request�ı��λỰ�е��������л�ȡͼƬ���������
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// shopImg�Ǻ�ǰ��Լ���õı�����
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
		} else {
			// ��������Ϣ���ظ�ǰ̨
			modelMap.put("success", false);
			modelMap.put("errMsg", "ͼƬ����Ϊ��");
		}

		// 2. ע�����
		if (shop != null && shopImg != null) {
			// Session TODO
			// ����persionInfo����Ϣ���϶�Ҫ��¼����ע����̡�
			// �����ⲿ����Ϣ���Ǵ�session�л�ȡ������������ǰ��,������ʱʱ���߱��������������죬��Ӳ���룬���㵥Ԫ����
			PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
			shop.setShopowner(personInfo);
			// ע�����
			ShopExecution se = null;
			try {
				ImageHolder imageHolder=new ImageHolder(shopImg.getInputStream(), shopImg.getOriginalFilename());
				se = shopService.addShop(shop, imageHolder);
				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
					// ���û����Բ����ĵ����б�
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
			// ��������Ϣ���ظ�ǰ̨
			modelMap.put("success", false);
			modelMap.put("errMsg", "�����������Ϣ");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> midifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 0. ��֤��У��
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "��֤�벻��ȷ");
			return modelMap;
		}
		// 1.���ղ�ת����Ӧ�Ĳ���������shop��Ϣ��ͼƬ��Ϣ
		// shopStr �Ǻ�ǰ��Լ���õĲ���ֵ����˴�request�л�ȡrequest���ֵ����ȡshop����Ϣ
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		// ʹ��jackson-databind ��jsonת��Ϊpojo
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			// ��jsonת��Ϊpojo
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			e.printStackTrace();
			// ��������Ϣ���ظ�ǰ̨
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}

		// 1.2 ͼƬ��Ϣ ����Apache Commons FileUpload���ļ��ϴ�
		// Spring MVC�е� ͼƬ����CommonsMultipartFile��
		CommonsMultipartFile shopImg = null;
		// ��request�ı��λỰ�е��������л�ȡͼƬ���������
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// shopImg�Ǻ�ǰ��Լ���õı�����
			shopImg = (CommonsMultipartFile) multipartRequest.getFile("shopImg");
		}

		// 2. �޸ĵ���
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
			// ��������Ϣ���ظ�ǰ̨
			modelMap.put("success", false);
			modelMap.put("errMsg", "���������Id");
		}
		return modelMap;
	}
}
