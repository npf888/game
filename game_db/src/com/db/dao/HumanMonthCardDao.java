package com.db.dao;

import java.util.List;

import com.core.orm.DBService;

import com.db.model.HumanMonthCardEntity;


/**
 * 月卡dao
 * @author wayne
 *
 */
public class HumanMonthCardDao extends BaseDao<HumanMonthCardEntity>{
	
	

	private static final String GET_MONTH_CARD_BY_CHARID = "queryMonthCardByCharid";
	private static final String[] GET_MONTH_CARD_BY_CHARID_PARAMS=new String[] { "charId" };
	public HumanMonthCardDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	public HumanMonthCardEntity getMonthCardByCharId(long charId)
	{
		List<HumanMonthCardEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_MONTH_CARD_BY_CHARID,GET_MONTH_CARD_BY_CHARID_PARAMS,new Object[]{charId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	
	
	@Override
	protected Class<HumanMonthCardEntity> getEntityClazz() 
	{
		return HumanMonthCardEntity.class;
	}
}
