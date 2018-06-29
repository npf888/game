package com.gameserver.ranknew.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 排行
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRankList extends GCMessage{
	
	/** 开始位置（包括） */
	private int start;
	/** 结束位置（包括） */
	private int end;
	/** 排行数据 */
	private com.gameserver.ranknew.data.RankListData[] rankListData;

	public GCRankList (){
	}
	
	public GCRankList (
			int start,
			int end,
			com.gameserver.ranknew.data.RankListData[] rankListData ){
			this.start = start;
			this.end = end;
			this.rankListData = rankListData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		start = readInteger();
		end = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rankListData = new com.gameserver.ranknew.data.RankListData[count];
		for(int i=0; i<count; i++){
			com.gameserver.ranknew.data.RankListData obj = new com.gameserver.ranknew.data.RankListData();
			obj.setCountries(readString());
			obj.setUserId(readLong());
			obj.setName(readString());
			obj.setImg(readString());
			obj.setLevel(readInteger());
			obj.setWin(readLong());
			obj.setRank(readInteger());
			obj.setVipLevel(readInteger());
			rankListData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(start);
		writeInteger(end);
		writeShort(rankListData.length);
		for(int i=0; i<rankListData.length; i++){
			writeString(rankListData[i].getCountries());
			writeLong(rankListData[i].getUserId());
			writeString(rankListData[i].getName());
			writeString(rankListData[i].getImg());
			writeInteger(rankListData[i].getLevel());
			writeLong(rankListData[i].getWin());
			writeInteger(rankListData[i].getRank());
			writeInteger(rankListData[i].getVipLevel());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RANK_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RANK_LIST";
	}

	public int getStart(){
		return start;
	}
		
	public void setStart(int start){
		this.start = start;
	}

	public int getEnd(){
		return end;
	}
		
	public void setEnd(int end){
		this.end = end;
	}

	public com.gameserver.ranknew.data.RankListData[] getRankListData(){
		return rankListData;
	}

	public void setRankListData(com.gameserver.ranknew.data.RankListData[] rankListData){
		this.rankListData = rankListData;
	}	
}