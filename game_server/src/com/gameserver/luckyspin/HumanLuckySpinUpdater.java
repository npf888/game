package com.gameserver.luckyspin;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanLuckySpinDao;
import com.db.model.HumanLuckySpinEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 邮件更新
 * @author wayne
 *
 */
public class HumanLuckySpinUpdater implements POUpdater
{
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		HumanLuckySpinDao luckySpinDao=Globals.getDaoService().getHumanLuckySpinDao();
		IIoOperation _oper = new SaveObjectOperation<HumanLuckySpinEntity,HumanLuckySpin>((HumanLuckySpin) obj, luckySpinDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}