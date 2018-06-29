package com.gameserver.player.async;

import java.util.List;

import org.slf4j.Logger;

import com.common.constants.DisconnectReason;
import com.common.constants.Loggers;
import com.common.constants.RoleConstants;
import com.core.async.IIoOperation;
import com.core.util.LogUtils;
import com.core.util.StringUtils;
import com.db.model.HumanEntity;
import com.db.model.HumanVipNewEntity;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerExitReason;
import com.gameserver.player.PlayerState;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.player.data.RoleInfo;
import com.gameserver.player.msg.GCNotifyException;


/**
 * 加载玩家的角色列表
 * @author Thinker
 */
public class PlayerRolesLoad implements IIoOperation
{
	private static final Logger logger = Loggers.playerLogger;
	private final Player player;
	private final long passportId;
	private boolean isOperateSucc = false;



	public PlayerRolesLoad(Player player){
		this.player = player;
		this.passportId = player.getPassportId();
	}

	@Override
	public int doIo()
	{
		do{
			try{
				//只有在玩家处理于连接状态的时候才加载他的角色列表
				if(!player.isOnline())  break;
				List<RoleInfo> roles = Globals.getOnlinePlayerService().loadPlayerRoleList(passportId);
				player.setRoles(roles);
				isOperateSucc = true;
			} catch (Exception e){
				isOperateSucc = false;
				if (logger.isErrorEnabled()){
					Loggers.playerLogger.error(LogUtils.buildLogInfoStr(passportId, "load character error"), e);
				}
			}
		} while (false);
		return STAGE_IO_DONE;
	}

	@Override
	public int doStart(){
		return STAGE_START_DONE;
	}

	@Override
	public int doStop(){
		if (isOperateSucc){
			if(player.getRoles().size()==0){
				//自动创建角色  并把角色（human 放到 player 中  ，player也放到 human中）
				RoleInfo role = new RoleInfo();
				role.setPassportId(player.getPassportId());
				role.setName(player.getPassportName());
				role.setLevel(RoleConstants.HUMAN_INIT_LEVEL_NUM);
				Globals.getLoginLogicalProcessor().createRole(player, role);
			}else{
				//发送加载完毕信息    不用创建角色（因为角色已经创建），然后（human 放到 player 中  ，player也放到 human中）
				player.setState(PlayerState.loadingrolelist);
				player.setState(PlayerState.waitingselectrole);
				RoleInfo roleInfo = player.getRoles().get(0);
				Globals.getLoginLogicalProcessor().selectRole(player, roleInfo.getRoleUUID());
			}
			
			//更新 redis 
			List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(player.getPassportId());
			if(humanEntityList.size()>0){  
				PlayerCacheInfo playerCacheInfo= PlayerCacheInfo.playerCacheInfoFromOfflineHuman(humanEntityList.get(0));
				HumanVipNewEntity entityVipNew = Globals.getDaoService().getVipNewDao().getVipHumanById(player.getPassportId());
				if(entityVipNew != null){
					playerCacheInfo.setVipLevel(entityVipNew.getVipLevel());
				}
				Globals.getPlayerCacheService().syncPlayerCache(playerCacheInfo);
			}
			
		}else{
			player.sendMessage(new GCNotifyException(DisconnectReason.FINISH_LOAD_HUMAN_EXCEPTION.getIndex(), ""));
			player.exitReason = PlayerExitReason.SERVER_ERROR;			
			player.disconnect();
		}
		return STAGE_STOP_DONE;
	}
}
