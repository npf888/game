package com.gameserver.slot.handler.slot12;

import java.util.List;
import java.util.Map;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType12;
import com.gameserver.slot.pojo.HumanTemporaryData;
import com.gameserver.slot.template.ScatterMultipleTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 维密老虎机
 * @author 郭君伟
 *
 */
public class SlotType12 extends SlotBase {



	@Override
	protected ScatterInfoType12 getScatterInfo(Human human) {
		ScatterInfoType12 tempScatterInfo =new ScatterInfoType12();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
		//wild的个数
		int wildNum = 0;
		//列数
		int col = tempSlotsListTemplate.getColumns();
		//行数
		int row = tempSlotsListTemplate.getRows();

		for(int i=0;i< col;i++){
			
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			//随机的步
			int tempIthReelPos = humanSlotManager.getCurrentSlotPosList().get(i);
			
			//ith reel from j to j+row
			for(int j=0;j<row;j++){
				
				int tempTurn =tempIthReelPos +j;
				
				//翻页了（循环起来一个圆）
				tempTurn = tempTurn%tempScrollList.size();
				
				SlotsTemplate tempSlotsTemplate = tempScrollList.get(tempTurn);
				
				if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SCATTER){
					tempScatterInfo.getPosList().add(i*row+j);//SCATTER 的位置
					++tempFoundNum;
					//判断有几个wild 然后调用 TaskService 的 spinSlot 方法
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.WILD){
					++wildNum;
				}
			}
		}
		//wild 大于等于 3 固定条件 则去调用  TaskService 的 spinSlot 方法
		if(wildNum >= 3){
			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
		}
		Map<Integer, List<ScatterMultipleTemplate>> map =  Globals.getScatterMultipleService().getMapDate(slot.getType());
		List<ScatterMultipleTemplate> list = map.get(tempFoundNum);
		if(list != null){
			int[] num = new int[list.size()];
			for(int i = 0;i < list.size();i++){
				num[i] = list.get(i).getId();
			}
			tempScatterInfo.setNum(num);
		}
		return tempScatterInfo;
	}

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,SlotConnectInfoData[] tempSlotConnectInfoDataArr) {

		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		HumanTemporaryData humanTemporaryData = humanSlotManager.getHumanTemporaryData();
//		if(humanSlotManager.getFreeTimes()-humanSlotManager.getUseTimes() <= 0){ //在免费的情况下不计算Scatter
		if(free == 0){ //在免费的情况下不计算Scatter
			humanTemporaryData.setSlot12FreeTimesWinRewards(0);
			ScatterInfoType12 tempScatterInfo = getScatterInfo(human);
				 if(tempScatterInfo.getNum() != null && tempScatterInfo.getNum().length > 0){
					 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
					 GCSlotType12 message12 = new GCSlotType12();
					 message12.setSlotsScatterData(tempScatterInfo.getNum());
				     player.sendMessage(message12);
				 }
		}else{
			long aa = humanTemporaryData.getSlot12FreeTimesWinRewards();
			humanTemporaryData.setSlot12FreeTimesWinRewards(humanTemporaryData.getSlot12FreeTimesWinRewards()+tempReward);
		}
		return tempSpecialConnectInfoData;
	}
	
	
	
	
	

}
