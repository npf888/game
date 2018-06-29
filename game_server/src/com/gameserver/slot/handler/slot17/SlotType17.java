package com.gameserver.slot.handler.slot17;

import java.util.List;
import java.util.Map;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.slot.data.BounsConnectInfoData;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType17;
import com.gameserver.slot.pojo.HumanTemporaryData;
import com.gameserver.slot.template.BonusWolfTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;

/**
 * 
 * 狼像老虎机
 * @author 牛鹏飞
 *
 */
public class SlotType17 extends SlotBase{

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet,
			int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		// 特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		ScatterInfoType17 tempScatterInfo = getScatterInfo(human);
		if (tempScatterInfo.getScatterTemplate() != null) {
			tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			tempReward += tempScatterInfo.getScatterTemplate().getPay() * tempAllBets;
			humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		//每次转动最多出现5个
		int  allBonusNum = tempScatterInfo.getBnus();
		
		HumanSlotManager humanSlotManager =  human.getHumanSlotManager();
		HumanTemporaryData humanTemporaryData= humanSlotManager.getHumanTemporaryData();
		//获取wolf的模板数据
		Map<Integer,List<BonusWolfTemplate>> bonusWolfTemplateMap = Globals.getBonusWolfService().getBonusWolfTemplateMap();
		//每轮的第几次转动
		int times = humanTemporaryData.getWolfTimes();
		if(allBonusNum < 3){
			return tempSpecialConnectInfoData;
		}
		humanTemporaryData.setWolfTimes(times%3==0?3:times%3);
		times = humanTemporaryData.getWolfTimes();
		humanTemporaryData.setBounsConnectInfoData(BounsConnectInfoData.convertFromScatterInfo(tempScatterInfo.getBonusList()));
		//bonus 单独 中奖的金额
		long bonusNum = 0;
		if(bonusWolfTemplateMap.containsKey(times)){
			List<BonusWolfTemplate> bonusWolfTemplateList = bonusWolfTemplateMap.get(times);
			//计算 当前次 bonus 的最大次数
			int maxNum = 0;
			BonusWolfTemplate bonusWolfTemplate = null;
			for(BonusWolfTemplate bw:bonusWolfTemplateList){
				if(bw.getBounsNum() > maxNum){
					maxNum=bw.getBounsNum();
					bonusWolfTemplate = bw;
				}
			}
			if(allBonusNum >= maxNum){
				bonusNum += (bet*Double.valueOf(bonusWolfTemplate.getRewardNum()).longValue())/100;
				slotLog(human,slot.getTempleId(),false,true,false,bonusNum,humanSlotManager.getCurrentBet());
			}else{
				//根据 用户本次转动 获得的所有的bonus个数 和 模板里的bonus的值 进行对比 ,获取 倍数
				for(BonusWolfTemplate bw :bonusWolfTemplateList){
					if(bw.getBounsNum() == allBonusNum){
						bonusNum += (bet*Double.valueOf(bw.getRewardNum()).longValue())/100;
						slotLog(human,slot.getTempleId(),false,true,false,bonusNum,humanSlotManager.getCurrentBet());
						break;
					}
				}
			}
			humanTemporaryData.setWolfBonusNum(3);
			//返回 bouns消息
			GCSlotType17 gCSlotType17 = new GCSlotType17();
			gCSlotType17.setBonusNum(bonusNum);
			gCSlotType17.setWolfTimes(humanTemporaryData.getWolfTimes());
			gCSlotType17.setWolfBonusNum(humanTemporaryData.getWolfBonusNum());
			gCSlotType17.setBounsConnectInfoData(new BounsConnectInfoData[]{humanTemporaryData.getBounsConnectInfoData()});
			player.sendMessage(gCSlotType17);
		}
		//每转动一次,次数加一
		humanTemporaryData.setWolfTimes(times+1);
		
		return tempSpecialConnectInfoData;
	}
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType17 getScatterInfo(Human human){
		
		ScatterInfoType17 tempScatterInfo =new ScatterInfoType17();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
		//bouns个数
		int bounsNum = 0;
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
				}else if(tempSlotsTemplate.getSlotElementType()  == SlotElementType.SLOTTYPE17){
					tempScatterInfo.getBonusList().add(i*row+j);//bons的位置
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
