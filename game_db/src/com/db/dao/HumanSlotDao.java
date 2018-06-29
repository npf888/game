package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanSlotEntity;


public class HumanSlotDao extends BaseDao<HumanSlotEntity>{

	private static final String GET_ALL_HUMAN_SLOTS_BY_CHAR_ID = "queryAllHumanSlotsByCharId";
	private static final String[] GET_ALL_HUMAN_SLOTS_BY_CHAR_ID_PARAMS = new String[] { "charId" };
	public HumanSlotDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 更具用户ID和邮件类别从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */
	
	public List<HumanSlotEntity> getAllSlotsByCharId(long charId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_ALL_HUMAN_SLOTS_BY_CHAR_ID, GET_ALL_HUMAN_SLOTS_BY_CHAR_ID_PARAMS, new Object[]{charId});
	}
	
	
	@Override
	protected Class<HumanSlotEntity> getEntityClazz() 
	{
		return HumanSlotEntity.class;
	}


}
