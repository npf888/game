package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanMiscEntity;

/**
 * 
 * @author wayne
 *
 */
public class HumanMiscDao extends BaseDao<HumanMiscEntity>{
	
	private static final String GET_MISC_BY_CHARID = "queryMiscByCharid";
	private static final String[] GET_MISC_BY_CHARID_PARAMS=new String[] { "charId" };
	
	public HumanMiscDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	public HumanMiscEntity getMiscByCharId(long charId)
	{
		List<HumanMiscEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_MISC_BY_CHARID,GET_MISC_BY_CHARID_PARAMS,new Object[]{charId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	
	@Override
	protected Class<HumanMiscEntity> getEntityClazz() 
	{
		return HumanMiscEntity.class;
	}
	
	public List<HumanMiscEntity> selectPlayerByOnlineReward(int currentOnlineRewardId, long ts)
	{
		List<HumanMiscEntity> entities = this._dbServ.findByNamedQueryAndNamedParam("selectPlayerByOnlineReward",new String[]{"rewardId", "ts"},new Object[]{currentOnlineRewardId, ts});
		return entities;
	}
	
}
