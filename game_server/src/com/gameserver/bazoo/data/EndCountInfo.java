package com.gameserver.bazoo.data;
/**
 * 最终结算的信息
 * @author JavaServer
 *
 */
public class EndCountInfo {

	//当前用户
	private long passportId;
	//所翻的倍数
	private int multiple;
	//输赢 多少钱  整数 是 赢钱，负数 是输钱
	private long gold;
	//用户摇到号的名称 例如  ：五小牛 - 豹子  等等
	private String name;
	
	/**
	 * 输的人 会向赢得人发送 倍
	 * 这个就是 赢得人 的集合 
	 * 下边的两个参数一一对应（顺序对应）
	 * @author JavaServer
	 */
	private int[] winPassportId;//赢得人的ID
	private int[] winMultiple;//倍数
	
	
	//色子 的值
	private int[] diceValues;
	
	//用户 自己的总金币
	private long personTotalGold;
	
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	public long getGold() {
		return gold;
	}
	public void setGold(long gold) {
		this.gold = gold;
	}
	public int[] getDiceValues() {
		return diceValues;
	}
	public void setDiceValues(int[] diceValues) {
		this.diceValues = diceValues;
	}
	public long getPassportId() {
		return passportId;
	}
	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getWinPassportId() {
		return winPassportId;
	}
	public void setWinPassportId(int[] winPassportId) {
		this.winPassportId = winPassportId;
	}
	public int[] getWinMultiple() {
		return winMultiple;
	}
	public void setWinMultiple(int[] winMultiple) {
		this.winMultiple = winMultiple;
	}
	public long getPersonTotalGold() {
		return personTotalGold;
	}
	public void setPersonTotalGold(long personTotalGold) {
		this.personTotalGold = personTotalGold;
	}
	
	
}
