package com.gameserver.function;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.FunctionDao;
import com.db.model.CommonActivityEntity;
import com.db.model.FunctionEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class FunctionUpdater  implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		FunctionDao functionDao = Globals.getDaoService().getFunctionDao();
		IIoOperation _oper = new SaveObjectOperation<FunctionEntity,Function>((Function) obj, functionDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		FunctionDao functionDao = Globals.getDaoService().getFunctionDao();
		FunctionEntity functionEntity=(FunctionEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<FunctionEntity>(functionEntity, 0L, functionDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
