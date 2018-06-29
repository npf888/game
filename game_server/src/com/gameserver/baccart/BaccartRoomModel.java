package com.gameserver.baccart;





import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.BaccartRoomModelEntity;
import com.gameserver.common.Globals;


public class BaccartRoomModel implements PersistanceObject<Long, BaccartRoomModelEntity>{

	/** 生命期 */
	private final LifeCycle lifeCycle;
	private long id;
	private int roomId;
	private long stock;
	private long jackpot;
	private int closed;
	private long updateTime;
	private long createTime;
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	public BaccartRoomModel(){
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
		return "baccartroommodel#"+this.id;
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


	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

	public long getJackpot() {
		return jackpot;
	}

	public void setJackpot(long jackpot) {
		this.jackpot = jackpot;
	}

	/**
	 * @return the closed
	 */
	public int getClosed() {
		return closed;
	}

	/**
	 * @param closed the closed to set
	 */
	public void setClosed(int closed) {
		this.closed = closed;
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
	public BaccartRoomModelEntity toEntity() {
		// TODO Auto-generated method stub
		BaccartRoomModelEntity baccartRoomModelEntity = new BaccartRoomModelEntity();
		baccartRoomModelEntity.setId(this.getDbId());
		baccartRoomModelEntity.setJackpot(this.getJackpot());
		baccartRoomModelEntity.setStock(this.getStock());
		baccartRoomModelEntity.setClosed(this.getClosed());
		baccartRoomModelEntity.setUpdateTime(this.getUpdateTime());
		baccartRoomModelEntity.setCreateTime(this.getCreateTime());
		baccartRoomModelEntity.setRoomId(this.getRoomId());
		return baccartRoomModelEntity;
	}

	@Override
	public void fromEntity(BaccartRoomModelEntity entity) {
		// TODO Auto-generated method stub
		this.setCreateTime(entity.getCreateTime());
		this.setDbId(entity.getId());
		this.setRoomId(entity.getRoomId());
		this.setJackpot(entity.getJackpot());
		this.setStock(entity.getStock());
		this.setClosed(entity.getClosed());
		this.setUpdateTime(entity.getUpdateTime());
		this.setInDb(true);
		this.active();
	}
	
	
	

}
