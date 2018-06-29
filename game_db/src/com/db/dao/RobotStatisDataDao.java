package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.RobotStatisDataEntity;

public class RobotStatisDataDao extends BaseDao<RobotStatisDataEntity>{
	
	public RobotStatisDataDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<RobotStatisDataEntity> getEntityClazz() {
		return RobotStatisDataEntity.class;
	}
	
	/**
	 * 操作
	 */
	private static final String GET_ACTIVITIES = "queryActivities";
	
	public List<RobotStatisDataEntity> getAllActivities()
	{
		return this._dbServ.findByNamedQuery(GET_ACTIVITIES);
	}
	
	public void saveEntity(RobotStatisDataEntity entity){
		this._dbServ.save(entity);
	}
}
