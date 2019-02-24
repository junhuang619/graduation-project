package com.jh.xysp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jh.xysp.dao.ProductDao;
import com.jh.xysp.dao.ProductImgDao;
import com.jh.xysp.dto.ImageHolder;
import com.jh.xysp.dto.ProductExecution;
import com.jh.xysp.entity.Product;
import com.jh.xysp.entity.ProductImg;
import com.jh.xysp.enums.ProductStateEnum;
import com.jh.xysp.exception.ProductOperationException;
import com.jh.xysp.service.ProductService;
import com.jh.xysp.util.ImageUtil;
import com.jh.xysp.util.PageCalculator;
import com.jh.xysp.util.PathUtil;

@Service
public class ProductServiceImpl implements	ProductService{
	@Autowired
	ProductDao productDao;
	@Autowired
	ProductImgDao productImgDao;
	
	@Override
    @Transactional
	public ProductExecution addProduct(Product product, ImageHolder imageHolder, List<ImageHolder> prodImgDetailList)
			throws ProductOperationException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null
				&& product.getProductCategory().getProductCategoryId() != null) {
			product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (imageHolder != null) {
            	addProductImg(product, imageHolder);
            }
            try {
            	int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0 ) {
                    throw new ProductOperationException("��Ʒ����ʧ��");
                }
                // ��������Ʒ�ɹ�������������Ʒ����ͼƬ����д��jh_product_img
                if (prodImgDetailList != null && prodImgDetailList.size() > 0) {
                    addProductDetailImgs(product, prodImgDetailList);
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationException("��Ʒ����ʧ�ܣ�" + e.getMessage());
			}
		}else {
			return new ProductExecution(ProductStateEnum.NULL_PARAMETER);
		}
	}

	private void addProductImg(Product product, ImageHolder imageHolder) {
		// ����shopId��ȡͼƬ�洢�����·��
        String relativePath = PathUtil.getShopImagePath(product.getShop().getShopId());
        // ���ͼƬ��ָ����Ŀ¼
        String relativeAddr = ImageUtil.generateThumbnails(imageHolder, relativePath);
        // ��relativeAddr���ø�product
        product.setImgAddr(relativeAddr);
	}
	private void addProductDetailImgs(Product product, List<ImageHolder> prodImgDetailList) {
		String relativePath = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<String> imgAddrList = ImageUtil.generateNormalImgs(prodImgDetailList, relativePath);
		if (imgAddrList != null && imgAddrList.size() > 0) {
            List<ProductImg> productImgList = new ArrayList<ProductImg>();
            for (String imgAddr : imgAddrList) {
                ProductImg productImg = new ProductImg();
                productImg.setImgAddr(imgAddr);
                productImg.setProductId(product.getProductId());
                productImg.setImgCreateTime(new Date());
                productImgList.add(productImg);
            }
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("������Ʒ����ͼƬʧ��");
                }
            } catch (Exception e) {
                throw new ProductOperationException("������Ʒ����ͼƬʧ��:" + e.toString());
            }
        }
	}

	@Override
	public Product queryProductById(long productId) {
		return productDao.selectProductById(productId);
	}

	@Override
	@Transactional
	public ProductExecution modifyProduct(Product product, ImageHolder imageHolder, List<ImageHolder> prodImgDetailList)
			throws ProductOperationException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null && product.getProductCategory().getProductCategoryId() != null) {
            // ����Ĭ�ϵ�����
            product.setLastEditTime(new Date());

            // Step1. ��������ͼ
            if (imageHolder != null) {
                Product tempProduct = productDao.selectProductById(product.getProductId());
                // 1.1 ɾ���ɵ�����ͼ
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                // 1.2 ����µ�����ͼ
                addProductImg(product, imageHolder);
            }

            // Step2. ������Ʒ����

            // ��������Ʒ�ɹ�������������Ʒ����ͼƬ����д��tb_product_img
            if (prodImgDetailList != null && prodImgDetailList.size() > 0) {
                // 2.1 ɾ�������productId��Ӧ��tb_product_img����Ϣ
                deleteProductImgs(product.getProductId());
                // 2.2 ������Ʒ����ͼƬ����д��tb_product_img
                addProductDetailImgs(product, prodImgDetailList);
            }
            try {
                // Step3.����tb_product
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("��Ʒ����ʧ��");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("��Ʒ����ʧ�ܣ�" + e.getMessage());
            }

        } else {
            return new ProductExecution(ProductStateEnum.NULL_PARAMETER);
        }
	}

	private void deleteProductImgs(Long productId) {
		// ��ȡ�������¶�Ӧ��productImg��Ϣ
        List<ProductImg> productImgList = productImgDao.selectProductImgList(productId);
        // ����ɾ����Ŀ¼�µ�ȫ���ļ�
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        // ɾ��tb_product_img�и�productId��Ӧ�ļ�¼
        productImgDao.deleteProductImgById(productId);
		
	}

	@Override
	public ProductExecution queryProductionList(Product productCondition, int pageIndex, int pageSize)
			throws ProductOperationException {
		List<Product> productList = null;
        int count = 0;
        try {
            // ��pageIndex ת��ΪDao��ʶ���rowIndex
            int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            // ����Dao���ȡproductList������
            productList = productDao.selectProductList(productCondition, rowIndex, pageSize);
            count = productDao.selectCountProduct(productCondition);
        } catch (Exception e) {
            e.printStackTrace();
            new ProductExecution(ProductStateEnum.INNER_ERROR);
        }
        return new ProductExecution(ProductStateEnum.SUCCESS, productList, count);
	}
}
