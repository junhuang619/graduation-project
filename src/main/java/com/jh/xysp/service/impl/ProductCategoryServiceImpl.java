package com.jh.xysp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jh.xysp.dao.ProductCategoryDao;
import com.jh.xysp.dao.ProductDao;
import com.jh.xysp.dto.ProductCategoryExecution;
import com.jh.xysp.entity.ProductCategory;
import com.jh.xysp.enums.ProductCategoryStateEnum;
import com.jh.xysp.exception.ProductCategoryOperationException;
import com.jh.xysp.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Autowired
	private ProductDao productDao;

	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	@Transactional
	public ProductCategoryExecution addProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException {
		// 非空判断
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				// 批量增加ProductCategory
				int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectNum > 0) {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategoryList,
							effectNum);
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchAddProductCategory Error:" + e.getMessage());
			}
		} else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPETY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException {
		// 第一步 需要先将该商品目录下的商品的类别Id置为空
		try {
            int effectNum = productDao.updateProductCategoryToNull(productCategoryId, shopId);
            if (effectNum < 0) {
                throw new ProductCategoryOperationException("商品类别更新失败");
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException(e.getMessage());
        }
		 // 第二步 删除该商品目录
		try {
			int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if (effectNum > 0) {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			} else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.INNER_ERROR);
			}
		} catch (Exception e) {
			throw new ProductCategoryOperationException(e.getMessage());
		}
	}
}
