package com.gameserver.baccart.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.baccart.handler.BaccartHandlerFactory;

/**
 * 玩家加入
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGBaccartJoin extends CGMessage{
	
	/** 房间id */
	private int roomId;
	
	public CGBaccartJoin (){
	}
	
	public CGBaccartJoin (
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
		return MessageType.CG_BACCART_JOIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BACCART_JOIN";
	}

	public int getRoomId(){
		return roomId;
	}
		
	public void setRoomId(int roomId){
		this.roomId = roomId;
	}
	


	@Override
	public void execute() {
		BaccartHandlerFactory.getHandler().handleBaccartJoin(this.getSession().getPlayer(), this);
	}
}