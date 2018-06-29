package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.CommonActivityEntity;


public class CommonActivityDao extends BaseDao<CommonActivityEntity>{

	private static final String GET_ALL_COMMON_ACTIVITY = "getAllCommonActivity";
	public CommonActivityDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<CommonActivityEntity> getEntityClazz() {
		return CommonActivityEntity.class;
	}

	
	
	
	
	public List<CommonActivityEntity> getCommonActivity()
	{
		return this._dbServ.findByNamedQuery(GET_ALL_COMMON_ACTIVITY);
	}
	
}
