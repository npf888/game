package com.gameserver.http.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.core.util.TimeUtils;
import com.gameserver.common.Globals;
import com.gameserver.common.config.GameServerConfig;
import com.gameserver.common.unit.GameUnitList;
import com.gameserver.http.weixin.util.Constant;
import com.gameserver.player.Player;
/**
 * 将要被微信调用的接口
 * 用户查询各路信息
 * @author JavaServer
 *
 */
public class BeInvokedByWeixin extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String active = req.getParameter(Constant.active);
		if(StringUtils.isBlank(active)){
			resp.getWriter().print(Constant.active_not_clear);
			return;
		//查看 所有用户的在线情况
		}else if(Constant.view_all_online.equals(active)){
			viewAllOnlinePlayer(req,resp);
			return;
		//根据passportId 查看 单个用户的在线情况
		}else if(Constant.view_single_online.equals(active)){
			viewSingleOnlinePlayer(req,resp);
			return;
		//开启机器人
		}else if(Constant.robot_start.equals(active) || Constant.robot_start_all.equals(active) ){
			openDownRoboot(req,resp);
			return;
		//关闭机器人
		}else if(Constant.robot_shutdown.equals(active) || Constant.robot_shutdown_all.equals(active) ){
			shutDownRoboot(req,resp);
			return;
		}else{
			resp.getWriter().print(Constant.active_not_clear);
			return;
		}
		
	}
	private void openDownRoboot(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		GameServerConfig config = Globals.getServerConfig();
		config.setRobot(true);
		StringBuilder sb = new StringBuilder();
		sb.append(Constant.robot_start_success);
		resp.getWriter().println(sb.toString());
	}
	private void shutDownRoboot(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		GameServerConfig config = Globals.getServerConfig();
		config.setRobot(false);
		StringBuilder sb = new StringBuilder();
		sb.append(Constant.robot_shutdown_success);
		resp.getWriter().println(sb.toString());
	}
	/**
	 * 查看所有在线用户数量
	 */
	private void viewAllOnlinePlayer(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
		GameUnitList<Player>  players = Globals.getOnlinePlayerService().getOnlinePlayers();
		int num = players.size();
		StringBuilder sb = new StringBuilder();
		sb.append(num);
		resp.getWriter().println(sb.toString());
	}
	/**
	 * 查看单个在线用户
	 */
	private void viewSingleOnlinePlayer(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		String passportId = req.getParameter("passportId");
		if(StringUtils.isBlank(passportId)){
			resp.getWriter().print(Constant.passportId_is_null);
			return;
		}
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(Long.valueOf(passportId));
		if(player == null){
			resp.getWriter().print(Constant.player_is_null);
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("今日累计在线时长，分钟数:"+player.getTodayOnlineTime()+",");
		sb.append("上次玩家登录时间:"+TimeUtils.longToDate(player.getLastLoginTime())+",");
		sb.append("玩家ip地址:"+player.getClientIp()+",");
		sb.append("当前终端类型 :"+player.getDeviceType()+",");
		sb.append("登陆国家 :"+player.getCountries()+",");
		sb.append("设备机型  :"+player.getDeviceModel()+",");
		resp.getWriter().print(sb.toString()+",");
	}

}
