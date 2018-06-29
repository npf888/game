package com.gameserver.slot.handler.slot29;

import java.util.ArrayList;
import java.util.List;

import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.data.Bonus29Data;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType29Bouns;
import com.gameserver.slot.msg.GCSlotType29BounsInfo;
import com.gameserver.slot.msg.GCSlotType29WildInfo;
import com.gameserver.slot.pojo.HumanTemporaryData;
import com.gameserver.slot.template.BounsZeusRewardTemplate;
import com.gameserver.slot.template.BounsZeusTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 西方龙 老虎机
 * @author JavaServer
 *
 */
public class SlotType29 extends SlotBase{

	
	//
	private int rate=1;
	@Override
	public void handleSlot(int free, int bet, int tempAllBets) {
		
			
			
			  human = player.getHuman();
				 
		       //5个卷轴每个卷轴随机步数
				List<Integer> randomIntList = smh.randomPoint(tempSlotsListTemplate,humanSlotManager,human.getLevel());
				
				//移动步数
				humanSlotManager.slot(randomIntList);
				
				
				smh.jackpotSlot(slot,slotService,tempAllBets);
				
				List<Integer> tempSlotElements = slotService.getSlotElementsBySlotPos(human);
				
				//每一把 转出来的所有元素
				getCurElementList().addAll(tempSlotElements);
				//
				addReward(tempSlotElements);
				tempSlotConnectInfoList = slotService.getSlotConnectInfoListByHuman(human);
				
				//连线奖励
				SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
				        
				SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
				      
				
				//当前位置信息
				
				sendMessageSlot(tempSlotElements,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
				
			
	}
	
	
	/**
	 * 返回替换后的元素列表 bigwild
	 * @param tempSlotElements
	 * @return
	 */
	public void addReward(List<Integer> tempSlotElements){
        int ele = 0;
		for(int i = 0;i< tempSlotsListTemplate.getColumns();i++){
			if(i == 1 || i == 3){//第2列
				boolean isSame = true;
				for(int num = 0;num < tempSlotsListTemplate.getRows();num++){
					//第2列 或者  第 4列 元素 的index 
					int index = tempSlotsListTemplate.getRows()*i+num;
					//初始化 第一个元素
					if(num == 0){
						ele=tempSlotElements.get(index);
					//后边的元素 与 第一个元素 对比，不相等就是false
					}else{
						int ele2=tempSlotElements.get(index);
						if(ele != ele2){
							isSame = false;
						}
					}
				}
				if(isSame){
					rate = rate*4;
				}
			}
			
		}
		
	}
	
	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet,
			int tempAllBets, 
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet*rate;//奖励
		}
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		ScatterInfoType29 tempScatterInfo = getScatterInfo(human);
		//处理Scatter
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		//wild 是否出现
		if(tempScatterInfo.getWild() >0){
			GCSlotType29WildInfo gCSlotType29WildInfo = new GCSlotType29WildInfo();
			if(ArrayUtils.intList2Array(tempScatterInfo.getWildList()) == null){
				gCSlotType29WildInfo.setPosList(new int[0]);
			}else{
				gCSlotType29WildInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getWildList()));
			}
			gCSlotType29WildInfo.setWildNum(tempScatterInfo.getWild());
			player.sendMessage(gCSlotType29WildInfo);
		}
		//bonus 是否大于 配置中的（3）个
		BounsZeusTemplate  bounsZeusTemplate= Globals.getBounsZeusService().getBzTem(tempSlotsListTemplate.getType());
		if(tempScatterInfo.getBnus()>=bounsZeusTemplate.getBonusNum()){
			GCSlotType29BounsInfo bounsInfo = new GCSlotType29BounsInfo();
			bounsInfo.setBounsNum(tempScatterInfo.getBnus());
			bounsInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getBonusList()));
			HumanTemporaryData humanTemporaryData= humanSlotManager.getHumanTemporaryData();
			List<Integer> isSonList = new ArrayList<Integer>();
			List<Bonus29Data> bonus29Datas = new ArrayList<Bonus29Data>();
			//小游戏 有三次机会
			int totalGold = 0;
			for(int j =0;j<3;j++){
				boolean isSon = false;
				//先获取权值LIST
				List<Integer> weightList= new ArrayList<Integer>();
				List<BounsZeusRewardTemplate> bounsZeusRewardTemplateList= Globals.getBounsZeusService().getZousReward(tempSlotsListTemplate.getType());
				List<BounsZeusRewardTemplate> leftBounsZeusRewardTemplateList = new ArrayList<BounsZeusRewardTemplate>();
				
				for(BounsZeusRewardTemplate bz :bounsZeusRewardTemplateList){
					boolean exist = false;
					for(BounsZeusRewardTemplate BounsZeusRewardTemplate:humanTemporaryData.getExistBounsZeusRewardTemplateList()){
						if(bz.getId() == BounsZeusRewardTemplate.getId()){
							exist =true;
							continue;
						}
					}
					if(!exist){
						leftBounsZeusRewardTemplateList.add(bz);
					}
				}
				for(BounsZeusRewardTemplate bz :leftBounsZeusRewardTemplateList){
					weightList.add(bz.getWeight());
				}
				BounsZeusRewardTemplate  bounsZeusRewardTemplate= Globals.getBounsZeusService().getBzRewardDragon(leftBounsZeusRewardTemplateList,weightList);
				humanTemporaryData.getExistBounsZeusRewardTemplateList().add(bounsZeusRewardTemplate);
				humanTemporaryData.getExistWeightList().add(bounsZeusRewardTemplate.getWeight());
				//前段传过来的位置
				GCSlotType29Bouns gCSlotType29Bouns = new GCSlotType29Bouns();
				List<Integer> goldList = new ArrayList<Integer>();
				gCSlotType29Bouns.setIsSon(0);
				int type = bounsZeusRewardTemplate.getType();
				int id = bounsZeusRewardTemplate.getId();
				if(type == 2){//抽到龙子 
					Double timesAll = 0.0;
					for(BounsZeusRewardTemplate bt:leftBounsZeusRewardTemplateList){
						if(bt.getType() != type){
							Double dd = (bet*bt.getTimes())/100;
							goldList.add(dd.intValue());
							timesAll +=bt.getTimes();
						}
					}
					totalGold = (bet*timesAll.intValue())/100;
					gCSlotType29Bouns.setIsSon(1);
					humanTemporaryData.getExistBounsZeusRewardTemplateList().clear();//每次结束清空所有的IDS
					humanTemporaryData.setDragonTime(0);
					isSon = true;
				}else if(j==2){//玩到第三次 
					for(BounsZeusRewardTemplate bt:leftBounsZeusRewardTemplateList){
						if(bt.getId() != id && bt.getType() != 2){
							Double dd = (bet*bt.getTimes())/100;
							goldList.add(dd.intValue());
						}
					}
					Double timesAll =bounsZeusRewardTemplate.getTimes();
					totalGold = (bet*timesAll.intValue())/100;
					goldList.add(0,totalGold);
					gCSlotType29Bouns.setIsSon(0);
					humanTemporaryData.getExistBounsZeusRewardTemplateList().clear();//每次结束清空所有的IDS
					humanTemporaryData.setDragonTime(0);
				}else{
					Double timesAll = bounsZeusRewardTemplate.getTimes();
					totalGold = (bet*timesAll.intValue())/100;
					goldList.add(totalGold);
				}
				
				int[] goldArray = new int[goldList.size()];
				for(int i=0;i<goldList.size();i++){
					goldArray[i]=goldList.get(i).intValue();
				}
				Bonus29Data bonus29Data = new Bonus29Data();
				bonus29Data.setGold(goldArray);
				bonus29Datas.add(bonus29Data);
				gCSlotType29Bouns.setGold(goldArray);
				if(isSon){
					isSonList.add(1);
					break;
				}
				isSonList.add(0);
			}
			
			slotLog(human,slot.getTempleId(),false,true,false,Long.valueOf(totalGold),humanSlotManager.getCurrentBet());
			int[] sonArr = new int[isSonList.size()];
			for(int i=0;i<isSonList.size();i++){
				sonArr[i]=isSonList.get(i);
			}
			bounsInfo.setIsSon(sonArr);
			Bonus29Data[] bonus29DataArr = new Bonus29Data[bonus29Datas.size()];
			for(int i=0;i<bonus29Datas.size();i++){
				bonus29DataArr[i]=bonus29Datas.get(i);
			}
			bounsInfo.setBonus29Data(bonus29DataArr);
			player.sendMessage(bounsInfo);
		}
		return tempSpecialConnectInfoData;
	}
	

	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType29 getScatterInfo(Human human){
		
		ScatterInfoType29 tempScatterInfo =new ScatterInfoType29();
		 List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
			// scatter 个数
			int tempFoundNum = 0;
			// bonus的 个数
			int bonusNum = 0;
			// wild的 个数
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
					}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BONUS29){
						tempScatterInfo.getBonusList().add(i*row+j);//bonus的位置  的位置
						++bonusNum;
					}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.WILD27){
						tempScatterInfo.getWildList().add(i*row+j);//wild的位置  的位置
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
