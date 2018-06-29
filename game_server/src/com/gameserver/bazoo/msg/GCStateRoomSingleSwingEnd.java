package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 10
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomSingleSwingEnd extends GCMessage{
	
	/** 重摇结束  的状态  */
	private int status;
	/** 重摇结束,用户该轮流看每个人的色子值 */
	private com.gameserver.bazoo.data.DiceInfo[] diceInfo;

	public GCStateRoomSingleSwingEnd (){
	}
	
	public GCStateRoomSingleSwingEnd (
			int status,
			com.gameserver.bazoo.data.DiceInfo[] diceInfo ){
			this.status = status;
			this.diceInfo = diceInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		status = readInteger();
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
		return MessageType.GC_STATE_ROOM_SINGLE_SWING_END;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_SINGLE_SWING_END";
	}

	public int getStatus(){
		return status;
	}
		
	public void setStatus(int status){
		this.status = status;
	}

	public com.gameserver.bazoo.data.DiceInfo[] getDiceInfo(){
		return diceInfo;
	}

	public void setDiceInfo(com.gameserver.bazoo.data.DiceInfo[] diceInfo){
		this.diceInfo = diceInfo;
	}	
}