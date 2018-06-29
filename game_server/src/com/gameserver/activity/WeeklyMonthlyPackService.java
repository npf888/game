package com.gameserver.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.common.InitializeRequired;
import com.gameserver.activity.template.WeeklyMonthlyPackTemplate;
import com.gameserver.common.Globals;

public class WeeklyMonthlyPackService implements InitializeRequired{

	List<WeeklyMonthlyPackTemplate> weekPackList = new ArrayList<WeeklyMonthlyPackTemplate>();
	List<WeeklyMonthlyPackTemplate> monthPackList = new ArrayList<WeeklyMonthlyPackTemplate>();
	Map<Long,WeeklyMonthlyPackTemplate> vipToMonthPackMap = new HashMap<Long,WeeklyMonthlyPackTemplate>();
	Map<Long,WeeklyMonthlyPackTemplate> vipToweekPackMap = new HashMap<Long,WeeklyMonthlyPackTemplate>();
	@Override
	public void init() {
		Map<Integer,WeeklyMonthlyPackTemplate> weeklyMonthlyPackTemplateMap = Globals.getTemplateService().getAll(WeeklyMonthlyPackTemplate.class);
		for(WeeklyMonthlyPackTemplate wm:weeklyMonthlyPackTemplateMap.values()){
			//周礼包
			if(wm.getPackType() == 0){
				weekPackList.add(wm);
			}else if(wm.getPackType() == 1){
				monthPackList.add(wm);
			}
		}
	}
	
	
	
	public List<WeeklyMonthlyPackTemplate> getWeekByVip(int vip){
		List<WeeklyMonthlyPackTemplate> WeeklyMonthlyPackTemplateList = new ArrayList<WeeklyMonthlyPackTemplate>();
		for(WeeklyMonthlyPackTemplate w:weekPackList){
			if(w.getVipLevel() == vip){
				WeeklyMonthlyPackTemplateList.add(w);
				break;
			}
		}
		for(WeeklyMonthlyPackTemplate w:monthPackList){
			if(w.getVipLevel() == vip){
				WeeklyMonthlyPackTemplateList.add(w);
			}
		}
		return WeeklyMonthlyPackTemplateList;
	}
	public WeeklyMonthlyPackTemplate getMonthByVip(int vip){
		for(WeeklyMonthlyPackTemplate m:monthPackList){
			if(m.getVipLevel() == vip){
				return m;
			}
		}
		return null;
	}
	
	
	public List<WeeklyMonthlyPackTemplate> getWeekPackList() {
		return weekPackList;
	}
	public void setWeekPackList(List<WeeklyMonthlyPackTemplate> weekPackList) {
		this.weekPackList = weekPackList;
	}
	public List<WeeklyMonthlyPackTemplate> getMonthPackList() {
		return monthPackList;
	}
	public void setMonthPackList(List<WeeklyMonthlyPackTemplate> monthPackList) {
		this.monthPackList = monthPackList;
	}
	
	

}
