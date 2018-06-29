package com.gameserver.texas.handler;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.util.Assert;
import com.db.model.HumanTexasEntity;
import com.db.model.HumanTexasSNGEntity;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanBagManager;
import com.gameserver.human.manager.HumanTexasManager;
import com.gameserver.player.Player;
import com.gameserver.texas.TexasRoom;
import com.gameserver.texas.data.HumanTexasInfoData;
import com.gameserver.texas.data.TexasRoomTypeInfoData;
import com.gameserver.texas.data.sng.HumanTexasSNGInfoData;
import com.gameserver.texas.data.sng.TexasSngInfoData;
import com.gameserver.texas.enums.JoinVipErrorEnum;
import com.gameserver.texas.msg.CGHumanTexasInfoDataSearch;
import com.gameserver.texas.msg.CGJoinTexas;
import com.gameserver.texas.msg.CGJoinTexasRoomId;
import com.gameserver.texas.msg.CGJoinTexasSng;
import com.gameserver.texas.msg.CGJoinTexasVip;
import com.gameserver.texas.msg.CGLeaveTexas;
import com.gameserver.texas.msg.CGTexasAddBet;
import com.gameserver.texas.msg.CGTexasAllIn;
import com.gameserver.texas.msg.CGTexasAuto;
import com.gameserver.texas.msg.CGTexasComplement;
import com.gameserver.texas.msg.CGTexasExit;
import com.gameserver.texas.msg.CGTexasFollow;
import com.gameserver.texas.msg.CGTexasGiveUp;
import com.gameserver.texas.msg.CGTexasList;
import com.gameserver.texas.msg.CGTexasLook;
import com.gameserver.texas.msg.CGTexasPeopleSetting;
import com.gameserver.texas.msg.CGTexasQuickStart;
import com.gameserver.texas.msg.CGTexasSeat;
import com.gameserver.texas.msg.CGTexasSngList;
import com.gameserver.texas.msg.CGTexasTips;
import com.gameserver.texas.msg.CGTexasVipList;
import com.gameserver.texas.msg.GCHumanTexasInfoDataSearch;
import com.gameserver.texas.msg.GCJoinTexasVipFailed;
import com.gameserver.texas.msg.GCTexasAuto;
import com.gameserver.texas.msg.GCTexasComplement;
import com.gameserver.texas.msg.GCTexasList;
import com.gameserver.texas.msg.GCTexasPeopleSetting;
import com.gameserver.texas.msg.GCTexasSngList;
import com.gameserver.texas.msg.GCTexasTips;
import com.gameserver.texas.template.SngMatchTemplate;
import com.gameserver.texas.template.TexasRoomTemplate;

/**
 * 德州扑克处理器
 * 
 * @author wayne
 *
 */
public class TexasMessageHandler extends TexasHandlerFactory {


	/** 日志 */
	private static final Logger logger = Loggers.texasRoomLogger;
//	/**sng 日志*/
//	private static final Logger sngLogger = Loggers.sngLogger;
//	/**vip日志*/
//	private static final Logger vipRoomLogger = Loggers.vipRoomLogger;
//	
	public TexasMessageHandler() {
	
	}

	/**
	 * 加入德州房间
	 * 
	 * @param player
	 * @param cgJoinTexas
	 */
	public void handleJoinTexas(Player player, CGJoinTexas cgJoinTexas) {
		
		
		int roomType = cgJoinTexas.getRoomId();
		logger.debug("玩家[" + player.getPassportId() + "]请求加入普通房间["+roomType+"]");
		
		TexasRoomTemplate texasRoomTemplate = Globals.getTemplateService().get(
				roomType, TexasRoomTemplate.class);

		if (texasRoomTemplate == null) {
			logger.warn("房间类型[" + roomType + "]不存在，玩家["
					+ player.getPassportId() + "]加入失败");
			return;
		}
		
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		
		TexasRoom texasRoom = humanTexasManager.getTexasRoom();
		//判断是否已经在房间内 退出房间
		if(texasRoom!=null)
		{
			logger.warn("玩家[" + player.getPassportId() + "]正在玩,退出房间,房间类型["+texasRoom.getId()+"]");
			Globals.getTexasService().exitRoom(player);
		}

		// 判断玩家是否有资格进入这类型房间
		if (player.getHuman().getGold() < texasRoomTemplate.getMinCarry()) {
			logger.warn("玩家筹码小于大盲注，玩家[" + player.getPassportId() + "]加入失败");
			// 加入失败
			player.getHuman().sendErrorMessage(
					LangConstants.TEXAS_LESS_MIN_CARRY);
			return;
		}
		
		//等级判断
		if(player.getHuman().getLevel() < texasRoomTemplate.getOpenLv()){
			logger.warn("玩家等级不够，玩家[" + player.getPassportId() + "]加入失败");
			// 加入失败
			player.getHuman().sendErrorMessage(LangConstants.ROLE_LEVEL_NO);
			return;
		}
		

		// 判断是否有房间可以进入
		TexasRoom room = Globals.getTexasService().roomForJoin(player, roomType);
		if (room == null) {
			logger.warn("德州房间已满，玩家[" + player.getPassportId() + "]加入失败");
			// 加入失败
			player.getHuman().sendErrorMessage(LangConstants.TEXAS_ROOM_FULL);
			return;
		}
	
		// 加入房间
		Globals.getTexasService().joinRoom(player,room);
	}
	
	/**
	 * 快速开始
	 * @param player
	 * @param cgTexasQuickStart
	 */
	public void handleTexasQuickStart(Player player,
			CGTexasQuickStart cgTexasQuickStart) {
		// TODO Auto-generated method stub
		logger.debug("玩家[" + player.getPassportId() + "]请求快速加入普通房间]");
		
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		
		TexasRoom texasRoom = humanTexasManager.getTexasRoom();
		//判断是否已经在房间内 离开房间
		if(texasRoom!=null)
		{
			Globals.getTexasService().exitRoom(player);
		}
		
		
        
		// 判断是否有房间可以进入
		TexasRoom room = Globals.getTexasService().roomForQuickStart(player);
		if (room == null) {
			logger.warn("德州房间已满，玩家[" + player.getPassportId() + "]加入失败");
			// 加入失败
			player.getHuman().sendErrorMessage(LangConstants.TEXAS_ROOM_FULL);
			return;
		}

			// 加入房间
		Globals.getTexasService().joinRoom(player,room);
	}
	



	/**
	 * 获得德州列表
	 * 
	 * @param player
	 * @param cgTexasList
	 */
	public void handleTexasList(Player player, CGTexasList cgTexasList) {

		logger.debug("玩家[" + player.getPassportId() + "]请求普通德州房间列表");
		GCTexasList gcTexasList = new GCTexasList();
		List<TexasRoomTypeInfoData> texasRoomTypeInfoDataList = Globals.getTexasService()
				.getTexasRoomTypeInfoDataList();
		
		TexasRoomTypeInfoData[] texasRoomTypeInfoDataArr = texasRoomTypeInfoDataList.toArray(new TexasRoomTypeInfoData[texasRoomTypeInfoDataList.size()]);
		Map<Integer,Long> texasRoomNumMap= Globals.getTexasService().getTexasRoomMapByIds();
		for(TexasRoomTypeInfoData texasRoomTypeInfoData :texasRoomTypeInfoDataArr){
			Long num = texasRoomNumMap.get(texasRoomTypeInfoData.getTypeId());
			TexasRoomTemplate tempTexasRoomTemplate = Globals.getTemplateService().get(texasRoomTypeInfoData.getTypeId(), TexasRoomTemplate.class);
			int virtualNum = tempTexasRoomTemplate.getDisplayNum();
			if(num == null){
				
				texasRoomTypeInfoData.setTotalNum(virtualNum);
			}
			else
			{
				if(num >virtualNum)
					texasRoomTypeInfoData.setTotalNum((int)(long)num);
				else{
					texasRoomTypeInfoData.setTotalNum((int)(long)num+virtualNum);
				}
			}
			texasRoomTypeInfoData.setHandsel(Globals.getTexasService().getTextas(tempTexasRoomTemplate.getId()).getJackpot());
		}
		gcTexasList.setRoomTypeList(texasRoomTypeInfoDataArr);
		
		player.sendMessage(gcTexasList);
		
	}

	/**
	 * 看牌
	 * 
	 * @param player
	 * @param cgTexasLook
	 */
	public void handleTexasLook(Player player, CGTexasLook cgTexasLook) {
		logger.debug("玩家[" + player.getPassportId() + "]请求 check");
		TexasRoom texasRoom = player.getHuman().getHumanTexasManager()
				.getTexasRoom();

		if (!validatePlayer(player))
		{
//			if(texasRoom.isSNG()){
//				sngLogger.warn("玩家["+ player.getPassportId() + "]还没有轮到");
//				return;
//			}
			
			logger.warn("玩家["+ player.getPassportId() + "]还没有轮到");
			return;
		}
		texasRoom.playerCheck(player);
	
	}

	/**
	 * 跟注
	 * 
	 * @param player
	 * @param cgTexasFollow
	 */
	public void handleTexasFollow(Player player, CGTexasFollow cgTexasFollow) {
		// TODO Auto-generated method stub
		logger.debug("玩家[" + player.getPassportId() + "]请求 call");
		TexasRoom texasRoom = player.getHuman().getHumanTexasManager()
				.getTexasRoom();

		if (!validatePlayer(player))
		{
//			if(texasRoom.isSNG()){
//				sngLogger.warn("玩家["+ player.getPassportId() + "]还没有轮到");
//				return;
//			}
			
			logger.warn("玩家["+ player.getPassportId() + "]还没有轮到");
			return;
		}
		texasRoom.playerCall(player);
	
	}

	/**
	 * 加注
	 * 
	 * @param player
	 * @param cgTexasAddBet
	 */
	public void handleTexasAddBet(Player player, CGTexasAddBet cgTexasAddBet) {
		// TODO Auto-generated method stub
		long addBet = cgTexasAddBet.getAddBet();
		
		logger.debug("玩家[" + player.getPassportId() + "]请求 raise["+addBet+"]");
		TexasRoom texasRoom = player.getHuman().getHumanTexasManager()
				.getTexasRoom();

		if (!validatePlayer(player))
		{
//			if(texasRoom.isSNG()){
//				sngLogger.warn("玩家["+ player.getPassportId() + "]还没有轮到");
//				return;
//			}
			
			logger.warn("玩家["+ player.getPassportId() + "]还没有轮到");
			return;
		}
	
		texasRoom.playerRaise(player, addBet);
		
	}

	/**
	 * allin
	 * 
	 * @param player
	 * @param cgTexasAllIn
	 */
	public void handleTexasAllIn(Player player, CGTexasAllIn cgTexasAllIn) {
		// TODO Auto-generated method stub
		logger.debug("玩家[" + player.getPassportId() + "]请求all in");
		TexasRoom texasRoom = player.getHuman().getHumanTexasManager()
				.getTexasRoom();

		if (!validatePlayer(player))
		{

			logger.warn("玩家["+ player.getPassportId() + "]还没有轮到");
			return;
		}
		
		texasRoom.playerAllIn(player);
		
	}

	/**
	 * 弃牌
	 * 
	 * @param player
	 * @param cgTexasGiveUp
	 */
	public void handleTexasGiveUp(Player player, CGTexasGiveUp cgTexasGiveUp) {
		// TODO Auto-generated method stub
		logger.debug("玩家[" + player.getPassportId() + "]请求give up");
		TexasRoom texasRoom = player.getHuman().getHumanTexasManager()
				.getTexasRoom();

		if (!validatePlayer(player))
		{
	
			logger.warn("玩家["+ player.getPassportId() + "]还没有轮到");
			return;
		}
		
		texasRoom.playerGiveup(player);
		
	}

	
	/**
	 * 离开房间
	 * @param player
	 * @param cgLeaveTexas
	 */
	public void handleLeaveTexas(Player player, CGLeaveTexas cgLeaveTexas) {
		// TODO Auto-generated method stub
		
		logger.debug("玩家[" + player.getPassportId() + "]请求站起");
		// 离开房间
		Human human = player.getHuman();
		if(human == null) return;
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		TexasRoom texasRoom = humanTexasManager.getTexasRoom();
		
		if(texasRoom==null){
			logger.warn("玩家["+ player.getPassportId() + "]已经离开房间了");
			return;
		}
		
		if(texasRoom.isSNG()){
			logger.warn("玩家["+ player.getPassportId() + "不能站起，只能离开房间sng");
			return;
		}
		
		if(!humanTexasManager.isInTable()){
			logger.warn("玩家["+ player.getPassportId() + "]已经离开桌子了");
			return;
		}
		

		Globals.getTexasService().leaveRoom(player);
	
	}
	
	/**
	 * 退出
	 * @param player
	 * @param cgTexasExit
	 */
	public void handleTexasExit(Player player, CGTexasExit cgTexasExit) {
		
		logger.debug("玩家[" + player.getPassportId() + "]请求退出房间");
		// 离开房间
		Human human = player.getHuman();
		if(human == null) return;
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		//判断是否在房间
		if(!humanTexasManager.isInRoom()){
			logger.warn("玩家["+ player.getPassportId() + "]已经离开房间了");
			return;
		}
		
		Globals.getTexasService().exitRoom(player);
		
	}

	/**
	 * 人数设定
	 * @param player
	 * @param cgTexasPeopleSetting
	 */
	public void handleTexasPeopleSetting(Player player,
			CGTexasPeopleSetting cgTexasPeopleSetting) {
		int tempPeople = cgTexasPeopleSetting.getPeople();
		logger.debug("玩家[" + player.getPassportId() + "]请求人数设定["+tempPeople+"]");
		
		Human human = player.getHuman();
		if(human == null) return;
		
		
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		
		//判断是否是0或1
		if(cgTexasPeopleSetting.getPeople()!=TexasRoom.TABLE_PLAYER_MIN && cgTexasPeopleSetting.getPeople()!=TexasRoom.TABLE_PLAYER_MAX)
		{
			logger.warn("玩家["+ player.getPassportId() + "]输入错误参数people["+cgTexasPeopleSetting.getPeople()+"]");
			return;
		}
		
		humanTexasManager.people(tempPeople);
		GCTexasPeopleSetting gcTexasPeopleSetting = new GCTexasPeopleSetting();
		gcTexasPeopleSetting.setPeople(cgTexasPeopleSetting.getPeople());
		player.sendMessage(gcTexasPeopleSetting);
	}

	/**
	 * 是否自动补充
	 * @param player
	 * @param cgTexasAuto
	 */
	public void handleTexasAuto(Player player, CGTexasAuto cgTexasAuto) {
		// TODO Auto-generated method stub
		int tempAuto = cgTexasAuto.getIsAuto();
		
		logger.debug("玩家[" + player.getPassportId() + "]请求自动补充["+tempAuto+"]");
		
		Human human = player.getHuman();
		if(human == null) return;
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		
		//判断是否是0或1
		if(cgTexasAuto.getIsAuto()!=0 && cgTexasAuto.getIsAuto()!=1)
		{
			logger.warn("玩家["+ player.getPassportId() + "]输入错误参数auto["+cgTexasAuto.getIsAuto()+"]");
			return;
		}
		
		humanTexasManager.auto(tempAuto);
		GCTexasAuto gcTexasAuto = new GCTexasAuto();
		gcTexasAuto.setIsAuto(cgTexasAuto.getIsAuto());
		player.sendMessage(gcTexasAuto);
	}
	
	/**
	 * 玩家坐下
	 * @param player
	 * @param cgTexasSeat
	 */
	public void handleTexasSeat(Player player, CGTexasSeat cgTexasSeat) {
		// TODO Auto-generated method stub
		
		logger.debug("玩家[" + player.getPassportId() + "]请求坐下]");
		Human human = player.getHuman();
		if(human == null) return;
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		
		
		int pos = cgTexasSeat.getPos();
		//判断房间
		TexasRoom texasRoom = humanTexasManager.getTexasRoom();
		//判断是否在房间
		if(texasRoom==null){
			logger.warn("玩家["+ player.getPassportId() + "]不在房间");
			return;
		}
		
	
		if(texasRoom.isSNG()){
			logger.warn("玩家["+ player.getPassportId() + "]在sng房间不能坐下");
			return;
		}
		
		int roomType = texasRoom.getId();
		TexasRoomTemplate texasRoomTemplate = Globals.getTemplateService().get(
				roomType, TexasRoomTemplate.class);

		if (texasRoomTemplate == null) {
			logger.warn("房间类型[" + roomType + "]不存在，玩家["
					+ player.getPassportId() + "]坐下失败");
			return;
		}
		
		//判断座位是否合法
		if(pos<0 || pos >= texasRoomTemplate.getRoomNum()){
			
			logger.warn("位置[" + pos + "]不存在，玩家["
					+ player.getPassportId() + "]坐下失败");
			return;
		}
		
		//判断在座位上
		if(humanTexasManager.isInTable()){
			logger.warn("玩家["+ player.getPassportId() + "]已经在座位上");
			return;
		}
		
		//判断座位是否有人
		Player posPlayer = texasRoom.getPlayerManager().getRoomPlayerByPos(pos);
		if(posPlayer !=null){
			logger.warn("位置[" + pos + "]已被占，玩家["+ player.getPassportId() + "]坐下失败");
			return;
		}
		
		// 判断玩家是否有资格进入这类型房间
		if (player.getHuman().getGold()  < texasRoomTemplate.getMinCarry()) {
			logger.warn("玩家筹码小于大盲注，玩家[" + player.getPassportId() + "]加入失败");
			// 加入失败
			player.getHuman().sendErrorMessage(
					LangConstants.TEXAS_LESS_MIN_CARRY);
			return;
		}


		// 坐下
		texasRoom.playerSeat(player,pos);
	}



	/**
	 * 验证参数
	 * 
	 * @param player
	 * @return
	 */
	private boolean validatePlayer(Player player) {

		TexasRoom texasRoom = player.getHuman().getHumanTexasManager()
				.getTexasRoom();

		if (texasRoom == null) {
		
			return false;
		}
		
		if(!texasRoom.ifPlayerAction())
			return false;

		if (!texasRoom.ifCurrentPlayer(player)) {
			
			return false;
		}
		return true;
	}
	
	/**
	 * 补充筹码
	 * @param player
	 * @param cgTexasComplement
	 */
	public void handleTexasComplement(Player player,
			CGTexasComplement cgTexasComplement) {
		// TODO Auto-generated method stub
		long tempComplement = cgTexasComplement.getComplement();
		logger.debug("玩家[" + player.getPassportId() + "]请求补充筹码["+tempComplement+"]");
		
		Human human = player.getHuman();
		Assert.notNull(human);
	
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		TexasRoom texasRoom =humanTexasManager.getTexasRoom();

		if (texasRoom == null) {
			logger.warn("玩家[" + player.getId() + "]" + "不在房间内");
			return ;
		}
		if (texasRoom.isSNG()) {
			logger.warn("玩家[" + player.getId() + "]" + "sng房间不能补充筹码");
			return ;
		}
		
		//玩家不在桌上
		if(!humanTexasManager.isInTable()){
			logger.warn("玩家[" + player.getId() + "]" + "不在桌上内");
			return ;
		}
		
		//判断补充数
		humanTexasManager.setNextComplement(tempComplement);
		GCTexasComplement  gcTexasComplement = new GCTexasComplement();
		gcTexasComplement.setComplement(cgTexasComplement.getComplement());
		human.sendMessage(gcTexasComplement);
	}

	/**
	 * 打赏
	 * @param player
	 * @param cgTexasTips
	 */
	public void handleTexasTips(Player player, CGTexasTips cgTexasTips) {
		// TODO Auto-generated method stub
		logger.debug("玩家[" + player.getPassportId() + "]打赏]");
		
		Human human = player.getHuman();
		Assert.notNull(human);
		
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		TexasRoom texasRoom =humanTexasManager.getTexasRoom();
		if(texasRoom==null){
			logger.warn("玩家[" + player.getId() + "]" + "不在房间内");
			return;
		}
		

		//玩家不在桌上
		if(!humanTexasManager.isInTable()){
			
			logger.warn("玩家[" + player.getId() + "]" + "不在桌上内");
			return ;
		}
		
	

		int tips = texasRoom.getDealerCost();
		
		if(!human.hasEnoughMoney(tips, Currency.GOLD)){
		
			logger.warn("玩家[" + player.getId() + "]" + "不够金钱");
			return ;
		}
		
		String detailReason = MessageFormat.format(LogReasons.GoldLogReason.TEXAS_TIPS.getReasonText(), texasRoom.getId());
		human.costMoney(tips, Currency.GOLD, true, LogReasons.GoldLogReason.TEXAS_TIPS, detailReason, -1, 1);
		GCTexasTips gcTexasTips = new GCTexasTips();
		gcTexasTips.setPos(humanTexasManager.getPos());
		Globals.getTexasService().broadcastMsg(texasRoom, gcTexasTips);
		
	}
	
	/**
	 **--------------------------------sng --------------------
	*/
	public void handleTexasSngList(Player player, CGTexasSngList cgTexasSngList) {
		logger.debug("玩家["+player.getPassportId()+"]接收请求sng列表");
		
		Human human = player.getHuman();
		Assert.notNull(human);
	
		GCTexasSngList gcTexasSngList = new GCTexasSngList();
		
		List<TexasSngInfoData> sngInfoDataList = Globals.getTexasService().getTexasSngInfoDataList();
		gcTexasSngList.setSngInfoDataList(sngInfoDataList.toArray(new TexasSngInfoData[sngInfoDataList.size()]));
		player.sendMessage(gcTexasSngList);
	}
	
	/**
	 * 请求加入sng
	 * @param player
	 * @param cgJoinTexasSng
	 */
	public void handleJoinTexasSng(Player player, CGJoinTexasSng cgJoinTexasSng) {
		int sngId = cgJoinTexasSng.getSngId();
		logger.debug("玩家["+player.getPassportId()+"]请求加入sng["+sngId+"]");
		
		Human human = player.getHuman();
		Assert.notNull(human);
		
		
		SngMatchTemplate sngMatchTemplate= Globals.getTemplateService().get(sngId, SngMatchTemplate.class);
		if(sngMatchTemplate==null){
			logger.warn("玩家["+player.getPassportId()+"]请求加入sng["+sngId+"]不存在");
			return;
		}
		
		TexasRoom texasRoom = human.getHumanTexasManager().getTexasRoom();
		//判断是否已经在房间内 退出房间
		if(texasRoom!=null)
		{
			logger.warn("玩家[" + player.getPassportId() + "]正在玩,退出房间,房间类型["+texasRoom.getId()+"]");
			Globals.getTexasService().exitRoom(player);
		}
		
		//判断是否有门票
		boolean useTicket = false;
		
		HumanBagManager bag = human.getHumanBagManager();
		int ticketCount = bag.getCount(sngMatchTemplate.getTicketId());
		if(ticketCount>0){
			useTicket = true;
		}
		
		if(!useTicket)
		{
			int needMoney = sngMatchTemplate.getEntryFee() + sngMatchTemplate.getServiceFee();
			//判断是否足够钱
			if(!human.hasEnoughMoney(needMoney, Currency.GOLD)){
				logger.warn("玩家["+player.getPassportId()+"]请求加入sng["+sngId+"]不存在");
				return;
			}
			String detailReason = MessageFormat.format(LogReasons.GoldLogReason.SNG_FEE.getReasonText(), sngId,needMoney);
			human.costMoney(needMoney, Currency.GOLD, true, LogReasons.GoldLogReason.SNG_FEE, detailReason, -1, 1);
		}
		else{
			bag.removeItem(sngMatchTemplate.getTicketId(), 1);
			
			Globals.getLogService().sendItemLog(human, LogReasons.ItemLogReason.SNG_TICKET_USE, LogReasons.ItemLogReason.SNG_TICKET_USE.getReasonText(), sngMatchTemplate.getTicketId(), 1, bag.getCount(sngMatchTemplate.getTicketId()));
		}
	
		// 判断是否有房间可以进入
		TexasRoom room = Globals.getTexasService().roomForJoinSNG( player,sngId);
		if (room == null) {
			logger.warn("德州sng房间已满，玩家[" + player.getPassportId() + "]加入失败");
			// 加入失败
			player.getHuman().sendErrorMessage(LangConstants.SNG_TEXAS_ROOM_FULL);
			return;
		}

		// 加入房间
		Globals.getTexasService().joinSNGRoom(player,room,useTicket);

	}
	/**
	 *******************************vip list ************************* 
	 */
	
	/**
	 * 请求vip列表
	 * @param player
	 * @param cgTexasVipList
	 */
	public void handleTexasVipList(Player player, CGTexasVipList cgTexasVipList) {
		// TODO Auto-generated method stub
		logger.debug("玩家["+player.getPassportId()+"]请求vip列表");
		player.sendMessage(Globals.getTexasService().buildTexasVipList(player));
	}

	/**
	 * 请求加入vip房间
	 * @param player
	 * @param cgJoinTexasVip
	 */
	public void handleJoinTexasVip(Player player, CGJoinTexasVip cgJoinTexasVip) {
		// TODO Auto-generated method stub
		long vipId = cgJoinTexasVip.getVipId();
		logger.debug("玩家["+player.getPassportId()+"]请求加入vip房间["+vipId+"]");
		
		TexasRoom room = Globals.getTexasService().vipRoomByRid(player,vipId);
		
		//不存在房间
		if(room == null){
			GCJoinTexasVipFailed gcJoinTexasVipFailed=  new GCJoinTexasVipFailed();
			gcJoinTexasVipFailed.setErrorCode(JoinVipErrorEnum.NO_EXIST.getIndex());
			player.sendMessage(gcJoinTexasVipFailed);
			return;
		}
		
		//密码错误
		if(!cgJoinTexasVip.getPassword().equals(room.getPassword())){
			GCJoinTexasVipFailed gcJoinTexasVipFailed=  new GCJoinTexasVipFailed();
			gcJoinTexasVipFailed.setErrorCode(JoinVipErrorEnum.PASSWORD_ERROR.getIndex());
			player.sendMessage(gcJoinTexasVipFailed);
			return;
		}
		GCJoinTexasVipFailed gcJoinTexasVipFailed=  new GCJoinTexasVipFailed();
		gcJoinTexasVipFailed.setErrorCode(JoinVipErrorEnum.SUCCESS.getIndex());
		player.sendMessage(gcJoinTexasVipFailed);
		Globals.getTexasService().joinRoom(player, room); 
		
	}

	/**
	 * 德州信息查询
	 * @param player
	 * @param cgHumanTexasInfoDataSearch
	 */
	public void handleHumanTexasInfoDataSearch(Player player,
			CGHumanTexasInfoDataSearch cgHumanTexasInfoDataSearch) {
		// TODO Auto-generated method stub
	
		Human human = player.getHuman();
		Assert.notNull(human);
		
		Player tempPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(cgHumanTexasInfoDataSearch.getPlayerId());

		if(tempPlayer!=null)
		{
			GCHumanTexasInfoDataSearch gcHumanTexasInfoDataSearch =new GCHumanTexasInfoDataSearch();
			gcHumanTexasInfoDataSearch.setHumanTexasInfoData(HumanTexasInfoData.convertFromHumanTexas(tempPlayer.getHuman().getHumanTexasManager().getHumanTexas()));
			gcHumanTexasInfoDataSearch.setHumanTexasSNGInfoData(HumanTexasSNGInfoData.convertFromHumanTexasSNG(tempPlayer.getHuman().getHumanTexasManager().getHumanTexasSNG()));
			gcHumanTexasInfoDataSearch.setPlayerId(cgHumanTexasInfoDataSearch.getPlayerId());
			player.sendMessage(gcHumanTexasInfoDataSearch);
			return;
		}
		
		HumanTexasEntity tempHumanTexasEntity =Globals.getDaoService().getHumanTexasDao().getHumanTexasByCharId(cgHumanTexasInfoDataSearch.getPlayerId());
		HumanTexasSNGEntity tempHumanTexasSNGEntity=Globals.getDaoService().getHumanTexasSNGDao().getHumanTexasSNGByCharId(cgHumanTexasInfoDataSearch.getPlayerId());
		if(tempHumanTexasEntity==null|| tempHumanTexasSNGEntity==null){

			return;
		}
		GCHumanTexasInfoDataSearch gcHumanTexasInfoDataSearch =new GCHumanTexasInfoDataSearch();
		gcHumanTexasInfoDataSearch.setHumanTexasInfoData(HumanTexasInfoData.convertFromHumanTexasEntity(tempHumanTexasEntity));
		gcHumanTexasInfoDataSearch.setHumanTexasSNGInfoData(HumanTexasSNGInfoData.convertFromHumanTexasSNGEntity(tempHumanTexasSNGEntity));
		gcHumanTexasInfoDataSearch.setPlayerId(cgHumanTexasInfoDataSearch.getPlayerId());
		player.sendMessage(gcHumanTexasInfoDataSearch);
	}
	/**
	 * 加入指定房间
	 * @param player
	 * @param cgJoinTexasRoomId
	 */
	public void handleJoinTexasRoomId(Player player,
			CGJoinTexasRoomId cgJoinTexasRoomId) {
		
		// TODO Auto-generated method stub
		long roomId = cgJoinTexasRoomId.getRoomId();
		logger.debug("玩家[" + player.getPassportId() + "]请求加入普通房间,房间号["+roomId+"]");
		
		HumanTexasManager humanTexasManager = player.getHuman().getHumanTexasManager();
		
		TexasRoom texasRoom = humanTexasManager.getTexasRoom();
		//判断是否已经在房间内 退出房间
		if(texasRoom!=null)
		{
			logger.warn("玩家[" + player.getPassportId() + "]正在玩,退出房间,房间类型["+texasRoom.getId()+"]");
			Globals.getTexasService().exitRoom(player);
		}
		
		TexasRoom room = Globals.getTexasService().normalRoomForRoomId(player,roomId);
		if(room == null){
			logger.warn("玩家[" + player.getPassportId() + "]请求的房间是空的");
			return;
		}
		
		// 判断玩家是否有资格进入这类型房间
		if (player.getHuman().getGold() < room.getMinCarry()) {
			logger.warn("玩家筹码小于大盲注，玩家[" + player.getPassportId() + "]加入失败");
			// 加入失败
			player.getHuman().sendErrorMessage(
					LangConstants.TEXAS_LESS_MIN_CARRY);
			return;
		}

		// 加入房间
		Globals.getTexasService().joinRoom(player,room);
	}
	
	

}
