package com.jh.xysp.dto;

public class Result<T> {
	// 是否成功的标识
    private boolean success;
    // 成功时返回的数据
    private T data;
    // 错误码
    private int errorCode;
    // 错误信息
    private String errMsg;
	public Result() {
		super();
	}
	public Result(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}
	public Result(boolean success, int errorCode, String errMsg) {
		super();
		this.success = success;
		this.errorCode = errorCode;
		this.errMsg = errMsg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
    
}
