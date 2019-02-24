package com.jh.xysp.dao;

import java.util.List;

import com.jh.xysp.entity.ProductImg;

public interface ProductImgDao {
	
	/**��������ͼƬ
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	
	/**����productId��ѯ���̶�Ӧ��ͼƬ������Ϣ
	 * @param productId
	 * @return
	 */
	List<ProductImg> selectProductImgList(long productId);
	
	/**ɾ����Ʒ��Ӧ����Ʒ����ͼƬ
	 * @param productId
	 * @return
	 */
	int deleteProductImgById(long productId);
}
