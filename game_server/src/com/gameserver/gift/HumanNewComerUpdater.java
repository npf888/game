package com.gameserver.gift;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanNewComerDao;
import com.db.model.HumanNewComerEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanNewComerUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		
		HumanNewComerDao HumanNewComerDao = Globals.getDaoService().getHumanNewComerDao();
		IIoOperation _oper = new SaveObjectOperation<HumanNewComerEntity,HumanNewComer>((HumanNewComer) obj, HumanNewComerDao);
		
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);

	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		throw new UnsupportedOperationException();
	}

}
