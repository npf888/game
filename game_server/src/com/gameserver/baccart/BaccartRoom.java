package com.gameserver.baccart;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.common.model.Card;
import com.core.util.MathUtils;
import com.core.util.TimeUtils;
import com.gameserver.baccart.data.BaccartBetData;
import com.gameserver.baccart.data.BaccartCoinSyncData;
import com.gameserver.baccart.data.BaccartJackpotData;
import com.gameserver.baccart.data.BaccartSettleData;
import com.gameserver.baccart.data.PearlRoadData;
import com.gameserver.baccart.enums.BaccartBetType;
import com.gameserver.baccart.enums.BaccartPairEnum;
import com.gameserver.baccart.enums.BaccartResultEnum;
import com.gameserver.baccart.enums.BaccartRoomState;
import com.gameserver.baccart.template.JackpotTemplate;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanBaccartManager;
import com.gameserver.player.Player;

/**
 * 百家乐房间
 * 
 * @author wayne
 *
 */
public class BaccartRoom implements InitializeRequired,
		DestroyRequired {
	// 最少洗牌数
	private static final int MIN_CARDS_NUM = 6;
	// 闲家最大补牌点数
	private static final int COMPLEMENT_NUM = 6;

	// 天牌点数
	private static final int NATURAL_CARD_NUM = 8;
	// 庄家税
	private static final float BANKER_TAX = 0.05f;
	// jackpot time
	private static final int MIN_CONTINUE_TIME = 4;

	private final String LOG_DIR = "logs/baccart";
	private Logger logger;

	private long shuffleTime = 2;
	private long betTime = 10;
	private long settleTime = 10;
	private long fillTime = 1;
	private long jackpotTime = 2;
	private long clearTime = 1;
	private int seatNum = 14;

	/** 百家乐牌组 */
	private BaccartRoomModel baccarRoomModel;
	private BaccartDeck baccartDeck = new BaccartDeck();
	private BaccartRoomPlayerManager baccartRoomPlayerManager = new BaccartRoomPlayerManager(seatNum);
	private BaccartRoomState baccartRoomState = BaccartRoomState.INIT;

	// 洗牌的时候清除
	private List<PearlRoadData> pearlRoadDataList = new ArrayList<PearlRoadData>();

	// 每盘结束后清除
	private List<Card> bankerCardList = new ArrayList<Card>();
	private List<Card> playerCardList = new ArrayList<Card>();
	private List<BaccartBetType> baccartBetTypeList = new ArrayList<BaccartBetType>();
	private List<BaccartSettleData> baccartSettleDataList = new ArrayList<BaccartSettleData>();
	private List<BaccartJackpotData> baccartJackpotDataList = new ArrayList<BaccartJackpotData>();
	private HashMap<BaccartBetType, Long> baccartBetDataMap = new HashMap<BaccartBetType, Long>();
	private BaccartBetType predictBetType = BaccartBetType.BANKER;

	private long lastFlagTime;

	private int id;
	private int minCarry;
	private int maxCarry;
	private int totalNum = 100;
	private int taxRate;
	/** 进入等级   **/
	private int openLv;
	

	public BaccartRoom(BaccartRoomModel baccartRoomModel) {
		this.baccarRoomModel = baccartRoomModel;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		logger.info("房间摧毁前");
		logger.info(this.buildPlayerBetLog());
		
		this.baccarRoomModel.setModified();
		Iterator<Player> playerIter =  this.getBaccartRoomPlayerManager().getPlayerList().iterator();
		
		while(playerIter.hasNext()){
			Player player = playerIter.next();
			Globals.getBaccartService().onBaccartRoomPlayerExit(this, player);

			Human human = player.getHuman();
			HumanBaccartManager humanBaccartManager = player.getHuman()
					.getHumanBaccartManager();
			int tempPos = humanBaccartManager.getPos();
			
		
			this.baccartRoomPlayerManager.returnPos(tempPos);

			playerIter.remove();
			
			String detailReason = MessageFormat.format(
					LogReasons.GoldLogReason.BACCARAT_EXIT.getReasonText(),
					humanBaccartManager.getCoins());

			human.giveMoney(humanBaccartManager.getCoins(), Currency.GOLD, true,
					LogReasons.GoldLogReason.BACCARAT_EXIT, detailReason, -1, 1);
			humanBaccartManager.leave();

			// 发送玩家加入信息
			logger.info("玩家[" + player.getPassportId() + "],离开百家乐房间["
					+ this.getId() + "]");
		}
	
		logger.info("房间摧毁");
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		logger = Loggers.customLogger(LOG_DIR, String.valueOf(this.getId()));
		shuffleTime = Globals.getBaccartService().getBaccartConfigTemplate()
				.getShuffleTime();
		betTime = Globals.getBaccartService().getBaccartConfigTemplate()
				.getBetTime();
		settleTime = Globals.getBaccartService().getBaccartConfigTemplate()
				.getDealTime()
				+ Globals.getBaccartService().getBaccartConfigTemplate()
						.getShowTime();
		jackpotTime = Globals.getBaccartService().getBaccartConfigTemplate().getJackpotTime();
	}

	
	public void tick()  {
		// TODO Auto-generated method stub
		
		checkState();

	}

	/**
	 * 检查房间状态
	 */
	private void checkState() {
		long now = Globals.getTimeService().now();
		long diff = now - lastFlagTime;
		switch (baccartRoomState) {
		case INIT:
			break;
		case SHUFFLE: {
			if (diff > shuffleTime * TimeUtils.SECOND) {
				startBet();
			}
		}
			break;
		case BET: {
			if (diff > betTime * TimeUtils.SECOND) {
				settle();
			}
		}
			break;

		case SETTLE: {
			if (diff > settleTime * TimeUtils.SECOND) {
				clear();
			}
		}
			break;
		
		case CLEAR: {
			if (diff > clearTime * TimeUtils.SECOND) {
				afterClear();
			}
		}
			break;
		default:
			break;
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BaccartRoomModel getBaccarRoomModel() {
		return this.baccarRoomModel;
	}

	public int getMinCarry() {
		return minCarry;
	}

	public void setMinCarry(int minCarry) {
		this.minCarry = minCarry;
	}

	public int getMaxCarry() {
		return maxCarry;
	}

	public void setMaxCarry(int maxCarry) {
		this.maxCarry = maxCarry;
	}

	public int getSeatNum() {
		return this.seatNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}

	public BaccartDeck getBaccartDeck() {
		return baccartDeck;
	}

	public BaccartRoomState getBaccartRoomState() {
		return baccartRoomState;
	}

	public void setBaccartRoomState(BaccartRoomState baccartRoomState) {
		this.baccartRoomState = baccartRoomState;
	}

	public void setBaccartDeck(BaccartDeck baccartDeck) {
		this.baccartDeck = baccartDeck;
	}

	public BaccartRoomPlayerManager getBaccartRoomPlayerManager() {
		return baccartRoomPlayerManager;
	}

	public void setBaccartRoomPlayerManager(
			BaccartRoomPlayerManager baccartRoomPlayerManager) {
		this.baccartRoomPlayerManager = baccartRoomPlayerManager;
	}

	public HashMap<BaccartBetType, Long> getBaccartBetDataMap() {
		return this.baccartBetDataMap;
	}

	public List<PearlRoadData> getPearlRoadDataList() {
		return this.pearlRoadDataList;
	}

	public List<BaccartSettleData> getBaccartSettleDataList() {
		return this.baccartSettleDataList;
	}
	
	

	public int getOpenLv() {
		return openLv;
	}

	public void setOpenLv(int openLv) {
		this.openLv = openLv;
	}

	/**
	 * 玩家加入
	 * 
	 * @param player
	 */
	public void playerJoin(Player player) {
		
		Human human = player.getHuman();
		
		HumanBaccartManager humanBaccartManager = human.getHumanBaccartManager();
        // 获取位置
		int pos = this.baccartRoomPlayerManager.assignPos(-1);
		
		// 判断金钱
		long tempExchange = 0L;
		long gold = human.getGold();
		if (gold > this.getMaxCarry()) {
			tempExchange = this.getMaxCarry();
		} else {
			tempExchange = gold;
		}

		String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BACCARAT_ENTER.getReasonText(),tempExchange);
		human.costMoney(tempExchange, Currency.GOLD, true,
				LogReasons.GoldLogReason.BACCARAT_ENTER, detailReason, -1, 1);

		humanBaccartManager.setCoins(tempExchange);
		humanBaccartManager.setPos(pos);
		humanBaccartManager.setBaccartRoom(this);
		this.baccartRoomPlayerManager.addPlayer(player);

		// 发送玩家加入信息
		logger.info("玩家[" + player.getPassportId() + "],百家乐房间[" + this.getId()
				+ "]分配座位[" + pos + "]");

		Globals.getBaccartService().onBaccartRoomPlayerJoin(this, player);
		afterPlayerJoin();
		//百家乐玩游戏成就
		human.getHumanAchievementManager().updateBaccartCumulativeNum();
	}

	/**
	 * 玩家加入后
	 */
	public void afterPlayerJoin() {

		switch (baccartRoomState) {
		case INIT: {
			shuffle();
		}
			break;
		case SHUFFLE:
			break;
		case BET:
			break;

		case SETTLE:
			break;

		case CLEAR:
			break;
		default:
			break;
		}
	}
	
	
	public boolean ifHasSeat(int pos){
		return this.baccartRoomPlayerManager.hasPos(pos);
	}

	/**
	 * 玩家坐下
	 * 
	 * @param player
	 */
	public void playerSeat(Player player, int pos) {

		HumanBaccartManager humanBaccartManager = player.getHuman()
				.getHumanBaccartManager();
		this.baccartRoomPlayerManager.returnPos(humanBaccartManager.getPos());
		pos = this.baccartRoomPlayerManager.assignPos(pos);
		humanBaccartManager.setPos(pos);

		// 发送玩家加入信息
		logger.info("玩家[" + player.getPassportId() + "],百家乐房间[" + this.getId()
				+ "]坐下座位[" + pos + "]");

		Globals.getBaccartService().onBaccartRoomPlayerSeat(this, player, pos);

	}

	/**
	 * 下注
	 */
	public void playerBet(Player player, BaccartBetData[] betDataList) {
		HumanBaccartManager humanBaccartManager = player.getHuman()
				.getHumanBaccartManager();

		for (BaccartBetData tempBaccartBetData : betDataList) {
			BaccartBetType tempBaccartBetType = BaccartBetType
					.valueOf(tempBaccartBetData.getBetType());
			Long tempBetNumLong = this.baccartBetDataMap.get(BaccartBetType
					.valueOf(tempBaccartBetData.getBetType()));
			long tempBetNum = 0;
			if (tempBetNumLong != null) {
				tempBetNum = tempBetNumLong;
			}
			tempBetNum += tempBaccartBetData.getBetNum();
			this.baccartBetDataMap.put(tempBaccartBetType, tempBetNum);
		}

		humanBaccartManager.bet(betDataList);

		// 发送玩家加入信息
		logger.info("玩家[" + player.getPassportId() + "],百家乐房间[" + this.getId()
				+ "]下注");

		Globals.getBaccartService().onBaccartRoomPlayerBet(this, player,
				betDataList);

	}

	/**
	 * 玩家站起
	 * 
	 * @param player
	 */
	public void playerStand(Player player) {
		HumanBaccartManager humanBaccartManager = player.getHuman()
				.getHumanBaccartManager();
		int tempPos = humanBaccartManager.getPos();
		this.baccartRoomPlayerManager.returnPos(tempPos);

		humanBaccartManager.setPos(-1);

		// 发送玩家加入信息
		logger.info("玩家[" + player.getPassportId() + "],百家乐房间[" + this.getId()
				+ "]站起位置[" + tempPos + "]");

		Globals.getBaccartService().onBaccartRoomPlayerStand(this, player);

	}

	/**
	 * 玩家离开
	 * 
	 * @param player
	 */
	public void playerLeave(Player player) {
		// 发送玩家信息
		Globals.getBaccartService().onBaccartRoomPlayerExit(this, player);

		Human human = player.getHuman();
		HumanBaccartManager humanBaccartManager = player.getHuman()
				.getHumanBaccartManager();
		int tempPos = humanBaccartManager.getPos();
		
	
		this.baccartRoomPlayerManager.returnPos(tempPos);

		this.baccartRoomPlayerManager.removePlayer(player);

		String detailReason = MessageFormat.format(
				LogReasons.GoldLogReason.BACCARAT_EXIT.getReasonText(),
				humanBaccartManager.getCoins());

		human.giveMoney(humanBaccartManager.getCoins(), Currency.GOLD, true,
				LogReasons.GoldLogReason.BACCARAT_EXIT, detailReason, -1, 1);
		
		humanBaccartManager.leave();

		// 发送玩家加入信息
		logger.info("玩家[" + player.getPassportId() + "],离开百家乐房间["
				+ this.getId() + "]");

		afterPlayerLeave();
	}

	public void afterPlayerLeave() {
		switch (baccartRoomState) {
		case INIT:
			break;
		case SHUFFLE:
		case BET:
		case SETTLE:
		case CLEAR:
			break;
		default:
			break;

		}
	}

	/**
	 * 洗牌
	 */
	private void shuffle() {
		logger.info("百家乐房间开始洗牌");
		this.pearlRoadDataList.clear();
		long now = Globals.getTimeService().now();
		baccartRoomState = BaccartRoomState.SHUFFLE;
		lastFlagTime = now;
		this.baccartDeck.shuffle();
		int remainCardsNum = this.baccartDeck.getCardList().size();
		// 发送洗牌接口
		Globals.getBaccartService().onBaccartRoomShuffle(this, remainCardsNum);
	}

	/**
	 * 开始下注
	 */
	private void startBet() {
		logger.info("百家乐房间开始下注");
		long now = Globals.getTimeService().now();
		baccartRoomState = BaccartRoomState.BET;
		lastFlagTime = now;
		// 发送开始下注接口
		Globals.getBaccartService().onBaccartRoomStartBet(this);
	}

	/**
	 * 结算
	 */
	private void settle() {
		logger.info("百家乐房间结算中");
		logger.info(this.buildPlayerBetLog());

		long now = Globals.getTimeService().now();
		baccartRoomState = BaccartRoomState.SETTLE;

		lastFlagTime = now;

		//cheatCards();//AAA 一直庄家赢 上线的时候注掉
		 if(ifSystemControl()){
			 logger.info("房间触发系统保护，结果["+this.predictBetType+"]");
			 cheatCards();
		 }
		 else{
			 dealCards();
		 }
		
		int tempNumOfPlayerCards = this.playerCardList.size();
		int tempNumOfBankerCards = this.bankerCardList.size();
		this.settleTime += this.fillTime*(tempNumOfPlayerCards+tempNumOfBankerCards-4);
		logger.info(this.buildDealCardsLog());
		calculate();
		calculateJackpot();
		
		
	}

	/**
	 * 启动系统保护
	 * 
	 * @return
	 */
	private boolean ifSystemControl() {

		long needPayBanker = 0;
		long needPayPlayer = 0;
		long needPayDraw = 0;

		long bankPairBet = 0;
		Long bankPairBetLong = baccartBetDataMap
				.get(BaccartBetType.BANKER_PAIR);
		if (bankPairBetLong != null) {
			bankPairBet = bankPairBetLong;
		}
		needPayBanker += bankPairBet * BaccartBetType.BANKER_PAIR.getRate();
		needPayPlayer += bankPairBet * BaccartBetType.BANKER_PAIR.getRate();
		needPayDraw += bankPairBet * BaccartBetType.BANKER_PAIR.getRate();

		long playerPairBet = 0;
		Long playerPairBetLong = baccartBetDataMap
				.get(BaccartBetType.PLAYER_PAIR);
		if (playerPairBetLong != null) {
			playerPairBet = playerPairBetLong;
		}

		needPayBanker += playerPairBet * BaccartBetType.PLAYER_PAIR.getRate();
		needPayPlayer += playerPairBet * BaccartBetType.PLAYER_PAIR.getRate();
		needPayDraw += playerPairBet * BaccartBetType.PLAYER_PAIR.getRate();

		long drawBet = 0;
		Long drawBetLong = baccartBetDataMap.get(BaccartBetType.DRAW);
		if (drawBetLong != null) {
			drawBet = drawBetLong;
		}

		needPayDraw += drawBet * BaccartBetType.DRAW.getRate();

		long bankerBet = 0;
		Long bankerBetLong = baccartBetDataMap.get(BaccartBetType.BANKER);
		if (bankerBetLong != null) {
			bankerBet = bankerBetLong;
		}
		needPayBanker += bankerBet * BaccartBetType.BANKER.getRate();

		long playerBet = 0;
		Long playerBetLong = baccartBetDataMap.get(BaccartBetType.PLAYER);
		if (playerBetLong != null) {
			playerBet = playerBetLong;
		}
		
		needPayPlayer += playerBet * BaccartBetType.PLAYER.getRate();

		long tempMax = Math.max(needPayBanker, needPayPlayer);
		tempMax = Math.max(tempMax, needPayDraw);

		long tempMin = Math.min(needPayBanker, needPayPlayer);
		tempMin = Math.min(tempMin, needPayDraw);
		
		if (tempMin == needPayBanker) {
			this.predictBetType = BaccartBetType.BANKER;
		} else if (tempMin == needPayPlayer) {
			this.predictBetType = BaccartBetType.PLAYER;
		} else {
			this.predictBetType = BaccartBetType.DRAW;
		}

		return tempMax > this.baccarRoomModel.getStock();
	}

	/**
	 * 发牌
	 */
	private void dealCards() {

		for (int i = 0; i < 2; i++) {
			this.bankerCardList.add(this.baccartDeck.getNextCard());
			this.playerCardList.add(this.baccartDeck.getNextCard());
		}

		// 判断是否是天牌
		if (ifPlayerNaturalCard()) {
			return;
		}
		// 判断庄家是否是天牌
		if (ifBankerNaturalCard()) {
			return;
		}

		// 判断闲家牌型 是否补牌
		if (ifPlayerShouldComplementCard()) {
			this.playerCardList.add(this.baccartDeck.getNextCard());
		}

		// 判断庄家是否是否补牌
		if (ifBankerShouldComplementCard()) {
			this.bankerCardList.add(this.baccartDeck.getNextCard());
		}

	}

	/**
	 * 获取cheat card
	 */
	private void cheatCards() {
		this.playerCardList.add(this.baccartDeck.getNextCard());
		this.bankerCardList.add(this.baccartDeck.getNextCard());
		switch (this.predictBetType) {
		case BANKER: {
			this.cheatComplementPlayerCard(NATURAL_CARD_NUM, -1);
			int tempPlayerNum = this.combineCard(this.playerCardList);
			this.cheatComplementBankerCard(tempPlayerNum, 1);
		}
			break;
		case PLAYER: {
			this.cheatComplementPlayerCard(NATURAL_CARD_NUM, -1);
			int tempPlayerNum = this.combineCard(this.playerCardList);
			this.cheatComplementBankerCard(tempPlayerNum, -1);
		}
			break;
		case DRAW: {
			this.cheatComplementPlayerCard(-1, 1);
			int tempPlayerNum = this.combineCard(this.playerCardList);
			this.cheatComplementBankerCard(tempPlayerNum, 0);
		}
			break;
		default:
			break;
		}
		// 判断是否是天牌
		if (ifPlayerNaturalCard()) {
			return;
		}
		// 判断庄家是否是天牌
		if (ifBankerNaturalCard()) {
			return;
		}

		switch (this.predictBetType) {
		case BANKER: {
			if (ifPlayerShouldComplementCard()){
				this.cheatComplementPlayerCard(NATURAL_CARD_NUM, -1);
			}
			
			if (ifBankerShouldComplementCard()){
				int tempPlayerNum = this.combineCard(this.playerCardList);
				this.cheatComplementBankerCard(tempPlayerNum, 1);
			}
			
		}
			break;
		case PLAYER: {
			if (ifPlayerShouldComplementCard()){
				this.cheatComplementPlayerCard(NATURAL_CARD_NUM, -1);
			}
			
			if (ifBankerShouldComplementCard()){
				int tempPlayerNum = this.combineCard(this.playerCardList);
				this.cheatComplementBankerCard(tempPlayerNum, -1);
			}
		
		}
			break;
		case DRAW: {
			if (ifPlayerShouldComplementCard()){
				this.cheatComplementPlayerCard(-1, 1);
			}
			
			if (ifBankerShouldComplementCard()){
				int tempPlayerNum = this.combineCard(this.playerCardList);
				this.cheatComplementBankerCard(tempPlayerNum, 0);
			}
		
		
		}
			break;
		default:
			break;
		}

		
	}

	/**
	 * 获取需要的牌
	 * 
	 * @return
	 */
	private List<Card.CardValueEnum> getNeedCardValueList(List<Card> aCardList,
			int atLeastNum, int result) {

		boolean maybePair = false;
		if (aCardList.size() == 1) {
			maybePair = true;
		}

		List<Card.CardValueEnum> tempCardValueEnumList = new ArrayList<Card.CardValueEnum>();
		int num = this.combineCard(aCardList);

		for (Card.CardValueEnum tempCardValueEnum : Card.CardValueEnum.values()) {
			if (tempCardValueEnum == Card.CardValueEnum.RED_JOKER)
				continue;
			if (tempCardValueEnum == Card.CardValueEnum.BLACK_JOKER)
				continue;
			if (maybePair) {
				Card tempFirstCard = aCardList.get(0);
				if (tempFirstCard.getCardValue() == tempCardValueEnum)
					continue;
			}

			int tempCardValue = tempCardValueEnum.getIndex();
			if (tempCardValue >= 10)
				tempCardValue = 0;
			int tempNum = num + tempCardValue;

			if (tempNum >= 10)
				tempNum -= 10;

			if (result == -1) {
				if (tempNum >= atLeastNum)
					continue;
			} else if (result == 1) {
				if (tempNum <= atLeastNum)
					continue;
			} else if (result == 0) {
				if (tempNum != atLeastNum)
					continue;
			} else {
				continue;
			}

			tempCardValueEnumList.add(tempCardValueEnum);
		}
		return tempCardValueEnumList;
	}

	/**
	 * 结算输赢
	 */
	private void calculate() {

		// 判断是否庄闲对
		PearlRoadData tempPearlRoadData = new PearlRoadData();
		int baccartPair = 0;
		if (ifBankerPair()) {
			baccartPair |= BaccartPairEnum.BANKER_PAIR.getIndex();

			baccartBetTypeList.add(BaccartBetType.BANKER_PAIR);
		}

		if (ifPlayerPair()) {
			baccartPair |= BaccartPairEnum.PLAYER_PAIR.getIndex();
			baccartBetTypeList.add(BaccartBetType.PLAYER_PAIR);
		}

		BaccartResultEnum tempBaccartResultEnum = BaccartResultEnum.DRAW;

		int bankerPoint = combineCard(this.bankerCardList);
		int playerPoint = combineCard(this.playerCardList);
		if (bankerPoint > playerPoint) {
			tempBaccartResultEnum = BaccartResultEnum.BANKER;
			baccartBetTypeList.add(BaccartBetType.BANKER);
		} else if (bankerPoint < playerPoint) {
			tempBaccartResultEnum = BaccartResultEnum.PLAYER;
			baccartBetTypeList.add(BaccartBetType.PLAYER);
		} else {
			baccartBetTypeList.add(BaccartBetType.DRAW);
		}

		tempPearlRoadData.setBaccartPair(baccartPair);
		tempPearlRoadData.setBaccartResult(tempBaccartResultEnum.getIndex());
		pearlRoadDataList.add(tempPearlRoadData);

		long totalBet = BaccartBetData
				.betNumForBaccartBetDataMap(this.baccartBetDataMap);
		long totalPay = 0;
		long tempTax = 0L;

		for (Player player : this.baccartRoomPlayerManager.getPlayerList()) {
			HumanBaccartManager baccartManager = player.getHuman()
					.getHumanBaccartManager();
			// 系统赔率
			long needPay = 0;
			// 退款
			long refund = 0;

			// 总下注
			long bet = baccartManager.getBetNum();

			baccartManager.addHumanExp(bet);
			
			if (tempBaccartResultEnum == BaccartResultEnum.DRAW) {
				needPay = this.resultForBet(player);
				
				Long tempBankerNum = baccartManager.getBaccartBetDataMap()
						.get(BaccartBetType.BANKER);
				if (tempBankerNum != null)
					refund+=tempBankerNum;
				Long tempDrawNum = baccartManager.getBaccartBetDataMap()
						.get(BaccartBetType.DRAW);
				if (tempDrawNum != null)
					refund+=tempDrawNum;
				Long tempPlayerNum = baccartManager.getBaccartBetDataMap()
						.get(BaccartBetType.PLAYER);
				if (tempPlayerNum != null)
					refund+=tempPlayerNum;
				
			} else {

				for (BaccartBetType tempBaccartBetType : baccartBetTypeList) {
					Long tempBetNum = baccartManager.getBaccartBetDataMap()
							.get(tempBaccartBetType);
					if (tempBetNum == null)
						continue;

					needPay += tempBetNum * (tempBaccartBetType.getRate());
					refund += tempBetNum;

					if (tempBaccartBetType == BaccartBetType.BANKER) {
						tempTax += tempBetNum * BANKER_TAX;
					}

				}

			}

			// 赢得筹码
			long winCoins = needPay + refund;
			long actualWinCoins = winCoins - bet;

			totalPay += winCoins;

			// 净利润
			if (actualWinCoins > 0) {
				tempTax += actualWinCoins * this.taxRate / 10000.0f;
				winCoins -= actualWinCoins * this.taxRate / 10000.0f;
			}

			// 获得钱
			if (winCoins > 0) {
				baccartManager.win(winCoins);
				BaccartSettleData tempBaccartSettleData = new BaccartSettleData();
				tempBaccartSettleData.setPlayerId(player.getPassportId());
				tempBaccartSettleData.setWinCoins(winCoins);
				this.baccartSettleDataList.add(tempBaccartSettleData);
				Globals.getRankNewServer().win(player.getHuman(), winCoins-bet);
				//成就赢得钱
				player.getHuman().getHumanAchievementManager().updateBaccartCumulativeWin(winCoins-bet);
			} else {
				baccartManager.lose();
			}
			baccartManager.getBaccartBetDataMap().clear();//这里调用一下情况防止玩家在结算完了，然后在推出情况
		}

		long extraJackpot = (long) (tempTax
				* Globals.getBaccartService().getBaccartConfigTemplate()
						.getJackpotRatio() / 10000.0f);
		this.baccarRoomModel.setJackpot(this.baccarRoomModel.getJackpot()
				+ extraJackpot);

		long extraStock = totalBet - totalPay;

		this.baccarRoomModel.setStock(this.baccarRoomModel.getStock()
				+ extraStock);
		this.baccarRoomModel.setModified();
		Globals.getBaccartService().onBaccartRoomSettle(this,
				this.bankerCardList, this.playerCardList, tempPearlRoadData,
				this.baccartSettleDataList);

		logger.info(this.buildSettleLog());
		logger.info(this.buildRoomLog());
		
	}

	/**
	 * 计算彩金
	 */
	private void calculateJackpot() {
		for (Player player : this.baccartRoomPlayerManager.getPlayerList()) {
			
			HumanBaccartManager humanBaccartManager = player.getHuman().getHumanBaccartManager();
			
			long tempPlayerJack = 0l;
			
			if (humanBaccartManager.getContinueWins() > MIN_CONTINUE_TIME) {
				JackpotTemplate tempJackpotTemplate = Globals
						.getBaccartService().jackpotTemplateByWinNums(
								humanBaccartManager.getMinWins());
				if(tempJackpotTemplate==null)
					continue;
				int index = MathUtils.random(tempJackpotTemplate.getChanceArr());
				int ratio = tempJackpotTemplate.getRatioArr()[index];
				tempPlayerJack = (long) (this.baccarRoomModel.getJackpot()* ratio / 10000.0f);
				
				humanBaccartManager.jackpot(tempPlayerJack);
				
				this.baccarRoomModel.setJackpot(this.baccarRoomModel.getJackpot() - tempPlayerJack);
				
				BaccartJackpotData tempBaccartJackpotData= new BaccartJackpotData();
				tempBaccartJackpotData.setJackpot(tempPlayerJack);
				tempBaccartJackpotData.setPlayerId(player.getPassportId());
				baccartJackpotDataList.add(tempBaccartJackpotData);
				
				Globals.getLogService().sendGoldLog(player.getHuman(), LogReasons.GoldLogReason.BACCARTMOSAICGOLD, LogReasons.GoldLogReason.BACCARTMOSAICGOLD.getReasonText(), tempPlayerJack,  player.getHuman().getGold());
			
				logger.info("玩家[" + player.getPassportId() + "]在房间["+ this.getId() + "]内获得jackpot[" + tempPlayerJack + "]");
			}
		}
		Globals.getBaccartService().onBaccartRoomPlayerJackpot(this, baccartJackpotDataList);
		this.baccarRoomModel.setModified();
		Globals.getBaccartService().onBaccartRoomJackpot(this,
				this.baccarRoomModel.getJackpot());
		
		logger.info(this.buildRoomLog());
	}

	/**
	 * 
	 */

	/**
	 * 清理台面
	 */
	private void clear() {
		Globals.getBaccartService().onBaccartRoomPlayerJackpotBroadcast(this, this.baccartJackpotDataList);
		
		List<BaccartCoinSyncData> tempBaccartCoinSyncDataList = new ArrayList<BaccartCoinSyncData>();
		//补充筹码
		for(Player player:this.baccartRoomPlayerManager.getPlayerList()){
			HumanBaccartManager tempHumanBaccartManager =  player.getHuman().getHumanBaccartManager();
			long complementGold = tempHumanBaccartManager.complementGold();
			if(complementGold!=0){
				Globals.getBaccartService().onBaccartRoomPlayerComplementCoinsTip(this,player,complementGold);
			}
			BaccartCoinSyncData tempBaccartCoinSyncData = new BaccartCoinSyncData();
			tempBaccartCoinSyncData.setPlayerId(player.getPassportId());
			tempBaccartCoinSyncData.setCoins(tempHumanBaccartManager.getCoins());
			tempBaccartCoinSyncDataList.add(tempBaccartCoinSyncData);
		}
		
	
		Globals.getBaccartService().onBaccartRoomCoinsSync(this,tempBaccartCoinSyncDataList);
		
		
	
		this.baccartBetDataMap.clear();
		this.bankerCardList.clear();
		this.baccartJackpotDataList.clear();
		this.playerCardList.clear();
		this.baccartBetTypeList.clear();
		this.baccartSettleDataList.clear();
		for (Player player : this.baccartRoomPlayerManager.getPlayerList()) {
			player.getHuman().getHumanBaccartManager().clear();
		}
		
		
		Globals.getBaccartService().onBaccartRoomBeaconChanged(this);
		Globals.getBaccartService().onBaccartRoomClear(this);
		
		logger.info("百家乐房间清理台面");
		long now = Globals.getTimeService().now();
		baccartRoomState = BaccartRoomState.CLEAR;
		lastFlagTime = now;
	
		
		this.settleTime = Globals.getBaccartService().getBaccartConfigTemplate()
				.getDealTime()
				+ Globals.getBaccartService().getBaccartConfigTemplate()
						.getShowTime();


	}

	/**
	 * 清理台面后
	 */
	private void afterClear() {
		
	
		
		// TODO Auto-generated method stub
		if (this.baccartRoomPlayerManager.getPlayerNum() == 0) {
			recycleRoom();
			return;
		}

		if (this.baccartDeck.getCardList().size() < MIN_CARDS_NUM) {
			this.shuffle();
		} else {
			this.startBet();
		}
	}

	/**
	 * 回收房间
	 */
	private void recycleRoom() {
		// 发送玩家加入信息
		logger.info("百家乐房间[" + this.getId() + "]回收");
		baccartRoomState = BaccartRoomState.INIT;
	}

	/**
	 * 牌型点数
	 */
	private int combineCard(List<Card> aCardList) {
		int temNum = 0;
		for (Card tempCard : aCardList) {
			if (tempCard.getCardValue().getIndex() >= Card.CardValueEnum.TEN
					.getIndex()) {
				continue;
			}
			temNum += tempCard.getCardValue().getIndex();
			if (temNum >= 10)
				temNum -= 10;
		}
		return temNum;
	}

	/**
	 * 补牌闲家
	 * 
	 * @param atLeastCard
	 */
	private void cheatComplementPlayerCard(int totalNum, int compare) {

		
		// 补牌闲家
		List<Card.CardValueEnum> tempCardValueEnumList = getNeedCardValueList(
				this.playerCardList, totalNum, compare);
		Card tempCard = this.baccartDeck.getNextCard(tempCardValueEnumList);
		if (tempCard == null) {
			this.playerCardList.add(this.baccartDeck.getNextCard());
		} else {
			this.playerCardList.add(tempCard);
		}

	}

	/**
	 * 补牌庄家
	 */
	private void cheatComplementBankerCard(int totalNum, int compare) {

	
		// 补牌庄家
		List<Card.CardValueEnum> tempCardValueEnumList = getNeedCardValueList(
				this.bankerCardList, totalNum, compare);
		Card tempCard = this.baccartDeck.getNextCard(tempCardValueEnumList);
		if (tempCard == null) {
			this.bankerCardList.add(this.baccartDeck.getNextCard());
		} else {
			this.bankerCardList.add(tempCard);
		}

	}

	/**
	 * 判断是否是天牌
	 */
	private boolean ifPlayerNaturalCard() {
		int tempBankerNum = combineCard(this.playerCardList);
		if (tempBankerNum >= NATURAL_CARD_NUM) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是天牌
	 */
	private boolean ifBankerNaturalCard() {
		int tempBankerNum = combineCard(this.bankerCardList);
		if (tempBankerNum >= NATURAL_CARD_NUM) {
			return true;
		}
		return false;
	}

	/**
	 * 闲家是否应该补牌
	 */
	private boolean ifPlayerShouldComplementCard() {

		int tempNum = combineCard(this.playerCardList);
		if (tempNum < COMPLEMENT_NUM) {
			return true;
		}

		return false;
	}

	/**
	 * 庄家是否应该补牌
	 *
	 */
	private boolean ifBankerShouldComplementCard() {
		int tempNum = combineCard(this.bankerCardList);
		if(this.playerCardList.size()==2){
			if(tempNum<=5)
				return true;
			else
				return false;
		}
		
		Card tempThirdPlayerCard = this.playerCardList.get(2);

		switch (tempThirdPlayerCard.getCardValue()) {
		case EIGHT: {
			if (tempNum <= 2)
				return true;
			else{
				return false;
			}
				
		}
		case ACE:
		case NINE:
		case TEN:
		case JACK:
		case QUEEN:
		case KING: {
			if (tempNum <= 3)
				return true;
			else{
				return false;
			}
		}
		case TWO:
		case THREE: {
			if (tempNum <= 4)
				return true;
			else{
				return false;
			}
		}
		case FOUR:
		case FIVE: {
			if (tempNum <= 5)
				return true;
			else{
				return false;
			}
		}
		case SIX:
		case SEVEN: {
			if (tempNum <= 6)
				return true;
			else{
				return false;
			}
		}
		default: {

		}

		}
		return false;

	}

	/**
	 * 是否是庄对
	 */
	private boolean ifBankerPair() {
		return ifCardsPair(this.bankerCardList);
	}

	/**
	 * 是否是闲对
	 */
	private boolean ifPlayerPair() {
		return ifCardsPair(this.playerCardList);
	}

	/**
	 * 是否是对子
	 */
	private boolean ifCardsPair(List<Card> aCardList) {
		Card tempCard1 = aCardList.get(0);
		Card tempCard2 = aCardList.get(1);
		if (tempCard1.actualCompareTo(tempCard2) == 0)
			return true;
		return false;
	}

	/**
	 * 是否房间已满
	 */
	public boolean ifFull() {
		return this.baccartRoomPlayerManager.getPlayerNum() >= this.totalNum;
	}

	/**
	 * 判断赢钱
	 */
	private long resultForBet(Player player) {
		HumanBaccartManager baccartManager = player.getHuman()
				.getHumanBaccartManager();
		long tempRefund = 0;

		for (BaccartBetType tempBaccartBetType : baccartBetTypeList) {
			Long tempBet = baccartManager.getBaccartBetDataMap().get(
					tempBaccartBetType);
			if (tempBet == null)
				continue;
			tempRefund += tempBet * (tempBaccartBetType.getRate());

		}

		return tempRefund;
	}

	/**
	 * 是否可以下注
	 * 
	 * @return
	 */
	public boolean ifCanBet() {
		return this.baccartRoomState == BaccartRoomState.BET;
	}

	/**
	 * 结算日志
	 * 
	 * @return
	 */
	private String buildDealCardsLog() {
		StringBuilder sb = new StringBuilder();
		sb.append("庄家牌[" + this.bankerCardList + "]\n");
		sb.append("闲家牌[" + this.playerCardList + "]\n");
		return sb.toString();
	}

	/**
	 * 输赢日志
	 */
	private String buildSettleLog() {
		StringBuilder sb = new StringBuilder();
		sb.append("比牌结果[" + this.baccartBetTypeList + "]\n");
		sb.append("结算结果[" + this.baccartSettleDataList + "]");

		return sb.toString();
	}

	/**
	 * 房间日志
	 */
	private String buildRoomLog() {
		StringBuilder sb = new StringBuilder();
		sb.append("房间状态[" + this.baccartRoomState + "]");
		sb.append("jackpot[" + this.baccarRoomModel.getJackpot() + "]\n");
		sb.append("库存[" + this.baccarRoomModel.getStock() + "]\n");
		return sb.toString();
	}

	/**
	 * 玩家下注日志
	 * 
	 * @return
	 */
	private String buildPlayerBetLog() {
		StringBuilder sb = new StringBuilder();
		sb.append("下注量:\n");
		// TODO Auto-generated method stub
		for (Player player : this.baccartRoomPlayerManager.getPlayerList()) {
			sb.append("玩家["
					+ player.getPassportId()
					+ "],下注前{"
					+ player.getHuman().getHumanBaccartManager().getCoins() + "}");
			sb.append(",下注{"
					+ player.getHuman().getHumanBaccartManager()
							.getBaccartBetDataMap() + "}");
			sb.append("下注后{"
					+ player.getHuman().getHumanBaccartManager()
							.getCoins() + "}");
		}
		return sb.toString();
	}
	
	/**
	 * 房间记录
	 */
	public String buildRoomLogForRemote(){
		StringBuilder sb = new StringBuilder();
		sb.append(buildDealCardsLog()+"\n");
		sb.append(buildPlayerBetLog()+"\n");
		sb.append(buildSettleLog()+"\n");
		return sb.toString();
	}

	/**
	 * 剩余时间
	 * @return
	 */
	public long getRemainTime() {
		// TODO Auto-generated method stub
		long now = Globals.getTimeService().now();
		
		switch(this.baccartRoomState){
		case INIT:
			return 0;
		case SHUFFLE:
			return this.shuffleTime*TimeUtils.SECOND-(now-this.lastFlagTime);
		case BET:
			return this.betTime*TimeUtils.SECOND-(now-this.lastFlagTime);
		case SETTLE:
			return this.settleTime*TimeUtils.SECOND-(now-this.lastFlagTime);
		case CLEAR:
			return this.clearTime*TimeUtils.SECOND-(now-this.lastFlagTime);
		}
		return 0;
	}
	
	/**
	 * 打开房间
	 */
	public void open() {
		// TODO Auto-generated method stub
		this.baccarRoomModel.setClosed(0);
		this.baccarRoomModel.setModified();
		
	}
	
	/**
	 * 关闭房间
	 */
	public void close() {
		// TODO Auto-generated method stub
		this.baccarRoomModel.setClosed(1);
		this.baccarRoomModel.setModified();
		
	}
	
	
	public boolean ifClose(){
		return this.baccarRoomModel.getClosed()==1;
	}
	
	
}
