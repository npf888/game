package com.gameserver.slot.handler.slot1;

import com.gameserver.common.Globals;
import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.template.SlotJackpotNewTemplate;
import com.gameserver.slot.template.SlotsListTemplate;

public class SlotType1 extends SlotBase {

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		SlotsListTemplate slotsListTemplate = Globals.getSlotService().getslotTemplateMap(slot.getTempleId());
		
		
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			//如果本条线是彩金 就 不要再算赔率了
			if(tempSlotConnectInfo.getJackpotid()<=0){
				tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
			}
			SlotJackpotNewTemplate sjt = tempSlotConnectInfo.getSjtNEW();
			/**
			 * 彩金 还有开关     是否开jackpot 1.开 2.关
			 */
			if(slotsListTemplate.getJackpotswitch() == 1){
				try{
					humanJackpot += smh.setJackpotNew(human,slot,bet,sjt,humanJackpot,tempSlotConnectInfo.isJackPort());
					slot.setModified();
				}catch(Exception e){
					logger.error("彩金错误",e);
				}
			}
		}
		
		
		
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		ScatterInfo tempScatterInfo = getScatterInfo(human);
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
			 logger.info("用户的免费转动次数 "+humanSlotManager.getFreeTimes()+"---------------------------------------------------------------------------------");
		}
		
		return tempSpecialConnectInfoData;
	}

}
