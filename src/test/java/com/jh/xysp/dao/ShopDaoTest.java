package com.jh.xysp.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jh.xysp.BaseTest;
import com.jh.xysp.entity.Area;
import com.jh.xysp.entity.PersonInfo;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	@Test
	@Ignore
	public void testInsertShop(){
		Shop shop= new Shop();
		PersonInfo owner = new PersonInfo();
		Area area= new Area();
		ShopCategory shopcategory= new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopcategory.setShopCategoryId(1L);
		shop.setShopowner(owner);
		shop.setShoparea(area);
		shop.setShopCategory(shopcategory);
		shop.setShopName("假商店");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setShopPhone("test");
		shop.setShopCreateTime(new Date());
		shop.setShopEnableStatus(1);
		shop.setShopAdvice("审核中..");
		int effectedNum=shopDao.insertShop(shop);
		assertEquals(1,effectedNum);
	}
	
	@Test
	@Ignore
	public void testUpdateShop(){
		Shop shop= new Shop();
		shop.setShopId(1L);
		shop.setShopDesc("更新");
		shop.setShopLastEditTime(new Date());
		int effectedNum=shopDao.updateShop(shop);
		assertEquals(1,effectedNum);
	}
	
	@Test
	@Ignore
	public void testQueryByShopId(){
		Long shopId=6L;
		Shop shop=shopDao.queryByShopId(shopId);
		System.out.println("areaId:"+shop.getShoparea().getAreaId());
		System.out.println("areaName:"+shop.getShoparea().getAreaName());
	}
	
	@Test
	public void testQueryShopListAndCount() {
		Shop shopCondition=new Shop();
		ShopCategory childCategory=new ShopCategory();
		ShopCategory parentCategory=new ShopCategory();
		parentCategory.setShopCategoryId(1L);
		childCategory.setShopCategoryParentId(parentCategory);
		shopCondition.setShopCategory(childCategory);
		List<Shop> shopList=shopDao.queryShopList(shopCondition, 0, 8);
		int count=shopDao.queryShopCount(shopCondition);
		System.out.println("店铺列表大小"+shopList.size());
		System.out.println("店铺总数"+count);
		for (Shop shop : shopList) {
            System.out.println(shop.toString());
        }
	}
}
