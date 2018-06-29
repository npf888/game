package com.gameserver.human.manager;

import java.util.Date;

import com.common.InitializeRequired;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanPayguideEntity;
import com.gameserver.common.Globals;
import com.gameserver.guide.msg.GCPayGuide;
import com.gameserver.guide.pojo.HumanPayguide;
import com.gameserver.human.Human;

public class HumanPayguideManager implements  InitializeRequired{
	/** 1:treasury-小金猪    2:exp-经验加速卡     3:preference-特惠礼包      4:club-俱乐部      5:monthcard-月卡    6:coupon-优惠券 */
	public static final int treasury=1;
	public static final int exp=2;
	public static final int preference=3;
	public static final int club=4;
	public static final int monthcard=5;
	public static final int coupon=6;
	
	
	private HumanPayguide humanPayguide;
	private Human owner;

	public HumanPayguideManager(Human human){
		this.owner=human;	
	}
	
	@Override
	public void init() {
		HumanPayguideEntity humanPayguideEntity = Globals.getDaoService().getHumanPayguideDao().getHumanPayguideEntity(owner.getPassportId());
		if(humanPayguideEntity == null){
			//初始化 数据 这条数据在数据库里 一个人只能有一条（时间都是当天的） 
			humanPayguide = new HumanPayguide(owner);
			humanPayguide.setTime(new Date());
			humanPayguide.setUserId(owner.getPassportId());
			humanPayguide.setTreasury(0);
			humanPayguide.setPreference(0);
			humanPayguide.setMonthcard(0);
			humanPayguide.setId(Globals.getUUIDService().getNextUUID(new Date().getTime(),UUIDType.HUMANPAYGUIDE));
			humanPayguide.setCoupon(0);
			humanPayguide.setClub(0);
			humanPayguide.setExp(0);
			humanPayguide.active();
			humanPayguide.setModified();
		}else{
			humanPayguide = new HumanPayguide(owner);
			humanPayguide.fromEntity(humanPayguideEntity);
			//如果不是 同一天 就 重新初始化
			if(!TimeUtils.formatYMDTime(humanPayguide.getTime().getTime()).equals(TimeUtils.formatYMDTime(new Date().getTime()))){
				humanPayguide.setTime(new Date());
				humanPayguide.setTreasury(0);
				humanPayguide.setPreference(0);
				humanPayguide.setMonthcard(0);
				humanPayguide.setCoupon(0);
				humanPayguide.setClub(0);
				humanPayguide.setExp(0);
				humanPayguide.setModified();
			}
		}
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public HumanPayguide getHumanPayguide() {
		return humanPayguide;
	}

	public void setHumanPayguide(HumanPayguide humanPayguide) {
		this.humanPayguide = humanPayguide;
	}

	
	/**
	 * 通知前端 优惠券的 新手引导
	 */
	
	public void sendCouponPayGuide(){
		int coupon = getHumanPayguide().getCoupon();
		//0:还没有提醒过，1：提醒过
		if(coupon== 0){
			getHumanPayguide().setCoupon(1);
			getHumanPayguide().setModified();
			//发送消息通知前端
			GCPayGuide gCPayGuide = new GCPayGuide();
			gCPayGuide.setPayType(HumanPayguideManager.coupon);
			getOwner().getPlayer().sendMessage(gCPayGuide);
		}
	}
	
	/**
	 * exp 经验加速卡  和 小金猪
	 */
	
	public void sendTreasuryAndExp(boolean isExp){
		/**
		 * 如果是 有中奖 则 直接走 经验
		 */
		if(isExp){
			sendExp();
		}else{
			int treasury = getHumanPayguide().getTreasury();
			/**
			 * 这里有个优先级   小金猪在前边 
			 */
			if(treasury == 0){
				getHumanPayguide().setTreasury(1);
				getHumanPayguide().setModified();
				//发送消息通知前端
				GCPayGuide gCPayGuide = new GCPayGuide();
				gCPayGuide.setPayType(HumanPayguideManager.treasury);
				getOwner().getPlayer().sendMessage(gCPayGuide);
			}else{
				sendExp();
			}
		}
		
	}
	
	private void sendExp(){
		int exp = getHumanPayguide().getExp();
		//0:还没有提醒过，1：提醒过
		if(exp== 0){
			getHumanPayguide().setExp(1);
			getHumanPayguide().setModified();
			//发送消息通知前端
			GCPayGuide gCPayGuide = new GCPayGuide();
			gCPayGuide.setPayType(HumanPayguideManager.exp);
			getOwner().getPlayer().sendMessage(gCPayGuide);
		}
	}
	
	
	/**
	 * 特惠礼包
	 */
	
	public void sendPreference(){
		int preference = getHumanPayguide().getPreference();
		//0:还没有提醒过，1：提醒过
		if(preference== 0){
			getHumanPayguide().setPreference(1);
			getHumanPayguide().setModified();
			//发送消息通知前端
			GCPayGuide gCPayGuide = new GCPayGuide();
			gCPayGuide.setPayType(HumanPayguideManager.preference);
			getOwner().getPlayer().sendMessage(gCPayGuide);
		}
	}
	/**
	 * 俱乐部
	 */
	
	public void sendClub(){
		int club = getHumanPayguide().getClub();
		//0:还没有提醒过，1：提醒过
		if(club== 0){
			getHumanPayguide().setClub(1);
			getHumanPayguide().setModified();
			//发送消息通知前端
			GCPayGuide gCPayGuide = new GCPayGuide();
			gCPayGuide.setPayType(HumanPayguideManager.club);
			getOwner().getPlayer().sendMessage(gCPayGuide);
		}
	}
	/**
	 * 月卡
	 */
	
	public void sendMonthcard(){
		int monthcard = getHumanPayguide().getMonthcard();
		//0:还没有提醒过，1：提醒过
		if(monthcard== 0){
			getHumanPayguide().setMonthcard(1);
			getHumanPayguide().setModified();
			//发送消息通知前端
			GCPayGuide gCPayGuide = new GCPayGuide();
			gCPayGuide.setPayType(HumanPayguideManager.monthcard);
			getOwner().getPlayer().sendMessage(gCPayGuide);
		}
	}
}
