package com.gameserver.human.manager;



import java.text.MessageFormat;
import java.util.HashMap;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanBaccartEntity;
import com.gameserver.baccart.BaccartRoom;
import com.gameserver.baccart.HumanBaccart;
import com.gameserver.baccart.data.BaccartBetData;
import com.gameserver.baccart.enums.BaccartBetType;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.lobby.enums.GameType;
import com.gameserver.vipnew.template.VipNewTemplate;


/**
 * 百家乐管理器
 * @author wayne
 *
 */
public class HumanBaccartManager  implements RoleDataHolder, InitializeRequired{

	
	public static int MAX_BEACON_WAIT_TIME = 10;
	public static int BEACON_CONTINUE_WIN_NUM=4;
	private Human owner;
	private HumanBaccart humanBaccart;
	
	private BaccartRoom baccartRoom;
	private long coins;
	private int pos;
	private int continueWins;
	private long minWins;
	private long lastWinTime;
	private long nextComplement;
	private int previousContinueWins;
	private long lastResult = 0;
	
	
	private HashMap<BaccartBetType,Long> baccartBetDataMap = new HashMap<BaccartBetType,Long>();
	
	
	/** 百家乐押注累计   **/
	private int betCumulative;
	
	
	
	/**
	 * 构造器
	 * 
	 * @param owner
	 */
	public HumanBaccartManager(Human owner) {
		this.owner = owner;
	
	}
	
	public Human getOwner(){
		return this.owner;
	}

	/**
	 * 加载德州信息
	 */
	public void load() {
		HumanBaccartEntity humanBaccartEntity = Globals.getDaoService().getHumanBaccartDao().getHumanBaccartByCharId(owner.getPassportId());
		humanBaccart = new HumanBaccart();
		
		if (humanBaccartEntity == null) {
			long now = Globals.getTimeService().now();
			humanBaccart.setDbId(Globals.getUUIDService().getNextUUID(now,
					UUIDType.HUMANBACCARTID));
			humanBaccart.setCharId(owner.getPassportId());
	
			humanBaccart.setCreateTime(Globals.getTimeService().now());
			humanBaccart.setOwner(owner);
			humanBaccart.setInDb(false);
			humanBaccart.active();
			humanBaccart.setModified();
			return;
		}
		humanBaccart.setOwner(owner);

		humanBaccart.fromEntity(humanBaccartEntity);
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub
	
	}

	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub

	}
	
	public HumanBaccart getHumanBaccart(){
		return this.humanBaccart;
	}
	
	public void setBaccartRoom(BaccartRoom baccartRoom){
		this.baccartRoom = baccartRoom;
	}
	
	public BaccartRoom getBaccartRoom(){
		return this.baccartRoom;
	}
	
	public long getCoins() {
		return coins;
	}

	public void setCoins(long coins) {
		this.coins = coins;
	}

	public void setPos(int pos){
		this.pos = pos;
	}
	
	public int getPos(){
		return pos;
	}

	public int getContinueWins() {
		return continueWins;
	}

	public long getMinWins(){
		return this.minWins;
	}
	
	public void setContinueWins(int continueWins) {
		this.continueWins = continueWins;
	}
	
	public long getLastWinTime(){
		return this.lastWinTime;
	}

	public void setNextComplement(long nextComplement){
		this.nextComplement = nextComplement;
	}
	
	public int getPreviousContinueWins() {
		return previousContinueWins;
	}

	public void setPreviousContinueWins(int previousContinueWins) {
		this.previousContinueWins = previousContinueWins;
	}

	public HashMap<BaccartBetType,Long> getBaccartBetDataMap(){
		return this.baccartBetDataMap;
	}

	/**
	 * 下注
	 * @param betDataList
	 */
	public void bet(BaccartBetData[] betDataList){
		long betNum = BaccartBetData.betNumForBaccartBetDataList(betDataList);
		this.coins -= betNum;
		
		for(BaccartBetData tempBaccartBetData:betDataList){
			BaccartBetType tempBaccartBetType=BaccartBetType.valueOf(tempBaccartBetData.getBetType());
			Long tempBetNumLong = this.baccartBetDataMap.get(BaccartBetType.valueOf(tempBaccartBetData.getBetType()));
			long tempBetNum = 0;
			if(tempBetNumLong!=null){
				tempBetNum = tempBetNumLong;
			}
			tempBetNum+=tempBaccartBetData.getBetNum();
			this.baccartBetDataMap.put(tempBaccartBetType, tempBetNum);
		}
		//下注百家乐成就统计
		this.owner.getHumanAchievementManager().updateBaccartCumulativeBet(betNum);
	}
	/**
	 * 判断是否能下注
	 * @param betDataList
	 * @return
	 */
	public boolean ifBet(BaccartBetData[] betDataList){
		for(BaccartBetData tempBaccartBetData : betDataList){
			BaccartBetType tempType = BaccartBetType.valueOf(tempBaccartBetData.getBetType());
			if(tempType==null)
				return false;
	
			if(tempType == BaccartBetType.BANKER){
				Long tempNum = this.baccartBetDataMap.get(BaccartBetType.PLAYER);
				if(tempNum!=null && tempNum>0){
					return false;
				}
				continue;
			}
			if(tempType == BaccartBetType.PLAYER){
				Long tempNum = this.baccartBetDataMap.get(BaccartBetType.BANKER);
				if(tempNum!=null && tempNum>0){
					return false;
				}
				continue;
			}
		}
		return true;
	}
	
	/**
	 *  
	 * @param winBet
	 * @return
	 */
	public void win(long winBet){
		this.coins+=winBet;
		
		long tempBet = this.getBetNum();
		lastResult = winBet- tempBet;
		if(lastResult>0){
		    checkIfContinueWins(lastResult);
			this.humanBaccart.setWinNum(this.humanBaccart.getWinNum()+1);
			this.humanBaccart.setGameNum(this.humanBaccart.getGameNum()+1);
			this.humanBaccart.setModified();
		}else{
			lose();
		}
	}
	
	/**
	 * jackpot
	 */
	public void jackpot(long jackpot){
		this.coins+=jackpot;
		//添加彩金
		Globals.getJackpotServer().add(owner.getPassportId(), owner.getImg(), owner.getName(), jackpot,GameType.BACCART.getIndex());
	}
	
	/**
	 * 失败
	 */
	public void lose() {
		// TODO Auto-generated method stub
		this.previousContinueWins = this.continueWins;
		this.setContinueWins(0);
		
		this.lastWinTime = Globals.getTimeService().now();
		//下注了
		if(getBetNum()>0){
			this.humanBaccart.setLostNum(this.humanBaccart.getLostNum()+1);
			this.humanBaccart.setGameNum(this.humanBaccart.getGameNum()+1);
			this.humanBaccart.setModified();
		}
	}
	
	/**
	 * 离开
	 */
	public void leave(){
		
		addHumanExp(getBetNum());
		
		this.pos = -1;
		this.baccartRoom = null;
		this.coins = 0;
		this.lastResult = 0;
		this.nextComplement = 0;
		this.betCumulative = 0;
		this.baccartBetDataMap.clear();
		lose();
	}
	
	/**
	 * 检查是否连续赢
	 */
	private void checkIfContinueWins(long wins){
	
		long now = Globals.getTimeService().now();
		if(now-lastWinTime>=MAX_BEACON_WAIT_TIME *TimeUtils.MIN || this.getContinueWins()==0){
			this.previousContinueWins = 0;
			this.setContinueWins(1);
			this.lastWinTime = now;
			this.minWins = wins;
		}else{
			this.previousContinueWins = this.getContinueWins();
			this.setContinueWins(this.getContinueWins() + 1);
			if(this.getContinueWins() == BEACON_CONTINUE_WIN_NUM){
				this.humanBaccart.setBeaconNum(this.humanBaccart.getBeaconNum()+1);
				this.humanBaccart.setModified();
			}
			this.lastWinTime = now;
			if(this.minWins>wins){
				this.minWins = wins;
			}
		}
		
		/*int maxContinueTime = Globals.getVipService().getVipTemplateByLevel(1).getWinTimes()-1;
		
		VipTemplate tempVipTemplate=Globals.getVipService().getVipTemplateByLevel(this.owner.getHumanVipManager().getHumanVip().getLevel());
		if(tempVipTemplate!=null){
			maxContinueTime = tempVipTemplate.getWinTimes();
		}*/
		
		int maxContinueTime = Globals.getVipNewServer().getVipNewTemplate(0).getWinTimes();
		
		VipNewTemplate vipNewTemplate = Globals.getVipNewServer().getVipNewTemplate(owner.getHumanVipNewManager().getVipLv());
		if(vipNewTemplate!=null){
			maxContinueTime = vipNewTemplate.getWinTimes();
		}
		
		if(this.getContinueWins()>maxContinueTime){
			this.previousContinueWins = 0;
			this.setContinueWins(1);
			this.minWins = wins;
		}
		
	}
	
	//清理桌面
	public void clear(){
		nextComplement = 0;
		
		this.lastResult = 0;
		this.baccartBetDataMap.clear();
	}
	
	/**
	 * 押注额度
	 * @return
	 */
	public long getBetNum(){
		return  BaccartBetData.betNumForBaccartBetDataMap(this.baccartBetDataMap);
	}
	
	/**
	 * 计算经验加成 目前中途离开不给算加成经验，只有等到最后结算的时候算加成经验
	 * 百家乐(经验值=下注量/参数)
	 * @param betNum
	 */
    public void addHumanExp(long betNum){
    	
    	if(betNum <= 0)return;
		
		int count = (int)betNum + betCumulative;
		
		int lvRatio = Globals.getConfigTempl().getLvRatio();
		
		owner.addExp(count/lvRatio, null);
		
		betCumulative = count%lvRatio;
		
	}
	
	/**
	 * 是否筹码足够
	 * 
	 * @return
	 */
	public long complementGold() {
	
		// 当前最大筹码
		long currentAll = this.coins + this.owner.getGold();
		if (currentAll + this.coins < baccartRoom.getMinCarry()) {
			return 0;
		}

		long complement = 0L;
		
		if(nextComplement==0){
			if(this.coins>=baccartRoom.getMinCarry())
				return 0L;
		}
		
		if (nextComplement != 0) {
			// 超过最大补充量
			complement = nextComplement;
		}
		// 补充一半
		else if (humanBaccart.getIsAuto() == 0) {
			complement = baccartRoom.getMaxCarry() / 2;
		}
		// 补充到最大量
		else {
			complement = baccartRoom.getMaxCarry();
		}

		complement = complement - this.coins;
		if (complement < 0)
			complement = 0L;

		// 判断是否超过自身筹码量
		if (complement >= this.owner.getGold()) {
			complement = this.owner.getGold();
		}

		if (complement <= 0)
			return 0;

		String detailReason = MessageFormat.format(
				LogReasons.GoldLogReason.BACCARAT_ROOM_COMPLEMENT.getReasonText(),
				baccartRoom.getId(), complement);
		this.owner.costMoney(complement, Currency.GOLD, true,
				LogReasons.GoldLogReason.BACCARAT_ROOM_COMPLEMENT, detailReason,
				-1, 1);

		this.coins += complement;
		return complement;
	}
	
	
	
	public void revive(boolean aRevive){
		if(!aRevive){
			this.previousContinueWins = 0;
			
		}else{
			this.continueWins = this.previousContinueWins;
			this.lastWinTime = Globals.getTimeService().now();
		}
	}

	public long getLastResult() {
		// TODO Auto-generated method stub
		return this.lastResult;
	}
}
