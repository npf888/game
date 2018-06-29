package com.gameserver.guide.pojo;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanPayguideDao;
import com.db.model.HumanPayguideEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanPayguideUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		HumanPayguideDao humanPayguideDao = Globals.getDaoService().getHumanPayguideDao();
		IIoOperation _oper = new SaveObjectOperation<HumanPayguideEntity,HumanPayguide>((HumanPayguide) obj, humanPayguideDao);
		
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
		
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		throw new UnsupportedOperationException();
		
	}

}
