package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * boss的开始结束信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBossStartEndInfo extends GCMessage{
	
	/** 下一个worldBoss的类型 */
	private int bossType;
	/** 是否 被击杀 1：被击杀- 0：没有被击杀 */
	private int beKilled;
	/** 0:开启，1:关闭, 2：进行中 */
	private int startEndRuning;
	/** 距离 下一个boss 开始的剩余时间 (秒) */
	private long nextBossLeftTime;
	/** 当前的boss 剩余时间 (秒) */
	private long curBossLeftTime;

	public GCBossStartEndInfo (){
	}
	
	public GCBossStartEndInfo (
			int bossType,
			int beKilled,
			int startEndRuning,
			long nextBossLeftTime,
			long curBossLeftTime ){
			this.bossType = bossType;
			this.beKilled = beKilled;
			this.startEndRuning = startEndRuning;
			this.nextBossLeftTime = nextBossLeftTime;
			this.curBossLeftTime = curBossLeftTime;
	}

	@Override
	protected boolean readImpl() {
		bossType = readInteger();
		beKilled = readInteger();
		startEndRuning = readInteger();
		nextBossLeftTime = readLong();
		curBossLeftTime = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bossType);
		writeInteger(beKilled);
		writeInteger(startEndRuning);
		writeLong(nextBossLeftTime);
		writeLong(curBossLeftTime);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BOSS_START_END_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BOSS_START_END_INFO";
	}

	public int getBossType(){
		return bossType;
	}
		
	public void setBossType(int bossType){
		this.bossType = bossType;
	}

	public int getBeKilled(){
		return beKilled;
	}
		
	public void setBeKilled(int beKilled){
		this.beKilled = beKilled;
	}

	public int getStartEndRuning(){
		return startEndRuning;
	}
		
	public void setStartEndRuning(int startEndRuning){
		this.startEndRuning = startEndRuning;
	}

	public long getNextBossLeftTime(){
		return nextBossLeftTime;
	}
		
	public void setNextBossLeftTime(long nextBossLeftTime){
		this.nextBossLeftTime = nextBossLeftTime;
	}

	public long getCurBossLeftTime(){
		return curBossLeftTime;
	}
		
	public void setCurBossLeftTime(long curBossLeftTime){
		this.curBossLeftTime = curBossLeftTime;
	}
}