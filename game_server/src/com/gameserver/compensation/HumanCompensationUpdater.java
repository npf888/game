package com.gameserver.compensation;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanCompensationDao;
import com.db.model.HumanCompensationEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 用户补偿
 * @author wayne
 *
 */
public class HumanCompensationUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanCompensationDao humanCompensationDao = Globals.getDaoService().getHumanCompensationDao();
		IIoOperation _oper = new SaveObjectOperation<HumanCompensationEntity,HumanCompensation>((HumanCompensation) obj, humanCompensationDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		HumanCompensationDao humanCompensationDao=Globals.getDaoService().getHumanCompensationDao();
		HumanCompensationEntity humanCompensationEntity=(HumanCompensationEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<HumanCompensationEntity>(humanCompensationEntity, humanCompensationEntity.getCharId(), humanCompensationDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}
