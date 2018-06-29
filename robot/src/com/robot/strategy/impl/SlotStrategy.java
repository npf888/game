package com.robot.strategy.impl;

import com.core.msg.IMessage;
import com.gameserver.slot.msg.CGSlotEnter;
import com.robot.Robot;
import com.robot.slot.SlotHandlerFactory;
import com.robot.strategy.OnceExecuteStrategy;

/**
 * 老虎机机器人
 * @author guojunwei
 *
 */
public class SlotStrategy extends OnceExecuteStrategy {
	

	public SlotStrategy(Robot robot) {
		super(robot);
	}

	
	@Override
	public void doAction() {
       //进入特定的老虎机并且转动
		CGSlotEnter message = new CGSlotEnter();
		message.setSlotId(this.getRobot().getRobotSlotCacheData().getSlotId());
		this.getRobot().sendMessage(message);
	}

	@Override
	public void onResponse(IMessage message) {
		SlotHandlerFactory.getHandler().execute(this.getRobot(),message);
	}

}
