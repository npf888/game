package com.gameserver.slot.handler;

import java.util.ArrayList;
import java.util.List;

import com.common.constants.Loggers;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.slot.Slot;
import com.gameserver.slot.data.GcRemoveSlotSlotList;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.slot33.ScatterInfoType33;
import com.gameserver.slot.pojo.HumanTemporaryData;
import com.gameserver.slot.template.PayTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.SlotsListTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.slot.vo.BeforeFirstPosition;
import com.gameserver.slot.vo.ChoseElement;
import com.gameserver.slot.vo.Element;
import com.gameserver.slot.vo.OneTwoThreePosition;
import com.gameserver.slot.vo.SingelElementLine;
import com.gameserver.slot.vo.SupplyElement;
import com.gameserver.task.enums.RefreshType;


/*
 * 三消老虎机中的公共部分
 */
public abstract class SlotRemoveBase extends SlotBase{
	/**
	 * 被选出来的元素列表
	 */
	List<ChoseElement> choseElementList = null;
	/**
	 * 将补充  的元素
	 */
	List<SupplyElement> supplyElementList = null;
	/**
	 * 元素的 0,1,2位置 注意没有 3以上 (这个 list有 5个OneTwoThreePosition 对象，每个对象的list最多有三个值 --纵向)
	 */
	List<OneTwoThreePosition> oneTwoThreePositionList = null;
	
	/**
	 * 被选中的 线（每一条 0,1,2 的位置信息） 
	 */
	OneTwoThreePosition oneTwoThreePosition = null;
	/**
	 * 上一条
	 */
	BeforeFirstPosition beforeFirstPosition = null;
	
	/**
	 * 消除转动 总共获得的金币
	 */
	public int tempRemoveReward = 0;
	public int tempTotalRemoveReward = 0;
	
	
	
	@Override
	public void handleSlot(int free, int bet, int tempAllBets) {
			
		    human = player.getHuman();
		    /**
		     * 初始化
		     */
		    initList();
		    List<Integer> tempSlotElements = new ArrayList<Integer>();
		    /**
			 * 三消转动
			 */
			if(free == 2){
				/**
				 * 消除元素过程
				 */
				dispelElement();
			
				for(int i=0;i<choseElementList.size();i++){
					ChoseElement cEle = choseElementList.get(i);
					for(int j=0;j<cEle.getChoseElements().size();j++){
						tempSlotElements.add(cEle.getChoseElements().get(j));
					}
				}
				//每一把 转出来的所有元素
				getCurElementList().addAll(tempSlotElements);
				/**
				 * 获取连线信息
				 */
				tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet);
				
				//连线奖励
				SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
				specificRemoveSlot(tempSlotElements,free,bet,tempAllBets,tempSlotConnectInfoDataArr);
				
				
			}else{
				//初始化数据
				human.getHumanSlotManager().getHumanTemporaryData().getSlotConnectInfoDataList().clear();
				human.getHumanSlotManager().getHumanTemporaryData().getGcRemoveSlotSlotListArr().clear();
				human.getHumanSlotManager().getHumanTemporaryData().setRemoveTimes(0);
				choseElementList.clear();
				//5个卷轴每个卷轴随机步数
				List<Integer> randomIntList = smh.randomPoint(tempSlotsListTemplate,humanSlotManager,human.getLevel());
				
				//移动步数
				humanSlotManager.slot(randomIntList);
				
				/**
				 * 记录每次转动的第一行
				 */
				beforeFirstPosition.getBeforFirstPosition().clear();
				List<Integer> currentList = humanSlotManager.getCurrentSlotPosList();
				for(int i=0;i<currentList.size();i++){
					beforeFirstPosition.getBeforFirstPosition().add(Long.valueOf(currentList.get(i)-1));
				}
				/**
				 * 这组元素 是筛选出来 15个元素（3*5的老虎机）
				 * 要是 3*3 就是 9个元素
				 */
				tempSlotElements = getSlotElementsBySlotPos(human);
					
				//每一把 转出来的所有元素
				getCurElementList().addAll(tempSlotElements);
				
				/**
				 * 获取连线信息
				 */
				tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet);
				
				//连线奖励
				SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
				
				SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
				sendMessageSlot(tempSlotElements,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
			}
			
			
	}
	
	/**
	 * 三销 的特殊方法
	 * @param tempSlotElements 
	 * @param free
	 * @param bet
	 * @param tempAllBets
	 * @param tempSlotConnectInfoDataArr
	 * @return
	 */
	public abstract void specificRemoveSlot(List<Integer> tempSlotElements, int free,int bet,int tempAllBets,SlotConnectInfoData[] tempSlotConnectInfoDataArr);
	
	
	
	
	public void saveHumanMessage(List<Integer> tempSlotElements,SlotConnectInfoData[] tempSlotConnectInfoDataArr, int free) {
		human.getHumanSlotManager().getHumanTemporaryData().setTheRemoveTimes();
		int times = human.getHumanSlotManager().getHumanTemporaryData().getRemoveTimes();
		List<GcRemoveSlotSlotList> gcRemoveSlotSlotListArr = human.getHumanSlotManager().getHumanTemporaryData().getGcRemoveSlotSlotListArr();
		List<SlotConnectInfoData> SlotConnectInfoDataList = human.getHumanSlotManager().getHumanTemporaryData().getSlotConnectInfoDataList();
		List<SlotConnectInfoData> theSlotConnectInfoDataList = new ArrayList<SlotConnectInfoData>();
		int lineNum = 0;
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){
			lineNum++;
			theSlotConnectInfoDataList.add(tempSlotConnectInfoDataArr[i]);
		}
		SlotConnectInfoDataList.addAll(theSlotConnectInfoDataList);
		GcRemoveSlotSlotList  gcRemoveSlotSlotList = new GcRemoveSlotSlotList();
		gcRemoveSlotSlotList.setLineNum(lineNum);
		gcRemoveSlotSlotList.setFreeTimes(times);
		gcRemoveSlotSlotList.setSlotElementList(ArrayUtils.intList2Array(tempSlotElements));
		gcRemoveSlotSlotList.setRewardNum(tempRemoveReward);
		gcRemoveSlotSlotList.setRewardSum(tempTotalRemoveReward);
		
		logger.info("三消转动----发送的金币数量-------------------------::tempRemoveReward:"+tempRemoveReward+"--tempTotalRemoveReward"+tempTotalRemoveReward);
		gcRemoveSlotSlotListArr.add(gcRemoveSlotSlotList);
		/**
		 * 每次三消完 这个值设置为0
		 */
		tempRemoveReward=0;
		/**极限追求**/
		Globals.getActivityService().extremePursuitForGift(human,slot.getType(),tempSlotElements);
	}
	private void initList() {
		oneTwoThreePositionList = human.getHumanSlotManager().getHumanTemporaryData().getOneTwoThreePositionList();
		oneTwoThreePosition = human.getHumanSlotManager().getHumanTemporaryData().getOneTwoThreePosition();
		beforeFirstPosition = human.getHumanSlotManager().getHumanTemporaryData().getBeforeFirstPosition();
		choseElementList = human.getHumanSlotManager().getHumanTemporaryData().getChoseElementList();
		supplyElementList = human.getHumanSlotManager().getHumanTemporaryData().getSupplyElementList();
		/**
		 * 初始化被消除 元素的 集合（主要是 有几列 就弄几个集合 放进去）
		 */
		int columns = tempSlotsListTemplate.getColumns();
		/**
		 * 初始化 补充 元素的 
		 */
		if(supplyElementList.size()<=0){
			for(int i=0;i<columns;i++){
				SupplyElement supplyElement = new SupplyElement();
				supplyElementList.add(supplyElement);
			}
		}
		/**
		 * 初始化 0,1,2 位置的
		 */
		if(oneTwoThreePositionList.size()<=0){
			for(int i=0;i<columns;i++){
				OneTwoThreePosition supplyElement = new OneTwoThreePosition();
				oneTwoThreePositionList.add(supplyElement);
			}
		}
		
	}
	private List<ChoseElement> dispelElement() {
	
		/**
		 * 
		 * 主要根据 beforeFirstPosition 这一条位置 来处理 ，这个位置 一直 在消除元素的过程中变化
		 * 
		 */
		List<Element> elementList = human.getHumanSlotManager().getHumanTemporaryData().getElementList();
		
		for(int i=0;i<elementList.size();i++){
			//第 i 列
			Element element = elementList.get(i);
			List<Long> lastList = beforeFirstPosition.getBeforFirstPosition();
			//这一列 要取出多少个元素
			int size = oneTwoThreePositionList.get(i).getOneTwoThreePosition().size();
			long lastPos = beforeFirstPosition.getBeforFirstPosition().get(i);
			beforeFirstPosition.getBeforFirstPosition().set(i, lastPos-2);
			//第一个将要被消掉的元素
			for(int j=0;j<size;j++){
				//element 中的元素的位置
				long  position = lastList.get(i)-(j+1);
				position=position%element.getAllElements().size();
				if(position >=0){
					SlotsTemplate ele = element.getAllElements().get(Long.valueOf(position).intValue());
					supplyElementList.get(i).getSupplyElement().add(ele.getTurn());
				}else{
					SlotsTemplate ele = element.getAllElements().get(Long.valueOf(element.getAllElements().size()+position).intValue());
					supplyElementList.get(i).getSupplyElement().add(ele.getTurn());
				}
			}
		}
		/**
		 * 将 supplyElementList 里的元素 放到 choseElementList 这里 组成新的 15 个元素
		 */
		for(int i=0;i<choseElementList.size();i++){
			//每一列
			ChoseElement choseElement = choseElementList.get(i);
			SupplyElement supplyElement = supplyElementList.get(i);
			OneTwoThreePosition oneTwoThreePosition = oneTwoThreePositionList.get(i);
			for(int j=0;j<oneTwoThreePosition.getOneTwoThreePosition().size();j++){
				int index = oneTwoThreePosition.getOneTwoThreePosition().get(j);
				choseElement.getChoseElements().remove(index);
				choseElement.getChoseElements().add(0,supplyElement.getSupplyElement().get(j));
			}
		}
		
		/**
		 * supplyElementList 这个里的 值每次用完 要清空
		 */
		for(SupplyElement supplyElement:supplyElementList){
			supplyElement.getSupplyElement().clear();
		}
		return choseElementList;
		
		
	}
	/**
	 * 获得出现元素
	 */
	public List<Integer> getSlotElementsBySlotPos(Human human){
		
		List<Integer> results = new ArrayList<Integer>();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
	
		Slot slot =  slotService.getSlotById(tempHumanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		
		//卷轴
		List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		/**
		 * 把所有元素信息封装起来
		 * 只封装一次  再也不会变了
		 */
		HumanSlotManager humanSlotManager = human.getHumanSlotManager();
		HumanTemporaryData  humanTemporaryData = humanSlotManager.getHumanTemporaryData();
		List<Element>  elementList = humanTemporaryData.getElementList();
		if(elementList.size() <= 0){
			for(List<SlotsTemplate> stList:tempScrollListList){
				Element element = new Element();
				for(int i=0;i<stList.size();i++){
					element.getAllElements().add(stList.get(i));
				}
				//所有元素信息
				elementList.add(element);
				
			}
		}
		
		
		//行数
		int rows = tempSlotsListTemplate.getRows();
		/**
		 * 根据当前老虎机位置信息 来找到 转出来的元素
		 */
		for(int i=0;i<humanSlotManager.getCurrentSlotPosList().size();i++){
			//取出 每一列的所有 元素
			Element  elements = elementList.get(i);
			//被选中的元素
			ChoseElement choseElement = new ChoseElement();
			//当前这列 的上次老虎机 最后的 位置
			int lastTurnBegin = humanSlotManager.getCurrentSlotPosList().get(i);
			//纵向取值（在一列上 往下取值）
			for(int j=0;j<rows;j++){
				//当前的位置信息
				int nowTurnPosition = lastTurnBegin+j;
				//翻页了
				nowTurnPosition = nowTurnPosition%(elements.getAllElements().size());  
				//获取到的元素
				int turn = elements.getAllElements().get(nowTurnPosition).getTurn();
				results.add(turn);
				choseElement.getChoseElements().add(turn);
			}
			choseElementList.add(choseElement);
		}
		
		
		return results;
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
			
			//横向的五个元素
			SingelElementLine singelLine =  getCombinationListByHumanAndPayline(human,tempPaylinesTemplate);
			
			//获得连线的奖励信息
			SlotConnectInfo tempSlotConnectInfo = getSlotConnectInfo(singelLine,currBet,human);
			
			if(tempSlotConnectInfo!=null){
				
				/**
				 * 连上线的元素位置  都是将被删除的元素位置 0,1,2 的位置 和上边 横向的五个位置对应 <--> sing1Line
				 * 
				 * 在此时此刻的这个为  oneTwoThreePosition 对象 是横向 装了  5个位置值 与 oneTwoThreePositionList 每一列 一一对应
				 * 
				 * 这里判断哪些值 需要扣掉 然后 去重复存起来
				 */
				for(int j=0;j<oneTwoThreePosition.getOneTwoThreePosition().size();j++){
					if(!tempSlotConnectInfo.getPosList().contains(j)){
						continue;
					}
					int position = oneTwoThreePosition.getOneTwoThreePosition().get(j);
					if(!oneTwoThreePositionList.get(j).getOneTwoThreePosition().contains(position)){
						oneTwoThreePositionList.get(j).getOneTwoThreePosition().add(position);
					}
				}
				
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
	public SingelElementLine getCombinationListByHumanAndPayline(Human human,PaylinesTemplate paylinesTemplate){
		SingelElementLine singelLine = new SingelElementLine();
		//每次都要先清空
		oneTwoThreePosition.getOneTwoThreePosition().clear();
		for(int i=0;i<paylinesTemplate.getPositionList().size();i++){//5个位置
			//这个位置就是 0,1,2
			int tempIthPayPos = paylinesTemplate.getPositionList().get(i)-1;
			try{
				ChoseElement choseElement = choseElementList.get(i);
				oneTwoThreePosition.getOneTwoThreePosition().add(tempIthPayPos);
				singelLine.getSingleLine().add(choseElement.getChoseElements().get(tempIthPayPos));
				
			}catch(Exception e){
				Loggers.slotLogger.error("", e);
			}
		}
		return singelLine;
	}

	/**
	 * 获取赔率
	 * @param slot
	 * @param tempCombinationList 得到的5个元素
	 * @param currBet 当前单线押注
	 * @param human
	 * @return
	 */
	public SlotConnectInfo getSlotConnectInfo(SingelElementLine singelLine ,int currBet,Human human){
		
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
				int turn  = singelLine.getSingleLine().get(i);
				
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
						elementType != SlotElementType.SLOT32WILD33 &&
						elementType != SlotElementType.SLOT32WILD35 &&
						elementType != SlotElementType.WILD && turn != tempSlotTurn){
					match=false;//这条Pay线不匹配
					break;
				}
				
				tempSlotConnectInfo.getPosList().add(i);//位置 横向的 顺序 0,1,2,3,4(这个 值 一点鸟用 都没有)
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
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType33 getScatterInfo(){
		
		ScatterInfoType33 tempScatterInfo =new ScatterInfoType33();
		//bouns 个数
		int  bounsNum = 0;
		//WILD 个数
		int  wildNum = 0;
		
        for(int i = 0;i <choseElementList.size();i++){
        	//每一列
        	ChoseElement choseElement = choseElementList.get(i);
        	for(int j=0;j<choseElement.getChoseElements().size();j++){
        		int curPosition = i*choseElement.getChoseElements().size()+j;
	             int turn = choseElement.getChoseElements().get(j);
	             int turnType = slotService.getTurnType(tempSlotsListTemplate.getType(),turn);
	        	if(turnType == SlotElementType.SLOT33BOUNS.getIndex()){
	        		bounsNum++;
	        		tempScatterInfo.getBonusList().add(curPosition);
	        	}else if(turnType == SlotElementType.WILD.getIndex()){
	        		tempScatterInfo.getWildList().add(curPosition);//wild 的位置
	        		++wildNum;
				}
	        	
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
		
		
		return tempScatterInfo;
	}

}
