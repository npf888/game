package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.TextasEntity;
/**
 * 
 * @author 郭君伟
 *
 */
public class TextasDao extends BaseDao<TextasEntity> {

	private static final String GET_ALL_Textas = "queryAllTextas";
	
	public TextasDao(DBService dbServ) {
		super(dbServ);
	}
	
	public List<TextasEntity> getAllTextas(){
		return this._dbServ.findByNamedQuery(GET_ALL_Textas);
	}

	@Override
	protected Class<TextasEntity> getEntityClazz() {
		return TextasEntity.class;
	}

}
