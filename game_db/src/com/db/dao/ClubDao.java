package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.ClubEntity;

public class ClubDao extends BaseDao<ClubEntity>{

	public ClubDao(DBService dbServ) {
		super(dbServ);
	}

	private static final String SELECT_BY_ID_QUERY = "selectClubById";
	private static final String SELECT_BY_NAME_QUERY = "selectClubByName";
	private static final String SELECT_50_QUERY = "select50";
	private static final String SELECT_All = "selectAll";
	private static final String CLEAN_RANKING = "cleanRanking";
	private static final String CLEAN_RANKING_MEMBER = "cleanRankingMember";
	private static final String[] SELECT_BY_ID__PARAMS = new String[] { "id" };
	private static final String[] SELECT_BY_NAME_PARAMS = new String[] { "name" };
	

	public ClubEntity getClubById(long id){
		List<ClubEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(SELECT_BY_ID_QUERY,SELECT_BY_ID__PARAMS,new Object[]{id});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	
	public ClubEntity getClubByName(String name){
		List<ClubEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(SELECT_BY_NAME_QUERY,SELECT_BY_NAME_PARAMS,new Object[]{name});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	
	public List<ClubEntity> getClubAll(){
		List<ClubEntity> entities= this._dbServ.findByNamedQuery(SELECT_All);
		if(entities!=null && entities.size()>0)
			return entities;
		return null;
	}

	/**
	 * 清理排行榜数值
	 */
	public void cleanGongxianAndMoney(){
		this._dbServ.queryForUpdate(CLEAN_RANKING,null,null);
		this._dbServ.queryForUpdate(CLEAN_RANKING_MEMBER,null,null);
	}
	/**
	 *
	 * @return
	 */
	public List<ClubEntity> getClub50(){
		List<ClubEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(SELECT_50_QUERY,null,null,50,0);
		if(entities!=null && entities.size()>0)
			return entities;
		return null;
	}
	
	
	@Override
	protected Class<ClubEntity> getEntityClazz() 
	{
		return ClubEntity.class;
	}
}
