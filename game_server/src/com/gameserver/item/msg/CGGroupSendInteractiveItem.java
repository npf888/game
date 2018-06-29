package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.item.handler.ItemHandlerFactory;

/**
 * 群体发送互动道具
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGroupSendInteractiveItem extends CGMessage{
	
	/** 互动道具id */
	private int itemId;
	
	public CGGroupSendInteractiveItem (){
	}
	
	public CGGroupSendInteractiveItem (
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
		return MessageType.CG_GROUP_SEND_INTERACTIVE_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GROUP_SEND_INTERACTIVE_ITEM";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}
	


	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleGroupSendInteractiveItem(this.getSession().getPlayer(), this);
	}
}