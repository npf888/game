package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 海盗老虎机中了bonus 游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType31Bonus extends GCMessage{
	
	/** 三种小游戏 1：第一个-海盗交锋，2:第二个-海岛钓鱼，3:第三个-宝藏探秘 */
	private int[] whichNum;

	public GCSlotType31Bonus (){
	}
	
	public GCSlotType31Bonus (
			int[] whichNum ){
			this.whichNum = whichNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		whichNum = new int[count];
		for(int i=0; i<count; i++){
			whichNum[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(whichNum.length);
		for(int i=0; i<whichNum.length; i++){
			writeInteger(whichNum[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE31_BONUS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE31_BONUS";
	}

	public int[] getWhichNum(){
		return whichNum;
	}

	public void setWhichNum(int[] whichNum){
		this.whichNum = whichNum;
	}	
}