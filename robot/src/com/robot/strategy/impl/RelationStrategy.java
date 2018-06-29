package com.robot.strategy.impl;

import com.common.model.Card;
import com.core.enums.IndexedEnum;
import com.core.msg.IMessage;
import com.core.util.Assert;
import com.gameserver.common.msg.GCSystemMessage;
import com.gameserver.relation.msg.CGApplyFriend;
import com.gameserver.relation.msg.CGLoadFriendList;
import com.gameserver.relation.msg.CGLoadFriendRequestList;
import com.gameserver.relation.msg.CGRequestFriend;
import com.gameserver.relation.msg.GCLoadFriendList;
import com.gameserver.relation.msg.GCLoadFriendRequestList;
import com.gameserver.texas.data.TexasRoomPlayerInfoData;
import com.gameserver.texas.msg.GCJoinTexas;
import com.gameserver.texas.msg.GCLeaveTexas;
import com.gameserver.texas.msg.GCSyncJoinTexas;
import com.gameserver.texas.msg.GCTexasAddBet;
import com.gameserver.texas.msg.GCTexasAllIn;
import com.gameserver.texas.msg.GCTexasBankerPos;
import com.gameserver.texas.msg.GCTexasButtomDeal;
import com.gameserver.texas.msg.GCTexasFlop;
import com.gameserver.texas.msg.GCTexasFollow;
import com.gameserver.texas.msg.GCTexasGiveUp;
import com.gameserver.texas.msg.GCTexasList;
import com.gameserver.texas.msg.GCTexasLook;
import com.gameserver.texas.msg.GCTexasPlayerTurn;
import com.gameserver.texas.msg.GCTexasRiver;
import com.gameserver.texas.msg.GCTexasTurn;
import com.robot.Robot;
import com.robot.strategy.LoopExecuteStrategy;
import com.robot.strategy.data.ClientRoomPlayer;
import com.robot.strategy.data.ClientTexasRoom;

public class RelationStrategy extends LoopExecuteStrategy{
	
	private enum RelationActionEnum implements IndexedEnum{
		REQUEST_EXIST_FRIEND(1),
		REQUEST_NO_EXIST_FRIEND(2),
		REQUEST_INVALID_USER(3)
		;

		private final int index;
		private RelationActionEnum(int index)
		{
			this.index = index;
		}
		
		@Override
		public int getIndex() {
			// TODO Auto-generated method stub
			return index;
		}
		
	}
	
	public RelationStrategy(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		
		doLoadFriendList();
		doLoadFriendRequestList();
	}

	private void doLoadFriendRequestList() {
		// TODO Auto-generated method stub
		CGLoadFriendList cgLoadFriendList = new CGLoadFriendList();
		getRobot().sendMessage(cgLoadFriendList);
	}



	private void doLoadFriendList() {
		// TODO Auto-generated method stub
		CGLoadFriendRequestList cgLoadFriendRequestList = new CGLoadFriendRequestList();
		getRobot().sendMessage(cgLoadFriendRequestList);
	}

	
	private void doRequestInvalidUser(){
		CGRequestFriend cgRequestFriend = new CGRequestFriend();
		cgRequestFriend.setPlayerId(10002);
		getRobot().sendMessage(cgRequestFriend);
	}

	private void doAcceptUser(){
		CGApplyFriend cgApplyFriend = new CGApplyFriend();
		cgApplyFriend.setPlayerId(10005);
		cgApplyFriend.setResult(1);
		getRobot().sendMessage(cgApplyFriend);
	}
	
	private void doRejectUser(){
		CGApplyFriend cgApplyFriend = new CGApplyFriend();
		cgApplyFriend.setPlayerId(10002);
		cgApplyFriend.setResult(0);
		getRobot().sendMessage(cgApplyFriend);
	}
	
	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub
		
		if(message instanceof GCLoadFriendList)
		{
			//doRequestInvalidUser();
		}
		
		if(message instanceof GCLoadFriendRequestList)
		{
			doAcceptUser();
		}
		
		if(message instanceof GCSystemMessage)
		{
			//showSystemMessage(((GCSystemMessage)message).getCode());
			return;
		}
		
		
	
	}



	private void showSystemMessage(String content) {
		// TODO Auto-generated method stub
		System.out.println(content);
	}
	
}
