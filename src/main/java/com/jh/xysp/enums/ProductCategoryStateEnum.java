package com.jh.xysp.enums;

public enum ProductCategoryStateEnum {
	SUCCESS(1, "�����ɹ�"), INNER_ERROR(-1001, "����ʧ��"), NULL_SHOP(-1002, "Shop��ϢΪ��"), EMPETY_LIST(-1003, "��������ƷĿ¼��Ϣ");
	private int state ;
    private String stateInfo;
	private ProductCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public static ProductCategoryStateEnum stateOf(int index) {
        for (ProductCategoryStateEnum productCategoryStateEnum : values()) {
            if (productCategoryStateEnum.getState() == index) {
                return productCategoryStateEnum;
            }
        }
        return null;
    }
}
