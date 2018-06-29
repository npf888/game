package com.gameserver.shop.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.shop.handler.ShopHandlerFactory;

/**
 * 客户端请求购买
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBuyItem extends CGMessage{
	
	/** 商品id */
	private int shopId;
	
	public CGBuyItem (){
	}
	
	public CGBuyItem (
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
		return MessageType.CG_BUY_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BUY_ITEM";
	}

	public int getShopId(){
		return shopId;
	}
		
	public void setShopId(int shopId){
		this.shopId = shopId;
	}
	


	@Override
	public void execute() {
		ShopHandlerFactory.getHandler().handleBuyItem(this.getSession().getPlayer(), this);
	}
}