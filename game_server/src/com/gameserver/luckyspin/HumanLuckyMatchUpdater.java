package com.gameserver.luckyspin;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanLuckyMatchDao;
import com.db.model.HumanLuckyMatchEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 邮件更新
 * @author wayne
 *
 */

public class HumanLuckyMatchUpdater implements POUpdater
{
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		HumanLuckyMatchDao luckyMatchDao=Globals.getDaoService().getHumanLuckyMatchDao();
		IIoOperation _oper = new SaveObjectOperation<HumanLuckyMatchEntity,HumanLuckyMatch>((HumanLuckyMatch) obj, luckyMatchDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}