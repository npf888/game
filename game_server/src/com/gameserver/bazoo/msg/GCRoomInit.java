package com.gameserver.bazoo.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 返回其他人的信息，大于等于 3个人的时候
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoomInit extends GCMessage{
	
	/** 公共场 or 私人场   0:公共 ，1:私人 */
	private int pubOrPri;
	/** 最后一个叫号的ID：如果是0就不是最后一个 */
	private long lastPassportId;
	/** 房间号 */
	private String roomNum;
	/** 等待时间（只有在第一次开始的时候） */
	private int waitTime;
	/** 每个用户叫号 间隔 */
	private int turnWait;
	/** 竞猜时间  间隔 */
	private int guessWait;
	/** 万能符是否叫过 0没有叫过，1叫过 */
	private int onePoint;
	/** 牛牛模式 统一摇完色子之后 到 重摇色子之前 */
	private int cowSwingToBegin;
	/** 牛牛模式 一局 的时间 */
	private int cowOneRoundTime;
	/** 牛牛模式 一局 用户 重摇结束到 结算之前 会有段时间 去看别人的色子 */
	private int cowLookDiceValueTime;
	/** 牛牛模式 结算时间 */
	private int cowEndCountTime;
	/** 梭哈模式 一个人代表的次数 */
	private int reShakingTimes;
	/** 梭哈模式 该轮到谁摇 色子 */
	private int showHandWhoTurn;
	/** 梭哈模式  用户摇 色子的时间 */
	private int showHandShakeTime;
	/** 梭哈模式  结束到开始 */
	private int showHandEndToStart;
	/** 牛牛模式  的庄家ID */
	private long bankPassportId;
	/** 结算返回 每个人的信息 */
	private com.gameserver.bazoo.data.EndCountInfo[] endCountInfo;
	/** 结算返回 每个人的信息 */
	private com.gameserver.bazoo.data.ShowHandBet[] showHandBet;
	/** 其他人的信息 */
	private com.gameserver.bazoo.data.ReturnPlayerInfo[] returnPlayerInfo;

	public GCRoomInit (){
	}
	
	public GCRoomInit (
			int pubOrPri,
			long lastPassportId,
			String roomNum,
			int waitTime,
			int turnWait,
			int guessWait,
			int onePoint,
			int cowSwingToBegin,
			int cowOneRoundTime,
			int cowLookDiceValueTime,
			int cowEndCountTime,
			int reShakingTimes,
			int showHandWhoTurn,
			int showHandShakeTime,
			int showHandEndToStart,
			long bankPassportId,
			com.gameserver.bazoo.data.EndCountInfo[] endCountInfo,
			com.gameserver.bazoo.data.ShowHandBet[] showHandBet,
			com.gameserver.bazoo.data.ReturnPlayerInfo[] returnPlayerInfo ){
			this.pubOrPri = pubOrPri;
			this.lastPassportId = lastPassportId;
			this.roomNum = roomNum;
			this.waitTime = waitTime;
			this.turnWait = turnWait;
			this.guessWait = guessWait;
			this.onePoint = onePoint;
			this.cowSwingToBegin = cowSwingToBegin;
			this.cowOneRoundTime = cowOneRoundTime;
			this.cowLookDiceValueTime = cowLookDiceValueTime;
			this.cowEndCountTime = cowEndCountTime;
			this.reShakingTimes = reShakingTimes;
			this.showHandWhoTurn = showHandWhoTurn;
			this.showHandShakeTime = showHandShakeTime;
			this.showHandEndToStart = showHandEndToStart;
			this.bankPassportId = bankPassportId;
			this.endCountInfo = endCountInfo;
			this.showHandBet = showHandBet;
			this.returnPlayerInfo = returnPlayerInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		pubOrPri = readInteger();
		lastPassportId = readLong();
		roomNum = readString();
		waitTime = readInteger();
		turnWait = readInteger();
		guessWait = readInteger();
		onePoint = readInteger();
		cowSwingToBegin = readInteger();
		cowOneRoundTime = readInteger();
		cowLookDiceValueTime = readInteger();
		cowEndCountTime = readInteger();
		reShakingTimes = readInteger();
		showHandWhoTurn = readInteger();
		showHandShakeTime = readInteger();
		showHandEndToStart = readInteger();
		bankPassportId = readLong();
		count = readShort();
		count = count < 0 ? 0 : count;
		endCountInfo = new com.gameserver.bazoo.data.EndCountInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.EndCountInfo obj = new com.gameserver.bazoo.data.EndCountInfo();
			obj.setPassportId(readLong());
			obj.setMultiple(readInteger());
			obj.setGold(readLong());
			obj.setName(readString());
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
				obj.setWinPassportId(subList);
			}
			{
				int subCount = readShort();
							int[] subList = new int[subCount];
							for(int j = 0; j < subCount; j++){
											subList[j] = readInteger();
									}
				obj.setWinMultiple(subList);
			}
			obj.setPersonTotalGold(readLong());
			endCountInfo[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		showHandBet = new com.gameserver.bazoo.data.ShowHandBet[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.ShowHandBet obj = new com.gameserver.bazoo.data.ShowHandBet();
			obj.setPassportId(readLong());
			obj.setBet(readInteger());
			obj.setGold(readLong());
			obj.setPersonTotalGold(readLong());
			showHandBet[i] = obj;
		}
		count = readShort();
		count = count < 0 ? 0 : count;
		returnPlayerInfo = new com.gameserver.bazoo.data.ReturnPlayerInfo[count];
		for(int i=0; i<count; i++){
			com.gameserver.bazoo.data.ReturnPlayerInfo obj = new com.gameserver.bazoo.data.ReturnPlayerInfo();
			obj.setPassportId(readLong());
			obj.setName(readString());
			obj.setGirlFlag(readInteger());
			obj.setUserStatus(readInteger());
			obj.setCurGold(readLong());
			obj.setHeadImg(readString());
			obj.setDiceContainer(readString());
			obj.setSeat(readInteger());
			obj.setWinTimes(readInteger());
			obj.setVipLevel(readInteger());
			obj.setNum(readInteger());
			obj.setValue(readInteger());
			obj.setDiceType(readInteger());
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
				obj.setRedDiceValues(subList);
			}
			obj.setClockImg(readString());
			obj.setClockItemId(readInteger());
			returnPlayerInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pubOrPri);
		writeLong(lastPassportId);
		writeString(roomNum);
		writeInteger(waitTime);
		writeInteger(turnWait);
		writeInteger(guessWait);
		writeInteger(onePoint);
		writeInteger(cowSwingToBegin);
		writeInteger(cowOneRoundTime);
		writeInteger(cowLookDiceValueTime);
		writeInteger(cowEndCountTime);
		writeInteger(reShakingTimes);
		writeInteger(showHandWhoTurn);
		writeInteger(showHandShakeTime);
		writeInteger(showHandEndToStart);
		writeLong(bankPassportId);
		writeShort(endCountInfo.length);
		for(int i=0; i<endCountInfo.length; i++){
			writeLong(endCountInfo[i].getPassportId());
			writeInteger(endCountInfo[i].getMultiple());
			writeLong(endCountInfo[i].getGold());
			writeString(endCountInfo[i].getName());
			int[] diceValues=endCountInfo[i].getDiceValues();
			writeShort(diceValues.length);
			for(int j=0; j<diceValues.length; j++){
				writeInteger(diceValues[j]);
			}
			int[] winPassportId=endCountInfo[i].getWinPassportId();
			writeShort(winPassportId.length);
			for(int j=0; j<winPassportId.length; j++){
				writeInteger(winPassportId[j]);
			}
			int[] winMultiple=endCountInfo[i].getWinMultiple();
			writeShort(winMultiple.length);
			for(int j=0; j<winMultiple.length; j++){
				writeInteger(winMultiple[j]);
			}
			writeLong(endCountInfo[i].getPersonTotalGold());
		}
		writeShort(showHandBet.length);
		for(int i=0; i<showHandBet.length; i++){
			writeLong(showHandBet[i].getPassportId());
			writeInteger(showHandBet[i].getBet());
			writeLong(showHandBet[i].getGold());
			writeLong(showHandBet[i].getPersonTotalGold());
		}
		writeShort(returnPlayerInfo.length);
		for(int i=0; i<returnPlayerInfo.length; i++){
			writeLong(returnPlayerInfo[i].getPassportId());
			writeString(returnPlayerInfo[i].getName());
			writeInteger(returnPlayerInfo[i].getGirlFlag());
			writeInteger(returnPlayerInfo[i].getUserStatus());
			writeLong(returnPlayerInfo[i].getCurGold());
			writeString(returnPlayerInfo[i].getHeadImg());
			writeString(returnPlayerInfo[i].getDiceContainer());
			writeInteger(returnPlayerInfo[i].getSeat());
			writeInteger(returnPlayerInfo[i].getWinTimes());
			writeInteger(returnPlayerInfo[i].getVipLevel());
			writeInteger(returnPlayerInfo[i].getNum());
			writeInteger(returnPlayerInfo[i].getValue());
			writeInteger(returnPlayerInfo[i].getDiceType());
			int[] diceValues=returnPlayerInfo[i].getDiceValues();
			writeShort(diceValues.length);
			for(int j=0; j<diceValues.length; j++){
				writeInteger(diceValues[j]);
			}
			int[] redDiceValues=returnPlayerInfo[i].getRedDiceValues();
			writeShort(redDiceValues.length);
			for(int j=0; j<redDiceValues.length; j++){
				writeInteger(redDiceValues[j]);
			}
			writeString(returnPlayerInfo[i].getClockImg());
			writeInteger(returnPlayerInfo[i].getClockItemId());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROOM_INIT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROOM_INIT";
	}

	public int getPubOrPri(){
		return pubOrPri;
	}
		
	public void setPubOrPri(int pubOrPri){
		this.pubOrPri = pubOrPri;
	}

	public long getLastPassportId(){
		return lastPassportId;
	}
		
	public void setLastPassportId(long lastPassportId){
		this.lastPassportId = lastPassportId;
	}

	public String getRoomNum(){
		return roomNum;
	}
		
	public void setRoomNum(String roomNum){
		this.roomNum = roomNum;
	}

	public int getWaitTime(){
		return waitTime;
	}
		
	public void setWaitTime(int waitTime){
		this.waitTime = waitTime;
	}

	public int getTurnWait(){
		return turnWait;
	}
		
	public void setTurnWait(int turnWait){
		this.turnWait = turnWait;
	}

	public int getGuessWait(){
		return guessWait;
	}
		
	public void setGuessWait(int guessWait){
		this.guessWait = guessWait;
	}

	public int getOnePoint(){
		return onePoint;
	}
		
	public void setOnePoint(int onePoint){
		this.onePoint = onePoint;
	}

	public int getCowSwingToBegin(){
		return cowSwingToBegin;
	}
		
	public void setCowSwingToBegin(int cowSwingToBegin){
		this.cowSwingToBegin = cowSwingToBegin;
	}

	public int getCowOneRoundTime(){
		return cowOneRoundTime;
	}
		
	public void setCowOneRoundTime(int cowOneRoundTime){
		this.cowOneRoundTime = cowOneRoundTime;
	}

	public int getCowLookDiceValueTime(){
		return cowLookDiceValueTime;
	}
		
	public void setCowLookDiceValueTime(int cowLookDiceValueTime){
		this.cowLookDiceValueTime = cowLookDiceValueTime;
	}

	public int getCowEndCountTime(){
		return cowEndCountTime;
	}
		
	public void setCowEndCountTime(int cowEndCountTime){
		this.cowEndCountTime = cowEndCountTime;
	}

	public int getReShakingTimes(){
		return reShakingTimes;
	}
		
	public void setReShakingTimes(int reShakingTimes){
		this.reShakingTimes = reShakingTimes;
	}

	public int getShowHandWhoTurn(){
		return showHandWhoTurn;
	}
		
	public void setShowHandWhoTurn(int showHandWhoTurn){
		this.showHandWhoTurn = showHandWhoTurn;
	}

	public int getShowHandShakeTime(){
		return showHandShakeTime;
	}
		
	public void setShowHandShakeTime(int showHandShakeTime){
		this.showHandShakeTime = showHandShakeTime;
	}

	public int getShowHandEndToStart(){
		return showHandEndToStart;
	}
		
	public void setShowHandEndToStart(int showHandEndToStart){
		this.showHandEndToStart = showHandEndToStart;
	}

	public long getBankPassportId(){
		return bankPassportId;
	}
		
	public void setBankPassportId(long bankPassportId){
		this.bankPassportId = bankPassportId;
	}

	public com.gameserver.bazoo.data.EndCountInfo[] getEndCountInfo(){
		return endCountInfo;
	}

	public void setEndCountInfo(com.gameserver.bazoo.data.EndCountInfo[] endCountInfo){
		this.endCountInfo = endCountInfo;
	}	

	public com.gameserver.bazoo.data.ShowHandBet[] getShowHandBet(){
		return showHandBet;
	}

	public void setShowHandBet(com.gameserver.bazoo.data.ShowHandBet[] showHandBet){
		this.showHandBet = showHandBet;
	}	

	public com.gameserver.bazoo.data.ReturnPlayerInfo[] getReturnPlayerInfo(){
		return returnPlayerInfo;
	}

	public void setReturnPlayerInfo(com.gameserver.bazoo.data.ReturnPlayerInfo[] returnPlayerInfo){
		this.returnPlayerInfo = returnPlayerInfo;
	}	
}