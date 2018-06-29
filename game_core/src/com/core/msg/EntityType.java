package com.core.msg;

import com.core.orm.BaseEntity;

/**
 * 实体类型定义
 * @author Thinker
 */
public class EntityType<T extends BaseEntity<?>>
{
	public final Class<T> entityClass;
	public final short type;
	public final EntityCreator<T> creator;

	public EntityType(Class<T> entityClass, short type, EntityCreator<T> creator)
	{
		this.creator = creator;
		this.entityClass = entityClass;
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (obj.getClass() != this.getClass()) 
		{
			return false;
		}
		EntityType _e = (EntityType) obj;
		return _e.entityClass == this.entityClass;
	}

	@Override
	public int hashCode()
	{
		return this.entityClass.hashCode();
	}

	/**
	 * 实体类型创建器
	 * 
	 * @author haijiang.jin
	 * 
	 * @param <T>
	 */
	public static interface EntityCreator<T extends BaseEntity<?>> 
	{
		/**
		 * 创建实体对象
		 * 
		 * @return
		 */
		public T createEntity();

		/**
		 * 创建实体对象的消息对象
		 * 
		 * @return
		 */
		public BaseEntityMsg<T> creageEntityMessage();
	}
}
