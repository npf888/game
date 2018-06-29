package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanVipNewEntity;

/**
 * 
 * @author 郭君伟
 *
 */
public class HumanVipNewDao extends BaseDao<HumanVipNewEntity> {

	public HumanVipNewDao(DBService dbServ) {
		super(dbServ);
	}
	
	private static final String GET_VIP_BY_HumanId = "queryVipByHumanId";
	private static final String[] GET_VIP_BY_HumanId_PARAMS = new String[] { "humanId" };
	
	
	public HumanVipNewEntity getVipHumanById(long humanId){
		List<HumanVipNewEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_VIP_BY_HumanId,GET_VIP_BY_HumanId_PARAMS,new Object[]{humanId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}

	@Override
	protected Class<HumanVipNewEntity> getEntityClazz() {
		return HumanVipNewEntity.class;
	}

}
