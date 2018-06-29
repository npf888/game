package com.gameserver.slot.handler.slot24;

import java.util.List;

import com.gameserver.common.Globals;
import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType24BounsGameStart;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 巴西风情老虎机
 * @author 郭君伟
 *
 */
public class SlotType24 extends SlotBase {
	
	/**bonus个数 **/
	private int bonusNum = 0;

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		// 特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;

		ScatterInfo tempScatterInfo = getScatterInfo();
		
		if (tempScatterInfo.getScatterTemplate() != null) {
			tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			tempReward += tempScatterInfo.getScatterTemplate().getPay() * tempAllBets;
			humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		
		//是否中老虎机
		if(bonusNum >= Globals.getBonusBrazilService().getBounusNum(slot.getType())){//中了小游戏
			int gameType = Globals.getBonusBrazilService().getGonaBePlayedGameBySlotType(slot.getType());
			GCSlotType24BounsGameStart gCSlotType24BounsGameStart  = new GCSlotType24BounsGameStart();
			gCSlotType24BounsGameStart.setGameType(gameType);
			/***** 1：喝酒小游戏 2：桑巴小游戏****/
			if(gameType == 1){
				gCSlotType24BounsGameStart.setRound(Globals.getBonusBrazilService().getRoundBySlotType(slot.getType()));
				gCSlotType24BounsGameStart.setColor(new int[1]);//因为这个数组不能为空所以写成这样 在 gameType == 1时 没啥用
			}else if(gameType == 2){
				gCSlotType24BounsGameStart.setColor(Globals.getBonusBrazilService().getColorBySlotType(slot.getType()));
				
			}
			player.sendMessage(gCSlotType24BounsGameStart);
		}
		
		return tempSpecialConnectInfoData;
	}
	
	
/*	private void bounsGames(int bet) {
		
		
		BounsZeusTemplate zstemplate = Globals.getBounsZeusService().getBzTem(slot.getType());
		if(bonusNum >= zstemplate.getBonusNum()){//中了小游戏
			GCSlotType24SendBouns GCSlotType24SendBouns = new GCSlotType24SendBouns();
			long times = 0;
			List<Integer> mtType = new ArrayList<Integer>();
			List<Long> moneyOrTimes = new ArrayList<Long>();
			//可以抽奖的次数
			int selectNum = zstemplate.getSelectNum();
			times=selectNum;
			for(int i=0;i<selectNum;i++){
				BounsZeusRewardTemplate temp = Globals.getBounsZeusService().getBzReward(slot.getType());
				int type = temp.getType();
				if(type == 1){//奖励单线押注
					SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
					double obtainMoney = bet*temp.getTimes()/100;
					mtType.add(1);//1表示金币
					moneyOrTimes.add(Double.valueOf(obtainMoney).longValue());
//					//小游戏获得钱
					slotLog(human,slot.getTempleId(),false,true,false,Double.valueOf(obtainMoney).longValue(),humanSlotManager.getCurrentBet());
				}else{//赠送免费次数
					double obtainTimes = bet*temp.getTimes()/100;
					selectNum = (int) (obtainTimes+selectNum);
					mtType.add(2);//2表示免费次数
					moneyOrTimes.add(Double.valueOf(obtainTimes).longValue());
				}
			}
			//list 转为数组
			int[] mtTypeInt = new int[mtType.size()];
			for(int i=0;i<mtType.size();i++){
				mtTypeInt[i]=mtType.get(i);
			}
			long[] moneyOrTimesLong = new long[moneyOrTimes.size()];
			for(int i=0;i<moneyOrTimes.size();i++){
				moneyOrTimesLong[i]=moneyOrTimes.get(i);
			}
			GCSlotType24SendBouns.setMtType(mtTypeInt);
			GCSlotType24SendBouns.setMoneyOrTimes(moneyOrTimesLong);
			GCSlotType24SendBouns.setTimes(times);
			player.sendMessage(GCSlotType24SendBouns);
		}
		
	}
*/	/*private void bounsGames(){
		
        BounsZeusTemplate zstemplate = Globals.getBounsZeusService().getBzTem(slot.getType());
		if(bonusNum >= zstemplate.getBonusNum()){//中了小游戏
			int bounsNum = zstemplate.getSelectNum();
			humanSlotManager.getHumanTemporaryData().setBxBouns(bounsNum);
			humanSlotManager.getHumanTemporaryData().setBxMoney(0);
			sendMessageBouns(bounsNum,0,SmallGameType13.Type2.getIndex());
		}
	}
	
	private void sendMessageBouns(int bounsNum,long money,int isSuccess){
		GCSlotType24Bouns message = new GCSlotType24Bouns();
		message.setBounsNum(bounsNum);
		message.setMoney(money);
		message.setIsSuccess(isSuccess);
		message.setSingleBounsNum(0);
		message.setSingleMoney(0);
		player.sendMessage(message);
	}*/
	

	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfo getScatterInfo(){
		
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
				
				if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SCATTER){
					tempScatterInfo.getPosList().add(i*row+j);//SCATTER 的位置
					++tempFoundNum;
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BONUS13){
					++bonusNum;
				}else if(tempSlotsTemplate.getType() == SlotElementType.WILD.getIndex()){
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

}
