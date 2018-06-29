package com.gameserver.shop.data;

import com.gameserver.shop.template.ShopTemplate;

/**
 * 商品信息
 * @author wayne
 *
 */
public class ShopInfoData {
	private int shopId;
	private int category;
	private int price;
	
	public int getShopId() {
		return shopId;
	}
	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public static ShopInfoData convertFrom(ShopTemplate shopTempalte)
	{
		ShopInfoData shopInfoData = new ShopInfoData();
		shopInfoData.setShopId(shopTempalte.getId());
		shopInfoData.setPrice(shopTempalte.getNum());
		shopInfoData.setCategory(shopTempalte.getCategory());
		return shopInfoData;
	}
}
