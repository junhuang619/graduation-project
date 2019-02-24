package com.jh.xysp.dto;

import java.util.List;

import com.jh.xysp.entity.ProductCategory;
import com.jh.xysp.enums.ProductCategoryStateEnum;

public class ProductCategoryExecution {
    private int state;
    private String stateInfo;
    private List<ProductCategory> productCategoryList;
    private int count;
	public ProductCategoryExecution() {
		super();
	}
	/**操作失败的时候返回的信息
	 * @param stateEnum
	 */
	public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum) {
		this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
	}
	public ProductCategoryExecution(ProductCategoryStateEnum productCategoryStateEnum, List<ProductCategory> productCategoryList, int count) {
        this.state = productCategoryStateEnum.getState();
        this.stateInfo = productCategoryStateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
        this.count = count;
    }
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}
	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}
	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
    
}
