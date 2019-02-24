package com.jh.xysp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.Shop;

public interface ShopDao {

	int insertShop(Shop shop);
	
	int updateShop(Shop shop);
	
	/**ͨ��shopId��ѯ����
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	
	/**��ҳ��ѯ���� ���������������֣�ģ����������״̬���������򣬵����������id��owner
	 * @param shopCondition
	 * @param rowIndex �ӵڼ��п�ʼȡ
	 * @param pageSize ���ص�����
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	
	/**����queryShopList����
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
