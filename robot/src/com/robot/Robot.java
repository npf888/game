package com.robot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.client.NIOClient;
import com.core.handler.BaseMessageHandler;
import com.core.msg.IMessage;
import com.core.msg.recognizer.BaseMessageRecognizer;
import com.core.orm.DBService;
import com.core.server.IMessageProcessor;
import com.core.server.QueueMessageProcessor;
import com.core.session.MinaSession;
import com.core.template.TemplateService;
import com.core.util.Assert;
import com.db.dao.RobotStatisDataDao;
import com.db.dao.SlotListDao;
import com.game.webserver.exception.LocalException;
import com.game.webserver.login.VisitorLoginParams;
import com.gameserver.common.msg.CGHandshake;
import com.gameserver.common.msg.CGPing;
import com.gameserver.player.enums.ChannelTypeEnum;
import com.gameserver.player.msg.CGCheckPlayerLogin;
import com.gameserver.player.msg.CGEnterScene;
import com.robot.bazoo.ClassicalPropertyService;
import com.robot.bazoo.RobotBazooCacheData;
import com.robot.bazoo.data.BazooTemp;
import com.robot.common.Globals;
import com.robot.msg.RobotClientSessionClosedMsg;
import com.robot.msg.RobotClientSessionOpenedMsg;
import com.robot.slot.RobotSlotCacheData;
import com.robot.slot.RobotSlotCacheDataService;
import com.robot.slot.SlotMessageType;
import com.robot.slot.TimeTask;
import com.robot.slot.TurnDisSlot;
import com.robot.slot.TurnSlot;
import com.robot.slot.XLSService;
import com.robot.slot.bonus.BonusHandler;
import com.robot.slot.scatter.GCSlotSlotHandler;
import com.robot.slot.scatter.ScatterHandler;
import com.robot.slot.wild.WildHandler;
import com.robot.startup.IRobotClientSession;
import com.robot.startup.RobotClientIoHandler;
import com.robot.startup.RobotClientMsgHandler;
import com.robot.startup.RobotClientMsgRecognizer;
import com.robot.strategy.IRobotStrategy;

/**
 * 机器人类
 * @author Thinker
 *
 */
public class Robot
{
	/** robot相关的日志 */
	public static final Logger robotLogger = LoggerFactory.getLogger("zsg.robot");
	
	public static final String RobotTitle = "rName";
	
	private String deviceMac;
	private String deviceId;
	private String macAddress;
	private String androidId;
	private String passportId;
	private String serverId;
	
	private String password = "1";
	/** 玩家的随机码  */
	private String userCode;
	
	private int pid;
	
	private String serverIp;
	
	private int port;
	
	private RobotState state;
	
	private long uuid;
	
	private int allianceId;
	
	private List<IRobotStrategy> strategyList;
	
	private NIOClient nioclient;
	
	private RobotSlotCacheData robotSlotCacheData;
	
	private RobotBazooCacheData robotBazooCacheData;
	
	/** 玩家与GameServer的会话 */
	private IRobotClientSession session;
	
	private DBService dbService;
	
	private RobotStatisDataDao dao;
	private SlotListDao slotListDao;
	
	/**老虎机 类型 和 bonus 类型**/
	private SlotMessageType slotMessageType;
	
	/**将数据存入数据库**/
	private RobotSlotCacheDataService robotSlotCacheDataService;
	
	/***转动老虎机****/
	private TurnSlot turnSlot;
	/***转动 三消老虎机****/
	private TurnDisSlot turnDisSlot;
	
	/***XLS的数据****/
	private TemplateService templateService;
	/***解析完  XLS的数据****/
	private XLSService XLSService;
	
	/***定时器****/
	private TimeTask timeTask;
	
	/***主要转动流程****/
	private GCSlotSlotHandler gCSlotSlotHandler;
	
	/***scatter处理****/
	private ScatterHandler scatterHandler;
	
	/***bouns处理****/
	private BonusHandler bonusHandler;
	
	/***wild处理****/
	private WildHandler wildHandler;
	
	/***********/
	private BazooTemp bazooTemp;
	//经典机器人的概率
	private ClassicalPropertyService classicalPropertyService;
	
	/*****CGPing******/
	private long time = new Date().getTime();
	
	
	public Robot(String deviceMac,String serverId,DBService dbService) throws LocalException
	{
		this.deviceMac = deviceMac;
		
		this.deviceId = "";
		this.macAddress = "";
		this.androidId = "777";
		
		this.serverId = serverId;

		this.strategyList = new ArrayList<IRobotStrategy>();
		this.state = RobotState.init;
		
		VisitorLoginParams visitorLoginParams=Globals.getSynLoginServerHandler().visitorLogin(deviceMac,deviceId,macAddress,androidId);
		
		if(visitorLoginParams.getErrorCode()!=0)
			return;
		this.serverIp = visitorLoginParams.getRes().getIp();
		this.port= visitorLoginParams.getRes().getPort();
		this.userCode = visitorLoginParams.getRes().getUserCode();
		this.passportId = String.valueOf(visitorLoginParams.getRes().getUserId());
		
		robotSlotCacheData = new RobotSlotCacheData();
		
		robotBazooCacheData = new RobotBazooCacheData();
		
		slotMessageType = new SlotMessageType();
		
		robotSlotCacheDataService = new RobotSlotCacheDataService();
		
		turnSlot = new TurnSlot();
		turnDisSlot = new TurnDisSlot();
		XLSService = new XLSService();
		timeTask = new TimeTask();
		gCSlotSlotHandler = new GCSlotSlotHandler();
		scatterHandler = new ScatterHandler();
		bonusHandler = new BonusHandler();
		wildHandler = new WildHandler();

		this.dbService = dbService;

		dao = new RobotStatisDataDao(dbService);
//		slotListDao = new SlotListDao(dbService);
		
		bazooTemp = new BazooTemp();
		classicalPropertyService = new ClassicalPropertyService();
	}
	
	/**
	 * 启动连接
	 */
	public void start()
	{
		nioclient = buildConnection();		
		nioclient.getMessageProcessor().start();
		nioclient.open();
	}
	
	/**
	 * 销毁连接
	 */
	public void destory()
	{
		if (this.nioclient != null)
		{
			this.nioclient.getMessageProcessor().stop();
			this.nioclient.close();
			this.nioclient = null;
		}
	}
		
	/**
	 * 是否连接
	 * @return
	 */
	public boolean isConnected()
	{
		if (this.session != null) {
			return this.session.isConnected();
		}
		return false;
	}
	
	public NIOClient buildConnection()
	{
		BaseMessageHandler _messageHandler = new BaseMessageHandler();
		_messageHandler.registerHandler(new RobotClientMsgHandler());

		IMessageProcessor _messageProcessor = new QueueMessageProcessor(_messageHandler);
		BaseMessageRecognizer _recognizer = new RobotClientMsgRecognizer();
		RobotClientIoHandler _ioHandler = new RobotClientIoHandler(); 
		ExecutorService _executorService = Executors.newSingleThreadExecutor();
		NIOClient _client = new NIOClient("Game Server", 
											this.serverIp, 
											this.port, 
											_executorService,
											_messageProcessor, 
											_recognizer, 
											_ioHandler, 
											this.new ConnectionCallback());
		return _client;
	}

	/**
	 * 获取机器人执行策略
	 * 
	 * @param index
	 * @return
	 */
	public IRobotStrategy getStrategy(int index) {
		if (index >= 0 && index < this.strategyList.size()) {
			return this.strategyList.get(index);
		} else {
			return null;
		}
	}

	/**
	 * 添加机器人执行策略
	 * 
	 * @param strategy
	 */
	public void addRobotStrategy(IRobotStrategy strategy) {
		this.strategyList.add(strategy);
	}
	
	public List<IRobotStrategy> getStrategyList() {
		return strategyList;
	}

	public String getPassportId() {
		return passportId;
	}

	public String getDeviceMac() {
		return deviceMac;
	}



	public BazooTemp getBazooTemp() {
		return bazooTemp;
	}

	public void setBazooTemp(BazooTemp bazooTemp) {
		this.bazooTemp = bazooTemp;
	}

	public int getPid() {
		return pid;
	}

	public String getServerIp() {
		return serverIp;
	}

	public int getPort() {
		return port;
	}

	public RobotState getState() {
		return state;
	}
	
	public int getAllianceId() {
		return allianceId;
	}
	
	public long getUuid() {
		return uuid;
	}

	public void setState(RobotState state) {
		this.state = state;
	}
	
	public void setSession(IRobotClientSession session) {
		this.session = session;
	}

	public IRobotClientSession getSession()
	{
		return this.session;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public WildHandler getWildHandler() {
		return wildHandler;
	}

	public void setWildHandler(WildHandler wildHandler) {
		this.wildHandler = wildHandler;
	}

	/**
	 *
	 * 发送消息 
	 * @param msg
	 */
	public void sendMessage(IMessage msg)
	{
		Assert.notNull(msg);
		if (session != null)
		{
			session.write(msg);			
		}
	}
	

	private class ConnectionCallback implements NIOClient.ConnectionCallback
	{		
		@Override
		public void onOpen(NIOClient client, IMessage msg)
		{
			RobotClientSessionOpenedMsg message = (RobotClientSessionOpenedMsg)msg;
			
			Robot.this.setSession(message.getSession());
			message.getSession().setRobot(Robot.this);
			Robot.this.setState(RobotState.connected);
			RobotManager.getInstance().addRobot((MinaSession)message.getSession(), Robot.this);
			CGHandshake shake = new CGHandshake();
			if(getPassportId() != null){
				shake.setId(getPassportId());
			}else{
				shake.setId("0");
			}
			shake.setFly(0);
			// 连接服务器端成功之后, 发送握手消息
			Robot.this.sendMessage(shake);
		}

		@Override
		public void onClose(NIOClient client, IMessage msg)
		{
			RobotClientSessionClosedMsg message = (RobotClientSessionClosedMsg)msg;
		
			Robot robot = RobotManager.getInstance().removeRobot((MinaSession)message.getSession());
			if(robot != null)
			{
				robot.setState(RobotState.logout);
				robot.setSession(null);
			}
			message.getSession().setRobot(null);
		}		
	}
	
	public void doLogin()
	{
		this.state = RobotState.login;
		CGCheckPlayerLogin cgCheckPlayerLogin = new CGCheckPlayerLogin();
		cgCheckPlayerLogin.setUserId(Long.parseLong(passportId));
		cgCheckPlayerLogin.setUserCode(userCode);
//		cgCheckPlayerLogin.setChannelType(ChannelTypeEnum.GOOGLEPLAY.getIndex());
		cgCheckPlayerLogin.setChannelType(ChannelTypeEnum.BOQU19.getIndex());
		sendMessage(cgCheckPlayerLogin);
	}
	
	public void doPlayerEnter()
	{
		this.state = RobotState.entergame;
		CGEnterScene cgEnterScene = new CGEnterScene();
		sendMessage(cgEnterScene);
	}
	public void doEnterScene()
	{
		this.state = RobotState.gaming;

		for (int i = 0; i < this.strategyList.size(); i++)
		{
			// 获取机器人执行策略
			IRobotStrategy strategy = this.getStrategy(i);
	
			if (strategy == null) {
				return;
			}

			// 创建机器人行为
			RobotAction action = new RobotAction(strategy);
			// 获取第一次执行的时间延迟
			int delay = strategy.getDelay();
			// 获取循环执行时的时间间隔
			int period = strategy.getPeriod();
			
			if (strategy.isRepeatable())
			{
				// 循环执行机器人操作
				RobotManager.getInstance().scheduleWithFixedDelay(action, delay, period);
			} else
			{
				// 仅执行一次机器人操作
				RobotManager.getInstance().scheduleOnce(action, delay);
			}
		}
	}

	/**
	 * 发送消息
	 * @return
	 */
	public void sendCGPing(){
		
		while(true){
			long now = new Date().getTime();
			long cha = now-time;
			if(cha >= 3000){
				robotLogger.info("[机器人"+this.getPassportId()+"]--[发送CGPing()]---cha:"+cha);
				this.sendMessage(new CGPing());
				time=now;
			}
		}
	}

	public RobotSlotCacheData getRobotSlotCacheData() {
		return robotSlotCacheData;
	}



	public RobotBazooCacheData getRobotBazooCacheData() {
		return robotBazooCacheData;
	}

	public void setRobotBazooCacheData(RobotBazooCacheData robotBazooCacheData) {
		this.robotBazooCacheData = robotBazooCacheData;
	}

	public DBService getDbService() {
		return dbService;
	}

	public RobotStatisDataDao getDao() {
		return dao;
	}

	public RobotSlotCacheDataService getRobotSlotCacheDataService() {
		return robotSlotCacheDataService;
	}

	public TurnSlot getTurnSlot() {
		return turnSlot;
	}

	public TurnDisSlot getTurnDisSlot() {
		return turnDisSlot;
	}

	public void setTurnDisSlot(TurnDisSlot turnDisSlot) {
		this.turnDisSlot = turnDisSlot;
	}

	public SlotMessageType getSlotMessageType() {
		return slotMessageType;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public ClassicalPropertyService getClassicalPropertyService() {
		return classicalPropertyService;
	}

	public void setClassicalPropertyService(
			ClassicalPropertyService classicalPropertyService) {
		this.classicalPropertyService = classicalPropertyService;
	}

	public XLSService getXLSService() {
		return XLSService;
	}

	public void setXLSService(XLSService xLSService) {
		XLSService = xLSService;
	}

	public TimeTask getTimeTask() {
		return timeTask;
	}

	public GCSlotSlotHandler getgCSlotSlotHandler() {
		return gCSlotSlotHandler;
	}

	public void setgCSlotSlotHandler(GCSlotSlotHandler gCSlotSlotHandler) {
		this.gCSlotSlotHandler = gCSlotSlotHandler;
	}

	public ScatterHandler getScatterHandler() {
		return scatterHandler;
	}

	public void setScatterHandler(ScatterHandler scatterHandler) {
		this.scatterHandler = scatterHandler;
	}

	public BonusHandler getBonusHandler() {
		return bonusHandler;
	}

	public void setBonusHandler(BonusHandler bonusHandler) {
		this.bonusHandler = bonusHandler;
	}

	public SlotListDao getSlotListDao() {
		return slotListDao;
	}

	public void setSlotListDao(SlotListDao slotListDao) {
		this.slotListDao = slotListDao;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}




	
	

}
