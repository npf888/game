package com.robot.slot.slotname;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.SlotListEntity;
import com.gameserver.human.Human;

/**
 * 每个老虎机的基本信息
 * @author JavaServer
 *
 */
public class SlotList implements PersistanceObject<Long, SlotListEntity>{
	private Human owner;
	private long chartId;
	private long id;
	private String langDesc;
	private int payLinesNum;
	private int bet1;
	private int bet2;
	private int bet3;
	private int bet4;
	private int bet5;
	private int type;
	private int bet1Lv;
	private int bet2Lv;
	private int bet3Lv;
	private int bet4Lv;
	private int bet5Lv;
	private boolean inDb;
	private final LifeCycle lifeCycle;
	
	public SlotList(){
		this.lifeCycle = new LifeCycleImpl(this);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLangDesc() {
		return langDesc;
	}
	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}
	public int getPayLinesNum() {
		return payLinesNum;
	}
	public void setPayLinesNum(int payLinesNum) {
		this.payLinesNum = payLinesNum;
	}
	public int getBet1() {
		return bet1;
	}
	public void setBet1(int bet1) {
		this.bet1 = bet1;
	}
	public int getBet2() {
		return bet2;
	}
	public void setBet2(int bet2) {
		this.bet2 = bet2;
	}
	public int getBet3() {
		return bet3;
	}
	public void setBet3(int bet3) {
		this.bet3 = bet3;
	}
	public int getBet4() {
		return bet4;
	}
	public void setBet4(int bet4) {
		this.bet4 = bet4;
	}
	public int getBet5() {
		return bet5;
	}
	public void setBet5(int bet5) {
		this.bet5 = bet5;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getBet1Lv() {
		return bet1Lv;
	}
	public void setBet1Lv(int bet1Lv) {
		this.bet1Lv = bet1Lv;
	}
	public int getBet2Lv() {
		return bet2Lv;
	}
	public void setBet2Lv(int bet2Lv) {
		this.bet2Lv = bet2Lv;
	}
	public int getBet3Lv() {
		return bet3Lv;
	}
	public void setBet3Lv(int bet3Lv) {
		this.bet3Lv = bet3Lv;
	}
	public int getBet4Lv() {
		return bet4Lv;
	}
	public void setBet4Lv(int bet4Lv) {
		this.bet4Lv = bet4Lv;
	}
	public int getBet5Lv() {
		return bet5Lv;
	}
	public void setBet5Lv(int bet5Lv) {
		this.bet5Lv = bet5Lv;
	}
	@Override
	public void setDbId(Long id) {
		this.id=id;
	}
	@Override
	public Long getDbId() {
		return id;
	}
	@Override
	public String getGUID() {
		return "slotList#"+this.id;
	}
	@Override
	public boolean isInDb() {
		return inDb;
	}
	@Override
	public void setInDb(boolean inDb) {
		this.inDb=inDb;
		
	}
	@Override
	public SlotListEntity toEntity() {
		SlotListEntity entity = new SlotListEntity();
		entity.setId(getId());
		entity.setLangDesc(getLangDesc());
		entity.setPayLinesNum(getPayLinesNum());
		entity.setBet1(getBet1());
		entity.setBet2(getBet2());
		entity.setBet3(getBet3());
		entity.setBet4(getBet4());
		entity.setBet5(getBet5());
		entity.setType(getType());
		entity.setBet1Lv(getBet1Lv());
		entity.setBet2Lv(getBet2Lv());
		entity.setBet3Lv(getBet3Lv());
		entity.setBet4Lv(getBet4Lv());
		entity.setBet5Lv(getBet5Lv());
		return entity;
	}
	@Override
	public void fromEntity(SlotListEntity entity) {
		this.id = entity.getId();
		this.langDesc = entity.getLangDesc();
		this.payLinesNum = entity.getPayLinesNum();
		this.bet1 = entity.getBet1();
		this.bet2 = entity.getBet2();
		this.bet3 = entity.getBet3();
		this.bet4 = entity.getBet4();
		this.bet5=entity.getBet5();
		this.type = entity.getType();
		this.bet1Lv =entity.getBet1Lv();
		this.bet2Lv =entity.getBet2Lv();
		this.bet3Lv=entity.getBet3Lv();
		this.bet4Lv=entity.getBet4Lv();
		this.bet5Lv=entity.getBet5Lv();
		this.setInDb(true);
		this.active();
		
	}
	public void active(){
		getLifeCycle().activate();
	}
	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}
	@Override
	public void setModified() {
		onUpdate();
		
	}
	@Override
	public long getCharId() {
		return chartId;
	}
	private void onUpdate()
	{
		if (owner != null) 
		{
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
	public Human getOwner() {
		return owner;
	}
	public void setOwner(Human owner) {
		this.owner = owner;
	}
	public long getChartId() {
		return chartId;
	}
	public void setChartId(long chartId) {
		this.chartId = chartId;
	}

}
