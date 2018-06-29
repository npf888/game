package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 统一摇色子返回 给机器人所有人的 所有的值
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRobotDiceUnifySwing extends GCMessage{
	
	/** 机器人看到所有人的值 */
	private com.gameserver.bazoo.data.DiceInfo[] diceInfo;

	public GCRobotDiceUnifySwing (){
	}
	
	public GCRobotDiceUnifySwing (
			com.gameserver.bazoo.data.DiceInfo[] diceInfo ){
			this.diceInfo = diceInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
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
		return MessageType.GC_ROBOT_DICE_UNIFY_SWING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROBOT_DICE_UNIFY_SWING";
	}

	public com.gameserver.bazoo.data.DiceInfo[] getDiceInfo(){
		return diceInfo;
	}

	public void setDiceInfo(com.gameserver.bazoo.data.DiceInfo[] diceInfo){
		this.diceInfo = diceInfo;
	}	
}