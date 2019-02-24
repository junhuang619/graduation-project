package com.jh.xysp.service;

import java.util.List;

import com.jh.xysp.dto.ProductCategoryExecution;
import com.jh.xysp.entity.ProductCategory;
import com.jh.xysp.exception.ProductCategoryOperationException;

public interface ProductCategoryService {
	List<ProductCategory> getProductCategoryList(long shopId);

	ProductCategoryExecution addProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;

	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException;
}
