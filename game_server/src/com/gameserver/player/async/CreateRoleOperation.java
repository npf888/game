package com.gameserver.player.async;

import com.core.async.IIoOperation;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerState;
import com.gameserver.player.data.RoleInfo;



/**
 * 异步IO操作： 保存一个新角色
 * 
 * @author Thinker
 */
public class CreateRoleOperation implements IIoOperation 
{
	/** 玩家 */
	private Player player;
	/** 角色 */
	private RoleInfo roleInfo;
	/** 是否创建成功 */
	private boolean isCreateSucc = false;

	public CreateRoleOperation(Player player, RoleInfo roleInfo)
	{
		this.player = player;
		this.roleInfo = roleInfo;
	}

	@Override
	public int doIo()
	{
		do 
		{
			if (!player.isOnline()) break;
			
			// 保存到数据库
			isCreateSucc = Globals.getOnlinePlayerService().createRole(player, roleInfo);
		} while (false);
		return STAGE_IO_DONE;
	}

	@Override
	public int doStart()
	{
		return STAGE_START_DONE;
	}

	@Override
	public int doStop() 
	{
		if (player.getState() == PlayerState.creatingrole)
		{
			if (isCreateSucc) 
			{
				player.getRoles().add(roleInfo);//增加新的角色
				player.setState(PlayerState.loadingrolelist);
				player.setState(PlayerState.waitingselectrole);
			
				Globals.getLoginLogicalProcessor().selectRole(player, roleInfo.getRoleUUID());
				
			} else
			{
				player.setState(PlayerState.waitingselectrole);
			}
		}
		return STAGE_STOP_DONE;
	}
}
