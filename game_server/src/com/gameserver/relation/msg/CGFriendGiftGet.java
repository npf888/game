package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.relation.handler.RelationHandlerFactory;

/**
 * 领取礼物
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGFriendGiftGet extends CGMessage{
	
	/** 礼物id */
	private long giftId;
	
	public CGFriendGiftGet (){
	}
	
	public CGFriendGiftGet (
			long giftId ){
			this.giftId = giftId;
	}
	
	@Override
	protected boolean readImpl() {
		giftId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(giftId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_FRIEND_GIFT_GET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FRIEND_GIFT_GET";
	}

	public long getGiftId(){
		return giftId;
	}
		
	public void setGiftId(long giftId){
		this.giftId = giftId;
	}
	


	@Override
	public void execute() {
		RelationHandlerFactory.getHandler().handleFriendGiftGet(this.getSession().getPlayer(), this);
	}
}