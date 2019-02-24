package com.jh.xysp.web.shopadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.xysp.dto.ImageHolder;
import com.jh.xysp.dto.ProductExecution;
import com.jh.xysp.entity.Product;
import com.jh.xysp.entity.ProductCategory;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.enums.ProductStateEnum;
import com.jh.xysp.exception.ProductOperationException;
import com.jh.xysp.service.ProductCategoryService;
import com.jh.xysp.service.ProductService;
import com.jh.xysp.util.CodeUtil;
import com.jh.xysp.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	// ����ϴ�ͼƬ����
	private static final int IMAGEMAXCOUNT = 6;

	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Product product = null;
		// ����ǰ�˴��ݹ�����product
		String productStr = null;
		// ��ƷͼƬ����ͼ�������������Ƶķ�װ�ࣩ
		ImageHolder thumbnail = null;
		// ��HttpServletRequestת��ΪMultipartHttpServletRequest�����Ժܷ���صõ��ļ������ļ�����
		MultipartHttpServletRequest multipartHttpServletRequest = null;
		// ������Ʒ����ͼ
		CommonsMultipartFile thumbnailFile = null;
		// ������Ʒ����ͼƬ
		List<ImageHolder> productDetailImgList = new ArrayList<ImageHolder>();
		// ����һ��ͨ�õĶಿ�ֽ�����
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "��֤�벻��ȷ");
			return modelMap;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			productStr = HttpServletRequestUtil.getString(request, "productStr");
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}

		try {
			// �ж� request �Ƿ����ļ��ϴ�,���ಿ������
			if (commonsMultipartResolver.isMultipart(request)) {
				// ��requestת���ɶಿ��request
				multipartHttpServletRequest = (MultipartHttpServletRequest) request;

				// �õ�����ͼ��CommonsMultipartFile ,��ǰ��Լ����ʹ��thumbnail ����
				// ��������ImageHolder����
				thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
				// ת��ΪImageHolder��ʹ��service��Ĳ�������Ҫ��
				thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());

				// �õ� ��Ʒ������б���ǰ��Լ��ʹ��productImg + i ���� ,������ImageHolder����
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile productDetailImgFile = (CommonsMultipartFile) multipartHttpServletRequest
							.getFile("productImg" + i);
					if (productDetailImgFile != null) {
						ImageHolder productDetailImg = new ImageHolder(productDetailImgFile.getInputStream(),
								productDetailImgFile.getOriginalFilename());
						productDetailImgList.add(productDetailImg);
					} else {
						// ����������л�ȡ�ĵ�fileΪ�գ���ֹѭ��
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "�ϴ�ͼƬ����Ϊ��");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if (product != null && thumbnailFile != null && productDetailImgList.size() > 0) {
			try {
				// ��session�л�ȡshop��Ϣ��������ǰ�˵Ĵ��ݸ��Ӱ�ȫ
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				// ����addProduct
				ProductExecution pe = productService.addProduct(product, thumbnail, productDetailImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "��������Ʒ��Ϣ");
		}
		return modelMap;
	}

	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductById(@RequestParam long productId) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (productId > -1) {
			Product product = productService.queryProductById(productId);
			List<ProductCategory> productCategoryList = productCategoryService
					.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	@RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyProduct(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "�����˴������֤��");
			return modelMap;
		}

		Product product = null;
		// ����ǰ�˴��ݹ�����product
		String productStr = null;
		// ��ƷͼƬ����ͼ�������������Ƶķ�װ�ࣩ
		ImageHolder thumbnail = null;

		// ��HttpServletRequestת��ΪMultipartHttpServletRequest�����Ժܷ���صõ��ļ������ļ�����
		MultipartHttpServletRequest multipartHttpServletRequest = null;
		// ������Ʒ����ͼ
		CommonsMultipartFile thumbnailFile = null;
		// ������Ʒ����ͼƬ
		List<ImageHolder> productDetailImgList = new ArrayList<ImageHolder>();

		// ����һ��ͨ�õĶಿ�ֽ�����
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		// Step2: ʹ��FastJson�ṩ��api,ʵ����Product �������service��ĵ�һ������
		ObjectMapper mapper = new ObjectMapper();
		// ��ȡǰ�˴��ݹ�����product,Լ����ʹ��productStr
		try {
			productStr = HttpServletRequestUtil.getString(request, "productStr");
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// Step3: ��Ʒ����ͼ �� ��Ʒ����ͼ �������service��ĵڶ��������͵���������
		try {
			// �ж� request �Ƿ����ļ��ϴ�,���ಿ������
			if (commonsMultipartResolver.isMultipart(request)) {
				// ��requestת���ɶಿ��request
				multipartHttpServletRequest = (MultipartHttpServletRequest) request;

				// �õ�����ͼ��CommonsMultipartFile ,��ǰ��Լ����ʹ��thumbnail ����
				// ��������ImageHolder����
				thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
				// ת��ΪImageHolder��ʹ��service��Ĳ�������Ҫ��
				thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());

				// �õ� ��Ʒ������б���ǰ��Լ��ʹ��productImg + i ���� ,������ImageHolder����
				for (int i = 0; i < IMAGEMAXCOUNT; i++) {
					CommonsMultipartFile productDetailImgFile = (CommonsMultipartFile) multipartHttpServletRequest
							.getFile("productImg" + i);
					if (productDetailImgFile != null) {
						ImageHolder productDetailImg = new ImageHolder(productDetailImgFile.getInputStream(),
								productDetailImgFile.getOriginalFilename());
						productDetailImgList.add(productDetailImg);
					} else {
						// ����������л�ȡ�ĵ�fileΪ�գ���ֹѭ��
						break;
					}
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "�ϴ�ͼƬ����Ϊ��");
				return modelMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}

		// Step4 ����Service��
		if (product != null && thumbnailFile != null && productDetailImgList.size() > 0) {
			try {
				// ��session�л�ȡshop��Ϣ��������ǰ�˵Ĵ��ݸ��Ӱ�ȫ
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				// ����addProduct
				ProductExecution pe = productService.modifyProduct(product, thumbnail, productDetailImgList);
				if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "��������Ʒ��Ϣ");
		}
		return modelMap;
	}
	@RequestMapping(value = "/changestatus",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeStatus(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        // ��ȡǰ�˴��ݹ�����product,Լ����ʹ��productStr
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            Product product = mapper.readValue(productStr, Product.class);
            Shop tempShop = (Shop) request.getSession().getAttribute("currentShop");
            product.setShop(tempShop);

            ProductExecution pe = productService.modifyProduct(product, null, null);
            if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("errMsg", "�����ɹ�");
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        return modelMap;
    }
	 @RequestMapping(value = "/getproductlist", method = RequestMethod.GET)
	    @ResponseBody
	    private Map<String, Object> queryProductList(HttpServletRequest request) {
	        Map<String, Object> modelMap = new HashMap<String, Object>();
	        // ��ȡǰ�˴��ݹ�����ҳ��
	        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
	        // ��ȡǰ�˴�������ÿҳҪ�󷵻ص���Ʒ����
	        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

	        // ��session�л�ȡshop��Ϣ����Ҫ�ǻ�ȡshopId ������ǰ̨�Ĳ����������ܱ�֤��ȫ
	        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
	        // ��ֵ�ж�
	        if ((pageIndex > -1) && (pageSize > -1) && currentShop != null && currentShop.getShopId() != null) {
	            // ��ȡǰ̨���ܴ��ݹ�������Ҫ�����������������Ƿ���Ҫ��ĳ����Ʒ����Լ�������Ʒ����ģ����ѯĳ�������µ���Ʒ
	            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
	            String productName = HttpServletRequestUtil.getString(request, "productName");
	            // ƴװ��ѯ����������ǰ�˴���������������
	            Product productCondition = compactProductCondition4Search(currentShop.getShopId(), productCategoryId, productName);
	            // ���÷���
	            ProductExecution pe = productService.queryProductionList(productCondition, pageIndex, pageSize);
	            // ��������ظ�ǰ̨
	            modelMap.put("productList", pe.getProductList());
	            modelMap.put("count", pe.getCount());
	            modelMap.put("success", true);
	        } else {
	            modelMap.put("success", false);
	            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
	        }
	        return modelMap;
	    }

	private Product compactProductCondition4Search(Long shopId, long productCategoryId, String productName) {
		Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
	}
}
