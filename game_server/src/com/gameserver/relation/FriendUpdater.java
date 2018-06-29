package com.gameserver.relation;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanFriendDao;
import com.db.model.FriendEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 玩家好友更新器
 * @author Thinker
 */
public class FriendUpdater implements POUpdater
{
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		HumanFriendDao friendDao=Globals.getDaoService().getHumanFriendDao();
		IIoOperation _oper = new SaveObjectOperation<FriendEntity,Friend>((Friend) obj, friendDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj)
	{
		HumanFriendDao friendDao=Globals.getDaoService().getHumanFriendDao();
		FriendEntity friendEntity=(FriendEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<FriendEntity>(friendEntity, friendEntity.getCharId(), friendDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}
