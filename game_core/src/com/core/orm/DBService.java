package com.core.orm;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.core.event.IEventTrigger;

/**
 * 数据库服务接口
 * 
 * @author Thinker
 * 
 */
public interface DBService extends IEventTrigger<DBAccessEvent>
{
	/**
	 * 根据 <tt>ID </tt> 获取指定类型的实体
	 * 
	 * @param <T>
	 * @param entityClazz 实体的类型
	 * @param ID 实体的 ID
	 * @return 
	 * 
	 * @throws DataAccessException 
	 * 
	 */
	public <T extends BaseEntity<?>> T get(Class<T> entityClazz, Serializable ID)throws DataAccessException;

	/**
	 * 获取指定类型的所有实体
	 * 
	 * @param <T>
	 * @param entityClazz 实体的类型
	 * @return 
	 * 
	 * @throws DataAccessException 
	 * 
	 */
	public <T extends BaseEntity<?>> List<T> getAll(Class<T> entityClazz)throws DataAccessException;

	/**
	 * 保存一个实体
	 * 
	 * @param entity
	 * @return
	 * @throws DataAccessException 
	 * 
	 */
	Serializable save(BaseEntity<?> entity) throws DataAccessException;

	/**
	 * 保存实体列表
	 * 
	 * @param el
	 * @return
	 * @throws DataAccessException 
	 * 
	 */
	List<Serializable> saveAll(List<BaseEntity<?>> el) throws DataAccessException;

	/**
	 * 更新一个实体
	 * 
	 * @param entity
	 * @throws DataAccessException
	 * 
	 */
	void update(BaseEntity<?> entity) throws DataAccessException;

	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 * @throws DataAccessException 
	 * 
	 */
	void delete(BaseEntity<?> entity) throws DataAccessException;

	/**
	 * 根据 ID 删除一个实体
	 * 
	 * @param entityClazz
	 * @param ID
	 * 
	 */
	@Deprecated
	void deleteById(Class<? extends BaseEntity<?>> entityClazz, Serializable ID);

	/**
	 * 软删除一个实体, 即, 在数据库并不真正的删除实体, 
	 * 只是将 {@link SoftDeleteEntity#getDeleted()} 标示为 {@link SoftDeleteEntity#DELETED}, 
	 * 并记录删除的时间 {@link SoftDeleteEntity#getDeleteDate()}
	 * 
	 * @param entity
	 * @throws DataAccessException
	 * 
	 */
	void softDelete(SoftDeleteEntity<?> entity) throws DataAccessException;

	/**
	 * 软删除一个实体, 即, 在数据库并不真正的删除实体, 
	 * 只是将 {@link SoftDeleteEntity#getDeleted()} 标示为 {@link SoftDeleteEntity#DELETED}, 
	 * 并记录删除的时间 {@link SoftDeleteEntity#getDeleteDate()}
	 * 
	 * @param entityClazz
	 * @param ID 
	 * @throws DataAccessException
	 * 
	 */
	@Deprecated
	void softDeleteById(
		Class<? extends SoftDeleteEntity<?>> entityClazz, Serializable ID) throws DataAccessException;

	/**
	 * 执行指定名称的查询
	 * 
	 * @param <T> 
	 * @param queryName 查询名称
	 * @return
	 * @throws DataAccessException
	 * 
	 */
	<T> List<T> findByNamedQuery(String queryName) throws DataAccessException;

	/**
	 * 执行指定名称的查询
	 * 
	 * @param <T> 
	 * @param queryName 查询的名称
	 * @param params 查询名称对应的查询语句中的参数名称
	 * @param values 与 params 对应的参数值
	 * @return
	 * @throws DataAccessException
	 * 
	 */
	<T> List<T> findByNamedQueryAndNamedParam(
		String queryName, String[] params, Object[] values) throws DataAccessException;

	/**
	 * 执行指定的更新查询
	 * 
	 * @param queryName 查询更新的名称
	 * @param params 查询更新名称对应的查询语句中的参数名称
	 * @param values 与 params 对应的参数值
	 * @return 更新的结果数
	 * @throws DataAccessException
	 * 
	 */
	int queryForUpdate(String queryName, String[] params, Object[] values) throws DataAccessException;

	/**
	 * 执行指定名称的查询,供分页查询使用
	 * 
	 * @param <T>
	 * @param queryName 查询的名称
	 * @param params 查询名称对应的查询语句中的参数名称
	 * @param values 与 params 对应的参数值
	 * @param maxResult 返回的最大结果数量,-1返回全部
	 * @param start 返回结果的起始索引,-1忽略该参数
	 * @return
	 * @throws DataAccessException 
	 * 
	 */
	<T> List<T> findByNamedQueryAndNamedParam(String queryName, String[] params, Object[] values, int maxResult, int start) throws DataAccessException;

	/**
	 * 保存或者更新(如果已经存在)一个实体
	 * 
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public abstract void saveOrUpdate(BaseEntity<?> entity)throws DataAccessException;

	/**
	 * 检查数据库连接是否正常
	 * 
	 * @throws SQLException 
	 * 
	 */
	void checkConnection();

	/**
	 * 检查实体类是否正常
	 * 
	 * @param entityClazz 
	 * @throws SQLException 
	 * 
	 */
	void checkEntityClazz(Class<? extends BaseEntity<?>> entityClazz);
	
	/**
	 * 执行指定名称的查询
	 * 
	 * @param queryName
	 *            查询名称
	 * @return
	 * @throws DataAccessException
	 */
	Long findUniqueResultByNamedQuery(String queryName) throws DataAccessException;
}