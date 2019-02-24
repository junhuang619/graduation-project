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
		//判断店铺是否为空
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			//给店铺信息赋值，初始值
			shop.setShopEnableStatus(0);
			shop.setShopCreateTime(new Date());
			shop.setShopLastEditTime(new Date());
			//添加用户信息
			int effectedNum=shopDao.insertShop(shop);
			if(effectedNum <=0) {
				throw new ShopOperationException("店铺创建失败");
			}else {
				
				if(thumbnail.getImage()!=null) {
					//存储图片
					try {
						addShopImg(shop,thumbnail);
					}catch(Exception e){
						throw new ShopOperationException("addShopImg error:"+e.getMessage());
					}
					//更新店铺的图片地址
					effectedNum= shopDao.updateShop(shop);
					if(effectedNum <=0) {
						throw new ShopOperationException("更新图片地址失败:");
					}
				}
			}
		}catch(Exception e){
			throw new RuntimeException("addShop error:"+e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}

	private void addShopImg(Shop shop, ImageHolder thumbnail) {
		//获取shop图片目的的相对值路径
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
			//判断是否需要处理图片
			if(thumbnail.getImage()!=null&&thumbnail.getImageName()!=null&& !"".equals(thumbnail.getImageName())) {
				Shop tempShop=shopDao.queryByShopId(shop.getShopId());
				if(tempShop.getShopImg()!=null) {
					ImageUtil.deleteFileOrPath(tempShop.getShopImg());
				}
				addShopImg(shop,thumbnail);
			}
			//更新店铺信息
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
