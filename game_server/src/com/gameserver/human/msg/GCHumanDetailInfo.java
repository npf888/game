package com.gameserver.human.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 服务器给客户端下发人物全信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanDetailInfo extends GCMessage{
	
	/** 玩家 */
	private com.gameserver.human.data.HumanInfoData human;

	public GCHumanDetailInfo (){
	}
	
	public GCHumanDetailInfo (
			com.gameserver.human.data.HumanInfoData human ){
			this.human = human;
	}

	@Override
	protected boolean readImpl() {
		human = new com.gameserver.human.data.HumanInfoData();
					human.setRoleId(readLong());
							human.setName(readString());
							human.setSex(readInteger());
							human.setLevel(readLong());
							human.setVipLevel(readLong());
							human.setDiamond(readLong());
							human.setGold(readLong());
							human.setCurExp(readLong());
							human.setMaxExp(readLong());
							human.setCharm(readLong());
							human.setSceneId(readInteger());
							human.setGvfirst(readString());
							human.setNewguide(readString());
							human.setWatchNum(readInteger());
							human.setCountries(readString());
							human.setIntegral(readLong());
							human.setNewGuyGift(readInteger());
							human.setTodayView(readInteger());
							human.setBazooNewGuyProcess(readString());
							human.setBazooGold(readLong());
							human.setBazooRoom(readString());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(human.getRoleId());
		writeString(human.getName());
		writeInteger(human.getSex());
		writeLong(human.getLevel());
		writeLong(human.getVipLevel());
		writeLong(human.getDiamond());
		writeLong(human.getGold());
		writeLong(human.getCurExp());
		writeLong(human.getMaxExp());
		writeLong(human.getCharm());
		writeInteger(human.getSceneId());
		writeString(human.getGvfirst());
		writeString(human.getNewguide());
		writeInteger(human.getWatchNum());
		writeString(human.getCountries());
		writeLong(human.getIntegral());
		writeInteger(human.getNewGuyGift());
		writeInteger(human.getTodayView());
		writeString(human.getBazooNewGuyProcess());
		writeLong(human.getBazooGold());
		writeString(human.getBazooRoom());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_DETAIL_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_DETAIL_INFO";
	}

	public com.gameserver.human.data.HumanInfoData getHuman(){
		return human;
	}
		
	public void setHuman(com.gameserver.human.data.HumanInfoData human){
		this.human = human;
	}
}