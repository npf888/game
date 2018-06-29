package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanGiftEntity;
/**
 * 
 * @author 郭君伟
 *
 */
public class HumanGiftDao extends BaseDao<HumanGiftEntity> {
	
	
	private static final String GET_HUMANGIFT_HUMANID = "queryHumanGiftId";
	
	private static final String[] GET_HUMANGIFT_HUMANID_PARAMS = new String[] { "charId" };

	public HumanGiftDao(DBService dbServ) {
		super(dbServ);
	}
	
	public HumanGiftEntity getHumanGiftById(long humanId){
		List<HumanGiftEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_HUMANGIFT_HUMANID,GET_HUMANGIFT_HUMANID_PARAMS,new Object[]{humanId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}

	@Override
	protected Class<HumanGiftEntity> getEntityClazz() {
		return HumanGiftEntity.class;
	}

}
