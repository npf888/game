package com.robot.strategy.impl;

import com.core.msg.IMessage;
import com.robot.Robot;
import com.robot.slotother.SlotOther;
import com.robot.strategy.OnceExecuteStrategy;

public class SlotOtherStrategy extends OnceExecuteStrategy {
	SlotOther slotOther = null;
	public SlotOtherStrategy(Robot robot) {
		super(robot);
		slotOther = new SlotOther();
	}

	@Override
	public void doAction() {
		//邮件
		//slotOther.getMailReq(this.getRobot());
		
		//活动
//		slotOther.getActivityReq(this.getRobot());
		
		
		slotOther.afterEnterSLot(this.getRobot());
		
		
	}

	@Override
	public void onResponse(IMessage message) {
		//邮件
		//slotOther.getMailResp(message);
		//活动
//		slotOther.getActivityResp(this.getRobot(), message);
		slotOther.jackpotRequest(this.getRobot());
		
	}

}
