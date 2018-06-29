package com.gameserver.monthcard;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanMonthCardDao;
import com.db.model.HumanMonthCardEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;


/**
 * 月卡
 * @author wayne
 *
 */
public class HumanMonthCardUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanMonthCardDao humanMonthCardDao = Globals.getDaoService().getHumanMonthCardDao();
		IIoOperation _oper = new SaveObjectOperation<HumanMonthCardEntity,HumanMonthCard>((HumanMonthCard) obj, humanMonthCardDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		throw new UnsupportedOperationException();
	}
}
