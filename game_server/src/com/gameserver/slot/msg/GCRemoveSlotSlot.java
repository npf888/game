package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * slot
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRemoveSlotSlot extends GCMessage{
	
	/** 连线信息列表 */
	private com.gameserver.slot.data.SlotConnectInfoData[] slotConnectInfoDataList;
	/** 三消老虎机列表 */
	private com.gameserver.slot.data.GcRemoveSlotSlotList[] gcRemoveSlotSlotList;

	public GCRemoveSlotSlot (){
	}
	
	public GCRemoveSlotSlot (
			com.gameserver.slot.data.SlotConnectInfoData[] slotConnectInfoDataList,
			com.gameserver.slot.data.GcRemoveSlotSlotList[] gcRemoveSlotSlotList ){
			this.slotConnectInfoDataList = slotConnectInfoDataList;
			this.gcRemoveSlotSlotList = gcRemoveSlotSlotList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
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
		count = readShort();
		count = count < 0 ? 0 : count;
		gcRemoveSlotSlotList = new com.gameserver.slot.data.GcRemoveSlotSlotList[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.GcRemoveSlotSlotList obj = new com.gameserver.slot.data.GcRemoveSlotSlotList();
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setSlotElementList(subList);
			}
			obj.setLineNum(readInteger());
			obj.setRewardNum(readLong());
			obj.setFreeTimes(readInteger());
			obj.setRewardSum(readLong());
			gcRemoveSlotSlotList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
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
		writeShort(gcRemoveSlotSlotList.length);
		for(int i=0; i<gcRemoveSlotSlotList.length; i++){
			int[] slotElementList=gcRemoveSlotSlotList[i].getSlotElementList();
			writeShort(slotElementList.length);
			for(int j=0; j<slotElementList.length; j++){
				writeInteger(slotElementList[j]);
			}
			writeInteger(gcRemoveSlotSlotList[i].getLineNum());
			writeLong(gcRemoveSlotSlotList[i].getRewardNum());
			writeInteger(gcRemoveSlotSlotList[i].getFreeTimes());
			writeLong(gcRemoveSlotSlotList[i].getRewardSum());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REMOVE_SLOT_SLOT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REMOVE_SLOT_SLOT";
	}

	public com.gameserver.slot.data.SlotConnectInfoData[] getSlotConnectInfoDataList(){
		return slotConnectInfoDataList;
	}

	public void setSlotConnectInfoDataList(com.gameserver.slot.data.SlotConnectInfoData[] slotConnectInfoDataList){
		this.slotConnectInfoDataList = slotConnectInfoDataList;
	}	

	public com.gameserver.slot.data.GcRemoveSlotSlotList[] getGcRemoveSlotSlotList(){
		return gcRemoveSlotSlotList;
	}

	public void setGcRemoveSlotSlotList(com.gameserver.slot.data.GcRemoveSlotSlotList[] gcRemoveSlotSlotList){
		this.gcRemoveSlotSlotList = gcRemoveSlotSlotList;
	}	
}