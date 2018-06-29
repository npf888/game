package com.gameserver.startup;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.common.constants.Loggers;
import com.core.object.LifeCycle;
import com.core.object.PersistanceObject;
import com.core.orm.BaseEntity;
import com.core.persistance.DataUpdater.UpdateEntry;
import com.db.dao.BaseDao;
import com.gameserver.common.Globals;
import com.gameserver.common.db.GameDaoService;
import com.gameserver.human.Human;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerConstants;
import com.gameserver.player.async.SavePlayerRoleOperation;
import com.gameserver.player.persistance.PlayerDataUpdater;

/**
 * 
 * gameserver关闭时的一些逻辑处理
 * @author Thinker
 * 
 */
public class ServerShutdownService
{
	/**
	 * 同步的将所有在线玩家的PlayerDataUpdater中所有待更新的数据全部同步更新一遍<br/>
	 * 此操作在关闭SceneTaskScheduler之后做，保证所有场景已经停止tick
	 */
	public void synSaveAllPlayerOnShutdown()
	{
		OnlinePlayerService olPlayerService = Globals.getOnlinePlayerService();
		synSavePlayerDataUpdater(olPlayerService);
		synSavePlayerBaseData(olPlayerService);
	}

	/**
	 * 同步保存每一个在线玩家的PlayerDataUpdater中的所有尚未保存数据
	 */
	private void synSavePlayerDataUpdater(OnlinePlayerService olPlayerService)
	{
		// 同步保存每一个在线玩家的PlayerDataUpdater中的所有为保存数据
		Collection<Long> allOnlines = olPlayerService.getAllOnlinePlayerRoleUUIDs();
		for (Long roleUUID : allOnlines)
		{
			Player player = Globals.getOnlinePlayerService().getPlayer(roleUUID);
			if (player == null)
			{
				continue;
			}
			PlayerDataUpdater updater = player.getDataUpdater();
			Map<Object, UpdateEntry> changedMap = updater.getChangedObjects();
			if (changedMap.isEmpty())
			{
				continue;
			}
			for (UpdateEntry entry : changedMap.values())
			{
				LifeCycle lc = entry.obj;
				try
				{
					if (entry.operation == UpdateEntry.OPER_UPDATE) 
					{
						// 执行更新操作
						synUpdateOrSave(lc.getPO());
					} else if (entry.operation == UpdateEntry.OPER_DEL)
					{
						// 执行删除操作
						synDelete(lc.getPO());
					}
				} catch (Exception e) 
				{
					Loggers.gameLogger.error(
							"exception occur when server shutdown", e);
				}
			}
		}
	}

	private static enum Oper
	{
		save 
		{
			@Override
			public <I extends Serializable, T extends BaseEntity<I>> void execute(PersistanceObject<I, T> po, BaseDao<T> dao)
			{
				dao.save(po.toEntity());
			}
		},
		update
		{
			@Override
			public <I extends Serializable, T extends BaseEntity<I>> void execute(PersistanceObject<I, T> po, BaseDao<T> dao)
			{
				dao.update(po.toEntity());
			}
		},
		delete
		{
			@Override
			public <I extends Serializable, T extends BaseEntity<I>> void execute(PersistanceObject<I, T> po, BaseDao<T> dao)
			{
				dao.delete(po.toEntity());
			}
		};

		public abstract <I extends Serializable, T extends BaseEntity<I>> void execute(PersistanceObject<I, T> po, BaseDao<T> dao);
	}


	private void synUpdateOrSave(PersistanceObject<?,?>  po)
	{
		boolean save = !(po.isInDb());
		if (save)
		{
			doOperByType(po, Oper.save);
		} else
		{
			doOperByType(po, Oper.update);
		}
	}

	private void synDelete(PersistanceObject<?,?> po)
	{
		doOperByType(po, Oper.delete);
	}

	private void doOperByType(PersistanceObject<?,?> po, Oper oper)
	{
		GameDaoService daoService = Globals.getDaoService();
		if (po instanceof Human) 
		{
			Human human = (Human) po;
			oper.execute(human, daoService.getHumanDao());
		} else 
		{
			Loggers.gameLogger.error("停服同步存库时漏掉了Entity类型："+ po.getClass().getName());
		}
	}

	/**
	 * 关服时同步保存每一个在线玩家的基本数据，即HumanEntity中的数据
	 */
	private void synSavePlayerBaseData(OnlinePlayerService olPlayerService)
	{
		Collection<Long> allOnlines = olPlayerService.getAllOnlinePlayerRoleUUIDs();
		for (Long roleUUID : allOnlines)
		{
			Player player = Globals.getOnlinePlayerService().getPlayer(roleUUID);
			SavePlayerRoleOperation operation = new SavePlayerRoleOperation(
					player, PlayerConstants.CHARACTER_INFO_MASK_BASE, true);
			operation.doStart();
			operation.doIo();
		}
	}
}
