package com.gameserver.recharge.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获取mycard交易码
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCMycardAuthcode extends GCMessage{
	
	/** 返回结果 1 表示成功 0 表示失败 */
	private String returnCode;
	/** 交易码 */
	private String authCode;

	public GCMycardAuthcode (){
	}
	
	public GCMycardAuthcode (
			String returnCode,
			String authCode ){
			this.returnCode = returnCode;
			this.authCode = authCode;
	}

	@Override
	protected boolean readImpl() {
		returnCode = readString();
		authCode = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(returnCode);
		writeString(authCode);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MYCARD_AUTHCODE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MYCARD_AUTHCODE";
	}

	public String getReturnCode(){
		return returnCode;
	}
		
	public void setReturnCode(String returnCode){
		this.returnCode = returnCode;
	}

	public String getAuthCode(){
		return authCode;
	}
		
	public void setAuthCode(String authCode){
		this.authCode = authCode;
	}
}