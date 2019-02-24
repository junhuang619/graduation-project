package com.jh.xysp.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jh.xysp.dao.ShopDao;
import com.jh.xysp.dto.ImageHolder;
import com.jh.xysp.dto.ShopExecution;
import com.jh.xysp.entity.Shop;
import com.jh.xysp.enums.ShopStateEnum;
import com.jh.xysp.exception.ShopOperationException;
import com.jh.xysp.service.ShopService;
import com.jh.xysp.util.ImageUtil;
import com.jh.xysp.util.PageCalculator;
import com.jh.xysp.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService{
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
		//�жϵ����Ƿ�Ϊ��
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//��������Ϣ��ֵ����ʼֵ
			shop.setShopEnableStatus(0);
			shop.setShopCreateTime(new Date());
			shop.setShopLastEditTime(new Date());
			//����û���Ϣ
			int effectedNum=shopDao.insertShop(shop);
			if(effectedNum <=0) {
				throw new ShopOperationException("���̴���ʧ��");
			}else {
				
				if(thumbnail.getImage()!=null) {
					//�洢ͼƬ
					try {
						addShopImg(shop,thumbnail);
					}catch(Exception e){
						throw new ShopOperationException("addShopImg error:"+e.getMessage());
					}
					//���µ��̵�ͼƬ��ַ
					effectedNum= shopDao.updateShop(shop);
					if(effectedNum <=0) {
						throw new ShopOperationException("����ͼƬ��ַʧ��:");
					}
				}
			}
		}catch(Exception e){
			throw new RuntimeException("addShop error:"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		//��ȡshopͼƬĿ�ĵ����ֵ·��
		String dest=PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr= ImageUtil.generateThumbnails(thumbnail,dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail)
			throws ShopOperationException {
		if(shop==null||shop.getShopId()==null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}else {
			try{
			//�ж��Ƿ���Ҫ����ͼƬ
			if(thumbnail.getImage()!=null&&thumbnail.getImageName()!=null&& !"".equals(thumbnail.getImageName())) {
				Shop tempShop=shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg()!=null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop,thumbnail);
			}
			//���µ�����Ϣ
			shop.setShopLastEditTime(new Date());
			int effectedNum=shopDao.updateShop(shop);
			if(effectedNum<=0) {
				return new ShopExecution(ShopStateEnum.INNER_ERROR);
			}else {
				shop=shopDao.queryByShopId(shop.getShopId());
				return new ShopExecution(ShopStateEnum.SUCCESS,shop);
			}}catch(Exception e) {
				throw new ShopOperationException("modifyShop error:"+e.getMessage());
			}
		}
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) throws ShopOperationException {
		int rowIndex=PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList=shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count=shopDao.queryShopCount(shopCondition);
		ShopExecution se= new ShopExecution();
		if(shopList!=null) {
			se.setShopList(shopList);
			se.setCount(count);
		}else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}
	
}
