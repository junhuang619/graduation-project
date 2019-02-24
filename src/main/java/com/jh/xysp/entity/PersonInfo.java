package com.jh.xysp.entity;

import java.util.Date;

public class PersonInfo {
    private Long userId;
    private String userName;
    private String userProfileImg;
    private String userEmail;
    private String userGender;
    private Integer userEnbaleStatus;
    private Integer userType;
    private Date userCreateTime;
    private Date userLastEditTime;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserProfileImg() {
		return userProfileImg;
	}
	public void setUserProfileImg(String userProfileImg) {
		this.userProfileImg = userProfileImg;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public Integer getUserEnbaleStatus() {
		return userEnbaleStatus;
	}
	public void setUserEnbaleStatus(Integer userEnbaleStatus) {
		this.userEnbaleStatus = userEnbaleStatus;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Date getUserCreateTime() {
		return userCreateTime;
	}
	public void setUserCreateTime(Date userCreateTime) {
		this.userCreateTime = userCreateTime;
	}
	public Date getUserLastEditTime() {
		return userLastEditTime;
	}
	public void setUserLastEditTime(Date userLastEditTime) {
		this.userLastEditTime = userLastEditTime;
	}
    
}
