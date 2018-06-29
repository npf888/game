package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 12
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomShowHandAllSwing extends GCMessage{
	
	/** 统一摇完色子 之后 等待 几秒 的 时间状态  */
	private int status;
	/** 剩余次数 */
	private int leftTimes;
	/** 剩余时间 */
	private long leftSecond;
	/** 重摇结束,用户该轮流看每个人的色子值 */
	private com.gameserver.bazoo.data.DiceInfo[] diceInfo;

	public GCStateRoomShowHandAllSwing (){
	}
	
	public GCStateRoomShowHandAllSwing (
			int status,
			int leftTimes,
			long leftSecond,
			com.gameserver.bazoo.data.DiceInfo[] diceInfo ){
			this.status = status;
			this.leftTimes = leftTimes;
			this.leftSecond = leftSecond;
			this.diceInfo = diceInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		status = readInteger();
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
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(status);
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
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STATE_ROOM_SHOW_HAND_ALL_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_SHOW_HAND_ALL_SWING";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
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
}