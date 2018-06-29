package com.gameserver.newbie.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获取存盘点
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGetSavePoint extends GCMessage{
	
	/** 步骤id */
	private int step;

	public GCGetSavePoint (){
	}
	
	public GCGetSavePoint (
			int step ){
			this.step = step;
	}

	@Override
	protected boolean readImpl() {
		step = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(step);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_SAVE_POINT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_SAVE_POINT";
	}

	public int getStep(){
		return step;
	}
		
	public void setStep(int step){
		this.step = step;
	}
}