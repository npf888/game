package com.gameserver.human.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.util.RandomUtil;
import com.core.uuid.UUIDType;
import com.db.model.HumanTreasuryEntity;
import com.gameserver.common.Globals;
import com.gameserver.common.db.RoleDataHolder;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.treasury.HumanTreasury;
import com.gameserver.treasury.msg.GCTreasury;
import com.gameserver.treasury.template.TreasuryTemplate;

public class HumanTreasuryManager implements RoleDataHolder, InitializeRequired{
	/** 主人 */
	private Human owner;
	private static Logger logger = Loggers.rechargeLogger;
	//数据库里的值(当前用户用的哪种类型的存钱罐)
	List<HumanTreasury> humanTreasurys = new ArrayList<HumanTreasury>();
	
	public HumanTreasuryManager(Human owner){
		this.owner=owner;
	}
	
	
	/**
	 * 加载邮件
	 */
	public void load()
	{
		List<HumanTreasuryEntity> _mailEntityList=Globals.getDaoService().getHumanTreasuryDao().getTreasuryByChartId(owner.getPassportId());
	
		if(_mailEntityList!=null&&_mailEntityList.size()>0){
			for(HumanTreasuryEntity humanTreasuryEntity:_mailEntityList){
				HumanTreasury humanTreasury = new HumanTreasury();
				humanTreasury.setOwner(owner);
				humanTreasury.fromEntity(humanTreasuryEntity);
				this.humanTreasurys.add(humanTreasury);
			}
		}
	}
	
	
	/**
	 * 推送存钱罐消息
	 */
	
	public GCTreasury sendTreasury(){
		try{
			//第二次以后的登录都是查询数据库
			if(humanTreasurys != null && humanTreasurys.size()>0){
				HumanTreasury humanTreasury = humanTreasurys.get(0);
				GCTreasury gCTreasury = new GCTreasury();
				gCTreasury.setCurGold(humanTreasury.getGold());
				gCTreasury.setMaxTreasury(humanTreasury.getMaxTreasury());
				gCTreasury.setTypeTreasury(humanTreasury.getType());
				gCTreasury.setVipPointTreasury(humanTreasury.getVipPointTreasury());
				return gCTreasury;
				
				//第一次登录取xls表里的标准数据 只取 type=0的数据(即金钱是 最小的那个)
			}else{
				TreasuryTemplate treasuryTemplate= Globals.getHumanTreasuryService().getTreasuryMap().get(0);
				
				
				HumanTreasury  humanTreasury = new HumanTreasury();
				humanTreasury.setOwner(owner);
				humanTreasury.setChartId(owner.getPassportId());
				humanTreasury.setGold(treasuryTemplate.getOriginalChipTreasury());
				humanTreasury.setFactorTreasury(treasuryTemplate.getFactorTreasury());
				humanTreasury.setCreateTime(new Date());
				humanTreasury.setUpdateTime(new Date());
				humanTreasury.setMaxTreasury(treasuryTemplate.getMaxTreasury());
				humanTreasury.setType(treasuryTemplate.getTypeTreasury());
				humanTreasury.setVipPointTreasury(treasuryTemplate.getVipPointTreasury());
				humanTreasury.setInDb(false);
				humanTreasury.active();
				long now = Globals.getTimeService().now();
				humanTreasury.setDbId(Globals.getUUIDService().getNextUUID(now,UUIDType.HUMANTREASURYID));
				humanTreasury.setModified();
				humanTreasurys.add(humanTreasury);
				
				GCTreasury gCTreasury = new GCTreasury();
				gCTreasury.setCurGold(humanTreasury.getGold());
				gCTreasury.setMaxTreasury(humanTreasury.getMaxTreasury());
				gCTreasury.setTypeTreasury(humanTreasury.getType());
				gCTreasury.setVipPointTreasury(humanTreasury.getVipPointTreasury());
				return gCTreasury;
			}
		}catch(Exception e){
			logger.error("", e);
			return null;
		}
	}
	
	
	/**
	 * 每次转动老虎机 往存钱罐里加钱
	 */
	
	public void everyTurnChangeGold(int bet,Human human){
		try{
			//当前的存钱罐
			if(humanTreasurys == null || humanTreasurys.size() == 0){
				return;
			}
			HumanTreasury humanTreasury = humanTreasurys.get(0);
			TreasuryTemplate treasuryTemplate= Globals.getHumanTreasuryService().getTreasuryMap().get(humanTreasury.getType());
			long bet1 = (int)bet;
			long factorTreasury = (int)treasuryTemplate.getFactorTreasury();
			long money = (bet1*factorTreasury)/10000;
			logger.info("one---bet::"+bet+"------humanTreasury.getFactorTreasury()::"+treasuryTemplate.getFactorTreasury());
			logger.info("one---money::"+money+"------humanTreasury.getGold()::"+humanTreasury.getGold());
			money = humanTreasury.getGold()+money;
			logger.info("two---money::"+money);
			//当前的金币小于最大值，但是加上转动老虎机的 金币又 超了，所以要取最大值
			if(humanTreasury.getGold() < treasuryTemplate.getMaxTreasury() && money >treasuryTemplate.getMaxTreasury()){
				humanTreasury.setGold(humanTreasury.getMaxTreasury());
				humanTreasury.setModified();
			}else if(money <= treasuryTemplate.getMaxTreasury()){
				humanTreasury.setGold(money);
				humanTreasury.setModified();
			}
			GCTreasury gCTreasury = new GCTreasury();
			gCTreasury.setCurGold(humanTreasury.getGold());
			gCTreasury.setMaxTreasury(humanTreasury.getMaxTreasury());
			gCTreasury.setTypeTreasury(humanTreasury.getType());
			gCTreasury.setVipPointTreasury(humanTreasury.getVipPointTreasury());
			human.getPlayer().sendMessage(gCTreasury);
		}catch(Exception e){
			logger.error("", e);
		}
		
	}
	
	/**
	 * 用户花完钱 购买的 存钱罐的金币 和vip等级
	 */
	public long spendMoneyBuyTreasury(Human human,int pid,int productId){
		try{
			HumanTreasury humanTreasury = humanTreasurys.get(0);
			/**
			 * 增加等级
			 */
			human.getHumanVipNewManager().addOnlyThePassPoint(humanTreasury.getVipPointTreasury());
			/**
			 * 增加金币
			 */
			long gold=humanTreasury.getGold();
			human.giveMoney(gold,Currency.GOLD, false, LogReasons.GoldLogReason.Treasury_type1, LogReasons.GoldLogReason.Treasury_type1.getReasonText(), productId, 1);
			int type = humanTreasury.getType();
			TreasuryTemplate treasuryTemplate = null;
			if(type == 5){
				int v = RandomUtil.nextInt(1, 6);
				treasuryTemplate= Globals.getHumanTreasuryService().getTreasuryMap().get(v);
			}else{
				treasuryTemplate= Globals.getHumanTreasuryService().getTreasuryMap().get(type+1);
				
			}
			humanTreasury.setGold(treasuryTemplate.getOriginalChipTreasury());
			humanTreasury.setFactorTreasury(treasuryTemplate.getFactorTreasury());
			humanTreasury.setCreateTime(new Date());
			humanTreasury.setUpdateTime(new Date());
			humanTreasury.setMaxTreasury(treasuryTemplate.getMaxTreasury());
			humanTreasury.setType(treasuryTemplate.getTypeTreasury());
			humanTreasury.setVipPointTreasury(treasuryTemplate.getVipPointTreasury());
			humanTreasury.setModified();
			
			GCTreasury gCTreasury = new GCTreasury();
			gCTreasury.setCurGold(humanTreasury.getGold());
			gCTreasury.setMaxTreasury(humanTreasury.getMaxTreasury());
			gCTreasury.setTypeTreasury(humanTreasury.getType());
			gCTreasury.setVipPointTreasury(humanTreasury.getVipPointTreasury());
			human.getPlayer().sendMessage(gCTreasury);
			
			return gold;
		}catch(Exception e){
			logger.error("", e);
		}
		return 0l;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void init() {
		
	}

	@Override
	public void checkAfterRoleLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkBeforeRoleEnter() {
		// TODO Auto-generated method stub
		
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	
	
}
