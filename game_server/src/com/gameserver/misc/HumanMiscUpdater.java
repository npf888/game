package com.gameserver.misc;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanMiscDao;
import com.db.model.HumanMiscEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * misc updater
 * @author wayne
 *
 */
public class HumanMiscUpdater implements POUpdater {
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanMiscDao humanMiscDao = Globals.getDaoService().getHumanMiscDao();
		IIoOperation _oper = new SaveObjectOperation<HumanMiscEntity,HumanMisc>((HumanMisc) obj, humanMiscDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}

