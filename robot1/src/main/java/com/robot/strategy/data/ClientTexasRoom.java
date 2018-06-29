package com.robot.strategy.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.common.model.Card;
import com.common.model.Card.CardTypeEnum;
import com.common.model.Card.CardValueEnum;
import com.core.enums.IndexedEnum;
import com.core.heartbeat.ITickable;
import com.core.util.ArrayUtils;
import com.core.util.EnumUtil;
import com.core.util.MathUtils;
import com.core.util.RandomUtil;
import com.gameserver.player.Player;
import com.gameserver.texas.data.TexasHandCard;
import com.gameserver.texas.data.TexasPoolSettleInfoData;
import com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData;
import com.gameserver.texas.data.TexasHandCard.TexasHandCardEnum;
import com.gameserver.texas.enums.RoomPlayerState;
import com.gameserver.texas.enums.TexasRoomEnum;
import com.gameserver.texas.enums.TexasRoomState;
import com.gameserver.texas.msg.CGLeaveTexas;
import com.gameserver.texas.msg.CGTexasAddBet;
import com.gameserver.texas.msg.CGTexasAllIn;
import com.gameserver.texas.msg.CGTexasExit;
import com.gameserver.texas.msg.CGTexasFollow;
import com.gameserver.texas.msg.CGTexasGiveUp;
import com.gameserver.texas.msg.CGTexasLook;
import com.gameserver.texas.msg.CGTexasSeat;
import com.robot.Robot;
import com.robot.TexasDeck;

public class ClientTexasRoom implements ITickable{
		
		private TexasDeck texasDeck = new TexasDeck(CardValueEnum.TWO);
	
		private enum TexasRoomActionEnum implements IndexedEnum{
			LOOK(0),
			ADDBET(1),
			FOLLOW(2),
			GIVEUP(3),
			ALLIN(4);
			private int index;
			/** 按索引顺序存放的枚举数组 */
			private static final List<TexasRoomActionEnum> indexes = IndexedEnumUtil.toIndexes(TexasRoomActionEnum.values());

			private TexasRoomActionEnum(int index)
			{
				this.index= index;
			}
			
			@Override
			public int getIndex() {
				// TODO Auto-generated method stub
				return index;
			}
			

			public static TexasRoomActionEnum valueOf( final int index) 
			{
				return EnumUtil.valueOf(indexes, index);
			}
		}
	
		private Logger logger = Loggers.clientTexasRoomLogger;
		private Robot robot;
		/**房间id*/
		private long rid;
		private TexasRoomEnum texasRoomEnum;
		/**类型id*/
		private int typeId;
		/**大类型*/
		private int roomType;
		
		/**德州房间人员管理器*/
		private ClientTexasRoomPlayerManager playerManager;
		/**房间状态*/
		private TexasRoomState texasRoomState;
		/**公共牌*/
		private List<Card> publicCardList = new ArrayList<Card>(5);
		
		/**小盲注*/
		private int smallBlind;
		/**池底*/
		private int betPool = 0;
		/**当前池底*/
		private int currentBetPool = 0;
		
		private int selfPos = -1;
	
		/**庄家位置*/
		private int bankerPos = -1;
		/**小盲注位置*/
		private int smallBlindPos = -1;
		/**最高下注额度*/
		private long maxBet = 0;
		/**最高下注位置*/
		private int maxBetPos = -1;
		/**当前下注玩家*/
		private ClientRoomPlayer currentBetter;
		/**上一次计时时间*/
		private long lastFlagTime = 0;
		/**玩家数*/
		private int playerNums=0;
		

		private int[] weightTables = new int[TexasRoomActionEnum.values().length];
		
		private int currentConsiderTime = 0;
		private int minConsiderTime = 3000;
		private int maxConsiderTime = 5000;
		private boolean isSelfConsider = false;
		private TexasHandCardEnum minTexasHandCardEnum = TexasHandCardEnum.ONE_PAIR;
		private Card.CardValueEnum minCardValueEnum = Card.CardValueEnum.NINE;
		
		public ClientTexasRoom(Robot robot,TexasRoomEnum texasRoomEnum){
			this.robot = robot;
			this.texasRoomEnum = texasRoomEnum;
			playerManager = new ClientTexasRoomPlayerManager();
			this.init();
		}
		
	
		public void init() {
			// TODO Auto-generated method stub
			texasRoomState = TexasRoomState.INIT;
			logger.info("德州房间状态["+texasRoomState+"]");
		}

		
	
		
	
		public Robot getRobot() {
			return robot;
		}
	
		/**
		 * 获得房间号
		 * @return
		 */
		public long getRid(){
			return this.rid;
		}
		
		
		/**
		 * 获取房间小类型id
		 * @return
		 */
		public int getTypeId(){
			return this.getTypeId();
		}
		
		/**
		 * 获取房间大类型
		 * @return
		 */
		public int getRoomType(){
			return this.getRoomType();
		}
		
		/**
		 * 获取小盲注
		 * @return
		 */
		public int getSmallBlind(){
			return smallBlind;
		}
		
		public void setSmallBlind(int smallBlind) {
			this.smallBlind = smallBlind;
		}


		/**
		 * 获得玩家管理器
		 * @return
		 */
		public ClientTexasRoomPlayerManager getPlayerManager(){
			return playerManager;
		}
		
		/**
		 * 玩家进入
		 */
		public void playerJoin(ClientRoomPlayer roomPlayer)
		{
			if(roomPlayer.getPlayerId() == Long.parseLong(getRobot().getPassportId()))
				selfPos = roomPlayer.getPos();
			this.playerManager.addPlayer(roomPlayer);
			logger.info("玩家[" + roomPlayer.getPlayerId()+ "] 进入 房间： [" + this.getRid() + "]");
		}
		
		/**
		 * 看牌
		 */
		public void playerLook(int pos)
		{
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
			logger.info("玩家[" + roomPlayer.getPlayerId()+ "] 看牌");		
		}
		
		/**
		 * 跟注
		 * @param player
		 * @param bet
		 */
		public void playerFollow(int pos)
		{
	
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
			
					
			//检查需要的下注量
			long bet = maxBet- roomPlayer.getCurrentBet();	
			bet(roomPlayer,bet);
			logger.info("玩家[" + roomPlayer.getPlayerId()+ "] 跟注["+bet+"]");
		}
		
		private long followNeed(int pos){
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
			
			//检查需要的下注量
			return  maxBet- roomPlayer.getCurrentBet();	
		
		}
		
		/**
		 * 加注
		 * @param player
		 */
		public void playerAddBet(int pos,long addBet){
			
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
				
			//检查需要的下注量
			long bet = maxBet*2 - roomPlayer.getCurrentBet();
			if(addBet<bet)
			{
				logger.error("玩家["+roomPlayer.getPlayerId()+"]"+"加注筹码不足");
				return;
			}
			
			//检查是否有足够的筹码
			if(addBet > roomPlayer.getCoins())
			{
				logger.error("没有足够筹码不能跟注");
				return;
			}
			
			bet(roomPlayer,addBet);
			logger.info("玩家[" + roomPlayer.getPlayerId()+ "] 加注:["+addBet+"]");
		}
		
		/**
		 * 加注最小
		 * @param pos
		 * @return
		 */
		private long addBetNeed(int pos){
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
			
			//检查需要的下注量
			long bet = 0L;
			if(maxBet!=0)
				bet = maxBet*2 - roomPlayer.getCurrentBet();
			else
				bet = smallBlind*2-roomPlayer.getCurrentBet();
			return bet;
		}
		
		/**
		 * 弃牌
		 * @param player
		 */
		public void playerGiveUp(int pos)
		{
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
			roomPlayer.setPlayerState(RoomPlayerState.GIVEUP);
			logger.info("玩家[" + roomPlayer.getPlayerId()+ "] 弃牌");
		}
		
		/**
		 * allin
		 * @param player
		 */
		public void playerAllIn(int pos ,long allInBet)
		{
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
			long bet = roomPlayer.getCoins();
			//检查筹码数量
			if(bet== 0)
				return;
		
			bet(roomPlayer,bet);
			logger.info("玩家[" + roomPlayer.getPlayerId()+ "] 全压["+allInBet+"]");
		}
		
		/**
		 * 玩家离开
		 * @param player
		 */
		public void playerLeave(long  playerId){
			
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPlayerId(playerId); 
			playerManager.removePlayer(roomPlayer);
			logger.info("玩家[" + roomPlayer.getPlayerId()+ "] 离开");
		
		}
		
		/**
		 * 筹码同步
		 */
		public void playerCoinsSync(int pos,long coins){
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
			roomPlayer.setCoins(coins);
			logger.info("玩家[" + roomPlayer.getPlayerId()+ "] 金币同步["+coins+"]");
		}
		
		/**
		 * 结算
		 */
		public void playerSettle(TexasRoomPlayerSettleCardsInfoData[] texasRoomPlayerSettleCardsInfoDataList,TexasPoolSettleInfoData[] texasPoolSettleInfoDataList){
			for(int i=0;i<texasPoolSettleInfoDataList.length;i++){
				for(int j=0;j<texasPoolSettleInfoDataList[i].getWinnerList().length;j++){
					
					ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(texasPoolSettleInfoDataList[i].getWinnerList()[j].getPos());
					roomPlayer.win(texasPoolSettleInfoDataList[i].getWinnerList()[j].getWinBet());
					logger.info("玩家[" + roomPlayer.getPlayerId()+ "]赢得筹码["+texasPoolSettleInfoDataList[i].getWinnerList()[j].getWinBet()+"]");
				}
				
			}
			settle();
		}
		
		/**
		 * 结算放弃
		 */
		public void playerSettleGiveup(int pos ,int[] getSettlePoolList ){
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
			int winNum = 0;
			for(int i=0;i<getSettlePoolList.length;i++){
				winNum+=getSettlePoolList[i];
			}
			roomPlayer.win(winNum);
			logger.info("玩家[" + roomPlayer.getPlayerId()+ "]赢得筹码["+winNum+"]");
			settle();
		}
	
		/**
		 * 押注
		 * @param betNum
		 */
		public void bet(ClientRoomPlayer roomPlayer,long betNum){
			long tempBet =betNum+roomPlayer.getCurrentBet();
			if(tempBet > maxBet)
				maxBet = tempBet;
			betPool += betNum;
			currentBetPool+= betNum;
			roomPlayer.bet(betNum);
		}
		
		/**
		 * 准备开始
		 * @param smallBlind
		 */
		public void prepareStart(int smallBlind){
			logger.info("德州房间重新开始");
			texasRoomState = TexasRoomState.INIT;
			this.playerNums = this.playerManager.getRoomPlayerList().size();
			this.smallBlind = smallBlind;
			//设置玩家状态
			for(ClientRoomPlayer roomPlayer : playerManager.getRoomPlayerList())
			{
				roomPlayer.start();
			}
		}
		
		
		/**
		 * 开始
		 */
		public void start(int bankerPos)
		{
			this.bankerPos = bankerPos;
			logger.info("房间[" + getRid()+ "] 开始");
			texasRoomState = TexasRoomState.PREFLOP;
			logger.info("德州房间["+texasRoomState+"]");	
			startNextRound();
		}
	
		/**
		 * 小盲注
		 */
		public void startOnSmallBlind(int pos,long smallBlind){
			ClientRoomPlayer smallBlindPlayer = playerManager.getRoomPlayerByPos(pos);
			this.smallBlindPos = pos;
			bet(smallBlindPlayer,smallBlind);
		}
		
		/**
		 * 大盲注
		 */
		public void startOnBigBlind(int pos,long bigBlind){
			ClientRoomPlayer bigBlindPlayer = playerManager.getRoomPlayerByPos(pos);
		
			bet(bigBlindPlayer,bigBlind);		
			maxBet = this.smallBlind*2;
			
			maxBetPos = bigBlindPlayer.getPos();
		}
		
		public void startNextRound()
		{
			for(ClientRoomPlayer roomPlayer : playerManager.getRoomPlayerList())
			{
				roomPlayer.startNextRound();
			}
			
			currentBetPool = 0;
			maxBet = 0;
		}
	
		
		/**
		 * 发底牌
		 */
		public void buttomDeal(int[] cards)
		{
		
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(selfPos);
			for(int temp :cards)
			{
				Card tempCard = this.texasDeck.getCardByValue(temp);
				roomPlayer.getCardList().add(tempCard);
			}
			
			logger.info("接收底牌[");
			for(Card card:roomPlayer.getCardList())
			{
				logger.info(card.toString());
			}
			logger.info("]");
			
		}
		
		
		/**
		 * 发三张公共牌
		 */
		public void flop(int[] publicCards){
			texasRoomState = TexasRoomState.FLOP;
			startNextRound();
			logger.info("房间状态["+texasRoomState+"]");
			logger.info("公共牌[");	
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(selfPos);
			for(int temp :publicCards)
			{
				Card tempCard = this.texasDeck.getCardByValue(temp);
				publicCardList.add(tempCard);
				roomPlayer.getCardList().add(tempCard);
				logger.info(tempCard.toString());
			}
			logger.info("]");
		}
		
		/**
		 * 发第四张牌
		 */
		public void turn(int cardValue){
			texasRoomState = TexasRoomState.TURN;
			Card card = this.texasDeck.getCardByValue(cardValue);
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(selfPos);
			startNextRound();
			logger.info("房间状态["+texasRoomState+"]");
			logger.info("第4张牌[");	
			publicCardList.add(card);
			roomPlayer.getCardList().add(card);
			logger.info(card.toString());	
			logger.info("]");	
	
		}
		
		/**
		 * 发第五张牌
		 */
		public void river(int cardValue){
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(selfPos);
			Card card = this.texasDeck.getCardByValue(cardValue);
			texasRoomState = TexasRoomState.RIVER;
			startNextRound();
			logger.info("房间状态["+texasRoomState+"]");
			logger.info("第5张牌[");	
			publicCardList.add(card);
			roomPlayer.getCardList().add(card);
			logger.info(card.toString());	
			logger.info("]");	
		
		}
		
	
		/**
		 * 结算
		 */
		private void settle()
		{
	
			texasRoomState = TexasRoomState.END;
			logger.info("德州房间状态["+texasRoomState+"]");
		}
		
		/**
		 * 清理台面
		 */
		public void clear() {
			// TODO Auto-generated method stub
			//设置玩家状态
			for(ClientRoomPlayer roomPlayer : playerManager.getRoomPlayerList())
			{
				roomPlayer.clear();
			}
			publicCardList.clear();
			betPool = 0;
			currentBetPool = 0;
			maxBet = 0;
			maxBetPos = -1;
			currentBetter = null;
			
		}
		
		/**
		 * 轮到该玩家
		 */
		public void playerTurn(int pos)
		{
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(pos);
			logger.info("轮到玩家["+roomPlayer.getPlayerId()+"]");
			currentBetter = roomPlayer;
			if(pos == selfPos)
			{
				logger.info("位置["+pos+"]");
				selfConsider();

			}
		}

	
		
		//设置权重列表
		private void refreshWeightTable(){
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(selfPos);
			
			weightTables[TexasRoomActionEnum.LOOK.getIndex()] = 1;
			weightTables[TexasRoomActionEnum.FOLLOW.getIndex()] = 1;
			weightTables[TexasRoomActionEnum.GIVEUP.getIndex()] = 1;
			weightTables[TexasRoomActionEnum.ALLIN.getIndex()] = 1;
			weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 1;
			
			switch(texasRoomState){
			case PREFLOP:
			{
				int handCardWeight = TexasHandCard.weightForHandCard(roomPlayer.getCardList().get(0), roomPlayer.getCardList().get(1));
				weightTables[TexasRoomActionEnum.LOOK.getIndex()] = 0;
				
				if(handCardWeight<TexasHandCard.MIN_HAND_CARD_WEIGHT){
				
					weightTables[TexasRoomActionEnum.FOLLOW.getIndex()] = 0;
			
					weightTables[TexasRoomActionEnum.ALLIN.getIndex()] = 0;
					weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 0;
					return;
				}
				
				//激进玩家还是保守
				//判断是否能加注
//				if(addBetNeed(selfPos)<=roomPlayer.getCoins()){
//					weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 1;
//				}
//				
				weightTables[TexasRoomActionEnum.GIVEUP.getIndex()] = 0;
				//判断是否能跟注
				if(followNeed(selfPos)<=roomPlayer.getCoins()){
					weightTables[TexasRoomActionEnum.ALLIN.getIndex()] = 0;
					weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 0;
					
				}else{
					weightTables[TexasRoomActionEnum.FOLLOW.getIndex()] = 0;
					weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 0;
				}

			}
				break;
			case FLOP:
			case TURN:
			case RIVER:
			{
			
				if(maxBet!=0)
					weightTables[TexasRoomActionEnum.LOOK.getIndex()] = 0;
				else{
					weightTables[TexasRoomActionEnum.FOLLOW.getIndex()] = 0;
					weightTables[TexasRoomActionEnum.GIVEUP.getIndex()] = 0;
					
				}
				
				TexasHandCard texasHandCard = TexasHandCard.convertFrom(roomPlayer.getCardList());
				if(texasHandCard.getTexasHandCardEnum().getIndex()<minTexasHandCardEnum.getIndex()){
					weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 0;
					
					//看成牌率
					if(!ifNeedContinue()){
						weightTables[TexasRoomActionEnum.FOLLOW.getIndex()] = 0;
						weightTables[TexasRoomActionEnum.ALLIN.getIndex()] = 0;
						
						return;
					}
					
				}
				else if(texasHandCard.getTexasHandCardEnum().getIndex() == minTexasHandCardEnum.getIndex()){
					int tempCardValue = texasHandCard.getCardList().get(0).getCardValue().getIndex();
					if(tempCardValue!= Card.CardValueEnum.ACE.getIndex() && tempCardValue<minCardValueEnum.getIndex() ){
						
						weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 0;
						//看成牌率
						if(!ifNeedContinue()){
							weightTables[TexasRoomActionEnum.FOLLOW.getIndex()] = 0;
							weightTables[TexasRoomActionEnum.ALLIN.getIndex()] = 0;
							return;
						}
						
					}
					
				}
				else{
					weightTables[TexasRoomActionEnum.LOOK.getIndex()] = 0;
				}
				
				weightTables[TexasRoomActionEnum.GIVEUP.getIndex()] = 0;
				
				long addNeed = addBetNeed(selfPos);
				long followNeed = followNeed(selfPos);
				if(weightTables[TexasRoomActionEnum.FOLLOW.getIndex()]!=0){
					if(followNeed <= roomPlayer.getCoins()){
						weightTables[TexasRoomActionEnum.ALLIN.getIndex()] = 0;
					}else{
						weightTables[TexasRoomActionEnum.FOLLOW.getIndex()] = 0;
						weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 0;
					}
				}
				
				if(maxBet==0){
					if(addNeed <= roomPlayer.getCoins()){
						weightTables[TexasRoomActionEnum.ALLIN.getIndex()] = 0;
					}
					else{
						weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 0;
					}
				}else{
					if(addNeed <= roomPlayer.getCoins()){
						weightTables[TexasRoomActionEnum.ALLIN.getIndex()] = 0;
					}else{
						weightTables[TexasRoomActionEnum.ADDBET.getIndex()] = 0;
					}
				}
				
		
				
			}
			
				break;
			default:
				break;
			}
			

		}
		
		/**
		 * 判断成牌率和赌率
		 * @return
		 */
		private boolean ifNeedContinue() {
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(selfPos);
			if(this.publicCardList.size()==5){
				TexasHandCard tempPublicTexasHandCard = TexasHandCard.convertFrom(this.publicCardList);
				TexasHandCard tempPlayerTexasHandCard = TexasHandCard.convertFrom(roomPlayer.getCardList());
//				int result = tempPlayerTexasHandCard.compareTo(tempPublicTexasHandCard);
//				if(result==0){
					TexasHandCardEnum minTexasHandCardEnum = tempPlayerTexasHandCard.getTexasHandCardEnum();
					
					HashMap<TexasHandCardEnum,Integer> tempPublicComplementMaps= complementPublicCards();
					int remainCardNum = this.texasDeck.getCardList().size()-this.playerNums*2+this.publicCardList.size();
					int goodCardNums = 0;
					
					for(Entry<TexasHandCardEnum, Integer> tempEntry:tempPublicComplementMaps.entrySet()){
						if(tempEntry.getKey().getIndex()>minTexasHandCardEnum.getIndex()){
							goodCardNums+=tempEntry.getValue();
						}
					}
					
					long followNeed = followNeed(selfPos);
					
					double badCardRate= ((double)(remainCardNum-goodCardNums))/remainCardNum;
					double betRate = ((double)followNeed)/betPool;
					
					if(badCardRate>betRate)
						return true;
					else
						return false;
//				}else{
//					
//				}
				
				//return false;
			}
			
			// TODO Auto-generated method stub
			HashMap<TexasHandCardEnum,Integer> tempComplementMaps = complementPlayerCards();
			TexasHandCardEnum minTexasHandCardEnum = TexasHandCardEnum.THREE_KIND;
			if(this.publicCardList.size() == 3){
				//三张一样的卡
				boolean same = true;
				Card tempCard = null;
				for(Card card:this.publicCardList){
					if(tempCard == null){
						tempCard = card;
						continue;
					}
					if(tempCard.getCardValue() != card.getCardValue()){
						same =false;
						break;
					}
				}
				if(same){
					minTexasHandCardEnum = TexasHandCardEnum.STRAIGHT;
				}
			}
			else{
				HashMap<TexasHandCardEnum,Integer> tempPublicComplementMaps= complementPublicCards();
				for(Entry<TexasHandCardEnum, Integer> tempEntry:tempPublicComplementMaps.entrySet()){
					tempComplementMaps.put(tempEntry.getKey(), tempComplementMaps.get(tempEntry.getKey())-tempEntry.getValue());
				}
			}
			
			int remainCardNum = this.texasDeck.getCardList().size()-this.playerNums*2+this.publicCardList.size();
			int goodCardNums = 0;
			for(Entry<TexasHandCardEnum, Integer> tempEntry:tempComplementMaps.entrySet()){
				if(tempEntry.getKey().getIndex()>=minTexasHandCardEnum.getIndex()){
					goodCardNums+=tempEntry.getValue();
				}
			}
			
		
			long followNeed = followNeed(selfPos);
			if(roomPlayer.getCoins()<=followNeed){
				followNeed= roomPlayer.getCoins();
			}
			double goodCardRate  = 0.0f;
			if(this.publicCardList.size() == 3){
				goodCardRate= ((double)goodCardNums)/remainCardNum +((double)goodCardNums)/(remainCardNum-1) ;
			}else{
				goodCardRate= ((double)goodCardNums)/remainCardNum;
			}
			double betRate = ((double)followNeed)/betPool;
			if(goodCardRate>betRate)
				return true;
			return false;
		}


		private void selfConsider(){
			isSelfConsider = true;
			currentConsiderTime = RandomUtil.nextInt(minConsiderTime, maxConsiderTime);
			lastFlagTime = System.currentTimeMillis();
		}
		
		/**
		 * 玩家自己
		 */
		private void selfTurn()
		{
			isSelfConsider = false;
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(selfPos);
			
//			List<TexasRoomActionEnum> actionList = new ArrayList<TexasRoomActionEnum>();
//			//判断是否是第一个下注的
//			if(maxBet == 0)
//			{
//				
//				actionList.add(TexasRoomActionEnum.ADDBET);
//				actionList.add(TexasRoomActionEnum.GIVEUP);
//				actionList.add(TexasRoomActionEnum.LOOK);
//				
//				//doRequestLook();
//				return;
//			}
//			else
//			{
//				//判断是否有足够的筹码
//				long needFollowBet = maxBet - roomPlayer.getCurrentBet();
//				if(roomPlayer.getCoins() > needFollowBet)
//				{
//					actionList.add(TexasRoomActionEnum.ADDBET);
//					actionList.add(TexasRoomActionEnum.GIVEUP);
//					actionList.add(TexasRoomActionEnum.FOLLOW);
//				//	doRequestFollow();
//				}
//				else
//				{
//					actionList.add(TexasRoomActionEnum.ALLIN);
//					actionList.add(TexasRoomActionEnum.GIVEUP);
//				//	doRequestAllIn();
//				}
//			}
			refreshWeightTable();
			System.out.println("当前牌,"+roomPlayer.getCardList());
			System.out.println("当前操作,");
			for(int weight:weightTables){
				System.out.println(weight);
			}
			
			List<Integer> indexList = ArrayUtils.randomIndexByWeight(weightTables, 1, false);
			
			TexasRoomActionEnum texasRoomActionEnum =TexasRoomActionEnum.valueOf(indexList.get(0));
			
			switch(texasRoomActionEnum)
			{
				case LOOK:
				{
					doRequestLook();
				}
					break;
				case ADDBET:
				{
					
					
					long needAddBet =addBetNeed(selfPos);
					if(roomPlayer.getCoins() > needAddBet)
					{
						
						doRequestAddBet(needAddBet);
					}
					else
					{
						doRequestAllIn();
					}
				}
					break;
				case FOLLOW:
				{
					doRequestFollow();
				}
					break;
				case GIVEUP:
				{
					doRequestGiveUp();
				}
					break;
				case ALLIN:
				{
					doRequestAllIn();
				}
					break;
				default:
					break;
			}
		}
		
		private TexasRoomActionEnum randomAction(List<TexasRoomActionEnum> actionList)
		{
			return actionList.get((int)MathUtils.random(0.f,(float)actionList.size()));
		}
		
		
	
		/**
		 * 看牌
		 */
		private void doRequestLook()
		{
			logger.info("玩家["+getRobot().getPassportId()+"] 请求看牌");
			CGTexasLook cgTexasLook = new CGTexasLook();
			getRobot().sendMessage(cgTexasLook);
		}
		
		/**
		 * 跟注
		 */
		private void doRequestFollow()
		{
			logger.info("玩家["+getRobot().getPassportId()+"] 请求跟注");
			CGTexasFollow cgTexasFollow = new CGTexasFollow();
			getRobot().sendMessage(cgTexasFollow);
		}
		
		/**
		 * 加注
		 */
		private void doRequestAddBet(long addBet)
		{
			logger.info("玩家["+getRobot().getPassportId()+"] 请求加注["+addBet+"]");
			System.out.printf("请求加注：%d\n",addBet);
			CGTexasAddBet cgTexasAddBet = new CGTexasAddBet();
			cgTexasAddBet.setAddBet(addBet);
			getRobot().sendMessage(cgTexasAddBet);
		}
		
		/**
		 * 德州allin
		 */
		private void doRequestAllIn()
		{
			logger.info("玩家["+getRobot().getPassportId()+"] 请求全下");
			
			CGTexasAllIn cgTexasAllIn = new CGTexasAllIn();
			getRobot().sendMessage(cgTexasAllIn);
		}
		
		/**
		 * 德州弃牌
		 */
		private void doRequestGiveUp()
		{
			logger.info("玩家["+getRobot().getPassportId()+"] 请求弃牌");
		
			CGTexasGiveUp cgTexasGiveUp = new CGTexasGiveUp();
			getRobot().sendMessage(cgTexasGiveUp);
		}
		
		/**
		 * 德州坐下
		 */
		private void  doRequestSeat(int pos){
			logger.info("玩家["+getRobot().getPassportId()+"] 请求坐下");
			
			CGTexasSeat cgTexasSeat = new CGTexasSeat();
			cgTexasSeat.setPos(pos);
			getRobot().sendMessage(cgTexasSeat);
		}
		
		/**
		 * 德州站起
		 */
		private void  doRequestLeave()
		{
			logger.info("玩家["+getRobot().getPassportId()+"] 请求离开");
			
			CGLeaveTexas cgLeaveTexas = new CGLeaveTexas();
			
			getRobot().sendMessage(cgLeaveTexas);
		}	
		/**
		 * 德州离开
		 */
		private void doRequestExit(){
			logger.info("玩家["+getRobot().getPassportId()+"] 请求退出");
			
			CGTexasExit cgTexasExit = new CGTexasExit();
			
			getRobot().sendMessage(cgTexasExit);
		}


		@Override
		public void tick() {
			// TODO Auto-generated method stub
			
			if(!isSelfConsider)
				return;
			long now = System.currentTimeMillis();
			long diff = now - lastFlagTime;
			if(diff > currentConsiderTime)
				selfTurn();
		}

		
		//补牌数
		public HashMap<TexasHandCardEnum,Integer> complementMaps(List<Card> aCardList){
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(selfPos);
			
			HashMap<TexasHandCardEnum,Integer> tempMap = new HashMap<TexasHandCardEnum,Integer>();
			List<Card> tempCardList = new ArrayList<Card>();
			tempCardList.addAll(aCardList);
			for(Card card :this.texasDeck.getCardList()){
				if(roomPlayer.getCardList().contains(card)){
					continue;
				}
				tempCardList.add(card);
				TexasHandCard texasHandCard=TexasHandCard.convertFrom(tempCardList);
				Integer tempNum =tempMap.get(texasHandCard.getTexasHandCardEnum());
				if(tempNum==null){
					tempNum=0;
				}
				tempMap.put(texasHandCard.getTexasHandCardEnum(), tempNum+1);
				tempCardList.remove(card);
			}
			
			return tempMap;
		}
		
		//底牌补牌数
		public HashMap<TexasHandCardEnum,Integer> complementPublicCards(){
			return complementMaps(this.publicCardList);
		}
		
		//玩家手牌补牌数
		public HashMap<TexasHandCardEnum,Integer> complementPlayerCards(){
			ClientRoomPlayer roomPlayer = playerManager.getRoomPlayerByPos(selfPos);
			return complementMaps(roomPlayer.getCardList());
		}
}
