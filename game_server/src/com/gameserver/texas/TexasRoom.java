package com.gameserver.texas;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.common.model.Card;
import com.core.heartbeat.HeartBeatAble;
import com.core.heartbeat.ITickable;
import com.core.util.Assert;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanTexasManager;
import com.gameserver.lobby.enums.GameType;
import com.gameserver.player.Player;
import com.gameserver.texas.data.TexasHandCard.TexasHandCardEnum;
import com.gameserver.texas.data.TexasPoolSettleInfoData;
import com.gameserver.texas.data.TexasRoomExp;
import com.gameserver.texas.data.TexasRoomPlayerSettleCardsInfoData;
import com.gameserver.texas.data.TexasRoomPlayerSettleInfoData;
import com.gameserver.texas.enums.RoomPlayerState;
import com.gameserver.texas.enums.TexasRoomEnum;
import com.gameserver.texas.enums.TexasRoomState;
import com.gameserver.texas.exceptions.TexasException;
import com.gameserver.texas.manager.TexasRoomPlayerManager;
import com.gameserver.texas.msg.GCHumanTexasExp;
import com.gameserver.texas.msg.GCTexasComplementNum;
import com.gameserver.texas.msg.GCTexasRoomInfo;
import com.gameserver.texas.template.SngMatchConfigTemplate;
import com.gameserver.texas.template.SngMatchTemplate;
import com.gameserver.texas.template.TexasRoomTemplate;
import com.gameserver.vip.template.VipRoomTemplate;

/**
 * 德州房间
 * 
 * @author wayne
 *
 */
public class TexasRoom implements ITickable, HeartBeatAble, InitializeRequired,
		DestroyRequired {

	private final String NORMAL_LOG_DIR = "logs/texas/normal";
	private final String SNG_LOG_DIR = "logs/texas/sng";
	private final String VIP_LOG_DIR = "logs/texas/vip";
	
	private Logger logger;
	
	public static final int TABLE_PLAYER_MIN = 6;
	public static final int TABLE_PLAYER_MAX = 9;
	

	/** 房间号 */
	private long rid;
	/** 局号 */
	private long gid;
	/**德州房间种类类型普通，sng*/
	private TexasRoomEnum texasRoomEnum;
	/**密码*/
	private String password;
	/**名称*/
	private String name;
	/**当前局数*/
	private long currentGames = 0;
	/**房间类型id*/
	private int id;
	/**德州房间大类型id*/
	private int roomType;
	/**小盲注*/
	private int smallBlind;
	/**最小携带*/
	private int minCarry;
	/**最大携带*/
	private int maxCarry;
	/**加入经验*/
	private int joinExp;
	/**胜利经验*/
	private int victoryExp;
	/**服务费1*/
	private int serviceValue1;
	/**服务费2*/
	private int serviceValue2;
	/**小费*/
	private int dealerCost;
	
	/** 德州房间人员管理器 */
	private TexasRoomPlayerManager playerManager;
	/** 德州扑克 */
	private TexasDeck texasDeck;
	/** 房间状态 */
	private TexasRoomState texasRoomState;
	/** 公共牌 */
	private List<Card> publicCardList = new ArrayList<Card>(5);
	/** 池底 */
	private int betPool = 0;
	/** 奖池 */
	private List<TexasPool> texasSidePoolList = new ArrayList<TexasPool>();
	/** 主池 */
	private int mainPool = 0;
	/** 庄家位置 */
	private int bankerPos = -1;
	/** 小盲注位置 */
	private int smallBlindPos = -1;
	/** 最高下注额度 */
	private long maxBet = 0;
	/** 最高下注位置 */
	private int maxBetPos = -1;
	/** 当前下注玩家 */
	private Player currentBetter;
	/** 当前下注位置 */
	private int currentPos = -1;
	/** 上一次计时时间 */
	private long lastFlagTime = 0;
	/** 总共结算时间 */
	private long totalSettleTime = 0;

	/** 最大玩家数 */
	private int MAX_PLAYER_COUNT = 9;
	/** 最大玩家数 */
	private int MIN_PLAYER_COUNT = 2;

	private int MAX_WAITING_COUNT = 3;
	
	private int bestSuitNum;

	/** 开始等候时间 */
	private int BEGIN_TIME = 2;
	/** preflop */
	private int PREFLOP_TIME = 3;
	/** side pool */
	private int SIDE_POOL_TIME = 3;
	/** flop */
	private int FLOP_TIME = 3;
	/** turn */
	private int TURN_TIME = 3;
	/** river */
	private int RIVER_TIME = 3;

	/** 玩家时间 */
	private int PLAYER_TIME = 10;
	/** 结算时间 */
	private int SETTLE_TIME = 2;
	
	/**开始时间*/
	private long startTime = 0;
	
	/**sng*/
	private Map<Integer,List<Player>> sngRankMap = new HashMap<Integer,List<Player>>();
	
	public TexasRoom(TexasRoomEnum texasRoomEnum,int id,long rid){
		this.texasRoomEnum = texasRoomEnum;
		this.rid = rid;
		this.id = id;
		
		// 时间配置
		BEGIN_TIME = Globals.getTexasService().getTexasRoomConfigTemplate()
				.getWaitingTime();
		PREFLOP_TIME = Globals.getTexasService().getTexasRoomConfigTemplate()
				.getPreflopTime();
		SIDE_POOL_TIME = Globals.getTexasService().getTexasRoomConfigTemplate()
				.getSettleTime();
		FLOP_TIME = Globals.getTexasService().getTexasRoomConfigTemplate()
				.getFlopTime();
		TURN_TIME = Globals.getTexasService().getTexasRoomConfigTemplate()
				.getTurnTime();
		RIVER_TIME = Globals.getTexasService().getTexasRoomConfigTemplate()
				.getRiverTime();
		PLAYER_TIME = Globals.getTexasService().getTexasRoomConfigTemplate()
				.getActTime();
		SETTLE_TIME = Globals.getTexasService().getTexasRoomConfigTemplate()
				.getCompareTime();
		
		Card.CardValueEnum tempValue = Card.CardValueEnum.TWO;
		switch(this.texasRoomEnum){
		case NORMAL:{
			TexasRoomTemplate template = Globals.getTemplateService().get(id, TexasRoomTemplate.class);
			Assert.notNull(template);
			MAX_PLAYER_COUNT = template.getRoomNum();
			MAX_WAITING_COUNT = template.getWatchNum();
			this.bestSuitNum = template.getSuitNum();
			this.roomType = template.getRoomType();
			this.smallBlind = template.getSmallBlind();
			this.minCarry = template.getMinCarry();
			this.maxCarry = template.getMaxCarry();
			this.joinExp = template.getJoinExp();
			this.victoryExp = template.getVictoryExp();
			this.serviceValue1 = template.getServiceValue1();
			this.serviceValue2 = template.getServiceValue2();
			this.dealerCost = template.getDealerCost();
			tempValue = template.getSmallCardValue();
			logger = Loggers.customLogger(NORMAL_LOG_DIR, String.valueOf(this.getRid()));
		}
			break;
		case SNG:
			SngMatchTemplate template = Globals.getTemplateService().get(id, SngMatchTemplate.class);
			
			Assert.notNull(template);
			MAX_PLAYER_COUNT = template.getRoomNum();
			MIN_PLAYER_COUNT = template.getRoomNum();
			this.bestSuitNum = template.getRoomNum();
			MAX_WAITING_COUNT = 0;
			this.dealerCost = template.getDealerCost();
		
			List<SngMatchConfigTemplate> sngMatchConfigTemplateList = Globals.getTexasService().getSngMatchConfigTemplateList(this.id);
			for(SngMatchConfigTemplate sngMatchConfigTemplate : sngMatchConfigTemplateList){
				this.smallBlind = sngMatchConfigTemplate.getSmallBlind();
				break;
			}
			logger = Loggers.customLogger(SNG_LOG_DIR, String.valueOf(this.getRid()));
			break;
		case VIP:{
			VipRoomTemplate vipTemplate = Globals.getTemplateService().get(id, VipRoomTemplate.class);
			Assert.notNull(vipTemplate);
			MAX_PLAYER_COUNT = vipTemplate.getRoomNum();
			MAX_WAITING_COUNT = vipTemplate.getWatchNum();
	
			this.smallBlind = vipTemplate.getSmallBlind();
			this.minCarry = vipTemplate.getMinCarry();
			this.maxCarry = vipTemplate.getMaxCarry();
			this.joinExp = vipTemplate.getJoinExp();
			this.victoryExp = vipTemplate.getVictoryExp();
			this.serviceValue1 = vipTemplate.getServiceValue1();
			this.serviceValue2 = vipTemplate.getServiceValue2();
			this.dealerCost = vipTemplate.getDealerCost();
			logger = Loggers.customLogger(VIP_LOG_DIR, String.valueOf(this.getRid()));
		}
			break;
		default:
			Assert.isTrue(false);;
			break;
		}
		
		playerManager = new TexasRoomPlayerManager(MAX_PLAYER_COUNT);
		texasDeck = new TexasDeck(tempValue);
		this.init();
	}



	@Override
	public void init() {
		// TODO Auto-generated method stub
		texasRoomState = TexasRoomState.INIT;
		lastFlagTime = Globals.getTimeService().now();
		logger.info("德州房间模式["+texasRoomEnum+",德州房间类型[" + this.getId() + "],房间号["+this.getRid()+"],状态[" + texasRoomState + "],服务费1["+this.getServiceValue1()+"],服务费2["+this.getServiceValue2()+"]");
		
	}

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		logger.info("房间销毁前");
		logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
		
		//通知销毁
		Globals.getTexasService().onTexasRoomDestroy(this);
		
		for (Player player : this.playerManager.getRoomPlayerList()) {
			Globals.getTexasService().onPlayerLeave(player, this, true);
			Globals.getTexasService().onPlayerExit(player,this);
		}
		
	
		for (Player player : this.playerManager.getWaitingPlayerList()) {
			Globals.getTexasService().onPlayerExit(player,this);
		}
		playerManager.getRoomPlayerList().clear();
		playerManager.getWaitingPlayerList().clear();
		logger.info("房间销毁");
	
	}

	/**
	 * 重新开始
	 */
	private void restart() {
		
		
		Globals.getTexasService().onClear(this);
		
		texasRoomState = TexasRoomState.INIT;
		
		logger.info("德州房间类型["+texasRoomEnum+",德州房间[" + rid + "]状态[" + texasRoomState + "]");
		
		texasDeck.shuffle();
		publicCardList.clear();
		betPool = 0;
		maxBet = 0;
		maxBetPos = -1;
		currentBetter = null;
		currentPos = -1;
		texasSidePoolList.clear();
		mainPool = 0;
		totalSettleTime = SETTLE_TIME;
		long now = Globals.getTimeService().now();
		int numOfPlayer = playerManager.getRoomPlayerList().size();
	
		if(isSNG())
		{
			if(numOfPlayer==0){
				recycleRoom();
				return;
			}
			lastFlagTime = now;
			texasRoomState = TexasRoomState.WAIT;
			
		}
		else{
			lastFlagTime = now;
			if (numOfPlayer >= MIN_PLAYER_COUNT) {
				texasRoomState = TexasRoomState.WAIT;
			}
			else{
				texasRoomState = TexasRoomState.WAIT_PEOPLE;
			}
		}
		
	}

	/**
	 * 房间 是否有座位
	 * 
	 * @return
	 */
	public boolean hasSeat() {
		return playerManager.getRoomPlayerList().size() < MAX_PLAYER_COUNT;
	}

	/**
	 * 房间是否已满
	 * 
	 * @return
	 */
	public boolean isFull() {
		return playerManager.getRoomPlayerList().size() >= MAX_PLAYER_COUNT
				&& playerManager.getWaitingPlayerList().size() >= MAX_WAITING_COUNT;
	}
	
	/**
	 * 房间是否是空的
	 * 
	 */
	
	public boolean isEmpty(){
		return playerManager.getRoomPlayerList().size() ==0;
	}

	/**
	 * 查看房间人数
	 */
	public int getTablePlayerNum(){
		return  playerManager.getRoomPlayerList().size();
	}



	/**
	 * 获得房间号
	 * 
	 * @return
	 */
	public long getRid() {
		return this.rid;
	}

	/**
	 * 设置房间号
	 */
	public long setRid(long rid) {
		return this.rid;
	}
	
	/**
	 * 获取局号
	 * @return
	 */
	public long getGid(){
		return this.gid;
	}

	public TexasRoomEnum getTexasRoomEnum() {
		return texasRoomEnum;
	}

	public void setTexasRoomEnum(TexasRoomEnum texasRoomEnum) {
		this.texasRoomEnum = texasRoomEnum;
	}

	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public long getCurrentGames() {
		return currentGames;
	}



	/**
	 * 获取房间小类型id
	 * 
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * 获取房间大类型
	 * 
	 * @return
	 */
	public int getRoomType() {
		return this.roomType;
	}

	private int getBestSuitNum(){
		return this.bestSuitNum;
	}
	/**
	 * 获取小盲注
	 * 
	 * @return
	 */
	public int getSmallBlind() {
		return this.smallBlind;
	}

	/**
	 * 获取最小携带
	 */
	public int getMinCarry() {
		return this.minCarry;
	}

	/**
	 * 获取最大携带
	 */
	public int getMaxCarry() {
		return this.maxCarry;
	}

	/**
	 * 获取获胜奖励
	 */
	public int getVictoryExp() {
		return this.victoryExp;
	}

	/**
	 * 获得失败奖励
	 */
	public int getJoinExp() {
		return this.joinExp;
	}

	/**
	 * 获取池底抽水
	 */
	public int getServiceValue1() {
		return this.serviceValue1;
	}

	/**
	 * 获取盲注抽水
	 */
	public int getServiceValue2() {
		return this.serviceValue2;
	}

	/**
	 * 获得小费
	 */
	public int getDealerCost() {
		return this.dealerCost;
	}
	
	public long getStartTime(){
		return this.startTime;
	}

	/**
	 * 获得玩家管理器
	 * 
	 * @return
	 */
	public TexasRoomPlayerManager getPlayerManager() {
		return playerManager;
	}

	/**
	 * 玩家观看
	 */
	public void playerWaiting(Player player) {
		HumanTexasManager humanTexasManager = player.getHuman()
				.getHumanTexasManager();
		this.playerManager.addWaitingPlayer(player);
		logger.info("玩家[" + player.getPassportId() + "] 进入 房间： ["
				+ this.getId() + "] 观看");
		humanTexasManager.waitingRoom(this);
		
		Globals.getTexasService().onPlayerWaitingRoom(player, this);
		
	
	}

	/**
	 * 玩家进入
	 */
	public void playerJoin(Player player,boolean ticket) {
		HumanTexasManager humanTexasManager = player.getHuman()
				.getHumanTexasManager();

		this.playerManager.addPlayer(player, -1);
		
		logger.info("玩家[" + player.getPassportId() + "] 进入 房间");
		
		humanTexasManager.joinRoom(this,ticket);
		Globals.getTexasService().onPlayerJoinRoom(player, this);
		afterPlayerJoin();
	}

	/**
	 * 玩家进入
	 */
	public void playerSeat(Player player, int pos) {
		HumanTexasManager humanTexasManager = player.getHuman()
				.getHumanTexasManager();
		this.playerManager.removeWaitingPlayer(player);
		this.playerManager.addPlayer(player, pos);

		humanTexasManager.joinRoom(this,false);
		
		logger.info("玩家[" + player.getPassportId() + "]坐下");
		
		Globals.getTexasService().onPlayerSeat(player, this);
		
		afterPlayerJoin();
	}

	/**
	 * 玩家加入后
	 */
	private void afterPlayerJoin() {
		long now = Globals.getTimeService().now();
		if(texasRoomState == TexasRoomState.INIT){
			lastFlagTime = now;
			texasRoomState = TexasRoomState.WAIT_PEOPLE;
			logger.info("德州房间类型["+texasRoomEnum+",德州房间[" + rid + "],德州房间状态[" + texasRoomState + "]");
		}
		else if (texasRoomState == TexasRoomState.WAIT_PEOPLE) {
			if (playerManager.getRoomPlayerList().size() >= MIN_PLAYER_COUNT) {
				lastFlagTime = now;
				texasRoomState = TexasRoomState.WAIT;
				logger.info("德州房间类型["+texasRoomEnum+",德州房间[" + rid + "],德州房间状态[" + texasRoomState + "]");
			}
		} else if (texasRoomState == TexasRoomState.WAIT) {
			lastFlagTime = now;
			logger.info("德州房间类型["+texasRoomEnum+",德州房间[" + rid + "],德州房间状态[" + texasRoomState + "]");
		
		}
	}

	/**
	 * 检查是否当前玩家
	 * 
	 * @param player
	 */
	public boolean ifCurrentPlayer(Player player) {
		
		
		return currentBetter == player;
	}
	
	/**
	 * 玩家进行
	 */
	public boolean ifPlayerAction(){
		if(texasRoomState == TexasRoomState.PREFLOP ){
			return true;
		}
		if(texasRoomState == TexasRoomState.FLOP ){
			return true;
		}
		if(texasRoomState == TexasRoomState.TURN ){
			return true;
		}
		if(texasRoomState == TexasRoomState.RIVER ){
			return true;
		}
		return false;
		
	}

	/**
	 * 检查是否开始下一回合
	 */
	private void afterPlayerAction(Player player) {

		// 判断是否剩下一个玩家
		if (isOver()) {
			settleGiveUp();
			return;
		}

		// 最后一个玩家
		if (isLastPlayer(player)) {
			// 全部allIn了
			if (isAllAllIn()) {
				switch (texasRoomState) {
				case PREFLOP: {
					flop();
					turn();
					river();
					break;
				}
				case FLOP: {
					turn();
					river();
					break;
				}
				case TURN: {
					river();
					break;
				}
				default:
					break;
				}
				sidePool();
				texasRoomState = TexasRoomState.RIVER_SIDE_POOL;
				lastFlagTime = Globals.getTimeService().now();

				return;
			}

			switch (texasRoomState) {
			case PREFLOP: {
				sidePool();
				texasRoomState = TexasRoomState.PREFLOP_SIDE_POOL;
				lastFlagTime = Globals.getTimeService().now();

			}
				break;
			case FLOP: {
				sidePool();
				texasRoomState = TexasRoomState.FLOP_SIDE_POOL;
				lastFlagTime = Globals.getTimeService().now();

			}
				break;
			case TURN: {
				sidePool();
				texasRoomState = TexasRoomState.TURN_SIDE_POOL;
				lastFlagTime = Globals.getTimeService().now();

			}
				break;
			case RIVER: {
				sidePool();
				texasRoomState = TexasRoomState.RIVER_SIDE_POOL;
				lastFlagTime = Globals.getTimeService().now();

			}
				break;
			default:
				break;
			}
			return;
		}
		if (player == currentBetter) {

			nextBetter(currentPos);
		}

	}

	/**
	 * 看牌
	 */
	public void playerCheck(Player player) {

		HumanTexasManager humanTexasManager = player.getHuman()
				.getHumanTexasManager();
		// 检查是否当前最大注是0
		if (maxBet != humanTexasManager.getCurrentBet()) {
			logger.error("玩家[" + player.getPassportId() + "] 不能看牌,最大下注["
					+ maxBet + "]");
			return;
		}

		if (maxBetPos == -1)
			maxBetPos = humanTexasManager.getPos();

		logger.info("玩家[" + player.getPassportId() + "] 看牌");
		
		Globals.getTexasService().onPlayerCheck(player, this);
	
		// 用户后面行为
		afterPlayerAction(player);
	}

	/**
	 * 跟注
	 * 
	 * @param player
	 * @param bet
	 */
	public void playerCall(Player player) {
		HumanTexasManager humanTexasManager = player.getHuman()
				.getHumanTexasManager();
		// 检查需要的下注量
		long bet = maxBet - humanTexasManager.getCurrentBet();

		// 检查是否有足够的筹码
		if (bet > humanTexasManager.getCoins()) {
			logger.error("玩家[" + player.getPassportId() + "]" + "筹码不足");
			return;
		}
		logger.info("玩家[" + player.getPassportId() + "]当前下注["+humanTexasManager.getCurrentBet()+"],最大下注["+maxBet+"],跟注" + "[" + maxBet + "],下注["+bet+"]");
		
		if(maxBetPos == -1){
			maxBetPos = humanTexasManager.getPos();
		}
		
		bet(player, bet);

		Globals.getTexasService().onPlayerCall(player, this);

		// 用户后面行为
		afterPlayerAction(player);
	}

	/**
	 * 加注
	 * 
	 * @param player
	 */
	public void playerRaise(Player player, long addBet) {

		HumanTexasManager humanTexasManager = player.getHuman()
				.getHumanTexasManager();
		long bet=0;
		// 检查需要的下注量
		if(maxBet!=0){
			bet= maxBet * 2 - humanTexasManager.getCurrentBet();
		}else{
			bet = this.smallBlind*2;
		}
		// 检查是否下注量大于大盲注

		if (addBet < bet) {
			logger.error("玩家[" + player.getPassportId() + "]" + "加注筹码不足");
			return;
		}

		// 检查是否有足够的筹码
		if (addBet > humanTexasManager.getCoins()) {
			logger.error("玩家[" + player.getPassportId() + "]" + "没有足够筹码不能跟注");
			return;
		}
		logger.info("玩家[" + player.getPassportId() + "]当前下注["+humanTexasManager.getCurrentBet()+"],最大下注["+maxBet+"],加注" + "[" + addBet + "]");
		

		bet(player, addBet);
		
		Globals.getTexasService().onPlayerRaise(player, this,addBet);

		afterPlayerAction(player);
	}

	/**
	 * 弃牌
	 * 
	 * @param player
	 */
	public void playerGiveup(Player player) {
		HumanTexasManager humanTexasManager = player.getHuman()
				.getHumanTexasManager();

		logger.info("玩家[" + player.getPassportId() + "] 弃牌");
		humanTexasManager.setPlayerState(RoomPlayerState.GIVEUP);
		
		Globals.getTexasService().onPlayerGiveup(player, this);

		afterPlayerAction(player);
	}

	/**
	 * allin
	 * 
	 * @param player
	 */
	public void playerAllIn(Player player) {
		HumanTexasManager humanTexasManager = player.getHuman()
				.getHumanTexasManager();

		long bet = humanTexasManager.getCoins();
		// 检查筹码数量
		if (bet <= 0) {
			logger.error("玩家[" + player.getPassportId() + "]" + "没有筹码");
			return;
		}

		logger.info("玩家[" + player.getPassportId() + "],当前下注["+humanTexasManager.getCurrentBet()+"],全压[" + bet + "],最大下注["+maxBet+"]");
		
		if(maxBetPos == -1){
			maxBetPos = humanTexasManager.getPos();
		}
		
		bet(player, bet);
		
		Globals.getTexasService().onPlayerAllIn(player, this,bet);

		afterPlayerAction(player);
	}

	/**
	 * 玩家离开
	 * 
	 * @param player
	 */
	public void playerLeave(Player player, boolean kick) {

		Human human = player.getHuman();
		if (human == null)
			return;

		if (!playerManager.containPlayer(player)) {
			logger.warn("玩家[" + player.getPassportId() + "] 不在德州房间 ["
					+ this.getId() + "]");
			return;
		}

		logger.info("玩家[" + human.getPassportId() + "] 离开德州房间 [" + this.getId()
				+ "]座位");
		playerManager.removePlayer(player);
		playerManager.addWaitingPlayer(player);
		
		Globals.getTexasService().onPlayerLeave(player, this,kick);
	
		afterPlayerLeave(player);
	}
	
	/**
	 * 设置排名
	 */
	public void sngSetRank(Player player){
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		int rank = this.getPlayerManager().getRoomPlayerList().size()+1;
		humanTexasManager.setSngRank(rank);
		List<Player> tempPlayerList = sngRankMap.get(rank);
		if(tempPlayerList == null){
			tempPlayerList = new ArrayList<Player>();
			sngRankMap.put(rank, tempPlayerList);
		}
		tempPlayerList.add(player);
		logger.info("玩家["+player.getPassportId()+"]获得sng排名["+rank+"]");
	}
	
	/**
	 * 玩家结算
	 */
	public void playerSettle(Player roomPlayer){
		logger.info("玩家[" + roomPlayer.getHuman().getPassportId() + "] 结算德州房间 [" + this.getId()
				+ "]");
		Globals.getTexasService().onPlayerSettle(roomPlayer,this);
		
		if(isSNG() && startTime!=0 ){
			if(playerManager.getRoomPlayerList().size()==1){
				Player winnerPlayer = playerManager.getRoomPlayerList().get(0);
				playerLeave(winnerPlayer, true);
				sngSetRank(winnerPlayer);
				playerSettle(winnerPlayer);
			}
		}
	}

	
	/**
	 * 玩家离开
	 */
	public void playerExit(Player roomPlayer) {
		if (playerManager.containPlayer(roomPlayer)) {
			playerLeave(roomPlayer, false);
			//sng 设置排名
			if(this.isSNG()){
				if(this.currentGames>0)
					sngSetRank(roomPlayer);
			}
			playerSettle(roomPlayer);
		}
		logger.info("玩家["+roomPlayer.getPassportId()+"]退出房间");
		playerManager.removeWaitingPlayer(roomPlayer);
		Globals.getTexasService().onPlayerExit(roomPlayer,this);
	}
	
	/**
	 * 回收房间
	 */
	private void recycleRoom(){
		logger.info("德州房间类型["+texasRoomEnum+",德州房间[" + rid + "]回收房间");
		currentGames = 0;
		startTime = 0;
		playerManager.getWaitingPlayerList().clear();
	}

	/**
	 * 玩家离开后
	 * 
	 * @param player
	 */
	private void afterPlayerLeave(Player roomPlayer) {
		switch (texasRoomState) {
		case INIT:{
		
		}
			break;
		case WAIT_PEOPLE:{
			if (playerManager.getRoomPlayerList().size() ==0 ) {
				
				texasRoomState = TexasRoomState.INIT;
			}
		}
			break;
		case WAIT:
			if (playerManager.getRoomPlayerList().size() < MIN_PLAYER_COUNT) {
				long now = Globals.getTimeService().now();
				lastFlagTime = now;
				texasRoomState = TexasRoomState.WAIT_PEOPLE;
			}
			break;
		case PREFLOP:
		case FLOP:
		case TURN:
		case RIVER:
			afterPlayerAction(roomPlayer);
			break;
		case PREFLOP_WAIT:
		case PREFLOP_SIDE_POOL:
		case FLOP_WAIT:
		case FLOP_SIDE_POOL:
		case TURN_WAIT:
		case TURN_SIDE_POOL:
		case RIVER_WAIT:
		case RIVER_SIDE_POOL:
			// 判断是否剩下一个玩家
			if (isOver()) {
				settleGiveUp();
				return;
			}
			break;
		case END:
			break;
		default:
			break;

		}

		// 判断等候玩家是否进入
		// checkWaitingList();
	}

	@Override
	public void tick() {
//		 Loggers.texasRoomLogger.info("tick");
		checkState();

	}

	@Override
	public void heartBeat() {
		// TODO Auto-generated method stub

	}

	/**
	 * 检查房间状态
	 */
	private void checkState() {
		long now = Globals.getTimeService().now();
		switch (texasRoomState) {
		
		case INIT:{
			// 拓展机器人
//			long durationTime = now - lastFlagTime;
//			Globals.getTexasService().onTexasRoomWait(this, durationTime);
		}
			break;
		case WAIT_PEOPLE:{
			// 拓展机器人
			long durationTime = now - lastFlagTime;
			Globals.getTexasService().onTexasRoomWait(this, durationTime);
		}
			break;
		case WAIT: {
			long durationTime = now - lastFlagTime;
			if (durationTime >= BEGIN_TIME * TimeUtils.SECOND) {
				if(isSNG()&&startTime==0)
					startTime = now;
				
				preflop();
			}
		}
			break;
		case PREFLOP_WAIT: {
			
			long durationTime = now - lastFlagTime;
			if (durationTime >= PREFLOP_TIME * TimeUtils.SECOND) {
				// 获取下一个玩家
			
				texasRoomState = TexasRoomState.PREFLOP;
				lastFlagTime = now;
				nextBetter(currentPos);
				
			}
		}
			break;
		case FLOP_WAIT: {
		
			long durationTime = now - lastFlagTime;
			if (durationTime >= FLOP_TIME * TimeUtils.SECOND) {
				// 获取下一个玩家
			
				texasRoomState = TexasRoomState.FLOP;
				lastFlagTime = now;
				nextBetter(currentPos);
			}
		}
			break;
		case TURN_WAIT: {
			
			long durationTime = now - lastFlagTime;
			if (durationTime >= TURN_TIME * TimeUtils.SECOND) {
				// 获取下一个玩家
				
				texasRoomState = TexasRoomState.TURN;
				lastFlagTime = now;
				nextBetter(currentPos);
			}
		}
			break;
		case RIVER_WAIT: {
		
			long durationTime = now - lastFlagTime;

			if (durationTime >= RIVER_TIME * TimeUtils.SECOND) {
				// 获取下一个玩家
				
				texasRoomState = TexasRoomState.RIVER;
				lastFlagTime = now;
				nextBetter(currentPos);
			}
		}
			break;
		case PREFLOP_SIDE_POOL: {
		
			long durationTime = now - lastFlagTime;

			if (durationTime >= SIDE_POOL_TIME * TimeUtils.SECOND) {
				flop();
			}
			break;
		}
		case FLOP_SIDE_POOL: {
		
			long durationTime = now - lastFlagTime;

			if (durationTime >= SIDE_POOL_TIME * TimeUtils.SECOND) {
				turn();
			}
		}
			break;
		case TURN_SIDE_POOL: {
		
			long durationTime = now - lastFlagTime;

			if (durationTime >= SIDE_POOL_TIME * TimeUtils.SECOND) {
				river();
			}
		}
			break;
		case RIVER_SIDE_POOL: {
			
			long durationTime = now - lastFlagTime;

			if (durationTime >= SIDE_POOL_TIME * TimeUtils.SECOND) {
				compare();
			}
		}
			break;
		case PREFLOP:
		case FLOP:
		case TURN:
		case RIVER: {
		
			long durationTime = now - lastFlagTime;

			if (durationTime >= PLAYER_TIME * TimeUtils.SECOND) {
				playerGiveup(currentBetter);
			}
		}
			break;
		case END: {
		
			long durationTime = now - lastFlagTime;
			if (durationTime >= totalSettleTime * TimeUtils.SECOND) {
				checkAllPlayerCoins();
				restart();
			}
		}
			break;
		default:
			break;
		}
	}

	/**
	 * 押注
	 * 
	 * @param betNum
	 */
	public void bet(Player roomPlayer, long betNum) {
		
		long tempBet = betNum
				+ roomPlayer.getHuman().getHumanTexasManager().getCurrentBet();
		if (maxBet < tempBet) {
			maxBetPos = roomPlayer.getHuman().getHumanTexasManager().getPos();
			maxBet = tempBet;
		}
		betPool += betNum;
		
		roomPlayer.getHuman().getHumanTexasManager().bet(betNum);
		
	}

	/**
	 * 选定下一个玩家
	 */
	private void nextBetter(int pos) {

		// 获得下一个押注玩家
		Player roomPlayer = playerManager.getNextBetter(pos);

		HumanTexasManager humanTexasManager = roomPlayer.getHuman()
				.getHumanTexasManager();
		currentBetter = roomPlayer;
		currentPos = humanTexasManager.getPos();
		
		if (humanTexasManager.getCoins() == 0) {
			afterPlayerAction(roomPlayer);
			return;
		}

		// 轮到该玩家
		playerTurn(roomPlayer);
	}

	/**
	 * 开始一轮
	 */
	private void startNextRound() {
		for (Player roomPlayer : playerManager.getRoomPlayerList()) {
			HumanTexasManager humanTexasManager = roomPlayer.getHuman()
					.getHumanTexasManager();
			humanTexasManager.startNextRound();
		}
		maxBet = 0;
		maxBetPos = smallBlindPos;
		currentPos = bankerPos;
	}

	/**
	 * 下大小盲注
	 */
	private void preflop() {
		gid = Globals.getUUIDService().getNextUUID(
				Globals.getTimeService().now(), UUIDType.HUMANTEXASROOMGAMEID);
		
		logger.info("德州房间类型["+texasRoomEnum+",德州房间[" + rid + "],开始德州扑克,德州房间局号[" + gid + "]");
		
		logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
		
		long now = Globals.getTimeService().now();
	
	
		long nextBlindTime = 0;
		//判断是否升盲住
		if(isSNG()){
			long tempDuration2 =now-startTime;
			long tempDuration =tempDuration2;
			List<SngMatchConfigTemplate> sngMatchConfigTemplateList = Globals.getTexasService().getSngMatchConfigTemplateList(this.getId());
			for(SngMatchConfigTemplate sngMatchConfigTemplate : sngMatchConfigTemplateList){
				tempDuration-=sngMatchConfigTemplate.getBlindTime()*TimeUtils.SECOND;
				if(tempDuration>0){ 
					continue;
				}
				nextBlindTime = - tempDuration;
				smallBlind = sngMatchConfigTemplate.getSmallBlind();
				logger.info("局数["+currentGames+"],盲注[" + smallBlind + "]时间["+((float)tempDuration2)/TimeUtils.SECOND+"]");
				break;
			}
		}
		
		Globals.getTexasService().onStart(this,nextBlindTime);
		++currentGames;
		
		startNextRound();
		
		//AAA 设置玩家状态
		int serviceFee = (int) Math.ceil(getSmallBlind() * 2* getServiceValue2() / 10000.0f);
		
		long jackpotPoolPer =  Globals.getTexasService().getJackpotPoolPer(id);
		
		long jackpotAddPoolPer =  Globals.getTexasService().getJackpotAddPoolPer(id);
		
		Textas textas =  Globals.getTexasService().getTextas(id);
		
		textas.setJackpot(textas.getJackpot()+(int) Math.ceil(jackpotPoolPer * serviceFee/ 10000.0f));
		textas.setCumuJackpot(textas.getCumuJackpot()+(int) Math.ceil(jackpotAddPoolPer * serviceFee/ 10000.0f));
		
		
		for (Player roomPlayer : playerManager.getRoomPlayerList()) {
			HumanTexasManager humanTexasManager = roomPlayer.getHuman()
					.getHumanTexasManager();
			humanTexasManager.start(serviceFee);
			
			logger.info("玩家[" + roomPlayer.getPassportId() + "]位置["+humanTexasManager.getPos()+"]平均抽水["+serviceFee+"]");
			
			Globals.getTexasService().onSyncGold(roomPlayer, this);
		}
		
		texasRoomState = TexasRoomState.PREFLOP_WAIT;
		lastFlagTime = now;
		logger.info("房间状态[" + texasRoomState + "]");
		// 选庄家
		if (getBankerPos() == -1) {
			this.bankerPos = playerManager.getRandomBankerPos();
		} else {
			Player tempPlayer = playerManager.getNextBetter(getBankerPos());
			if (tempPlayer == null) {
				logger.error("德州房间[" + texasRoomState + "] 逻辑错误");
				logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
				throw new TexasException("只有一个玩家不能开始");
				
			}
			this.bankerPos = tempPlayer.getHuman().getHumanTexasManager().getPos();
		}

		Globals.getTexasService().onBankerPos(this, this.bankerPos);
		logger.info("庄家位置["+this.bankerPos+"]");
		
		// 下大小盲注
		int smallBlindBet = this.getSmallBlind();

		Player smallBlindPlayer = playerManager.getNextBetter(getBankerPos());
		if (smallBlindPlayer == null) {
			logger.error("德州房间[" + texasRoomState + "] 逻辑错误");
			logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
			throw new TexasException("小盲注玩家没找到");
		}
		
		
		smallBlindPos = smallBlindPlayer.getHuman().getHumanTexasManager()
				.getPos();
		
		
		
		Player bigBlindPlayer = playerManager.getNextBetter(smallBlindPos);
		if (bigBlindPlayer == null) {
			logger.error("德州房间[" + texasRoomState + "] 逻辑错误");
			logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
			throw new TexasException("");
		}
		
		long shouldSmallBet = smallBlindBet;
		if(isSNG()){
			if(smallBlindPlayer.getHuman().getHumanTexasManager().getCoins()<shouldSmallBet){
				shouldSmallBet = smallBlindPlayer.getHuman().getHumanTexasManager().getCoins();
			}
		}
		
		bet(smallBlindPlayer, shouldSmallBet);
		logger.info("玩家["+smallBlindPlayer.getPassportId()+"],位置["+smallBlindPos+"],下小盲注["+shouldSmallBet+"]");
		
		Globals.getTexasService().onSmallBlind(this,smallBlindPos,shouldSmallBet);
		
		long shouldBidBlindBet = smallBlindBet * 2;
		if(isSNG()){
			if(bigBlindPlayer.getHuman().getHumanTexasManager().getCoins()<shouldBidBlindBet){
				shouldBidBlindBet = bigBlindPlayer.getHuman().getHumanTexasManager().getCoins();
			}
		}
		
		int bigBlindPos = bigBlindPlayer.getHuman().getHumanTexasManager().getPos();
		bet(bigBlindPlayer, shouldBidBlindBet);
		logger.info("玩家["+bigBlindPlayer.getPassportId()+"],位置["+bigBlindPos+"],下大盲注["+shouldBidBlindBet+"]");
		
		Globals.getTexasService().onBigBlind(this,bigBlindPlayer.getHuman().getHumanTexasManager().getPos(),shouldBidBlindBet);
		
		maxBet = smallBlindBet * 2;
		maxBetPos = -1;
		currentBetter = bigBlindPlayer;
		currentPos = currentBetter.getHuman().getHumanTexasManager().getPos();
		
		// 发底牌
		buttomDeal();
		Globals.getTexasService().onPreflop(this);

	}

	/**
	 * 发底牌
	 */
	private void buttomDeal() {
		for (Player roomPlayer : playerManager.getRoomPlayerList()) {
			for (int i = 0; i < 2; i++) {
				Card card = texasDeck.getNextCard();
				roomPlayer.getHuman().getHumanTexasManager().getCardList()
						.add(card);
				roomPlayer.getHuman().getHumanTexasManager().getWholeCardList()
						.add(card);
			}
		}
		logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
	}

	/**
	 * 发三张公共牌
	 */
	private void flop() {
		long now = Globals.getTimeService().now();
		texasRoomState = TexasRoomState.FLOP_WAIT;
		lastFlagTime = now;
		logger.info("房间状态[" + texasRoomState + "]");
		startNextRound();
	
		for (int i = 0; i < 3; i++) {
			Card card = texasDeck.getNextCard();
			publicCardList.add(card);
		}
		
		logger.info("公共牌:["+publicCardList+"]");
		
		Globals.getTexasService().onFlop(this);
		
		for (Player roomPlayer : playerManager.getRoomPlayerList()) {
			if (roomPlayer.getHuman().getHumanTexasManager().getPlayerState() == RoomPlayerState.GAMING) {
				roomPlayer.getHuman().getHumanTexasManager().getWholeCardList()
						.addAll(publicCardList);
				roomPlayer.getHuman().getHumanTexasManager().convert();
				Globals.getTexasService().onSendTexasHandCardType(roomPlayer,this);
			}
		}
		
		logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
	}

	/**
	 * 发第四张牌
	 */
	public void turn() {
		long now = Globals.getTimeService().now();
		lastFlagTime = now;
		texasRoomState = TexasRoomState.TURN_WAIT;
		startNextRound();
		
		logger.info("德州房间[" + texasRoomState + "]");

		Card card = texasDeck.getNextCard();
		publicCardList.add(card);
		
		logger.info("公共牌:["+publicCardList+"]");
		
		Globals.getTexasService().onTurn(this);
		
		for (Player roomPlayer : playerManager.getRoomPlayerList()) {
			if (roomPlayer.getHuman().getHumanTexasManager().getPlayerState() == RoomPlayerState.GAMING) {
				roomPlayer.getHuman().getHumanTexasManager().getWholeCardList()
						.add(card);
				roomPlayer.getHuman().getHumanTexasManager().convert();
				Globals.getTexasService().onSendTexasHandCardType(roomPlayer,this);
			}
		}
		
		logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
	}

	/**
	 * 发第五张牌
	 */
	public void river() {
		long now = Globals.getTimeService().now();
		lastFlagTime = now;
		texasRoomState = TexasRoomState.RIVER_WAIT;
		startNextRound();
		logger.info("德州房间[" + texasRoomState + "]");

		Card card = texasDeck.getNextCard();
		publicCardList.add(card);
		
		logger.info("公共牌:["+publicCardList+"]");
		
		Globals.getTexasService().onRiver(this);
		
		for (Player roomPlayer : playerManager.getRoomPlayerList()) {
			if (roomPlayer.getHuman().getHumanTexasManager().getPlayerState() == RoomPlayerState.GAMING) {
				roomPlayer.getHuman().getHumanTexasManager().getWholeCardList()
						.add(card);
				roomPlayer.getHuman().getHumanTexasManager().convert();
				Globals.getTexasService().onSendTexasHandCardType(roomPlayer, this);
			}
		}
		logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
	}

	/**
	 * 比牌
	 */
	private void compare() {
		logger.info("比牌中");
		settle();
	}

	/**
	 * 比较每一个池子最大牌的人
	 */
	public List<Player> winnerListForPool(List<Player> aPlayerList) {
		List<Player> mainPoolWinnerList = new ArrayList<Player>();
		for (Player player : aPlayerList) {
			if (player.getHuman().getHumanTexasManager().isGaming()) {
				if (mainPoolWinnerList.size() == 0) {
					mainPoolWinnerList.add(player);
				} else {
					HumanTexasManager winnerHumanTexasManager = mainPoolWinnerList.get(0).getHuman().getHumanTexasManager();
					
					HumanTexasManager tempHumanTexasManager = player.getHuman().getHumanTexasManager();
					
					int result = tempHumanTexasManager.getTexasHandCard().compareTo(winnerHumanTexasManager.getTexasHandCard());
					
					if (result == 0) {//牌一样大追加
						mainPoolWinnerList.add(player);
					} else if (result == 1) {
						mainPoolWinnerList.clear();
						mainPoolWinnerList.add(player);
					}
				}
			}
		}
		return mainPoolWinnerList;
	}
	
	/**
	 * 获取彩金奖励
	 * @param aPlayerList
	 * @return
	 */
	public Map<Integer,Player> maxCard(List<Player> aPlayerList){
		Map<Integer,Player> map = new HashMap<Integer,Player>();
		
		for (Player player : aPlayerList) {
			if (player.getHuman().getHumanTexasManager().isGaming()) {
				HumanTexasManager htm = player.getHuman().getHumanTexasManager();
				//获取自己的牌型
				TexasHandCardEnum thc = htm.getTexasHandCard().getTexasHandCardEnum();
				
				if(Globals.getTexasService().isCar(thc.getIndex())){
					changeJackpotTextas(thc,htm,player.getHuman());
				}
				//成就牌型统计
				player.getHuman().getHumanAchievementManager().updateTexasCarType(thc.getIndex());
			}
			
		}
		return map;
	}
	
	/**
	 * 
	 * @param thc
	 * @param htm
	 * @return 玩家获得彩金
	 */
	private void changeJackpotTextas(TexasHandCardEnum thc,HumanTexasManager htm,Human human){
		
		double jackpotPer = Globals.getTexasService().getCarType(thc.getIndex());
		
		int roomId = htm.getTexasRoom().getId();
		
		Textas textas =  Globals.getTexasService().getTextas(roomId);
		
		//这个德州房间的彩金数量
		long jackpot = textas.getJackpot();
		
		//玩家获得
		int glob = (int)Math.ceil(jackpot*jackpotPer);
		
		//彩金减少
		textas.setJackpot(jackpot - glob);
		
		long jackpot1 = textas.getJackpot();
		//初始值
		long iniValue = Globals.getTexasService().getJackpotinit(roomId);
		
		if(jackpot1 < iniValue){
			long cumuJackpot = textas.getCumuJackpot();
			
			if((jackpot1 + cumuJackpot) > iniValue){
				textas.setCumuJackpot(cumuJackpot - (iniValue - jackpot1) );
				textas.setJackpot(iniValue);
			}else{
				textas.setCumuJackpot(0);
				textas.setJackpot(jackpot1+cumuJackpot);
			}
		}
		textas.setModified();
		
		String detailReason = MessageFormat.format(LogReasons.GoldLogReason.TEXAS_JACKPOT.getReasonText(),htm.getTexasRoom().getId() ,glob);
		human.giveMoney(glob, Currency.GOLD, true, LogReasons.GoldLogReason.TEXAS_JACKPOT, detailReason, -1, 1);
		
		//添加彩金
		Globals.getJackpotServer().add(human.getPassportId(), human.getImg(), human.getName(), glob,GameType.TEXAS.getIndex());
		
		Globals.getNoticeService().broadcastNoticeMulti(LangConstants.JACKPOT_BROADCAST, String.valueOf(GameType.TEXAS.getIndex()),human.getName(),human.getImg(),String.valueOf(glob));

	}

	/**
	 * 分池
	 */
	private void sidePool() {
		texasSidePoolList.clear();

		List<Player> remainPlayerList = new ArrayList<Player>();
		for (Player player : playerManager.getRoomPlayerList()) {
			HumanTexasManager humanTexasManager = player.getHuman()
					.getHumanTexasManager();
			if (humanTexasManager.getPlayerState() == RoomPlayerState.GAMING) {
				remainPlayerList.add(player);
			}
		}

		Collections.sort(remainPlayerList, new Comparator<Player>() {
			public int compare(Player player1, Player player2) {

				if (player1.getHuman().getHumanTexasManager().getAllBet() > player2
						.getHuman().getHumanTexasManager().getAllBet())
					return 1;
				if (player1.getHuman().getHumanTexasManager().getAllBet() < player2
						.getHuman().getHumanTexasManager().getAllBet())
					return -1;
				return 0;
			}
		});

		// 取最小值
		int min = remainPlayerList.get(0).getHuman().getHumanTexasManager()
				.getAllBet();
		int previous = min;

		List<Player> tempRemainPlayerList = new ArrayList<Player>();
		TexasPool texasPool = null;
		for (int i = 0; i < remainPlayerList.size(); i++) {
			Player tempPlayer = remainPlayerList.get(i);
			if (i == 0) {
				tempRemainPlayerList.add(tempPlayer);
			}

			int tempAllBet = tempPlayer.getHuman().getHumanTexasManager()
					.getAllBet();

			if (tempAllBet > min) {
				if (texasPool == null) {
					texasPool = new TexasPool();
					texasPool.setBet(tempAllBet - min);
					texasPool.getPlayerList().add(tempPlayer);
					previous = tempAllBet;
					continue;
				}

				if (tempAllBet == previous) {
					texasPool.getPlayerList().add(tempPlayer);

				} else {
					texasSidePoolList.add(texasPool);
					texasPool = new TexasPool();
					texasPool.setBet(tempAllBet - min);
					texasPool.getPlayerList().add(tempPlayer);
					texasPool.setBet(tempAllBet - previous);
				}

				previous = tempAllBet;

			}
		}

		if (texasPool != null) {
			texasSidePoolList.add(texasPool);
		}

		mainPool = betPool;

		for (int i = 0; i < texasSidePoolList.size(); i++) {
			int tempPoolNum =  texasSidePoolList.get(i).getBet()
					* texasSidePoolList.get(i).getPlayerList().size();
			mainPool -= tempPoolNum;
			logger.info("分池"+i+":["+tempPoolNum+"]");
		}
		
		logger.info("主池["+mainPool+"]");
		
		Globals.getTexasService().onSidePool(this);
	}



	/**
	 * 结算放弃
	 */
	private void settleGiveUp() {
		logger.info("剩余一人结算中");

		for (Player player : playerManager.getRoomPlayerList()) {
			HumanTexasManager humanTexasManager = player.getHuman()
					.getHumanTexasManager();
			if (humanTexasManager.getPlayerState() == RoomPlayerState.GAMING) {
				logger.info("玩家["+player.getPassportId()+"],位置["+humanTexasManager.getPos()+"]赢得主池["+betPool+"]");
				humanTexasManager.win(betPool);
				humanTexasManager.settle(getServiceValue1(), false);
				Globals.getTexasService().onSettleGiveUp(player, this);
				break;
			}
		}
		
		//彩金结算
		//maxCard(playerManager.getRoomPlayerList());
		
		
		long now = Globals.getTimeService().now();
		lastFlagTime = now;
		texasRoomState = TexasRoomState.END;
		logger.info("德州房间状态[" + texasRoomState + "]");

	//	checkAllPlayerCoins();
	}

	/**
	 * 结算 
	 * 每一局最后的结算
	 */
	private void settle() {
		logger.info("结算中");

		List<TexasPoolSettleInfoData> texasPoolSettleInfoDataList = new ArrayList<TexasPoolSettleInfoData>();
		
		// 结算主池
		List<Player> mainPoolWinnerList = winnerListForPool(playerManager.getRoomPlayerList());
        
		//彩金结算
		maxCard(playerManager.getRoomPlayerList());
		
		TexasPoolSettleInfoData mainTexasPoolSettleInfoData = buildPoolSettleInfoData(mainPoolWinnerList, mainPool);

		texasPoolSettleInfoDataList.add(mainTexasPoolSettleInfoData);
		// 结算边池
		for (int i = 0; i < texasSidePoolList.size(); i++) {
			
			TexasPool texasPool = texasSidePoolList.get(i);
			
			List<Player> tempSidePoolWinnerList = winnerListForPool(texasPool.getPlayerList());
			if(tempSidePoolWinnerList.size() == 0){
				continue;
			}
			
			TexasPoolSettleInfoData sideTexasPoolSettleInfoData = buildPoolSettleInfoData(tempSidePoolWinnerList, texasPool.getAllbet());
	
			texasPoolSettleInfoDataList.add(sideTexasPoolSettleInfoData);
			
			if(texasPool.getPlayerList().size() ==  1){ //判断是否是有效下注 如果池子里面的人数是一  就是无效押注
				Player player = texasPool.getPlayerList().get(0);
				if(player != null){
					player.getHuman().getHumanTexasManager().invalidBet(texasPool.getBet());
				}
			}
			
			
		}

		List<TexasRoomPlayerSettleCardsInfoData> texasRoomPlayerSettleCardsInfoDataList = new ArrayList<TexasRoomPlayerSettleCardsInfoData>();

		Map<Long,Integer> expMap = new HashMap<Long,Integer>();
				
		for (Player player : playerManager.getRoomPlayerList()) {
			HumanTexasManager humanTexasManager = player.getHuman()
					.getHumanTexasManager();
			if (humanTexasManager.isGaming()) {
				TexasRoomPlayerSettleCardsInfoData texasRoomPlayerSettleCardsInfoData = new TexasRoomPlayerSettleCardsInfoData();
				int[] cardArr = new int[5];
				for (int j = 0; j < 5; j++) {
					cardArr[j] = humanTexasManager.getTexasHandCard()
							.getFinalCardList().get(j).getValue();
				}
				texasRoomPlayerSettleCardsInfoData.setPos(humanTexasManager
						.getPos());
				texasRoomPlayerSettleCardsInfoData.setCardListStr(String
						.format("%d,%d,%d,%d,%d", cardArr[0], cardArr[1],
								cardArr[2], cardArr[3], cardArr[4]));
				texasRoomPlayerSettleCardsInfoData.setFirstCard(player
						.getHuman().getHumanTexasManager().getCardList().get(0)
						.getValue());
				texasRoomPlayerSettleCardsInfoData.setSecondCard(player
						.getHuman().getHumanTexasManager().getCardList().get(1)
						.getValue());
				texasRoomPlayerSettleCardsInfoDataList
						.add(texasRoomPlayerSettleCardsInfoData);
				
				logger.info("玩家["+player.getPassportId()+"]赢得筹码["+humanTexasManager.getWinbet()+"]");
				
				humanTexasManager.settle(getServiceValue1(), true);

			}
			//expMap.put(player.getPassportId(), humanTexasManager.getExp());
			//humanTexasManager.addHumanExp();
		}
		
		//sendMessageExp(expMap);

		Globals.getTexasService().onSettle(this, texasPoolSettleInfoDataList, texasRoomPlayerSettleCardsInfoDataList);

		long now = Globals.getTimeService().now();
		lastFlagTime = now;
		totalSettleTime = (texasSidePoolList.size() + 1) * SETTLE_TIME;
		texasRoomState = TexasRoomState.END;
		logger.info("德州房间状态[" + texasRoomState + "]");

	//	checkAllPlayerCoins();
	}
	
	private void sendMessageExp(Map<Long,Integer> expMap){
		TexasRoomExp[] expNum = new TexasRoomExp[expMap.size()];
		int inedx = 0;
		for(Entry<Long, Integer> en : expMap.entrySet()){
			TexasRoomExp t = new TexasRoomExp();
			t.setPlayerId(en.getKey());
			t.setExp(en.getValue());
			expNum[inedx++] = t;
		}
        GCHumanTexasExp message = new GCHumanTexasExp();
        message.setTexasRoomExp(expNum);
		Globals.getTexasService().broadcastMsg(this, message);
	}

	/**
	 * 判断玩家是否筹码不够
	 */
	private void checkAllPlayerCoins() {
		
		logger.info("玩家信息:{\n"+this.playersLog()+"\n}");
		logger.info("检查筹码中");
		
		List<Player> kickPlayers = new ArrayList<Player>();
		for (Player player : playerManager.getRoomPlayerList()) {
			HumanTexasManager humanTexasManager = player.getHuman()
					.getHumanTexasManager();
			// 判断是否筹码足够
			if (!humanTexasManager.isStillInSeat()) {
				// 判断 是否补足金币
				long tempComplement = humanTexasManager.complementGold();
				if (tempComplement!=0) {
					player.sendMessage(new GCTexasComplementNum(tempComplement));
					logger.info("玩家["+player.getPassportId()+"]补充筹码["+humanTexasManager.getCoins()+"]");
					
				} else {
					kickPlayers.add(player);
				}
			}else{
				
				if(humanTexasManager.getNextComplement()!=0){
					// 判断 是否补足金币
					long tempComplement = humanTexasManager.complementGold();
					if (tempComplement!=0) {
						player.sendMessage(new GCTexasComplementNum(tempComplement));
						logger.info("玩家["+player.getPassportId()+"]补充筹码["+humanTexasManager.getCoins()+"]");
						
					} 
				}
		
			}
		}
		
		//玩家离开
		for (Player player : kickPlayers) {
			playerLeave(player, true);
		}
		
		//sng 设置排名
		if(this.isSNG()){
			//设置排名
			for(Player player : kickPlayers){
				sngSetRank(player);
				
			}
		}
		
		//结算
		for(Player player : kickPlayers){
			playerSettle(player);
		}
		
		
		
	}

	/**
	 * 结算数据
	 */
	private TexasPoolSettleInfoData buildPoolSettleInfoData(List<Player> aWinnerPlayerList, int bet) {
		
		
		TexasPoolSettleInfoData texasPoolSettleInfoData = new TexasPoolSettleInfoData();
		
		int tempBet = bet / aWinnerPlayerList.size();

		TexasRoomPlayerSettleInfoData[] tempWinnerList = new TexasRoomPlayerSettleInfoData[aWinnerPlayerList.size()];
		
		for (int i = 0; i < aWinnerPlayerList.size(); i++) {
			
			Player player = aWinnerPlayerList.get(i);
			
			HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
			// 获得筹码
			humanTexasManager.win(tempBet);
			
			TexasRoomPlayerSettleInfoData texasRoomPlayerSettleInfoData = new TexasRoomPlayerSettleInfoData();
			texasRoomPlayerSettleInfoData.setPos(player.getHuman().getHumanTexasManager().getPos());
			texasRoomPlayerSettleInfoData.setWinBet(tempBet);
			texasRoomPlayerSettleInfoData.setHandCardType(humanTexasManager
					.getTexasHandCard().getTexasHandCardEnum().getIndex());

			tempWinnerList[i] = texasRoomPlayerSettleInfoData;

		}
		texasPoolSettleInfoData.setWinnerList(tempWinnerList);
		return texasPoolSettleInfoData;
	}

	/**
	 * 轮到该玩家
	 */
	private void playerTurn(Player roomPlayer) {
		lastFlagTime = Globals.getTimeService().now();
		logger.info("轮到玩家["+roomPlayer.getPassportId()+"],位置["+roomPlayer.getHuman().getHumanTexasManager().getPos()+"]");

		Globals.getTexasService().onPlayerTurn(roomPlayer, this);
	}

	/**
	 * 判断是否结束
	 */
	private boolean isOver() {
		// 玩家就剩一个的时候
		int numInGame = 0;
		for (Player roomPlayer : playerManager.getRoomPlayerList()) {
			if (roomPlayer.getHuman().getHumanTexasManager().getPlayerState() == RoomPlayerState.GAMING) {
				++numInGame;
			}
		}

		if (numInGame == 1)
			return true;
		return false;
	}

	/**
	 * 判断是否全部allin
	 */
	private boolean isAllAllIn() {

		int numInGame = 0;
		for (Player roomPlayer : playerManager.getRoomPlayerList()) {
			if (roomPlayer.getHuman().getHumanTexasManager().getPlayerState() == RoomPlayerState.GAMING
					&& roomPlayer.getHuman().getHumanTexasManager().getCoins() != 0) {
				++numInGame;

			}
		}
		// 剩余一个没有allin 或0个
		if (numInGame <= 1)
			return true;
		return false;
	}

	/**
	 * 判断是否是新一轮了
	 */
	private boolean isLastPlayer(Player roomPlayer) {
		int curPos = roomPlayer.getHuman().getHumanTexasManager().getPos();
		Player player = playerManager.getNextBetter(curPos);
		if (player == null)
			return true;

		int nextPos = player.getHuman().getHumanTexasManager().getPos();
		
		if(maxBetPos == -1)
			return false;
		int tempMax = maxBetPos;
		
		int tempNextPos = nextPos;
		if (tempMax <= curPos)
			tempMax += MAX_PLAYER_COUNT;
		if (tempNextPos <= curPos)
			tempNextPos += MAX_PLAYER_COUNT;

		if (curPos < tempMax && tempMax <= tempNextPos) {
			return true;
		}

		return false;
	}



	/**
	 * 房间信息
	 * 
	 * @return
	 */
	public GCTexasRoomInfo buildTexasRoomInfoData() {

		GCTexasRoomInfo gcTexasRoomInfo = new GCTexasRoomInfo();

		gcTexasRoomInfo.setBankPos(this.getBankerPos());
		gcTexasRoomInfo.setCurrentBetterPos(currentPos);
		int[] tempPublicCardArr = new int[publicCardList.size()];
		for (int i = 0; i < publicCardList.size(); i++) {
			tempPublicCardArr[i] = publicCardList.get(i).getValue();
		}
		gcTexasRoomInfo.setPublicCardList(tempPublicCardArr);

		long[] sidePool = new long[texasSidePoolList.size() + 1];
		sidePool[0] = mainPool;
		for (int i = 0; i < texasSidePoolList.size(); i++) {
			sidePool[i + 1] = texasSidePoolList.get(i).getBet()
					* texasSidePoolList.get(i).getPlayerList().size();
		}
		gcTexasRoomInfo.setSidePoolList(sidePool);

		return gcTexasRoomInfo;
	}
	
	/**
	 * 是否是sng
	 * @return
	 */
	public boolean isSNG(){
		return this.texasRoomEnum == TexasRoomEnum.SNG;
	}
	
	/**
	 * 是否是vip
	 */
	public boolean isVip(){
		return this.texasRoomEnum == TexasRoomEnum.VIP;
	}
	
	/**
	 * 是否是普通
	 */
	public boolean isNormal(){
		return this.texasRoomEnum == TexasRoomEnum.NORMAL;
	}
	
	/**
	 * sng排名奖励
	 */
	public int sngRewardForRank(int rank){
		if(rank>=3)
			return 0;
		
		List<Player> tempPlayerList=sngRankMap.get(rank);
		int tempPlayerSize = tempPlayerList.size();
		Assert.isTrue(tempPlayerSize>=1);
		SngMatchTemplate sngMatchTemplate = Globals.getTemplateService().get(this.getId(), SngMatchTemplate.class);
		if(rank == 2){
			return sngMatchTemplate.getChipsReward2()/tempPlayerSize;
		}
		else
		{
			return sngMatchTemplate.getChipsReward1();
		}
	}
	
	/**
	 * 是否是游戏中
	 */
	public boolean isGaming(){
		if(texasRoomState == TexasRoomState.INIT)
			return false;
		if(texasRoomState == TexasRoomState.WAIT_PEOPLE)
			return false;
		if(texasRoomState==TexasRoomState.WAIT)
			return false;
		if(texasRoomState == TexasRoomState.END)
			return false;
		return true;
	}


	/**
	 * 庄家位置
	 * @return
	 */
	public int getBankerPos() {
		return bankerPos;
	}
	
	public List<Card> getPublicCardList(){
		return this.publicCardList;
	}
	
	public int getBetPool(){
		return this.betPool;
	}
	
	public int getMainPool(){
		return this.mainPool;
	}
	
	public List<TexasPool> getTexasSidePoolList(){
		return texasSidePoolList;
	}
	
	public boolean ifBestSuit(){
		return this.getPlayerManager().getRoomPlayerList().size()>=this.bestSuitNum;
	}
	
	public String playersLog(){
		StringBuilder sb = new StringBuilder();
		for(Player player : playerManager.getRoomPlayerList()){
			sb.append(playerLog(player));
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * 玩家信息
	 * @param player
	 * @return
	 */
	public String playerLog(Player player){
		HumanTexasManager humanTexasManager= player.getHuman().getHumanTexasManager();
		return humanTexasManager.buildGameLog();
	}
}
