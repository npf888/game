package com.gameserver.chat;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.gameserver.chat.interfaces.IChatListener;
import com.gameserver.chat.msg.GCChatMsg;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;

/**
 * 处理聊天
 * @author Thinker
 */
public class ChatService implements InitializeRequired ,AfterInitializeRequired,DestroyRequired
{
	private Logger logger = Loggers.chatLogger;
	
	private List<IChatListener> chatListnerList = new ArrayList<IChatListener>();
	
	public ChatService() 
	{
	}

	@Override
	public void init() 
	{
		logger.info("chat service init");
	}
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		logger.info("chat service after init");
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		logger.info("chat service destroy");
	}
	
	//广播消息
	public void broadcastChatMsg(GCChatMsg gcChatMsg){
		for(Player player : Globals.getOnlinePlayerService().getOnlinePlayersMap().values()){
			player.sendMessage(gcChatMsg);
		}
	}
	
	public void addChatListner(IChatListener chatListener){
		chatListnerList.add(chatListener);
	}
	
	public void removeChatListner(IChatListener chatListener){
		chatListnerList.remove(chatListener);
	}
	
	public void onChat(Human human){
		for(IChatListener temp : chatListnerList){
			temp.onChat(human);
		}
	}

}
