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
		// �ǿ��ж�
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				// ��������ProductCategory
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
		// ��һ�� ��Ҫ�Ƚ�����ƷĿ¼�µ���Ʒ�����Id��Ϊ��
		try {
            int effectNum = productDao.updateProductCategoryToNull(productCategoryId, shopId);
            if (effectNum < 0) {
                throw new ProductCategoryOperationException("��Ʒ������ʧ��");
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException(e.getMessage());
        }
		 // �ڶ��� ɾ������ƷĿ¼
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
