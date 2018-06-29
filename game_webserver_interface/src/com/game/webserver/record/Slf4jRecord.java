package com.game.webserver.record;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.game.webserver.exception.LocalException;
import com.game.webserver.exception.LocalLog4jException;
public class Slf4jRecord implements IRecord{
	private Logger localLogger;

	  public Slf4jRecord()
	    throws LocalException
	  {
	    this.localLogger = LoggerFactory.getLogger("local");

	    if (LogManager.exists("local") == null)
	      throw new LocalLog4jException();
	  }

	  public void recordInfo(String info)
	  {
	    this.localLogger.info(info);
	  }

	  public void recordError(String errorInfo, Throwable throwable)
	  {
	    this.localLogger.error(errorInfo, throwable);
	  }
}
