package com.robot.strategy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.common.model.Card;
import com.core.msg.IMessage;
import com.core.util.Assert;
import com.gameserver.common.msg.GCSystemMessage;
import com.gameserver.texas.data.TexasRoomPlayerInfoData;
import com.gameserver.texas.data.sng.TexasSngInfoData;
import com.gameserver.texas.enums.TexasRoomEnum;
import com.gameserver.texas.msg.CGJoinTexasSng;
import com.gameserver.texas.msg.CGLeaveTexas;
import com.gameserver.texas.msg.CGTexasQuickStart;
import com.gameserver.texas.msg.CGTexasSngList;
import com.gameserver.texas.msg.GCJoinTexas;
import com.gameserver.texas.msg.GCLeaveTexas;
import com.gameserver.texas.msg.GCSyncJoinTexas;
import com.gameserver.texas.msg.GCTexasAddBet;
import com.gameserver.texas.msg.GCTexasAllIn;
import com.gameserver.texas.msg.GCTexasBankerPos;
import com.gameserver.texas.msg.GCTexasBigBlind;
import com.gameserver.texas.msg.GCTexasButtomDeal;
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
import com.gameserver.texas.msg.GCTexasSngList;
import com.gameserver.texas.msg.GCTexasTurn;
import com.robot.Robot;
import com.robot.strategy.OnceExecuteStrategy;
import com.robot.strategy.data.ClientRoomPlayer;
import com.robot.strategy.data.ClientTexasRoom;

public class SNGStrategy extends OnceExecuteStrategy{

	private ClientTexasRoom texasRoom;
	private int smallBlind;
	private List<TexasSngInfoData> texasSngInfoDataList = new ArrayList<TexasSngInfoData>();

	public SNGStrategy(Robot robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		doGetSNGList();
	
	}


	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub


		if(message instanceof GCSystemMessage)
		{
			//showSystemMessage(((GCSystemMessage)message).getCode());
			return;
		}
		
		if(message instanceof GCTexasSngList){
			GCTexasSngList gcTexasSngList= (GCTexasSngList)message;
	
			for(TexasSngInfoData sngInfoData :gcTexasSngList.getSngInfoDataList() )
				texasSngInfoDataList.add(sngInfoData);
			
			doRequestAddTexasSng();
		}
		
		if(message instanceof GCJoinTexas)
		{
			GCJoinTexas gcJoinTexas = (GCJoinTexas)message;
			texasRoom = new ClientTexasRoom(getRobot(),TexasRoomEnum.valueOf(gcJoinTexas.getTexasRoomEnum()));
			texasRoom.init();
			
			
			Assert.isTrue(texasRoom.getPlayerManager().getRoomPlayerList().size() == 0,"已经接收过一次了");
			
			
			for(TexasRoomPlayerInfoData playerInfoData : gcJoinTexas.getPlayerList())
			{
				ClientRoomPlayer roomPlayer = ClientRoomPlayer.convertFromRoomPlayerInfoData(playerInfoData);
				texasRoom.playerJoin(roomPlayer);
			}
	
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
			if(gcLeaveTexas.getPlayerId() == Long.parseLong(getRobot().getPassportId())){
				doRequestAddTexasSng();
			}
			
			return;
		}
			
		if(message instanceof GCTexasBankerPos)
		{
			GCTexasBankerPos gcTexasBankerPos  = (GCTexasBankerPos)message;
			texasRoom.start(gcTexasBankerPos.getPos());
			return;
		}
		
		if(message instanceof GCTexasSmallBlind)
		{
			GCTexasSmallBlind gcTexasSmallBlind  = (GCTexasSmallBlind)message;
			texasRoom.startOnSmallBlind(gcTexasSmallBlind.getPos(), gcTexasSmallBlind.getSmallBlind());
			return;
		}
		
		if(message instanceof GCTexasBigBlind)
		{
			GCTexasBigBlind gcTexasBigBlind  = (GCTexasBigBlind)message;
			texasRoom.startOnBigBlind(gcTexasBigBlind.getPos(), gcTexasBigBlind.getBigBlind());
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
	}
	

	
	/**
	 * 获取sng列表
	 */
	private void doGetSNGList() {
		System.out.println("请求sng列表");
		// TODO Auto-generated method stub
		CGTexasSngList cgTexasSngList = new CGTexasSngList();
		getRobot().sendMessage(cgTexasSngList);
	}
	
	/**
	 * 请求加入sng
	 */
	private void doRequestAddTexasSng() {
		// TODO Auto-generated method stub
		TexasSngInfoData texasSngInfoData = texasSngInfoDataList.get(0); 
		System.out.println("请求加入sng"+texasSngInfoData.getId());
		// TODO Auto-generated method stub
		CGJoinTexasSng cgJoinTexasSng = new CGJoinTexasSng();
		cgJoinTexasSng.setSngId(texasSngInfoData.getId());
		getRobot().sendMessage(cgJoinTexasSng);
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
