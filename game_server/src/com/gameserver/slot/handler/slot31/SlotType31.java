package com.gameserver.slot.handler.slot31;

import java.util.ArrayList;
import java.util.List;

import com.core.util.ArrayUtils;
import com.core.util.RandomUtil;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType31Bonus;
import com.gameserver.slot.msg.GCSlotType31SpecificWildInfo;
import com.gameserver.slot.msg.GCSlotType31WildInfo;
import com.gameserver.slot.template.PayTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 海盗老虎机
 * @author JavaServer
 *
 */
public class SlotType31 extends SlotBase{
	//用wild 替换 普通元素的  普通元素的 索引  
	private List<Integer> changeSlotElementsIndexList= new ArrayList<Integer>();
	
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
			
			//小于等于配置
			tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet,tempSlotElementsCope);
			
			//连线奖励
			SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
			
			SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
		     
			sendMessageSlot(tempSlotElements,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
			
			//Loggers.slotLogger.info(JSON.toJSONString(tempSpecialConnectInfoData));
	}
	/**
	 * 返回替换后的元素列表 SLOTWILD31
	 * @param tempSlotElements
	 * @return
	 */
	public List<Integer> getReplaceSlotElements(List<Integer> tempSlotElements){
		/**
		 * 这个tempElement 主要用于  将替换后的元素 放到 list2 中
		 * 而不改变  tempSlotElements 这个集合
		 */
		List<Integer> tempElement = new ArrayList<Integer>();
		tempElement.addAll(tempSlotElements);
		
		
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> existWild31List = new ArrayList<Integer>();
		for(Integer ele: tempSlotElements){
			if(slotService.getTurnType(tempSlotsListTemplate.getType(),ele.intValue()) == SlotElementType.SLOTWILD31.getIndex()){
				existWild31List.add(ele);
			}
		}
		//如果没有特殊wild 就不转换，直接 把元素封装到 list2中
		if(existWild31List == null || existWild31List.size() == 0){
			for(int i = 0;i< tempSlotsListTemplate.getColumns();i++){
				List<Integer> listsmall = new ArrayList<Integer>();
				 for(int num = i*tempSlotsListTemplate.getRows();num < (i*tempSlotsListTemplate.getRows()+tempSlotsListTemplate.getRows());num++){
					 listsmall.add(tempSlotElements.get(num));
				 }
				 list2.add(listsmall);
			}
			return tempSlotElements;
		}
		
		/**
		 * 有一个SLOTWILD31 就会随机替换三个，有两个SLOTWILD31就会随机替换 6个以此类推 
		 */
		
		//先把可替换元素全部取出来
		List<Integer> theChangeEleList = new ArrayList<Integer>();
		for(int j=0;j<tempSlotElements.size();j++){
			int ele = tempSlotElements.get(j);
			if(slotService.getTurnType(tempSlotsListTemplate.getType(),ele) != SlotElementType.SLOTWILD31.getIndex() 
				&&slotService.getTurnType(tempSlotsListTemplate.getType(),ele) != SlotElementType.SLOTBONUS31.getIndex() 
				&&slotService.getTurnType(tempSlotsListTemplate.getType(),ele) != SlotElementType.SCATTER.getIndex() 
				&&slotService.getTurnType(tempSlotsListTemplate.getType(),ele) != SlotElementType.WILD.getIndex() 
					){
				//在这里记录位置
				theChangeEleList.add(j);
			}
		}
		//每次替换三个
		int times = 3;
		if(theChangeEleList.size() < 3){
			times=theChangeEleList.size();
		}
		for(int i=0;i<existWild31List.size();i++){
			for(int j=0;j<times;j++){
				if(theChangeEleList.size()==0){
					break;
				}
				int theChangeEle1 = RandomUtil.nextInt(0, theChangeEleList.size());
				//将wild替换到list中
				int rTurn = slotService.getTurnBySlotType(slot.getType(),SlotElementType.SLOTWILD31.getIndex());
//				tempSlotElements.set(theChangeEleList.get(theChangeEle1), rTurn);
				tempElement.set(theChangeEleList.get(theChangeEle1), rTurn);
				//普通元素添加到 集合里
				changeSlotElementsIndexList.add(theChangeEleList.get(theChangeEle1));
				theChangeEleList.remove(theChangeEle1);
			}
		}
		
		for(int i = 0;i< tempSlotsListTemplate.getColumns();i++){
			List<Integer> listsmall = new ArrayList<Integer>();
			 for(int num = i*tempSlotsListTemplate.getRows();num < (i*tempSlotsListTemplate.getRows()+tempSlotsListTemplate.getRows());num++){
				 listsmall.add(tempElement.get(num));
			 }
			 list2.add(listsmall);
		}
		for(List<Integer> list3 : list2){
			list1.addAll(list3);
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
		
		
		int x = 0;
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
						elementType != SlotElementType.SLOTWILD31 &&
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

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet,
			int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		ScatterInfoType31 tempScatterInfo = getScatterInfo();
		//处理Scatter
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		//特殊 wild 是否出现
		if(tempScatterInfo.getSpecificWildNum() >0){
			GCSlotType31SpecificWildInfo gCSlotType31SpecificWildInfo = new GCSlotType31SpecificWildInfo();
			//替换前的 加上 替换后的元素
			/*tempScatterInfo.getWildList().addAll(changeSlotElementsIndexList);
			gCSlotType31SpecificWildInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getWildList()));
			gCSlotType31SpecificWildInfo.setWildNum(tempScatterInfo.getWild());*/
			gCSlotType31SpecificWildInfo.setPosList(ArrayUtils.intList2Array(changeSlotElementsIndexList));
			gCSlotType31SpecificWildInfo.setWildNum(changeSlotElementsIndexList.size());
			player.sendMessage(gCSlotType31SpecificWildInfo);
		}
		//wild 是否出现 
		if(tempScatterInfo.getWild() >0){
			GCSlotType31WildInfo gCSlotType31WildInfo = new GCSlotType31WildInfo();
			gCSlotType31WildInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getWildList()));
			gCSlotType31WildInfo.setWildNum(tempScatterInfo.getWild());
			player.sendMessage(gCSlotType31WildInfo);
		}
		
		//bonus 是否大于 配置中的（3）个
		int bonusNum = Globals.getBounsPirateService().getBonusNum(tempSlotsListTemplate.getType());
		if(tempScatterInfo.getBnus()>=bonusNum){
			GCSlotType31Bonus bounsInfo = new GCSlotType31Bonus();
			bounsInfo.setWhichNum(new int[]{1,2,3});
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
	protected ScatterInfoType31 getScatterInfo(){
		
		ScatterInfoType31 tempScatterInfo =new ScatterInfoType31();
		//bouns 个数
		int  bounsNum = 0;
		//WILD 个数
		int  wildNum = 0;
		//WILD 个数
		int  specificWildNum = 0;
		int scatters = 0;
        for(int i = 0;i <  tempSlotElementsCope.size();i++){
             int turn = tempSlotElementsCope.get(i);
             int turnType = slotService.getTurnType(tempSlotsListTemplate.getType(),turn);
        	if(turnType == SlotElementType.SLOTBONUS31.getIndex()){
        		bounsNum++;
        		tempScatterInfo.getBonusList().add(i);
        	}else if(turnType == SlotElementType.SCATTER.getIndex()){
        		scatters++;
        		tempScatterInfo.getPosList().add(i);
        	}else if(turnType == SlotElementType.SLOTWILD31.getIndex()){
				tempScatterInfo.getWildList().add(i);//wild 的位置
				++specificWildNum;
        	}else if(turnType == SlotElementType.WILD.getIndex()){
        		tempScatterInfo.getWildList().add(i);//wild 的位置
        		++wildNum;
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
		tempScatterInfo.setSpecificWildNum(specificWildNum);
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
