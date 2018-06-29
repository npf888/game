package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.ActivityEntity;

/**
 * 活动dao
 * @author wayne
 *
 */
public class ActivityDao extends BaseDao<ActivityEntity>{
	
	
	private static final String GET_ACTIVITIES = "queryActivities";

	public ActivityDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}


	/**
	 * 更具用户ID和邮件类别从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */
	
	public List<ActivityEntity> getAllActivities()
	{
		return this._dbServ.findByNamedQuery(GET_ACTIVITIES);
	}
	
	
	@Override
	protected Class<ActivityEntity> getEntityClazz() 
	{
		return ActivityEntity.class;
	}

}
