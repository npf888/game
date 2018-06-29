package com.logserver.createtable;

import java.util.TimerTask;

/**
 * 定义CreateMission
 * 
 * @author Thinker
 * 
 */
public class CreateTabaleTask extends TimerTask 
{
	private ITableCreator iTableCreator;

	public CreateTabaleTask(ITableCreator creator) 
	{
		this.iTableCreator = creator;
	}

	/**
	 * 建立日志数据表
	 */
	public void run()
	{
		this.iTableCreator.buildTable();
	}
}
