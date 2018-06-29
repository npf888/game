package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.item.handler.ItemHandlerFactory;

/**
 * 用金币购买道具
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooItemBuyByGold extends CGMessage{
	
	/** 目标色钟的itemId */
	private int itemId;
	
	public CGBazooItemBuyByGold (){
	}
	
	public CGBazooItemBuyByGold (
			int itemId ){
			this.itemId = itemId;
	}
	
	@Override
	protected boolean readImpl() {
		itemId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_ITEM_BUY_BY_GOLD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_ITEM_BUY_BY_GOLD";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}
	


	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleBazooItemBuyByGold(this.getSession().getPlayer(), this);
	}
}