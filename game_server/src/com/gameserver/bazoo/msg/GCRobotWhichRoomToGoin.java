package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 告诉机器人 要进入哪个房间 
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRobotWhichRoomToGoin extends GCMessage{
	
	/** 用户ID */
	private long passportId;
	/** 要进入哪个房间 */
	private String roomNumber;

	public GCRobotWhichRoomToGoin (){
	}
	
	public GCRobotWhichRoomToGoin (
			long passportId,
			String roomNumber ){
			this.passportId = passportId;
			this.roomNumber = roomNumber;
	}

	@Override
	protected boolean readImpl() {
		passportId = readLong();
		roomNumber = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(passportId);
		writeString(roomNumber);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROBOT_WHICH_ROOM_TO_GOIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROBOT_WHICH_ROOM_TO_GOIN";
	}

	public long getPassportId(){
		return passportId;
	}
		
	public void setPassportId(long passportId){
		this.passportId = passportId;
	}

	public String getRoomNumber(){
		return roomNumber;
	}
		
	public void setRoomNumber(String roomNumber){
		this.roomNumber = roomNumber;
	}
}