package com.gameserver.slot.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 自己获奖记录返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSngRankInfo extends GCMessage{
	
	/** 个人获奖列表 */
	private com.gameserver.slot.data.HumanSngInfo[] humanSngInfos;

	public GCSngRankInfo (){
	}
	
	public GCSngRankInfo (
			com.gameserver.slot.data.HumanSngInfo[] humanSngInfos ){
			this.humanSngInfos = humanSngInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		humanSngInfos = new com.gameserver.slot.data.HumanSngInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.slot.data.HumanSngInfo obj = new com.gameserver.slot.data.HumanSngInfo();
			obj.setTournamentType(readInteger());
			obj.setRank(readInteger());
			obj.setRewardNum(readLong());
			obj.setRewardTime(readLong());
			humanSngInfos[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(humanSngInfos.length);
		for(int i=0; i<humanSngInfos.length; i++){
			writeInteger(humanSngInfos[i].getTournamentType());
			writeInteger(humanSngInfos[i].getRank());
			writeLong(humanSngInfos[i].getRewardNum());
			writeLong(humanSngInfos[i].getRewardTime());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SNG_RANK_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SNG_RANK_INFO";
	}

	public com.gameserver.slot.data.HumanSngInfo[] getHumanSngInfos(){
		return humanSngInfos;
	}

	public void setHumanSngInfos(com.gameserver.slot.data.HumanSngInfo[] humanSngInfos){
		this.humanSngInfos = humanSngInfos;
	}	
}