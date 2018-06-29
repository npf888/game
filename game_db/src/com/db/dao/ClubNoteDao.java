package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.ClubNoteEntity;

public class ClubNoteDao extends BaseDao<ClubNoteEntity> {

	public ClubNoteDao(DBService dbServ) {
		super(dbServ);
	}

	private static final String SELECT_QUERY = "selectClubNote";
	private static final String[] SELECT_QUERY_PARAMS = new String[] { "club_id" };

	public List<ClubNoteEntity> getClubNote(long clubId) {
		List<ClubNoteEntity> entities = this._dbServ.findByNamedQueryAndNamedParam(SELECT_QUERY,SELECT_QUERY_PARAMS,new Object[]{clubId});
		if (entities != null && entities.size() > 0)
			return entities;
		return null;
	}

	@Override
	protected Class<ClubNoteEntity> getEntityClazz() {
		return ClubNoteEntity.class;
	}
}
