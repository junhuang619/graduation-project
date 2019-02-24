package com.jh.xysp.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jh.xysp.BaseTest;
import com.jh.xysp.entity.ShopCategory;

public class ShopCategoryDaoTest extends BaseTest{

	@Autowired
    ShopCategoryDao shopCategoryDao;
	@Test
    public void testQueryShopCategoryList() {
		List<ShopCategory> shopCategoryList=shopCategoryDao.queryShopCategory(null);
		System.out.println(shopCategoryList.size());
	}
}
