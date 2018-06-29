package com.gameserver.worldboss.pojo;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.BossDao;
import com.db.model.BossEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 任务更新
 * @author wayne
 *
 */
public class BossUpdater implements POUpdater{
	
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		BossDao bossDao=Globals.getDaoService().getBossDao();
		IIoOperation _oper = new SaveObjectOperation<BossEntity,Boss>((Boss) obj, bossDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
