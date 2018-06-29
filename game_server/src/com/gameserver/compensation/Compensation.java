package com.gameserver.compensation;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.CompensationEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;

/**
 * 补偿
 * @author wayne
 *
 */
public class Compensation implements PersistanceObject<Long, CompensationEntity>{

	/** 生命期 */
	private final LifeCycle lifeCycle;
	private long id;

	private String title;
	private String content;
	private List<RandRewardData> itemList = new ArrayList<RandRewardData>();
	private long updateTime;
	private long createTime;
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	public Compensation(){
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	@Override
	public LifeCycle getLifeCycle() {
		// TODO Auto-generated method stub
		return lifeCycle;
	}

	
	@Override
	public void setDbId(Long id) {
		// TODO Auto-generated method stub
		this.id =id;
	}

	@Override
	public Long getDbId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return "compensation#"+this.id;
	}

	@Override
	public boolean isInDb() {
		// TODO Auto-generated method stub
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		// TODO Auto-generated method stub
		this.inDb = inDb;
	}

	@Override
	public long getCharId() {
		// TODO Auto-generated method stub
		return 0;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<RandRewardData> getItemList() {
		return itemList;
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


	
	/**
	 * 激活此关系
	 */
	public void active() 
	{
		getLifeCycle().activate();
	}


	@Override
	public void setModified() {
		// TODO Auto-generated method stub
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
	public CompensationEntity toEntity() {
		// TODO Auto-generated method stub
		CompensationEntity compensationEntity = new CompensationEntity();
		compensationEntity.setContent(this.getContent());
		compensationEntity.setId(this.getDbId());
		compensationEntity.setItemPack(JSON.toJSONString(this.getItemList()));
		compensationEntity.setUpdateTime(this.getUpdateTime());
		compensationEntity.setTitle(this.getTitle());
		compensationEntity.setCreateTime(this.getCreateTime());
		return compensationEntity;
	}

	@Override
	public void fromEntity(CompensationEntity entity) {
		// TODO Auto-generated method stub
		this.setDbId(entity.getId());
		this.setTitle(entity.getTitle());
		this.setContent(entity.getContent());
		this.getItemList().addAll(JSON.parseArray(entity.getItemPack(), RandRewardData.class));
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
		this.setInDb(true);
		this.active();
	}

}
