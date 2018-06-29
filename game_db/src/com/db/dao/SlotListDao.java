package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.SlotListEntity;

public class SlotListDao extends BaseDao<SlotListEntity>{
	public SlotListDao(DBService dbServ) {
		super(dbServ);
	}

	@Override
	protected Class<SlotListEntity> getEntityClazz() {
		return SlotListEntity.class;
	}
	
	/**
	 * 操作
	 */
	private static final String GET_SLOT_LISTS = "querySlotLists";
	private static final String GET_SLOT_ALL_LISTS = "querySlotAllLists";
	
	public List<SlotListEntity> getAllSlotLists()
	{
		return this._dbServ.findByNamedQuery(GET_SLOT_LISTS);
	}
	public List<SlotListEntity> getEverySlot()
	{
		return this._dbServ.findByNamedQuery(GET_SLOT_ALL_LISTS);
	}
	
	public void saveEntity(SlotListEntity entity){
		this._dbServ.save(entity);
	}
}
