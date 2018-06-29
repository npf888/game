package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 水晶魔法老虎机拼图个数
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotType19 extends GCMessage{
	
	/** 区域ID */
	private int id;
	/** 1：是           0：否 */
	private int levelEnough;
	/** 1.单线下注额的倍数 2.免费转动次数 */
	private int mType;
	/** 金钱 money 或者 次数time */
	private long mt;

	public GCSlotType19 (){
	}
	
	public GCSlotType19 (
			int id,
			int levelEnough,
			int mType,
			long mt ){
			this.id = id;
			this.levelEnough = levelEnough;
			this.mType = mType;
			this.mt = mt;
	}

	@Override
	protected boolean readImpl() {
		id = readInteger();
		levelEnough = readInteger();
		mType = readInteger();
		mt = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		writeInteger(levelEnough);
		writeInteger(mType);
		writeLong(mt);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TYPE19;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TYPE19";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}

	public int getLevelEnough(){
		return levelEnough;
	}
		
	public void setLevelEnough(int levelEnough){
		this.levelEnough = levelEnough;
	}

	public int getMType(){
		return mType;
	}
		
	public void setMType(int mType){
		this.mType = mType;
	}

	public long getMt(){
		return mt;
	}
		
	public void setMt(long mt){
		this.mt = mt;
	}
}