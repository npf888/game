package com.gameserver.relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.common.constants.Loggers;
import com.core.uuid.UUIDType;
import com.db.model.FriendEntity;
import com.db.model.FriendRequestEntity;
import com.db.model.HumanEntity;
import com.db.model.HumanFriendGiftEntity;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanRelationManager;
import com.gameserver.player.Player;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.player.data.PlayerInfoData;
import com.gameserver.relation.data.FriendDetailInfoData;
import com.gameserver.relation.data.FriendGiftInfoData;
import com.gameserver.relation.msg.GCAddFriend;
import com.gameserver.relation.msg.GCDeleteFriend;
import com.gameserver.relation.msg.GCFriendGiftSync;
import com.gameserver.relation.msg.GCLoadFriendGiftList;
import com.gameserver.relation.msg.GCLoadFriendList;
import com.gameserver.relation.msg.GCLoadFriendRequestList;
import com.gameserver.relation.msg.GCRequestFriendSync;
import com.gameserver.relation.redismsg.FriendAddRedisMessage;
import com.gameserver.relation.redismsg.FriendDeleteRedisMessage;
import com.gameserver.relation.redismsg.FriendGiftRedisMessage;
import com.gameserver.relation.redismsg.FriendRequestRedisMessage;

/**
 * 好友逻辑处理
 * @author wayne
 *
 */
public class RelationLogic {
	
	@SuppressWarnings("unused")
	private Logger logger = Loggers.relationLogger;
	
	private static RelationLogic instance = new RelationLogic();
	public static RelationLogic getInstance(){
		return instance;
	}
	
	/**
	 *好友信息
	 */
	public GCLoadFriendList buildFriendListMsg(Human human)
	{
		HumanRelationManager humanRelationManager =	human.getHumanRelationManager();
		GCLoadFriendList gcLoadFriendList = new GCLoadFriendList();
		
		int len = humanRelationManager.getFriendList().size();
		List<Long> playerIds = new ArrayList<Long>();
		for(int i=0;i<len;i++)
		{
			long tempFriendId = humanRelationManager.getFriendList().get(i).getFriendId();
			if(playerIds.contains(tempFriendId))
				continue;
			playerIds.add(tempFriendId);
		}
		
        Map<Long,PlayerCacheInfo> tempFriendMap=Globals.getPlayerCacheService().getPlayerCacheMapByIds(playerIds);
		 
	
		List<FriendDetailInfoData> friendDetailInfoDataList = new ArrayList<FriendDetailInfoData>();
		
		for(Friend tempFriend :humanRelationManager.getFriendList()){
			PlayerCacheInfo tempPlayerCacheInfo = tempFriendMap.get(tempFriend.getFriendId());
			if(tempPlayerCacheInfo == null)
				continue;
			FriendDetailInfoData  friendDetailInfoData = FriendDetailInfoData.convertFromFriendAndPlayCacheInfo(tempFriend, tempPlayerCacheInfo);
			friendDetailInfoDataList.add(friendDetailInfoData);
		
		}
		
		gcLoadFriendList.setFriendDetailInfoDatalist(friendDetailInfoDataList.toArray(new FriendDetailInfoData[friendDetailInfoDataList.size()] ));
		return gcLoadFriendList;
	}
	
	/**
	 * 加载待处理好友请求
	 * @return
	 */
	public GCLoadFriendRequestList buildFriendRequestListMsg(Human human) {
		// TODO Auto-generated method stub
		HumanRelationManager humanRelationManager =	human.getHumanRelationManager();
		GCLoadFriendRequestList gcLoadFriendRequestList = new GCLoadFriendRequestList();
	
		int len = humanRelationManager.getFriendRequestList().size();
		List<Long> playerIds = new ArrayList<Long>();
		for(int i=0;i<len;i++)
		{
			long tempFriendId = humanRelationManager.getFriendRequestList().get(i).getSendId();
			if(playerIds.contains(tempFriendId))
				continue;
			playerIds.add(tempFriendId);
		}
		
		 Map<Long,PlayerCacheInfo> tempFriendMap=Globals.getPlayerCacheService().getPlayerCacheMapByIds(playerIds);
		 
		
		List<PlayerInfoData> playerInfoDataList = new ArrayList<PlayerInfoData>();
		for(FriendRequest tempFriendRequest:humanRelationManager.getFriendRequestList()){
			PlayerCacheInfo tempPlayerCacheInfo = tempFriendMap.get(tempFriendRequest.getSendId());
			if(tempPlayerCacheInfo == null)
				continue;
			PlayerInfoData playerInfoData = PlayerInfoData.convertFrom(tempPlayerCacheInfo);
			playerInfoDataList.add(playerInfoData);
		}
		
		gcLoadFriendRequestList.setFriendRequestInfoDataList(playerInfoDataList.toArray(new PlayerInfoData[playerInfoDataList.size()]));

		return gcLoadFriendRequestList;
	}
	

	/**
	 *好友礼物信息
	 */
	public GCLoadFriendGiftList buildFriendGiftListMsg(Human human)
	{
		HumanRelationManager humanRelationManager =	human.getHumanRelationManager();
		GCLoadFriendGiftList gcLoadFriendGiftList = new GCLoadFriendGiftList();
		
		int len = humanRelationManager.getFriendGiftList().size();
		List<Long> playerIds = new ArrayList<Long>();
		for(int i=0;i<len;i++)
		{
			long tempFriendId = humanRelationManager.getFriendGiftList().get(i).getFriendId();
			if(playerIds.contains(tempFriendId))
				continue;
			playerIds.add(tempFriendId);
		}
		
		 Map<Long,PlayerCacheInfo> tempFriendMap=Globals.getPlayerCacheService().getPlayerCacheMapByIds(playerIds);
		 
		
		 List<FriendGiftInfoData> friendGiftInfoDataList = new ArrayList<FriendGiftInfoData>();
		for(FriendGift friendGift:humanRelationManager.getFriendGiftList()){
			PlayerCacheInfo tempPlayerCacheInfo = tempFriendMap.get(friendGift.getFriendId());
			if(tempPlayerCacheInfo==null)
				continue;
			FriendGiftInfoData friendGiftInfoData = FriendGiftInfoData.convertFromFriendGiftAndPlayerCache(friendGift,tempPlayerCacheInfo);
			friendGiftInfoDataList.add(friendGiftInfoData);
		}
		
		gcLoadFriendGiftList.setFriendGiftInfoDataList(friendGiftInfoDataList.toArray(new FriendGiftInfoData[friendGiftInfoDataList.size()]));;
		return gcLoadFriendGiftList;
	}
	

	/**
	 * 发送好友请求
	 */
	public boolean sendRequestFriend(Human human,PlayerCacheInfo playerCacheInfo)
	{
		long now =Globals.getTimeService().now();
		FriendRequest friendRequest = new FriendRequest(human);
		friendRequest.setCharId(playerCacheInfo.getPlayerId());
		long friendRequestEntityId = Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANFRIENDREQUESTID);
		friendRequest.setDbId(friendRequestEntityId);
		friendRequest.setSendId(human.getPassportId());
		friendRequest.setUpdateTime(now);
		friendRequest.setCreateTime(now);
		friendRequest.setInDb(false);
		//给发送请求的用户记录下，所有发送申请的 好友的ID
		addRequestFriendIds(human,playerCacheInfo.getPlayerId());
		Player friendPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(playerCacheInfo.getPlayerId());
		if(friendPlayer!=null){
			
			FriendRequest tempFriendRequest = friendPlayer.getHuman().getHumanRelationManager().friendRequestByFriendId(human.getPassportId());
			if(tempFriendRequest != null)
				return false;
			addFriendRequest(friendPlayer.getHuman(),friendRequest);
			FriendRequestEntity friendRequestEntity = friendRequest.toEntity();
			Globals.getDaoService().getHumanFriendRequestDao().save(friendRequestEntity);
			return true;
		}
		
		FriendRequestEntity tempFriendRequest  = Globals.getDaoService().getHumanFriendRequestDao().getFriendRequestByCharIdAndSendId(playerCacheInfo.getPlayerId(), human.getPassportId());
		if(tempFriendRequest!=null)
			return false;
		FriendRequestEntity friendRequestEntity = friendRequest.toEntity();
		//可以优化 改成异步
		Globals.getDaoService().getHumanFriendRequestDao().save(friendRequestEntity);
	
		//同步线上数据
		FriendRequestRedisMessage friendRequestRedisMessage = new FriendRequestRedisMessage();
		friendRequestRedisMessage.setFriendRequestEntity(friendRequestEntity);
		Globals.getRedisService().sendRedisMsgToServer(playerCacheInfo.getServerId(), friendRequestRedisMessage);
		return true;
	}
	
	public void addRequestFriendIds(Human human,long id){
		String existIds = human.getRequestFriendIds();
		if(StringUtils.isBlank(existIds)){
			existIds=id+",";
		}else{
			existIds+=id+",";
		}
		human.setRequestFriendIds(existIds);
		human.setModified();
	}

	/**
	 * 添加好友请求
	 */
	public void addFriendRequest(Human human,FriendRequest friendRequest)
	{
		human.getHumanRelationManager().addFriendRequest(friendRequest);
		PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(friendRequest.getSendId());
		//通知用户
		if(playerCacheInfo == null){
			return;
		}
		
		GCRequestFriendSync gcRequestFriendSync = new GCRequestFriendSync();
		gcRequestFriendSync.setFriendRequestInfoData(PlayerInfoData.convertFrom(playerCacheInfo));
		human.sendMessage(gcRequestFriendSync);
		
	}
	
	/*** * 添加好友
	 */
	public void applyFriend(Human human,PlayerCacheInfo playerCacheInfo,boolean thirdParty)
	{
		long roleId = playerCacheInfo.getPlayerId();
		//自己数据
		List<FriendEntity> friendList= new ArrayList<FriendEntity>();
		long now =Globals.getTimeService().now();
		FriendEntity friendEntity = new FriendEntity();
		friendEntity.setCharId(human.getPassportId());
		long friendRequestEntityId = Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANFRIENDID);
		friendEntity.setId(friendRequestEntityId);
		friendEntity.setFriendId(roleId);
		friendEntity.setAgree(1);
		
		setGiftTime(friendEntity,roleId,human.getPassportId());
		
		friendEntity.setUpdateTime(now);
		friendEntity.setCreateTime(now);
		if(thirdParty){
			friendEntity.setFacebook(1);
		}
		friendList.add(friendEntity);
		
		//朋友数据
		FriendEntity friendEntity2 = new FriendEntity();
		friendEntity2.setCharId(roleId);
		long friendRequestEntityId2 = Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANFRIENDID);
		friendEntity2.setId(friendRequestEntityId2);
		friendEntity2.setFriendId(human.getPassportId());
		friendEntity2.setAgree(0);
		//好友玩家
		Player playerFriend = setGiftTime(friendEntity2,human.getPassportId(),roleId);
		
		friendEntity2.setUpdateTime(now);
		friendEntity2.setCreateTime(now);
		if(thirdParty){
			friendEntity2.setFacebook(1);
		}
		friendList.add(friendEntity2);
		
		//可以优化 改成异步
		Globals.getDaoService().getHumanFriendDao().saveAll(friendList);
		Friend friend = new Friend();
		friend.fromEntity(friendEntity);
		friend.setOwner(human);
		addFriend(human,friend);
		/*human.getHumanAchievementManager().updateFriend();*/
		
		if(playerFriend !=null)
		{
			Friend friend2 = new Friend();
			friend2.fromEntity(friendEntity2);
			friend2.setOwner(playerFriend.getHuman());
			addFriend(playerFriend.getHuman(),friend2);
			return;
		}
		
		//同步线上数据
		FriendAddRedisMessage friendAddRedisMessage = new FriendAddRedisMessage();
		friendAddRedisMessage.setFriendEntity(friendEntity2);
		Globals.getRedisService().sendRedisMsgToServer(playerCacheInfo.getServerId(), friendAddRedisMessage);
		
	}
	
	private Player setGiftTime(FriendEntity friendEntity,long passporId,long roleId){
		Player playerFriend = Globals.getOnlinePlayerService().getPlayerByPassportId(roleId);
		if(playerFriend != null){
			 Map<Long, String> map = playerFriend.getHuman().getAddfriendIds();
			 if(map.containsKey(passporId)){
				 friendEntity.setGiftTime(Long.valueOf(map.get(passporId)));
			 }
		}else{
			List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(roleId);
			if(humanEntityList != null && humanEntityList.get(0) != null){
				HumanEntity en = humanEntityList.get(0);
				 Map<Long, String> map = JSON.parseObject(en.getAddfriendIds(),new TypeReference<HashMap<Long,String>>() {});
				 if(map.containsKey(passporId)){
					 friendEntity.setGiftTime(Long.valueOf(map.get(passporId)));
				 }
			}
		}
		return playerFriend;
	}

	/**
	 * 添加好友
	 * @param human
	 * @param friend
	 */
	public void addFriend(Human human, Friend friend) {
		// TODO Auto-generated method stub
		GCAddFriend gcAddFriend = new GCAddFriend();
		PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(friend.getFriendId());
		if(playerCacheInfo==null)
			return;
		human.getHumanRelationManager().getFriendList().add(friend);
		
		gcAddFriend.setFriendDetailInfoData(FriendDetailInfoData.convertFromFriendAndPlayCacheInfo(friend,playerCacheInfo));
		human.sendMessage(gcAddFriend);
	}
	
	
	/**
	 * 删除好友
	 * @param human
	 * @param friend
	 */
	public void deleteFriend(Human human,Friend friend, PlayerCacheInfo playerCacheInfo) {
		// TODO Auto-generated method stub
		//查询数据
		FriendEntity friendEntity = Globals.getDaoService().getHumanFriendDao().getFriendByCharIdFriendId(human.getPassportId(), friend.getFriendId());
		FriendEntity friendEntity2 = Globals.getDaoService().getHumanFriendDao().getFriendByCharIdFriendId(friend.getFriendId(), human.getPassportId());
		if(friendEntity!=null)
		{
			Globals.getDaoService().getHumanFriendDao().delete(friendEntity);
		}
		
		if(friendEntity2!=null)
		{
			Globals.getDaoService().getHumanFriendDao().delete(friendEntity2);
		}
		
		human.getHumanRelationManager().getFriendList().remove(friend);
		
		//删除数据
		//friend.onDelete();
		
		//同步线上数据
		FriendDeleteRedisMessage friendDeleteRedisMessage = new FriendDeleteRedisMessage();
		friendDeleteRedisMessage.setPlayerId(playerCacheInfo.getPlayerId());
		friendDeleteRedisMessage.setFriendId(human.getPassportId());
		Globals.getRedisService().sendRedisMsgToServer(playerCacheInfo.getServerId(), friendDeleteRedisMessage);
		
	}

	/**
	 * 同步删除好友
	 * @param human
	 * @param friend
	 */
	public void deleteFriend(Human human, Friend friend) {
		human.getHumanRelationManager().getFriendList().remove(friend);
		
		// TODO Auto-generated method stub
		GCDeleteFriend gcDeleteFriend = new GCDeleteFriend();
		gcDeleteFriend.setFriendId(friend.getFriendId());
		human.sendMessage(gcDeleteFriend);
	}

	
	/**
	 * 发送好友礼物
	 */
	public void sendFriendGift(Human human,PlayerCacheInfo playerCacheInfo)
	{
		
		HumanFriendGiftEntity humanFriendGiftEntity=createFriendGift(playerCacheInfo.getPlayerId(),human.getPassportId());
		
		Globals.getDaoService().getHumanFriendGiftDao().save(humanFriendGiftEntity);
		

		//同步线上数据
		FriendGiftRedisMessage friendGiftRedisMessage = new FriendGiftRedisMessage();
		
		friendGiftRedisMessage.setFriendGiftEntity(humanFriendGiftEntity);
		
		Globals.getRedisService().sendRedisMsgToServer(playerCacheInfo.getServerId(), friendGiftRedisMessage);
		
	}
	
	/**
	 * 接收好友礼物
	 * @param human
	 * @param friendGift
	 */
	public void addFriendGift(Human human, FriendGift friendGift) {
		// TODO Auto-generated method stub
		GCFriendGiftSync gcFriendGiftSync = new GCFriendGiftSync();
		PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(friendGift.getCharId());
		if(playerCacheInfo==null)
			return;
		human.getHumanRelationManager().getFriendGiftList().add(friendGift);
		gcFriendGiftSync.setFriendGiftInfoData(FriendGiftInfoData.convertFromFriendGiftAndPlayerCache(friendGift, playerCacheInfo));
		human.sendMessage(gcFriendGiftSync);
	}
	
	/**
	 * 创建好友礼物
	 * @param selfId
	 * @param friendId
	 * @return
	 */
	public HumanFriendGiftEntity createFriendGift(long selfId,long friendId){
		long now = Globals.getTimeService().now();
		long friendRequestEntityId = Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANFRIENDGIFTID);
		HumanFriendGiftEntity humanFriendGiftEntity = new HumanFriendGiftEntity();
		humanFriendGiftEntity.setCharId(selfId);
		humanFriendGiftEntity.setFriendId(friendId);
		humanFriendGiftEntity.setGetTime(0);
		humanFriendGiftEntity.setUpdateTime(now);
		humanFriendGiftEntity.setCreateTime(now);
		humanFriendGiftEntity.setId(friendRequestEntityId);
		return humanFriendGiftEntity;
	}

	
}
