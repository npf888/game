package com.gameserver.slot.handler.slot20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.handler.SlotBase;
import com.gameserver.slot.msg.GCSlotType20;
import com.gameserver.slot.msg.GCSlotType20BounsNew;
import com.gameserver.slot.template.BounsElephont1RewardTemplate;
import com.gameserver.slot.template.PayTemplate;
import com.gameserver.slot.template.PaylinesTemplate;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotJackpotTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;
/**
 * 
 *泰国象老虎机
 */
public class SlotType20 extends SlotBase {
	
	
	
	/**是否中了小游戏 **/
	private int bonusNum;
	/**替换后的列表 **/
    private List<Integer> tempSlotElementsCope;
	/**替换固定元素后 **/
	private List<List<Integer>> list2 = new ArrayList<List<Integer>>();
	
	@Override
	public void handleSlot(int free, int bet, int tempAllBets) {
		 int freeNum = humanSlotManager.getHumanTemporaryData().getElephantFreeNum();
		  
		   if(free == 2 && freeNum > 0){//在bigwild转动中
			   operation(freeNum);
		   }
		
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
			getCurElementList().addAll(tempSlotElements);
			
			smh.jackpotSlot(slot,slotService,tempAllBets);
			
			//小于等于配置
			tempSlotConnectInfoList = getSlotConnectInfoListByHuman(bet);
			
			//连线奖励
			SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
			
			SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
		     
			/*if(humanSlotManager.getFreeTimes() - humanSlotManager.getUseTimes() <= 0){
				sendMessageSlot(tempSlotElementsCope,tempSlotConnectInfoDataArr,0,tempSpecialConnectInfoData);
			}else{
				sendMessageSlot(tempSlotElementsCope,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
			}*/
			sendMessageSlot(tempSlotElementsCope,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
//			Loggers.slotLogger.info(JSON.toJSONString(tempSpecialConnectInfoData));
	}
	
	private void operation(int freeNum) {
        
		humanSlotManager.getHumanTemporaryData().setElephantFreeNum(freeNum - 1);

		LinkedHashMap<Integer, Integer> map = humanSlotManager.getHumanTemporaryData().getElephantMap();
		Map<Integer,Integer> remData = new HashMap<Integer,Integer>();
		
		for (Entry<Integer, Integer> en : map.entrySet()) {
			int key = en.getKey();
			int value = en.getValue() - 1;
			map.put(key, value);
			if(value <= 0){
				remData.put(key, value);
			}
		}
		for(Entry<Integer,Integer> en : remData.entrySet()){
			map.remove(en.getKey());
		}
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
				/*//获取当前老虎机ID 获取当前押注的金额   获取当前赔率ID   currBet
				int slotsId = slot.getTempleId();
				SlotJackpotTemplate sjt = this.slotService.getSlotJackpotTemplate(slotsId,currBet,tempPayTemplate.getId());
				if(sjt != null){
					tempSlotConnectInfo.setSjt(sjt);
					if(jackport == JACKPORT){
						tempSlotConnectInfo.setJackPort(true);
					}
				}*/
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
			List<Integer> post = list2.get(i);
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
	 * 返回替换后的元素列表 bigwild
	 * @param tempSlotElements
	 * @return
	 */
	public List<Integer> getReplaceSlotElements(List<Integer> tempSlotElements){
        List<Integer> list1 = new ArrayList<Integer>();
        //需要固定的列
        LinkedHashMap<Integer, Integer> map = humanSlotManager.getHumanTemporaryData().getElephantMap();
       
        int index = 0;
		for(int i = 0;i< tempSlotsListTemplate.getColumns();i++){
			List<Integer> listsmall = new ArrayList<Integer>();
			 
			 boolean fly = false;
			 if(map.containsKey(i)){//检查那一列需要替换
				 fly = true;
			 }
			 for(int num = 0;num < tempSlotsListTemplate.getRows();num++){
				  if(fly){
					  listsmall.add(humanSlotManager.getHumanTemporaryData().getElephantTurn());
				  }else{
					  listsmall.add(tempSlotElements.get(index));
				  }
				 index++;
			 }
			 list2.add(listsmall);
		}
		
		for(List<Integer> list3 : list2){
			list1.addAll(list3);
		}
		return list1;
	}

	@Override
	public SpecialConnectInfoData specificSlot(int free, int bet, int tempAllBets,
			SlotConnectInfoData[] tempSlotConnectInfoDataArr) {
		
		for(int i=0;i<tempSlotConnectInfoDataArr.length;i++){//获得奖励
			SlotConnectInfo tempSlotConnectInfo = tempSlotConnectInfoList.get(i);
			tempSlotConnectInfoDataArr[i] = SlotConnectInfoData.convertFromSlotConnectInfo(tempSlotConnectInfo);
			tempReward+=tempSlotConnectInfo.getPay()*bet;//奖励
		}
		
		//特殊奖励 Scatter玩法
		SpecialConnectInfoData tempSpecialConnectInfoData = null;
		
		ScatterInfo tempScatterInfo = getScatterInfo(human);
		
		if(tempScatterInfo.getScatterTemplate() != null){
			 tempSpecialConnectInfoData = SpecialConnectInfoData.convertFromScatterInfo(tempScatterInfo);
			 tempReward+=tempScatterInfo.getScatterTemplate().getPay()*tempAllBets;
			 humanSlotManager.addFreeSlot(tempScatterInfo.getScatterTemplate().getFreeSpinNum());
		}
		
		if(humanSlotManager.getHumanTemporaryData().isElephantIsBigWild()){
			sendMessageWild();
			int freeNum = humanSlotManager.getHumanTemporaryData().getElephantFreeNum();
			if(freeNum <= 0){
				humanSlotManager.getHumanTemporaryData().setElephantIsBigWild(false);
			}
		}
		
		//小游戏玩法---原先的小游戏注释掉，更新成新的小游戏
		int slotType = tempSlotsListTemplate.getType();
		if(Globals.getBounsElephontTemplateService().isNewBonus(slotType, bonusNum)){
			/*List<Integer> list = Globals.getBounsElephontTemplateService().getRandBox(tempSlotsListTemplate.getType());
			int num = 1;
			for(int n : list){
				num = num*(n/100);
			}
			int allBet = num*bet;
			sendMessageBouns(ArrayUtils.intList2Array(list));//小游戏
			//小游戏获得钱
	 		slotLog(human,slot.getTempleId(),false,true,false,allBet,humanSlotManager.getCurrentBet());*/
			/**
			 * 新的小游戏
			 */
			List<Long> reReward = new ArrayList<Long>();
			List<Integer> reIsPicture = new ArrayList<Integer>();
			List<Long> reSamePictureGold = new ArrayList<Long>();
			//用户 总的点击次数
			int totalTouchTimes = Globals.getBounsElephontTemplateService().getTimes(slotType);
			//相同图片的数量
			int samePictureNum = Globals.getBounsElephontTemplateService().getSameNum(slotType);
			//总共的关数
			int totalPass = Globals.getBounsElephontTemplateService().getTotalPass(slotType);
			//用户走到了第几关
			int pass = 1;
			//相同图片数量 领取奖励 要根据 配置中 的 typeNum 来定
			int  pictureNum = 0;
			// 总共得到的 金币
			long totalMoney = 0l;
			//已经选过的 元素
			List<BounsElephont1RewardTemplate> haselephantList = new ArrayList<BounsElephont1RewardTemplate>();
			//每一关都有一个  是否进入下一关
			List<Boolean> totalPassList = new ArrayList<Boolean>();
			for(int i=1;i<=totalPass;i++){
				totalPassList.add(false);
			}
			for(int i=0;i<totalTouchTimes;i++){
				
				//根据 pass 取数据，第一关取第一关的数据 ，第二关取第二关的数据  ，第三关 取第三关的数据
				BounsElephont1RewardTemplate rewardTemplate = Globals.getBounsElephontTemplateService().getNewPost(slotType, pass,haselephantList);
				haselephantList.add(rewardTemplate);
				//如果是 金币 直接给钱
				if(rewardTemplate.getType() != 1){
					totalMoney+=bet*Long.valueOf(rewardTemplate.getReward()).longValue()/100;
					reReward.add(bet*Long.valueOf(rewardTemplate.getReward()).longValue()/100);
					reIsPicture.add(0);
				//如果不是金币 那么就是图片 每一关里的图片 都 有两张相等的
				}else{
					pictureNum++;
					reReward.add(0l);
					reIsPicture.add(1);
					//到这里说明翻到了 samePictureNum 张一样的 图片 然后进入下一关
					if(pictureNum == samePictureNum){
						long reward = Globals.getBounsElephontTemplateService().getRewardByTypeNum(slotType,pass,pictureNum);
						totalMoney+=bet*reward/100;
						reSamePictureGold.add(bet*reward/100);
						totalPassList.set(pass-1,true);
						//进入下一关
						pass++;
						//置为零
						pictureNum=0;
						//清空上一关的  抽到过的元素
						haselephantList.clear();
					}
				}
			}
			//如果没有进入到下一关 结算 相同图片 获得的金币
			for(int i=0;i<totalPassList.size();i++){
				if(i < pass){
					if(!totalPassList.get(i)){
						long reward = Globals.getBounsElephontTemplateService().getRewardByTypeNum(slotType,pass,pictureNum);
						totalMoney+=bet*reward/100;
						reSamePictureGold.add(bet*reward/100);
						break;
					}
				}
			}
			
			/**
			 * 游戏结束
			 * 增加钱
			 */
			slotLog(human,slot.getTempleId(),false,true,false,totalMoney,humanSlotManager.getCurrentBet());
			
			long[] reRewardArr = new long[reReward.size()];
			for(int i=0;i<reReward.size();i++){
				reRewardArr[i]=reReward.get(i);
			}
			int[] reIsPictureArr = new int[reIsPicture.size()];
			for(int i=0;i<reIsPicture.size();i++){
				reIsPictureArr[i]=reIsPicture.get(i);
			}
			long[] reSamePictureGoldArr = new long[reSamePictureGold.size()];
			for(int i=0;i<reSamePictureGold.size();i++){
				reSamePictureGoldArr[i]=reSamePictureGold.get(i);
			}
			
			GCSlotType20BounsNew  gCSlotType20BounsNew = new GCSlotType20BounsNew();
			gCSlotType20BounsNew.setIsPicture(reIsPictureArr);
			gCSlotType20BounsNew.setReward(reRewardArr);
			gCSlotType20BounsNew.setSamePictureGold(reSamePictureGoldArr);
			gCSlotType20BounsNew.setTotalGold(totalMoney);
			player.sendMessage(gCSlotType20BounsNew);
		}
		
		return tempSpecialConnectInfoData;
	}
	
	/*private void sendMessageBouns(int[] multiples){
		GCSlotType20Bouns message = new GCSlotType20Bouns();
		message.setMultiples(multiples);
		player.sendMessage(message);
	}*/

	@Override
	protected ScatterInfo getScatterInfo(Human human) {
		
        ScatterInfo tempScatterInfo =new ScatterInfo();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
		int wildNum = 0;
		int sWild = 0;
		//列数
		int col = tempSlotsListTemplate.getColumns();
		//行数
		int row = tempSlotsListTemplate.getRows();
		
		LinkedHashMap<Integer,Integer> map = humanSlotManager.getHumanTemporaryData().getElephantMap();
		
		for(int i=0;i< col;i++){
			
			List<SlotsTemplate> tempScrollList = tempScrollListList.get(i);
			//随机的步
			int tempIthReelPos = humanSlotManager.getCurrentSlotPosList().get(i);
			
			int num = 0;
			int turn = -1;
			for(int j=0;j<row;j++){
				int tempTurn =tempIthReelPos +j;
				//翻页了（循环起来一个圆）
				tempTurn = tempTurn%tempScrollList.size();
				SlotsTemplate tempSlotsTemplate = tempScrollList.get(tempTurn);
				if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SCATTER){
					tempScatterInfo.getPosList().add(i*row+j);//SCATTER 的位置
					++tempFoundNum;
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SLOTTYPE20WILD){
					num++;
					++sWild;
					turn = tempSlotsTemplate.getTurn();
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.SLOTTYPE20WILD_BONUS){
					bonusNum++;
				}else if(tempSlotsTemplate.getType() == SlotElementType.WILD.getIndex()){
					++wildNum;
				}
			}
			if(Globals.getBounsElephontTemplateService().isBigWild(tempSlotsListTemplate.getType(), num) && !map.containsKey(i)){
				
				if(humanSlotManager.getHumanTemporaryData().getElephantTurn() == -1){
					humanSlotManager.getHumanTemporaryData().setElephantTurn(turn);
				}
			  //增加免费转动次数
			  int bigWildNum = Globals.getBounsElephontTemplateService().bigWildNum(tempSlotsListTemplate.getType());
			  int freeNum = humanSlotManager.getHumanTemporaryData().getElephantFreeNum();
			  humanSlotManager.getHumanTemporaryData().setElephantFreeNum(freeNum+bigWildNum);
			  map.put(i, bigWildNum);
			  humanSlotManager.getHumanTemporaryData().setElephantIsBigWild(true);//开始bigwild转动
			}
		}
		if((wildNum+sWild) >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
  		}
  		if(bonusNum >= 3){
  			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType7.getIndex());
  		}
		
		
		if(humanSlotManager.getHumanTemporaryData().getElephantFreeNum() <= 0){//在bigwild 转动的时候忽略 sctter
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
		}
		return tempScatterInfo;
	}
	
	private void sendMessageWild(){
		GCSlotType20 message = new GCSlotType20();
		LinkedHashMap<Integer, Integer> map = humanSlotManager.getHumanTemporaryData().getElephantMap();
		message.setColumn(ArrayUtils.intSet2Array(map.keySet()));
		message.setFreeNum(humanSlotManager.getHumanTemporaryData().getElephantFreeNum());
		player.sendMessage(message);
//	    Loggers.slotLogger.info("=============================================="+JSON.toJSONString(message));
	}
	
	

}
