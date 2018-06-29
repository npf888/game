package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.CGMessage;
import com.gameserver.bazoo.handler.BazooHandlerFactory;

/**
 * 加入私人房间（根据房间号加入的）
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoomPriJoin extends CGMessage{
	
	/** 房间号 */
	private String roomNumber;
	/** 加入房间需要此房间的 密码 */
	private String password;
	
	public CGRoomPriJoin (){
	}
	
	public CGRoomPriJoin (
			String roomNumber,
			String password ){
			this.roomNumber = roomNumber;
			this.password = password;
	}
	
	@Override
	protected boolean readImpl() {
		roomNumber = readString();
		password = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(roomNumber);
		writeString(password);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ROOM_PRI_JOIN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ROOM_PRI_JOIN";
	}

	public String getRoomNumber(){
		return roomNumber;
	}
		
	public void setRoomNumber(String roomNumber){
		this.roomNumber = roomNumber;
	}

	public String getPassword(){
		return password;
	}
		
	public void setPassword(String password){
		this.password = password;
	}
	


	@Override
	public void execute() {
		BazooHandlerFactory.getHandler().handleRoomPriJoin(this.getSession().getPlayer(), this);
	}
}