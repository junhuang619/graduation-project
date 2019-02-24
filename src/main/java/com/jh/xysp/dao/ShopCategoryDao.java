package com.jh.xysp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.ShopCategory;

public interface ShopCategoryDao {
	List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
