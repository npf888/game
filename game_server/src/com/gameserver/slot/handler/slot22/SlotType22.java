package com.gameserver.slot.handler.slot22;

import java.util.List;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.Slot;
import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType22;
import com.gameserver.slot.template.ScatterCowboyTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 西部牛仔 老虎机
 * @author 郭君伟
 *
 */
public class SlotType22 extends SlotBase {
	
	private static int gameType1 = 1;
	private static int gameType2 = 2;
	private static int gameType3 = 3;
	
	private int tempFoundNum;

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		      
		
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
	       //特殊奖励 Scatter玩法
			SpecialConnectInfoData tempSpecialConnectInfoData = null;
			
			ScatterInfo tempScatterInfo = getScatterInfo(human);
			
			/*if(tempScatterInfo.getScatterTemplate() != null){
				 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
				 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
				 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
			}*/
			
			
			ScatterCowboyTemplate sst = Globals.getScatterCowboyService().getSCT(tempSlotsListTemplate.getType(), tempFoundNum);
			
			if(sst != null){
				int gameType = sst.getGameType();
				int num = sst.getRewardNum()/100;
				if(gameType == gameType1){
					tempReward = tempReward*num;
				}else if(gameType == gameType2){
					 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
					 humanSlotManager.addFreeSlot(num);
				}else if(gameType == gameType3){
					//通知客户端有小游戏了
					sendMessage(num,bet);
					humanSlotManager.getHumanTemporaryData().setRemaining(num);
				}
				
			}
		
		return tempSpecialConnectInfoData;
	}
	
	private void sendMessage(int remaining,int bet){
		GCSlotType22 message = new GCSlotType22();
		message.setRemaining(remaining);
		long[] rewardNumArr = new long[remaining];
		long totalGold = 0l;
		for(int i=0;i<remaining;i++){
				Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
				long times = Globals.getScatterCowboyService().getTimes(slot.getType());
				long obtainRewardNum = bet*times/100;
				rewardNumArr[i]=obtainRewardNum;
				totalGold+=obtainRewardNum;
				//增加钱
				//增加钱
		}
		slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
		message.setRewardNum(rewardNumArr);
		
		player.sendMessage(message);
	}
	
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfo getScatterInfo(Human human){
		
		ScatterInfo tempScatterInfo =new ScatterInfo();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		
		//列数
		int col = tempSlotsListTemplate.getColumns();
		//行数
		int row = tempSlotsListTemplate.getRows();
        int scatter = 0;
        int wildNum = 0;
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
				
				if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SLOTTYPE22){
					++tempFoundNum;
				}else if(tempSlotsTemplate.getType() == SlotElementType.WILD.getIndex()){
					++wildNum;
				}
				/*else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SCATTER){
					tempScatterInfo.getPosList().add(i*row+j);//SCATTER 的位置
					++scatter;
				}*/
			}
		}
		if(wildNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
  		}
      /*  List<ScatterTemplate> list = slotService.getScatterTemplate(slot.getType());
		
		ScatterTemplate temp = null;
		int num = 0;
		for(ScatterTemplate tempScatterTemplate :list){
			int scatterNum = tempScatterTemplate.getScatterNum();
			if(scatter>=scatterNum && scatterNum > num){
				num = scatterNum;
				temp = tempScatterTemplate;
			}
		}
		if(temp != null){
			tempScatterInfo.setScatterTemplate(temp);
		}*/
		
		
		return tempScatterInfo;
	}

}
