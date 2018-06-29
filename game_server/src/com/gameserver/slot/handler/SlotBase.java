package com.gameserver.slot.handler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.LogReasons.GoldLogReason;
import com.common.constants.Loggers;
import com.core.util.ArrayUtils;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanSlotManager;
import com.gameserver.lobby.msg.GCNewJackpot;
import com.gameserver.player.Player;
import com.gameserver.slot.Slot;
import com.gameserver.slot.SlotService;
import com.gameserver.slot.data.ScatterInfo;
import com.gameserver.slot.data.SlotConnectInfo;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.data.SpecialConnectInfoData;
import com.gameserver.slot.enums.SlotElementType;
import com.gameserver.slot.enums.SlotTypeEnum;
import com.gameserver.slot.handler.slot1.SlotType1;
import com.gameserver.slot.handler.slot10.SlotType10;
import com.gameserver.slot.handler.slot11.SlotType11;
import com.gameserver.slot.handler.slot12.SlotType12;
import com.gameserver.slot.handler.slot13.SlotType13;
import com.gameserver.slot.handler.slot14.SlotType14;
import com.gameserver.slot.handler.slot15.SlotType15;
import com.gameserver.slot.handler.slot16.SlotType16;
import com.gameserver.slot.handler.slot17.SlotType17;
import com.gameserver.slot.handler.slot18.SlotType18;
import com.gameserver.slot.handler.slot19.SlotType19;
import com.gameserver.slot.handler.slot20.SlotType20;
import com.gameserver.slot.handler.slot21.SlotType21;
import com.gameserver.slot.handler.slot22.SlotType22;
import com.gameserver.slot.handler.slot23.SlotType23;
import com.gameserver.slot.handler.slot24.SlotType24;
import com.gameserver.slot.handler.slot25.SlotType25;
import com.gameserver.slot.handler.slot26.SlotType26;
import com.gameserver.slot.handler.slot27.SlotType27;
import com.gameserver.slot.handler.slot28.SlotType28;
import com.gameserver.slot.handler.slot29.SlotType29;
import com.gameserver.slot.handler.slot3.SlotType3;
import com.gameserver.slot.handler.slot30.SlotType30;
import com.gameserver.slot.handler.slot31.SlotType31;
import com.gameserver.slot.handler.slot32.SlotType32;
import com.gameserver.slot.handler.slot33.SlotType33;
import com.gameserver.slot.handler.slot38.SlotType38;
import com.gameserver.slot.handler.slot8.SlotType8;
import com.gameserver.slot.msg.GCSlotSlot;
import com.gameserver.slot.pojo.PlayerSlotSenceBet;
import com.gameserver.slot.template.ScatterTemplate;
import com.gameserver.slot.template.SlotsListTemplate;
import com.gameserver.slot.template.SlotsTemplate;
import com.gameserver.task.enums.RefreshType;

public abstract class SlotBase {
	
    protected Logger logger = Loggers.slotLogger;
    
    protected static final int JACKPORT = 5;
	
    protected Player player;
	
    protected SlotsListTemplate tempSlotsListTemplate;
	
    protected HumanSlotManager humanSlotManager;
	
    protected SlotService slotService;
	
    protected Slot slot;
	
    protected SlotMessageHandler smh;
    
    /**
     * 当前老虎及转动元素 转动出来的  每一把 的 所有元素 
     */
    protected List<Integer> curElementList = new ArrayList<Integer>();
	
	/**赢得钱 **/
    protected long tempReward =0l;
	/**玩家获得总彩金 **/
    protected long humanJackpot = 0l;
    
    
    //===================
    
    protected Human human;
    
    protected List<SlotConnectInfo> tempSlotConnectInfoList;
    
    public void setTempSlotConnectInfoList(List<SlotConnectInfo> tempSlotConnectInfoList)
    {
    	this.tempSlotConnectInfoList = tempSlotConnectInfoList;
    }
    
	
	
	public List<SlotConnectInfo> getTempSlotConnectInfoList() {
		return tempSlotConnectInfoList;
	}



	public void setSmh(SlotMessageHandler smh) {
		this.smh = smh;
	}



	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setHuman(Human human) {
		this.human = human;
	}


	public void setTempSlotsListTemplate(SlotsListTemplate tempSlotsListTemplate) {
		this.tempSlotsListTemplate = tempSlotsListTemplate;
	}



	public void setHumanSlotManager(HumanSlotManager humanSlotManager) {
		this.humanSlotManager = humanSlotManager;
	}



	public void setSlotService(SlotService slotService) {
		this.slotService = slotService;
	}



	public void setSlot(Slot slot) {
		this.slot = slot;
	}


	public long getTempReward() {
		return tempReward;
	}


	public long getHumanJackpot() {
		return humanJackpot;
	}


	public List<Integer> getCurElementList() {
		return curElementList;
	}



	public void setCurElementList(List<Integer> curElementList) {
		this.curElementList = curElementList;
	}



	public  void handleSlot(int free,int bet,int tempAllBets){
		
		    human = player.getHuman();
		 
	       //5个卷轴每个卷轴随机步数
			List<Integer> randomIntList = smh.randomPoint(tempSlotsListTemplate,humanSlotManager,human.getLevel());
			
			//移动步数
			humanSlotManager.slot(randomIntList);
			
			smh.jackpotSlot(slot,slotService,tempAllBets);
			
			//小于等于20
			tempSlotConnectInfoList = slotService.getSlotConnectInfoListByHuman(human);
			
			//连线奖励
			SlotConnectInfoData[] tempSlotConnectInfoDataArr = new SlotConnectInfoData[tempSlotConnectInfoList.size()];
			        
			SpecialConnectInfoData tempSpecialConnectInfoData = specificSlot(free,bet,tempAllBets,tempSlotConnectInfoDataArr);
			      
			
			//当前位置信息
			List<Integer> tempSlotElements = slotService.getSlotElementsBySlotPos(human);
			//每一把 转出来的所有元素
			getCurElementList().addAll(tempSlotElements);
			
			sendMessageSlot(tempSlotElements,tempSlotConnectInfoDataArr,free,tempSpecialConnectInfoData);
			
			
	}
	
	protected void sendMessageSlot(List<Integer> tempSlotElements,SlotConnectInfoData[] tempSlotConnectInfoDataArr,int free,SpecialConnectInfoData tempSpecialConnectInfoData) {
		
		GCSlotSlot gcSlotSlot = new GCSlotSlot();
		gcSlotSlot.setMsgId(player.getHuman().getHumanSlotManager().getMsgCache().getNum());
		gcSlotSlot.setSlotElementList(ArrayUtils.intList2Array(tempSlotElements));
		gcSlotSlot.setSlotConnectInfoDataList(tempSlotConnectInfoDataArr);
		gcSlotSlot.setFreeTimes(humanSlotManager.getFreeTimes()-humanSlotManager.getUseTimes());
		gcSlotSlot.setRewardNum(tempReward);
		logger.info("发送的金币数量-------------------------::"+tempReward);
		gcSlotSlot.setRewardSum(humanSlotManager.getCurrentRewardNum()+tempReward);
		gcSlotSlot.setFree(free);
		
		if(tempSpecialConnectInfoData!=null){
			gcSlotSlot.setSpecialConnectInfoDataList(new SpecialConnectInfoData[]{tempSpecialConnectInfoData});
		}else{
			gcSlotSlot.setSpecialConnectInfoDataList(new SpecialConnectInfoData[]{});
		}
		player.sendMessage(gcSlotSlot);
		
		player.getHuman().getHumanSlotManager().getMsgCache().setMessage(gcSlotSlot);
		
		/**极限追求**/
		Globals.getActivityService().extremePursuitForGift(human,slot.getType(),tempSlotElements);
	}
	
	
	
	
	public abstract SpecialConnectInfoData specificSlot(int free,int bet,int tempAllBets,SlotConnectInfoData[] tempSlotConnectInfoDataArr);
	
	/**
	 * 计算SCATTER
	 * @param human
	 * @param linebet
	 * @return
	 */
	protected ScatterInfo getScatterInfo(Human human){
		
		ScatterInfo tempScatterInfo =new ScatterInfo();
		
        List<List<SlotsTemplate>> tempScrollListList = slotService.getSlotsTemplate(slot.getType(),human.getLevel());
		// scatter 个数
		int tempFoundNum = 0;
		// wild 个数
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
					//判断有几个wild 然后调用 TaskService 的 spinSlot 方法
				}else if(tempSlotsTemplate.getSlotElementType() == SlotElementType.WILD){
					++wildNum;
				}
			}
		}
		//wild 大于等于 3 固定条件 则去调用  TaskService 的 spinSlot 方法
		if(wildNum >= 3){
			Globals.getTaskNewService().spinSlot(human, slot.getType(), RefreshType.RefreshType6.getIndex());
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

	/**这个加钱的 只能被 小游戏调用**/
	public void slotLog(Human human, int slotId, boolean cost, boolean scatter, boolean get, long totalGold,Long bet) {

		String SLOT_COST = "SLOT_COST" + slotId;

		String SLOT_GET = "SLOT_GET" + slotId;

		String SLOT_SCATTER = "SLOT_SCATTER" + slotId;

		if (cost) {
			// 扣除钱
			GoldLogReason goldLogReason = LogReasons.GoldLogReason.valueOf(SLOT_COST);
			human.costMoney(totalGold, Currency.GOLD, true, goldLogReason,goldLogReason.getReasonText(), -1, 1);
		} else if (scatter) {
			/**
			 * 小游戏获得 的钱
			 * 添加到竞赛里
			 **/
			Globals.getTournamentService().putData(human.getPlayer(), slot.getType(), human.getPassportId(), totalGold);
			
			/**
			 * 世界boss
			 */
			try{
				Globals.getWorldBossNewService().attackBoss(human,totalGold,Long.valueOf(bet).intValue(), slot,null,null,null,0);
				
				/**
				 * 触发 winner触发的翻倍转盘
				 */
				HumanSlotManager humanSlotManager =  human.getHumanSlotManager();
				Slot slot = Globals.getSlotService().getSlotById(humanSlotManager.getCurrentSlotId());
				SlotsListTemplate slotsListTemplate = Globals.getTemplateService().get(slot.getTempleId(), SlotsListTemplate.class);
				long curGold = humanSlotManager.getCurrentBet()/slotsListTemplate.getPayLinesNum();
				human.getHumanSlotManager().handleWinner(totalGold, Long.valueOf(curGold).intValue(), slotsListTemplate,false,1);
			}catch(Exception e){
				logger.error("", e);
			}
			GoldLogReason goldLogReason = LogReasons.GoldLogReason.valueOf(SLOT_SCATTER);
			String detailReason = MessageFormat.format(goldLogReason.getReasonText(),slotId, bet.intValue());
			human.giveMoney(totalGold, Currency.GOLD, true, goldLogReason, detailReason,-1, 1);
		} else if (get) {
			GoldLogReason goldLogReason = LogReasons.GoldLogReason.valueOf(SLOT_GET);
			String detailReason = MessageFormat.format(goldLogReason.getReasonText(),slotId, bet);
			human.giveMoney(totalGold, Currency.GOLD, true,goldLogReason,detailReason, -1, 1);
		}
		
	}
	
	public static SlotBase getSlot(int type){
		if(SlotTypeEnum.SlotType1.getIndex() == type || 
				SlotTypeEnum.SlotType2.getIndex() == type || 
//				SlotTypeEnum.SlotType3.getIndex() == type || 
				//原先的沙滩不可以用了 改成和维密一样的了
				//SlotTypeEnum.SlotType4.getIndex() == type || 
				SlotTypeEnum.SlotType5.getIndex() == type || 
				SlotTypeEnum.SlotType6.getIndex() == type || 
				SlotTypeEnum.SlotType7.getIndex() == type || 
				SlotTypeEnum.SlotType9.getIndex() == type 
				
				){
			return new SlotType1();
		}else if(SlotTypeEnum.SlotType3.getIndex() == type){
			return new SlotType3();
		}else if(SlotTypeEnum.SlotType4.getIndex() == type){
			return new SlotType12();
		}else if(SlotTypeEnum.SlotType8.getIndex() == type){
			return new SlotType8();
		}else if(SlotTypeEnum.SlotType10.getIndex() == type){
			return new SlotType10();
		}else if(SlotTypeEnum.SlotType11.getIndex() == type){
			return new SlotType11();
		}else if(SlotTypeEnum.SlotType12.getIndex() == type){
			return new SlotType12();
		}else if(SlotTypeEnum.SlotType13.getIndex() == type){
			return new SlotType13();
		}else if(SlotTypeEnum.SlotType14.getIndex() == type){
			return new SlotType14();
		}else if(SlotTypeEnum.SlotType15.getIndex() == type){
			return new SlotType15();

		}else if(SlotTypeEnum.SlotType16.getIndex() == type){
			return new SlotType16();
		}else if(SlotTypeEnum.SlotType17.getIndex() == type){
			return new SlotType17();
		}else if(SlotTypeEnum.SlotType18.getIndex() == type){
			return new SlotType18();
		}else if(SlotTypeEnum.SlotType19.getIndex() == type){
			return new SlotType19();
		}else if(SlotTypeEnum.SlotType20.getIndex() == type){
			return new SlotType20();
		}else if(SlotTypeEnum.SlotType21.getIndex() == type){
			return new SlotType21();
		}else if(SlotTypeEnum.SlotType22.getIndex() == type){
			return new SlotType22();
		}else if(SlotTypeEnum.SlotType23.getIndex() == type){
			return new SlotType23();
		}else if(SlotTypeEnum.SlotType24.getIndex() == type){
			return new SlotType24();
		}else if(SlotTypeEnum.SlotType25.getIndex() == type){
			return new SlotType25();
		}else if(SlotTypeEnum.SlotType26.getIndex() == type){
			return new SlotType26();
		}else if(SlotTypeEnum.SlotType27.getIndex() == type){
			return new SlotType27();
		}else if(SlotTypeEnum.SlotType28.getIndex() == type){
			return new SlotType28();
		}else if(SlotTypeEnum.SlotType29.getIndex() == type){
			return new SlotType29();
		}else if(SlotTypeEnum.SlotType30.getIndex() == type){
			return new SlotType30();
		}else if(SlotTypeEnum.SlotType31.getIndex() == type){
			return new SlotType31();
		}else if(SlotTypeEnum.SlotType32.getIndex() == type){
			return new SlotType32();
		}else if(SlotTypeEnum.SlotType33.getIndex() == type){
			return new SlotType33();
		}else if(SlotTypeEnum.SlotType38.getIndex() == type){
			return new SlotType38();
		}
		return null;
	}
	
	
	
	/**
	 * 彩金 发送消息
	 */
	public void sendMessgae(Slot slot,int bet,SlotsListTemplate slotsListTemplate){
		GCNewJackpot gCNewJackpot  = new GCNewJackpot();
		if(bet ==slotsListTemplate.getBet1() ){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot1()).intValue());
			
		}else if(bet ==slotsListTemplate.getBet2()){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot2()).intValue());
			
		}else if(bet ==slotsListTemplate.getBet3()){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot3()).intValue());
			
		}else if(bet ==slotsListTemplate.getBet4()){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot4()).intValue());
			
		}else if(bet ==slotsListTemplate.getBet5()){
			gCNewJackpot.setJackpot(Long.valueOf(slot.getJackpot5()).intValue());
			
		}
		List<PlayerSlotSenceBet> playerSlotSenceBetList= Globals.getSlotService().getPlayerSlotSenceBetList();
		for(PlayerSlotSenceBet playerSlotSenceBet:playerSlotSenceBetList){
			if(slot.getTempleId() == playerSlotSenceBet.getSlotId() && playerSlotSenceBet.getBet() == bet){
				Player  player = Globals.getOnlinePlayerService().getPlayerByPassportId(Long.valueOf(playerSlotSenceBet.getPlayerId()).intValue());
				if(player != null){
					player.sendMessage(gCNewJackpot);
				}
			}
		}
	}
	
	public long getJackpot(Slot slot,int bet,SlotsListTemplate slotsListTemplate){
		if(bet ==slotsListTemplate.getBet1() ){
			return Long.valueOf(slot.getJackpot1()).intValue();
			
		}else if(bet ==slotsListTemplate.getBet2()){
			return Long.valueOf(slot.getJackpot2()).intValue();
			
		}else if(bet ==slotsListTemplate.getBet3()){
			return Long.valueOf(slot.getJackpot3()).intValue();
			
		}else if(bet ==slotsListTemplate.getBet4()){
			return Long.valueOf(slot.getJackpot4()).intValue();
			
		}else if(bet ==slotsListTemplate.getBet5()){
			return Long.valueOf(slot.getJackpot5()).intValue();
		}
		return 0;
	}
	public long getcumnJackpot(Slot slot,int bet,SlotsListTemplate slotsListTemplate){
		if(bet ==slotsListTemplate.getBet1() ){
			return Long.valueOf(slot.getCumuJackpot1()).intValue();
			
		}else if(bet ==slotsListTemplate.getBet2()){
			return Long.valueOf(slot.getCumuJackpot2()).intValue();
			
		}else if(bet ==slotsListTemplate.getBet3()){
			return Long.valueOf(slot.getCumuJackpot3()).intValue();
			
		}else if(bet ==slotsListTemplate.getBet4()){
			return Long.valueOf(slot.getCumuJackpot4()).intValue();
			
		}else if(bet ==slotsListTemplate.getBet5()){
			return Long.valueOf(slot.getCumuJackpot5()).intValue();
		}
		return 0;
	}

	/**
	 * 先 补充 两个池子
	 */
	public void supplemen(int bet,int totalBet,SlotsListTemplate slotsListTemplate){
		
		
		long jackpotAdd = totalBet*slotsListTemplate.getJackpotAddPoolPer()/10000;
		long cumuJackpotAdd = totalBet*slotsListTemplate.getJackpotPoolPer()/10000;
		
		if(bet == slotsListTemplate.getBet1()){
			long jackpot = slot.getJackpot1()+jackpotAdd;
			slot.setJackpot1(jackpot);
			long cumuJackpot =slot.getCumuJackpot1()+cumuJackpotAdd;
			slot.setCumuJackpot1(cumuJackpot);
			
		}else if(bet == slotsListTemplate.getBet2()){
			long jackpot = slot.getJackpot2()+jackpotAdd;
			slot.setJackpot2(jackpot);
			long cumuJackpot =slot.getCumuJackpot2()+cumuJackpotAdd;
			slot.setCumuJackpot2(cumuJackpot);
			
		}else if(bet == slotsListTemplate.getBet3()){
			long jackpot = slot.getJackpot3()+jackpotAdd;
			slot.setJackpot3(jackpot);
			long cumuJackpot =slot.getCumuJackpot3()+cumuJackpotAdd;
			slot.setCumuJackpot3(cumuJackpot);
			
		}else if(bet == slotsListTemplate.getBet4()){
			long jackpot = slot.getJackpot4()+jackpotAdd;
			slot.setJackpot4(jackpot);
			long cumuJackpot =slot.getCumuJackpot4()+cumuJackpotAdd;
			slot.setCumuJackpot4(cumuJackpot);
			
		}else if(bet == slotsListTemplate.getBet5()){
			long jackpot = slot.getJackpot5()+jackpotAdd;
			slot.setJackpot5(jackpot);
			long cumuJackpot =slot.getCumuJackpot5()+cumuJackpotAdd;
			slot.setCumuJackpot5(cumuJackpot);
		}
	}
	
	/**
	 * 再用累计池 补充另一个池子
	 */
	public void suppleAnotherOne(int bet,int totalBet,SlotsListTemplate slotsListTemplate,long humanJackpot){
		
		
		
		if(bet == slotsListTemplate.getBet1()){
			
				
				long jackPot = slot.getJackpot1();
				long cumeJackPot = slot.getCumuJackpot1();
				
				long jackpotOriValue = slotsListTemplate.getJackpotOriValue1();
				
				if(jackPot < jackpotOriValue){
					long make  = jackpotOriValue - jackPot;//差多少
					if(cumeJackPot < make){
						slot.setJackpot1(jackPot + cumeJackPot);
						slot.setCumuJackpot1(0l);
					}else{
						slot.setJackpot1(jackpotOriValue);
						slot.setCumuJackpot1(cumeJackPot - make);
					}
				}
			
		}else if(bet == slotsListTemplate.getBet2()){
				
				long jackPot = slot.getJackpot2();
				long cumeJackPot = slot.getCumuJackpot2();
				
				long jackpotOriValue = slotsListTemplate.getJackpotOriValue2();
				
				if(jackPot < jackpotOriValue){
					long make  = jackpotOriValue - jackPot;//差多少
					if(cumeJackPot < make){
						slot.setJackpot2(jackPot + cumeJackPot);
						slot.setCumuJackpot2(0l);
					}else{
						slot.setJackpot2(jackpotOriValue);
						slot.setCumuJackpot2(cumeJackPot - make);
					}
				}
			
		}else if(bet == slotsListTemplate.getBet3()){
				
				long jackPot = slot.getJackpot3();
				long cumeJackPot = slot.getCumuJackpot3();
				
				long jackpotOriValue = slotsListTemplate.getJackpotOriValue3();
				
				if(jackPot < jackpotOriValue){
					long make  = jackpotOriValue - jackPot;//差多少
					if(cumeJackPot < make){
						slot.setJackpot3(jackPot + cumeJackPot);
						slot.setCumuJackpot3(0l);
					}else{
						slot.setJackpot3(jackpotOriValue);
						slot.setCumuJackpot3(cumeJackPot - make);
					}
				}
				
		}else if(bet == slotsListTemplate.getBet4()){
				
				long jackPot = slot.getJackpot4();
				long cumeJackPot = slot.getCumuJackpot4();
				
				long jackpotOriValue = slotsListTemplate.getJackpotOriValue4();
				
				if(jackPot < jackpotOriValue){
					long make  = jackpotOriValue - jackPot;//差多少
					if(cumeJackPot < make){
						slot.setJackpot4(jackPot + cumeJackPot);
						slot.setCumuJackpot4(0l);
					}else{
						slot.setJackpot4(jackpotOriValue);
						slot.setCumuJackpot4(cumeJackPot - make);
					}
				}
				
		}else if(bet == slotsListTemplate.getBet5()){
				
				long jackPot = slot.getJackpot5();
				long cumeJackPot = slot.getCumuJackpot5();
				
				long jackpotOriValue = slotsListTemplate.getJackpotOriValue5();
				
				if(jackPot < jackpotOriValue){
					long make  = jackpotOriValue - jackPot;//差多少
					if(cumeJackPot < make){
						slot.setJackpot5(jackPot + cumeJackPot);
						slot.setCumuJackpot5(0l);
					}else{
						slot.setJackpot5(jackpotOriValue);
						slot.setCumuJackpot5(cumeJackPot - make);
					}
				}
		}
	}
	
	
}

