package com.gameserver.bazoo.service.TimeTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.gameserver.bazoo.taskSchedule.ShowShandTask;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 无双吹牛的定时任务
 * @author JavaServer
 *
 */
public class BazooScheduleService {
	private static final Logger logger = Loggers.BAZOO;
	/** 定时任务List */
	List<BazooScheduleImpl> bzaooScheduleList = new ArrayList<BazooScheduleImpl>();
	
	
	
	/**
	 * 添加定时任务
	 */
	public void addBazooSchedule(int millisecond,String rNubmer,BazooTask bazooTask){
		BazooScheduleImpl BazooSchedule = new BazooScheduleImpl();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.SECOND, millisecond);
		BazooSchedule.setStop(false);
		BazooSchedule.setTime(cal.getTimeInMillis());
		BazooSchedule.setRoomNubmer(rNubmer);
		BazooSchedule.setBazooTask(bazooTask);
		bzaooScheduleList.add(BazooSchedule);
	}
	
	
	/**
	 * 停止 已经添加的定时任务
	 */
	public void stopBazooSchedule(String rNubmer,Player player){
		
		for(BazooScheduleImpl BazooSchedule:bzaooScheduleList){
			String roomNumber = BazooSchedule.getRoomNubmer();
			if(roomNumber.equals(rNubmer)){
				BazooTask BazooTask = BazooSchedule.getBazooTask();
				if(BazooTask instanceof ShowShandTask){
					ShowShandTask ShowShandTask = (ShowShandTask)BazooSchedule.getBazooTask();
					if(player.getPassportId() == ShowShandTask.getLastPlayer().getPassportId()){
						BazooSchedule.setStop(true);
						logger.info("[无双吹牛]---[停止等待1]---[roomNumber::"+roomNumber+"]");
						break;
					}
				}else{
					BazooSchedule.setStop(true);
					logger.info("[无双吹牛]---[停止等待2]---[roomNumber::"+roomNumber+"]");
					break;
				}
			}
		}
	}
	/**
	 * 强制停止 某个房间的 所有等待业务
	 * @param rNubmer
	 * @param player
	 */
	public void forceStopBazooSchedule(String rNubmer){
		
		for(BazooScheduleImpl BazooSchedule:bzaooScheduleList){
			String roomNumber = BazooSchedule.getRoomNubmer();
			if(roomNumber.equals(rNubmer)){
				BazooSchedule.setStop(true);
				logger.info("[无双吹牛]---[停止等待2]---[roomNumber::"+roomNumber+"]");
				break;
			}
		}
	}
	
	
	
	/**
	 * 通过一个跟线程 一直循环 跑 ，看看有没有摇定时的任务
	 */
	
	public void runRunRun(){
		while(true){
			try{
				Thread.sleep(10);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			for(int i=0;i<bzaooScheduleList.size();i++){
				BazooScheduleImpl BazooSchedule = bzaooScheduleList.get(i);
				//如果用户停止了这个定时，就会被移除
				if(BazooSchedule.isStop()){
					logger.info("=================================移除当前 被打断的 前的定时任务1================================="+BazooSchedule.getRoomNubmer());
					bzaooScheduleList.remove(BazooSchedule);
					break;
				}
			}
			
		    for(int i=0;i<bzaooScheduleList.size();i++){
		    	BazooScheduleImpl BazooSchedule = bzaooScheduleList.get(i);
		    	if(BazooSchedule.isStop()){
					logger.info("=================================移除当前 被打断的 前的定时任务2================================="+BazooSchedule.getRoomNubmer());
					bzaooScheduleList.remove(BazooSchedule);
					break;
				}
		    	
				//如果时间到了 就会被执行 ，执行完了 就会被移除
				long now = Globals.getTimeService().now();
				long cha = now-BazooSchedule.getTime();
				if(now>=BazooSchedule.getTime()){
					logger.info("============================================执行定时任务的开始============================================"+cha+"===被设置的时间:"+BazooSchedule.getTime());
					BazooSchedule.getBazooTask().execute();
					BazooSchedule.setStop(true);
					bzaooScheduleList.remove(BazooSchedule);
					break;
				}
		    }
		}
		
	}
	
	
	
	
	public  List<BazooScheduleImpl>  getBzaooScheduleList(){
		return bzaooScheduleList;
	}
}
