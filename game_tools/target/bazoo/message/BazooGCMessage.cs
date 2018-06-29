using System.Collections;

public class BazooGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_BAZOO_CHANGE_NAME,GC_BAZOO_CHANGE_NAME);
		register(NetMessageType.GC_BAZOO_HEART_BEAT,GC_BAZOO_HEART_BEAT);
		register(NetMessageType.GC_MODE_CHOSE,GC_MODE_CHOSE);
		register(NetMessageType.GC_ROOM_CREATE,GC_ROOM_CREATE);
		register(NetMessageType.GC_ROOM_PRI_SEARCH,GC_ROOM_PRI_SEARCH);
		register(NetMessageType.GC_ROOM_PRI_List,GC_ROOM_PRI_List);
		register(NetMessageType.GC_ROOM_PRI_JOIN,GC_ROOM_PRI_JOIN);
		register(NetMessageType.GC_STATE_ROOM_ENTER,GC_STATE_ROOM_ENTER);
		register(NetMessageType.GC_STATE_ROOM_MATCHING,GC_STATE_ROOM_MATCHING);
		register(NetMessageType.GC_STATE_ROOM_READY,GC_STATE_ROOM_READY);
		register(NetMessageType.GC_STATE_ROOM_ROUND_BEGIN,GC_STATE_ROOM_ROUND_BEGIN);
		register(NetMessageType.GC_STATE_ROOM_ROUND_TURN,GC_STATE_ROOM_ROUND_TURN);
		register(NetMessageType.GC_STATE_ROOM_CALL_DICE,GC_STATE_ROOM_CALL_DICE);
		register(NetMessageType.GC_STATE_ROOM_ROUND_OPEN,GC_STATE_ROOM_ROUND_OPEN);
		register(NetMessageType.GC_STATE_ROOM_ROUND_GUESS,GC_STATE_ROOM_ROUND_GUESS);
		register(NetMessageType.GC_STATE_ROOM_ROUND_RESULT,GC_STATE_ROOM_ROUND_RESULT);
		register(NetMessageType.GC_STATE_ROOM_SINGLE_SWING_BEGIN,GC_STATE_ROOM_SINGLE_SWING_BEGIN);
		register(NetMessageType.GC_STATE_ROOM_SINGLE_SWING_END,GC_STATE_ROOM_SINGLE_SWING_END);
		register(NetMessageType.GC_STATE_ROOM_SHOW_HAND_ALL_SWING,GC_STATE_ROOM_SHOW_HAND_ALL_SWING);
		register(NetMessageType.GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING,GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING);
		register(NetMessageType.GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT,GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT);
		register(NetMessageType.GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL,GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL);
		register(NetMessageType.GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE,GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE);
		register(NetMessageType.GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT,GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT);
		register(NetMessageType.GC_STATE_ROOM_BLACK_WHITE_END,GC_STATE_ROOM_BLACK_WHITE_END);
		register(NetMessageType.GC_ROOM_BE_REMOVEED,GC_ROOM_BE_REMOVEED);
		register(NetMessageType.GC_ROOM_ENTER_NOT_ALLOW,GC_ROOM_ENTER_NOT_ALLOW);
		register(NetMessageType.GC_ROOM_MATCHEDING,GC_ROOM_MATCHEDING);
		register(NetMessageType.GC_ROOM_MATCHED,GC_ROOM_MATCHED);
		register(NetMessageType.GC_ROOM_INIT,GC_ROOM_INIT);
		register(NetMessageType.GC_ROOM_ENTER,GC_ROOM_ENTER);
		register(NetMessageType.GC_ROOM_STATE,GC_ROOM_STATE);
		register(NetMessageType.GC_ROOM_OUT,GC_ROOM_OUT);
		register(NetMessageType.GC_DICE_UNIFY_SWING,GC_DICE_UNIFY_SWING);
		register(NetMessageType.GC_ROBOT_DICE_UNIFY_SWING,GC_ROBOT_DICE_UNIFY_SWING);
		register(NetMessageType.GC_ROBOT_WHICH_ROOM_TO_GOIN,GC_ROBOT_WHICH_ROOM_TO_GOIN);
		register(NetMessageType.GC_DICE_USER_SHOULD_SWING,GC_DICE_USER_SHOULD_SWING);
		register(NetMessageType.GC_DICE_SINGLE_SWING,GC_DICE_SINGLE_SWING);
		register(NetMessageType.GC_TALK_BIG,GC_TALK_BIG);
		register(NetMessageType.GC_ROB_OPEN,GC_ROB_OPEN);
		register(NetMessageType.GC_GUESS_OPEN,GC_GUESS_OPEN);
		register(NetMessageType.GC_GUESS_BIG_SMALL,GC_GUESS_BIG_SMALL);
		register(NetMessageType.GC_END_COUNT,GC_END_COUNT);
		register(NetMessageType.GC_COW_UNIFY_SWING,GC_COW_UNIFY_SWING);
		register(NetMessageType.GC_COW_SINGLE_SWING_BEGIN,GC_COW_SINGLE_SWING_BEGIN);
		register(NetMessageType.GC_COW_SINGLE_SWING,GC_COW_SINGLE_SWING);
		register(NetMessageType.GC_COW_SINGLE_SWING_END,GC_COW_SINGLE_SWING_END);
		register(NetMessageType.GC_COW_END_UNIFY_SWING,GC_COW_END_UNIFY_SWING);
		register(NetMessageType.GC_SHOW_HAND_UNIFY_SWING,GC_SHOW_HAND_UNIFY_SWING);
		register(NetMessageType.GC_SHOW_HAND_LITTLE_SWING,GC_SHOW_HAND_LITTLE_SWING);
		register(NetMessageType.GC_SHOW_HAND_SINGLE_SWICH,GC_SHOW_HAND_SINGLE_SWICH);
		register(NetMessageType.GC_SHOW_HAND_SINGLE_SWICH_CANCEL,GC_SHOW_HAND_SINGLE_SWICH_CANCEL);
		register(NetMessageType.GC_SHOW_HAND_SINGLE_SWING,GC_SHOW_HAND_SINGLE_SWING);
		register(NetMessageType.GC_SHOW_HAND_END_COUNT,GC_SHOW_HAND_END_COUNT);
		register(NetMessageType.GC_BLACK_WHITE_ALL_SWING,GC_BLACK_WHITE_ALL_SWING);
		register(NetMessageType.GC_BLACK_WHITE_WHO_TURN,GC_BLACK_WHITE_WHO_TURN);
		register(NetMessageType.GC_BLACK_WHITE_CALL_NUM,GC_BLACK_WHITE_CALL_NUM);
		register(NetMessageType.GC_BLACK_WHITE_END_COUNT,GC_BLACK_WHITE_END_COUNT);
		register(NetMessageType.GC_BAZOO_HALL_STATUS,GC_BAZOO_HALL_STATUS);
		register(NetMessageType.GC_BAZOO_MAGIC_FACE,GC_BAZOO_MAGIC_FACE);
		register(NetMessageType.GC_BAZOO_BOQU,GC_BAZOO_BOQU);
	}
 
  	/**
	 * 修改昵称 返回
	 * @param nickname 返回用户改的名称
	 */
	public void GC_BAZOO_CHANGE_NAME(InputMessage data) 
	{
		string nickname = data.GetString();		
		BazooHandler.Instance().GC_BAZOO_CHANGE_NAME(nickname);
	}
 
  	/**
	 * 心跳
	 */
	public void GC_BAZOO_HEART_BEAT(InputMessage data) 
	{
		BazooHandler.Instance().GC_BAZOO_HEART_BEAT();
	}
 
  	/**
	 * 选择模式 返回数据
	 * @param betTotalNum bet场 对应的人数信息
	 */
	public void GC_MODE_CHOSE(InputMessage data) 
	{
		int i,size;
		ArrayList betTotalNum = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BetTotalNum betTotalNum_Datas= new BetTotalNum();
			betTotalNum_Datas.bet = data.GetInt();//倍数
			betTotalNum_Datas.totalNum =data.GetLong();
			betTotalNum.Add(betTotalNum_Datas);
		}
		BazooHandler.Instance().GC_MODE_CHOSE(betTotalNum);
	}
 
  	/**
	 * 创建房间（创建的房间都是私人房间）
	 * @param isSuccess 创建房间是否成功，0：成功，1：失败
	 */
	public void GC_ROOM_CREATE(InputMessage data) 
	{
		int isSuccess = data.GetInt();		
		BazooHandler.Instance().GC_ROOM_CREATE(isSuccess);
	}
 
  	/**
	 * 搜索私人房间（根据房间号,返回）
	 * @param roomNumber 房间号
	 */
	public void GC_ROOM_PRI_SEARCH(InputMessage data) 
	{
		string roomNumber = data.GetString();		
		BazooHandler.Instance().GC_ROOM_PRI_SEARCH(roomNumber);
	}
 
  	/**
	 * 所有的房间的返回
	 * @param priRoomData 所有的房间
	 */
	public void GC_ROOM_PRI_List(InputMessage data) 
	{
		int i,size;
		ArrayList priRoomData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			PriRoomData priRoomData_Datas= new PriRoomData();
			priRoomData_Datas.roomNumber = data.GetString();//房间号
			priRoomData_Datas.creater = data.GetString();//创建者
			priRoomData_Datas.createrPassportId =data.GetLong();
			priRoomData_Datas.flag = data.GetInt();//性别 0：女，1：男
			priRoomData_Datas.vip = data.GetInt();//vip等级
			priRoomData_Datas.modeType = data.GetInt();//房间的模式  1：经典模式，2：牛牛模式，3：梭哈模式
			priRoomData_Datas.bet = data.GetInt();//底注
			priRoomData_Datas.img = data.GetString();//头像
			priRoomData_Datas.numTotalNum = data.GetString();//房间当前人数/房间总人数
			priRoomData_Datas.isNeedPassword = data.GetInt();//是否需要密码：0需要， 1不需要
			priRoomData.Add(priRoomData_Datas);
		}
		BazooHandler.Instance().GC_ROOM_PRI_List(priRoomData);
	}
 
  	/**
	 * 加入私人房间 返回
	 * @param modeType 模式
	 * @param isSuccess 创建房间是否成功，0：成功，1：失败
	 */
	public void GC_ROOM_PRI_JOIN(InputMessage data) 
	{
		int modeType = data.GetInt();		
		int isSuccess = data.GetInt();		
		BazooHandler.Instance().GC_ROOM_PRI_JOIN(modeType,isSuccess);
	}
 
  	/**
	 * 根据状态返回消息 0
	 * @param status 新一轮 的状态 
	 */
	public void GC_STATE_ROOM_ENTER(InputMessage data) 
	{
		int status = data.GetInt();		
		BazooHandler.Instance().GC_STATE_ROOM_ENTER(status);
	}
 
  	/**
	 * 根据状态返回消息 1
	 * @param status 匹配状态 
	 */
	public void GC_STATE_ROOM_MATCHING(InputMessage data) 
	{
		int status = data.GetInt();		
		BazooHandler.Instance().GC_STATE_ROOM_MATCHING(status);
	}
 
  	/**
	 * 根据状态返回消息 2
	 * @param status 倒计时  还有多长时间 开始 统一摇色子
	 * @param leftSecond 倒计时  还剩几秒
	 */
	public void GC_STATE_ROOM_READY(InputMessage data) 
	{
		int status = data.GetInt();		
		int leftSecond = data.GetInt();		
		BazooHandler.Instance().GC_STATE_ROOM_READY(status,leftSecond);
	}
 
  	/**
	 * 根据状态返回消息 3
	 * @param status 收到统一摇色子的消息 之后的状态
	 */
	public void GC_STATE_ROOM_ROUND_BEGIN(InputMessage data) 
	{
		int status = data.GetInt();		
		BazooHandler.Instance().GC_STATE_ROOM_ROUND_BEGIN(status);
	}
 
  	/**
	 * 根据状态返回消息4
	 * @param status 没有叫号 ，处于等待状态
	 * @param whoTurnPassportId 谁正在等待（那个人的ID）
	 * @param leftSecond 等待剩余时间
	 */
	public void GC_STATE_ROOM_ROUND_TURN(InputMessage data) 
	{
		int status = data.GetInt();		
		long whoTurnPassportId = data.GetLong();
		int leftSecond = data.GetInt();		
		BazooHandler.Instance().GC_STATE_ROOM_ROUND_TURN(status,whoTurnPassportId,leftSecond);
	}
 
  	/**
	 * 根据状态返回消息 5
	 * @param status 已经叫号（和上边 的 5 循环切换 状态）
	 */
	public void GC_STATE_ROOM_CALL_DICE(InputMessage data) 
	{
		int status = data.GetInt();		
		BazooHandler.Instance().GC_STATE_ROOM_CALL_DICE(status);
	}
 
  	/**
	 * 根据状态返回消息 6
	 * @param status 抢开之后的状态
	 * @param robPassportId 抢开的人的ID
	 * @param robMultiple 抢开倍数
	 */
	public void GC_STATE_ROOM_ROUND_OPEN(InputMessage data) 
	{
		int status = data.GetInt();		
		long robPassportId = data.GetLong();
		int robMultiple = data.GetInt();		
		BazooHandler.Instance().GC_STATE_ROOM_ROUND_OPEN(status,robPassportId,robMultiple);
	}
 
  	/**
	 * 根据状态返回消息 7
	 * @param status 竞猜的状态
	 */
	public void GC_STATE_ROOM_ROUND_GUESS(InputMessage data) 
	{
		int status = data.GetInt();		
		BazooHandler.Instance().GC_STATE_ROOM_ROUND_GUESS(status);
	}
 
  	/**
	 * 根据状态返回消息 8 或 11
	 * @param status 结算 中 的状态 
	 * @param endCountInfo 结算返回 每个人的信息
	 */
	public void GC_STATE_ROOM_ROUND_RESULT(InputMessage data) 
	{
		int i,size;
		int status = data.GetInt();		
		ArrayList endCountInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			EndCountInfo endCountInfo_Datas= new EndCountInfo();
			int endCountInfo_j;
			endCountInfo_Datas.passportId =data.GetLong();
			endCountInfo_Datas.multiple = data.GetInt();//所翻的倍数
			endCountInfo_Datas.gold =data.GetLong();
			endCountInfo_Datas.name = data.GetString();//牛牛 模式专用：用户摇到号的名称 例如  ：五小牛 、豹子  等等
				ArrayList endCountInfo_diceValues = new ArrayList();
				int endCountInfo_diceValuesSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_diceValuesSize; endCountInfo_j++){
					int endCountInfo_diceValues_Datas = data.GetInt();//摇出色子的值得集合
					endCountInfo_diceValues.Add(endCountInfo_diceValues_Datas);
				}
			endCountInfo_Datas.diceValues = endCountInfo_diceValues;
				ArrayList endCountInfo_winPassportId = new ArrayList();
				int endCountInfo_winPassportIdSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winPassportIdSize; endCountInfo_j++){
					int endCountInfo_winPassportId_Datas = data.GetInt();//赢得人的ID
					endCountInfo_winPassportId.Add(endCountInfo_winPassportId_Datas);
				}
			endCountInfo_Datas.winPassportId = endCountInfo_winPassportId;
				ArrayList endCountInfo_winMultiple = new ArrayList();
				int endCountInfo_winMultipleSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winMultipleSize; endCountInfo_j++){
					int endCountInfo_winMultiple_Datas = data.GetInt();//倍数
					endCountInfo_winMultiple.Add(endCountInfo_winMultiple_Datas);
				}
			endCountInfo_Datas.winMultiple = endCountInfo_winMultiple;
			endCountInfo_Datas.personTotalGold =data.GetLong();
			endCountInfo.Add(endCountInfo_Datas);
		}
		BazooHandler.Instance().GC_STATE_ROOM_ROUND_RESULT(status,endCountInfo);
	}
 
  	/**
	 * 根据状态返回消息 9
	 * @param status 重摇开始 的状态 
	 */
	public void GC_STATE_ROOM_SINGLE_SWING_BEGIN(InputMessage data) 
	{
		int status = data.GetInt();		
		BazooHandler.Instance().GC_STATE_ROOM_SINGLE_SWING_BEGIN(status);
	}
 
  	/**
	 * 根据状态返回消息 10
	 * @param status 重摇结束  的状态 
	 * @param diceInfo 重摇结束,用户该轮流看每个人的色子值
	 */
	public void GC_STATE_ROOM_SINGLE_SWING_END(InputMessage data) 
	{
		int i,size;
		int status = data.GetInt();		
		ArrayList diceInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			DiceInfo diceInfo_Datas= new DiceInfo();
			int diceInfo_j;
			diceInfo_Datas.passportId =data.GetLong();
				ArrayList diceInfo_diceValues = new ArrayList();
				int diceInfo_diceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_diceValuesSize; diceInfo_j++){
					int diceInfo_diceValues_Datas = data.GetInt();//色子值
					diceInfo_diceValues.Add(diceInfo_diceValues_Datas);
				}
			diceInfo_Datas.diceValues = diceInfo_diceValues;
			diceInfo_Datas.cowNameInt = data.GetInt();//几小牛
				ArrayList diceInfo_redDiceValues = new ArrayList();
				int diceInfo_redDiceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_redDiceValuesSize; diceInfo_j++){
					int diceInfo_redDiceValues_Datas = data.GetInt();//需要被标红的色子的值的集合
					diceInfo_redDiceValues.Add(diceInfo_redDiceValues_Datas);
				}
			diceInfo_Datas.redDiceValues = diceInfo_redDiceValues;
			diceInfo.Add(diceInfo_Datas);
		}
		BazooHandler.Instance().GC_STATE_ROOM_SINGLE_SWING_END(status,diceInfo);
	}
 
  	/**
	 * 根据状态返回消息 12
	 * @param status 统一摇完色子 之后 等待 几秒 的 时间状态 
	 * @param leftTimes 剩余次数
	 * @param leftSecond 剩余时间
	 * @param diceInfo 重摇结束,用户该轮流看每个人的色子值
	 */
	public void GC_STATE_ROOM_SHOW_HAND_ALL_SWING(InputMessage data) 
	{
		int i,size;
		int status = data.GetInt();		
		int leftTimes = data.GetInt();		
		long leftSecond = data.GetLong();
		ArrayList diceInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			DiceInfo diceInfo_Datas= new DiceInfo();
			int diceInfo_j;
			diceInfo_Datas.passportId =data.GetLong();
				ArrayList diceInfo_diceValues = new ArrayList();
				int diceInfo_diceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_diceValuesSize; diceInfo_j++){
					int diceInfo_diceValues_Datas = data.GetInt();//色子值
					diceInfo_diceValues.Add(diceInfo_diceValues_Datas);
				}
			diceInfo_Datas.diceValues = diceInfo_diceValues;
			diceInfo_Datas.cowNameInt = data.GetInt();//几小牛
				ArrayList diceInfo_redDiceValues = new ArrayList();
				int diceInfo_redDiceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_redDiceValuesSize; diceInfo_j++){
					int diceInfo_redDiceValues_Datas = data.GetInt();//需要被标红的色子的值的集合
					diceInfo_redDiceValues.Add(diceInfo_redDiceValues_Datas);
				}
			diceInfo_Datas.redDiceValues = diceInfo_redDiceValues;
			diceInfo.Add(diceInfo_Datas);
		}
		BazooHandler.Instance().GC_STATE_ROOM_SHOW_HAND_ALL_SWING(status,leftTimes,leftSecond,diceInfo);
	}
 
  	/**
	 * 根据状态返回消息 13
	 * @param status 每个人单独摇 的状态（大部分 时间都在这个状态） 
	 * @param lastPassportId 最后一个需要
	 * @param passportId 当前摇色子的用户的ID
	 * @param leftTimes 剩余次数
	 * @param leftSecond 剩余时间
	 * @param diceInfo 其他人的色子的数组
	 * @param showHandBet 结算返回 每个人的信息
	 */
	public void GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING(InputMessage data) 
	{
		int i,size;
		int status = data.GetInt();		
		long lastPassportId = data.GetLong();
		ArrayList passportId = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long passportId_Datas = data.GetLong();
			passportId.Add(passportId_Datas);
		}
		int leftTimes = data.GetInt();		
		long leftSecond = data.GetLong();
		ArrayList diceInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			DiceInfo diceInfo_Datas= new DiceInfo();
			int diceInfo_j;
			diceInfo_Datas.passportId =data.GetLong();
				ArrayList diceInfo_diceValues = new ArrayList();
				int diceInfo_diceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_diceValuesSize; diceInfo_j++){
					int diceInfo_diceValues_Datas = data.GetInt();//色子值
					diceInfo_diceValues.Add(diceInfo_diceValues_Datas);
				}
			diceInfo_Datas.diceValues = diceInfo_diceValues;
			diceInfo_Datas.cowNameInt = data.GetInt();//几小牛
				ArrayList diceInfo_redDiceValues = new ArrayList();
				int diceInfo_redDiceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_redDiceValuesSize; diceInfo_j++){
					int diceInfo_redDiceValues_Datas = data.GetInt();//需要被标红的色子的值的集合
					diceInfo_redDiceValues.Add(diceInfo_redDiceValues_Datas);
				}
			diceInfo_Datas.redDiceValues = diceInfo_redDiceValues;
			diceInfo.Add(diceInfo_Datas);
		}
		ArrayList showHandBet = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ShowHandBet showHandBet_Datas= new ShowHandBet();
			showHandBet_Datas.passportId =data.GetLong();
			showHandBet_Datas.bet = data.GetInt();//上面某个用户  倍数
			showHandBet_Datas.gold =data.GetLong();
			showHandBet_Datas.personTotalGold =data.GetLong();
			showHandBet.Add(showHandBet_Datas);
		}
		BazooHandler.Instance().GC_STATE_ROOM_SHOW_HAND_SINGLE_SWING(status,lastPassportId,passportId,leftTimes,leftSecond,diceInfo,showHandBet);
	}
 
  	/**
	 * 根据状态返回消息 14
	 * @param status 摇完 到进入下一局 之前的状态
	 * @param endCountInfo 结算返回 每个人的信息
	 */
	public void GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT(InputMessage data) 
	{
		int i,size;
		int status = data.GetInt();		
		ArrayList endCountInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			EndCountInfo endCountInfo_Datas= new EndCountInfo();
			int endCountInfo_j;
			endCountInfo_Datas.passportId =data.GetLong();
			endCountInfo_Datas.multiple = data.GetInt();//所翻的倍数
			endCountInfo_Datas.gold =data.GetLong();
			endCountInfo_Datas.name = data.GetString();//牛牛 模式专用：用户摇到号的名称 例如  ：五小牛 、豹子  等等
				ArrayList endCountInfo_diceValues = new ArrayList();
				int endCountInfo_diceValuesSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_diceValuesSize; endCountInfo_j++){
					int endCountInfo_diceValues_Datas = data.GetInt();//摇出色子的值得集合
					endCountInfo_diceValues.Add(endCountInfo_diceValues_Datas);
				}
			endCountInfo_Datas.diceValues = endCountInfo_diceValues;
				ArrayList endCountInfo_winPassportId = new ArrayList();
				int endCountInfo_winPassportIdSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winPassportIdSize; endCountInfo_j++){
					int endCountInfo_winPassportId_Datas = data.GetInt();//赢得人的ID
					endCountInfo_winPassportId.Add(endCountInfo_winPassportId_Datas);
				}
			endCountInfo_Datas.winPassportId = endCountInfo_winPassportId;
				ArrayList endCountInfo_winMultiple = new ArrayList();
				int endCountInfo_winMultipleSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winMultipleSize; endCountInfo_j++){
					int endCountInfo_winMultiple_Datas = data.GetInt();//倍数
					endCountInfo_winMultiple.Add(endCountInfo_winMultiple_Datas);
				}
			endCountInfo_Datas.winMultiple = endCountInfo_winMultiple;
			endCountInfo_Datas.personTotalGold =data.GetLong();
			endCountInfo.Add(endCountInfo_Datas);
		}
		BazooHandler.Instance().GC_STATE_ROOM_SHOW_HAND_ROUND_RESULT(status,endCountInfo);
	}
 
  	/**
	 * 根据状态返回消息 15
	 * @param whoCallPassportId 是谁叫的号
	 * @param diceType 叫的是 什么（1:红，2：黑，3：单，4：双）
	 * @param killNum 几杀
	 * @param multiple 变化的倍数
	 * @param blackWhiteBet 结算返回 每个人的信息
	 * @param blackWhiteDiceInfo 所有人色子的剩余值
	 */
	public void GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL(InputMessage data) 
	{
		int i,size;
		long whoCallPassportId = data.GetLong();
		int diceType = data.GetInt();		
		int killNum = data.GetInt();		
		int multiple = data.GetInt();		
		ArrayList blackWhiteBet = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BlackWhiteBet blackWhiteBet_Datas= new BlackWhiteBet();
			int blackWhiteBet_j;
			blackWhiteBet_Datas.passportId =data.GetLong();
				ArrayList blackWhiteBet_bet = new ArrayList();
				int blackWhiteBet_betSize = data.GetShort();
				for(blackWhiteBet_j=0; blackWhiteBet_j<blackWhiteBet_betSize; blackWhiteBet_j++){
					int blackWhiteBet_bet_Datas = data.GetInt();//所有赢了 的人 的倍数
					blackWhiteBet_bet.Add(blackWhiteBet_bet_Datas);
				}
			blackWhiteBet_Datas.bet = blackWhiteBet_bet;
				ArrayList blackWhiteBet_winPassportIds = new ArrayList();
				int blackWhiteBet_winPassportIdsSize = data.GetShort();
				for(blackWhiteBet_j=0; blackWhiteBet_j<blackWhiteBet_winPassportIdsSize; blackWhiteBet_j++){
					int blackWhiteBet_winPassportIds_Datas = data.GetInt();//所有赢了 的人的Id
					blackWhiteBet_winPassportIds.Add(blackWhiteBet_winPassportIds_Datas);
				}
			blackWhiteBet_Datas.winPassportIds = blackWhiteBet_winPassportIds;
			blackWhiteBet_Datas.gold =data.GetLong();
			blackWhiteBet.Add(blackWhiteBet_Datas);
		}
		ArrayList blackWhiteDiceInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BlackWhiteDiceInfo blackWhiteDiceInfo_Datas= new BlackWhiteDiceInfo();
			int blackWhiteDiceInfo_j;
			blackWhiteDiceInfo_Datas.passportId =data.GetLong();
			blackWhiteDiceInfo_Datas.isOut = data.GetInt();//0:未出局，1：已出局
				ArrayList blackWhiteDiceInfo_diceValues = new ArrayList();
				int blackWhiteDiceInfo_diceValuesSize = data.GetShort();
				for(blackWhiteDiceInfo_j=0; blackWhiteDiceInfo_j<blackWhiteDiceInfo_diceValuesSize; blackWhiteDiceInfo_j++){
					int blackWhiteDiceInfo_diceValues_Datas = data.GetInt();//每一把被移除 之前所有 的色子值
					blackWhiteDiceInfo_diceValues.Add(blackWhiteDiceInfo_diceValues_Datas);
				}
			blackWhiteDiceInfo_Datas.diceValues = blackWhiteDiceInfo_diceValues;
				ArrayList blackWhiteDiceInfo_removeDiceValues = new ArrayList();
				int blackWhiteDiceInfo_removeDiceValuesSize = data.GetShort();
				for(blackWhiteDiceInfo_j=0; blackWhiteDiceInfo_j<blackWhiteDiceInfo_removeDiceValuesSize; blackWhiteDiceInfo_j++){
					int blackWhiteDiceInfo_removeDiceValues_Datas = data.GetInt();//被移除的色子 index的值
					blackWhiteDiceInfo_removeDiceValues.Add(blackWhiteDiceInfo_removeDiceValues_Datas);
				}
			blackWhiteDiceInfo_Datas.removeDiceValues = blackWhiteDiceInfo_removeDiceValues;
			blackWhiteDiceInfo.Add(blackWhiteDiceInfo_Datas);
		}
		BazooHandler.Instance().GC_STATE_ROOM_BLACK_WHITE_SOME_ONE_CALL(whoCallPassportId,diceType,killNum,multiple,blackWhiteBet,blackWhiteDiceInfo);
	}
 
  	/**
	 * 根据状态返回消息 16
	 * @param whoTurnPassportId 用户ID
	 * @param leftSecond 剩余时间
	 */
	public void GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE(InputMessage data) 
	{
		long whoTurnPassportId = data.GetLong();
		long leftSecond = data.GetLong();
		BazooHandler.Instance().GC_STATE_ROOM_BLACK_WHITE_WAIT_SOME_ONE(whoTurnPassportId,leftSecond);
	}
 
  	/**
	 * 根据状态返回消息 17
	 * @param multiple 变化的倍数
	 * @param passportId 所有需要重摇的用户的ID
	 * @param leftDiceNum 所有需要重摇的用户剩余的色子数
	 */
	public void GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT(InputMessage data) 
	{
		int i,size;
		int multiple = data.GetInt();		
		ArrayList passportId = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long passportId_Datas = data.GetLong();
			passportId.Add(passportId_Datas);
		}
		ArrayList leftDiceNum = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int leftDiceNum_Datas = data.GetInt();
			leftDiceNum.Add(leftDiceNum_Datas);
		}
		BazooHandler.Instance().GC_STATE_ROOM_BLACK_WHITE_SWING_LEFT(multiple,passportId,leftDiceNum);
	}
 
  	/**
	 * 根据状态返回消息 18
	 * @param endCountInfo 结算返回 每个人的信息
	 */
	public void GC_STATE_ROOM_BLACK_WHITE_END(InputMessage data) 
	{
		int i,size;
		ArrayList endCountInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			EndCountInfo endCountInfo_Datas= new EndCountInfo();
			int endCountInfo_j;
			endCountInfo_Datas.passportId =data.GetLong();
			endCountInfo_Datas.multiple = data.GetInt();//所翻的倍数
			endCountInfo_Datas.gold =data.GetLong();
			endCountInfo_Datas.name = data.GetString();//牛牛 模式专用：用户摇到号的名称 例如  ：五小牛 、豹子  等等
				ArrayList endCountInfo_diceValues = new ArrayList();
				int endCountInfo_diceValuesSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_diceValuesSize; endCountInfo_j++){
					int endCountInfo_diceValues_Datas = data.GetInt();//摇出色子的值得集合
					endCountInfo_diceValues.Add(endCountInfo_diceValues_Datas);
				}
			endCountInfo_Datas.diceValues = endCountInfo_diceValues;
				ArrayList endCountInfo_winPassportId = new ArrayList();
				int endCountInfo_winPassportIdSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winPassportIdSize; endCountInfo_j++){
					int endCountInfo_winPassportId_Datas = data.GetInt();//赢得人的ID
					endCountInfo_winPassportId.Add(endCountInfo_winPassportId_Datas);
				}
			endCountInfo_Datas.winPassportId = endCountInfo_winPassportId;
				ArrayList endCountInfo_winMultiple = new ArrayList();
				int endCountInfo_winMultipleSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winMultipleSize; endCountInfo_j++){
					int endCountInfo_winMultiple_Datas = data.GetInt();//倍数
					endCountInfo_winMultiple.Add(endCountInfo_winMultiple_Datas);
				}
			endCountInfo_Datas.winMultiple = endCountInfo_winMultiple;
			endCountInfo_Datas.personTotalGold =data.GetLong();
			endCountInfo.Add(endCountInfo_Datas);
		}
		BazooHandler.Instance().GC_STATE_ROOM_BLACK_WHITE_END(endCountInfo);
	}
 
  	/**
	 * 被踢出房间
	 */
	public void GC_ROOM_BE_REMOVEED(InputMessage data) 
	{
		BazooHandler.Instance().GC_ROOM_BE_REMOVEED();
	}
 
  	/**
	 * 不允许 进入房间
	 * @param langId 多语言ID
	 * @param paramsList 参数列表
	 */
	public void GC_ROOM_ENTER_NOT_ALLOW(InputMessage data) 
	{
		int i,size;
		int langId = data.GetInt();		
		ArrayList paramsList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			string paramsList_Datas = data.GetString();
			paramsList.Add(paramsList_Datas);
		}
		BazooHandler.Instance().GC_ROOM_ENTER_NOT_ALLOW(langId,paramsList);
	}
 
  	/**
	 * 进入房间  匹配中   当前只有一个用户（用户自己）的时候 发送此消息 告诉用户 应该处于等待状态
	 */
	public void GC_ROOM_MATCHEDING(InputMessage data) 
	{
		BazooHandler.Instance().GC_ROOM_MATCHEDING();
	}
 
  	/**
	 * 匹配到玩家
	 * @param waitTime 等待开始时间（秒）
	 */
	public void GC_ROOM_MATCHED(InputMessage data) 
	{
		int waitTime = data.GetInt();		
		BazooHandler.Instance().GC_ROOM_MATCHED(waitTime);
	}
 
  	/**
	 * 返回其他人的信息，大于等于 3个人的时候
	 * @param pubOrPri 公共场 or 私人场   0:公共 ，1:私人
	 * @param lastPassportId 最后一个叫号的ID：如果是0就不是最后一个
	 * @param roomNum 房间号
	 * @param waitTime 等待时间（只有在第一次开始的时候）
	 * @param turnWait 每个用户叫号 间隔
	 * @param guessWait 竞猜时间  间隔
	 * @param onePoint 万能符是否叫过 0没有叫过，1叫过
	 * @param cowSwingToBegin 牛牛模式 统一摇完色子之后 到 重摇色子之前
	 * @param cowOneRoundTime 牛牛模式 一局 的时间
	 * @param cowLookDiceValueTime 牛牛模式 一局 用户 重摇结束到 结算之前 会有段时间 去看别人的色子
	 * @param cowEndCountTime 牛牛模式 结算时间
	 * @param reShakingTimes 梭哈模式 一个人代表的次数
	 * @param showHandWhoTurn 梭哈模式 该轮到谁摇 色子
	 * @param showHandShakeTime 梭哈模式  用户摇 色子的时间
	 * @param showHandEndToStart 梭哈模式  结束到开始
	 * @param bankPassportId 牛牛模式  的庄家ID
	 * @param endCountInfo 结算返回 每个人的信息
	 * @param showHandBet 结算返回 每个人的信息
	 * @param returnPlayerInfo 其他人的信息
	 */
	public void GC_ROOM_INIT(InputMessage data) 
	{
		int i,size;
		int pubOrPri = data.GetInt();		
		long lastPassportId = data.GetLong();
		string roomNum = data.GetString();		
		int waitTime = data.GetInt();		
		int turnWait = data.GetInt();		
		int guessWait = data.GetInt();		
		int onePoint = data.GetInt();		
		int cowSwingToBegin = data.GetInt();		
		int cowOneRoundTime = data.GetInt();		
		int cowLookDiceValueTime = data.GetInt();		
		int cowEndCountTime = data.GetInt();		
		int reShakingTimes = data.GetInt();		
		int showHandWhoTurn = data.GetInt();		
		int showHandShakeTime = data.GetInt();		
		int showHandEndToStart = data.GetInt();		
		long bankPassportId = data.GetLong();
		ArrayList endCountInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			EndCountInfo endCountInfo_Datas= new EndCountInfo();
			int endCountInfo_j;
			endCountInfo_Datas.passportId =data.GetLong();
			endCountInfo_Datas.multiple = data.GetInt();//所翻的倍数
			endCountInfo_Datas.gold =data.GetLong();
			endCountInfo_Datas.name = data.GetString();//牛牛 模式专用：用户摇到号的名称 例如  ：五小牛 、豹子  等等
				ArrayList endCountInfo_diceValues = new ArrayList();
				int endCountInfo_diceValuesSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_diceValuesSize; endCountInfo_j++){
					int endCountInfo_diceValues_Datas = data.GetInt();//摇出色子的值得集合
					endCountInfo_diceValues.Add(endCountInfo_diceValues_Datas);
				}
			endCountInfo_Datas.diceValues = endCountInfo_diceValues;
				ArrayList endCountInfo_winPassportId = new ArrayList();
				int endCountInfo_winPassportIdSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winPassportIdSize; endCountInfo_j++){
					int endCountInfo_winPassportId_Datas = data.GetInt();//赢得人的ID
					endCountInfo_winPassportId.Add(endCountInfo_winPassportId_Datas);
				}
			endCountInfo_Datas.winPassportId = endCountInfo_winPassportId;
				ArrayList endCountInfo_winMultiple = new ArrayList();
				int endCountInfo_winMultipleSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winMultipleSize; endCountInfo_j++){
					int endCountInfo_winMultiple_Datas = data.GetInt();//倍数
					endCountInfo_winMultiple.Add(endCountInfo_winMultiple_Datas);
				}
			endCountInfo_Datas.winMultiple = endCountInfo_winMultiple;
			endCountInfo_Datas.personTotalGold =data.GetLong();
			endCountInfo.Add(endCountInfo_Datas);
		}
		ArrayList showHandBet = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ShowHandBet showHandBet_Datas= new ShowHandBet();
			showHandBet_Datas.passportId =data.GetLong();
			showHandBet_Datas.bet = data.GetInt();//上面某个用户  倍数
			showHandBet_Datas.gold =data.GetLong();
			showHandBet_Datas.personTotalGold =data.GetLong();
			showHandBet.Add(showHandBet_Datas);
		}
		ArrayList returnPlayerInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ReturnPlayerInfo returnPlayerInfo_Datas= new ReturnPlayerInfo();
			int returnPlayerInfo_j;
			returnPlayerInfo_Datas.passportId =data.GetLong();
			returnPlayerInfo_Datas.name = data.GetString();//用户的名称
			returnPlayerInfo_Datas.girlFlag = data.GetInt();//性别 0:女       1:男
			returnPlayerInfo_Datas.userStatus = data.GetInt();//用户当前的状态 0：观战   未参与(因为刚进入房间 别的用户 正在玩，所以此时只能观战)    1：参与      2:纯粹的观战模式 （进去就是看看 什么其他的也不干）
			returnPlayerInfo_Datas.curGold =data.GetLong();
			returnPlayerInfo_Datas.headImg = data.GetString();//头像信息
			returnPlayerInfo_Datas.diceContainer = data.GetString();//色钟 不同状态 的色子 有不同的色钟
			returnPlayerInfo_Datas.seat = data.GetInt();//位置
			returnPlayerInfo_Datas.winTimes = data.GetInt();//几连胜
			returnPlayerInfo_Datas.vipLevel = data.GetInt();//vip等级
			returnPlayerInfo_Datas.num = data.GetInt();//用户最后叫号 色子的个数
			returnPlayerInfo_Datas.value = data.GetInt();//用户最后叫号 色子的值
			returnPlayerInfo_Datas.diceType = data.GetInt();//色子的int名
				ArrayList returnPlayerInfo_diceValues = new ArrayList();
				int returnPlayerInfo_diceValuesSize = data.GetShort();
				for(returnPlayerInfo_j=0; returnPlayerInfo_j<returnPlayerInfo_diceValuesSize; returnPlayerInfo_j++){
					int returnPlayerInfo_diceValues_Datas = data.GetInt();//色子的值
					returnPlayerInfo_diceValues.Add(returnPlayerInfo_diceValues_Datas);
				}
			returnPlayerInfo_Datas.diceValues = returnPlayerInfo_diceValues;
				ArrayList returnPlayerInfo_redDiceValues = new ArrayList();
				int returnPlayerInfo_redDiceValuesSize = data.GetShort();
				for(returnPlayerInfo_j=0; returnPlayerInfo_j<returnPlayerInfo_redDiceValuesSize; returnPlayerInfo_j++){
					int returnPlayerInfo_redDiceValues_Datas = data.GetInt();//红色 色子的值
					returnPlayerInfo_redDiceValues.Add(returnPlayerInfo_redDiceValues_Datas);
				}
			returnPlayerInfo_Datas.redDiceValues = returnPlayerInfo_redDiceValues;
			returnPlayerInfo_Datas.clockImg = data.GetString();//色钟图片
			returnPlayerInfo_Datas.clockItemId = data.GetInt();//色钟itemId
			returnPlayerInfo.Add(returnPlayerInfo_Datas);
		}
		BazooHandler.Instance().GC_ROOM_INIT(pubOrPri,lastPassportId,roomNum,waitTime,turnWait,guessWait,onePoint,cowSwingToBegin,cowOneRoundTime,cowLookDiceValueTime,cowEndCountTime,reShakingTimes,showHandWhoTurn,showHandShakeTime,showHandEndToStart,bankPassportId,endCountInfo,showHandBet,returnPlayerInfo);
	}
 
  	/**
	 * 进入房间  有新人进入了，那些正在房间里的人  会收到 这个消息
	 * @param returnPlayerInfo 其他人的信息
	 */
	public void GC_ROOM_ENTER(InputMessage data) 
	{
		int i,size;
		ArrayList returnPlayerInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ReturnPlayerInfo returnPlayerInfo_Datas= new ReturnPlayerInfo();
			int returnPlayerInfo_j;
			returnPlayerInfo_Datas.passportId =data.GetLong();
			returnPlayerInfo_Datas.name = data.GetString();//用户的名称
			returnPlayerInfo_Datas.girlFlag = data.GetInt();//性别 0:女       1:男
			returnPlayerInfo_Datas.userStatus = data.GetInt();//用户当前的状态 0：观战   未参与(因为刚进入房间 别的用户 正在玩，所以此时只能观战)    1：参与      2:纯粹的观战模式 （进去就是看看 什么其他的也不干）
			returnPlayerInfo_Datas.curGold =data.GetLong();
			returnPlayerInfo_Datas.headImg = data.GetString();//头像信息
			returnPlayerInfo_Datas.diceContainer = data.GetString();//色钟 不同状态 的色子 有不同的色钟
			returnPlayerInfo_Datas.seat = data.GetInt();//位置
			returnPlayerInfo_Datas.winTimes = data.GetInt();//几连胜
			returnPlayerInfo_Datas.vipLevel = data.GetInt();//vip等级
			returnPlayerInfo_Datas.num = data.GetInt();//用户最后叫号 色子的个数
			returnPlayerInfo_Datas.value = data.GetInt();//用户最后叫号 色子的值
			returnPlayerInfo_Datas.diceType = data.GetInt();//色子的int名
				ArrayList returnPlayerInfo_diceValues = new ArrayList();
				int returnPlayerInfo_diceValuesSize = data.GetShort();
				for(returnPlayerInfo_j=0; returnPlayerInfo_j<returnPlayerInfo_diceValuesSize; returnPlayerInfo_j++){
					int returnPlayerInfo_diceValues_Datas = data.GetInt();//色子的值
					returnPlayerInfo_diceValues.Add(returnPlayerInfo_diceValues_Datas);
				}
			returnPlayerInfo_Datas.diceValues = returnPlayerInfo_diceValues;
				ArrayList returnPlayerInfo_redDiceValues = new ArrayList();
				int returnPlayerInfo_redDiceValuesSize = data.GetShort();
				for(returnPlayerInfo_j=0; returnPlayerInfo_j<returnPlayerInfo_redDiceValuesSize; returnPlayerInfo_j++){
					int returnPlayerInfo_redDiceValues_Datas = data.GetInt();//红色 色子的值
					returnPlayerInfo_redDiceValues.Add(returnPlayerInfo_redDiceValues_Datas);
				}
			returnPlayerInfo_Datas.redDiceValues = returnPlayerInfo_redDiceValues;
			returnPlayerInfo_Datas.clockImg = data.GetString();//色钟图片
			returnPlayerInfo_Datas.clockItemId = data.GetInt();//色钟itemId
			returnPlayerInfo.Add(returnPlayerInfo_Datas);
		}
		BazooHandler.Instance().GC_ROOM_ENTER(returnPlayerInfo);
	}
 
  	/**
	 * 房间状态 变化
	 * @param roomState 房间状态
	 */
	public void GC_ROOM_STATE(InputMessage data) 
	{
		int roomState = data.GetInt();		
		BazooHandler.Instance().GC_ROOM_STATE(roomState);
	}
 
  	/**
	 * 某个用户 退出房间 当前 房间内所有用户 收到 此退出房间消息 
	 * @param beRemovedPassportIds 每次退出房间时,被移除房间 的所有的用户ID
	 */
	public void GC_ROOM_OUT(InputMessage data) 
	{
		int i,size;
		ArrayList beRemovedPassportIds = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long beRemovedPassportIds_Datas = data.GetLong();
			beRemovedPassportIds.Add(beRemovedPassportIds_Datas);
		}
		BazooHandler.Instance().GC_ROOM_OUT(beRemovedPassportIds);
	}
 
  	/**
	 * 统一摇色子得返回(由服务器来统一摇色子  同时表示新一轮开始 )
	 * @param passportId 用户ID
	 * @param diceValues 色子的值
	 */
	public void GC_DICE_UNIFY_SWING(InputMessage data) 
	{
		int i,size;
		long passportId = data.GetLong();
		ArrayList diceValues = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int diceValues_Datas = data.GetInt();
			diceValues.Add(diceValues_Datas);
		}
		BazooHandler.Instance().GC_DICE_UNIFY_SWING(passportId,diceValues);
	}
 
  	/**
	 * 统一摇色子返回 给机器人所有人的 所有的值
	 * @param diceInfo 机器人看到所有人的值
	 */
	public void GC_ROBOT_DICE_UNIFY_SWING(InputMessage data) 
	{
		int i,size;
		ArrayList diceInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			DiceInfo diceInfo_Datas= new DiceInfo();
			int diceInfo_j;
			diceInfo_Datas.passportId =data.GetLong();
				ArrayList diceInfo_diceValues = new ArrayList();
				int diceInfo_diceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_diceValuesSize; diceInfo_j++){
					int diceInfo_diceValues_Datas = data.GetInt();//色子值
					diceInfo_diceValues.Add(diceInfo_diceValues_Datas);
				}
			diceInfo_Datas.diceValues = diceInfo_diceValues;
			diceInfo_Datas.cowNameInt = data.GetInt();//几小牛
				ArrayList diceInfo_redDiceValues = new ArrayList();
				int diceInfo_redDiceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_redDiceValuesSize; diceInfo_j++){
					int diceInfo_redDiceValues_Datas = data.GetInt();//需要被标红的色子的值的集合
					diceInfo_redDiceValues.Add(diceInfo_redDiceValues_Datas);
				}
			diceInfo_Datas.redDiceValues = diceInfo_redDiceValues;
			diceInfo.Add(diceInfo_Datas);
		}
		BazooHandler.Instance().GC_ROBOT_DICE_UNIFY_SWING(diceInfo);
	}
 
  	/**
	 * 告诉机器人 要进入哪个房间 
	 * @param passportId 用户ID
	 * @param roomNumber 要进入哪个房间
	 */
	public void GC_ROBOT_WHICH_ROOM_TO_GOIN(InputMessage data) 
	{
		long passportId = data.GetLong();
		string roomNumber = data.GetString();		
		BazooHandler.Instance().GC_ROBOT_WHICH_ROOM_TO_GOIN(passportId,roomNumber);
	}
 
  	/**
	 * 紧接上一个接口，服务器等待一小会之后，告诉所有人谁改摇色子了shouldCallPassportId 如果等于 0 说明是重摇,不等于 0是 统一摇
	 * @param shouldCallPassportId 统一摇完之后 该哪个用户叫号
	 */
	public void GC_DICE_USER_SHOULD_SWING(InputMessage data) 
	{
		long shouldCallPassportId = data.GetLong();
		BazooHandler.Instance().GC_DICE_USER_SHOULD_SWING(shouldCallPassportId);
	}
 
  	/**
	 * 单独摇色子返回值，返回给自己的有diceValues,返回个其他人的没有 diceValues为空
	 * @param passportId 用户ID
	 * @param diceValues 色子的值
	 */
	public void GC_DICE_SINGLE_SWING(InputMessage data) 
	{
		int i,size;
		long passportId = data.GetLong();
		ArrayList diceValues = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int diceValues_Datas = data.GetInt();
			diceValues.Add(diceValues_Datas);
		}
		BazooHandler.Instance().GC_DICE_SINGLE_SWING(passportId,diceValues);
	}
 
  	/**
	 * 叫号完毕
	 * @param WhoTurnPassportId 轮到哪个人(这个人的ID)叫号了
	 * @param curPassportId 当前叫号的是哪个人
	 * @param callDiceNum 用户叫的色子的数量
	 * @param callDiceValue 用户叫的色子的值
	 * @param onePoint 万能符是否叫过 0没有叫过，1叫过
	 */
	public void GC_TALK_BIG(InputMessage data) 
	{
		long WhoTurnPassportId = data.GetLong();
		long curPassportId = data.GetLong();
		int callDiceNum = data.GetInt();		
		int callDiceValue = data.GetInt();		
		int onePoint = data.GetInt();		
		BazooHandler.Instance().GC_TALK_BIG(WhoTurnPassportId,curPassportId,callDiceNum,callDiceValue,onePoint);
	}
 
  	/**
	 * 抢开返回信息
	 * @param robPassportId 抢开的人的ID
	 * @param multiple 抢开的倍数
	 */
	public void GC_ROB_OPEN(InputMessage data) 
	{
		long robPassportId = data.GetLong();
		int multiple = data.GetInt();		
		BazooHandler.Instance().GC_ROB_OPEN(robPassportId,multiple);
	}
 
  	/**
	 * 抢开 之后 , 竞猜大小之前  前端 要等待一小段时间(动画时间)  然后在发送这个接口 通知前端 打开 竞猜的 窗口 
	 */
	public void GC_GUESS_OPEN(InputMessage data) 
	{
		BazooHandler.Instance().GC_GUESS_OPEN();
	}
 
  	/**
	 * 抢开 之后 其他人 竞猜大小 推送所有人
	 * @param passportId 当前是哪个用户(他的ID)
	 * @param agreePassportId 当前这个用户 同意了 哪个用户(他的ID)
	 */
	public void GC_GUESS_BIG_SMALL(InputMessage data) 
	{
		long passportId = data.GetLong();
		long agreePassportId = data.GetLong();
		BazooHandler.Instance().GC_GUESS_BIG_SMALL(passportId,agreePassportId);
	}
 
  	/**
	 * 结算返回
	 * @param finalDiceNum 最终 这一局 有几个 叫的色子值(个数)
	 * @param endCountInfo 结算返回 每个人的信息
	 */
	public void GC_END_COUNT(InputMessage data) 
	{
		int i,size;
		int finalDiceNum = data.GetInt();		
		ArrayList endCountInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			EndCountInfo endCountInfo_Datas= new EndCountInfo();
			int endCountInfo_j;
			endCountInfo_Datas.passportId =data.GetLong();
			endCountInfo_Datas.multiple = data.GetInt();//所翻的倍数
			endCountInfo_Datas.gold =data.GetLong();
			endCountInfo_Datas.name = data.GetString();//牛牛 模式专用：用户摇到号的名称 例如  ：五小牛 、豹子  等等
				ArrayList endCountInfo_diceValues = new ArrayList();
				int endCountInfo_diceValuesSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_diceValuesSize; endCountInfo_j++){
					int endCountInfo_diceValues_Datas = data.GetInt();//摇出色子的值得集合
					endCountInfo_diceValues.Add(endCountInfo_diceValues_Datas);
				}
			endCountInfo_Datas.diceValues = endCountInfo_diceValues;
				ArrayList endCountInfo_winPassportId = new ArrayList();
				int endCountInfo_winPassportIdSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winPassportIdSize; endCountInfo_j++){
					int endCountInfo_winPassportId_Datas = data.GetInt();//赢得人的ID
					endCountInfo_winPassportId.Add(endCountInfo_winPassportId_Datas);
				}
			endCountInfo_Datas.winPassportId = endCountInfo_winPassportId;
				ArrayList endCountInfo_winMultiple = new ArrayList();
				int endCountInfo_winMultipleSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winMultipleSize; endCountInfo_j++){
					int endCountInfo_winMultiple_Datas = data.GetInt();//倍数
					endCountInfo_winMultiple.Add(endCountInfo_winMultiple_Datas);
				}
			endCountInfo_Datas.winMultiple = endCountInfo_winMultiple;
			endCountInfo_Datas.personTotalGold =data.GetLong();
			endCountInfo.Add(endCountInfo_Datas);
		}
		BazooHandler.Instance().GC_END_COUNT(finalDiceNum,endCountInfo);
	}
 
  	/**
	 * 牛牛 模式:统一摇号返回
	 * @param oneRoundTime 一局的时间（秒）
	 * @param cowNameInt 名称用 int类型表示
	 * @param diceValues 色子的值的集合
	 * @param redDiceValues 需要被标红的色子的值的集合
	 * @param passportId 庄家的ID
	 */
	public void GC_COW_UNIFY_SWING(InputMessage data) 
	{
		int i,size;
		int oneRoundTime = data.GetInt();		
		int cowNameInt = data.GetInt();		
		ArrayList diceValues = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int diceValues_Datas = data.GetInt();
			diceValues.Add(diceValues_Datas);
		}
		ArrayList redDiceValues = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int redDiceValues_Datas = data.GetInt();
			redDiceValues.Add(redDiceValues_Datas);
		}
		long passportId = data.GetLong();
		BazooHandler.Instance().GC_COW_UNIFY_SWING(oneRoundTime,cowNameInt,diceValues,redDiceValues,passportId);
	}
 
  	/**
	 * 牛牛 模式:重摇开始
	 */
	public void GC_COW_SINGLE_SWING_BEGIN(InputMessage data) 
	{
		BazooHandler.Instance().GC_COW_SINGLE_SWING_BEGIN();
	}
 
  	/**
	 * 牛牛 模式:统一摇号返回
	 * @param passportId 用户的ID
	 * @param cowNameInt 名称用 int类型表示
	 * @param diceValues 色子的值的集合
	 * @param redDiceValues 需要被标红的色子的值的集合
	 */
	public void GC_COW_SINGLE_SWING(InputMessage data) 
	{
		int i,size;
		long passportId = data.GetLong();
		int cowNameInt = data.GetInt();		
		ArrayList diceValues = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int diceValues_Datas = data.GetInt();
			diceValues.Add(diceValues_Datas);
		}
		ArrayList redDiceValues = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int redDiceValues_Datas = data.GetInt();
			redDiceValues.Add(redDiceValues_Datas);
		}
		BazooHandler.Instance().GC_COW_SINGLE_SWING(passportId,cowNameInt,diceValues,redDiceValues);
	}
 
  	/**
	 * 牛牛 模式:单独摇号
	 * @param diceInfo 重摇结束,用户该轮流看每个人的色子值
	 */
	public void GC_COW_SINGLE_SWING_END(InputMessage data) 
	{
		int i,size;
		ArrayList diceInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			DiceInfo diceInfo_Datas= new DiceInfo();
			int diceInfo_j;
			diceInfo_Datas.passportId =data.GetLong();
				ArrayList diceInfo_diceValues = new ArrayList();
				int diceInfo_diceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_diceValuesSize; diceInfo_j++){
					int diceInfo_diceValues_Datas = data.GetInt();//色子值
					diceInfo_diceValues.Add(diceInfo_diceValues_Datas);
				}
			diceInfo_Datas.diceValues = diceInfo_diceValues;
			diceInfo_Datas.cowNameInt = data.GetInt();//几小牛
				ArrayList diceInfo_redDiceValues = new ArrayList();
				int diceInfo_redDiceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_redDiceValuesSize; diceInfo_j++){
					int diceInfo_redDiceValues_Datas = data.GetInt();//需要被标红的色子的值的集合
					diceInfo_redDiceValues.Add(diceInfo_redDiceValues_Datas);
				}
			diceInfo_Datas.redDiceValues = diceInfo_redDiceValues;
			diceInfo.Add(diceInfo_Datas);
		}
		BazooHandler.Instance().GC_COW_SINGLE_SWING_END(diceInfo);
	}
 
  	/**
	 * 牛牛 模式:结算完毕  给所有人返回
	 * @param endCountInfo 结算返回 每个人的信息
	 */
	public void GC_COW_END_UNIFY_SWING(InputMessage data) 
	{
		int i,size;
		ArrayList endCountInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			EndCountInfo endCountInfo_Datas= new EndCountInfo();
			int endCountInfo_j;
			endCountInfo_Datas.passportId =data.GetLong();
			endCountInfo_Datas.multiple = data.GetInt();//所翻的倍数
			endCountInfo_Datas.gold =data.GetLong();
			endCountInfo_Datas.name = data.GetString();//牛牛 模式专用：用户摇到号的名称 例如  ：五小牛 、豹子  等等
				ArrayList endCountInfo_diceValues = new ArrayList();
				int endCountInfo_diceValuesSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_diceValuesSize; endCountInfo_j++){
					int endCountInfo_diceValues_Datas = data.GetInt();//摇出色子的值得集合
					endCountInfo_diceValues.Add(endCountInfo_diceValues_Datas);
				}
			endCountInfo_Datas.diceValues = endCountInfo_diceValues;
				ArrayList endCountInfo_winPassportId = new ArrayList();
				int endCountInfo_winPassportIdSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winPassportIdSize; endCountInfo_j++){
					int endCountInfo_winPassportId_Datas = data.GetInt();//赢得人的ID
					endCountInfo_winPassportId.Add(endCountInfo_winPassportId_Datas);
				}
			endCountInfo_Datas.winPassportId = endCountInfo_winPassportId;
				ArrayList endCountInfo_winMultiple = new ArrayList();
				int endCountInfo_winMultipleSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winMultipleSize; endCountInfo_j++){
					int endCountInfo_winMultiple_Datas = data.GetInt();//倍数
					endCountInfo_winMultiple.Add(endCountInfo_winMultiple_Datas);
				}
			endCountInfo_Datas.winMultiple = endCountInfo_winMultiple;
			endCountInfo_Datas.personTotalGold =data.GetLong();
			endCountInfo.Add(endCountInfo_Datas);
		}
		BazooHandler.Instance().GC_COW_END_UNIFY_SWING(endCountInfo);
	}
 
  	/**
	 * 梭哈 模式:统一摇号返回
	 * @param leftTimes 剩余次数
	 * @param diceInfo 梭哈 模式：统一摇号会返回所有人的值
	 */
	public void GC_SHOW_HAND_UNIFY_SWING(InputMessage data) 
	{
		int i,size;
		int leftTimes = data.GetInt();		
		ArrayList diceInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			DiceInfo diceInfo_Datas= new DiceInfo();
			int diceInfo_j;
			diceInfo_Datas.passportId =data.GetLong();
				ArrayList diceInfo_diceValues = new ArrayList();
				int diceInfo_diceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_diceValuesSize; diceInfo_j++){
					int diceInfo_diceValues_Datas = data.GetInt();//色子值
					diceInfo_diceValues.Add(diceInfo_diceValues_Datas);
				}
			diceInfo_Datas.diceValues = diceInfo_diceValues;
			diceInfo_Datas.cowNameInt = data.GetInt();//几小牛
				ArrayList diceInfo_redDiceValues = new ArrayList();
				int diceInfo_redDiceValuesSize = data.GetShort();
				for(diceInfo_j=0; diceInfo_j<diceInfo_redDiceValuesSize; diceInfo_j++){
					int diceInfo_redDiceValues_Datas = data.GetInt();//需要被标红的色子的值的集合
					diceInfo_redDiceValues.Add(diceInfo_redDiceValues_Datas);
				}
			diceInfo_Datas.redDiceValues = diceInfo_redDiceValues;
			diceInfo.Add(diceInfo_Datas);
		}
		BazooHandler.Instance().GC_SHOW_HAND_UNIFY_SWING(leftTimes,diceInfo);
	}
 
  	/**
	 * 梭哈  模式:最小的人 摇色子
	 * @param passportId 那个点数 最低的人 去摇色子（这个人的ID）
	 */
	public void GC_SHOW_HAND_LITTLE_SWING(InputMessage data) 
	{
		int i,size;
		ArrayList passportId = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long passportId_Datas = data.GetLong();
			passportId.Add(passportId_Datas);
		}
		BazooHandler.Instance().GC_SHOW_HAND_LITTLE_SWING(passportId);
	}
 
  	/**
	 * 梭哈  模式:选择色子（某个）
	 * @param passportId 谁选择的色子
	 * @param diceIndex 用户选择的某个色子的索引
	 */
	public void GC_SHOW_HAND_SINGLE_SWICH(InputMessage data) 
	{
		long passportId = data.GetLong();
		int diceIndex = data.GetInt();		
		BazooHandler.Instance().GC_SHOW_HAND_SINGLE_SWICH(passportId,diceIndex);
	}
 
  	/**
	 * 梭哈  模式:取消 选择色子（某个）
	 * @param passportId 谁选择的色子
	 * @param diceIndex 用户  取消选择的某个色子的索引
	 */
	public void GC_SHOW_HAND_SINGLE_SWICH_CANCEL(InputMessage data) 
	{
		long passportId = data.GetLong();
		int diceIndex = data.GetInt();		
		BazooHandler.Instance().GC_SHOW_HAND_SINGLE_SWICH_CANCEL(passportId,diceIndex);
	}
 
  	/**
	 * 梭哈  模式:最小的人 摇色子
	 * @param passportId 那个点数 最低的人 去摇色子（这个人的ID）
	 * @param diceValues 将要被 重摇的  色子的值的集合
	 * @param nameInt 几小牛
	 * @param leftTimes 剩余次数
	 * @param showHandBet 结算返回 每个人的信息
	 */
	public void GC_SHOW_HAND_SINGLE_SWING(InputMessage data) 
	{
		int i,size;
		long passportId = data.GetLong();
		ArrayList diceValues = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int diceValues_Datas = data.GetInt();
			diceValues.Add(diceValues_Datas);
		}
		int nameInt = data.GetInt();		
		int leftTimes = data.GetInt();		
		ArrayList showHandBet = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			ShowHandBet showHandBet_Datas= new ShowHandBet();
			showHandBet_Datas.passportId =data.GetLong();
			showHandBet_Datas.bet = data.GetInt();//上面某个用户  倍数
			showHandBet_Datas.gold =data.GetLong();
			showHandBet_Datas.personTotalGold =data.GetLong();
			showHandBet.Add(showHandBet_Datas);
		}
		BazooHandler.Instance().GC_SHOW_HAND_SINGLE_SWING(passportId,diceValues,nameInt,leftTimes,showHandBet);
	}
 
  	/**
	 * 梭哈  模式: 最终结算
	 * @param endCountInfo 结算返回 每个人的信息
	 */
	public void GC_SHOW_HAND_END_COUNT(InputMessage data) 
	{
		int i,size;
		ArrayList endCountInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			EndCountInfo endCountInfo_Datas= new EndCountInfo();
			int endCountInfo_j;
			endCountInfo_Datas.passportId =data.GetLong();
			endCountInfo_Datas.multiple = data.GetInt();//所翻的倍数
			endCountInfo_Datas.gold =data.GetLong();
			endCountInfo_Datas.name = data.GetString();//牛牛 模式专用：用户摇到号的名称 例如  ：五小牛 、豹子  等等
				ArrayList endCountInfo_diceValues = new ArrayList();
				int endCountInfo_diceValuesSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_diceValuesSize; endCountInfo_j++){
					int endCountInfo_diceValues_Datas = data.GetInt();//摇出色子的值得集合
					endCountInfo_diceValues.Add(endCountInfo_diceValues_Datas);
				}
			endCountInfo_Datas.diceValues = endCountInfo_diceValues;
				ArrayList endCountInfo_winPassportId = new ArrayList();
				int endCountInfo_winPassportIdSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winPassportIdSize; endCountInfo_j++){
					int endCountInfo_winPassportId_Datas = data.GetInt();//赢得人的ID
					endCountInfo_winPassportId.Add(endCountInfo_winPassportId_Datas);
				}
			endCountInfo_Datas.winPassportId = endCountInfo_winPassportId;
				ArrayList endCountInfo_winMultiple = new ArrayList();
				int endCountInfo_winMultipleSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winMultipleSize; endCountInfo_j++){
					int endCountInfo_winMultiple_Datas = data.GetInt();//倍数
					endCountInfo_winMultiple.Add(endCountInfo_winMultiple_Datas);
				}
			endCountInfo_Datas.winMultiple = endCountInfo_winMultiple;
			endCountInfo_Datas.personTotalGold =data.GetLong();
			endCountInfo.Add(endCountInfo_Datas);
		}
		BazooHandler.Instance().GC_SHOW_HAND_END_COUNT(endCountInfo);
	}
 
  	/**
	 * 统一摇色子得返回(由服务器来统一摇色子  同时表示新一轮开始 )
	 * @param passportId 所有需要重摇的用户的ID
	 * @param leftDiceNum 所有需要重摇的用户剩余的色子数
	 * @param diceValues 当前用户的色子的值
	 * @param multiple 变化的倍数
	 * @param blackWhiteNum 红黑单双的数量
	 */
	public void GC_BLACK_WHITE_ALL_SWING(InputMessage data) 
	{
		int i,size;
		ArrayList passportId = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long passportId_Datas = data.GetLong();
			passportId.Add(passportId_Datas);
		}
		ArrayList leftDiceNum = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int leftDiceNum_Datas = data.GetInt();
			leftDiceNum.Add(leftDiceNum_Datas);
		}
		ArrayList diceValues = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int diceValues_Datas = data.GetInt();
			diceValues.Add(diceValues_Datas);
		}
		int multiple = data.GetInt();		
		BlackWhiteNum blackWhiteNum = new BlackWhiteNum();
		blackWhiteNum.red = data.GetInt();//红  数量
		blackWhiteNum.black = data.GetInt();//黑  数量
		blackWhiteNum.single = data.GetInt();//单 数量
		blackWhiteNum.doubles = data.GetInt();//双 数量
		BazooHandler.Instance().GC_BLACK_WHITE_ALL_SWING(passportId,leftDiceNum,diceValues,multiple,blackWhiteNum);
	}
 
  	/**
	 * 轮到谁叫号了
	 * @param whoTurnPassportId 用户ID
	 * @param leftSecond 剩余时间
	 */
	public void GC_BLACK_WHITE_WHO_TURN(InputMessage data) 
	{
		long whoTurnPassportId = data.GetLong();
		long leftSecond = data.GetLong();
		BazooHandler.Instance().GC_BLACK_WHITE_WHO_TURN(whoTurnPassportId,leftSecond);
	}
 
  	/**
	 * 每个人轮流 叫号 返回
	 * @param whoCallPassportId 是谁叫的号
	 * @param diceType 叫的是 什么（1:红，2：黑，3：单，4：双）
	 * @param killNum 几杀
	 * @param multiple 变化的倍数
	 * @param blackWhiteBet 结算返回 每个人的信息
	 * @param blackWhiteDiceInfo 所有人色子的剩余值
	 */
	public void GC_BLACK_WHITE_CALL_NUM(InputMessage data) 
	{
		int i,size;
		long whoCallPassportId = data.GetLong();
		int diceType = data.GetInt();		
		int killNum = data.GetInt();		
		int multiple = data.GetInt();		
		ArrayList blackWhiteBet = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BlackWhiteBet blackWhiteBet_Datas= new BlackWhiteBet();
			int blackWhiteBet_j;
			blackWhiteBet_Datas.passportId =data.GetLong();
				ArrayList blackWhiteBet_bet = new ArrayList();
				int blackWhiteBet_betSize = data.GetShort();
				for(blackWhiteBet_j=0; blackWhiteBet_j<blackWhiteBet_betSize; blackWhiteBet_j++){
					int blackWhiteBet_bet_Datas = data.GetInt();//所有赢了 的人 的倍数
					blackWhiteBet_bet.Add(blackWhiteBet_bet_Datas);
				}
			blackWhiteBet_Datas.bet = blackWhiteBet_bet;
				ArrayList blackWhiteBet_winPassportIds = new ArrayList();
				int blackWhiteBet_winPassportIdsSize = data.GetShort();
				for(blackWhiteBet_j=0; blackWhiteBet_j<blackWhiteBet_winPassportIdsSize; blackWhiteBet_j++){
					int blackWhiteBet_winPassportIds_Datas = data.GetInt();//所有赢了 的人的Id
					blackWhiteBet_winPassportIds.Add(blackWhiteBet_winPassportIds_Datas);
				}
			blackWhiteBet_Datas.winPassportIds = blackWhiteBet_winPassportIds;
			blackWhiteBet_Datas.gold =data.GetLong();
			blackWhiteBet.Add(blackWhiteBet_Datas);
		}
		ArrayList blackWhiteDiceInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BlackWhiteDiceInfo blackWhiteDiceInfo_Datas= new BlackWhiteDiceInfo();
			int blackWhiteDiceInfo_j;
			blackWhiteDiceInfo_Datas.passportId =data.GetLong();
			blackWhiteDiceInfo_Datas.isOut = data.GetInt();//0:未出局，1：已出局
				ArrayList blackWhiteDiceInfo_diceValues = new ArrayList();
				int blackWhiteDiceInfo_diceValuesSize = data.GetShort();
				for(blackWhiteDiceInfo_j=0; blackWhiteDiceInfo_j<blackWhiteDiceInfo_diceValuesSize; blackWhiteDiceInfo_j++){
					int blackWhiteDiceInfo_diceValues_Datas = data.GetInt();//每一把被移除 之前所有 的色子值
					blackWhiteDiceInfo_diceValues.Add(blackWhiteDiceInfo_diceValues_Datas);
				}
			blackWhiteDiceInfo_Datas.diceValues = blackWhiteDiceInfo_diceValues;
				ArrayList blackWhiteDiceInfo_removeDiceValues = new ArrayList();
				int blackWhiteDiceInfo_removeDiceValuesSize = data.GetShort();
				for(blackWhiteDiceInfo_j=0; blackWhiteDiceInfo_j<blackWhiteDiceInfo_removeDiceValuesSize; blackWhiteDiceInfo_j++){
					int blackWhiteDiceInfo_removeDiceValues_Datas = data.GetInt();//被移除的色子 index的值
					blackWhiteDiceInfo_removeDiceValues.Add(blackWhiteDiceInfo_removeDiceValues_Datas);
				}
			blackWhiteDiceInfo_Datas.removeDiceValues = blackWhiteDiceInfo_removeDiceValues;
			blackWhiteDiceInfo.Add(blackWhiteDiceInfo_Datas);
		}
		BazooHandler.Instance().GC_BLACK_WHITE_CALL_NUM(whoCallPassportId,diceType,killNum,multiple,blackWhiteBet,blackWhiteDiceInfo);
	}
 
  	/**
	 * 红黑单双  模式: 最终结算
	 * @param endCountInfo 结算返回 每个人的信息
	 */
	public void GC_BLACK_WHITE_END_COUNT(InputMessage data) 
	{
		int i,size;
		ArrayList endCountInfo = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			EndCountInfo endCountInfo_Datas= new EndCountInfo();
			int endCountInfo_j;
			endCountInfo_Datas.passportId =data.GetLong();
			endCountInfo_Datas.multiple = data.GetInt();//所翻的倍数
			endCountInfo_Datas.gold =data.GetLong();
			endCountInfo_Datas.name = data.GetString();//牛牛 模式专用：用户摇到号的名称 例如  ：五小牛 、豹子  等等
				ArrayList endCountInfo_diceValues = new ArrayList();
				int endCountInfo_diceValuesSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_diceValuesSize; endCountInfo_j++){
					int endCountInfo_diceValues_Datas = data.GetInt();//摇出色子的值得集合
					endCountInfo_diceValues.Add(endCountInfo_diceValues_Datas);
				}
			endCountInfo_Datas.diceValues = endCountInfo_diceValues;
				ArrayList endCountInfo_winPassportId = new ArrayList();
				int endCountInfo_winPassportIdSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winPassportIdSize; endCountInfo_j++){
					int endCountInfo_winPassportId_Datas = data.GetInt();//赢得人的ID
					endCountInfo_winPassportId.Add(endCountInfo_winPassportId_Datas);
				}
			endCountInfo_Datas.winPassportId = endCountInfo_winPassportId;
				ArrayList endCountInfo_winMultiple = new ArrayList();
				int endCountInfo_winMultipleSize = data.GetShort();
				for(endCountInfo_j=0; endCountInfo_j<endCountInfo_winMultipleSize; endCountInfo_j++){
					int endCountInfo_winMultiple_Datas = data.GetInt();//倍数
					endCountInfo_winMultiple.Add(endCountInfo_winMultiple_Datas);
				}
			endCountInfo_Datas.winMultiple = endCountInfo_winMultiple;
			endCountInfo_Datas.personTotalGold =data.GetLong();
			endCountInfo.Add(endCountInfo_Datas);
		}
		BazooHandler.Instance().GC_BLACK_WHITE_END_COUNT(endCountInfo);
	}
 
  	/**
	 * 大厅通知前端, 状态 是否有没做的事（会显示小红点）
	 * @param signInStatus 0:没有领取,1:已经领取
	 * @param taskNumber 任务 已完成未领取的数量
	 */
	public void GC_BAZOO_HALL_STATUS(InputMessage data) 
	{
		int signInStatus = data.GetInt();		
		int taskNumber = data.GetInt();		
		BazooHandler.Instance().GC_BAZOO_HALL_STATUS(signInStatus,taskNumber);
	}
 
  	/**
	 * 返回 魔法表情
	 * @param sendPassportId 谁发的
	 * @param receivePassportId 给谁发的
	 * @param magicFace 发送的魔法表情
	 */
	public void GC_BAZOO_MAGIC_FACE(InputMessage data) 
	{
		long sendPassportId = data.GetLong();
		long receivePassportId = data.GetLong();
		string magicFace = data.GetString();		
		BazooHandler.Instance().GC_BAZOO_MAGIC_FACE(sendPassportId,receivePassportId,magicFace);
	}
 
  	/**
	 * 博趣平台入口 返回页面（前端直接访问）
	 * @param htmlPage base64编码之后的带参数的页面
	 */
	public void GC_BAZOO_BOQU(InputMessage data) 
	{
		string htmlPage = data.GetString();		
		BazooHandler.Instance().GC_BAZOO_BOQU(htmlPage);
	}
}