package com.gameserver.rank.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 排行
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCCommonRank extends GCMessage{
	
	/** 自己排名 */
	private long selfRank;
	/** 页面 */
	private int page;
	/** 排行版类型 */
	private int rankType;
	/** 排行数据 */
	private com.gameserver.rank.data.RankData[] rankDataList;

	public GCCommonRank (){
	}
	
	public GCCommonRank (
			long selfRank,
			int page,
			int rankType,
			com.gameserver.rank.data.RankData[] rankDataList ){
			this.selfRank = selfRank;
			this.page = page;
			this.rankType = rankType;
			this.rankDataList = rankDataList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		selfRank = readLong();
		page = readInteger();
		rankType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rankDataList = new com.gameserver.rank.data.RankData[count];
		for(int i=0; i<count; i++){
			com.gameserver.rank.data.RankData obj = new com.gameserver.rank.data.RankData();
			obj.setUId(readLong());
			obj.setName(readString());
			obj.setImg(readString());
			obj.setScore(readLong());
			rankDataList[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(selfRank);
		writeInteger(page);
		writeInteger(rankType);
		writeShort(rankDataList.length);
		for(int i=0; i<rankDataList.length; i++){
			writeLong(rankDataList[i].getUId());
			writeString(rankDataList[i].getName());
			writeString(rankDataList[i].getImg());
			writeLong(rankDataList[i].getScore());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_COMMON_RANK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_COMMON_RANK";
	}

	public long getSelfRank(){
		return selfRank;
	}
		
	public void setSelfRank(long selfRank){
		this.selfRank = selfRank;
	}

	public int getPage(){
		return page;
	}
		
	public void setPage(int page){
		this.page = page;
	}

	public int getRankType(){
		return rankType;
	}
		
	public void setRankType(int rankType){
		this.rankType = rankType;
	}

	public com.gameserver.rank.data.RankData[] getRankDataList(){
		return rankDataList;
	}

	public void setRankDataList(com.gameserver.rank.data.RankData[] rankDataList){
		this.rankDataList = rankDataList;
	}	
}