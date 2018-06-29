package com.gameserver.texas.msg;

import com.gameserver.common.msg.MessageType;
import com.gameserver.common.msg.GCMessage;

/**
 * 德州基本信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHumanTexasInfoData extends GCMessage{
	
	/** 总数 */
	private int totalCount;
	/** 获胜数 */
	private int winCount;
	/** 自动 */
	private int isAuto;
	/** 人数 */
	private int people;
	/** 周盈利 */
	private int weekWinCoins;
	/** 单日最高盈利 */
	private int dayBiggestWinCoins;
	/** 最大手牌 */
	private int[] biggestHandCardList;

	public GCHumanTexasInfoData (){
	}
	
	public GCHumanTexasInfoData (
			int totalCount,
			int winCount,
			int isAuto,
			int people,
			int weekWinCoins,
			int dayBiggestWinCoins,
			int[] biggestHandCardList ){
			this.totalCount = totalCount;
			this.winCount = winCount;
			this.isAuto = isAuto;
			this.people = people;
			this.weekWinCoins = weekWinCoins;
			this.dayBiggestWinCoins = dayBiggestWinCoins;
			this.biggestHandCardList = biggestHandCardList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		totalCount = readInteger();
		winCount = readInteger();
		isAuto = readInteger();
		people = readInteger();
		weekWinCoins = readInteger();
		dayBiggestWinCoins = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		biggestHandCardList = new int[count];
		for(int i=0; i<count; i++){
			biggestHandCardList[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(totalCount);
		writeInteger(winCount);
		writeInteger(isAuto);
		writeInteger(people);
		writeInteger(weekWinCoins);
		writeInteger(dayBiggestWinCoins);
		writeShort(biggestHandCardList.length);
		for(int i=0; i<biggestHandCardList.length; i++){
			writeInteger(biggestHandCardList[i]);
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_TEXAS_INFO_DATA;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_TEXAS_INFO_DATA";
	}

	public int getTotalCount(){
		return totalCount;
	}
		
	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
	}

	public int getWinCount(){
		return winCount;
	}
		
	public void setWinCount(int winCount){
		this.winCount = winCount;
	}

	public int getIsAuto(){
		return isAuto;
	}
		
	public void setIsAuto(int isAuto){
		this.isAuto = isAuto;
	}

	public int getPeople(){
		return people;
	}
		
	public void setPeople(int people){
		this.people = people;
	}

	public int getWeekWinCoins(){
		return weekWinCoins;
	}
		
	public void setWeekWinCoins(int weekWinCoins){
		this.weekWinCoins = weekWinCoins;
	}

	public int getDayBiggestWinCoins(){
		return dayBiggestWinCoins;
	}
		
	public void setDayBiggestWinCoins(int dayBiggestWinCoins){
		this.dayBiggestWinCoins = dayBiggestWinCoins;
	}

	public int[] getBiggestHandCardList(){
		return biggestHandCardList;
	}

	public void setBiggestHandCardList(int[] biggestHandCardList){
		this.biggestHandCardList = biggestHandCardList;
	}	
}