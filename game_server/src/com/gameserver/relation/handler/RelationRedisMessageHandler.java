package com.gameserver.relation.handler;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.util.Assert;
import com.db.model.HumanFriendGiftEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanRelationManager;
import com.gameserver.player.Player;
import com.gameserver.relation.Friend;
import com.gameserver.relation.FriendGift;
import com.gameserver.relation.FriendRequest;
import com.gameserver.relation.RelationLogic;
import com.gameserver.relation.redismsg.FriendAddRedisMessage;
import com.gameserver.relation.redismsg.FriendDeleteRedisMessage;
import com.gameserver.relation.redismsg.FriendGiftRedisMessage;
import com.gameserver.relation.redismsg.FriendRequestRedisMessage;

/**
 * 内部服务器消息
 * @author wayne
 *
 */
public class RelationRedisMessageHandler {

	private Logger logger = Loggers.relationRedisLogger;
	/**
	 * 好友请求通知
	 * @param player
	 * @param FriendRequestRedisMessage
	 */
	public void handleFriendRequestRedisMessage(FriendRequestRedisMessage friendRequestRedisMessage) {
		// AAA:2017 null
		long playerId = friendRequestRedisMessage.getFriendRequestEntity().getCharId();
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(playerId);
		if(player==null)
			return;
		
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		
		HumanRelationManager humanRelationManager = human.getHumanRelationManager();
		
		FriendRequest friendRequest=new FriendRequest(human);
	
		friendRequest.fromEntity( friendRequestRedisMessage.getFriendRequestEntity());
		
		//检查是否已经加过这个用户
		if(humanRelationManager.checkIfAddFriend(friendRequest.getSendId()))
		{
			logger.warn("玩家["+playerId+"] 已经添加过 好友玩家["+friendRequest.getSendId()+"]");
			return;
		}
		
		RelationLogic.getInstance().addFriendRequest(human,friendRequest);
	
	}
	
	/**
	 * 添加好友消息
	 * @param friendAddRedisMessage
	 */
	public void handleFriendAddRedisMessage(FriendAddRedisMessage friendAddRedisMessage) {
		// TODO Auto-generated method stub
		long playerId = friendAddRedisMessage.getFriendEntity().getCharId();
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(playerId);
		if(player==null)
			return;
		
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		
		HumanRelationManager humanRelationManager = human.getHumanRelationManager();
		
		Friend friend=new Friend();
		friend.setOwner(human);
		friend.fromEntity( friendAddRedisMessage.getFriendEntity());
		
		//检查是否已经加过这个用户
		if(humanRelationManager.checkIfAddFriend(friend.getFriendId()))
		{
			logger.warn("玩家["+playerId+"] 已经添加过 好友玩家["+friend.getFriendId()+"]");
			return;
		}
		
		RelationLogic.getInstance().addFriend(human,friend);
	}

	/**
	 * 删除好友
	 * @param friendDeleteRedisMessage
	 */
	public void handleFriendDeleteRedisMessage(
			FriendDeleteRedisMessage friendDeleteRedisMessage) {
		// TODO Auto-generated method stub
		long playerId = friendDeleteRedisMessage.getPlayerId();
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(playerId);
		if(player==null)
			return;
		long friendId = friendDeleteRedisMessage.getFriendId();
		
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		
		HumanRelationManager humanRelationManager = human.getHumanRelationManager();
		

		//检查是否已经加过这个用户
		Friend friend = humanRelationManager.friendById(friendId);
		if(friend==null)
		{
			logger.warn("玩家["+playerId+"] 没有添加过 好友玩家["+playerId+"]");
			return;
		}
		
		RelationLogic.getInstance().deleteFriend(human,friend);
	}

	public void handleFriendGiftRedisMessage(
			FriendGiftRedisMessage friendGiftRedisMessage) {
		// TODO Auto-generated method stub
	
		HumanFriendGiftEntity tempFriendGiftEntity= friendGiftRedisMessage.getFriendGiftEntity();
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(tempFriendGiftEntity.getCharId());
		if(player==null)
			return;
	
		Human human = player.getHuman();
		FriendGift friendGift=new FriendGift();
		friendGift.setOwner(player.getHuman());
		friendGift.fromEntity( tempFriendGiftEntity);
		logger.debug("玩家["+human.getPassportId()+"]接收好友["+tempFriendGiftEntity.getFriendId()+"]礼物");
		RelationLogic.getInstance().addFriendGift(human,friendGift);
	}
}
