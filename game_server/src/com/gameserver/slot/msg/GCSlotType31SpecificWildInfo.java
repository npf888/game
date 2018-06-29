package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 海盗老虎机特殊wild信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType31SpecificWildInfo extends GCMessage{
	
	/** wild个数 */
	private int wildNum;
	/** wild位置 */
	private int[] posList;

	public GCSlotType31SpecificWildInfo (){
	}
	
	public GCSlotType31SpecificWildInfo (
			int wildNum,
			int[] posList ){
			this.wildNum = wildNum;
			this.posList = posList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		wildNum = readInteger();
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
		writeInteger(wildNum);
		writeShort(posList.length);
		for(int i=0; i<posList.length; i++){
			writeInteger(posList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE31_SPECIFIC_WILD_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE31_SPECIFIC_WILD_INFO";
	}

	public int getWildNum(){
		return wildNum;
	}
		
	public void setWildNum(int wildNum){
		this.wildNum = wildNum;
	}

	public int[] getPosList(){
		return posList;
	}

	public void setPosList(int[] posList){
		this.posList = posList;
	}	
}