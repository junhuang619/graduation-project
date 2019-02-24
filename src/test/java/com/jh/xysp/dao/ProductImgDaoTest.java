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
import com.jh.xysp.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest{
	@Autowired
	private ProductImgDao productImgDao;
	@Test
	@Ignore
    public void testBatchInsertProductImg() {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("/test/xxxx");
        productImg1.setImgDesc("商品详情图片1");
        productImg1.setImgPriority(20);
        productImg1.setImgCreateTime(new Date());
        productImg1.setProductId(1L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("/test/xxxx");
        productImg2.setImgDesc("商品详情图片2");
        productImg2.setImgPriority(19);
        productImg2.setImgCreateTime(new Date());
        productImg2.setProductId(1L);

        // 添加到productImgList中
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        // 调用接口批量新增商品详情图片
        int effectNum = productImgDao.batchInsertProductImg(productImgList);

        assertEquals(2, effectNum);
    }
	@Test
    public void testA_BatchInsertProductImg() {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("/xxx/xxx");
        productImg1.setImgDesc("商品详情图片1x");
        productImg1.setImgPriority(88);
        productImg1.setImgCreateTime(new Date());
        productImg1.setProductId(5L);

        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("/yyy/yyyy");
        productImg2.setImgDesc("商品详情图片2y");
        productImg2.setImgPriority(66);
        productImg2.setImgCreateTime(new Date());
        productImg2.setProductId(5L);

        // 添加到productImgList中
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        // 调用接口批量新增商品详情图片
        int effectNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectNum);
    }

    @Test
    public void testB_DeleteProductImgById() {
        Long productId = 5L;
        int effectNum = productImgDao.deleteProductImgById(productId);
        assertEquals(2, effectNum);
    }
}
