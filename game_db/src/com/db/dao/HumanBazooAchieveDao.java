package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanBazooAchieveEntity;

public class HumanBazooAchieveDao extends BaseDao<HumanBazooAchieveEntity>{
	private static final String GET_HUMAN_BAZOO_ACHIEVE = "queryHumanBazooAchieveByPassportId";
	private static final String[] GET_HUMAN_BAZOO_ACHIEVE_PARAMS = new String[] { "passportId" };
	

	public HumanBazooAchieveDao(DBService dbServ) {
		super(dbServ);
	}
	
	/**
	 * 根据用户ID查询 用户
	 * @param UUID
	 * @return
	 */
	
	public HumanBazooAchieveEntity getHumanBazooAchieveByPassportId(long passportId)
	{
		
		List<HumanBazooAchieveEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_HUMAN_BAZOO_ACHIEVE,GET_HUMAN_BAZOO_ACHIEVE_PARAMS,new Object[]{passportId});
		if(entities == null || entities.size() == 0){
			return null;
		}
		return entities.get(0);
	}
	
	
	@Override
	protected Class<HumanBazooAchieveEntity> getEntityClazz() 
	{
		return HumanBazooAchieveEntity.class;
	}
}

