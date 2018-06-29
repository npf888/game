package com.gameserver.slot.handler.slot19;

import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.ScatterCrystalService;
import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType19;
import com.gameserver.slot.template.ScatterCrastalTemplate;
import com.gameserver.slot.template.ScatterCrystalRewardTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/** 
 * 水晶魔法 老虎机
 * @author 牛鹏飞
 *
 */
public class SlotType19 extends SlotBase{
	 protected Logger logger = Loggers.slotLogger;
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

			ScatterInfo tempScatterInfo = this.getScatterInfo(human);

			if (tempScatterInfo.getScatterTemplate() != null) {
				tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
				//三个scatter 触发小游戏
				
				ScatterCrystalRewardTemplate scatterCrystalReward  = Globals.getScatterCrystalService().getScatterByWeight();
				
				//判断 此用户的 等级是否足够玩此游戏
				long levelDown = scatterCrystalReward.getLevelDown();
				long levelUp = scatterCrystalReward.getLevelUp();
				long level = human.getLevel();
				logger.info("---"+human.getPassportId()+"-[水晶魔法]---[小游戏]--level"+level);
				GCSlotType19 gCSlotType19 = new GCSlotType19();
				if(level < levelDown || level > levelUp){
					gCSlotType19.setId(scatterCrystalReward.getId());
					gCSlotType19.setLevelEnough(ScatterCrystalService.level_enough_no);
					player.sendMessage(gCSlotType19);
					return tempSpecialConnectInfoData;
				}else{
					gCSlotType19.setId(scatterCrystalReward.getId());
					gCSlotType19.setLevelEnough(ScatterCrystalService.level_enough_yes);
				}
				int mType = scatterCrystalReward.getType();// 获取的 1：金额  或者次数 2：次数
				if(mType == ScatterCrystalService.type_money){
					gCSlotType19.setMType(ScatterCrystalService.type_money);
					long addMoney = (Long.valueOf(bet).longValue()*Long.valueOf(scatterCrystalReward.getRewardNum()).longValue())/100;
					gCSlotType19.setMt(addMoney);
					//增加钱
					slotLog(human,slot.getTempleId(),false,true,false,addMoney,humanSlotManager.getCurrentBet());
					player.sendMessage(gCSlotType19);
					return tempSpecialConnectInfoData;
				}else if(mType == ScatterCrystalService.type_times){
					gCSlotType19.setMType(ScatterCrystalService.type_times);
					long addTimes = scatterCrystalReward.getRewardNum()/100;
					gCSlotType19.setMt(Long.valueOf(addTimes).intValue());
					//增加次数
					humanSlotManager.addFreeSlot(Long.valueOf(addTimes).intValue());
					player.sendMessage(gCSlotType19);
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
	protected ScatterInfo getScatterInfo(Human human){
		
		ScatterInfo tempScatterInfo =new ScatterInfo();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
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
				
				if(tempSlotsTemplate.getSlotElementType() == SlotElementType.CRYSTAL18){
					tempScatterInfo.getPosList().add(i*row+j);//SCATTER 的位置
					++tempFoundNum;
				}else if(tempSlotsTemplate.getType() == SlotElementType.WILD.getIndex()){
					++wildNum;
				}
			}
		}
		if(wildNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
  		}
		List<ScatterCrastalTemplate> sctList = Globals.getScatterCrystalService().getScatterCrastalList();
		if(sctList != null && sctList.size() >0){
			//先计算最大的配置的scatterMax
			int scatterMax = 0;
			ScatterTemplate maxScatterTemplate = null;
			for(ScatterCrastalTemplate scatterCrastalTemplate :sctList){
				int scatterNum = scatterCrastalTemplate.getScatterNum();
				if(scatterNum>scatterMax){
					scatterMax=scatterNum;
					maxScatterTemplate=crastalToScatter(scatterCrastalTemplate);
				}
			}
			//如果转动老虎机获得的scatter大于等于最大值，就去最大的 tempScatterTemplate
			if(tempFoundNum>=scatterMax){
				tempScatterInfo.setScatterTemplate(maxScatterTemplate);
				return tempScatterInfo;
			}
			for(ScatterCrastalTemplate scatterCrastalTemplate :sctList){
				int scatterNum = scatterCrastalTemplate.getScatterNum();
				if(tempFoundNum==scatterNum){
					tempScatterInfo.setScatterTemplate(crastalToScatter(scatterCrastalTemplate));
					return tempScatterInfo;
				}
			}
		}
		
		/*for(ScatterCrastalTemplate tempScatterTemplate :sctList){
			int scatterNum = tempScatterTemplate.getScatterNum();
			if(tempFoundNum>=scatterNum){
				tempScatterInfo.setScatterTemplate(crastalToScatter(tempScatterTemplate));
				return tempScatterInfo;
			}
			
		}*/
		
		return tempScatterInfo;
	}
	
	private ScatterTemplate crastalToScatter(ScatterCrastalTemplate tempScatterTemplate){
		ScatterTemplate scatterTemplate= new ScatterTemplate();
		scatterTemplate.setId(tempScatterTemplate.getId());
		scatterTemplate.setNameId(tempScatterTemplate.getNameId());
		scatterTemplate.setLangDesc(tempScatterTemplate.getLangDesc());
		scatterTemplate.setDescrip(tempScatterTemplate.getDescrip());
		scatterTemplate.setIcon(tempScatterTemplate.getIcon());
		scatterTemplate.setSlotsNum(tempScatterTemplate.getSlotsNum());
		scatterTemplate.setScatterNum(tempScatterTemplate.getScatterNum());
		scatterTemplate.setXlsFileName(tempScatterTemplate.getXlsFileName());
		scatterTemplate.setSheetName(tempScatterTemplate.getSheetName());
		return scatterTemplate;
	}
	
	
}
