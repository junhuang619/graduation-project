package com.jh.xysp.service;

import java.io.InputStream;

import com.jh.xysp.dto.ImageHolder;
import com.jh.xysp.dto.ShopExecution;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.exception.ShopOperationException;

public interface ShopService {
	
	/**
	 * 注册商铺信息
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution addShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;

	/**
	 * 通过店铺id获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);
	/**
	 * 更新店铺信息
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop,ImageHolder thumbnail)throws ShopOperationException;

	/**获取商铺列表. 在这一个方法中同样的会调用查询总数的DAO层方法，封装到ShopExecution中
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) throws ShopOperationException;
}
