package com.gameserver.worldboss.util;

import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;
import com.gameserver.worldboss.data.RefreshBossData;
import com.gameserver.worldboss.msg.GCRefreshBossInfo;
import com.gameserver.worldboss.pojo.Boss;
import com.gameserver.worldboss.schedule.ScheduleBossAirRollEnd;

public class BossBloodReturningUtils {
	private Logger logger = Loggers.WORLDBOSS;
	/**
	 *  1.（airtime）秒内，所有wild连线伤害降低百分之（）
	 *	2.恢复自身最大血量的百分之（）
	 *	3.（airtime）秒内，收到伤害降低百分之（）
	 *	4.（airtime）秒内，有（）概率免疫伤害。
	 *
	 *  回血
	 * @param calendarRollSkill 
	 */
	public  void  bossBloodReturning(Boss boss,Calendar calendarRollSkill,Map<Long,Integer> openPanel){
		//技能开始后的持续时间 秒
		int airtime = boss.getAirtime();
		//airtime 这个等于 0 就是代表一次
		if(airtime == 0){
			// 恢复最大血量百分数（除以100） 
			int recover1 = boss.getRecover1();
			//基础血量
			long blood = boss.getBlood();
			//增长的血量
			long increaseBlood = boss.getIncreaseBlood();
			//将要回复的血量 
			long recoverBlood = blood*recover1/100;
			//设置增加的血量
			//设置变化中的血量（当前boss的血量）
			if(boss.getChangingBlood()+recoverBlood>boss.getBlood()){
				logger.info("increaseBlood="+increaseBlood+" --- boss.getBlood()="+boss.getBlood()+" ---boss.getChangingBlood()="+boss.getChangingBlood());
				boss.setIncreaseBlood(increaseBlood+(boss.getBlood()-boss.getChangingBlood()));
				boss.setChangingBlood(boss.getBlood());
			}else{
				logger.info("increaseBlood="+increaseBlood+" --- recoverBlood="+recoverBlood);
				boss.setIncreaseBlood(increaseBlood+recoverBlood);
				boss.setChangingBlood(boss.getChangingBlood()+recoverBlood);
			}
			boss.setModified();
		}else{
			/**
			 * 这三种 skill 持续中，技能 一开始 就设为true-- 状态 就是 持续中 ...
			 */
			int type = boss.getType();
			long now = Globals.getTimeService().now();
			boss.setSkillLeftTime(now);
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(calendarRollSkill.getTime());
			endCalendar.add(Calendar.SECOND, airtime);
			if(type == 1){
				//wild中奖连线伤害降低百分数。（除以100）
				boss.setWildreduceOnOff(true);
			}else if(type == 3){
				// 伤害减免百分数
				boss.setDamagereduceOnOff(true);
			}else if(type == 4){
				//免伤概率，百分数（除以100）
				boss.setAvoidOnOff(true);
			}
			//持续 这么长airtime 时间之后就结束
			Globals.getScheduleService().scheduleOnce(new ScheduleBossAirRollEnd(), LLScheduleEnum.WORLD_BOSS_AIR_ROLL_END,endCalendar.getTime());
		}
		
		refreshBoss(boss,airtime,openPanel);
	}
	
	
	/**
	 * 刷新 boss
	 */
	public  void refreshBoss(Boss boss,Integer skillTIme,Map<Long,Integer> openPanel){
		for(Entry<Long,Integer> entry:openPanel.entrySet()){
			long userId = entry.getKey();
			long isOpenOrNot = entry.getValue();
			//0是打开
			if(isOpenOrNot == 0){
				Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(userId);
				if(player == null){
					continue;
				}
				GCRefreshBossInfo gCRefreshBossInfo = refreshBoss(boss,player,skillTIme);
				player.sendMessage(gCRefreshBossInfo);
			}
		}
	}
	
	public  GCRefreshBossInfo refreshBoss(Boss boss,Player player,Integer skillTime){

		GCRefreshBossInfo gCRefreshBossInfo = new GCRefreshBossInfo();
		RefreshBossData[] refreshBossDataArr = new RefreshBossData[1];
		RefreshBossData refreshBossData = new RefreshBossData();
		refreshBossData.setCurBlood(boss.getChangingBlood());
		if(skillTime != null){
			refreshBossData.setSkillTime(skillTime);
		}
		refreshBossDataArr[0]=refreshBossData;
		gCRefreshBossInfo.setRefreshBossData(refreshBossDataArr);
		
		return gCRefreshBossInfo;
	}
	
	
	/**
	 * 结束回血
	 */
	public void bossBloodReturningWithAirtimeEND(Boss boss){
		if(boss ==null){
			return;
		}
		boss.setSkillLeftTime(0);
		int type = boss.getType();
		if(type == 1){
			boss.setWildreduceOnOff(false);
		}else if(type == 3){
			boss.setDamagereduceOnOff(false);
		}else if(type == 4){
			boss.setAvoidOnOff(false);
		}
	}
	
}
