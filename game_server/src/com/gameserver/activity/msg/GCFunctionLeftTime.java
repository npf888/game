package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 功能
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCFunctionLeftTime extends GCMessage{
	
	/** 图片 */
	private String img;
	/** 剩余时间 */
	private long leftTime;
	/** 类型  1 筹码   2 物品  3 礼包  4 功能性付费 */
	private int[] functionType;

	public GCFunctionLeftTime (){
	}
	
	public GCFunctionLeftTime (
			String img,
			long leftTime,
			int[] functionType ){
			this.img = img;
			this.leftTime = leftTime;
			this.functionType = functionType;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		img = readString();
		leftTime = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		functionType = new int[count];
		for(int i=0; i<count; i++){
			functionType[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(img);
		writeLong(leftTime);
		writeShort(functionType.length);
		for(int i=0; i<functionType.length; i++){
			writeInteger(functionType[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FUNCTION_LEFT_TIME;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FUNCTION_LEFT_TIME";
	}

	public String getImg(){
		return img;
	}
		
	public void setImg(String img){
		this.img = img;
	}

	public long getLeftTime(){
		return leftTime;
	}
		
	public void setLeftTime(long leftTime){
		this.leftTime = leftTime;
	}

	public int[] getFunctionType(){
		return functionType;
	}

	public void setFunctionType(int[] functionType){
		this.functionType = functionType;
	}	
}