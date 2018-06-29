package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.FunctionEntity;

public class FunctionDao extends BaseDao<FunctionEntity>{

	private static final String GET_FUNCTIONENTITY = "getfunctionentity";
	
	public FunctionDao(DBService dbServ) {
		super(dbServ);
	}

	
	/**
	 * 查询所有没有过期的功能
	 * @param UUID
	 * @return
	 */
	
	public List<FunctionEntity> getAllFunctionEntity()
	{
		return this._dbServ.findByNamedQuery(GET_FUNCTIONENTITY);
	}
	
	
	@Override
	protected Class<FunctionEntity> getEntityClazz() 
	{
		return FunctionEntity.class;
	}
}
