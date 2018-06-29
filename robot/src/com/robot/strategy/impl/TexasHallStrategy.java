package com.robot.strategy.impl;


import java.util.HashMap;

import com.common.model.Card;
import com.core.msg.IMessage;
import com.core.util.Assert;
import com.gameserver.common.msg.GCSystemMessage;
import com.gameserver.texas.data.TexasRoomPlayerInfoData;
import com.gameserver.texas.enums.TexasRoomEnum;
import com.gameserver.texas.msg.CGJoinTexasRoomId;
import com.gameserver.texas.msg.CGLeaveTexas;
import com.gameserver.texas.msg.CGTexasQuickStart;
import com.gameserver.texas.msg.GCJoinTexas;
import com.gameserver.texas.msg.GCLeaveTexas;
import com.gameserver.texas.msg.GCSyncJoinTexas;
import com.gameserver.texas.msg.GCTexasAddBet;
import com.gameserver.texas.msg.GCTexasAllIn;
import com.gameserver.texas.msg.GCTexasBankerPos;
import com.gameserver.texas.msg.GCTexasBigBlind;
import com.gameserver.texas.msg.GCTexasButtomDeal;
import com.gameserver.texas.msg.GCTexasClearTable;
import com.gameserver.texas.msg.GCTexasCoinsSync;
import com.gameserver.texas.msg.GCTexasFlop;
import com.gameserver.texas.msg.GCTexasFollow;
import com.gameserver.texas.msg.GCTexasGiveUp;
import com.gameserver.texas.msg.GCTexasLook;
import com.gameserver.texas.msg.GCTexasPlayerTurn;
import com.gameserver.texas.msg.GCTexasPrepareRestart;
import com.gameserver.texas.msg.GCTexasRiver;
import com.gameserver.texas.msg.GCTexasSettle;
import com.gameserver.texas.msg.GCTexasSettleGiveup;
import com.gameserver.texas.msg.GCTexasSidePool;
import com.gameserver.texas.msg.GCTexasSmallBlind;
import com.gameserver.texas.msg.GCTexasTurn;
import com.robot.Robot;
import com.robot.strategy.OnceExecuteStrategy;
import com.robot.strategy.data.ClientRoomPlayer;
import com.robot.strategy.data.ClientTexasRoom;

public class TexasHallStrategy extends OnceExecuteStrategy{
	
	private ClientTexasRoom texasRoom;
	private int smallBlind;
	private long roomId;
	
	private HashMap<Integer,Integer> smallBlindMap = new HashMap<Integer,Integer>();

	{
		smallBlindMap.put(10000, 2);
		smallBlindMap.put(10001, 5);
		smallBlindMap.put(10002, 10);
		smallBlindMap.put(10003, 15);
		smallBlindMap.put(10004, 20);
		smallBlindMap.put(10005, 25);
		smallBlindMap.put(10006, 50);
		smallBlindMap.put(10007, 100);
		smallBlindMap.put(10008, 200);
		smallBlindMap.put(10009, 500);
		smallBlindMap.put(10010, 1000);
		smallBlindMap.put(10011, 2000);
		smallBlindMap.put(10012, 5000);
		smallBlindMap.put(10013, 10000);
		smallBlindMap.put(10014, 20000);
		smallBlindMap.put(10015, 50000);
		smallBlindMap.put(10016, 100000);
		smallBlindMap.put(10017, 200000);
		smallBlindMap.put(10018, 2);
		smallBlindMap.put(10019, 5);
		smallBlindMap.put(10020, 10);
		smallBlindMap.put(10021, 15);
		smallBlindMap.put(10022, 20);
		smallBlindMap.put(10023, 25);
		smallBlindMap.put(10024, 50);
		smallBlindMap.put(10025, 100);
		smallBlindMap.put(10026, 200);
		smallBlindMap.put(10027, 500);
		smallBlindMap.put(10028, 1000);
		
		smallBlindMap.put(10029, 2000);
		smallBlindMap.put(10030, 5000);
		smallBlindMap.put(10031, 10000);
		smallBlindMap.put(10032, 20000);
		smallBlindMap.put(10033, 50000);
		smallBlindMap.put(10034, 100000);
		
		smallBlindMap.put(10035, 200000);
	}



	public TexasHallStrategy(Robot robot,long roomId) {
		super(robot);
		// TODO Auto-generated constructor stub
		this.roomId = roomId;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		doRequestJoinRoomId(roomId);
	}

	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub


		if(message instanceof GCSystemMessage)
		{
			//showSystemMessage(((GCSystemMessage)message).getCode());
			return;
		}
		
		if (message instanceof GCJoinTexas)
		{
			GCJoinTexas gcJoinTexas = (GCJoinTexas)message;
			texasRoom = new ClientTexasRoom(getRobot(),TexasRoomEnum.valueOf(gcJoinTexas.getTexasRoomEnum()));
			texasRoom.init();
			
			Assert.isTrue(texasRoom.getPlayerManager().getRoomPlayerList().size() == 0,"已经接收过一次了");
			
			texasRoom.setSmallBlind(smallBlindMap.get(gcJoinTexas.getRoomType()));
			
			for(TexasRoomPlayerInfoData playerInfoData : gcJoinTexas.getPlayerList())
			{
				ClientRoomPlayer roomPlayer = ClientRoomPlayer.convertFromRoomPlayerInfoData(playerInfoData);
				texasRoom.playerJoin(roomPlayer);
			}
			
			com.robot.common.Globals.getHeartBeatThread().getTexasRoomManager().addTexasRoom(texasRoom);
			return;
		}
		
		if(message instanceof GCSyncJoinTexas)
		{
			GCSyncJoinTexas gcSyncJoinTexas  = (GCSyncJoinTexas)message;
			ClientRoomPlayer roomPlayer = ClientRoomPlayer.convertFromRoomPlayerInfoData(gcSyncJoinTexas.getPlayerJoin());
			texasRoom.playerJoin(roomPlayer);
			return;
		}
		
		if(message instanceof GCLeaveTexas)
		{
			GCLeaveTexas gcLeaveTexas  = (GCLeaveTexas)message;
			Assert.isTrue(texasRoom.getPlayerManager().getRoomPlayerList().size() != 0,"没有可离开的玩家");
			texasRoom.playerLeave(gcLeaveTexas.getPlayerId());
			if(Long.parseLong(getRobot().getPassportId()) ==gcLeaveTexas.getPlayerId() )
				getRobot().destory();
			return;
		}
			
		if(message instanceof GCTexasBankerPos)
		{
			GCTexasBankerPos gcTexasBankerPos  = (GCTexasBankerPos)message;
			texasRoom.start(gcTexasBankerPos.getPos());
			return;
		}
		
		if(message instanceof GCTexasButtomDeal)
		{
			GCTexasButtomDeal gcTexasButtomDeal  = (GCTexasButtomDeal)message;
			texasRoom.buttomDeal(gcTexasButtomDeal.getCardList());
			return;
		}
		
		if(message instanceof GCTexasPlayerTurn)
		{
			GCTexasPlayerTurn gcTexasPlayerTurn  = (GCTexasPlayerTurn)message;
			texasRoom.playerTurn(gcTexasPlayerTurn.getPosition());
			
			return;
		}
		
		if(message instanceof GCTexasFlop)
		{
			GCTexasFlop gcTexasFlop  = (GCTexasFlop)message;
			texasRoom.flop(gcTexasFlop.getCardList());
			return;
		}
		
		if(message instanceof GCTexasTurn)
		{
			GCTexasTurn gcTexasTurn  = (GCTexasTurn)message;
			
			texasRoom.turn(gcTexasTurn.getCard());
			return;
		}
		
		if(message instanceof GCTexasRiver)
		{
			GCTexasRiver gcTexasRiver  = (GCTexasRiver)message;
		
			texasRoom.river(gcTexasRiver.getCard());
			return;
		}
		
		if(message instanceof GCTexasLook)
		{
			GCTexasLook gcTexasLook  = (GCTexasLook)message;
			texasRoom.playerLook(gcTexasLook.getPos());
		
			return;
		}
		
		if(message instanceof GCTexasFollow)
		{
			GCTexasFollow gcTexasFollow  = (GCTexasFollow)message;
			texasRoom.playerFollow(gcTexasFollow.getPos());
		
			return;
		}
		
		if(message instanceof GCTexasAddBet)
		{
			GCTexasAddBet gcTexasAddBet  = (GCTexasAddBet)message;
			texasRoom.playerAddBet(gcTexasAddBet.getPos(),gcTexasAddBet.getAddBet());
			return;
		}
		
		if(message instanceof GCTexasAllIn)
		{
			GCTexasAllIn gcTexasAllIn  = (GCTexasAllIn)message;
			texasRoom.playerAllIn(gcTexasAllIn.getPos(),gcTexasAllIn.getAllInBet());
			return;
		}
		
		if(message instanceof GCTexasGiveUp)
		{
			GCTexasGiveUp gcTexasGiveUp  = (GCTexasGiveUp)message;
			texasRoom.playerGiveUp(gcTexasGiveUp.getPos());
			return;
		}
		
		if(message instanceof GCTexasPrepareRestart){
			texasRoom.prepareStart(((GCTexasPrepareRestart)message).getSmallBlind());
		}
		
		if(message instanceof GCTexasCoinsSync){
			GCTexasCoinsSync gcTexasCoinsSync  = (GCTexasCoinsSync)message;
			texasRoom.playerCoinsSync(gcTexasCoinsSync.getPos(), gcTexasCoinsSync.getCoins());
		}
		
		if(message instanceof GCTexasSettle){
			GCTexasSettle gcTexasSettle  = (GCTexasSettle)message;
			texasRoom.playerSettle(gcTexasSettle.getPlayerList(),gcTexasSettle.getSettlePoolList());
		}
		if(message instanceof GCTexasSettleGiveup){
			GCTexasSettleGiveup gcTexasSettleGiveup  = (GCTexasSettleGiveup)message;
			texasRoom.playerSettleGiveup(gcTexasSettleGiveup.getWinnerPos(),gcTexasSettleGiveup.getSettlePoolList());
		}
		
		if(message instanceof GCTexasSidePool){
			
		}
		
		if(message instanceof GCTexasBigBlind){
			GCTexasBigBlind gcTexasBigBlind  = (GCTexasBigBlind)message;
			texasRoom.startOnBigBlind(gcTexasBigBlind.getPos(), gcTexasBigBlind.getBigBlind());
		}
		
		if(message instanceof GCTexasSmallBlind){
			GCTexasSmallBlind gcTexasSmallBlind  = (GCTexasSmallBlind)message;
			texasRoom.startOnSmallBlind(gcTexasSmallBlind.getPos(), gcTexasSmallBlind.getSmallBlind());
		}
		

		
		if(message instanceof GCTexasClearTable){
			GCTexasClearTable gcTexasClearTable  = (GCTexasClearTable)message;
			texasRoom.clear();
		}
		
	}
	

	
	
	
	/**
	 * 快速加入
	 */
	private void doQuickRequestJoin(){
		System.out.println("开始快速加入");
		
		CGTexasQuickStart cgTexasQuickStart = new CGTexasQuickStart();
		getRobot().sendMessage(cgTexasQuickStart);
	}
	
	private void doRequestJoinRoomId(long roomId){
		System.out.println("请求加入房间["+roomId+"]");
		CGJoinTexasRoomId cgJoinTexasRoomId= new CGJoinTexasRoomId();
		cgJoinTexasRoomId.setRoomId(roomId);
		getRobot().sendMessage(cgJoinTexasRoomId);
	}

	
	/**
	 * 离开房间
	 */
	private void doRequestLeaveTexasRoom()
	{
		System.out.println("开始尝试离开德州类型");
		CGLeaveTexas cgLeaveTeaxs = new CGLeaveTexas();
		getRobot().sendMessage(cgLeaveTeaxs);
	}
	


	
	/**
	 * 系统消息
	 * @param content
	 */
	private void showSystemMessage(String content)
	{
		System.out.printf("system message :%s\n",content);
	}
	

}
