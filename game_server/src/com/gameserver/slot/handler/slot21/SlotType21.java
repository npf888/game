package com.gameserver.slot.handler.slot21;

import java.util.ArrayList;
import java.util.List;

import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.BounsTigerService;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType21BounsInfo;
import com.gameserver.slot.pojo.HumanTemporaryData;
import com.gameserver.slot.template.BounsTigerRewardTemplate;
import com.gameserver.slot.template.BounsTigerTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 老虎 老虎机
 * @author 牛鹏飞
 *
 */
public class SlotType21 extends SlotBase{

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet,
			int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		ScatterInfoType21 tempScatterInfo = getScatterInfo(human);
		//处理Scatter
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		//处理bouns
		List<BounsTigerTemplate>  bounsTigers= Globals.getBounsTigerService().getBounsTigerList();
		for(BounsTigerTemplate bounsTigerTemplate:bounsTigers){
			if(tempScatterInfo.getBnus()>=bounsTigerTemplate.getBonusNum()){
				GCSlotType21BounsInfo bounsInfo = new GCSlotType21BounsInfo();
				bounsInfo.setBounsNum(tempScatterInfo.getBnus());
				bounsInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getBonusList()));
				//获取奖池的倍数
				List<BounsTigerRewardTemplate>  bounsTigerRewards= Globals.getBounsTigerService().getBounsTigerRewardList();
				
				long[] rewardTimeList = new long[bounsTigerRewards.size()];
				for(int i=0;i<bounsTigerRewards.size();i++){
					rewardTimeList[i]= Long.valueOf(bounsTigerRewards.get(i).getTimes()).longValue()*bet/100;
				}
				bounsInfo.setRewardTimeList(rewardTimeList);
				
				List<Integer> isMatchList = new ArrayList<Integer>();
				List<Long> totalGoldList = new ArrayList<Long>();
				List<Integer> rewardPoolList = new ArrayList<Integer>();
				//小游戏
				for(int j=0;j<10;j++){
					//随机获取 卡片模板
					BounsTigerRewardTemplate bounsTigerRewardTemplate = Globals.getBounsTigerService().getRewardBYWeight();
					//临时数据
					HumanTemporaryData humanTemporaryData = humanSlotManager.getHumanTemporaryData();
					List<Integer> tigerPickUpRewards = humanTemporaryData.getTigerPickUpRewards();
					//随机获取的奖池
					int rewardPool = bounsTigerRewardTemplate.getRewardPool();
					rewardPoolList.add(rewardPool);
					
					
					//只要 翻到相同卡片就结束了
					for(Integer reward:tigerPickUpRewards){
						if(reward == rewardPool){//如果有匹配的
							long addMoney = (bet*Long.valueOf(bounsTigerRewardTemplate.getTimes()).longValue())/100;
							totalGoldList.add(addMoney);
							isMatchList.add(BounsTigerService.is_mathch_yes);
							//只要 翻到相同卡片就结束了  所以把第几次这个值 置为1
							tigerPickUpRewards.clear();//如果匹配上就清空了
							//增加钱
							slotLog(human,slot.getTempleId(),false,true,false,addMoney,humanSlotManager.getCurrentBet());
							int[] isMatchArr = new int[isMatchList.size()];
							for(int i=0;i<isMatchList.size();i++){
								isMatchArr[i]=isMatchList.get(i);
							}
							long[] totalGoldArr = new long[totalGoldList.size()];
							for(int i=0;i<totalGoldList.size();i++){
								totalGoldArr[i]=totalGoldList.get(i);
							}
							int[] rewardPoolArr = new int[rewardPoolList.size()];
							for(int i=0;i<rewardPoolList.size();i++){
								rewardPoolArr[i]=rewardPoolList.get(i);
							}
							bounsInfo.setIsMatch(isMatchArr);
							bounsInfo.setTotalGold(totalGoldArr);
							bounsInfo.setRewardPool(rewardPoolArr);
							player.sendMessage(bounsInfo);
							return tempSpecialConnectInfoData;
						}
					}
				//如果是第一次肯定不匹配  或者 后边几次 不匹配
				totalGoldList.add(0l);
				isMatchList.add(BounsTigerService.is_mathch_no);
				tigerPickUpRewards.add(bounsTigerRewardTemplate.getRewardPool());
				}
			}
		
		}
		return tempSpecialConnectInfoData;
	}
	
	/**
	 * 重写 SCATTER 计算bouns
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType21 getScatterInfo(Human human){
		
		ScatterInfoType21 tempScatterInfo =new ScatterInfoType21();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
		//bouns 个数
		int  bounsNum = 0;
		int  wildNum = 0;
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
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SLOTTYPE21_BONUS){
					tempScatterInfo.getBonusList().add(i*row+j);//bouns 的位置
					++bounsNum;
				}else if(tempSlotsTemplate.getType() == SlotElementType.WILD.getIndex()){
					++wildNum;
				}
			}
		}
		if(wildNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
  		}
  		if(bounsNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType7.getIndex());
  		}
		tempScatterInfo.setBnus(bounsNum);
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
