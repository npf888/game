package com.db.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.core.orm.DataAccessException;
import com.core.orm.BaseEntity;
import com.core.orm.DBService;
import com.core.orm.SoftDeleteEntity;

/**
 * 基础的 DAO
 * 
 * @param <E> 实体的类型 
 * @author Thinker
 * 
 */
public abstract class BaseDao<E extends BaseEntity<?>> 
{
	/** 数据库服务 */
	protected final DBService _dbServ;

	/**
	 * 类参数构造器
	 * 
	 * @param dbServ 
	 * 
	 */
	public BaseDao(DBService dbServ)
	{
		if (dbServ == null) 
		{
			throw new IllegalArgumentException("dbServ is null");
		}
		this._dbServ = dbServ;
	}

	/**
	 * 获取数据库服务
	 * 
	 * @return 
	 * 
	 */
	public DBService getDBService()
	{
		return _dbServ;
	}

	/**
	 * 保存一个实体
	 * 
	 * @param obj
	 * 
	 */
	public Serializable save(E obj)
	{
		if (obj == null)
		{
			return null;
		} else
		{
			return this._dbServ.save(obj);
		}
	}

	/**
	 * 保存列表中的所有实体
	 * 
	 * @param objList
	 * @return 
	 * 
	 */
	public List<Serializable> saveAll(List<E> objList)
	{
		if (objList == null || objList.size() <= 0)
		{
			return Collections.emptyList();
		}

		@SuppressWarnings("unchecked")
		List<BaseEntity<?>> el = (List<BaseEntity<?>>)objList;
		// 保存实体列表并返回结果
		return this._dbServ.saveAll(el);
	}

	/**
	 * 更新一个实体
	 * 
	 * @param obj
	 * 
	 */
	public void update(E obj)
	{
		if (obj == null)
		{
			return;
		} else
		{
			this._dbServ.update(obj);
		}
	}

	/**
	 * 保存或更新一个实体
	 * 
	 * @param obj 
	 * 
	 */
	public void saveOrUpdate(E obj) 
	{
		if (obj == null)
		{
			return;
		} else 
		{
			this._dbServ.saveOrUpdate(obj);
		}
	}

	/**
	 * 删除一个实体
	 * 
	 * @param obj
	 * @throws DataAccessException 
	 * @see {@link DBService#softDelete(SoftDeleteEntity)}
	 * @see {@link DBService#delete(BaseEntity)}
	 * 
	 */
	public void delete(E obj)
	{
		if (obj == null)
		{
			return;
		}

		if (obj instanceof SoftDeleteEntity)
		{
			// 软删除
			this._dbServ.softDelete((SoftDeleteEntity<?>) obj);
		} else 
		{
			// 物理删除
			this._dbServ.delete(obj);
		}
	}
	
	/**
	 * 根据指定类型的指定 ID 的实体
	 * 
	 * @param entityClazz
	 * @param ID 
	 * 
	 */
	@SuppressWarnings({ "deprecation" })
	public void delete(Class<? extends BaseEntity<?>> entityClazz,Serializable ID)
	{

		if (entityClazz == null || ID == null)
		{
			return;
		}

		this._dbServ.deleteById(entityClazz, ID);
	}

	/**
	 * 根据主键获取一个实体
	 * 
	 * @param ID
	 * @return 
	 * @throws DataAccessException 
	 * @see {@link DBService#get(Class, java.io.Serializable)}
	 * 
	 */
	public E get(Serializable ID)
	{
		if (ID == null) 
		{
			return null;
		} else 
		{
			return this._dbServ.get(this.getEntityClazz(), ID);
		}
	}

	/**
	 * 根据指定类型的指定 ID 查询实体
	 * 
	 * @param entityClazz
	 * @param ID
	 * @return 
	 * @see {@link DBService#get(Class, java.io.Serializable)} 
	 * 
	 */
	public Object get(Class<? extends BaseEntity<Serializable>> entityClazz,Serializable ID) 
	{
		if (entityClazz == null || ID == null)
		{
			return null;
		}

		return this._dbServ.get(entityClazz, ID);
	}

	/**
	 * 获取所有实体
	 * 
	 * @param clazz
	 * @return 
	 * 
	 */
	public List<E> getAll(Class<E> clazz) 
	{
		return this._dbServ.getAll(clazz);
	}

	/**
	 * 取得该 Dao 所操作的对象类型
	 * 
	 * @return 
	 * 
	 */
	protected abstract Class<E> getEntityClazz();

	/**
	 * 检查实体类
	 * 
	 * @throws SQLException 
	 * 
	 */
	public final void checkEntity()
	{
		this._dbServ.checkEntityClazz(this.getEntityClazz());
	}
}
