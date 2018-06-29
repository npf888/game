package com.gameserver.slot.handler.slot10;

import java.util.List;

import com.gameserver.common.Globals;
import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType10;
import com.gameserver.slot.msg.GCSlotType10Scatter;
import com.gameserver.slot.template.BoxTemplate;
import com.gameserver.slot.template.WinMultipleTemplate;
/**
 * 马来网红老虎机
 *
 */
public class SlotType10 extends SlotBase {


	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		
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
		
		
		if(tempScatterInfo.getScatterTemplate() != null){//触发小游戏 大转盘，前端发完了 大转盘 会发送 CGFreeSlotReward 消息结算
			GCSlotType10Scatter gCSlotType11 = new GCSlotType10Scatter();
			List<BoxTemplate> boxTemplateList=Globals.getSlotService().getRandomBoxListByTypeLevel(slot.getType(), human.getLevel());
			gCSlotType11.setPos(boxTemplateList.get(0).getId());
			player.sendMessage(gCSlotType11);
		}else{
			//这个是 马来网红中间 横条的
			if(free != 1){//在没有免费的情况下有效果
				GCSlotType10 gCSlotType10 = new GCSlotType10();
				WinMultipleTemplate wt = slotService.getWinMultip();
				 if(wt == null){
					 gCSlotType10.setWinMulId(1);
				 }else{
					 float winMul = wt.getTimes()/100f;
					 gCSlotType10.setWinMulId(wt.getId());
					 tempReward = (long)Math.floor(tempReward*winMul);
				 }
				player.sendMessage(gCSlotType10);
			}
		}
		return tempSpecialConnectInfoData;
	}

	
	

}
