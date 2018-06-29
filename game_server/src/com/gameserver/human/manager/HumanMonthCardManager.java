package com.gameserver.human.manager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanMonthCardEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.monthcard.HumanMonthCard;
import com.gameserver.monthcard.data.HumanMonthCardInfoData;
import com.gameserver.monthcard.msg.GCHumanMonthCardInfoData;
import com.gameserver.recharge.template.RechargeTemplate;


/**
 * 月卡管理器
 * @author wayne
 *
 */
public class HumanMonthCardManager  implements RoleDataHolder, InitializeRequired{
	
	
	private Human owner;
	private HumanMonthCard humanMonthCard;
	
	public HumanMonthCardManager(Human owner)
	{
		this.owner = owner;
	}
	
	public Human getOwner(){
		return this.owner;
	}
	
	public HumanMonthCard getHumanMonthCard()
	{
		return this.humanMonthCard;
	}
	
	/**
	 * 加载
	 */
	public void load(){
		HumanMonthCardEntity humanMonthCardEntity = Globals.getDaoService()
				.getHumanMonthCardDao().getMonthCardByCharId(owner.getPassportId());
		humanMonthCard = new HumanMonthCard();
		if (humanMonthCardEntity == null) {
			long now = Globals.getTimeService().now();
			humanMonthCard.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANMONTHCARDID));
			humanMonthCard.setCharId(owner.getPassportId());
			humanMonthCard.setCreateTime(Globals.getTimeService().now());
			humanMonthCard.setOwner(owner);
			humanMonthCard.setInDb(false);
			humanMonthCard.active();
			humanMonthCard.setModified();
			return;
		}

		humanMonthCard.setOwner(owner);
		humanMonthCard.fromEntity(humanMonthCardEntity);
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
	 * 初始奖励
	 * @return
	 */
	public List<RandRewardData> getMonthInitRewardData(int min) {
		// TODO Auto-generated method stub
		//判断是否叠加

		if(ifExpired())
		{
			long now = TimeUtils.getBeginOfDay( Globals.getTimeService().now());
			
			humanMonthCard.setBeginTime(now);
			humanMonthCard.setDuration(min * TimeUtils.MIN);
		}
		else
		{
			humanMonthCard.setDuration(humanMonthCard.getDuration()+min * TimeUtils.MIN);
		}
		humanMonthCard.setModified();
		
		List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
		RandRewardData randRewardData = new RandRewardData();
		randRewardData.setRewardId(Currency.GOLD.getIndex());
		randRewardData.setRewardCount(Globals.getConfigTempl().getMonthCardNum());
		randRewardDataList.add(randRewardData);
		return randRewardDataList;
	}
	/**
	 * 每天奖励 old
	 * @return
	 */
	public List<RandRewardData> getMonthDailyRewardData() {
		long now =Globals.getTimeService().now();
		humanMonthCard.setGetTime(now);
		humanMonthCard.setModified();
		List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
		RandRewardData randRewardData = new RandRewardData();
		randRewardData.setRewardId(Currency.GOLD.getIndex());
		randRewardData.setRewardCount(Globals.getConfigTempl().getMonthCardNumDay());
		randRewardDataList.add(randRewardData);
		
		return randRewardDataList;
	}
	/**
	 * 每天奖励 new
	 * @return
	 */
	public List<RandRewardData> getMonthDailyRewardDataNew() {
		long now = 0l;
		try {
			now = TimeUtils.getYMDTime(TimeUtils.formatYMDTime(new Date().getTime()));
		} catch (ParseException e) {
			Loggers.monthCardLogger.error("", e);
		}
		List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
		long getTime = humanMonthCard.getGetTime();
		long dur = 0l;
		if(getTime>0){
			 dur = (now - getTime)/(24*60*60*1000);
		}else{
			long beginTime = humanMonthCard.getBeginTime();
			dur = (now - beginTime)/(24*60*60*1000);
		}
		if(dur>0){
			humanMonthCard.setGetTime(now);
			humanMonthCard.setModified();
			for(int i=0;i<dur;i++){
				RandRewardData randRewardData = new RandRewardData();
				randRewardData.setRewardId(Currency.GOLD.getIndex());
				randRewardData.setRewardCount(Globals.getConfigTempl().getMonthCardNumDay());
				//在这里用 channelType（渠道ID） 和 产品ID 来查询template
				RechargeTemplate rechargeTemplate = Globals.getRechargeService().rechargeTemplateBySkuId(owner.getPlayer().getChannelType(), "goddesscasino_chips30");
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
	 * 判断是否购买
	 */
	public boolean ifExpired(){
		long now =Globals.getTimeService().now();
		return now >(humanMonthCard.getBeginTime()+humanMonthCard.getDuration());
		
	}
	
	/**
	 * 判断是否可以领取
	 * @return
	 */
	public boolean ifGet(){
		long now =Globals.getTimeService().now();
		return TimeUtils.isSameDay(humanMonthCard.getGetTime(), now);
	}

	/**
	 * 月卡数据
	 * @return
	 */
	public GCHumanMonthCardInfoData buildHumanMonthCardInfoData() {
		// TODO Auto-generated method stub
		GCHumanMonthCardInfoData gcHumanMonthCardInfoData = new GCHumanMonthCardInfoData();
		HumanMonthCardInfoData humanMonthCardInfoData = HumanMonthCardInfoData.convertFromHumanMonthCard(humanMonthCard);
		gcHumanMonthCardInfoData.setMonthCardInfoData(humanMonthCardInfoData);
		return gcHumanMonthCardInfoData;
	}
}
