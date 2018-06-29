package com.gameserver.chat.handler;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.ModuleMessageHandler;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.gameserver.bazoo.service.room.Room;
import com.gameserver.chat.enums.ChatChannelEnum;
import com.gameserver.chat.msg.CGChatMsg;
import com.gameserver.chat.msg.GCChatMsg;
import com.gameserver.common.Globals;
import com.gameserver.fw.ClubCache;
import com.gameserver.human.Human;
import com.gameserver.player.Player;


/**
 * 聊天消息处理器
 * @author Thinker
 */
public class ChatMessageHandler implements ModuleMessageHandler
{
	private Logger logger = Loggers.chatLogger;
	

	public ChatMessageHandler()
	{
	}

//	/**
//	 * 处理聊天消息
//	 * 
//	 * @param player
//	 * @param msg
//	 */
//	public void handleChatMsg(Player player, CGChatMsg msg)
//	{
//		Human human = player.getHuman();
//		
//		int channel = msg.getChannel();
//		ChatChannelEnum chatChannelEnum = ChatChannelEnum.valueOf(channel);
//		if(chatChannelEnum == null)
//		{
//			logger.warn("player["+player.getPassportId() +"]chat channel error channel["+channel+"]");
//			return;
//		}
//		
//		String msgContent = msg.getContent();
//		if (null == msgContent|| (msgContent = msgContent.trim()).length() == 0)
//		{
//			logger.warn("player["+player.getPassportId() +"]chat content is null");
//			return;
//		}
//		
//	
//		/* 过滤聊天中的不良信息 */
//		msgContent = Globals.getWordFilterService().filterHtmlTag(msgContent);
//		boolean hasDirtyWords = Globals.getWordFilterService().containKeywords(msgContent);
//		
//		LogReasons.ChatLogReason reason=null;
//		if (hasDirtyWords)
//		{
//			// 记录玩家发表不良信息的日志
//			
//			reason=LogReasons.ChatLogReason.REASON_CHAT_DIRTY_WORD;
//			msgContent = Globals.getWordFilterService().filter(msgContent);
//			
//		}else{
//			//记录玩家普通聊天的日志
//			reason=LogReasons.ChatLogReason.REASON_CHAT_COMMON;
//		}
//		
//	
//		if (msgContent.isEmpty()) 
//		{
//			logger.warn("player["+player.getPassportId() +"]chat content is null");
//			return;
//		}
//		
//		//判断时间
//		long now = Globals.getTimeService().now();
//
//		msg.setContent(msgContent);
//		
//		ChatRedisMessage chatRedisMessage = new ChatRedisMessage();
//		chatRedisMessage.setChannel(msg.getChannel());
//		chatRedisMessage.setContent(msgContent);
//		chatRedisMessage.setFromRoleImg(human.getImg());
//		chatRedisMessage.setDestRoleUUID(msg.getDestRoleUUID());
//		chatRedisMessage.setFromRoleName(human.getName());
//		chatRedisMessage.setFromRoleUUID(player.getPassportId());
//		
//		if(chatChannelEnum == ChatChannelEnum.PRIVATE)
//		{
//			PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(msg.getDestRoleUUID());
//			if(playerCacheInfo == null)
//			{
//				logger.warn("player["+player.getPassportId() +"]no exist");
//				
//				return;
//			}
//			chatRedisMessage.setDestRoleName(playerCacheInfo.getName());
//	
//			GCChatMsg gcChatMsg = new GCChatMsg();
//			gcChatMsg.setContent(msgContent);
//			gcChatMsg.setChannel(channel);
//			gcChatMsg.setFromRoleName(human.getName());
//			gcChatMsg.setFromRoleUUID(player.getPassportId());
//			player.sendMessage(gcChatMsg);
//			Globals.getLogService().sendChatLog(player.getHuman(),reason,reason.getReasonText(),channel,playerCacheInfo.getName(), msgContent);
//		}
//		else
//		{
//			chatRedisMessage.setDestRoleName("");
//		
//			if(chatChannelEnum == ChatChannelEnum.WORLD)
//			{
//				long lastChat = player.getLastChatTime(msg.getChannel());
//				
//				if(now-lastChat<Globals.getConfigTempl().getChatIntervalTime()*TimeUtils.SECOND)
//				{
//					player.sendSystemMessage(LangConstants.CHAT_TOO_OFTEN);
//					return;
//				}
//				player.setLastChatTime(msg.getChannel(), Globals.getTimeService().now());
//			}
//			Globals.getLogService().sendChatLog(player.getHuman(),reason,reason.getReasonText(),channel,"", msgContent);
//
//		}
//		Globals.getRedisService().broadcastRedisMsg(chatRedisMessage);
//		Globals.getChatService().onChat(human);
//		
//		Globals.getTaskNewService().spinSlot(human, ClientType.TASK102.getIndex(), RefreshType.RefreshType1.getIndex());
//	}

	/**
	 * 处理聊天消息
	 * 
	 * @param player
	 * @param msg
	 */
	public void handleChatMsg(Player player, CGChatMsg msg)
	{
		Human human = player.getHuman();
		
		int channel = msg.getChannel();
		ChatChannelEnum chatChannelEnum = ChatChannelEnum.valueOf(channel);
		if(chatChannelEnum == null)
		{
			logger.warn("player["+player.getPassportId() +"]chat channel error channel["+channel+"]");
			return;
		}
		
		String msgContent = msg.getContent();
		if (null == msgContent|| (msgContent = msgContent.trim()).length() == 0)
		{
			logger.warn("player["+player.getPassportId() +"]chat content is null");
			return;
		}
		
		//判断时间
		long now = Globals.getTimeService().now();
		long lastChat = player.getLastChatTime(msg.getChannel());
		if(now-lastChat<Globals.getConfigTempl().getChatIntervalTime()*TimeUtils.SECOND)
		{
			player.sendSystemMessage(LangConstants.CHAT_TOO_OFTEN);
			return;
		}
		player.setLastChatTime(msg.getChannel(), Globals.getTimeService().now());
		
	
		/* 过滤聊天中的不良信息 */
//		msgContent = Globals.getWordFilterService().filterHtmlTag(msgContent);
		boolean hasDirtyWords = Globals.getWordFilterService().containKeywords(msgContent);
		
		LogReasons.ChatLogReason reason=null;
		if (hasDirtyWords)
		{
			// 记录玩家发表不良信息的日志
			
			reason=LogReasons.ChatLogReason.REASON_CHAT_DIRTY_WORD;
			msgContent = Globals.getWordFilterService().filter(msgContent);
			
		}else{
			//记录玩家普通聊天的日志
			reason=LogReasons.ChatLogReason.REASON_CHAT_COMMON;
		}
		
	
		if (msgContent.isEmpty()) 
		{
			logger.warn("player["+player.getPassportId() +"]chat content is null");
			return;
		}
		


		msg.setContent(msgContent);
		
//		ChatRedisMessage chatRedisMessage = new ChatRedisMessage();
//		chatRedisMessage.setChannel(msg.getChannel());
//		chatRedisMessage.setContent(msgContent);
//		chatRedisMessage.setFromRoleImg(human.getImg());
//		chatRedisMessage.setDestRoleUUID(msg.getDestRoleUUID());
//		chatRedisMessage.setFromRoleName(human.getName());
//		chatRedisMessage.setFromRoleUUID(player.getPassportId());
		
		GCChatMsg gcChatMsg = new GCChatMsg();
		gcChatMsg.setChannel(channel);
		gcChatMsg.setContent(msgContent);
		gcChatMsg.setFromRoleImg(player.getImg());
		gcChatMsg.setFromRoleName(human.getName());
		gcChatMsg.setFromRoleUUID(player.getPassportId());
		gcChatMsg.setLv((int)human.getLevel());
		gcChatMsg.setNational(player.getCountries());
		gcChatMsg.setRoomNumber(msg.getRoomNumber());
		gcChatMsg.setMsgType(msg.getMsgType());
//		int rank = Globals.getRankNewServer().getHumanRankbyId(human.getPassportId());
//		gcChatMsg.setRank(rank);
		gcChatMsg.setRank(0);
		gcChatMsg.setSex(human.getGirl());
//		gcChatMsg.setViplv((int)human.getHumanVipNewManager().getVipLv());
		gcChatMsg.setViplv(0);
		List<Player> toList = new ArrayList<>();
		switch(chatChannelEnum)
		{
		case PRIVATE:
		{
//			PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(msg.getDestRoleUUID());
//			if(playerCacheInfo == null)
//			{
//				logger.warn("player["+player.getPassportId() +"]no exist");
//				Globals.getLogService().sendChatLog(player.getHuman(),reason,reason.getReasonText(),channel,"", msgContent);
//				return;
//			}
//			chatRedisMessage.setDestRoleName(playerCacheInfo.getName());
			toList.add(player);
			Player to = Globals.getOnlinePlayerService().getPlayerByPassportId(msg.getDestRoleUUID());
			if(to!=null)
			toList.add(to);
			break;
		}
		case WORLD:
		{
			for(Player p : Globals.getOnlinePlayerService().getOnlinePlayers())
			{
				if(p!=null)
				{
					toList.add(p);
				}
			}
			break;
		}
		case SPEAKER: 
		{
			
			
			
			/*HumanBagManager bagManager = human.getHumanBagManager();
			int itemId = Globals.getConfigTempl().getSpeakerItemId();
			int tempCount = bagManager.getCount(itemId);
			if (tempCount < 1) {
				logger.error("passportId: " + human.getPassportId() + " 没有喇叭");
				human.sendSystemMessage(LangConstants.NO_ITEM_SPEAKER);
				return;
			}*/
			
			
			//扣钱 判断用户钱够不够 （不够就返回 不让发送消息了）
			boolean ok = Globals.getBazooPubService().speakCostGold(player,msg.getMsgType());
			if(!ok){
				logger.error("passportId: " + human.getPassportId() + " 金币不足 不能发送 喇叭");
				human.sendSystemMessage(LangConstants.GOLD_NOT_ENOUGH);
				return;
			}
			
			logger.error("passportId: " + human.getPassportId() + " 开始发送 喇叭");
			/*
			bagManager.removeItem(itemId, 1);
			String itemUseDetailReason = MessageFormat.format(LogReasons.ItemLogReason.ITEM_USE.getReasonText(), itemId);
			Globals.getLogService().sendItemLog(human, LogReasons.ItemLogReason.ITEM_USE, itemUseDetailReason, itemId, 1, tempCount - 1);
			player.sendMessage(bagManager.buildHumanBagInfoData());*/
			for (Player p : Globals.getOnlinePlayerService().getOnlinePlayers()) {
				if (p != null) {
					toList.add(p);
				}
			}
			String content ="[FFA400]"+player.getHuman().getName()+":[-][FDFF60]"+msgContent+"[-]";
			Globals.getNoticeService().broadcast(content);
			
			break;
		}
		case CLUB:
		{
			String clubId = player.getHuman().getClubId();
			if(StringUtils.isNotEmpty(clubId))
			{
				Set<Long> ids = ClubCache.getClubMembersOfClub(clubId).keySet();
				if(ids!=null)
				{
					for(Long id : ids)
					{
						Player p = Globals.getOnlinePlayerService().getPlayerByPassportId(id);
						if(p!=null)
						{
							toList.add(p);
						}
					}
				}
			}
			break;
		}
		case ROOM:
		{
			/*String ids = player.getHuman().getSlotRoomId();
			if(StringUtils.isNotEmpty(ids))
			{
				String[] sss = Globals.getSlotRoomService().getRoomMemeber(player);
				for(String s : sss)
				{
					long id = Long.parseLong(s);
					Player p = Globals.getOnlinePlayerService().getPlayerByPassportId(id);
					if(p!=null)
					{
						toList.add(p);
					}
				}
			}*/
			Room room = Globals.getBazooPubService().getRoomService().getRoom(player);
			List<Player> players = room.getPlayers();
			for(Player p:players){
				toList.add(p);
			}
			break;
		}
		case BACCARAT:{break;}
			
		}
		if(toList.size()==0)
		{
			toList.add(player);
		}
		for(Player p : toList)
		{
			p.sendMessage(gcChatMsg);
		}
//		Globals.getTaskNewService().spinSlot(human, ClientType.TASK102.getIndex(), RefreshType.RefreshType1.getIndex());
	}
}
