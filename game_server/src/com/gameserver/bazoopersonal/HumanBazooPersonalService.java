package com.gameserver.bazoopersonal;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;

import com.common.InitializeRequired;
import com.common.LogReasons;
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.core.util.TimeUtils;
import com.db.model.HumanBazooPersonalEntity;
import com.gameserver.bazoo.insideData.PlayerInfo;
import com.gameserver.bazoo.service.cow.CowUserInfo;
import com.gameserver.bazoo.service.room.RoomNumber;
import com.gameserver.bazoopersonal.data.BazooPersonalInfo;
import com.gameserver.bazoorank.schedule.ScheduleCleanBazooRank;
import com.gameserver.bazoowins.HumanBazooWins;
import com.gameserver.common.Globals;
import com.gameserver.human.Human;
import com.gameserver.player.Player;
import com.gameserver.player.enums.PlayerRoleEnum;

public class HumanBazooPersonalService implements InitializeRequired {
	private Logger logger = Loggers.BAZOO;

	@Override
	public void init() {
		logger.info("[无双吹牛-个人信息]---[定时清理 设置 开始]--------------------------------");
		
		//设置定时器
		Date now = new Date();
		
		Date date0 = getDay();
		long dayDelay = date0.getTime()-now.getTime();
		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleCleanBazooRank(1), LLScheduleEnum.BAZOO_RANK, dayDelay, TimeUtils.DAY);
		
		
		//每周
		Date date = getWeekMonday();
		long weekDelay = date.getTime()-now.getTime();
		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleCleanBazooRank(2), LLScheduleEnum.BAZOO_RANK, weekDelay, 7*TimeUtils.DAY);
			
		
		//每月
		Date date1 = getMonthFirstday();
		long monthDelay = date1.getTime()-now.getTime();
		Globals.getScheduleService().scheduleWithFixedDelay(new ScheduleCleanBazooRank(3), LLScheduleEnum.BAZOO_RANK, monthDelay, 30*TimeUtils.DAY);
	}
	//每天
	public  Date getDay(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return TimeUtils.getYMDHMSDate(TimeUtils.formatYMDTime(cal.getTime().getTime())+" 00:00:00");
	}
	public  Date getWeekMonday(){
		//获取本月的 第一天
		Calendar cal=new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		
		//然后再加 7天
		cal.add(Calendar.DAY_OF_MONTH, 7);
		Date first=cal.getTime();
		return TimeUtils.getYMDHMSDate(TimeUtils.formatYMDTime(first.getTime())+" 00:00:00");
	}
	public Date getMonthFirstday(){
		//获取本月的 第一天
		Calendar cal = Calendar.getInstance();    
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	    
	    
		//然后再加 30天
		cal.add(Calendar.DAY_OF_MONTH, 30);
		Date first=cal.getTime();
		return TimeUtils.getYMDHMSDate(TimeUtils.formatYMDTime(first.getTime())+" 00:00:00");
	}
	
	/**
	 * 清理个人 信息 的数据
	 * @param dateType
	 */
	public void cleanBazooPersonal(int dateType) {
		List<HumanBazooPersonalEntity> HumanBazooPersonalEntityList = Globals.getDaoService().getHumanBazooPersonalDao().getAll(HumanBazooPersonalEntity.class);
		if(HumanBazooPersonalEntityList == null){
			return;
		}
		if(dateType == 1){
			for(HumanBazooPersonalEntity entity :HumanBazooPersonalEntityList){
				entity.setDayProfit(0);
				Globals.getDaoService().getHumanBazooPersonalDao().saveOrUpdate(entity);
			}
			
		}else if(dateType == 2){
			for(HumanBazooPersonalEntity entity :HumanBazooPersonalEntityList){
				entity.setWeekProfit(0);
				Globals.getDaoService().getHumanBazooPersonalDao().saveOrUpdate(entity);
			}
			
		}else if(dateType == 3){
			for(HumanBazooPersonalEntity entity :HumanBazooPersonalEntityList){
				entity.setMonthProfit(0);
				Globals.getDaoService().getHumanBazooPersonalDao().saveOrUpdate(entity);
			}
		}
		
	} 
	
	
	public void addTimes(Player player){
		
		Human human = player.getHuman();
		int modeType = human.getBazooRoomNumber().getModeType();
		List<HumanBazooPersonal> HumanBazooPersonalList = human.getHumanBazooPersonalManager().getHumanBazooPersonalList();
		for(HumanBazooPersonal HumanBazooPersonal:HumanBazooPersonalList){
			
			if(HumanBazooPersonal.getModeType() == modeType){
				HumanBazooPersonal.setNumberOfGame(HumanBazooPersonal.getNumberOfGame()+1);//局数
			}
		}
	}
	/**
	 * 玩游戏  增加 金币
	 */
	public void addGold(Player player,long gold,PlayerInfo info){
		Human human = player.getHuman();
		int modeType = human.getBazooRoomNumber().getModeType();
		HumanBazooWins maxHumanBazooWins = human.getHumanBazooWinsManager().getMaxHumanBazooWins();
		List<HumanBazooPersonal> HumanBazooPersonalList = human.getHumanBazooPersonalManager().getHumanBazooPersonalList();
		for(HumanBazooPersonal HumanBazooPersonal:HumanBazooPersonalList){
			
			if(modeType == HumanBazooPersonal.getModeType()){
				
				
				int winOrLost = 0;//默认是输了
				HumanBazooPersonal.setNumberOfGame(HumanBazooPersonal.getNumberOfGame()+1);//局数
				if(gold > 0){//大于零 说明赢了
					winOrLost=1;
					HumanBazooPersonal.setWinTimes(HumanBazooPersonal.getWinTimes()+1);//赢的次数
				}
				
				
				
				if(HumanBazooPersonal.getModeType() == RoomNumber.MODE_TYPE_CLASSICAL){
					
					if(gold > HumanBazooPersonal.getSingleTopGold()){
						HumanBazooPersonal.setSingleTopGold(HumanBazooPersonal.getSingleTopGold());//单局 最高
					}
					
					Double wintimes = Integer.valueOf(HumanBazooPersonal.getWinTimes()).doubleValue();
					Double numberOfGame = Integer.valueOf(HumanBazooPersonal.getNumberOfGame()).doubleValue();
					double rate = (wintimes/numberOfGame)*100;
					HumanBazooPersonal.setRateOfWinning(Long.valueOf(Math.round(rate)).intValue());//胜率(取余数)
					
					if(maxHumanBazooWins == null){
						HumanBazooPersonal.setAWinningStreak(0);//连胜
					}else{
						HumanBazooPersonal.setAWinningStreak(maxHumanBazooWins.getWinTimes());//连胜
					}
					
				}else if(HumanBazooPersonal.getModeType() ==RoomNumber.MODE_TYPE_COW){
					CowUserInfo cowUserInfo = human.getBazooRoomEveryUserInfo().getCowUserInfo();
					int carNum = cowUserInfo.getCowNum();
					int aCarNum = Integer.valueOf(HumanBazooPersonal.getBigPatterns().split("-")[1]);
					if(carNum > aCarNum){
						List<Integer> diceValues = human.getBazooRoomEveryUserInfo().getDiceValues();
						String diceValueStr = "";
						for(int i=0;i<diceValues.size();i++){
							diceValueStr+=diceValues.get(i)+",";
						}
						diceValueStr = diceValueStr.substring(0, diceValueStr.lastIndexOf(","))+"-"+modeType;
						HumanBazooPersonal.setBigPatterns(diceValueStr);//最大牌型
					}
					
					int banker = cowUserInfo.getBanker();
					if(banker == CowUserInfo.isBanker){//如果是庄家 才会有通杀
						if(cowUserInfo.getWinPlayers().size()==0){
							HumanBazooPersonal.setPassToKill(HumanBazooPersonal.getPassToKill()+1);//通杀
						}
					}
					
					
				}else if(HumanBazooPersonal.getModeType() == RoomNumber.MODE_TYPE_SHOWHAND){
					int cardNum = human.getBazooRoomEveryUserInfo().getShowHandUserInfo().getCard().getCardNum();
					if(cardNum == 1){//是豹子就加一
						HumanBazooPersonal.setPantherNumber(HumanBazooPersonal.getPantherNumber());//豹子
					}
				}else if(HumanBazooPersonal.getModeType() == RoomNumber.MODE_TYPE_BLACK_WHITE){
					int killNum = human.getBazooRoomEveryUserInfo().getBlackWhiteInfo().getKillNum();
					if(killNum == 3){
						HumanBazooPersonal.setThreeKill(HumanBazooPersonal.getThreeKill()+1);
					}else if(killNum == 4){
						HumanBazooPersonal.setFourKill(HumanBazooPersonal.getFourKill()+1);
					}else if(killNum == 5){
						HumanBazooPersonal.setFiveKill(HumanBazooPersonal.getFiveKill()+1);
						
					}
				}
				
				HumanBazooPersonal.setDayProfit(HumanBazooPersonal.getDayProfit()+gold);
				HumanBazooPersonal.setWeekProfit(HumanBazooPersonal.getWeekProfit()+gold);
				HumanBazooPersonal.setMonthProfit(HumanBazooPersonal.getMonthProfit()+gold);
				HumanBazooPersonal.setModified();
				
				//发送日志
				sendStaticLog(player,modeType,winOrLost);
			}
			
			
		}
	}
	
	
	private void sendStaticLog(Player player,int modeType,int winOrLost){
		int isRobot = 0;//默认不是机器人
		if(player.getPlayerRoleEnum() == PlayerRoleEnum.ROBOT){//机器人
			isRobot=1;
		}
		
		Globals.getLogService().sendDiceStatisticsWinLostLog(player.getHuman(), 
				LogReasons.DiceStatisticsWinLostLogReason.BAZOO_WIN_LOST_GOLD, null, 0, player.getHuman().getBazooRoomNumber().toString(),
				isRobot, modeType, winOrLost);
	}
	
	/**
	 * 查询个人 三种模式的信息
	 */
	public BazooPersonalInfo[] getBazooPersonBy(Player player) {
		List<HumanBazooPersonal> humanBazooPersonalList = player.getHuman().getHumanBazooPersonalManager().getHumanBazooPersonalList();
		BazooPersonalInfo[] bazooPersonalInfoArr = new BazooPersonalInfo[humanBazooPersonalList.size()];
		for(int i=0;i<humanBazooPersonalList.size();i++){
			HumanBazooPersonal humanBazooPersonal = humanBazooPersonalList.get(i);
			BazooPersonalInfo bazooPersonalInfo = new BazooPersonalInfo();
			bazooPersonalInfo.setModeType(humanBazooPersonal.getModeType());
			
			bazooPersonalInfo.setNumberOfGame(humanBazooPersonal.getNumberOfGame());
			bazooPersonalInfo.setAWinningStreak(humanBazooPersonal.getAWinningStreak());
			
			bazooPersonalInfo.setBigPatterns(humanBazooPersonal.getBigPatterns().replaceAll(",", "").split("-")[0]);
			bazooPersonalInfo.setPantherNumber(humanBazooPersonal.getPantherNumber());
			bazooPersonalInfo.setPassToKill(humanBazooPersonal.getPassToKill());
			bazooPersonalInfo.setRateOfWinning(humanBazooPersonal.getRateOfWinning());
			bazooPersonalInfo.setSingleTopGold(humanBazooPersonal.getSingleTopGold());
			
			bazooPersonalInfo.setDayProfit(humanBazooPersonal.getDayProfit()>0?humanBazooPersonal.getDayProfit():0);
			bazooPersonalInfo.setWeekProfit(humanBazooPersonal.getWeekProfit()>0?humanBazooPersonal.getWeekProfit():0);
			bazooPersonalInfo.setMonthProfit(humanBazooPersonal.getMonthProfit()>0?humanBazooPersonal.getMonthProfit():0);
			
			bazooPersonalInfo.setThreeKill(humanBazooPersonal.getThreeKill());
			bazooPersonalInfo.setFourKill(humanBazooPersonal.getFourKill());
			bazooPersonalInfo.setFiveKill(humanBazooPersonal.getFiveKill());
			bazooPersonalInfoArr[i]=bazooPersonalInfo;
		}
		
		return bazooPersonalInfoArr;
		
	}

	
	public BazooPersonalInfo[] getss(long passportId){
		List<HumanBazooPersonalEntity> humanBazooPersonalEntityList = Globals.getDaoService().getHumanBazooPersonalDao().getBazooPersonalByPassportId(passportId);
		BazooPersonalInfo[] bazooPersonalInfoArr = new BazooPersonalInfo[humanBazooPersonalEntityList.size()];
		if(humanBazooPersonalEntityList != null && humanBazooPersonalEntityList.size() > 0){
			for(int i=0;i<humanBazooPersonalEntityList.size();i++){
				HumanBazooPersonalEntity entity = humanBazooPersonalEntityList.get(i);
				BazooPersonalInfo bazooPersonalInfo = new BazooPersonalInfo();
				bazooPersonalInfo.setModeType(entity.getModeType());
				
				bazooPersonalInfo.setNumberOfGame(entity.getNumberOfGame());
				bazooPersonalInfo.setAWinningStreak(entity.getaWinningStreak());
				bazooPersonalInfo.setBigPatterns(entity.getBigPatterns().replaceAll(",", "").split("-")[0]);
				bazooPersonalInfo.setPantherNumber(entity.getPantherNumber());
				bazooPersonalInfo.setPassToKill(entity.getPassToKill());
				bazooPersonalInfo.setRateOfWinning(entity.getRateOfWinning());
				bazooPersonalInfo.setSingleTopGold(entity.getSingleTopGold());
				
				bazooPersonalInfo.setDayProfit(entity.getDayProfit()>0?entity.getDayProfit():0);
				bazooPersonalInfo.setWeekProfit(entity.getWeekProfit()>0?entity.getWeekProfit():0);
				bazooPersonalInfo.setMonthProfit(entity.getMonthProfit()>0?entity.getMonthProfit():0);
				
				bazooPersonalInfo.setThreeKill(entity.getThreeKill());
				bazooPersonalInfo.setFourKill(entity.getFourKill());
				bazooPersonalInfo.setFiveKill(entity.getFiveKill());
				
				bazooPersonalInfoArr[i]=bazooPersonalInfo;
			
			}
		}
		return bazooPersonalInfoArr;
	}
	
	
}
