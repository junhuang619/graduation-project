package com.jh.xysp.service;

import java.util.List;

import com.jh.xysp.entity.ShopCategory;

public interface ShopCategoryService {
	public static final String SCLISTKEY = "shopcategory";
	List<ShopCategory> getShopCategoryList(ShopCategory ShopCategoryCondition );
}
