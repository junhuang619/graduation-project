package com.jh.xysp.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jh.xysp.BaseTest;
import com.jh.xysp.dto.ImageHolder;
import com.jh.xysp.dto.ProductExecution;
import com.jh.xysp.entity.Product;
import com.jh.xysp.entity.ProductCategory;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.enums.ProductStateEnum;

public class ProductServiceTest extends BaseTest{
	@Autowired
	private ProductService productService;
	@Test
	@Ignore
	public void testAddProduct() throws Exception {
		Product product = new Product();
		ProductCategory productCategory = new ProductCategory();
		Shop shop = new Shop();
        productCategory.setProductCategoryId(11L);
        shop.setShopId(1L);
        product.setProductName("������Ʒ1");
        product.setProductDesc("������Ʒ1");
        product.setNormalPrice("10");
        product.setPromotionPrice("8");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        product.setProductCategory(productCategory);
        product.setShop(shop);
        
        File productFile = new File("F:/eclipse-image/new1.jpg");
        InputStream ins = new FileInputStream(productFile);
        ImageHolder imageHolder = new ImageHolder(ins, productFile.getName());
        
        List<ImageHolder> prodImgDetailList = new ArrayList<ImageHolder>();
        File productDetailFile1 = new File("F:/eclipse-image/new1.jpg");
        InputStream ins1 = new FileInputStream(productDetailFile1);
        ImageHolder imageHolder1 = new ImageHolder(ins1, productDetailFile1.getName());

        File productDetailFile2 = new File("F:/eclipse-image/new2.jpg");
        InputStream ins2 = new FileInputStream(productDetailFile2);
        ImageHolder imageHolder2 = new ImageHolder(ins2, productDetailFile2.getName());

        prodImgDetailList.add(imageHolder1);
        prodImgDetailList.add(imageHolder2);
        
        
        ProductExecution pe = productService.addProduct(product, imageHolder, prodImgDetailList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
	}
	@Test
    public void testModifyProduct() throws Exception {

        // ע����е������ϵ��ȷ����Щ�����ڶ�Ӧ�ı��еĴ���
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(11L);

        // ע����е������ϵ��ȷ����Щ�����ڶ�Ӧ�ı��еĴ���
        Shop shop = new Shop();
        shop.setShopId(1L);

        // ����Product
        Product product = new Product();
        product.setProductName("�²�����Ʒ");
        product.setProductDesc("�²�����Ʒ");

        product.setNormalPrice("100");
        product.setPromotionPrice("80");
        product.setPriority(20);
        product.setLastEditTime(new Date());
        product.setProductCategory(productCategory);
        product.setShop(shop);

        product.setProductId(5L);
        // ���� ��ƷͼƬ
        File productFile = new File("F:/eclipse-image/new1.jpg");
        InputStream ins = new FileInputStream(productFile);
        ImageHolder imageHolder = new ImageHolder(ins, productFile.getName());

        // ������Ʒ����ͼƬ
        List<ImageHolder> prodImgDetailList = new ArrayList<ImageHolder>();

        File productDetailFile1 = new File("F:/eclipse-image/new1.jpg");
        InputStream ins1 = new FileInputStream(productDetailFile1);
        ImageHolder imageHolder1 = new ImageHolder(ins1, productDetailFile1.getName());

        File productDetailFile2 = new File("F:/eclipse-image/new2.jpg");
        InputStream ins2 = new FileInputStream(productDetailFile2);
        ImageHolder imageHolder2 = new ImageHolder(ins2, productDetailFile2.getName());

        prodImgDetailList.add(imageHolder1);
        prodImgDetailList.add(imageHolder2);

        // ���÷���
        ProductExecution pe = productService.modifyProduct(product, imageHolder, prodImgDetailList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }
}
