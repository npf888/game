package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 梭哈  模式:选择色子（某个）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCShowHandSingleSwich extends GCMessage{
	
	/** 谁选择的色子 */
	private long passportId;
	/** 用户选择的某个色子的索引 */
	private int diceIndex;

	public GCShowHandSingleSwich (){
	}
	
	public GCShowHandSingleSwich (
			long passportId,
			int diceIndex ){
			this.passportId = passportId;
			this.diceIndex = diceIndex;
	}

	@Override
	protected boolean readImpl() {
		passportId = readLong();
		diceIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(passportId);
		writeInteger(diceIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_HAND_SINGLE_SWICH;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_HAND_SINGLE_SWICH";
	}

	public long getPassportId(){
		return passportId;
	}
		
	public void setPassportId(long passportId){
		this.passportId = passportId;
	}

	public int getDiceIndex(){
		return diceIndex;
	}
		
	public void setDiceIndex(int diceIndex){
		this.diceIndex = diceIndex;
	}
}