package com.gameserver.newbie.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 新手存盘点
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotNewbie extends GCMessage{
	
	/** 元素列表 */
	private int[] slotElementList;
	/** 连线信息列表 */
	private com.gameserver.newbie.data.NewbieSlotConnectInfoData[] slotConnectInfoDataList;
	/** 收益 */
	private long rewardNum;
	/** 累计奖金 */
	private long rewardSum;
	/** 特殊连线信息列表 */
	private com.gameserver.newbie.data.NewbieSpecialConnectInfoData[] specialConnectInfoDataList;

	public GCSlotNewbie (){
	}
	
	public GCSlotNewbie (
			int[] slotElementList,
			com.gameserver.newbie.data.NewbieSlotConnectInfoData[] slotConnectInfoDataList,
			long rewardNum,
			long rewardSum,
			com.gameserver.newbie.data.NewbieSpecialConnectInfoData[] specialConnectInfoDataList ){
			this.slotElementList = slotElementList;
			this.slotConnectInfoDataList = slotConnectInfoDataList;
			this.rewardNum = rewardNum;
			this.rewardSum = rewardSum;
			this.specialConnectInfoDataList = specialConnectInfoDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		slotElementList = new int[count];
		for(int i=0; i<count; i++){
			slotElementList[i] = readInteger();
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		slotConnectInfoDataList = new com.gameserver.newbie.data.NewbieSlotConnectInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.newbie.data.NewbieSlotConnectInfoData obj = new com.gameserver.newbie.data.NewbieSlotConnectInfoData();
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
		rewardSum = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		specialConnectInfoDataList = new com.gameserver.newbie.data.NewbieSpecialConnectInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.newbie.data.NewbieSpecialConnectInfoData obj = new com.gameserver.newbie.data.NewbieSpecialConnectInfoData();
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
		return MessageType.GC_SLOT_NEWBIE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_NEWBIE";
	}

	public int[] getSlotElementList(){
		return slotElementList;
	}

	public void setSlotElementList(int[] slotElementList){
		this.slotElementList = slotElementList;
	}	

	public com.gameserver.newbie.data.NewbieSlotConnectInfoData[] getSlotConnectInfoDataList(){
		return slotConnectInfoDataList;
	}

	public void setSlotConnectInfoDataList(com.gameserver.newbie.data.NewbieSlotConnectInfoData[] slotConnectInfoDataList){
		this.slotConnectInfoDataList = slotConnectInfoDataList;
	}	

	public long getRewardNum(){
		return rewardNum;
	}
		
	public void setRewardNum(long rewardNum){
		this.rewardNum = rewardNum;
	}

	public long getRewardSum(){
		return rewardSum;
	}
		
	public void setRewardSum(long rewardSum){
		this.rewardSum = rewardSum;
	}

	public com.gameserver.newbie.data.NewbieSpecialConnectInfoData[] getSpecialConnectInfoDataList(){
		return specialConnectInfoDataList;
	}

	public void setSpecialConnectInfoDataList(com.gameserver.newbie.data.NewbieSpecialConnectInfoData[] specialConnectInfoDataList){
		this.specialConnectInfoDataList = specialConnectInfoDataList;
	}	
}