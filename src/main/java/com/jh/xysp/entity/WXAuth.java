package com.jh.xysp.entity;

import java.util.Date;

public class WXAuth {
	private Long wxAuthId;
    private String wxopenId;
    private Date wxcreateTime;
    private PersonInfo psersonInfo;
	public Long getWxAuthId() {
		return wxAuthId;
	}
	public void setWxAuthId(Long wxAuthId) {
		this.wxAuthId = wxAuthId;
	}
	public String getWxopenId() {
		return wxopenId;
	}
	public void setWxopenId(String wxopenId) {
		this.wxopenId = wxopenId;
	}
	public Date getWxcreateTime() {
		return wxcreateTime;
	}
	public void setWxcreateTime(Date wxcreateTime) {
		this.wxcreateTime = wxcreateTime;
	}
	public PersonInfo getPsersonInfo() {
		return psersonInfo;
	}
	public void setPsersonInfo(PersonInfo psersonInfo) {
		this.psersonInfo = psersonInfo;
	}
    
}
