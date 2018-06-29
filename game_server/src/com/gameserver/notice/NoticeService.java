package com.gameserver.notice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

import com.common.AfterInitializeRequired;
import com.common.DestroyRequired;
import com.common.InitializeRequired;
import com.common.constants.Loggers;
import com.core.schedule.LLScheduleEnum;
import com.core.util.TimeUtils;
import com.db.model.NoticeEntity;
import com.gameserver.common.Globals;
import com.gameserver.notice.msg.GCNoticeInfoData;
import com.gameserver.notice.msg.GCNoticeInfoDataMulti;
import com.gameserver.notice.schedule.ScheduleDateNotice;
import com.gameserver.player.Player;


/**
 * 跑马灯服务
 * @author wayne
 *
 */
public class NoticeService  implements InitializeRequired,DestroyRequired,AfterInitializeRequired{
	
	private Logger logger = Loggers.noticeLogger;
	private List<Notice> noticeList = new ArrayList<Notice>();
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		loadNoticeFromDB();
		
	}
	
	/**
	 * 从数据库加载活动信息
	 */
	private void loadNoticeFromDB()
	{
		logger.info("加载所有公告");
		List<NoticeEntity> dbNoticeList=Globals.getDaoService().getNoticeDao().getAllNotice();
		if(dbNoticeList==null) return;
		long now=Globals.getTimeService().now();
		for(NoticeEntity noticeEntity:dbNoticeList) 
		{
			if(noticeEntity.getEndTime()<now)
			{
				continue;
			}
			Notice notice = new Notice();
			notice.fromEntity(noticeEntity);
			noticeList.add(notice);
		}
		checkNotices();
	}
	/**
	 * 这个是一分钟遍历一次，看看有没有需要 跑的跑马灯
	 */
	//遍历所有公告
	public void checkNotices(){
		logger.info("遍历所有公告");
		long now=Globals.getTimeService().now();
		Iterator<Notice> iter = noticeList.iterator();
		while(iter.hasNext())
		{
			logger.debug("跑马灯[] --- 探测中");
			Notice notice = iter.next();
			
			//还没开始
			if(notice.getStartTime()>now)
				continue;
			

			long actualStartTime = TimeUtils.getBeginOfDay(now) + notice.getDailyStartTime();
			long actualEndTime = TimeUtils.getBeginOfDay(now) + notice.getDailyEndTime();
			
			//过期删除
			if(notice.getEndTime()<now)
			{
				logger.info("公告["+notice.getDbId()+"]过期了");
				iter.remove();
				continue;
			}
			
			//播放完了 今天
			if(actualEndTime<now){
				logger.debug("公告["+notice.getDbId()+"]今天放完了");
				continue;
			}
			
			//还没到时间
			if(actualStartTime>now){
				logger.debug("公告["+notice.getDbId()+"]还没到时间播放了");
				continue;
			}
			
			logger.debug("跑马灯["+notice.getContent()+"] --- 探测中");
			//判断是否超过间隔时间
			long tempInterval = now - notice.getLastNoticeTime();
			if(tempInterval < notice.getIntervalTime()* TimeUtils.MIN){
				logger.debug("公告["+notice.getDbId()+"]还没超过间隔时间了");
				continue;
			}
			notice.setLastNoticeTime(now);
			broadcast(notice);
			
		}
		
	}
	
	/**
	 * 广播
	 * @param notice
	 */
	private void broadcast(Notice notice)
	{
		GCNoticeInfoData gcNoticeInfoData = new GCNoticeInfoData();
		gcNoticeInfoData.setContent(notice.getContent());
		for(Player player:Globals.getOnlinePlayerService().getOnlinePlayersMap().values())
		{
			player.sendMessage(gcNoticeInfoData);
		}
	}
	
	/**
	 * 发送文字性的东西
	 * @param content
	 */
	public void broadcast(String content){
		GCNoticeInfoData gcNoticeInfoData = new GCNoticeInfoData();
		gcNoticeInfoData.setContent(content);
		for(Player player:Globals.getOnlinePlayerService().getOnlinePlayersMap().values())
		{
			player.sendMessage(gcNoticeInfoData);
		}
	}
	
	/**
	 * 公告 by id
	 * @param nId
	 * @return
	 */
	public Notice getNoticeById(long nId){
		for(Notice notice : this.noticeList){
			if(notice.getDbId() == nId)
				return notice;
		}
		return null;
	}

	/**
	 * 移除公告
	 */
	public void updateNotice(Notice notice){
		Notice tempNotice = getNoticeById(notice.getDbId());
		if(tempNotice != null){
			logger.info("更新公告["+notice.getDbId()+"]");
			noticeList.remove(tempNotice);
			noticeList.add(notice);
		}
		else{
			logger.info("添加公告["+notice.getDbId()+"]");
			noticeList.add(notice);
		}
		
	}
	
	//删除公告
	public void removeNotice(Notice notice){
		Notice tempNotice = getNoticeById(notice.getDbId());
		if(tempNotice != null){
			logger.info("删除公告["+notice.getDbId()+"]");
			noticeList.remove(tempNotice);
		}
	}
	/**
	 * 广播消息
	 */
	public void broadcastNoticeMulti(int langId,String ...params){
		GCNoticeInfoDataMulti gcNoticeInfoDataMulti =new GCNoticeInfoDataMulti();
		gcNoticeInfoDataMulti.setLangId(langId);
		gcNoticeInfoDataMulti.setParamsList(params);
		for(Player player:Globals.getOnlinePlayerService().getOnlinePlayersMap().values())
		{
			player.sendMessage(gcNoticeInfoDataMulti);
		}
	}
	
	
	/**
	 * 设置跑马灯 的业务（这个业务 不用了，前边已经有了，）
	 * @param rNotice
	 */

	public void setNoticeRun(long id) {
		Notice rNotice = Globals.getNoticeService().getNoticeById(id);
		if(rNotice == null){
			return;
		}
		Calendar calendar = Calendar.getInstance();
		//
		long startTime = rNotice.getStartTime();
		//几点
		long dailyStartTime = rNotice.getDailyStartTime();
		/**
		 * 第一次开始时间
		 */
		calendar.setTimeInMillis(startTime+dailyStartTime);
		Globals.getScheduleService().scheduleOnce(new ScheduleDateNotice(id,calendar), LLScheduleEnum.SCHEDULE_NOTICE, calendar.getTime());
		
	}
}
