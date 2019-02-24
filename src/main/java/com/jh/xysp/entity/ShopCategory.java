package com.jh.xysp.entity;

import java.util.Date;

public class ShopCategory {
	private Long shopCategoryId;
	private String shopCategoryName;
    private String shopCategoryDesc;
    private String shopCategoryImg;
    private Integer shopCategoryPriority;
    private Date shopCategoryCreateTime;
    private Date shopCategoryLastEditTime;
    //上级ID
    private ShopCategory shopCategoryParentId;
    public Long getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(Long shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public String getShopCategoryName() {
		return shopCategoryName;
	}
	public void setShopCategoryName(String shopCategoryName) {
		this.shopCategoryName = shopCategoryName;
	}
	public String getShopCategoryDesc() {
		return shopCategoryDesc;
	}
	public void setShopCategoryDesc(String shopCategoryDesc) {
		this.shopCategoryDesc = shopCategoryDesc;
	}
	public String getShopCategoryImg() {
		return shopCategoryImg;
	}
	public void setShopCategoryImg(String shopCategoryImg) {
		this.shopCategoryImg = shopCategoryImg;
	}
	public Integer getShopCategoryPriority() {
		return shopCategoryPriority;
	}
	public void setShopCategoryPriority(Integer shopCategoryPriority) {
		this.shopCategoryPriority = shopCategoryPriority;
	}
	public Date getShopCategoryCreateTime() {
		return shopCategoryCreateTime;
	}
	public void setShopCategoryCreateTime(Date shopCategoryCreateTime) {
		this.shopCategoryCreateTime = shopCategoryCreateTime;
	}
	public Date getShopCategoryLastEditTime() {
		return shopCategoryLastEditTime;
	}
	public void setShopCategoryLastEditTime(Date shopCategoryLastEditTime) {
		this.shopCategoryLastEditTime = shopCategoryLastEditTime;
	}
	public ShopCategory getShopCategoryParentId() {
		return shopCategoryParentId;
	}
	public void setShopCategoryParentId(ShopCategory shopCategoryParentId) {
		this.shopCategoryParentId = shopCategoryParentId;
	}
}
