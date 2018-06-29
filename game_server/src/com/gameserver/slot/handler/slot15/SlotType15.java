package com.gameserver.slot.handler.slot15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.slot.Slot;
import com.gameserver.slot.WildSphinxService;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.enums.SlotTypeEnum;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType15BounsStart;
import com.gameserver.slot.template.BounsSphinxRewardTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsListTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.slot.template.WildSphinxTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 
 * 狮身人面像老虎机
 * @author 牛鹏飞
 *
 */
public class SlotType15 extends SlotBase {
	//1组或者多组 匹配上的元素
	private List<List<SlotsTemplate>> tempCombinationLists = new ArrayList<List<SlotsTemplate>>();
	//和上班线上匹配上的元素 对应的 元素位置
	private List<List<Integer>> tempPositionLists = new ArrayList<List<Integer>>();
	//此 map 存放wild 的位置 和 对应的倍数
	private Map<Integer,Double> positionMap = new HashMap<Integer,Double>();
	
	
	private Map<Integer, List<WildSphinxTemplate>> wildSphinxMap = Globals.getWildSphinxService().getWildSphinxMap();
	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet,
			int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){
			SlotConnectInfo slotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(slotConnectInfo);
			List<SlotsTemplate> slotTemplates = tempCombinationLists.get(i);
			List<Integer> curPositionList = tempPositionLists.get(i);
			//是否存在wild元素
			boolean existWild = false;
			for(int j=0;j<slotTemplates.size();j++){
				SlotsTemplate st = slotTemplates.get(j);
				int type = st.getType();
				//判断如果存在WILD元素 则 随机乘以 相应slotNum下 wildSphinxs 的值
				if(type == SlotElementType.SLOTTYPE15.getIndex()){
					existWild = true;
					List<WildSphinxTemplate> wildSphinxs = wildSphinxMap.get(st.getSlotsNum());
					if(wildSphinxs!=null){
						Double multiple = positionMap.get(curPositionList.get(j));
						if(multiple == null){
							WildSphinxTemplate wildSphinxTemplate = Globals.getWildSphinxService().getWildWeight(st.getSlotsNum());
							double value = wildSphinxTemplate.getTimes()/100;
							positionMap.put(curPositionList.get(j), value);
							tempReward += slotConnectInfo.getPay()*bet*value;
						}else{
							tempReward += slotConnectInfo.getPay()*bet*multiple;
						}
					}
				}
			}
			
			if(!existWild){
				tempReward += slotConnectInfo.getPay()*bet;
			}
		}

		
		// 特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;

		ScatterInfoType15 tempScatterInfo = getScatterInfo(human);

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

	private void sendMessageBonus(int bet){
		GCSlotType15BounsStart message = new GCSlotType15BounsStart();

		List<Long> curGoldList = new ArrayList<Long>();
		long totalGold = 0l;
		for(int i=0;i<5;i++){
			//获取相应奖池中的list
			//根据奖池的权值 随机取一个数据
			BounsSphinxRewardTemplate  bsr = Globals.getWildSphinxService().getRewardWeight(i+1, SlotTypeEnum.SlotType15.getIndex());
			
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
	
	
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfoType15 getScatterInfo(Human human){
		
		ScatterInfoType15 tempScatterInfo =new ScatterInfoType15();
		
		int bonus = 0;
		int wildNum = 0;
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
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
				}else if(tempSlotsTemplate.getType() ==  SlotElementType.SLOTTYPE15_BONUS.getIndex()){
					bonus++;
				}else if(tempSlotsTemplate.getType() == SlotElementType.SLOTTYPE15.getIndex()){
					++wildNum;
				}
			}
		}
		if(wildNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
  		}
  		if(bonus >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType7.getIndex());
  		}
		//所有WILD 元素
		tempScatterInfo.setBnus(bonus);
		
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

	
	
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 *//*
	protected ScatterInfoType15 getScatterInfo(){
		
		ScatterInfoType15 tempScatterInfo =new ScatterInfoType15();
		int bnus = 0;
		int scatters = 0;
		if(tempCombinationLists!= null && tempCombinationLists.size() >0){
			for(int i = 0;i < tempCombinationLists.size();i++){
				List<SlotsTemplate> sts = tempCombinationLists.get(i);
				for(SlotsTemplate slotsTemplate:sts){
					if(slotsTemplate.getType() ==  SlotElementType.SLOTTYPE15_BONUS.getIndex()){
						bnus++;
						tempScatterInfo.getBonusList().add(i);
					}else if(slotsTemplate.getType() ==  SlotElementType.SCATTER.getIndex()){
						scatters++;
						tempScatterInfo.getPosList().add(i);
					}
				}
			}
			
		}
		
		tempScatterInfo.setBnus(bnus);
		
        
		List<ScatterTemplate> list = slotService.getScatterTemplate(slot.getType());
		for(ScatterTemplate tempScatterTemplate :list){
			int scatterNum = tempScatterTemplate.getScatterNum();
			if(scatters >=scatterNum){
				tempScatterInfo.setScatterTemplate(tempScatterTemplate);
				return tempScatterInfo;
			}
		}
		
		return tempScatterInfo;
	}*/
	
	
	@Override
	public  void handleSlot(int free,int bet,int tempAllBets){
		
	    human = player.getHuman();
	 
       //5个卷轴每个卷轴随机步数
		List<Integer> randomIntList = smh.randomPoint(tempSlotsListTemplate,humanSlotManager,human.getLevel());
		
		//移动步数
		humanSlotManager.slot(randomIntList);
		
		smh.jackpotSlot(slot,slotService,tempAllBets);
		
		//小于等于20
		tempSlotConnectInfoList = getSlotConnectInfoListByHuman(human);
		
		//连线奖励
		SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
		        
		SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
		      
		
		//当前位置信息
		List<Integer> tempSlotElements = slotService.getSlotElementsBySlotPos(human);
		//每一把 转出来的所有元素
		getCurElementList().addAll(tempSlotElements);
		
		sendMessageSlot(tempSlotElements,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
		
	}
	
	

	/**
	 * 获得列表
	 */
	public List<SlotConnectInfo> getSlotConnectInfoListByHuman(Human human){
		
		List<SlotConnectInfo> tempSlotConnectInfoDataList= new ArrayList<SlotConnectInfo>();
		
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
		
		Slot slot =  slotService.getSlotById(tempHumanSlotManager.getCurrentSlotId());
		
		SlotsListTemplate tempSlotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
		
		int columns = tempSlotsListTemplate.getColumns();
		
		//获取连线类型
		int lineType = tempSlotsListTemplate.getLineType();
		
		List<PaylinesTemplate> listLineType = slotService.getPaylinesTemplate(lineType);
		
		//当前单线押注额
		int currBet = (int)tempHumanSlotManager.getCurrentBet()/tempSlotsListTemplate.getPayLinesNum();
				
		//获得连线的种类 20条线
		for(int i=0;i<tempSlotsListTemplate.getPayLinesNum();i++){
			
			//连线表顺序取
			PaylinesTemplate tempPaylinesTemplate = listLineType.get(i);
			
			//获得连线的元素列表（摇取得5个元素）
			List<SlotsTemplate> tempCombinationList = getCombinationListByHumanAndPayline(human,tempPaylinesTemplate,columns);
			
			//获得连线的奖励信息
			SlotConnectInfo tempSlotConnectInfo = slotService.getSlotConnectInfo(slot,tempCombinationList,currBet,human);
			
			if(tempSlotConnectInfo!=null){
				
				tempCombinationLists.add(tempCombinationList);
				
				tempSlotConnectInfo.setPaylinesTemplate(tempPaylinesTemplate);
				
				tempSlotConnectInfoDataList.add(tempSlotConnectInfo);
			}else{
				//移除最后一个，因为 如果没有中奖，那么最后一个是新添加的
				tempPositionLists.remove(tempPositionLists.size()-1);
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
	public List<SlotsTemplate> getCombinationListByHumanAndPayline(Human human,PaylinesTemplate paylinesTemplate,int columns){
		List<SlotsTemplate> tempCombinationList = new ArrayList<SlotsTemplate>();
		List<Integer> positionList = new ArrayList<Integer>();
		HumanSlotManager tempHumanSlotManager = human.getHumanSlotManager();
		
		Slot slot =  slotService.getSlotById(tempHumanSlotManager.getCurrentSlotId());
	
		//获取这一类型老虎机的5种图形组合 (位置 图标列表)
		List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
	    //paylinesTemplate.getPositionList().size()
		for(int i=0;i<columns;i++){//5个位置
			//获取这个位置的所有元素集合
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);//
			//ith reel current pos 当前随机的步数
			int tempIthReelPos = tempHumanSlotManager.getCurrentSlotPosList().get(i);
			
			int tempIthPayPos = tempIthReelPos+paylinesTemplate.getPositionList().get(i)-1;
			
			//获得选中位置
			tempIthPayPos = tempIthPayPos%tempScrollList.size();
			/**
			 * 添加当前这条线 的元素
			 */
			tempCombinationList.add(tempScrollList.get(tempIthPayPos));
			/**
			 * 添加当前这条线 的位置
			 */
			positionList.add(i*tempScrollList.size()+tempIthPayPos+1);
		}
		tempPositionLists.add(positionList);
		return tempCombinationList;
	}
	
}



