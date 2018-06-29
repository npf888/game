package com.robot.strategy.impl;

import com.core.msg.IMessage;
import com.gameserver.bazoo.msg.CGModeChose;
import com.gameserver.common.msg.CGPing;
import com.robot.Robot;
import com.robot.bazoo.showhand.BazooShowHandHandlerFactory;
import com.robot.strategy.OnceExecuteStrategy;

public class BazooShowHandStrategy extends OnceExecuteStrategy {
	public BazooShowHandStrategy(Robot robot) {
		super(robot);
	}

	
	@Override
	public void doAction() {
		//首先  先 进入房间 如果满了 在创建房间
		CGModeChose CGModeChose = new CGModeChose();
		CGModeChose.setModeType(3);
		this.getRobot().sendMessage(CGModeChose);
		Robot robot = this.getRobot();
		
	}

	@Override
	public void onResponse(IMessage message) {
		
		BazooShowHandHandlerFactory.getHandler().execute(this.getRobot(),message);
	}
}
