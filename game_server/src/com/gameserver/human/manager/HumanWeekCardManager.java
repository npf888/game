package com.gameserver.human.manager;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanWeekCardEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.recharge.template.RechargeTemplate;
import com.gameserver.weekcard.HumanWeekCard;
import com.gameserver.weekcard.data.HumanWeekCardInfoData;
import com.gameserver.weekcard.msg.GCHumanWeekCardInfoData;

/**
 * 月卡管理器
 * @author wayne
 *
 */
public class HumanWeekCardManager  implements RoleDataHolder, InitializeRequired{
	

	
	private Human owner;
	private HumanWeekCard humanWeekCard;
	
	public HumanWeekCardManager(Human owner)
	{
		this.owner = owner;
	
	}
	
	public Human getOwner(){
		return this.owner;
	}
	
	public HumanWeekCard getHumanWeekCard()
	{
		return this.humanWeekCard;
	}
	
	public void load(){
		HumanWeekCardEntity humanWeekCardEntity = Globals.getDaoService()
				.getHumanWeekCardDao().getWeekCardByCharId(owner.getPassportId());
		humanWeekCard = new HumanWeekCard();
		if (humanWeekCardEntity == null) {
			long now = Globals.getTimeService().now();
			humanWeekCard.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANWEEKCARDID));
			humanWeekCard.setCharId(owner.getPassportId());
			humanWeekCard.setCreateTime(Globals.getTimeService().now());
			humanWeekCard.setOwner(owner);
			humanWeekCard.setInDb(false);
			humanWeekCard.active();
			humanWeekCard.setModified();
			return;
		}
		humanWeekCard.setOwner(owner);
		humanWeekCard.fromEntity(humanWeekCardEntity);
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
	 * 判断是否过期
	 */
	public boolean ifExpired(){
		long now =Globals.getTimeService().now();
		return now >(humanWeekCard.getBeginTime()+humanWeekCard.getDuration());
		
	}
	
	/**
	 * 判断是否可以领取
	 * @return
	 */
	public boolean ifGet(){
		long now =Globals.getTimeService().now();
		return TimeUtils.isSameDay(humanWeekCard.getGetTime(), now);
	}
	
	/**
	 * 建立初始奖励
	 * @return
	 */
	public List<RandRewardData> getWeekInitRewardData(int min)
	{
		
		if(ifExpired())
		{
			long now = TimeUtils.getBeginOfDay( Globals.getTimeService().now());
			
			humanWeekCard.setBeginTime(now);
			humanWeekCard.setDuration(min * TimeUtils.MIN);

		}
		else
		{
			humanWeekCard.setDuration(humanWeekCard.getDuration()+min * TimeUtils.MIN);
		}
		humanWeekCard.setModified();
		return getReward();
	}
	
	//周卡发送奖励
	public List<RandRewardData>  getReward(){
		List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
		RandRewardData randRewardData = new RandRewardData();
		randRewardData.setRewardId(Currency.GOLD.getIndex());
		randRewardData.setRewardCount(Globals.getConfigTempl().getWeekCardNum());
		randRewardDataList.add(randRewardData);
		return randRewardDataList;
	}
	
	/**
	 * 建立周奖励
	 */
	public List<RandRewardData> getWeekDailyRewardData()
	{
		long now =Globals.getTimeService().now();
		humanWeekCard.setGetTime(now);
		humanWeekCard.setModified();
		List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
		RandRewardData randRewardData = new RandRewardData();
		randRewardData.setRewardId(Currency.GOLD.getIndex());
		randRewardData.setRewardCount(Globals.getConfigTempl().getWeekCardNumDay());
		randRewardDataList.add(randRewardData);
		
		return randRewardDataList;
	}
	/**
	 * 每天奖励 new
	 * @return
	 */
	public List<RandRewardData> getWeekDailyRewardDataNew() {
		long now = 0l;
		try {
			now = TimeUtils.getYMDTime(TimeUtils.formatYMDTime(new Date().getTime()));
		} catch (ParseException e) {
			Loggers.monthCardLogger.error("", e);
		}
		List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
		long getTime = humanWeekCard.getGetTime();
		long dur = 0l;
		if(getTime>0){
			 dur = (now - getTime)/(24*60*60*1000);
		}else{
			long beginTime = humanWeekCard.getBeginTime();
			dur = (now - beginTime)/(24*60*60*1000);
		}
		if(dur>0){
			humanWeekCard.setGetTime(now);
			humanWeekCard.setModified();
			for(int i=0;i<dur;i++){
				RandRewardData randRewardData = new RandRewardData();
				randRewardData.setRewardId(Currency.GOLD.getIndex());
				randRewardData.setRewardCount(Globals.getConfigTempl().getWeekCardNumDay());
				//在这里用 channelType（渠道ID） 和 产品ID 来查询template
				RechargeTemplate rechargeTemplate = Globals.getRechargeService().rechargeTemplateBySkuId(owner.getPlayer().getChannelType(), "casinolegends_chips29");
				if(rechargeTemplate != null){
					randRewardData.setVippoint(rechargeTemplate.getVipPoint());
				}else{
					randRewardData.setVippoint(0);
				}
				randRewardDataList.add(randRewardData);
			}
		}
		
		return randRewardDataList;
	}
	/**
	 * 周卡信息
	 * @return
	 */
	public GCHumanWeekCardInfoData buildHumanWeekCardInfoData()
	{
		GCHumanWeekCardInfoData gcHumanWeekCardInfoData = new GCHumanWeekCardInfoData();
		HumanWeekCardInfoData humanWeekCardInfoData = HumanWeekCardInfoData.convertFromHumanWeekCard(humanWeekCard);
		gcHumanWeekCardInfoData.setWeekCardInfoData(humanWeekCardInfoData);
		return gcHumanWeekCardInfoData;
		
	}
}
