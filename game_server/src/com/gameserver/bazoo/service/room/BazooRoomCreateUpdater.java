package com.gameserver.bazoo.service.room;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.BazooRoomCreateDao;
import com.db.model.BazooRoomCreateEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class BazooRoomCreateUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		BazooRoomCreateDao BazooRoomCreateDao = Globals.getDaoService().getBazooRoomCreateDao();
		IIoOperation _oper = new SaveObjectOperation<BazooRoomCreateEntity,BazooRoomCreate>((BazooRoomCreate) obj, BazooRoomCreateDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		BazooRoomCreateDao BazooRoomCreateDao=Globals.getDaoService().getBazooRoomCreateDao();
		BazooRoomCreateEntity BazooRoomCreateEntity=(BazooRoomCreateEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<BazooRoomCreateEntity>(BazooRoomCreateEntity, 0L, BazooRoomCreateDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
