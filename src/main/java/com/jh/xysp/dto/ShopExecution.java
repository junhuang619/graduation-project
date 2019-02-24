package com.jh.xysp.dto;

import java.util.List;

import com.jh.xysp.entity.Shop;
import com.jh.xysp.enums.ShopStateEnum;

public class ShopExecution {
	// ���״̬
	private int state;
	// ״̬��ʶ
	private String stateInfo;
	// ��������
	private int count;
	// ������shop����ɾ�ĵ���ʱʹ�ã�
	private Shop shop;
	// shop�б�
	private List<Shop> shopList;

	public ShopExecution() {

	}
	//���̲���ʧ�ܵĹ�����
	public ShopExecution(ShopStateEnum stateEnum){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
	}
	//���̲����ɹ��Ĺ�����
	public ShopExecution(ShopStateEnum stateEnum,Shop shop){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.shop=shop;
	}
	//���̲����ɹ��Ĺ�����
	public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList){
		this.state=stateEnum.getState();
		this.stateInfo=stateEnum.getStateInfo();
		this.shopList=shopList;
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
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
	
}
