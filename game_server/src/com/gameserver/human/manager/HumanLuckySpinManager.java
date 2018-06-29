package com.gameserver.human.manager;

import java.util.List;

import com.common.InitializeRequired;
import com.core.util.ArrayUtils;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanLuckyMatchEntity;
import com.db.model.HumanLuckySpinEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.luckyspin.HumanLuckyMatch;
import com.gameserver.luckyspin.HumanLuckySpin;
import com.gameserver.luckyspin.msg.GCBigZhuanpan;


public class HumanLuckySpinManager implements RoleDataHolder, InitializeRequired{
	
	/** 主人 */
	private Human owner;
	private HumanLuckySpin humanLuckySpin;
	private HumanLuckyMatch humanLuckyMatch;
	
	public HumanLuckySpinManager(Human owner){
		this.owner=owner;
	}
	
	public Human getOwner() {
		return owner;
	}
	
	public HumanLuckySpin getHumanLuckySpin(){
		return humanLuckySpin;
	}
	
	public void load()
	{
		loadLuckSpin();
		loadLuckyMatch();
	}
	
	private void loadLuckSpin(){
		HumanLuckySpinEntity humanLuckySpinEntity = Globals.getDaoService().getHumanLuckySpinDao().getLuckySpinByCharId(owner.getPassportId());
		humanLuckySpin = new HumanLuckySpin();
		if (humanLuckySpinEntity == null) {
			long now = Globals.getTimeService().now();
			
			humanLuckySpin.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.LUCKYSPINID));
			humanLuckySpin.setCharId(owner.getPassportId());
			humanLuckySpin.setCreateTime(Globals.getTimeService().now());
			this.refreshPool();
			humanLuckySpin.setOwner(owner);
			humanLuckySpin.setInDb(false);
			humanLuckySpin.active();
			humanLuckySpin.setModified();
			return;
		}
		humanLuckySpin.setOwner(owner);
		humanLuckySpin.fromEntity(humanLuckySpinEntity);
	}
	
	private void loadLuckyMatch(){
		HumanLuckyMatchEntity humanLuckyMatchEntity = Globals.getDaoService().getHumanLuckyMatchDao().getLuckyMatchByCharId(owner.getPassportId());
		humanLuckyMatch = new HumanLuckyMatch();
		if (humanLuckyMatchEntity == null) {
			long now = Globals.getTimeService().now();
			
			humanLuckyMatch.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.LUCKYMATCHID));
			humanLuckyMatch.setCharId(owner.getPassportId());
			humanLuckyMatch.setCreateTime(Globals.getTimeService().now());
			this.refreshMatchPool();
			humanLuckyMatch.setOwner(owner);
			humanLuckyMatch.setInDb(false);
			humanLuckyMatch.active();
			humanLuckyMatch.setModified();
			return;
		}
		humanLuckyMatch.setOwner(owner);
		humanLuckyMatch.fromEntity(humanLuckyMatchEntity);
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
	
	/**
	 * 是否有免费次数
	 * @return
	 */
	public boolean ifHasFreeTime(){
		long now = Globals.getTimeService().now();
		return !TimeUtils.isSameDay(humanLuckySpin.getLastSpinTime(), now);
	}
	
	/**
	 * 是否有免费次数 
	 * @return true:可以免费
	 */
	public boolean ifFree(){
		long now = Globals.getTimeService().now();
		this.getHumanLuckySpin().getLastSpinTime();
		return now - this.getHumanLuckySpin().getLastSpinTime() > Globals.getConfigTempl().getLogintime()*60*60*1000;
	}
	
	/**
	 * 池子顺序
	 */
	public int poolPos(){
		int remains = this.humanLuckySpin.getTimes()%this.humanLuckySpin.getPoolList().size();
		return this.humanLuckySpin.getPoolList().get(remains);
	}
	/**
	 * spin
	 * @param free
	 */
	public void spin(boolean free){
		if(free){
			
			humanLuckySpin.setFreeTimes(humanLuckySpin.getFreeTimes()+1);
		}else{
			humanLuckySpin.setTimes(humanLuckySpin.getTimes()+1);
		}
		long now = Globals.getTimeService().now();
		humanLuckySpin.setLastSpinTime(now);
		humanLuckySpin.setModified();
	}
	
	public void checkIfRefresh() {
		// TODO Auto-generated method stub
		int remains = this.humanLuckySpin.getTimes()%this.humanLuckySpin.getPoolList().size();
		if(remains==0){
			refreshPool();
		}
	}
	
	
	
	private void refreshPool(){
		this.humanLuckySpin.getPoolList().clear();
		int size = Globals.getLuckySpinService().getPoolSize();
		int[] weights = new int[size];
		for(int i=0;i<weights.length;i++){
			weights[i]=1;
		}
		List<Integer> randomList =ArrayUtils.randomIndexByWeight(weights, weights.length, false);
		this.humanLuckySpin.getPoolList().addAll(randomList);
		this.humanLuckySpin.setModified();
	}
	
	/**
	 * 池子顺序
	 */
	public int matchPoolPos(){
		int remains = this.humanLuckyMatch.getTimes()%this.humanLuckyMatch.getPoolList().size();
		return this.humanLuckyMatch.getPoolList().get(remains);
	}
	
	/**
	 * spin
	 * @param free
	 */
	public void match(){
	
		long now = Globals.getTimeService().now();
		humanLuckyMatch.setLastMatchTime(now);
		humanLuckyMatch.setTimes(humanLuckyMatch.getTimes()+1);
		humanLuckyMatch.setModified();
	}
	
	public void checkIfMatchRefresh(){
		int remains = this.humanLuckySpin.getTimes()%this.humanLuckyMatch.getPoolList().size();
		if(remains==0){
			refreshMatchPool();
		}
	}
	
	private void refreshMatchPool(){
		this.humanLuckyMatch.getPoolList().clear();
		int size = Globals.getLuckySpinService().getMatchPoolSize();
		int[] weights = new int[size];
		for(int i=0;i<weights.length;i++){
			weights[i]=1;
		}
		
		List<Integer> randomList =ArrayUtils.randomIndexByWeight(weights, weights.length, false);
		this.humanLuckyMatch.getPoolList().addAll(randomList);
		this.humanLuckyMatch.setModified();
	}
	
	public GCBigZhuanpan buildGCLuckySpinInfoData(){
		GCBigZhuanpan message = new GCBigZhuanpan();
		message.setIsFree(owner.getHumanLuckySpinManager().ifFree()?1:0);
		message.setLoginPoint(owner.getHumanWeekSignInManager().getHumanWeekSignIn().getSignInList().size());
		return message;
	}
}
