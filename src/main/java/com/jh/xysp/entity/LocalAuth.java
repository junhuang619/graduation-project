package com.jh.xysp.entity;

import java.util.Date;

public class LocalAuth {
	private Long localAuthId;
    private String localUserName;
    private String localPassWord;
    private Date localCreateTime;
    private Date localLastEditTime;
    private PersonInfo personInfo;
	public Long getLocalAuthId() {
		return localAuthId;
	}
	public void setLocalAuthId(Long localAuthId) {
		this.localAuthId = localAuthId;
	}
	public String getLocalUserName() {
		return localUserName;
	}
	public void setLocalUserName(String localUserName) {
		this.localUserName = localUserName;
	}
	public String getLocalPassWord() {
		return localPassWord;
	}
	public void setLocalPassWord(String localPassWord) {
		this.localPassWord = localPassWord;
	}
	public Date getLocalCreateTime() {
		return localCreateTime;
	}
	public void setLocalCreateTime(Date localCreateTime) {
		this.localCreateTime = localCreateTime;
	}
	public Date getLocalLastEditTime() {
		return localLastEditTime;
	}
	public void setLocalLastEditTime(Date localLastEditTime) {
		this.localLastEditTime = localLastEditTime;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
    
}
