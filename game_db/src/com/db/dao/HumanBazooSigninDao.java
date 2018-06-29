package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanBazooSigninEntity;

public class HumanBazooSigninDao extends BaseDao<HumanBazooSigninEntity>{
	private static final String GET_HUMAN_BAZOO_SIGNIN = "queryHumanBazooSigninByPassportId";
	private static final String[] GET_HUMAN_BAZOO_SIGNIN_PARAMS = new String[] { "passportId" };
	

	public HumanBazooSigninDao(DBService dbServ) {
		super(dbServ);
	}
	
	/**
	 * 根据用户ID查询 用户
	 * @param UUID
	 * @return
	 */
	
	public HumanBazooSigninEntity getHumanBazooSigninByPassportId(long passportId)
	{
		
		List<HumanBazooSigninEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_HUMAN_BAZOO_SIGNIN,GET_HUMAN_BAZOO_SIGNIN_PARAMS,new Object[]{passportId});
		if(entities == null || entities.size()==0)
			return null;
		return entities.get(0);
	}
	
	
	@Override
	protected Class<HumanBazooSigninEntity> getEntityClazz() 
	{
		return HumanBazooSigninEntity.class;
	}
}
