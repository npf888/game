package com.robot.strategy.impl;

import com.core.msg.IMessage;
import com.gameserver.bazoo.msg.CGModeChose;
import com.gameserver.bazoo.msg.CGRoomEnter;
import com.gameserver.bazoo.msg.CGRoomPubJoin;
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
		
		//AllClassicalCowShowHandMain   RobotBazooClassicalMain  RobotBazooCowMain  RobotBazooShowHandMain 调用
		/*if(this.getRobot().getBazooTemp().getWayOfType().equals("normal")){
			//首先  先 进入房间 如果满了 在创建房间
			CGModeChose CGModeChose = new CGModeChose();
			CGModeChose.setModeType(3);
			this.getRobot().sendMessage(CGModeChose);
			Robot robot = this.getRobot();
		}else{
			//首先  先 进入房间 如果满了 在创建房间
			CGModeChose CGModeChose = new CGModeChose();
			CGModeChose.setModeType(Integer.valueOf(this.getRobot().getBazooTemp().getData().getRoomNumber().split("_")[0]));
			this.getRobot().sendMessage(CGModeChose);
			
		}
		*/
	}

	@Override
	public void onResponse(IMessage message) {
		
		BazooShowHandHandlerFactory.getHandler().execute(this.getRobot(),message);
	}
}
