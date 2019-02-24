package com.jh.xysp.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.Product;

public interface ProductDao {
	/**增加商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	
	/**根据productId查询product
	 * @param productId
	 * @return
	 */
	Product selectProductById(long productId);
	
	/**修改商品
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
	List<Product> selectProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	int selectCountProduct(@Param("productCondition") Product productCondition);
	int updateProductCategoryToNull(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
