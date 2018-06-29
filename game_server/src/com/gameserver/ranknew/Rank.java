package com.gameserver.ranknew;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.RankEntity;
import com.gameserver.common.Globals;
/**
 * 
 * @author 郭君伟
 *
 */

public class Rank implements PersistanceObject<Long,RankEntity>{
	
	
	/** 生命期 */
	private final LifeCycle lifeCycle;
	
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	/**主键 **/
	private long id;
	/** 角色ID **/
	private long charId;
	/**今日单居最大赢取彩金 **/
	private long daySingleJackpot;
	/**今日总的累计彩金 **/
	private long dayTotalJackpot;
	/**注册到现在最大的彩金 **/
	private long singleJackpot;
	/**注册到现在累计彩金 **/
	private long totalJackpot;
	/**vip等级 **/
	private int viplevel;
	
	private long updateTime;
	  
	private long createTime;
	
	private String scoreList;
	
	public Rank(){
		this.lifeCycle = new LifeCycleImpl(this);
	}

	@Override
	public void setDbId(Long id) {
		this.id = id;
	}

	@Override
	public Long getDbId() {
		return id;
	}

	@Override
	public String getGUID() {
		return "RankNew#"+this.id;
	}

	@Override
	public boolean isInDb() {
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}

	

	public long getDaySingleJackpot() {
		return daySingleJackpot;
	}

	public void setDaySingleJackpot(long daySingleJackpot) {
		this.daySingleJackpot = daySingleJackpot;
	}

	public long getDayTotalJackpot() {
		return dayTotalJackpot;
	}

	public void setDayTotalJackpot(long dayTotalJackpot) {
		this.dayTotalJackpot = dayTotalJackpot;
	}

	public long getSingleJackpot() {
		return singleJackpot;
	}

	public void setSingleJackpot(long singleJackpot) {
		this.singleJackpot = singleJackpot;
	}

	public long getTotalJackpot() {
		return totalJackpot;
	}

	public void setTotalJackpot(long totalJackpot) {
		this.totalJackpot = totalJackpot;
	}

	public int getViplevel() {
		return viplevel;
	}

	public void setViplevel(int viplevel) {
		this.viplevel = viplevel;
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

	public void setCharId(long charId) {
		this.charId = charId;
	}

	@Override
	public RankEntity toEntity() {
		RankEntity rankEntity = new RankEntity();
		rankEntity.setId(id);
		rankEntity.setCharId(charId);
		rankEntity.setDaySingleJackpot(daySingleJackpot);
		rankEntity.setDayTotalJackpot(dayTotalJackpot);
		rankEntity.setSingleJackpot(singleJackpot);
		rankEntity.setTotalJackpot(totalJackpot);
		rankEntity.setViplevel(viplevel);
		rankEntity.setUpdateTime(updateTime);
		rankEntity.setCreateTime(createTime);
		rankEntity.setScoreList(scoreList);
		return rankEntity;
	}

	@Override
	public void fromEntity(RankEntity entity) {
		this.id = entity.getId();
		this.charId = entity.getCharId();
		this.daySingleJackpot = entity.getDaySingleJackpot();
		this.dayTotalJackpot = entity.getDayTotalJackpot();
		this.singleJackpot = entity.getSingleJackpot();
		this.totalJackpot = entity.getTotalJackpot();
		this.viplevel = entity.getViplevel();
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.scoreList = entity.getScoreList();
		this.setInDb(true);
		this.active();
	}
	
    /**
	 * 激活此关系
	 */
	public void active() 
	{
		getLifeCycle().activate();
	}


	@Override
	public void setModified() {
		onUpdate();
	}
	

	/**
	 * 关系被更新回调处理
	 */
	public void onUpdate()
	{

		// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive())
		{
			//全球管理器的更新，不属于个人操作
			this.updateTime = Globals.getTimeService().now();
			Globals.getGlobalLogicRunner().getGlobalDataUpdater().addUpdate(lifeCycle);
		}
		
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	@Override
	public long getCharId() {
		return charId;
	}

	public String getScoreList() {
		return scoreList;
	}

	public void setScoreList(String scoreList) {
		this.scoreList = scoreList;
	}

	

}
