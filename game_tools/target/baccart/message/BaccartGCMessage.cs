using System.Collections;

public class BaccartGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_BACCART_LIST,GC_BACCART_LIST);
		register(NetMessageType.GC_BACCART_SHUFFLE,GC_BACCART_SHUFFLE);
		register(NetMessageType.GC_BACCART_START_BET,GC_BACCART_START_BET);
		register(NetMessageType.GC_BACCART_BET,GC_BACCART_BET);
		register(NetMessageType.GC_BACCART_SETTLE,GC_BACCART_SETTLE);
		register(NetMessageType.GC_BACCART_CLEAR_TABLE,GC_BACCART_CLEAR_TABLE);
		register(NetMessageType.GC_BACCART_JOIN,GC_BACCART_JOIN);
		register(NetMessageType.GC_BACCART_SYNC_JOIN,GC_BACCART_SYNC_JOIN);
		register(NetMessageType.GC_BACCART_LIGHT,GC_BACCART_LIGHT);
		register(NetMessageType.GC_BACCART_STAND,GC_BACCART_STAND);
		register(NetMessageType.GC_BACCART_SEAT,GC_BACCART_SEAT);
		register(NetMessageType.GC_BACCART_EXIT,GC_BACCART_EXIT);
		register(NetMessageType.GC_BACCART_JACKPOT,GC_BACCART_JACKPOT);
		register(NetMessageType.GC_BACCART_PLAYER_JACKPOT,GC_BACCART_PLAYER_JACKPOT);
		register(NetMessageType.GC_HUMAN_BACCART,GC_HUMAN_BACCART);
		register(NetMessageType.GC_HUMAN_BACCART_COINS_SYNC,GC_HUMAN_BACCART_COINS_SYNC);
		register(NetMessageType.GC_BACCART_AUTO,GC_BACCART_AUTO);
		register(NetMessageType.GC_BACCART_COMPLEMENT,GC_BACCART_COMPLEMENT);
		register(NetMessageType.GC_BACCART_REVIVE,GC_BACCART_REVIVE);
		register(NetMessageType.GC_BACCART_COMPLEMENT_TIP,GC_BACCART_COMPLEMENT_TIP);
	}
 
  	/**
	 * 百家乐列表
	 * @param baccartRoomDataList 百家乐房间列表
	 */
	public void GC_BACCART_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList baccartRoomDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BaccartRoomData baccartRoomDataList_Datas= new BaccartRoomData();
			baccartRoomDataList_Datas.roomId = data.GetInt();//房间id
			baccartRoomDataList_Datas.num = data.GetInt();//人数
			baccartRoomDataList_Datas.jackpot =data.GetLong();
			baccartRoomDataList_Datas.closed = data.GetInt();//关闭状态
			baccartRoomDataList.Add(baccartRoomDataList_Datas);
		}
		BaccartHandler.Instance().GC_BACCART_LIST(baccartRoomDataList);
	}
 
  	/**
	 * 百家乐洗牌
	 * @param remainCards 剩余牌
	 */
	public void GC_BACCART_SHUFFLE(InputMessage data) 
	{
		int remainCards = data.GetInt();		
		BaccartHandler.Instance().GC_BACCART_SHUFFLE(remainCards);
	}
 
  	/**
	 * 百家乐开始下注
	 */
	public void GC_BACCART_START_BET(InputMessage data) 
	{
		BaccartHandler.Instance().GC_BACCART_START_BET();
	}
 
  	/**
	 * 百家乐下注
	 * @param playerId 玩家id
	 * @param betDataList 下注列表
	 */
	public void GC_BACCART_BET(InputMessage data) 
	{
		int i,size;
		long playerId = data.GetLong();
		ArrayList betDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BaccartBetData betDataList_Datas= new BaccartBetData();
			betDataList_Datas.betType = data.GetInt();//押注类型
			betDataList_Datas.betNum =data.GetLong();
			betDataList.Add(betDataList_Datas);
		}
		BaccartHandler.Instance().GC_BACCART_BET(playerId,betDataList);
	}
 
  	/**
	 * 百家乐下注
	 * @param bankerCardList 庄家牌
	 * @param playerCardList 闲家牌
	 * @param pearlRoadData 珠盘路
	 * @param settleDataList 赢钱列表
	 */
	public void GC_BACCART_SETTLE(InputMessage data) 
	{
		int i,size;
		ArrayList bankerCardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int bankerCardList_Datas = data.GetInt();
			bankerCardList.Add(bankerCardList_Datas);
		}
		ArrayList playerCardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int playerCardList_Datas = data.GetInt();
			playerCardList.Add(playerCardList_Datas);
		}
		PearlRoadData pearlRoadData = new PearlRoadData();
		pearlRoadData.baccartPair = data.GetInt();//百家乐对子
		pearlRoadData.baccartResult = data.GetInt();//百家乐结果
		ArrayList settleDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BaccartSettleData settleDataList_Datas= new BaccartSettleData();
			settleDataList_Datas.playerId =data.GetLong();
			settleDataList_Datas.winCoins =data.GetLong();
			settleDataList.Add(settleDataList_Datas);
		}
		BaccartHandler.Instance().GC_BACCART_SETTLE(bankerCardList,playerCardList,pearlRoadData,settleDataList);
	}
 
  	/**
	 * 百家乐清理桌面
	 */
	public void GC_BACCART_CLEAR_TABLE(InputMessage data) 
	{
		BaccartHandler.Instance().GC_BACCART_CLEAR_TABLE();
	}
 
  	/**
	 * 玩家加入
	 * @param playerDataList 玩家信息列表
	 * @param pearlRoadDataList 珠盘路
	 * @param betDataList 下注信息
	 * @param roomState 房间状态
	 * @param jackpot jackpot
	 * @param roomId 房间id
	 * @param remainCards 剩余牌数
	 * @param remainTime 剩余时间
	 */
	public void GC_BACCART_JOIN(InputMessage data) 
	{
		int i,size;
		ArrayList playerDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BaccartPlayerData playerDataList_Datas= new BaccartPlayerData();
			playerDataList_Datas.playerId =data.GetLong();
			playerDataList_Datas.name = data.GetString();//名字
			playerDataList_Datas.img = data.GetString();//图像
			playerDataList_Datas.gold =data.GetLong();
			playerDataList_Datas.vip = data.GetInt();//vip
			playerDataList_Datas.pos = data.GetInt();//位置
			playerDataList.Add(playerDataList_Datas);
		}
		ArrayList pearlRoadDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			PearlRoadData pearlRoadDataList_Datas= new PearlRoadData();
			pearlRoadDataList_Datas.baccartPair = data.GetInt();//百家乐对子
			pearlRoadDataList_Datas.baccartResult = data.GetInt();//百家乐结果
			pearlRoadDataList.Add(pearlRoadDataList_Datas);
		}
		ArrayList betDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BaccartBetData betDataList_Datas= new BaccartBetData();
			betDataList_Datas.betType = data.GetInt();//押注类型
			betDataList_Datas.betNum =data.GetLong();
			betDataList.Add(betDataList_Datas);
		}
		int roomState = data.GetInt();		
		long jackpot = data.GetLong();
		int roomId = data.GetInt();		
		int remainCards = data.GetInt();		
		long remainTime = data.GetLong();
		BaccartHandler.Instance().GC_BACCART_JOIN(playerDataList,pearlRoadDataList,betDataList,roomState,jackpot,roomId,remainCards,remainTime);
	}
 
  	/**
	 * 玩家加入
	 * @param playerData 玩家信息
	 */
	public void GC_BACCART_SYNC_JOIN(InputMessage data) 
	{
		BaccartPlayerData playerData = new BaccartPlayerData();
		playerData.playerId = data.GetLong();//玩家d
		playerData.name = data.GetString();//名字
		playerData.img = data.GetString();//图像
		playerData.gold = data.GetLong();//金币
		playerData.vip = data.GetInt();//vip
		playerData.pos = data.GetInt();//位置
		BaccartHandler.Instance().GC_BACCART_SYNC_JOIN(playerData);
	}
 
  	/**
	 * 玩家明灯
	 * @param lightDataList 明灯信息
	 */
	public void GC_BACCART_LIGHT(InputMessage data) 
	{
		int i,size;
		ArrayList lightDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BaccartLightData lightDataList_Datas= new BaccartLightData();
			lightDataList_Datas.continueWin = data.GetInt();//连胜次数
			lightDataList_Datas.minWins =data.GetLong();
			lightDataList_Datas.playerId =data.GetLong();
			lightDataList.Add(lightDataList_Datas);
		}
		BaccartHandler.Instance().GC_BACCART_LIGHT(lightDataList);
	}
 
  	/**
	 * 玩家站起
	 * @param playerId 玩家d
	 */
	public void GC_BACCART_STAND(InputMessage data) 
	{
		long playerId = data.GetLong();
		BaccartHandler.Instance().GC_BACCART_STAND(playerId);
	}
 
  	/**
	 * 玩家坐下
	 * @param playerId 玩家d
	 * @param pos 坐下位置
	 */
	public void GC_BACCART_SEAT(InputMessage data) 
	{
		long playerId = data.GetLong();
		int pos = data.GetInt();		
		BaccartHandler.Instance().GC_BACCART_SEAT(playerId,pos);
	}
 
  	/**
	 * 玩家退出
	 * @param playerId 玩家id
	 */
	public void GC_BACCART_EXIT(InputMessage data) 
	{
		long playerId = data.GetLong();
		BaccartHandler.Instance().GC_BACCART_EXIT(playerId);
	}
 
  	/**
	 * 彩金池
	 * @param jackpot 筹码
	 */
	public void GC_BACCART_JACKPOT(InputMessage data) 
	{
		long jackpot = data.GetLong();
		BaccartHandler.Instance().GC_BACCART_JACKPOT(jackpot);
	}
 
  	/**
	 * 玩家彩金
	 * @param jackpotDataList 个人彩金信息
	 */
	public void GC_BACCART_PLAYER_JACKPOT(InputMessage data) 
	{
		int i,size;
		ArrayList jackpotDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BaccartJackpotData jackpotDataList_Datas= new BaccartJackpotData();
			jackpotDataList_Datas.jackpot =data.GetLong();
			jackpotDataList_Datas.playerId =data.GetLong();
			jackpotDataList.Add(jackpotDataList_Datas);
		}
		BaccartHandler.Instance().GC_BACCART_PLAYER_JACKPOT(jackpotDataList);
	}
 
  	/**
	 * 百家乐玩家信息
	 * @param humanBaccartData 百家乐
	 */
	public void GC_HUMAN_BACCART(InputMessage data) 
	{
		HumanBaccartData humanBaccartData = new HumanBaccartData();
		humanBaccartData.gameNum = data.GetLong();//游戏数
		humanBaccartData.winNum = data.GetLong();//赢局数
		humanBaccartData.beaconNum = data.GetLong();//明灯数
		humanBaccartData.playerId = data.GetLong();//玩家id
		BaccartHandler.Instance().GC_HUMAN_BACCART(humanBaccartData);
	}
 
  	/**
	 * 百家乐筹码同步
	 * @param baccartCoinSyncDataList 百家乐
	 */
	public void GC_HUMAN_BACCART_COINS_SYNC(InputMessage data) 
	{
		int i,size;
		ArrayList baccartCoinSyncDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BaccartCoinSyncData baccartCoinSyncDataList_Datas= new BaccartCoinSyncData();
			baccartCoinSyncDataList_Datas.coins =data.GetLong();
			baccartCoinSyncDataList_Datas.playerId =data.GetLong();
			baccartCoinSyncDataList.Add(baccartCoinSyncDataList_Datas);
		}
		BaccartHandler.Instance().GC_HUMAN_BACCART_COINS_SYNC(baccartCoinSyncDataList);
	}
 
  	/**
	 * 百家乐自动补充
	 * @param auto 自动补充
	 */
	public void GC_BACCART_AUTO(InputMessage data) 
	{
		int auto = data.GetInt();		
		BaccartHandler.Instance().GC_BACCART_AUTO(auto);
	}
 
  	/**
	 * 百家乐补充筹码
	 * @param complement 补充数
	 */
	public void GC_BACCART_COMPLEMENT(InputMessage data) 
	{
		long complement = data.GetLong();
		BaccartHandler.Instance().GC_BACCART_COMPLEMENT(complement);
	}
 
  	/**
	 * 百家乐复活
	 * @param revive 复活
	 */
	public void GC_BACCART_REVIVE(InputMessage data) 
	{
		int revive = data.GetInt();		
		BaccartHandler.Instance().GC_BACCART_REVIVE(revive);
	}
 
  	/**
	 * 百家乐补充筹码
	 * @param complement 补充
	 */
	public void GC_BACCART_COMPLEMENT_TIP(InputMessage data) 
	{
		long complement = data.GetLong();
		BaccartHandler.Instance().GC_BACCART_COMPLEMENT_TIP(complement);
	}
}