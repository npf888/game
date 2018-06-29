package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.BaccartRoomModelEntity;

/**
 * 百家乐房间
 * @author wayne
 *
 */
public class BaccartRoomModelDao extends BaseDao<BaccartRoomModelEntity>{
	
	private static final String GET_BACCARTROOM_BY_ROOM_ID = "queryBaccartRoomByRoomId";
	private static final String[] GET_BACCARTROOM_BY_ROOM_ID_PARAMS = new String[] { "roomId" };

	public BaccartRoomModelDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}


	/**
	 * 更具用户ID和邮件类别从数据库中读取邮件列表
	 * @param UUID
	 * @return
	 */
	
	public BaccartRoomModelEntity getBaccartRoomByRoomId(int roomId)
	{
		List<BaccartRoomModelEntity> baccartRoomModelEntityList=this._dbServ.findByNamedQueryAndNamedParam(GET_BACCARTROOM_BY_ROOM_ID,GET_BACCARTROOM_BY_ROOM_ID_PARAMS,new Object[]{roomId});
		if(baccartRoomModelEntityList==null || baccartRoomModelEntityList.size()==0)
			return null;
		return baccartRoomModelEntityList.get(0);
	}
	
	
	@Override
	protected Class<BaccartRoomModelEntity> getEntityClazz() 
	{
		return BaccartRoomModelEntity.class;
	}

}
