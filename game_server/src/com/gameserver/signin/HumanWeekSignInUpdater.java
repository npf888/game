package com.gameserver.signin;


import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanWeekSignInDao;
import com.db.model.HumanWeekSignInEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 签到
 * @author Thinker
 *
 */
public class HumanWeekSignInUpdater  implements POUpdater
{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanWeekSignInDao humanWeekSignInDao = Globals.getDaoService().getHumanWeekSignInDao();
		IIoOperation _oper = new SaveObjectOperation<HumanWeekSignInEntity,HumanWeekSignIn>((HumanWeekSignIn) obj, humanWeekSignInDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}