package com.gameserver.http.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.core.uuid.UUIDType;
import com.db.model.ActivityEntity;
import com.gameserver.activity.Activity;
import com.gameserver.activity.ActivityRewardRule;
import com.gameserver.activity.HumanActivity;
import com.gameserver.activity.HumanActivityData;
import com.gameserver.activity.HumanActivityDataFactory;
import com.gameserver.activity.HumanRewardActivityDetailData;
import com.gameserver.activity.enums.ActivityTypeEnum;
import com.gameserver.common.Globals;
/**
 * 更新活动列表，并向客户端推送 活动list
 * @author JavaServer
 *
 */
public class UpdateActivity extends HttpServlet{
	//添加活动
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Activity activity = new Activity();
		String id = (String)req.getParameter("id");
		ActivityEntity entity =Globals.getDaoService().getActivityDao().get(Long.valueOf(id));
		activity.fromEntity(entity);
		//公共的活动 要创建一条公共的数据
		if(ActivityTypeEnum.ActivityType17 ==activity.getActivityType() ){
			 HumanActivity humanActivity = new HumanActivity();
			 long now = Globals.getTimeService().now();
			 humanActivity.setDbId(Globals.getUUIDService().getNextUUID(now, UUIDType.HUMANACTIVITYID));
			 humanActivity.setActivityId(activity.getDbId());
			 humanActivity.setCharId(0);
		
			 HumanActivityData tempHumanActivityData = HumanActivityDataFactory.createHumanActivityData(activity.getActivityType(),activity.getRuleList());
			 if(tempHumanActivityData == null)
			 {
				 return;
			 }
			 
			 humanActivity.setHumanActivityData(tempHumanActivityData);
			 
			 for( ActivityRewardRule activityRewardRule: activity.getRuleList()){
				 humanActivity.getHumanRewardActivityDetailDataList().add(HumanRewardActivityDetailData.convertFromActivityRewardRule(activityRewardRule));
			 }
			 
			 humanActivity.setUpdateTime(now);
			 humanActivity.setCreateTime(now);
			 Globals.getDaoService().getHumanActivityDao().save(humanActivity.toEntity());
			 Globals.getActivityService().getPublicActivityList().add(humanActivity);
		}
		Globals.getActivityService().updateActivity(activity);
		JSONObject jb = new JSONObject();
		jb.put("message", "OK");
		resp.getWriter().print(jb.toJSONString());
		//推送activity
		//Globals.getActivityService().buildActivityList();
	}
	//删除活动
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = (String)req.getParameter("id");
		Globals.getActivityService().deleteActivity(Long.valueOf(id));
		JSONObject jb = new JSONObject();
		jb.put("message", "OK");
		resp.getWriter().print(jb.toJSONString());
	}
}
