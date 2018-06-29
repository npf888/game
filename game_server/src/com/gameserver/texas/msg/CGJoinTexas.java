package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.texas.handler.TexasHandlerFactory;

/**
 * 加入德州房间类型
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGJoinTexas extends CGMessage{
	
	/** 房间id */
	private int roomId;
	
	public CGJoinTexas (){
	}
	
	public CGJoinTexas (
			int roomId ){
			this.roomId = roomId;
	}
	
	@Override
	protected boolean readImpl() {
		roomId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(roomId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_JOIN_TEXAS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_JOIN_TEXAS";
	}

	public int getRoomId(){
		return roomId;
	}
		
	public void setRoomId(int roomId){
		this.roomId = roomId;
	}
	


	@Override
	public void execute() {
		TexasHandlerFactory.getHandler().handleJoinTexas(this.getSession().getPlayer(), this);
	}
}