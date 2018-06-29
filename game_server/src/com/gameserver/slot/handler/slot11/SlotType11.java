package com.gameserver.slot.handler.slot11;

import java.util.List;

import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType11;

/**
 * 日月潭老虎机
 *
 */
public class SlotType11 extends SlotBase{


	private GCSlotType11 sendMessage11(List<Integer> listPost){
		GCSlotType11 message11 = new GCSlotType11();
		message11.setSunMoonLakeData(ArrayUtils.intList2Array(listPost));
		return message11;
	}


	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		List<Integer> listPost = Globals.getRiyuetanMultipleService().getRandPosition();
		 
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			//最后连线位置-1 获取数组位置
			int post = tempSlotConnectInfo.getPaylinesTemplate().getPosition5()-1;
			
			int num = listPost.get(post);
				
			tempReward+=tempSlotConnectInfo.getPay()*bet*num;//奖励
			
			player.sendMessage(sendMessage11(listPost));
		}
		
		
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		ScatterInfo tempScatterInfo = getScatterInfo(human);
		
		if(tempScatterInfo.getScatterTemplate() != null){//有免费次数
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		return tempSpecialConnectInfoData;
	}
	
	
	
	
}
