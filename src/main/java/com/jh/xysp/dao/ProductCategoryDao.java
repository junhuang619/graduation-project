package com.jh.xysp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.ProductCategory;

public interface ProductCategoryDao {
	/**ͨ��shopid��ѯ������Ʒ���
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	
	/**����������Ʒ���
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	/**ɾ���ض�shop�µ�productCategory
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId") Long productCategoryId, @Param("shopId") Long shopId);
}
