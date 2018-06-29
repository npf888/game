package com.db.dao;

import java.util.List;

import com.core.orm.DBService;

import com.db.model.HumanWeekSignInEntity;

/**
 * 签到
 * @author wayne
 *
 */
public class HumanWeekSignInDao extends BaseDao<HumanWeekSignInEntity>{

	private static final String GET_WEEK_SIGN_IN_BY_CHARID = "queryWeekSignInByCharid";
	private static final String[] GET_WEEK_SIGN_IN_BY_CHARID_PARAMS=new String[] { "charId" };
	
	private static final String SELECT_NOT_SIGN_FOR_JPUSH = "selectNotSignForJpush";
	private static final String[] SELECT_NOT_SIGN_FOR_JPUSH_PARAMS=new String[] {};
	
	public HumanWeekSignInDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	public HumanWeekSignInEntity getWeekSignInByCharId(long charId)
	{
		List<HumanWeekSignInEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_WEEK_SIGN_IN_BY_CHARID,GET_WEEK_SIGN_IN_BY_CHARID_PARAMS,new Object[]{charId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	
	@Override
	protected Class<HumanWeekSignInEntity> getEntityClazz() 
	{
		return HumanWeekSignInEntity.class;
	}
	
	public List<HumanWeekSignInEntity> selectNotSignForJpush()
	{
		List<HumanWeekSignInEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(SELECT_NOT_SIGN_FOR_JPUSH,SELECT_NOT_SIGN_FOR_JPUSH_PARAMS,new Object[]{});
		if(entities!=null && entities.size()>0)
			return entities;
		return null;
	}
}
