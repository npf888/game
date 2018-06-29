package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanItemEntity;

/**
 * 道具dao
 * @author wayne
 *
 */
public class HumanItemDao extends BaseDao<HumanItemEntity>{

	private static final String GET_ITEMS_BY_CHARID = "queryItemsByCharid";
	private static final String[] GET_ITEMS_BY_CHARID_PARAMS=new String[] { "charId" };
	
	public HumanItemDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	public List<HumanItemEntity> getItemsByCharId(long charId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_ITEMS_BY_CHARID,GET_ITEMS_BY_CHARID_PARAMS,new Object[]{charId});
	
	}
	
	
	@Override
	protected Class<HumanItemEntity> getEntityClazz() 
	{
		return HumanItemEntity.class;
	}
}

