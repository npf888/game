package com.db.dao;

import com.core.orm.DBService;
import com.db.model.ClubInvatePlayerEntity;

import java.util.List;

public class ClubInvatePlayerDao extends BaseDao<ClubInvatePlayerEntity>{
	private static final String SELECT_QUERY = "selectClubInvatePlayers";

	public ClubInvatePlayerDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<ClubInvatePlayerEntity> getEntityClazz()
	{
		return ClubInvatePlayerEntity.class;
	}
	
	/**
	 * @return
	 */
	public List<ClubInvatePlayerEntity> getClubInvatePlayersAll(){
		List<ClubInvatePlayerEntity> entities= this._dbServ.findByNamedQuery(SELECT_QUERY);
		if(entities!=null && entities.size()>0)
			return entities;
		return null;
	}

}
