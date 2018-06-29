package com.gameserver.task;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanTaskDao;
import com.db.model.HumanTaskEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 任务更新
 * @author wayne
 *
 */
public class HumanTaskUpdater implements POUpdater{
	
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		HumanTaskDao humanTaskDao=Globals.getDaoService().getHumanTaskDao();
		IIoOperation _oper = new SaveObjectOperation<HumanTaskEntity,HumanTask>((HumanTask) obj, humanTaskDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
