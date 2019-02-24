package com.jh.xysp.enums;

public enum ShopStateEnum {
	CHECK(0, "�����"), OFFLINE(-1, "�Ƿ�����"), SUCCESS(1, "�����ɹ�"), 
	PASS(2, "ͨ����֤"), INNER_ERROR(-1001, "�ڲ�ϵͳ����"),
	NULL_SHOPID(-1002,"shopIDΪ��"),NULL_SHOP(-1003,"ShopΪ��");
	private int state;
	private String stateInfo;
	
	private ShopStateEnum(int state,String stateInfo) {
		this.state=state;
		this.stateInfo=stateInfo;
	}
	
	public static ShopStateEnum stateOf(int state) {
		for(ShopStateEnum stateEnum: values()) {
			if(stateEnum.getState()==state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
}
