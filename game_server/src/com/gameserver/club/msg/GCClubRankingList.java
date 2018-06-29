package com.gameserver.club.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 俱乐部赛季排行
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCClubRankingList extends GCMessage{
	
	/** 1 活跃帮   2贡献榜 */
	private int opType;
	/** 俱乐部排行 */
	private com.gameserver.club.protocol.ClubListUnit[] list;
	/** 自己的俱乐部信息 */
	private com.gameserver.club.protocol.ClubListUnit self;
	/** 排名 */
	private int selfRank;

	public GCClubRankingList (){
	}
	
	public GCClubRankingList (
			int opType,
			com.gameserver.club.protocol.ClubListUnit[] list,
			com.gameserver.club.protocol.ClubListUnit self,
			int selfRank ){
			this.opType = opType;
			this.list = list;
			this.self = self;
			this.selfRank = selfRank;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		opType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		list = new com.gameserver.club.protocol.ClubListUnit[count];
		for(int i=0; i<count; i++){
			com.gameserver.club.protocol.ClubListUnit obj = new com.gameserver.club.protocol.ClubListUnit();
			obj.setClubId(readString());
			obj.setIco(readInteger());
			obj.setName(readString());
			obj.setType(readInteger());
			obj.setLevel(readInteger());
			obj.setLimit(readInteger());
			obj.setNum(readInteger());
			obj.setMaxNum(readInteger());
			obj.setHuoyue(readInteger());
			obj.setGongxian(readInteger());
			obj.setApplied(readInteger());
			list[i] = obj;
		}
		self = new com.gameserver.club.protocol.ClubListUnit();
					self.setClubId(readString());
							self.setIco(readInteger());
							self.setName(readString());
							self.setType(readInteger());
							self.setLevel(readInteger());
							self.setLimit(readInteger());
							self.setNum(readInteger());
							self.setMaxNum(readInteger());
							self.setHuoyue(readInteger());
							self.setGongxian(readInteger());
							self.setApplied(readInteger());
				selfRank = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(opType);
		writeShort(list.length);
		for(int i=0; i<list.length; i++){
			writeString(list[i].getClubId());
			writeInteger(list[i].getIco());
			writeString(list[i].getName());
			writeInteger(list[i].getType());
			writeInteger(list[i].getLevel());
			writeInteger(list[i].getLimit());
			writeInteger(list[i].getNum());
			writeInteger(list[i].getMaxNum());
			writeInteger(list[i].getHuoyue());
			writeInteger(list[i].getGongxian());
			writeInteger(list[i].getApplied());
		}
		writeString(self.getClubId());
		writeInteger(self.getIco());
		writeString(self.getName());
		writeInteger(self.getType());
		writeInteger(self.getLevel());
		writeInteger(self.getLimit());
		writeInteger(self.getNum());
		writeInteger(self.getMaxNum());
		writeInteger(self.getHuoyue());
		writeInteger(self.getGongxian());
		writeInteger(self.getApplied());
		writeInteger(selfRank);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CLUB_RANKING_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CLUB_RANKING_LIST";
	}

	public int getOpType(){
		return opType;
	}
		
	public void setOpType(int opType){
		this.opType = opType;
	}

	public com.gameserver.club.protocol.ClubListUnit[] getList(){
		return list;
	}

	public void setList(com.gameserver.club.protocol.ClubListUnit[] list){
		this.list = list;
	}	

	public com.gameserver.club.protocol.ClubListUnit getSelf(){
		return self;
	}
		
	public void setSelf(com.gameserver.club.protocol.ClubListUnit self){
		this.self = self;
	}

	public int getSelfRank(){
		return selfRank;
	}
		
	public void setSelfRank(int selfRank){
		this.selfRank = selfRank;
	}
}