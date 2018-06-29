package com.gameserver.bazoo.handler;


import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.common.LogReasons;
import com.common.ModuleMessageHandler;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.gameserver.bazoo.data.BoQuThroughData;
import com.gameserver.bazoo.data.NewGuyProcess;
import com.gameserver.bazoo.data.PriRoomData;
import com.gameserver.bazoo.msg.CGBazooBoqu;
import com.gameserver.bazoo.msg.CGBazooChangeName;
import com.gameserver.bazoo.msg.CGBazooFacebookAddGold;
import com.gameserver.bazoo.msg.CGBazooHallStatus;
import com.gameserver.bazoo.msg.CGBazooHeartBeat;
import com.gameserver.bazoo.msg.CGBazooMagicFace;
import com.gameserver.bazoo.msg.CGBazooNewguyProcess;
import com.gameserver.bazoo.msg.CGBlackWhiteCallNum;
import com.gameserver.bazoo.msg.CGCowSingleSwing;
import com.gameserver.bazoo.msg.CGDiceSingleSwing;
import com.gameserver.bazoo.msg.CGGuessBigSmall;
import com.gameserver.bazoo.msg.CGModeChose;
import com.gameserver.bazoo.msg.CGRobOpen;
import com.gameserver.bazoo.msg.CGRoomCreate;
import com.gameserver.bazoo.msg.CGRoomEnter;
import com.gameserver.bazoo.msg.CGRoomOut;
import com.gameserver.bazoo.msg.CGRoomPriJoin;
import com.gameserver.bazoo.msg.CGRoomPriList;
import com.gameserver.bazoo.msg.CGRoomPriSearch;
import com.gameserver.bazoo.msg.CGRoomPubJoin;
import com.gameserver.bazoo.msg.CGShowHandSingleSwich;
import com.gameserver.bazoo.msg.CGShowHandSingleSwichCancel;
import com.gameserver.bazoo.msg.CGShowHandSingleSwing;
import com.gameserver.bazoo.msg.CGTalkBig;
import com.gameserver.bazoo.msg.GCBazooBoqu;
import com.gameserver.bazoo.msg.GCBazooChangeName;
import com.gameserver.bazoo.msg.GCBazooHallStatus;
import com.gameserver.bazoo.msg.GCBazooMagicFace;
import com.gameserver.bazoo.msg.GCRoomCreate;
import com.gameserver.bazoo.msg.GCRoomEnterNotAllow;
import com.gameserver.bazoo.msg.GCRoomPriJoin;
import com.gameserver.bazoo.msg.GCRoomPriList;
import com.gameserver.bazoo.msg.GCRoomPriSearch;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.player.Player;


/**
 * 聊天消息处理器
 * @author Thinker
 */
public class BazooMessageHandler implements ModuleMessageHandler
{
	private Logger logger = Loggers.BAZOO;
	
	
	/**
	 * 选择模式
	 * @param player
	 * @param cgModeChose
	 */
	public void handleModeChose(Player player, CGModeChose cgModeChose) {
		Globals.getBazooPubService().handleModeChose(player, cgModeChose);
		
	}

	/**
	 * 私人创建房间
	 * @param player
	 * @param cgRoomCreate
	 */
	public void handleRoomCreate(Player player, CGRoomCreate cgRoomCreate) {
		//只要用户 还在某个房间里 就不让创建房间
		boolean isInRoom = Globals.getBazooPubService().beforeEnterBazooRoom(player);
		if(isInRoom){
			String roomNumber = player.getHuman().getBazooRoom();
			GCRoomEnterNotAllow GCRoomEnterNotAllow = new GCRoomEnterNotAllow();
			GCRoomEnterNotAllow.setLangId(LangConstants.DICE_USER_NOT_OUT_ROOM);
			GCRoomEnterNotAllow.setParamsList(new String[]{roomNumber});
			player.sendMessage(GCRoomEnterNotAllow);
			logger.info("[无双吹牛]---[创建房间]---[用户不能创建房间,他还在"+roomNumber+" 房间中]---[当前用户ID::"+player.getPassportId()+"]---[当前用户名::"+player.getHuman().getName()+"]");
			return;
		}
		//就是设置一下参数 ，其他啥都没干
		Globals.getBazooPriService().priRoomCreate(player,cgRoomCreate);
		Globals.getBazooPubService().enterBazooRoom(player,cgRoomCreate.getBet(),RoomNumber.PRI_ROOM);
		Room room = Globals.getBazooPubService().getRoomService().getRoom(player.getHuman().getBazooRoomNumber());
		GCRoomCreate GCRoomCreate = new GCRoomCreate();
		//创建成功
		if(room != null){
			room.setPassword(cgRoomCreate.getPassword());
			room.setPriRoomCreater(player);
			
			GCRoomCreate.setIsSuccess(0);
			player.sendMessage(GCRoomCreate);
			
			GCRoomPriJoin GCRoomPriJoin = new GCRoomPriJoin();
			GCRoomPriJoin.setIsSuccess(0);
			GCRoomPriJoin.setModeType(room.getRoomNumber().getModeType());
			player.sendMessage(GCRoomPriJoin);
		//创建失败
		}else{
			GCRoomCreate.setIsSuccess(1);
			player.sendMessage(GCRoomCreate);
		}
		
	}
	/**
	 * 列出所有私人房间
	 * @param player
	 * @param cgRoomPriSearch
	 */
	public void handleRoomPriList(Player player, CGRoomPriList cgRoomPriList) {
		PriRoomData[] PriRoomDataArr = Globals.getBazooPriService().listAllPriRooms();
		GCRoomPriList GCRoomPriList = new GCRoomPriList();
		GCRoomPriList.setPriRoomData(PriRoomDataArr);
		player.sendMessage(GCRoomPriList);
	}
	/**
	 * 搜索私人房间
	 * @param player
	 * @param cgRoomPriSearch
	 */
	public void handleRoomPriSearch(Player player,
			CGRoomPriSearch cgRoomPriSearch) {
		String roomNumber = cgRoomPriSearch.getRoomNumber();
		boolean exist = Globals.getBazooPriService().roomPriSearch(roomNumber);
		GCRoomPriSearch GCRoomPriSearch = new GCRoomPriSearch();
		if(exist){
			GCRoomPriSearch.setRoomNumber(roomNumber);
		}else{
			GCRoomPriSearch.setRoomNumber("");
		}
		player.sendMessage(GCRoomPriSearch);
		
	}
	
	/**
	 * 加入私人房间
	 * @param player
	 * @param cgRoomCreate
	 */
	public void handleRoomPriJoin(Player player, CGRoomPriJoin cgRoomPriJoin) {
		String roomNumber = cgRoomPriJoin.getRoomNumber();
		RoomNumber objRoomNumber= RoomNumber.toRoomNumber(roomNumber);
		if(objRoomNumber.getPubOrPri() != RoomNumber.PRI_ROOM){
			logger.info("[无双吹牛]---[加入私人房间]---[该房间"+roomNumber+"是公共房间]");
			return;
		}
		Room room = Globals.getBazooPubService().getRoomService().getRoom(objRoomNumber);
		if(room == null){
			logger.info("[无双吹牛]---[加入私人房间]---[该房间"+roomNumber+"不存在]");
			GCRoomEnterNotAllow GCRoomEnterNotAllow = new GCRoomEnterNotAllow();
			GCRoomEnterNotAllow.setLangId(LangConstants.DICE_USER_ROOM_NOT_EXIST);
			GCRoomEnterNotAllow.setParamsList(new String[]{roomNumber});
			player.sendMessage(GCRoomEnterNotAllow);
			return;
		}
		//判断房间需不需要密码 如果是空的就是不需要密码
		if(StringUtils.isNotBlank(room.getPassword())){//需要密码
			if(StringUtils.isBlank(cgRoomPriJoin.getPassword()) || !room.getPassword().equals(cgRoomPriJoin.getPassword())){
				logger.info("[无双吹牛]---[加入私人房间]---[该房间"+roomNumber+",密码不正确]");
				GCRoomEnterNotAllow GCRoomEnterNotAllow = new GCRoomEnterNotAllow();
				GCRoomEnterNotAllow.setLangId(LangConstants.DICE_USER_PRI_PASSWORD_WRONG);
				GCRoomEnterNotAllow.setParamsList(new String[]{});
				player.sendMessage(GCRoomEnterNotAllow);
				return;
			}
		}
		player.getHuman().setBazooRoomNumber(objRoomNumber);
		Globals.getBazooPubService().enterBazooRoom(player,objRoomNumber.getBet(),RoomNumber.PRI_ROOM);
		
		GCRoomPriJoin GCRoomPriJoin = new GCRoomPriJoin();
		GCRoomPriJoin.setIsSuccess(0);
		GCRoomPriJoin.setModeType(objRoomNumber.getModeType());
		player.sendMessage(GCRoomPriJoin);
	}


	
	/**
	 * 进入房间
	 * @param player
	 * @param cgRoomEnter
	 */
	public void handleRoomEnter(Player player, CGRoomEnter cgRoomEnter) {
		Globals.getBazooPubService().enterBazooRoom(player,cgRoomEnter.getBet(),RoomNumber.PUB_ROOM);
	}


	/**
	 * 退出房间
	 * @param player
	 * @param cgRoomOut
	 */
	public void handleRoomOut(Player player, CGRoomOut cgRoomOut) {
		logger.info("[无双吹牛]---[用户主动-退出房间]---[当前用户::"+player.getPassportId()+"]");
		Globals.getBazooPubService().outBazooRoom(player);
	}


	/**
	 * 单独摇色子
	 * @param player
	 * @param cgDiceSingleSwing
	 */
	public void handleDiceSingleSwing(Player player,
			CGDiceSingleSwing cgDiceSingleSwing) {
		Globals.getBazooMService().singleSwingDice(player);
	}


	/**
	 * 开始说大话
	 * @param player
	 * @param cgTalkBig
	 * @param isback 是否是后台 发送的调用 是 1，非 0
	 */
	public void handleTalkBig(Player player, CGTalkBig cgTalkBig) {
		int diceNum = cgTalkBig.getDiceNum();
		int diceValue = cgTalkBig.getDiceValue();
		Globals.getBazooMService().talkBig(player, diceNum, diceValue,"前端");
		
	}

	
	/**
	 * 抢开
	 * @param player
	 * @param cgRobOpen
	 */
	public void handleRobOpen(Player player, CGRobOpen cgRobOpen) {
		
		
		Globals.getBazooMService().robOpen(player);
	}


	/**
	 * 竞猜大小
	 * @param player
	 * @param cgOtherGuessBigSmall
	 */
	public void handleGuessBigSmall(Player player,
			CGGuessBigSmall cgGuessBigSmall) {
		Globals.getBazooMService().everyGuessBigSmall(player, cgGuessBigSmall.getBigSmall());
		
	}

	
	
	/**
	 * 牛牛模式 
	 * 单独摇色子
	 * @param player
	 * @param cgCowSingleSwing
	 */
	public void handleCowSingleSwing(Player player,
			CGCowSingleSwing cgCowSingleSwing) {
		
		Globals.getBazooCowService().swingSingle(player, cgCowSingleSwing.getDiceValues());
	}

	/**
	 * 无双吹牛的 心跳
	 * @param player
	 * @param cgBazooHeartBeat
	 */
	public void handleBazooHeartBeat(Player player,
			CGBazooHeartBeat cgBazooHeartBeat) {
		Globals.getBazooPubService().bazooHeartBeat(player);
	}

	
	/**
	 * 梭哈 模式
	 * 统一摇色子
	 * @param player
	 * @param cgShowHandSingleSwing
	 */
	public void handleShowHandSingleSwing(Player player,
			CGShowHandSingleSwing cgShowHandSingleSwing) {
		List<Integer> diceValues = new ArrayList<Integer>();
		int[] diceValueArr =cgShowHandSingleSwing.getDiceValues();
		if(diceValueArr == null){
			diceValueArr=new int[0];
		}else{
			for(int i=0;i<diceValueArr.length;i++){
				diceValues.add(diceValueArr[i]);
			}
		}
		Globals.getBazooShowHandService().swingSingle(player,diceValues,true);
		
	}

	/**
	 * 梭哈模式
	 * 用户 选择 某个色子
	 * @param player
	 * @param cgShowHandSingleSwich
	 */
	public void handleShowHandSingleSwich(Player player,
			CGShowHandSingleSwich cgShowHandSingleSwich) {
		Globals.getBazooShowHandService().singleSwich(player,cgShowHandSingleSwich.getDiceIndex());
		
	}

	/**
	 * 
	 * 梭哈模式
	 * 用户  取消选择 某个色子
	 * @param player
	 * @param cgShowHandSingleSwich
	 */
	public void handleShowHandSingleSwichCancel(Player player,
			CGShowHandSingleSwichCancel cgShowHandSingleSwichCancel) {
		
		Globals.getBazooShowHandService().singleSwichCancel(player,cgShowHandSingleSwichCancel.getDiceIndex());
	}

	public void handleBazooChangeName(Player player,
			CGBazooChangeName cgBazooChangeName) {
		
		String nickname = cgBazooChangeName.getNickname();
		if(StringUtils.isNotBlank(nickname)){
			player.getHuman().setName(nickname);
			player.getHuman().setModified();
		}
		GCBazooChangeName GCBazooChangeName = new GCBazooChangeName();
		GCBazooChangeName.setNickname(player.getHuman().getName());
		player.sendMessage(GCBazooChangeName);
	}

	public void handleRoomPubJoin(Player player, CGRoomPubJoin cgRoomPubJoin) {
		String roomNumber = cgRoomPubJoin.getRoomNumber();
		RoomNumber objRoomNumber= RoomNumber.toRoomNumber(roomNumber);
		Room room = Globals.getBazooPubService().getRoomService().getRoom(objRoomNumber);
		if(room == null){
			GCRoomEnterNotAllow GCRoomEnterNotAllow = new GCRoomEnterNotAllow();
			GCRoomEnterNotAllow.setLangId(LangConstants.DICE_USER_ROOM_NOT_EXIST);
			GCRoomEnterNotAllow.setParamsList(new String[]{roomNumber});
			player.sendMessage(GCRoomEnterNotAllow);
			logger.info("[无双吹牛]---[该房间"+roomNumber+"不存在]");
			return;
		}
		//将房间好设置到 用户身上
		player.getHuman().setBazooRoomNumber(objRoomNumber);
		logger.info("[无双吹牛]---[当前房间"+roomNumber+"，当前用户:"+player.getPassportId()+"]");
		Globals.getBazooPubService().enterBazooRoom(player,objRoomNumber.getBet(),RoomNumber.PUB_ROOM);
		
	}

	public void handleBazooHallStatus(Player player,
			CGBazooHallStatus cgBazooHallStatus) {
		GCBazooHallStatus GCBazooHallStatus = player.getHuman().getHumanBazooManager().getStatus();
		player.sendMessage(GCBazooHallStatus);
	}

	/**
	 * 发送魔法表情
	 * @param player
	 * @param cgBazooMagicFace
	 */
	public void handleBazooMagicFace(Player player,
			CGBazooMagicFace cgBazooMagicFace) {
		GCBazooMagicFace magicFace = new GCBazooMagicFace();
		magicFace.setSendPassportId(cgBazooMagicFace.getSendPassportId());
		magicFace.setReceivePassportId(cgBazooMagicFace.getReceivePassportId());
		magicFace.setMagicFace(cgBazooMagicFace.getMagicFace());
		
		Room room = Globals.getBazooPubService().getRoomService().getRoom(player);
		if(room == null){
			return;
		}
		List<Player> players = room.getPlayers();
		for(Player p:players){
			p.sendMessage(magicFace);
		}
	}

	
	/**
	 * 红黑单双模式
	 * @param player
	 * @param cgBlackWhiteCallNum
	 */
	public void handleBlackWhiteCallNum(Player player,
			CGBlackWhiteCallNum cgBlackWhiteCallNum) {
		Globals.getBlackWhiteService().callNum(player, cgBlackWhiteCallNum.getDiceType(),"用户");
	}

	
	
	
	
	
	/**
	 * 第三方 平台 博趣 的入口
	 * @param player
	 * @param cgBazooBoqu
	 */
	public void handleBazooBoqu(Player player, CGBazooBoqu cgBazooBoqu) {
		BoQuThroughData boQuThroughData = new BoQuThroughData();
		boQuThroughData.setAccess_token(player.getHuman().getUserInfo().getAccessToken());
		boQuThroughData.setToken_type(player.getHuman().getUserInfo().getTokenType());
		boQuThroughData.setGame_id(Globals.getServerConfig().getGameId());
		boQuThroughData.setGame_user_id(Long.valueOf(player.getPassportId()).intValue());
		boQuThroughData.setTimestamp(new Date().getTime());
		String jb = JSONObject.toJSONString(boQuThroughData);
		logger.info("当前访问的博趣的页面：："+jb);
		String base64JB = new String(Base64.getEncoder().encode(jb.getBytes()));
		
		String device = cgBazooBoqu.getPcOrMobile();
		GCBazooBoqu gCBazooBoqu = new GCBazooBoqu();
		if(device.equals("pc")){//pc端
			String htmlPage = Globals.getServerConfig().getBoquPCThroughURL()+"?data="+base64JB;
			gCBazooBoqu.setHtmlPage(htmlPage);
		}else{//移动端
			String htmlPage = Globals.getServerConfig().getBoquMobileThroughURL()+"?data="+base64JB;
			gCBazooBoqu.setHtmlPage(htmlPage);
		}
		player.sendMessage(gCBazooBoqu);
	}
	
	
	/**
	 * 新手流程
	 * @param player
	 * @param cgBazooNewguyProcess
	 */
	public void handleBazooNewguyProcess(Player player,
			CGBazooNewguyProcess cgBazooNewguyProcess) {
		int modeType = cgBazooNewguyProcess.getModeType();
		String newGuyProcess = player.getHuman().getBazooNewGuyProcess();
		NewGuyProcess newGuy = null;
		if(StringUtils.isBlank(newGuyProcess)){
			newGuy= new NewGuyProcess();
			newGuy.setClassicCompleted(0);
			newGuy.setNiuniuCompleted(0);
			newGuy.setShowhandCompleted(0);
			newGuy.setRedblackCompleted(0);
		}else{
			newGuy= JSONObject.parseObject(newGuyProcess,NewGuyProcess.class);
		}
		if(modeType == 1){
			newGuy.setClassicCompleted(1);
		}else if(modeType == 2){
			newGuy.setNiuniuCompleted(1);
		}else if(modeType == 3){
			newGuy.setShowhandCompleted(1);
		}else if(modeType == 4){
			newGuy.setRedblackCompleted(1);
		}
		player.getHuman().setBazooNewGuyProcess(JSONObject.toJSONString(newGuy));
		player.getHuman().setModified();
	}

	/**
	 * facebook 给用户 加金币（用户每看完一次广告 就加一次金币）
	 * @param player
	 * @param cgBazooFacebookAddGold
	 */
	public void handleBazooFacebookAddGold(Player player,
			CGBazooFacebookAddGold cgBazooFacebookAddGold) {
		/*Integer gold = cgBazooFacebookAddGold.getGold();
		if(gold == null || gold <= 0){
			logger.info("facebook 添加金币 不正常：："+gold);
			return;
		}*/
		player.getHuman().giveMoney(120000, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_FACEBOOK_GIVE_GOLD, LogReasons.GoldLogReason.BAZOO_FACEBOOK_GIVE_GOLD.getReasonText(), -1, 1);
		
	}

	

	








}
