package com.jh.xysp.entity;

import java.util.Date;

public class ProductImg {
    private Long productImgId;
    private String imgAddr;
    private String imgDesc;
    private Integer imgPriority;
    private Date imgCreateTime;
    private Long productId;
	public Long getProductImgId() {
		return productImgId;
	}
	public void setProductImgId(Long productImgId) {
		this.productImgId = productImgId;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getImgDesc() {
		return imgDesc;
	}
	public void setImgDesc(String imgDesc) {
		this.imgDesc = imgDesc;
	}
	public Integer getImgPriority() {
		return imgPriority;
	}
	public void setImgPriority(Integer imgPriority) {
		this.imgPriority = imgPriority;
	}
	public Date getImgCreateTime() {
		return imgCreateTime;
	}
	public void setImgCreateTime(Date imgCreateTime) {
		this.imgCreateTime = imgCreateTime;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
    
}
