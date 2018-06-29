package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanBazooPersonalEntity;

public class HumanBazooPersonalDao extends BaseDao<HumanBazooPersonalEntity>{
	private static final String QUERY_BAZOO_PERSONAL_BY_DAY = "queryBazooPersonalAllByDay";
	private static final String QUERY_BAZOO_PERSONAL_BY_WEEK = "queryBazooPersonalAllByWeek";
	private static final String QUERY_BAZOO_PERSONAL_BY_MONTH = "queryBazooPersonalAllByMonth";
	private static final String GET_BAZOO_PERSONAL_BY_PASSPORTID = "queryBazooPersonalByPassportId";
	private static final String[] GET_BAZOO_PERSONAL_BY_PASSPORTID_PARAMS = new String[] { "passportId" };

	public HumanBazooPersonalDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<HumanBazooPersonalEntity> getEntityClazz() {
		return HumanBazooPersonalEntity.class;
	}

	
	
	
	public List<HumanBazooPersonalEntity> getBazooPersonalByPassportId(long passportId)
	{
		List<HumanBazooPersonalEntity> HumanBazooPersonalEntitylist=this._dbServ.findByNamedQueryAndNamedParam(GET_BAZOO_PERSONAL_BY_PASSPORTID,GET_BAZOO_PERSONAL_BY_PASSPORTID_PARAMS,new Object[]{passportId});
		return HumanBazooPersonalEntitylist;
	}
	
	
	public List<HumanBazooPersonalEntity> getBazooPersonalAll(int page, int dateType)
	{
		int perPage = 50;
		int start = perPage*(page-1);
		List<HumanBazooPersonalEntity> HumanBazooPersonalEntitylist = null;
		if(dateType == 1){
			HumanBazooPersonalEntitylist=this._dbServ.findByNamedQueryAndNamedParam(QUERY_BAZOO_PERSONAL_BY_DAY,new String[]{},new Object[]{},perPage,start);
			
		}else if(dateType == 2){
			HumanBazooPersonalEntitylist=this._dbServ.findByNamedQueryAndNamedParam(QUERY_BAZOO_PERSONAL_BY_WEEK,new String[]{},new Object[]{},perPage,start);
			
		}else if(dateType == 3){
			HumanBazooPersonalEntitylist=this._dbServ.findByNamedQueryAndNamedParam(QUERY_BAZOO_PERSONAL_BY_MONTH,new String[]{},new Object[]{},perPage,start);
			
		}
		return HumanBazooPersonalEntitylist;
	}
	

}
