package com.gameserver.slot.handler.slot14;

import java.util.ArrayList;
import java.util.List;

import com.core.util.ArrayUtils;
import com.core.util.RandomUtil;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.data.Bonus14Data;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType14;
import com.gameserver.slot.msg.GCSlotType14AppleBonus;
import com.gameserver.slot.msg.GCSlotType14PreyBonus;
import com.gameserver.slot.msg.GCSlotType14RuneBonus;
import com.gameserver.slot.template.BonusStoneAgeAppleTemplate;
import com.gameserver.slot.template.BonusStoneAgePreyTemplate;
import com.gameserver.slot.template.BonusStoneAgeRuneTemplate;
import com.gameserver.slot.template.PayTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.task.enums.RefreshType;

/**
 * 
 *石器时代老虎机
 */
public class SlotType14 extends SlotBase {
	
	/**替换后位置 **/
	private List<List<Integer>> replacePos = new ArrayList<List<Integer>>();
	
	private List<Integer> tempSlotElements;
	/**发给客户端替换后位置 **/
	private List<Integer> tempSlotElementsCope = new ArrayList<Integer>();;
	
	@Override
	public void handleSlot(int free, int bet, int tempAllBets) {
		
		human = player.getHuman();
		
        //5个卷轴每个卷轴随机步数
		List<Integer> randomIntList = smh.randomPoint(tempSlotsListTemplate,humanSlotManager,human.getLevel());
		
		//移动步数
		humanSlotManager.slot(randomIntList);
		
		//当前位置信息
		tempSlotElements = slotService.getSlotElementsBySlotPos(human);
		//每一把 转出来的所有元素
		getCurElementList().addAll(tempSlotElements);
		
		boolean fly = replaceCheck(tempSlotElements);
		
		smh.jackpotSlot(slot,slotService,tempAllBets);
		
		//获得连线信息
		tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet);
		
		//连线奖励
		SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
		
		SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
		
		if(fly){//有替换位置
			sendMessageCopy();
		}
		sendMessageSlot(tempSlotElements,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
	}
	
	private void sendMessageCopy(){
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i< tempSlotElementsCope.size();i++){
			int turnNew = tempSlotElementsCope.get(i);
			int turn = tempSlotElements.get(i);
			if(turn != turnNew){
				list.add(turnNew);
			}
		}
		GCSlotType14 message = new GCSlotType14();
		message.setSlotElementListCope(ArrayUtils.intList2Array(list));
		player.sendMessage(message);
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
			
			int jackport = 0;
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
				if(elementType == SlotElementType.JACKPOT){
					jackport++;
				}
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
	public List<Integer> getCombinationListByHumanAndPayline(Human human,PaylinesTemplate paylinesTemplate){
		List<Integer> tempCombinationList = new ArrayList<Integer>();
		
		for(int i=0;i<paylinesTemplate.getPositionList().size();i++){//5个位置
			
			int tempIthPayPos = paylinesTemplate.getPositionList().get(i)-1;
			
			List<Integer> post = replacePos.get(i);
			
			tempCombinationList.add(post.get(tempIthPayPos));
		}
		return tempCombinationList;
	}
	
	/**
	 * 获得列表
	 */
	public List<SlotConnectInfo> getSlotConnectInfoListByHuman(int currBet){
		
		List<SlotConnectInfo> tempSlotConnectInfoDataList= new ArrayList<SlotConnectInfo>();
	
		//获取连线类型
		int lineType = tempSlotsListTemplate.getLineType();
		
		List<PaylinesTemplate> listLineType = slotService.getPaylinesTemplate(lineType);
			
		//获得连线的种类 20条线
		for(int i=0;i<tempSlotsListTemplate.getPayLinesNum();i++){
			
			//连线表顺序取
			PaylinesTemplate tempPaylinesTemplate = listLineType.get(i);
			
			//获得连线的元素列表（摇取得5个元素）
			List<Integer> tempCombinationList = getCombinationListByHumanAndPayline(human,tempPaylinesTemplate);
			
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
	 * 检查是否有神秘元素
	 * @param tempSlotElements
	 */
	private boolean replaceCheck(List<Integer> tempSlotElements){
		boolean fly = false;
        int index = 0;
		for(int i = 0;i< tempSlotsListTemplate.getColumns();i++){
			List<Integer> listsmall = new ArrayList<Integer>();
			 for(int num = 0;num < tempSlotsListTemplate.getRows();num++){
				 int turn = tempSlotElements.get(index);
				 if(slotService.getTurnType(tempSlotsListTemplate.getType(),turn) == SlotElementType.MYSTERIOUS.getIndex()){
					int newTurn = Globals.getMagicSymbolService().getReel(tempSlotsListTemplate.getType(), i);
					 listsmall.add(newTurn);
					 fly = true;
				 }else{
					 listsmall.add(turn);
				 }
				 index++;
			 }
			 replacePos.add(listsmall);
		}
		for(List<Integer> list3 : replacePos){
			tempSlotElementsCope.addAll(list3);
		}
		return fly;
	}
	
	

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {

		for (int i = 0; i < tempSlotConnectInfoDataArr.length; i++) {// 获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward += tempSlotConnectInfo.getPay() * bet;// 奖励
		}

		// 特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;

		ScatterInfoType14 tempScatterInfo = getScatterInfo();

		if (tempScatterInfo.getScatterTemplate() != null) {
			tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			tempReward += tempScatterInfo.getScatterTemplate().getPay() * tempAllBets;
			humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		/*
		 * 老的 小游戏
		 * if(tempScatterInfo.getBnus() >= Globals.getMagicSymbolService().getBonusNum(slot.getType())){
			
			
			//中小游戏了 获取倍数
			int bonus = Globals.getMagicSymbolService().getBonus(slot.getType());
			int totalNum = bet*bonus/100;
			//小游戏获得钱

	 		slotLog(human,slot.getTempleId(),false,true,false,totalNum,humanSlotManager.getCurrentBet());

			sendMessageBonus(totalNum);
		}*/
		if(tempScatterInfo.getBnus() >= Globals.getBonusStoneAgeService().getBonusNum(slot.getType())){
			//共三个小游戏 ，随机一个小游戏
			int whichNum = RandomUtil.nextInt(1, 4);
			
			/**
			 * 第一个小游戏  采集果子 的游戏
			 */
			if(whichNum == 1){
				GCSlotType14AppleBonus gCSlotType14AppleBonus = new GCSlotType14AppleBonus();
				//初始次数
				int times = Globals.getBonusStoneAgeService().getAppleNum(slot.getType());
				int zTimes = times;
				List<Bonus14Data> Bonus14DataList = new ArrayList<Bonus14Data>();
				for(int i=0;i<zTimes;i++){
					
					BonusStoneAgeAppleTemplate bonusStoneAgeAppleTemplate = Globals.getBonusStoneAgeService().getAppleTemplate(slot.getType());
					Bonus14Data bonus14Data = new Bonus14Data();
					bonus14Data.setTimes(times);
					times--;
					int singleCollectNum = bonusStoneAgeAppleTemplate.getAppleNum();
					bonus14Data.setSingleCollectNum(singleCollectNum);
					if(i==0){
						bonus14Data.setOverlyingCollectNum(bonus14Data.getOverlyingCollectNum()+singleCollectNum);
					}else{
						bonus14Data.setOverlyingCollectNum(Bonus14DataList.get(i-1).getOverlyingCollectNum()+singleCollectNum);
					}
					
					
					long appleReward = Globals.getBonusStoneAgeService().getAppleReward(slot.getType());
					
					long singleGold = singleCollectNum*appleReward*bet/100;
					bonus14Data.setSingleGold(singleGold);
					if(i==0){
						bonus14Data.setOverlyingGold(bonus14Data.getOverlyingGold()+singleGold);
					}else{
						bonus14Data.setOverlyingGold(Bonus14DataList.get(i-1).getOverlyingGold()+singleGold);
					}
					
					
					Bonus14DataList.add(bonus14Data);
				}
				//加钱
				slotLog(human,slot.getTempleId(),false,true,false,Bonus14DataList.get(Bonus14DataList.size()-1).getOverlyingGold(),humanSlotManager.getCurrentBet());
				
				Bonus14Data[] Bonus14DataArr = new Bonus14Data[Bonus14DataList.size()];
				for(int i=0;i<Bonus14DataList.size();i++){
					Bonus14DataArr[i]=Bonus14DataList.get(i);
				}
				gCSlotType14AppleBonus.setBonus14Data(Bonus14DataArr);
				player.sendMessage(gCSlotType14AppleBonus);
				
			//翻牌的游戏
			}else if(whichNum == 2){
				List<Integer> theNums = new ArrayList<Integer>();
				GCSlotType14RuneBonus gCSlotType14RuneBonus = new GCSlotType14RuneBonus();
				int  sameNum = Globals.getBonusStoneAgeService().getRuneSameNum(slot.getType());
				//流程 每次获取的 rewardPool （也就是每次获取的那张牌）
				List<Integer> rewardPoolList = new ArrayList<Integer>();
				//已经相同的数量
				int hasSameNum=1;
				
				for(int i=0;i<6;i++){
					//随机取一张牌
					BonusStoneAgeRuneTemplate bonusStoneAgeRuneTemplate = Globals.getBonusStoneAgeService().getRuneTemplate(slot.getType());
					int rewardPool = bonusStoneAgeRuneTemplate.getRewardPool();
					rewardPoolList.add(rewardPool);
					
					if(theNums.contains(rewardPool)){
						hasSameNum++;
						//匹配上了,取出theNum对应的奖励 发个用户
						if(hasSameNum == sameNum){
							long totalGold = Long.valueOf(bonusStoneAgeRuneTemplate.getTimes()).longValue()*bet/100;
							slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
							gCSlotType14RuneBonus.setTotalGold(totalGold);
							break;
						}
					}
					//没有匹配上
					theNums.add(rewardPool);
				}
				gCSlotType14RuneBonus.setSameNum(sameNum);
				int[] rewardPoolArr = new int[rewardPoolList.size()];
				for(int i=0;i<rewardPoolList.size();i++){
					rewardPoolArr[i]=rewardPoolList.get(i);
				}
				gCSlotType14RuneBonus.setRewardPoolList(rewardPoolArr);
				player.sendMessage(gCSlotType14RuneBonus);
				
				
			/**
			 * 扑兽夹
			 */
			}else if(whichNum == 3){
				GCSlotType14PreyBonus gCSlotType14PreyBonus = new GCSlotType14PreyBonus();
				//捕兽夹 的个数
				int nums = Globals.getBonusStoneAgeService().getPreyNum(slot.getType());
				//奖励
				long preyReward = Globals.getBonusStoneAgeService().getPreyReward(slot.getType());
				BonusStoneAgePreyTemplate bonusStoneAgePreyTemplate = Globals.getBonusStoneAgeService().getPreyTemplate(slot.getType());
				//捕到多少个 猎物
				int preyNum = bonusStoneAgePreyTemplate.getPreyNum();
				long perGold = preyReward*bet/100;
				long totalGold = preyNum*preyReward*bet/100;
				slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
				
				
				
				
				gCSlotType14PreyBonus.setPerGold(perGold);
				gCSlotType14PreyBonus.setNums(nums);
				gCSlotType14PreyBonus.setPreyNum(preyNum);
				gCSlotType14PreyBonus.setTotalGold(totalGold);
				
				player.sendMessage(gCSlotType14PreyBonus);
			}
			
			
			
		}
		return tempSpecialConnectInfoData;
	}
	
	/*
	 * private void sendMessageBonus(int bonus){
		GCSlotType14Bonus message = new GCSlotType14Bonus();
		message.setBounsWeight(bonus);
		this.player.sendMessage(message);
	}*/
	
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType14 getScatterInfo(){
		
		ScatterInfoType14 tempScatterInfo =new ScatterInfoType14();
		int bnus = 0;
		int wildNum = 0;
		int scatters = 0;
        for(int i = 0;i <  tempSlotElementsCope.size();i++){
             int turn = tempSlotElementsCope.get(i);
            int turnType = slotService.getTurnType(tempSlotsListTemplate.getType(),turn);
        	if( turnType== SlotElementType.BONUS14.getIndex()){
        		bnus++;
        		tempScatterInfo.getBonusList().add(i);
        	}else if(turnType == SlotElementType.SCATTER.getIndex()){
        		scatters++;
        		tempScatterInfo.getPosList().add(i);
        	}else if(turnType == SlotElementType.WILD.getIndex()){
				++wildNum;
			}
        }
        if(wildNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
  		}
  		if(bnus >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType7.getIndex());
  		}
        tempScatterInfo.setBnus(bnus);
        
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
 