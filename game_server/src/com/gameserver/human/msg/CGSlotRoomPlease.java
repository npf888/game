package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.human.handler.HumanHandlerFactory;

/**
 * 邀请朋友加入老虎机房间
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGSlotRoomPlease extends CGMessage{
	
	/** 邀请的好友ID */
	private long req_playerId;
	
	public CGSlotRoomPlease (){
	}
	
	public CGSlotRoomPlease (
			long req_playerId ){
			this.req_playerId = req_playerId;
	}
	
	@Override
	protected boolean readImpl() {
		req_playerId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(req_playerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SLOT_ROOM_PLEASE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SLOT_ROOM_PLEASE";
	}

	public long getReq_playerId(){
		return req_playerId;
	}
		
	public void setReq_playerId(long req_playerId){
		this.req_playerId = req_playerId;
	}
	


	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleSlotRoomPlease(this.getSession().getPlayer(), this);
	}
}