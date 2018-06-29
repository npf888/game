package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.ClubInviteEntity;

public class ClubInviteDao extends BaseDao<ClubInviteEntity>{

	private static final String QUERY = "selectClubInviteByPlayer";
	private static final String[] QUERY__PARAMS = new String[] { "player_id" };


	public ClubInviteDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<ClubInviteEntity> getEntityClazz() 
	{
		return ClubInviteEntity.class;
	}
	

	/**
	 * @param playerId
	 * @return
	 */
	public List<ClubInviteEntity> selectClubInviteByPlayer(long playerId){
		List<ClubInviteEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(QUERY,QUERY__PARAMS,new Object[]{playerId});
		if(entities!=null && entities.size()>0)
			return entities;
		return null;
	}
}
