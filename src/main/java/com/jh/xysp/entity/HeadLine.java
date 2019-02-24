package com.jh.xysp.entity;

import java.util.Date;

public class HeadLine {
	private Long lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer linePriority;
    private Integer lineEnableStatus;
    private Date lineCreateTime;
    private Date lineLastEditTime;
	public Long getLineId() {
		return lineId;
	}
	public void setLineId(Long lineId) {
		this.lineId = lineId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getLineLink() {
		return lineLink;
	}
	public void setLineLink(String lineLink) {
		this.lineLink = lineLink;
	}
	public String getLineImg() {
		return lineImg;
	}
	public void setLineImg(String lineImg) {
		this.lineImg = lineImg;
	}
	public Integer getLinePriority() {
		return linePriority;
	}
	public void setLinePriority(Integer linePriority) {
		this.linePriority = linePriority;
	}
	public Integer getLineEnableStatus() {
		return lineEnableStatus;
	}
	public void setLineEnableStatus(Integer lineEnableStatus) {
		this.lineEnableStatus = lineEnableStatus;
	}
	public Date getLineCreateTime() {
		return lineCreateTime;
	}
	public void setLineCreateTime(Date lineCreateTime) {
		this.lineCreateTime = lineCreateTime;
	}
	public Date getLineLastEditTime() {
		return lineLastEditTime;
	}
	public void setLineLastEditTime(Date lineLastEditTime) {
		this.lineLastEditTime = lineLastEditTime;
	}
    
}
