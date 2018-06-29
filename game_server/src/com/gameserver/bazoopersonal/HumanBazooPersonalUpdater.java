package com.gameserver.bazoopersonal;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanBazooPersonalDao;
import com.db.model.HumanBazooPersonalEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanBazooPersonalUpdater implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanBazooPersonalDao humanBazooPersonalDao = Globals.getDaoService().getHumanBazooPersonalDao();
		IIoOperation _oper = new SaveObjectOperation<HumanBazooPersonalEntity,HumanBazooPersonal>((HumanBazooPersonal) obj, humanBazooPersonalDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
