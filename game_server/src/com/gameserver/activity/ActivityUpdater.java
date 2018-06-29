package com.gameserver.activity;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.ActivityDao;
import com.db.model.ActivityEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class ActivityUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		ActivityDao activityDao = Globals.getDaoService().getActivityDao();
		IIoOperation _oper = new SaveObjectOperation<ActivityEntity,Activity>((Activity) obj, activityDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		ActivityDao activityDao=Globals.getDaoService().getActivityDao();
		ActivityEntity activityEntity=(ActivityEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<ActivityEntity>(activityEntity, 0L, activityDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}

