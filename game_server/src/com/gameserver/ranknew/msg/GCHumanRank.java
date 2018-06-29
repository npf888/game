package com.gameserver.ranknew.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 请求自己的排名返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanRank extends GCMessage{
	
	/** 自己的排名 */
	private int rank;
	/** 玩家积分 */
	private long win;
	/** 结算下次刷新点 */
	private long refreshPoint;

	public GCHumanRank (){
	}
	
	public GCHumanRank (
			int rank,
			long win,
			long refreshPoint ){
			this.rank = rank;
			this.win = win;
			this.refreshPoint = refreshPoint;
	}

	@Override
	protected boolean readImpl() {
		rank = readInteger();
		win = readLong();
		refreshPoint = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(rank);
		writeLong(win);
		writeLong(refreshPoint);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_RANK";
	}

	public int getRank(){
		return rank;
	}
		
	public void setRank(int rank){
		this.rank = rank;
	}

	public long getWin(){
		return win;
	}
		
	public void setWin(long win){
		this.win = win;
	}

	public long getRefreshPoint(){
		return refreshPoint;
	}
		
	public void setRefreshPoint(long refreshPoint){
		this.refreshPoint = refreshPoint;
	}
}