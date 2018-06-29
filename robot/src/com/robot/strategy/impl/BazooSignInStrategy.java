package com.robot.strategy.impl;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.bazoo.data.PriRoomData;
import com.gameserver.bazoo.msg.CGRoomPriList;
import com.gameserver.bazoo.msg.GCBazooHallStatus;
import com.gameserver.bazoo.msg.GCRoomCreate;
import com.gameserver.bazoo.msg.GCRoomPriJoin;
import com.gameserver.bazoo.msg.GCRoomPriList;
import com.gameserver.bazoogift.msg.GCBazooRedPackage;
import com.gameserver.bazoogift.msg.GCBazooSendGift;
import com.gameserver.bazoorank.data.HumanRankInfo;
import com.gameserver.bazoorank.msg.GCBazooRankRequest;
import com.gameserver.bazootask.data.BazooTaskInfo;
import com.gameserver.bazootask.msg.GCBazooAchieveTask;
import com.gameserver.bazootask.msg.GCBazooTask;
import com.gameserver.chat.msg.CGChatMsg;
import com.gameserver.chat.msg.GCChatMsg;
import com.gameserver.item.data.ItemInfoDataNew;
import com.gameserver.item.msg.GCBazooItemBuyByGold;
import com.gameserver.item.msg.GCBazooItemRequest;
import com.gameserver.item.msg.GCBazooMallRequest;
import com.gameserver.player.data.PlayerInfoData;
import com.gameserver.relation.msg.GCLoadFriendRequestList;
import com.robot.Robot;
import com.robot.strategy.OnceExecuteStrategy;

public class BazooSignInStrategy extends OnceExecuteStrategy {
	
	private Logger logger = Loggers.BAZOO;
	public BazooSignInStrategy(Robot robot) {
		super(robot);
	}

	
	@Override
	public void doAction() {
		//首先  先 进入房间 如果满了 在创建房间
		/*CGLoadFriendRequestList CGLoadFriendRequestList = new CGLoadFriendRequestList();
		this.getRobot().sendMessage(CGLoadFriendRequestList);*/
		
		/*CGBazooSignin CGBazooSignin = new CGBazooSignin();
		this.getRobot().sendMessage(CGBazooSignin);*/
		
		/* 排行榜
		CGBazooRankRequest CGBazooRankRequest = new CGBazooRankRequest();
		CGBazooRankRequest.setDateType(1);
		CGBazooRankRequest.setPage(1);
		this.getRobot().sendMessage(CGBazooRankRequest);*/
		/*
		CGBazooRankTotalGoldRequest CGBazooRankTotalGoldRequest = new CGBazooRankTotalGoldRequest();
		this.getRobot().sendMessage(CGBazooRankTotalGoldRequest);*/
		/*
		CGBazooAchieveTask CGBazooAchieveTask = new CGBazooAchieveTask();
		this.getRobot().sendMessage(CGBazooAchieveTask);
		*/
		
		/*CGBazooTask CGBazooTask = new CGBazooTask();
		this.getRobot().sendMessage(CGBazooTask);*/
		/*CGBazooGetReward CGBazooGetReward = new CGBazooGetReward();
		CGBazooGetReward.setTaskId(10000);
		this.getRobot().sendMessage(CGBazooGetReward);*/
		
		/*CGQueryPlayerInfo CGQueryPlayerInfo = new CGQueryPlayerInfo();
		CGQueryPlayerInfo.setUserId(Integer.valueOf(this.getRobot().getPassportId()));
		this.getRobot().sendMessage(CGQueryPlayerInfo);*/
		 
		
		
		/*CGRoomCreate CGRoomCreate = new CGRoomCreate();
		CGRoomCreate.setModeType(1);
		CGRoomCreate.setBet(1000);
		CGRoomCreate.setPassword("123456");
		this.getRobot().sendMessage(CGRoomCreate);*/
		
		
		
		/*CGBazooSendGift CGBazooSendGift = new CGBazooSendGift();
		CGBazooSendGift.setItemType(3);
		CGBazooSendGift.setItemId(0);
		CGBazooSendGift.setNumber(2);
		CGBazooSendGift.setToPlayerId(1427); //自己 1436
		this.getRobot().sendMessage(CGBazooSendGift);*/
		
		/*CGBazooItemBuyByGold CGBazooItemBuyByGold = new CGBazooItemBuyByGold();
		CGBazooItemBuyByGold.setItemId(20002);
		this.getRobot().sendMessage(CGBazooItemBuyByGold);*/
		
		/*CGBazooItemClockChange CGBazooItemClockChange = new CGBazooItemClockChange();
		CGBazooItemClockChange.setItemId(20000);
		this.getRobot().sendMessage(CGBazooItemClockChange);*/
		
	/*	CGBazooItemRequest CGBazooItemRequest = new CGBazooItemRequest();
		CGBazooItemRequest.setItemType(3);
		this.getRobot().sendMessage(CGBazooItemRequest);*/
		
		/*CGBazooMallRequest CGBazooMallRequest = new CGBazooMallRequest();
		this.getRobot().sendMessage(CGBazooMallRequest);*/
		
		/*CGBazooRedPackage CGBazooRedPackage = new CGBazooRedPackage();
		CGBazooRedPackage.setItemId(20010);
		this.getRobot().sendMessage(CGBazooRedPackage);*/
		
		
//		CGRequestOrder  CGRequestOrder = new CGRequestOrder();
//		CGRequestOrder.setProductId(1);
//		this.getRobot().sendMessage(CGRequestOrder);
		
		/*for(int i=0;i<10;i++){
			try {
				Thread.sleep(1000);
				CGBazooNewGuyProcess CGBazooNewGuyProcess = new CGBazooNewGuyProcess();
				int modetype = RandomUtil.nextInt(1, 5);
				CGBazooNewGuyProcess.setModeType(modetype);
				int process = RandomUtil.nextInt(1, 11);
				CGBazooNewGuyProcess.setProcess(process);
				this.getRobot().sendMessage(CGBazooNewGuyProcess);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		
		CGChatMsg  chchatMsg = new CGChatMsg();
		chchatMsg.setChannel(1);
		chchatMsg.setContent("sssss");
		chchatMsg.setDestRoleUUID(0);
		chchatMsg.setMsgType(0);
		
		this.getRobot().sendMessage(chchatMsg);
	}

	@Override
	public void onResponse(IMessage message) {
		
		
		if(message instanceof GCChatMsg){
			GCChatMsg GCChatMsg = (GCChatMsg)message;
			GCChatMsg.getChannel();
			logger.info("聊天"+GCChatMsg.getContent()+"----"+GCChatMsg.getFromRoleImg());
			return;
		}
		
		
		
		if(message instanceof GCBazooRankRequest){
			GCBazooRankRequest GCBazooRankRequest = (GCBazooRankRequest)message;
			HumanRankInfo[] HumanRankInfoArr = GCBazooRankRequest.getHumanRankInfo();
			for(int i=0;i<HumanRankInfoArr.length;i++){
				HumanRankInfo HumanRankInfo = HumanRankInfoArr[i];
				logger.info("[无双吹牛-排行榜]---["+HumanRankInfo.getGold()+"-"+HumanRankInfo.getName()+"-"+HumanRankInfo.getPassportId()+"]");
			}
		}
		
		if(message instanceof GCBazooAchieveTask){
			GCBazooAchieveTask GCBazooAchieveTask = (GCBazooAchieveTask)message;
			BazooTaskInfo[] BazooTaskInfoArr = GCBazooAchieveTask.getBazooTaskInfo();
			for(int i=0;i<BazooTaskInfoArr.length;i++){
				BazooTaskInfo BazooTaskInfo = BazooTaskInfoArr[i];
				logger.info("[无双吹牛-成就任务]---["+BazooTaskInfo.getTaskId()+""
										  + "-"+BazooTaskInfo.getRefreshtype()+"-"
										  + "-"+BazooTaskInfo.getFinishTimes()+"-"
										  + "-"+BazooTaskInfo.getModeType()+"-"
										  + "-"+BazooTaskInfo.getNameId()+"-"
										  + "-"+BazooTaskInfo.getRewardNum()+"-"
										  + "-"+BazooTaskInfo.getWayOfPlay()+"]");
			}
		}
		if(message instanceof GCBazooTask){
			GCBazooTask GCBazooTask = (GCBazooTask)message;
			BazooTaskInfo[] BazooTaskInfoArr = GCBazooTask.getBazooTaskInfo();
			for(int i=0;i<BazooTaskInfoArr.length;i++){
				BazooTaskInfo BazooTaskInfo = BazooTaskInfoArr[i];
				logger.info("[无双吹牛-成就任务]---["+BazooTaskInfo.getTaskId()+""
						+ "-"+BazooTaskInfo.getRefreshtype()+"-"
						+ "-"+BazooTaskInfo.getFinishTimes()+"-"
						+ "-"+BazooTaskInfo.getModeType()+"-"
						+ "-"+BazooTaskInfo.getNameId()+"-"
						+ "-"+BazooTaskInfo.getRewardNum()+"-"
						+ "-"+BazooTaskInfo.getWayOfPlay()+"]");
			}
		}
		
		
		if(message instanceof GCBazooHallStatus){
			GCBazooHallStatus GCBazooHallStatus = (GCBazooHallStatus)message;
			logger.info("[无双吹牛-登录]---[收到啦]---["+GCBazooHallStatus.getSignInStatus()+""+GCBazooHallStatus.getTaskNumber());
		}
		
		
		
		if(message instanceof GCLoadFriendRequestList){
			GCLoadFriendRequestList msg = (GCLoadFriendRequestList)message;
			PlayerInfoData[]  PlayerInfoDataArr  = msg.getFriendRequestInfoDataList();
			if(PlayerInfoDataArr != null && PlayerInfoDataArr.length >0){
				for(PlayerInfoData data:PlayerInfoDataArr){
					logger.info("[无双吹牛-登录]---[好友申请的ID]---["+data.getPlayerId());
				}
			}
		}
		
		if(message instanceof GCRoomCreate){
			GCRoomCreate GCRoomCreate = (GCRoomCreate)message;
			logger.info("[无双吹牛-创建私人房间]---[GCRoomCreate-IsSuccess]---["+GCRoomCreate.getIsSuccess());
			
		}
		if(message instanceof GCRoomPriJoin){
			GCRoomPriJoin GCRoomPriJoin = (GCRoomPriJoin)message;
			logger.info("[无双吹牛-创建私人房间]---[GCRoomPriJoin-IsSuccess]---["+GCRoomPriJoin.getIsSuccess());
			
			CGRoomPriList CGRoomPriList = new CGRoomPriList();
			this.getRobot().sendMessage(CGRoomPriList);
		}
		if(message instanceof GCRoomPriList){
			GCRoomPriList GCRoomPriList = (GCRoomPriList)message;
			PriRoomData[] PriRoomDataArr = GCRoomPriList.getPriRoomData();
			for(int i=0;i<PriRoomDataArr.length;i++){
				PriRoomData PriRoomData = PriRoomDataArr[i];
				logger.info("[无双吹牛-创建私人房间-返回]---[getCreater]---["+PriRoomData.getCreater());
				logger.info("[无双吹牛-创建私人房间-返回]---[getCreaterPassportId]---["+PriRoomData.getCreaterPassportId());
				logger.info("[无双吹牛-创建私人房间-返回]---[getFlag]---["+PriRoomData.getFlag());
				logger.info("[无双吹牛-创建私人房间-返回]---[getImg]---["+PriRoomData.getImg());
				logger.info("[无双吹牛-创建私人房间-返回]---[getIsNeedPassword]---["+PriRoomData.getIsNeedPassword());
				logger.info("[无双吹牛-创建私人房间-返回]---[getModeType]---["+PriRoomData.getModeType());
				logger.info("[无双吹牛-创建私人房间-返回]---[getNumTotalNum]---["+PriRoomData.getNumTotalNum());
				logger.info("[无双吹牛-创建私人房间-返回]---[getRoomNumber]---["+PriRoomData.getRoomNumber());
				logger.info("[无双吹牛-创建私人房间-返回]---[getVip]---["+PriRoomData.getVip());
			}
			
		}
		
		//礼物
		if(message instanceof GCBazooSendGift){
			GCBazooSendGift GCBazooSendGift = (GCBazooSendGift)message;
			logger.info("[无双吹牛-礼物-返回]---["+GCBazooSendGift.getFromPlayerGold());
			logger.info("[无双吹牛-礼物-返回]---["+GCBazooSendGift.getToPlayerId());
			logger.info("[无双吹牛-礼物-返回]---["+GCBazooSendGift.getItemId());
			logger.info("[无双吹牛-礼物-返回]---["+GCBazooSendGift.getItemType());
		}
		//背包列表
		if(message instanceof GCBazooItemRequest){
			GCBazooItemRequest GCBazooItemRequest = (GCBazooItemRequest)message;
			ItemInfoDataNew[] ItemInfoDataNewArr = GCBazooItemRequest.getItemInfoDataNew();
			for(int i=0;i<ItemInfoDataNewArr.length;i++){
				ItemInfoDataNew data = ItemInfoDataNewArr[i];
				logger.info("[无双吹牛-礼物-返回]---["+data.getImg());
				logger.info("[无双吹牛-礼物-返回]---["+data.getItemId());
				logger.info("[无双吹牛-礼物-返回]---["+data.getItemId());
				logger.info("[无双吹牛-礼物-返回]---["+data.getItemType());
				logger.info("[无双吹牛-礼物-返回]---["+data.getPrice());
				logger.info("[无双吹牛-礼物-返回]---["+data.getUsingClockItemId());
				logger.info("[无双吹牛-礼物-返回]-------------------------------------------------");
			}
			
		}
		//道具列表
		if(message instanceof GCBazooMallRequest){
			GCBazooMallRequest GCBazooMallRequest = (GCBazooMallRequest)message;
			ItemInfoDataNew[] ItemInfoDataNewArr = GCBazooMallRequest.getItemInfoDataNew();
			for(int i=0;i<ItemInfoDataNewArr.length;i++){
				ItemInfoDataNew data = ItemInfoDataNewArr[i];
				logger.info("[无双吹牛-道具列表-返回]---["+data.getImg());
				logger.info("[无双吹牛-道具列表-返回]---["+data.getItemId());
				logger.info("[无双吹牛-道具列表-返回]---["+data.getItemId());
				logger.info("[无双吹牛-道具列表-返回]---["+data.getItemType());
				logger.info("[无双吹牛-道具列表-返回]---["+data.getPrice());
				logger.info("[无双吹牛-道具列表-返回]---["+data.getUsingClockItemId());
				logger.info("[无双吹牛-道具列表-返回]-------------------------------------------------");
			}
			
		}
		//是否成功
		if(message instanceof GCBazooItemBuyByGold){
			GCBazooItemBuyByGold GCBazooItemBuyByGold = (GCBazooItemBuyByGold)message;
			int success = GCBazooItemBuyByGold.getIsSucess();
			int langId = GCBazooItemBuyByGold.getLangId();
			String[] params = GCBazooItemBuyByGold.getParamsList();
			logger.info("[无双吹牛-是否成功-返回]---[success："+success);
			logger.info("[无双吹牛-是否成功-返回]---[langId："+langId);
			logger.info("[无双吹牛-礼物-返回]-------------------------------------------------");
			
		}
		//红包 是否成功
		if(message instanceof GCBazooRedPackage){
			GCBazooRedPackage GCBazooRedPackage = (GCBazooRedPackage)message;
			int success = GCBazooRedPackage.getIsSucess();
			int langId = GCBazooRedPackage.getLangId();
			String[] params = GCBazooRedPackage.getParamsList();
			logger.info("[无双吹牛-红包是否成功-返回]---[success："+success);
			logger.info("[无双吹牛-红包是否成功-返回]---[langId："+langId);
			logger.info("[无双吹牛-红包-返回]-------------------------------------------------");
			
		}
		
	}
}