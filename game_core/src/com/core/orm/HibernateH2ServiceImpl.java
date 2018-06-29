package com.core.orm;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.event.IEvent;
import com.core.event.IEventListener;

/**
 * 基于Hibernate实现的H2Service
 * @author Thinker
 * 
 */
@SuppressWarnings("unchecked")
public class HibernateH2ServiceImpl implements DBService 
{
	private static final Logger logger = LoggerFactory.getLogger("tr.h2cache.hibernate");
	private final SessionFactory sessionFactory;
	private final HibernamteTemplate _tranxTmpl = new TranHibernateTemplate();
	/** 事件监听器 */
	private final IEventListener<IEvent<?>> eventListener;
	/** 检查数据库连接是否正常的SQL */
	private final static String DB_CHECK_SQL = "select 1";
	/** 检查实体 */
	private final static String DB_CHECK_ENTITY = "from __entityName__";

	/**
	 * 构建一个无事件监听器的HibernateH2ServiceImpl
	 * 
	 * @param hibernateCfgXmlUrl
	 *            hibernate.cfg.xml的URL
	 * @param resourceNames
	 *            额外的Hibernate
	 *            Mapping配置文件(如定义查询的配置,以及单独的mapping文件等),这些资源需要在classpath中可以找到
	 */
	public HibernateH2ServiceImpl(URL hibernateCfgXmlUrl,String... resourceNames)
	{
		this(null, hibernateCfgXmlUrl, resourceNames);
	}

	/**
	 * 构建一个HibernateH2ServiceImpl
	 * 
	 * @param eventListener
	 *            事件监听器,可以为空
	 * @param hibernateCfgXmlUrl
	 *            hibernate.cfg.xml的URL
	 * @param resourceNames
	 *            额外的Hibernate
	 *            Mapping配置文件(如定义查询的配置,以及单独的mapping文件等),这些资源需要在classpath中可以找到
	 */
	public HibernateH2ServiceImpl(IEventListener<IEvent<?>> eventListener,URL hibernateCfgXmlUrl, String... resourceNames)
	{
		Configuration cfg = new AnnotationConfiguration().configure(hibernateCfgXmlUrl);
		if (resourceNames != null)
		{
			for (String _resourceName : resourceNames)
			{
				cfg.addResource(_resourceName);
			}
		}
		sessionFactory = cfg.buildSessionFactory();
		this.eventListener = eventListener;
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
	public <T extends BaseEntity<?>> List<T> getAll(final Class<T> entityClazz)throws DataAccessException 
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
				String hql = "from " + entityClazz.getSimpleName();
				// 创建查询并返回结果
				return sess.createQuery(hql).list();
			}
		});
	}

	public void createTable(final BaseEntity<?> entity)throws DataAccessException 
	{
		_tranxTmpl.doCall(new HibernateCallback<Object>() 
		{
			@Override
			public Object doCall(Session session)
			{
				return null;
			}
		});
}

	@Override
	public Serializable save(final BaseEntity<?> entity)
			throws DataAccessException {
		return _tranxTmpl.doCall(new HibernateCallback<Serializable>() {
			@Override
			public Serializable doCall(Session session) {
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
	public void deleteById(Class<? extends BaseEntity<?>> entityClass,
			final Serializable id) {
		final String _className = entityClass.getSimpleName();
		final String _sql = String.format("delete from %s where id=?",
				_className);
		_tranxTmpl.doCall(new HibernateCallback<Void>() {
			@Override
			public Void doCall(Session session) {
				Query _query = session.createQuery(_sql);
				_query.setParameter(0, id);
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
				query.setParameterList(paramNames[i], (Collection<?>) values[i]);
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

	@Override
	public void checkEntityClazz(final Class<? extends BaseEntity<?>> entityClazz) {
		if (entityClazz == null) {
			throw new IllegalArgumentException("entityClazz is null");
		}

		this._tranxTmpl.doCall(new HibernateCallback<Void>() {
			@Override
			public Void doCall(Session sess) {
				if (sess == null) {
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

	@Override
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
