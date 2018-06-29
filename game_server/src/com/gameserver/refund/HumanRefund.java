package com.gameserver.refund;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.core.util.Assert;
import com.db.model.HumanRefundEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.refund.enums.RefundStatusEnum;

public class HumanRefund implements PersistanceObject<Long, HumanRefundEntity>{

	/** 生命期 */
	private final LifeCycle lifeCycle;
	private long id;
	private long charId;
	private Human owner;
	private String title;
	private String content;
	private RefundStatusEnum refundStatus;
	private List<RandRewardData> itemList = new ArrayList<RandRewardData>();

	private long updateTime;
	private long createTime;
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	public HumanRefund(){
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	public Human getOwner() {
		Assert.notNull(owner);
		return owner;
	}
	
	public void setOwner(Human owner) {
		this.owner = owner;
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

	public RefundStatusEnum getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(RefundStatusEnum refundStatus) {
		this.refundStatus = refundStatus;
	}

	public List<RandRewardData> getItemList() {
		return itemList;
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
		return "huamnrefund#"+this.id;
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
		return this.charId;
	}

	
	public void setCharId(long charId) {
		this.charId = charId;
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
			owner.getPlayer().getDataUpdater().addUpdate(lifeCycle);
		}
		
	}

	@Override
	public HumanRefundEntity toEntity() {
		// TODO Auto-generated method stub
		HumanRefundEntity entity = new HumanRefundEntity();
		entity.setId(getDbId());
		entity.setCharId(getCharId());
		entity.setTitle(getTitle());
		entity.setContent(getContent());
		entity.setItemPack(JSON.toJSONString(this.getItemList()));
		entity.setRefundStatus(getRefundStatus().getIndex());
		entity.setCreateTime(getCreateTime());
		entity.setUpdateTime(getUpdateTime());
		return entity;
	}

	@Override
	public void fromEntity(HumanRefundEntity entity) {
		// TODO Auto-generated method stub
		this.id = entity.getId();
		this.setCharId(entity.getCharId());
		this.setTitle(entity.getTitle());
		this.setContent(entity.getContent());
		this.getItemList().addAll(JSON.parseArray(entity.getItemPack(), RandRewardData.class));
		this.setRefundStatus(RefundStatusEnum.valueOf(entity.getRefundStatus()));
		this.updateTime = entity.getUpdateTime();
		this.createTime = entity.getCreateTime();
		this.setInDb(true);
		this.active();
	}

	


}
