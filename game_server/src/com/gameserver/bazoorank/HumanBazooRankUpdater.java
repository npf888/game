package com.gameserver.bazoorank;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanBazooRankDao;
import com.db.model.HumanBazooRankEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanBazooRankUpdater  implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanBazooRankDao bazooRankDao = Globals.getDaoService().getBazooRankDao();
		IIoOperation _oper = new SaveObjectOperation<HumanBazooRankEntity,HumanBazooRank>((HumanBazooRank) obj, bazooRankDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
