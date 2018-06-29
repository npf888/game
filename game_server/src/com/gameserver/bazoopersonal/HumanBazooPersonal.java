package com.gameserver.bazoopersonal;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanBazooPersonalEntity;
import com.gameserver.human.Human;

public class HumanBazooPersonal implements PersistanceObject<Long, HumanBazooPersonalEntity>{

	
	
	private Human owner;
	private boolean inDb;
	private LifeCycle lifeCycle;
	
	
	private long id;
	private long passportId;
	private int modeType;

	
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
	
	
	private long dayProfit;
	private long weekProfit;
	private long monthProfit;
	
	
	public HumanBazooPersonal(Human human){
		this.lifeCycle = new LifeCycleImpl(this);
		this.owner=human;
	}
	
	@Override
	public void setDbId(Long id) {
		this.id=id;
		
	}

	@Override
	public Long getDbId() {
		return this.id;
	}

	@Override
	public String getGUID() {
		return "HumanBazooPersonal#"+id;
	}

	@Override
	public boolean isInDb() {
		return this.inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb=inDb;
		
	}

	@Override
	public long getCharId() {
		return this.passportId;
	}
	
	
	public void active(){
		getLifeCycle().activate();
	}
	
	
	
	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public void setLifeCycle(LifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
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

	public int getAWinningStreak() {
		return aWinningStreak;
	}

	public void setAWinningStreak(int aWinningStreak) {
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

	@Override
	public HumanBazooPersonalEntity toEntity() {
		HumanBazooPersonalEntity entity = new HumanBazooPersonalEntity();
		entity.setId(id);
		entity.setPassportId(passportId);
		entity.setModeType(modeType);
		
		entity.setNumberOfGame(numberOfGame);
		entity.setWinTimes(winTimes);
		entity.setSingleTopGold(singleTopGold);
		entity.setRateOfWinning(rateOfWinning);
		entity.setaWinningStreak(aWinningStreak);
		entity.setPassToKill(passToKill);
		entity.setBigPatterns(bigPatterns);
		entity.setPantherNumber(pantherNumber);
		entity.setThreeKill(threeKill);
		entity.setFourKill(fourKill);
		entity.setFiveKill(fiveKill);
		entity.setDayProfit(dayProfit);
		entity.setWeekProfit(weekProfit);
		entity.setMonthProfit(monthProfit);
		return entity;
	}

	@Override
	public void fromEntity(HumanBazooPersonalEntity entity) {
		this.setId(entity.getId());
		this.setPassportId(entity.getPassportId());
		this.setModeType(entity.getModeType());
		
		this.setNumberOfGame(entity.getNumberOfGame());
		this.setWinTimes(entity.getWinTimes());
		this.setSingleTopGold(entity.getSingleTopGold());
		this.setRateOfWinning(entity.getRateOfWinning());
		this.setAWinningStreak(entity.getaWinningStreak());
		this.setPassToKill(entity.getPassToKill());
		this.setBigPatterns(entity.getBigPatterns());
		this.setPantherNumber(entity.getPantherNumber());
		this.setThreeKill(entity.getThreeKill());
		this.setFourKill(entity.getFourKill());
		this.setFiveKill(entity.getFiveKill());
		
		
		this.setDayProfit(entity.getDayProfit());
		this.setWeekProfit(entity.getWeekProfit());
		this.setMonthProfit(entity.getMonthProfit());
		this.setInDb(true);
		this.active();
	}

	@Override
	public LifeCycle getLifeCycle() {
		return this.lifeCycle;
	}

	@Override
	public void setModified() {
		onUpdate();
	}
	
	
	
	private void onUpdate()
	{
		if (owner != null) 
		{
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}

}
