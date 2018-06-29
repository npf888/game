package com.gameserver.bazooachieve.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 获取 成就 返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooAchieve extends GCMessage{
	
	/** 任务列表 */
	private com.gameserver.bazooachieve.data.BazooAchieveInfo[] bazooAchieveInfo;

	public GCBazooAchieve (){
	}
	
	public GCBazooAchieve (
			com.gameserver.bazooachieve.data.BazooAchieveInfo[] bazooAchieveInfo ){
			this.bazooAchieveInfo = bazooAchieveInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		bazooAchieveInfo = new com.gameserver.bazooachieve.data.BazooAchieveInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazooachieve.data.BazooAchieveInfo obj = new com.gameserver.bazooachieve.data.BazooAchieveInfo();
			obj.setBigtype(readInteger());
			obj.setModeType(readInteger());
			obj.setWayOfPlay(readInteger());
			obj.setCondition(readInteger());
			obj.setRewardNum(readInteger());
			obj.setFinishVlaues(readInteger());
			obj.setIsFinish(readInteger());
			obj.setNameId(readInteger());
			obj.setDescrip(readInteger());
			obj.setIcon(readString());
			bazooAchieveInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bazooAchieveInfo.length);
		for(int i=0; i<bazooAchieveInfo.length; i++){
			writeInteger(bazooAchieveInfo[i].getBigtype());
			writeInteger(bazooAchieveInfo[i].getModeType());
			writeInteger(bazooAchieveInfo[i].getWayOfPlay());
			writeInteger(bazooAchieveInfo[i].getCondition());
			writeInteger(bazooAchieveInfo[i].getRewardNum());
			writeInteger(bazooAchieveInfo[i].getFinishVlaues());
			writeInteger(bazooAchieveInfo[i].getIsFinish());
			writeInteger(bazooAchieveInfo[i].getNameId());
			writeInteger(bazooAchieveInfo[i].getDescrip());
			writeString(bazooAchieveInfo[i].getIcon());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_ACHIEVE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_ACHIEVE";
	}

	public com.gameserver.bazooachieve.data.BazooAchieveInfo[] getBazooAchieveInfo(){
		return bazooAchieveInfo;
	}

	public void setBazooAchieveInfo(com.gameserver.bazooachieve.data.BazooAchieveInfo[] bazooAchieveInfo){
		this.bazooAchieveInfo = bazooAchieveInfo;
	}	
}