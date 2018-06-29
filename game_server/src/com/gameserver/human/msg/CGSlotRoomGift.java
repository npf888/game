package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 老虎机房间发礼物
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotRoomGift extends CGMessage{
	
	/** 礼物ID */
	private int giftId;
	/** 发送类型 0 个人 1 全体 */
	private int send_type;
	/** 接收者ID在发送类型是0的时候有效果 */
	private long rece_playerId;
	
	public CGSlotRoomGift (){
	}
	
	public CGSlotRoomGift (
			int giftId,
			int send_type,
			long rece_playerId ){
			this.giftId = giftId;
			this.send_type = send_type;
			this.rece_playerId = rece_playerId;
	}
	
	@Override
	protected boolean readImpl() {
		giftId = readInteger();
		send_type = readInteger();
		rece_playerId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(giftId);
		writeInteger(send_type);
		writeLong(rece_playerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_ROOM_GIFT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_ROOM_GIFT";
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
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleSlotRoomGift(this.getSession().getPlayer(), this);
	}
}