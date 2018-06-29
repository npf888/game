package com.robot.strategy.impl;

import com.core.msg.IMessage;
import com.gameserver.bazoo.msg.CGModeChose;
import com.gameserver.bazoo.msg.GCBazooHeartBeat;
import com.gameserver.bazoo.msg.GCDiceUnifySwing;
import com.gameserver.bazoo.msg.GCDiceUserShouldSwing;
import com.gameserver.bazoo.msg.GCEndCount;
import com.gameserver.bazoo.msg.GCGuessOpen;
import com.gameserver.bazoo.msg.GCRobOpen;
import com.gameserver.bazoo.msg.GCTalkBig;
import com.gameserver.common.msg.CGPing;
import com.robot.Robot;
import com.robot.bazoo.BazooHandlerFactory;
import com.robot.strategy.OnceExecuteStrategy;

public class BazooClassicalStrategy extends OnceExecuteStrategy {
	public BazooClassicalStrategy(Robot robot) {
		super(robot);
	}

	
	@Override
	public void doAction() {
		//首先  先 进入房间 如果满了 在创建房间
		CGModeChose CGModeChose = new CGModeChose();
		CGModeChose.setModeType(1);
		this.getRobot().sendMessage(CGModeChose);
		
	}

	@Override
	public void onResponse(IMessage message) {
		
		//统一摇色子  和 单独 摇色子 返回
		if(message instanceof GCDiceUnifySwing){
			BazooHandlerFactory.getHandler2().execute(this.getRobot(),message,"swingBack");
			
		}else if(message instanceof GCDiceUserShouldSwing){
			BazooHandlerFactory.getHandler2().execute(this.getRobot(),message,"shouldSwing");
			
		}else if(message instanceof GCTalkBig){
			BazooHandlerFactory.getHandler2().execute(this.getRobot(),message,"callBack");
		}else if(message instanceof GCRobOpen){
			BazooHandlerFactory.getHandler2().execute(this.getRobot(),message,"robBack");
		}else if(message instanceof GCGuessOpen){
			BazooHandlerFactory.getHandler2().execute(this.getRobot(),message,"guessOpen");
			
		}else if(message instanceof GCEndCount){
			BazooHandlerFactory.getHandler2().execute(this.getRobot(),message,"endCountBack");
		}else if(message instanceof GCBazooHeartBeat){
			BazooHandlerFactory.getHandler2().execute(this.getRobot(),message,"GCBazooHeartBeat");
			
		}else{
			BazooHandlerFactory.getHandler().execute(this.getRobot(),message);
		}
	}
}
