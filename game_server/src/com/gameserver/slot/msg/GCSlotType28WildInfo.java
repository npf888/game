package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 海底世界老虎机wild信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType28WildInfo extends GCMessage{
	
	/** wild个数 */
	private int wildNum;
	/** 中奖倍数 */
	private int rate;
	/** wild位置 */
	private int[] posList;
	/** wild元素替换 本列所有元素后 所有元素的位置 */
	private int[] allPosList;

	public GCSlotType28WildInfo (){
	}
	
	public GCSlotType28WildInfo (
			int wildNum,
			int rate,
			int[] posList,
			int[] allPosList ){
			this.wildNum = wildNum;
			this.rate = rate;
			this.posList = posList;
			this.allPosList = allPosList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		wildNum = readInteger();
		rate = readInteger();
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
		writeInteger(rate);
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
		return MessageType.GC_SLOT_TYPE28_WILD_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE28_WILD_INFO";
	}

	public int getWildNum(){
		return wildNum;
	}
		
	public void setWildNum(int wildNum){
		this.wildNum = wildNum;
	}

	public int getRate(){
		return rate;
	}
		
	public void setRate(int rate){
		this.rate = rate;
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