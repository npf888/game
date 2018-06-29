package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 石器时代老虎机 捕猎小游戏
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType14PreyBonus extends GCMessage{
	
	/** 捕兽夹 的数量 */
	private int nums;
	/** 捕获到的 野兽的数量 */
	private int preyNum;
	/** 每个猎物获得的金币 */
	private long perGold;
	/** 获得的总的金币数 */
	private long totalGold;

	public GCSlotType14PreyBonus (){
	}
	
	public GCSlotType14PreyBonus (
			int nums,
			int preyNum,
			long perGold,
			long totalGold ){
			this.nums = nums;
			this.preyNum = preyNum;
			this.perGold = perGold;
			this.totalGold = totalGold;
	}

	@Override
	protected boolean readImpl() {
		nums = readInteger();
		preyNum = readInteger();
		perGold = readLong();
		totalGold = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(nums);
		writeInteger(preyNum);
		writeLong(perGold);
		writeLong(totalGold);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE14_PREY_BONUS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE14_PREY_BONUS";
	}

	public int getNums(){
		return nums;
	}
		
	public void setNums(int nums){
		this.nums = nums;
	}

	public int getPreyNum(){
		return preyNum;
	}
		
	public void setPreyNum(int preyNum){
		this.preyNum = preyNum;
	}

	public long getPerGold(){
		return perGold;
	}
		
	public void setPerGold(long perGold){
		this.perGold = perGold;
	}

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}
}