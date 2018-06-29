package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanWeekCardEntity;

/**
 * 周卡dao
 * @author wayne
 *
 */
public class HumanWeekCardDao extends BaseDao<HumanWeekCardEntity>{
	
	private static final String GET_WEEK_CARD_BY_CHARID = "queryWeekCardByCharid";
	private static final String[] GET_WEEK_CARD_BY_CHARID_PARAMS=new String[] { "charId" };
	public HumanWeekCardDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	public HumanWeekCardEntity getWeekCardByCharId(long charId)
	{
		List<HumanWeekCardEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_WEEK_CARD_BY_CHARID,GET_WEEK_CARD_BY_CHARID_PARAMS,new Object[]{charId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	
	
	@Override
	protected Class<HumanWeekCardEntity> getEntityClazz() 
	{
		return HumanWeekCardEntity.class;
	}
}

