package com.gameserver.player.async;

import com.common.constants.Loggers;
import com.core.util.LogUtils;
import com.db.model.HumanEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.operation.BindUUIDIoOperation;
import com.gameserver.human.Human;
import com.gameserver.player.Player;

/**
 * 异步保存角色信息
 * 
 * @author Thinker
 */
public class FaceBookUpdteOperation implements BindUUIDIoOperation
{
	/** 玩家 */
	private Player player;
	private Human human;
	/** 角色基础信息 */
	private HumanEntity humanEntity = null;


	public FaceBookUpdteOperation(Player player)
	{
		this.player = player;
	}

	@Override
	public int doStart()
	{
		this.human = player.getHuman();
		this.humanEntity = human.toEntity();
		if (human == null)
		{
			// 如果玩家没有角色绑定,则忽略保存,直接返回STATE_IO_DONE,进入下一个阶段
			return STAGE_IO_DONE;
		}
		return STAGE_START_DONE;
	}

	@Override
	public int doIo()
	{
		do
		{
			// 保存玩家的基本数据到数据库
			try
			{
				if (this.humanEntity != null)
				{
					Globals.getDaoService().getHumanDao().update(humanEntity);
				}
				
			} catch (Exception e)
			{
				Loggers.playerLogger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(), "Save facebook info error."), e);
			}

		} while (false);
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() 
	{
		return STAGE_STOP_DONE;
	}

	@Override
	public long getBindUUID()
	{
		return this.player.getRoleUUID();
	}
}
