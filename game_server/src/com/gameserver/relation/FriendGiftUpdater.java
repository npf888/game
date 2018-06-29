package com.gameserver.relation;

import com.core.async.IIoOperation;
import com.core.object.PersistanceObject;
import com.db.dao.HumanFriendGiftDao;
import com.db.model.HumanFriendGiftEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.common.db.operation.DeleteEntityOperation;
import com.gameserver.common.db.operation.SaveObjectOperation;


/**
 * 玩家好友更新器
 * @author Thinker
 */
public class FriendGiftUpdater implements POUpdater
{
	@Override
	public void save(PersistanceObject<?, ?> obj)
	{
		HumanFriendGiftDao friendGiftDao=Globals.getDaoService().getHumanFriendGiftDao();
		IIoOperation _oper = new SaveObjectOperation<HumanFriendGiftEntity,FriendGift>((FriendGift) obj, friendGiftDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}

	@Override
	public void delete(PersistanceObject<?, ?> obj)
	{
		HumanFriendGiftDao friendGiftDao=Globals.getDaoService().getHumanFriendGiftDao();
		HumanFriendGiftEntity humanFriendGiftEntity=(HumanFriendGiftEntity)obj.toEntity();
		IIoOperation _oper = new DeleteEntityOperation<HumanFriendGiftEntity>(humanFriendGiftEntity, humanFriendGiftEntity.getCharId(), friendGiftDao);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_oper);
	}
}
