package com.jh.xysp.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.Product;

public interface ProductDao {
	/**������Ʒ
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	
	/**����productId��ѯproduct
	 * @param productId
	 * @return
	 */
	Product selectProductById(long productId);
	
	/**�޸���Ʒ
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
	List<Product> selectProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
	int selectCountProduct(@Param("productCondition") Product productCondition);
	int updateProductCategoryToNull(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
