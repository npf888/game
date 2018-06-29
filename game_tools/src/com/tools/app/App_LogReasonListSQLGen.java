package com.tools.app;

import com.tools.log.LogReasonListSqlGenerator;

/**
 * 日志原因 SQL 生成器
 * 
 * @author Thinker
 * 
 */
public class App_LogReasonListSQLGen
{
	
	public static void main(String[] args)
	{
		try 
		{
			// 生成日志原因 SQL
			LogReasonListSqlGenerator.main(args);
		} catch (Exception ex)
		{
			// 抛出异常
			throw new RuntimeException(ex);
		}
	}
}
