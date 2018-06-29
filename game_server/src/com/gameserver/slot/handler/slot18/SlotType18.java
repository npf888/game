package com.gameserver.slot.handler.slot18;

import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType18;
/**
 * 猫老虎机
 * @author 郭君伟
 *
 */
public class SlotType18 extends SlotBase {

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
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		if(humanJackpot > 0){
			GCSlotType18 message = new GCSlotType18();
			message.setSlotId(this.slot.getTempleId());
			message.setJackpot(humanJackpot);
			player.sendMessage(message);
		}
		
		return tempSpecialConnectInfoData;
	}

}
