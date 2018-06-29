package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.UserInfo;

public class UserInfoDao extends BaseDao<UserInfo> {
	private static final String SELECT_QUERY = "getUserInfoByUsernameAndChannel";
	private static final String[] SELECT_QUERY_PARAMS = new String[] { "username","channel" };
	
	public UserInfoDao(DBService dbServ) {
		super(dbServ);
	}
	
	@Override
	protected Class<UserInfo> getEntityClazz() {
		return UserInfo.class;
	}

	public UserInfo getUserInfoByUsernameAndChannel(String username, String channel) {
		List<UserInfo> entities = this._dbServ.findByNamedQueryAndNamedParam(SELECT_QUERY,SELECT_QUERY_PARAMS,new Object[]{username,channel});
		if(entities != null){
			return entities.get(0);
		}
		return null;
	}


}
