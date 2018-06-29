package com.db.dao;

import java.util.List;

import com.core.orm.DBService;
import com.db.model.DbVersion;

/**
 * 数据库版本号操作类
 * @author Thinker
 * 
 */
public class DbVersionDao extends BaseDao<DbVersion>
{

	public DbVersionDao(DBService dbServcie)
	{
		super(dbServcie);
	}

	
	public DbVersion getDbVersion()
	{
		 List<DbVersion> _dbVersions = _dbServ.findByNamedQuery("queryDbVersion");
		if (_dbVersions == null || _dbVersions.isEmpty()) 
		{
			return null;
		}
		return _dbVersions.get(0); 
		 
	}

	@Override
	protected Class<DbVersion> getEntityClazz()
	{
		return DbVersion.class;
	}
}
