package com.gameserver.slot.handler.slot38;

import java.util.List;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType38BonusTrigger;
import com.gameserver.slot.msg.GCSlotType38Jackpot;
import com.gameserver.slot.msg.GCSlotType38Pumpkin;
import com.gameserver.slot.msg.GCSlotType38Wild;
import com.gameserver.slot.template.BounsHallowmasJackpotTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotJackpotNewTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.slot.template.WildSphinxTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 万圣节 老虎机
 * @author 牛鹏飞
 *
 */
public class SlotType38 extends SlotBase{

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet,
			int tempAllBets, 
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		
		
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			//如果本条线是彩金 就 不要再算赔率了
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		ScatterInfoType38 tempScatterInfo = getScatterInfo(human);
		//处理Scatter
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		//wild的玩法
		if(tempScatterInfo.getWild() > Globals.getBonusHallowmasService().getWildNum(tempSlotsListTemplate.getType())){
			WildSphinxTemplate wildSphinxTemplate= Globals.getWildSphinxService().getWildWeight(tempSlotsListTemplate.getType());
			tempReward+=bet*wildSphinxTemplate.getTimes()/100;
			GCSlotType38Wild gCSlotType38Wild = new  GCSlotType38Wild();
			gCSlotType38Wild.setMultiple(Double.valueOf(wildSphinxTemplate.getTimes()/100).intValue());
			player.sendMessage(gCSlotType38Wild);
		}
		
		
		
		//jackpot 给预奖励
		if(tempScatterInfo.getJackpotNum() > Globals.getBonusHallowmasService().getJackpotNum(tempSlotsListTemplate.getType())){
			//给第一部分奖励
			try{
				/**
				 * 这个jackpot的 玩法 稍微特殊
				 * 每次转动 奖池变化 和 slotType1(公共的) 是一样的
				 * 只是 给用户发奖励 是特殊的
				 */
				SlotJackpotNewTemplate sjt = slotService.getJackpotNewList(tempSlotsListTemplate.getType()).get(0);
				long reward1 = smh.setJackpotNew(human,slot,bet,sjt,humanJackpot,true);
				slot.setModified();
				
				//给第二部分钱
				BounsHallowmasJackpotTemplate bounsHallowmasJackpotTemplate = Globals.getBonusHallowmasService().getJackpotTemplate(tempSlotsListTemplate.getType());
				long reward2 = 0l;
				int number = bounsHallowmasJackpotTemplate.getNumber();
				int proportion = bounsHallowmasJackpotTemplate.getProportion();
				if(number != 0 || (number == 0 && proportion >0)){
					SlotJackpotNewTemplate sjt1 = new SlotJackpotNewTemplate();
					sjt1.setJackpotreward(proportion);
					reward2 = smh.setJackpotNew(human,slot,bet,sjt1,humanJackpot,true);
					slot.setModified();
				}
				GCSlotType38Jackpot gCSlotType38Jackpot = new GCSlotType38Jackpot();
				List<Integer> jackpotList =	tempScatterInfo.getJackpotList();
				int[] JackpotArr = new int[jackpotList.size()];
				for(int i=0;i<jackpotList.size();i++){
					JackpotArr[i]=jackpotList.get(i);
				}
				gCSlotType38Jackpot.setJackpotList(JackpotArr);
				gCSlotType38Jackpot.setNumber(number);
				gCSlotType38Jackpot.setReward1(reward1);
				gCSlotType38Jackpot.setReward2(reward2);
				player.sendMessage(gCSlotType38Jackpot);
				//发送jackpot
			}catch(Exception e){
				logger.error("彩金错误",e);
			}
			//1*3 的卷轴转动 ，给第二次奖励
			
		}
		
		
		
		
		//南瓜头 的玩法
		int collectPumpKinNum = Globals.getBonusHallowmasService().getCollectPumpkin(tempSlotsListTemplate.getType());
		if(tempScatterInfo.getPumpkin() > collectPumpKinNum){
			//给予奖励
			long reward = Globals.getBonusHallowmasService().getCollectReward(tempSlotsListTemplate.getType());
			long totalGold = reward*bet/100;
			GCSlotType38Pumpkin GCSlotType38Pumpkin = new GCSlotType38Pumpkin();
			GCSlotType38Pumpkin.setTotalGold(totalGold);
			slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
			player.sendMessage(GCSlotType38Pumpkin);
		}
		
		
		
		
		//bonus 是否大于 配置中的（3）个  鬼混的玩法
		int bonusNum = Globals.getBonusHallowmasService().getBonusNum(tempSlotsListTemplate.getType());
		if(tempScatterInfo.getBnus()>=bonusNum){
			GCSlotType38BonusTrigger GCSlotType38BonusTrigger = new GCSlotType38BonusTrigger();
			player.sendMessage(GCSlotType38BonusTrigger);
		}
		return tempSpecialConnectInfoData;
	}
	
	

	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType38 getScatterInfo(Human human){
		
		ScatterInfoType38 tempScatterInfo =new ScatterInfoType38();
		 List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
			// scatter 个数
			int tempFoundNum = 0;
			// bonus的 个数
			int bonusNum = 0;
			int wildNum = 0;
			int pumpkin = 0;
			int jackpotNum = 0;
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
					}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BONUS_GAME){
						tempScatterInfo.getBonusList().add(i*row+j);//bonus的位置  的位置
						++bonusNum;
					}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.WILD){
						tempScatterInfo.getWildList().add(i*row+j);
						++wildNum;
					//南瓜头的而位置 和数量
					}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SLOT38BOUNS){
						tempScatterInfo.getPumpkinList().add(i*row+j);
						++pumpkin;
					//jackpot 位置 和数量
					}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.JACKPOT){
						tempScatterInfo.getJackpotList().add(i*row+j);
						++jackpotNum;
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
			tempScatterInfo.setWild(wildNum);
			tempScatterInfo.setPumpkin(pumpkin);
			tempScatterInfo.setJackpotNum(jackpotNum);
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
