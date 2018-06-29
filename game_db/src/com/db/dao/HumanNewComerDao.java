package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanNewComerEntity;

public class HumanNewComerDao extends BaseDao<HumanNewComerEntity>{
	private static final String HUMAN_NEW_COMER_ENTITY = "queryAllHumanNewComerEntity";
	public HumanNewComerDao(DBService dbServ) {
		super(dbServ);
	}
	

	@Override
	protected Class<HumanNewComerEntity> getEntityClazz() 
	{
		return HumanNewComerEntity.class;
	}
	
	
	/**
	 *
	 * @param UUID
	 * @return
	 */
	
	public List<HumanNewComerEntity> getAllHumanNewComerEntity(long userId)
	{
		List<HumanNewComerEntity> HumanNewComerEntityList=
				this._dbServ.findByNamedQueryAndNamedParam(HUMAN_NEW_COMER_ENTITY,new String[]{"userId"},new Object[]{userId});
		return HumanNewComerEntityList;
	}
	
	

}
