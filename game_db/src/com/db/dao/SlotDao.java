package com.db.dao;

import java.util.List;

import com.core.orm.DBService;

import com.db.model.SlotEntity;

public class SlotDao extends BaseDao<SlotEntity>{

	private static final String GET_ALL_SLOTS = "queryAllSlots";

	
	public SlotDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}
	



	/**
	 * 更具用户ID和邮件类别从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */
	
	public List<SlotEntity> getAllSlots()
	{
		return this._dbServ.findByNamedQuery(GET_ALL_SLOTS);
	}
	
	
	@Override
	protected Class<SlotEntity> getEntityClazz() 
	{
		return SlotEntity.class;
	}

}
