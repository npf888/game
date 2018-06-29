package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanVipEntity;


/**
 * vip dao
 * @author wayne
 *
 */
public class HumanVipDao extends  BaseDao<HumanVipEntity>{

	public HumanVipDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	private static final String GET_VIP_BY_CHARID = "queryVipByCharid";
	private static final String[] GET_VIP_BY_CHARID_PARAMS=new String[] { "charId" };
	

	public HumanVipEntity getVipByCharId(long charId)
	{
		List<HumanVipEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_VIP_BY_CHARID,GET_VIP_BY_CHARID_PARAMS,new Object[]{charId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	
	
	@Override
	protected Class<HumanVipEntity> getEntityClazz() 
	{
		return HumanVipEntity.class;
	}
}

