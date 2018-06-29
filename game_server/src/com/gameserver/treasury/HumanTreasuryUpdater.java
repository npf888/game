package com.gameserver.treasury;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanTreasuryDao;
import com.db.model.HumanTreasuryEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanTreasuryUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanTreasuryDao humanTreasuryDao = Globals.getDaoService().getHumanTreasuryDao();
		IIoOperation _oper = new SaveObjectOperation<HumanTreasuryEntity,HumanTreasury>((HumanTreasury) obj, humanTreasuryDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) {
		// TODO Auto-generated method stub
		HumanTreasuryDao humanTreasuryDao =Globals.getDaoService().getHumanTreasuryDao();
		HumanTreasuryEntity humanTreasuryEntity=(HumanTreasuryEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<HumanTreasuryEntity>(humanTreasuryEntity, humanTreasuryEntity.getChartId(), humanTreasuryDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}
