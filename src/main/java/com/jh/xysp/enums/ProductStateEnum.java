package com.jh.xysp.enums;

public enum ProductStateEnum {
	SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_PARAMETER(-1002, "缺少参数");

    private int state;
    private String stateInfo;
	private ProductStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public static ProductStateEnum stateOf(int state) {
        for (ProductStateEnum stateEnum : values()) {
            if(stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }
}
