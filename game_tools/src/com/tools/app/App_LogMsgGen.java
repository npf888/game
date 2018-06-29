package com.tools.app;

import com.tools.log.LogMsgGenerator;

/**
 * Log 日志生成 统计日志
 * 
 * @author Thinker 
 *
 */
public class App_LogMsgGen
{

	public static void main(String[] args)
	{
		try
		{
			// 生成 Log 日志
			LogMsgGenerator.main(args);
		} catch (Exception ex)
		{
			// 抛出异常
			throw new RuntimeException(ex);
		}
	}
}
