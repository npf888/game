package com.gameserver.shop.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端请求商品信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCShopList extends GCMessage{
	
	/** 商品信息 */
	private com.gameserver.shop.data.ShopInfoData[] shopList;

	public GCShopList (){
	}
	
	public GCShopList (
			com.gameserver.shop.data.ShopInfoData[] shopList ){
			this.shopList = shopList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		shopList = new com.gameserver.shop.data.ShopInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.shop.data.ShopInfoData obj = new com.gameserver.shop.data.ShopInfoData();
			obj.setShopId(readInteger());
			obj.setPrice(readInteger());
			shopList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(shopList.length);
		for(int i=0; i<shopList.length; i++){
			writeInteger(shopList[i].getShopId());
			writeInteger(shopList[i].getPrice());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOP_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOP_LIST";
	}

	public com.gameserver.shop.data.ShopInfoData[] getShopList(){
		return shopList;
	}

	public void setShopList(com.gameserver.shop.data.ShopInfoData[] shopList){
		this.shopList = shopList;
	}	
}