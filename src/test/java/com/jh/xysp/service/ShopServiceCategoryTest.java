package com.jh.xysp.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jh.xysp.BaseTest;
import com.jh.xysp.entity.ShopCategory;

public class ShopServiceCategoryTest extends BaseTest{
	@Autowired
    ShopCategoryService shopCategoryService;

    @Test
    public void testQueryShopCategory() {

        List<ShopCategory> shopCategories;

        // ��ѯ ShopCategoryΪnull�����������ѯparent_id is null
        shopCategories = shopCategoryService.getShopCategoryList(null);
        for (ShopCategory shopCategory2 : shopCategories) {
            System.out.println("-----||" + shopCategory2);
        }

        // ��ѯ ShopCategory��Ϊ�յ����
        ShopCategory shopCategory = new ShopCategory();
        shopCategories = shopCategoryService.getShopCategoryList(shopCategory);
        for (ShopCategory shopCategory2 : shopCategories) {
            System.out.println("----->>" + shopCategory2);
        }

        // ��ѯ��Ӧ�����µ�Ŀ¼
        ShopCategory parent = new ShopCategory();
        ShopCategory child = new ShopCategory();

        parent.setShopCategoryId(1L);
        child.setShopCategoryParentId(parent);

        shopCategories = shopCategoryService.getShopCategoryList(child);
        for (ShopCategory shopCategory2 : shopCategories) {
            System.out.println("-----**" + shopCategory2);
        }

    }
}
