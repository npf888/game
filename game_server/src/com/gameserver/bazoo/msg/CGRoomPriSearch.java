package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 搜索私人房间（根据房间号）
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoomPriSearch extends CGMessage{
	
	/** 房间号 */
	private String roomNumber;
	
	public CGRoomPriSearch (){
	}
	
	public CGRoomPriSearch (
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
		return MessageType.CG_ROOM_PRI_SEARCH;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROOM_PRI_SEARCH";
	}

	public String getRoomNumber(){
		return roomNumber;
	}
		
	public void setRoomNumber(String roomNumber){
		this.roomNumber = roomNumber;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleRoomPriSearch(this.getSession().getPlayer(), this);
	}
}