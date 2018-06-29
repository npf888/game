package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.HumanTaskEntity;

/**
 * 任务
 * @author wayne
 *
 */
public class HumanTaskDao extends BaseDao<HumanTaskEntity>{
	
	private static final String GET_TASK_BY_CHARID = "queryTaskByCharid";
	private static final String[] GET_TASK_BY_CHARID_PARAMS = new String[] { "charId" };
	

	public HumanTaskDao(DBService dbServ) {
		super(dbServ);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 从数据库中读取用户好友信息
	 * @param UUID
	 * @return
	 */

	public HumanTaskEntity getTaskByCharId(long charId)
	{
		List<HumanTaskEntity> tempList=this._dbServ.findByNamedQueryAndNamedParam(GET_TASK_BY_CHARID, GET_TASK_BY_CHARID_PARAMS,
				new Object[] { charId });
		if(tempList==null || tempList.size()==0)
			return null;
		return tempList.get(0);
	
	}

	@Override
	protected Class<HumanTaskEntity> getEntityClazz()
	{
		return HumanTaskEntity.class;
	}

}
