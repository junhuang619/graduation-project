package com.jh.xysp.entity;

import java.util.Date;

public class Shop {
	private Long shopId;
    private String shopName;
    private String shopDesc;
    private String shopAddr;
    private String shopPhone;
    private String shopImg;
    private Integer shopPriority;
    private Date shopCreateTime;
    private Date shopLastEditTime;
    private Integer shopEnableStatus;
    private String shopAdvice;
    private Area shoparea;
    private PersonInfo shopowner;
    private ShopCategory shopCategory;
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopDesc() {
		return shopDesc;
	}
	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	public String getShopAddr() {
		return shopAddr;
	}
	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}
	public String getShopPhone() {
		return shopPhone;
	}
	public void setShopPhone(String shopPhone) {
		this.shopPhone = shopPhone;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	public Integer getShopPriority() {
		return shopPriority;
	}
	public void setShopPriority(Integer shopPriority) {
		this.shopPriority = shopPriority;
	}
	public Date getShopCreateTime() {
		return shopCreateTime;
	}
	public void setShopCreateTime(Date shopCreateTime) {
		this.shopCreateTime = shopCreateTime;
	}
	public Date getShopLastEditTime() {
		return shopLastEditTime;
	}
	public void setShopLastEditTime(Date shopLastEditTime) {
		this.shopLastEditTime = shopLastEditTime;
	}
	public Integer getShopEnableStatus() {
		return shopEnableStatus;
	}
	public void setShopEnableStatus(Integer shopEnableStatus) {
		this.shopEnableStatus = shopEnableStatus;
	}
	public String getShopAdvice() {
		return shopAdvice;
	}
	public void setShopAdvice(String shopAdvice) {
		this.shopAdvice = shopAdvice;
	}
	public Area getShoparea() {
		return shoparea;
	}
	public void setShoparea(Area shoparea) {
		this.shoparea = shoparea;
	}
	public PersonInfo getShopowner() {
		return shopowner;
	}
	public void setShopowner(PersonInfo shopowner) {
		this.shopowner = shopowner;
	}
	public ShopCategory getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
    
}
