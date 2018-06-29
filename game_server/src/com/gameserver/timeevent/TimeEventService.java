package com.gameserver.timeevent;

import com.core.template.TemplateService;
import com.core.util.TimeUtils;
import com.gameserver.timeevent.template.RefreshTimeDayTemplate;

/**
 * 定时器获取数据服务对象
 * @author Thinker
 *
 */
public class TimeEventService {

	/** 副本模板管理器,负责从模板中取得副本信息 */
	private TemplateService templService;
	
	
	
	public TimeEventService(TemplateService templService){
		this.templService=templService;
	}
	
	
	/**
	 * 根据Id获取副本模板
	 * @return
	 */
	public RefreshTimeDayTemplate getRefreshTimeDayTemplateById(int refreshTimeDayTemplateId){
		RefreshTimeDayTemplate refreshTimeDay=this.templService.get(refreshTimeDayTemplateId, RefreshTimeDayTemplate.class);
		return refreshTimeDay;
	}
	
	/**
	 * 根据Id获取副本模板中的指定时间
	 * @return
	 */
	public long getRefreshTimeDayById(long date,int refreshTimeDayTemplateId){
		RefreshTimeDayTemplate refreshTimeDay=getRefreshTimeDayTemplateById(refreshTimeDayTemplateId);
		return TimeUtils.getTodaySomeTime(date,refreshTimeDay.getRefreshTimeStr());
	}
	

}
