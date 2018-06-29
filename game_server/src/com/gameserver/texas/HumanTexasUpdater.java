package com.gameserver.texas;


import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanTexasDao;
import com.db.model.HumanTexasEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 德州扑克
 * @author Thinker
 *
 */
public class HumanTexasUpdater  implements POUpdater
{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanTexasDao humanTexasDao = Globals.getDaoService().getHumanTexasDao();
		IIoOperation _oper = new SaveObjectOperation<HumanTexasEntity,HumanTexas>((HumanTexas) obj, humanTexasDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}