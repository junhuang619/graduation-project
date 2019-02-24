package com.jh.xysp.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jh.xysp.BaseTest;
import com.jh.xysp.dto.ImageHolder;
import com.jh.xysp.dto.ShopExecution;
import com.jh.xysp.entity.Area;
import com.jh.xysp.entity.PersonInfo;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.entity.ShopCategory;
import com.jh.xysp.enums.ShopStateEnum;
import com.jh.xysp.exception.ShopOperationException;


public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException,FileNotFoundException{
		Shop shop=new Shop();
		shop.setShopId(9L);
		shop.setShopName("修改后咖啡店");
		File shopImg = new File("F:/eclipse-image/new1.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder=new ImageHolder(is, "new1.jpg");
		ShopExecution shopEexcution =shopService.modifyShop(shop, imageHolder);
		System.out.println("新地址："+shopEexcution.getShop().getShopImg());
	}
	
	@Test
	@Ignore
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        personInfo.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);
        shop.setShoparea(area);
        shop.setShopowner(personInfo);
        shop.setShopCategory(shopCategory);
        shop.setShopName("小吃店");
        shop.setShopDesc("胖字的小吃店");
        shop.setShopAddr("西门");
		shop.setShopPhone("123456789");
		shop.setShopCreateTime(new Date());
		shop.setShopAdvice("审核中..");
        shop.setShopEnableStatus(ShopStateEnum.CHECK.getState());
        File shopImg = new File("F:/eclipse-image/beijing.jpg");
        InputStream is=new FileInputStream(shopImg);
        ImageHolder imageHolder=new ImageHolder(is, shopImg.getName());
        ShopExecution se = shopService.addShop(shop,imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
    }
	@Test
	public void testGetShopList() {
		Shop shopCondition=new Shop();
		ShopCategory sc=new ShopCategory();
		sc.setShopCategoryId(2L);
		shopCondition.setShopCategory(sc);
		ShopExecution se=shopService.getShopList(shopCondition, 1, 2);
		System.out.println(se.getShopList().size());
		System.out.println(se.getCount());
	}
}
