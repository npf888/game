package com.gameserver.slot.pojo;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.gameserver.slot.data.BounsConnectInfoData;
import com.gameserver.slot.data.GcRemoveSlotSlotList;
import com.gameserver.slot.data.SlotConnectInfoData;
import com.gameserver.slot.template.BounsZeusRewardTemplate;
import com.gameserver.slot.vo.BeforeFirstPosition;
import com.gameserver.slot.vo.ChoseElement;
import com.gameserver.slot.vo.Element;
import com.gameserver.slot.vo.OneTwoThreePosition;
import com.gameserver.slot.vo.SupplyElement;
/**
 * 
 * 老虎机临时数据，重新进入老虎机的时候重置，数据不入库
 *
 */
public class HumanTemporaryData {

		//是否 免费发送(如果为ture就是免费发送)
		private boolean megaSuperEpicWin= false;
	   // 维密老虎机 倍数 临时数据===============
		private int multiple;
		// 宙斯老虎机数据 临时数据===============
		/**小游戏抽奖次数数据 **/
		private int zsBouns;
		/**宙斯抽奖获得钱 **/
		private int zsMoney;
		// 狮身人面老虎机 临时数据 ==============
		/**总金币**/
		private long totalGold = 0l;
		/**用户必须从1 到5**/
		private int sequence = 1; 
		
		/****维密老虎机自由转动时  5 次 或者 10 或者 20次 的 综合，没进行下一次付费转动，就清空***/
		private long slot12FreeTimesWinRewards;
		
		//阿兹特克文明老虎机 临时数据
		/**卡牌数量 **/
		private int cardNumber;
		
		//狼老虎机
		/**每轮的第几次转动**/
		private int wolfTimes;
		/**bonus元素出现的次数**/
		private int wolfBonusNum;

		/**bonus元素出现的次数**/
		private BounsConnectInfoData bounsConnectInfoData;
		
		//泰国象老虎机
		/**当前免费转动次数 **/
		private int elephantFreeNum;
		/**当前固定卷轴 **/
		private LinkedHashMap<Integer,Integer> elephantMap = new LinkedHashMap<Integer,Integer>();
		/**要替换的元素 **/
		private int elephantTurn;
		/**是否bigwild true表示转动 **/
		private boolean elephantIsBigWild;
		
		
		//老虎 老虎机
		private int theTigerTimes;//第几次选牌
		private List<Integer> tigerPickUpRewards;//选中的奖池
		
		//西部牛仔老虎机
    	/**剩余次数 **/
    	private int remaining;
    	/**总收益 **/
    	private long rewardNum;
		
    	//东方龙老虎机
    	/** 5个奖池变化后的值 **/
    	private String SlotType23Jackpot;
    	private long obtainReward;//用户每次中奖获得的奖金数
    	private int ln1;//用户 增长的钱，当不够  5的是时候累计起来，到 5 在进 1，然后清空
    	private int ln2;//用户 增长的钱，当不够  5的是时候累计起来，到 5 在进 1，然后清空
    	private int ln3;//用户 增长的钱，当不够  5的是时候累计起来，到 5 在进 1，然后清空
    	private int ln4;//用户 增长的钱，当不够  5的是时候累计起来，到 5 在进 1，然后清空
    	private int ln5;//用户 增长的钱，当不够  5的是时候累计起来，到 5 在进 1，然后清空
    	
    	//巴西风情老虎机
    	
    	/**小游戏抽奖次数数据 **/
		private int bxBouns;
		/**巴西风情抽奖获得钱 **/
		private int bxMoney;
    	
		//忍者老虎机
		/** 忍者 转动后的位置 0到 4 随机生成的**/
		private int position;
		
		//西方龙 老虎机
		/** 忍者 转动后的位置 0到 4 随机生成的**/
		private int dragonTime;//第几次访问
		private List<Integer> ids;
		private List<Integer> existWeightList;
		private List<BounsZeusRewardTemplate>   existBounsZeusRewardTemplateList;

		//福尔摩斯
		/**第几次转动 到第四次就 置为1**/
		private int holmesTimes;
		private int holmestotalGolds;
		private int holmesAllWin;//是否全中，默认为1 全中，0 不全中
	
		/**
		 * 斯巴达 老虎机
		 * @return
		 */
		private List<Integer> soldierElement;
		//社交转动过程中 箭射中的wild    
		private List<Integer> shootedWildElement;
		//社交转动过程中 箭射中的wild
		private int socialityFreeNum;
		//用户个人 攻城次数
		private int selfAttackNum;
		/**是否斯巴达自由转动  true表示转动 **/
		private boolean  SpartaFreeTurn;
		/**
		 * 
		 * 
		 * 小红帽老虎机 是不是 三消转动
		 * 
		 * **/
				private boolean  disTurn;
				//当前老虎机 5个卷轴  所有元素信息 只初始化 一次 就再也不会变了
				private List<Element> elementList = null;
				/**
				 * 元素的 0,1,2位置 注意没有 3以上
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
				 * 被选出来的元素列表
				 */
				List<ChoseElement> choseElementList = null;
				/**
				 * 将补充  的元素
				 */
				List<SupplyElement> supplyElementList = null;
				
		
				/**
				 * 三消转动的次数
				 */
				private int removeTimes = 0;
				
				List<GcRemoveSlotSlotList> gcRemoveSlotSlotListArr = new ArrayList<GcRemoveSlotSlotList>();
				List<SlotConnectInfoData> slotConnectInfoDataList = new ArrayList<SlotConnectInfoData>();
		/************************************************************/
		
				
		/**
		 * 赢得 winner 的 翻倍 转盘
		 * 
		 * @return
		 */
		private boolean isWinner;
		//购买之后 才会给这么多钱
		private long winnerGold;
		
		
		
		public int getMultiple() {
			return multiple;
		}
		public void setMultiple(int multiple) {
			this.multiple = multiple;
		}
		public int getZsBouns() {
			return zsBouns;
		}
		public void setZsBouns(int zsBouns) {
			this.zsBouns = zsBouns;
		}
		public int getZsMoney() {
			return zsMoney;
		}
		public void setZsMoney(int zsMoney) {
			this.zsMoney = zsMoney;
		}
		public long getTotalGold() {
			return totalGold;
		}
		public void setTotalGold(long totalGold) {
			this.totalGold = totalGold;
		}
		public int getSequence() {
			return sequence;
		}
		public void setSequence(int sequence) {
			this.sequence = sequence;
		}
		public int getCardNumber() {
			return cardNumber;
		}
		public void setCardNumber(int cardNumber) {
			this.cardNumber = cardNumber;
		}
		public int getWolfTimes() {
			return wolfTimes;
		}
		public void setWolfTimes(int wolfTimes) {
			this.wolfTimes = wolfTimes;
		}
		public int getWolfBonusNum() {
			return wolfBonusNum;
		}
		public void setWolfBonusNum(int wolfBonusNum) {
			this.wolfBonusNum = wolfBonusNum;
		}
		
	public int getElephantFreeNum() {
			return elephantFreeNum;
		}
		public void setElephantFreeNum(int elephantFreeNum) {
			this.elephantFreeNum = elephantFreeNum;
		}
		
		
	public LinkedHashMap<Integer, Integer> getElephantMap() {
			return elephantMap;
		}
		public void setElephantMap(LinkedHashMap<Integer, Integer> elephantMap) {
			this.elephantMap = elephantMap;
		}
	public int getElephantTurn() {
			return elephantTurn;
		}
		public void setElephantTurn(int elephantTurn) {
			this.elephantTurn = elephantTurn;
		}
		
		
	public boolean isElephantIsBigWild() {
			return elephantIsBigWild;
		}
		public void setElephantIsBigWild(boolean elephantIsBigWild) {
			this.elephantIsBigWild = elephantIsBigWild;
		}
	public BounsConnectInfoData getBounsConnectInfoData() {
			return bounsConnectInfoData;
		}
		public void setBounsConnectInfoData(BounsConnectInfoData bounsConnectInfoData) {
			this.bounsConnectInfoData = bounsConnectInfoData;
		}
		
		
		
	public int getTheTigerTimes() {
			return theTigerTimes;
		}
		public void setTheTigerTimes(int theTigerTimes) {
			this.theTigerTimes = theTigerTimes;
		}
		
	public List<Integer> getTigerPickUpRewards() {
			return tigerPickUpRewards;
		}
		public void setTigerPickUpRewards(List<Integer> tigerPickUpRewards) {
			this.tigerPickUpRewards = tigerPickUpRewards;
		}
		
		
	public int getRemoveTimes() {
			return removeTimes;
		}
		public void setRemoveTimes(int removeTimes) {
			this.removeTimes = removeTimes;
		}
		/**
		 * 加一
		 */
		public void setTheRemoveTimes(){
			this.removeTimes++;
		}
	public int getRemaining() {
			return remaining;
		}
		public void setRemaining(int remaining) {
			this.remaining = remaining;
		}
		public long getRewardNum() {
			return rewardNum;
		}
		public void setRewardNum(long rewardNum) {
			this.rewardNum = rewardNum;
		}
		
	public String getSlotType23Jackpot() {
			return SlotType23Jackpot;
		}
		public void setSlotType23Jackpot(String slotType23Jackpot) {
			SlotType23Jackpot = slotType23Jackpot;
		}
		
		
	public List<SlotConnectInfoData> getSlotConnectInfoDataList() {
			return slotConnectInfoDataList;
		}
		public void setSlotConnectInfoDataList(
				List<SlotConnectInfoData> slotConnectInfoDataList) {
			this.slotConnectInfoDataList = slotConnectInfoDataList;
		}
	public int getBxBouns() {
			return bxBouns;
		}
		public void setBxBouns(int bxBouns) {
			this.bxBouns = bxBouns;
		}
		public int getBxMoney() {
			return bxMoney;
		}
		public void setBxMoney(int bxMoney) {
			this.bxMoney = bxMoney;
		}
		
		
	public int getPosition() {
			return position;
		}
		public void setPosition(int position) {
			this.position = position;
		}
		
		
	public long getObtainReward() {
			return obtainReward;
		}
		public void setObtainReward(long obtainReward) {
			this.obtainReward = obtainReward;
		}
		
		
	public List<Integer> getIds() {
			return ids;
		}
		public void setIds(List<Integer> ids) {
			this.ids = ids;
		}
		
		
	public int getHolmesTimes() {
			return holmesTimes;
		}
		public void setHolmesTimes(int holmesTimes) {
			this.holmesTimes = holmesTimes;
		}
		
	public int getHolmestotalGolds() {
			return holmestotalGolds;
		}
		public void setHolmestotalGolds(int holmestotalGolds) {
			this.holmestotalGolds = holmestotalGolds;
		}
		
	public int getHolmesAllWin() {
			return holmesAllWin;
		}
		public void setHolmesAllWin(int holmesAllWin) {
			this.holmesAllWin = holmesAllWin;
		}
		
		
		
		
	public long getSlot12FreeTimesWinRewards() {
			return slot12FreeTimesWinRewards;
		}
		public void setSlot12FreeTimesWinRewards(long slot12FreeTimesWinRewards) {
			this.slot12FreeTimesWinRewards = slot12FreeTimesWinRewards;
		}
	public int getDragonTime() {
			return dragonTime;
		}
		public void setDragonTime(int dragonTime) {
			this.dragonTime = dragonTime;
		}
	public List<Integer> getExistWeightList() {
			return existWeightList;
		}
		public void setExistWeightList(List<Integer> existWeightList) {
			this.existWeightList = existWeightList;
		}
		public List<BounsZeusRewardTemplate> getExistBounsZeusRewardTemplateList() {
			return existBounsZeusRewardTemplateList;
		}
		public void setExistBounsZeusRewardTemplateList(
				List<BounsZeusRewardTemplate> existBounsZeusRewardTemplateList) {
			this.existBounsZeusRewardTemplateList = existBounsZeusRewardTemplateList;
		}
		
		public List<Integer> getSoldierElement() {
			return soldierElement;
		}
		public void setSoldierElement(List<Integer> soldierElement) {
			this.soldierElement = soldierElement;
		}
		
		
		public List<Integer> getShootedWildElement() {
			return shootedWildElement;
		}
		public void setShootedWildElement(List<Integer> shootedWildElement) {
			this.shootedWildElement = shootedWildElement;
		}
		public int getSocialityFreeNum() {
			return socialityFreeNum;
		}
		public void setSocialityFreeNum(int socialityFreeNum) {
			this.socialityFreeNum = socialityFreeNum;
		}
		
		
		public int getSelfAttackNum() {
			return selfAttackNum;
		}
		public void setSelfAttackNum(int selfAttackNum) {
			this.selfAttackNum = selfAttackNum;
		}
		
		
		
		

	public boolean isMegaSuperEpicWin() {
			return megaSuperEpicWin;
		}
		public void setMegaSuperEpicWin(boolean megaSuperEpicWin) {
			this.megaSuperEpicWin = megaSuperEpicWin;
		}
	public List<ChoseElement> getChoseElementList() {
			return choseElementList;
		}
		public void setChoseElementList(List<ChoseElement> choseElementList) {
			this.choseElementList = choseElementList;
		}
		public List<SupplyElement> getSupplyElementList() {
			return supplyElementList;
		}
		public void setSupplyElementList(List<SupplyElement> supplyElementList) {
			this.supplyElementList = supplyElementList;
		}
	public OneTwoThreePosition getOneTwoThreePosition() {
			return oneTwoThreePosition;
		}
		public void setOneTwoThreePosition(OneTwoThreePosition oneTwoThreePosition) {
			this.oneTwoThreePosition = oneTwoThreePosition;
		}
		public BeforeFirstPosition getBeforeFirstPosition() {
			return beforeFirstPosition;
		}
		public void setBeforeFirstPosition(BeforeFirstPosition beforeFirstPosition) {
			this.beforeFirstPosition = beforeFirstPosition;
		}
	public List<OneTwoThreePosition> getOneTwoThreePositionList() {
			return oneTwoThreePositionList;
		}
		public void setOneTwoThreePositionList(
				List<OneTwoThreePosition> oneTwoThreePositionList) {
			this.oneTwoThreePositionList = oneTwoThreePositionList;
		}
	public List<Element> getElementList() {
			return elementList;
		}
		public void setElementList(List<Element> elementList) {
			this.elementList = elementList;
		}
	public boolean isDisTurn() {
			return disTurn;
		}
		public void setDisTurn(boolean disTurn) {
			this.disTurn = disTurn;
		}
	public boolean isSpartaFreeTurn() {
			return SpartaFreeTurn;
		}
		public void setSpartaFreeTurn(boolean spartaFreeTurn) {
			SpartaFreeTurn = spartaFreeTurn;
		}
		
		
		
	public boolean isWinner() {
			return isWinner;
		}
		public void setWinner(boolean isWinner) {
			this.isWinner = isWinner;
		}
		public long getWinnerGold() {
			return winnerGold;
		}
		public void setWinnerGold(long winnerGold) {
			this.winnerGold = winnerGold;
		}
	public List<GcRemoveSlotSlotList> getGcRemoveSlotSlotListArr() {
			return gcRemoveSlotSlotListArr;
		}
		public void setGcRemoveSlotSlotListArr(
				List<GcRemoveSlotSlotList> gcRemoveSlotSlotListArr) {
			this.gcRemoveSlotSlotListArr = gcRemoveSlotSlotListArr;
		}
	public int getLn1() {
			return ln1;
		}
		public void setLn1(int ln1) {
			this.ln1 = ln1;
		}
		public int getLn2() {
			return ln2;
		}
		public void setLn2(int ln2) {
			this.ln2 = ln2;
		}
		public int getLn3() {
			return ln3;
		}
		public void setLn3(int ln3) {
			this.ln3 = ln3;
		}
		public int getLn4() {
			return ln4;
		}
		public void setLn4(int ln4) {
			this.ln4 = ln4;
		}
		public int getLn5() {
			return ln5;
		}
		public void setLn5(int ln5) {
			this.ln5 = ln5;
		}
		
		
	/**
	 * 进入老虎机重值数据
	 */
	public void release() {

		this.multiple = 0;
		this.zsBouns = 0;
		this.zsMoney = 0;
		this.totalGold = 0l;
		this.sequence = 1;
		this.cardNumber = 0;
		this.wolfBonusNum=0;
		this.wolfTimes=1;
		this.bounsConnectInfoData=new BounsConnectInfoData();
		this.elephantFreeNum=0;
		this.elephantMap.clear();
		this.elephantTurn=-1;
		this.elephantIsBigWild = false;
		this.theTigerTimes=1;
		this.tigerPickUpRewards=new ArrayList<Integer>();
		this.remaining = 0;
		this.rewardNum = 0;
		this.SlotType23Jackpot = "";
		this.bxBouns = 0;
		this.bxMoney = 0;
		this.position = 0;
		this.obtainReward = 0;
		this.slot12FreeTimesWinRewards = 1;
		this.ids = new ArrayList<Integer>();
		this.holmestotalGolds = 0;
		this.holmesTimes = 1;
		this.holmesAllWin = 1;
		this.dragonTime = 1;
		this.ln1 = 0;
		this.ln2 = 0;
		this.ln3 = 0;
		this.ln4 = 0;
		this.ln5 = 0;
		this.existWeightList = new ArrayList<Integer>();
		this.existBounsZeusRewardTemplateList = new ArrayList<BounsZeusRewardTemplate>();
		this.soldierElement = new ArrayList<Integer>();
		this.shootedWildElement = new ArrayList<Integer>();
		this.socialityFreeNum = 0;
		this.selfAttackNum = 0;
		this.SpartaFreeTurn = false;
		this.disTurn = false;
		this.elementList = new ArrayList<Element>();
		this.oneTwoThreePositionList  = new ArrayList<OneTwoThreePosition>();;
		this.oneTwoThreePosition  = new OneTwoThreePosition();;
		this.beforeFirstPosition  = new BeforeFirstPosition();;
		this.choseElementList  = new ArrayList<ChoseElement>();;
		this.supplyElementList  = new ArrayList<SupplyElement>();;
		this.megaSuperEpicWin  = false;
		this.removeTimes  = 0;
		this.isWinner=false;
		this.winnerGold= 0;
		
	}
}
