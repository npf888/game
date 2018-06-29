package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 竞赛开始 返回的列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSlotTournamentStartData extends GCMessage{
	
	/** 竞赛类型 */
	private int tournamentType;
	/** 时间块 */
	private long startTime;
	/** 奖金总数 */
	private long allBonus;
	/** 总人数 */
	private long totalNum;

	public GCSlotTournamentStartData (){
	}
	
	public GCSlotTournamentStartData (
			int tournamentType,
			long startTime,
			long allBonus,
			long totalNum ){
			this.tournamentType = tournamentType;
			this.startTime = startTime;
			this.allBonus = allBonus;
			this.totalNum = totalNum;
	}

	@Override
	protected boolean readImpl() {
		tournamentType = readInteger();
		startTime = readLong();
		allBonus = readLong();
		totalNum = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(tournamentType);
		writeLong(startTime);
		writeLong(allBonus);
		writeLong(totalNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SLOT_TOURNAMENT_START_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SLOT_TOURNAMENT_START_DATA";
	}

	public int getTournamentType(){
		return tournamentType;
	}
		
	public void setTournamentType(int tournamentType){
		this.tournamentType = tournamentType;
	}

	public long getStartTime(){
		return startTime;
	}
		
	public void setStartTime(long startTime){
		this.startTime = startTime;
	}

	public long getAllBonus(){
		return allBonus;
	}
		
	public void setAllBonus(long allBonus){
		this.allBonus = allBonus;
	}

	public long getTotalNum(){
		return totalNum;
	}
		
	public void setTotalNum(long totalNum){
		this.totalNum = totalNum;
	}
}