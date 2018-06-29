package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 牛牛 模式:统一摇号返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCCowSingleSwing extends GCMessage{
	
	/** 用户的ID */
	private long passportId;
	/** 名称用 int类型表示 */
	private int cowNameInt;
	/** 色子的值的集合 */
	private int[] diceValues;
	/** 需要被标红的色子的值的集合 */
	private int[] redDiceValues;

	public GCCowSingleSwing (){
	}
	
	public GCCowSingleSwing (
			long passportId,
			int cowNameInt,
			int[] diceValues,
			int[] redDiceValues ){
			this.passportId = passportId;
			this.cowNameInt = cowNameInt;
			this.diceValues = diceValues;
			this.redDiceValues = redDiceValues;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		passportId = readLong();
		cowNameInt = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		diceValues = new int[count];
		for(int i=0; i<count; i++){
			diceValues[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		redDiceValues = new int[count];
		for(int i=0; i<count; i++){
			redDiceValues[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(passportId);
		writeInteger(cowNameInt);
		writeShort(diceValues.length);
		for(int i=0; i<diceValues.length; i++){
			writeInteger(diceValues[i]);
		}
		writeShort(redDiceValues.length);
		for(int i=0; i<redDiceValues.length; i++){
			writeInteger(redDiceValues[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_COW_SINGLE_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COW_SINGLE_SWING";
	}

	public long getPassportId(){
		return passportId;
	}
		
	public void setPassportId(long passportId){
		this.passportId = passportId;
	}

	public int getCowNameInt(){
		return cowNameInt;
	}
		
	public void setCowNameInt(int cowNameInt){
		this.cowNameInt = cowNameInt;
	}

	public int[] getDiceValues(){
		return diceValues;
	}

	public void setDiceValues(int[] diceValues){
		this.diceValues = diceValues;
	}	

	public int[] getRedDiceValues(){
		return redDiceValues;
	}

	public void setRedDiceValues(int[] redDiceValues){
		this.redDiceValues = redDiceValues;
	}	
}