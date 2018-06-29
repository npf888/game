package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 统一摇色子得返回(由服务器来统一摇色子  同时表示新一轮开始 )
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBlackWhiteAllSwing extends GCMessage{
	
	/** 所有需要重摇的用户的ID */
	private long[] passportId;
	/** 所有需要重摇的用户剩余的色子数 */
	private int[] leftDiceNum;
	/** 当前用户的色子的值 */
	private int[] diceValues;
	/** 变化的倍数 */
	private int multiple;
	/** 红黑单双的数量 */
	private com.gameserver.bazoo.data.BlackWhiteNum blackWhiteNum;

	public GCBlackWhiteAllSwing (){
	}
	
	public GCBlackWhiteAllSwing (
			long[] passportId,
			int[] leftDiceNum,
			int[] diceValues,
			int multiple,
			com.gameserver.bazoo.data.BlackWhiteNum blackWhiteNum ){
			this.passportId = passportId;
			this.leftDiceNum = leftDiceNum;
			this.diceValues = diceValues;
			this.multiple = multiple;
			this.blackWhiteNum = blackWhiteNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		passportId = new long[count];
		for(int i=0; i<count; i++){
			passportId[i] = readLong();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		leftDiceNum = new int[count];
		for(int i=0; i<count; i++){
			leftDiceNum[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		diceValues = new int[count];
		for(int i=0; i<count; i++){
			diceValues[i] = readInteger();
		}
		multiple = readInteger();
		blackWhiteNum = new com.gameserver.bazoo.data.BlackWhiteNum();
					blackWhiteNum.setRed(readInteger());
							blackWhiteNum.setBlack(readInteger());
							blackWhiteNum.setSingle(readInteger());
							blackWhiteNum.setDoubles(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(passportId.length);
		for(int i=0; i<passportId.length; i++){
			writeLong(passportId[i]);
		}
		writeShort(leftDiceNum.length);
		for(int i=0; i<leftDiceNum.length; i++){
			writeInteger(leftDiceNum[i]);
		}
		writeShort(diceValues.length);
		for(int i=0; i<diceValues.length; i++){
			writeInteger(diceValues[i]);
		}
		writeInteger(multiple);
		writeInteger(blackWhiteNum.getRed());
		writeInteger(blackWhiteNum.getBlack());
		writeInteger(blackWhiteNum.getSingle());
		writeInteger(blackWhiteNum.getDoubles());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BLACK_WHITE_ALL_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BLACK_WHITE_ALL_SWING";
	}

	public long[] getPassportId(){
		return passportId;
	}

	public void setPassportId(long[] passportId){
		this.passportId = passportId;
	}	

	public int[] getLeftDiceNum(){
		return leftDiceNum;
	}

	public void setLeftDiceNum(int[] leftDiceNum){
		this.leftDiceNum = leftDiceNum;
	}	

	public int[] getDiceValues(){
		return diceValues;
	}

	public void setDiceValues(int[] diceValues){
		this.diceValues = diceValues;
	}	

	public int getMultiple(){
		return multiple;
	}
		
	public void setMultiple(int multiple){
		this.multiple = multiple;
	}

	public com.gameserver.bazoo.data.BlackWhiteNum getBlackWhiteNum(){
		return blackWhiteNum;
	}
		
	public void setBlackWhiteNum(com.gameserver.bazoo.data.BlackWhiteNum blackWhiteNum){
		this.blackWhiteNum = blackWhiteNum;
	}
}