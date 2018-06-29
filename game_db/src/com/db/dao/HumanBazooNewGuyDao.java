package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanBazooAchieveEntity;
import com.db.model.HumanBazooNewGuyEntity;

public class HumanBazooNewGuyDao extends BaseDao<HumanBazooNewGuyEntity>{

	
	private static final String GET_HUMAN_BAZOO_NEW_GUY = "queryHumanBazooNewGuyByPassportId";
	private static final String[] GET_HUMAN_BAZOO_NEW_GUY_PARAMS = new String[] { "passportId"};
	
	public HumanBazooNewGuyDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<HumanBazooNewGuyEntity> getEntityClazz() {
		return HumanBazooNewGuyEntity.class;
	}

	
	
	/**
	 * 根据用户ID查询  四种类型的新手
	 * @param UUID
	 * @return
	 */
	
	public List<HumanBazooNewGuyEntity> getHumanBazooNewGuyByPassportId(long passportId)
	{
		
		List<HumanBazooNewGuyEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_HUMAN_BAZOO_NEW_GUY,GET_HUMAN_BAZOO_NEW_GUY_PARAMS,new Object[]{passportId});
		if(entities == null || entities.size() == 0){
			return null;
		}
		return entities;
	}
}
