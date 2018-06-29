package com.gameserver.bazoorank.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 排行榜返回
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCBazooRankRequest extends GCMessage{
	
	/** 排行榜 人物信息 */
	private com.gameserver.bazoorank.data.HumanRankInfo[] humanRankInfo;

	public GCBazooRankRequest (){
	}
	
	public GCBazooRankRequest (
			com.gameserver.bazoorank.data.HumanRankInfo[] humanRankInfo ){
			this.humanRankInfo = humanRankInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		humanRankInfo = new com.gameserver.bazoorank.data.HumanRankInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoorank.data.HumanRankInfo obj = new com.gameserver.bazoorank.data.HumanRankInfo();
			obj.setPassportId(readLong());
			obj.setVip(readInteger());
			obj.setName(readString());
			obj.setHeadImg(readString());
			obj.setGold(readLong());
			humanRankInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(humanRankInfo.length);
		for(int i=0; i<humanRankInfo.length; i++){
			writeLong(humanRankInfo[i].getPassportId());
			writeInteger(humanRankInfo[i].getVip());
			writeString(humanRankInfo[i].getName());
			writeString(humanRankInfo[i].getHeadImg());
			writeLong(humanRankInfo[i].getGold());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAZOO_RANK_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAZOO_RANK_REQUEST";
	}

	public com.gameserver.bazoorank.data.HumanRankInfo[] getHumanRankInfo(){
		return humanRankInfo;
	}

	public void setHumanRankInfo(com.gameserver.bazoorank.data.HumanRankInfo[] humanRankInfo){
		this.humanRankInfo = humanRankInfo;
	}	
}