package com.gameserver.activity.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 活动更新
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCUpdateActivity extends GCMessage{
	
	/** 活动信息 */
	private com.gameserver.activity.data.ActivityData activityDataList;

	public GCUpdateActivity (){
	}
	
	public GCUpdateActivity (
			com.gameserver.activity.data.ActivityData activityDataList ){
			this.activityDataList = activityDataList;
	}

	@Override
	protected boolean readImpl() {
		activityDataList = new com.gameserver.activity.data.ActivityData();
					activityDataList.setActivityId(readLong());
							activityDataList.setTitle(readString());
							activityDataList.setTitle_cn(readString());
							activityDataList.setTitle_tw(readString());
							activityDataList.setDesc(readString());
							activityDataList.setDesc_cn(readString());
							activityDataList.setDesc_tw(readString());
							activityDataList.setHall_pic_tw(readString());
							activityDataList.setHall_pic_cn(readString());
							activityDataList.setHall_pic(readString());
							activityDataList.setPic(readString());
							activityDataList.setPic_cn(readString());
							activityDataList.setPic_tw(readString());
							activityDataList.setLink(readInteger());
							activityDataList.setRuleSre(readString());
							activityDataList.setStartTime(readLong());
							activityDataList.setEndTime(readLong());
							activityDataList.setFullValue(readString());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(activityDataList.getActivityId());
		writeString(activityDataList.getTitle());
		writeString(activityDataList.getTitle_cn());
		writeString(activityDataList.getTitle_tw());
		writeString(activityDataList.getDesc());
		writeString(activityDataList.getDesc_cn());
		writeString(activityDataList.getDesc_tw());
		writeString(activityDataList.getHall_pic_tw());
		writeString(activityDataList.getHall_pic_cn());
		writeString(activityDataList.getHall_pic());
		writeString(activityDataList.getPic());
		writeString(activityDataList.getPic_cn());
		writeString(activityDataList.getPic_tw());
		writeInteger(activityDataList.getLink());
		writeString(activityDataList.getRuleSre());
		writeLong(activityDataList.getStartTime());
		writeLong(activityDataList.getEndTime());
		writeString(activityDataList.getFullValue());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_ACTIVITY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_ACTIVITY";
	}

	public com.gameserver.activity.data.ActivityData getActivityDataList(){
		return activityDataList;
	}
		
	public void setActivityDataList(com.gameserver.activity.data.ActivityData activityDataList){
		this.activityDataList = activityDataList;
	}
}