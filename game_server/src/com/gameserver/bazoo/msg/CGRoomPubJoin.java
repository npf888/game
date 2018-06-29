package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 加入公共房间（根据房间号加入的）
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoomPubJoin extends CGMessage{
	
	/** 房间号 */
	private String roomNumber;
	
	public CGRoomPubJoin (){
	}
	
	public CGRoomPubJoin (
			String roomNumber ){
			this.roomNumber = roomNumber;
	}
	
	@Override
	protected boolean readImpl() {
		roomNumber = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(roomNumber);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ROOM_PUB_JOIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROOM_PUB_JOIN";
	}

	public String getRoomNumber(){
		return roomNumber;
	}
		
	public void setRoomNumber(String roomNumber){
		this.roomNumber = roomNumber;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleRoomPubJoin(this.getSession().getPlayer(), this);
	}
}