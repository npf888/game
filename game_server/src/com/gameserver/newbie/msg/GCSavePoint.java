package com.gameserver.newbie.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 新手存盘点
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSavePoint extends GCMessage{
	
	/** 0 成功 1失败 */
	private int ret;

	public GCSavePoint (){
	}
	
	public GCSavePoint (
			int ret ){
			this.ret = ret;
	}

	@Override
	protected boolean readImpl() {
		ret = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(ret);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SAVE_POINT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SAVE_POINT";
	}

	public int getRet(){
		return ret;
	}
		
	public void setRet(int ret){
		this.ret = ret;
	}
}