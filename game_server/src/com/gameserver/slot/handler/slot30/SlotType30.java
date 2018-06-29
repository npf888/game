package com.gameserver.slot.handler.slot30;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType30BounsInfo;
import com.gameserver.slot.template.BonusHolmesRewardTemplate;
import com.gameserver.slot.template.BonusHolmesTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 福尔摩斯老虎机
 * @author 牛鹏飞
 *
 */
public class SlotType30 extends SlotBase{

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
		
		ScatterInfoType30 tempScatterInfo = getScatterInfo(human);
		//处理Scatter
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		//bonus 是否大于 配置中的（3）个
		List<BonusHolmesTemplate> bonusHolmesTemplateList= Globals.getBonusHolmesService().getBonusHolmesTemplateMap().get(tempSlotsListTemplate.getType());
		for(BonusHolmesTemplate BonusHolmesTemplate:bonusHolmesTemplateList){
			if(tempScatterInfo.getBnus()>=BonusHolmesTemplate.getBonusNum()){
				GCSlotType30BounsInfo bounsInfo = new GCSlotType30BounsInfo();
				bounsInfo.setBounsNum(tempScatterInfo.getBnus());
				bounsInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getBonusList()));
				//小游戏
				
				
				Map<Integer, List<BonusHolmesRewardTemplate>>  bhrtMap=Globals.getBonusHolmesService().getBonusHolmesRewardTemplateMap();
				List<BonusHolmesRewardTemplate> bonusHolmesRewardTemplateList=bhrtMap.get(slot.getType());
				//总共就四次
				int[] times = new int[4]; 
				long[] reward = new long[4]; 
				long totalGold = 0;
				for(int i=0;i<4;i++){
					for(BonusHolmesRewardTemplate bhr:bonusHolmesRewardTemplateList){
						int num = bhr.getNum();
						//如果是第一次 ，第二次，第三次.... 进行计算
						if((i+1) == num){
							long rewardNum = bhr.getRewardNum();
							int probability = bhr.getProbability();
							boolean isWin = false;
							if(probability >= 100){
								isWin = true;
							}else{
								Random random = new Random();
								int rand = random.nextInt(100);
								if(rand <= probability){
									isWin =true;
								}
							}
							if(isWin){
								long curGold = (bet*rewardNum)/100;
								times[i]=i;
								reward[i]=curGold;
								totalGold+=curGold;
							}else{
								times[i]=i;
								reward[i]=0;
							}
							break;
						}
					}
				
				}
				slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
				bounsInfo.setTimes(times);
				bounsInfo.setReward(reward);
				player.sendMessage(bounsInfo);
				break;
			}
		}
		return tempSpecialConnectInfoData;
	}
	
	
	

	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType30 getScatterInfo(Human human){
		
		ScatterInfoType30 tempScatterInfo =new ScatterInfoType30();
		 List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
			// scatter 个数
			int tempFoundNum = 0;
			// bonus的 个数
			int bonusNum = 0;
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
					}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BONUS30){
						tempScatterInfo.getBonusList().add(i*row+j);//bonus的位置  的位置
						++bonusNum;
					}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.WILD){
						++wildNum;
					}
				}
			}
			if(wildNum >= 3){
	  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
	  		}
	  		if(bonusNum >= 3){
	  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType7.getIndex());
	  		}
			tempScatterInfo.setBnus(bonusNum);
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
