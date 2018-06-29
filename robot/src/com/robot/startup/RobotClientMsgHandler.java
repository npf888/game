package com.robot.startup;


import com.core.msg.IMessage;
import com.core.server.IMessageHandler;
import com.core.session.MinaSession;
import com.gameserver.common.msg.GCMessage;
import com.robot.Robot;
import com.robot.RobotManager;


public class RobotClientMsgHandler implements IMessageHandler<IMessage>
{
	private Robot getRobot(IMessage message)
	{
		MinaSession minaSession = (MinaSession)((GCMessage)message).getSession();
		return RobotManager.getInstance().getRobot(minaSession);		
	}

	@Override
	public void execute(IMessage message)
	{
		com.robot.common.Globals.getHeartBeatThread().put(message);
		
	}

	@Override
	public short[] getTypes() 
	{
		return RobotMessageMappingProvider.msgTypes;
	}
}
