package com.gameserver.bazoo.data;
/**
 * 每个用户进入到房间 要获取其他 用户的信息
 * @author JavaServer
 *
 */
public class ReturnPlayerInfo {

	//其他用户ID
	private long passportId;
	//其他用户名称
	private String name;
	//性别 0:女       1:男
	private Integer girlFlag;
	//用户当前的状态 0：观战   未参与(因为刚进入房间 别的用户 正在玩，所以此时只能观战)    1：参与      2:纯粹的观战模式 （进去就是看看 什么其他的也不干）
	private Integer userStatus;
	//其他用户金币
	private long curGold;
	//其他用户头像
	private String headImg;
	//色钟 不同状态 的色子 有不同的色钟
	private String diceContainer;
	private Integer seat;
	//几连胜
	private int winTimes;
	//vip等级
	private int vipLevel;
	//用户最后叫号 色子的个数
	private int num;
	//用户最后叫号 色子的值
	private int value;
	
	public int diceType;
	public int[] diceValues;
	public int[] redDiceValues;
	
	//色钟图片
	private String clockImg;
	//色钟itemId
	private int clockItemId;
	
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
	public long getCurGold() {
		return curGold;
	}
	public void setCurGold(long curGold) {
		this.curGold = curGold;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getDiceContainer() {
		return diceContainer;
	}
	public void setDiceContainer(String diceContainer) {
		this.diceContainer = diceContainer;
	}
	public Integer getSeat() {
		return seat;
	}
	public void setSeat(Integer seat) {
		this.seat = seat;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getWinTimes() {
		return winTimes;
	}
	public void setWinTimes(int winTimes) {
		this.winTimes = winTimes;
	}
	public int getVipLevel() {
		return vipLevel;
	}
	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	public Integer getGirlFlag() {
		return girlFlag;
	}
	public void setGirlFlag(Integer girlFlag) {
		this.girlFlag = girlFlag;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public int getDiceType() {
		return diceType;
	}
	public void setDiceType(int diceType) {
		this.diceType = diceType;
	}
	public int[] getDiceValues() {
		return diceValues;
	}
	public void setDiceValues(int[] diceValues) {
		this.diceValues = diceValues;
	}
	public int[] getRedDiceValues() {
		return redDiceValues;
	}
	public void setRedDiceValues(int[] redDiceValues) {
		this.redDiceValues = redDiceValues;
	}
	
	public String getClockImg() {
		return clockImg;
	}
	public void setClockImg(String clockImg) {
		this.clockImg = clockImg;
	}
	public int getClockItemId() {
		return clockItemId;
	}
	public void setClockItemId(int clockItemId) {
		this.clockItemId = clockItemId;
	}
	
	
	
	
}
