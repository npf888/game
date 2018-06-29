package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州加入vip房间
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCJoinTexasVipFailed extends GCMessage{
	
	/** errorCode */
	private int errorCode;

	public GCJoinTexasVipFailed (){
	}
	
	public GCJoinTexasVipFailed (
			int errorCode ){
			this.errorCode = errorCode;
	}

	@Override
	protected boolean readImpl() {
		errorCode = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(errorCode);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_JOIN_TEXAS_VIP_FAILED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_JOIN_TEXAS_VIP_FAILED";
	}

	public int getErrorCode(){
		return errorCode;
	}
		
	public void setErrorCode(int errorCode){
		this.errorCode = errorCode;
	}
}