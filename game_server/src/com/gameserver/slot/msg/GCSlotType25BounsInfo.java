package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 忍者老虎机bouns信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType25BounsInfo extends GCMessage{
	
	/** 忍者随机位置 */
	private int position;
	/** bouns个数 */
	private int bounsNum;
	/** bouns位置 */
	private int[] posList;

	public GCSlotType25BounsInfo (){
	}
	
	public GCSlotType25BounsInfo (
			int position,
			int bounsNum,
			int[] posList ){
			this.position = position;
			this.bounsNum = bounsNum;
			this.posList = posList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		position = readInteger();
		bounsNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		posList = new int[count];
		for(int i=0; i<count; i++){
			posList[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(position);
		writeInteger(bounsNum);
		writeShort(posList.length);
		for(int i=0; i<posList.length; i++){
			writeInteger(posList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE25_BOUNS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE25_BOUNS_INFO";
	}

	public int getPosition(){
		return position;
	}
		
	public void setPosition(int position){
		this.position = position;
	}

	public int getBounsNum(){
		return bounsNum;
	}
		
	public void setBounsNum(int bounsNum){
		this.bounsNum = bounsNum;
	}

	public int[] getPosList(){
		return posList;
	}

	public void setPosList(int[] posList){
		this.posList = posList;
	}	
}