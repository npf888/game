package com.gameserver.achievement.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获取成就数据返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCAchInfo extends GCMessage{
	
	/** 成就完成情况 */
	private com.gameserver.achievement.data.AchievementStateData[] achievementStateData;
	/** 成就进度 */
	private com.gameserver.achievement.data.AchievementsProgressData[] achievementsProgressData;

	public GCAchInfo (){
	}
	
	public GCAchInfo (
			com.gameserver.achievement.data.AchievementStateData[] achievementStateData,
			com.gameserver.achievement.data.AchievementsProgressData[] achievementsProgressData ){
			this.achievementStateData = achievementStateData;
			this.achievementsProgressData = achievementsProgressData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		achievementStateData = new com.gameserver.achievement.data.AchievementStateData[count];
		for(int i=0; i<count; i++){
			com.gameserver.achievement.data.AchievementStateData obj = new com.gameserver.achievement.data.AchievementStateData();
			obj.setId(readInteger());
			obj.setState(readInteger());
			obj.setCompleteTime(readLong());
			achievementStateData[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		achievementsProgressData = new com.gameserver.achievement.data.AchievementsProgressData[count];
		for(int i=0; i<count; i++){
			com.gameserver.achievement.data.AchievementsProgressData obj = new com.gameserver.achievement.data.AchievementsProgressData();
			obj.setServerType(readInteger());
			obj.setSmalType(readInteger());
			obj.setValue(readString());
			achievementsProgressData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(achievementStateData.length);
		for(int i=0; i<achievementStateData.length; i++){
			writeInteger(achievementStateData[i].getId());
			writeInteger(achievementStateData[i].getState());
			writeLong(achievementStateData[i].getCompleteTime());
		}
		writeShort(achievementsProgressData.length);
		for(int i=0; i<achievementsProgressData.length; i++){
			writeInteger(achievementsProgressData[i].getServerType());
			writeInteger(achievementsProgressData[i].getSmalType());
			writeString(achievementsProgressData[i].getValue());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ACH_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ACH_INFO";
	}

	public com.gameserver.achievement.data.AchievementStateData[] getAchievementStateData(){
		return achievementStateData;
	}

	public void setAchievementStateData(com.gameserver.achievement.data.AchievementStateData[] achievementStateData){
		this.achievementStateData = achievementStateData;
	}	

	public com.gameserver.achievement.data.AchievementsProgressData[] getAchievementsProgressData(){
		return achievementsProgressData;
	}

	public void setAchievementsProgressData(com.gameserver.achievement.data.AchievementsProgressData[] achievementsProgressData){
		this.achievementsProgressData = achievementsProgressData;
	}	
}