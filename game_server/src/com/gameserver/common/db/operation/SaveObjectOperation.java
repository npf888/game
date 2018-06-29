package com.gameserver.common.db.operation;

import org.slf4j.Logger;

import com.common.constants.CommonErrorLogInfo;
import com.common.constants.Loggers;
import com.core.object.PersistanceObject;
import com.core.orm.BaseEntity;
import com.core.orm.DataAccessException;
import com.core.util.ErrorsUtil;
import com.db.dao.BaseDao;
import com.gameserver.weixin.util.WeixinUtil;

/**
 * 保存主键是UUID的对象数据到数据库中
 * @author Thinker
 * 
 */
public class SaveObjectOperation<E extends BaseEntity<?>, P extends PersistanceObject<?, E>> implements BindUUIDIoOperation
{
	private static final Logger logger = Loggers.errorLogger;

	/** 业务对象 */
	protected final P persistObject;

	/** 实体对象 */
	private E entity;

	/** 是否执行插入操作,如果为false,则执行更新操作 */
	protected boolean save = true;

	private final BaseDao<E> dao;


	public SaveObjectOperation(P persistObject, BaseDao<E> dao) 
	{
		this.persistObject = persistObject;
		this.dao = dao;
	}

	@Override
	public int doStart()
	{
		if (persistObject.getDbId() == null)
		{
			// dbId为空时,程序中有bug?停止保存
			if (logger.isErrorEnabled())
			{
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.DB_NO_ID,"#GS.SaveObjectOperation.doStart",
						"The db id must be set before save or update."),new IllegalStateException());
			}
			return STAGE_STOP_DONE;
		}
		save = !(persistObject.isInDb());
		persistObject.setInDb(true);
		entity = persistObject.toEntity();
		return STAGE_START_DONE;
	}

	@Override
	public int doIo()
	{
		// 保存到数据库
		try 
		{
			if (save)
			{
				dao.save(entity);
			} else
			{
				dao.update(entity);
			}
		} catch (DataAccessException e)
		{
//			WeixinUtil.sendMessage("数据库存储异常："+e.getMessage()+";;"+e.getLocalizedMessage());
			// 如果是保存操作,说明该对象未保存成功,设置inDb为false,尝试下次保存
			if (save) 
			{
				persistObject.setInDb(false);
			}
			if (logger.isErrorEnabled())
			{
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.DB_OPERATE_FAIL,
						"#GS.SaveObjectOperation.doIo", null), e);
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

	@Override
	public long getBindUUID() 
	{
		return this.persistObject.getCharId();
	}
}
