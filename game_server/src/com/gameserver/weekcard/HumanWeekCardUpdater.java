package com.gameserver.weekcard;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanWeekCardDao;
import com.db.model.HumanWeekCardEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;


/**
 * 周卡
 * @author wayne
 *
 */
public class HumanWeekCardUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanWeekCardDao humanWeekCardDao = Globals.getDaoService().getHumanWeekCardDao();
		IIoOperation _oper = new SaveObjectOperation<HumanWeekCardEntity,HumanWeekCard>((HumanWeekCard) obj, humanWeekCardDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
