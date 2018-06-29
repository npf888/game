package com.gameserver.common.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 系统提示消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSystemMessage extends GCMessage{
	
	/** 消息内容 */
	private int code;
	/** 消息显示类型 */
	private short showType;

	public GCSystemMessage (){
	}
	
	public GCSystemMessage (
			int code,
			short showType ){
			this.code = code;
			this.showType = showType;
	}

	@Override
	protected boolean readImpl() {
		code = readInteger();
		showType = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(code);
		writeShort(showType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SYSTEM_MESSAGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SYSTEM_MESSAGE";
	}

	public int getCode(){
		return code;
	}
		
	public void setCode(int code){
		this.code = code;
	}

	public short getShowType(){
		return showType;
	}
		
	public void setShowType(short showType){
		this.showType = showType;
	}
}