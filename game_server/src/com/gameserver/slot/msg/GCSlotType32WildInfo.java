package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 斯巴达老虎机 返回普通wild的集合（用的元素也是那个固定的wild）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType32WildInfo extends GCMessage{
	
	/** 普通wild的集合 */
	private int[] posList;
	/** 普通wild的数量 */
	private int wildNum;

	public GCSlotType32WildInfo (){
	}
	
	public GCSlotType32WildInfo (
			int[] posList,
			int wildNum ){
			this.posList = posList;
			this.wildNum = wildNum;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		posList = new int[count];
		for(int i=0; i<count; i++){
			posList[i] = readInteger();
		}
		wildNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(posList.length);
		for(int i=0; i<posList.length; i++){
			writeInteger(posList[i]);
		}
		writeInteger(wildNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE32_WILD_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE32_WILD_INFO";
	}

	public int[] getPosList(){
		return posList;
	}

	public void setPosList(int[] posList){
		this.posList = posList;
	}	

	public int getWildNum(){
		return wildNum;
	}
		
	public void setWildNum(int wildNum){
		this.wildNum = wildNum;
	}
}