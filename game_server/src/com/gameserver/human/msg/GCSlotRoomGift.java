package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎机房间发礼物
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotRoomGift extends GCMessage{
	
	/** 发送者ID */
	private long send_playerId;
	/** 礼物ID */
	private int giftId;
	/** 发送类型 0 个人 1 全体 */
	private int send_type;
	/** 接收者ID在发送类型是0的时候有效果 */
	private long rece_playerId;

	public GCSlotRoomGift (){
	}
	
	public GCSlotRoomGift (
			long send_playerId,
			int giftId,
			int send_type,
			long rece_playerId ){
			this.send_playerId = send_playerId;
			this.giftId = giftId;
			this.send_type = send_type;
			this.rece_playerId = rece_playerId;
	}

	@Override
	protected boolean readImpl() {
		send_playerId = readLong();
		giftId = readInteger();
		send_type = readInteger();
		rece_playerId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(send_playerId);
		writeInteger(giftId);
		writeInteger(send_type);
		writeLong(rece_playerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_ROOM_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_ROOM_GIFT";
	}

	public long getSend_playerId(){
		return send_playerId;
	}
		
	public void setSend_playerId(long send_playerId){
		this.send_playerId = send_playerId;
	}

	public int getGiftId(){
		return giftId;
	}
		
	public void setGiftId(int giftId){
		this.giftId = giftId;
	}

	public int getSend_type(){
		return send_type;
	}
		
	public void setSend_type(int send_type){
		this.send_type = send_type;
	}

	public long getRece_playerId(){
		return rece_playerId;
	}
		
	public void setRece_playerId(long rece_playerId){
		this.rece_playerId = rece_playerId;
	}
}