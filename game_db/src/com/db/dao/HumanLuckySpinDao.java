package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanLuckySpinEntity;

public class HumanLuckySpinDao extends BaseDao<HumanLuckySpinEntity>{
	
	private static final String GET_LUCKY_SPIN_BY_CHARID = "queryLuckySpinByCharid";
	private static final String[] GET_LUCKY_SPIN_BY_CHARID_PARAMS=new String[] { "charId" };
	
	public HumanLuckySpinDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<HumanLuckySpinEntity> getEntityClazz() {
		// TODO Auto-generated method stub
		return HumanLuckySpinEntity.class;
	}
	

	
	
	public HumanLuckySpinEntity getLuckySpinByCharId(long charId)
	{
		List<HumanLuckySpinEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_LUCKY_SPIN_BY_CHARID,GET_LUCKY_SPIN_BY_CHARID_PARAMS,new Object[]{charId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	


}
