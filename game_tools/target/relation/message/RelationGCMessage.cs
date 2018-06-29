using System.Collections;

public class RelationGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_LOAD_FRIEND_LIST,GC_LOAD_FRIEND_LIST);
		register(NetMessageType.GC_LOAD_FRIEND_REQUEST_LIST,GC_LOAD_FRIEND_REQUEST_LIST);
		register(NetMessageType.GC_REQUEST_FRIEND,GC_REQUEST_FRIEND);
		register(NetMessageType.GC_REQUEST_FRIEND_SYNC,GC_REQUEST_FRIEND_SYNC);
		register(NetMessageType.GC_APPLY_FRIEND,GC_APPLY_FRIEND);
		register(NetMessageType.GC_ADD_FRIEND,GC_ADD_FRIEND);
		register(NetMessageType.GC_DELETE_FRIEND,GC_DELETE_FRIEND);
		register(NetMessageType.GC_LOAD_FRIEND_GIFT_LIST,GC_LOAD_FRIEND_GIFT_LIST);
		register(NetMessageType.GC_FRIEND_GIFT,GC_FRIEND_GIFT);
		register(NetMessageType.GC_FRIEND_GIFT_SYNC,GC_FRIEND_GIFT_SYNC);
		register(NetMessageType.GC_FRIEND_GIFT_GET,GC_FRIEND_GIFT_GET);
		register(NetMessageType.GC_FACEBOOK_FRIENDS_SYNC,GC_FACEBOOK_FRIENDS_SYNC);
		register(NetMessageType.GC_STRANGER_LIST,GC_STRANGER_LIST);
	}
 
  	/**
	 * 客户端请求好友列表
	 * @param friendDetailInfoDatalist 玩家信息
	 */
	public void GC_LOAD_FRIEND_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList friendDetailInfoDatalist = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			FriendDetailInfoData friendDetailInfoDatalist_Datas= new FriendDetailInfoData();
			friendDetailInfoDatalist_Datas.playerId =data.GetLong();
			friendDetailInfoDatalist_Datas.name = data.GetString();//名字
			friendDetailInfoDatalist_Datas.img = data.GetString();//图片
			friendDetailInfoDatalist_Datas.giftTime =data.GetLong();
			friendDetailInfoDatalist_Datas.gold =data.GetLong();
			friendDetailInfoDatalist_Datas.level =data.GetLong();
			friendDetailInfoDatalist_Datas.facebook = data.GetInt();//facebook
			friendDetailInfoDatalist_Datas.sex = data.GetInt();//性别
			friendDetailInfoDatalist_Datas.countries = data.GetString();//国籍
			friendDetailInfoDatalist_Datas.isGame = data.GetInt();//是否在游戏中 1 在游戏中 0 没有在
			friendDetailInfoDatalist_Datas.playerState = data.GetInt();//玩家状态 1 在线 0 不在线
			friendDetailInfoDatalist_Datas.offlineTime =data.GetLong();
			friendDetailInfoDatalist_Datas.vipLevel = data.GetInt();//VIP等级 
			friendDetailInfoDatalist_Datas.alreadyInvateClub = data.GetInt();//是否已经邀请加入俱乐部
			friendDetailInfoDatalist_Datas.alreadyJoinClub = data.GetInt();//是否已经加入俱乐部未加入: 0  已加入: 1   
			friendDetailInfoDatalist.Add(friendDetailInfoDatalist_Datas);
		}
		RelationHandler.Instance().GC_LOAD_FRIEND_LIST(friendDetailInfoDatalist);
	}
 
  	/**
	 * 客户端请求好友列表
	 * @param friendRequestInfoDataList 玩家信息
	 */
	public void GC_LOAD_FRIEND_REQUEST_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList friendRequestInfoDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			PlayerInfoData friendRequestInfoDataList_Datas= new PlayerInfoData();
			friendRequestInfoDataList_Datas.playerId =data.GetLong();
			friendRequestInfoDataList_Datas.name = data.GetString();//名字
			friendRequestInfoDataList_Datas.img = data.GetString();//图片
			friendRequestInfoDataList_Datas.gold =data.GetLong();
			friendRequestInfoDataList_Datas.diamond =data.GetLong();
			friendRequestInfoDataList_Datas.charm =data.GetLong();
			friendRequestInfoDataList_Datas.level =data.GetLong();
			friendRequestInfoDataList_Datas.sex = data.GetInt();//性别
			friendRequestInfoDataList_Datas.viplevel = data.GetInt();//vip等级
			friendRequestInfoDataList_Datas.countries = data.GetString();//国籍
			friendRequestInfoDataList_Datas.age = data.GetInt();//年龄
			friendRequestInfoDataList_Datas.slotRotate =data.GetLong();
			friendRequestInfoDataList_Datas.slotWin =data.GetLong();
			friendRequestInfoDataList_Datas.slotSingleWin =data.GetLong();
			friendRequestInfoDataList_Datas.slotWinNum =data.GetLong();
			friendRequestInfoDataList_Datas.integral =data.GetLong();
			friendRequestInfoDataList_Datas.isRequest = data.GetInt();//是否已经申请添加好友 0:未申请,1:已申请 
			friendRequestInfoDataList_Datas.newGuyGift = data.GetInt();//新手大礼包:1已购买，0 未购买
			friendRequestInfoDataList_Datas.clubId = data.GetString();//俱乐部id，空字符串表示未加入俱乐部
			friendRequestInfoDataList_Datas.clubIco = data.GetInt();//俱乐部图标
			friendRequestInfoDataList_Datas.clubInvitedTimes = data.GetInt();//被俱乐部邀请次数，0为未被邀请
			friendRequestInfoDataList_Datas.achieveRate = data.GetString();//无双吹牛 成就  完成个数/总个数
			friendRequestInfoDataList.Add(friendRequestInfoDataList_Datas);
		}
		RelationHandler.Instance().GC_LOAD_FRIEND_REQUEST_LIST(friendRequestInfoDataList);
	}
 
  	/**
	 * 请求添加好友
	 * @param playerId 玩家id
	 */
	public void GC_REQUEST_FRIEND(InputMessage data) 
	{
		long playerId = data.GetLong();
		RelationHandler.Instance().GC_REQUEST_FRIEND(playerId);
	}
 
  	/**
	 * 请求添加好友同步
	 * @param friendRequestInfoData 玩家信息
	 */
	public void GC_REQUEST_FRIEND_SYNC(InputMessage data) 
	{
		PlayerInfoData friendRequestInfoData = new PlayerInfoData();
		friendRequestInfoData.playerId = data.GetLong();//玩家id
		friendRequestInfoData.name = data.GetString();//名字
		friendRequestInfoData.img = data.GetString();//图片
		friendRequestInfoData.gold = data.GetLong();//玩家筹码
		friendRequestInfoData.diamond = data.GetLong();//钻石
		friendRequestInfoData.charm = data.GetLong();//玩家筹码
		friendRequestInfoData.level = data.GetLong();//等级
		friendRequestInfoData.sex = data.GetInt();//性别
		friendRequestInfoData.viplevel = data.GetInt();//vip等级
		friendRequestInfoData.countries = data.GetString();//国籍
		friendRequestInfoData.age = data.GetInt();//年龄
		friendRequestInfoData.slotRotate = data.GetLong();//总转次数
		friendRequestInfoData.slotWin = data.GetLong();//总赢得分
		friendRequestInfoData.slotSingleWin = data.GetLong();//单次赢取最大
		friendRequestInfoData.slotWinNum = data.GetLong();//玩家总胜利次数
		friendRequestInfoData.integral = data.GetLong();//排行榜积分
		friendRequestInfoData.isRequest = data.GetInt();//是否已经申请添加好友 0:未申请,1:已申请 
		friendRequestInfoData.newGuyGift = data.GetInt();//新手大礼包:1已购买，0 未购买
		friendRequestInfoData.clubId = data.GetString();//俱乐部id，空字符串表示未加入俱乐部
		friendRequestInfoData.clubIco = data.GetInt();//俱乐部图标
		friendRequestInfoData.clubInvitedTimes = data.GetInt();//被俱乐部邀请次数，0为未被邀请
		friendRequestInfoData.achieveRate = data.GetString();//无双吹牛 成就  完成个数/总个数
		RelationHandler.Instance().GC_REQUEST_FRIEND_SYNC(friendRequestInfoData);
	}
 
  	/**
	 * 客户端处理好友请求
	 * @param playId 玩家id
	 * @param result 处理结果
	 */
	public void GC_APPLY_FRIEND(InputMessage data) 
	{
		long playId = data.GetLong();
		int result = data.GetInt();		
		RelationHandler.Instance().GC_APPLY_FRIEND(playId,result);
	}
 
  	/**
	 * 客户端处理好友请求
	 * @param friendDetailInfoData 玩家信息
	 */
	public void GC_ADD_FRIEND(InputMessage data) 
	{
		FriendDetailInfoData friendDetailInfoData = new FriendDetailInfoData();
		friendDetailInfoData.playerId = data.GetLong();//玩家id
		friendDetailInfoData.name = data.GetString();//名字
		friendDetailInfoData.img = data.GetString();//图片
		friendDetailInfoData.giftTime = data.GetLong();//礼物时间
		friendDetailInfoData.gold = data.GetLong();//筹码
		friendDetailInfoData.level = data.GetLong();//等级
		friendDetailInfoData.facebook = data.GetInt();//facebook
		friendDetailInfoData.sex = data.GetInt();//性别
		friendDetailInfoData.countries = data.GetString();//国籍
		friendDetailInfoData.isGame = data.GetInt();//是否在游戏中 1 在游戏中 0 没有在
		friendDetailInfoData.playerState = data.GetInt();//玩家状态 1 在线 0 不在线
		friendDetailInfoData.offlineTime = data.GetLong();//下线时间点
		friendDetailInfoData.vipLevel = data.GetInt();//VIP等级 
		friendDetailInfoData.alreadyInvateClub = data.GetInt();//是否已经邀请加入俱乐部
		friendDetailInfoData.alreadyJoinClub = data.GetInt();//是否已经加入俱乐部未加入: 0  已加入: 1   
		RelationHandler.Instance().GC_ADD_FRIEND(friendDetailInfoData);
	}
 
  	/**
	 * 客户端删除好友请求
	 * @param friendId 好友id
	 */
	public void GC_DELETE_FRIEND(InputMessage data) 
	{
		long friendId = data.GetLong();
		RelationHandler.Instance().GC_DELETE_FRIEND(friendId);
	}
 
  	/**
	 * 客户端请求好友礼物列表
	 * @param friendGiftInfoDataList 玩家信息
	 */
	public void GC_LOAD_FRIEND_GIFT_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList friendGiftInfoDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			FriendGiftInfoData friendGiftInfoDataList_Datas= new FriendGiftInfoData();
			friendGiftInfoDataList_Datas.giftId =data.GetLong();
			friendGiftInfoDataList_Datas.playerId =data.GetLong();
			friendGiftInfoDataList_Datas.name = data.GetString();//名字
			friendGiftInfoDataList_Datas.img = data.GetString();//图片
			friendGiftInfoDataList_Datas.sendTime =data.GetLong();
			friendGiftInfoDataList_Datas.getTime =data.GetLong();
			friendGiftInfoDataList.Add(friendGiftInfoDataList_Datas);
		}
		RelationHandler.Instance().GC_LOAD_FRIEND_GIFT_LIST(friendGiftInfoDataList);
	}
 
  	/**
	 * 发送礼物
	 * @param friendAlreadyGiftListData 已发送礼物好友列表
	 */
	public void GC_FRIEND_GIFT(InputMessage data) 
	{
		int i,size;
		ArrayList friendAlreadyGiftListData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			FriendAlreadyGiftListData friendAlreadyGiftListData_Datas= new FriendAlreadyGiftListData();
			friendAlreadyGiftListData_Datas.playId =data.GetLong();
			friendAlreadyGiftListData.Add(friendAlreadyGiftListData_Datas);
		}
		RelationHandler.Instance().GC_FRIEND_GIFT(friendAlreadyGiftListData);
	}
 
  	/**
	 * 发送礼物同步
	 * @param friendGiftInfoData 玩家信息
	 */
	public void GC_FRIEND_GIFT_SYNC(InputMessage data) 
	{
		FriendGiftInfoData friendGiftInfoData = new FriendGiftInfoData();
		friendGiftInfoData.giftId = data.GetLong();//礼物id
		friendGiftInfoData.playerId = data.GetLong();//玩家id
		friendGiftInfoData.name = data.GetString();//名字
		friendGiftInfoData.img = data.GetString();//图片
		friendGiftInfoData.sendTime = data.GetLong();//发送时间
		friendGiftInfoData.getTime = data.GetLong();//领取时间
		RelationHandler.Instance().GC_FRIEND_GIFT_SYNC(friendGiftInfoData);
	}
 
  	/**
	 * 领取礼物
	 * @param giftId 礼物id
	 */
	public void GC_FRIEND_GIFT_GET(InputMessage data) 
	{
		long giftId = data.GetLong();
		RelationHandler.Instance().GC_FRIEND_GIFT_GET(giftId);
	}
 
  	/**
	 * facebook好友
	 * @param friendRequestInfoData 玩家信息
	 */
	public void GC_FACEBOOK_FRIENDS_SYNC(InputMessage data) 
	{
		int i,size;
		ArrayList friendRequestInfoData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			FriendDetailInfoData friendRequestInfoData_Datas= new FriendDetailInfoData();
			friendRequestInfoData_Datas.playerId =data.GetLong();
			friendRequestInfoData_Datas.name = data.GetString();//名字
			friendRequestInfoData_Datas.img = data.GetString();//图片
			friendRequestInfoData_Datas.giftTime =data.GetLong();
			friendRequestInfoData_Datas.gold =data.GetLong();
			friendRequestInfoData_Datas.level =data.GetLong();
			friendRequestInfoData_Datas.facebook = data.GetInt();//facebook
			friendRequestInfoData_Datas.sex = data.GetInt();//性别
			friendRequestInfoData_Datas.countries = data.GetString();//国籍
			friendRequestInfoData_Datas.isGame = data.GetInt();//是否在游戏中 1 在游戏中 0 没有在
			friendRequestInfoData_Datas.playerState = data.GetInt();//玩家状态 1 在线 0 不在线
			friendRequestInfoData_Datas.offlineTime =data.GetLong();
			friendRequestInfoData_Datas.vipLevel = data.GetInt();//VIP等级 
			friendRequestInfoData_Datas.alreadyInvateClub = data.GetInt();//是否已经邀请加入俱乐部
			friendRequestInfoData_Datas.alreadyJoinClub = data.GetInt();//是否已经加入俱乐部未加入: 0  已加入: 1   
			friendRequestInfoData.Add(friendRequestInfoData_Datas);
		}
		RelationHandler.Instance().GC_FACEBOOK_FRIENDS_SYNC(friendRequestInfoData);
	}
 
  	/**
	 * 返回陌生人列表
	 * @param strangerData 服务器返回数据
	 */
	public void GC_STRANGER_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList strangerData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			StrangerData strangerData_Datas= new StrangerData();
			strangerData_Datas.userId =data.GetLong();
			strangerData_Datas.img = data.GetString();//头像图片
			strangerData_Datas.name = data.GetString();//名字
			strangerData_Datas.level = data.GetInt();//玩家等级
			strangerData_Datas.sex = data.GetInt();//性别
			strangerData_Datas.countries = data.GetString();//国籍
			strangerData_Datas.vipLevel = data.GetInt();//VIP等级 
			strangerData_Datas.isRequest = data.GetInt();//是否已经申请添加好友 0:未申请,1:已申请 
			strangerData.Add(strangerData_Datas);
		}
		RelationHandler.Instance().GC_STRANGER_LIST(strangerData);
	}
}