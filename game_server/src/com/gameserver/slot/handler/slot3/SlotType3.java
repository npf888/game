package com.gameserver.slot.handler.slot3;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.WildSphinxService;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.enums.SlotTypeEnum;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType3BounsStart;
import com.gameserver.slot.template.BounsSphinxRewardTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 水果老虎机
 * @author JavaServer
 *
 */
public class SlotType3 extends SlotBase {

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		ScatterInfoType3 tempScatterInfo = getScatterInfo(human);

		if (tempScatterInfo.getScatterTemplate() != null) {
			tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			tempReward += tempScatterInfo.getScatterTemplate().getPay() * tempAllBets;
			humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		//如果bonus 的个数 >= 配置的数量（3） 则触发 bonus小游戏
		if(tempScatterInfo.getBnus() >= Globals.getWildSphinxService().getBonusNum(slot.getType())){
			//发消息给客户端
			sendMessageBonus(bet);
		}
		
		return tempSpecialConnectInfoData;
	}

	@Override
	protected ScatterInfoType3 getScatterInfo(Human human){
		
		ScatterInfoType3 tempScatterInfo =new ScatterInfoType3();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
		// wild 个数
		int wildNum = 0;
		//bonus个数
		int bonus = 0;
		//列数
		int col = tempSlotsListTemplate.getColumns();
		//行数
		int row = tempSlotsListTemplate.getRows();

		for(int i=0;i< col;i++){
			
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			//随机的步
			int tempIthReelPos = humanSlotManager.getCurrentSlotPosList().get(i);
			
			//ith reel from j to j+row
			for (int j = 0; j < row; j++) {

				int tempTurn = tempIthReelPos + j;

				// 翻页了（循环起来一个圆）
				tempTurn = tempTurn % tempScrollList.size();

				SlotsTemplate tempSlotsTemplate = tempScrollList.get(tempTurn);

				if (tempSlotsTemplate.getSlotElementType() == SlotElementType.SCATTER) {
					tempScatterInfo.getPosList().add(i * row + j);// SCATTER 的位置
					++tempFoundNum;
					// 判断有几个wild 然后调用 TaskService 的 spinSlot 方法
				} else if (tempSlotsTemplate.getSlotElementType() == SlotElementType.WILD) {
					++wildNum;
				} else if (tempSlotsTemplate.getType() == SlotElementType.SLOTTYPE15_BONUS.getIndex()) {
					bonus++;
				}
			}
		}
		//wild 大于等于 3 固定条件 则去调用  TaskService 的 spinSlot 方法
		if(wildNum >= 3){
			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
		}
		if(bonus >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType7.getIndex());
  		}
		//所有WILD 元素
		tempScatterInfo.setBnus(bonus);
		List<ScatterTemplate> list = slotService.getScatterTemplate(slot.getType());
		
		ScatterTemplate temp = null;
		int num = 0;
		for(ScatterTemplate tempScatterTemplate :list){
			int scatterNum = tempScatterTemplate.getScatterNum();
			if(tempFoundNum>=scatterNum && scatterNum > num){
				num = scatterNum;
				temp = tempScatterTemplate;
			}
		}
		if(temp != null){
			tempScatterInfo.setScatterTemplate(temp);
		}
		
		return tempScatterInfo;
	}
	
	private void sendMessageBonus(int bet){
		GCSlotType3BounsStart message = new GCSlotType3BounsStart();

		List<Long> curGoldList = new ArrayList<Long>();
		long totalGold = 0l;
		for(int i=0;i<5;i++){
			//获取相应奖池中的list
			//根据奖池的权值 随机取一个数据
			BounsSphinxRewardTemplate  bsr = Globals.getWildSphinxService().getRewardWeight(i+1, SlotTypeEnum.SlotType3.getIndex());
			
			if(bsr == null){
				logger.warn("玩家奖池["+(i+1)+"]为空");
				return;
			}
			//如果 type类型是1和3全部中奖 ，2为不中奖
			if(bsr.getType() == WildSphinxService.type_1 || bsr.getType() == WildSphinxService.type_3){
				//设置到返回值里
				double times = bsr.getTimes()/100;
				double gold = bet*times;
				Long reward = Double.valueOf(gold).longValue();
				totalGold+=reward;
				//增加钱
				curGoldList.add(reward);
			}else {
				curGoldList.add(0l);
				break;
			}
		}
		slotLog(human,slot.getTempleId(),false,true,false,Long.valueOf(totalGold),humanSlotManager.getCurrentBet());
		long[] curGoldArr = new long[curGoldList.size()];
		for(int i=0;i<curGoldList.size();i++){
			curGoldArr[i]=curGoldList.get(i);
		}
		message.setCurrentGold(curGoldArr);
		this.player.sendMessage(message);
		
	}
}

