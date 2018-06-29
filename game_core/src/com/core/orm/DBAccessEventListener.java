package com.core.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.config.Config;
import com.core.event.IEventListener;

/**
 * 数据库访问事件监听器,监听的事件处理如下:
 * <ul>
 * <li>{@link DBAccessEvent.Type#ERROR} 向外部的监控系统汇报错误状态</li>
 * </ul>
 * @author Thinker
 * 
 */
public class DBAccessEventListener implements IEventListener<DBAccessEvent>
{
	private final static Logger logger = LoggerFactory.getLogger("lzr.db.listener");
	private final  Config serverConfig;

	public DBAccessEventListener( Config serverConfig)
	{
		super();
		this.serverConfig = serverConfig;
	}

	@Override
	public void fireEvent(DBAccessEvent event)
	{
		if (event == null)
		{
			return;
		}
		if (event.getType() == DBAccessEvent.Type.ERROR)
		{
			if("1".equals(System.getenv("__WAR_DEVELOP_MODE__"))) return;
			
			// 出错了,向外部的监控系统汇报状态
			try {
				
//				ReportServerStatus.report(serverConfig.getReportDomain(),
//						serverConfig.getServerDomain(), serverConfig
//								.getServerId(), ServerStatusType.STATUS_ERROR
//								.getStatusCode(), event.getInfo());
				
			} catch (Exception ioe) 
			{
				if (logger.isErrorEnabled())
				{
					logger.error("Report status error", ioe);
				}
			}
		}
	}
}
