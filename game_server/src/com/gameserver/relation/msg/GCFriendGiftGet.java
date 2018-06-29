package com.gameserver.relation.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 领取礼物
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFriendGiftGet extends GCMessage{
	
	/** 礼物id */
	private long giftId;

	public GCFriendGiftGet (){
	}
	
	public GCFriendGiftGet (
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
		return MessageType.GC_FRIEND_GIFT_GET;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_GIFT_GET";
	}

	public long getGiftId(){
		return giftId;
	}
		
	public void setGiftId(long giftId){
		this.giftId = giftId;
	}
}