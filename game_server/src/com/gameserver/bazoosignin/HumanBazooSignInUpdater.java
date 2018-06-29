package com.gameserver.bazoosignin;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanBazooSigninDao;
import com.db.model.HumanBazooSigninEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanBazooSignInUpdater implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanBazooSigninDao humanBazooSigninDao = Globals.getDaoService().getHumanBazooSigninDao();
		IIoOperation _oper = new SaveObjectOperation<HumanBazooSigninEntity,HumanBazooSignIn>((HumanBazooSignIn) obj, humanBazooSigninDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
