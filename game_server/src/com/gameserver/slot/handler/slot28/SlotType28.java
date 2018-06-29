package com.gameserver.slot.handler.slot28;

import java.util.ArrayList;
import java.util.List;

import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType28BounsInfo;
import com.gameserver.slot.msg.GCSlotType28ScatterInfo;
import com.gameserver.slot.msg.GCSlotType28WildInfo;
import com.gameserver.slot.template.BonusOceanRewardTemplate;
import com.gameserver.slot.template.BonusOceanTemplate;
import com.gameserver.slot.template.PayTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.ScatterOceanRewardTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 海洋世界 老虎机
 * @author JavaServer
 *
 */
public class SlotType28 extends SlotBase{
	
	private List<Integer> tempSlotElementsCope;
	
	private List<List<Integer>> list2 = new ArrayList<List<Integer>>();
	//中奖的倍数，如果有一个完整的美人鱼，就是2，有两个就是4
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
				if(free == 0){
					//在第2卷轴和第4卷轴出现 完整美人鱼，添加奖励
					addReward(tempSlotElements);
					tempSlotConnectInfoList = slotService.getSlotConnectInfoListByHuman(human);
					
					//每一把 转出来的所有元素
					getCurElementList().addAll(tempSlotElements);
				}else if(free == 1){
					//获取替换后的元素列表
					tempSlotElementsCope = getReplaceSlotElements(tempSlotElements);
					addReward(tempSlotElementsCope);
					//小于等于配置
					tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet,tempSlotElementsCope);
					
					//每一把 转出来的所有元素
					getCurElementList().addAll(tempSlotElementsCope);
				}
				//小于等于20
				
				//连线奖励
				SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
				        
				SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
				      
				
				//当前位置信息
				if(free == 0){
					sendMessageSlot(tempSlotElements,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
				}else if(free == 1){
					sendMessageSlot(tempSlotElementsCope,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
				}
				
			
	}
	
	/**
	 * 返回替换后的元素列表 bigwild
	 * @param tempSlotElements
	 * @return
	 */
	public List<Integer> getReplaceSlotElements(List<Integer> tempSlotElements){
		List<Integer> list1 = new ArrayList<Integer>();
		int index = 0;
		int columns = tempSlotsListTemplate.getColumns();
		int rows = tempSlotsListTemplate.getRows();
		for(int i = 0;i< columns;i++){
			List<Integer> perColumn = new ArrayList<Integer>();//某一列
			for(int num = 0;num < rows;num++){
				if(i == 1 || i == 3){
					index = i*rows+num;
					list1.add(SlotElementType.WILD.getIndex());
					perColumn.add(SlotElementType.WILD.getIndex());
				}else{
					index = i*rows+num;
					int ele = tempSlotElements.get(index);
					list1.add(ele);
					perColumn.add(ele);
					
				}
			}
			list2.add(perColumn);
		}
		return list1;
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
	 * 返回替换后的元素列表 bigwild
	 * @param tempSlotElements
	 * @return
	 */
//	public void addReward(List<Integer> tempSlotElements){
//        int index = 0;
//		for(int i = 0;i< tempSlotsListTemplate.getColumns();i++){
//			if(i == 1 || i == 3){//第2列
//				int number = 0;
//				boolean isSame = true;
//				for(int num = 0;num < tempSlotsListTemplate.getRows();num++){
//					index = num*i+i;
//					if(number == 0){
//						number = index;
//					}else if(number != index){
//						isSame = false;
//					}
//				}
//				if(isSame){
//					rate = rate*2;
//				}
//			}
//			
//		}
//		
//	}
	public void addReward(List<Integer> tempSlotElements){
        int index = 0;
        int column = tempSlotsListTemplate.getColumns();
        int rows = tempSlotsListTemplate.getRows();
		for(int i = 0;i< column;i++){
			if(i == 1 || i == 3){//第2列
				int eleFirst = 0;
				int eleNext=0;
				boolean isSame = true;
				for(int num = 0;num <rows-1;num++){
					index = rows*i+num;
					eleFirst=tempSlotElements.get(index);
					eleNext = tempSlotElements.get(index+1);
					if(eleFirst!=eleNext){
						isSame = false;
					}
				}
				if(isSame){
					rate = rate*2;
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
		
		ScatterInfoType28 tempScatterInfo = getScatterInfo(human,free);
		/**
		 * 
		 * 新增的小游戏
		 * 这里的scatter 出现三个以上 会出现乌龟赛跑的 游戏 
		 * 发送给前端的消息， 数据里 第一条数据 永远是 用户 的名次
		 * 
		 */
		if(tempScatterInfo.getSpeclScatterNum() >= Globals.getBonusOceanService().getScatterNum(tempSlotsListTemplate.getType())){
			/**
			 * 用户随机跑的名称
			 */
			List<Long> rewards = new ArrayList<Long>();
			List<Integer> ranks = new ArrayList<Integer>();
			//用户抽到的名次
			ScatterOceanRewardTemplate scatterOceanRewardTemplate = Globals.getBonusOceanService().getScatterRewardByWeight(tempSlotsListTemplate.getType());
			rewards.add(Long.valueOf(scatterOceanRewardTemplate.getRewardNum()).longValue()*bet/100);
			ranks.add(scatterOceanRewardTemplate.getRank());
			//加钱
			slotLog(human,slot.getTempleId(),false,true,false,Long.valueOf(scatterOceanRewardTemplate.getRewardNum()).longValue()*bet/100,humanSlotManager.getCurrentBet());
			/**
			 * 剩下的名次
			 */
			List<ScatterOceanRewardTemplate>  post =Globals.getBonusOceanService().getScatterRewardByWeightOther(tempSlotsListTemplate.getType(), scatterOceanRewardTemplate);
			rewards.add(Long.valueOf(post.get(0).getRewardNum()).longValue()*bet/100);
			ranks.add(post.get(0).getRank());
			
			rewards.add(Long.valueOf(post.get(1).getRewardNum()).longValue()*bet/100);
			ranks.add(post.get(1).getRank());
		
			long[] rewardsArr = new long[rewards.size()];
			for(int i=0;i<rewards.size();i++){
				rewardsArr[i]=rewards.get(i);
			}
			
			int[] ranksArr = new int[ranks.size()];
			for(int i=0;i<ranks.size();i++){
				ranksArr[i]=ranks.get(i);
			}
			
			int[] speclScatterNumArr = new int[tempScatterInfo.getSpeclScatterNumList().size()];
			for(int i=0;i<tempScatterInfo.getSpeclScatterNumList().size();i++){
				speclScatterNumArr[i]=tempScatterInfo.getSpeclScatterNumList().get(i);
			}
			
			GCSlotType28ScatterInfo gCSlotType28ScatterInfo = new GCSlotType28ScatterInfo();
			gCSlotType28ScatterInfo.setRands(ranksArr);
			gCSlotType28ScatterInfo.setRewards(rewardsArr);
			gCSlotType28ScatterInfo.setSpecilScatter(speclScatterNumArr);
			player.sendMessage(gCSlotType28ScatterInfo);
		}
		//wild 是否出现
		if(tempScatterInfo.getWild() >0){
			GCSlotType28WildInfo gCSlotType28WildInfo = new GCSlotType28WildInfo();
			if(ArrayUtils.intList2Array(tempScatterInfo.getWildList()) == null){
				gCSlotType28WildInfo.setPosList(new int[0]);
			}else{
				gCSlotType28WildInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getWildList()));
			}
			gCSlotType28WildInfo.setWildNum(tempScatterInfo.getWild());
			gCSlotType28WildInfo.setAllPosList(new int[0]);
			gCSlotType28WildInfo.setRate(rate);
			player.sendMessage(gCSlotType28WildInfo);
		}
		//bonus 是否大于 配置中的（3）个
		List<BonusOceanTemplate>  bonusOceanTemplates= Globals.getBonusOceanService().getBonusOceanTemplateMap().get(tempSlotsListTemplate.getType());
		for(BonusOceanTemplate bonusOceanTemplate:bonusOceanTemplates){
			if(tempScatterInfo.getBnus()>=bonusOceanTemplate.getBonusNum()){
				GCSlotType28BounsInfo bounsInfo = new GCSlotType28BounsInfo();
				bounsInfo.setBounsNum(tempScatterInfo.getBnus());
				bounsInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getBonusList()));
				//小游戏
				BonusOceanRewardTemplate bonusOceanRewardTemplate = Globals.getBonusOceanService().getRewardWeight(tempSlotsListTemplate.getType());
				int type = bonusOceanRewardTemplate.getType();
				long rewardNum = bonusOceanRewardTemplate.getRewardNum();
				bounsInfo.setRewardId(bonusOceanRewardTemplate.getId());
				if(type ==1 || type == 3){//直接赠送奖金
					long curGold = (bet*rewardNum)/100;
					slotLog(human,slot.getTempleId(),false,true,false,curGold,humanSlotManager.getCurrentBet());
				}else if(type == 2){//赠送转动次数
					long freeNum = rewardNum/100;
					humanSlotManager.addFreeSlot(Long.valueOf(freeNum).intValue());
				}
				player.sendMessage(bounsInfo);
				break;
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
	protected ScatterInfoType28 getScatterInfo(Human human,int free){
		
		ScatterInfoType28 tempScatterInfo =new ScatterInfoType28();
		
		if(free == 0){
			 List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
				// scatter 个数
				int tempFoundNum = 0;
				// bonus的 个数
				int bonusNum = 0;
				// wild的 个数
				int wildNum = 0;
				//特殊scatter 用于 乌龟赛跑
				int speclScatterNum = 0;
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
						}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.BONUS28){
							tempScatterInfo.getBonusList().add(i*row+j);//bonus的位置  的位置
							++bonusNum;
						}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.WILD27){
							tempScatterInfo.getWildList().add(i*row+j);//wild的位置  的位置
							++wildNum;
						}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SLOT28SPECILSCATTER){
							tempScatterInfo.getSpeclScatterNumList().add(i*row+j);//wild的位置  的位置
							++speclScatterNum;
						}
					}
				}
				tempScatterInfo.setBnus(bonusNum);
				tempScatterInfo.setWild(wildNum);
				tempScatterInfo.setSpeclScatterNum(speclScatterNum);
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
		}else{
			//bouns 个数
			int  bounsNum = 0;
			//WILD 个数
			int  wildNum = 0;
			int scatters = 0;
			
			//特殊scatter 用于 乌龟赛跑
			int speclScatterNum = 0;
	        for(int i = 0;i <  tempSlotElementsCope.size();i++){
	             int turn = tempSlotElementsCope.get(i);
	             int turnType = slotService.getTurnType(tempSlotsListTemplate.getType(),turn);
	        	if(turnType == SlotElementType.BONUS14.getIndex()){
	        		bounsNum++;
	        		tempScatterInfo.getBonusList().add(i);
	        	}else if(turnType == SlotElementType.SCATTER.getIndex()){
	        		scatters++;
	        		tempScatterInfo.getPosList().add(i);
	        	}else if(turnType == SlotElementType.SLOTWILD26.getIndex()){
					tempScatterInfo.getWildList().add(i);//wild 的位置
					++wildNum;
				}else if(turnType == SlotElementType.SLOT28SPECILSCATTER.getIndex()){
					tempScatterInfo.getSpeclScatterNumList().add(i);//wild的位置  的位置
					++speclScatterNum;
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
			tempScatterInfo.setSpeclScatterNum(speclScatterNum);
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

}
