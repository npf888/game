package com.robot.startup;

import org.apache.mina.common.IoSession;


import com.core.msg.IMessage;
import com.core.server.AbstractIoHandler;
import com.robot.Robot;
import com.robot.msg.RobotClientSessionClosedMsg;
import com.robot.msg.RobotClientSessionOpenedMsg;

/**
 * 机器人IO处理
 * @author Thinker
 *
 */
public class RobotClientIoHandler extends AbstractIoHandler<RobotClientSession>
{
	@Override
	public void sessionOpened(IoSession session)
	{
		if (Robot.robotLogger.isDebugEnabled())
		{
			Robot.robotLogger.debug("Session opened");
		}
		RobotClientSession s = this.createSession(session);
		session.setAttachment(s);
		IMessage msg = new RobotClientSessionOpenedMsg(s);
		msgProcessor.put(msg);
	}
	
	@Override
	public void sessionClosed(IoSession session)
	{
		if (Robot.robotLogger.isDebugEnabled())
		{
			Robot.robotLogger.debug("Session closed");
		}
		
		RobotClientSession ms = (RobotClientSession) session.getAttachment();
		if (ms == null)
		{
			return;
		}
		session.setAttachment(null);
		IMessage msg = new RobotClientSessionClosedMsg(ms);
		msgProcessor.put(msg);
	}


	@Override
	protected RobotClientSession createSession(IoSession session)
	{		
		return new RobotClientSession(session);
	}
}
