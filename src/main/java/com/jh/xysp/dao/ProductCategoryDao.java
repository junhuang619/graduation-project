package com.jh.xysp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.ProductCategory;

public interface ProductCategoryDao {
	/**通过shopid查询店铺商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);
	
	/**批量新增商品类别
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	/**删除特定shop下的productCategory
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 */
	int deleteProductCategory(@Param("productCategoryId") Long productCategoryId, @Param("shopId") Long shopId);
}
