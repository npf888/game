package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.uuid.UUIDType;
import com.db.model.HumanSlotEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.slot.HumanSlot;
import com.gameserver.slot.Slot;
import com.gameserver.slot.pojo.HumanTemporaryData;
import com.gameserver.slot.pojo.SlotMessageCache;
import com.gameserver.slot.template.SlotsListTemplate;

/**
 * 老虎机管理器
 * @author wayne
 *
 */
public class HumanSlotManager  implements RoleDataHolder, InitializeRequired{
	private static Logger logger = Loggers.rechargeLogger;
	private Human owner;
	
	/** 对应的老虎机 **/
	private List<HumanSlot> humanSlotList = new ArrayList<HumanSlot>();
	
	/** 当前所在老虎机id **/
	private long currentSlotId = 0l;
	/** 当前老虎机卷轴位置列表 **/
	private List<Integer> currentSlotPosList = new ArrayList<Integer>();
	/** 当前押注  20*bet  **/
	private long currentBet;
	/** 当前累计的奖金  免费转动的时候累计的**/
	private long currentRewardNum;
	/** 免费次数 **/
	private int freeTimes;
	/** 使用了次数 **/
	private int useTimes;
	
	/** 累积押注 **/
	private int betCumulative;
	
	/**当前老虎机ID **/
	private int slotId;
	
	/** true： 第一次玩老虎机 **/
	private volatile boolean fly = false;
	
	private boolean isBigWin = false;
	
	private HumanTemporaryData humanTemporaryData = new HumanTemporaryData();
	
	
	private SlotMessageCache msgCache = new SlotMessageCache();
	
	private  List<Integer> randomIntListDEMO = new ArrayList<Integer>();
	
	public HumanSlotManager(Human owner){
		this.owner=owner;
	}
	
	
	
	public boolean isFly() {
		return fly;
	}



	public void setFly(boolean fly) {
		this.fly = fly;
	}



	public List<HumanSlot> getHumanSlotList(){
		return humanSlotList;
	}
	
	public List<Integer> getCurrentSlotPosList(){
		return this.currentSlotPosList;
	}	
	
	/**
	 * @return the currentSlotId
	 */
	public long getCurrentSlotId() {
		return currentSlotId;
	}

	/**
	 * @param currentSlotId the currentSlotId to set
	 */
	public void setCurrentSlotId(long currentSlotId) {
		this.currentSlotId = currentSlotId;
	}
	
	
	
	public int getSlotId() {
		return slotId;
	}

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	/**
	 * @return the currentBet
	 */
	public long getCurrentBet() {
		return currentBet;
	}

	/**
	 * @param currentBet the currentBet to set
	 */
	public void setCurrentBet(long currentBet) {
		this.currentBet = currentBet;
	}

	/**
	 * @return the currentRewardNum
	 */
	public long getCurrentRewardNum() {
		return currentRewardNum;
	}

	/**
	 * @param currentRewardNum the currentRewardNum to set
	 */
	public void setCurrentRewardNum(long currentRewardNum) {
		this.currentRewardNum = currentRewardNum;
	}

	/**
	 * @return the freeTimes
	 */
	public int getFreeTimes() {
		return freeTimes;
	}

	/**
	 * @param freeTimes the freeTimes to set
	 */
	public void setFreeTimes(int freeTimes) {
		this.freeTimes = freeTimes;
	}

	/**
	 * @return the useTimes
	 */
	public int getUseTimes() {
		return useTimes;
	}

	/**
	 * @param useTimes the useTimes to set
	 */
	public void setUseTimes(int useTimes) {
		this.useTimes = useTimes;
	}
	

	
    
	public HumanTemporaryData getHumanTemporaryData() {
		return humanTemporaryData;
	}



	public void setHumanTemporaryData(HumanTemporaryData humanTemporaryData) {
		this.humanTemporaryData = humanTemporaryData;
	}


	

	public SlotMessageCache getMsgCache() {
		return msgCache;
	}



	public void setMsgCache(SlotMessageCache msgCache) {
		this.msgCache = msgCache;
	}



	public boolean isBigWin() {
		return isBigWin;
	}



	public void setBigWin(boolean isBigWin) {
		this.isBigWin = isBigWin;
	}



	public List<Integer> getRandomIntListDEMO() {
		return randomIntListDEMO;
	}



	public void setRandomIntListDEMO(List<Integer> randomIntListDEMO) {
		this.randomIntListDEMO = randomIntListDEMO;
	}



	public void load(){
	  List<HumanSlotEntity>	humanSlotEntityList=Globals.getDaoService().getHumanSlotDao().getAllSlotsByCharId(owner.getPassportId());;
	  if(humanSlotEntityList==null ){
		  humanSlotEntityList = new ArrayList<HumanSlotEntity>();
	  }
	  
	  for(HumanSlotEntity humanSlotEntity : humanSlotEntityList){
		  HumanSlot tempHumanSlot = new HumanSlot();
		  tempHumanSlot.setOwner(owner);
		  tempHumanSlot.fromEntity(humanSlotEntity);
		  humanSlotList.add(tempHumanSlot);
	  }
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		checkSlots();
	}

	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub
		
	}
	
	private void checkSlots(){
		Map<Integer,Slot> map = Globals.getSlotService().getSlotList();
		for(Slot slot : map.values()){
			HumanSlot tempHumanSlot=getHumanSlotBySlotId(slot.getDbId());
			if(tempHumanSlot !=null){
				continue;
			}
			long now = Globals.getTimeService().now();
			tempHumanSlot = new HumanSlot();
			tempHumanSlot.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANSLOT));
			tempHumanSlot.setSlotId(slot.getDbId());
			tempHumanSlot.setCharId(owner.getPassportId());
			tempHumanSlot.setCreateTime(Globals.getTimeService().now());

			tempHumanSlot.setOwner(owner);
			tempHumanSlot.setInDb(false);
			tempHumanSlot.active();
			tempHumanSlot.setModified();
			this.humanSlotList.add(tempHumanSlot);
		}
	}
	
	/**
	 * 获得human slot 
	 * @param slotId
	 * @return
	 */
	public HumanSlot getHumanSlotBySlotId(long slotId){
		for(HumanSlot humanSlot:humanSlotList){
			if(humanSlot.getSlotId() == slotId)
				return humanSlot;
		}
		return null;
	}

	public boolean ifInSlot(){
		return this.currentSlotId!=0;
	}
	
	/**
	 * 老步加新步
	 * @param slotPosList
	 */
	public void slot(List<Integer> slotPosList){
		List<Integer> tempSlotList = new ArrayList<Integer>();
		if(!currentSlotPosList.isEmpty()){
			//老步加新步
			for(int i=0;i<slotPosList.size();i++){
				int nextPos = this.currentSlotPosList.get(i)+slotPosList.get(i);
				tempSlotList.add(nextPos);
			}
			currentSlotPosList.clear();
			currentSlotPosList.addAll(tempSlotList);
		}else{
			currentSlotPosList.addAll(slotPosList);
		}

	}

	
	/**
	 * 判断是否有免费次数
	 * @return
	 */
	public boolean ifHasFreeTimes(){
		return this.freeTimes>this.useTimes;
	}
	
	/**
	 * 判断是否免费次数结算
	 */
	public boolean ifFreeTimesEnd(){
		return this.freeTimes>0 && this.freeTimes == this.useTimes;
	}
	
	/**
	 * 免费slot
	 */
	public void freeSlot(){
		this.useTimes+=1;
	}
	
	/**
	 * 添加免费次数
	 */
	public void addFreeSlot(int ft){
		this.freeTimes +=ft;
	}
	
	/**
	 * 添加当前奖励
	 */
	public void addReward(long rn){
		this.currentRewardNum+=rn;
	}
	
	/**
	 * 玩家进入新的老虎机 重置数据
	 */
	public void reset(){
		this.currentSlotId = 0l;
		this.betCumulative = 0;
		currentSlotPosList.clear();
		this.resetFreeTimes();
		humanTemporaryData.release();
		randomIntListDEMO.clear();
	}
	
	/**
	 * 重置免费次数
	 */
	public void resetFreeTimes(){
		this.currentRewardNum = 0l;
		this.freeTimes=0;
		this.useTimes = 0;
	}
	
	/**
	 * 总押注
	 * @param slot
	 * @param tempAllBets
	 */
	public void bet(Slot slot,long tempAllBets){
		//获取当前角色老虎机
		HumanSlot tempHumanSlot = this.getHumanSlotBySlotId(slot.getDbId());
		this.currentBet = tempAllBets;
		isFirst();
		tempHumanSlot.setTotalBet(tempHumanSlot.getTotalBet()+tempAllBets);//总共下注累计
		tempHumanSlot.setModified();
		slot.spin(); //增加这个老虎机总的旋转次数
		slot.bet(tempAllBets);//增加这个老虎机总的押注量
	/*	addHumanExp(tempAllBets);//计算经验加成*/	}
	
	
	/**
	 * 玩家是否第一次玩老虎机
	 */
	private void isFirst(){
		fly = true;
		for(HumanSlot humanSlot : humanSlotList){
			if(humanSlot.getTotalBet() > 0){
				fly = false;
			}
		}
	}
	
	/**
	 *  经验值=下注量/20(参数可调整)
	 * @param betNum
	 */
    public void addHumanExp(long betNum){
    	
		int count = (int)betNum + betCumulative;
		
		int lvRatio = Globals.getConfigTempl().getLvRatio();
		
		owner.addExp(count/lvRatio, null);
		
		betCumulative = count%lvRatio;
	}
	
	/**
	 * 返还
	 * @param slot
	 * @param refund
	 */
	public void refund(Slot slot,long refund,long jackPot){
		HumanSlot tempHumanSlot = this.getHumanSlotBySlotId(slot.getDbId());
		tempHumanSlot.setTotalRefund(tempHumanSlot.getTotalRefund()+refund);
		tempHumanSlot.setTotalJackpot(tempHumanSlot.getTotalJackpot()+jackPot);
		tempHumanSlot.setModified();
		slot.refund(refund);
	}
	/**
	 * 判断 是否 是 megaWinNum，superWinNum，epicWinNum 这三个
	 * 如果是 ，就 说明送礼物 的时候的 免费的，如果不是 说明送礼物的时候是付费的
	 * @return
	 */
	public void isEpicOrSuperOrMega(long tempReward,int bet,SlotsListTemplate tempSlotsListTemplate){
		
		long times = tempReward/bet;
		if(times >= tempSlotsListTemplate.getEpicWinNum()){
			this.getHumanTemporaryData().setMegaSuperEpicWin(true);
		}else if(times >= tempSlotsListTemplate.getSuperWinNum()){
			this.getHumanTemporaryData().setMegaSuperEpicWin(true);
		}else if(times >= tempSlotsListTemplate.getMegaWinNum()){
			this.getHumanTemporaryData().setMegaSuperEpicWin(true);
		}
	}
	public int switchWinner(long tempReward,int bet,SlotsListTemplate tempSlotsListTemplate){
		
		long times = tempReward/bet;
		if(times >= tempSlotsListTemplate.getEpicWinNum()){
			return 4;
		}else if(times >= tempSlotsListTemplate.getSuperWinNum()){
			return 3;
		}else if(times >= tempSlotsListTemplate.getMegaWinNum()){
			return 2;
		}else if(times >= tempSlotsListTemplate.getBigWinNum()){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 判断用户中了 第几个 winner 
	 * 
	 * 记录购买之后应该给多少金币
	 */
	
	public void handleWinner(long tempReward,int bet,SlotsListTemplate tempSlotsListTemplate,boolean isJackpot,int goldType){
		logger.info("-------"+owner.getPassportId()+"-[大奖转盘]----开始------开始的金币：："+tempReward);
		int swithWinner= 0;
		if(isJackpot){
			swithWinner=5;
		}else{
			swithWinner = switchWinner(tempReward,bet,tempSlotsListTemplate);
			if(swithWinner == 0){
				return;
			}
		}
		logger.info("-------"+owner.getPassportId()+"-[大奖转盘]----开始------swithWinner：："+swithWinner);
		Globals.getWinnerWheelService().getByWinGold(owner,tempReward, swithWinner,goldType);
		
	}
}
