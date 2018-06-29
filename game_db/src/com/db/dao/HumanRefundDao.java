package com.db.dao;

import java.util.List;

import com.core.orm.DBService;

import com.db.model.HumanRefundEntity;

/**
 * 
 * @author wayne
 *
 */
public class HumanRefundDao   extends BaseDao<HumanRefundEntity>{

	private static final String GET_REFUNDS_BY_CHARID = "queryRefundsByCharid";
	private static final String[] GET_REFUNDS_BY_CHARID_PARAMS = new String[] { "charId" };
	
	public HumanRefundDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 更具用户ID和邮件类别从数据库中读取用户补偿
	 * @param UUID
	 * @return
	 */
	
	public List<HumanRefundEntity> getAllHumanRefunds(long charId)
	{
		
		List<HumanRefundEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_REFUNDS_BY_CHARID,GET_REFUNDS_BY_CHARID_PARAMS,new Object[]{charId});
		return entities;
	}
	
	
	@Override
	protected Class<HumanRefundEntity> getEntityClazz() 
	{
		return HumanRefundEntity.class;
	}

}
