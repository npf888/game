package com.gameserver.bazooachieve;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanBazooAchieveDao;
import com.db.model.HumanBazooAchieveEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanBazooAchieveUpdater implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanBazooAchieveDao HumanBazooAchieveDao = Globals.getDaoService().getHumanBazooAchieveDao();
		IIoOperation _oper = new SaveObjectOperation<HumanBazooAchieveEntity,HumanBazooAchieve>((HumanBazooAchieve) obj, HumanBazooAchieveDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}

}
