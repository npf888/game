package com.gameserver.bazoonewguy;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanBazooNewGuyDao;
import com.db.model.HumanBazooNewGuyEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanBazooNewGuyUpdater implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanBazooNewGuyDao humanBazooNewGuyDao = Globals.getDaoService().gethumanBazooNewGuyDao();
		IIoOperation _oper = new SaveObjectOperation<HumanBazooNewGuyEntity,HumanBazooNewGuy>((HumanBazooNewGuy) obj, humanBazooNewGuyDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}

}
