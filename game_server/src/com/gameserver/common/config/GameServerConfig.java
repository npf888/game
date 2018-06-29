package com.gameserver.common.config;

import java.io.File;

import com.core.config.ServerConfig;

/**
 * 服务器配置信息
 * 
 * 一些key/value对 获取资源的路径
 * 
 * @author Thinker
 * 
 */
public class GameServerConfig extends ServerConfig 
{
	/** 系统配置的数据库版本号 */
	private String dbVersion;
	
	/** 最大允许在线人数 */
	private volatile int maxOnlineUsers;

	/** Telnet服务器名称 */
	private String telnetServerName;
	/** Telnet绑定的ip */
	private String telnetBindIp;
	/** Telnet绑定的端口 */
	private String telnetPort;
	/** 登陆墙是否打开，默认关闭 */
	private volatile boolean loginWallEnabled = false;
	
	/** 是否以异或方式加载模版资源 */
	private boolean templateXorLoad = true;
	/** 时区 */
	protected String timeZone = "";

	
	/**redis*/
	private String redisHost;
	/**redis port*/
	private int redisPort;
	/**redis max active */
	private int redisMaxActive;
	/**redis max wait*/
	private int redisMaxWait;
	/**redis maxIdle*/
	private int redisMaxIdle;
	
	private String password;
	

	
	/**log*/
	private String logHost;
	private int logPort;
	private boolean logEnable;
	
	private boolean test;
	private boolean robot;
	private String robotServerIp;
	private int robotServerPort;

	
	private String smsaddress;
	private String smsPhoneNumber;
	private String smsAccount;
	private String smsPwd;
	private int smsMaxpeople;
	private String smsContent;
	
	private int httpDataPort;
	private String httpDataAddress;
	
	private String httpRobotAddress;
	private String boquURL;
	private String boquPCThroughURL;//pc个人中心
	private String boquMobileThroughURL;//手机的个人中心
	private String boquRedirectUrl;//博趣 端从定向的 url
	private Integer gameId;
	private String boquOutDomain;//博趣 端从定向的 url
	
	
	
	public GameServerConfig() 
	{

	}

	/**
	 * 取得资源文件的绝对路径
	 * 
	 * @param pathes
	 *            路径的参数,每个参数将使用路径分隔符连接起来
	 * @return
	 */
	@Override
	public String getResourceFullPath(String... pathes)
	{
		StringBuilder _sb = new StringBuilder();
		_sb.append(this.getBaseResourceDir());
		for (String _path : pathes)
		{
			_sb.append(File.separator);
			_sb.append(_path);
		}
		return _sb.toString();
	}
	

	@Override
	public void validate()
	{
		super.validate();
	}

	/**
	 * 登陆墙是否打开
	 * @return
	 */
	public boolean isLoginWallEnabled() {
		return loginWallEnabled;
	}

	/**
	 * 设置登陆墙是否打开
	 * @param loginWallEnabled
	 */
	public void setLoginWallEnabled(boolean loginWallEnabled) {
		this.loginWallEnabled = loginWallEnabled;
	}
	
	/**
	 * 获得脚本文件路径
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public String getResourceFilePath(String fileName) {
		return this.getResourceFullPath(this.getScriptDir(), fileName);
	}

	/**
	 * @return the maxOnlineUsers
	 */
	public int getMaxOnlineUsers() {
		return maxOnlineUsers;
	}

	/**
	 * @param maxOnlineUsers
	 *            the maxOnlineUsers to set
	 */
	public void setMaxOnlineUsers(int maxOnlineUsers) {
		this.maxOnlineUsers = maxOnlineUsers;
	}


	public String getTelnetServerName() {
		return telnetServerName;
	}

	public void setTelnetServerName(String telnetServerName) {
		this.telnetServerName = telnetServerName;
	}

	public String getTelnetBindIp() {
		return telnetBindIp;
	}

	public void setTelnetBindIp(String telnetBindIp) {
		this.telnetBindIp = telnetBindIp;
	}

	public String getTelnetPort() {
		return telnetPort;
	}

	public void setTelnetPort(String telnetPort) {
		this.telnetPort = telnetPort;
	}



	
	public void setDbVersion(String dbVersion) {
		this.dbVersion = dbVersion;
	}

	public String getDbVersion() {
		return dbVersion;
	}


	/**
	 * 使用异或方式加载模版资源 ?
	 * 
	 * @return
	 */
	public boolean isTemplateXorLoad() {
		return this.templateXorLoad;
	}

	/**
	 * 使用异或方式加载模版资源 ?
	 * 
	 * @param value
	 */
	public void setTemplateXorLoad(boolean value) {
		this.templateXorLoad = value;
	}

	/**
	 * 获取时区
	 * 
	 * @return
	 */
	public String getTimeZone() {
		return this.timeZone;
	}

	/**
	 * 设置时区
	 * 
	 * @param value
	 */
	public void setTimeZone(String value) {
		this.timeZone = value;
	}



	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public int getRedisMaxActive() {
		return redisMaxActive;
	}

	public void setRedisMaxActive(int redisMaxActive) {
		this.redisMaxActive = redisMaxActive;
	}

	public int getRedisMaxWait() {
		return redisMaxWait;
	}

	public void setRedisMaxWait(int redisMaxWait) {
		this.redisMaxWait = redisMaxWait;
	}

	public int getRedisMaxIdle() {
		return redisMaxIdle;
	}

	public void setRedisMaxIdle(int redisMaxIdle) {
		this.redisMaxIdle = redisMaxIdle;
	}

	

	public String getLogHost() {
		return logHost;
	}

	public void setLogHost(String logHost) {
		this.logHost = logHost;
	}

	public int getLogPort() {
		return logPort;
	}

	public void setLogPort(int logPort) {
		this.logPort = logPort;
	}

	public boolean isLogEnable() {
		return logEnable;
	}

	public void setLogEnable(boolean logEnable) {
		this.logEnable = logEnable;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public boolean isRobot() {
		return robot;
	}

	public void setRobot(boolean robot) {
		this.robot = robot;
	}

	public String getRobotServerIp() {
		return robotServerIp;
	}

	public void setRobotServerIp(String robotServerIp) {
		this.robotServerIp = robotServerIp;
	}

	public int getRobotServerPort() {
		return robotServerPort;
	}

	public void setRobotServerPort(int robotServerPort) {
		this.robotServerPort = robotServerPort;
	}

	public String getSmsaddress() {
		return smsaddress;
	}

	public void setSmsaddress(String smsaddress) {
		this.smsaddress = smsaddress;
	}

	public String getSmsPhoneNumber() {
		return smsPhoneNumber;
	}

	public void setSmsPhoneNumber(String smsPhoneNumber) {
		this.smsPhoneNumber = smsPhoneNumber;
	}

	public String getSmsAccount() {
		return smsAccount;
	}

	public void setSmsAccount(String smsAccount) {
		this.smsAccount = smsAccount;
	}

	public String getSmsPwd() {
		return smsPwd;
	}

	public void setSmsPwd(String smsPwd) {
		this.smsPwd = smsPwd;
	}

	public int getSmsMaxpeople() {
		return smsMaxpeople;
	}

	public void setSmsMaxpeople(int smsMaxpeople) {
		this.smsMaxpeople = smsMaxpeople;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public int getHttpDataPort() {
		return httpDataPort;
	}

	public void setHttpDataPort(int httpDataPort) {
		this.httpDataPort = httpDataPort;
	}

	public String getHttpDataAddress() {
		return httpDataAddress;
	}

	public void setHttpDataAddress(String httpDataAddress) {
		this.httpDataAddress = httpDataAddress;
	}

	public String getHttpRobotAddress() {
		return httpRobotAddress;
	}

	public void setHttpRobotAddress(String httpRobotAddress) {
		this.httpRobotAddress = httpRobotAddress;
	}

	public String getBoquURL() {
		return boquURL;
	}

	public void setBoquURL(String boquURL) {
		this.boquURL = boquURL;
	}

	

	public String getBoquPCThroughURL() {
		return boquPCThroughURL;
	}

	public void setBoquPCThroughURL(String boquPCThroughURL) {
		this.boquPCThroughURL = boquPCThroughURL;
	}

	public String getBoquMobileThroughURL() {
		return boquMobileThroughURL;
	}

	public void setBoquMobileThroughURL(String boquMobileThroughURL) {
		this.boquMobileThroughURL = boquMobileThroughURL;
	}

	public String getBoquRedirectUrl() {
		return boquRedirectUrl;
	}

	public void setBoquRedirectUrl(String boquRedirectUrl) {
		this.boquRedirectUrl = boquRedirectUrl;
	}

	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBoquOutDomain() {
		return boquOutDomain;
	}

	public void setBoquOutDomain(String boquOutDomain) {
		this.boquOutDomain = boquOutDomain;
	}

	
   
	
   
}
