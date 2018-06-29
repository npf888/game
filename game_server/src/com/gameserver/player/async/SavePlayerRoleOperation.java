package com.gameserver.player.async;

import com.common.LogReasons.OnlineTimeLogReason;
import com.common.constants.Loggers;
import com.core.util.LogUtils;
import com.core.util.TimeUtils;
import com.db.model.HumanEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.operation.BindUUIDIoOperation;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerConstants;

/**
 * 异步保存角色信息
 * 
 * @author Thinker
 */
public class SavePlayerRoleOperation implements BindUUIDIoOperation
{
	/** 玩家 */
	private Player player;
	/** 保存信息 Mask */
	private int mask;
	private boolean isLogoutSaving;
	private Human human;
	/** 角色基础信息 */
	private HumanEntity humanEntity = null;


	public SavePlayerRoleOperation(Player player,int mask,boolean isLogoutSaving)
	{
		this.player = player;
		this.mask = mask;
		this.isLogoutSaving = isLogoutSaving;
	}

	@Override
	public int doStart()
	{
		this.human = player.getHuman();
		if (human == null)
		{
			// 如果玩家没有角色绑定,则忽略保存,直接返回STATE_IO_DONE,进入下一个阶段
			return STAGE_IO_DONE;
		}
		try
		{
			if (isLogoutSaving) 
			{
				human.logLogout(OnlineTimeLogReason.DEFAULT);
			}
			if ((mask & PlayerConstants.CHARACTER_INFO_MASK_BASE) != 0) 
			{
				this.humanEntity = human.toEntity();
			}
		
		} catch (Exception ex)
		{
			long roleUUID = player.getRoleUUID();
			long passportID = 0L;
			long dbID = 0L;

			if (humanEntity != null)
			{
				passportID = humanEntity.getPassportId();
				dbID = humanEntity.getId();
			}
			// 错误日志
			String errmsg = "Conventer error. pid=" + passportID + ",cid=" + dbID;
			// 记录错误日志
			Loggers.playerLogger.error(LogUtils.buildLogInfoStr(roleUUID, errmsg), ex);
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
				
				if (isLogoutSaving) 
				{
					long lastLoginTime = human.getLastLoginTime();
					if(lastLoginTime != 0)
					{
						// 如果跨天，对管理开始时间清零，当日历史累计在线时长清零，重新计算提醒时间
						long now = Globals.getTimeService().now();
						if (!TimeUtils.isSameDay(human.getLastLoginTime(), now))
						{
							int time = (int) (now - TimeUtils.getTodayBegin(Globals.getTimeService())) / 1000 / 60;
							
							this.player.setTodayOnlineTime(time);
						} else 
						{
							this.player.setTodayOnlineTime((int) (now - human.getLastLoginTime()) / 1000 / 60);
						}
	
					}
				}
			} catch (Exception e)
			{
				Loggers.playerLogger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(), "Save character base info error."), e);
			}

		} while (false);
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() 
	{
		if (isLogoutSaving)
		{
			Globals.getOnlinePlayerService().removeSession(player.getSession());
			Globals.getOnlinePlayerService().logoutedState(player);
			Loggers.playerLogger.info("PlayerOffline.passportId[" + player.getPassportId() + "].SavePlayerRoleOperation doStop");
		}
		return STAGE_STOP_DONE;
	}

	@Override
	public long getBindUUID()
	{
		return this.player.getRoleUUID();
	}
}
