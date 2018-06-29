package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 叫号完毕
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCTalkBig extends GCMessage{
	
	/** 轮到哪个人(这个人的ID)叫号了 */
	private long WhoTurnPassportId;
	/** 当前叫号的是哪个人 */
	private long curPassportId;
	/** 用户叫的色子的数量 */
	private int callDiceNum;
	/** 用户叫的色子的值 */
	private int callDiceValue;
	/** 万能符是否叫过 0没有叫过，1叫过 */
	private int onePoint;

	public GCTalkBig (){
	}
	
	public GCTalkBig (
			long WhoTurnPassportId,
			long curPassportId,
			int callDiceNum,
			int callDiceValue,
			int onePoint ){
			this.WhoTurnPassportId = WhoTurnPassportId;
			this.curPassportId = curPassportId;
			this.callDiceNum = callDiceNum;
			this.callDiceValue = callDiceValue;
			this.onePoint = onePoint;
	}

	@Override
	protected boolean readImpl() {
		WhoTurnPassportId = readLong();
		curPassportId = readLong();
		callDiceNum = readInteger();
		callDiceValue = readInteger();
		onePoint = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(WhoTurnPassportId);
		writeLong(curPassportId);
		writeInteger(callDiceNum);
		writeInteger(callDiceValue);
		writeInteger(onePoint);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_TALK_BIG;
	}
	
	@Override
	public String getTypeName() {
		return "GC_TALK_BIG";
	}

	public long getWhoTurnPassportId(){
		return WhoTurnPassportId;
	}
		
	public void setWhoTurnPassportId(long WhoTurnPassportId){
		this.WhoTurnPassportId = WhoTurnPassportId;
	}

	public long getCurPassportId(){
		return curPassportId;
	}
		
	public void setCurPassportId(long curPassportId){
		this.curPassportId = curPassportId;
	}

	public int getCallDiceNum(){
		return callDiceNum;
	}
		
	public void setCallDiceNum(int callDiceNum){
		this.callDiceNum = callDiceNum;
	}

	public int getCallDiceValue(){
		return callDiceValue;
	}
		
	public void setCallDiceValue(int callDiceValue){
		this.callDiceValue = callDiceValue;
	}

	public int getOnePoint(){
		return onePoint;
	}
		
	public void setOnePoint(int onePoint){
		this.onePoint = onePoint;
	}
}