package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 13
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomShowHandSingleSwing extends GCMessage{
	
	/** 每个人单独摇 的状态（大部分 时间都在这个状态）  */
	private int status;
	/** 最后一个需要 */
	private long lastPassportId;
	/** 当前摇色子的用户的ID */
	private long[] passportId;
	/** 剩余次数 */
	private int leftTimes;
	/** 剩余时间 */
	private long leftSecond;
	/** 其他人的色子的数组 */
	private com.gameserver.bazoo.data.DiceInfo[] diceInfo;
	/** 结算返回 每个人的信息 */
	private com.gameserver.bazoo.data.ShowHandBet[] showHandBet;

	public GCStateRoomShowHandSingleSwing (){
	}
	
	public GCStateRoomShowHandSingleSwing (
			int status,
			long lastPassportId,
			long[] passportId,
			int leftTimes,
			long leftSecond,
			com.gameserver.bazoo.data.DiceInfo[] diceInfo,
			com.gameserver.bazoo.data.ShowHandBet[] showHandBet ){
			this.status = status;
			this.lastPassportId = lastPassportId;
			this.passportId = passportId;
			this.leftTimes = leftTimes;
			this.leftSecond = leftSecond;
			this.diceInfo = diceInfo;
			this.showHandBet = showHandBet;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		status = readInteger();
		lastPassportId = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		passportId = new long[count];
		for(int i=0; i<count; i++){
			passportId[i] = readLong();
		}
		leftTimes = readInteger();
		leftSecond = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		diceInfo = new com.gameserver.bazoo.data.DiceInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.DiceInfo obj = new com.gameserver.bazoo.data.DiceInfo();
			obj.setPassportId(readLong());
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setDiceValues(subList);
			}
			obj.setCowNameInt(readInteger());
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setRedDiceValues(subList);
			}
			diceInfo[i] = obj;
		}
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
		writeInteger(status);
		writeLong(lastPassportId);
		writeShort(passportId.length);
		for(int i=0; i<passportId.length; i++){
			writeLong(passportId[i]);
		}
		writeInteger(leftTimes);
		writeLong(leftSecond);
		writeShort(diceInfo.length);
		for(int i=0; i<diceInfo.length; i++){
			writeLong(diceInfo[i].getPassportId());
			int[] diceValues=diceInfo[i].getDiceValues();
			writeShort(diceValues.length);
			for(int j=0; j<diceValues.length; j++){
				writeInteger(diceValues[j]);
			}
			writeInteger(diceInfo[i].getCowNameInt());
			int[] redDiceValues=diceInfo[i].getRedDiceValues();
			writeShort(redDiceValues.length);
			for(int j=0; j<redDiceValues.length; j++){
				writeInteger(redDiceValues[j]);
			}
		}
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
		return MessageType.GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}

	public long getLastPassportId(){
		return lastPassportId;
	}
		
	public void setLastPassportId(long lastPassportId){
		this.lastPassportId = lastPassportId;
	}

	public long[] getPassportId(){
		return passportId;
	}

	public void setPassportId(long[] passportId){
		this.passportId = passportId;
	}	

	public int getLeftTimes(){
		return leftTimes;
	}
		
	public void setLeftTimes(int leftTimes){
		this.leftTimes = leftTimes;
	}

	public long getLeftSecond(){
		return leftSecond;
	}
		
	public void setLeftSecond(long leftSecond){
		this.leftSecond = leftSecond;
	}

	public com.gameserver.bazoo.data.DiceInfo[] getDiceInfo(){
		return diceInfo;
	}

	public void setDiceInfo(com.gameserver.bazoo.data.DiceInfo[] diceInfo){
		this.diceInfo = diceInfo;
	}	

	public com.gameserver.bazoo.data.ShowHandBet[] getShowHandBet(){
		return showHandBet;
	}

	public void setShowHandBet(com.gameserver.bazoo.data.ShowHandBet[] showHandBet){
		this.showHandBet = showHandBet;
	}	
}