package com.jh.xysp.enums;

public enum ProductStateEnum {
	SUCCESS(1, "�����ɹ�"), INNER_ERROR(-1001, "����ʧ��"), NULL_PARAMETER(-1002, "ȱ�ٲ���");

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
