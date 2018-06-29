package com.gameserver.texas;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.common.model.Card;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanTexasEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;

/**
 * 德州
 * @author Thinker
 *
 */
public class HumanTexas implements PersistanceObject<Long, HumanTexasEntity>
{
	private Human owner;
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	private int isAuto;
	private int people;
	private int count;
	private int winCount;
	private List<Card> biggestHandCardList = new ArrayList<Card>();
	private int weekWinCoins;
	private int dayBiggestWinCoins;
	private long updateTime;
	private long createTime;
	private boolean inDb;
	
	public HumanTexas()
	{
		this.lifeCycle = new LifeCycleImpl(this);
	}


	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}
	
	@Override
	public LifeCycle getLifeCycle() {
	
		return lifeCycle;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCharId(long charId) {
		this.charId = charId;
	}





	public int getIsAuto() {
		return isAuto;
	}


	public void setIsAuto(int isAuto) {
		this.isAuto = isAuto;
	}


	public int getPeople() {
		return people;
	}


	public void setPeople(int people) {
		this.people = people;
	}


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


	public List<Card> getBiggestHandCardList() {
		return biggestHandCardList;
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


	public long getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}


	public long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}


	@Override
	public void setDbId(Long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public Long getDbId() {
	
		return this.id;
	}

	@Override
	public String getGUID() {

		return "texas#"+this.id;
	}

	@Override
	public boolean isInDb() {
	
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		
		this.inDb = inDb;
	}


	public long getCharId() {
	
		return charId;
	}

	@Override
	public HumanTexasEntity toEntity()
	{
		HumanTexasEntity entity = new HumanTexasEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setIsAuto(getIsAuto());
		entity.setPeople(getPeople());
		List<Integer> cardIntList = new ArrayList<Integer>();
		for(Card card:this.getBiggestHandCardList()){
			cardIntList.add(card.getValue());
		}
		entity.setBiggestHandCard(JSON.toJSONString(cardIntList));
		entity.setWeekWinCoins(getWeekWinCoins());
		entity.setDayBiggestWinCoins(getDayBiggestWinCoins());
		entity.setCount(getCount());
		entity.setWinCount(getWinCount());
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		
		return entity;
	}

	@Override
	public void fromEntity(HumanTexasEntity entity)
	{
		this.id = entity.getId();
		this.charId = entity.getCharId();
		this.isAuto=entity.getIsAuto();
		this.people= entity.getPeople();
		this.weekWinCoins = entity.getWeekWinCoins();
		this.dayBiggestWinCoins = entity.getDayBiggestWinCoins();
		this.count = entity.getCount();
		this.winCount = entity.getWinCount();
		JSONArray arr =JSON.parseArray(entity.getBiggestHandCard());
		if(arr!= null)
		{
			for(Object obj:arr)
			{
				this.getBiggestHandCardList().add(new Card((Integer) obj));
			}
		}
	
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.setInDb(true);
		this.active();
	}

	@Override
	public void setModified()
	{
		onUpdate();
	}
	
	/**
	 * 激活此2关系
	 */
	public void active(){
		getLifeCycle().activate();
	}
	
	private void onUpdate()
	{
		if (owner != null) 
		{
			// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
			
			this.lifeCycle.checkModifiable();
			if (this.lifeCycle.isActive())
			{
				// 物品的生命期处于活动状态,并且该宠物不是空的,则执行通知更新机制进行
				long now = Globals.getTimeService().now();
				this.updateTime = now ;
				owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
			}
		}
	}
}
