package com.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

@Entity
@Table(name = "t_human_bazoo_personal")
public class HumanBazooPersonalEntity implements BaseEntity<Long>{

	private long id;
	private long passportId;
	//局数
	private int numberOfGame;
	
	/**
	 * 吹牛模式
	 */
	
	//单局最高
	private long singleTopGold;
	//赢的次数
	private int winTimes;
	//胜率
	private int rateOfWinning;
	//连胜
	private int aWinningStreak;
	
	/**
	 * 牛牛模式
	 */
	
	//通杀
	private int passToKill;
	//最大牌型
	private String bigPatterns;
	
	
	/**
	 * 梭哈模式
	 */
	
	//豹子数
	private int pantherNumber;
	
	/**
	 * 红黑单双 模式
	 */
	
	//三杀
	private int threeKill;
	//四杀
	private int fourKill;
	//五杀
	private int fiveKill;
	
	
	
	private int modeType;
	private long dayProfit;
	private long weekProfit;
	private long monthProfit;
	
	
	
	@Id
	@Override
	public Long getId() {
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
		
	}
	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public int getModeType() {
		return modeType;
	}

	public void setModeType(int modeType) {
		this.modeType = modeType;
	}

	public long getDayProfit() {
		return dayProfit;
	}

	public void setDayProfit(long dayProfit) {
		this.dayProfit = dayProfit;
	}

	public long getWeekProfit() {
		return weekProfit;
	}

	public void setWeekProfit(long weekProfit) {
		this.weekProfit = weekProfit;
	}

	public long getMonthProfit() {
		return monthProfit;
	}

	public void setMonthProfit(long monthProfit) {
		this.monthProfit = monthProfit;
	}

	public void setId(long id) {
		this.id = id;
	}
	public int getNumberOfGame() {
		return numberOfGame;
	}
	public long getSingleTopGold() {
		return singleTopGold;
	}
	public void setSingleTopGold(long singleTopGold) {
		this.singleTopGold = singleTopGold;
	}
	public int getRateOfWinning() {
		return rateOfWinning;
	}
	public void setRateOfWinning(int rateOfWinning) {
		this.rateOfWinning = rateOfWinning;
	}
	public int getaWinningStreak() {
		return aWinningStreak;
	}
	public void setaWinningStreak(int aWinningStreak) {
		this.aWinningStreak = aWinningStreak;
	}
	public int getPassToKill() {
		return passToKill;
	}
	public void setPassToKill(int passToKill) {
		this.passToKill = passToKill;
	}
	public String getBigPatterns() {
		return bigPatterns;
	}
	public void setBigPatterns(String bigPatterns) {
		this.bigPatterns = bigPatterns;
	}
	public int getPantherNumber() {
		return pantherNumber;
	}
	public void setPantherNumber(int pantherNumber) {
		this.pantherNumber = pantherNumber;
	}
	public void setNumberOfGame(int numberOfGame) {
		this.numberOfGame = numberOfGame;
	}
	public int getWinTimes() {
		return winTimes;
	}
	public void setWinTimes(int winTimes) {
		this.winTimes = winTimes;
	}
	public int getThreeKill() {
		return threeKill;
	}
	public void setThreeKill(int threeKill) {
		this.threeKill = threeKill;
	}
	public int getFourKill() {
		return fourKill;
	}
	public void setFourKill(int fourKill) {
		this.fourKill = fourKill;
	}
	public int getFiveKill() {
		return fiveKill;
	}
	public void setFiveKill(int fiveKill) {
		this.fiveKill = fiveKill;
	}
	

	
	
	
}
