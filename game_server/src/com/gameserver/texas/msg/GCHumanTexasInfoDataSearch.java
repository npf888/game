package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州信息查询
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanTexasInfoDataSearch extends GCMessage{
	
	/** 玩家id */
	private long playerId;
	/** 德州sng信息 */
	private com.gameserver.texas.data.sng.HumanTexasSNGInfoData humanTexasSNGInfoData;
	/** 德州信息 */
	private com.gameserver.texas.data.HumanTexasInfoData humanTexasInfoData;

	public GCHumanTexasInfoDataSearch (){
	}
	
	public GCHumanTexasInfoDataSearch (
			long playerId,
			com.gameserver.texas.data.sng.HumanTexasSNGInfoData humanTexasSNGInfoData,
			com.gameserver.texas.data.HumanTexasInfoData humanTexasInfoData ){
			this.playerId = playerId;
			this.humanTexasSNGInfoData = humanTexasSNGInfoData;
			this.humanTexasInfoData = humanTexasInfoData;
	}

	@Override
	protected boolean readImpl() {
		playerId = readLong();
		humanTexasSNGInfoData = new com.gameserver.texas.data.sng.HumanTexasSNGInfoData();
					humanTexasSNGInfoData.setJoinTimes(readInteger());
							humanTexasSNGInfoData.setGoldNum(readInteger());
							humanTexasSNGInfoData.setSilverNum(readInteger());
							humanTexasSNGInfoData.setCopperNum(readInteger());
							humanTexasSNGInfoData.setWeekScore(readInteger());
				humanTexasInfoData = new com.gameserver.texas.data.HumanTexasInfoData();
					humanTexasInfoData.setCount(readInteger());
							humanTexasInfoData.setWinCount(readInteger());
							humanTexasInfoData.setWeekWinCoins(readInteger());
							humanTexasInfoData.setDayBiggestWinCoins(readInteger());
						{
			int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
			humanTexasInfoData.setBiggestHandCardList(subList);
		}
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(playerId);
		writeInteger(humanTexasSNGInfoData.getJoinTimes());
		writeInteger(humanTexasSNGInfoData.getGoldNum());
		writeInteger(humanTexasSNGInfoData.getSilverNum());
		writeInteger(humanTexasSNGInfoData.getCopperNum());
		writeInteger(humanTexasSNGInfoData.getWeekScore());
		writeInteger(humanTexasInfoData.getCount());
		writeInteger(humanTexasInfoData.getWinCount());
		writeInteger(humanTexasInfoData.getWeekWinCoins());
		writeInteger(humanTexasInfoData.getDayBiggestWinCoins());
		int[] biggestHandCardList=humanTexasInfoData.getBiggestHandCardList();
		writeShort(biggestHandCardList.length);
		for(int i=0; i<biggestHandCardList.length; i++){	
				writeInteger(biggestHandCardList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_TEXAS_INFO_DATA_SEARCH;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_TEXAS_INFO_DATA_SEARCH";
	}

	public long getPlayerId(){
		return playerId;
	}
		
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}

	public com.gameserver.texas.data.sng.HumanTexasSNGInfoData getHumanTexasSNGInfoData(){
		return humanTexasSNGInfoData;
	}
		
	public void setHumanTexasSNGInfoData(com.gameserver.texas.data.sng.HumanTexasSNGInfoData humanTexasSNGInfoData){
		this.humanTexasSNGInfoData = humanTexasSNGInfoData;
	}

	public com.gameserver.texas.data.HumanTexasInfoData getHumanTexasInfoData(){
		return humanTexasInfoData;
	}
		
	public void setHumanTexasInfoData(com.gameserver.texas.data.HumanTexasInfoData humanTexasInfoData){
		this.humanTexasInfoData = humanTexasInfoData;
	}
}