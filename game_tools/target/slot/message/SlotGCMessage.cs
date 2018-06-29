using System.Collections;

public class SlotGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_SLOT_ENTER,GC_SLOT_ENTER);
		register(NetMessageType.GC_SLOT_SLOT,GC_SLOT_SLOT);
		register(NetMessageType.GC_REMOVE_SLOT_SLOT,GC_REMOVE_SLOT_SLOT);
		register(NetMessageType.GC_ROTARY_TABLE,GC_ROTARY_TABLE);
		register(NetMessageType.GC_FREE_SLOT_REWARD,GC_FREE_SLOT_REWARD);
		register(NetMessageType.GC_SLOT_LIST,GC_SLOT_LIST);
		register(NetMessageType.GC_SLOT_TOURNAMENT_START_DATA,GC_SLOT_TOURNAMENT_START_DATA);
		register(NetMessageType.GC_SLOT_TOURNAMENT_NOT_OPEN,GC_SLOT_TOURNAMENT_NOT_OPEN);
		register(NetMessageType.GC_SLOT_RANK_LIST,GC_SLOT_RANK_LIST);
		register(NetMessageType.GC_TOURNAMENT_GET_LIST,GC_TOURNAMENT_GET_LIST);
		register(NetMessageType.GC_SLOT_GET_RANK,GC_SLOT_GET_RANK);
		register(NetMessageType.GC_SNG_RANK_INFO,GC_SNG_RANK_INFO);
		register(NetMessageType.GC_SLOT_GET_REWARD,GC_SLOT_GET_REWARD);
		register(NetMessageType.GC_SLOT_TYPE10,GC_SLOT_TYPE10);
		register(NetMessageType.GC_SLOT_TYPE10_SCATTER,GC_SLOT_TYPE10_SCATTER);
		register(NetMessageType.GC_SLOT_TYPE11,GC_SLOT_TYPE11);
		register(NetMessageType.GC_SLOT_TYPE12,GC_SLOT_TYPE12);
		register(NetMessageType.GC_SLOT_TYPE12_CHOOSE,GC_SLOT_TYPE12_CHOOSE);
		register(NetMessageType.GC_SLOT_TYPE12_FREE,GC_SLOT_TYPE12_FREE);
		register(NetMessageType.GC_SLOT_TYPE3_BOUNS_START,GC_SLOT_TYPE3_BOUNS_START);
		register(NetMessageType.GC_SLOT_TYPE13_BOUNS,GC_SLOT_TYPE13_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE13_SEND_BOUNS,GC_SLOT_TYPE13_SEND_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE14,GC_SLOT_TYPE14);
		register(NetMessageType.GC_SLOT_TYPE14_BONUS,GC_SLOT_TYPE14_BONUS);
		register(NetMessageType.GC_SLOT_TYPE14_APPLE_BONUS,GC_SLOT_TYPE14_APPLE_BONUS);
		register(NetMessageType.GC_SLOT_TYPE14_RUNE_BONUS,GC_SLOT_TYPE14_RUNE_BONUS);
		register(NetMessageType.GC_SLOT_TYPE14_PREY_BONUS,GC_SLOT_TYPE14_PREY_BONUS);
		register(NetMessageType.GC_SLOT_TYPE15_BOUNS_START,GC_SLOT_TYPE15_BOUNS_START);
		register(NetMessageType.GC_SLOT_TYPE15_BOUNS,GC_SLOT_TYPE15_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE16,GC_SLOT_TYPE16);
		register(NetMessageType.GC_SLOT_TYPE17,GC_SLOT_TYPE17);
		register(NetMessageType.GC_SLOT_TYPE19,GC_SLOT_TYPE19);
		register(NetMessageType.GC_SLOT_TYPE20,GC_SLOT_TYPE20);
		register(NetMessageType.GC_SLOT_TYPE20_BOUNS,GC_SLOT_TYPE20_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE20_BOUNS_NEW,GC_SLOT_TYPE20_BOUNS_NEW);
		register(NetMessageType.GC_SLOT_TYPE21_BOUNS_INFO,GC_SLOT_TYPE21_BOUNS_INFO);
		register(NetMessageType.GC_SLOT_TYPE21_BOUNS,GC_SLOT_TYPE21_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE22,GC_SLOT_TYPE22);
		register(NetMessageType.GC_SLOT_TYPE23_BOUNS_INFO,GC_SLOT_TYPE23_BOUNS_INFO);
		register(NetMessageType.GC_SLOT_TYPE23_INIT_REWARD,GC_SLOT_TYPE23_INIT_REWARD);
		register(NetMessageType.GC_SLOT_TYPE24_BOUNS,GC_SLOT_TYPE24_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE24_SEND_BOUNS,GC_SLOT_TYPE24_SEND_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE24_BOUNS_GAME_START,GC_SLOT_TYPE24_BOUNS_GAME_START);
		register(NetMessageType.GC_SLOT_TYPE24_BOUNS_SAMBA,GC_SLOT_TYPE24_BOUNS_SAMBA);
		register(NetMessageType.GC_SLOT_TYPE24_BOUNS_BAR,GC_SLOT_TYPE24_BOUNS_BAR);
		register(NetMessageType.GC_SLOT_TYPE25_WILD_INFO,GC_SLOT_TYPE25_WILD_INFO);
		register(NetMessageType.GC_SLOT_TYPE25_BOUNS_INFO,GC_SLOT_TYPE25_BOUNS_INFO);
		register(NetMessageType.GC_SLOT_TYPE25_BOUNS,GC_SLOT_TYPE25_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE18,GC_SLOT_TYPE18);
		register(NetMessageType.GC_SLOT_TYPE26_WILD_INFO,GC_SLOT_TYPE26_WILD_INFO);
		register(NetMessageType.GC_SLOT_TYPE26_BOUNS_INFO,GC_SLOT_TYPE26_BOUNS_INFO);
		register(NetMessageType.GC_SLOT_TYPE26_BOUNS,GC_SLOT_TYPE26_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE27_BOUNS_INFO,GC_SLOT_TYPE27_BOUNS_INFO);
		register(NetMessageType.GC_SLOT_TYPE28_WILD_INFO,GC_SLOT_TYPE28_WILD_INFO);
		register(NetMessageType.GC_SLOT_TYPE28_BOUNS_INFO,GC_SLOT_TYPE28_BOUNS_INFO);
		register(NetMessageType.GC_SLOT_TYPE28_BOUNS,GC_SLOT_TYPE28_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE28_SCATTER_INFO,GC_SLOT_TYPE28_SCATTER_INFO);
		register(NetMessageType.GC_SLOT_TYPE29_WILD_INFO,GC_SLOT_TYPE29_WILD_INFO);
		register(NetMessageType.GC_SLOT_TYPE29_BOUNS_INFO,GC_SLOT_TYPE29_BOUNS_INFO);
		register(NetMessageType.GC_SLOT_TYPE29_BOUNS,GC_SLOT_TYPE29_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE30_BOUNS_INFO,GC_SLOT_TYPE30_BOUNS_INFO);
		register(NetMessageType.GC_SLOT_TYPE30_BOUNS,GC_SLOT_TYPE30_BOUNS);
		register(NetMessageType.GC_SLOT_TYPE31_WILD_INFO,GC_SLOT_TYPE31_WILD_INFO);
		register(NetMessageType.GC_SLOT_TYPE31_SPECIFIC_WILD_INFO,GC_SLOT_TYPE31_SPECIFIC_WILD_INFO);
		register(NetMessageType.GC_SLOT_TYPE31_BONUS,GC_SLOT_TYPE31_BONUS);
		register(NetMessageType.GC_SLOT_TYPE31_BONUS_ONE,GC_SLOT_TYPE31_BONUS_ONE);
		register(NetMessageType.GC_SLOT_TYPE31_BONUS_TWO,GC_SLOT_TYPE31_BONUS_TWO);
		register(NetMessageType.GC_SLOT_TYPE31_BONUS_THREE,GC_SLOT_TYPE31_BONUS_THREE);
		register(NetMessageType.GC_SLOT_TYPE32_BULLET_IN,GC_SLOT_TYPE32_BULLET_IN);
		register(NetMessageType.GC_SLOT_TYPE32_BULLET_OUT,GC_SLOT_TYPE32_BULLET_OUT);
		register(NetMessageType.GC_SLOT_TYPE32_LEFT_BULLET_NUM,GC_SLOT_TYPE32_LEFT_BULLET_NUM);
		register(NetMessageType.GC_SLOT_TYPE32_SOCIAL_CONTACT,GC_SLOT_TYPE32_SOCIAL_CONTACT);
		register(NetMessageType.GC_SLOT_TYPE32_WILD_INFO,GC_SLOT_TYPE32_WILD_INFO);
		register(NetMessageType.GC_SLOT_TYPE32_BONUS,GC_SLOT_TYPE32_BONUS);
		register(NetMessageType.GC_SLOT_TYPE32_SPECIAL_LIST,GC_SLOT_TYPE32_SPECIAL_LIST);
		register(NetMessageType.GC_SLOT_TYPE33_BONUS_LIST,GC_SLOT_TYPE33_BONUS_LIST);
		register(NetMessageType.GC_SLOT_TYPE38_WILD,GC_SLOT_TYPE38_WILD);
		register(NetMessageType.GC_SLOT_TYPE38_PUMPKIN,GC_SLOT_TYPE38_PUMPKIN);
		register(NetMessageType.GC_SLOT_TYPE38_JACKPOT,GC_SLOT_TYPE38_JACKPOT);
		register(NetMessageType.GC_SLOT_TYPE38_BONUS_TRIGGER,GC_SLOT_TYPE38_BONUS_TRIGGER);
		register(NetMessageType.GC_SLOT_TYPE38_BONUS,GC_SLOT_TYPE38_BONUS);
		register(NetMessageType.GC_GET_SLOT_CACHEMSG,GC_GET_SLOT_CACHEMSG);
		register(NetMessageType.GC_SLOT_ERROR,GC_SLOT_ERROR);
		register(NetMessageType.GC_HUMAN_JACKPOT_REWARD,GC_HUMAN_JACKPOT_REWARD);
		register(NetMessageType.GC_WINNER_WHEEL,GC_WINNER_WHEEL);
	}
 
  	/**
	 * 进入老虎机
	 * @param slotId 老虎机Id
	 * @param resultType 返回类型  0 成功 1 等级不足 2 筹码不足
	 */
	public void GC_SLOT_ENTER(InputMessage data) 
	{
		int slotId = data.GetInt();		
		int resultType = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_ENTER(slotId,resultType);
	}
 
  	/**
	 * slot
	 * @param msgId 消息码
	 * @param free 是否免费
	 * @param slotElementList 元素列表
	 * @param slotConnectInfoDataList 连线信息列表
	 * @param rewardNum 收益
	 * @param freeTimes 免费次数
	 * @param rewardSum 累计奖金
	 * @param specialConnectInfoDataList 特殊连线信息列表
	 */
	public void GC_SLOT_SLOT(InputMessage data) 
	{
		int i,size;
		long msgId = data.GetLong();
		int free = data.GetInt();		
		ArrayList slotElementList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int slotElementList_Datas = data.GetInt();
			slotElementList.Add(slotElementList_Datas);
		}
		ArrayList slotConnectInfoDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			SlotConnectInfoData slotConnectInfoDataList_Datas= new SlotConnectInfoData();
			int slotConnectInfoDataList_j;
			slotConnectInfoDataList_Datas.payLineId = data.GetInt();//连线信息
				ArrayList slotConnectInfoDataList_posList = new ArrayList();
				int slotConnectInfoDataList_posListSize = data.GetShort();
				for(slotConnectInfoDataList_j=0; slotConnectInfoDataList_j<slotConnectInfoDataList_posListSize; slotConnectInfoDataList_j++){
					int slotConnectInfoDataList_posList_Datas = data.GetInt();//位置
					slotConnectInfoDataList_posList.Add(slotConnectInfoDataList_posList_Datas);
				}
			slotConnectInfoDataList_Datas.posList = slotConnectInfoDataList_posList;
			slotConnectInfoDataList_Datas.payId = data.GetInt();//赔率ID
			slotConnectInfoDataList.Add(slotConnectInfoDataList_Datas);
		}
		long rewardNum = data.GetLong();
		int freeTimes = data.GetInt();		
		long rewardSum = data.GetLong();
		ArrayList specialConnectInfoDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			SpecialConnectInfoData specialConnectInfoDataList_Datas= new SpecialConnectInfoData();
			int specialConnectInfoDataList_j;
				ArrayList specialConnectInfoDataList_posList = new ArrayList();
				int specialConnectInfoDataList_posListSize = data.GetShort();
				for(specialConnectInfoDataList_j=0; specialConnectInfoDataList_j<specialConnectInfoDataList_posListSize; specialConnectInfoDataList_j++){
					int specialConnectInfoDataList_posList_Datas = data.GetInt();//位置
					specialConnectInfoDataList_posList.Add(specialConnectInfoDataList_posList_Datas);
				}
			specialConnectInfoDataList_Datas.posList = specialConnectInfoDataList_posList;
			specialConnectInfoDataList.Add(specialConnectInfoDataList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_SLOT(msgId,free,slotElementList,slotConnectInfoDataList,rewardNum,freeTimes,rewardSum,specialConnectInfoDataList);
	}
 
  	/**
	 * slot
	 * @param slotConnectInfoDataList 连线信息列表
	 * @param gcRemoveSlotSlotList 三消老虎机列表
	 */
	public void GC_REMOVE_SLOT_SLOT(InputMessage data) 
	{
		int i,size;
		ArrayList slotConnectInfoDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			SlotConnectInfoData slotConnectInfoDataList_Datas= new SlotConnectInfoData();
			int slotConnectInfoDataList_j;
			slotConnectInfoDataList_Datas.payLineId = data.GetInt();//连线信息
				ArrayList slotConnectInfoDataList_posList = new ArrayList();
				int slotConnectInfoDataList_posListSize = data.GetShort();
				for(slotConnectInfoDataList_j=0; slotConnectInfoDataList_j<slotConnectInfoDataList_posListSize; slotConnectInfoDataList_j++){
					int slotConnectInfoDataList_posList_Datas = data.GetInt();//位置
					slotConnectInfoDataList_posList.Add(slotConnectInfoDataList_posList_Datas);
				}
			slotConnectInfoDataList_Datas.posList = slotConnectInfoDataList_posList;
			slotConnectInfoDataList_Datas.payId = data.GetInt();//赔率ID
			slotConnectInfoDataList.Add(slotConnectInfoDataList_Datas);
		}
		ArrayList gcRemoveSlotSlotList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			GcRemoveSlotSlotList gcRemoveSlotSlotList_Datas= new GcRemoveSlotSlotList();
			int gcRemoveSlotSlotList_j;
				ArrayList gcRemoveSlotSlotList_slotElementList = new ArrayList();
				int gcRemoveSlotSlotList_slotElementListSize = data.GetShort();
				for(gcRemoveSlotSlotList_j=0; gcRemoveSlotSlotList_j<gcRemoveSlotSlotList_slotElementListSize; gcRemoveSlotSlotList_j++){
					int gcRemoveSlotSlotList_slotElementList_Datas = data.GetInt();//元素列表
					gcRemoveSlotSlotList_slotElementList.Add(gcRemoveSlotSlotList_slotElementList_Datas);
				}
			gcRemoveSlotSlotList_Datas.slotElementList = gcRemoveSlotSlotList_slotElementList;
			gcRemoveSlotSlotList_Datas.lineNum = data.GetInt();//每次三消转动的线数
			gcRemoveSlotSlotList_Datas.rewardNum =data.GetLong();
			gcRemoveSlotSlotList_Datas.freeTimes = data.GetInt();//三消次数
			gcRemoveSlotSlotList_Datas.rewardSum =data.GetLong();
			gcRemoveSlotSlotList.Add(gcRemoveSlotSlotList_Datas);
		}
		SlotHandler.Instance().GC_REMOVE_SLOT_SLOT(slotConnectInfoDataList,gcRemoveSlotSlotList);
	}
 
  	/**
	 * 转盘游戏返回
	 * @param firstType 1：GOLD BONUS;2:FREE SPIN
	 * @param secondIndex 第二个盘子停留的位置 表里面的 ID 
	 * @param thirdIndex 第三个盘子停留的位置 表里面的 ID
	 * @param bigWheelIndex 第四个盘子停留的位置 表里面的 ID
	 * @param posList 大转盘玩法元素位置
	 * @param bounsList BounsGameTemplate表ID 
	 * @param bounsListPosition BounsGame元素位置  
	 */
	public void GC_ROTARY_TABLE(InputMessage data) 
	{
		int i,size;
		int firstType = data.GetInt();		
		int secondIndex = data.GetInt();		
		int thirdIndex = data.GetInt();		
		int bigWheelIndex = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		ArrayList bounsList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int bounsList_Datas = data.GetInt();
			bounsList.Add(bounsList_Datas);
		}
		ArrayList bounsListPosition = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int bounsListPosition_Datas = data.GetInt();
			bounsListPosition.Add(bounsListPosition_Datas);
		}
		SlotHandler.Instance().GC_ROTARY_TABLE(firstType,secondIndex,thirdIndex,bigWheelIndex,posList,bounsList,bounsListPosition);
	}
 
  	/**
	 * slot
	 * @param boxList 盒子列表
	 * @param pos 盒子位置
	 * @param rewardNum 奖金数
	 */
	public void GC_FREE_SLOT_REWARD(InputMessage data) 
	{
		int i,size;
		ArrayList boxList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int boxList_Datas = data.GetInt();
			boxList.Add(boxList_Datas);
		}
		int pos = data.GetInt();		
		long rewardNum = data.GetLong();
		SlotHandler.Instance().GC_FREE_SLOT_REWARD(boxList,pos,rewardNum);
	}
 
  	/**
	 * 老虎机列表
	 * @param slotList 老虎机列表
	 */
	public void GC_SLOT_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList slotList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			SlotList slotList_Datas= new SlotList();
			slotList_Datas.slotId = data.GetInt();//老虎机唯一ID
			slotList_Datas.handsel =data.GetLong();
			slotList.Add(slotList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_LIST(slotList);
	}
 
  	/**
	 * 竞赛开始 返回的列表
	 * @param tournamentType 竞赛类型
	 * @param startTime 时间块
	 * @param allBonus 奖金总数
	 * @param totalNum 总人数
	 */
	public void GC_SLOT_TOURNAMENT_START_DATA(InputMessage data) 
	{
		int tournamentType = data.GetInt();		
		long startTime = data.GetLong();
		long allBonus = data.GetLong();
		long totalNum = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TOURNAMENT_START_DATA(tournamentType,startTime,allBonus,totalNum);
	}
 
  	/**
	 * 接收到此消息说明 竞赛处于关闭状态
	 */
	public void GC_SLOT_TOURNAMENT_NOT_OPEN(InputMessage data) 
	{
		SlotHandler.Instance().GC_SLOT_TOURNAMENT_NOT_OPEN();
	}
 
  	/**
	 * 竞技排行榜（始终包括自己）
	 * @param slotRankData 竞赛类型展示
	 */
	public void GC_SLOT_RANK_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList slotRankData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			SlotRankData slotRankData_Datas= new SlotRankData();
			slotRankData_Datas.tournamentType = data.GetInt();//老虎机ID
			slotRankData_Datas.rank = data.GetInt();//名次 -1 表示没有上榜
			slotRankData_Datas.img = data.GetString();//头像
			slotRankData_Datas.name = data.GetString();//名称
			slotRankData_Datas.bonus =data.GetLong();
			slotRankData_Datas.passportId =data.GetLong();
			slotRankData_Datas.vipLevel = data.GetInt();//用户等级
			slotRankData.Add(slotRankData_Datas);
		}
		SlotHandler.Instance().GC_SLOT_RANK_LIST(slotRankData);
	}
 
  	/**
	 * 进入老虎机竞赛页面返回（竞赛 开始和结束都会推送此集合）
	 * @param sngTournamentData 竞赛类型展示
	 */
	public void GC_TOURNAMENT_GET_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList sngTournamentData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			SngTournamentData sngTournamentData_Datas= new SngTournamentData();
			sngTournamentData_Datas.tournamentType = data.GetInt();//竞速类型
			sngTournamentData_Datas.endTimeBlock =data.GetLong();
			sngTournamentData_Datas.totalHuman = data.GetInt();//老虎机竞赛总人数
			sngTournamentData_Datas.sngJackpot =data.GetLong();
			sngTournamentData_Datas.vipLevel = data.GetInt();//用户等级
			sngTournamentData.Add(sngTournamentData_Datas);
		}
		SlotHandler.Instance().GC_TOURNAMENT_GET_LIST(sngTournamentData);
	}
 
  	/**
	 * 返回老虎机前3名排行数据 变化的时候也会主动推给客户端
	 * @param slotRankData 排行榜列表
	 */
	public void GC_SLOT_GET_RANK(InputMessage data) 
	{
		int i,size;
		ArrayList slotRankData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			SlotRankData slotRankData_Datas= new SlotRankData();
			slotRankData_Datas.tournamentType = data.GetInt();//老虎机ID
			slotRankData_Datas.rank = data.GetInt();//名次 -1 表示没有上榜
			slotRankData_Datas.img = data.GetString();//头像
			slotRankData_Datas.name = data.GetString();//名称
			slotRankData_Datas.bonus =data.GetLong();
			slotRankData_Datas.passportId =data.GetLong();
			slotRankData_Datas.vipLevel = data.GetInt();//用户等级
			slotRankData.Add(slotRankData_Datas);
		}
		SlotHandler.Instance().GC_SLOT_GET_RANK(slotRankData);
	}
 
  	/**
	 * 自己获奖记录返回
	 * @param humanSngInfos 个人获奖列表
	 */
	public void GC_SNG_RANK_INFO(InputMessage data) 
	{
		int i,size;
		ArrayList humanSngInfos = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			HumanSngInfo humanSngInfos_Datas= new HumanSngInfo();
			humanSngInfos_Datas.tournamentType = data.GetInt();//竞速类型
			humanSngInfos_Datas.rank = data.GetInt();//名次
			humanSngInfos_Datas.rewardNum =data.GetLong();
			humanSngInfos_Datas.rewardTime =data.GetLong();
			humanSngInfos.Add(humanSngInfos_Datas);
		}
		SlotHandler.Instance().GC_SNG_RANK_INFO(humanSngInfos);
	}
 
  	/**
	 * 竞赛 结束奖励前三名
	 * @param rewardNum 奖励金币数量
	 * @param rank 名次
	 */
	public void GC_SLOT_GET_REWARD(InputMessage data) 
	{
		long rewardNum = data.GetLong();
		int rank = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_GET_REWARD(rewardNum,rank);
	}
 
  	/**
	 * 马来网红倍数
	 * @param winMulId 马来网红倍数ID
	 */
	public void GC_SLOT_TYPE10(InputMessage data) 
	{
		int winMulId = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE10(winMulId);
	}
 
  	/**
	 * 马来网红scatter倍数
	 * @param pos 马来网红scatter倍数
	 */
	public void GC_SLOT_TYPE10_SCATTER(InputMessage data) 
	{
		int pos = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE10_SCATTER(pos);
	}
 
  	/**
	 * 日月潭老虎机返回
	 * @param sunMoonLakeData 日月潭老虎机中奖倍数
	 */
	public void GC_SLOT_TYPE11(InputMessage data) 
	{
		int i,size;
		ArrayList sunMoonLakeData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int sunMoonLakeData_Datas = data.GetInt();
			sunMoonLakeData.Add(sunMoonLakeData_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE11(sunMoonLakeData);
	}
 
  	/**
	 * 维密老虎机scatter返回
	 * @param slotsScatterData 维密老虎机要选择的ID
	 */
	public void GC_SLOT_TYPE12(InputMessage data) 
	{
		int i,size;
		ArrayList slotsScatterData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int slotsScatterData_Datas = data.GetInt();
			slotsScatterData.Add(slotsScatterData_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE12(slotsScatterData);
	}
 
  	/**
	 * 维密老虎机玩家选择ID
	 * @param freeNum 免费次数
	 */
	public void GC_SLOT_TYPE12_CHOOSE(InputMessage data) 
	{
		int freeNum = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE12_CHOOSE(freeNum);
	}
 
  	/**
	 * 维密老虎机自由转动结束后发送这个消息
	 * @param rewardNum 奖金数没有乘倍数前的
	 * @param multiple 倍数
	 */
	public void GC_SLOT_TYPE12_FREE(InputMessage data) 
	{
		long rewardNum = data.GetLong();
		int multiple = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE12_FREE(rewardNum,multiple);
	}
 
  	/**
	 * 水果老虎机 判断是否开始小游戏 bouns
	 * @param currentGold 当前的金币
	 */
	public void GC_SLOT_TYPE3_BOUNS_START(InputMessage data) 
	{
		int i,size;
		ArrayList currentGold = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long currentGold_Datas = data.GetLong();
			currentGold.Add(currentGold_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE3_BOUNS_START(currentGold);
	}
 
  	/**
	 * 宙斯老虎机数据推送
	 * @param isSuccess 是否成功 1 成功 0 失败 ,2开启小游戏
	 * @param bounsNum 可以抽奖次数
	 * @param money 抽奖获得的金钱总数
	 * @param singleBounsNum 点击获得免费次数
	 * @param singleMoney 点击获得的金钱总数
	 */
	public void GC_SLOT_TYPE13_BOUNS(InputMessage data) 
	{
		int isSuccess = data.GetInt();		
		int bounsNum = data.GetInt();		
		long money = data.GetLong();
		long singleBounsNum = data.GetLong();
		long singleMoney = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE13_BOUNS(isSuccess,bounsNum,money,singleBounsNum,singleMoney);
	}
 
  	/**
	 * 宙斯老虎机数据推送(主动推送 全部数据回去)
	 * @param mtType 相对应的 金币 或 免费次数 类型，1:金币，2:免费次数
	 * @param moneyOrTimes 相对应的 金币或者免费次数
	 * @param times 初始的免费次数
	 */
	public void GC_SLOT_TYPE13_SEND_BOUNS(InputMessage data) 
	{
		int i,size;
		ArrayList mtType = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int mtType_Datas = data.GetInt();
			mtType.Add(mtType_Datas);
		}
		ArrayList moneyOrTimes = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long moneyOrTimes_Datas = data.GetLong();
			moneyOrTimes.Add(moneyOrTimes_Datas);
		}
		long times = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE13_SEND_BOUNS(mtType,moneyOrTimes,times);
	}
 
  	/**
	 * 石器时代老虎机有替换元素
	 * @param slotElementListCope 替换后的元素列表
	 */
	public void GC_SLOT_TYPE14(InputMessage data) 
	{
		int i,size;
		ArrayList slotElementListCope = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int slotElementListCope_Datas = data.GetInt();
			slotElementListCope.Add(slotElementListCope_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE14(slotElementListCope);
	}
 
  	/**
	 * 石器时代老虎机有替换元素
	 * @param bounsWeight bonus倍数
	 */
	public void GC_SLOT_TYPE14_BONUS(InputMessage data) 
	{
		int bounsWeight = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE14_BONUS(bounsWeight);
	}
 
  	/**
	 * 石器时代老虎机 苹果
	 * @param bonus14Data 每次敲打的苹果的数据
	 */
	public void GC_SLOT_TYPE14_APPLE_BONUS(InputMessage data) 
	{
		int i,size;
		ArrayList bonus14Data = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			Bonus14Data bonus14Data_Datas= new Bonus14Data();
			bonus14Data_Datas.times = data.GetInt();//元素列表
			bonus14Data_Datas.singleGold =data.GetLong();
			bonus14Data_Datas.overlyingGold =data.GetLong();
			bonus14Data_Datas.singleCollectNum = data.GetInt();//单次 敲打的数量
			bonus14Data_Datas.overlyingCollectNum = data.GetInt();//累计 收集的数量
			bonus14Data.Add(bonus14Data_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE14_APPLE_BONUS(bonus14Data);
	}
 
  	/**
	 * 石器时代老虎机 翻牌小游戏
	 * @param rewardPoolList 流程-每次翻到的牌
	 * @param sameNum 相同 的数量
	 * @param totalGold 获得的总的金币数量
	 */
	public void GC_SLOT_TYPE14_RUNE_BONUS(InputMessage data) 
	{
		int i,size;
		ArrayList rewardPoolList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int rewardPoolList_Datas = data.GetInt();
			rewardPoolList.Add(rewardPoolList_Datas);
		}
		int sameNum = data.GetInt();		
		long totalGold = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE14_RUNE_BONUS(rewardPoolList,sameNum,totalGold);
	}
 
  	/**
	 * 石器时代老虎机 捕猎小游戏
	 * @param nums 捕兽夹 的数量
	 * @param preyNum 捕获到的 野兽的数量
	 * @param perGold 每个猎物获得的金币
	 * @param totalGold 获得的总的金币数
	 */
	public void GC_SLOT_TYPE14_PREY_BONUS(InputMessage data) 
	{
		int nums = data.GetInt();		
		int preyNum = data.GetInt();		
		long perGold = data.GetLong();
		long totalGold = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE14_PREY_BONUS(nums,preyNum,perGold,totalGold);
	}
 
  	/**
	 * 狮身人面老虎机 判断是否开始小游戏 bouns
	 * @param currentGold 当前的金币
	 */
	public void GC_SLOT_TYPE15_BOUNS_START(InputMessage data) 
	{
		int i,size;
		ArrayList currentGold = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long currentGold_Datas = data.GetLong();
			currentGold.Add(currentGold_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE15_BOUNS_START(currentGold);
	}
 
  	/**
	 * 狮身人面老虎机的返回值
	 * @param totalGold 总金币
	 * @param currentGold 当前金币
	 * @param isSuccess 是否成功获取金币  1：是，0：否
	 */
	public void GC_SLOT_TYPE15_BOUNS(InputMessage data) 
	{
		long totalGold = data.GetLong();
		long currentGold = data.GetLong();
		int isSuccess = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE15_BOUNS(totalGold,currentGold,isSuccess);
	}
 
  	/**
	 * 阿兹特克老虎机拼图个数
	 * @param cardNumber 图片个数
	 */
	public void GC_SLOT_TYPE16(InputMessage data) 
	{
		int cardNumber = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE16(cardNumber);
	}
 
  	/**
	 * 狼老虎机bouns玩法
	 * @param bonusNum bouns单独中奖金额
	 * @param wolfTimes 转动次数
	 * @param wolfBonusNum bonus个数
	 * @param bounsConnectInfoData bonus位置
	 */
	public void GC_SLOT_TYPE17(InputMessage data) 
	{
		int i,size;
		long bonusNum = data.GetLong();
		int wolfTimes = data.GetInt();		
		int wolfBonusNum = data.GetInt();		
		ArrayList bounsConnectInfoData = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			BounsConnectInfoData bounsConnectInfoData_Datas= new BounsConnectInfoData();
			int bounsConnectInfoData_j;
				ArrayList bounsConnectInfoData_posList = new ArrayList();
				int bounsConnectInfoData_posListSize = data.GetShort();
				for(bounsConnectInfoData_j=0; bounsConnectInfoData_j<bounsConnectInfoData_posListSize; bounsConnectInfoData_j++){
					int bounsConnectInfoData_posList_Datas = data.GetInt();//位置
					bounsConnectInfoData_posList.Add(bounsConnectInfoData_posList_Datas);
				}
			bounsConnectInfoData_Datas.posList = bounsConnectInfoData_posList;
			bounsConnectInfoData.Add(bounsConnectInfoData_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE17(bonusNum,wolfTimes,wolfBonusNum,bounsConnectInfoData);
	}
 
  	/**
	 * 水晶魔法老虎机拼图个数
	 * @param id 区域ID
	 * @param levelEnough 1：是           0：否
	 * @param mType 1.单线下注额的倍数 2.免费转动次数
	 * @param mt 金钱 money 或者 次数time
	 */
	public void GC_SLOT_TYPE19(InputMessage data) 
	{
		int id = data.GetInt();		
		int levelEnough = data.GetInt();		
		int mType = data.GetInt();		
		long mt = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE19(id,levelEnough,mType,mt);
	}
 
  	/**
	 * 泰国大象老虎机 BigWild
	 * @param column 那一列中了BigWild
	 * @param freeNum 一共获得免费转动次数
	 */
	public void GC_SLOT_TYPE20(InputMessage data) 
	{
		int i,size;
		ArrayList column = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int column_Datas = data.GetInt();
			column.Add(column_Datas);
		}
		int freeNum = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE20(column,freeNum);
	}
 
  	/**
	 * 泰国大象老虎机Bouns小游戏 
	 * @param multiples 宝箱倍数客户端需要缩小100倍
	 */
	public void GC_SLOT_TYPE20_BOUNS(InputMessage data) 
	{
		int i,size;
		ArrayList multiples = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int multiples_Datas = data.GetInt();
			multiples.Add(multiples_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE20_BOUNS(multiples);
	}
 
  	/**
	 * 泰国大象老虎机Bouns小游戏(新的小游戏-替换了老的小游戏) 
	 * @param reward 每次获取的reward
	 * @param isPicture 是否是 相片 0 不是，1 是
	 * @param samePictureGold 相同图片获取的金币
	 * @param totalGold 获得的总金币
	 */
	public void GC_SLOT_TYPE20_BOUNS_NEW(InputMessage data) 
	{
		int i,size;
		ArrayList reward = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long reward_Datas = data.GetLong();
			reward.Add(reward_Datas);
		}
		ArrayList isPicture = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int isPicture_Datas = data.GetInt();
			isPicture.Add(isPicture_Datas);
		}
		ArrayList samePictureGold = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long samePictureGold_Datas = data.GetLong();
			samePictureGold.Add(samePictureGold_Datas);
		}
		long totalGold = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE20_BOUNS_NEW(reward,isPicture,samePictureGold,totalGold);
	}
 
  	/**
	 * 老虎老虎机bouns信息
	 * @param bounsNum bouns个数
	 * @param posList bouns位置
	 * @param rewardTimeList 奖池的倍数从小到大 依次排列
	 * @param isMatch 是否匹配
	 * @param totalGold 对应的奖金
	 * @param rewardPool 第几个奖池
	 */
	public void GC_SLOT_TYPE21_BOUNS_INFO(InputMessage data) 
	{
		int i,size;
		int bounsNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		ArrayList rewardTimeList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long rewardTimeList_Datas = data.GetLong();
			rewardTimeList.Add(rewardTimeList_Datas);
		}
		ArrayList isMatch = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int isMatch_Datas = data.GetInt();
			isMatch.Add(isMatch_Datas);
		}
		ArrayList totalGold = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long totalGold_Datas = data.GetLong();
			totalGold.Add(totalGold_Datas);
		}
		ArrayList rewardPool = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int rewardPool_Datas = data.GetInt();
			rewardPool.Add(rewardPool_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE21_BOUNS_INFO(bounsNum,posList,rewardTimeList,isMatch,totalGold,rewardPool);
	}
 
  	/**
	 * 老虎老虎机bouns信息
	 * @param rewardPool 第几个奖池
	 * @param isMatch 是否匹配：1 是，0 否
	 * @param totalGold 总金币
	 */
	public void GC_SLOT_TYPE21_BOUNS(InputMessage data) 
	{
		int rewardPool = data.GetInt();		
		int isMatch = data.GetInt();		
		int totalGold = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE21_BOUNS(rewardPool,isMatch,totalGold);
	}
 
  	/**
	 * 西部牛仔老虎机挖矿小游戏返回
	 * @param remaining 剩余次数
	 * @param rewardNum 每次获得的金币
	 */
	public void GC_SLOT_TYPE22(InputMessage data) 
	{
		int i,size;
		int remaining = data.GetInt();		
		ArrayList rewardNum = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long rewardNum_Datas = data.GetLong();
			rewardNum.Add(rewardNum_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE22(remaining,rewardNum);
	}
 
  	/**
	 * 东方龙老虎机bouns信息
	 * @param bounsNum bouns个数
	 * @param obtainReward 每次中奖获得的奖金数
	 * @param jackpotInfo 奖池的初始化信息，每个奖池的金额以','分割
	 */
	public void GC_SLOT_TYPE23_BOUNS_INFO(InputMessage data) 
	{
		int bounsNum = data.GetInt();		
		long obtainReward = data.GetLong();
		string jackpotInfo = data.GetString();		
		SlotHandler.Instance().GC_SLOT_TYPE23_BOUNS_INFO(bounsNum,obtainReward,jackpotInfo);
	}
 
  	/**
	 * 东方龙老虎机初始化奖池信息
	 * @param jackpotInfo 奖池的初始化信息，每个奖池的金额以','分割
	 */
	public void GC_SLOT_TYPE23_INIT_REWARD(InputMessage data) 
	{
		string jackpotInfo = data.GetString();		
		SlotHandler.Instance().GC_SLOT_TYPE23_INIT_REWARD(jackpotInfo);
	}
 
  	/**
	 * 巴西风情老虎机数据推送
	 * @param isSuccess 是否成功 1 成功 0 失败 ,2开启小游戏
	 * @param bounsNum 可以抽奖次数
	 * @param money 抽奖获得的金钱总数
	 * @param singleBounsNum 点击获得免费次数
	 * @param singleMoney 点击获得的金钱总数
	 */
	public void GC_SLOT_TYPE24_BOUNS(InputMessage data) 
	{
		int isSuccess = data.GetInt();		
		int bounsNum = data.GetInt();		
		long money = data.GetLong();
		long singleBounsNum = data.GetLong();
		long singleMoney = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE24_BOUNS(isSuccess,bounsNum,money,singleBounsNum,singleMoney);
	}
 
  	/**
	 * 巴西风情 数据推送(主动推送 全部数据回去)
	 * @param mtType 相对应的 金币 或 免费次数 类型，1:金币，2:免费次数
	 * @param moneyOrTimes 相对应的 金币或者免费次数
	 * @param times 初始的免费次数
	 */
	public void GC_SLOT_TYPE24_SEND_BOUNS(InputMessage data) 
	{
		int i,size;
		ArrayList mtType = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int mtType_Datas = data.GetInt();
			mtType.Add(mtType_Datas);
		}
		ArrayList moneyOrTimes = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long moneyOrTimes_Datas = data.GetLong();
			moneyOrTimes.Add(moneyOrTimes_Datas);
		}
		long times = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE24_SEND_BOUNS(mtType,moneyOrTimes,times);
	}
 
  	/**
	 * 巴西风情老虎机触发 bonus 游戏
	 * @param gameType 随机出来要玩 哪个游戏小游戏类型 1：喝酒小游戏 2：桑巴小游戏
	 * @param color 桑巴小游戏 会用到这个，作者:力度条颜色（前端上传） 1：黄色 2：黄绿色 3：绿色
	 * @param chance 桑巴小游戏 会用到这个，用户有几次机会，默认是 1 次
	 * @param round 总共有几轮  喝酒小游戏会用到这个
	 */
	public void GC_SLOT_TYPE24_BOUNS_GAME_START(InputMessage data) 
	{
		int i,size;
		int gameType = data.GetInt();		
		ArrayList color = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int color_Datas = data.GetInt();
			color.Add(color_Datas);
		}
		int chance = data.GetInt();		
		int round = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE24_BOUNS_GAME_START(gameType,color,chance,round);
	}
 
  	/**
	 * 巴西风情老虎机  桑巴  游戏
	 * @param reward 获得的金币
	 */
	public void GC_SLOT_TYPE24_BOUNS_SAMBA(InputMessage data) 
	{
		long reward = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE24_BOUNS_SAMBA(reward);
	}
 
  	/**
	 * 巴西风情老虎机  喝酒  游戏
	 * @param isReward 是否是金币  0：不是，1：是
	 * @param rewards 获得的金币数量集合
	 * @param totalGold 获得的总钱数
	 */
	public void GC_SLOT_TYPE24_BOUNS_BAR(InputMessage data) 
	{
		int i,size;
		ArrayList isReward = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int isReward_Datas = data.GetInt();
			isReward.Add(isReward_Datas);
		}
		ArrayList rewards = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long rewards_Datas = data.GetLong();
			rewards.Add(rewards_Datas);
		}
		long totalGold = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE24_BOUNS_BAR(isReward,rewards,totalGold);
	}
 
  	/**
	 * 忍者老虎机wild信息
	 * @param wildNum wild个数
	 * @param posList wild位置
	 * @param allPosList wild元素替换 本列所有元素后 所有元素的位置
	 */
	public void GC_SLOT_TYPE25_WILD_INFO(InputMessage data) 
	{
		int i,size;
		int wildNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		ArrayList allPosList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int allPosList_Datas = data.GetInt();
			allPosList.Add(allPosList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE25_WILD_INFO(wildNum,posList,allPosList);
	}
 
  	/**
	 * 忍者老虎机bouns信息
	 * @param position 忍者随机位置
	 * @param bounsNum bouns个数
	 * @param posList bouns位置
	 */
	public void GC_SLOT_TYPE25_BOUNS_INFO(InputMessage data) 
	{
		int i,size;
		int position = data.GetInt();		
		int bounsNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE25_BOUNS_INFO(position,bounsNum,posList);
	}
 
  	/**
	 * 忍者老虎机bouns小游戏
	 * @param isMatch 是否匹配：1 是，0否
	 * @param totalGold 中奖额度
	 */
	public void GC_SLOT_TYPE25_BOUNS(InputMessage data) 
	{
		int isMatch = data.GetInt();		
		long totalGold = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE25_BOUNS(isMatch,totalGold);
	}
 
  	/**
	 * 猫老虎机彩金推送
	 * @param slotId 老虎机ID
	 * @param jackpot 获得彩金
	 */
	public void GC_SLOT_TYPE18(InputMessage data) 
	{
		int slotId = data.GetInt();		
		long jackpot = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE18(slotId,jackpot);
	}
 
  	/**
	 * 女巫魔法老虎机wild信息
	 * @param wildNum wild个数
	 * @param posList wild位置
	 * @param allPosList wild元素替换 本列所有元素后 所有元素的位置
	 */
	public void GC_SLOT_TYPE26_WILD_INFO(InputMessage data) 
	{
		int i,size;
		int wildNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		ArrayList allPosList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int allPosList_Datas = data.GetInt();
			allPosList.Add(allPosList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE26_WILD_INFO(wildNum,posList,allPosList);
	}
 
  	/**
	 * 女巫魔法老虎机bouns信息
	 * @param bounsNum bouns个数
	 * @param totalGold 奖金额度
	 * @param posList bouns位置
	 */
	public void GC_SLOT_TYPE26_BOUNS_INFO(InputMessage data) 
	{
		int i,size;
		int bounsNum = data.GetInt();		
		long totalGold = data.GetLong();
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE26_BOUNS_INFO(bounsNum,totalGold,posList);
	}
 
  	/**
	 * 女巫魔法老虎机bouns小游戏
	 * @param totalGold 中奖额度
	 */
	public void GC_SLOT_TYPE26_BOUNS(InputMessage data) 
	{
		int totalGold = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE26_BOUNS(totalGold);
	}
 
  	/**
	 * 犀牛老虎机bouns信息
	 * @param bounsNum bouns个数
	 * @param totalGold 中奖额度
	 */
	public void GC_SLOT_TYPE27_BOUNS_INFO(InputMessage data) 
	{
		int bounsNum = data.GetInt();		
		long totalGold = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE27_BOUNS_INFO(bounsNum,totalGold);
	}
 
  	/**
	 * 海底世界老虎机wild信息
	 * @param wildNum wild个数
	 * @param rate 中奖倍数
	 * @param posList wild位置
	 * @param allPosList wild元素替换 本列所有元素后 所有元素的位置
	 */
	public void GC_SLOT_TYPE28_WILD_INFO(InputMessage data) 
	{
		int i,size;
		int wildNum = data.GetInt();		
		int rate = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		ArrayList allPosList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int allPosList_Datas = data.GetInt();
			allPosList.Add(allPosList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE28_WILD_INFO(wildNum,rate,posList,allPosList);
	}
 
  	/**
	 * 海底世界老虎机bouns信息
	 * @param bounsNum bouns个数
	 * @param posList bouns位置
	 * @param rewardId 奖池的ID
	 */
	public void GC_SLOT_TYPE28_BOUNS_INFO(InputMessage data) 
	{
		int i,size;
		int bounsNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		int rewardId = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE28_BOUNS_INFO(bounsNum,posList,rewardId);
	}
 
  	/**
	 * 海底世界老虎机bouns小游戏
	 * @param rewardId 奖金模板的ID
	 */
	public void GC_SLOT_TYPE28_BOUNS(InputMessage data) 
	{
		int rewardId = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE28_BOUNS(rewardId);
	}
 
  	/**
	 * 海底世界老虎机 新添加龟兔赛跑游戏，用户的 名次永远是数组中的第一个
	 * @param rewards 奖励集合
	 * @param rands 名次集合
	 * @param specilScatter 特殊scatter位置
	 */
	public void GC_SLOT_TYPE28_SCATTER_INFO(InputMessage data) 
	{
		int i,size;
		ArrayList rewards = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long rewards_Datas = data.GetLong();
			rewards.Add(rewards_Datas);
		}
		ArrayList rands = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int rands_Datas = data.GetInt();
			rands.Add(rands_Datas);
		}
		ArrayList specilScatter = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int specilScatter_Datas = data.GetInt();
			specilScatter.Add(specilScatter_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE28_SCATTER_INFO(rewards,rands,specilScatter);
	}
 
  	/**
	 * 西方龙老虎机wild信息
	 * @param wildNum wild个数
	 * @param posList wild位置
	 */
	public void GC_SLOT_TYPE29_WILD_INFO(InputMessage data) 
	{
		int i,size;
		int wildNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE29_WILD_INFO(wildNum,posList);
	}
 
  	/**
	 * 西方龙老虎机bouns信息
	 * @param bounsNum bouns个数
	 * @param posList bouns位置
	 * @param isSon 是否是龙子 1:是龙子，0不是龙子
	 * @param bonus29Data 金币
	 */
	public void GC_SLOT_TYPE29_BOUNS_INFO(InputMessage data) 
	{
		int i,size;
		int bounsNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		ArrayList isSon = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int isSon_Datas = data.GetInt();
			isSon.Add(isSon_Datas);
		}
		ArrayList bonus29Data = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			Bonus29Data bonus29Data_Datas= new Bonus29Data();
			int bonus29Data_j;
				ArrayList bonus29Data_gold = new ArrayList();
				int bonus29Data_goldSize = data.GetShort();
				for(bonus29Data_j=0; bonus29Data_j<bonus29Data_goldSize; bonus29Data_j++){
					int bonus29Data_gold_Datas = data.GetInt();//金币
					bonus29Data_gold.Add(bonus29Data_gold_Datas);
				}
			bonus29Data_Datas.gold = bonus29Data_gold;
			bonus29Data.Add(bonus29Data_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE29_BOUNS_INFO(bounsNum,posList,isSon,bonus29Data);
	}
 
  	/**
	 * 西方龙老虎机bouns小游戏
	 * @param isSon 1:是龙子，0不是龙子
	 * @param gold 金币
	 */
	public void GC_SLOT_TYPE29_BOUNS(InputMessage data) 
	{
		int i,size;
		int isSon = data.GetInt();		
		ArrayList gold = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int gold_Datas = data.GetInt();
			gold.Add(gold_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE29_BOUNS(isSon,gold);
	}
 
  	/**
	 * 福尔摩斯老虎机bouns信息
	 * @param bounsNum bouns个数
	 * @param posList bouns位置
	 * @param times 第几次选择
	 * @param reward 第几次的奖励
	 */
	public void GC_SLOT_TYPE30_BOUNS_INFO(InputMessage data) 
	{
		int i,size;
		int bounsNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		ArrayList times = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int times_Datas = data.GetInt();
			times.Add(times_Datas);
		}
		ArrayList reward = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long reward_Datas = data.GetLong();
			reward.Add(reward_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE30_BOUNS_INFO(bounsNum,posList,times,reward);
	}
 
  	/**
	 * 福尔摩斯老虎机bouns小游戏
	 * @param isSingleWin 1：单个中，0：单个不中
	 * @param isAllWin 1：是全中，0：不全中
	 * @param gold 单次金币
	 * @param totalGold 所有金币
	 */
	public void GC_SLOT_TYPE30_BOUNS(InputMessage data) 
	{
		int isSingleWin = data.GetInt();		
		int isAllWin = data.GetInt();		
		int gold = data.GetInt();		
		int totalGold = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE30_BOUNS(isSingleWin,isAllWin,gold,totalGold);
	}
 
  	/**
	 * 海盗老虎机wild信息
	 * @param wildNum wild个数
	 * @param posList wild位置
	 */
	public void GC_SLOT_TYPE31_WILD_INFO(InputMessage data) 
	{
		int i,size;
		int wildNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE31_WILD_INFO(wildNum,posList);
	}
 
  	/**
	 * 海盗老虎机特殊wild信息
	 * @param wildNum wild个数
	 * @param posList wild位置
	 */
	public void GC_SLOT_TYPE31_SPECIFIC_WILD_INFO(InputMessage data) 
	{
		int i,size;
		int wildNum = data.GetInt();		
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE31_SPECIFIC_WILD_INFO(wildNum,posList);
	}
 
  	/**
	 * 海盗老虎机中了bonus 游戏
	 * @param whichNum 三种小游戏 1：第一个-海盗交锋，2:第二个-海岛钓鱼，3:第三个-宝藏探秘
	 */
	public void GC_SLOT_TYPE31_BONUS(InputMessage data) 
	{
		int i,size;
		ArrayList whichNum = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int whichNum_Datas = data.GetInt();
			whichNum.Add(whichNum_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE31_BONUS(whichNum);
	}
 
  	/**
	 * 海盗老虎机:bonus 游戏 第一个-海盗交锋
	 * @param firstFew 最开始的 子弹数量
	 * @param multiple 金币所翻倍数
	 * @param typeList 类型的集合，0 是金币    1是炮弹 ,和下边的集合一一对应
	 * @param goldsOrNumList 金币或者炮弹的集合
	 */
	public void GC_SLOT_TYPE31_BONUS_ONE(InputMessage data) 
	{
		int i,size;
		int firstFew = data.GetInt();		
		int multiple = data.GetInt();		
		ArrayList typeList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int typeList_Datas = data.GetInt();
			typeList.Add(typeList_Datas);
		}
		ArrayList goldsOrNumList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long goldsOrNumList_Datas = data.GetLong();
			goldsOrNumList.Add(goldsOrNumList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE31_BONUS_ONE(firstFew,multiple,typeList,goldsOrNumList);
	}
 
  	/**
	 * 海盗老虎机:bonus 游戏 第二个-海岛钓鱼
	 * @param rewardPoolList 奖池的倍数从小到大 依次排列
	 * @param matchNumList 钓鱼轨迹
	 * @param gold 最终获得的奖励的金币数量
	 */
	public void GC_SLOT_TYPE31_BONUS_TWO(InputMessage data) 
	{
		int i,size;
		ArrayList rewardPoolList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int rewardPoolList_Datas = data.GetInt();
			rewardPoolList.Add(rewardPoolList_Datas);
		}
		ArrayList matchNumList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int matchNumList_Datas = data.GetInt();
			matchNumList.Add(matchNumList_Datas);
		}
		long gold = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE31_BONUS_TWO(rewardPoolList,matchNumList,gold);
	}
 
  	/**
	 * 海盗老虎机:bonus 游戏 第三个-宝藏探秘
	 * @param whichNum 第几关
	 * @param rewardTypeList 类型 的集合 和 下边顺序对应  奖励类型： 0.金币 1.次数 2.下一关（也有金币奖励）
	 * @param rewardNumList 奖励集合 和上边 顺序对应
	 * @param startNum 用户初始 点击宝藏次数
	 */
	public void GC_SLOT_TYPE31_BONUS_THREE(InputMessage data) 
	{
		int i,size;
		int whichNum = data.GetInt();		
		ArrayList rewardTypeList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int rewardTypeList_Datas = data.GetInt();
			rewardTypeList.Add(rewardTypeList_Datas);
		}
		ArrayList rewardNumList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long rewardNumList_Datas = data.GetLong();
			rewardNumList.Add(rewardNumList_Datas);
		}
		int startNum = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE31_BONUS_THREE(whichNum,rewardTypeList,rewardNumList,startNum);
	}
 
  	/**
	 * 斯巴达老虎机 每个用户在进入到这个老虎机的时候 返回这个消息，显示还有多少个攻城车 就可以攻破城墙了
	 * @param bulletLeftNum 攻城车剩余数量（还差几辆攻城车就会 打开社交活动），如果已经打开就是0个
	 */
	public void GC_SLOT_TYPE32_BULLET_IN(InputMessage data) 
	{
		int bulletLeftNum = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE32_BULLET_IN(bulletLeftNum);
	}
 
  	/**
	 * 斯巴达老虎机攻城车出现 通知当前用户
	 */
	public void GC_SLOT_TYPE32_BULLET_OUT(InputMessage data) 
	{
		SlotHandler.Instance().GC_SLOT_TYPE32_BULLET_OUT();
	}
 
  	/**
	 * 斯巴达老虎机攻城车剩余数量
	 * @param bulletLeftNum 攻城车剩余数量（还差几辆攻城车就会 打开社交活动）
	 * @param userId 当前获得攻城车的人的ID
	 */
	public void GC_SLOT_TYPE32_LEFT_BULLET_NUM(InputMessage data) 
	{
		int bulletLeftNum = data.GetInt();		
		int userId = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE32_LEFT_BULLET_NUM(bulletLeftNum,userId);
	}
 
  	/**
	 * 斯巴达老虎机社交活动开启
	 * @param freeNum 社交的自由转动数量
	 * @param rewardList 社交的奖励 一次排列 
	 */
	public void GC_SLOT_TYPE32_SOCIAL_CONTACT(InputMessage data) 
	{
		int i,size;
		int freeNum = data.GetInt();		
		ArrayList rewardList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long rewardList_Datas = data.GetLong();
			rewardList.Add(rewardList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE32_SOCIAL_CONTACT(freeNum,rewardList);
	}
 
  	/**
	 * 斯巴达老虎机 返回普通wild的集合（用的元素也是那个固定的wild）
	 * @param posList 普通wild的集合
	 * @param wildNum 普通wild的数量
	 */
	public void GC_SLOT_TYPE32_WILD_INFO(InputMessage data) 
	{
		int i,size;
		ArrayList posList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int posList_Datas = data.GetInt();
			posList.Add(posList_Datas);
		}
		int wildNum = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE32_WILD_INFO(posList,wildNum);
	}
 
  	/**
	 * 斯巴达老虎机 闯关游戏
	 * @param levelList 有几条数据就是走了几关，然后每一条数据是每一关 攻击或防守的次数
	 * @param isSuccessList 每一次攻击是否成功，0否，1是  ，顺序排列
	 * @param rewardNumList 每一次攻击获得的奖励，失败就是0
	 */
	public void GC_SLOT_TYPE32_BONUS(InputMessage data) 
	{
		int i,size;
		ArrayList levelList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int levelList_Datas = data.GetInt();
			levelList.Add(levelList_Datas);
		}
		ArrayList isSuccessList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int isSuccessList_Datas = data.GetInt();
			isSuccessList.Add(isSuccessList_Datas);
		}
		ArrayList rewardNumList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long rewardNumList_Datas = data.GetLong();
			rewardNumList.Add(rewardNumList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE32_BONUS(levelList,isSuccessList,rewardNumList);
	}
 
  	/**
	 * 斯巴达老虎机 特殊元素 包含 大战士 小战士 用来固定的
	 * @param smallSoldier 小战士集合
	 * @param bigSoldier 大战士集合
	 */
	public void GC_SLOT_TYPE32_SPECIAL_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList smallSoldier = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int smallSoldier_Datas = data.GetInt();
			smallSoldier.Add(smallSoldier_Datas);
		}
		ArrayList bigSoldier = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int bigSoldier_Datas = data.GetInt();
			bigSoldier.Add(bigSoldier_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE32_SPECIAL_LIST(smallSoldier,bigSoldier);
	}
 
  	/**
	 * 小红帽老虎机bonus小游戏  返回对象
	 * @param rollType 每次摇色子的类型
	 * @param reward 每次摇色子的 值
	 * @param selectNum 摇色子的次数
	 */
	public void GC_SLOT_TYPE33_BONUS_LIST(InputMessage data) 
	{
		int i,size;
		ArrayList rollType = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int rollType_Datas = data.GetInt();
			rollType.Add(rollType_Datas);
		}
		ArrayList reward = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			long reward_Datas = data.GetLong();
			reward.Add(reward_Datas);
		}
		int selectNum = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE33_BONUS_LIST(rollType,reward,selectNum);
	}
 
  	/**
	 * 万圣节老虎机   wild的玩法 
	 * @param multiple 倍数
	 */
	public void GC_SLOT_TYPE38_WILD(InputMessage data) 
	{
		int multiple = data.GetInt();		
		SlotHandler.Instance().GC_SLOT_TYPE38_WILD(multiple);
	}
 
  	/**
	 * 万圣节老虎机   南瓜头给奖励 
	 * @param totalGold 总共中了多少钱
	 */
	public void GC_SLOT_TYPE38_PUMPKIN(InputMessage data) 
	{
		long totalGold = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE38_PUMPKIN(totalGold);
	}
 
  	/**
	 * 万圣节老虎机   jackpot 
	 * @param reward1 第一部分中奖
	 * @param reward2 第二部分中奖
	 * @param number 第二部分中奖的 bonus的数量
	 * @param JackpotList jackpot的位置
	 */
	public void GC_SLOT_TYPE38_JACKPOT(InputMessage data) 
	{
		int i,size;
		long reward1 = data.GetLong();
		long reward2 = data.GetLong();
		long number = data.GetLong();
		ArrayList JackpotList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			int JackpotList_Datas = data.GetInt();
			JackpotList.Add(JackpotList_Datas);
		}
		SlotHandler.Instance().GC_SLOT_TYPE38_JACKPOT(reward1,reward2,number,JackpotList);
	}
 
  	/**
	 * 万圣节老虎机   触发 bonus小游戏 
	 */
	public void GC_SLOT_TYPE38_BONUS_TRIGGER(InputMessage data) 
	{
		SlotHandler.Instance().GC_SLOT_TYPE38_BONUS_TRIGGER();
	}
 
  	/**
	 * 万圣节老虎机   用户玩 bonus小游戏 
	 * @param totalGold 总共中了多少钱
	 */
	public void GC_SLOT_TYPE38_BONUS(InputMessage data) 
	{
		long totalGold = data.GetLong();
		SlotHandler.Instance().GC_SLOT_TYPE38_BONUS(totalGold);
	}
 
  	/**
	 * 请求老虎机缓存消息,服务器没有缓存消息的时候返回
	 */
	public void GC_GET_SLOT_CACHEMSG(InputMessage data) 
	{
		SlotHandler.Instance().GC_GET_SLOT_CACHEMSG();
	}
 
  	/**
	 * 老虎机错误
	 */
	public void GC_SLOT_ERROR(InputMessage data) 
	{
		SlotHandler.Instance().GC_SLOT_ERROR();
	}
 
  	/**
	 * 老虎机彩金 获得的值
	 * @param jackpotReward 获得彩金的数量
	 */
	public void GC_HUMAN_JACKPOT_REWARD(InputMessage data) 
	{
		long jackpotReward = data.GetLong();
		SlotHandler.Instance().GC_HUMAN_JACKPOT_REWARD(jackpotReward);
	}
 
  	/**
	 * 老虎机彩金 获得的值
	 * @param wheelType 转盘的类型（每个类型的转盘 花费的美元不一样）
	 * @param multiple 获得彩金的数量
	 * @param totalGold 计算完毕之后的 用户如果购买 应该得的金币
	 * @param goldType 充钱的类型：0：转动老虎机，1：小游戏
	 */
	public void GC_WINNER_WHEEL(InputMessage data) 
	{
		int wheelType = data.GetInt();		
		int multiple = data.GetInt();		
		long totalGold = data.GetLong();
		int goldType = data.GetInt();		
		SlotHandler.Instance().GC_WINNER_WHEEL(wheelType,multiple,totalGold,goldType);
	}
}