package com.gameserver.vipnew;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanVipNewDao;
import com.db.model.HumanVipNewEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanVipNewUpdater implements POUpdater {

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		
		HumanVipNewDao humanVipNewDao = Globals.getDaoService().getVipNewDao();
		IIoOperation _oper = new SaveObjectOperation<HumanVipNewEntity,HumanVipNew>((HumanVipNew) obj, humanVipNewDao);
		
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);

	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
	
		throw new UnsupportedOperationException();

	}

}
