package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanBaccartEntity;

/**
 * 百家乐dao
 * @author wayne
 *
 */
public class HumanBaccartDao extends BaseDao<HumanBaccartEntity>{
	private static final String GET_BACCART_BY_CHARID = "queryBaccartByCharid";
	private static final String[] GET_BACCART_BY_CHARID_PARAMS = new String[] { "charId" };
	

	public HumanBaccartDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 更具用户ID和邮件类别从数据库中读取用户补偿
	 * @param UUID
	 * @return
	 */
	
	public HumanBaccartEntity getHumanBaccartByCharId(long charId)
	{
		
		List<HumanBaccartEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_BACCART_BY_CHARID,GET_BACCART_BY_CHARID_PARAMS,new Object[]{charId});
		if(entities == null || entities.size()==0)
			return null;
		return entities.get(0);
	}
	
	
	@Override
	protected Class<HumanBaccartEntity> getEntityClazz() 
	{
		return HumanBaccartEntity.class;
	}

}