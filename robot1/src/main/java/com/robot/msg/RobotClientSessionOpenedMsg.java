package com.robot.msg;

import com.core.msg.sys.SessionOpenedMessage;
import com.robot.startup.IRobotClientSession;

/**
 * 机器人开启会话
 * @author Thinker
 *
 */
public class RobotClientSessionOpenedMsg extends SessionOpenedMessage<IRobotClientSession>
{

	public RobotClientSessionOpenedMsg(IRobotClientSession session) 
	{
		super(session);
	}

	@Override
	public void execute()
	{	
		
	}
}
