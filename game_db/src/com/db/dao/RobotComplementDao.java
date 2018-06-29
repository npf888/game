package com.db.dao;


import com.core.orm.DBService;
import com.db.model.RobotComplementEntity;

/**
 * 机器人
 * @author wayne
 *
 */
public class RobotComplementDao  extends BaseDao<RobotComplementEntity>{

	
	public RobotComplementDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<RobotComplementEntity> getEntityClazz() 
	{
		return RobotComplementEntity.class;
	}
}
