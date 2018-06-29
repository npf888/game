package com.gameserver.human.manager;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanVipEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.human.Human;
import com.gameserver.vip.HumanVip;
import com.gameserver.vip.data.HumanVipInfoData;
import com.gameserver.vip.msg.GCHumanVipInfoData;
import com.gameserver.vip.template.VipTemplate;

/**
 * vip管理器
 * @author wayne
 *
 */
public class HumanVipManager implements RoleDataHolder, InitializeRequired{
	
	private Logger logger = Loggers.vipLogger;
	
	private Human owner;
	//vip
	private HumanVip vip;
	
	public HumanVipManager(Human owner)
	{
		this.owner = owner;
	}
	
	public Human getOwner(){
		return this.owner;
	}
	
	public HumanVip getHumanVip()
	{
		return vip;
	}
	

	public void load(){
		HumanVipEntity humanVipEntity = Globals.getDaoService().getVipDao().getVipByCharId(owner.getPassportId());
		vip = new HumanVip();
		if (humanVipEntity == null) {
			long now = Globals.getTimeService().now();
			vip.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANVIPID));
			vip.setCharId(owner.getPassportId());
			vip.setCreateTime(Globals.getTimeService().now());
			vip.setOwner(owner);
			vip.setInDb(false);
			vip.active();
			vip.setModified();
			return;
		}
		vip.setOwner(owner);
		vip.fromEntity(humanVipEntity);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		//checkIfExpire();
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
	 * 检查是否过期
	 */
	public void checkIfExpire() {
		// TODO Auto-generated method stub
		long now = Globals.getTimeService().now();

		if(vip.getEndTime() <now)
		{
			logger.info("玩家["+owner.getPassportId()+"]vip 过期了");
			vip.setLevel(0);
			vip.setDays(0);
			vip.setModified();
			return;
		}
	}
	
	/**
	 * 购买vip
	 */
	public void buyVipLevel(int lv){
		if(vip.getLevel() == lv){
			vip.setDays(vip.getDays()+Globals.getConfigTempl().getVipTime());
		}
		else
		{
			vip.setDays(Globals.getConfigTempl().getVipTime());
			vip.setBuyTime(Globals.getTimeService().now());
		}
		vip.setLevel(lv);
	
		vip.setModified();
	}
	
	/**
	 * 领取奖励
	 */
	public void getReward(){
		long now = Globals.getTimeService().now();
		vip.setGetTime(now);
		vip.setModified();
	} 
	
	/**
	 * 领取剩余奖励
	 */
	public List<RandRewardData> getRemainRewards(){
		List<RandRewardData> randRewardDataList=  new ArrayList<RandRewardData>();
		long now = Globals.getTimeService().now();
		int days = TimeUtils.daysBetween(now,vip.getEndTime());
		if(TimeUtils.isSameDay(now, vip.getGetTime()))
		{
			--days;
		}
		
		for(int i=0;i<days;i++){
			VipTemplate vipTemplate = Globals.getVipService().getVipTemplateByLevel(vip.getLevel());
			randRewardDataList.addAll(vipTemplate.getDailyRewardList());
		}
		
		return RandRewardData.combine(randRewardDataList);
	
	}
	
	/**
	 * 是否是vip
	 */
	public boolean ifVip(){
		return vip.getLevel()>0;
	}
	
	/**
	 * 是否已经领过了
	 */
	public boolean ifGet(){
		long now = Globals.getTimeService().now();
		return TimeUtils.isSameDay(now, vip.getGetTime());
	}

	
	/**
	 * vip信息
	 * @return
	 */
	public GCHumanVipInfoData buildHumanVipInfoData()
	{
		GCHumanVipInfoData gcHumanVipInfoData = new GCHumanVipInfoData();
		HumanVipInfoData humanVipInfoData = HumanVipInfoData.convertFromHumanVip(vip);
		gcHumanVipInfoData.setVipInfoData(humanVipInfoData);
		return gcHumanVipInfoData;
		
	}
}
