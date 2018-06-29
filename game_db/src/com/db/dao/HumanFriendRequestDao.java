package com.db.dao;

import java.util.List;

import com.core.orm.DBService;

import com.db.model.FriendRequestEntity;

/**
 * 好友
 * @author wayne
 *
 */
public class HumanFriendRequestDao extends BaseDao<FriendRequestEntity>{

	private static final String GET_FRIEND_REQUEST_BY_CHARID = "queryFriendRequestByCharid";
	private static final String[] GET_FRIEND_REQUEST_BY_CHARID_PARAMS = new String[] { "charId" };
	
	private static final String GET_FRIEND_REQUEST_BY_CHARID_SENDID = "queryFriendRequestByCharidSendid";
	private static final String[] GET_FRIEND_REQUEST_BY_CHARID_SENDID_PARAMS = new String[] { "charId","sendId" };
	
	private static final String SELECT_FRIEND_REQUEST_FOR_JPUSH = "selectFriendRequestForJpush";
	private static final String[] SELECT_FRIEND_REQUEST_FOR_JPUSH_PARAMS = new String[] {};
	
	public HumanFriendRequestDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 从数据库中读取用户好友信息
	 * @param UUID
	 * @return
	 */

	public List<FriendRequestEntity> getFriendRequestListByCharId(long charId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_FRIEND_REQUEST_BY_CHARID, GET_FRIEND_REQUEST_BY_CHARID_PARAMS,
				new Object[] { charId });
	
	}
	
	/**
	 * 从数据库中读取用户好友信息
	 * @param UUID
	 * @return
	 */

	public FriendRequestEntity getFriendRequestByCharIdAndSendId(long charId,long sendId)
	{
		List<FriendRequestEntity> tempL =  this._dbServ.findByNamedQueryAndNamedParam(GET_FRIEND_REQUEST_BY_CHARID_SENDID, GET_FRIEND_REQUEST_BY_CHARID_SENDID_PARAMS,
				new Object[] { charId,sendId });
		if(tempL.size()==0)
			return null;
		return tempL.get(0);
	}

	@Override
	protected Class<FriendRequestEntity> getEntityClazz()
	{
		return FriendRequestEntity.class;
	}

	/**
	 * 有好友申请未处理的列表
	 * @param charId
	 * @return
	 */
	public List<FriendRequestEntity> selectFriendRequestForJpush()
	{
		List<FriendRequestEntity> l =  this._dbServ.findByNamedQueryAndNamedParam(SELECT_FRIEND_REQUEST_FOR_JPUSH, SELECT_FRIEND_REQUEST_FOR_JPUSH_PARAMS, new Object[] {});
		return l;
	}
}
