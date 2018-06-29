package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanPayguideEntity;

public class HumanPayguideDao extends BaseDao<HumanPayguideEntity>{

	private static final String GET_HUMANPAYGUIDE_BY_USERID = "humanpayguideByUserid";
	
	
	public HumanPayguideDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<HumanPayguideEntity> getEntityClazz() {
		return HumanPayguideEntity.class;
	}

	
	
	/**
	 * 更具用户ID和邮件类别从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */
	
	public HumanPayguideEntity getHumanPayguideEntity(long userId)
	{
		List<HumanPayguideEntity> humanPayguideEntityList=this._dbServ.findByNamedQueryAndNamedParam(GET_HUMANPAYGUIDE_BY_USERID,new String[]{"userId"},new Object[]{userId});
		if(humanPayguideEntityList==null || humanPayguideEntityList.size()==0)
			return null;
		return humanPayguideEntityList.get(0);
	}
	
}
