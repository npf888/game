package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 加入私人房间 返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomPriJoin extends GCMessage{
	
	/** 模式 */
	private int modeType;
	/** 创建房间是否成功，0：成功，1：失败 */
	private int isSuccess;

	public GCRoomPriJoin (){
	}
	
	public GCRoomPriJoin (
			int modeType,
			int isSuccess ){
			this.modeType = modeType;
			this.isSuccess = isSuccess;
	}

	@Override
	protected boolean readImpl() {
		modeType = readInteger();
		isSuccess = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(modeType);
		writeInteger(isSuccess);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_PRI_JOIN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_PRI_JOIN";
	}

	public int getModeType(){
		return modeType;
	}
		
	public void setModeType(int modeType){
		this.modeType = modeType;
	}

	public int getIsSuccess(){
		return isSuccess;
	}
		
	public void setIsSuccess(int isSuccess){
		this.isSuccess = isSuccess;
	}
}