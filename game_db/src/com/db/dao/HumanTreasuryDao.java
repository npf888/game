package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanTreasuryEntity;

public class HumanTreasuryDao extends BaseDao<HumanTreasuryEntity>{

	public HumanTreasuryDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<HumanTreasuryEntity> getEntityClazz() {
		return HumanTreasuryEntity.class;
	}
	
	/**
	 * 操作
	 */
	private static final String GET_TREASURY_BY_CHARTID = "queryTreasuryByChartId";
	
	public List<HumanTreasuryEntity> getTreasuryByChartId(long chartId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_TREASURY_BY_CHARTID,new String[]{"chartId"},new Object[]{chartId});
	}
	
	public void saveOrUpdateEntity(HumanTreasuryEntity entity){
		this._dbServ.saveOrUpdate(entity);
	}
	
	
	
	
	private static final String SELECT_PLAYER_BY_GOLD_PERCENT = "selectPlayerByGoldPercent";
	
	public List<HumanTreasuryEntity> selectPlayerByGoldPercent(int type)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(SELECT_PLAYER_BY_GOLD_PERCENT,new String[]{"type"},new Object[]{type});
	}
}
