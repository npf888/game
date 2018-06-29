package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.FriendEntity;

/**
 * 好友
 * @author wayne
 *
 */
public class HumanFriendDao extends BaseDao<FriendEntity>{

	private static final String GET_FRIEND_BY_CHARID = "queryFriendByCharid";
	private static final String[] GET_FRIEND_BY_CHARID_PARAMS = new String[] { "charId" };
	private static final String GET_FRIEND_BY_CHARID_FRIENDID= "queryFriendByCharidFriendId";
	private static final String[]  GET_FRIEND_BY_CHARID_FRIENDID_PARAMS = new String[] { "charId","friendId" };
	public HumanFriendDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 从数据库中读取用户好友信息
	 * @param UUID
	 * @return
	 */
	
	public List<FriendEntity> getFriendListByCharId(long charId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_FRIEND_BY_CHARID, GET_FRIEND_BY_CHARID_PARAMS,
				new Object[] { charId });
	
	}
	
	public FriendEntity getFriendByCharIdFriendId(long charId,long friendId)
	{
		List<FriendEntity> entities= this._dbServ.findByNamedQueryAndNamedParam(GET_FRIEND_BY_CHARID_FRIENDID,GET_FRIEND_BY_CHARID_FRIENDID_PARAMS,new Object[]{charId,friendId});
		if(entities!=null && entities.size()>0)
			return entities.get(0);
		return null;
	}

	@Override
	protected Class<FriendEntity> getEntityClazz()
	{
		return FriendEntity.class;
	}

}
