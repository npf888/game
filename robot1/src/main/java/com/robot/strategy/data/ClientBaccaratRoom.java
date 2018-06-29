package com.robot.strategy.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.core.util.MathUtils;
import com.gameserver.baccart.data.BaccartBetData;
import com.gameserver.baccart.data.BaccartPlayerData;
import com.gameserver.baccart.data.BaccartSettleData;
import com.gameserver.baccart.data.PearlRoadData;
import com.gameserver.baccart.enums.BaccartBetType;
import com.gameserver.baccart.enums.BaccartPairEnum;
import com.gameserver.baccart.enums.BaccartResultEnum;
import com.gameserver.baccart.enums.BaccartRoomState;
import com.gameserver.baccart.msg.CGBaccartBet;
import com.robot.Robot;

public class ClientBaccaratRoom {
	
	private Robot robot;
	
	private List<ClientBaccaratRoomPlayer> baccaratRoomPlayerList = new ArrayList<ClientBaccaratRoomPlayer>();
	
	private List<BaccartBetData> baccartBetDataList = new ArrayList<BaccartBetData>();
	
	private List<PearlRoadData> pearlRoadDataList = new ArrayList<PearlRoadData>();
	private BaccartRoomState roomState;
	
	public ClientBaccaratRoom(Robot robot){
		this.robot = robot;
	}
	
	public List<BaccartBetData> getBaccartBetDataList() {
		return baccartBetDataList;
	}
	public void setBaccartBetDataList(List<BaccartBetData> baccartBetDataList) {
		this.baccartBetDataList = baccartBetDataList;
	}

	public List<PearlRoadData> getPearlRoadDataList() {
		return pearlRoadDataList;
	}
	public void setPearlRoadDataList(List<PearlRoadData> pearlRoadDataList) {
		this.pearlRoadDataList = pearlRoadDataList;
	}
	public BaccartRoomState getRoomState() {
		return roomState;
	}
	public void setRoomState(BaccartRoomState roomState) {
		this.roomState = roomState;
	}
	
	public List<ClientBaccaratRoomPlayer>  getBaccaratRoomPlayerList(){
		return this.baccaratRoomPlayerList;
	}
	
	
	public void playerJoin(BaccartPlayerData baccartPlayerData ){
		System.out.println("玩家["+baccartPlayerData.getPlayerId()+"]加入");
		ClientBaccaratRoomPlayer tempClientBaccaratRoomPlayer =  ClientBaccaratRoomPlayer.convertFrom(baccartPlayerData);
		this.baccaratRoomPlayerList.add(tempClientBaccaratRoomPlayer);
	}
	
	public void playerLeave(long playerId){
		System.out.println("玩家["+playerId+"]离开");
		Iterator<ClientBaccaratRoomPlayer> tempIter=this.baccaratRoomPlayerList.iterator();
		while(tempIter.hasNext()){
			ClientBaccaratRoomPlayer tempClientBaccaratRoomPlayer =tempIter.next();
			if(tempClientBaccaratRoomPlayer.getPlayerId() == playerId){
				tempIter.remove();
				break;
			}
		}
	}
	
	public void playerBet(long playerId,BaccartBetData[] betDataArr){
		ClientBaccaratRoomPlayer tempBaccaratRoomPlayer = clientBaccaratRoomPlayerByPlayerId(playerId);
		for(BaccartBetData tempBaccartBetData : betDataArr){
			System.out.println("玩家["+playerId+"],下注["+BaccartBetType.valueOf(tempBaccartBetData.getBetType())+"],筹码["+tempBaccartBetData.getBetNum()+"]");

			tempBaccaratRoomPlayer.bet(tempBaccartBetData.getBetNum());
		}
	}
	
	public void playerStand(long playerId) {
		// TODO Auto-generated method stub
		ClientBaccaratRoomPlayer tempBaccaratRoomPlayer = clientBaccaratRoomPlayerByPlayerId(playerId);
		tempBaccaratRoomPlayer.setPos(-1);
	}
	public void playerSeat(long playerId, int pos) {
		// TODO Auto-generated method stub
		ClientBaccaratRoomPlayer tempBaccaratRoomPlayer = clientBaccaratRoomPlayerByPlayerId(playerId);
		tempBaccaratRoomPlayer.setPos(pos);
	}
	
	public void playerCoinSync(long playerId, long coins) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void shuffle(int remainCards){
		System.out.println("洗牌中，剩余牌["+remainCards+"]");
	}
	
	public void startBet() {
		// TODO Auto-generated method stub
		System.out.println("开始下注");
		CGBaccartBet cgBaccartBet = new CGBaccartBet();
		BaccartBetData[] tempBaccartBetDataArr = new BaccartBetData[1];
		BaccartBetData tempBaccartBetData = new BaccartBetData();
		
		tempBaccartBetData.setBetNum(10000);
	//	int randomBetType = MathUtils.random(BaccartBetType.BANKER.getIndex(), BaccartBetType.PLAYER_PAIR.getIndex());
		tempBaccartBetData.setBetType(BaccartBetType.BANKER.getIndex());
		tempBaccartBetDataArr[0]= tempBaccartBetData;
		cgBaccartBet.setBetDataList(tempBaccartBetDataArr);
		this.robot.sendMessage(cgBaccartBet);
	}
	
	public void settle(int[] is, int[] is2, PearlRoadData pearlRoadData, BaccartSettleData[] baccartSettleDatas) {
		// TODO Auto-generated method stub
		System.out.println("结算中");
		System.out.println("对子["+BaccartPairEnum.valueOf(pearlRoadData.getBaccartPair())+"]");
		System.out.println("结果["+BaccartResultEnum.valueOf( pearlRoadData.getBaccartResult())+"]");
		for(BaccartSettleData tempBaccartSettleData : baccartSettleDatas){
			System.out.println("玩家["+tempBaccartSettleData.getPlayerId()+"],赢得筹码["+tempBaccartSettleData.getWinCoins()+"]");
			ClientBaccaratRoomPlayer tempBaccaratRoomPlayer = clientBaccaratRoomPlayerByPlayerId(tempBaccartSettleData.getPlayerId());
			tempBaccaratRoomPlayer.win(tempBaccartSettleData.getWinCoins());
		}
	}
	
	public void clear(){
		System.out.println("清理桌面");
	}
	
	public ClientBaccaratRoomPlayer clientBaccaratRoomPlayerByPlayerId(long playerId){
		Iterator<ClientBaccaratRoomPlayer> tempIter=this.baccaratRoomPlayerList.iterator();
		while(tempIter.hasNext()){
			ClientBaccaratRoomPlayer tempClientBaccaratRoomPlayer =tempIter.next();
			if(tempClientBaccaratRoomPlayer.getPlayerId() == playerId){
				return tempClientBaccaratRoomPlayer;
			}
		}
		return null;
	}

	
	
}
