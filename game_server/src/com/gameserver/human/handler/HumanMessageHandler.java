package com.gameserver.human.handler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.db.model.UserInfo;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.VideoRewardServer;
import com.gameserver.human.manager.HumanBagManager;
import com.gameserver.human.manager.HumanMiscManager;
import com.gameserver.human.msg.CGChaneageCountries;
import com.gameserver.human.msg.CGExpDouble;
import com.gameserver.human.msg.CGHumanChangeImg;
import com.gameserver.human.msg.CGHumanChangeName;
import com.gameserver.human.msg.CGHumanChangeSex;
import com.gameserver.human.msg.CGHumanDetailInfoTodayView;
import com.gameserver.human.msg.CGHumanNewGuide;
import com.gameserver.human.msg.CGHumanVideoNum;
import com.gameserver.human.msg.CGRoomBigwinGift;
import com.gameserver.human.msg.CGSlotRoomGift;
import com.gameserver.human.msg.CGSlotRoomPlease;
import com.gameserver.human.msg.GCChaneageCountries;
import com.gameserver.human.msg.GCExpDouble;
import com.gameserver.human.msg.GCHumanChangeImg;
import com.gameserver.human.msg.GCHumanChangeName;
import com.gameserver.human.msg.GCHumanChangeSex;
import com.gameserver.human.msg.GCHumanVideoNum;
import com.gameserver.human.msg.GCSlotRoomGift;
import com.gameserver.human.msg.GCSlotRoomPlease;
import com.gameserver.human.redismsg.RoomReq;
import com.gameserver.item.template.InteractiveItemTemplate;
import com.gameserver.player.Player;
import com.gameserver.player.PlayerConstants;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.task.enums.ClientType;
import com.gameserver.task.enums.RefreshType;

/**
 * 
 * @author wayne
 *
 */
public class HumanMessageHandler {
	
	private Logger logger  = Loggers.humanLogger;

	
	/**
	 * 更改名字
	 * @param player
	 * @param cgHumanChangeName
	 */
	public void handleHumanChangeName(Player player,
			CGHumanChangeName cgHumanChangeName) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if(human == null)
			return;
		
	
		
		//过滤字符
		String name = Globals.getDirtFilterService().filtName(cgHumanChangeName.getName());
		String beforeName = human.getName();
		//判断字符长度
		if(name.length()<PlayerConstants.MIN_NAME_LENGTH_ENG){
			logger.warn("玩家["+player.getPassportId()+"] 重命名长度过短 ["+cgHumanChangeName.getName()+"]");
			return;
		}
		
		if(name.length()>PlayerConstants.MAX_NAME_LENGTH_ENG){
			logger.warn("玩家["+player.getPassportId()+"] 重命名长度过长["+cgHumanChangeName.getName()+"]");
			return;
		}
		
		if(name == beforeName){
			GCHumanChangeName gcHumanChangeName = new GCHumanChangeName();
			gcHumanChangeName.setDuplicateNum(LangConstants.USER_DOUBLE);
			player.sendMessage(gcHumanChangeName);
			logger.warn("玩家["+player.getPassportId()+"] 重命名一样名字["+cgHumanChangeName.getName()+"]");
			return;
		}
		
		//查询 名字
		String dbName = Globals.getOnlinePlayerService().loadPlayerByName(name);
		if (dbName!=null){
			GCHumanChangeName gcHumanChangeName = new GCHumanChangeName();
			gcHumanChangeName.setDuplicateNum(LangConstants.USER_DOUBLE);
			player.sendMessage(gcHumanChangeName);
			logger.warn("玩家["+player.getPassportId()+"] 命名已存在的名字["+cgHumanChangeName.getName()+"]");
			return;
		}
		
		//判断是否是首次重名
		HumanMiscManager miscManager = human.getHumanMiscManager();
		
		if(miscManager.ifRename()){
		
			HumanBagManager  humanBagManager = human.getHumanBagManager();
			int tempRenameCount = humanBagManager.getCount(Globals.getConfigTempl().getChangeNameId());
			if(tempRenameCount<=0){
				logger.warn("玩家["+player.getPassportId()+"] 没有足够的改名卡");
				return;
			}
			humanBagManager.removeItem(Globals.getConfigTempl().getChangeNameId(), 1);
			//record item use
			String itemUseDetailReason = MessageFormat.format(LogReasons.ItemLogReason.ITEM_USE.getReasonText(),  Globals.getConfigTempl().getChangeNameId());
			Globals.getLogService().sendItemLog(human, LogReasons.ItemLogReason.ITEM_USE, itemUseDetailReason, Globals.getConfigTempl().getChangeNameId(), 1, tempRenameCount-1);
			
			human.sendMessage(humanBagManager.buildHumanBagInfoData());
		}
		
		miscManager.rename();
		human.setName(name);
		human.setModified();
		GCHumanChangeName gcHumanChangeName = new GCHumanChangeName();
		gcHumanChangeName.setName(name);
		gcHumanChangeName.setDuplicateNum(0);
		human.sendMessage(gcHumanChangeName);
		human.sendMessage(miscManager.buildGCMiscInfoData());
		
		human.sendSystemMessage(LangConstants.CHANGE_NAME_SUCCESS);
		
		logger.debug("玩家["+player.getPassportId()+"] 更改名称 ["+cgHumanChangeName.getName()+"]");
		Globals.getClubService().updateHumanInfoToClub(human);
		
		Globals.getSlotRoomService().change2(player);
		//record rename log
		Globals.getLogService().sendRenameLog(human, LogReasons.RenameLogReason.RENAME, LogReasons.RenameLogReason.RENAME.getReasonText(), beforeName, human.getName());
	}

	/**
	 * 更改性别
	 * @param player
	 * @param cgHumanChangeSex
	 */
	public void handleHumanChangeSex(Player player,
			CGHumanChangeSex cgHumanChangeSex) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if(human == null)
			return;
		if(cgHumanChangeSex.getSex()!=0 && cgHumanChangeSex.getSex()!=1){
			logger.warn("玩家["+player.getPassportId()+"] 改变性别错误["+cgHumanChangeSex.getSex()+"]");
			return;
		}
		if(human.getGirl()==cgHumanChangeSex.getSex() ){
			logger.warn("玩家["+player.getPassportId()+"] 改变相同性别错误["+cgHumanChangeSex.getSex()+"]");
			return;
		}
		
		human.setGirl(cgHumanChangeSex.getSex());
		human.setModified();
		Globals.getClubService().updateHumanInfoToClub(human);
		GCHumanChangeSex gcHumanChangeSex = new GCHumanChangeSex();
		gcHumanChangeSex.setSex(cgHumanChangeSex.getSex());
		human.sendMessage(gcHumanChangeSex);
		logger.debug("玩家["+player.getPassportId()+"] 更改性别 ["+cgHumanChangeSex.getSex()+"]");
	}
	
	/**
	 * 改变图像
	 * @param player
	 * @param cgHumanChangeImg
	 */
	public void handleHumanChangeImg(Player player,
			CGHumanChangeImg cgHumanChangeImg) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if(human == null)
			return;
		String img = cgHumanChangeImg.getImg();
		if(img.length() == 0){
			logger.warn("玩家["+player.getPassportId()+"] 改变图片错误["+img+"]");
			return;
		}
		
		
		human.setImg(img);
		UserInfo userInfo = Globals.getDaoService().getUserInfoDao().get(human.getPassportId());
		userInfo.setImg(img);
		Globals.getDaoService().getUserInfoDao().saveOrUpdate(userInfo);
		human.setModified();
		GCHumanChangeImg gcHumanChangeImg = new GCHumanChangeImg();
		gcHumanChangeImg.setImg(img);
		human.sendMessage(gcHumanChangeImg);
//		Globals.getSlotRoomService().change2(player);
//		Globals.getClubService().updateHumanInfoToClub(human);
		logger.debug("玩家["+player.getPassportId()+"] 更改图片 ["+img+"]");
	}

	public void handleHumanNewGuide(Player player, CGHumanNewGuide cgHumanNewGuide) {
		
		int guideID = cgHumanNewGuide.getGuideId();
		
		String newGuide = player.getHuman().getNewguide();
		
		if(!newGuide.equals("")){
			newGuide = newGuide+","+guideID;
		}else{
			newGuide = String.valueOf(guideID);
		}
		
		player.getHuman().setNewguide(newGuide);
		player.getHuman().setModified();
	}

	public void handleHumanVideoNum(Player player, CGHumanVideoNum cgHumanVideoNum) {
		Human human = player.getHuman();
		Date watchTime = human.getWatchTime();
		VideoRewardServer vs = Globals.getVideoRewardServer();
		//如果等于空 说明是第一次 给钱
		if(watchTime == null){
			human.setWatchTime(new Date());
			human.setWatchNum(1);
			int reward = vs.getReward(1);
			sendGold(player,reward);
		}else{
			String watchDate = TimeUtils.formatYMDTime(watchTime.getTime());
			String nowDate = TimeUtils.formatYMDTime(new Date().getTime());
			//如果不相等  说明 隔天了  重新开始 给钱
			if(!watchDate.equals(nowDate)){
				human.setWatchTime(new Date());
				human.setWatchNum(1);
				int reward = vs.getReward(1);
				sendGold(player,reward);
			//没有 隔天 看看 领了几次，超过 10 次 就不让领了
			}else{
				
				int watchNum = human.getWatchNum()+1;
				int reward = vs.getReward(watchNum);
				//说明 今天次数 还么有到  给钱
				if(reward > 0 ){
					sendGold(player,reward);
					human.setWatchNum(watchNum);
				}
			}
		}
		 
		human.setModified();
		player.sendMessage(new GCHumanVideoNum());
		 
	}

	private void sendGold(Player player,int reward){
		 if(reward != 0){
				//增加钱
				/*String detailReason = LogReasons.GoldLogReason.WATCHVIDEO.getReasonText();
				player.getHuman().giveMoney(reward, Currency.GOLD, false, LogReasons.GoldLogReason.WATCHVIDEO, detailReason, -1, 1);*/
				List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
				RandRewardData data = new RandRewardData();
				data.setRewardId(3);
				data.setRewardCount(reward);
				randRewardDataList.add(data);
				CommonLogic.getInstance().giveRandReward(player.getHuman(), randRewardDataList,LogReasons.GoldLogReason.WATCHVIDEO,LogReasons.DiamondLogReason.WATCHVIDEO,LogReasons.CharmLogReason.WATCHVIDEO,LogReasons.ItemLogReason.WATCHVIDEO, true);
				
			 }
	}
	
	public void handleChaneageCountries(Player player, CGChaneageCountries cgChaneageCountries) {
	
		Human human = player.getHuman();
		int ageNew = cgChaneageCountries.getAge();
		
		//TODO 后期国家验证
		String countries = cgChaneageCountries.getCountries();
		
		int agereturn = human.getAge();
		GCChaneageCountries message = new GCChaneageCountries();
		message.setAge(agereturn);
		
		if(0< ageNew && ageNew <= 100){
			human.setAge(ageNew);
			message.setAge(ageNew);
		}
		
		human.setCountries(countries);
		human.setModified();
		player.setCountries(countries);
		message.setCountries(countries);
		player.sendMessage(message);
		
		Globals.getClubService().updateHumanInfoToClub(human);
		
		Globals.getSlotRoomService().change2(player);
	}

	public void handleSlotRoomGift(Player player, CGSlotRoomGift cgSlotRoomGift) {
		logger.info("--gift:"+player.getPassportId()+"-----开始发送礼物----");
		Human human = player.getHuman();
		
		String roomId = human.getSlotRoomId();
		
		int giftID = cgSlotRoomGift.getGiftId();
		
		int sendType = cgSlotRoomGift.getSend_type();
		
		long otherPlayerId = cgSlotRoomGift.getRece_playerId();
		
		int slotId = human.getHumanSlotManager().getSlotId();
		
		InteractiveItemTemplate template = Globals.getItemService().getInteractiveItem(giftID);
		
		if(template == null){
			logger.error("角色ID 【"+player.getPassportId()+"】 发送的礼物ID 【"+giftID+"】不存在");
			return;
		}
		
		if(sendType == 0 && otherPlayerId == human.getPassportId()){
			human.sendSystemMessage(LangConstants.FRIEND_GIFT_NOT_SELF);
			return;
		}
		
		//需要钱
		int cost = template.getCostChips();
		//魅力值
		int charm = template.getChangeCharm();
		
		if(human.equals("")){
			logger.error("角色ID 【"+player.getPassportId()+"】  没有加入房间");
			return;
		}
		
		int buckleGold = cost;
		
		if(sendType == 1){
			int num = Globals.getSlotRoomService().getRoomNum(slotId, roomId);
			if(num == 0){
				logger.error("角色ID 【"+player.getPassportId()+"】  多人发送礼物错误");
				return;
			}
			buckleGold = (num-1)*cost;
		}
		
		if(otherPlayerId != 0){
			if(!human.hasEnoughMoney(cost, Currency.GOLD)){
				logger.error("角色ID 【"+player.getPassportId()+"】  多人发送礼物金币不足");
				human.sendSystemMessage(LangConstants.NO_ENOUGH_MONEY);
				return;
			}
		}
		
		GCSlotRoomGift message = new GCSlotRoomGift();
		message.setGiftId(giftID);
		message.setSend_playerId(human.getPassportId());
		message.setRece_playerId(cgSlotRoomGift.getRece_playerId());
		message.setSend_type(sendType);
		
		Globals.getSlotRoomService().sendRoomGift(player, message, slotId, roomId,charm);
		
		//扣钱 
		String detailReason = MessageFormat.format(LogReasons.GoldLogReason.BUY_INTERACTIVE_ITEM.getReasonText(),giftID ,1);
		//如果是  0 就是群体发送 不扣钱
		logger.info("--gift:"+player.getPassportId()+"------otherPlayerId:::"+otherPlayerId);
		if(otherPlayerId != 0){
			human.costMoney(buckleGold, Currency.GOLD, true, LogReasons.GoldLogReason.BUY_INTERACTIVE_ITEM, detailReason, giftID, 1);
		}else{
			
			human.giveMoney(1, Currency.GOLD, false, LogReasons.GoldLogReason.BUY_INTERACTIVE_ITEM, detailReason,-1, 1);
			human.costMoney(1, Currency.GOLD, true, LogReasons.GoldLogReason.BUY_INTERACTIVE_ITEM, detailReason, giftID, 1);
				
		}
		
		human.sendSystemMessage(LangConstants.GIFTS_TO_SEND_SUCCESS);
		
		Globals.getTaskNewService().spinSlot(human, ClientType.TASK102.getIndex(), RefreshType.RefreshType1.getIndex());
		
		/**
		 * 判断送礼物 是否免费 的字段 设置为false（只要发送一次礼物就 设置为false，每次都要设置）
		 */
		human.getHumanSlotManager().getHumanTemporaryData().setMegaSuperEpicWin(false);
	}

	/**
	 * 邀请好友
	 * @param player
	 * @param cgSlotRoomPlease
	 */
	public void handleSlotRoomPlease(Player player, CGSlotRoomPlease cgSlotRoomPlease) {
		
	      Human human = player.getHuman();
	      
	      String roomId = human.getSlotRoomId();
	      
	      int slotId = human.getHumanSlotManager().getSlotId();
	      
	      if(roomId.equals("") || slotId == 0){
	    	  return;
	      }
	      
	      long req_playerId = cgSlotRoomPlease.getReq_playerId();
	      
	      Map<Long, Long> map =  human.getHumanRelationManager().getFriendYaoqiTime();
	      
	      if(map.containsKey(req_playerId)){
	    	 long startTime = map.get(req_playerId);
	    	 if((System.currentTimeMillis() - startTime) < 10*1000){
	    		 human.sendSystemMessage(LangConstants.SLOT_ROOM_REQ1);
	 			return;
	    	 }
	      }
	      map.put(req_playerId, System.currentTimeMillis());
		
	      human.sendSystemMessage(LangConstants.SLOT_ROOM_REQ2);
	    
	      Player reqPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(req_playerId);
	      PlayerCacheInfo playerCacheInfo= Globals.getPlayerCacheService().getPlayerCacheById(player.getPassportId());
	      if(reqPlayer != null){
	    	  GCSlotRoomPlease message = new GCSlotRoomPlease();
	    	  message.setFriendImg(human.getImg());
	    	  message.setFriendName(human.getName());
	    	  message.setSlotId(slotId);
	    	  message.setRoomId(roomId);
	    	  message.setPlayerId(player.getPassportId());
	    	  message.setVipLevel(playerCacheInfo.getVipLevel());
	    	  reqPlayer.sendMessage(message);
	      }else{
	    	  PlayerCacheInfo cache = Globals.getPlayerCacheService().getPlayerCacheById(req_playerId);
	    	  if(cache != null){
	    		  RoomReq rr = new RoomReq();
	    		  rr.setFriendImg(human.getImg());
	    		  rr.setFriendName(human.getName());
	    		  rr.setRoomId(roomId);
	    		  rr.setSlotId(slotId);
	    		  rr.setPassId(req_playerId);
	    		  rr.setPlayerId(player.getPassportId());
	    		  Globals.getRedisService().sendRedisMsgToServer(cache.getServerId(), rr);
	    	  }
	      }
	      
	}

	public void handleRoomBigwinGift(Player player, CGRoomBigwinGift cgRoomBigwinGift) {
		
		Human human = player.getHuman();
		//true 有免费
	    boolean fly = human.getHumanSlotManager().isBigWin();
	    
	    if(fly){
	    	int giftID = cgRoomBigwinGift.getGiftId();
	 	    
	        InteractiveItemTemplate template = Globals.getItemService().getInteractiveItem(giftID);
	   		
            String roomId = human.getSlotRoomId();
			
			int slotId = human.getHumanSlotManager().getSlotId();
			
	   		if(template == null || roomId.equals("") || slotId == 0){
	   			logger.error("角色ID 【"+player.getPassportId()+"】 发送的礼物ID 【"+giftID+"】不存在");
	   			return;
	   		}
	   		
	   		human.getHumanSlotManager().setBigWin(false);
	   	    //魅力值
			int charm = template.getChangeCharm();
	   		
	   		GCSlotRoomGift message = new GCSlotRoomGift();
			
			message.setSend_playerId(human.getPassportId());
			message.setGiftId(giftID);
			message.setSend_type(1);
			message.setRece_playerId(human.getPassportId());
			
			Globals.getSlotRoomService().sendRoomGift(player, message, slotId, roomId,charm);
			
			human.sendSystemMessage(LangConstants.GIFTS_TO_SEND_SUCCESS);
			
			
	   		
	    }
	  
	   
	}

	public void handleHumanDetailInfoTodayView(Player player,
			CGHumanDetailInfoTodayView cgHumanDetailInfoTodayView) {
		player.getHuman().setTodayView(1);
		player.getHuman().setModified();
		
	}

	public void handleExpDouble(Player player, CGExpDouble cgExpDouble) {
		GCExpDouble GCExpDouble = new GCExpDouble();
		if(player.getHuman().getDoubleExpEndTime() != null){
			long leftTime = (player.getHuman().getDoubleExpEndTime().getTime()-Globals.getTimeService().now());
			if(leftTime <= 0){
				leftTime = 0;
			}else{
				leftTime = leftTime/1000;
			}
			GCExpDouble.setLeftTime(leftTime);
		}else{
			GCExpDouble.setLeftTime(0);
		}
		player.sendMessage(GCExpDouble);
		
	}

	

	
}
