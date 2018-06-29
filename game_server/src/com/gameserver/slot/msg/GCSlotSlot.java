package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * slot
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotSlot extends GCMessage{
	
	/** 消息码 */
	private long msgId;
	/** 是否免费 */
	private int free;
	/** 元素列表 */
	private int[] slotElementList;
	/** 连线信息列表 */
	private com.gameserver.slot.data.SlotConnectInfoData[] slotConnectInfoDataList;
	/** 收益 */
	private long rewardNum;
	/** 免费次数 */
	private int freeTimes;
	/** 累计奖金 */
	private long rewardSum;
	/** 特殊连线信息列表 */
	private com.gameserver.slot.data.SpecialConnectInfoData[] specialConnectInfoDataList;

	public GCSlotSlot (){
	}
	
	public GCSlotSlot (
			long msgId,
			int free,
			int[] slotElementList,
			com.gameserver.slot.data.SlotConnectInfoData[] slotConnectInfoDataList,
			long rewardNum,
			int freeTimes,
			long rewardSum,
			com.gameserver.slot.data.SpecialConnectInfoData[] specialConnectInfoDataList ){
			this.msgId = msgId;
			this.free = free;
			this.slotElementList = slotElementList;
			this.slotConnectInfoDataList = slotConnectInfoDataList;
			this.rewardNum = rewardNum;
			this.freeTimes = freeTimes;
			this.rewardSum = rewardSum;
			this.specialConnectInfoDataList = specialConnectInfoDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		msgId = readLong();
		free = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		slotElementList = new int[count];
		for(int i=0; i<count; i++){
			slotElementList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		slotConnectInfoDataList = new com.gameserver.slot.data.SlotConnectInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.SlotConnectInfoData obj = new com.gameserver.slot.data.SlotConnectInfoData();
			obj.setPayLineId(readInteger());
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setPosList(subList);
			}
			obj.setPayId(readInteger());
			slotConnectInfoDataList[i] = obj;
		}
		rewardNum = readLong();
		freeTimes = readInteger();
		rewardSum = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		specialConnectInfoDataList = new com.gameserver.slot.data.SpecialConnectInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.SpecialConnectInfoData obj = new com.gameserver.slot.data.SpecialConnectInfoData();
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setPosList(subList);
			}
			specialConnectInfoDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(msgId);
		writeInteger(free);
		writeShort(slotElementList.length);
		for(int i=0; i<slotElementList.length; i++){
			writeInteger(slotElementList[i]);
		}
		writeShort(slotConnectInfoDataList.length);
		for(int i=0; i<slotConnectInfoDataList.length; i++){
			writeInteger(slotConnectInfoDataList[i].getPayLineId());
			int[] posList=slotConnectInfoDataList[i].getPosList();
			writeShort(posList.length);
			for(int j=0; j<posList.length; j++){
				writeInteger(posList[j]);
			}
			writeInteger(slotConnectInfoDataList[i].getPayId());
		}
		writeLong(rewardNum);
		writeInteger(freeTimes);
		writeLong(rewardSum);
		writeShort(specialConnectInfoDataList.length);
		for(int i=0; i<specialConnectInfoDataList.length; i++){
			int[] posList=specialConnectInfoDataList[i].getPosList();
			writeShort(posList.length);
			for(int j=0; j<posList.length; j++){
				writeInteger(posList[j]);
			}
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_SLOT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_SLOT";
	}

	public long getMsgId(){
		return msgId;
	}
		
	public void setMsgId(long msgId){
		this.msgId = msgId;
	}

	public int getFree(){
		return free;
	}
		
	public void setFree(int free){
		this.free = free;
	}

	public int[] getSlotElementList(){
		return slotElementList;
	}

	public void setSlotElementList(int[] slotElementList){
		this.slotElementList = slotElementList;
	}	

	public com.gameserver.slot.data.SlotConnectInfoData[] getSlotConnectInfoDataList(){
		return slotConnectInfoDataList;
	}

	public void setSlotConnectInfoDataList(com.gameserver.slot.data.SlotConnectInfoData[] slotConnectInfoDataList){
		this.slotConnectInfoDataList = slotConnectInfoDataList;
	}	

	public long getRewardNum(){
		return rewardNum;
	}
		
	public void setRewardNum(long rewardNum){
		this.rewardNum = rewardNum;
	}

	public int getFreeTimes(){
		return freeTimes;
	}
		
	public void setFreeTimes(int freeTimes){
		this.freeTimes = freeTimes;
	}

	public long getRewardSum(){
		return rewardSum;
	}
		
	public void setRewardSum(long rewardSum){
		this.rewardSum = rewardSum;
	}

	public com.gameserver.slot.data.SpecialConnectInfoData[] getSpecialConnectInfoDataList(){
		return specialConnectInfoDataList;
	}

	public void setSpecialConnectInfoDataList(com.gameserver.slot.data.SpecialConnectInfoData[] specialConnectInfoDataList){
		this.specialConnectInfoDataList = specialConnectInfoDataList;
	}	
}