package com.gameserver.shop.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.shop.handler.ShopHandlerFactory;

/**
 * 客户端请求商品信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGShopList extends CGMessage{
	
	
	public CGShopList (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOP_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOP_LIST";
	}
	


	@Override
	public void execute() {
		ShopHandlerFactory.getHandler().handleShopList(this.getSession().getPlayer(), this);
	}
}