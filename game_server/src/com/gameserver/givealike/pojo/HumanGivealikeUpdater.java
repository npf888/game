package com.gameserver.givealike.pojo;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanGivealikeDao;
import com.db.model.HumanGivealikeEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanGivealikeUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		HumanGivealikeDao humanGivealikeDao = Globals.getDaoService().getHumanGivealikeDao();
		IIoOperation _oper = new SaveObjectOperation<HumanGivealikeEntity,HumanGivealike>((HumanGivealike) obj, humanGivealikeDao);
		
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
		
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		throw new UnsupportedOperationException();
		
	}

}
