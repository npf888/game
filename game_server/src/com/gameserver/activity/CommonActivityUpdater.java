package com.gameserver.activity;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.CommonActivityDao;
import com.db.model.CommonActivityEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class CommonActivityUpdater  implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		CommonActivityDao commonActivityDao = Globals.getDaoService().getCommonActivityDao();
		IIoOperation _oper = new SaveObjectOperation<CommonActivityEntity,CommonActivity>((CommonActivity) obj, commonActivityDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		CommonActivityDao commonActivityDao = Globals.getDaoService().getCommonActivityDao();
		CommonActivityEntity commonActivityEntity=(CommonActivityEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<CommonActivityEntity>(commonActivityEntity, 0L, commonActivityDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
