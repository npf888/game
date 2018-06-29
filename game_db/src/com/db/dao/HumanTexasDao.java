package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.FriendEntity;
import com.db.model.HumanTexasEntity;;

/**
 * 德州扑克dao
 * @author Thinker
 *
 */
public class HumanTexasDao extends BaseDao<HumanTexasEntity>
{
	
	private static final String GET_HUMAN_TEXAS_BY_CHARID = "queryHumanTexasByCharid";
	private static final String[] GET_HUMAN_TEXAS_BY_CHARID_PARAMS = new String[] { "charId" };
	
	public HumanTexasDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 从数据库中读取用户好友信息
	 * @param UUID
	 * @return
	 */
	
	public HumanTexasEntity getHumanTexasByCharId(long charId)
	{
		List<HumanTexasEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_HUMAN_TEXAS_BY_CHARID, GET_HUMAN_TEXAS_BY_CHARID_PARAMS,
				new Object[] { charId });
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	
	}

	@Override
	protected Class<HumanTexasEntity> getEntityClazz() 
	{
		return HumanTexasEntity.class;
	}
}
