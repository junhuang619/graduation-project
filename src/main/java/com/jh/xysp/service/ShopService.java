package com.jh.xysp.service;

import java.io.InputStream;

import com.jh.xysp.dto.ImageHolder;
import com.jh.xysp.dto.ShopExecution;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.exception.ShopOperationException;

public interface ShopService {
	
	/**
	 * ע��������Ϣ
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution addShop(Shop shop,ImageHolder thumbnail) throws ShopOperationException;

	/**
	 * ͨ������id��ȡ������Ϣ
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);
	/**
	 * ���µ�����Ϣ
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop,ImageHolder thumbnail)throws ShopOperationException;

	/**��ȡ�����б�. ����һ��������ͬ���Ļ���ò�ѯ������DAO�㷽������װ��ShopExecution��
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) throws ShopOperationException;
}
