package com.gameserver.texas.data;

import com.db.model.HumanTexasEntity;
import com.gameserver.texas.HumanTexas;


/**
 * 玩家德州数据
 * @author wayne
 *
 */
public class HumanTexasInfoData {
	//总数
	private int count;
	//获胜局数
	private int winCount;
	//最大牌型
	private int[] biggestHandCardList;
	//周获胜筹码
	private int weekWinCoins;
	//每天获胜筹码
	private int dayBiggestWinCoins;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getWinCount() {
		return winCount;
	}
	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	public int[] getBiggestHandCardList() {
		return biggestHandCardList;
	}
	public void setBiggestHandCardList(int[] biggestHandCardList) {
		this.biggestHandCardList = biggestHandCardList;
	}
	public int getWeekWinCoins() {
		return weekWinCoins;
	}
	public void setWeekWinCoins(int weekWinCoins) {
		this.weekWinCoins = weekWinCoins;
	}
	public int getDayBiggestWinCoins() {
		return dayBiggestWinCoins;
	}
	public void setDayBiggestWinCoins(int dayBiggestWinCoins) {
		this.dayBiggestWinCoins = dayBiggestWinCoins;
	}
	
	public static HumanTexasInfoData convertFromHumanTexas(HumanTexas humanTexas){
		HumanTexasInfoData tempHumanTexasInfoData = new HumanTexasInfoData();
		int[] biggestCardArr = new int[humanTexas.getBiggestHandCardList()
		               				.size()];
   		for (int i = 0; i < humanTexas.getBiggestHandCardList().size(); i++) {
   			biggestCardArr[i] = humanTexas.getBiggestHandCardList().get(i)
   					.getValue();
   		}
   		tempHumanTexasInfoData.setCount(humanTexas.getCount());
		tempHumanTexasInfoData.setBiggestHandCardList(biggestCardArr);
		tempHumanTexasInfoData.setDayBiggestWinCoins(humanTexas.getDayBiggestWinCoins());
		tempHumanTexasInfoData.setWeekWinCoins(humanTexas.getWeekWinCoins());
		tempHumanTexasInfoData.setWinCount(humanTexas.getWinCount());
		return tempHumanTexasInfoData;
	}
	
	public static HumanTexasInfoData convertFromHumanTexasEntity(HumanTexasEntity humanTexasEntity){
		
		HumanTexas tempHumanTexas =  new HumanTexas();
		tempHumanTexas.fromEntity(humanTexasEntity);
		return convertFromHumanTexas(tempHumanTexas);
	}

}
