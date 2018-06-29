package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanCollectEntity;

/**
 * 
 * @author 郭君伟
 *
 */
public class HumanCollectDao extends BaseDao<HumanCollectEntity> {

	public HumanCollectDao(DBService dbServ) {
		super(dbServ);
	}

	
	private static final String GET_COLLECT_BY_HUMANID = "queryCollectByHumanId";
	private static final String[] GET_COLLECT_BY_HUMANID_PARAMS = new String[] { "humanId" };
	
	
	public HumanCollectEntity getCollectHumanById(long humanId){
		List<HumanCollectEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_COLLECT_BY_HUMANID,GET_COLLECT_BY_HUMANID_PARAMS,new Object[]{humanId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}
	
	@Override
	protected Class<HumanCollectEntity> getEntityClazz() {
		return HumanCollectEntity.class;
	}

}
