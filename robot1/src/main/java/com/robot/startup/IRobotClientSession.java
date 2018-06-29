package com.robot.startup;

import org.apache.mina.common.IoSession;

import com.core.session.ISession;
import com.robot.Robot;

/**
 * 机器人客户端会话接口
 * @author Thinker
 *
 */
public interface IRobotClientSession extends ISession
{
	void setRobot(Robot player);
	Robot getRobot();
	IoSession getIoSession();
}
