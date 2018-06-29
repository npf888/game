package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 斯巴达老虎机攻城车剩余数量
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType32LeftBulletNum extends GCMessage{
	
	/** 攻城车剩余数量（还差几辆攻城车就会 打开社交活动） */
	private int bulletLeftNum;
	/** 当前获得攻城车的人的ID */
	private int userId;

	public GCSlotType32LeftBulletNum (){
	}
	
	public GCSlotType32LeftBulletNum (
			int bulletLeftNum,
			int userId ){
			this.bulletLeftNum = bulletLeftNum;
			this.userId = userId;
	}

	@Override
	protected boolean readImpl() {
		bulletLeftNum = readInteger();
		userId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bulletLeftNum);
		writeInteger(userId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE32_LEFT_BULLET_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE32_LEFT_BULLET_NUM";
	}

	public int getBulletLeftNum(){
		return bulletLeftNum;
	}
		
	public void setBulletLeftNum(int bulletLeftNum){
		this.bulletLeftNum = bulletLeftNum;
	}

	public int getUserId(){
		return userId;
	}
		
	public void setUserId(int userId){
		this.userId = userId;
	}
}