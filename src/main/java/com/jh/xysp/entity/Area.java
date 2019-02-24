package com.jh.xysp.entity;

import java.util.Date;

public class Area {
	//ID
    private Integer areaId;
    //区域名
    private String areaName;
    //权重
    private Integer areaPriority;
    //创建时间
    private Date areaCreateTime;
    //更新时间
    private Date areaLastEditTime;
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getAreaPriority() {
		return areaPriority;
	}
	public void setAreaPriority(Integer areaPriority) {
		this.areaPriority = areaPriority;
	}
	public Date getAreaCreateTime() {
		return areaCreateTime;
	}
	public void setAreaCreateTime(Date areaCreateTime) {
		this.areaCreateTime = areaCreateTime;
	}
	public Date getAreaLastEditTime() {
		return areaLastEditTime;
	}
	public void setAreaLastEditTime(Date areaLastEditTime) {
		this.areaLastEditTime = areaLastEditTime;
	}
    
}
