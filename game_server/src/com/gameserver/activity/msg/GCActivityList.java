package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 活动列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCActivityList extends GCMessage{
	
	/** 活动信息 */
	private com.gameserver.activity.data.ActivityData[] activityDataList;

	public GCActivityList (){
	}
	
	public GCActivityList (
			com.gameserver.activity.data.ActivityData[] activityDataList ){
			this.activityDataList = activityDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		activityDataList = new com.gameserver.activity.data.ActivityData[count];
		for(int i=0; i<count; i++){
			com.gameserver.activity.data.ActivityData obj = new com.gameserver.activity.data.ActivityData();
			obj.setActivityId(readLong());
			obj.setTitle(readString());
			obj.setTitle_cn(readString());
			obj.setTitle_tw(readString());
			obj.setDesc(readString());
			obj.setDesc_cn(readString());
			obj.setDesc_tw(readString());
			obj.setHall_pic_tw(readString());
			obj.setHall_pic_cn(readString());
			obj.setHall_pic(readString());
			obj.setPic(readString());
			obj.setPic_cn(readString());
			obj.setPic_tw(readString());
			obj.setLink(readInteger());
			obj.setRuleSre(readString());
			obj.setStartTime(readLong());
			obj.setEndTime(readLong());
			obj.setFullValue(readString());
			activityDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(activityDataList.length);
		for(int i=0; i<activityDataList.length; i++){
			writeLong(activityDataList[i].getActivityId());
			writeString(activityDataList[i].getTitle());
			writeString(activityDataList[i].getTitle_cn());
			writeString(activityDataList[i].getTitle_tw());
			writeString(activityDataList[i].getDesc());
			writeString(activityDataList[i].getDesc_cn());
			writeString(activityDataList[i].getDesc_tw());
			writeString(activityDataList[i].getHall_pic_tw());
			writeString(activityDataList[i].getHall_pic_cn());
			writeString(activityDataList[i].getHall_pic());
			writeString(activityDataList[i].getPic());
			writeString(activityDataList[i].getPic_cn());
			writeString(activityDataList[i].getPic_tw());
			writeInteger(activityDataList[i].getLink());
			writeString(activityDataList[i].getRuleSre());
			writeLong(activityDataList[i].getStartTime());
			writeLong(activityDataList[i].getEndTime());
			writeString(activityDataList[i].getFullValue());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ACTIVITY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ACTIVITY_LIST";
	}

	public com.gameserver.activity.data.ActivityData[] getActivityDataList(){
		return activityDataList;
	}

	public void setActivityDataList(com.gameserver.activity.data.ActivityData[] activityDataList){
		this.activityDataList = activityDataList;
	}	
}