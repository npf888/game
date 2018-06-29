package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanBazooRankEntity;

public class HumanBazooRankDao extends BaseDao<HumanBazooRankEntity>{
	private static final String QUERY_BAZOO_RANK_BY_DAY = "queryBazooRankAllByDay";
	private static final String QUERY_BAZOO_RANK_BY_WEEK = "queryBazooRankAllByWeek";
	private static final String QUERY_BAZOO_RANK_BY_MONTH = "queryBazooRankAllByMonth";
	private static final String GET_BAZOO_RANK_BY_PASSPORTID = "queryBazooRankByPassportId";
	private static final String[] GET_BAZOO_RANK_BY_PASSPORTID_PARAMS = new String[] { "passportId" };

	public HumanBazooRankDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<HumanBazooRankEntity> getEntityClazz() {
		return HumanBazooRankEntity.class;
	}

	
	
	
	public HumanBazooRankEntity getBazooRankByPassportId(long passportId)
	{
		List<HumanBazooRankEntity> BazooRankEntitylist=this._dbServ.findByNamedQueryAndNamedParam(GET_BAZOO_RANK_BY_PASSPORTID,GET_BAZOO_RANK_BY_PASSPORTID_PARAMS,new Object[]{passportId});
		if(BazooRankEntitylist==null || BazooRankEntitylist.size()==0)
			return null;
		return BazooRankEntitylist.get(0);
	}
	
	
	public List<HumanBazooRankEntity> getBazooRankAll(int dateType)
	{
		int perPage = 100;
		int start = 0;
		List<HumanBazooRankEntity> BazooRankEntitylist = null;
		if(dateType == 1){
			BazooRankEntitylist=this._dbServ.findByNamedQueryAndNamedParam(QUERY_BAZOO_RANK_BY_DAY,new String[]{},new Object[]{},perPage,start);
			
		}else if(dateType == 2){
			BazooRankEntitylist=this._dbServ.findByNamedQueryAndNamedParam(QUERY_BAZOO_RANK_BY_WEEK,new String[]{},new Object[]{},perPage,start);
			
		}else if(dateType == 3){
			BazooRankEntitylist=this._dbServ.findByNamedQueryAndNamedParam(QUERY_BAZOO_RANK_BY_MONTH,new String[]{},new Object[]{},perPage,start);
			
		}
		return BazooRankEntitylist;
	}
	
	
	
	
	
	
	
	
}
