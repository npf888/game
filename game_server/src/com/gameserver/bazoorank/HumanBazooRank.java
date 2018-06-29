package com.gameserver.bazoorank;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.HumanBazooRankEntity;
import com.db.model.HumanBazooSigninEntity;
import com.gameserver.human.Human;

public class HumanBazooRank implements PersistanceObject<Long, HumanBazooRankEntity>{

	
	private Human owner;
	private boolean inDb;
	private LifeCycle lifeCycle;
	
	private long id;
	private long passportId;
	private String name;
	private String headImg;
	private long dayProfit;
	private long weekProfit;
	private long monthProfit;
	private int bazooAgentDisplay;
	private int bazooRobotDisplay;
	
	public HumanBazooRank(Human human){
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
		return "HumanBazooRank#"+this.id;
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
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public int getBazooAgentDisplay() {
		return bazooAgentDisplay;
	}

	public void setBazooAgentDisplay(int bazooAgentDisplay) {
		this.bazooAgentDisplay = bazooAgentDisplay;
	}

	public int getBazooRobotDisplay() {
		return bazooRobotDisplay;
	}

	public void setBazooRobotDisplay(int bazooRobotDisplay) {
		this.bazooRobotDisplay = bazooRobotDisplay;
	}

	public void active(){
		getLifeCycle().activate();
	}
	@Override
	public HumanBazooRankEntity toEntity() {
		HumanBazooRankEntity entity = new HumanBazooRankEntity();
		entity.setId(id);
		entity.setPassportId(passportId);
		entity.setName(name);
		entity.setHeadImg(headImg);
		entity.setDayProfit(dayProfit);
		entity.setWeekProfit(weekProfit);
		entity.setMonthProfit(monthProfit);
		entity.setBazooAgentDisplay(bazooAgentDisplay);
		entity.setBazooRobotDisplay(bazooRobotDisplay);
		return entity;
	}

	@Override
	public void fromEntity(HumanBazooRankEntity entity) {
		this.setId(entity.getId());
		this.setPassportId(entity.getPassportId());
		this.setName(entity.getName());
		this.setHeadImg(entity.getHeadImg());
		this.setDayProfit(entity.getDayProfit());
		this.setWeekProfit(entity.getWeekProfit());
		this.setMonthProfit(entity.getMonthProfit());
		if(entity.getBazooAgentDisplay() != null){
			this.setBazooAgentDisplay(entity.getBazooAgentDisplay());
		}else{
			this.setBazooAgentDisplay(0);
		}
		
		if(entity.getBazooRobotDisplay() != null){
			this.setBazooRobotDisplay(entity.getBazooRobotDisplay());
		}else{
			this.setBazooRobotDisplay(0);
		}
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
