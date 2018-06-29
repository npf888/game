package com.logserver.telnet.command;

import java.util.Map;

import org.apache.mina.common.IoSession;

import com.logserver.telnet.command.AbstractTelnetCommand;

/**
 * Telnet退出命令
 * @author Thinker
 * 
 */
public class QuitCommand extends AbstractTelnetCommand 
{
	public QuitCommand() 
	{
		super("QUIT");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,IoSession session) 
	{
		// FIXME 这里的实现是为了保证在连接关闭前将响应消息结束的数据发出去
		sendMessage(session, this.getCommandName() + " end");
		session.close();
	}
}
