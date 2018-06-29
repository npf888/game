package com.gameserver.bazootask;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanBazooTaskDao;
import com.db.model.HumanBazooTaskEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanBazooTaskUpdater implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanBazooTaskDao HumanBazooTaskDao = Globals.getDaoService().getHumanBazooTaskDao();
		IIoOperation _oper = new SaveObjectOperation<HumanBazooTaskEntity,HumanBazooTask>((HumanBazooTask) obj, HumanBazooTaskDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
