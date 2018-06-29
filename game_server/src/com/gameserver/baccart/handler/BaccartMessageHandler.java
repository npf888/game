package com.gameserver.baccart.handler;

import java.text.MessageFormat;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.util.Assert;
import com.core.util.TimeUtils;
import com.db.model.HumanBaccartEntity;
import com.gameserver.baccart.BaccartRoom;
import com.gameserver.baccart.data.BaccartBetData;
import com.gameserver.baccart.data.HumanBaccartData;
import com.gameserver.baccart.enums.BaccartBetType;
import com.gameserver.baccart.msg.CGBaccaratQuickStart;
import com.gameserver.baccart.msg.CGBaccartAuto;
import com.gameserver.baccart.msg.CGBaccartBet;
import com.gameserver.baccart.msg.CGBaccartComplement;
import com.gameserver.baccart.msg.CGBaccartExit;
import com.gameserver.baccart.msg.CGBaccartJoin;
import com.gameserver.baccart.msg.CGBaccartList;
import com.gameserver.baccart.msg.CGBaccartRevive;
import com.gameserver.baccart.msg.CGBaccartSeat;
import com.gameserver.baccart.msg.CGBaccartStand;
import com.gameserver.baccart.msg.CGHumanBaccart;
import com.gameserver.baccart.msg.GCBaccartAuto;
import com.gameserver.baccart.msg.GCBaccartComplement;
import com.gameserver.baccart.msg.GCBaccartList;
import com.gameserver.baccart.msg.GCBaccartRevive;
import com.gameserver.baccart.msg.GCHumanBaccart;
import com.gameserver.baccart.template.JackpotTemplate;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanBaccartManager;
import com.gameserver.player.Player;
import com.gameserver.vipnew.template.VipNewTemplate;


/**
 * 百家乐
 * @author wayne
 *
 */
public class BaccartMessageHandler {

	private Logger logger = Loggers.baccartRoomLogger;
	
	/**
	 * 百家乐房间列表
	 * @param player
	 * @param cgBaccartList
	 */
	public void handleBaccartList(Player player, CGBaccartList cgBaccartList) {
		// TODO Auto-generated method stub
		logger.debug("玩家["+player.getPassportId()+"] 请求百家乐列表");
		GCBaccartList gcBaccartList= Globals.getBaccartService().buildGCBaccartList();
		player.sendMessage(gcBaccartList);
	}
	
	/**
	 * 请求玩家下注
	 * @param player
	 * @param cgBaccartBet
	 */
	public void handleBaccartBet(Player player, CGBaccartBet cgBaccartBet) {
		// TODO Auto-generated method stub
		logger.debug("玩家["+player.getPassportId()+"] 押注");
		
		HumanBaccartManager humanBaccartManager = player.getHuman().getHumanBaccartManager();
		BaccartRoom room = humanBaccartManager.getBaccartRoom();
		
		if(room == null){
			logger.debug("玩家["+player.getPassportId()+"] 不在房间内");
			return;
		}
		
		if(!room.ifCanBet()){
			logger.debug("玩家["+player.getPassportId()+"] 在房间["+room.getId()+"],下注失败");
			return;
		}
		
		//判断筹码数
		long totalNum = 0;
		for(BaccartBetData tempBaccartBetData:cgBaccartBet.getBetDataList()){
			BaccartBetType tempBaccartBetType = BaccartBetType.valueOf(tempBaccartBetData.getBetType());
			if(tempBaccartBetType == null){
				logger.warn("玩家["+player.getPassportId()+"],房间["+room.getId()+"],下注类型错误["+tempBaccartBetData.getBetType()+"]");
				return;
			}
			if(tempBaccartBetData.getBetNum() <= 0){
				logger.warn("玩家["+player.getPassportId()+"],房间["+room.getId()+"],下注小于等于0");
				return;
			}
			totalNum+=tempBaccartBetData.getBetNum();
		}
		
		if(totalNum<=0){
			logger.warn("玩家["+player.getPassportId()+"],房间["+room.getId()+"],下注小于等于0");
			return;
		}
		
		//是否有足够的钱
		if(totalNum>humanBaccartManager.getCoins()){
			logger.warn("玩家["+player.getPassportId()+"]下注["+totalNum+"],但是自身只有["+humanBaccartManager.getCoins()+"]");
			return;
		}
		
		//判断是否可以下注
		if(!humanBaccartManager.ifBet(cgBaccartBet.getBetDataList())){
			logger.warn("玩家["+player.getPassportId()+"]下注["+totalNum+"],但是自身只有["+humanBaccartManager.getCoins()+"]");
			return;
		}
		
		room.playerBet(player, cgBaccartBet.getBetDataList());
		
	}
	
	/**
	 * 加入房间
	 * @param player
	 * @param cgBaccartJoin
	 */
	public void handleBaccartJoin(Player player, CGBaccartJoin cgBaccartJoin) {
		// TODO Auto-generated method stub
	
		HumanBaccartManager humanBaccartManager = player.getHuman().getHumanBaccartManager();
		BaccartRoom room = humanBaccartManager.getBaccartRoom();
		if(room !=null){
			logger.warn("玩家["+player.getPassportId()+"] 还没退出房间["+room.getId()+"]");
			room.playerLeave(player);
		}
		
		room = Globals.getBaccartService().baccartRoomById(cgBaccartJoin.getRoomId());
		if(room == null){
			logger.warn("玩家["+player.getPassportId()+"] 进入不存在的房间["+cgBaccartJoin.getRoomId()+"]");
			return;
		}
		
		if(room.ifClose()){
			logger.debug("玩家["+player.getPassportId()+"] 进入房间["+cgBaccartJoin.getRoomId()+"]失败,已关闭");
			return;
		}
		
		//判断是否房间已满
		if(room.ifFull()){
			logger.debug("玩家["+player.getPassportId()+"] 进入房间["+cgBaccartJoin.getRoomId()+"]失败,已满");
			return;
		}
		
		//判断是否有足够钱
		if(player.getHuman().getGold()<room.getMinCarry()){
			logger.warn("玩家["+player.getPassportId()+"] 进入房间["+cgBaccartJoin.getRoomId()+"]失败,不够钱");
			return;
		}
		
		//等级判断
		if(player.getHuman().getLevel() < room.getOpenLv()){
			logger.warn("玩家["+player.getPassportId()+"] 等级不足");
			return;
		}
		
		logger.debug("玩家["+player.getPassportId()+"] 加入房间["+cgBaccartJoin.getRoomId()+"]");
		
		room.playerJoin(player);
		
	}

	/**
	 * 退出房间
	 * @param player
	 * @param cgBaccartExit
	 */
	public void handleBaccartExit(Player player, CGBaccartExit cgBaccartExit) {
		// TODO Auto-generated method stub
		HumanBaccartManager humanBaccartManager = player.getHuman().getHumanBaccartManager();
		BaccartRoom room = humanBaccartManager.getBaccartRoom();
		
		if(room == null){
			logger.warn("玩家["+player.getPassportId()+"] 不在房间内]");
			return;
		}
		
		logger.debug("玩家["+player.getPassportId()+"] 离开房间["+room.getId()+"]");
		room.playerLeave(player);
	}

	/**
	 * 坐下
	 * @param player
	 * @param cgBaccartSeat
	 */
	public void handleBaccartSeat(Player player, CGBaccartSeat cgBaccartSeat) {
		// TODO Auto-generated method stub
		HumanBaccartManager humanBaccartManager = player.getHuman().getHumanBaccartManager();
		BaccartRoom room = humanBaccartManager.getBaccartRoom();
		
		if(room == null){
			logger.warn("玩家["+player.getPassportId()+"] 不在房间内]");
			return;
		}
		
		int pos = cgBaccartSeat.getPos();
		if(pos<0 || pos>=room.getSeatNum()){
			logger.warn("玩家["+player.getPassportId()+"] 坐下房间["+room.getId()+"],位置["+pos+"]失败");
			return;
		}
		
		if(!room.ifHasSeat(pos)){
			logger.warn("玩家["+player.getPassportId()+"] 坐下房间["+room.getId()+"],位置["+pos+"]失败");
			return;
		}
		
		logger.debug("玩家["+player.getPassportId()+"] 坐下房间["+room.getId()+"],位置["+pos+"]");
		room.playerSeat(player, pos);
	}

	/**
	 * 站起
	 * @param player
	 * @param cgBaccartStand
	 */
	public void handleBaccartStand(Player player, CGBaccartStand cgBaccartStand) {
		// TODO Auto-generated method stub
		HumanBaccartManager humanBaccartManager = player.getHuman().getHumanBaccartManager();
		BaccartRoom room = humanBaccartManager.getBaccartRoom();
		
		if(room == null){
			logger.warn("玩家["+player.getPassportId()+"] 不在房间内]");
			return;
		}
		
		
		logger.debug("玩家["+player.getPassportId()+"] 站起["+room.getId()+"]");
		room.playerStand(player);
	}

	/**
	 * 百家乐信息
	 * @param player
	 * @param cgHumanBaccart
	 */
	public void handleHumanBaccart(Player player, CGHumanBaccart cgHumanBaccart) {
		// TODO Auto-generated method stub
		
		long tempPlayerId = cgHumanBaccart.getPlayerId();
		Player tempPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(tempPlayerId);
		

		if(tempPlayer != null){
			HumanBaccartData tempHumanBaccartData = HumanBaccartData.convertFromPlayer(tempPlayer);
			GCHumanBaccart gcHumanBaccart = new GCHumanBaccart();
			gcHumanBaccart.setHumanBaccartData(tempHumanBaccartData);
			player.sendMessage(gcHumanBaccart);
		}else{
			HumanBaccartEntity tempHumanBaccartEntity= Globals.getDaoService().getHumanBaccartDao().getHumanBaccartByCharId(tempPlayerId); 
			if(tempHumanBaccartEntity==null){
				logger.warn("玩家["+tempPlayerId+"] 不在游戏内]");
				return;
			}
			HumanBaccartData tempHumanBaccartData = HumanBaccartData.convertFromHumanBaccartEntity(tempHumanBaccartEntity);
			GCHumanBaccart gcHumanBaccart = new GCHumanBaccart();
			gcHumanBaccart.setHumanBaccartData(tempHumanBaccartData);
			player.sendMessage(gcHumanBaccart);
		}
			
	}
	
	/**
	 * 自动
	 * @param player
	 * @param cgBaccartAuto
	 */
	public void handleBaccartAuto(Player player, CGBaccartAuto cgBaccartAuto) {

		int tempAuto = cgBaccartAuto.getAuto();
		
		logger.debug("玩家[" + player.getPassportId() + "]请求自动补充["+tempAuto+"]");
		
		Human human = player.getHuman();
		if(human == null) return;
		HumanBaccartManager humanBaccartManager = human.getHumanBaccartManager();
		
		//判断是否是0或1
		if(cgBaccartAuto.getAuto()!=0 && cgBaccartAuto.getAuto()!=1)
		{
			logger.warn("玩家["+ player.getPassportId() + "]输入错误参数auto["+cgBaccartAuto.getAuto()+"]");
			return;
		}
		humanBaccartManager.getHumanBaccart().setIsAuto(tempAuto);
		humanBaccartManager.getHumanBaccart().setModified();
		
		GCBaccartAuto gcBaccartAuto = new GCBaccartAuto();
		gcBaccartAuto.setAuto(tempAuto);
		player.sendMessage(gcBaccartAuto);
	}

	/**
	 * 百家乐
	 * @param player
	 * @param cgBaccartComplement
	 */
	public void handleBaccartComplement(Player player,
			CGBaccartComplement cgBaccartComplement) {

		long tempComplement = cgBaccartComplement.getComplement();
		logger.debug("玩家[" + player.getPassportId() + "]请求补充筹码["+tempComplement+"]");
		
		Human human = player.getHuman();
		Assert.notNull(human);
	
		HumanBaccartManager humanBaccartManager = human.getHumanBaccartManager();
		BaccartRoom baccartRoom =humanBaccartManager.getBaccartRoom();

		if (baccartRoom == null) {
			logger.warn("玩家[" + player.getId() + "]" + "不在房间内");
			return ;
		}
		
		//判断补充数
		humanBaccartManager.setNextComplement(tempComplement);
		GCBaccartComplement gcBaccartComplement = new GCBaccartComplement();
		gcBaccartComplement.setComplement(tempComplement);
		human.sendMessage(gcBaccartComplement);
	}

	/**
	 * 复活
	 * @param player
	 * @param cgBaccartRevive
	 */
	public void handleBaccartRevive(Player player,
			CGBaccartRevive cgBaccartRevive) {
		// TODO Auto-generated method stub
		int tempComplement = cgBaccartRevive.getRevive();
		logger.debug("玩家[" + player.getPassportId() + "]请求复活["+tempComplement+"]");
		
		Human human = player.getHuman();
		Assert.notNull(human);
		
		int vipLevel = human.getHumanVipNewManager().getVipLv();
		
		VipNewTemplate vipNewTemplate = Globals.getVipNewServer().getVipNewTemplate(vipLevel);
		
		if(vipNewTemplate == null || vipNewTemplate.getBeaconRevive() != 1){
			logger.warn("玩家[" + player.getId() + "]" + "没有权限复活");
			return ;
		}
	
		HumanBaccartManager humanBaccartManager = human.getHumanBaccartManager();
		BaccartRoom baccartRoom =humanBaccartManager.getBaccartRoom();

		if (baccartRoom == null) {
			logger.warn("玩家[" + player.getId() + "]" + "不在房间内");
			return ;
		}
		int tempRevive = cgBaccartRevive.getRevive();
		if(tempRevive!=0 && tempRevive!=1){
			logger.warn("玩家[" + player.getId() + "]" + "请求复活["+tempRevive+"]参数错误");
			return;
		}
		
		if(tempRevive==0){
			logger.debug("玩家[" + player.getId() + "]" + "不复活");
			humanBaccartManager.revive(false);
			GCBaccartRevive gcBaccartRevive = new GCBaccartRevive();
			gcBaccartRevive.setRevive(cgBaccartRevive.getRevive());
			player.sendMessage(gcBaccartRevive);
			return;
		}
		
		int cost = reviveCost(humanBaccartManager);
		if(cost==-1){
			logger.warn("玩家[" + player.getId() + "]" + "不可以复活");
			GCBaccartRevive gcBaccartRevive = new GCBaccartRevive();
			gcBaccartRevive.setRevive(0);
			player.sendMessage(gcBaccartRevive);
			return;
		}
		
		if(!human.hasEnoughMoney(cost, Currency.GOLD)){
			logger.warn("玩家[" + player.getId() + "]" + "不可以复活没有足够的钱");
			GCBaccartRevive gcBaccartRevive = new GCBaccartRevive();
			gcBaccartRevive.setRevive(0);
			player.sendMessage(gcBaccartRevive);
			return;
		}
		
	/*	String detailReason = MessageFormat.format(LogReasons.DiamondLogReason.BACCART_REVIVE.getReasonText(), baccartRoom.getId(),humanBaccartManager.getPreviousContinueWins(),humanBaccartManager.getMinWins());
		human.costMoney(cost, Currency.GOLD, true, LogReasons.DiamondLogReason.BACCART_REVIVE, detailReason, -1, 1);*/
		String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BACCART_REVIVE.getReasonText(), baccartRoom.getId(),humanBaccartManager.getPreviousContinueWins(),humanBaccartManager.getMinWins());
		human.costMoney(cost, Currency.GOLD, true, LogReasons.GoldLogReason.BACCART_REVIVE, detailReason, -1, 1);
		humanBaccartManager.revive(true);
		
		//发送复活信息
		GCBaccartRevive gcBaccartRevive = new GCBaccartRevive();
		gcBaccartRevive.setRevive(cgBaccartRevive.getRevive());
		player.sendMessage(gcBaccartRevive);
		human.sendMessage(gcBaccartRevive);
		
		Globals.getBaccartService().onBaccartRoomBeaconChanged(baccartRoom);
	}
	
	/**
	 * 复活花费
	 * @param humanBaccartManager
	 * @return
	 */
	private int reviveCost(HumanBaccartManager humanBaccartManager){
		if(humanBaccartManager.getContinueWins()!=0)
			return -1;
		if(humanBaccartManager.getPreviousContinueWins()<HumanBaccartManager.BEACON_CONTINUE_WIN_NUM){
			return -1;
		}
		
		int tempPrevious = humanBaccartManager.getPreviousContinueWins();
		JackpotTemplate tempJackpotTemplate = Globals.getBaccartService().jackpotTemplateByWinNums(humanBaccartManager.getMinWins());
		if(tempJackpotTemplate==null)
			return -1;
		if(tempPrevious>tempJackpotTemplate.getReviveCostList().size()|| tempPrevious <=0){
			return -1;
		}
		
		//判断时间
		long now = Globals.getTimeService().now();
		
		if(now -humanBaccartManager.getLastWinTime() >HumanBaccartManager.MAX_BEACON_WAIT_TIME*TimeUtils.MIN){
			return -1;
		}
		
		return tempJackpotTemplate.getReviveCostList().get(tempPrevious-1);
	}
	
	/**
	 * 百家乐快速开始
	 * @param player
	 * @param cgBaccaratQuickStart
	 */
	public void handleBaccaratQuickStart(Player player,
			CGBaccaratQuickStart cgBaccaratQuickStart) {
		// TODO Auto-generated method stub

		HumanBaccartManager humanBaccartManager = player.getHuman().getHumanBaccartManager();
		BaccartRoom room = humanBaccartManager.getBaccartRoom();
		if(room !=null){
			logger.warn("玩家["+player.getPassportId()+"] 还没退出房间["+room.getId()+"]");
			room.playerLeave(player);
		}
		
		room = Globals.getBaccartService().baccartRoomForQuickStartByPlayer(player);
		if(room == null){
			logger.warn("玩家["+player.getPassportId()+"] 进入房间失败]");
			return;
		}
		
		logger.debug("玩家["+player.getPassportId()+"] 加入房间["+room.getId()+"]");

		room.playerJoin(player);
	}

}
