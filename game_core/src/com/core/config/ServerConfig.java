package com.core.config;

import java.io.File;

import com.common.constants.CommonErrorLogInfo;
import com.common.constants.SharedConstants;
import com.core.util.ErrorsUtil;


/**
 * {@link Config}的简单实现
 * @author Thinker
 */
public abstract class ServerConfig implements Config
{
	/**
	 * 服务器类型：1-GameServer 2-WorldServer 3-LoginServer 4-DBSServer 5-AgentServer 6-LogServer
	 */
	
	/** 生产模式:0 调式模式:1 */
	private int debug = 0;
	/** 系统的字符编码 */
	private String charset;
	/** 系统配置的版本号 */
	private String version;
	/** 系统配置的资源版本号 */
	private String resourceVersion;
	/**区id*/
	private String regionId;
	/** 服务器ID  */
	private String serverId;

	/** 服务绑定的IP */
	private String bindIp;
	/** 服务器在服务器组中的索引值 */
	private int serverIndex;
	/** 服务器监听的端口,多个商品以逗号","分隔 */
	private String ports;
	/** 服务器的名称 */
	private String serverName;
	/** 服务器的主机ip */
	private String serverHost;
	/** 服务器组的域名 s1.l.mop.com */
	private String serverDomain;
	/** 每个端口IO处理的线程个数 */
	private int ioProcessor;
	/** 系统所使用的语言 */
	private String language;
	/** 多语言资源所在的目录 */
	private String i18nDir;
	/** 资源文件根目录 */
	private String baseResourceDir;
	/** 脚本所在的目录 */
	private String scriptDir;
	


	/** Flash 客户端发送poliyc请求时的响应 */
	private String flashSocketPolicy;
	/** 数据库初始化类型： 0 Hibernate 1 Ibatis */
	private int dbInitType = 0;
	/** 数据库配置文件路径 */
	private String dbConfigName;


	/** MIS (后台)系统 IP 地址 */
	private String misIps = null;
	/** localKey */
	private String localMd5Key = SharedConstants.DEFAULT_LOCAL_MD5_KEY;
	
	/** 是否开启超时踢出玩家*/
	private boolean lastNetOnOff=false;
	

	@Override
	public boolean getIsDebug() 
	{
		return getDebug() == 1;
	}

	@Override
	public String getVersion()
	{
		return this.version;
	}

	public void setVersion(String version) 
	{
		this.version = version;
	}

	/**
	 * 取得资源文件的绝对路径
	 * 
	 * @param path
	 * @return
	 */
	public String getResourceFullPath(String path) 
	{
		return this.baseResourceDir + File.separator + path;
	}

	/**
	 * 取得资源文件的绝对路径
	 * 
	 * @param pathes
	 *            路径的参数,每个参数将使用路径分隔符连接起来
	 * @return
	 */
	public String getResourceFullPath(String... pathes)
	{
		StringBuilder _sb = new StringBuilder();
		_sb.append(this.baseResourceDir);
		for (String _path : pathes) 
		{
			_sb.append(File.separator);
			_sb.append(_path);
		}
		return _sb.toString();
	}

	public String getBaseResourceDir()
	{
		return baseResourceDir;
	}

	public void setBaseResourceDir(String baseResourceDir)
	{
		this.baseResourceDir = baseResourceDir;
	}

	public String getLanguage() 
	{
		return language;
	}

	public void setLanguage(String language) 
	{
		this.language = language;
	}

	public String getI18nDir()
	{
		return i18nDir;
	}

	public void setI18nDir(String dir) 
	{
		i18nDir = dir;
	}

	public String getCharset() 
	{
		return charset;
	}

	public void setCharset(String charset)
	{
		this.charset = charset;
	}




	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getServerId()
	{
		return serverId;
	}

	public void setServerId(String serverId)
	{
		this.serverId = serverId;
	}



	

	public String getBindIp() 
	{
		return bindIp;
	}

	public void setBindIp(String bindIp) 
	{
		this.bindIp = bindIp;
	}

	public int getServerIndex() {
		return serverIndex;
	}

	public void setServerIndex(int serverIndex) {
		this.serverIndex = serverIndex;
	}

	public String getPorts()
	{
		return ports;
	}

	public void setPorts(String ports)
	{
		this.ports = ports;
	}

	public String getServerName() 
	{
		return serverName;
	}

	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}

	public String getServerHost() 
	{
		return serverHost;
	}

	public void setServerHost(String serverHost) 
	{
		this.serverHost = serverHost;
	}

	public int getIoProcessor()
	{
		return ioProcessor;
	}

	public void setIoProcessor(int ioProcessor)
	{
		this.ioProcessor = ioProcessor;
	}


	public void setDebug(int debug) 
	{
		this.debug = debug;
	}

	public int getDebug()
	{
		return debug;
	}

	public String getScriptDir() 
	{
		return scriptDir;
	}

	public void setScriptDir(String scriptDir)
	{
		this.scriptDir = scriptDir;
	}

	public String getFlashSocketPolicy()
	{
		return flashSocketPolicy;
	}

	public void setFlashSocketPolicy(String flashSocketPolicy) 
	{
		this.flashSocketPolicy = flashSocketPolicy;
	}



	public int getDbInitType()
	{
		return dbInitType;
	}

	public void setDbInitType(int dbInitType)
	{
		this.dbInitType = dbInitType;
	}

	public String getDbConfigName() 
	{
		return dbConfigName;
	}
	

	public void setDbConfigName(String dbConfigName) 
	{
		this.dbConfigName = dbConfigName;
	}
	

	



	@Override
	public void validate()
	{
		
		if(this.ports == null||(ports = ports.trim()).length()==0)
		{
			throw new IllegalArgumentException(ErrorsUtil.error(CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT, "", "ports"));
		}
		// 版本号配置检查
		if (this.getVersion() == null)
		{
			throw new IllegalArgumentException("The version  must not be null");
		}
	
		
	}


	public void setServerDomain(String serverDomain) 
	{
		this.serverDomain = serverDomain;
	}

	public String getServerDomain()
	{
		return serverDomain;
	}

	/**
	 * 获得脚本文件全目录
	 * 
	 * @return
	 */
	public String getScriptDirFullPath()
	{
		return this.getResourceFullPath(this.getScriptDir());
	}

	public void setResourceVersion(String resourceVersion) 
	{
		this.resourceVersion = resourceVersion;
	}

	public String getResourceVersion()
	{
		return resourceVersion;
	}


	/**
	 * 获取 MIS (后台)系统 IP 地址
	 * 
	 * @return
	 */
	public String getMisIps() 
	{
		return this.misIps;
	}

	/**
	 * 设置 MIS (后台)系统 IP 地址
	 * 
	 * @param allowedIps
	 */
	public void setMisIps(String allowedIps)
	{
		this.misIps = allowedIps;
	}

	/**
	 * 获取平台密钥
	 * 
	 * @return 
	 * 
	 */
	public String getLocalKey()
	{
		return this.localMd5Key;
	}

	/**
	 * 设置平台密钥
	 * 
	 * @param value 
	 * 
	 */
	public void setLocalKey(String value) 
	{
		this.localMd5Key = value;
	}

	public boolean isLastNetOnOff() {
		return lastNetOnOff;
	}

	public void setLastNetOnOff(boolean lastNetOnOff) {
		this.lastNetOnOff = lastNetOnOff;
	}


	
}
