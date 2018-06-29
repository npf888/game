using System.Collections;

public class TexasGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_TEXAS_LIST,GC_TEXAS_LIST);
		register(NetMessageType.GC_JOIN_TEXAS,GC_JOIN_TEXAS);
		register(NetMessageType.GC_LEAVE_TEXAS,GC_LEAVE_TEXAS);
		register(NetMessageType.GC_SYNC_JOIN_TEXAS,GC_SYNC_JOIN_TEXAS);
		register(NetMessageType.GC_TEXAS_SMALL_BLIND,GC_TEXAS_SMALL_BLIND);
		register(NetMessageType.GC_TEXAS_BIG_BLIND,GC_TEXAS_BIG_BLIND);
		register(NetMessageType.GC_TEXAS_BANKER_POS,GC_TEXAS_BANKER_POS);
		register(NetMessageType.GC_TEXAS_BUTTOM_DEAL,GC_TEXAS_BUTTOM_DEAL);
		register(NetMessageType.GC_TEXAS_PLAYER_TURN,GC_TEXAS_PLAYER_TURN);
		register(NetMessageType.GC_TEXAS_FLOP,GC_TEXAS_FLOP);
		register(NetMessageType.GC_TEXAS_TURN,GC_TEXAS_TURN);
		register(NetMessageType.GC_TEXAS_RIVER,GC_TEXAS_RIVER);
		register(NetMessageType.GC_TEXAS_LOOK,GC_TEXAS_LOOK);
		register(NetMessageType.GC_TEXAS_FOLLOW,GC_TEXAS_FOLLOW);
		register(NetMessageType.GC_TEXAS_ADD_BET,GC_TEXAS_ADD_BET);
		register(NetMessageType.GC_TEXAS_ALL_IN,GC_TEXAS_ALL_IN);
		register(NetMessageType.GC_TEXAS_GIVE_UP,GC_TEXAS_GIVE_UP);
		register(NetMessageType.GC_TEXAS_SIDE_POOL,GC_TEXAS_SIDE_POOL);
		register(NetMessageType.GC_TEXAS_ROOM_INFO,GC_TEXAS_ROOM_INFO);
		register(NetMessageType.GC_TEXAS_SETTLE,GC_TEXAS_SETTLE);
		register(NetMessageType.GC_TEXAS_SETTLE_GIVEUP,GC_TEXAS_SETTLE_GIVEUP);
		register(NetMessageType.GC_TEXAS_PREPARE_RESTART,GC_TEXAS_PREPARE_RESTART);
		register(NetMessageType.GC_TEXAS_COINS_SYNC,GC_TEXAS_COINS_SYNC);
		register(NetMessageType.GC_TEXAS_SEAT,GC_TEXAS_SEAT);
		register(NetMessageType.GC_TEXAS_EXIT,GC_TEXAS_EXIT);
		register(NetMessageType.GC_HUMAN_TEXAS_INFO_DATA,GC_HUMAN_TEXAS_INFO_DATA);
		register(NetMessageType.GC_TEXAS_COMPLEMENT,GC_TEXAS_COMPLEMENT);
		register(NetMessageType.GC_TEXAS_TIPS,GC_TEXAS_TIPS);
		register(NetMessageType.GC_TEXAS_PEOPLE_SETTING,GC_TEXAS_PEOPLE_SETTING);
		register(NetMessageType.GC_TEXAS_HAND_CARD,GC_TEXAS_HAND_CARD);
		register(NetMessageType.GC_TEXAS_SNG_LIST,GC_TEXAS_SNG_LIST);
		register(NetMessageType.GC_SNG_RANK,GC_SNG_RANK);
		register(NetMessageType.GC_HUMAN_TEXAS_SNG_INFO_DATA,GC_HUMAN_TEXAS_SNG_INFO_DATA);
		register(NetMessageType.GC_TEXAS_VIP_LIST,GC_TEXAS_VIP_LIST);
		register(NetMessageType.GC_JOIN_TEXAS_VIP_FAILED,GC_JOIN_TEXAS_VIP_FAILED);
		register(NetMessageType.GC_TEXAS_CLEAR_TABLE,GC_TEXAS_CLEAR_TABLE);
		register(NetMessageType.GC_TEXAS_COMPLEMENT_NUM,GC_TEXAS_COMPLEMENT_NUM);
		register(NetMessageType.GC_HUMAN_TEXAS_INFO_DATA_SEARCH,GC_HUMAN_TEXAS_INFO_DATA_SEARCH);
		register(NetMessageType.GC_HUMAN_TEXAS_EXP,GC_HUMAN_TEXAS_EXP);
	}
 
  	/**
	 * 德州房间类型列表
	 * @param roomTypeList 房间类型列表
	 */
	public void GC_TEXAS_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList roomTypeList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			TexasRoomTypeInfoData roomTypeList_Datas= new TexasRoomTypeInfoData();
			roomTypeList_Datas.typeId = data.GetInt();//房间类型id
			roomTypeList_Datas.roomTag = data.GetInt();//房间标签
			roomTypeList_Datas.openUp = data.GetInt();//开启
			roomTypeList_Datas.smallBlind = data.GetInt();//小盲注
			roomTypeList_Datas.minCarry = data.GetInt();//最小携带量
			roomTypeList_Datas.maxCarry = data.GetInt();//最大携带量
			roomTypeList_Datas.roomNum = data.GetInt();//人数
			roomTypeList_Datas.totalNum = data.GetInt();//总人数
			roomTypeList_Datas.openLv = data.GetInt();//进入等级
			roomTypeList_Datas.list = data.GetInt();//显示类型 1 2 3 4
			roomTypeList_Datas.handsel =data.GetLong();
			roomTypeList.Add(roomTypeList_Datas);
		}
		TexasHandler.Instance().GC_TEXAS_LIST(roomTypeList);
	}
 
  	/**
	 * 加入德州
	 * @param playerList 玩家列表
	 * @param roomType 房间类型
	 * @param texasRoomEnum sng或普通房
	 */
	public void GC_JOIN_TEXAS(InputMessage data) 
	{
		int i,size;
		ArrayList playerList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			TexasRoomPlayerInfoData playerList_Datas= new TexasRoomPlayerInfoData();
			playerList_Datas.playerId =data.GetLong();
			playerList_Datas.name = data.GetString();//玩家名字
			playerList_Datas.img = data.GetString();//玩家图片
			playerList_Datas.playerState = data.GetInt();//玩家状态
			playerList_Datas.coins =data.GetLong();
			playerList_Datas.vip = data.GetInt();//vip
			playerList_Datas.pos = data.GetInt();//位置
			playerList_Datas.currentBet =data.GetLong();
			playerList_Datas.allBet =data.GetLong();
			playerList.Add(playerList_Datas);
		}
		int roomType = data.GetInt();		
		int texasRoomEnum = data.GetInt();		
		TexasHandler.Instance().GC_JOIN_TEXAS(playerList,roomType,texasRoomEnum);
	}
 
  	/**
	 * 离开德州房间
	 * @param playerId 离开id
	 * @param ifNoGold 是否没钱
	 */
	public void GC_LEAVE_TEXAS(InputMessage data) 
	{
		long playerId = data.GetLong();
		int ifNoGold = data.GetInt();		
		TexasHandler.Instance().GC_LEAVE_TEXAS(playerId,ifNoGold);
	}
 
  	/**
	 * 同步加入德州
	 * @param playerJoin 玩家列表
	 */
	public void GC_SYNC_JOIN_TEXAS(InputMessage data) 
	{
		TexasRoomPlayerInfoData playerJoin = new TexasRoomPlayerInfoData();
		playerJoin.playerId = data.GetLong();//玩家id
		playerJoin.name = data.GetString();//玩家名字
		playerJoin.img = data.GetString();//玩家图片
		playerJoin.playerState = data.GetInt();//玩家状态
		playerJoin.coins = data.GetLong();//筹码
		playerJoin.vip = data.GetInt();//vip
		playerJoin.pos = data.GetInt();//位置
		playerJoin.currentBet = data.GetLong();//当前押注
		playerJoin.allBet = data.GetLong();//总押注
		TexasHandler.Instance().GC_SYNC_JOIN_TEXAS(playerJoin);
	}
 
  	/**
	 * 庄家位置
	 * @param pos 位置
	 * @param smallBlind 小盲注
	 */
	public void GC_TEXAS_SMALL_BLIND(InputMessage data) 
	{
		int pos = data.GetInt();		
		long smallBlind = data.GetLong();
		TexasHandler.Instance().GC_TEXAS_SMALL_BLIND(pos,smallBlind);
	}
 
  	/**
	 * 庄家位置
	 * @param pos 庄家位置
	 * @param bigBlind 大盲注
	 */
	public void GC_TEXAS_BIG_BLIND(InputMessage data) 
	{
		int pos = data.GetInt();		
		long bigBlind = data.GetLong();
		TexasHandler.Instance().GC_TEXAS_BIG_BLIND(pos,bigBlind);
	}
 
  	/**
	 * 庄家位置
	 * @param pos 庄家位置
	 */
	public void GC_TEXAS_BANKER_POS(InputMessage data) 
	{
		int pos = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_BANKER_POS(pos);
	}
 
  	/**
	 * 底牌发送
	 * @param cardList 底牌
	 */
	public void GC_TEXAS_BUTTOM_DEAL(InputMessage data) 
	{
		int i,size;
		ArrayList cardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int cardList_Datas = data.GetInt();
			cardList.Add(cardList_Datas);
		}
		TexasHandler.Instance().GC_TEXAS_BUTTOM_DEAL(cardList);
	}
 
  	/**
	 * 轮到该玩家
	 * @param position 轮到该玩家
	 */
	public void GC_TEXAS_PLAYER_TURN(InputMessage data) 
	{
		int position = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_PLAYER_TURN(position);
	}
 
  	/**
	 * 发三张公共牌
	 * @param cardList 公共牌
	 */
	public void GC_TEXAS_FLOP(InputMessage data) 
	{
		int i,size;
		ArrayList cardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int cardList_Datas = data.GetInt();
			cardList.Add(cardList_Datas);
		}
		TexasHandler.Instance().GC_TEXAS_FLOP(cardList);
	}
 
  	/**
	 * 轮到该玩家
	 * @param card 第4张牌
	 */
	public void GC_TEXAS_TURN(InputMessage data) 
	{
		int card = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_TURN(card);
	}
 
  	/**
	 * 轮到该玩家
	 * @param card 第5张牌
	 */
	public void GC_TEXAS_RIVER(InputMessage data) 
	{
		int card = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_RIVER(card);
	}
 
  	/**
	 * 德州看牌
	 * @param pos 跟注位置
	 */
	public void GC_TEXAS_LOOK(InputMessage data) 
	{
		int pos = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_LOOK(pos);
	}
 
  	/**
	 * 德州跟注
	 * @param pos 跟注位置
	 */
	public void GC_TEXAS_FOLLOW(InputMessage data) 
	{
		int pos = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_FOLLOW(pos);
	}
 
  	/**
	 * 德州加注
	 * @param pos 加注位置
	 * @param addBet 加注额度
	 */
	public void GC_TEXAS_ADD_BET(InputMessage data) 
	{
		int pos = data.GetInt();		
		long addBet = data.GetLong();
		TexasHandler.Instance().GC_TEXAS_ADD_BET(pos,addBet);
	}
 
  	/**
	 * 德州allin
	 * @param pos allin位置
	 * @param allInBet allin额度
	 */
	public void GC_TEXAS_ALL_IN(InputMessage data) 
	{
		int pos = data.GetInt();		
		long allInBet = data.GetLong();
		TexasHandler.Instance().GC_TEXAS_ALL_IN(pos,allInBet);
	}
 
  	/**
	 * 德州allin
	 * @param pos allin位置
	 */
	public void GC_TEXAS_GIVE_UP(InputMessage data) 
	{
		int pos = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_GIVE_UP(pos);
	}
 
  	/**
	 * 德州分池
	 * @param sidePoolList 边池列表 
	 */
	public void GC_TEXAS_SIDE_POOL(InputMessage data) 
	{
		int i,size;
		ArrayList sidePoolList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int sidePoolList_Datas = data.GetInt();
			sidePoolList.Add(sidePoolList_Datas);
		}
		TexasHandler.Instance().GC_TEXAS_SIDE_POOL(sidePoolList);
	}
 
  	/**
	 * 德州房间信息
	 * @param roomState 房间状态
	 * @param publicCardList 公共牌
	 * @param sidePoolList 边池
	 * @param bankPos 庄家位置
	 * @param currentBetterPos 当前下注位置
	 */
	public void GC_TEXAS_ROOM_INFO(InputMessage data) 
	{
		int i,size;
		int roomState = data.GetInt();		
		ArrayList publicCardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int publicCardList_Datas = data.GetInt();
			publicCardList.Add(publicCardList_Datas);
		}
		ArrayList sidePoolList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long sidePoolList_Datas = data.GetLong();
			sidePoolList.Add(sidePoolList_Datas);
		}
		int bankPos = data.GetInt();		
		int currentBetterPos = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_ROOM_INFO(roomState,publicCardList,sidePoolList,bankPos,currentBetterPos);
	}
 
  	/**
	 * 德州结算
	 * @param playerList 玩家列表 
	 * @param settlePoolList 边池获胜列表 
	 */
	public void GC_TEXAS_SETTLE(InputMessage data) 
	{
		int i,size;
		ArrayList playerList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			TexasRoomPlayerSettleCardsInfoData playerList_Datas= new TexasRoomPlayerSettleCardsInfoData();
			playerList_Datas.pos = data.GetInt();//玩家位置
			playerList_Datas.cardListStr = data.GetString();//手牌
			playerList_Datas.firstCard = data.GetInt();//第一张牌
			playerList_Datas.secondCard = data.GetInt();//第二张牌
			playerList.Add(playerList_Datas);
		}
		ArrayList settlePoolList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			TexasPoolSettleInfoData settlePoolList_Datas= new TexasPoolSettleInfoData();
			int settlePoolList_j;
				ArrayList settlePoolList_winnerList = new ArrayList();
				int settlePoolList_winnerListSize = data.GetShort();
				for(settlePoolList_j=0; settlePoolList_j<settlePoolList_winnerListSize; settlePoolList_j++){
					TexasRoomPlayerSettleInfoData settlePoolList_winnerList_Datas= new TexasRoomPlayerSettleInfoData();
					settlePoolList_winnerList_Datas.pos = data.GetInt();//玩家位置
					settlePoolList_winnerList_Datas.winBet = data.GetLong();//赢得筹码
					settlePoolList_winnerList_Datas.handCardType = data.GetInt();//手牌类型
					settlePoolList_winnerList.Add(settlePoolList_winnerList_Datas);
				}
			settlePoolList_Datas.winnerList = settlePoolList_winnerList;
			settlePoolList.Add(settlePoolList_Datas);
		}
		TexasHandler.Instance().GC_TEXAS_SETTLE(playerList,settlePoolList);
	}
 
  	/**
	 * 德州结算
	 * @param winnerPos 获胜玩家 
	 * @param settlePoolList 边池列表 
	 */
	public void GC_TEXAS_SETTLE_GIVEUP(InputMessage data) 
	{
		int i,size;
		int winnerPos = data.GetInt();		
		ArrayList settlePoolList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int settlePoolList_Datas = data.GetInt();
			settlePoolList.Add(settlePoolList_Datas);
		}
		TexasHandler.Instance().GC_TEXAS_SETTLE_GIVEUP(winnerPos,settlePoolList);
	}
 
  	/**
	 * 德州清理台面
	 * @param smallBlind 下一局小盲注 
	 * @param duration 升盲注剩余时间
	 */
	public void GC_TEXAS_PREPARE_RESTART(InputMessage data) 
	{
		int smallBlind = data.GetInt();		
		long duration = data.GetLong();
		TexasHandler.Instance().GC_TEXAS_PREPARE_RESTART(smallBlind,duration);
	}
 
  	/**
	 * 德州金币同步
	 * @param pos 玩家 
	 * @param coins 金币数 
	 */
	public void GC_TEXAS_COINS_SYNC(InputMessage data) 
	{
		int pos = data.GetInt();		
		long coins = data.GetLong();
		TexasHandler.Instance().GC_TEXAS_COINS_SYNC(pos,coins);
	}
 
  	/**
	 * 德州玩家坐下
	 * @param playerJoin 玩家列表
	 */
	public void GC_TEXAS_SEAT(InputMessage data) 
	{
		TexasRoomPlayerInfoData playerJoin = new TexasRoomPlayerInfoData();
		playerJoin.playerId = data.GetLong();//玩家id
		playerJoin.name = data.GetString();//玩家名字
		playerJoin.img = data.GetString();//玩家图片
		playerJoin.playerState = data.GetInt();//玩家状态
		playerJoin.coins = data.GetLong();//筹码
		playerJoin.vip = data.GetInt();//vip
		playerJoin.pos = data.GetInt();//位置
		playerJoin.currentBet = data.GetLong();//当前押注
		playerJoin.allBet = data.GetLong();//总押注
		TexasHandler.Instance().GC_TEXAS_SEAT(playerJoin);
	}
 
  	/**
	 * 德州玩家退出
	 */
	public void GC_TEXAS_EXIT(InputMessage data) 
	{
		TexasHandler.Instance().GC_TEXAS_EXIT();
	}
 
  	/**
	 * 德州基本信息
	 * @param totalCount 总数
	 * @param winCount 获胜数
	 * @param isAuto 自动
	 * @param people 人数
	 * @param weekWinCoins 周盈利
	 * @param dayBiggestWinCoins 单日最高盈利
	 * @param biggestHandCardList 最大手牌
	 */
	public void GC_HUMAN_TEXAS_INFO_DATA(InputMessage data) 
	{
		int i,size;
		int totalCount = data.GetInt();		
		int winCount = data.GetInt();		
		int isAuto = data.GetInt();		
		int people = data.GetInt();		
		int weekWinCoins = data.GetInt();		
		int dayBiggestWinCoins = data.GetInt();		
		ArrayList biggestHandCardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int biggestHandCardList_Datas = data.GetInt();
			biggestHandCardList.Add(biggestHandCardList_Datas);
		}
		TexasHandler.Instance().GC_HUMAN_TEXAS_INFO_DATA(totalCount,winCount,isAuto,people,weekWinCoins,dayBiggestWinCoins,biggestHandCardList);
	}
 
  	/**
	 * 德州补充筹码
	 * @param complement 补充数
	 */
	public void GC_TEXAS_COMPLEMENT(InputMessage data) 
	{
		long complement = data.GetLong();
		TexasHandler.Instance().GC_TEXAS_COMPLEMENT(complement);
	}
 
  	/**
	 * 德州打赏
	 * @param pos 打赏位置
	 */
	public void GC_TEXAS_TIPS(InputMessage data) 
	{
		int pos = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_TIPS(pos);
	}
 
  	/**
	 * 德州人数
	 * @param people 人数
	 */
	public void GC_TEXAS_PEOPLE_SETTING(InputMessage data) 
	{
		int people = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_PEOPLE_SETTING(people);
	}
 
  	/**
	 * 德州快速开始
	 * @param handCardType 手牌类型
	 */
	public void GC_TEXAS_HAND_CARD(InputMessage data) 
	{
		int handCardType = data.GetInt();		
		TexasHandler.Instance().GC_TEXAS_HAND_CARD(handCardType);
	}
 
  	/**
	 * 德州sng列表
	 * @param sngInfoDataList 玩家列表
	 */
	public void GC_TEXAS_SNG_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList sngInfoDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			TexasSngInfoData sngInfoDataList_Datas= new TexasSngInfoData();
			sngInfoDataList_Datas.id = data.GetInt();//活动id
			sngInfoDataList_Datas.openUp = data.GetInt();//开启状态
			sngInfoDataList_Datas.nums = data.GetInt();//人数
			sngInfoDataList.Add(sngInfoDataList_Datas);
		}
		TexasHandler.Instance().GC_TEXAS_SNG_LIST(sngInfoDataList);
	}
 
  	/**
	 * 德州sng名次
	 * @param pos pos
	 * @param sngRank rank
	 */
	public void GC_SNG_RANK(InputMessage data) 
	{
		int pos = data.GetInt();		
		int sngRank = data.GetInt();		
		TexasHandler.Instance().GC_SNG_RANK(pos,sngRank);
	}
 
  	/**
	 * 德州sng基本信息
	 * @param joinTimes 参加次数
	 * @param goldNum 金杯
	 * @param silverNum 银杯
	 * @param copperNum 铜杯
	 * @param weekScore 周分数
	 * @param rank 排行
	 */
	public void GC_HUMAN_TEXAS_SNG_INFO_DATA(InputMessage data) 
	{
		int joinTimes = data.GetInt();		
		int goldNum = data.GetInt();		
		int silverNum = data.GetInt();		
		int copperNum = data.GetInt();		
		int weekScore = data.GetInt();		
		long rank = data.GetLong();
		TexasHandler.Instance().GC_HUMAN_TEXAS_SNG_INFO_DATA(joinTimes,goldNum,silverNum,copperNum,weekScore,rank);
	}
 
  	/**
	 * 德州vip列表
	 * @param vipInfoDataList 房间列表
	 */
	public void GC_TEXAS_VIP_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList vipInfoDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			TexasVipInfoData vipInfoDataList_Datas= new TexasVipInfoData();
			vipInfoDataList_Datas.id =data.GetLong();
			vipInfoDataList_Datas.name = data.GetString();//名字
			vipInfoDataList_Datas.tId = data.GetInt();//vip房间id
			vipInfoDataList_Datas.secure = data.GetInt();//安全
			vipInfoDataList_Datas.num = data.GetInt();//人数
			vipInfoDataList.Add(vipInfoDataList_Datas);
		}
		TexasHandler.Instance().GC_TEXAS_VIP_LIST(vipInfoDataList);
	}
 
  	/**
	 * 德州加入vip房间
	 * @param errorCode errorCode
	 */
	public void GC_JOIN_TEXAS_VIP_FAILED(InputMessage data) 
	{
		int errorCode = data.GetInt();		
		TexasHandler.Instance().GC_JOIN_TEXAS_VIP_FAILED(errorCode);
	}
 
  	/**
	 * 德州清理桌面
	 */
	public void GC_TEXAS_CLEAR_TABLE(InputMessage data) 
	{
		TexasHandler.Instance().GC_TEXAS_CLEAR_TABLE();
	}
 
  	/**
	 * 德州补充筹码数
	 * @param complement 补充数
	 */
	public void GC_TEXAS_COMPLEMENT_NUM(InputMessage data) 
	{
		long complement = data.GetLong();
		TexasHandler.Instance().GC_TEXAS_COMPLEMENT_NUM(complement);
	}
 
  	/**
	 * 德州信息查询
	 * @param playerId 玩家id
	 * @param humanTexasSNGInfoData 德州sng信息
	 * @param humanTexasInfoData 德州信息
	 */
	public void GC_HUMAN_TEXAS_INFO_DATA_SEARCH(InputMessage data) 
	{
		long playerId = data.GetLong();
		HumanTexasSNGInfoData humanTexasSNGInfoData = new HumanTexasSNGInfoData();
		humanTexasSNGInfoData.joinTimes = data.GetInt();//参加数
		humanTexasSNGInfoData.goldNum = data.GetInt();//金牌数
		humanTexasSNGInfoData.silverNum = data.GetInt();//银牌数
		humanTexasSNGInfoData.copperNum = data.GetInt();//通牌数
		humanTexasSNGInfoData.weekScore = data.GetInt();//周赢分
		HumanTexasInfoData humanTexasInfoData = new HumanTexasInfoData();
		int j;
		humanTexasInfoData.count = data.GetInt();//游戏数
		humanTexasInfoData.winCount = data.GetInt();//胜利数
		humanTexasInfoData.weekWinCoins = data.GetInt();//明灯数
		humanTexasInfoData.dayBiggestWinCoins = data.GetInt();//天赢利
		ArrayList biggestHandCardList = new ArrayList();
		int biggestHandCardListSize = data.GetShort();
		for(j=0; j<biggestHandCardListSize; j++)
		{
			int biggestHandCardList_Datas = data.GetInt();//玩家最大手牌
			biggestHandCardList.Add(biggestHandCardList_Datas);
		}
		humanTexasInfoData.biggestHandCardList = biggestHandCardList;
		TexasHandler.Instance().GC_HUMAN_TEXAS_INFO_DATA_SEARCH(playerId,humanTexasSNGInfoData,humanTexasInfoData);
	}
 
  	/**
	 * 德州结算经验广播
	 * @param texasRoomExp 玩家经验列表
	 */
	public void GC_HUMAN_TEXAS_EXP(InputMessage data) 
	{
		int i,size;
		ArrayList texasRoomExp = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			TexasRoomExp texasRoomExp_Datas= new TexasRoomExp();
			texasRoomExp_Datas.playerId =data.GetLong();
			texasRoomExp_Datas.exp = data.GetInt();//增加经验
			texasRoomExp.Add(texasRoomExp_Datas);
		}
		TexasHandler.Instance().GC_HUMAN_TEXAS_EXP(texasRoomExp);
	}
}