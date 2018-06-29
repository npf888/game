using System.Collections;

public class ClubGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_CLUB_LIST,GC_CLUB_LIST);
		register(NetMessageType.GC_CLUB_SEARCH_RESULT,GC_CLUB_SEARCH_RESULT);
		register(NetMessageType.GC_CLUB_RANKING_LIST,GC_CLUB_RANKING_LIST);
		register(NetMessageType.GC_CLUB_INFO,GC_CLUB_INFO);
		register(NetMessageType.GC_CLUB_MEMBER_LIST,GC_CLUB_MEMBER_LIST);
		register(NetMessageType.GC_CLUB_SIGN_DATA,GC_CLUB_SIGN_DATA);
		register(NetMessageType.GC_CLUB_SIGN,GC_CLUB_SIGN);
		register(NetMessageType.GC_CLUB_DONATE_DATA,GC_CLUB_DONATE_DATA);
		register(NetMessageType.GC_CLUB_DONATE,GC_CLUB_DONATE);
		register(NetMessageType.GC_CLUB_NOTE_LIST,GC_CLUB_NOTE_LIST);
		register(NetMessageType.GC_CLUB_INVATE_LIST,GC_CLUB_INVATE_LIST);
		register(NetMessageType.GC_CLUB_JOIN_RESULT,GC_CLUB_JOIN_RESULT);
		register(NetMessageType.GC_CLUB_APPLY_LIST,GC_CLUB_APPLY_LIST);
		register(NetMessageType.GC_CLUB_APPLY_OP,GC_CLUB_APPLY_OP);
		register(NetMessageType.GC_CLUB_INVATE,GC_CLUB_INVATE);
		register(NetMessageType.GC_CLUB_JOIN2,GC_CLUB_JOIN2);
		register(NetMessageType.GC_CLUB_EDIT,GC_CLUB_EDIT);
		register(NetMessageType.GC_CLUB_CHANGE_NAME,GC_CLUB_CHANGE_NAME);
		register(NetMessageType.GC_CLUB_LEAVE,GC_CLUB_LEAVE);
		register(NetMessageType.GC_CLUB_KICK,GC_CLUB_KICK);
		register(NetMessageType.GC_CLUB_PROMATE,GC_CLUB_PROMATE);
		register(NetMessageType.GC_CLUB_INFO_GET,GC_CLUB_INFO_GET);
		register(NetMessageType.GC_CLUB_NOTE_DELETE,GC_CLUB_NOTE_DELETE);
		register(NetMessageType.GC_CLUB_GET_GIFT,GC_CLUB_GET_GIFT);
		register(NetMessageType.GC_CLUB_TANHE_STATE,GC_CLUB_TANHE_STATE);
		register(NetMessageType.GC_CLUB_TANHE_VOTE,GC_CLUB_TANHE_VOTE);
	}
 
  	/**
	 * 获取俱乐部列表
	 * @param list 俱乐部排行
	 */
	public void GC_CLUB_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList list = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ClubListUnit list_Datas= new ClubListUnit();
			list_Datas.clubId = data.GetString();//俱乐部id
			list_Datas.ico = data.GetInt();//图标id
			list_Datas.name = data.GetString();//名字
			list_Datas.type = data.GetInt();//类型
			list_Datas.level = data.GetInt();//等级
			list_Datas.limit = data.GetInt();//限制
			list_Datas.num = data.GetInt();//人数
			list_Datas.maxNum = data.GetInt();//最大人数
			list_Datas.huoyue = data.GetInt();//活跃度
			list_Datas.gongxian = data.GetInt();//贡献
			list_Datas.applied = data.GetInt();//是否申请过  申请过: 1
			list.Add(list_Datas);
		}
		ClubHandler.Instance().GC_CLUB_LIST(list);
	}
 
  	/**
	 * 俱乐部搜索结果
	 * @param list 俱乐部搜索结果
	 */
	public void GC_CLUB_SEARCH_RESULT(InputMessage data) 
	{
		int i,size;
		ArrayList list = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ClubListUnit list_Datas= new ClubListUnit();
			list_Datas.clubId = data.GetString();//俱乐部id
			list_Datas.ico = data.GetInt();//图标id
			list_Datas.name = data.GetString();//名字
			list_Datas.type = data.GetInt();//类型
			list_Datas.level = data.GetInt();//等级
			list_Datas.limit = data.GetInt();//限制
			list_Datas.num = data.GetInt();//人数
			list_Datas.maxNum = data.GetInt();//最大人数
			list_Datas.huoyue = data.GetInt();//活跃度
			list_Datas.gongxian = data.GetInt();//贡献
			list_Datas.applied = data.GetInt();//是否申请过  申请过: 1
			list.Add(list_Datas);
		}
		ClubHandler.Instance().GC_CLUB_SEARCH_RESULT(list);
	}
 
  	/**
	 * 俱乐部赛季排行
	 * @param opType 1 活跃帮   2贡献榜
	 * @param list 俱乐部排行
	 * @param self 自己的俱乐部信息
	 * @param selfRank 排名
	 */
	public void GC_CLUB_RANKING_LIST(InputMessage data) 
	{
		int i,size;
		int opType = data.GetInt();		
		ArrayList list = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ClubListUnit list_Datas= new ClubListUnit();
			list_Datas.clubId = data.GetString();//俱乐部id
			list_Datas.ico = data.GetInt();//图标id
			list_Datas.name = data.GetString();//名字
			list_Datas.type = data.GetInt();//类型
			list_Datas.level = data.GetInt();//等级
			list_Datas.limit = data.GetInt();//限制
			list_Datas.num = data.GetInt();//人数
			list_Datas.maxNum = data.GetInt();//最大人数
			list_Datas.huoyue = data.GetInt();//活跃度
			list_Datas.gongxian = data.GetInt();//贡献
			list_Datas.applied = data.GetInt();//是否申请过  申请过: 1
			list.Add(list_Datas);
		}
		ClubListUnit self = new ClubListUnit();
		self.clubId = data.GetString();//俱乐部id
		self.ico = data.GetInt();//图标id
		self.name = data.GetString();//名字
		self.type = data.GetInt();//类型
		self.level = data.GetInt();//等级
		self.limit = data.GetInt();//限制
		self.num = data.GetInt();//人数
		self.maxNum = data.GetInt();//最大人数
		self.huoyue = data.GetInt();//活跃度
		self.gongxian = data.GetInt();//贡献
		self.applied = data.GetInt();//是否申请过  申请过: 1
		int selfRank = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_RANKING_LIST(opType,list,self,selfRank);
	}
 
  	/**
	 * 获取俱乐部信息
	 * @param clubInfo 俱乐部信息
	 */
	public void GC_CLUB_INFO(InputMessage data) 
	{
		ClubInfoUnit clubInfo = new ClubInfoUnit();
		int j;
		clubInfo.id = data.GetString();//id
		clubInfo.name = data.GetString();//名字
		clubInfo.ico = data.GetInt();//ico
		clubInfo.level = data.GetInt();//等级
		clubInfo.progress = data.GetInt();//等级进度
		clubInfo.notice = data.GetString();//公告
		clubInfo.huoyue = data.GetInt();//赛季活跃点
		clubInfo.money = data.GetInt();//资产
		clubInfo.male = data.GetInt();//男
		clubInfo.female = data.GetInt();//女
		clubInfo.rankHuoYue = data.GetInt();//俱乐部活跃排名
		clubInfo.rankGongXian = data.GetInt();//俱乐部贡献排名
		clubInfo.clubType = data.GetInt();//类型
		clubInfo.limit = data.GetInt();//段位
		clubInfo.zhiwu = data.GetInt();//自己的职务
		clubInfo.seasonEndSecond = data.GetLong();//到赛季结束的时间秒
		ArrayList additionalIco = new ArrayList();
		int additionalIcoSize = data.GetShort();
		for(j=0; j<additionalIcoSize; j++)
		{
			string additionalIco_Datas = data.GetString();//额外解锁的图标
			additionalIco.Add(additionalIco_Datas);
		}
		clubInfo.additionalIco = additionalIco;
		ClubHandler.Instance().GC_CLUB_INFO(clubInfo);
	}
 
  	/**
	 * 获取俱乐部成员列表
	 * @param list 如果操作成功 成员变化后的信息 
	 */
	public void GC_CLUB_MEMBER_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList list = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ClubMemberListUnit list_Datas= new ClubMemberListUnit();
			list_Datas.playerId =data.GetLong();
			list_Datas.name = data.GetString();//成员名字
			list_Datas.ico = data.GetString();//ico
			list_Datas.level = data.GetInt();//成员等级
			list_Datas.country = data.GetString();//国籍
			list_Datas.zhiwu = data.GetInt();//职务
			list_Datas.gongxian = data.GetInt();//个人贡献
			list_Datas.huoyue = data.GetInt();//个人活跃度
			list_Datas.online = data.GetInt();//是否在线
			list_Datas.inGame = data.GetInt();//是否游戏中
			list_Datas.logoutTime =data.GetLong();
			list_Datas.tanheState = data.GetInt();//不可弹劾:0 可弹劾:1 弹劾进行中:2 弹劾成功:3 
			list_Datas.agree = data.GetInt();//同意人数
			list_Datas.refuse = data.GetInt();//拒绝人数
			list_Datas.selfState = data.GetInt();//个人状态 1 同意 2 拒绝 0 未表态
			list_Datas.vipLevel = data.GetInt();//用户vip的等级
			list_Datas.girlFlag = data.GetInt();//用户性别 1：男，2：女
			list.Add(list_Datas);
		}
		ClubHandler.Instance().GC_CLUB_MEMBER_LIST(list);
	}
 
  	/**
	 * 俱乐签到信息
	 * @param canSign 是否可以签到
	 * @param nextSignTime 若不能签到这个是知道下次可签到需要持续的时间毫秒
	 */
	public void GC_CLUB_SIGN_DATA(InputMessage data) 
	{
		int canSign = data.GetInt();		
		long nextSignTime = data.GetLong();
		ClubHandler.Instance().GC_CLUB_SIGN_DATA(canSign,nextSignTime);
	}
 
  	/**
	 * 俱乐签到
	 * @param ret 0 成功 1失败
	 */
	public void GC_CLUB_SIGN(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_SIGN(ret);
	}
 
  	/**
	 * 俱乐部捐献信息
	 * @param canDonate 是否可以捐献
	 * @param nextSignTime 若不能捐献这个是知道下次可捐献需要持续的时间毫秒
	 */
	public void GC_CLUB_DONATE_DATA(InputMessage data) 
	{
		int canDonate = data.GetInt();		
		long nextSignTime = data.GetLong();
		ClubHandler.Instance().GC_CLUB_DONATE_DATA(canDonate,nextSignTime);
	}
 
  	/**
	 * 俱乐捐献
	 * @param ret 0 成功 1失败
	 */
	public void GC_CLUB_DONATE(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_DONATE(ret);
	}
 
  	/**
	 * 俱乐留言列表
	 * @param opType 操作类型 1 全部  2 增加  3 删除   4 更新 （可能用不上）
	 * @param clubNote 俱乐部留言板
	 */
	public void GC_CLUB_NOTE_LIST(InputMessage data) 
	{
		int i,size;
		int opType = data.GetInt();		
		ArrayList clubNote = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ClubNoteUnit clubNote_Datas= new ClubNoteUnit();
			clubNote_Datas.noteId = data.GetString();//留言id
			clubNote_Datas.playerId =data.GetLong();
			clubNote_Datas.playerName = data.GetString();//玩家名字
			clubNote_Datas.img = data.GetString();//头像
			clubNote_Datas.guoji = data.GetString();//国籍
			clubNote_Datas.level =data.GetLong();
			clubNote_Datas.noteType = data.GetInt();//类型 0 常规 1 礼物
			clubNote_Datas.content = data.GetString();//内容
			clubNote_Datas.giftId = data.GetInt();//礼物id
			clubNote_Datas.alreadyGet = data.GetInt();//1已经领取 0 未领取
			clubNote_Datas.zhiwu = data.GetInt();//职务 1 主席  2副主席 3主管4成员
			clubNote_Datas.time =data.GetLong();
			clubNote.Add(clubNote_Datas);
		}
		ClubHandler.Instance().GC_CLUB_NOTE_LIST(opType,clubNote);
	}
 
  	/**
	 * 获取俱乐留邀请
	 * @param list 邀请列表 
	 */
	public void GC_CLUB_INVATE_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList list = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ClubInvateUnit list_Datas= new ClubInvateUnit();
			list_Datas.clubId = data.GetString();//俱乐部id
			list_Datas.ico = data.GetInt();//图标id
			list_Datas.name = data.GetString();//名字
			list_Datas.type = data.GetInt();//类型
			list_Datas.level = data.GetInt();//等级
			list_Datas.limit = data.GetInt();//限制
			list_Datas.num = data.GetInt();//人数
			list_Datas.maxNum = data.GetInt();//最大人数
			list_Datas.huoyue = data.GetInt();//活跃度
			list_Datas.createTime =data.GetLong();
			list.Add(list_Datas);
		}
		ClubHandler.Instance().GC_CLUB_INVATE_LIST(list);
	}
 
  	/**
	 * 俱乐部邀请/拒绝结果
	 * @param clubId 要删除的俱乐部id
	 */
	public void GC_CLUB_JOIN_RESULT(InputMessage data) 
	{
		string clubId = data.GetString();		
		ClubHandler.Instance().GC_CLUB_JOIN_RESULT(clubId);
	}
 
  	/**
	 * 获取俱乐留申请
	 * @param opType 类型 0 all  1 增加  2 删除  
	 * @param list 申请列表
	 */
	public void GC_CLUB_APPLY_LIST(InputMessage data) 
	{
		int i,size;
		int opType = data.GetInt();		
		ArrayList list = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ClubApplyUnit list_Datas= new ClubApplyUnit();
			list_Datas.playerId =data.GetLong();
			list_Datas.playerName = data.GetString();//申请人名字
			list_Datas.img = data.GetString();//头像
			list_Datas.country = data.GetString();//国籍
			list_Datas.level =data.GetLong();
			list_Datas.time =data.GetLong();
			list.Add(list_Datas);
		}
		ClubHandler.Instance().GC_CLUB_APPLY_LIST(opType,list);
	}
 
  	/**
	 * 获取俱乐申请列表
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_APPLY_OP(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_APPLY_OP(ret);
	}
 
  	/**
	 * 获取俱乐申请列表
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_INVATE(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_INVATE(ret);
	}
 
  	/**
	 * 从俱乐部列表加入俱乐部
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_JOIN2(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_JOIN2(ret);
	}
 
  	/**
	 * 设置俱乐部
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_EDIT(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_EDIT(ret);
	}
 
  	/**
	 * 设置俱乐部
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_CHANGE_NAME(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_CHANGE_NAME(ret);
	}
 
  	/**
	 * 离开俱乐部
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_LEAVE(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_LEAVE(ret);
	}
 
  	/**
	 * 踢出俱乐部
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_KICK(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_KICK(ret);
	}
 
  	/**
	 * 授权
	 * @param ret 类型 0 成功 1 失败
	 * @param info 如果操作成功 成员变化后的信息 
	 */
	public void GC_CLUB_PROMATE(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubMemberListUnit info = new ClubMemberListUnit();
		info.playerId = data.GetLong();//成员id
		info.name = data.GetString();//成员名字
		info.ico = data.GetString();//ico
		info.level = data.GetInt();//成员等级
		info.country = data.GetString();//国籍
		info.zhiwu = data.GetInt();//职务
		info.gongxian = data.GetInt();//个人贡献
		info.huoyue = data.GetInt();//个人活跃度
		info.online = data.GetInt();//是否在线
		info.inGame = data.GetInt();//是否游戏中
		info.logoutTime = data.GetLong();//上次下线时间
		info.tanheState = data.GetInt();//不可弹劾:0 可弹劾:1 弹劾进行中:2 弹劾成功:3 
		info.agree = data.GetInt();//同意人数
		info.refuse = data.GetInt();//拒绝人数
		info.selfState = data.GetInt();//个人状态 1 同意 2 拒绝 0 未表态
		info.vipLevel = data.GetInt();//用户vip的等级
		info.girlFlag = data.GetInt();//用户性别 1：男，2：女
		ClubHandler.Instance().GC_CLUB_PROMATE(ret,info);
	}
 
  	/**
	 * 获取俱乐部信息
	 * @param clubInfo 俱乐部信息get
	 */
	public void GC_CLUB_INFO_GET(InputMessage data) 
	{
		ClubInfoGetUnit clubInfo = new ClubInfoGetUnit();
		clubInfo.id = data.GetString();//id
		clubInfo.name = data.GetString();//名字
		clubInfo.ico = data.GetInt();//ico
		clubInfo.level = data.GetInt();//等级
		clubInfo.progress = data.GetInt();//等级进度
		clubInfo.notice = data.GetString();//公告
		clubInfo.huoyue = data.GetInt();//赛季活跃点
		clubInfo.money = data.GetInt();//资产
		clubInfo.male = data.GetInt();//男
		clubInfo.female = data.GetInt();//女
		clubInfo.rankHuoYue = data.GetInt();//俱乐部活跃排名
		clubInfo.rankGongXian = data.GetInt();//俱乐部贡献排名
		clubInfo.clubType = data.GetInt();//类型
		clubInfo.limit = data.GetInt();//段位
		ClubHandler.Instance().GC_CLUB_INFO_GET(clubInfo);
	}
 
  	/**
	 * 俱乐部留言删除
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_NOTE_DELETE(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_NOTE_DELETE(ret);
	}
 
  	/**
	 * 俱乐部留言礼物
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_GET_GIFT(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_GET_GIFT(ret);
	}
 
  	/**
	 * 弹劾状态
	 * @param result 不可弹劾:0 可弹劾:1 弹劾进行中:2 弹劾成功:3 弹劾失败：4
	 * @param agree 同意人数
	 * @param refuse 拒绝人数
	 * @param selfState 个人状态 1 同意 2 拒绝 0 未表态
	 */
	public void GC_CLUB_TANHE_STATE(InputMessage data) 
	{
		int result = data.GetInt();		
		int agree = data.GetInt();		
		int refuse = data.GetInt();		
		int selfState = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_TANHE_STATE(result,agree,refuse,selfState);
	}
 
  	/**
	 * 俱乐部弹劾拒绝
	 * @param ret 类型 0 成功 1 失败
	 */
	public void GC_CLUB_TANHE_VOTE(InputMessage data) 
	{
		int ret = data.GetInt();		
		ClubHandler.Instance().GC_CLUB_TANHE_VOTE(ret);
	}
}