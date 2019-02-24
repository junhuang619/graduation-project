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
import com.jh.xysp.entity.Product;
import com.jh.xysp.entity.ProductCategory;
import com.jh.xysp.entity.Shop;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest{
	@Autowired
    ProductDao productDao;
    @Test
    @Ignore
    public void testA_InsertProdcut() {

        // ע����е������ϵ��ȷ����Щ�����ڶ�Ӧ�ı��еĴ���
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(11L);

        // ע����е������ϵ��ȷ����Щ�����ڶ�Ӧ�ı��еĴ���
        Shop shop = new Shop();
        shop.setShopId(1L);

        Product product = new Product();
        product.setProductName("������ƷѼ");
        product.setProductDesc("������ƷѼ");
        product.setImgAddr("/aaa/bbb");
        product.setNormalPrice("10");
        product.setPromotionPrice("8");
        product.setPriority(66);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(1);
        product.setProductCategory(productCategory);
        product.setShop(shop);

        int effectNum = productDao.insertProduct(product);
        assertEquals(1, effectNum);
    }

    @Test
    @Ignore
    public void testB_UpdateProduct() {

        // ע����е������ϵ��ȷ����Щ�����ڶ�Ӧ�ı��еĴ���
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(11L);

        // ע����е������ϵ��ȷ����Щ�����ڶ�Ӧ�ı��еĴ���
        Shop shop = new Shop();
        shop.setShopId(1L);

        Product product = new Product();
        product.setProductName("�޸ĺ����ƷѼ");
        product.setProductDesc("�޸ĺ����ƷѼ");
        product.setImgAddr("/mmm/ddd");
        product.setNormalPrice("350");
        product.setPromotionPrice("300");
        product.setPriority(66);
        product.setLastEditTime(new Date());
        product.setEnableStatus(1);

        product.setProductCategory(productCategory);
        product.setShop(shop);

        // ����productId
        product.setProductId(2L);

        int effectNum = productDao.updateProduct(product);
        assertEquals(1, effectNum);
    }
    @Test
    @Ignore
    public void testC_SelectProductListAndCount() {
        List<Product> productList = new ArrayList<Product>();
        Product productCondition = new Product();

        productList = productDao.selectProductList(productCondition, 0, 5);
        assertEquals(5, productList.size());
        
        int count=productDao.selectCountProduct(productCondition);
        assertEquals(5, count);
        productCondition.setProductName("����");
        productList = productDao.selectProductList(productCondition, 0, 3);
        assertEquals(3, productList.size());
        count=productDao.selectCountProduct(productCondition);
        assertEquals(3, count);
    }
    @Test
    public void testUpdateProductCategory2Null() {
        long productCategoryId = 11L;
        long shopId = 1L;
        int effectNum = productDao.updateProductCategoryToNull(productCategoryId, shopId);
        assertEquals(4, effectNum);

        productCategoryId = 1L;
        effectNum = productDao.updateProductCategoryToNull(productCategoryId, shopId);
        assertEquals(1, effectNum);

    }
}
