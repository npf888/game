package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 狮身人面老虎机的返回值
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType15Bouns extends GCMessage{
	
	/** 总金币 */
	private long totalGold;
	/** 当前金币 */
	private long currentGold;
	/** 是否成功获取金币  1：是，0：否 */
	private int isSuccess;

	public GCSlotType15Bouns (){
	}
	
	public GCSlotType15Bouns (
			long totalGold,
			long currentGold,
			int isSuccess ){
			this.totalGold = totalGold;
			this.currentGold = currentGold;
			this.isSuccess = isSuccess;
	}

	@Override
	protected boolean readImpl() {
		totalGold = readLong();
		currentGold = readLong();
		isSuccess = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(totalGold);
		writeLong(currentGold);
		writeInteger(isSuccess);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE15_BOUNS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE15_BOUNS";
	}

	public long getTotalGold(){
		return totalGold;
	}
		
	public void setTotalGold(long totalGold){
		this.totalGold = totalGold;
	}

	public long getCurrentGold(){
		return currentGold;
	}
		
	public void setCurrentGold(long currentGold){
		this.currentGold = currentGold;
	}

	public int getIsSuccess(){
		return isSuccess;
	}
		
	public void setIsSuccess(int isSuccess){
		this.isSuccess = isSuccess;
	}
}