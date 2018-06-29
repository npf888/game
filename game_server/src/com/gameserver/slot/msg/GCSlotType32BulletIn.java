package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 斯巴达老虎机 每个用户在进入到这个老虎机的时候 返回这个消息，显示还有多少个攻城车 就可以攻破城墙了
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType32BulletIn extends GCMessage{
	
	/** 攻城车剩余数量（还差几辆攻城车就会 打开社交活动），如果已经打开就是0个 */
	private int bulletLeftNum;

	public GCSlotType32BulletIn (){
	}
	
	public GCSlotType32BulletIn (
			int bulletLeftNum ){
			this.bulletLeftNum = bulletLeftNum;
	}

	@Override
	protected boolean readImpl() {
		bulletLeftNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bulletLeftNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE32_BULLET_IN;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE32_BULLET_IN";
	}

	public int getBulletLeftNum(){
		return bulletLeftNum;
	}
		
	public void setBulletLeftNum(int bulletLeftNum){
		this.bulletLeftNum = bulletLeftNum;
	}
}