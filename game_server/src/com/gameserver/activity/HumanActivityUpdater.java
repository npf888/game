package com.gameserver.activity;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanActivityDao;
import com.db.model.HumanActivityEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanActivityUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanActivityDao humanActivityDao = Globals.getDaoService().getHumanActivityDao();
		IIoOperation _oper = new SaveObjectOperation<HumanActivityEntity,HumanActivity>((HumanActivity) obj, humanActivityDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		HumanActivityDao humanActivityDao=Globals.getDaoService().getHumanActivityDao();
		HumanActivityEntity humanActivityEntity=(HumanActivityEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<HumanActivityEntity>(humanActivityEntity, humanActivityEntity.getCharId(), humanActivityDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}

