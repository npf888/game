package com.gameserver.activity;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanMonthWeekDao;
import com.db.model.HumanMonthWeekEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanMonthWeekUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanMonthWeekDao humanMonthWeekDao = Globals.getDaoService().getHumanMonthWeekDao();
		IIoOperation _oper = new SaveObjectOperation<HumanMonthWeekEntity,HumanMonthWeek>((HumanMonthWeek) obj, humanMonthWeekDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		throw new UnsupportedOperationException();
	}
}
