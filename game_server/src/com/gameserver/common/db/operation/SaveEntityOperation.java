package com.gameserver.common.db.operation;

import org.slf4j.Logger;

import com.common.constants.CommonErrorLogInfo;
import com.common.constants.Loggers;
import com.core.async.IIoOperation;
import com.core.orm.BaseEntity;
import com.core.orm.DataAccessException;
import com.core.util.ErrorsUtil;
import com.db.dao.BaseDao;

/**
 * 保存实体操作
 * @author Thinker
 *
 * @param <E>
 */
public class SaveEntityOperation<E extends BaseEntity<?>> implements IIoOperation
{
	private static final Logger logger = Loggers.errorLogger;
	
	/** 实体对象 */
	private E entity;
	private final BaseDao<E> dao;
	
	/** 是否执行插入操作,如果为false,则执行更新操作 */
	protected boolean isInDb = true;
	
	public SaveEntityOperation( BaseDao<E> dao, E entity, boolean isInDb)
	{
		this.dao = dao;
		this.entity = entity;
		this.isInDb = isInDb;
	}
	
	@Override
	public int doStart() 
	{
		return STAGE_START_DONE;
	}

	@Override
	public int doIo() 
	{
		// 保存到数据库
		try 
		{
			if (!isInDb)
			{
				dao.save(entity);
			} else
			{
				dao.update(entity);
			}
		} catch (DataAccessException e)
		{
			// 如果是保存操作,说明该对象未保存成功,设置inDb为false,尝试下次保存
			if (isInDb)
			{
				
			}
			if (logger.isErrorEnabled())
			{
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.DB_OPERATE_FAIL,"#GS.SaveObjectOperation.doIo", null), e);
			}
			return STAGE_STOP_DONE;
		}
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop()
	{
		return STAGE_STOP_DONE;
	}
}
