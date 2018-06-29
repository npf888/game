package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.ClubInvateEntity;

public class ClubInvateDao extends BaseDao<ClubInvateEntity> {

	private static final String SELECT_QUERY = "selectClubInvateByPlayerId";
	private static final String[] SELECT_QUERY_PARAMS = new String[] { "id" };
	

	public ClubInvateDao(DBService dbServ) {
		super(dbServ);
	}

	/**
	 * 查找俱乐部邀请
	 * @return
	 */
	public List<ClubInvateEntity> getClubInvateByPlayerId(long playerId) {
		List<ClubInvateEntity> entities = this._dbServ.findByNamedQueryAndNamedParam(SELECT_QUERY,SELECT_QUERY_PARAMS,new Object[]{playerId});
		if (entities != null && entities.size() > 0)
			return entities;
		return null;
	}

	@Override
	protected Class<ClubInvateEntity> getEntityClazz() {
		return ClubInvateEntity.class;
	}

}
