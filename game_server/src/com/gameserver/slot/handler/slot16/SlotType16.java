package com.gameserver.slot.handler.slot16;

import java.util.List;

import com.gameserver.common.Globals;
import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType16;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;

/**
 * 
 * 阿兹特克文明
 *
 */
public class SlotType16 extends SlotBase {

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
		
		ScatterInfo tempScatterInfo = getScatterInfo();
		
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		int num = humanSlotManager.getHumanTemporaryData().getCardNumber();
		sendMessage(num); 
		if(Globals.getBonusAztecService().isBonus(slot.getType(), num)){
			int reward = Globals.getBonusAztecService().getRewardNum(slot.getType())/100;
			int obtain = reward*bet;
			//增加钱
			slotLog(human,slot.getTempleId(),false,true,false,obtain,humanSlotManager.getCurrentBet());
			humanSlotManager.getHumanTemporaryData().setCardNumber(num- Globals.getBonusAztecService().getBonus(slot.getType()));
		}
		return tempSpecialConnectInfoData;
	}
	
	private void sendMessage(int cardNum){
		GCSlotType16 message = new GCSlotType16();
		message.setCardNumber(cardNum);
		this.player.sendMessage(message);
	}
	
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfo getScatterInfo(){
		
		ScatterInfo tempScatterInfo =new ScatterInfo();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
		// wild 个数
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
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SLOTTYPE16){
					int cardNew = humanSlotManager.getHumanTemporaryData().getCardNumber()+1;
					humanSlotManager.getHumanTemporaryData().setCardNumber(cardNew);//卡片数量加一
				}else if(tempSlotsTemplate.getType() == SlotElementType.WILD.getIndex()){
					++wildNum;
				}
			}
		}
		if(wildNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
  		}
		List<ScatterTemplate> list = slotService.getScatterTemplate(slot.getType());
		
		if(list != null && list.size() >0){
			//先计算最大的配置的scatterMax
			int scatterMax = 0;
			ScatterTemplate maxScatterTemplate = null;
			for(ScatterTemplate tempScatterTemplate :list){
				int scatterNum = tempScatterTemplate.getScatterNum();
				if(scatterNum>scatterMax){
					scatterMax=scatterNum;
					maxScatterTemplate=tempScatterTemplate;
				}
			}
			//如果转动老虎机获得的scatter大于等于最大值，就去最大的 tempScatterTemplate
			if(tempFoundNum>=scatterMax){
				tempScatterInfo.setScatterTemplate(maxScatterTemplate);
				return tempScatterInfo;
			}
			for(ScatterTemplate tempScatterTemplate :list){
				int scatterNum = tempScatterTemplate.getScatterNum();
				if(tempFoundNum==scatterNum){
					tempScatterInfo.setScatterTemplate(tempScatterTemplate);
					return tempScatterInfo;
				}
			}
		}
		
		return tempScatterInfo;
	}


}
