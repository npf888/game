package com.gameserver.slot.handler.slot33;

import java.util.ArrayList;
import java.util.List;

import com.core.util.RandomUtil;
import com.gameserver.common.Globals;
import com.gameserver.slot.RedgirlService;
import com.gameserver.slot.data.GcRemoveSlotSlotList;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.handler.SlotRemoveBase;
import com.gameserver.slot.msg.GCRemoveSlotSlot;
import com.gameserver.slot.msg.GCSlotType33BonusList;
import com.gameserver.slot.template.BounsRedgirlRewardTemplate;

/**
 * 小红帽 老虎机
 * @author JavaServer
 *
 */
public class SlotType33 extends SlotRemoveBase{
	
	
	
	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet,
			int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		
			for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
				SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
				tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
				tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
			}
			
			specificRemoveSlot(null,free,bet,tempAllBets,tempSlotConnectInfoDataArr);
			
			/**
			 * 发送三消转动的消息 全部发出去
			 */
			GCRemoveSlotSlot GCRemoveSlotSlot = new GCRemoveSlotSlot();
			List<GcRemoveSlotSlotList> GcRemoveSlotSlotLists = human.getHumanSlotManager().getHumanTemporaryData().getGcRemoveSlotSlotListArr();
			GcRemoveSlotSlotList[] GcRemoveSlotSlotListArr = new GcRemoveSlotSlotList[GcRemoveSlotSlotLists.size()];
			for(int i=0;i<GcRemoveSlotSlotLists.size();i++){
				GcRemoveSlotSlotListArr[i]=GcRemoveSlotSlotLists.get(i);
			}
			GCRemoveSlotSlot.setGcRemoveSlotSlotList(GcRemoveSlotSlotListArr);
			List<SlotConnectInfoData> SlotConnectInfoDatas = human.getHumanSlotManager().getHumanTemporaryData().getSlotConnectInfoDataList();
			SlotConnectInfoData[] SlotConnectInfoDataArr = new SlotConnectInfoData[SlotConnectInfoDatas.size()];
			for(int i=0;i<SlotConnectInfoDatas.size();i++){
				SlotConnectInfoDataArr[i]=SlotConnectInfoDatas.get(i);
			}
			GCRemoveSlotSlot.setSlotConnectInfoDataList(SlotConnectInfoDataArr);
			player.sendMessage(GCRemoveSlotSlot);
			//特殊奖励 Scatter玩法
			SpecialConnectInfoData tempSpecialConnectInfoData = null;
			
			int time = Globals.getRedgirlService().getNumByTimes(slot.getType(), human.getHumanSlotManager().getHumanTemporaryData().getRemoveTimes());
			ScatterInfoType33 tempScatterInfo = getScatterInfo();
			//free 自由转动 
			if(time > 0){
				tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
				humanSlotManager.addFreeSlot(time);
			}
			
			/**
			 * 这个老虎机没有 scatter 元素
			 * 根据 消掉的元素 而定， 消到几个有几个
			 */
			if(free == 2){
				 humanSlotManager.addFreeSlot(1);
			}
			//bonus 是否大于 配置中的（3）个
			int bonusNum = Globals.getRedgirlService().getBonusNumBySlotNum(tempSlotsListTemplate.getType());
			if(tempScatterInfo.getBnus()>=bonusNum){
				List<Integer> rollTypeList = new ArrayList<Integer>();
				List<Long> rewardList = new ArrayList<Long>();
				int selectNum = Globals.getRedgirlService().getSelectNumBySlotNum(tempSlotsListTemplate.getType());
				long totalGold = 0l;
				int position = 0;
				for(int i=0;i<selectNum;i++){
					//每次都要 要一次色子
					int num = RandomUtil.nextInt(1, 7);
					//摇几步 走几步
					position+=num;
					BounsRedgirlRewardTemplate bounsRedgirlRewardTemplate = Globals.getRedgirlService().rollTheDice(tempSlotsListTemplate.getType(),position);
					if(bounsRedgirlRewardTemplate == null){
						continue;
					}
					//这个id表示 第几个格子
					long times = bounsRedgirlRewardTemplate.getTimes();
					int type = bounsRedgirlRewardTemplate.getType();
					if(type==RedgirlService.bet.intValue()){//1：单线下注额倍数；
						totalGold+=bet*times/100;
						rollTypeList.add(type);
						rewardList.add(bet*times/100);
					}else if(type==RedgirlService.back_two.intValue()){//2：后退2步
						position-=2;
						rollTypeList.add(type);
						rewardList.add(-2l);
					}else if(type==RedgirlService.back_four.intValue()){//3.前进4步
						position+=4;
						rollTypeList.add(type);
						rewardList.add(4l);
					}else if(type==RedgirlService.reward.intValue()){//4.宝箱奖励 这个就不用乘以单线下注额的 倍数
						totalGold+=bet*times/100;
						rollTypeList.add(type);
						rewardList.add(bet*times/100);
					}
				}
				
				//加钱
				slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
				
				
				int[] rollTypeArr = new int[rollTypeList.size()];
				for(int i=0;i<rollTypeList.size();i++){
					rollTypeArr[i]=rollTypeList.get(i);
				}
				long[] rewardArr = new long[rewardList.size()];
				for(int i=0;i<rewardList.size();i++){
					rewardArr[i]=rewardList.get(i);
				}
				GCSlotType33BonusList gCSlotType33BonusList = new GCSlotType33BonusList();
				gCSlotType33BonusList.setReward(rewardArr);
				gCSlotType33BonusList.setRollType(rollTypeArr);
				gCSlotType33BonusList.setSelectNum(selectNum);
				player.sendMessage(gCSlotType33BonusList);
			}
			return tempSpecialConnectInfoData;
	}

	@Override
	public void specificRemoveSlot(List<Integer> tempSlotElements,int free, int bet,
			int tempAllBets, SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		if(tempSlotElements==null){
			handleSlot(2, bet, tempAllBets);
			return;
		}
		int length = tempSlotConnectInfoDataArr.length;
		for(int i=0;i<length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempRemoveReward+=tempSlotConnectInfo.getPay()*bet;//一次 付费转动的  三消转动 每次奖励
			tempTotalRemoveReward+=tempSlotConnectInfo.getPay()*bet;//一次 付费转动的  三消转动 累计奖励
		}
		
		/**
		 * 每次三消转动加钱
		 */
		slotLog(human,slot.getTempleId(),false,true,false,tempRemoveReward,humanSlotManager.getCurrentBet());
		/**
		 * 只要有连线 就会消除元素
		 */
		if(tempSlotConnectInfoDataArr != null && tempSlotConnectInfoDataArr.length>0){
			
			saveHumanMessage(tempSlotElements,tempSlotConnectInfoDataArr,free);
			handleSlot(2, bet, tempAllBets);
		}
	}
	
	

}
