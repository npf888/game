package com.gameserver.player.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;

import com.common.constants.DisconnectReason;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.common.constants.TerminalTypeEnum;
import com.core.async.IIoOperation;
import com.core.util.Assert;
import com.core.util.TimeUtils;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoopersonal.data.BazooPersonalInfo;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerExitReason;
import com.gameserver.player.PlayerState;
import com.gameserver.player.async.PlayerAuthOperation;
import com.gameserver.player.async.PlayerAuthOperation.ICallback;
import com.gameserver.player.async.SaveDeviceOperation;
import com.gameserver.player.auth.UserAuth.AuthResult;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.player.data.PlayerInfoData;
import com.gameserver.player.enums.ChannelTypeEnum;
import com.gameserver.player.enums.PlayerRoleEnum;
import com.gameserver.player.msg.CGCheckPlayerLogin;
import com.gameserver.player.msg.CGClientVersion;
import com.gameserver.player.msg.CGEnterScene;
import com.gameserver.player.msg.CGPlayerEnter;
import com.gameserver.player.msg.CGQueryPlayerInfo;
import com.gameserver.player.msg.CGQueryPlayerInfoName;
import com.gameserver.player.msg.GCCheckPlayerLogin;
import com.gameserver.player.msg.GCNotifyException;
import com.gameserver.player.msg.GCQueryPlayerInfo;
import com.gameserver.player.msg.GCQueryPlayerInfoName;
import com.gameserver.scene.handler.SceneHandlerFactory;
import com.gameserver.startup.GameServerIoHandler;

/**
 * 玩家消息处理器，处理玩家登录、换线、换地图等消息，主线程中调用
 * 
 * @author Thinker
 * 
 */
public class PlayerMessageHandler {


	/** 日志 */
	private static final Logger logger = Loggers.loginLogger;

	protected PlayerMessageHandler() {
	
	}

	// //////////////////////////////////////////////////玩家常用逻辑开始///////////////////////////////////////////////////////


	/**
	 * 检查玩家登陆信息
	 * 
	 * @param player
	 * @param userName
	 * @param password
	 * @return  true:验证通过
	 * 
	 */
	private boolean checkPlayerLogin(Player player) {
		
		Assert.notNull(player,"player should not be null");
		
		if (player.getSession() == null|| player.getSession().isConnected() == false)
			return false;
		
		// 获取客户端 IP 地址
		String ipAddr = player.getIPAddrWhitoutPort();

		if (!LoginChecker.getInstance().checkIPAddr(ipAddr)) {
			// 验证同一 IP 不能在一秒钟不能两次登陆,
			// 如果是, 则发送错误消息给客户端
			player.sendMessage(new GCNotifyException(DisconnectReason.LOGIN_ON_ANOTHER_CLIENT.getIndex(), ""));

			player.exitReason = PlayerExitReason.MULTI_LOGIN;
			// 在断开玩家连接时, 必须设置状态!!
			// 以保证登录登出的事务特性!!
			player.setState(PlayerState.logouting);

			player.disconnect();
			return false;
		}
		
		

		if (LoginChecker.getInstance().serverIsFull()) {
			// 验证服务器是否人满了?
			// 如果是, 则发送错误消息给客户端
			player.sendMessage(new GCNotifyException(
					DisconnectReason.ENTER_SERVER_FAIL.getIndex(), Globals
							.getLangService().readSysLang(
									LangConstants.LOGIN_ERROR_SERVER_FULL)));

			player.exitReason = PlayerExitReason.SERVER_IS_FULL;
			// 在断开玩家连接时, 必须设置状态!!
			// 以保证登录登出的事务特性!!
			player.setState(PlayerState.logouting);
			player.disconnect();
			return false;
		}

		player.setState(PlayerState.connected);
		return true;
	}



	/**
	 * 玩家验证完成回调
	 * 
	 * @author Thinker
	 * 
	 */
	private static class PlayerAuthFinishedCallback implements ICallback {
		/** 玩家 */
		private Player player;

		public PlayerAuthFinishedCallback(Player p) {
			this.player = p;
		}

		@Override
		public void onFinished(AuthResult result) {
		
			//如果玩家已经断开连接
			if (player.getSession() == null || player.getSession().isConnected() == false) 
			{
				logger.info("玩家 " + player.getPassportId() + " 已断线 :: LoginLogicalProcessor.handleAuthResult");
				return;
			}
			
			
			//认证失败
			if (result == null || !result.success || result.userInfo == null)	
			{
				player.sendMessage(new GCNotifyException(DisconnectReason.PLAYER_AUTH_LOGIN_INVALID.getIndex(),""));
				player.exitReason = PlayerExitReason.PLAYER_AUTH_LOGIN_INVALID;
				player.disconnect();
				//log 增加验证失败日志
				logger.info("登录失败, 玩家 = " + player.getPassportId() + " :: LoginLogicalProcessor.handleAuthResult");
				return;
			}
			
			// 继续处理验证完成之后的操作!
			boolean loginOk = Globals.getLoginLogicalProcessor().handleAuthResult(this.player, result);
			
			if (!loginOk) {

				return;
			} else {
				
				player.setState(PlayerState.auth);
				//设置Player的passwordId
				player.setUpdateForFb(result.userInfo.isUpdateFbInfo());
				player.setPassportId(result.userInfo.getId());
				player.setPassportName(result.userInfo.getName());
				player.setImg(result.userInfo.getImg());
				player.setDeviceMac(result.userInfo.getDeviceMac());
				logger.info(player.getPassportId()+">>>"+player.isUpdateForFb()+">>>"+player.getPassportName()+">>>"+player.getImg()+"deviceMac: "+result.userInfo.getDeviceMac());
								
				
				player.setAccountId(result.userInfo.getAccountId());
				player.setFacebookId(result.userInfo.getFacebookId());
				player.setPlayerRoleEnum(PlayerRoleEnum.indexOf(result.userInfo.getRole()));
				
				// 记录用户验证时间
				Globals.getOnlinePlayerService().OnPlayerAuthFinish(player);
				
				GCCheckPlayerLogin gCCheckPlayerLogin = new GCCheckPlayerLogin();
				gCCheckPlayerLogin.setLoginId(player.getPassportId());
				gCCheckPlayerLogin.setImg(player.getImg());
				long now = Globals.getTimeService().now();
				gCCheckPlayerLogin.setUtcOffset(TimeUtils.TIME_ZONE.getOffset(now));
				gCCheckPlayerLogin.setPlayerRole(player.getPlayerRoleEnum().getIndex());
				gCCheckPlayerLogin.setFacebookId(result.userInfo.getFacebookId());
				gCCheckPlayerLogin.setAccountId(result.userInfo.getAccountId());
				player.sendMessage(gCCheckPlayerLogin);
			}

			if (result != null && result.userInfo != null) {
				// 玩家信息验证成功, 并且玩家信息不为空,
				// 则记录玩家当前设备信息 每次登陆都记录
				this.saveDeviceInfo(this.player, result.userInfo.getId(),
						result.userInfo.getName());
			}
			
		
		}

		/**
		 * 保存设备信息
		 * 
		 * @param p
		 * @param passportID
		 * @param passportName
		 * 
		 */
		private void saveDeviceInfo(Player p, long passportID,
				String passportName) {

			// 创建异步操作
			IIoOperation saveDevOp = new SaveDeviceOperation(p);

			// 保存设备信息
			Globals.getAsyncService()
					.createOperationAndExecuteAtOnce(saveDevOp);
		}
	}



	// //////////////////////////////////////////////////玩家常用内部通信逻辑开始///////////////////////////////////////////////////////
	/**
	 * 校验用户登录信息
	 * 
	 * @param player
	 * @param cgCheckPlayerLogin
	 */
	public void handleCheckPlayerLogin(Player player,
			CGCheckPlayerLogin cgCheckPlayerLogin) {
		
		// 将客户端信息存储到 player
		player.setDeviceId(cgCheckPlayerLogin.getDeviceId());
		player.setDeviceModel(cgCheckPlayerLogin.getDeviceModel());
		player.setDeviceType(cgCheckPlayerLogin.getDeviceType());
		player.setClientVersion(cgCheckPlayerLogin.getClientVersion());
		player.setOsVersion(cgCheckPlayerLogin.getOsVersion());
		player.setResourceVersion(cgCheckPlayerLogin.getResourceVersion());
		player.setCountries(cgCheckPlayerLogin.getCountries());
		ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.indexOf(cgCheckPlayerLogin.getChannelType());
		if(channelTypeEnum == null){
			// 增加检查标志
			logger.warn("调用 checkPlayerLogin 函数返回 false,  :: PlayerMessageHandler.handlePublicPlayerLogin,渠道是空");
			return;
		}
		
		player.setChannelType(channelTypeEnum);
		
	
		// 做一些基本的校验 
		//(同一个IP 不能在一定时间内连续登陆，在有了断线重连功能的时候 这个功能没有用了)
		//(服务器人数是否已经满了) 
		//true 验证通过，玩家状态是 已连接状态connected
		boolean check = checkPlayerLogin(player);

		if (check) {
			// 获取客户端 IP 地址
			String ipAddr = player.getIPAddrWhitoutPort();
			// 终端设备类型
			TerminalTypeEnum terminalType = TerminalTypeEnum.valueOf(TerminalTypeEnum.convertDeviceType(cgCheckPlayerLogin.getDeviceType()));

			// 创建验证结果回调
			ICallback authFinishedCall = new PlayerAuthFinishedCallback(player);
			// 创建 IO 操作
			IIoOperation authOp = new PlayerAuthOperation(
					cgCheckPlayerLogin.getUserId(),
					cgCheckPlayerLogin.getUserCode(), Globals.getServerConfig()
							.getServerId(), ipAddr, terminalType,
					authFinishedCall);
			// 执行 IO 操作
			Globals.getAsyncService().createOperationAndExecuteAtOnce(authOp);
			return;
		}
		
		// 增加检查标志
		logger.warn("调用 checkPlayerLogin 函数返回 false,  :: PlayerMessageHandler.handlePublicPlayerLogin");
		
	}



	/**
	 * 玩家主动下线时调用此方法
	 * 
	 * @param player
	 * @see GameServerIoHandler#sessionClosed(IoSession)
	 * 
	 */
	public void handlePlayerCloseSession(final Player player) {
		if (player == null)
			return;
		// 移除 IP 地址
		LoginChecker.getInstance().removeIPAddr(player.getIPAddrWhitoutPort());

		/*if (player.getState() != PlayerState.logouting
				&& player.getState() != PlayerState.logouted) {
			//下线清空竞赛ID
			//Globals.getSlotService().loginOutClear(player.getPassportId());
			int slotId = player.getHuman().getHumanSlotManager().getSlotId();
			int slotType = Globals.getSlotService().getTypeById(slotId);
			Globals.getTournamentService().loginOut(player.getPassportId(), slotType);
			//AAA handlePlayerCloseSession 断线重连
			//Globals.getOnlinePlayerService().offlinePlayer(player,player.exitReason == null ? PlayerExitReason.LOGOUT: player.exitReason);
		}*/
	}
	
	/**
	 * 玩家进入大厅
	 * @param player
	 * @param cgPlayerEnter
	 */
	public void handlePlayerEnter(Player player, CGPlayerEnter cgPlayerEnter) {
		
		Globals.getLoginLogicalProcessor().loadCharacters(player);
		
		
		
	}

	/**
	 * 
	 * 进入地图
	 * 
	 * @param player
	 * @param cgEnterScene
	 */
	public void handleEnterScene(final Player player, CGEnterScene cgEnterScene) 
	{
		//判断能否进入城镇
		Human human = player.getHuman();
		if(human == null) return;
	//	if(human.getOpenSceneManager().getOpenSceneById(cgEnterScene.getSceneId())==null) return;
		
		SceneHandlerFactory.getHandler().handleLeaveScene(player.getId(), player.getSceneId(),null);
		// 发送系统消息令玩家进入新的场景
		player.setTargetSceneId(cgEnterScene.getSceneId());
		player.setState(PlayerState.entering);
		//人数出现错误,不能进入场景,断开连接
		if (!Globals.getSceneService().onPlayerEnterScene(player))
		{
			Loggers.gameLogger.warn("player " + player.getPassportName() + " can't enter scene, targetSceneId :" + player.getTargetSceneId());
			player.sendMessage(new GCNotifyException(DisconnectReason.ENTER_SCENE_FAIL.getIndex(), ""));
			player.exitReason = PlayerExitReason.SERVER_ERROR;
			player.disconnect();
			return;
		}
	}

	/**
	 * 查询玩家基本信息
	 * @param player
	 * @param cgQueryPlayerInfo
	 */
	public void handleQueryPlayerInfo(Player player,
			CGQueryPlayerInfo cgQueryPlayerInfo) {
		// TODO Auto-generated method stub
		PlayerCacheInfo playerCacheInfo= Globals.getPlayerCacheService().getPlayerCacheById(cgQueryPlayerInfo.getUserId());			
		if(playerCacheInfo == null)
		{
			logger.warn("player["+player.getPassportId() +"]no exist");
			player.sendSystemMessage(LangConstants.FRIEND_NOT_EXIST);
			return;
		}
		
//		long passportId = cgQueryPlayerInfo.getUserId();
//		Player p = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
//    	String clubId = null;
//    	if(p!=null)
//    	{
//    		clubId = p.getHuman().getClubId();
//    	}
//    	else
//    	{
//    		HumanDao hDao = Globals.getDaoService().getHumanDao();
//			List<HumanEntity> humanEntityList = hDao.loadHumans(passportId);
//			if (humanEntityList != null && humanEntityList.get(0) != null) {
//				HumanEntity en = humanEntityList.get(0);
//				clubId = en.getClubId();
//				}
//    	}
    	
		
		PlayerInfoData playerInfoData =  PlayerInfoData.convertFrom(playerCacheInfo);
		playerInfoData.setIsRequest(0);
		String requestIds = player.getHuman().getRequestFriendIds();
		 if(StringUtils.isBlank(requestIds)){
		 }else{
			 String[] rIdArr = requestIds.split(",");
			 for(int j=0;j<rIdArr.length;j++){
				 if(Long.valueOf(rIdArr[j]).intValue()==playerCacheInfo.getPlayerId()){
					 playerInfoData.setIsRequest(1);
					 break;
				 }
			 }
		 }
		 //设置无双吹牛 的成就      完成 个数/总个数
		Player userPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(playerCacheInfo.getPlayerId());
		if(userPlayer == null){
			Room room = Globals.getBazooPubService().getRoomService().getRoom(RoomNumber.toRoomNumber(playerCacheInfo.getRoomNumber()));
			if(room  != null){
				for(Player p:room.getPlayers()){
					if(p.getPassportId() == playerCacheInfo.getPlayerId()){
						userPlayer=p;
						break;
					}
				}
			}
		}
		//这是玩家没有在线 ， 只能查询数据库了
		if(userPlayer == null){
			playerInfoData.setAchieveRate(Globals.getHumanBazooTaskService().getAchieveRate(playerCacheInfo.getPlayerId()));
		}else{
			playerInfoData.setAchieveRate(userPlayer.getHuman().getHumanBazooTaskManager().getAchieveRate());
		}
		GCQueryPlayerInfo gcQueryPlayerInfo = new GCQueryPlayerInfo();
		gcQueryPlayerInfo.setPlayerInfoData(playerInfoData);
		logger.info("------------------------------"+playerInfoData.getAchieveRate());
		//这是玩家没有在线 ， 只能查询数据库了
		if(userPlayer == null){
			BazooPersonalInfo[] bazooPersonalInfo = Globals.getHumanBazooPersonalService().getss(playerCacheInfo.getPlayerId());
			gcQueryPlayerInfo.setBazooPersonalInfo(bazooPersonalInfo);
		}else{
			BazooPersonalInfo[] bazooPersonalInfo = Globals.getHumanBazooPersonalService().getBazooPersonBy(userPlayer);
			gcQueryPlayerInfo.setBazooPersonalInfo(bazooPersonalInfo);
		}
		player.sendMessage(gcQueryPlayerInfo);
	}

	/**
	 * 玩家名字查询信息
	 * @param player
	 * @param cgQueryPlayerInfoName
	 */
	public void handleQueryPlayerInfoName(Player player, CGQueryPlayerInfoName cgQueryPlayerInfoName) {
		String userName = cgQueryPlayerInfoName.getUserName();
		
		
		List<PlayerCacheInfo> list = Globals.getPlayerCacheService().getPlayerCacheByName(userName);
		
		if(list.isEmpty()){
			logger.warn("player["+player.getPassportId() +"]no exist");
			player.sendSystemMessage(LangConstants.FRIEND_NOT_EXIST);
			return;
		}
		
		List<PlayerInfoData> list1 = new ArrayList<PlayerInfoData>();
		
		for(PlayerCacheInfo info : list){
//			long passportId = info.getPlayerId();
//			Player p = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
//	    	String clubId = null;
//	    	if(p!=null)
//	    	{
//	    		clubId = p.getHuman().getClubId();
//	    	}
//	    	else
//	    	{
//	    		HumanDao hDao = Globals.getDaoService().getHumanDao();
//				List<HumanEntity> humanEntityList = hDao.loadHumans(passportId);
//				if (humanEntityList != null && humanEntityList.get(0) != null) {
//					HumanEntity en = humanEntityList.get(0);
//					clubId = en.getClubId();
//					}
//	    	}
			PlayerInfoData playerInfoData =  PlayerInfoData.convertFrom(info);
			list1.add(playerInfoData);
		}
		
		GCQueryPlayerInfoName message = new GCQueryPlayerInfoName();
		
		message.setPlayerInfoData(list1.toArray(new PlayerInfoData[list1.size()]));
		
		player.sendMessage(message);
	}

	public void handleClientVersion(Player player, CGClientVersion cgClientVersion) {
		Human human = player.getHuman();
		human.setClientVersion(cgClientVersion.getVersion());
		human.setModified();
	}

}
