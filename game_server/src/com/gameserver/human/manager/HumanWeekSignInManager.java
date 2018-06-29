package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.util.ArrayUtils;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanWeekSignInEntity;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.signin.HumanWeekSignIn;
import com.gameserver.signin.msg.GCSignIn;
import com.gameserver.signin.msg.GCSignInInfoData;
import com.gameserver.vipnew.VipNewServer;

/**
 * 签到
 * @author wayne
 *
 */
public class HumanWeekSignInManager   implements RoleDataHolder, InitializeRequired{

	private Logger logger = Loggers.signInLogger;
	
	private Human owner;
	private HumanWeekSignIn humanWeekSignIn;
	
	/**
	 * 构造器
	 * 
	 * @param owner
	 */
	public HumanWeekSignInManager(Human owner) {
		this.owner = owner;
	}
	
	public Human getOwner(){
		return this.owner;
	}
	
	public HumanWeekSignIn getHumanWeekSignIn()
	{
		return this.humanWeekSignIn;
	}
	
	
	public void load(){
		HumanWeekSignInEntity humanWeekSignInEntity = Globals.getDaoService()
				.getHumanWeekSignInDao().getWeekSignInByCharId(owner.getPassportId());
		humanWeekSignIn = new HumanWeekSignIn();
		if (humanWeekSignInEntity == null) {
			long now = Globals.getTimeService().now();
			humanWeekSignIn.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANWEEKSIGNINID));
			humanWeekSignIn.setCharId(owner.getPassportId());
			humanWeekSignIn.setUpdateTime(Globals.getTimeService().now());
			humanWeekSignIn.setCreateTime(Globals.getTimeService().now());
			humanWeekSignIn.setOwner(owner);
			humanWeekSignIn.setInDb(false);
			humanWeekSignIn.active();
			humanWeekSignIn.setModified();
			return;
		}

		humanWeekSignIn.setOwner(owner);
		humanWeekSignIn.fromEntity(humanWeekSignInEntity);
	}
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		long now = Globals.getTimeService().now();
		//checkIfExpire(now);
		
	}

	private void checkIfExpire(long now) {
		
		//跨周删除
		if(!TimeUtils.isInSameWeek(humanWeekSignIn.getUpdateTime(), now)){
			humanWeekSignIn.getSignInList().clear();
			humanWeekSignIn.setModified();
		}
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
	 * 获取登陆奖励
	 * @param now
	 */
	public RandRewardData signInNew(){
		
	  List<Integer> list = humanWeekSignIn.getSignInList();
	  
	  int size = Globals.getSignInService().getSignSize();
	  
	  RandRewardData data = new RandRewardData();
		if(size > list.size()){
			int day = list.size()+1;
			data = Globals.getSignInService().getRandRewardDataForSignInNew(day);
			list.add(day);
		}else{
			data = Globals.getSignInService().getRandRewardDataForSignInNew(list.size());
			list.clear();
		}
		humanWeekSignIn.setModified();
		return data;
	}
	
	/**
	 * 获取登陆进度
	 * @return
	 */
	public List<Integer> getProgress(){
		return humanWeekSignIn.getSignInList();
	}
	
	
	
	//领取奖励
	public void signIn(int aDay,long now){
		checkIfExpire(now);
		//判断是第几天
		int day = TimeUtils.getWeekDayOfTime(now);
		if(aDay!= day)
		{
			//已经领取了
			logger.warn("玩家["+owner.getPassportId()+"]签到["+aDay+"] 天错误");
			owner.getPlayer().sendSystemMessage(LangConstants.ALREADY_SIGN_IN);
			return;
		}
		
		for(Integer signInDay : humanWeekSignIn.getSignInList())
		{
			if(signInDay==day)
			{
				//已经领取了
				logger.warn("玩家["+owner.getPassportId()+"]第["+day+"] 已经签到过了");
				owner.getPlayer().sendSystemMessage(LangConstants.ALREADY_SIGN_IN);
				return;
			}
		}
		
		humanWeekSignIn.getSignInList().add(day);
		
		//计算VIP加成
        VipNewServer server = Globals.getVipNewServer();
		
        
        RandRewardData template = Globals.getSignInService().getRandRewardDataForSignIn(day);
        
		int cumulative = humanWeekSignIn.getSignInList().size();
		
		//判断已经领了几天了
		RandRewardData culumativeRandRewardDataTemple = Globals.getSignInService().getRandRewardDataForCumulativeSignIn(cumulative);
		
		List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
		
		if(template!=null){//每天奖励
			
			RandRewardData randRewardData = new RandRewardData();
	    	randRewardData.setRewardCount(template.getRewardCount());
	    	randRewardData.setRewardId(template.getRewardId());
	    		
			int vipLevel = owner.getHumanVipNewManager().getVipLv();
			randRewardData.setRewardCount(server.getSiginRatio(vipLevel, randRewardData.getRewardCount()));
			randRewardDataList.add(randRewardData);
		}
		
		
			
		if(culumativeRandRewardDataTemple!=null){//累计登陆奖励
			
			RandRewardData culumativeRandRewardData = new RandRewardData();
			culumativeRandRewardData.setRewardCount(culumativeRandRewardDataTemple.getRewardCount());
			culumativeRandRewardData.setRewardId(culumativeRandRewardDataTemple.getRewardId());
	    	
			int vipLevel = owner.getHumanVipNewManager().getVipLv();
			culumativeRandRewardData.setRewardCount(server.getSiginRatio(vipLevel, culumativeRandRewardData.getRewardCount()));
			randRewardDataList.add(culumativeRandRewardData);
		}
			
		RandRewardData[] data = new RandRewardData[randRewardDataList.size()];
		
		for(int i = 0;i < randRewardDataList.size();i++){
			data[i] = randRewardDataList.get(i);
		}
		
		CommonLogic.getInstance().giveRandReward(owner, randRewardDataList,LogReasons.GoldLogReason.WEEK_SIGN_IN,LogReasons.DiamondLogReason.WEEK_SIGN_IN,LogReasons.CharmLogReason.WEEK_SIGN_IN,LogReasons.ItemLogReason.WEEK_SIGN_IN, false);
		GCSignIn gcSignIn = new GCSignIn();
		gcSignIn.setDay(day);	
		gcSignIn.setRandRewardList(data);
		owner.sendMessage(gcSignIn);
		
		humanWeekSignIn.setModified();
		//领取签到奖励
		logger.debug("玩家["+owner.getPassportId()+"]第["+day+"] 天签到领取奖励");
		Globals.getLogService().sendSignInLog(owner, LogReasons.SignInLogReason.SIGN_IN, LogReasons.SignInLogReason.SIGN_IN.getReasonText(), day, cumulative);
	}

	/**
	 * 签到数据 改成登陆
	 * @return
	 */
	public GCSignInInfoData buildHumanWeekSignInInfoData() {
		// TODO Auto-generated method stub
		GCSignInInfoData gcSignInInfoData = new GCSignInInfoData();
		gcSignInInfoData.setDayList(ArrayUtils.intList2Array(humanWeekSignIn.getSignInList()));
		return gcSignInInfoData;
	}

}
