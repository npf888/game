package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanBazooTaskEntity;

public class HumanBazooTaskDao extends BaseDao<HumanBazooTaskEntity>{
	private static final String GET_HUMAN_BAZOO_TASK = "queryHumanBazooTaskByPassportId";
	private static final String[] GET_HUMAN_BAZOO_TASK_PARAMS = new String[] { "passportId" };
	

	public HumanBazooTaskDao(DBService dbServ) {
		super(dbServ);
	}
	
	/**
	 * 根据用户ID查询 用户
	 * @param UUID
	 * @return
	 */
	
	public HumanBazooTaskEntity getHumanBazooTaskByPassportId(long passportId)
	{
		
		List<HumanBazooTaskEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_HUMAN_BAZOO_TASK,GET_HUMAN_BAZOO_TASK_PARAMS,new Object[]{passportId});
		if(entities == null || entities.size() == 0){
			return null;
		}
		return entities.get(0);
	}
	
	
	@Override
	protected Class<HumanBazooTaskEntity> getEntityClazz() 
	{
		return HumanBazooTaskEntity.class;
	}
}

