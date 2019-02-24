package com.jh.xysp.dao;

import java.util.List;

import com.jh.xysp.entity.ProductImg;

public interface ProductImgDao {
	
	/**批量增加图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	/**根据productId查询商铺对应的图片详情信息
	 * @param productId
	 * @return
	 */
	List<ProductImg> selectProductImgList(long productId);
	
	/**删除商品对应的商品详情图片
	 * @param productId
	 * @return
	 */
	int deleteProductImgById(long productId);
}
