package com.db.dao;

import com.core.orm.DBService;
import com.db.model.ClubGiftRecordEntity;

public class ClubGiftRecordtDao extends BaseDao<ClubGiftRecordEntity> {
	
	public ClubGiftRecordtDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<ClubGiftRecordEntity> getEntityClazz() {
		return ClubGiftRecordEntity.class;
	}

}
