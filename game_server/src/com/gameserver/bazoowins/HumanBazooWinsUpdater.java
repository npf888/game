package com.gameserver.bazoowins;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanBazooWinsDao;
import com.db.model.HumanActivityEntity;
import com.db.model.HumanBazooWinsEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

public class HumanBazooWinsUpdater implements POUpdater{

	@Override
	public void save(PersistanceObject<?, ?> obj) 
	{
		HumanBazooWinsDao HumanBazooWinsDao = Globals.getDaoService().getHumanBazooWinsDao();
		IIoOperation _oper = new SaveObjectOperation<HumanBazooWinsEntity,HumanBazooWins>((HumanBazooWins) obj, HumanBazooWinsDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		HumanBazooWinsDao HumanBazooWinsDao=Globals.getDaoService().getHumanBazooWinsDao();
		HumanBazooWinsEntity HumanBazooWinsEntity=(HumanBazooWinsEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<HumanBazooWinsEntity>(HumanBazooWinsEntity, HumanBazooWinsEntity.getPassportId(), HumanBazooWinsDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

}
