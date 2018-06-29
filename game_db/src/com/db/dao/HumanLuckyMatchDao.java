package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanLuckyMatchEntity;


public class HumanLuckyMatchDao  extends BaseDao<HumanLuckyMatchEntity>{
	
	private static final String GET_LUCKY_MATCH_BY_CHARID = "queryLuckyMatchByCharid";
	private static final String[] GET_LUCKY_MATCH_BY_CHARID_PARAMS=new String[] { "charId" };
	
	public HumanLuckyMatchDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<HumanLuckyMatchEntity> getEntityClazz() {
		// TODO Auto-generated method stub
		return HumanLuckyMatchEntity.class;
	}
	

	
	
	public HumanLuckyMatchEntity getLuckyMatchByCharId(long charId)
	{
		List<HumanLuckyMatchEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_LUCKY_MATCH_BY_CHARID,GET_LUCKY_MATCH_BY_CHARID_PARAMS,new Object[]{charId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	


}
