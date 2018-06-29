package com.core.orm;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.hibernate.type.NullableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.constants.SharedConstants;
import com.core.event.IEvent;
import com.core.event.IEventListener;

/**
 * 基于Hibernate实现的DBService
 * @author Thinker
 * 
 */
@SuppressWarnings("unchecked")
public class HibernateDBServcieImpl implements DBService 
{
	private static final Logger logger = LoggerFactory.getLogger("lzr.db.hibernate");
	private final SessionFactory sessionFactory;
	private final HibernamteTemplate _tranxTmpl = new TranHibernateTemplate();
	/** 事件监听器 */
	private final IEventListener<IEvent<?>> eventListener;
	/** 检查数据库连接是否正常的SQL */
	private final static String DB_CHECK_SQL = "select 1";
	/** 检查实体 */
	private final static String DB_CHECK_ENTITY = "from __entityName__";

	/**
	 * 构建一个无事件监听器的HibernateDBServiceImpl
	 * 
	 * @param hibernateCfgXmlUrl
	 *            hibernate.cfg.xml的URL
	 * @param resourceNames
	 *            额外的Hibernate
	 *            Mapping配置文件(如定义查询的配置,以及单独的mapping文件等),这些资源需要在classpath中可以找到
	 */
	public HibernateDBServcieImpl(URL hibernateCfgXmlUrl,String... resourceNames)
	{
		this(null, hibernateCfgXmlUrl, resourceNames);
	}

	/**
	 * 构建一个HibernateDBServiceImpl
	 * 
	 * @param eventListener
	 *            事件监听器,可以为空
	 * @param hibernateCfgXmlUrl
	 *            hibernate.cfg.xml的URL
	 * @param resourceNames
	 *            额外的Hibernate
	 *            Mapping配置文件(如定义查询的配置,以及单独的mapping文件等),这些资源需要在classpath中可以找到
	 */
	public HibernateDBServcieImpl(IEventListener<IEvent<?>> eventListener,URL hibernateCfgXmlUrl, String... resourceNames) 
	{
		Configuration cfg = new AnnotationConfiguration().configure(hibernateCfgXmlUrl);
		if (resourceNames != null)
		{
			for (String _resourceName : resourceNames)
			{
				cfg.addResource(_resourceName);
			}
		}

		try 
		{
			sessionFactory = cfg.buildSessionFactory();
			this.eventListener = eventListener;
		} catch (Exception ex)
		{
			// 记录异常信息
			logger.error("HibernateDBServiceImpl error!", ex);
			// 抛出运行时异常
			throw new RuntimeException(ex);
		}
	}

	@Override
	public <T extends BaseEntity<?>> T get(final Class<T> entityClass,final Serializable id) throws DataAccessException
	{
		return _tranxTmpl.doCall(new HibernateCallback<T>()
		{
			@Override
			public T doCall(Session session)
			{
				return (T) session.get(entityClass, id);
			}
		});
	}

	@Override
	public <T extends BaseEntity<?>> List<T> getAll(final Class<T> entityClazz) throws DataAccessException 
	{

		return this._tranxTmpl.doCall(new HibernateCallback<List<T>>() 
		{
			@Override
			public List<T> doCall(Session sess) 
			{
				if (sess == null)
				{
					return Collections.emptyList();
				}

				// from xxxEntity 
				String hql = "from " + entityClazz.getSimpleName();
				// 创建查询并返回结果
				return sess.createQuery(hql).list();
			}
		});
	}

	@Override
	public Serializable save(final BaseEntity<?> entity)throws DataAccessException
	{
		return _tranxTmpl.doCall(new HibernateCallback<Serializable>() 
		{
			@Override
			public Serializable doCall(Session session)
			{
				return session.save(entity);
			}
		});
	}

	@Override
	public List<Serializable> saveAll(final List<BaseEntity<?>> el)
		throws DataAccessException {

		if (el == null || 
			el.size() <= 0) {
			return Collections.emptyList();
		}

		return this._tranxTmpl.doCall(new HibernateCallback<List<Serializable>>() {
			@Override
			public List<Serializable> doCall(Session sess) {
				if (sess == null) {
					return Collections.emptyList();
				}

				List<Serializable> resultList = new ArrayList<Serializable>();

				for (BaseEntity<?> e : el) {
					// 保存数据库实体并将返回结果添加到列表
					resultList.add(sess.save(e));
				}

				return resultList;
			}
		});
	}

	@Override
	public void update(final BaseEntity<?> entity) throws DataAccessException {
		_tranxTmpl.doCall(new HibernateCallback<Object>() {
			@Override
			public Object doCall(Session session) {
				session.update(entity);
				return null;
			}
		});

	}

	@Override
	public void delete(final BaseEntity<?> entity) throws DataAccessException {
		_tranxTmpl.doCall(new HibernateCallback<Void>() {
			@Override
			public Void doCall(Session session) {
				session.delete(entity);
				return null;
			}
		});
	}

	@Override
	public void deleteById(
		Class<? extends BaseEntity<?>> entityClazz,
		final Serializable ID) {

		final String _className = entityClazz.getSimpleName();
		final String _sql = String.format("delete from %s where id=?",
				_className);
		_tranxTmpl.doCall(new HibernateCallback<Void>() {
			@Override
			public Void doCall(Session session) {
				Query _query = session.createQuery(_sql);
				_query.setParameter(0, ID);
				_query.executeUpdate();
				return null;
			}
		});

	}

	@Override
	public void softDelete(final SoftDeleteEntity<?> entity)
			throws DataAccessException {
		final String _className = entity.getClass().getSimpleName();
		final String _sql = String
				.format(
						"update %s e set e.deleted =?, e.deleteDate = NOW() where e.id=?",
						_className);
		_tranxTmpl.doCall(new HibernateCallback<Void>() {
			@Override
			public Void doCall(Session session) {
				Query _query = session.createQuery(_sql);
				_query.setParameter(0, SoftDeleteEntity.DELETED);
				_query.setParameter(1, entity.getId());
				_query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	public void softDeleteById(
			final Class<? extends SoftDeleteEntity<?>> entityClass,
			final Serializable id) throws DataAccessException {
		final String _className = entityClass.getSimpleName();
		final String _sql = String
				.format(
						"update %s e set e.deleted =?, e.deleteDate = NOW() where e.id=?",
						_className);
		_tranxTmpl.doCall(new HibernateCallback<Void>() {
			@Override
			public Void doCall(Session session) {
				Query _query = session.createQuery(_sql);
				_query.setParameter(0, SoftDeleteEntity.DELETED);
				_query.setParameter(1, id);
				_query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	public <T> List<T> findByNamedQuery(final String queryName)
			throws DataAccessException {
		return findByNamedQueryAndNamedParam(queryName, null, null);
	}

	@Override
	public <T> List<T> findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values)
			throws DataAccessException {
		return findByNamedQueryAndNamedParam(queryName, paramNames, values, -1,
				-1);
	}

	@Override
	public <T> List<T> findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values,
			final int maxResult, final int start) throws DataAccessException {
		if (arrayLength(paramNames) != arrayLength(values)) {
			throw new IllegalArgumentException(
					"The paramNames length != values length");
		}
		return _tranxTmpl.doCall(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doCall(Session session) {
				Query _query = session.getNamedQuery(queryName);
				if (maxResult > -1) {
					_query.setMaxResults(maxResult);
				}
				if (start > -1) {
					_query.setFirstResult(start);
				}
				prepareQuery(paramNames, values, _query);
				return _query.list();
			}
		});
	}

	@Override
	public int queryForUpdate(final String queryName,
			final String[] paramNames, final Object[] values)
			throws DataAccessException {
		if (arrayLength(paramNames) != arrayLength(values)) {
			throw new IllegalArgumentException(
					"The paramNames length != values length");
		}
		return _tranxTmpl.doCall(new HibernateCallback<Integer>() {
			@Override
			public Integer doCall(Session session) {
				Query _query = session.getNamedQuery(queryName);
				prepareQuery(paramNames, values, _query);
				return _query.executeUpdate();
			}
		});
	}

	/**
	 * 直接执行指定的callback方法
	 * 
	 * @param callback
	 * @throws DataAccessException
	 */
	public <T> T call(final HibernateCallback<T> callback)
			throws DataAccessException {
		return _tranxTmpl.doCall(callback);
	}

	@Override
	public void saveOrUpdate(final BaseEntity<?> entity)
			throws DataAccessException {
		this._tranxTmpl.doCall(new HibernateCallback<Void>() {
			@Override
			public Void doCall(Session session) {
				session.saveOrUpdate(entity);
				return null;
			}
		});
	}

	/**
	 * 取得数组的长度
	 * 
	 * @param arrays
	 * @return
	 */
	private int arrayLength(Object[] arrays) {
		return arrays == null ? -1 : arrays.length;
	}

	private void prepareQuery(final String[] paramNames, final Object[] values,
			Query query) {
		for (int i = 0; paramNames != null && i < paramNames.length; i++) {
			if (values[i] instanceof Collection) {
				query.setParameterList(paramNames[i], (Collection<?>)values[i]);
			} else {
				query.setParameter(paramNames[i], values[i]);
			}
		}
	}

	public interface HibernateCallback<T> {
		public T doCall(Session session);
	}

	private interface HibernamteTemplate {
		public <T> T doCall(HibernateCallback<T> callback);
	}

	private final class TranHibernateTemplate implements HibernamteTemplate {
		@Override
		public <T> T doCall(HibernateCallback<T> callback) {
			Session _session = null;
			Transaction _tr = null;
			T _result = null;
			Exception _e = null;
			final long _begin = System.nanoTime();
			try {
				_session = sessionFactory.openSession();
				_tr = _session.beginTransaction();
				_result = callback.doCall(_session);
				_tr.commit();
			} catch (Exception e) {
				_e = e;
				if (_tr != null) {
					_tr.rollback();
				}
				throw new DataAccessException(e);
			} finally {
				try {
					if (_session != null) {
						_session.close();
					}
				} finally {
					// 在这里触发事件通知,避免影响连接的释放
					if (_e != null) {
						try {
							SharedConstants.respondDBTime=SharedConstants.respondDBTime+((System.nanoTime() - _begin) / 1000000);
							if (eventListener != null) {
								eventListener.fireEvent(new DBAccessEvent(
										DBAccessEvent.Type.ERROR, _e
												.getMessage()));
								
							}
						} catch (Exception le) {
							if (logger.isErrorEnabled()) {
								logger.error("Trigger event fail", le);
							}
						}
					} else {
						SharedConstants.respondDBTime=SharedConstants.respondDBTime+((System.nanoTime() - _begin) / 1000000);
					}
				}
			}
			return _result;
		}
	}

	@Override
	public void checkConnection() {
		_tranxTmpl.doCall(new HibernateCallback<List<?>>() {
			@Override
			public List<?> doCall(Session session) {
				Query _query = session.createSQLQuery(DB_CHECK_SQL);
				return _query.list();
			}
		});
	}

	/**
	 * @category 支持动态表查询
	 * @param queryName
	 *            查询名称
	 * @param paramValues
	 *            查询参数
	 * @param resultTypeMapping
	 *            返回值参数类型匹配
	 * @param c
	 *            返回值类型
	 */
	public <T> List<T> dynamicTableQuery(final String queryName,
			final Map<String, String> paramValues,
			final Map<String, NullableType> resultTypeMapping, final Class<?> c) {
		return _tranxTmpl.doCall(new HibernateCallback<List<T>>() {
			@Override
			public List<T> doCall(Session session) {
				SQLQuery sqlQuery = createSqlQuery(queryName, paramValues,
						session);
				if (sqlQuery == null) {
					return null;
				}
				setResultTypeMapping(sqlQuery);
				sqlQuery.setResultTransformer(Transformers.aliasToBean(c));
				return sqlQuery.list();
			}

			private void setResultTypeMapping(SQLQuery sqlQuery) {
				if (resultTypeMapping == null || sqlQuery == null) {
					return;
				}
				Set<String> keySet = resultTypeMapping.keySet();
				for (String key : keySet) {
					sqlQuery.addScalar(key, resultTypeMapping.get(key));
				}
			}
		});
	}

	/** 支持动态表更新 */
	public int dynamicTableUpdate(final String queryName,
			final Map<String, String> paramValues) {
		Integer rs = _tranxTmpl.doCall(new HibernateCallback<Integer>() {
			@Override
			public Integer doCall(Session session) {
				SQLQuery sqlQuery = null;
				sqlQuery = createSqlQuery(queryName, paramValues, session);
				if (sqlQuery == null) {
					return 0;
				}
				return sqlQuery.executeUpdate();
			}
		});
		if (rs == null) {
			return 0;
		}
		return 0;
	}

	/** 支持动态表查询 */
	private SQLQuery createSqlQuery(final String queryName,
			final Map<String, String> paramValues, Session session)
			throws HibernateException {
		Query _query = session.getNamedQuery(queryName);
		String sqlStr = _query.getQueryString();
		sqlStr = constructParam(sqlStr, paramValues);
		SQLQuery sqlQuery = null;
		sqlQuery = session.createSQLQuery(sqlStr);
		return sqlQuery;
	}
	/** 组装原生SQL */
	private String constructParam(String sql,
			final Map<String, String> paramValues) {
		if (paramValues == null) {
			return null;
		}
		Set<String> keySet = paramValues.keySet();
		if (keySet == null) {
			return null;
		}
		for (String tempKey : keySet) {
			if (StringUtils.isEmpty(tempKey)) {
				continue;
			}
			String value = paramValues.get(tempKey);
			if("tableName".equals(tempKey)){
				value = this.toLowerCase(value);
			}
			sql = sql.replace(":" + tempKey, value);
		}
		return sql;
	}

	/** 转换表名为大小写 */
	private String toLowerCase(String upperCase) {
		if (StringUtils.isBlank(upperCase)) {
			return null;
		}
		upperCase = StringUtils.trim(upperCase);
		return StringUtils.lowerCase(upperCase);
	}

	@Override
	public void checkEntityClazz(final Class<? extends BaseEntity<?>> entityClazz)
	{
		if (entityClazz == null)
		{
			throw new IllegalArgumentException("entityClazz is null");
		}

		this._tranxTmpl.doCall(new HibernateCallback<Void>() 
		{
			@Override
			public Void doCall(Session sess)
			{
				if (sess == null) 
				{
					throw new IllegalArgumentException("sess is null");
				}

				String sql = DB_CHECK_ENTITY;
				String entityName = entityClazz.getSimpleName();

				// 替换查询语句中的参数
				sql = sql.replace("__entityName__", entityName);

				// 尝试选取 1 条记录
				sess.createQuery(sql).setMaxResults(1).list();
				return null;
			}
		});
	}
	
	public Long findUniqueResultByNamedQuery(String queryName) throws DataAccessException {
		return findUniqueResultByNamedQueryAndNamedParam(queryName, null, null);
	}
	
	public Long findUniqueResultByNamedQueryAndNamedParam(
			final String queryName, final String[] paramNames,
			final Object[] values) throws DataAccessException {
		if (arrayLength(paramNames) != arrayLength(values)) {
			throw new IllegalArgumentException(
					"The paramNames length != values length");
		}
		return _tranxTmpl.doCall(new HibernateCallback<Long>() {
			@Override
			public Long doCall(Session session) {
				Query _query = session.getNamedQuery(queryName);
				prepareQuery(paramNames, values, _query);
				return (Long) _query.uniqueResult();
			}
		});
	}
}

