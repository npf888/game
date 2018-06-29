package com.gameserver.slot.handler.slot8;

import java.util.ArrayList;
import java.util.List;

import com.common.LogReasons;
import com.core.util.ArrayUtils;
import com.core.util.RandomUtil;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.slot.Slot;
import com.gameserver.slot.data.ScatterRotaryInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCRotaryTable;
import com.gameserver.slot.template.BigWheelTemplate;
import com.gameserver.slot.template.BounsGameTemplate;
import com.gameserver.slot.template.GoldBonusTemplate;
import com.gameserver.slot.template.SlotJackpotTemplate;
import com.gameserver.slot.template.SlotsListTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 澳门女神老虎机
 *
 */
public class SlotType8 extends SlotBase {


	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
			/*SlotJackpotTemplate sjt = tempSlotConnectInfo.getSjt();
			if(sjt != null){
				humanJackpot = smh.setJackpot(slot,sjt,humanJackpot,tempSlotConnectInfo.isJackPort());
			}*/
		}
		
		List<Integer> bounsList = new ArrayList<Integer>();
		List<Integer> bounsListPosition = new ArrayList<Integer>();
		
		ScatterRotaryInfo info = getScatterInfoBigwheel(human,bet);
		
		bounsList = info.getBounsList();
		bounsListPosition = info.getBounsListPosition();
		
		GCRotaryTable rotaryTable = new GCRotaryTable();
		rotaryTable.setFirstType(info.getType());
		rotaryTable.setSecondIndex(info.getSecondIndex());
		rotaryTable.setThirdIndex(info.getThirdIndex());
		rotaryTable.setBigWheelIndex(info.getBigWheelIndex());
		rotaryTable.setPosList(ArrayUtils.intList2Array(info.getPosList()));
		rotaryTable.setBounsList(ArrayUtils.intList2Array(bounsList));
		rotaryTable.setBounsListPosition(ArrayUtils.intList2Array(bounsListPosition));
		player.sendMessage(rotaryTable);
		
		return null;
	}
	
    private ScatterRotaryInfo getScatterInfoBigwheel(Human human,int linebet){
		
		ScatterRotaryInfo tempScatterInfo =new ScatterRotaryInfo();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
		
		Slot slot =  slotService.getSlotById(humanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		
		List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		
		int tempFoundNum = 0;
		//wild 数量
		int wildNum = 0;
		//列数
		int col = tempHumanSlotManager.getCurrentSlotPosList().size();
		//行数
		int row = tempSlotsListTemplate.getRows();
		
		int bounsGameGold = 0;
		
		for(int i=0;i< col;i++){
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			//随机的步
			int tempIthReelPos = tempHumanSlotManager.getCurrentSlotPosList().get(i);
			
			//ith reel from j to j+row
			for(int j=0;j<row;j++){
				
				int tempTurn =tempIthReelPos +j;
				//翻页了
				tempTurn = tempTurn%tempScrollList.size();
				
				SlotsTemplate tempSlotsTemplate = tempScrollList.get(tempTurn);
				
				if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BIGWHEEL){
					tempScatterInfo.getPosList().add(i*row+j);
					++tempFoundNum;
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BONUS_GAME){
					BounsGameTemplate bg = Globals.getSlotWheelService().getBouns();
					tempScatterInfo.getBounsList().add(bg.getId());
					tempScatterInfo.getBounsListPosition().add(i*row+j);
					if(bg.getType() == 1){//单线
						bounsGameGold = bounsGameGold + linebet*bg.getTimes();
					}else if(bg.getType() == 2){//总线
						bounsGameGold = bounsGameGold + linebet*tempSlotsListTemplate.getPayLinesNum()*bg.getTimes();
					}
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.WILD){
					++wildNum;
				}
			}
		}
		//wild 大于等于 3 固定条件 则去调用  TaskService 的 spinSlot 方法
		if(wildNum >= 3){
			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
		}
		
		int num = Globals.getSlotWheelService().getSlotIconNum(slot.getType());
		
	    int money2 = 0;
		
		if(num <= tempFoundNum){//触发转盘游戏
			
			//随机 [1 3)
			int type = RandomUtil.nextInt(1, 3);
			tempScatterInfo.setType(type);
			//随机2盘
			GoldBonusTemplate gb2 = Globals.getSlotWheelService().getTwoPlates(type, 2);
			tempScatterInfo.setSecondIndex(gb2.getId());
			
			//随机3盘
			GoldBonusTemplate gb3 = Globals.getSlotWheelService().getTwoPlates(type, 3);
			tempScatterInfo.setThirdIndex(gb3.getId());
			int rewardType3 = gb3.getRewardType();
			
			BigWheelTemplate bw4 = null;
			
			if(rewardType3 == 4){
				//随机大盘
				bw4 = Globals.getSlotWheelService().getBigwheel();
				tempScatterInfo.setBigWheelIndex(bw4.getId());
				
			}
			
			if(type == 1){//增加钱
				money2 = gb2.getRewardNum()*linebet;
				if(rewardType3 == 3){
					money2 = money2+gb3.getRewardNum()*linebet;
				}else if(rewardType3 == 2){
					money2 = money2*gb3.getRewardNum();
				}else if(rewardType3 == 4){
					money2 = money2+bw4.getTimes()*linebet;
				}
				
			}else{//增加自由旋转次数
				int freeNum1 = gb2.getRewardNum();
				if(rewardType3 == 3){
					freeNum1 = freeNum1+gb3.getRewardNum();
				}else if(rewardType3 == 2){
					freeNum1 = freeNum1*gb3.getRewardNum();
				}else if(rewardType3 == 4){
					money2 = money2+bw4.getTimes()*linebet;
				}
				tempHumanSlotManager.addFreeSlot(freeNum1);
			}
			
		}
		int total = bounsGameGold+money2;
		
		if(total > 0){//这里直接发钱
			//增加钱
			String detailReason = LogReasons.GoldLogReason.ROTARY_TABLE.getReasonText();
			human.giveMoney(total, Currency.GOLD, false, LogReasons.GoldLogReason.ROTARY_TABLE, detailReason, -1, 1);
			human.setModified();
		}
		return tempScatterInfo;
	}


	

}
