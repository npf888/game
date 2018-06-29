package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 根据状态返回消息 15
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCStateRoomBlackWhiteSomeOneCall extends GCMessage{
	
	/** 是谁叫的号 */
	private long whoCallPassportId;
	/** 叫的是 什么（1:红，2：黑，3：单，4：双） */
	private int diceType;
	/** 几杀 */
	private int killNum;
	/** 变化的倍数 */
	private int multiple;
	/** 结算返回 每个人的信息 */
	private com.gameserver.bazoo.data.BlackWhiteBet[] blackWhiteBet;
	/** 所有人色子的剩余值 */
	private com.gameserver.bazoo.data.BlackWhiteDiceInfo[] blackWhiteDiceInfo;

	public GCStateRoomBlackWhiteSomeOneCall (){
	}
	
	public GCStateRoomBlackWhiteSomeOneCall (
			long whoCallPassportId,
			int diceType,
			int killNum,
			int multiple,
			com.gameserver.bazoo.data.BlackWhiteBet[] blackWhiteBet,
			com.gameserver.bazoo.data.BlackWhiteDiceInfo[] blackWhiteDiceInfo ){
			this.whoCallPassportId = whoCallPassportId;
			this.diceType = diceType;
			this.killNum = killNum;
			this.multiple = multiple;
			this.blackWhiteBet = blackWhiteBet;
			this.blackWhiteDiceInfo = blackWhiteDiceInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		whoCallPassportId = readLong();
		diceType = readInteger();
		killNum = readInteger();
		multiple = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		blackWhiteBet = new com.gameserver.bazoo.data.BlackWhiteBet[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.BlackWhiteBet obj = new com.gameserver.bazoo.data.BlackWhiteBet();
			obj.setPassportId(readLong());
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setBet(subList);
			}
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setWinPassportIds(subList);
			}
			obj.setGold(readLong());
			blackWhiteBet[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		blackWhiteDiceInfo = new com.gameserver.bazoo.data.BlackWhiteDiceInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.BlackWhiteDiceInfo obj = new com.gameserver.bazoo.data.BlackWhiteDiceInfo();
			obj.setPassportId(readLong());
			obj.setIsOut(readInteger());
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setDiceValues(subList);
			}
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setRemoveDiceValues(subList);
			}
			blackWhiteDiceInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(whoCallPassportId);
		writeInteger(diceType);
		writeInteger(killNum);
		writeInteger(multiple);
		writeShort(blackWhiteBet.length);
		for(int i=0; i<blackWhiteBet.length; i++){
			writeLong(blackWhiteBet[i].getPassportId());
			int[] bet=blackWhiteBet[i].getBet();
			writeShort(bet.length);
			for(int j=0; j<bet.length; j++){
				writeInteger(bet[j]);
			}
			int[] winPassportIds=blackWhiteBet[i].getWinPassportIds();
			writeShort(winPassportIds.length);
			for(int j=0; j<winPassportIds.length; j++){
				writeInteger(winPassportIds[j]);
			}
			writeLong(blackWhiteBet[i].getGold());
		}
		writeShort(blackWhiteDiceInfo.length);
		for(int i=0; i<blackWhiteDiceInfo.length; i++){
			writeLong(blackWhiteDiceInfo[i].getPassportId());
			writeInteger(blackWhiteDiceInfo[i].getIsOut());
			int[] diceValues=blackWhiteDiceInfo[i].getDiceValues();
			writeShort(diceValues.length);
			for(int j=0; j<diceValues.length; j++){
				writeInteger(diceValues[j]);
			}
			int[] removeDiceValues=blackWhiteDiceInfo[i].getRemoveDiceValues();
			writeShort(removeDiceValues.length);
			for(int j=0; j<removeDiceValues.length; j++){
				writeInteger(removeDiceValues[j]);
			}
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL";
	}

	public long getWhoCallPassportId(){
		return whoCallPassportId;
	}
		
	public void setWhoCallPassportId(long whoCallPassportId){
		this.whoCallPassportId = whoCallPassportId;
	}

	public int getDiceType(){
		return diceType;
	}
		
	public void setDiceType(int diceType){
		this.diceType = diceType;
	}

	public int getKillNum(){
		return killNum;
	}
		
	public void setKillNum(int killNum){
		this.killNum = killNum;
	}

	public int getMultiple(){
		return multiple;
	}
		
	public void setMultiple(int multiple){
		this.multiple = multiple;
	}

	public com.gameserver.bazoo.data.BlackWhiteBet[] getBlackWhiteBet(){
		return blackWhiteBet;
	}

	public void setBlackWhiteBet(com.gameserver.bazoo.data.BlackWhiteBet[] blackWhiteBet){
		this.blackWhiteBet = blackWhiteBet;
	}	

	public com.gameserver.bazoo.data.BlackWhiteDiceInfo[] getBlackWhiteDiceInfo(){
		return blackWhiteDiceInfo;
	}

	public void setBlackWhiteDiceInfo(com.gameserver.bazoo.data.BlackWhiteDiceInfo[] blackWhiteDiceInfo){
		this.blackWhiteDiceInfo = blackWhiteDiceInfo;
	}	
}