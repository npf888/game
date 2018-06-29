package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.RankEntity;

public class RankDao extends BaseDao<RankEntity> {

	private static final String GET_ALL_Rank = "queryAllRank";
	
	private static final String GET_ROLE_NEW = "queryRankNewByCharid";
	
	private static final String[] GET_VIP_BY_CHARID_PARAMS=new String[] { "charId" };
	
	public RankDao(DBService dbServ) {
		super(dbServ);
	}

	/**
	 * 查询所有
	 * @return
	 */
	public List<RankEntity> getAllRankEntity(){
		return this._dbServ.findByNamedQuery(GET_ALL_Rank);
	}
	
	/**
	 * 加载单个排行榜数据
	 * @param charId
	 * @return
	 */
	public RankEntity getRankEntity(long charId){
		List<RankEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_ROLE_NEW,GET_VIP_BY_CHARID_PARAMS,new Object[]{charId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	
	
	@Override
	protected Class<RankEntity> getEntityClazz() {
		return RankEntity.class;
	}

}
