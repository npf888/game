package com.gameserver.http.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.db.model.UserInfo;
import com.gameserver.common.Globals;
import com.gameserver.player.Player;

/**
 * 第三方  获取用户的金币的数量
 * @author JavaServer
 *
 */
@SuppressWarnings("serial")
public class ObtainNumberOfGold  extends HttpServlet{
	private static final Logger logger = LoggerFactory.getLogger(ObtainNumberOfGold.class);
	//添加活动
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//用户传过  用户名 和 渠道
		String username = req.getParameter("username");
		String channel = req.getParameter("channel");
		String gold = req.getParameter("gold");
		if(StringUtils.isBlank(gold)){
			logger.warn("第三方平台充值，玩家 金币 数 ["+gold+"] 为空");
			return;
		}
		if(StringUtils.isBlank(username) || StringUtils.isBlank(channel)){
			logger.warn("第三方平台充值，玩家 用户名["+username+"] 或者 渠道 ["+channel+"] 为空");
			return;
		}
		//通过渠道查询 用户ID
		UserInfo userInfo = Globals.getDaoService().getUserInfoDao().getUserInfoByUsernameAndChannel(username,channel);
		if(userInfo == null){
			logger.warn("第三方平台充值，玩家 用户名 为 ["+username+"]  同时渠道为 ["+channel+"] 的用户不存在");
			return;
		}
		
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(userInfo.getId());
		
		JSONObject jb = new JSONObject();
		jb.put("passportId", userInfo.getId());
		jb.put("gold", player.getHuman().getGold());
		resp.getWriter().print(jb.toJSONString());
	}

}
