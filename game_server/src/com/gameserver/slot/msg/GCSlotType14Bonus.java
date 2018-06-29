package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 石器时代老虎机有替换元素
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType14Bonus extends GCMessage{
	
	/** bonus倍数 */
	private int bounsWeight;

	public GCSlotType14Bonus (){
	}
	
	public GCSlotType14Bonus (
			int bounsWeight ){
			this.bounsWeight = bounsWeight;
	}

	@Override
	protected boolean readImpl() {
		bounsWeight = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bounsWeight);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE14_BONUS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE14_BONUS";
	}

	public int getBounsWeight(){
		return bounsWeight;
	}
		
	public void setBounsWeight(int bounsWeight){
		this.bounsWeight = bounsWeight;
	}
}