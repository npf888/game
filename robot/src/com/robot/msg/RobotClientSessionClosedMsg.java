package com.robot.msg;

import com.core.msg.sys.SessionClosedMessage;
import com.robot.startup.IRobotClientSession;

/**
 * 机器人关闭会话
 * @author Thinker
 *
 */
public class RobotClientSessionClosedMsg extends SessionClosedMessage<IRobotClientSession>
{

	public RobotClientSessionClosedMsg(IRobotClientSession sender)
	{
		super(sender);
	}
	
	@Override
	public void execute()
	{		
		
	}
}
