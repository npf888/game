package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.BossEntity;

public class BossDao extends BaseDao<BossEntity>{

	private static final String GET_LAST_BOSS= "getlastboss";
	private static final String GET_LAST_TWO_BOSS= "getlasttwoboss";
	private static final String GET_BOSS_BY_ID= "getbossbyid";
	
	public BossDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<BossEntity> getEntityClazz() {
		return BossEntity.class;
	}

	/**
	 * 获取上一个boss
	 * @return
	 */
	public List<BossEntity> getBossByBossId(long bossId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_BOSS_BY_ID, new String[]{"bossID"}, new Object[]{bossId});
	}
	public List<BossEntity> getLastBoss()
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_LAST_BOSS, new String[]{}, new Object[]{}, 1, 0);
	}
	public List<BossEntity> getLastTwoBoss()
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_LAST_TWO_BOSS, new String[]{}, new Object[]{}, 2, 0);
	}
}
