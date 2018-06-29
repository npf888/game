package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 加入德州房间号
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGJoinTexasRoomId extends CGMessage{
	
	/** 房间id */
	private long roomId;
	
	public CGJoinTexasRoomId (){
	}
	
	public CGJoinTexasRoomId (
			long roomId ){
			this.roomId = roomId;
	}
	
	@Override
	protected boolean readImpl() {
		roomId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(roomId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_JOIN_TEXAS_ROOM_ID;
	}
	
	@Override
	public String getTypeName() {
		return "CG_JOIN_TEXAS_ROOM_ID";
	}

	public long getRoomId(){
		return roomId;
	}
		
	public void setRoomId(long roomId){
		this.roomId = roomId;
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleJoinTexasRoomId(this.getSession().getPlayer(), this);
	}
}