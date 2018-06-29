package com.gameserver.shop.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 客户端购买成功
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBuyItem extends GCMessage{
	
	/** 商品id */
	private int shopId;

	public GCBuyItem (){
	}
	
	public GCBuyItem (
			int shopId ){
			this.shopId = shopId;
	}

	@Override
	protected boolean readImpl() {
		shopId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(shopId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_ITEM";
	}

	public int getShopId(){
		return shopId;
	}
		
	public void setShopId(int shopId){
		this.shopId = shopId;
	}
}