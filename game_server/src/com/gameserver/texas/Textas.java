package com.gameserver.texas;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.TextasEntity;
import com.gameserver.common.Globals;

/**
 * 
 * @author 郭君伟
 *
 */
public class Textas implements PersistanceObject<Long, TextasEntity> {
	
	
	private final LifeCycle lifeCycle;
	
	 /**主键 **/
	 private long id;
	/** 德州房间ID**/
	 private int texasId;
	 /**彩金池 **/
	 private long jackpot;
	 /**累计彩金池 **/
	 private long cumuJackpot;
	 /** 更新时间**/
	 private long updateTime;
	 /**创建时间 **/
	 private long createTime;
	 
	 private boolean inDb;
	 
	 
	 public Textas() {
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
		return "Textas#"+this.id;
	}

	@Override
	public boolean isInDb() {
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb = inDb;
	}



	public int getTexasId() {
		return texasId;
	}

	public void setTexasId(int texasId) {
		this.texasId = texasId;
	}

	public long getJackpot() {
		return jackpot;
	}

	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}

	public long getCumuJackpot() {
		return cumuJackpot;
	}

	public void setCumuJackpot(long cumuJackpot) {
		this.cumuJackpot = cumuJackpot;
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
	public TextasEntity toEntity() {
		TextasEntity entity = new TextasEntity();
		entity.setId(id);
		entity.setJackpot(jackpot);
		entity.setTexasId(texasId);
		entity.setCumuJackpot(cumuJackpot);
		entity.setUpdateTime(updateTime);
		entity.setCreateTime(createTime);
		return entity;
	}

	@Override
	public void fromEntity(TextasEntity entity) {
		this.id = entity.getId();
		this.texasId = entity.getTexasId();
		this.jackpot = entity.getJackpot();
		this.cumuJackpot = entity.getCumuJackpot();
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.setInDb(true);
		this.active();
	}

	/**
	 * 激活此2关系
	 */
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
	
	private void onUpdate()
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
	public long getCharId() {
		return 0;
	}

}
