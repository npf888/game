package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 创建房间（创建的房间都是私人房间）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomCreate extends GCMessage{
	
	/** 创建房间是否成功，0：成功，1：失败 */
	private int isSuccess;

	public GCRoomCreate (){
	}
	
	public GCRoomCreate (
			int isSuccess ){
			this.isSuccess = isSuccess;
	}

	@Override
	protected boolean readImpl() {
		isSuccess = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(isSuccess);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_CREATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_CREATE";
	}

	public int getIsSuccess(){
		return isSuccess;
	}
		
	public void setIsSuccess(int isSuccess){
		this.isSuccess = isSuccess;
	}
}