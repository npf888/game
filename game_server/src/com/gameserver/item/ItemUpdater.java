package com.gameserver.item;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanItemDao;
import com.db.model.HumanItemEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 道具更新
 * @author wayne
 *
 */
public class ItemUpdater implements POUpdater{
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		HumanItemDao itemDao=Globals.getDaoService().getHumanItemDao();
		IIoOperation _oper = new SaveObjectOperation<HumanItemEntity,Item>((Item) obj, itemDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj) 
	{
		HumanItemDao itemDao=Globals.getDaoService().getHumanItemDao();
		HumanItemEntity humanItemEntity=(HumanItemEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<HumanItemEntity>(humanItemEntity, humanItemEntity.getCharId(), itemDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}

