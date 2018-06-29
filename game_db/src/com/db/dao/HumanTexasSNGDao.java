package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanTexasSNGEntity;



/**
 * 德州扑克dao
 * @author Thinker
 *
 */
public class HumanTexasSNGDao extends BaseDao<HumanTexasSNGEntity>
{
	
	private static final String GET_HUMAN_TEXAS_SNG_BY_CHARID = "queryHumanTexasSNGByCharid";
	private static final String[] GET_HUMAN_TEXAS_SNG_BY_CHARID_PARAMS = new String[] { "charId" };
	
	public HumanTexasSNGDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 从数据库中读取用户好友信息
	 * @param UUID
	 * @return
	 */
	
	public HumanTexasSNGEntity getHumanTexasSNGByCharId(long charId)
	{
		List<HumanTexasSNGEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_HUMAN_TEXAS_SNG_BY_CHARID, GET_HUMAN_TEXAS_SNG_BY_CHARID_PARAMS,
				new Object[] { charId });
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	
	}

	@Override
	protected Class<HumanTexasSNGEntity> getEntityClazz() 
	{
		return HumanTexasSNGEntity.class;
	}
}
