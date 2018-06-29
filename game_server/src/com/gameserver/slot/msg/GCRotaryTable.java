package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 转盘游戏返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRotaryTable extends GCMessage{
	
	/** 1：GOLD BONUS;2:FREE SPIN */
	private int firstType;
	/** 第二个盘子停留的位置 表里面的 ID  */
	private int secondIndex;
	/** 第三个盘子停留的位置 表里面的 ID */
	private int thirdIndex;
	/** 第四个盘子停留的位置 表里面的 ID */
	private int bigWheelIndex;
	/** 大转盘玩法元素位置 */
	private int[] posList;
	/** BounsGameTemplate表ID  */
	private int[] bounsList;
	/** BounsGame元素位置   */
	private int[] bounsListPosition;

	public GCRotaryTable (){
	}
	
	public GCRotaryTable (
			int firstType,
			int secondIndex,
			int thirdIndex,
			int bigWheelIndex,
			int[] posList,
			int[] bounsList,
			int[] bounsListPosition ){
			this.firstType = firstType;
			this.secondIndex = secondIndex;
			this.thirdIndex = thirdIndex;
			this.bigWheelIndex = bigWheelIndex;
			this.posList = posList;
			this.bounsList = bounsList;
			this.bounsListPosition = bounsListPosition;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		firstType = readInteger();
		secondIndex = readInteger();
		thirdIndex = readInteger();
		bigWheelIndex = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		posList = new int[count];
		for(int i=0; i<count; i++){
			posList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		bounsList = new int[count];
		for(int i=0; i<count; i++){
			bounsList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		bounsListPosition = new int[count];
		for(int i=0; i<count; i++){
			bounsListPosition[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(firstType);
		writeInteger(secondIndex);
		writeInteger(thirdIndex);
		writeInteger(bigWheelIndex);
		writeShort(posList.length);
		for(int i=0; i<posList.length; i++){
			writeInteger(posList[i]);
		}
		writeShort(bounsList.length);
		for(int i=0; i<bounsList.length; i++){
			writeInteger(bounsList[i]);
		}
		writeShort(bounsListPosition.length);
		for(int i=0; i<bounsListPosition.length; i++){
			writeInteger(bounsListPosition[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROTARY_TABLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROTARY_TABLE";
	}

	public int getFirstType(){
		return firstType;
	}
		
	public void setFirstType(int firstType){
		this.firstType = firstType;
	}

	public int getSecondIndex(){
		return secondIndex;
	}
		
	public void setSecondIndex(int secondIndex){
		this.secondIndex = secondIndex;
	}

	public int getThirdIndex(){
		return thirdIndex;
	}
		
	public void setThirdIndex(int thirdIndex){
		this.thirdIndex = thirdIndex;
	}

	public int getBigWheelIndex(){
		return bigWheelIndex;
	}
		
	public void setBigWheelIndex(int bigWheelIndex){
		this.bigWheelIndex = bigWheelIndex;
	}

	public int[] getPosList(){
		return posList;
	}

	public void setPosList(int[] posList){
		this.posList = posList;
	}	

	public int[] getBounsList(){
		return bounsList;
	}

	public void setBounsList(int[] bounsList){
		this.bounsList = bounsList;
	}	

	public int[] getBounsListPosition(){
		return bounsListPosition;
	}

	public void setBounsListPosition(int[] bounsListPosition){
		this.bounsListPosition = bounsListPosition;
	}	
}