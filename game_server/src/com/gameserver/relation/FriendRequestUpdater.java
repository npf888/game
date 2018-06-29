package com.gameserver.relation;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanFriendRequestDao;
import com.db.model.FriendRequestEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;

/**
 * 玩家好友更新器
 * @author Thinker
 */
public class FriendRequestUpdater implements POUpdater
{
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		HumanFriendRequestDao friendRequestDao=Globals.getDaoService().getHumanFriendRequestDao();
		IIoOperation _oper = new SaveObjectOperation<FriendRequestEntity,FriendRequest>((FriendRequest) obj, friendRequestDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj)
	{
		HumanFriendRequestDao friendRequestDao=Globals.getDaoService().getHumanFriendRequestDao();
		FriendRequestEntity friendRequestEntity=(FriendRequestEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<FriendRequestEntity>(friendRequestEntity, friendRequestEntity.getCharId(), friendRequestDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}
