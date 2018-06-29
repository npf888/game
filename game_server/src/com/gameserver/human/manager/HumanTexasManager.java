package com.gameserver.human.manager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.LogReasons.GoldLogReason;
import com.common.constants.Loggers;
import com.common.model.Card;
import com.core.util.Assert;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanTexasEntity;
import com.db.model.HumanTexasSNGEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.rank.RankLogic;
import com.gameserver.rank.enums.RankTypeEnum;
import com.gameserver.texas.HumanTexas;
import com.gameserver.texas.TexasRoom;
import com.gameserver.texas.Textas;
import com.gameserver.texas.data.TexasHandCard;
import com.gameserver.texas.data.TexasRoomExp;
import com.gameserver.texas.data.TexasRoomPlayerInfoData;
import com.gameserver.texas.enums.RoomPlayerState;
import com.gameserver.texas.msg.GCHumanTexasExp;
import com.gameserver.texas.msg.GCHumanTexasInfoData;
import com.gameserver.texas.msg.GCHumanTexasSngInfoData;
import com.gameserver.texas.msg.GCSngRank;
import com.gameserver.texas.sng.HumanTexasSNG;
import com.gameserver.texas.template.SngMatchTemplate;


/**
 * 德州扑克管理器
 * 
 * @author Thinker
 *
 */
public class HumanTexasManager implements RoleDataHolder, InitializeRequired {

	private Logger logger = Loggers.texasRoomLogger;
	private Logger cardLogger = Loggers.texasCardLogger;

	private Human owner;
	private HumanTexas humanTexas;
	private HumanTexasSNG humanTexasSNG;

	/** 德州房间 */
	private TexasRoom texasRoom;
	/** 玩家状态 */
	private RoomPlayerState playerState;
	/** 总筹码 也就是进入房间的原始筹码   在加入房间的时候设置 普通房间  VIP房间 为当前角色的金钱数量 SNG房间为配置数据*/
	private long coins;
	/** 位置 */
	private int pos;
	/** 当前押注  当前轮出得筹码，要进行下一轮的时候清空*/
	private int currentBet;
	/** 总押注    每次压押注的时候 coins 减 ，allBet 加*/
	private int allBet;
	/** 赢得 **/
	private int winBet;
	
	/** 有效押注 每局清零 **/
	private int invalidBet;
	
	/** 每一局结算的最后少于固定筹码 追加到这个变量里面，下一局结算的时候一块算上**/
	private int betCumulative;
	

	/** 底牌 */
	private List<Card> cardList = new ArrayList<Card>();
	/** 牌型 */
	private List<Card> wholeCardList = new ArrayList<Card>();
	/** 德州手牌 */
	private TexasHandCard texasHandCard;
	/** 德州最大手牌 */
	private TexasHandCard biggestTexasHandCard;
	/** 德州下把补充筹码 */
	private long nextComplement;
	
	/**是否使用门票*/
	private boolean ifUseTicket;
	/**排名*/
	private int sngRank;
	
	/**
	 * 构造器
	 * 
	 * @param owner
	 */
	public HumanTexasManager(Human owner) {
		this.owner = owner;
		playerState = RoomPlayerState.INIT;
	}

	/**
	 * 加载德州信息
	 */
	public void load() {
		loadNormal();
		loadSNG();
	}
	
	/**
	 * 加载普通场数据
	 */
	private void loadNormal(){
		HumanTexasEntity humanTexasEntity = Globals.getDaoService()
				.getHumanTexasDao()
				.getHumanTexasByCharId(owner.getPassportId());
		humanTexas = new HumanTexas();
		if (humanTexasEntity == null) {
			long now = Globals.getTimeService().now();
			humanTexas.setDbId(Globals.getUUIDService().getNextUUID(now,
					UUIDType.HUMANTEXASID));
			humanTexas.setCharId(owner.getPassportId());
			humanTexas.setPeople(Globals.getConfigTempl().getDefaultNum());
			humanTexas.setIsAuto(Globals.getConfigTempl().getSupplyChips());
			humanTexas.setCount(0);
			humanTexas.setDayBiggestWinCoins(0);
			humanTexas.setWeekWinCoins(0);
			humanTexas.setWinCount(0);

			humanTexas.setCreateTime(Globals.getTimeService().now());
			humanTexas.setOwner(owner);
			humanTexas.setInDb(false);
			humanTexas.active();
			humanTexas.setModified();
			return;
		}

		humanTexas.setOwner(owner);

		humanTexas.fromEntity(humanTexasEntity);
	}
	
	/**
	 * 加载sng
	 */
	private void loadSNG(){
		HumanTexasSNGEntity humanTexasSNGEntity = Globals.getDaoService()
				.getHumanTexasSNGDao()
				.getHumanTexasSNGByCharId(owner.getPassportId());
		humanTexasSNG = new HumanTexasSNG();
		if (humanTexasSNGEntity == null) {
			long now = Globals.getTimeService().now();
			humanTexasSNG.setDbId(Globals.getUUIDService().getNextUUID(now,
					UUIDType.HUMANTEXASSNGID));
			humanTexasSNG.setCharId(owner.getPassportId());
			humanTexasSNG.setCopperNum(0);
			humanTexasSNG.setGoldNum(0);
			humanTexasSNG.setSilverNum(0);
			humanTexasSNG.setJoinTimes(0);
			humanTexasSNG.setWeekScore(0);
			humanTexasSNG.setCreateTime(Globals.getTimeService().now());
			humanTexasSNG.setOwner(owner);
			humanTexasSNG.setInDb(false);
			humanTexasSNG.active();
			humanTexasSNG.setModified();
			return;
		}

		humanTexasSNG.setOwner(owner);

		humanTexasSNG.fromEntity(humanTexasSNGEntity);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		if (humanTexas.getBiggestHandCardList().size() != 0) {
			biggestTexasHandCard = TexasHandCard.convertFrom(humanTexas
					.getBiggestHandCardList());
		}
		
		checkIfSNGRefresh();
	}

	private void checkIfSNGRefresh(){
		long now = Globals.getTimeService().now();
		if(!TimeUtils.isInSameWeek(now, humanTexasSNG.getUpdateTime())){
			humanTexasSNG.setWeekScore(0);
			humanTexasSNG.setModified();
		}
	}
	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub

	}

	/**
	 * 获得德州
	 * 
	 * @return
	 */
	public HumanTexas getHumanTexas() {
		return humanTexas;
	}


	/**
	 * 获得德州sng
	 * 
	 * @return
	 */
	public HumanTexasSNG getHumanTexasSNG() {
		return humanTexasSNG;
	}

	
	public TexasRoom getTexasRoom() {
		return texasRoom;
	}

	public void setTexasRoom(TexasRoom texasRoom) {
		this.texasRoom = texasRoom;
	}

	/**
	 * 获得玩家状态
	 * 
	 * @return
	 */
	public RoomPlayerState getPlayerState() {
		return playerState;
	}

	/**
	 * 设置玩家状态
	 * 
	 * @param roomPlayerState
	 */
	public void setPlayerState(RoomPlayerState roomPlayerState) {
		this.playerState = roomPlayerState;
	}

	/**
	 * 设置筹码
	 * 
	 * @param coins
	 * @return
	 */
	@Deprecated
	public long setCoins(long coins) {
		return this.coins = coins;
	}

	/**
	 * 获得筹码
	 * 
	 * @return
	 */
	public long getCoins() {
		return coins;
	}

	/**
	 * 获得位置
	 * 
	 * @return
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * 获得位置
	 * 
	 * @return
	 */
	public void setPos(int pos) {
		this.pos = pos;
	}

	/**
	 * 获得当轮下注
	 * 
	 * @return
	 */
	public int getCurrentBet() {
		return currentBet;
	}

	/**
	 * 设置下注
	 * 
	 * @param currentBet
	 */
	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}

	/**
	 * 获得总下注
	 * 
	 * @return
	 */
	public int getAllBet() {
		return allBet;
	}

	/**
	 * 设置总下注
	 * 
	 * @param allBet
	 * @deprecated
	 */
	public void setAllBet(int allBet) {
		this.allBet = allBet;
	}

	/**
	 * 获得获胜的筹码
	 * 
	 * @return
	 */
	public int getWinbet() {
		return winBet;
	}

	/**
	 * 获得卡牌
	 * 
	 * @return
	 */
	public List<Card> getCardList() {
		return cardList;
	}

	/**
	 * 获得所有卡牌
	 * 
	 * @return
	 */
	public List<Card> getWholeCardList() {
		return wholeCardList;
	}

	/**
	 * 获得手牌
	 * 
	 * @return
	 */
	public TexasHandCard getTexasHandCard() {
		return texasHandCard;
	}

	public long getNextComplement() {
		return nextComplement;
	}

	public void setNextComplement(long nextComplement) {
		this.nextComplement = nextComplement;
	}
	
	public boolean getIfUseTicket(){
		return this.ifUseTicket;
	}

	public int getSngRank() {
		return sngRank;
	}

	public void setSngRank(int sngRank) {
		this.sngRank = sngRank;
	}

	/**
	 * 获得玩家手牌
	 * 
	 * @return
	 */
	public int[] getCardValueArray() {
		int[] cardValueList = new int[cardList.size()];
		for (int i = 0; i < cardList.size(); i++) {
			cardValueList[i] = cardList.get(i).getValue();
		}
		return cardValueList;
	}

	/**
	 * 开始游戏
	 */
	public void start(int serviceFee) {
		if(!texasRoom.isSNG())
			Globals.getTexasService().onPlay(owner);
		playerState = RoomPlayerState.GAMING;
		coins -= serviceFee;
		allBet = 0;
		currentBet = 0;
		winBet = 0;
		nextComplement = 0;
		betCumulative = 0;
		invalidBet = 0;
		cardList.clear();
		wholeCardList.clear();
		texasHandCard = null;
		humanTexas.setCount(humanTexas.getCount() + 1);
		humanTexas.setModified();
	}

	/**
	 * 开始下一轮bet
	 * 
	 * @return
	 */
	public void startNextRound() {
		currentBet = 0;
	}

	/**
	 * 放弃
	 */
	public void giveUp() {
		playerState = RoomPlayerState.GIVEUP;
	}

	/**
	 * 下注
	 * 
	 * @param bet
	 */
	public void bet(long bet) {
		coins -= bet;
		currentBet += bet;
		allBet += bet;
		invalidBet += bet;
		addExp((int)bet);
		//累计押注统计
	    this.owner.getHumanAchievementManager().updateTexasCumulativeBet(bet);
	}
	
	private void sendMessage(int num){
		TexasRoomExp[] te = new TexasRoomExp[1];
		TexasRoomExp e = new TexasRoomExp();
		e.setPlayerId(owner.getPassportId());
		e.setExp(num);
		te[0] = e;
		GCHumanTexasExp message = new GCHumanTexasExp();
	    message.setTexasRoomExp(te);
		Globals.getTexasService().broadcastMsg(this.texasRoom, message);
	}
	
	
	
	/**
	 * 计算有效押注
	 */
	public void invalidBet(long bet){
		invalidBet -= bet;
	}
	
	

	/**
	 * 换算
	 */
	public void convert() {
		// asc order
		Collections.sort(wholeCardList);
		cardLogger.debug("转化前：" + wholeCardList.toString());
		texasHandCard = TexasHandCard.convertFrom(wholeCardList);
		cardLogger.debug("转化后：" + "牌型[" + texasHandCard.getTexasHandCardEnum()
				+ "]," + texasHandCard.getCardList().toString());
	}

	/**
	 * 获得手牌
	 */

	public void settleCards() {
		if (biggestTexasHandCard == null) {

			humanTexas.getBiggestHandCardList().addAll(
					texasHandCard.getFinalCardList());

			Collections.sort(humanTexas.getBiggestHandCardList());
			biggestTexasHandCard = texasHandCard;
			humanTexas.setModified();
		} else {
			int result = texasHandCard.compareTo(biggestTexasHandCard);
			// 当前更大
			if (result >= 0) {
				humanTexas.getBiggestHandCardList().clear();
				humanTexas.getBiggestHandCardList().addAll(
						texasHandCard.getFinalCardList());
				Collections.sort(humanTexas.getBiggestHandCardList());
				biggestTexasHandCard = texasHandCard;
				humanTexas.setModified();
			}
		}
	}

	/**
	 * 赢得筹码
	 * 
	 * @param bet
	 */
	public void win(int bet) {
		winBet += bet;
	}

	/**
	 * 结算
	 */
	public void settle(int serviceFeeRate, boolean compare) {

		int tempWin = winBet - allBet;
		
		if (tempWin > 0) {//盈利了
            //AAA 抽水
			int serviceFee = (int) Math.ceil(serviceFeeRate * tempWin/ 10000.0f);
			
			long jackpotPoolPer =  Globals.getTexasService().getJackpotPoolPer(texasRoom.getId());
			
			long jackpotAddPoolPer =  Globals.getTexasService().getJackpotAddPoolPer(texasRoom.getId());
			
			Textas textas =  Globals.getTexasService().getTextas(texasRoom.getId());
			
			textas.setJackpot(textas.getJackpot()+(int) Math.ceil(jackpotPoolPer * serviceFee/ 10000.0f));
			textas.setCumuJackpot(textas.getCumuJackpot()+(int) Math.ceil(jackpotAddPoolPer * serviceFee/ 10000.0f));
			
			tempWin -= serviceFee;
			coins+=allBet;
			coins+=tempWin;
			humanTexas.setWinCount(humanTexas.getWinCount() + 1);
			humanTexas.setWeekWinCoins(humanTexas.getWeekWinCoins() + tempWin);
			if (tempWin > humanTexas.getDayBiggestWinCoins()){
				humanTexas.setDayBiggestWinCoins(tempWin);
			}
				
			
			//owner.addExp(texasRoom.getVictoryExp(), null);
		
			humanTexas.setModified();
			if (compare) {
				settleCards();
			}
		  Globals.getRankNewServer().win(owner, tempWin);
		  
		
		  //赢得筹码累计统计
		  this.owner.getHumanAchievementManager().updateTexasCumulativeWin(tempWin);
		  
		} else {
			if(winBet>0){
				coins+=winBet;
				humanTexas.setModified();
			}
			
			if (compare) {
				settleCards();
			}
		}
		
		if(!texasRoom.isSNG()){
			int tempCardEnum = -1;
			if(texasHandCard != null){
				tempCardEnum= texasHandCard.getTexasHandCardEnum().getIndex();
			}
			Globals.getTexasService().onPlayFinished(owner,tempCardEnum,tempWin);
		}
		
		//addHumanExp();
	}
	
	
	
	
	/**
	 * 计算人物经验加成
	 */
	/*public void addHumanExp(){
		
		int count = invalidBet + betCumulative;
		
		int lvRatio = Globals.getConfigTempl().getLvRatio();
		
		owner.addExp(count/lvRatio, null);
		
		betCumulative = count%lvRatio;
		
		invalidBet = 0;
	}*/
	
	public int addExp(int bet){
		int lvRatio = Globals.getConfigTempl().getLvRatio();
		int num = bet/lvRatio;
		owner.addExp(num, null);
		return num;
	}
	
	/**
	 * 获取经验
	 * @return
	 */
	/*public int getExp(){
		
        int count = invalidBet + betCumulative;
		
		int lvRatio = Globals.getConfigTempl().getLvRatio();
		
		return count/lvRatio;
	}*/

	/**
	 * 加入房间
	 * 
	 * @return
	 */
	public void joinRoom(TexasRoom texasRoom,boolean ticket) {
		playerState = RoomPlayerState.INIT;
		this.texasRoom = texasRoom;
		this.ifUseTicket = ticket;
	
		switch(this.texasRoom.getTexasRoomEnum()){
		case NORMAL:
			joinNormalRoom();
			break;
		case SNG:
			joinSNGRoom();
			break;
		case VIP:
			joinVipRoom();
			break;
		default:
			Assert.isTrue(false);
			break;
		}
       //加入房间
	   this.owner.getHumanAchievementManager().updateTexasCumulativeNum();
	}
	
	/**
	 * 加入普通放假
	 */
	private void joinNormalRoom(){
		long needCoins = this.owner.getGold();

		if (needCoins < texasRoom.getMinCarry()) {
			logger.warn("玩家[" + this.owner.getPassportId() + "] 进入 房间： ["
					+ texasRoom.getId() + "] 筹码不足");
			return;
		}

		if (needCoins > texasRoom.getMaxCarry()/2) {
			needCoins = texasRoom.getMaxCarry()/2;
		}
		this.coins = needCoins;
		String detailReason = MessageFormat.format(
				GoldLogReason.ENTER_TEXAS_ROOM.getReasonText(),
				texasRoom.getRoomType(), needCoins);
		this.owner.costMoney(needCoins, Currency.GOLD, true,
				GoldLogReason.ENTER_TEXAS_ROOM, detailReason, -1, 1);

	}
	
	/**
	 * 加入vip房间
	 */
	private void joinVipRoom(){
		long needCoins = this.owner.getGold();

		if (needCoins < texasRoom.getMinCarry()) {
			logger.warn("玩家[" + this.owner.getPassportId() + "] 进入 房间： ["
					+ texasRoom.getId() + "] 筹码不足");
			return;
		}

		if (needCoins > texasRoom.getMaxCarry()/2) {
			needCoins = texasRoom.getMaxCarry()/2;
		}
		this.coins = needCoins;
		String detailReason = MessageFormat.format(
				GoldLogReason.ENTER_TEXAS_ROOM.getReasonText(),
				texasRoom.getRoomType(), needCoins);
		this.owner.costMoney(needCoins, Currency.GOLD, true,
				GoldLogReason.ENTER_TEXAS_ROOM, detailReason, -1, 1);

	}
	
	/**
	 * 加入sng房间
	 */
	private void joinSNGRoom(){
		SngMatchTemplate template = Globals.getTemplateService().get(texasRoom.getId(), SngMatchTemplate.class);
		this.coins = template.getInitialChips();
	}

	/**
	 * 加入房间
	 * 
	 * @return
	 */
	public void waitingRoom(TexasRoom texasRoom) {
		this.texasRoom = texasRoom;
		playerState = RoomPlayerState.WAITING;
	}

	/**
	 * 退出房间
	 * 
	 * @return
	 */
	public void leaveRoom() {
	
	}
	
	/**
	 * 结算房间
	 */
	public void settleRoom(){
		
		if(texasRoom.isSNG()){
			//判断是否开始
			if(texasRoom.getCurrentGames()==0){
				//退回门票 或 金钱
				refundSNGFee();
			}
			else
			{
				//结算
				onSngEnd();
			}
			owner.sendMessage(buildHumanTexasSNGInfoData());
		}
		else{
			if (this.getCoins() != 0) {
				String detailReason = MessageFormat.format(
						LogReasons.GoldLogReason.EXIT_TEXAS_ROOM.getReasonText(),
						texasRoom.getId(), this.getCoins());
				this.owner.giveMoney(this.getCoins(), Currency.GOLD, true,
						LogReasons.GoldLogReason.EXIT_TEXAS_ROOM, detailReason, -1,
						1);
				this.coins = 0;
			}

			owner.sendMessage(buildHumanTexasInfoData());
		}
		
		playerState = RoomPlayerState.WAITING;
		if (isGaming() && allBet > 0) {
			//owner.addExp(texasRoom.getJoinExp(), null);
			//addHumanExp();
		}

		allBet = 0;
		currentBet = 0;
		winBet = 0;
		pos = -1;
		cardList.clear();
		wholeCardList.clear();
		texasHandCard = null;
		
		invalidBet = 0;
		betCumulative = 0;
		
	}
	
	/**
	 * 回退门票
	 */
	private void refundSNGFee(){
		
		SngMatchTemplate sngMatchTemplate = Globals.getTemplateService().get(texasRoom.getId(), SngMatchTemplate.class);
		HumanBagManager humanBagManager = owner.getHumanBagManager();
		if(getIfUseTicket()){
			humanBagManager.addItem(sngMatchTemplate.getTicketId(), 1);
			Globals.getLogService().sendItemLog(owner, LogReasons.ItemLogReason.SNG_TICKET_REFUND, LogReasons.ItemLogReason.SNG_TICKET_REFUND.getReasonText(), sngMatchTemplate.getTicketId(), 1, humanBagManager.getCount(sngMatchTemplate.getTicketId()));

		}
		else{
			int refundFee = sngMatchTemplate.getServiceFee()+sngMatchTemplate.getEntryFee();
			String detailReason = MessageFormat.format(LogReasons.GoldLogReason.SNG_FEE_REFUND.getReasonText(),sngMatchTemplate.getId(),refundFee);
			owner.giveMoney( refundFee, Currency.GOLD, true, LogReasons.GoldLogReason.SNG_FEE_REFUND, detailReason, -1, 1);
		}
	}
	
	
	//退出房间
	public void exitRoom() {
		texasRoom = null;
	}

	/**
	 * 改变自动设定
	 */
	public void auto(int isAuto) {
		Assert.isTrue(isAuto == 1 || isAuto == 0, "auto只能是0或1");
		this.humanTexas.setIsAuto(isAuto);
		this.humanTexas.setModified();
	}

	/**
	 * 改变人数设定
	 */
	public void people(int people) {
		Assert.isTrue(people == TexasRoom.TABLE_PLAYER_MIN
				|| people == TexasRoom.TABLE_PLAYER_MAX, "people只能是5或9");
		this.humanTexas.setPeople(people);
		this.humanTexas.setModified();
	}

	/**
	 * 是否应该坐在座位上
	 */
	public boolean isStillInSeat() {
		Assert.isTrue(isInTable(), "not in table");
		if(this.texasRoom.isSNG()){
			if(this.coins>0)
				return true;
			else
				return false;
		}
			
		if (this.coins < texasRoom.getSmallBlind() * 2) {
			return false;
		}
		return true;
	}

	/**
	 * 是否筹码足够
	 * 
	 * @return
	 */
	public long complementGold() {
		Assert.isTrue(isInTable(), "not in table");
		
		if(texasRoom.isSNG()){
			return 0;
		}

		// 当前最大筹码
		long currentAll = this.coins + this.owner.getGold();
		if (currentAll + this.coins < texasRoom.getMinCarry()) {
			return 0;
		}

		long complement = 0L;

		if (nextComplement != 0) {
			// 超过最大补充量
			complement = nextComplement;
		}
		// 补充一半
		else if (humanTexas.getIsAuto() == 0) {
			complement = texasRoom.getMaxCarry() / 2;
		}
		// 补充到最大量
		else {
			complement = texasRoom.getMaxCarry();
		}

		complement = complement - this.coins;
		if (complement < 0)
			complement = 0L;

		// 判断是否超过自身筹码量
		if (complement >= this.owner.getGold()) {
			complement = this.owner.getGold();
		}

		if (complement <= 0)
			return 0;

		String detailReason = MessageFormat.format(
				LogReasons.GoldLogReason.TEXAS_ROOM_COMPLEMENT.getReasonText(),
				this.getTexasRoom().getId(), complement);
		this.owner.costMoney(complement, Currency.GOLD, true,
				LogReasons.GoldLogReason.TEXAS_ROOM_COMPLEMENT, detailReason,
				-1, 1);

		this.coins += complement;
		return complement;
	}
	
	/**
	 * sng 开始
	 */
	public void onSngStart(){
		this.humanTexasSNG.setJoinTimes(this.humanTexasSNG.getJoinTimes()+1);
		this.humanTexasSNG.setModified();
	}
	
	/**
	 * sng名次
	 */
	public void onSngEnd(){
		
		logger.info("玩家["+owner.getPassportId()+"],房间号["+texasRoom.getRid()+"],"+"sngId["+texasRoom.getId()+"],名次["+sngRank+"]");
		
		
		GCSngRank gcSngRank = new GCSngRank();
		gcSngRank.setSngRank(this.sngRank);
		gcSngRank.setPos(this.pos);
		Globals.getTexasService().broadcastMsg(texasRoom, gcSngRank);
		
		SngMatchTemplate sngMatchTemplate = Globals.getTemplateService().get(texasRoom.getId(), SngMatchTemplate.class);
		if(this.sngRank==3){
			this.humanTexasSNG.setCopperNum(this.humanTexasSNG.getCopperNum()+1);
			this.humanTexasSNG.setModified();
		}
		if(this.sngRank==2){
			this.humanTexasSNG.setSilverNum(this.humanTexasSNG.getSilverNum()+1);
			this.humanTexasSNG.setWeekScore(this.humanTexasSNG.getWeekScore()+sngMatchTemplate.getScoreReward2());
			//给金钱奖励
			this.owner.giveMoney(texasRoom.sngRewardForRank(this.sngRank), Currency.GOLD, true, LogReasons.GoldLogReason.SNG_SILVER_REWARD, LogReasons.GoldLogReason.SNG_SILVER_REWARD.getReasonText(), -1, 1);
			
			this.humanTexasSNG.setModified();
		}
		if(this.sngRank==1){
			this.humanTexasSNG.setGoldNum(this.humanTexasSNG.getGoldNum()+1);
			this.humanTexasSNG.setWeekScore(this.humanTexasSNG.getWeekScore()+sngMatchTemplate.getScoreReward1());
			//给金钱奖励
			this.owner.giveMoney(texasRoom.sngRewardForRank(this.sngRank), Currency.GOLD, false, LogReasons.GoldLogReason.SNG_GOLD_REWARD, LogReasons.GoldLogReason.SNG_GOLD_REWARD.getReasonText(), -1, 1);
			
			this.humanTexasSNG.setModified();
		}
		
		
	}
	
	/**
	 * 玩家信息
	 * 
	 * @return
	 */
	public TexasRoomPlayerInfoData buildRoomPlayerData() {
		TexasRoomPlayerInfoData texasRoomPlayerInfoData = new TexasRoomPlayerInfoData();
		texasRoomPlayerInfoData.setCoins(getCoins());
		texasRoomPlayerInfoData.setPlayerId(owner.getPlayer().getPassportId());
		texasRoomPlayerInfoData.setPos(getPos());
		texasRoomPlayerInfoData.setImg(owner.getImg());
		texasRoomPlayerInfoData.setName(owner.getPlayer().getPassportName());
		texasRoomPlayerInfoData.setPlayerState(playerState.getIndex());
		texasRoomPlayerInfoData.setCurrentBet(getCurrentBet());
		texasRoomPlayerInfoData.setAllBet(getAllBet());
		texasRoomPlayerInfoData.setVip(owner.getHumanVipNewManager().getVipLv());
		return texasRoomPlayerInfoData;
	}

	/**
	 * 德州数据
	 * 
	 * @return
	 */
	public GCHumanTexasInfoData buildHumanTexasInfoData() {
		GCHumanTexasInfoData gcHumanTexasInfoData = new GCHumanTexasInfoData();
		gcHumanTexasInfoData.setTotalCount(humanTexas.getCount());
		gcHumanTexasInfoData.setDayBiggestWinCoins(humanTexas
				.getDayBiggestWinCoins());
		gcHumanTexasInfoData.setWeekWinCoins(humanTexas.getWeekWinCoins());
		gcHumanTexasInfoData.setWinCount(humanTexas.getWinCount());
		gcHumanTexasInfoData.setIsAuto(humanTexas.getIsAuto());
		gcHumanTexasInfoData.setPeople(humanTexas.getPeople());
		int[] biggestCardArr = new int[humanTexas.getBiggestHandCardList()
				.size()];
		for (int i = 0; i < humanTexas.getBiggestHandCardList().size(); i++) {
			biggestCardArr[i] = humanTexas.getBiggestHandCardList().get(i)
					.getValue();
		}

		gcHumanTexasInfoData.setBiggestHandCardList(biggestCardArr);

		return gcHumanTexasInfoData;
	}

	/**
	 * 德州sng数据
	 * 
	 * @return
	 */
	public GCHumanTexasSngInfoData buildHumanTexasSNGInfoData() {
		GCHumanTexasSngInfoData gcHumanTexasSngInfoData = new GCHumanTexasSngInfoData();
		gcHumanTexasSngInfoData.setGoldNum(humanTexasSNG.getGoldNum());
		gcHumanTexasSngInfoData.setCopperNum(humanTexasSNG.getCopperNum());
		gcHumanTexasSngInfoData.setSilverNum(humanTexasSNG.getSilverNum());
		gcHumanTexasSngInfoData.setJoinTimes(humanTexasSNG.getJoinTimes());
		gcHumanTexasSngInfoData.setWeekScore(humanTexasSNG.getWeekScore());
		gcHumanTexasSngInfoData.setRank(RankLogic.getInstance().querySelfRank(owner.getPassportId(),RankTypeEnum.TEXAS_SNG_WEEK));
		return gcHumanTexasSngInfoData;
	}

	
	public boolean isInRoom() {
		return texasRoom != null;
	}

	/**
	 * 判断是否在游戏中
	 * 
	 * @return
	 */
	public boolean isGaming() {
		return texasRoom != null && playerState == RoomPlayerState.GAMING;
	}

	/**
	 * 判断是否在桌上
	 * 
	 * @return
	 */
	public boolean isInTable() {
		return texasRoom != null && pos != -1;
	}
	
	

	/**
	 * 判断是否围观
	 * 
	 * @return
	 */
	public boolean isLooking() {
		return texasRoom != null && playerState == RoomPlayerState.WAITING;
	}
	
	/**
	 * 日志
	 */
	
	public String buildGameLog(){
		StringBuilder sb = new StringBuilder();
		sb.append("玩家id["+owner.getPassportId()+"]\n");
		sb.append("位置["+this.getPos()+"]\n");
		sb.append("总下注["+this.getAllBet()+"]\n");
		sb.append("总筹码["+this.getCoins()+"]\n");
		sb.append("手上牌["+this.getCardList()+"]\n");
		return sb.toString();
	}
}
