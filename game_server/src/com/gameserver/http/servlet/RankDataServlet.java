package com.gameserver.http.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取排行榜数据
 * @author 郭君伟
 *
 */
public class RankDataServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		/*
		String start = req.getParameter("start");
		String end = req.getParameter("end");
		String rankType = req.getParameter("rankType");
		if(start != null && end != null && rankType != null){
			
			 response.setStatus(HttpServletResponse.SC_OK);
			 String key = "";
			 switch (RankKeyType.indexOf(Integer.parseInt(rankType))) {
				case RankKeyType1:
					key = RankKeyType.RankKeyType1.getKey();
					break;
				case RankKeyType2:
					key = RankKeyType.RankKeyType2.getKey();
					break;
				case RankKeyType3:
					key = RankKeyType.RankKeyType3.getKey();
					break;
				case RankKeyType4:
					key = RankKeyType.RankKeyType4.getKey();
					break;
				case RankKeyType5:
					key = RankKeyType.RankKeyType5.getKey();
					break;
				case RankKeyType6:
					key = RankKeyType.RankKeyType6.getKey();
					break;
				default:
					break;
				}
				
				if(key.equals("")){
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					return;
				}
				
		  List<RankDataVO> listData = Globals.getRankNewServer().getRankNetwork(key,Integer.parseInt(start) ,Integer.parseInt(end) );
			
		  response.getWriter().println(JSON.toJSONString(listData));
			
			
		}else{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}*/
		 
		 
       
        
       
	}
         
}
