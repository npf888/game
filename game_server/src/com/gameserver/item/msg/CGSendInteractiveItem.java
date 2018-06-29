package com.gameserver.item.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.item.handler.ItemHandlerFactory;

/**
 * 发送互动道具
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSendInteractiveItem extends CGMessage{
	
	/** 互动道具id */
	private int itemId;
	/** 玩家id */
	private long playerId;
	
	public CGSendInteractiveItem (){
	}
	
	public CGSendInteractiveItem (
			int itemId,
			long playerId ){
			this.itemId = itemId;
			this.playerId = playerId;
	}
	
	@Override
	protected boolean readImpl() {
		itemId = readInteger();
		playerId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemId);
		writeLong(playerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SEND_INTERACTIVE_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SEND_INTERACTIVE_ITEM";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	


	@Override
	public void execute() {
		ItemHandlerFactory.getHandler().handleSendInteractiveItem(this.getSession().getPlayer(), this);
	}
}