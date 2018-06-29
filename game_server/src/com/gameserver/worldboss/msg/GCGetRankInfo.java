package com.gameserver.worldboss.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回 伤害 排行榜 的 信息 请求
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCGetRankInfo extends GCMessage{
	
	/** 当前用户所在排行榜的位置 */
	private int curIndex;
	/** boss的信息 */
	private com.gameserver.worldboss.data.AttackRankData[] attackRankData;

	public GCGetRankInfo (){
	}
	
	public GCGetRankInfo (
			int curIndex,
			com.gameserver.worldboss.data.AttackRankData[] attackRankData ){
			this.curIndex = curIndex;
			this.attackRankData = attackRankData;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		curIndex = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		attackRankData = new com.gameserver.worldboss.data.AttackRankData[count];
		for(int i=0; i<count; i++){
			com.gameserver.worldboss.data.AttackRankData obj = new com.gameserver.worldboss.data.AttackRankData();
			obj.setName(readString());
			obj.setBossId(readLong());
			obj.setUserId(readLong());
			obj.setAttackTotalBlood(readLong());
			obj.setVipAdditionRate(readInteger());
			obj.setAttackRate(readInteger());
			obj.setSortIndex(readInteger());
			attackRankData[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(curIndex);
		writeShort(attackRankData.length);
		for(int i=0; i<attackRankData.length; i++){
			writeString(attackRankData[i].getName());
			writeLong(attackRankData[i].getBossId());
			writeLong(attackRankData[i].getUserId());
			writeLong(attackRankData[i].getAttackTotalBlood());
			writeInteger(attackRankData[i].getVipAdditionRate());
			writeInteger(attackRankData[i].getAttackRate());
			writeInteger(attackRankData[i].getSortIndex());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_RANK_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_RANK_INFO";
	}

	public int getCurIndex(){
		return curIndex;
	}
		
	public void setCurIndex(int curIndex){
		this.curIndex = curIndex;
	}

	public com.gameserver.worldboss.data.AttackRankData[] getAttackRankData(){
		return attackRankData;
	}

	public void setAttackRankData(com.gameserver.worldboss.data.AttackRankData[] attackRankData){
		this.attackRankData = attackRankData;
	}	
}