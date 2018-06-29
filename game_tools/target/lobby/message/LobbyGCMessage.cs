using System.Collections;

public class LobbyGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_JACKPOT_LIST_DATA,GC_JACKPOT_LIST_DATA);
		register(NetMessageType.GC_JACKPOT_LEVEL_DATA,GC_JACKPOT_LEVEL_DATA);
		register(NetMessageType.GC_GAMETYPE_JACKPOT,GC_GAMETYPE_JACKPOT);
		register(NetMessageType.GC_NEW_JACKPOT,GC_NEW_JACKPOT);
		register(NetMessageType.GC_SLOT_NEW_JACKPOTS,GC_SLOT_NEW_JACKPOTS);
		register(NetMessageType.GC_ALL_SLOT_NEW_JACKPOTS,GC_ALL_SLOT_NEW_JACKPOTS);
	}
 
  	/**
	 * 最高彩金获得者前20位
	 * @param jackpotList 彩金获得者数据
	 */
	public void GC_JACKPOT_LIST_DATA(InputMessage data) 
	{
		int i,size;
		ArrayList jackpotList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			JackpotList jackpotList_Datas= new JackpotList();
			jackpotList_Datas.userId =data.GetLong();
			jackpotList_Datas.img = data.GetString();//头像图片
			jackpotList_Datas.name = data.GetString();//名字
			jackpotList_Datas.jackpot =data.GetLong();
			jackpotList_Datas.gameType = data.GetInt();//游戏类型 百家乐 1 德州扑克 2 老虎机 classic：3  老虎机 四大美人：4 老虎机 水果：5
			jackpotList.Add(jackpotList_Datas);
		}
		LobbyHandler.Instance().GC_JACKPOT_LIST_DATA(jackpotList);
	}
 
  	/**
	 * 适合自己等级游戏的彩金
	 * @param jackpotfitLevelData 适合自己等级游戏彩金
	 */
	public void GC_JACKPOT_LEVEL_DATA(InputMessage data) 
	{
		int i,size;
		ArrayList jackpotfitLevelData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			JackpotfitLevelData jackpotfitLevelData_Datas= new JackpotfitLevelData();
			jackpotfitLevelData_Datas.gameType = data.GetInt();//游戏类型 百家乐 1 德州扑克 2 老虎机 classic：3  老虎机 四大美人：4 老虎机 水果：5
			jackpotfitLevelData_Datas.jackpot =data.GetLong();
			jackpotfitLevelData.Add(jackpotfitLevelData_Datas);
		}
		LobbyHandler.Instance().GC_JACKPOT_LEVEL_DATA(jackpotfitLevelData);
	}
 
  	/**
	 * 返回游戏彩金
	 * @param gameType 游戏类型 百家乐 1 德州扑克 2 老虎机 classic：3  老虎机 四大美人：4 老虎机 水果：5
	 * @param gameId 游戏的id
	 * @param gamejackpot 游戏彩金
	 */
	public void GC_GAMETYPE_JACKPOT(InputMessage data) 
	{
		int gameType = data.GetInt();		
		int gameId = data.GetInt();		
		long gamejackpot = data.GetLong();
		LobbyHandler.Instance().GC_GAMETYPE_JACKPOT(gameType,gameId,gamejackpot);
	}
 
  	/**
	 * 游戏彩金数
	 * @param jackpot 根据bet数 获取的相应的彩金值
	 */
	public void GC_NEW_JACKPOT(InputMessage data) 
	{
		long jackpot = data.GetLong();
		LobbyHandler.Instance().GC_NEW_JACKPOT(jackpot);
	}
 
  	/**
	 * 进入老虎机后的第一个页面展示的 四个阶段的 彩金数
	 * @param jackpotNum 《第几个》--不同的等级对应的最大彩金数，有几个传几个，（配置中可以关闭不同等级的彩金，然以就不显示）
	 * @param jackpot 不同的等级对应的最大彩金数，有几个传几个，（配置中可以关闭不同等级的彩金，然以就不显示）
	 */
	public void GC_SLOT_NEW_JACKPOTS(InputMessage data) 
	{
		int i,size;
		ArrayList jackpotNum = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int jackpotNum_Datas = data.GetInt();
			jackpotNum.Add(jackpotNum_Datas);
		}
		ArrayList jackpot = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long jackpot_Datas = data.GetLong();
			jackpot.Add(jackpot_Datas);
		}
		LobbyHandler.Instance().GC_SLOT_NEW_JACKPOTS(jackpotNum,jackpot);
	}
 
  	/**
	 * 返回  所有老虎机（每种类型的老虎机 对应一个最高的彩金）的最高彩金
	 * @param slotType 老虎机类型
	 * @param jackpot 老虎机类型 对应的最高彩金
	 */
	public void GC_ALL_SLOT_NEW_JACKPOTS(InputMessage data) 
	{
		int i,size;
		ArrayList slotType = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int slotType_Datas = data.GetInt();
			slotType.Add(slotType_Datas);
		}
		ArrayList jackpot = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long jackpot_Datas = data.GetLong();
			jackpot.Add(jackpot_Datas);
		}
		LobbyHandler.Instance().GC_ALL_SLOT_NEW_JACKPOTS(slotType,jackpot);
	}
}