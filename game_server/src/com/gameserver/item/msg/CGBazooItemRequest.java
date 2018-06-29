package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.item.handler.ItemHandlerFactory;

/**
 * 请求背包
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBazooItemRequest extends CGMessage{
	
	/** 请求哪种类型的 1:所有,2:色钟，3：红包，4：礼物 */
	private int itemType;
	
	public CGBazooItemRequest (){
	}
	
	public CGBazooItemRequest (
			int itemType ){
			this.itemType = itemType;
	}
	
	@Override
	protected boolean readImpl() {
		itemType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAZOO_ITEM_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAZOO_ITEM_REQUEST";
	}

	public int getItemType(){
		return itemType;
	}
		
	public void setItemType(int itemType){
		this.itemType = itemType;
	}
	


	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleBazooItemRequest(this.getSession().getPlayer(), this);
	}
}