package com.gameserver.http.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.gameserver.common.Globals;
import com.gameserver.conversioncode.ConversioncodeService;

public class Receivecode extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String code = req.getParameter("code");//兑换码
		/*String state = req.getParameter("state");//可用 0，不可用 1*/	
		String status = req.getParameter("status");//状态 1 添加，2修改
		String gold = req.getParameter("gold");
		String endTimeStr = req.getParameter("endTime");
		long endTime = new Date().getTime(); 
		if(StringUtils.isNotBlank(endTimeStr)){
			endTime = Long.valueOf(endTimeStr); 
		}
		String codeType = req.getParameter("codeType");
		if(StringUtils.isBlank(status)){
			resp.getWriter().print("status is null");
			return;
		}
		ConversioncodeService conversioncodeService = Globals.getConversioncodeService();
		//增加
		if("1".equals(status)){
			if(StringUtils.isBlank(gold)){
				gold="0";
			}
			conversioncodeService.addDate(code, Long.valueOf(gold), Long.valueOf(endTime),codeType);
		//修改
		}else if("2".equals(status)){
			if(StringUtils.isBlank(code)){
				resp.getWriter().print("code is null");
				return;
			}
			conversioncodeService.editStata(code, Long.valueOf(gold), Long.valueOf(endTime),codeType);
		//删除
		}else if("3".equals(status)){
			if(StringUtils.isBlank(code)){
				resp.getWriter().print("code is null");
				return;
			}
			conversioncodeService.deleteStata(code);
		}
		resp.getWriter().print("ok");
	}
}
