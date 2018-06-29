package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 刷新boss的信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRefreshBossInfo extends GCMessage{
	
	/** 刷新boss的信息 */
	private com.gameserver.worldboss.data.RefreshBossData[] refreshBossData;

	public GCRefreshBossInfo (){
	}
	
	public GCRefreshBossInfo (
			com.gameserver.worldboss.data.RefreshBossData[] refreshBossData ){
			this.refreshBossData = refreshBossData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		refreshBossData = new com.gameserver.worldboss.data.RefreshBossData[count];
		for(int i=0; i<count; i++){
			com.gameserver.worldboss.data.RefreshBossData obj = new com.gameserver.worldboss.data.RefreshBossData();
			obj.setCurBlood(readLong());
			obj.setSkillTime(readLong());
			refreshBossData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(refreshBossData.length);
		for(int i=0; i<refreshBossData.length; i++){
			writeLong(refreshBossData[i].getCurBlood());
			writeLong(refreshBossData[i].getSkillTime());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REFRESH_BOSS_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REFRESH_BOSS_INFO";
	}

	public com.gameserver.worldboss.data.RefreshBossData[] getRefreshBossData(){
		return refreshBossData;
	}

	public void setRefreshBossData(com.gameserver.worldboss.data.RefreshBossData[] refreshBossData){
		this.refreshBossData = refreshBossData;
	}	
}