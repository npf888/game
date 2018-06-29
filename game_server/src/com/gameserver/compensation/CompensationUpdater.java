package com.gameserver.compensation;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.CompensationDao;
import com.db.model.CompensationEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 补偿更新
 * @author wayne
 *
 */
public class CompensationUpdater  implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		CompensationDao compensationDao = Globals.getDaoService().getCompensationDao();
		IIoOperation _oper = new SaveObjectOperation<CompensationEntity,Compensation>((Compensation) obj, compensationDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		CompensationDao compensationDao=Globals.getDaoService().getCompensationDao();
		CompensationEntity compensationEntity=(CompensationEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<CompensationEntity>(compensationEntity, 0L, compensationDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	
	}

}
