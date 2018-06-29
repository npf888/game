package com.gameserver.player;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.common.IoSession;

import com.common.InitializeRequired;
import com.common.constants.CommonErrorLogInfo;
import com.common.constants.DisconnectReason;
import com.common.constants.Loggers;
import com.common.constants.SysMsgShowTypes;
import com.core.heartbeat.HeartBeatAble;
import com.core.msg.IMessage;
import com.core.msg.MessageQueue;
import com.core.util.Assert;
import com.core.util.ErrorsUtil;
import com.core.util.LogUtils;
import com.gameserver.bazoo.service.room.RoomEveryUserInfo;
import com.gameserver.common.Globals;
import com.gameserver.common.msg.GCSystemMessage;
import com.gameserver.common.unit.GameUnit;
import com.gameserver.human.Human;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.player.data.RoleInfo;
import com.gameserver.player.enums.ChannelTypeEnum;
import com.gameserver.player.enums.PlayerRoleEnum;
import com.gameserver.player.msg.GCNotifyException;
import com.gameserver.player.persistance.PlayerDataUpdater;
import com.gameserver.startup.GameClientSession;
import com.gameserver.startup.GameServerIoHandler;
/**
 * 游戏中的玩家，维护玩家的会话和玩家所有角色的引用
 * @author Thinker
 */
public class Player implements HeartBeatAble, GameUnit,InitializeRequired 
{
	/** 玩家与GameServer的会话 */
	private GameClientSession session;	
	/** 玩家当前使用的角色 */
	private Human human;
	/** 玩家的消息队列 */
	private MessageQueue<IMessage> msgQueue;
	
	
	/** 玩家上线时由玩家所属GameServer分配的 */
	private int id;
	/** 玩家的passport,可能会有线程可视性问题,使用volatile修饰* */
	private volatile long passportId;
	/**账户id*/
	private String accountId;
	/**facebookId*/
	private String facebookId;
	/** 玩家的passprot名称 */
	private String passportName;

	/** 玩家的所有角色列表,登陆选择角色使用 */
	private List<RoleInfo> roles;
	
	/** 目标场景id */
	private int targetSceneId;
	
	/** 玩家今日累计在线时长，分钟数 */
	private int todayOnlineTime;
	/** 上次玩家登录时间 */
	private long lastLoginTime;
	
	/**图片*/
	private String img;

	/** 最后一次发送聊天消息的时间 */
	private transient Map<Integer, Long> lastChatTime;
	/** 玩家的状态 */
	private volatile PlayerStateManager _stateManager;
	/** 玩家数据更新调度器 */
	private PlayerDataUpdater _dataUpdater;
	/** 玩家ip地址 */
	private String clientIp;
	/** 退出原因 */
	public PlayerExitReason exitReason;
	
	/** 处理的消息总数,为避免同步，在主线程中修改 */
	private static volatile long playerMessageCount = 0;
	/** 处理的出错消息个数 */
	private static volatile int playerErrorMessageCount = 0;
	

	/** 当前终端类型 */
	private String deviceType;
	
	/** 设备id */
	private String deviceID = "";
	/** 设备机型 */
	private String deviceModel="";
	/** 操作系统版本号 */
	private String osVersion="";
	/** 渠道类型 */
	private ChannelTypeEnum channelType  ;
	/** 客户端版本 */
	private String clientVersion = "";
	/** 客户端资源版本 */
	private int resourceVersion = 0;
	
	/**登陆国家 **/
	private String countries;

	private PlayerRoleEnum playerRoleEnum;
	
	private boolean updateForFb;
	
	private String deviceMac;
	
	public Player(GameClientSession session)
	{
		this.session = session;		
		session.setPlayer(this);
		this._stateManager = new PlayerStateManager(this);
	}

	@Override
	public void init()
	{
		this.msgQueue = new MessageQueue<IMessage>();
		_dataUpdater = new PlayerDataUpdater();
		lastChatTime = new HashMap<Integer, Long>();
	}


	
	public void setSession(GameClientSession session)
	{
		this.session = session;
	}
	
	public GameClientSession getSession() 
	{
		return session;
	}
	
	public void setHuman(Human human)
	{
		this.human = human;
	}

	public Human getHuman() 
	{
		return human;
	}
	

	@Override
	public int getId() 
	{
		return id;
	}

	@Override
	public void setId(int id) 
	{
		this.id = id;
	}
	
	public void setPassportId(long passportId) 
	{
		this.passportId = passportId;
		//添加日志，记录passport = 0的情况
		if (0 == passportId)
		{
			boolean isonline = this.isOnline();
			boolean isInScene = this.isInScene();
	
			Loggers.gameLogger.info("player passportid = 0: id:" + id + 
					";isonline:" + isonline +";passportName:" + passportName+ ";isInScene:" + isInScene);
		}
	}

	public long getPassportId()
	{
		return passportId;
	}
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public void setPassportName(String passportName)
	{
		this.passportName = passportName;
	}

	public String getPassportName() 
	{
		return passportName;
	}



	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	
	/**
	 * 放置玩家需要处理的消息
	 * @param msg
	 */
	public void putMessage(IMessage msg)
	{
		this.msgQueue.put(msg);
		// 记录玩家处理消息个数
		playerMessageCount++;
	}
	
	/**
	 * 处理服务器收到的来自玩家的消息，在玩家当前所属的场景线程中调用
	 */
	public void processMessage() 
	{
		for (int i = 0; i < 12; i++) 
		{
			if (msgQueue.isEmpty()) break;
			IMessage msg = msgQueue.get();
			Assert.notNull(msg);
			long begin = System.nanoTime();
			try
			{
				if (!_stateManager.canProcess(msg))
				{
					Loggers.playerLogger.warn(LogUtils.buildLogInfoStr(this.human.getUUID(), "msg type " + msg.getType()+"("+msg.getTypeName()+")"
							+ " can't be processed in curState:"
							+ _stateManager.getState()));
					return;
				}else
				{
					msg.execute();	
				}
			} catch (Throwable e)
			{
				playerErrorMessageCount++;
				Loggers.errorLogger.error("Process input message error!", e);
				sendMessage(new GCNotifyException(DisconnectReason.HANDLE_MSG_EXCEPTION.getIndex(), ""));
				this.exitReason = PlayerExitReason.SERVER_ERROR;
				this.disconnect();
			} finally
			{
				// 特例，统计时间跨度
				long time = (System.nanoTime() - begin) / (1000);
				if (time > 1)
				{
					int type = msg.getType();
					if(type != 504){//CG_PING=504   过滤掉CGPing
						Loggers.msgLogger.info("Message:" + msg.getTypeName()
								+ " Type:" + type + "Class:" + msg.getClass()
								+ " Time:" + time + "us"
								+ " Total:" + playerMessageCount
								+ " Error:"	+ playerErrorMessageCount);
					}
				}
			}
		}
	}

	
	/**
	 * 如果用户不在房间内 就不会发送消息
	 * @param msg
	 */
	public void sendBazooMessage(IMessage msg){
		if(this.getHuman().getBazooRoomEveryUserInfo().getIsInRoom() != RoomEveryUserInfo.USER_NOT_IN_ROOM){
			this.sendMessage(msg);
		}
	}
	
	
	/**
	 * 将消息发送给Player
	 * @param msg
	 */
	public void sendMessage(IMessage msg)
	{
		// 断言参数不为空
		Assert.notNull(msg);
		if (this._stateManager != null && this._stateManager.needSend(msg.getType()) == false)
		{
			Loggers.msgLogger.debug("msg : " + msg+ " don't need to be send to player in curState : "+ _stateManager.getState());
			return;
		}
		if (session != null) 
		{
			session.write(msg);			
		}
	}



	public void sendSystemMessage(Integer key)
	{
		GCSystemMessage msg = new GCSystemMessage(key, SysMsgShowTypes.generic);
		sendMessage(msg);
	}
	
	public void sendErrorMessage(Integer key)
	{
		GCSystemMessage msg = new GCSystemMessage(key, SysMsgShowTypes.error);
		sendMessage(msg);
	}

	public void sendImportantMessage(Integer key)
	{
		GCSystemMessage msg = new GCSystemMessage(key, SysMsgShowTypes.important);
		sendMessage(msg);
	}
	
	
	
	public List<RoleInfo> getRoles()
	{
		return roles;
	}

	public void setRoles(List<RoleInfo> roles) 
	{
		this.roles = roles;
	}
	
	public RoleInfo getRoleByUUID(long roleUUID)
	{
		RoleInfo role=null;
		if(this.roles==null) return role;
		for(int i=0;i<this.roles.size();i++)
		{
			role=this.roles.get(i);
			if(role.getRoleUUID()==roleUUID) return role;
		}
		return null;
	}

	public boolean setState(PlayerState state)
	{
		if ( this.getState() == PlayerState.logouting)
		{
			return true;
		}
		return this._stateManager.setState(state);
	}

	public PlayerState getState() 
	{
		return this._stateManager.getState();
	}

	/**
	 * 判断玩家当前是否在场景中
	 * 
	 * @return
	 */
	public boolean isInScene()
	{
		if(this.human != null)
			return this.human.getScene() != null;
		else
			return false;
	}

	/**
	 * 判断玩家是否在线
	 * 
	 * @return
	 */
	public boolean isOnline()
	{
		if(session==null) return false;
		
		return session.isConnected();
	}

	/**
	 * @return the lastChatTime
	 */
	public long getLastChatTime(int scope) 
	{
		Long time = this.lastChatTime.get(scope);

		if (time == null) 
		{
			return 0;
		}
		return time;
	}

	/**
	 * @param lastChatTime
	 *            the lastChatTime to set
	 */
	public void setLastChatTime(int scope, long lastChatTime)
	{
		this.lastChatTime.put(scope, lastChatTime);
	}

	/**
	 * @return
	 */
	public int getSceneId() {
		return this.human.getSceneId();
	}

	public void setTargetSceneId(int targetSceneId)
	{
		this.targetSceneId = targetSceneId;
	}

	public int getTargetSceneId()
	{
		return targetSceneId;
	}
	/**
	 * 获得玩家当前角色UUID
	 * 
	 * @return
	 */
	public long getRoleUUID()
	{
		if (this.human == null)
		{
			return -1l;
		} else 
		{
			return this.human.getUUID();
		}
	}

	public PlayerStateManager getStateManager() 
	{
		return _stateManager;
	}

	/**
	 * 在处理玩家消息出现异常时调用
	 */
	public void onException()
	{
		if (session.closeOnException())
		{
			// 此处会自动触发GameServerIoHandler#sessionClosed
			session.close();
		}
	}
	
	/**
	 * 关闭用户连接, 解除和 session 的绑定
	 * 
	 * @see GameServerIoHandler#sessionClosed(IoSession)
	 * 
	 */
	public void disconnect() 
	{
		if (this.session == null)
		{
			Globals.getOnlinePlayerService().removePlayer(this);
			Loggers.gameLogger.info("session null 强制删除玩家["+this.getPassportId()+"]");
			return;
		}
		// 此处会自动触发: GameServerIoHandler#sessionClosed
		this.session.close();
	}
	public void disconnectNew() 
	{
		if (this.session == null)
		{
			//Globals.getOnlinePlayerService().removePlayer(this);
			Loggers.gameLogger.info("session null 强制删除玩家["+this.getPassportId()+"]");
			return;
		}
		// 此处会自动触发: GameServerIoHandler#sessionClosed
		this.session.close();
	}
	


	public void setTodayOnlineTime(int todayOnlineTime)
	{
		this.todayOnlineTime = todayOnlineTime;
	}

	public int getTodayOnlineTime() 
	{
		return todayOnlineTime;
	}

	public void setLastLoginTime(long lastLoginTime)
	{
		this.lastLoginTime = lastLoginTime;
	}

	public long getLastLoginTime() 
	{
		return lastLoginTime;
	}
	



	/**
	 * 更新数据
	 */
	private void updateData() 
	{
		try
		{
			this._dataUpdater.update();
		} catch (Exception ex)
		{
			
			Loggers.updateLogger.error(ErrorsUtil.error(CommonErrorLogInfo.INVALID_STATE,
				"#GS.ServiceBuilder.buildGameMessageHandler", ""),ex);
		}
	}

	public PlayerDataUpdater getDataUpdater()
	{
		return _dataUpdater;
	}



	@Override
	public void heartBeat()
	{
		//状态管理器心跳
		if(this._stateManager!=null) this._stateManager.onHeartBeat();
		//玩家角色心跳
		if(this.human!=null) this.human.heartBeat();
		//更新数据
		this.updateData();
	}

	public void setClientIp(String clientIp)
	{
		this.clientIp = clientIp;
	}

	public String getClientIp()
	{
		return clientIp;
	}




	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * 获取当前用户的设备的id
	 * 
	 * @return
	 */
	public String getDeviceId()
	{
		return deviceID;
	}

	/**
	 * 设置当前用户的设备的id
	 * 
	 * @param deviceID
	 */
	public void setDeviceId(String deviceID) 
	{
		this.deviceID = deviceID;
	}

	
	
	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	

	public ChannelTypeEnum getChannelType() {
		return channelType;
	}

	public void setChannelType(ChannelTypeEnum channelType) {
		this.channelType = channelType;
	}

	/**
	 * 获取当前用户的客户端版本
	 * 
	 * @return
	 */
	public String getClientVersion() 
	{
		return clientVersion;
	}

	/**
	 * 设置当前用户的客户端版本
	 * 
	 * @param clientVersion
	 */
	public void setClientVersion(String clientVersion) 
	{
		this.clientVersion = clientVersion;
	}
	
	public int getResourceVersion() {
		return resourceVersion;
	}

	public void setResourceVersion(int resourceVersion) {
		this.resourceVersion = resourceVersion;
	}



	/**
	 * 获取不带端口号的 IP 地址 
	 */
	public String getIPAddrWhitoutPort()
	{
		return trimIpAddrPort(this.getClientIp());
	}

	/**
	 * 清除 IP 地址中的端口号
	 */
	private static String trimIpAddrPort(String ipAddr)
	{
		if (ipAddr == null) return "";
		// 获取冒号位置
		int colonIndex = ipAddr.indexOf(":");
		if (colonIndex == -1)  return ipAddr;
		return ipAddr.substring(0, colonIndex);
	}


	

	
	public String getCountries() {
		return countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}





	/////////////////////////////////////////////构建退出数据信息///////////////////////////////////////////////
	/** 玩家最后通信时间*/
	private long lastNetTime;
	
	/** 玩家有几次没有回复消息了*/
	private int outTimes;
	


	public int getOutTimes() {
		return outTimes;
	}

	public void setOutTimes(int outTimes) {
		this.outTimes = outTimes;
	}

	public long getLastNetTime() {
		return lastNetTime;
	}

	public PlayerRoleEnum getPlayerRoleEnum() {
		return playerRoleEnum;
	}

	public void setPlayerRoleEnum(PlayerRoleEnum playerRoleEnum) {
		this.playerRoleEnum = playerRoleEnum;
	}

	public void asyncNetTime(){
		lastNetTime=System.currentTimeMillis();
		outTimes=0;
	}
	
	
	/**
	 * 保存redis
	 * @return
	 */
	public void syncPlayerCacheInfo(){
		PlayerCacheInfo playerCacheInfo = PlayerCacheInfo.playerCacheInfoFromHuman(this.getHuman());
		Globals.getPlayerCacheService().syncPlayerCache(playerCacheInfo);
	}

	public boolean isUpdateForFb() {
		return updateForFb;
	}

	public void setUpdateForFb(boolean updateForFb) {
		this.updateForFb = updateForFb;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	

}
