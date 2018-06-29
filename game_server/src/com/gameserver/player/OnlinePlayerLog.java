package com.gameserver.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minidev.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import com.common.LogReasons.PlayerOnleLogReason;
import com.common.constants.Loggers;
import com.core.schedule.LLISchedule;
import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.ip.geoip.IPSearchService;


/**
 * 定时在线用户统计
 * @author 郭君伟
 *
 */
public class OnlinePlayerLog implements LLISchedule {
	public static final Logger logger = Loggers.playerLogger;
	@Override
	public void execute() {
		PlayerOnleLogReason reason = PlayerOnleLogReason.PLAYERONLEHOURS;
		long now = Globals.getTimeService().now();
		int timeDian = TimeUtils.getHourTime(now);
		logger.info("当前的时间戳-------："+now+" ----- timeDian:"+timeDian);
		int count = Globals.getOnlinePlayerService().getRoleSize();

		//每个国家有多少人统计出来
		Map<String,Integer> countryNumMap = new HashMap<String,Integer>();
		Map<String,Integer> ipNumMap = new HashMap<String,Integer>();
		Map<Long,Player> players = Globals.getOnlinePlayerService().getOnlinePlayersMap();
		IPSearchService iPSearchService = Globals.getiPSearchService();
		for(Entry<Long, Player> entry:players.entrySet()){
			Player player = entry.getValue();
			String country = player.getCountries();
			Integer existCountryNum = countryNumMap.get(country);
			if(existCountryNum == null){
				countryNumMap.put(country, 1);
			}else{
				countryNumMap.put(country, existCountryNum.intValue()+1);
			}
			
			String ip = player.getIPAddrWhitoutPort();
			if(StringUtils.isNotBlank(ip)){
				String coun = iPSearchService.searchCountryByIp(ip);
				Integer existIpNum = ipNumMap.get(coun);
				if(existIpNum == null){
					ipNumMap.put(coun, 1);
				}else{
					ipNumMap.put(coun, existIpNum.intValue()+1);
				}
			}
			
		}
		String json = JSONObject.toJSONString(countryNumMap);
		String ipJson = JSONObject.toJSONString(ipNumMap);
		Globals.getLogService().sendPlayerOnleLog(null, reason, reason.getReasonText(), timeDian, count,json,ipJson);

	}

}
