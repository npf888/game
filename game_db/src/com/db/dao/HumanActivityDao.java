package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanActivityEntity;


/**
 * 活动dao
 * @author wayne
 *
 */
public class HumanActivityDao extends BaseDao<HumanActivityEntity>{
	
	private static final String GET_ACTIVITY_BY_CHARID = "queryActivityByCharid";
	private static final String[] GET_ACTIVITY_BY_CHARID_PARAMS = new String[] { "charId" };
	

	public HumanActivityDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}



	/**
	 * 更具用户ID和邮件类别从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */
	
	public List<HumanActivityEntity> getAllHumanActivities(long charId)
	{
		
		List<HumanActivityEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_ACTIVITY_BY_CHARID,GET_ACTIVITY_BY_CHARID_PARAMS,new Object[]{charId});
		return entities;
	}
	
	
	@Override
	protected Class<HumanActivityEntity> getEntityClazz() 
	{
		return HumanActivityEntity.class;
	}
}

