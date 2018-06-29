using System.Collections;

public class HumanGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_HUMAN_DETAIL_INFO,GC_HUMAN_DETAIL_INFO);
		register(NetMessageType.GC_ROLE_SYMBOL_CHANGED_LONG,GC_ROLE_SYMBOL_CHANGED_LONG);
		register(NetMessageType.GC_HUMAN_CHANGE_NAME,GC_HUMAN_CHANGE_NAME);
		register(NetMessageType.GC_HUMAN_CHANGE_SEX,GC_HUMAN_CHANGE_SEX);
		register(NetMessageType.GC_HUMAN_CHANGE_IMG,GC_HUMAN_CHANGE_IMG);
		register(NetMessageType.GC_HUMAN_CHANGE_VIP,GC_HUMAN_CHANGE_VIP);
		register(NetMessageType.GC_HUMAN_VIDEO_NUM,GC_HUMAN_VIDEO_NUM);
		register(NetMessageType.GC_CHANEAGE_COUNTRIES,GC_CHANEAGE_COUNTRIES);
		register(NetMessageType.GC_SLOT_ROOM1,GC_SLOT_ROOM1);
		register(NetMessageType.GC_SLOT_ROOM2,GC_SLOT_ROOM2);
		register(NetMessageType.GC_SLOT_ROOM3,GC_SLOT_ROOM3);
		register(NetMessageType.GC_SLOT_ROOM4,GC_SLOT_ROOM4);
		register(NetMessageType.GC_SLOT_ROOM5,GC_SLOT_ROOM5);
		register(NetMessageType.GC_SLOT_ROOM_GIFT,GC_SLOT_ROOM_GIFT);
		register(NetMessageType.GC_SLOT_ROOM_PLEASE,GC_SLOT_ROOM_PLEASE);
		register(NetMessageType.GC_FRIEND_GAME,GC_FRIEND_GAME);
		register(NetMessageType.GC_EXP_DOUBLE,GC_EXP_DOUBLE);
		register(NetMessageType.GC_LEVEL_GIFT_MORE,GC_LEVEL_GIFT_MORE);
	}
 
  	/**
	 * 服务器给客户端下发人物全信息
	 * @param human 玩家
	 */
	public void GC_HUMAN_DETAIL_INFO(InputMessage data) 
	{
		HumanInfoData human = new HumanInfoData();
		human.roleId = data.GetLong();//人物角色UUID
		human.name = data.GetString();//名字
		human.sex = data.GetInt();//性别
		human.level = data.GetLong();//级别
		human.vipLevel = data.GetLong();//VIP 等级
		human.diamond = data.GetLong();//钻石
		human.gold = data.GetLong();//金币
		human.curExp = data.GetLong();//经验
		human.maxExp = data.GetLong();//经验上限
		human.charm = data.GetLong();//魅力值
		human.sceneId = data.GetInt();//当前所在城镇ID
		human.gvfirst = data.GetString();//gameview首次充值记录
		human.newguide = data.GetString();//0 大礼包 1 百家乐 2 德州 3 老虎
		human.watchNum = data.GetInt();//视频观看次数
		human.countries = data.GetString();//国籍
		human.integral = data.GetLong();//排行榜积分
		human.newGuyGift = data.GetInt();//新手大礼包:1已购买，0 未购买 
		human.todayView = data.GetInt();//1 当天已经显示，0 当天未显示
		human.bazooNewGuyProcess = data.GetString();//新手的进度({classicCompleted:0, niuniuCompleted:0, showhandCompleted:0, redblackCompleted:0})
		human.bazooGold = data.GetLong();//无双吹牛 用户金币的数量
		human.bazooRoom = data.GetString();//无双吹牛   用户所在的房间
		HumanHandler.Instance().GC_HUMAN_DETAIL_INFO(human);
	}
 
  	/**
	 * 用于发送符号数值改变消息
	 * @param roleType 角色类型
	 * @param roleUUID 角色UUID
	 * @param properties 所有变化的符号值
	 */
	public void GC_ROLE_SYMBOL_CHANGED_LONG(InputMessage data) 
	{
		int i,size;
		int roleType = data.GetShort();		
		long roleUUID = data.GetLong();
		ArrayList properties = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			KeyValuePairIntData properties_Datas= new KeyValuePairIntData();
			properties_Datas.key = data.GetInt();//符号索引
			properties_Datas.value =data.GetLong();
			properties.Add(properties_Datas);
		}
		HumanHandler.Instance().GC_ROLE_SYMBOL_CHANGED_LONG(roleType,roleUUID,properties);
	}
 
  	/**
	 * 更改名字
	 * @param name 玩家名字
	 * @param duplicateNum (如果不是重复，此字段值为null 或者0)重复多语言的ID
	 */
	public void GC_HUMAN_CHANGE_NAME(InputMessage data) 
	{
		string name = data.GetString();		
		int duplicateNum = data.GetInt();		
		HumanHandler.Instance().GC_HUMAN_CHANGE_NAME(name,duplicateNum);
	}
 
  	/**
	 * 更改性别
	 * @param sex 性别
	 */
	public void GC_HUMAN_CHANGE_SEX(InputMessage data) 
	{
		int sex = data.GetInt();		
		HumanHandler.Instance().GC_HUMAN_CHANGE_SEX(sex);
	}
 
  	/**
	 * 更改图像 
	 * @param img 玩家图像 
	 */
	public void GC_HUMAN_CHANGE_IMG(InputMessage data) 
	{
		string img = data.GetString();		
		HumanHandler.Instance().GC_HUMAN_CHANGE_IMG(img);
	}
 
  	/**
	 * VIP等级的修改 
	 * @param vip VIP等级的修改 
	 */
	public void GC_HUMAN_CHANGE_VIP(InputMessage data) 
	{
		int vip = data.GetInt();		
		HumanHandler.Instance().GC_HUMAN_CHANGE_VIP(vip);
	}
 
  	/**
	 * 观看视频返回
	 */
	public void GC_HUMAN_VIDEO_NUM(InputMessage data) 
	{
		HumanHandler.Instance().GC_HUMAN_VIDEO_NUM();
	}
 
  	/**
	 * 修改角色年龄国际返回
	 * @param countries 国籍
	 * @param age 年龄
	 */
	public void GC_CHANEAGE_COUNTRIES(InputMessage data) 
	{
		string countries = data.GetString();		
		int age = data.GetInt();		
		HumanHandler.Instance().GC_CHANEAGE_COUNTRIES(countries,age);
	}
 
  	/**
	 * 玩家进入老虎机获取其他人信息
	 * @param humanBroadcastInfo 房间玩家列表 
	 */
	public void GC_SLOT_ROOM1(InputMessage data) 
	{
		int i,size;
		ArrayList humanBroadcastInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			HumanBroadcastInfo humanBroadcastInfo_Datas= new HumanBroadcastInfo();
			humanBroadcastInfo_Datas.playerId =data.GetLong();
			humanBroadcastInfo_Datas.img = data.GetString();//图片
			humanBroadcastInfo_Datas.level = data.GetInt();//等级
			humanBroadcastInfo_Datas.countries = data.GetString();//国籍
			humanBroadcastInfo_Datas.gold =data.GetLong();
			humanBroadcastInfo_Datas.name = data.GetString();//名字
			humanBroadcastInfo_Datas.giftId = data.GetInt();//礼品的ID没有就是0
			humanBroadcastInfo_Datas.isRequest = data.GetInt();//是否已经申请添加好友 0:未申请,1:已申请 
			humanBroadcastInfo_Datas.vipLevel = data.GetInt();//用户vip的等级
			humanBroadcastInfo_Datas.girlFlag = data.GetInt();//用户性别 1：男，2：女
			humanBroadcastInfo.Add(humanBroadcastInfo_Datas);
		}
		HumanHandler.Instance().GC_SLOT_ROOM1(humanBroadcastInfo);
	}
 
  	/**
	 * 老虎机玩家广播2
	 * @param playerId 玩家id
	 * @param img 图片
	 * @param level 等级
	 * @param countries 国籍
	 * @param name 名字
	 */
	public void GC_SLOT_ROOM2(InputMessage data) 
	{
		long playerId = data.GetLong();
		string img = data.GetString();		
		int level = data.GetInt();		
		string countries = data.GetString();		
		string name = data.GetString();		
		HumanHandler.Instance().GC_SLOT_ROOM2(playerId,img,level,countries,name);
	}
 
  	/**
	 * 老虎机玩家广播3
	 * @param playerId 玩家id
	 * @param gold 玩家筹码
	 */
	public void GC_SLOT_ROOM3(InputMessage data) 
	{
		long playerId = data.GetLong();
		long gold = data.GetLong();
		HumanHandler.Instance().GC_SLOT_ROOM3(playerId,gold);
	}
 
  	/**
	 * 老虎机玩家广播4
	 * @param playerId 玩家id
	 * @param bigAward 大奖类型 0：没有中大奖 1 2 3 4
	 */
	public void GC_SLOT_ROOM4(InputMessage data) 
	{
		long playerId = data.GetLong();
		int bigAward = data.GetInt();		
		HumanHandler.Instance().GC_SLOT_ROOM4(playerId,bigAward);
	}
 
  	/**
	 * 玩家退出老虎机玩家广播5
	 * @param playerId 玩家id
	 */
	public void GC_SLOT_ROOM5(InputMessage data) 
	{
		long playerId = data.GetLong();
		HumanHandler.Instance().GC_SLOT_ROOM5(playerId);
	}
 
  	/**
	 * 老虎机房间发礼物
	 * @param send_playerId 发送者ID
	 * @param giftId 礼物ID
	 * @param send_type 发送类型 0 个人 1 全体
	 * @param rece_playerId 接收者ID在发送类型是0的时候有效果
	 */
	public void GC_SLOT_ROOM_GIFT(InputMessage data) 
	{
		long send_playerId = data.GetLong();
		int giftId = data.GetInt();		
		int send_type = data.GetInt();		
		long rece_playerId = data.GetLong();
		HumanHandler.Instance().GC_SLOT_ROOM_GIFT(send_playerId,giftId,send_type,rece_playerId);
	}
 
  	/**
	 * 邀请朋友加入老虎机房间返回
	 * @param playerId 发出邀请的角色ID
	 * @param friendImg 邀请的好友IMG
	 * @param friendName 邀请的好友名字
	 * @param slotId 要求加入的老虎机ID
	 * @param vipLevel VIP等级
	 * @param roomId 房间ID
	 */
	public void GC_SLOT_ROOM_PLEASE(InputMessage data) 
	{
		long playerId = data.GetLong();
		string friendImg = data.GetString();		
		string friendName = data.GetString();		
		int slotId = data.GetInt();		
		int vipLevel = data.GetInt();		
		string roomId = data.GetString();		
		HumanHandler.Instance().GC_SLOT_ROOM_PLEASE(playerId,friendImg,friendName,slotId,vipLevel,roomId);
	}
 
  	/**
	 * 好友进入游戏通知
	 * @param friendId 好友ID
	 */
	public void GC_FRIEND_GAME(InputMessage data) 
	{
		long friendId = data.GetLong();
		HumanHandler.Instance().GC_FRIEND_GAME(friendId);
	}
 
  	/**
	 * 双倍经验加成
	 * @param leftTime 双倍经验的剩余时间
	 */
	public void GC_EXP_DOUBLE(InputMessage data) 
	{
		long leftTime = data.GetLong();
		HumanHandler.Instance().GC_EXP_DOUBLE(leftTime);
	}
 
  	/**
	 * 等级礼包 （多个同时展示）
	 * @param levelMoreGiftData 类型-数量
	 */
	public void GC_LEVEL_GIFT_MORE(InputMessage data) 
	{
		int i,size;
		ArrayList levelMoreGiftData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			LevelMoreGiftData levelMoreGiftData_Datas= new LevelMoreGiftData();
			levelMoreGiftData_Datas.giftType = data.GetInt();//奖励类型
			levelMoreGiftData_Datas.rewardNum =data.GetLong();
			levelMoreGiftData.Add(levelMoreGiftData_Datas);
		}
		HumanHandler.Instance().GC_LEVEL_GIFT_MORE(levelMoreGiftData);
	}
}