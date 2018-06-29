package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.ClubApplyEntity;

public class ClubApplyDao extends BaseDao<ClubApplyEntity>{

	private static final String QUERY = "selectClubApply";
	private static final String[] QUERY__PARAMS = new String[] { "club_id" };

	private static final String QUERY2 = "selectClubApplyByPlayer";
	private static final String[] QUERY__PARAMS2 = new String[] { "player_id" };

	public ClubApplyDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<ClubApplyEntity> getEntityClazz() 
	{
		return ClubApplyEntity.class;
	}
	
	/**
	 * @param clubId
	 * @return
	 */
	public List<ClubApplyEntity> getClubApply(long clubId){
		List<ClubApplyEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(QUERY,QUERY__PARAMS,new Object[]{clubId});
		if(entities!=null && entities.size()>0)
			return entities;
		return null;
	}

	/**
	 * @param playerId
	 * @return
	 */
	public List<ClubApplyEntity> getClubApplyByPlayerId(long playerId){
		List<ClubApplyEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(QUERY2,QUERY__PARAMS2,new Object[]{playerId});
		if(entities!=null && entities.size()>0)
			return entities;
		return null;
	}
}
