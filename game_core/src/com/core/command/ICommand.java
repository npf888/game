package com.core.command;

import com.core.session.ISession;

/**
 * 命令执行接口,命令形式是字符串,其格式一般为
 * @author Thinker
 */
public interface ICommand<T extends ISession>
{
	/** 聊天调试命令的前缀 {@value} */
	public static final String CMD_PREFIX = "!";

	/**
	 * 用指定的参数<code>commands</code>执行命令
	 * 
	 * @param sender
	 *            命令发送者
	 * @param commands
	 *            命令参数
	 */
	public abstract void execute(T player, String[] commands);

	/**
	 * 命令名称
	 * 
	 * @return
	 */
	public abstract String getCommandName();
}