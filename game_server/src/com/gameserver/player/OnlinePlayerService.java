package com.gameserver.player;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.NonThreadSafe;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.common.exception.CrossThreadException;
import com.core.orm.DataAccessException;
import com.core.session.ISession;
import com.core.util.Assert;
import com.core.util.LogUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanEntity;
import com.game.webserver.login.ResetUpdateFbInfoParams;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.CGHandshake;
import com.gameserver.common.msg.GCMessage;
import com.gameserver.common.unit.GameUnitList;
import com.gameserver.human.Human;
import com.gameserver.ip.geoip.IPSearchService;
import com.gameserver.player.async.SavePlayerRoleOperation;
import com.gameserver.player.data.RoleInfo;
import com.gameserver.player.enums.PlayerRoleEnum;
import com.gameserver.player.handler.PlayerMessageHandler;
import com.gameserver.player.msg.CGPlayerEnter;
import com.gameserver.scene.SceneService;
import com.gameserver.startup.GameServerRuntime;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

/**
 * 在线玩家管理器
 * 
 * @author Thinker
 * 
 */
public class OnlinePlayerService implements InitializeRequired, NonThreadSafe {
	
	public static final Logger logger = Loggers.playerLogger;
	
	/** 最多同时在线的人数 */
	private int maxPlayerNum;

	/** 维护当前Server所有在线玩家实例 */
	private GameUnitList<Player> onlinePlayers;
	
	/** 在线玩家列表，方便查询，key：玩家当前角色UUID，value：玩家引用 */
	private Map<Long, Player> _onlinePlayersMap;
	
	/** 登录用户集合 <Long passportId,Player loginUser> */
	private Map<Long, Player> passportIdPlayers;

	/** CG_HANDSHAKE 消息的时候添加  */
	private Map<ISession, Player> sessionPlayers;
	

	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	/** 最大登录时间间隔   有断线重连这个登陆时间间隔限制没有用了*/
	public static final long Max_Interval_LastLoginTime = 0;// 20 * 1000

	/**
	 * 初始化在线玩家实例数组
	 * 
	 * @param maxPlayerNum
	 *            最多同时在线的人数
	 */
	public OnlinePlayerService() {}
	
	@Override
	public void init() {
		
		this.maxPlayerNum = Globals.getServerConfig().getMaxOnlineUsers();
		onlinePlayers = new GameUnitList<Player>(maxPlayerNum);
		_onlinePlayersMap = new ConcurrentHashMap<Long, Player>(maxPlayerNum);
		passportIdPlayers = new ConcurrentHashMap<Long, Player>();

		sessionPlayers = Maps.newConcurrentHashMap();
	}
	
	/**
	 * 根据 passportId 查找在线用户
	 * 
	 * @param passportId
	 * @return
	 */
	public Player getPlayerByPassportId(long passportId) {
		return passportIdPlayers.get(passportId);
	}
	public int getPlayerByPassportSzie() {
		return passportIdPlayers.size();
	}
	
	public int getRoleSize(){
		return _onlinePlayersMap.size();
	}

	/**
	 * 根据玩家角色uuid获得玩家对象的引用
	 * 
	 * @param roleUUID
	 *            玩家当前角色UUID
	 * @return
	 */
	public Player getPlayer(long roleUUID) {
		return _onlinePlayersMap.get(roleUUID);
	}
	
	public Map<ISession, Player> getSessionPlayers(){
		return sessionPlayers;
	}

	/**
	 * 建立连接时建立 Session 与 Player 的对应关系. 在 CGHandshake 消息中执行! 即,
	 * 在客户端与服务器端建立连接时被调用!
	 * 
	 * @param session
	 * @param user
	 * @see CGHandshake
	 * 
	 */
	public void putPlayer(ISession session, Player user) {
		if (session == null || user == null)
			return;
		synchronized(sessionPlayers){
			sessionPlayers.put(session, user);
		}
		
	}
	
	

	public int getOnlinePlayerCount() {
		return onlinePlayers.size();
	}

	/**
	 * 玩家进入当前服务器. 在 PlayerMessageHandler#handlePlayerEnter 函数中被调用! 即,
	 * 在玩家已经登录服务器并选择完角色后被调用! 注意: 此时尚未从数据库中加载角色数据... 从时序上来看, 要先于
	 * {@link #putPlayer(String, Player)}
	 * 
	 * @param player
	 * @param roleUUID
	 *            玩家当前角色的UUID
	 * @see #putPlayer(String, Player)
	 * @see PlayerMessageHandler#handlePlayerEnter(Player, CGPlayerEnter)
	 * 
	 */
	public boolean onPlayerEnterServer(long roleUUID, Player player) {
		
		return this.addPlayer(roleUUID,player);
	}

	/**
	 * 用户认证完成
	 */
	public void OnPlayerAuthFinish(Player player) {
	/*	if (player == null)
			return;
		playerLoginTimeMap.put(player.getPassportId(),System.currentTimeMillis());*/
	}

	/**
	 * 遍历维护在线玩家实例的数组，找到数组中第一 个引用为<code>null</code>的 索引，将当前<code>player</code>
	 * 对象的引用存储到数组的该位置
	 * <p>
	 * 注意：<b>此方法只能在主线程中调用</b>，否则会 抛出异常{@link CrossThreadException}
	 * 
	 * @param player
	 * @return
	 */
	private boolean addPlayer(long roleUUID,Player player) {
		Assert.notNull(player);
		checkThread();

		if (onlinePlayers.size() >= maxPlayerNum) {
			logger.info("Online player number reaches the upper limit");
			return false;
		}
		writeLock.lock();
		try {
			
			onlinePlayers.add(player);
			_onlinePlayersMap.put(roleUUID, player);
			passportIdPlayers.put(player.getPassportId(), player);
			logger.info("Player login, passportId: " + player.getPassportId());
			return true;
		} finally {
			writeLock.unlock();
		}
	}

	public void broadcastMessage(final GCMessage msg,
			final Predicate<Human> filter) {
		for (Player player : _onlinePlayersMap.values()) {
			if (player == null)
				continue;

			if (!PlayerState.gaming.equals(player.getState()))
				continue;

			Human human = player.getHuman();
			if (human == null)
				continue;

			if (filter == null || filter.apply(player.getHuman()))
				player.sendMessage(msg);
		}
	}

	public void sendMessage(final GCMessage msg, List<Long> passportIds) {
		for (Long passportId : passportIds) {
			Player p = passportIdPlayers.get(passportId);
			if(p!=null)
			{
				p.sendMessage(msg);
			}
		}
	}
	/**
	 * 获得玩家实例
	 * 
	 * @param playerId
	 * @return
	 */
	public Player getPlayerByTempId(int playerId) {
		readLock.lock();
		try {
			Player player = onlinePlayers.get(playerId);
			return player;
		} finally {
			readLock.unlock();
		}
	}

	/**
	 * 获取所有在线玩家passportId列表
	 * 
	 * <pre>
	 * 需要遍历在线玩家列表时调用此方法
	 * 不应该将onlinePlayers对外暴露
	 * 
	 * </pre>
	 * 
	 */
	public Collection<Long> getAllOnlinePlayerRoleUUIDs() {
		Set<Long> onlinePlayerUUIDs = this._onlinePlayersMap.keySet();
		return onlinePlayerUUIDs;
	}

	/**
	 * 使所有玩家下线，注意一定要在调用{@link SceneService#stop()}方法之后调用此方法
	 * 
	 * @param reason
	 *            服务器主动发起的
	 */
	public void offlineAllPlayers(PlayerExitReason reason) {
		Assert.isTrue(reason == PlayerExitReason.SERVER_SHUTDOWN);
		Loggers.playerLogger
				.info("All players will be offline,to save players "
						+ this.onlinePlayers.size());
		Collection<Player> players = _onlinePlayersMap.values();

		for (Player player : players) {
			if (player != null && player.getState() != PlayerState.logouting) {
				try {
					this.offlinePlayer(player, reason);
				} catch (Exception e) {
					Loggers.playerLogger.error(
							"Error occurs when offline all players", e);
				}
			}
		}
	}

	/**
	 * <pre>
	 * 玩家下线，此方法在主线程中调用
	 * </pre>
	 * 
	 * @param player
	 * @param reason
	 */
	public void offlinePlayer(Player player, final PlayerExitReason reason) {
		checkThread();
		player.exitReason = reason;
		logger.info("PlayerOffline.passportId[" + player.getPassportId()+ "]" + ",State=" + player.getState());

		// 当客户端处于PlayerState.init状态时，客户端与服务器只建立了连接
		if (player.getState() == PlayerState.init|| player.getState() == PlayerState.connected) {
			player.setState(PlayerState.logouting);
			Globals.getOnlinePlayerService().removeSession(player.getSession());
			Globals.getOnlinePlayerService().removePlayer(player);
			logger.info("PlayerOffline.passportId[" + player.getPassportId()+ "].offlinePlayer.removeSession");
			return;
		} else if (player.getState() == PlayerState.auth
				|| player.getState() == PlayerState.loadingrolelist
				|| player.getState() == PlayerState.waitingselectrole
				|| player.getState() == PlayerState.creatingrole
				|| player.getState() == PlayerState.loading) {
			player.setState(PlayerState.logouting);
			Globals.getOnlinePlayerService().removeSession(player.getSession());
			Globals.getOnlinePlayerService().removePlayer(player);
			logger.info("PlayerOffline.passportId["
					+ player.getPassportId()
					+ "].offlinePlayer.removeSession removePlayer logoutedState 1");
			return;
		}

		if (player.getState() == PlayerState.entering
				|| player.getState() == PlayerState.leaving
				|| player.getState() == PlayerState.gaming) {
			player.setState(PlayerState.logouting);
		}

		if (player.isInScene()) {
			// 离开场景后会自动调存库方法，存库之后会自动移除玩家
			Globals.getSceneService().onPlayerLeaveScene(player, null);
			player.setState(PlayerState.logouting);
		}
		
		//机器人退出
		if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
			Globals.getRobotService().robotExit(player);
		}
		
		// 增加离线保存战斗信息快照的mask
		SavePlayerRoleOperation _saveTask = new SavePlayerRoleOperation(player,PlayerConstants.CHARACTER_INFO_MASK_BASE, true);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_saveTask);

		logger.info("PlayerOffline.passportId[" + player.getPassportId()+ "].offlinePlayer.SavePlayerRoleOperation");
	}
	
	
	/**
	 * <pre>
	 * 玩家下线，此方法在主线程中调用
	 * </pre>
	 * 
	 * @param player
	 * @param reason
	 */
	public void offlinePlayerNew(Player player, final PlayerExitReason reason) {
		//checkThread();
		player.exitReason = reason;
		logger.info("PlayerOffline.passportId[" + player.getPassportId()+ "]" + ",State=" + player.getState());
		
		// 当客户端处于PlayerState.init状态时，客户端与服务器只建立了连接
		if (player.getState() == PlayerState.init|| player.getState() == PlayerState.connected) {
			player.setState(PlayerState.logouting);
			Globals.getOnlinePlayerService().removeSession(player.getSession());
			Globals.getOnlinePlayerService().removePlayer(player);
			logger.info("PlayerOffline.passportId[" + player.getPassportId()+ "].offlinePlayer.removeSession");
			return;
		} else if (player.getState() == PlayerState.auth
				|| player.getState() == PlayerState.loadingrolelist
				|| player.getState() == PlayerState.waitingselectrole
				|| player.getState() == PlayerState.creatingrole
				|| player.getState() == PlayerState.loading) {
			player.setState(PlayerState.logouting);
			Globals.getOnlinePlayerService().removeSession(player.getSession());
			Globals.getOnlinePlayerService().removePlayer(player);
			logger.info("PlayerOffline.passportId["
					+ player.getPassportId()
					+ "].offlinePlayer.removeSession removePlayer logoutedState 1");
			return;
		}
		
		if (player.getState() == PlayerState.entering
				|| player.getState() == PlayerState.leaving
				|| player.getState() == PlayerState.gaming) {
			player.setState(PlayerState.logouting);
			Globals.getOnlinePlayerService().removeSession(player.getSession());
			Globals.getOnlinePlayerService().removePlayer(player);
		}
		
		if (player.isInScene()) {
			// 离开场景后会自动调存库方法，存库之后会自动移除玩家
			Globals.getSceneService().onPlayerLeaveScene(player, null);
			player.setState(PlayerState.logouting);
		}
		
		//机器人退出
		if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){
			Globals.getRobotService().robotExit(player);
		}
		
		// 增加离线保存战斗信息快照的mask
		SavePlayerRoleOperation _saveTask = new SavePlayerRoleOperation(player,PlayerConstants.CHARACTER_INFO_MASK_BASE, true);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_saveTask);
		Globals.getRankNewServer().logoutData(player.getHuman().getPassportId());
		logger.info("PlayerOffline.passportId[" + player.getPassportId()+ "].offlinePlayer.SavePlayerRoleOperation");
	}

	/**
	 * 根据玩家临时id移除一个在线玩家，同时通知WorldServer
	 * 
	 * <pre>
	 * 1、如果正在异步加载角色信息或退出保存，则只修改状态为logout，不进行移除操作
	 * 2、注意&lt;b&gt;此方法只能在主线程中调用&lt;/b&gt;，否则会 抛出异常{@link CrossThreadException}
	 * </pre>
	 * 
	 * @param passortId
	 *            玩家在场景中的Id
	 */
	public void removePlayer(Player player) {
		if (player == null)
			return;
		checkThread();
		if ((player.getState() != PlayerState.logouting) && (player.getState() != PlayerState.logouted)) {
			logger.error("Only player with state [" + player.getState()+ "] can be remove " + player.getPassportId());
		}
		
		logger.info("Player exit, passportId: " + player.getPassportId());
		writeLock.lock();
		try {
			
			if (player.getHuman() != null) {
				_onlinePlayersMap.remove(player.getHuman().getUUID());
			
			}
			passportIdPlayers.remove(player.getPassportId());
			if(onlinePlayers.contains(player)){
				onlinePlayers.remove(player);
			}
			
		} finally {
			writeLock.unlock();
		}
	}
	public void removePlayerMandatory(Player player) {
		if (player == null)
			return;
		
		logger.info("removePlayerMandatory ::::Player exit, passportId: " + player.getPassportId());
		writeLock.lock();
		try {
			if (player.getHuman() != null) {
				_onlinePlayersMap.remove(player.getHuman().getUUID());
			}
			passportIdPlayers.remove(player.getPassportId());
			if(onlinePlayers.contains(player)){
				onlinePlayers.remove(player);
			}
			
		} finally {
			writeLock.unlock();
		}
	}
	

	/**
	 * 玩家登出游戏的最后一步操作, 在所有登出步骤执行完成之后被调用!
	 * 
	 * @param logoutPlayer
	 * 
	 */
	public void logoutedState(Player logoutPlayer) {
		if (logoutPlayer == null)
			return;
		if (logoutPlayer.getState() != PlayerState.logouted)
			logoutPlayer.setState(PlayerState.logouting);

		Globals.getOnlinePlayerService().removePlayer(logoutPlayer);
		logoutPlayer.setState(PlayerState.logouted);
	}

	/**
	 * 去除session
	 * 
	 * @param session
	 */
	public void removeSession(ISession session) {
		if (session == null)
			return;
        synchronized (sessionPlayers) {
        	Player removePlayer = sessionPlayers.remove(session);
        	
        	if (removePlayer == null)
    			return;
    		removePlayer.setSession(null);
		}
	}

	/**
	 * 如果当前线程不合法，则抛出异常{@link CrossThreadException}
	 */
	@Override
	public boolean checkThread() {
		if (GameServerRuntime.isShutdowning()
				|| GameServerRuntime.isOpen() == false
				|| Globals.getMessageProcessor().isStop()) {
			return true;
		}

		if (Thread.currentThread().getId() != Globals.getGlobalLogicRunner().getThreadId()) {
			throw new CrossThreadException();
		}
		return false;
	}

	
	public GameUnitList<Player> getOnlinePlayers() {
		return onlinePlayers;
	}

	/**
	 * 获取在线玩家MAP
	 * 
	 * @return
	 */
	public Map<Long, Player> getOnlinePlayersMap() {
		return _onlinePlayersMap;
	}

	/**
	 * 检查世界否已经爆满
	 * 
	 * @return
	 */
	public boolean isFull() {
		// 当世界总人数达到世界人数上限时爆满
		int onlinePlayerCount = getOnlinePlayerCount();
		
		int maxOnline = Globals.getServerConfig().getMaxOnlineUsers();
		
		return onlinePlayerCount>= maxOnline;
	}

	/**
	 * 加载玩家列表
	 * 
	 * @param passportId
	 * @return
	 */
	public List<RoleInfo> loadPlayerRoleList(long passportId) {
		try {
			
			Loggers.playerLogger.info("loadPlayerRoleList begin");
			// 从数据库中读取
			List<HumanEntity> humans = Globals.getDaoService().getHumanDao().loadHumans(passportId);
			
			List<RoleInfo> roles = new ArrayList<RoleInfo>(humans.size());
			// 进行转换
			for (int i = 0; i < humans.size(); i++) {
				HumanEntity human = humans.get(i);
				RoleInfo roleInfo = new RoleInfo();
				roleInfo.setRoleUUID(human.getId());

				roleInfo.setName(human.getName());
				roleInfo.setLevel(human.getLevel());
				roleInfo.setFirstLogin(human.getSceneId() <= 0 ? 1 : 0);
	
				roles.add(roleInfo);
			}
			Loggers.playerLogger.info("loadPlayerRoleList over");
			return roles;
		} catch (DataAccessException e) {
			Loggers.playerLogger.error(LogUtils.buildLogInfoStr(passportId,
					"#GS.PlayerManagerImpl.loadPlayersByPid"), e);
			return null;
		}
	}

	/**
	 * 创建角色
	 * 
	 * @param player
	 * @param roleInfo
	 * @return
	 */
	public boolean createRole(Player player, RoleInfo roleInfo) {
		try {
			// 插入角色对象:UUID
			long now = Globals.getTimeService().now();
			roleInfo.setRoleUUID(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMAN));
			HumanEntity he = roleInfo.toEntity();
			he.setGold(Globals.getConfigTempl().getInitialChips());
			he.setDiamond(Globals.getConfigTempl().getInitialFire());
			he.setCharm(Globals.getConfigTempl().getInitialCharm());
			he.setCreateTime(Globals.getTimeService().now());
			if (player.isUpdateForFb()) {
				he.setName(player.getPassportName());
				he.setImg(player.getImg());

				ResetUpdateFbInfoParams resetUpdateFbInfoParams = Globals.getSynLoginServerHandler()
						.resetUpdateFbInfo(player.getPassportId());
				if (resetUpdateFbInfoParams.getErrorCode() == 0) {
					logger.info("successfully updateFbInfo for: " + player.getPassportId());
				} else {
					logger.info("failed updateFbInfo for: " + player.getPassportId());
				}

			}
			Globals.getDaoService().getHumanDao().save(he);
			logger.info("create human for: "+player.getPassportId()+">>>"+he.getName()+">>>"+he.getImg());
			
			String initialchips = LogReasons.GoldLogReason.INITIALCHIPS.getReasonText();
			Human human = new Human(player);
			IPSearchService iPSearchService = Globals.getiPSearchService();
			human.setIpCountries(iPSearchService.searchCountryByIp(player.getIPAddrWhitoutPort()));
			human.setName(player.getPassportName());
			Globals.getLogService().sendGoldLog(human, LogReasons.GoldLogReason.INITIALCHIPS, initialchips, Globals.getConfigTempl().getInitialChips(),  Globals.getConfigTempl().getInitialChips());
		
			LogReasons.PlayerLoginLogReason reason = LogReasons.PlayerLoginLogReason.PLAYERLOGINLOG2;
			Globals.getLogService().sendPlayerLoginLog(human, reason, reason.getReasonText(), player.getChannelType().getIndex(), player.getDeviceModel(), player.getClientVersion(),player.getCountries(),iPSearchService.searchCountryByIp(player.getIPAddrWhitoutPort()));
			
			return true;
		} catch (DataAccessException e) {
			Loggers.playerLogger.error(
					LogUtils.buildLogInfoStr(player.getPassportId(),
							"PlayerManagerImpl.createCharacter"), e);
			player.sendSystemMessage(LangConstants.LOGIN_UNKOWN_ERROR);
			return false;
		}
	}

	/**
	 * 根据姓名获得角色信息
	 * 
	 * @param name
	 * @return
	 */
	public String loadPlayerByName(String name) {
		HumanEntity humanEntity = Globals.getDaoService().getHumanDao()
				.loadHuman(name);
		if (humanEntity != null) {
			return humanEntity.getName();
		} else {
			return null;
		}
	}

}
