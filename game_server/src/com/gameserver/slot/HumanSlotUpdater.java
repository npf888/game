package com.gameserver.slot;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanSlotDao;
import com.db.model.HumanSlotEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanSlotUpdater implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		HumanSlotDao humanSlotDao = Globals.getDaoService().getHumanSlotDao();
		IIoOperation _oper = new SaveObjectOperation<HumanSlotEntity,HumanSlot>((HumanSlot) obj, humanSlotDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		throw new UnsupportedOperationException();
	}

}
