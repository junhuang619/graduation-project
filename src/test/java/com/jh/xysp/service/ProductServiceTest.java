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
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
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

        // 注意表中的外键关系，确保这些数据在对应的表中的存在
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(11L);

        // 注意表中的外键关系，确保这些数据在对应的表中的存在
        Shop shop = new Shop();
        shop.setShopId(1L);

        // 构造Product
        Product product = new Product();
        product.setProductName("新测试商品");
        product.setProductDesc("新测试商品");

        product.setNormalPrice("100");
        product.setPromotionPrice("80");
        product.setPriority(20);
        product.setLastEditTime(new Date());
        product.setProductCategory(productCategory);
        product.setShop(shop);

        product.setProductId(5L);
        // 构造 商品图片
        File productFile = new File("F:/eclipse-image/new1.jpg");
        InputStream ins = new FileInputStream(productFile);
        ImageHolder imageHolder = new ImageHolder(ins, productFile.getName());

        // 构造商品详情图片
        List<ImageHolder> prodImgDetailList = new ArrayList<ImageHolder>();

        File productDetailFile1 = new File("F:/eclipse-image/new1.jpg");
        InputStream ins1 = new FileInputStream(productDetailFile1);
        ImageHolder imageHolder1 = new ImageHolder(ins1, productDetailFile1.getName());

        File productDetailFile2 = new File("F:/eclipse-image/new2.jpg");
        InputStream ins2 = new FileInputStream(productDetailFile2);
        ImageHolder imageHolder2 = new ImageHolder(ins2, productDetailFile2.getName());

        prodImgDetailList.add(imageHolder1);
        prodImgDetailList.add(imageHolder2);

        // 调用服务
        ProductExecution pe = productService.modifyProduct(product, imageHolder, prodImgDetailList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }
}
