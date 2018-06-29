package com.gameserver.slot;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.SlotDao;
import com.db.model.SlotEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class SlotUpdater implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		SlotDao slotDao = Globals.getDaoService().getSlotDao();
		IIoOperation _oper = new SaveObjectOperation<SlotEntity,Slot>((Slot) obj, slotDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
