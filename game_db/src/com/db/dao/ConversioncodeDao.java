package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.Conversioncode;

public class ConversioncodeDao extends BaseDao<Conversioncode> {
	
	private static final String GET_ALL_Conversioncode = "queryAllConversioncode";

	public ConversioncodeDao(DBService dbServ) {
		super(dbServ);
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<Conversioncode> getAllConversioncodeEntity(){
		return this._dbServ.findByNamedQuery(GET_ALL_Conversioncode);
	}

	
	@Override
	protected Class<Conversioncode> getEntityClazz() {
		return Conversioncode.class;
	}

}
