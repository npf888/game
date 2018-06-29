package com.gameserver.conversioncode;

import com.core.object.LifeCycle;
import com.core.object.LifeCycleImpl;
import com.core.object.PersistanceObject;
import com.db.model.Conversioncode;
import com.gameserver.common.Globals;

/**
 * 随机码实体
 * @author 郭君伟
 *
 */
public class ConversioncodeData implements PersistanceObject<Long, Conversioncode> {
	
	
	/** 生命期 */
	private final LifeCycle lifeCycle;
	
	private long id;
	 
	private String conversionCode;
	
	private long gold;
	/**结束时间 **/
	private long endTime;
	/**是否有效 1 表示无效的 **/
	private int isdelete;
	
	/**0：通用型-每人可用一次；1：特殊型-只能给一个人用 **/
	private int codeType;
	
	private long updateTime;
	
	private long createTime;
	
	/** 是否已经在数据库中 */
	private boolean inDb;
	
	public ConversioncodeData(){
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
		return "ConversioncodeData#"+this.id;
	}

	@Override
	public boolean isInDb() {
		return inDb;
	}

	@Override
	public void setInDb(boolean inDb) {
		this.inDb =inDb;
	}

	@Override
	public long getCharId() {
		return 0;
	}

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConversionCode() {
		return conversionCode;
	}

	public void setConversionCode(String conversionCode) {
		this.conversionCode = conversionCode;
	}

	public long getGold() {
		return gold;
	}

	public void setGold(long gold) {
		this.gold = gold;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
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

	

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	@Override
	public Conversioncode toEntity() {
		Conversioncode code = new Conversioncode();
		code.setId(id);
		code.setConversionCode(conversionCode);
		code.setGold(gold);
		code.setEndTime(endTime);
		code.setIsdelete(isdelete);
		code.setUpdateTime(updateTime);
		code.setCreateTime(createTime);
		code.setCodeType(codeType);
		return code;
	}

	@Override
	public void fromEntity(Conversioncode entity) {
		this.setDbId(entity.getId());
		this.setConversionCode(entity.getConversionCode());
		this.setGold(entity.getGold());
		this.setEndTime(entity.getEndTime());
		this.setIsdelete(entity.getIsdelete());
		this.setUpdateTime(entity.getUpdateTime());
		this.setCreateTime(entity.getCreateTime());
		this.setCodeType(entity.getCodeType());
		this.setInDb(true);
		this.active();
	}

	/**
	 * 激活此关系
	 */
	public void active(){
		getLifeCycle().activate();
	}


	@Override
	public void setModified() {
		onUpdate();
	}
	
	
	public LifeCycle getLifeCycle() {
		return lifeCycle;
	}

	/**
	 * 关系被更新回调处理
	 */
	public void onUpdate(){

		// TODO 为了防止发生一些错误的使用状况,暂时把此处的检查限制得很严格
		this.lifeCycle.checkModifiable();
		if (this.lifeCycle.isActive())
		{
			//全球管理器的更新，不属于个人操作
			this.updateTime = Globals.getTimeService().now();
			Globals.getGlobalLogicRunner().getGlobalDataUpdater().addUpdate(lifeCycle);
		}
		
	}

}
