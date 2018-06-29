package com.gameserver.common.schedule;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.gameserver.bazoo.msg.GCBazooHeartBeat;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 无双吹牛房间的心跳
 * 每隔5秒 或者10秒发送一次消息
 * 如果用户两次没有回复 就 剔除房间 但不删除用户
 * @author JavaServer
 *
 */
public class RoomHeartBeatServer implements LLISchedule{
	
	private Logger logger = Loggers.scheduleLogger;
	//这个是无双吹牛  用户多长时间没有反应 就被 在房间里删除（其实是改变状态） 
	private static final int BAZOO_TIME = 3;
	@Override
	public void execute() {
//		String time=TimeUtils.formatYMDHMSTime(new Date().getTime());
//		logger.debug("[无双吹牛]---[心跳]---[心跳时间::"+time+"]");
		//监测 用户是否在线 如果不在线  就之改下 房间里用户的状态就可以了
		checkPlayers();
	}
	
	
	
	public void checkPlayers(){
		//判断是否开启了超时踢出玩家功能
		if(!Globals.getServerConfig().isLastNetOnOff()) return;
		
		//这个循环单独用于无双吹牛
		Collection<Long> onlinePlayerUUIDs = Globals.getOnlinePlayerService().getAllOnlinePlayerRoleUUIDs();
		for(Long roleId:onlinePlayerUUIDs){
			//这个循环单独用于无双吹牛
			Player player=Globals.getOnlinePlayerService().getPlayer(roleId);
//			player.sendMessage(new GCBazooHeartBeat()); //改用cgPing  前端主动发的
			//有几次心跳 没有回复消息了
//			int outTime=player.getOutTimes();
			long lastNetTime = player.getLastNetTime();
			long now = Globals.getTimeService().now();
			long cha = (now-lastNetTime)/1000;
			//如果大于六秒 说明用户下线了
			if(cha>=15){
				//退出房间
				/*logger.info("[无双吹牛]---[心跳-退出房间]---[当前用户::"+player.getPassportId()+"]");*/
				Globals.getBazooPubService().outBazooRoom(player);
			}
//			player.setOutTimes(outTime+1);
		}
		
		
		/**
		 * 定时清除 房间里只有一个人 而且 他已经退出了的房间
		 */
		Globals.getBazooPubService().removeSingleOneOutRoom();
				
	}
}