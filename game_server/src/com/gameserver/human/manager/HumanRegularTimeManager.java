package com.gameserver.human.manager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.HumanMonthWeekEntity;
import com.gameserver.activity.HumanMonthWeek;
import com.gameserver.activity.ScheduleRegularTime;
import com.gameserver.activity.WeeklyMonthlyPackService;
import com.gameserver.activity.msg.GCMonthOrWeek;
import com.gameserver.activity.template.WeeklyMonthlyPackTemplate;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.recharge.template.RechargeTemplate;

/**
 * 用户一登录按照一定时间 循环处理
 * 
 * 目前有  周、月特惠充值活动（不是活动 是功能）
 * @author JavaServer
 *
 */
public class HumanRegularTimeManager implements  InitializeRequired{

	
	private Logger logger = Loggers.scheduleLogger;
	private Human ower;
	//用户所拥有的特惠礼包的类型
	private List<HumanMonthWeek> HumanMonthWeekList = new ArrayList<HumanMonthWeek>();
	public HumanRegularTimeManager(Human ower){
		this.ower=ower;
	}
	/**
	 * 
	 */
	@Override
	public void init() {
		try{
			startMonthAndWeekNew();
		}catch(Exception e){
			logger.error("", e);
		}
	}
	/**
	 * 此方法 不用了
	 * @throws ParseException
	 */
	/*private void startMonthAndWeek() throws ParseException{
		
		WeeklyMonthlyPackService weeklyMonthlyPackService = Globals.getWeeklyMonthlyPackService();
		WeeklyMonthlyPackTemplate weekPack = weeklyMonthlyPackService.getWeekByVip(ower.getHumanVipManager().getHumanVip().getLevel());
		*//**
		 * 周
		 *//*
		//礼包持续时间
		int packDurationTime = weekPack.getPackDuration()/(24*60*60);
		
		//礼包刷新时间
		int packCycleTime = weekPack.getPackCycle()/(24*60*60);
		*//**
		 * 当前时间，如果没有就 设置当前时间，
		 * 
		 *//*
		Date now = new Date();
		String nowStr = TimeUtils.formatYMDTime(now.getTime());
		String regularTime  = this.ower.getRegularTime();
		if(StringUtils.isBlank(regularTime)){
			regularTime = nowStr;
			getOwer().setRegularTime(nowStr);
			getOwer().setModified();
		}else{
			//这里比较用户的最后登录时间  如果超过一周 没有登录，就重新设置当前时间
			long lastLoginTime = getOwer().getLastLoginTime();
			long regularTimeLong = TimeUtils.getYMDTime(regularTime);
			long diff = (lastLoginTime-regularTimeLong);
			long aWeek = (24*3600*1000)*packCycleTime;
			//如果大于一周 重置如果超过一周，就重新设置当前时间
			if(diff > aWeek){
				regularTime = nowStr;
				getOwer().setRegularTime(nowStr);
				getOwer().setModified();
			}
		}
		//每天刷新  看是否 到了 到了第7天 ，如果到了 就发送消息
		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleRegularTime7(regularTime,nowStr,packCycleTime,packDurationTime,ower), LLScheduleEnum.REGULARTIME, 0, 60*1000);
		//每天刷新  看是否 到了 到了第28天 ，如果到了 就发送消息
		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleRegularTime28(regularTime,nowStr,packCycleTime,packDurationTime,ower), LLScheduleEnum.REGULARTIME, 0, 60*1000);
	}*/
	
	
	private void startMonthAndWeekNew() throws ParseException{
		
		WeeklyMonthlyPackService weeklyMonthlyPackService = Globals.getWeeklyMonthlyPackService();
		List<WeeklyMonthlyPackTemplate> weekPackList = weeklyMonthlyPackService.getWeekByVip(ower.getHumanVipManager().getHumanVip().getLevel());
		for(WeeklyMonthlyPackTemplate weekPack:weekPackList){
			int packType = weekPack.getPackType();
			
			//礼包持续时间
			int packDurationTime = weekPack.getPackDuration()/(24*60*60);
			Date now = new Date();
			//礼包刷新时间
			long packCycleTime = weekPack.getPackCycle()/(24*60*60);
			//礼包类型，0表示周礼包，1表示月礼包
			
			/**
			 * 周  月
			 */
			HumanMonthWeekEntity humanMonthWeekEntity = Globals.getDaoService().getHumanMonthWeekDao().getHumanMonthWeekEntityByUserIdAndMnType(ower.getPassportId(), packType);
			//如果当前 库里 用户没有 马上创建 一个 特惠 礼包
			if(humanMonthWeekEntity == null){
				startHumanMonthWeek(now,ower,packDurationTime,packType,null,false);
			}else{
				Date startdate = humanMonthWeekEntity.getStartTime();
				long coninueTime = humanMonthWeekEntity.getContinueTime()*24*60*60*1000;
				//首先看看 还在不在 特惠日期内
				long endTime = startdate.getTime()+coninueTime;//正式 用
//				long endTime = startdate.getTime()+30*60*1000;//测试 用
				//如果过期了  看看需不需要 在创建一个  （周特惠礼包 从开始时间的 7天之内不用创建）
				if(now.getTime()>endTime){
					long didNotLoginTime = now.getTime()-startdate.getTime();
					long tPackCycleTime = 24*60*60*1000*packCycleTime;//正式 用
//					long tPackCycleTime = 35*60*1000;//测试 用
					//如果超过七天 就创建一个 没有就拉到
					if(didNotLoginTime>tPackCycleTime){
						HumanMonthWeek humanMonthWeek = new HumanMonthWeek(ower);
						humanMonthWeek.fromEntity(humanMonthWeekEntity);
						startHumanMonthWeek(now,ower,packDurationTime,packType,humanMonthWeek,true);
					/**
					 * 没有超过7天但是超过一天， 也要清楚 HumanMonthWeekList 相应的对象
					 */
					}else{
						List<HumanMonthWeek> shouldRemoveHumanMonthWeekList = new ArrayList<HumanMonthWeek>();
						if(HumanMonthWeekList != null && HumanMonthWeekList.size()>0){
							for(HumanMonthWeek HumanMonthWeek:HumanMonthWeekList){
								if(HumanMonthWeek.getMwType() == packType){
									shouldRemoveHumanMonthWeekList.add(HumanMonthWeek);
								}
							}
							HumanMonthWeekList.removeAll(shouldRemoveHumanMonthWeekList);
						}
					}
					
				//如果没有过期 就放到内存里
				}else{
					HumanMonthWeek humanMonthWeek = new HumanMonthWeek(ower);
					humanMonthWeek.fromEntity(humanMonthWeekEntity);
					HumanMonthWeekList.add(humanMonthWeek);
					
					Date startTime = humanMonthWeek.getStartTime();
					long continueTime = humanMonthWeek.getContinueTime()*24*60*60*1000;
					long leftTime = ((startTime.getTime()+continueTime)-now.getTime())/1000;
					/**
					 * 然后看看是否 已经购买 如果购买，剩余时间设置为0
					 */
					if(leftTime <=0 || humanMonthWeek.getIsBuy() == 1){
						leftTime = 0;
					}
					
					if(humanMonthWeek.getIsBuy() == 1){
						continue;
					}
					//开始特惠礼包
					sendMessage(humanMonthWeek.getMwType(),0,leftTime,now,"开始");
				}
			}
		}
			
	}


	public List<HumanMonthWeek> getHumanMonthWeekList() {
			return HumanMonthWeekList;
		}
		public void setHumanMonthWeekList(List<HumanMonthWeek> humanMonthWeekList) {
			HumanMonthWeekList = humanMonthWeekList;
		}
	public void sendMessage(int mwType,int startOrEnd,long leftTime,Date now,String log){
		//直接发送开始消息
		GCMonthOrWeek gCMonthOrWeek = new GCMonthOrWeek();
		gCMonthOrWeek.setMwtype(mwType);//周
		gCMonthOrWeek.setStartOrEnd(startOrEnd);
		gCMonthOrWeek.setLeftTime(leftTime);
		ower.getPlayer().sendMessage(gCMonthOrWeek);
		logger.info("+++"+log+"  消息发送(周特惠礼包) 当当当++::"+now+" -- now::"+now);
	}

	public void startHumanMonthWeek(Date now,Human ower,int packDurationTime, int packType,HumanMonthWeek humanMonthWeekexist,boolean saveOrUpdate){
		HumanMonthWeek humanMonthWeek = createHumanMonthWeek(now,ower,packDurationTime,packType,humanMonthWeekexist,saveOrUpdate);
		humanMonthWeek.setModified();
		//添加到 特惠礼包
		HumanMonthWeekList.add(humanMonthWeek);
		//开始特惠礼包
		long leftTime = packDurationTime*24*60*60;
		sendMessage(packType,0,leftTime,now,"开始");
		/**
		 * 设置定时任务 （不能已登录就老 设置定时任务，如果已经设置好了就不要设置了）  -- 结束特惠礼包
		 */
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(Calendar.DAY_OF_MONTH, packDurationTime);//正式 用
//		calendar.add(Calendar.SECOND, 30*60);//测试 用
		//结束日期
		Globals.getScheduleService().scheduleOnce(new ScheduleRegularTime(humanMonthWeek,now,ower,packDurationTime), LLScheduleEnum.REGULARTIME,  calendar.getTime());
	}
	
	
	
	public HumanMonthWeek createHumanMonthWeek(Date now,Human ower,long packDurationTime,int packType,HumanMonthWeek humanMonthWeekexist,boolean saveOrUpdate){
		HumanMonthWeek humanMonthWeek = new HumanMonthWeek(ower);
		if(!saveOrUpdate){
			humanMonthWeek.setCreateTime(now);
			humanMonthWeek.setUpdateTime(now);
			humanMonthWeek.setDbId(Globals.getUUIDService().getNextUUID(now.getTime(), UUIDType.HUMANMONTHWEEK));
			humanMonthWeek.setUserId(ower.getPassportId());
		}else{
			humanMonthWeek=humanMonthWeekexist;
			humanMonthWeek.setUpdateTime(now);
		}
		humanMonthWeek.setIsBuy(0);//没有购买
		humanMonthWeek.setContinueTime(packDurationTime);
		humanMonthWeek.setMwType(packType);
		humanMonthWeek.setStartTime(TimeUtils.stringToTime(TimeUtils.formatYMDTime(now.getTime())+" 00:00:00"));//正式用
//		humanMonthWeek.setStartTime(now);//测试用
		humanMonthWeek.setInDb(saveOrUpdate);
		humanMonthWeek.active();
		return humanMonthWeek;
	}
	/**
	 * 移除特惠礼包
	 * @param humanMonthWeek 
	 */
	public void removeHumanMonthWeek(HumanMonthWeek humanMonthWeek){
		HumanMonthWeekList.remove(humanMonthWeek);
	}
	public Human getOwer() {
		return ower;
	}
	public void setOwer(Human ower) {
		this.ower = ower;
	}
	public Map<Integer,Long> getLeftTime() {
		//类型--剩余时间
		Map<Integer,Long> leftTimeMap = new HashMap<Integer,Long>();
		long now = Globals.getTimeService().now();
		if(HumanMonthWeekList!= null && HumanMonthWeekList.size()>0){
			for(HumanMonthWeek humanMonthWeek:HumanMonthWeekList){
				Date startTime = humanMonthWeek.getStartTime();
				long continueTime = humanMonthWeek.getContinueTime()*24*60*60*1000;
				long leftTime = (startTime.getTime()+continueTime)-now;
				if(leftTime <=0){
					leftTime = 0;
				}
				if(humanMonthWeek.getMwType()==0){
					leftTimeMap.put(0, leftTime/1000);
				}else if(humanMonthWeek.getMwType()==1){
					leftTimeMap.put(1, leftTime/1000);
				}
				
			}
		}
		return leftTimeMap;
		
	}

	public void changeIsBuy(int packtype,RechargeTemplate rechargeTemplate){
		//周 月
		if(HumanMonthWeekList!= null && HumanMonthWeekList.size()>0){
			for(HumanMonthWeek humanMonthWeek:HumanMonthWeekList){
				if(humanMonthWeek.getMwType() == packtype){
					humanMonthWeek.setIsBuy(1);
					humanMonthWeek.active();
					humanMonthWeek.setModified();
					ower.getHumanVipNewManager().addOnlyThePassPoint(rechargeTemplate.getVipPoint());
					sendMessage(packtype, 1,0l,new Date(), "购买一次结束");
					break;
				}
			}
			
		}
	}
	
}
