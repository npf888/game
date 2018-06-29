package com.core.command;

import com.core.session.ISession;


/**
 * 管理命令接口
 * @author Thinker
 * 
 */
public interface IAdminCommand<T extends ISession> extends ICommand<T> 
{
	
}
