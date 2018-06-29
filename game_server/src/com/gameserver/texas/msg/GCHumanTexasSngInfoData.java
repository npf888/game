package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州sng基本信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanTexasSngInfoData extends GCMessage{
	
	/** 参加次数 */
	private int joinTimes;
	/** 金杯 */
	private int goldNum;
	/** 银杯 */
	private int silverNum;
	/** 铜杯 */
	private int copperNum;
	/** 周分数 */
	private int weekScore;
	/** 排行 */
	private long rank;

	public GCHumanTexasSngInfoData (){
	}
	
	public GCHumanTexasSngInfoData (
			int joinTimes,
			int goldNum,
			int silverNum,
			int copperNum,
			int weekScore,
			long rank ){
			this.joinTimes = joinTimes;
			this.goldNum = goldNum;
			this.silverNum = silverNum;
			this.copperNum = copperNum;
			this.weekScore = weekScore;
			this.rank = rank;
	}

	@Override
	protected boolean readImpl() {
		joinTimes = readInteger();
		goldNum = readInteger();
		silverNum = readInteger();
		copperNum = readInteger();
		weekScore = readInteger();
		rank = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(joinTimes);
		writeInteger(goldNum);
		writeInteger(silverNum);
		writeInteger(copperNum);
		writeInteger(weekScore);
		writeLong(rank);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_TEXAS_SNG_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_TEXAS_SNG_INFO_DATA";
	}

	public int getJoinTimes(){
		return joinTimes;
	}
		
	public void setJoinTimes(int joinTimes){
		this.joinTimes = joinTimes;
	}

	public int getGoldNum(){
		return goldNum;
	}
		
	public void setGoldNum(int goldNum){
		this.goldNum = goldNum;
	}

	public int getSilverNum(){
		return silverNum;
	}
		
	public void setSilverNum(int silverNum){
		this.silverNum = silverNum;
	}

	public int getCopperNum(){
		return copperNum;
	}
		
	public void setCopperNum(int copperNum){
		this.copperNum = copperNum;
	}

	public int getWeekScore(){
		return weekScore;
	}
		
	public void setWeekScore(int weekScore){
		this.weekScore = weekScore;
	}

	public long getRank(){
		return rank;
	}
		
	public void setRank(long rank){
		this.rank = rank;
	}
}