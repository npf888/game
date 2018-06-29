package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.BazooRoomCreateEntity;

public class BazooRoomCreateDao extends BaseDao<BazooRoomCreateEntity>{
	private static final String GET_ROOMNUMBER = "queryFromBazooRoomCreate";

	public BazooRoomCreateDao(DBService dbServ) {
		super(dbServ);
	}


	/**
	 * 更具用户ID和邮件类别从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */
	
	public List<BazooRoomCreateEntity> getAllRoomNumberFromBazooRoomCreate()
	{
		return this._dbServ.findByNamedQuery(GET_ROOMNUMBER);
	}
	
	
	@Override
	protected Class<BazooRoomCreateEntity> getEntityClazz() 
	{
		return BazooRoomCreateEntity.class;
	}
}
