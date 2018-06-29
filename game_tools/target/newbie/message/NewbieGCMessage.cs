using System.Collections;

public class NewbieGCMessage:AbstractMessageReceiver
{
	protected override void collectObservers()
	{
		register(NetMessageType.GC_SAVE_POINT,GC_SAVE_POINT);
		register(NetMessageType.GC_GET_SAVE_POINT,GC_GET_SAVE_POINT);
		register(NetMessageType.GC_SLOT_NEWBIE,GC_SLOT_NEWBIE);
	}
 
  	/**
	 * 新手存盘点
	 * @param ret 0 成功 1失败
	 */
	public void GC_SAVE_POINT(InputMessage data) 
	{
		int ret = data.GetInt();		
		NewbieHandler.Instance().GC_SAVE_POINT(ret);
	}
 
  	/**
	 * 获取存盘点
	 * @param step 步骤id
	 */
	public void GC_GET_SAVE_POINT(InputMessage data) 
	{
		int step = data.GetInt();		
		NewbieHandler.Instance().GC_GET_SAVE_POINT(step);
	}
 
  	/**
	 * 新手存盘点
	 * @param slotElementList 元素列表
	 * @param slotConnectInfoDataList 连线信息列表
	 * @param rewardNum 收益
	 * @param rewardSum 累计奖金
	 * @param specialConnectInfoDataList 特殊连线信息列表
	 */
	public void GC_SLOT_NEWBIE(InputMessage data) 
	{
		int i,size;
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
			NewbieSlotConnectInfoData slotConnectInfoDataList_Datas= new NewbieSlotConnectInfoData();
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
		long rewardSum = data.GetLong();
		ArrayList specialConnectInfoDataList = new ArrayList();
		size = data.GetShort();
		for(i=0; i<size; i++)
		{
			NewbieSpecialConnectInfoData specialConnectInfoDataList_Datas= new NewbieSpecialConnectInfoData();
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
		NewbieHandler.Instance().GC_SLOT_NEWBIE(slotElementList,slotConnectInfoDataList,rewardNum,rewardSum,specialConnectInfoDataList);
	}
}