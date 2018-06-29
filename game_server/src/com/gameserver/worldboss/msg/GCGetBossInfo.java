package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回两个boss信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGetBossInfo extends GCMessage{
	
	/** boss的信息 */
	private com.gameserver.worldboss.data.BossInfoData[] bossInfoData;
	/** 上次boss Winner */
	private com.gameserver.worldboss.data.LastWinHumanData[] lastWinHumanData;

	public GCGetBossInfo (){
	}
	
	public GCGetBossInfo (
			com.gameserver.worldboss.data.BossInfoData[] bossInfoData,
			com.gameserver.worldboss.data.LastWinHumanData[] lastWinHumanData ){
			this.bossInfoData = bossInfoData;
			this.lastWinHumanData = lastWinHumanData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		bossInfoData = new com.gameserver.worldboss.data.BossInfoData[count];
		for(int i=0; i<count; i++){
			com.gameserver.worldboss.data.BossInfoData obj = new com.gameserver.worldboss.data.BossInfoData();
			obj.setNameId(readInteger());
			obj.setDescrip(readInteger());
			obj.setBeKilled(readInteger());
			obj.setBossType(readInteger());
			obj.setChangingBlood(readLong());
			obj.setBlood(readInteger());
			obj.setIncreaseBlood(readLong());
			obj.setRewardNum1(readInteger());
			obj.setRewardNum2(readInteger());
			obj.setStartTime(readLong());
			obj.setContinueTime(readLong());
			obj.setEndTime(readLong());
			bossInfoData[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		lastWinHumanData = new com.gameserver.worldboss.data.LastWinHumanData[count];
		for(int i=0; i<count; i++){
			com.gameserver.worldboss.data.LastWinHumanData obj = new com.gameserver.worldboss.data.LastWinHumanData();
			obj.setLevel(readLong());
			obj.setVipLevel(readInteger());
			obj.setUserId(readLong());
			obj.setCountry(readString());
			obj.setHead(readString());
			obj.setName(readString());
			obj.setAttackTotalGold(readLong());
			lastWinHumanData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bossInfoData.length);
		for(int i=0; i<bossInfoData.length; i++){
			writeInteger(bossInfoData[i].getNameId());
			writeInteger(bossInfoData[i].getDescrip());
			writeInteger(bossInfoData[i].getBeKilled());
			writeInteger(bossInfoData[i].getBossType());
			writeLong(bossInfoData[i].getChangingBlood());
			writeInteger(bossInfoData[i].getBlood());
			writeLong(bossInfoData[i].getIncreaseBlood());
			writeInteger(bossInfoData[i].getRewardNum1());
			writeInteger(bossInfoData[i].getRewardNum2());
			writeLong(bossInfoData[i].getStartTime());
			writeLong(bossInfoData[i].getContinueTime());
			writeLong(bossInfoData[i].getEndTime());
		}
		writeShort(lastWinHumanData.length);
		for(int i=0; i<lastWinHumanData.length; i++){
			writeLong(lastWinHumanData[i].getLevel());
			writeInteger(lastWinHumanData[i].getVipLevel());
			writeLong(lastWinHumanData[i].getUserId());
			writeString(lastWinHumanData[i].getCountry());
			writeString(lastWinHumanData[i].getHead());
			writeString(lastWinHumanData[i].getName());
			writeLong(lastWinHumanData[i].getAttackTotalGold());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_BOSS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_BOSS_INFO";
	}

	public com.gameserver.worldboss.data.BossInfoData[] getBossInfoData(){
		return bossInfoData;
	}

	public void setBossInfoData(com.gameserver.worldboss.data.BossInfoData[] bossInfoData){
		this.bossInfoData = bossInfoData;
	}	

	public com.gameserver.worldboss.data.LastWinHumanData[] getLastWinHumanData(){
		return lastWinHumanData;
	}

	public void setLastWinHumanData(com.gameserver.worldboss.data.LastWinHumanData[] lastWinHumanData){
		this.lastWinHumanData = lastWinHumanData;
	}	
}