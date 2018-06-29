package com.gameserver.function;

import java.util.Date;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.FunctionEntity;
import com.gameserver.common.Globals;

public class Function implements PersistanceObject<Long, FunctionEntity>{

	
	/** 生命期 */
	private final LifeCycle lifeCycle;
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	
	private Long id;
	private int functionType;
	private String title;
	private String descrip;
	private String pic;
	private Date startTime;
	private Date endTime;
	private String conditions;
	private Date createTime;
	
	
	
	public Function(){
		this.lifeCycle = new LifeCycleImpl(this);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public int getFunctionType() {
		return functionType;
	}

	public void setFunctionType(int functionType) {
		this.functionType = functionType;
	}

	/**
	 * 激活此2关系
	 */
	public void active(){
		getLifeCycle().activate();
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
		return "activity#"+this.id;
	}

	@Override
	public boolean isInDb() {
		return this.inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb =inDb;
		
	}

	@Override
	public long getCharId() {
		return 0;
	}

	@Override
	public FunctionEntity toEntity() {
		FunctionEntity entity = new FunctionEntity();
		entity.setId(id);
		entity.setFunctionType(functionType);
		entity.setTitle(title);
		entity.setDescrip(descrip);
		entity.setPic(pic);
		entity.setStartTime(startTime);
		entity.setEndTime(endTime);
		entity.setConditions(conditions);
		entity.setCreateTime(createTime);
		return entity;
	}

	@Override
	public void fromEntity(FunctionEntity entity) {
		this.setId(entity.getId());
		this.setFunctionType(entity.getFunctionType());
		this.setTitle(entity.getTitle());
		this.setDescrip(entity.getDescrip());
		this.setPic(entity.getPic());
		this.setStartTime(entity.getStartTime());
		this.setEndTime(entity.getEndTime());
		this.setConditions(entity.getConditions());
		this.setCreateTime(entity.getCreateTime());
		this.setInDb(true);
		this.active();
		
	}

	@Override
	public LifeCycle getLifeCycle() {
		return lifeCycle;
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
			Globals.getGlobalLogicRunner().getGlobalDataUpdater().addUpdate(lifeCycle);
		}
		
	}
	
	public void onDelete()
	{
		this.lifeCycle.destroy();
		Globals.getGlobalLogicRunner().getGlobalDataUpdater().addDelete(this.getLifeCycle());
	
	}
}
