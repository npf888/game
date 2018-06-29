package com.gameserver.baccart;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.BaccartRoomModelDao;
import com.db.model.BaccartRoomModelEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class BaccartRoomModelUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		BaccartRoomModelDao baccartRoomModelDao = Globals.getDaoService().getBaccartRoomModelDao();
		IIoOperation _oper = new SaveObjectOperation<BaccartRoomModelEntity,BaccartRoomModel>((BaccartRoomModel) obj, baccartRoomModelDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
