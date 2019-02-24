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
                    throw new ProductOperationException("商品创建失败");
                }
                // 如果添加商品成功，继续处理商品详情图片，并写入jh_product_img
                if (prodImgDetailList != null && prodImgDetailList.size() > 0) {
                    addProductDetailImgs(product, prodImgDetailList);
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
			} catch (Exception e) {
				throw new ProductOperationException("商品创建失败：" + e.getMessage());
			}
		}else {
			return new ProductExecution(ProductStateEnum.NULL_PARAMETER);
		}
	}

	private void addProductImg(Product product, ImageHolder imageHolder) {
		// 根据shopId获取图片存储的相对路径
        String relativePath = PathUtil.getShopImagePath(product.getShop().getShopId());
        // 添加图片到指定的目录
        String relativeAddr = ImageUtil.generateThumbnails(imageHolder, relativePath);
        // 将relativeAddr设置给product
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
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
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
            // 设置默认的属性
            product.setLastEditTime(new Date());

            // Step1. 处理缩略图
            if (imageHolder != null) {
                Product tempProduct = productDao.selectProductById(product.getProductId());
                // 1.1 删除旧的缩略图
                if (tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                // 1.2 添加新的缩略图
                addProductImg(product, imageHolder);
            }

            // Step2. 处理商品详情

            // 如果添加商品成功，继续处理商品详情图片，并写入tb_product_img
            if (prodImgDetailList != null && prodImgDetailList.size() > 0) {
                // 2.1 删除库表中productId对应的tb_product_img的信息
                deleteProductImgs(product.getProductId());
                // 2.2 处理商品详情图片，并写入tb_product_img
                addProductDetailImgs(product, prodImgDetailList);
            }
            try {
                // Step3.更新tb_product
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("商品更新失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("商品更新失败：" + e.getMessage());
            }

        } else {
            return new ProductExecution(ProductStateEnum.NULL_PARAMETER);
        }
	}

	private void deleteProductImgs(Long productId) {
		// 获取该商铺下对应的productImg信息
        List<ProductImg> productImgList = productImgDao.selectProductImgList(productId);
        // 遍历删除该目录下的全部文件
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        // 删除tb_product_img中该productId对应的记录
        productImgDao.deleteProductImgById(productId);
		
	}

	@Override
	public ProductExecution queryProductionList(Product productCondition, int pageIndex, int pageSize)
			throws ProductOperationException {
		List<Product> productList = null;
        int count = 0;
        try {
            // 将pageIndex 转换为Dao层识别的rowIndex
            int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            // 调用Dao层获取productList和总量
            productList = productDao.selectProductList(productCondition, rowIndex, pageSize);
            count = productDao.selectCountProduct(productCondition);
        } catch (Exception e) {
            e.printStackTrace();
            new ProductExecution(ProductStateEnum.INNER_ERROR);
        }
        return new ProductExecution(ProductStateEnum.SUCCESS, productList, count);
	}
}
