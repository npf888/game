package com.gameserver.slot.handler.slot26;

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
import com.gameserver.slot.msg.GCSlotType26BounsInfo;
import com.gameserver.slot.msg.GCSlotType26WildInfo;
import com.gameserver.slot.template.PayTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 
 * 女巫魔法老虎机
 * @author 牛鹏飞
 *
 */
public class SlotType26 extends SlotBase{


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
			
			//Loggers.slotLogger.info(JSON.toJSONString(tempSpecialConnectInfoData));
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
				 if(slotService.getTurnType(tempSlotsListTemplate.getType(),turn) == SlotElementType.SLOTWILD26.getIndex()){
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
		
		ScatterInfoType26 tempScatterInfo = getScatterInfo();
		//处理Scatter
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		//wild 是否出现
		if(tempScatterInfo.getWild() >0){
			GCSlotType26WildInfo gCSlotType26WildInfo = new GCSlotType26WildInfo();
			gCSlotType26WildInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getWildList()));
			gCSlotType26WildInfo.setWildNum(tempScatterInfo.getWild());
			gCSlotType26WildInfo.setAllPosList(ArrayUtils.intList2Array(tempSlotElementsCope));
			player.sendMessage(gCSlotType26WildInfo);
		}
		//bonus 是否大于 配置中的（3）个
		int bonusNum = Globals.getMagicSymbolService().getBonusNum(tempSlotsListTemplate.getType());
		if(tempScatterInfo.getBnus()>=bonusNum){
			GCSlotType26BounsInfo bounsInfo = new GCSlotType26BounsInfo();
			bounsInfo.setBounsNum(tempScatterInfo.getBnus());
			bounsInfo.setPosList(ArrayUtils.intList2Array(tempScatterInfo.getBonusList()));
			//小游戏
			long times  = Globals.getMagicSymbolService().getBonus(tempSlotsListTemplate.getType());
			long totalGold=(bet*times)/100;
			//小游戏获得钱
			slotLog(human,slot.getTempleId(),false,true,false,totalGold,humanSlotManager.getCurrentBet());
			bounsInfo.setTotalGold(totalGold);
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
	protected ScatterInfoType26 getScatterInfo(){
		
		ScatterInfoType26 tempScatterInfo =new ScatterInfoType26();
		//bouns 个数
		int  bounsNum = 0;
		//WILD 个数
		int  wildNum = 0;
		int scatters = 0;
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
