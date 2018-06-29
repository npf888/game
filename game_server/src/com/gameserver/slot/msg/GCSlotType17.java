package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 狼老虎机bouns玩法
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType17 extends GCMessage{
	
	/** bouns单独中奖金额 */
	private long bonusNum;
	/** 转动次数 */
	private int wolfTimes;
	/** bonus个数 */
	private int wolfBonusNum;
	/** bonus位置 */
	private com.gameserver.slot.data.BounsConnectInfoData[] bounsConnectInfoData;

	public GCSlotType17 (){
	}
	
	public GCSlotType17 (
			long bonusNum,
			int wolfTimes,
			int wolfBonusNum,
			com.gameserver.slot.data.BounsConnectInfoData[] bounsConnectInfoData ){
			this.bonusNum = bonusNum;
			this.wolfTimes = wolfTimes;
			this.wolfBonusNum = wolfBonusNum;
			this.bounsConnectInfoData = bounsConnectInfoData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		bonusNum = readLong();
		wolfTimes = readInteger();
		wolfBonusNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		bounsConnectInfoData = new com.gameserver.slot.data.BounsConnectInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.BounsConnectInfoData obj = new com.gameserver.slot.data.BounsConnectInfoData();
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setPosList(subList);
			}
			bounsConnectInfoData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(bonusNum);
		writeInteger(wolfTimes);
		writeInteger(wolfBonusNum);
		writeShort(bounsConnectInfoData.length);
		for(int i=0; i<bounsConnectInfoData.length; i++){
			int[] posList=bounsConnectInfoData[i].getPosList();
			writeShort(posList.length);
			for(int j=0; j<posList.length; j++){
				writeInteger(posList[j]);
			}
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE17;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE17";
	}

	public long getBonusNum(){
		return bonusNum;
	}
		
	public void setBonusNum(long bonusNum){
		this.bonusNum = bonusNum;
	}

	public int getWolfTimes(){
		return wolfTimes;
	}
		
	public void setWolfTimes(int wolfTimes){
		this.wolfTimes = wolfTimes;
	}

	public int getWolfBonusNum(){
		return wolfBonusNum;
	}
		
	public void setWolfBonusNum(int wolfBonusNum){
		this.wolfBonusNum = wolfBonusNum;
	}

	public com.gameserver.slot.data.BounsConnectInfoData[] getBounsConnectInfoData(){
		return bounsConnectInfoData;
	}

	public void setBounsConnectInfoData(com.gameserver.slot.data.BounsConnectInfoData[] bounsConnectInfoData){
		this.bounsConnectInfoData = bounsConnectInfoData;
	}	
}