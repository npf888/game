package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 女巫魔法老虎机wild信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType26WildInfo extends GCMessage{
	
	/** wild个数 */
	private int wildNum;
	/** wild位置 */
	private int[] posList;
	/** wild元素替换 本列所有元素后 所有元素的位置 */
	private int[] allPosList;

	public GCSlotType26WildInfo (){
	}
	
	public GCSlotType26WildInfo (
			int wildNum,
			int[] posList,
			int[] allPosList ){
			this.wildNum = wildNum;
			this.posList = posList;
			this.allPosList = allPosList;
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
		count = readShort();
		count = count < 0 ? 0 : count;
		allPosList = new int[count];
		for(int i=0; i<count; i++){
			allPosList[i] = readInteger();
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
		writeShort(allPosList.length);
		for(int i=0; i<allPosList.length; i++){
			writeInteger(allPosList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE26_WILD_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE26_WILD_INFO";
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

	public int[] getAllPosList(){
		return allPosList;
	}

	public void setAllPosList(int[] allPosList){
		this.allPosList = allPosList;
	}	
}