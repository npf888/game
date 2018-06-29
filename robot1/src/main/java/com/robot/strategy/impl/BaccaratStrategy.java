package com.robot.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import com.core.msg.IMessage;
import com.gameserver.baccart.data.BaccartBetData;
import com.gameserver.baccart.data.BaccartPlayerData;
import com.gameserver.baccart.data.BaccartRoomData;
import com.gameserver.baccart.data.PearlRoadData;
import com.gameserver.baccart.enums.BaccartRoomState;
import com.gameserver.baccart.msg.CGBaccartJoin;
import com.gameserver.baccart.msg.CGBaccartList;
import com.gameserver.baccart.msg.GCBaccartBet;
import com.gameserver.baccart.msg.GCBaccartClearTable;
import com.gameserver.baccart.msg.GCBaccartExit;
import com.gameserver.baccart.msg.GCBaccartJoin;
import com.gameserver.baccart.msg.GCBaccartList;
import com.gameserver.baccart.msg.GCBaccartSeat;
import com.gameserver.baccart.msg.GCBaccartSettle;
import com.gameserver.baccart.msg.GCBaccartShuffle;
import com.gameserver.baccart.msg.GCBaccartStand;
import com.gameserver.baccart.msg.GCBaccartStartBet;

import com.gameserver.baccart.msg.GCBaccartSyncJoin;
import com.robot.Robot;
import com.robot.strategy.OnceExecuteStrategy;
import com.robot.strategy.data.ClientBaccaratRoom;
import com.robot.strategy.data.ClientBaccaratRoomPlayer;

/**
 * 百家乐策略
 * @author 郭君伟
 *
 */
public class BaccaratStrategy  extends OnceExecuteStrategy{

	private List<BaccartRoomData> baccartRoomDataList = new ArrayList<BaccartRoomData>();
	private ClientBaccaratRoom baccartRoom = new ClientBaccaratRoom(getRobot());
	
	public BaccaratStrategy(Robot robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		//请求百家乐列表
		doRequestBaccaratList();
		
	}


	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub
		if(message instanceof GCBaccartList){
			baccartRoomDataList.clear();
			GCBaccartList gcBaccartList = (GCBaccartList)message;
			for(BaccartRoomData tempBaccartRoomData:gcBaccartList.getBaccartRoomDataList())
				baccartRoomDataList.add(tempBaccartRoomData);
			showBaccaratList();
			
			doRequestJoinBaccartRoom(baccartRoomDataList.get(0).getRoomId());
		}
		
		if(message instanceof GCBaccartJoin){
			GCBaccartJoin gcBaccartJoin = (GCBaccartJoin)message;
			baccartRoomJoin(gcBaccartJoin.getRoomState(),gcBaccartJoin.getPearlRoadDataList(),gcBaccartJoin.getPlayerDataList(),gcBaccartJoin.getBetDataList());
		}
		
		if(message instanceof GCBaccartSyncJoin){
			GCBaccartSyncJoin gcBaccartSyncJoin = (GCBaccartSyncJoin)message;
			baccartRoomPlayerJoin(gcBaccartSyncJoin.getPlayerData());
		}
		
		if(message instanceof GCBaccartSyncJoin){
			GCBaccartSyncJoin gcBaccartSyncJoin = (GCBaccartSyncJoin)message;
			baccartRoomPlayerJoin(gcBaccartSyncJoin.getPlayerData());
		}
		
		if(message instanceof GCBaccartExit){
			GCBaccartExit gcBaccartExit = (GCBaccartExit)message;
			baccartRoomPlayerExit(gcBaccartExit.getPlayerId());
		}
		
		if(message instanceof GCBaccartBet){
			GCBaccartBet gcBaccartBet = (GCBaccartBet)message;
			
			this.playerBet(gcBaccartBet.getPlayerId(),gcBaccartBet.getBetDataList());
		}
		
		if(message instanceof GCBaccartSettle){
			GCBaccartSettle gcBaccartSettle = (GCBaccartSettle)message;	
			this.setttle(gcBaccartSettle);
		}
		
		if(message instanceof GCBaccartShuffle){
			GCBaccartShuffle gcBaccartShuffle = (GCBaccartShuffle)message;
			this.shuffle(gcBaccartShuffle.getRemainCards());
		}
		
		if(message instanceof GCBaccartStartBet){
			
			this.startBet();
		}
		
		if(message instanceof GCBaccartClearTable){
			
			this.clear();
		}
		
		if(message instanceof GCBaccartStand){
			GCBaccartStand gcBaccartStand = (GCBaccartStand)message;
			this.playerStand(gcBaccartStand.getPlayerId());
		}
		if(message instanceof GCBaccartSeat){
			GCBaccartSeat gcBaccartSeat = (GCBaccartSeat)message;
			this.playerSeat(gcBaccartSeat.getPlayerId(), gcBaccartSeat.getPos());
		}
		
		
	
	}
	


	

	/**
	 * 请求百家乐列表
	 */
	private void doRequestBaccaratList(){
		CGBaccartList cgBaccartList = new CGBaccartList();
		getRobot().sendMessage(cgBaccartList);
	}
	
	/**
	 * 展示百家乐列表
	 */
	private void showBaccaratList(){
		for(BaccartRoomData tempBaccartRoomData:baccartRoomDataList){
			System.out.println(tempBaccartRoomData);
		}
	}
	
	/**
	 * 请求加入百家乐房间
	 */
	private void doRequestJoinBaccartRoom(int roomId){
		System.out.println("发送加入请求");
		CGBaccartJoin cgBaccartJoin = new CGBaccartJoin();
		cgBaccartJoin.setRoomId(roomId);
		getRobot().sendMessage(cgBaccartJoin);
	}
	
	/**
	 * 百家乐加入房间
	 */
	private void baccartRoomJoin(int roomState,PearlRoadData[] pearlRoadDataList,BaccartPlayerData[] playerDataList,BaccartBetData[] betDataList){
		this.baccartRoom.setRoomState(BaccartRoomState.valueOf(roomState));
		for(PearlRoadData tempPearlRoadData : pearlRoadDataList){
			this.baccartRoom.getPearlRoadDataList().add(tempPearlRoadData);
		}
		
		for(BaccartPlayerData tempBaccartPlayerData:playerDataList){
			ClientBaccaratRoomPlayer tempClientBaccaratRoomPlayer = ClientBaccaratRoomPlayer.convertFrom(tempBaccartPlayerData);
			this.baccartRoom.getBaccaratRoomPlayerList().add(tempClientBaccaratRoomPlayer);
		}
		
		for(BaccartBetData tempBaccartBetData:betDataList){
			this.baccartRoom.getBaccartBetDataList().add(tempBaccartBetData);
		}
		
	}
	
	/**
	 * 玩家加入
	 */
	private void baccartRoomPlayerJoin(BaccartPlayerData baccartPlayerData){
		this.baccartRoom.playerJoin(baccartPlayerData);
	}
	
	/**
	 * 玩家离开
	 * @param playerId
	 */
	private void baccartRoomPlayerExit(long playerId){
		this.baccartRoom.playerLeave(playerId);
	}
	
	/**
	 * 玩家押注
	 */
	private void playerBet(long playerId,BaccartBetData[] baccartBetDataArr){
		this.baccartRoom.playerBet(playerId, baccartBetDataArr);
	}
	
	/**
	 * 玩家站起
	 */
	private void playerStand(long playerId){
		this.baccartRoom.playerStand(playerId);
	}
	
	/**
	 * 玩家坐下
	 */
	private void playerSeat(long playerId,int pos){
		this.baccartRoom.playerSeat(playerId,pos);
	}
	
	/**
	 * 筹码同步
	 * @param playerId
	 * @param coins
	 */
	private void playerCoinsSync(long playerId, long coins) {
		// TODO Auto-generated method stub
		this.baccartRoom.playerCoinSync(playerId,coins);
	}
	
	/**
	 * 开始下注
	 */
	private void startBet(){
		this.baccartRoom.startBet();
	}
	/**
	 * 洗牌
	 */
	private void shuffle(int remainCards){
		this.baccartRoom.shuffle(remainCards);
	}
	
	/**
	 * 结算
	 */
	private void setttle(GCBaccartSettle gcBaccartSettle){
		
		this.baccartRoom.settle(gcBaccartSettle.getBankerCardList(),gcBaccartSettle.getPlayerCardList(),gcBaccartSettle.getPearlRoadData(),gcBaccartSettle.getSettleDataList());

	}
	
	/**
	 * 清理桌面
	 */
	private void clear() {
		// TODO Auto-generated method stub
		this.baccartRoom.clear();
	}
}
