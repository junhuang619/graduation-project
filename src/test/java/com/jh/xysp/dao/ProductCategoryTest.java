package com.jh.xysp.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.jh.xysp.BaseTest;
import com.jh.xysp.entity.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryTest extends BaseTest{
    @Autowired
    ProductCategoryDao productCategoryDao;
    @Test
    public void testB_TestQueryByShopId()throws Exception{
    	long shopId=1L;
    	List<ProductCategory> productCategoryList=productCategoryDao.queryProductCategoryList(shopId);
    	System.out.println(productCategoryList.size());
    }
    @Test
    public void testA_BatchInsertProductCategory() {
    	ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("��Ʒ���1");
        productCategory1.setProductCategoryDesc("������Ʒ���1");
        productCategory1.setProductCategoryPriority(1);
        productCategory1.setProductCategoryCreateTime(new Date());
        productCategory1.setShopId(1L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("��Ʒ���2");
        productCategory2.setProductCategoryDesc("������Ʒ���2");
        productCategory2.setProductCategoryPriority(2);
        productCategory2.setProductCategoryCreateTime(new Date());
        productCategory2.setShopId(1L);
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);
        int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2, effectNum);
    }
    @Test
    public void testC_DeleteProductCategory() {
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(1L);
        // ����ѭ��ɾ��
        for (ProductCategory productCategory : productCategoryList) {
            if ("��Ʒ���1".equals(productCategory.getProductCategoryName()) || "��Ʒ���2".equals(productCategory.getProductCategoryName())) {
                int effectNum = productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(), 1L);
                assertEquals(1, effectNum);
            }
        }
    }
}
