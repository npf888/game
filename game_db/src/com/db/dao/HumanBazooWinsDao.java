package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanBazooWinsEntity;

public class HumanBazooWinsDao extends BaseDao<HumanBazooWinsEntity>{
	private static final String GET_HUMAN_BAZOO_WINS = "queryHumanBazooWinsByPassportId";
	private static final String[] GET_HUMAN_BAZOO_WINS_PARAMS = new String[] { "passportId" };
	

	public HumanBazooWinsDao(DBService dbServ) {
		super(dbServ);
	}
	
	/**
	 * 根据用户ID查询 用户
	 * @param UUID
	 * @return
	 */
	
	public List<HumanBazooWinsEntity> getHumanBazooWinsByPassportId(long passportId)
	{
		
		List<HumanBazooWinsEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_HUMAN_BAZOO_WINS,GET_HUMAN_BAZOO_WINS_PARAMS,new Object[]{passportId});
		return entities;
	}
	
	
	@Override
	protected Class<HumanBazooWinsEntity> getEntityClazz() 
	{
		return HumanBazooWinsEntity.class;
	}
}


