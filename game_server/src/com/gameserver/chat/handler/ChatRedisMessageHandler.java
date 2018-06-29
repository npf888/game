package com.gameserver.chat.handler;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.baccart.BaccartRoom;
import com.gameserver.chat.enums.ChatChannelEnum;
import com.gameserver.chat.msg.GCChatMsg;
import com.gameserver.chat.redisMsg.ChatRedisMessage;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.texas.TexasRoom;


/**
 * redis
 * @author wayne
 *
 */
public class ChatRedisMessageHandler {

	private Logger logger = Loggers.redisLogger;
	
	public void handleChatRedisMessage(ChatRedisMessage chatRedisMessage) {
		// TODO Auto-generated method stub
		int channel = chatRedisMessage.getChannel();
		ChatChannelEnum chatChannelEnum = ChatChannelEnum.valueOf(channel);
		if(chatChannelEnum == null)
		{
			logger.warn("redis chat channel is ["+channel+"]");
			return;
		}
		String msgContent = chatRedisMessage.getContent();
		/** 聊天信息长度需要控制在允许范围内 */
		int _permitLen = Globals.getConfigTempl().getChatNumLimit();
		if (_permitLen < msgContent.length())
		{
			logger.warn("redis chat more than permit len");
			msgContent = msgContent.substring(0, _permitLen);
		}

		GCChatMsg gcChatMsg = new GCChatMsg();
		gcChatMsg.setChannel(channel);
		gcChatMsg.setContent(msgContent);
		gcChatMsg.setFromRoleImg(chatRedisMessage.getFromRoleImg());
		gcChatMsg.setFromRoleName(chatRedisMessage.getFromRoleName());
		gcChatMsg.setFromRoleUUID(chatRedisMessage.getFromRoleUUID());
		switch(chatChannelEnum){
		case PRIVATE:
		{
			//判断是否玩家在
			Player chatPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(chatRedisMessage.getDestRoleUUID());
			if(chatPlayer == null)
			{
				logger.warn("redis chat player ["+chatRedisMessage.getDestRoleUUID()+"] no exist");
				return;
			}
			chatPlayer.sendMessage(gcChatMsg);
		}
			break;
		case BACCARAT:{
			Player chatPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(chatRedisMessage.getFromRoleUUID());
			if(chatPlayer == null)
			{
				logger.warn("redis chat player ["+chatRedisMessage.getFromRoleUUID()+"] no exist");
				return;
			}
			BaccartRoom baccartRoom = chatPlayer.getHuman().getHumanBaccartManager().getBaccartRoom();
			if(baccartRoom == null)
			{
				logger.warn("redis chat player ["+chatRedisMessage.getFromRoleUUID()+"] no in room");
				return;
			}
			for(Player player :baccartRoom.getBaccartRoomPlayerManager().getPlayerList())
			{
				player.sendMessage(gcChatMsg);
			}
			
		}
			break;
		case ROOM:
		{
			Player chatPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(chatRedisMessage.getFromRoleUUID());
			if(chatPlayer == null)
			{
				logger.warn("redis chat player ["+chatRedisMessage.getFromRoleUUID()+"] no exist");
				return;
			}
			TexasRoom texasRoom = chatPlayer.getHuman().getHumanTexasManager().getTexasRoom();
			if(texasRoom == null)
			{
				logger.warn("redis chat player ["+chatRedisMessage.getFromRoleUUID()+"] no in room");
				return;
			}
			for(Player player :texasRoom.getPlayerManager().getRoomPlayerList())
			{
				player.sendMessage(gcChatMsg);
			}
			for(Player player :texasRoom.getPlayerManager().getWaitingPlayerList())
			{
				player.sendMessage(gcChatMsg);
			}
		}
			break;
		case WORLD:
		{
			//判断是否玩家在
			for(Player player: Globals.getOnlinePlayerService().getOnlinePlayersMap().values())
			{
				player.sendMessage(gcChatMsg);
			}

		}
			break;
		}
	}

}
