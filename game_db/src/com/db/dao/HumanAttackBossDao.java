package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanAttackBossEntity;
import com.db.model.HumanEntity;

public class HumanAttackBossDao extends BaseDao<HumanAttackBossEntity>{

	/** 根据bossId 查询所有攻击过他的 人 */
	public static final String QUERY_BY_BOSSID = "queryByBossid";
	public static final String QUERY_NUM_BY_BOSSID = "queryNumByBossid";
	public static final String QUERY_WIN_NUM_BY_BOSSID = "queryWinNumByBossid";
	public static final String QUERY_WIN_NUM_BY_BOSSID_USERID = "queryWinNumByBossidUserId";
	
	public HumanAttackBossDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<HumanAttackBossEntity> getEntityClazz() {
		return HumanAttackBossEntity.class;
	}
	
	

	/**
	 * 根据bossId从数据库获取所有角色
	 */
	public List<HumanAttackBossEntity> getHumanAttackBossByBossId(long bossId)
	{
		return _dbServ.findByNamedQueryAndNamedParam(QUERY_BY_BOSSID, new String[] { "bossId" },
				new Object[] { bossId });
	}
	/**
	 * 根据bossId从数据库获取 前边 多少名
	 */
	public List<HumanAttackBossEntity> getForwardNumberHumanAttackBossByBossId(long bossId)
	{
		return _dbServ.findByNamedQueryAndNamedParam(QUERY_NUM_BY_BOSSID, new String[] { "bossId" },
				new Object[] { bossId});
	}
	/**
	 * 根据bossId从数据库获取 打 boss的 第一个人
	 */
	public List<HumanAttackBossEntity> getWinnerByBossId(long bossId)
	{
		
		return this._dbServ.findByNamedQueryAndNamedParam(QUERY_WIN_NUM_BY_BOSSID, new String[] { "bossId" }, new Object[]{bossId}, 1, 0);
	}
	/**
	 * 根据bossId 和 userId 从数据库获取 打 boss的 最后一次
	 */
	public List<HumanAttackBossEntity> getWinnerByBossIdAndUserId(long bossId,long userId)
	{
		
		return this._dbServ.findByNamedQueryAndNamedParam(QUERY_WIN_NUM_BY_BOSSID_USERID, new String[] { "bossId","userId" }, new Object[]{bossId,userId}, 1, 0);
	}
}
