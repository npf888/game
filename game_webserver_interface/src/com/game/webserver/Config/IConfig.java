package com.game.webserver.Config;

import java.util.concurrent.ExecutorService;

import com.game.webserver.exception.LocalException;
import com.game.webserver.record.IRecord;

public interface IConfig {
	  public static final String LOCAL_CFG_FILE = "gameWebServer.properties";
	  public static final String LOCAL_LOGGER_NAME = "local";
	  public static final String LOCAL_GAMEINFO_ELEMENT = "game_info";
	  public static final String LOCAL_CONFIG_TIMEOUT = "timeout";
	  public static final String LOCAL_CONFIG_THREADNUM = "thread_num";
	  public static final String LOCAL_CONFIG_GAMEKEY = "game_key";
	  public static final String LOCAL_CONFIG_REQUESTDOMAIN = "request_domain";
	  public static final String LOCAL_CONFIG_REPORTDOMAIN = "report_domain";
	  public static final String LOCAL_CONFIG_GMDOMAIN = "gm_domain";
	  public static final String LOCAL_CONFIG_BIIP = "bi_ip";
	  public static final String LOCAL_CONFIG_GAMEID = "game_id";
	  public static final String LOCAL_CONFIG_CHANNEL = "channel";
	  public static final String LOCAL_CONFIG_LOCALE = "locale";
	  public static final String OK = "ok";
	  public static final String FAIL = "fail";
	  public static final String RESULT_SPLIT = ":";
	  public static final int RESULT_LENGTH_MIN = 2;
	  public static final String URL_ENCODE = "UTF-8";
	  public static final String SPLIT_VERTICAL = "\\|";
	  public static final String SPLIT_STRIPING = "-";
	  

	  public abstract void initlize()
			    throws LocalException;

	  public abstract IRecord getRecord();

	  public abstract ExecutorService getReportService();

	  public abstract String getRequestDomain();

	  public abstract String getReportDomain();
	  public abstract String getGmDomain();

	  public abstract String getGameKey();

	  public abstract int getTimeout();

	  public abstract void setReportService(ExecutorService paramExecutorService);

	  public abstract int getAreaId();
	
}
