package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 梭哈  模式:最小的人 摇色子
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCShowHandSingleSwing extends GCMessage{
	
	/** 那个点数 最低的人 去摇色子（这个人的ID） */
	private long passportId;
	/** 将要被 重摇的  色子的值的集合 */
	private int[] diceValues;
	/** 几小牛 */
	private int nameInt;
	/** 剩余次数 */
	private int leftTimes;
	/** 结算返回 每个人的信息 */
	private com.gameserver.bazoo.data.ShowHandBet[] showHandBet;

	public GCShowHandSingleSwing (){
	}
	
	public GCShowHandSingleSwing (
			long passportId,
			int[] diceValues,
			int nameInt,
			int leftTimes,
			com.gameserver.bazoo.data.ShowHandBet[] showHandBet ){
			this.passportId = passportId;
			this.diceValues = diceValues;
			this.nameInt = nameInt;
			this.leftTimes = leftTimes;
			this.showHandBet = showHandBet;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		passportId = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		diceValues = new int[count];
		for(int i=0; i<count; i++){
			diceValues[i] = readInteger();
		}
		nameInt = readInteger();
		leftTimes = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		showHandBet = new com.gameserver.bazoo.data.ShowHandBet[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.ShowHandBet obj = new com.gameserver.bazoo.data.ShowHandBet();
			obj.setPassportId(readLong());
			obj.setBet(readInteger());
			obj.setGold(readLong());
			obj.setPersonTotalGold(readLong());
			showHandBet[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(passportId);
		writeShort(diceValues.length);
		for(int i=0; i<diceValues.length; i++){
			writeInteger(diceValues[i]);
		}
		writeInteger(nameInt);
		writeInteger(leftTimes);
		writeShort(showHandBet.length);
		for(int i=0; i<showHandBet.length; i++){
			writeLong(showHandBet[i].getPassportId());
			writeInteger(showHandBet[i].getBet());
			writeLong(showHandBet[i].getGold());
			writeLong(showHandBet[i].getPersonTotalGold());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_HAND_SINGLE_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_HAND_SINGLE_SWING";
	}

	public long getPassportId(){
		return passportId;
	}
		
	public void setPassportId(long passportId){
		this.passportId = passportId;
	}

	public int[] getDiceValues(){
		return diceValues;
	}

	public void setDiceValues(int[] diceValues){
		this.diceValues = diceValues;
	}	

	public int getNameInt(){
		return nameInt;
	}
		
	public void setNameInt(int nameInt){
		this.nameInt = nameInt;
	}

	public int getLeftTimes(){
		return leftTimes;
	}
		
	public void setLeftTimes(int leftTimes){
		this.leftTimes = leftTimes;
	}

	public com.gameserver.bazoo.data.ShowHandBet[] getShowHandBet(){
		return showHandBet;
	}

	public void setShowHandBet(com.gameserver.bazoo.data.ShowHandBet[] showHandBet){
		this.showHandBet = showHandBet;
	}	
}