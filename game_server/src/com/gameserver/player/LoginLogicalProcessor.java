package com.gameserver.player;


import java.util.List;

import org.slf4j.Logger;

import com.common.constants.DisconnectReason;
import com.common.constants.Loggers;
import com.common.constants.SharedConstants;
import com.common.constants.TerminalTypeEnum;
import com.core.util.Assert;
import com.db.model.UserInfo;
import com.gameserver.common.Globals;
import com.gameserver.player.async.CreateRoleOperation;
import com.gameserver.player.async.LoadPlayerRoleOperation;
import com.gameserver.player.async.PlayerRolesLoad;
import com.gameserver.player.auth.UserAuth;
import com.gameserver.player.auth.UserAuth.AuthResult;
import com.gameserver.player.data.RoleInfo;
import com.gameserver.player.enums.PlayerRoleEnum;
import com.gameserver.player.msg.GCNotifyException;
import com.gameserver.status.ServerStatus;
import com.gameserver.status.enums.ServerStatusEnum;


/**
 * 处理玩家登录，创建角色，选择角色等相关逻辑
 * @author Thinker
 */
public class LoginLogicalProcessor
{
	/** 日志 */
	private static final Logger logger = Loggers.loginLogger;
	private UserAuth userAuth;

	public LoginLogicalProcessor(UserAuth userAuth)
	{
		this.userAuth = userAuth;
	
	}
	
	/**
	 * 处理认证结果
	 * @param player
	 * @param result
	 * @return
	 */
	public boolean handleAuthResult(Player player, final AuthResult result) 
	{
	
		final UserInfo userInfo = result.userInfo;
		Assert.notNull(userInfo);
		
		ServerStatus serverStatus = Globals.getServerStatusService().getServerStatus();
		ServerStatusEnum serverStatusEnum = ServerStatusEnum.valueOf(serverStatus.getStatus());
		
		PlayerRoleEnum role = PlayerRoleEnum.indexOf(userInfo.getRole());
		if(role == null){
			logger.warn("玩家["+userInfo.getId()+"],角色["+userInfo.getRole()+"]错误");
			player.disconnect();
			return false;
		}
		
		// 登陆墙不允许普通玩家登陆
		if(role== PlayerRoleEnum.NORMAL && serverStatusEnum != ServerStatusEnum.RUN)
		{
			player.sendMessage(new GCNotifyException(DisconnectReason.PLAYER_AUTH_LOGIN_INVALID.getIndex(),""));
			player.disconnect();
			return false;
		}
		
		
		Player existPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(userInfo.getId());
		if(existPlayer!=null)
		{
			logger.info("exist deviceMac: ["+existPlayer.getDeviceMac()+"] will login with device: "+userInfo.getDeviceMac());
		}
		
		//玩家已在线
		if (existPlayer != null && (!existPlayer.getDeviceMac().equals(userInfo.getDeviceMac())))
		{
			//Globals.getOnlinePlayerService().addLoginRecord(userInfo.getId());
			// 踢掉当前在线玩家，通知当前登录玩家稍后重试
			if (existPlayer.getState() != PlayerState.logouting) 
			{
				//log 增加玩家断线日志, 需要记录玩家状态!
				logger.info("令已在线的玩家 " + existPlayer.getPassportId() + " 断开连接, " +
					"玩家当前状态 = " + existPlayer.getState() + " :: LoginLogicalProcessor.handleAuthResult.One");
				// 发送消息并断线
				existPlayer.sendMessage(new GCNotifyException(DisconnectReason.LOGIN_ON_ANOTHER_CLIENT.getIndex(), "账号被人从另一个客户端登录"));
				existPlayer.exitReason = PlayerExitReason.MULTI_LOGIN;
				existPlayer.disconnect();
				return false;
			} else 
			{
				//已在线的玩家没断线, 需要记录玩家状态 
				logger.info("已在线的玩家 " + existPlayer.getPassportId() + " 未断开连接,该玩家可能会丢失数据！！！ " +
					"玩家当前状态 = " + existPlayer.getState() + " :: LoginLogicalProcessor.handleAuthResult.Two");
				
				Globals.getOnlinePlayerService().logoutedState(existPlayer);	
			}
			// 断开玩家连接
			player.sendMessage(new GCNotifyException(DisconnectReason.LOGIN_ON_ANOTHER_CLIENT.getIndex(),""));
			player.exitReason =  PlayerExitReason.MULTI_LOGIN;
			player.disconnect();
			return false;
		}
		
	
		return true;
	}


	

	/**
	 * 进入大厅
	 */
	public void selectRole(Player player,long roleUUID){
		
		player.init();//初始化必要管理器
		
		OnlinePlayerService onlinePlayerService = Globals.getOnlinePlayerService();
		if(player.getRoles().size()==0)
		{
			Loggers.serverLogger.info("玩家的账号["+player.getPassportId()+"]不存在");
			return;
		}
		Player existPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(player.getPassportId());
		// 玩家已在线
		if (existPlayer != null)
		{
			// 踢掉当前在线玩家，通知当前登录玩家稍后重试
			if (existPlayer.getState() != PlayerState.logouting){		
				existPlayer.sendMessage(new GCNotifyException(DisconnectReason.LOGIN_ON_ANOTHER_CLIENT.getIndex(), ""));
				existPlayer.exitReason = PlayerExitReason.MULTI_LOGIN;
				existPlayer.disconnect();
			}
			
			player.sendMessage(new GCNotifyException(DisconnectReason.LOGIN_ON_ANOTHER_CLIENT.getIndex(),""));
			player.exitReason =  PlayerExitReason.MULTI_LOGIN;
			player.disconnect();
			Loggers.serverLogger.info("踢掉重复连接的玩家,当前玩家的账号id为: " + player.getPassportId());
			return;
		}
		
		RoleInfo roleInfo = player.getRoles().get(0);
		//人数已经满,不能进入服务器,断开连接
		if(!onlinePlayerService.onPlayerEnterServer(roleInfo.getRoleUUID(), player))
		{
			Loggers.gameLogger.warn("player " + player.getPassportName() + " can't enter server");
			player.sendMessage(new GCNotifyException(DisconnectReason.ENTER_SERVER_FAIL.getIndex(), "进入游戏失败"));
			player.exitReason = PlayerExitReason.SERVER_ERROR;
			player.disconnect();
			return;
		}
		
		// 正常登录，设置为加载角色列表 状态
		player.setState(PlayerState.loading);
		
		// 异步加载角色列表
		LoadPlayerRoleOperation _loadTask = new LoadPlayerRoleOperation(player,roleUUID);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_loadTask);
	}
	

	/**
	 * 玩家登录时加载角色列表
	 * 
	 * @param player
	 * @return
	 */
	public boolean loadCharacters(Player player) 
	{
		// 正常登录，设置为加载角色列表 状态
		player.setState(PlayerState.loadingrolelist);
		// 异步加载角色列表
		PlayerRolesLoad _loadTask = new PlayerRolesLoad(player);
		
		Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(_loadTask);
		
		return true;
	}


	
	/**
	 * 验证玩家信息
	 * 
	 * @param userName
	 * @param userPass
	 * @param ipAddr
	 * @param tt 终端设备类型
	 * @return
	 */
	public AuthResult authUser(long userId,String userCode,String serverId,String ipAddr, TerminalTypeEnum tt)
	{
		return this.userAuth.auth(userId, userCode,serverId, ipAddr, tt);
	}

	
	/**
	 * 处理玩家创建角色
	 * 
	 * @param player
	 * @param role
	 */
	public void createRole(Player player, RoleInfo role)
	{
		// 检查是否达到角色数上限
		List<RoleInfo> roles = player.getRoles();
		if (roles != null && roles.size() >= SharedConstants.MAX_ROLE_PER_PLAYER)
		{
			//player.sendMessage(new GCCreateRoleFailed((langService.read(LangConstants.ROLE_CREATE_ERROR_MAX))));
			return;
		}

		// 所有判断都在状态更新之前,否则状态混乱
		player.setState(PlayerState.creatingrole);
		// 异步保存到DBS
		CreateRoleOperation _createTask = new CreateRoleOperation(player, role);
		Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(_createTask);
		logger.info("玩家参加角色, loginName = " + player.getDeviceId());
		
	
	}
}
