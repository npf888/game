package com.gameserver.slot.handler.slot27;

import java.util.List;
import java.util.Map;

import com.gameserver.common.Globals;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType27BounsInfo;
import com.gameserver.slot.template.BounsRhinoTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 犀牛 老虎机
 * @author JavaServer
 *
 */
public class SlotType27  extends SlotBase{


	
	
	

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
		
		ScatterInfoType27 tempScatterInfo = getScatterInfo();
		//处理Scatter
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		//bonus 是否大于 配置中的（3）个
		Map<Integer, List<BounsRhinoTemplate>> bounsRhinoTemplateMap = Globals.getRhinoService().getBounsRhinoTemplateMap();
		List<BounsRhinoTemplate> bounsRhinoTemplates= bounsRhinoTemplateMap.get(tempSlotsListTemplate.getType());
		int slotMaxBonusNum = Globals.getRhinoService().getSlotMaxBonusNum().get(tempSlotsListTemplate.getType());
		long totalBet = humanSlotManager.getCurrentBet();
		GCSlotType27BounsInfo bounsInfo = new GCSlotType27BounsInfo();
		bounsInfo.setBounsNum(tempScatterInfo.getBnus());
//		bounsInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getBonusList()));
		long totalGold = 0;
		long maxRewardNum = 0;
		for(BounsRhinoTemplate bounsRhinoTemplate:bounsRhinoTemplates){
			if(bounsRhinoTemplate.getBounsNum() == slotMaxBonusNum){
				maxRewardNum=bounsRhinoTemplate.getRewardNum();
			}
		}
		//如果是 最大值 就直接 return 了，结束了
		if(tempScatterInfo.getBnus() >= slotMaxBonusNum){
			totalGold=(totalBet*maxRewardNum)/100;
			logger.info("in 2..............totalGold:::"+totalGold);
			bounsInfo.setTotalGold(totalGold);
			slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
			player.sendMessage(bounsInfo);
			return tempSpecialConnectInfoData;
		}
		for(BounsRhinoTemplate bounsRhinoTemplate:bounsRhinoTemplates){
			int bonusNum = bounsRhinoTemplate.getBounsNum();
			int rewardNum = bounsRhinoTemplate.getRewardNum();
			if(bonusNum == tempScatterInfo.getBnus()){
				totalGold=(totalBet*rewardNum)/100;
				logger.info("in 1..............totalGold:::"+totalGold);
				bounsInfo.setTotalGold(totalGold);
				slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
				player.sendMessage(bounsInfo);
				break;
			}
			/*logger.info("in..............bonusNum::"+bonusNum+"  tempScatterInfo.getBnus()"+tempScatterInfo.getBnus());
			if(bonusNum == tempScatterInfo.getBnus()){
				totalGold=(int)(totalBet*rewardNum)/100;
				logger.info("in 1..............totalGold:::"+totalGold);
				bounsInfo.setTotalGold(totalGold);
				slotLog(human,slot.getTempleId(),false,true,false,Long.valueOf(totalGold),humanSlotManager.getCurrentBet());
				player.sendMessage(bounsInfo);
				break;
			}else if(bonusNum == slotMaxBonusNum &&   tempScatterInfo.getBnus() > slotMaxBonusNum ){
				totalGold=(int)(totalBet*rewardNum)/100;
				logger.info("in 2..............totalGold:::"+totalGold);
				bounsInfo.setTotalGold(totalGold);
				slotLog(human,slot.getTempleId(),false,true,false,Long.valueOf(totalGold),humanSlotManager.getCurrentBet());
				player.sendMessage(bounsInfo);
				break;
			}*/
		}
		return tempSpecialConnectInfoData;
	}
	
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType27 getScatterInfo(){
		
		ScatterInfoType27 tempScatterInfo =new ScatterInfoType27();
		  List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
			// scatter 个数
		  	int scatters = 0;
			//WILD 个数
			int  wildNum = 0;
			//bouns 个数
			int  bounsNum = 0;
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
					
		        	if(tempSlotsTemplate.getSlotElementType() == SlotElementType.WILD){
		        		wildNum++;
		        		tempScatterInfo.getBonusList().add(i*row+j);
		        	}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SCATTER){
		        		scatters++;
		        		tempScatterInfo.getPosList().add(i*row+j);
		        	}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BONUS27){
						tempScatterInfo.getWildList().add(i*row+j);//wild 的位置
						++bounsNum;
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
				tempScatterInfo.setWild(wildNum);
				
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
					if(scatters>=scatterMax){
						tempScatterInfo.setScatterTemplate(maxScatterTemplate);
						return tempScatterInfo;
					}
					for(ScatterTemplate tempScatterTemplate :list){
						int scatterNum = tempScatterTemplate.getScatterNum();
						if(scatters==scatterNum){
							tempScatterInfo.setScatterTemplate(tempScatterTemplate);
							return tempScatterInfo;
						}
					}
				}
				return tempScatterInfo;
		 }
	
	
}
