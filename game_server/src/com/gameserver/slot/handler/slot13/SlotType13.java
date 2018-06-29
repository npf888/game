package com.gameserver.slot.handler.slot13;

import java.util.ArrayList;
import java.util.List;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType13SendBouns;
import com.gameserver.slot.template.BounsZeusRewardTemplate;
import com.gameserver.slot.template.BounsZeusTemplate;
import com.gameserver.slot.template.PayTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.task.enums.RefreshType;

/**
 * 宙斯老虎机
 * @author 郭君伟
 *
 */
public class SlotType13 extends SlotBase {
	
	private List<Integer> tempSlotElementsCope;
	
	private List<List<Integer>> list2 = new ArrayList<List<Integer>>();

	@Override
	public void handleSlot(int free, int bet, int tempAllBets) {
		
		    human = player.getHuman();
		    
	        //5个卷轴每个卷轴随机步数
			List<Integer> randomIntList = smh.randomPoint(tempSlotsListTemplate,humanSlotManager,human.getLevel());
			
			//移动步数
			humanSlotManager.slot(randomIntList);
			
			//当前位置信息
			List<Integer> tempSlotElements = slotService.getSlotElementsBySlotPos(human);
			
			//检查替换后的元素列表
			tempSlotElementsCope = getReplaceSlotElements(tempSlotElements);
			
			//每一把 转出来的所有元素
			getCurElementList().addAll(tempSlotElementsCope);
			
			smh.jackpotSlot(slot,slotService,tempAllBets);
			
			//小于等于配置
			tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet,tempSlotElementsCope);
			
			//连线奖励
			SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
			
			SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
		     
			sendMessageSlot(tempSlotElements,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
			
//			Loggers.slotLogger.info(JSON.toJSONString(tempSpecialConnectInfoData));
	}
	
	/**
	 * 获取赔率
	 * @param slot
	 * @param tempCombinationList 得到的5个元素
	 * @param currBet 当前单线押注
	 * @param human
	 * @return
	 */
	public SlotConnectInfo getSlotConnectInfo(List<Integer> tempCombinationList ,int currBet,Human human){
		
		//获取当前老虎机赔率数据
		List<PayTemplate> tempPayTemplateList = slotService.getPayTemplateListBySlotType(slot.getType());
		
		SlotConnectInfo tempSlotConnectInfo = new SlotConnectInfo();
		
		
		
		for(PayTemplate tempPayTemplate : tempPayTemplateList){//循环逐个比较
			
			boolean match = true;
		
			tempSlotConnectInfo.getPosList().clear();
			
			//int jackport = 0;
			//5个位置逐个比较
			for(int i=0;i<tempPayTemplate.getCombinationList().size();i++){
				//图标ID
				int tempSlotTurn = tempPayTemplate.getCombinationList().get(i);
				if(tempSlotTurn ==0){
					continue;
				}
				//图标
				int turn  = tempCombinationList.get(i);
				
				//判断是不是wild
				SlotElementType elementType =  SlotElementType.indexOf(slotService.getTurnType(tempSlotsListTemplate.getType(),turn));
				if(
						elementType != SlotElementType.WILD1 &&
						elementType != SlotElementType.WILD2 &&
						elementType != SlotElementType.WILD3 &&
						elementType != SlotElementType.WILD4 &&
						elementType != SlotElementType.bigWILD &&
						elementType != SlotElementType.SLOTTYPE15 &&
						elementType != SlotElementType.SLOTTYPE20WILD &&
						elementType != SlotElementType.SLOTWILD26 &&
						elementType != SlotElementType.WILD27 &&
						elementType != SlotElementType.WILD && turn != tempSlotTurn){
					match=false;//这条Pay线不匹配
					break;
				}
				
				
				//用于计算jackpot
				/*if(elementType == SlotElementType.JACKPOT){
					jackport++;
				}*/
				tempSlotConnectInfo.getPosList().add(i);//位置
			}
			
			if(match){//这个图标List 完全匹配
				
				tempSlotConnectInfo.setPay(tempPayTemplate.getPay());
				tempSlotConnectInfo.setPayId(tempPayTemplate.getId());
				
				return tempSlotConnectInfo;
			}
		}
		
		return null;
	}
	
	/**
	 * 获得5个位置摇取元素 卷轴停的位置
	 * @param human
	 * @param paylinesTemplate 连线
	 * @return
	 */
	public List<Integer> getCombinationListByHumanAndPayline(Human human,PaylinesTemplate paylinesTemplate,List<Integer> tempSlotElementsCope){
		List<Integer> tempCombinationList = new ArrayList<Integer>();
		
		for(int i=0;i<paylinesTemplate.getPositionList().size();i++){//5个位置
			
			int tempIthPayPos = paylinesTemplate.getPositionList().get(i)-1;
			List<Integer> post = list2.get(i);
			//tempCombinationList.add(tempSlotElementsCope.get(tempIthPayPos));
			tempCombinationList.add(post.get(tempIthPayPos));
		}
		return tempCombinationList;
	}
	
	/**
	 * 获得列表
	 */
	public List<SlotConnectInfo> getSlotConnectInfoListByHuman(int currBet,List<Integer> tempSlotElementsCope){
		
		List<SlotConnectInfo> tempSlotConnectInfoDataList= new ArrayList<SlotConnectInfo>();
	
		//获取连线类型
		int lineType = tempSlotsListTemplate.getLineType();
		
		List<PaylinesTemplate> listLineType = slotService.getPaylinesTemplate(lineType);
			
		//获得连线的种类 20条线
		for(int i=0;i<tempSlotsListTemplate.getPayLinesNum();i++){
			
			//连线表顺序取
			PaylinesTemplate tempPaylinesTemplate = listLineType.get(i);
			
			//获得连线的元素列表（摇取得5个元素）
			List<Integer> tempCombinationList = getCombinationListByHumanAndPayline(human,tempPaylinesTemplate,tempSlotElementsCope);
			
			//获得连线的奖励信息
			SlotConnectInfo tempSlotConnectInfo = getSlotConnectInfo(tempCombinationList,currBet,human);
			
			if(tempSlotConnectInfo!=null){
				
				tempSlotConnectInfo.setPaylinesTemplate(tempPaylinesTemplate);
				
				tempSlotConnectInfoDataList.add(tempSlotConnectInfo);
			}
		}
		return tempSlotConnectInfoDataList;
	}
	
	/**
	 * 返回替换后的元素列表 bigwild
	 * @param tempSlotElements
	 * @return
	 */
	public List<Integer> getReplaceSlotElements(List<Integer> tempSlotElements){
		List<Integer> list1 = new ArrayList<Integer>();
		
        int index = 0;
		for(int i = 0;i< tempSlotsListTemplate.getColumns();i++){
			List<Integer> listsmall = new ArrayList<Integer>();
			
			 boolean fly = false;
			 for(int num = 0;num < tempSlotsListTemplate.getRows();num++){
				 int turn = tempSlotElements.get(index);
				 if(slotService.getTurnType(tempSlotsListTemplate.getType(),turn) == SlotElementType.bigWILD.getIndex()){
					 fly = true; 
				 }
				 listsmall.add(tempSlotElements.get(index));
				 index++;
			 }
			 if(fly){
				 int size = listsmall.size();
				 listsmall.clear();
				for(int n = 0;n < size;n++){
					listsmall.add(n, SlotElementType.WILD.getIndex());
				}
			 }
			 list2.add(listsmall);
		}
		
		for(List<Integer> list3 : list2){
			list1.addAll(list3);
		}
		return list1;
	}
	/*
	//去掉连线中的bigwild
	public  SlotConnectInfoData convertFromSlotConnectInfo(SlotConnectInfo slotConnectInfo){
		SlotConnectInfoData tempSlotConnectInfoData = new SlotConnectInfoData();
		tempSlotConnectInfoData.setPayLineId(slotConnectInfo.getPaylinesTemplate().getId());
		int[] arrays = ArrayUtils.intList2Array(slotConnectInfo.getPosList());
		
		List<Integer> result = new ArrayList<Integer>();
		for(int i=0;i<arrays.length;i++){
			if(slotService.getTurnType(tempSlotsListTemplate.getType(),tempSlotElementsCope.get(arrays[i])) == SlotElementType.bigWILD.getIndex()){
				continue;
			}
			result.add(arrays[i]);
		}
		int[] re = new int[result.size()];
		for(int i =0;i<result.size();i++){
			re[i]=result.get(i);
		}
		tempSlotConnectInfoData.setPosList(re);
		tempSlotConnectInfoData.setPayId(slotConnectInfo.getPayId());
		return tempSlotConnectInfoData;
		
	}*/
	

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

		ScatterInfoType13 tempScatterInfo = getScatterInfo();
		
		if (tempScatterInfo.getScatterTemplate() != null) {
			tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			tempReward += tempScatterInfo.getScatterTemplate().getPay() * tempAllBets;
			humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		//bouns 游戏
		int bouns = tempScatterInfo.getBnus();
		//是否中老虎机
		bounsGames(bouns,bet);
		
		return tempSpecialConnectInfoData;
	}
	
	
	private void bounsGames(int bouns,int bet) {
		BounsZeusTemplate zstemplate = Globals.getBounsZeusService().getBzTem(slot.getType());
		if(bouns >= zstemplate.getBonusNum()){//中了小游戏
			GCSlotType13SendBouns gCSlotType13SendBouns = new GCSlotType13SendBouns();
			long times = 0;
			List<Integer> mtType = new ArrayList<Integer>();
			List<Long> moneyOrTimes = new ArrayList<Long>();
			//可以抽奖的次数
			int selectNum = zstemplate.getSelectNum();
			times = selectNum;
			long totalMoney = 0l;
			for(int i=0;i<selectNum;i++){
				BounsZeusRewardTemplate temp = Globals.getBounsZeusService().getBzReward(slot.getType());
				int type = temp.getType();
				if(type == 1){//奖励单线押注
//					SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
					double obtainMoney = bet*temp.getTimes()/100;
					totalMoney+=obtainMoney;
			 		mtType.add(1);//1表示金币
			 		moneyOrTimes.add(Double.valueOf(obtainMoney).longValue());
//					//小游戏获得钱
				}else{//赠送免费次数
					double obtainTimes = bet*temp.getTimes()/100;
					selectNum = (int) (obtainTimes+selectNum);
					mtType.add(2);//2表示免费次数
			 		moneyOrTimes.add(Double.valueOf(obtainTimes).longValue());
				}
			}
			
			slotLog(human,slot.getTempleId(),false,true,false,Double.valueOf(totalMoney).longValue(),humanSlotManager.getCurrentBet());
			
			//list 转为数组
			int[] mtTypeInt = new int[mtType.size()];
			for(int i=0;i<mtType.size();i++){
				mtTypeInt[i]=mtType.get(i);
			}
			long[] moneyOrTimesLong = new long[moneyOrTimes.size()];
			for(int i=0;i<moneyOrTimes.size();i++){
				moneyOrTimesLong[i]=moneyOrTimes.get(i);
			}
			gCSlotType13SendBouns.setMtType(mtTypeInt);
			gCSlotType13SendBouns.setMoneyOrTimes(moneyOrTimesLong);
			gCSlotType13SendBouns.setTimes(times);
			player.sendMessage(gCSlotType13SendBouns);
		}
	}

	/*
	 * 原先多次交互的逻辑 不用了
	 * private void bounsGames(int bouns){
		
         BounsZeusTemplate zstemplate = Globals.getBounsZeusService().getBzTem(slot.getType());
		if(bouns >= zstemplate.getBonusNum()){//中了小游戏
			int bounsNum = zstemplate.getSelectNum();
			humanSlotManager.getHumanTemporaryData().setZsBouns(bounsNum);
			humanSlotManager.getHumanTemporaryData().setZsMoney(0);
			sendMessageBouns(bounsNum,0,SmallGameType13.Type2.getIndex());
		}
	}
	
	private void sendMessageBouns(int bounsNum,long money,int isSuccess){
		GCSlotType13Bouns message = new GCSlotType13Bouns();
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
	protected ScatterInfoType13 getScatterInfo(){
		
		ScatterInfoType13 tempScatterInfo =new ScatterInfoType13();
		int bnus = 0;
		//wild元素
		int wildNum = 0;
		int scatters = 0;
        for(int i = 0;i <  tempSlotElementsCope.size();i++){
             int turn = tempSlotElementsCope.get(i);
             int turnType = slotService.getTurnType(tempSlotsListTemplate.getType(),turn);
        	if(turnType == SlotElementType.BONUS13.getIndex()){
        		bnus++;
        		tempScatterInfo.getBonusList().add(i);
        	}else if(turnType == SlotElementType.SCATTER.getIndex()){
        		scatters++;
        		tempScatterInfo.getPosList().add(i);
        		//判断有几个wild 然后调用 TaskService 的 spinSlot 方法
			}else if(turnType == SlotElementType.bigWILD.getIndex()){
				++wildNum;
			}
        }
        /**
         * wild 大于等于 3 固定条件 则去调用  TaskService 的 spinSlot 方法
         * 3个wild 相当于一个 大wild 所以 要大于9
         * 
         * 还有个忍者 也是这样
         */
  		if(wildNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
  		}
  		if(bnus >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType7.getIndex());
  		}
        tempScatterInfo.setBnus(bnus);
        
		List<ScatterTemplate> list = slotService.getScatterTemplate(slot.getType());
		ScatterTemplate temp = null;
		int num = 0;
		for(ScatterTemplate tempScatterTemplate :list){
			int scatterNum = tempScatterTemplate.getScatterNum();
			if(scatters>=scatterNum && scatterNum > num){
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
