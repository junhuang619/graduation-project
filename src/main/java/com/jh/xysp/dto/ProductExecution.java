package com.jh.xysp.dto;

import java.util.List;

import com.jh.xysp.entity.Product;
import com.jh.xysp.enums.ProductStateEnum;

public class ProductExecution {
	 /**
     * �������ص�״̬��Ϣ
     */
    private int state;
    /**
     * �������ص�״̬��Ϣ����
     */
    private String stateInfo;

    /**
     * �����ɹ�������
     */
    private int count;

    /**
     * ������������ѯ��Ʒ�б����ص�Product����
     */
    private List<Product> productList;

    /**
     * ��ɾ�ĵĲ������ص���Ʒ��Ϣ
     */
    private Product product;

	public ProductExecution() {
		super();
	}
	public ProductExecution(ProductStateEnum productStateEnum, List<Product> productList, int count) {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.productList = productList;
        this.count = count;
    }
	public ProductExecution(ProductStateEnum productStateEnum, Product product) {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
        this.product = product;
    }
	public ProductExecution(ProductStateEnum productStateEnum) {
        this.state = productStateEnum.getState();
        this.stateInfo = productStateEnum.getStateInfo();
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
