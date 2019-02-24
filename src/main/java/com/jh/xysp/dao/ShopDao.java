package com.jh.xysp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jh.xysp.entity.Shop;

public interface ShopDao {

	int insertShop(Shop shop);
	
	int updateShop(Shop shop);
	
	/**通过shopId查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	
	/**分页查询店铺 ，条件：店铺名字（模糊），店铺状态，店铺区域，店铺类别，区域id，owner
	 * @param shopCondition
	 * @param rowIndex 从第几行开始取
	 * @param pageSize 返回的条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex,@Param("pageSize")int pageSize);
	
	/**返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
