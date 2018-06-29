package com.game.webserver.Config;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.game.webserver.exception.LocalConfigException;
import com.game.webserver.exception.LocalException;
import com.game.webserver.record.IRecord;
import com.game.webserver.record.Slf4jRecord;

public class LocalConfig implements IConfig{
	/** timeout时间*/
	private int timeout = 11;
	/** 线程数*/
	private int threadNum = 2;
	
	private String gameKey;
	private String requestDomain = null;
	
	private String reportDomain = null;
	
	private String gmDomain = null;
	
	private String configFile = LOCAL_CFG_FILE;
	private static String robotFile = "gameWebServerRobot.properties";
	
	private IRecord record;
	private ExecutorService reportService;
	private int areaId;
	

	private String gameId="0101001";
	private String channel="";
	private String locale="";
	
	public LocalConfig(String configFile, int areaId)
		    throws LocalException
    {
	    if (configFile != null) {
	      this.configFile = configFile;
	    }
	    this.areaId = areaId;
	
	    initlize();
	
	    checkConfig();
    }
	
	public static LocalConfig robotLocalConfig() throws LocalException{
	
		LocalConfig localConfig = new LocalConfig(robotFile,1);
		return localConfig;
	}
	
	public LocalConfig()
		    throws LocalException
    {
	
	    initlize();
	
	    checkConfig();
    }
	  
	@Override
	public void initlize() throws LocalException {
		initLogger();
	    initConfigFile();
	}
	
	private void initLogger()
    throws LocalException
    {
      this.record = new Slf4jRecord();

      this.record.recordInfo("#IMOP.LOCAL.LOGGER.INITLIZE...");
    }

	private void initConfigFile()
    {
		
		InputStream in = ClassLoader.getSystemResourceAsStream(this.configFile);
		Properties p = new Properties();
		try {
			p.load(in);
			initGameInfoElement(p);
			initThreadPool();
		} catch (Exception e) {
			this.record.recordError("#IMOP.LOCAL.INITLIZE.ERROR...FILE:" + this.configFile, e);
		}
    }
	
	private void initGameInfoElement(Properties p)
	{
	    this.timeout=Integer.parseInt(p.getProperty(LOCAL_CONFIG_TIMEOUT));
	    this.threadNum=Integer.parseInt(p.getProperty(LOCAL_CONFIG_THREADNUM));
	    this.gameKey=p.getProperty(LOCAL_CONFIG_GAMEKEY);
	    this.requestDomain=p.getProperty(LOCAL_CONFIG_REQUESTDOMAIN);
	    this.reportDomain=p.getProperty(LOCAL_CONFIG_REPORTDOMAIN);
	    this.gmDomain = p.getProperty(LOCAL_CONFIG_GMDOMAIN);
	
	    this.gameId=p.getProperty(LOCAL_CONFIG_GAMEID);
	    this.channel=p.getProperty(LOCAL_CONFIG_CHANNEL);
	    this.locale=p.getProperty(LOCAL_CONFIG_LOCALE);
	}
	
	//启动线程
	private void initThreadPool()
	{
	   if (this.reportService == null) {
	      this.reportService = Executors.newFixedThreadPool(this.threadNum);
	   }
	   this.record.recordInfo("#IMOP.LOCAL.INIT.THREADPOOL.OK:SIZE:" + this.threadNum);
	}
	
	private void checkConfig()
		    throws LocalConfigException
	{
//	    if (this.areaId == 0) {
//	      throw new LocalConfigException("areaId is zero");
//	    }
	
//	    if (this.serverId == 0) {
//	      throw new LocalConfigException("serverId is zero");
//	    }
	
	    if (this.gameKey == null) {
	      throw new LocalConfigException("gameKey is null");
	    }
	
	    if (this.requestDomain == null) {
	      throw new LocalConfigException("requestDomain is null");
	    }
	
	    if (this.reportDomain == null) {
	      throw new LocalConfigException("reportDomain is null");
	    }
	
	    if (10 > this.timeout) {
	      throw new LocalConfigException("timeout is too short");
	    }
	
//	    if (this.reportService == null) {
//	      throw new LocalConfigException("reportService is null");
//	    }
	
	    this.record.recordInfo("#IMOP.LOCAL.START:AREAID:" + this.areaId);
	}
	
	@Override
	public IRecord getRecord() {
		return this.record;
	}

	@Override
	public ExecutorService getReportService() {
		return this.reportService;
	}

	@Override
	public String getRequestDomain() {
		return this.requestDomain;
	}

	@Override
	public String getReportDomain() {
		return this.reportDomain;
	}
	
	@Override
	public String getGmDomain() {
		// TODO Auto-generated method stub
		return this.gmDomain;
	}

	@Override
	public String getGameKey() {
		return this.gameKey;
	}

	@Override
	public int getTimeout() {
		return this.timeout;
	}

	

	

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public void setReportService(ExecutorService paramExecutorService) {
		this.reportService = reportService;
	}

	@Override
	public int getAreaId() {
		return this.areaId;
	}

	
}
