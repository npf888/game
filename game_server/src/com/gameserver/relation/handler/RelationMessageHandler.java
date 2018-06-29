package com.gameserver.relation.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.LogReasons.CharmLogReason;
import com.common.LogReasons.DiamondLogReason;
import com.common.LogReasons.ItemLogReason;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.util.ArrayUtils;
import com.core.util.Assert;
import com.core.util.TimeUtils;
import com.game.webserver.facebook.FacebookFriendParam;
import com.game.webserver.facebook.FacebookFriendRes;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanMiscManager;
import com.gameserver.human.manager.HumanRelationManager;
import com.gameserver.mail.MailLogic;
import com.gameserver.player.Player;
import com.gameserver.player.cache.PlayerCacheInfo;
import com.gameserver.recharge.async.AsyncHttpOperation;
import com.gameserver.recharge.async.AsyncHttpOperation.IAsyncHttpCallBack;
import com.gameserver.relation.Friend;
import com.gameserver.relation.FriendGift;
import com.gameserver.relation.FriendRequest;
import com.gameserver.relation.RelationLogic;
import com.gameserver.relation.data.FriendAlreadyGiftListData;
import com.gameserver.relation.data.FriendDetailInfoData;
import com.gameserver.relation.data.StrangerData;
import com.gameserver.relation.msg.CGApplyFriend;
import com.gameserver.relation.msg.CGDeleteFriend;
import com.gameserver.relation.msg.CGEnterFriendsRoom;
import com.gameserver.relation.msg.CGFacebookFriendsSync;
import com.gameserver.relation.msg.CGFacebookNum;
import com.gameserver.relation.msg.CGFriendGift;
import com.gameserver.relation.msg.CGFriendGiftGet;
import com.gameserver.relation.msg.CGLoadFriendGiftList;
import com.gameserver.relation.msg.CGLoadFriendList;
import com.gameserver.relation.msg.CGLoadFriendRequestList;
import com.gameserver.relation.msg.CGRequestFriend;
import com.gameserver.relation.msg.CGStrangerList;
import com.gameserver.relation.msg.GCApplyFriend;
import com.gameserver.relation.msg.GCDeleteFriend;
import com.gameserver.relation.msg.GCFacebookFriendsSync;
import com.gameserver.relation.msg.GCFriendGift;
import com.gameserver.relation.msg.GCFriendGiftGet;
import com.gameserver.relation.msg.GCLoadFriendGiftList;
import com.gameserver.relation.msg.GCLoadFriendList;
import com.gameserver.relation.msg.GCLoadFriendRequestList;
import com.gameserver.relation.msg.GCRequestFriend;
import com.gameserver.relation.msg.GCStrangerList;
import com.gameserver.relation.redismsg.FriendSlotIdRoomId;
import com.gameserver.slot.handler.SlotHandlerFactory;
import com.gameserver.slot.msg.CGSlotEnterRoom;
import com.gameserver.task.enums.ClientType;
import com.gameserver.task.enums.RefreshType;

/**
 * 好友处理器
 * @author wayne
 *
 */
public class RelationMessageHandler implements IAsyncHttpCallBack<FacebookFriendRes>{
	
	private Logger logger = Loggers.relationLogger;
	
	/**
	 * 请求加载列表
	 * @param player
	 * @param cgLoadFriendList
	 */
	public void handleLoadFriendList(Player player,
			CGLoadFriendList cgLoadFriendList) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		GCLoadFriendList gcLoadFriendList = RelationLogic.getInstance().buildFriendListMsg(human);
		player.sendMessage(gcLoadFriendList);
		
	}
	
	/**
	 * 请求待处理列表
	 * @param player
	 * @param cgLoadFriendRequestList
	 */
	public void handleLoadFriendRequestList(Player player,
			CGLoadFriendRequestList cgLoadFriendRequestList) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		GCLoadFriendRequestList gcLoadFriendRequestList = RelationLogic.getInstance().buildFriendRequestListMsg(human);
		player.sendMessage(gcLoadFriendRequestList);
	}
	
	/**
	 * 请求加好友
	 * @param player
	 * @param cgRequestFriend
	 */
	public void handleRequestFriend(Player player,
			CGRequestFriend cgRequestFriend) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		
		HumanRelationManager humanRelationManager = human.getHumanRelationManager();
		
		long friendId =cgRequestFriend.getPlayerId();
		
		//判断是否自己
		if(player.getPassportId() == friendId)
		{
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]打算添加自己为好友");
			
			human.sendSystemMessage(LangConstants.FRIEND_REQUEST_NOT_SELF);
			return;
		}
		
		//判断是否存在此用户
		PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(friendId);
		if(playerCacheInfo == null)
		{
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]添加玩家["+friendId+"]不存在");
			human.sendSystemMessage(LangConstants.USER_NO_EXIST);
			return;
		}
		
		//判断是否已经添加好友
		if(humanRelationManager.checkIfAddFriend(friendId)){
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]已经添加了好友["+friendId+"]");
			human.sendSystemMessage(LangConstants.FRIEND_ISEXIST);
			return;
		}
		
		//判断自己的好友数量以达到上限
		if(humanRelationManager.checkIfIsFull())
		{
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]好友已经满了");
			player.getHuman().sendSystemMessage(LangConstants.FRIEND_ISMAX);
			return;
		}
		

		
		//发送好友申请邮件
		boolean flag =RelationLogic.getInstance().sendRequestFriend(human, playerCacheInfo);
		if (!flag){
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]已经添加了");
			//player.getHuman().sendSystemMessage(LangConstants.FRIEND_ISMAX);
			human.sendSystemMessage(LangConstants.FRIEND_REQUEST_SEND);
			return;
		}
		GCRequestFriend gcRequestFriend=new GCRequestFriend();
		gcRequestFriend.setPlayerId(friendId);
		human.sendMessage(gcRequestFriend);
	
		human.sendSystemMessage(LangConstants.FRIEND_REQUEST_SEND);
		Globals.getLogService().sendFriendLog(human, LogReasons.FriendLogReason.FRIEND_REQUEST, LogReasons.FriendLogReason.FRIEND_REQUEST.getReasonText(), friendId);
		
	}
	
	
	/**
	 * 处理好友请求
	 * @param player
	 * @param cgApplyFriend
	 */
	public void handleApplyFriend(Player player, CGApplyFriend cgApplyFriend) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		
		HumanRelationManager humanRelationManager = human.getHumanRelationManager();
		
		logger.debug("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]申请好友");
		
		long playerId =cgApplyFriend.getPlayerId();
		FriendRequest friendRequest = humanRelationManager.friendRequestById(playerId);
	
		//检查请求是否存在
		if(friendRequest==null){
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]正在处理不存在的请求");
			human.sendSystemMessage(LangConstants.FRIEND_REQUEST_NOT_EXIST);
			return;
		}
		
		humanRelationManager.getFriendRequestList().remove(friendRequest);
		friendRequest.onDelete();
		
		//判断是否存在此用户
		PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(playerId);
		if(playerCacheInfo == null)
		{
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]添加玩家["+playerId+"]不存在");
			human.sendSystemMessage(LangConstants.USER_NO_EXIST);
			return;
		}
		
		//判断是否已经加过了
		if(humanRelationManager.checkIfAddFriend(friendRequest.getSendId())){
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]已经加过这个好友");
			human.sendSystemMessage(LangConstants.FRIEND_ISEXIST);
			return;
		}
		
		Player requestFriend = Globals.getOnlinePlayerService().getPlayerByPassportId(playerId);
		//在线的情况，在这里处理，不在线的情况 在登录的时候 加载好友列表的时候处理
		if(requestFriend != null){
			String existIds = requestFriend.getHuman().getRequestFriendIds();
			if(StringUtils.isNotBlank(existIds)){
				existIds=existIds.replace(playerId+",", "");
				human.setRequestFriendIds(existIds);
				human.setModified();
			}
		}
		//拒绝好友请求
		if(cgApplyFriend.getResult()==0)
		{
	
			GCApplyFriend gcApplyFriend = new GCApplyFriend();
			gcApplyFriend.setResult(0);
			gcApplyFriend.setPlayId(cgApplyFriend.getPlayerId());
			player.sendMessage(gcApplyFriend);
			Globals.getLogService().sendFriendLog(human, LogReasons.FriendLogReason.FRIEND_REQUEST_REJECT, LogReasons.FriendLogReason.FRIEND_REQUEST_REJECT.getReasonText(), cgApplyFriend.getPlayerId());
			
			return;
		}
	
		
		//判断自己的好友数量以达到上限
		if(humanRelationManager.checkIfIsFull())
		{
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]好友数量已达上限");
			player.getHuman().sendSystemMessage(LangConstants.FRIEND_ISMAX);
			return;
		}
		
		//判断对方好友数量以达到上限
		
		//添加好友
		RelationLogic.getInstance().applyFriend(human, playerCacheInfo,false);
		
		human.sendSystemMessage(LangConstants.FRIEND_APPROVE);
		GCApplyFriend gcApplyFriend = new GCApplyFriend();
		gcApplyFriend.setPlayId(cgApplyFriend.getPlayerId());
		gcApplyFriend.setResult(1);
		human.sendMessage(gcApplyFriend);
		Globals.getLogService().sendFriendLog(human, LogReasons.FriendLogReason.FRIEND_REQUEST_ACCEPT, LogReasons.FriendLogReason.FRIEND_REQUEST_ACCEPT.getReasonText(), cgApplyFriend.getPlayerId());
		//活动
		/**好友 增加一个 自己**/
	/*	Globals.getActivityService().MakeFriendsForGift(human,playerCacheInfo.getPlayerId());*/
		
		
		/**对方**/
		Player friend = Globals.getOnlinePlayerService().getPlayerByPassportId(playerId);
		if(friend!=null){
			Globals.getActivityService().MakeFriendsForGift(friend.getHuman(),human.getPassportId());
			List<Friend> friends = friend.getHuman().getHumanRelationManager().getFriendList();
			if(friends != null){
				for(Friend f:friends){
					if(f.getFriendId()==human.getPassportId()){
						f.setAgree(1);
						f.setModified();
					}
				}
			}
		}
		
		
	}

	
	/**
	 * 删除好友
	 * @param player
	 * @param cgDeleteFriend
	 */
	public void handleDeleteFriend(Player player, CGDeleteFriend cgDeleteFriend) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		
		HumanRelationManager humanRelationManager = human.getHumanRelationManager();		
		long friendId = cgDeleteFriend.getFriendId();
		
		//判断是否存在此用户
		PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(friendId);
		if(playerCacheInfo == null)
		{
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]删除玩家["+friendId+"]不存在");
			human.sendSystemMessage(LangConstants.USER_NO_EXIST);
			return;
		}
		
		Friend friend = humanRelationManager.friendById(friendId);
		//检查好友是否存在
		if(friend==null){
			human.sendSystemMessage(LangConstants.FRIEND_ISNOTEXIST);
			return;
		}
	
		RelationLogic.getInstance().deleteFriend(human, friend, playerCacheInfo);
		
		
		
		
		// TODO Auto-generated method stub
		GCDeleteFriend gcDeleteFriend = new GCDeleteFriend();
		gcDeleteFriend.setFriendId(friendId);
		
		human.sendMessage(gcDeleteFriend);
		Globals.getLogService().sendFriendLog(human, LogReasons.FriendLogReason.FRIEND_REQUEST_DELETE, LogReasons.FriendLogReason.FRIEND_REQUEST_DELETE.getReasonText(), cgDeleteFriend.getFriendId());
	}
	
	/**
	 * 加载好友列表
	 * @param player
	 * @param cgLoadFriendGiftList
	 */
	public void handleLoadFriendGiftList(Player player,
			CGLoadFriendGiftList cgLoadFriendGiftList) {
		// TODO Auto-generated method stub
	
		Human human = player.getHuman();
		Assert.notNull(human,"human 不能为空");
		GCLoadFriendGiftList gcLoadFriendGiftList = RelationLogic.getInstance().buildFriendGiftListMsg(human);
		player.sendMessage(gcLoadFriendGiftList);
		
	}
	
	/**
	 * 发送礼物
	 * @param player
	 * @param cgFriendGift
	 */
	public void handleFriendGift(Player player, CGFriendGift cgFriendGift) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		
		Assert.notNull(human,"human 不能为空");
		
		long friendId = cgFriendGift.getFriendId();
		
		HumanRelationManager humanRelationManager = human.getHumanRelationManager();
		
		
		int len = humanRelationManager.getFriendList().size();
		//角色好友列表
		/*List<Long> playerIds = new ArrayList<Long>();
		for(int i=0;i<len;i++)
		{
			long tempFriendId = humanRelationManager.getFriendList().get(i).getFriendId();
			if(playerIds.contains(tempFriendId))
				continue;
			playerIds.add(tempFriendId);
		}*/
		List<Long> playerIds = new ArrayList<Long>();
		for(int i=0;i<len;i++)
		{
			long tempFriendId = humanRelationManager.getFriendList().get(i).getFriendId();
			if(friendId == tempFriendId){
				playerIds.add(tempFriendId);
			}
			
		}
		
		
		List<Long> success = new ArrayList<Long>();
		for(Long playerId : playerIds){
			boolean result = sendSingleGift(player,human,humanRelationManager,playerId);
			if(result){
				success.add(playerId);
			}
		}
		
		FriendAlreadyGiftListData[]  data = new FriendAlreadyGiftListData[success.size()];
		for(int i = 0;i < success.size();i++){
			FriendAlreadyGiftListData fag = new FriendAlreadyGiftListData();
			fag.setPlayId(success.get(i));
			data[i] = fag;
		}
		if(success.isEmpty()){
			return;
		}
		sendMail(human,success,String.valueOf(LangConstants.GiftTitle),String.valueOf(LangConstants.Giftcontent));
		
		Globals.getTaskNewService().spinSlot(human,ClientType.TASK102.getIndex(),RefreshType.RefreshType2.getIndex());
		
		GCFriendGift gcFriendGift=new GCFriendGift();
		gcFriendGift.setFriendAlreadyGiftListData(data);
		human.sendMessage(gcFriendGift);
		
		if(data.length > 0){
			player.getHuman().sendSystemMessage(LangConstants.GIFTS_TO_SEND_SUCCESS);
			player.getHuman().getHumanAchievementManager().updateGiftGiving();
		}
	}
	
	private boolean sendSingleGift(Player player,Human human,HumanRelationManager humanRelationManager,Long friendId){
		//判断是否自己
		if(player.getPassportId() == friendId)
		{
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]不能给自己发礼物");
			
			//human.sendSystemMessage(LangConstants.FRIEND_GIFT_NOT_SELF);
			return false;
		}
		
		//判断是否存在此用户
		PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(friendId);
		if(playerCacheInfo == null)
		{
			logger.warn("玩家ID["+player.getPassportId()+"]玩家名称["+player.getHuman().getName()+"]发送玩家["+friendId+"]不存在");
			//human.sendSystemMessage(LangConstants.USER_NO_EXIST);
			return false;
		}
		
		//判断是否已经添加好友
		Friend friend = humanRelationManager.friendById(friendId);
		if(friend == null){
			logger.warn("玩家ID["+player.getPassportId()+"]和玩家["+friendId+"]不是好友，不能发送礼物");
			//human.sendSystemMessage(LangConstants.FRIEND_ISNOTEXIST);
			return false;
		}
		
		long now = Globals.getTimeService().now();
		//当前零点时间
		long beginOfToday = TimeUtils.getBeginOfDay(now);
		//判断是否已经发送过好友礼物
		if(beginOfToday<=friend.getGiftTime())
		{
			logger.warn("玩家ID["+player.getPassportId()+"]已经发送过好友礼物给玩家["+friendId+"]");
			//player.getHuman().sendSystemMessage(LangConstants.FRIEND_GIFT_ALREADY_SEND);
			return false;
		}
		
		//玩家跨天玩游戏
		if(!TimeUtils.isSameDay(human.getLastLoginTime(), now) ){
			human.getAddfriendIds().clear();
		 }
		Map<Long,String> setfriendId =  human.getAddfriendIds();
		if(setfriendId.containsKey(friendId)){
			logger.warn("玩家ID["+player.getPassportId()+"]已经发送过好友礼物给玩家["+friendId+"]");
			//player.getHuman().sendSystemMessage(LangConstants.FRIEND_GIFT_ALREADY_SEND);
			return false;
		}else{
			setfriendId.put(friendId, String.valueOf(now));
			human.setModified();
		}
		
		logger.debug("玩家ID["+player.getPassportId()+"]发送好友礼物给玩家["+friendId+"]");
		
		friend.setGiftTime(now);
		friend.setModified();
		
		//发送礼物
		//RelationLogic.getInstance().sendFriendGift(human, playerCacheInfo);
		
		Globals.getLogService().sendFriendLog(human, LogReasons.FriendLogReason.FRIEND_GIFT, LogReasons.FriendLogReason.FRIEND_GIFT.getReasonText(),friendId);
		return true;
	}
	
	private void sendMail(Human huamn,List<Long> listId, String title,String content){
	
		 List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
		 randRewardDataList.addAll(Globals.getConfigTempl().getGiftRewardList());
		  //发邮件
		 MailLogic.getInstance().systemSendMailHumanGfit(huamn,title ,content, listId, randRewardDataList); 
	}

	/**
	 * 领取礼物
	 * @param player
	 * @param cgFriendGiftGet
	 */
	public void handleFriendGiftGet(Player player,
			CGFriendGiftGet cgFriendGiftGet) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		HumanRelationManager humanRelationManager = human.getHumanRelationManager();
		FriendGift friendGift= humanRelationManager.friendGiftById(cgFriendGiftGet.getGiftId());
		if(friendGift == null){
			logger.warn("玩家ID["+player.getPassportId()+"]领取礼物id["+cgFriendGiftGet.getGiftId()+"]不存在");
			human.sendSystemMessage(LangConstants.FRIEND_GIFT_NOT_EXIST);
			return;
		}
		
		if(friendGift.ifGet()){
			logger.warn("玩家ID["+player.getPassportId()+"]领取礼物id["+cgFriendGiftGet.getGiftId()+"]已经领取过了");
			human.sendSystemMessage(LangConstants.FRIEND_GIFT_ALREADY_GET);
			return;
		}
		
		CommonLogic.getInstance().giveRandReward(human, Globals.getConfigTempl().getGiftRewardList(),LogReasons.GoldLogReason.FRIEND_GIFT, DiamondLogReason.FRIEND_GIFT, CharmLogReason.FRIEND_GIFT, ItemLogReason.FRIEND_GIFT, true);
		
		
//		long now = Globals.getTimeService().now();
//		friendGift.setGetTime(now);
//		friendGift.setModified();
		humanRelationManager.getFriendGiftList().remove(friendGift);
		friendGift.onDelete();
		GCFriendGiftGet gcFriendGiftGet = new GCFriendGiftGet();
		gcFriendGiftGet.setGiftId(cgFriendGiftGet.getGiftId());
		human.sendMessage(gcFriendGiftGet);
		
		logger.debug("玩家ID["+player.getPassportId()+"]领取礼物id["+cgFriendGiftGet.getGiftId()+"]");
	}

	/**
	 * facebook 好友同步
	 * @param player
	 * @param cgFacebookFriendsSync
	 */
	public void handleFacebookFriendsSync(Player player,
			CGFacebookFriendsSync cgFacebookFriendsSync) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		Assert.notNull(human);
		for(String fId:cgFacebookFriendsSync.getFriendList()){
			logger.info("玩家["+human.getPassportId()+"]同步facebook好友:" +fId);
		}
		
		if(cgFacebookFriendsSync.getFriendList().length==0){
			logger.debug("玩家["+human.getPassportId()+"]同步facebook好友");
			GCFacebookFriendsSync gcFacebookFriendsSync =new GCFacebookFriendsSync();
			gcFacebookFriendsSync.setFriendRequestInfoData(new FriendDetailInfoData[0]);
			human.sendMessage(gcFacebookFriendsSync);
			return;
		}
		
		List<String> idList = ArrayUtils.toList(cgFacebookFriendsSync.getFriendList());
		if(idList.size()==0)
			return;
		
		//发送异步调用
		FacebookFriendParam facebookFriendParam = new FacebookFriendParam(Globals.getLocalConfig().getRequestDomain(),"api/facebook/friends.json",true);
		facebookFriendParam.setFriendIdList(idList);
	
		AsyncHttpOperation<FacebookFriendRes> asyncHttpOper = new AsyncHttpOperation<FacebookFriendRes>(player,facebookFriendParam, RelationHandlerFactory.getHandler());;
		Globals.getAsyncService().createGlobalAsyncOperationAndExecuteAtOnce(asyncHttpOper);
		
		
		logger.debug("玩家["+human.getPassportId()+"]同步facebook好友");
	
	}

	
	@Override
	public void onFinished(Player player, int errorCode, FacebookFriendRes res) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		if(human==null){
			logger.warn("玩家["+player.getPassportId()+"]已经下线");
			return;
		}
		
		if(errorCode!=0){
			logger.warn("玩家["+player.getPassportId()+"]请求本地服务器数据失败");
			GCFacebookFriendsSync gcFacebookFriendsSync =new GCFacebookFriendsSync();
			gcFacebookFriendsSync.setFriendRequestInfoData(new FriendDetailInfoData[0]);
			human.sendMessage(gcFacebookFriendsSync);
			return ;
		}
		
		if(res.getFriendIdList()==null){
			return;
		}
		logger.info("好友:"+res.getFriendIdList().toString());
		HumanRelationManager humanRelationManager =	human.getHumanRelationManager(); 
		
		List<Long> changed = new ArrayList<Long>();
		for(long friendId :res.getFriendIdList()){
			Friend tempFriend = humanRelationManager.friendById(friendId);
			if(tempFriend ==null){
				//判断是否存在此用户
				PlayerCacheInfo playerCacheInfo = Globals.getPlayerCacheService().getPlayerCacheById(friendId);
				if(playerCacheInfo == null)
				{
					continue;
				}
				RelationLogic.getInstance().applyFriend(human, playerCacheInfo,true);
			}else{
				tempFriend.setFacebook(1);
				tempFriend.setModified();
				
			}
			changed.add(friendId);
		}
	
		Map<Long,PlayerCacheInfo> tempFriendMap=Globals.getPlayerCacheService().getPlayerCacheMapByIds(changed);
		List<FriendDetailInfoData> friendDetailInfoDataList = new ArrayList<FriendDetailInfoData>();
		for(Friend tempFriend :humanRelationManager.getFriendList()){
			PlayerCacheInfo tempPlayerCacheInfo = tempFriendMap.get(tempFriend.getFriendId());
			if(tempPlayerCacheInfo == null)
				continue;
			FriendDetailInfoData  friendDetailInfoData = FriendDetailInfoData.convertFromFriendAndPlayCacheInfo(tempFriend, tempPlayerCacheInfo);
			friendDetailInfoDataList.add(friendDetailInfoData);
		
		}
		
		GCFacebookFriendsSync gcFacebookFriendsSync =new GCFacebookFriendsSync();	
		gcFacebookFriendsSync.setFriendRequestInfoData(friendDetailInfoDataList.toArray(new FriendDetailInfoData[friendDetailInfoDataList.size()]));
		player.sendMessage(gcFacebookFriendsSync);
	}

	/**
	 * 返回陌生人链表
	 * @param player
	 * @param cgStrangerList
	 */
	public void handleStrangerList(Player player, CGStrangerList cgStrangerList) {
		
		Human human = player.getHuman();
		
		HumanRelationManager humanRelationManager =	human.getHumanRelationManager();
		
		Set<Long> setIds = new HashSet<Long>();
		
		List<Friend> friendList = humanRelationManager.getFriendList();
		for(Friend friend : friendList){
			setIds.add(friend.getFriendId());
		}
		//过滤自己
		setIds.add(human.getPassportId());
		
		List<PlayerCacheInfo> tempFriendMap=Globals.getPlayerCacheService().getPlayerCacheMap(setIds);
		
		 StrangerData[] strangerData = new StrangerData[tempFriendMap.size()];
		 
		 for(int i = 0;i < tempFriendMap.size();i++){
			 PlayerCacheInfo cache = tempFriendMap.get(i);
			 StrangerData data = new StrangerData();
			 data.setUserId(cache.getPlayerId());
			 data.setImg(cache.getImg());
			 data.setName(cache.getName());
			 data.setLevel((int)cache.getLevel());
			 data.setCountries(cache.getCountries());
			 data.setSex(cache.getSex());
			 data.setVipLevel(cache.getVipLevel());
			 data.setIsRequest(0);
			 String requestIds = human.getRequestFriendIds();
			 if(StringUtils.isBlank(requestIds)){
			 }else{
				 String[] rIdArr = requestIds.split(",");
				 for(int j=0;j<rIdArr.length;j++){
					 if(Long.valueOf(rIdArr[j]).intValue()==cache.getPlayerId()){
						 data.setIsRequest(1);
						 break;
					 }
				 }
			 }
			 strangerData[i] = data;
		 }
		 
		GCStrangerList message = new GCStrangerList();
		message.setStrangerData(strangerData);
		player.sendMessage(message);
	}

	public void handleFacebookNum(Player player, CGFacebookNum cgFacebookNum) {
		
		int friendNum = cgFacebookNum.getFriendNum();
		Human human = player.getHuman();
		
		HumanMiscManager humanMiscManager =  human.getHumanMiscManager();
		humanMiscManager.checkIfRefresh();
		
		
		for(int i = 0 ;i < friendNum;i++){
			humanMiscManager.getHumanMisc().getFbInviteList().add(String.valueOf(i));
		}
		
		humanMiscManager.getHumanMisc().setModified();
		
		player.sendMessage(humanMiscManager.buildGCMiscFBInfoData());
		human.getHumanAchievementManager().updateFaceBook();
		
	}

	public void handleEnterFriendsRoom(Player player, CGEnterFriendsRoom cgEnterFriendsRoom) {
		
		long friendId = cgEnterFriendsRoom.getFriendId();
		
		Player friend = Globals.getOnlinePlayerService().getPlayerByPassportId(friendId);
		if(friend != null){
			Human friendHuman = friend.getHuman();
			String friendRoomId = friendHuman.getSlotRoomId();
			if(!friendRoomId.equals("")){
			  int friendSlotId = friendHuman.getHumanSlotManager().getSlotId();
			  
			  CGSlotEnterRoom message = new CGSlotEnterRoom();
			  message.setRoomId(friendRoomId);
			  message.setSlotId(friendSlotId);
			  SlotHandlerFactory.getHandler().handleSlotEnterRoom(player, message);
			}
		}else{
			PlayerCacheInfo cache = Globals.getPlayerCacheService().getPlayerCacheById(friendId);
			if(cache != null && cache.getPlayerState() != 0){
				FriendSlotIdRoomId message = new FriendSlotIdRoomId();
				message.setPalyerId(player.getHuman().getPassportId());
				message.setFriendId(friendId);
				message.setServerid(Globals.getServerConfig().getServerId());
				Globals.getRedisService().sendRedisMsgToServer(cache.getServerId(), message);
			}else{
				//玩家已经下线 或者缓存不存在
				player.getHuman().sendSystemMessage(LangConstants.TEXAS_ROOM_FULL);
			}
		}
	}



}
