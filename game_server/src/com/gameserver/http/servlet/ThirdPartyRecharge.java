/*package com.gameserver.http.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.common.LogReasons;
import com.db.model.HumanEntity;
import com.db.model.UserInfo;
import com.gameserver.bazoo.data.HumanInfo;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.player.Player;
import com.gameserver.recharge.HumanRechargeOrder;
import com.gameserver.recharge.enums.TopUpType;

*//**
 * 第三方平台调用充值
 * @author JavaServer
 *
 *//*
@SuppressWarnings("serial")
public class ThirdPartyRecharge extends HttpServlet{
	private static final Logger logger = LoggerFactory.getLogger(ThirdPartyRecharge.class);
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
		
		// 记录订单
		long passportId = userInfo.getId();
		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
		if(player == null){
			HumanEntity entity = giveGold(passportId,Long.valueOf(gold));
			if(entity == null){
				logger.warn("第三方平台充值，玩家 用户ID 为 ["+passportId+"]  同时渠道为 ["+channel+"] 的用户有问题，不能充值");
				return;
			}
			Globals.getRechargeService().thirdPartyProduceOrder(entity,0, 0,Long.valueOf(gold));
			
			JSONObject jb = new JSONObject();
			jb.put("passportId", userInfo.getId());
			jb.put("gold", entity.getGold());
			resp.getWriter().print(jb.toJSONString());
			return;
		}
		//先下一个订单
		HumanRechargeOrder order = Globals.getRechargeService().thirdPartyGenerateOrder(player.getHuman(),0,0,Long.valueOf(gold));
		order.setChannelOrderId(channel);//渠道 平台 的标识
		order.setTopUpType(TopUpType.COMMON.getIndex());
		order.setModified();
		
		//要不要验证

		//充值
		player.getHuman().giveMoney(Long.valueOf(gold), Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_THIRD_PARTY_GIVE_GOLD, LogReasons.GoldLogReason.BAZOO_THIRD_PARTY_GIVE_GOLD.getReasonText(), -1, 1);
		
		
		JSONObject jb = new JSONObject();
		jb.put("passportId", userInfo.getId());
		jb.put("gold", player.getHuman().getGold());
		resp.getWriter().print(jb.toJSONString());
	}

	
	*//**
	 * 用户 不在线 用此方法充值
	 * @param passportId
	 * @param giveGold
	 * @param costPassportId
	 *//*
	private HumanEntity giveGold(long passportId,long giveGold){
		List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(passportId);
		if(humanEntityList != null && humanEntityList.size()>0){
			HumanEntity entity = humanEntityList.get(0);
			entity.setGold(entity.getGold()+Long.valueOf(giveGold).longValue());
			Globals.getDaoService().getHumanDao().saveOrUpdate(entity);
			HumanInfo human = new HumanInfo(null);
			human.setCurPassportId(entity.getPassportId());
			String costDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_THIRD_PARTY_GIVE_GOLD.getReasonText(),"");
			Globals.getLogService().sendGoldLog(human, LogReasons.GoldLogReason.BAZOO_THIRD_PARTY_GIVE_GOLD, costDetailReason, giveGold, entity.getGold());
			return entity;
		}
		return null;
	}
}
*/