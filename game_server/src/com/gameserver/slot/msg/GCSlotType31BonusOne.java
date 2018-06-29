package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 海盗老虎机:bonus 游戏 第一个-海盗交锋
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType31BonusOne extends GCMessage{
	
	/** 最开始的 子弹数量 */
	private int firstFew;
	/** 金币所翻倍数 */
	private int multiple;
	/** 类型的集合，0 是金币    1是炮弹 ,和下边的集合一一对应 */
	private int[] typeList;
	/** 金币或者炮弹的集合 */
	private long[] goldsOrNumList;

	public GCSlotType31BonusOne (){
	}
	
	public GCSlotType31BonusOne (
			int firstFew,
			int multiple,
			int[] typeList,
			long[] goldsOrNumList ){
			this.firstFew = firstFew;
			this.multiple = multiple;
			this.typeList = typeList;
			this.goldsOrNumList = goldsOrNumList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		firstFew = readInteger();
		multiple = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		typeList = new int[count];
		for(int i=0; i<count; i++){
			typeList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		goldsOrNumList = new long[count];
		for(int i=0; i<count; i++){
			goldsOrNumList[i] = readLong();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(firstFew);
		writeInteger(multiple);
		writeShort(typeList.length);
		for(int i=0; i<typeList.length; i++){
			writeInteger(typeList[i]);
		}
		writeShort(goldsOrNumList.length);
		for(int i=0; i<goldsOrNumList.length; i++){
			writeLong(goldsOrNumList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE31_BONUS_ONE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE31_BONUS_ONE";
	}

	public int getFirstFew(){
		return firstFew;
	}
		
	public void setFirstFew(int firstFew){
		this.firstFew = firstFew;
	}

	public int getMultiple(){
		return multiple;
	}
		
	public void setMultiple(int multiple){
		this.multiple = multiple;
	}

	public int[] getTypeList(){
		return typeList;
	}

	public void setTypeList(int[] typeList){
		this.typeList = typeList;
	}	

	public long[] getGoldsOrNumList(){
		return goldsOrNumList;
	}

	public void setGoldsOrNumList(long[] goldsOrNumList){
		this.goldsOrNumList = goldsOrNumList;
	}	
}