package com.gameserver.player;
import java.util.Date;
import java.util.Map;

import com.common.constants.DisconnectReason;
import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.session.ISession;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.player.msg.GCNotifyException;

/**
 * 周期性校验玩家是否连接超时
 *
 */
public class ScheduleLastNetTime  implements LLISchedule
{

	//系统 中：用户多长时间没有登录 就被干掉
	private static final int MAX_TIME = 1;
	
	public ScheduleLastNetTime()
	{
	}


	private void sendLastNetTime(){
		//判断是否开启了超时踢出玩家功能
		if(!Globals.getServerConfig().isLastNetOnOff()) return;
		
		long now=System.currentTimeMillis();
		
		//循环在线玩家
		for(Long roleId:Globals.getOnlinePlayerService().getAllOnlinePlayerRoleUUIDs()){
			try{
				Player player=Globals.getOnlinePlayerService().getPlayer(roleId);
				//获得玩家上次通信时间
				long lastNetTime=player.getLastNetTime();
				//判断两个时间是否间隔了10分钟
				int time=TimeUtils.compareTime(lastNetTime, now, TimeUtils.MIN);
				//改成相差多少秒
				//大于x分钟踢掉玩家
				if(time>=MAX_TIME){
					player.sendMessage(new GCNotifyException(DisconnectReason.LASTNETTIMEOUT.getIndex(), ""));
					player.exitReason = PlayerExitReason.LASTNETTIMEOUT;
					player.disconnectNew();
					Globals.getOnlinePlayerService().offlinePlayerNew(player, PlayerExitReason.LASTNETTIMEOUT);
					Globals.getSlotRoomService().outLogin(player);
					player.getHuman().setModified();
					int slotId = player.getHuman().getHumanSlotManager().getSlotId();
					int slotType = Globals.getSlotService().getTypeById(slotId);
					Globals.getTournamentService().loginOut(player.getPassportId(), slotType);
				}
			}catch(Exception e){
				continue;
			}
		}
		
		Map<ISession, Player> sessionPlayers = Globals.getOnlinePlayerService().getSessionPlayers();
		for(ISession isession : sessionPlayers.keySet()){
			Player player = sessionPlayers.get(isession);
			//获得玩家上次通信时间
			long lastNetTime=player.getLastNetTime();
			//判断两个时间是否间隔了10分钟
			int time=TimeUtils.compareTime(lastNetTime, now, TimeUtils.MIN);
			//大于x分钟踢掉玩家
			if(time>=MAX_TIME){
				Globals.getOnlinePlayerService().removeSession(isession);
			}
		}
		
	}
	
	
	@Override
	public void execute() {
		String time=TimeUtils.formatYMDHMSTime(new Date().getTime());
		Loggers.scheduleLogger.info("======>start sendLastNetTime time is:"+time);
		sendLastNetTime();
	}
}
