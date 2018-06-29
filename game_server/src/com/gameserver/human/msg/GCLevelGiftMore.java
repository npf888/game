package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 等级礼包 （多个同时展示）
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLevelGiftMore extends GCMessage{
	
	/** 类型-数量 */
	private com.gameserver.human.data.LevelMoreGiftData[] levelMoreGiftData;

	public GCLevelGiftMore (){
	}
	
	public GCLevelGiftMore (
			com.gameserver.human.data.LevelMoreGiftData[] levelMoreGiftData ){
			this.levelMoreGiftData = levelMoreGiftData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		levelMoreGiftData = new com.gameserver.human.data.LevelMoreGiftData[count];
		for(int i=0; i<count; i++){
			com.gameserver.human.data.LevelMoreGiftData obj = new com.gameserver.human.data.LevelMoreGiftData();
			obj.setGiftType(readInteger());
			obj.setRewardNum(readLong());
			levelMoreGiftData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(levelMoreGiftData.length);
		for(int i=0; i<levelMoreGiftData.length; i++){
			writeInteger(levelMoreGiftData[i].getGiftType());
			writeLong(levelMoreGiftData[i].getRewardNum());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LEVEL_GIFT_MORE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LEVEL_GIFT_MORE";
	}

	public com.gameserver.human.data.LevelMoreGiftData[] getLevelMoreGiftData(){
		return levelMoreGiftData;
	}

	public void setLevelMoreGiftData(com.gameserver.human.data.LevelMoreGiftData[] levelMoreGiftData){
		this.levelMoreGiftData = levelMoreGiftData;
	}	
}