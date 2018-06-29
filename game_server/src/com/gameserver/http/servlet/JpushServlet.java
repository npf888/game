package com.gameserver.http.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gameserver.common.Globals;

@SuppressWarnings("serial")
public class JpushServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(JpushServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String jsonStr = req.getParameter("json");//兑换码
		
		Globals.getRedisService().push("jpush", jsonStr);
		logger.info("receive jpush: "+jsonStr);
		resp.getWriter().print(jsonStr);
	}

}
