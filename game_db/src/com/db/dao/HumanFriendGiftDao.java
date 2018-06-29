package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanFriendGiftEntity;

/**
 * 好友礼物dao
 * @author wayne
 *
 */
public class HumanFriendGiftDao extends BaseDao<HumanFriendGiftEntity> {
	
	private static final String GET_FRIEND_GIFT_BY_CHARID = "queryFriendGiftByCharid";
	private static final String[] GET_FRIEND_GIFT_BY_CHARID_PARAMS = new String[] { "charId" };

	public HumanFriendGiftDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 从数据库中读取用户好友信息
	 * @param UUID
	 * @return
	 */
	
	public List<HumanFriendGiftEntity> getFriendGiftListByCharId(long charId)
	{
		return this._dbServ.findByNamedQueryAndNamedParam(GET_FRIEND_GIFT_BY_CHARID, GET_FRIEND_GIFT_BY_CHARID_PARAMS,
				new Object[] { charId });
	
	}
	

	@Override
	protected Class<HumanFriendGiftEntity> getEntityClazz()
	{
		return HumanFriendGiftEntity.class;
	}

}
