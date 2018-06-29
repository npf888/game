package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 老虎机彩金 获得的值
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCWinnerWheel extends GCMessage{
	
	/** 转盘的类型（每个类型的转盘 花费的美元不一样） */
	private int wheelType;
	/** 获得彩金的数量 */
	private int multiple;
	/** 计算完毕之后的 用户如果购买 应该得的金币 */
	private long totalGold;
	/** 充钱的类型：0：转动老虎机，1：小游戏 */
	private int goldType;

	public GCWinnerWheel (){
	}
	
	public GCWinnerWheel (
			int wheelType,
			int multiple,
			long totalGold,
			int goldType ){
			this.wheelType = wheelType;
			this.multiple = multiple;
			this.totalGold = totalGold;
			this.goldType = goldType;
	}

	@Override
	protected boolean readImpl() {
		wheelType = readInteger();
		multiple = readInteger();
		totalGold = readLong();
		goldType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(wheelType);
		writeInteger(multiple);
		writeLong(totalGold);
		writeInteger(goldType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_WINNER_WHEEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_WINNER_WHEEL";
	}

	public int getWheelType(){
		return wheelType;
	}
		
	public void setWheelType(int wheelType){
		this.wheelType = wheelType;
	}

	public int getMultiple(){
		return multiple;
	}
		
	public void setMultiple(int multiple){
		this.multiple = multiple;
	}

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}

	public int getGoldType(){
		return goldType;
	}
		
	public void setGoldType(int goldType){
		this.goldType = goldType;
	}
}