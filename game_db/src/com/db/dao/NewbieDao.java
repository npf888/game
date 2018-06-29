package com.db.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.orm.DBService;
import com.db.model.NewbieEntity;

public class NewbieDao extends BaseDao<NewbieEntity>
{
	private static final Logger logger = LoggerFactory.getLogger(NewbieDao.class);
	
	public NewbieDao(DBService dbServ)
	{
		super(dbServ);
	}
	
	/**
	 * 新手引导已完成步骤
	 * @param charId
	 * @return
	 */
	public NewbieEntity querNewbieStepByCharid(long charId)
	{
		List<NewbieEntity> l = this._dbServ.findByNamedQueryAndNamedParam("querNewbieStepByCharid", new String[] {"charId"}, new Object[] { charId});
		if(l!=null && l.size()==1)
		{
			return l.get(0);
		}
		else
		{
			logger.error("wrong newbie records for charId: "+charId);
			return null;
		}
	}
	
	
	@Override
	protected Class<NewbieEntity> getEntityClazz() 
	{
		return NewbieEntity.class;
	}
}
