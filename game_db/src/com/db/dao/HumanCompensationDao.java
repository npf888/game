package com.db.dao;

import java.util.List;

import com.core.orm.DBService;

import com.db.model.HumanCompensationEntity;

/**
 * 用户补偿数据
 * @author wayne
 *
 */
public class HumanCompensationDao  extends BaseDao<HumanCompensationEntity>{

	private static final String GET_COMPENSATIONS_BY_CHARID = "queryCompensationsByCharid";
	private static final String[] GET_COMPENSATIONS_BY_CHARID_PARAMS = new String[] { "charId" };
	
	public HumanCompensationDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 更具用户ID和邮件类别从数据库中读取用户补偿
	 * @param UUID
	 * @return
	 */
	
	public List<HumanCompensationEntity> getAllHumanCompensations(long charId)
	{
		
		List<HumanCompensationEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_COMPENSATIONS_BY_CHARID,GET_COMPENSATIONS_BY_CHARID_PARAMS,new Object[]{charId});
		return entities;
	}
	
	
	@Override
	protected Class<HumanCompensationEntity> getEntityClazz() 
	{
		return HumanCompensationEntity.class;
	}

}
