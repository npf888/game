package com.gameserver.http.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gameserver.common.Globals;
import com.gameserver.player.OnlinePlayerService;

public class ServiceInfo extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		OnlinePlayerService online = Globals.getOnlinePlayerService();
		//维护当前Server所有在线玩家实例
		//int onlinePlayers = online.getOnlinePlayerCount();
		
		//在线玩家列表，方便查询，key：玩家当前角色UUID，value：玩家引用
		//int _onlinePlayersMap = online.getOnlinePlayersMap().size();
		//在线玩家的会话管理
	//	int sessionPlayers = online.getSessionCount();
		
		//登录用户集合 <Long passportId,Player loginUser>
		//int passportIdPlayers = online.getpassportIdPlayersCount();
		//查询 用户的最近登录时间; key:用户 Player的 passportID ,value:时间(单位为毫秒)
		//int playerLoginTimeMapCount = online.getplayerLoginTimeMapCount();
		// 登录用户集合 <String roleName, Player loginUser>
		//int  roleNamePlayers = online.getPlayerCount();
		
		/*StringBuilder sb = new StringBuilder();
		sb.append("onlinePlayers").append(onlinePlayers).append("</br>")
		.append("_onlinePlayersMap").append(_onlinePlayersMap).append("</br>")
		.append("sessionPlayers").append(sessionPlayers).append("</br>")
		.append("passportIdPlayers").append(passportIdPlayers).append("</br>");*/
		//.append("playerLoginTimeMapCount").append(playerLoginTimeMapCount).append("</br>")
		//.append("roleNamePlayers").append(roleNamePlayers).append("</br>");
		// response.getWriter().println(sb.toString());
		StringBuilder sb = new StringBuilder();
		 sb.append(online.getOnlinePlayerCount()).append("||");
		 sb.append(online.getOnlinePlayersMap().size()).append("||");
		 sb.append(online.getPlayerByPassportSzie()).append("||");
		 sb.append(online.getSessionPlayers().size()).append("||");
		 response.getWriter().println(sb.toString());
	}
}
