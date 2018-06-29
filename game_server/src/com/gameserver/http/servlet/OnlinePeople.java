package com.gameserver.http.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.alibaba.fastjson.JSONObject;
import com.gameserver.common.Globals;
import com.gameserver.common.unit.GameUnitList;
import com.gameserver.http.vo.OnlinePlayerVO;
import com.gameserver.player.Player;

public class OnlinePeople extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String channelId = req.getParameter("channelId");
		String account = req.getParameter("account");
		String username = req.getParameter("username");
		GameUnitList<Player> players = Globals.getOnlinePlayerService().getOnlinePlayers();
		List<Player> existPlayers = new ArrayList<Player>();
		if(players != null){
			for(Player player:players){
				existPlayers.add(player);
			}
		}
		List<OnlinePlayerVO> onlinePlayerList = new ArrayList<OnlinePlayerVO>();
		//只要有一个 条件 不为空就是条件查询
		
		if(StringUtils.isNotBlank(channelId) || StringUtils.isNotBlank(account) || StringUtils.isNotBlank(username)){
			
			for(Player player :existPlayers){
				if(StringUtils.isNotBlank(channelId) && NumberUtils.isNumber(channelId)){
					if(player.getChannelType().getIndex() != Integer.valueOf(channelId).intValue()){
						continue;
					}
				}
				if(StringUtils.isNotBlank(account) && NumberUtils.isNumber(account)){
					if(!String.valueOf(player.getPassportId()).contains(account)){
						continue;
					}
				}
				if(StringUtils.isNotBlank(account)){
					if(!String.valueOf(player.getHuman().getName()).contains(username)){
						continue;
					}
				}
				
				setPeople(player, onlinePlayerList);
			}
			//把总人数 和 每个人的信息 组装成json返回去
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("errorCode", 0);
			result.put("totalNum", onlinePlayerList.size());
			result.put("onlinePeople", onlinePlayerList);
			String reJson = JSONObject.toJSONString(result);
			resp.getWriter().print(reJson);
			return;
		}
		
		
		
		int start = Integer.valueOf(req.getParameter("start")).intValue();
		int limit = Integer.valueOf(req.getParameter("limit")).intValue();
		int end = start+limit;
		if(existPlayers != null){
			//总人数
			int totalNum = existPlayers.size(); 
			
			if(limit < existPlayers.size()){//成员过少  全部取出来
				for(int i=0;i<existPlayers.size();i++){
					Player player = existPlayers.get(i);
					setPeople(player, onlinePlayerList);
				}
			}else{
				if(start <existPlayers.size() && end <existPlayers.size()){
					for(int i=start;i<end;i++){
						Player player = existPlayers.get(i);
						setPeople(player, onlinePlayerList);
					}
				}else if(start <existPlayers.size() && end >=existPlayers.size()){
					for(int i=start;i<existPlayers.size();i++){
						Player player = existPlayers.get(i);
						setPeople(player, onlinePlayerList);
					}
				}
			}
			
			//把总人数 和 每个人的信息 组装成json返回去
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("errorCode", 0);
			result.put("totalNum", totalNum);
			result.put("onlinePeople", onlinePlayerList);
			String reJson = JSONObject.toJSONString(result);
			resp.getWriter().print(reJson);
			return;
		}
	}
	
	
	private void setPeople(Player player,List<OnlinePlayerVO> onlinePlayerList){
		if(player == null){
			return;
		}
		OnlinePlayerVO OnlinePlayerVO = new OnlinePlayerVO();
		OnlinePlayerVO.setName(player.getHuman().getName());
		OnlinePlayerVO.setPassportId(player.getPassportId());
		OnlinePlayerVO.setGirlFlag(player.getHuman().getGirl());
		OnlinePlayerVO.setChannelType(player.getChannelType().getIndex());
		OnlinePlayerVO.setDevice(player.getDeviceModel());
		OnlinePlayerVO.setGold(player.getHuman().getGold());
		OnlinePlayerVO.setTotalMinute(player.getHuman().getTotalMinute());
		onlinePlayerList.add(OnlinePlayerVO);
	}
}
