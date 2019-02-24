package com.jh.xysp.entity;

import java.util.Date;

public class ProductCategory {
	private Long productCategoryId;
    private Long shopId;
    private String productCategoryName;
    private String productCategoryDesc;
	private Integer productCategoryPriority;
    private Date productCategoryCreateTime;
    public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public String getProductCategoryDesc() {
		return productCategoryDesc;
	}
	public void setProductCategoryDesc(String productCategoryDesc) {
		this.productCategoryDesc = productCategoryDesc;
	}
	public Long getProductCategoryId() {
		return productCategoryId;
	}
	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}
	public String getProductCategoryName() {
		return productCategoryName;
	}
	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}
	public Integer getProductCategoryPriority() {
		return productCategoryPriority;
	}
	public void setProductCategoryPriority(Integer productCategoryPriority) {
		this.productCategoryPriority = productCategoryPriority;
	}
	public Date getProductCategoryCreateTime() {
		return productCategoryCreateTime;
	}
	public void setProductCategoryCreateTime(Date productCategoryCreateTime) {
		this.productCategoryCreateTime = productCategoryCreateTime;
	}
    
}
