package com.gameserver.human;

import com.core.object.PersistanceObject;
import com.gameserver.common.Globals;
import com.gameserver.common.db.POUpdater;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerConstants;
import com.gameserver.player.async.SavePlayerRoleOperation;

/**
 * 玩家角色基本信息更新器
 * @author Thinker
 */
public class HumanUpdater implements POUpdater
{
	@Override
	public void save(PersistanceObject<?,?>  obj)
	{
		final Human human = (Human) obj;
		if (human == null) return;
		Player player = human.getPlayer();
		if (player == null) return;
		
		SavePlayerRoleOperation saveTask = new SavePlayerRoleOperation(player,PlayerConstants.CHARACTER_INFO_MASK_BASE,false);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(saveTask);
	}

	@Override
	public void delete(PersistanceObject<?,?>  obj)
	{
		throw new UnsupportedOperationException();
	}
}
