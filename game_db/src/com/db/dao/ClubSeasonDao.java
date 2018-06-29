package com.db.dao;

import com.core.orm.DBService;
import com.db.model.ClubSeasonEntity;

import java.util.List;

public class ClubSeasonDao extends BaseDao<ClubSeasonEntity>{

	public ClubSeasonDao(DBService dbServ) {
		super(dbServ);
	}

	private static final String SELECT_QUERY = "selectSeasonEndTime";

	public ClubSeasonEntity getClubSeasonEntity(long id){
		List<ClubSeasonEntity> entities= this._dbServ.findByNamedQuery(SELECT_QUERY);
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}

	@Override
	protected Class<ClubSeasonEntity> getEntityClazz()
	{
		return ClubSeasonEntity.class;
	}
}
