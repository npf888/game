package com.gameserver.http.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.core.util.TimeUtils;
import com.core.uuid.UUIDType;
import com.db.model.ActivityEntity;
import com.gameserver.activity.ActivityRewardRule;
import com.gameserver.activity.HumanActivity;
import com.gameserver.activity.HumanActivityData;
import com.gameserver.activity.HumanActivityDataFactory;
import com.gameserver.activity.HumanRewardActivityDetailData;
import com.gameserver.activity.enums.ActivityTypeEnum;
import com.gameserver.common.Globals;
import com.gameserver.function.Function;
/**
 * 更新功能
 * @author JavaServer
 *
 */
public class UpdateFunction extends HttpServlet{
	//添加活动
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Function function = new Function();
		String id = req.getParameter("id");
		String title = req.getParameter("title");
		String startTime = req.getParameter("startTime");
		String endTime = req.getParameter("endTime");
		String descrip = req.getParameter("descrip");
		String pic = req.getParameter("pic");
		String functionType = req.getParameter("functionType");
		String condition = req.getParameter("conditionCategroy");
		if(StringUtils.isNotBlank(id)){
			function.setId(Long.valueOf(id));
		}
		function.setTitle(title);
		function.setStartTime(TimeUtils.getYMDTimeFromStr(startTime));
		function.setEndTime(TimeUtils.getYMDTimeFromStr(endTime));
		function.setDescrip(descrip);
		function.setPic(pic);
		function.setFunctionType(Integer.valueOf(functionType));
		if(StringUtils.isNotBlank(condition)){
			function.setConditions(condition);
		}
		function.active();
		Globals.getFunctionService().updateFunction(function);
		JSONObject jb = new JSONObject();
		jb.put("message", "OK");
		resp.getWriter().print(jb.toJSONString());
		//推送activity
		//Globals.getActivityService().buildActivityList();
	}
	//删除功能
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
 		String id = (String)req.getParameter("id");
		Globals.getFunctionService().deleteActivity(Long.valueOf(id));
		JSONObject jb = new JSONObject();
		jb.put("message", "OK");
		resp.getWriter().print(jb.toJSONString());
	}
}
