package com.db.dao;

import java.util.List;

import com.core.orm.DBService;

import com.db.model.CompensationEntity;

/**
 * 补偿dao
 * @author wayne
 *
 */
public class CompensationDao extends BaseDao<CompensationEntity>{
	private static final String GET_COMPENSATIONS = "queryCompensations";

	public CompensationDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	public List<CompensationEntity> getAllCompensations()
	{
		return this._dbServ.findByNamedQuery(GET_COMPENSATIONS);
	}
	
	@Override
	protected Class<CompensationEntity> getEntityClazz() 
	{
		return CompensationEntity.class;
	}

}


