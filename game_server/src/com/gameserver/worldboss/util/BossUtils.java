package com.gameserver.worldboss.util;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.worldboss.msg.GCBossStartEndInfo;
import com.gameserver.worldboss.pojo.Boss;
import com.gameserver.worldboss.template.BossPropertyTemplate;
import com.gameserver.worldboss.template.BossTemplate;

/**
 * 
 * @author JavaServer
 *
 */
public class BossUtils {

	public  GCBossStartEndInfo getGCBossStartEndInfo(Boss boss,Boss lastBoss,
			BossTemplate bossTemplate,
			List<BossPropertyTemplate> bossPropertyTemplateList,
			List<BossTemplate> bossTemplateList,
			int status){
		GCBossStartEndInfo gCBossStartEndInfo = new GCBossStartEndInfo();
		gCBossStartEndInfo.setStartEndRuning(status);
		if(status == 1){//刚结束 或者被击杀
			gCBossStartEndInfo.setCurBossLeftTime(0);
			if(boss != null){
				gCBossStartEndInfo.setBeKilled(boss.getBeKilled());
			}else if(lastBoss != null){
				gCBossStartEndInfo.setBeKilled(lastBoss.getBeKilled());
			}
		}else if(status == 0){//刚开始
			gCBossStartEndInfo.setBeKilled(0);
			gCBossStartEndInfo.setCurBossLeftTime(bossTemplate.getContinuetime()*60);
		}else if(status == 2){//进行中
			long end = 0;
			if(boss != null){
				gCBossStartEndInfo.setBeKilled(boss.getBeKilled());
				end = boss.getEndTime().getTime();
			}else if(lastBoss != null){
				end = lastBoss.getEndTime().getTime();
				gCBossStartEndInfo.setBeKilled(lastBoss.getBeKilled());
			}
			
			Date now = new Date();
			long lefTime = (end-now.getTime())/1000;
			gCBossStartEndInfo.setCurBossLeftTime(lefTime);
		}
		for(BossPropertyTemplate BossPropertyTemplate :bossPropertyTemplateList){
			if(boss != null && BossPropertyTemplate.getId() == boss.getNextBossId()){
				gCBossStartEndInfo.setBossType(BossPropertyTemplate.getType());
				break;
			}else if(lastBoss != null && BossPropertyTemplate.getId() == lastBoss.getNextBossId()){
				gCBossStartEndInfo.setBossType(BossPropertyTemplate.getType());
				break;
			}
		}
		gCBossStartEndInfo.setNextBossLeftTime(getNextStartLeftTime(bossTemplateList));
		return gCBossStartEndInfo;
	}
	
	public  long getNextStartLeftTime(List<BossTemplate> bossTemplateList){
		int index=0;
		for(int i=0;i<bossTemplateList.size();i++){
				long nowLong = Globals.getTimeService().now();
				String now = TimeUtils.formatYMDTime(nowLong);
				long bossLong = TimeUtils.stringToTime(now+" "+bossTemplateList.get(i).getStarttime()+":00").getTime();
				if(nowLong < bossLong){
					index=i;
					break;
				}
		}
		BossTemplate nextBossTemplate = bossTemplateList.get(index);
		String startTime = nextBossTemplate.getStarttime();
		String[] hourMin = startTime.split(":");
		String hour = hourMin[0];
		String min = hourMin[1];
		
		Date now = new Date();
		long nowLong = now.getTime();
		//设置 提醒个定时器
		Calendar calendarRemind = Calendar.getInstance();
		calendarRemind.setTime(new Date());
		calendarRemind.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
		calendarRemind.set(Calendar.MINUTE,Integer.valueOf(min).intValue());
		if(calendarRemind.getTime().getTime()<nowLong){
			calendarRemind.add(Calendar.DAY_OF_MONTH, 1);
		}
		long nextStartLeftTime = (calendarRemind.getTime().getTime()-nowLong)/1000;
		return nextStartLeftTime;
	}
	
	
	
	
	
}
