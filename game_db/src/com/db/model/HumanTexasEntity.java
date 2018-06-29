package com.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.core.orm.BaseEntity;

/**
 * 数据库实体类：德州信息，暂时先放在这儿
 * @author Thinker
 */
@Entity
@Table(name = "t_human_texas")
public class HumanTexasEntity implements BaseEntity<Long>
{

	
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private long id;
	/** 玩家角色ID */
	private long charId;
	/**是否自动补充*/
	private int isAuto;
	/**人数*/
	private int people;
	/** 玩的局数  */
	private int count;
	/**胜利局数*/
	private int winCount;
	/** 最大牌型 */
	private String biggestHandCard;
	/** 周盈利 */
	private int weekWinCoins;
	/** 单日最高盈利 */
	private int dayBiggestWinCoins;
	/** 更新时间 */
	private long updateTime;
	/** 创建时间 */
	private long createTime;



	public HumanTexasEntity()
	{
		
	}

	@Id
	@Column
	@Override
	public Long getId()
	{
		return id;
	}

	@Override
	public void setId(Long id)
	{
		this.id = id;
	}

	@Column
	public long getCharId() {
		return charId;
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

	@Column
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Column
	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	@Column
	public String getBiggestHandCard() {
		return biggestHandCard;
	}

	public void setBiggestHandCard(String biggestHandCard) {
		this.biggestHandCard = biggestHandCard;
	}

	@Column
	public int getWeekWinCoins() {
		return weekWinCoins;
	}

	public void setWeekWinCoins(int weekWinCoins) {
		this.weekWinCoins = weekWinCoins;
	}

	@Column
	public int getDayBiggestWinCoins() {
		return dayBiggestWinCoins;
	}

	public void setDayBiggestWinCoins(int dayBiggestWinCoins) {
		this.dayBiggestWinCoins = dayBiggestWinCoins;
	}

	@Column
	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	@Column
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	

}
