package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanAchievementEntity;

public class HumanAchievementDao extends BaseDao<HumanAchievementEntity> {
	
	private static final String GET_ALL_ACHIEVEMENT_BY_CHAR_ID = "queryAllACHIEVEMENTByCharId";
	private static final String[] GET_ALL_HUMAN_ACHIEVEMENT_BY_CHAR_ID_PARAMS = new String[] { "charId" };

	public HumanAchievementDao(DBService dbServ) {
		super(dbServ);
	}

	public HumanAchievementEntity getAchievementEntity(long charId){
		List<HumanAchievementEntity> list = this._dbServ.findByNamedQueryAndNamedParam(GET_ALL_ACHIEVEMENT_BY_CHAR_ID, GET_ALL_HUMAN_ACHIEVEMENT_BY_CHAR_ID_PARAMS, new Object[]{charId});
		if(list!=null && list.size()>0)
			return list.get(0);
		return null;
	}
	
	
	@Override
	protected Class<HumanAchievementEntity> getEntityClazz() {
		return HumanAchievementEntity.class;
	}

}
