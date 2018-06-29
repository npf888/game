package com.gameserver.http.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.Loggers;
import com.db.model.HumanEntity;
import com.gameserver.bazoo.data.HumanInfo;
import com.gameserver.common.Globals;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.player.Player;

@SuppressWarnings("serial")
public class AddAndCostGold extends HttpServlet{
	private Logger logger = Loggers.BAZOO;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String costPassportId = req.getParameter("costPassportId");
		String givePassportId = req.getParameter("givePassportId");
		String gold = req.getParameter("gold");
		if(!NumberUtils.isNumber(costPassportId) || !NumberUtils.isNumber(givePassportId) || !NumberUtils.isNumber(gold)){
			resp.getWriter().print("costPassportId  || givePassportId || gold 不正确-非数字");
			return;
		}
		Player costPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(Integer.valueOf(costPassportId));
		Player givePlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(Integer.valueOf(givePassportId));
		//分四种情况给钱  1：两个用户都在线，2-3：两个用户只有一个在线，4：都不在线
		if(costPlayer != null && givePlayer != null){
			String costDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_AGENT_COST_GOLD.getReasonText(),String.valueOf(costPlayer.getPassportId()));
			costPlayer.getHuman().costMoney(Long.valueOf(gold), Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_AGENT_COST_GOLD, costDetailReason, -1, 1);
			
			String giveDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_AGENT_GIVE_GOLD.getReasonText(),String.valueOf(costPlayer.getPassportId()));
			givePlayer.getHuman().giveMoney(Long.valueOf(gold), Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_AGENT_GIVE_GOLD, giveDetailReason, -1, 1);
		}
		
		//costPlayer 不在线的情况
		if(costPlayer == null && givePlayer != null)
		{
			this.costGold(Long.valueOf(costPassportId),Long.valueOf(gold).longValue());
			String giveDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_AGENT_GIVE_GOLD.getReasonText(),costPassportId);
			givePlayer.getHuman().giveMoney(Long.valueOf(gold), Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_AGENT_GIVE_GOLD, giveDetailReason, -1, 1);
		}
		//givePlayer 不在线的情况
		if(costPlayer != null && givePlayer == null)
		{
			String costDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_AGENT_GIVE_GOLD.getReasonText(),String.valueOf(costPlayer.getPassportId()));
			costPlayer.getHuman().costMoney(Long.valueOf(gold), Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_AGENT_COST_GOLD,costDetailReason, -1, 1);
			this.giveGold(Long.valueOf(givePassportId),Long.valueOf(gold).longValue(),costPassportId);
		}
		//costPlayer  和      givePlayer 都不在线的情况
		if(costPlayer == null && givePlayer == null)
		{
			this.costGold(Long.valueOf(costPassportId),Long.valueOf(gold).longValue());
			this.giveGold(Long.valueOf(givePassportId),Long.valueOf(gold).longValue(),costPassportId);
		}
		
		
		resp.getWriter().print("修改成功");
	}
	
	
	
	private void giveGold(long passportId,long giveGold, String costPassportId){
		List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(passportId);
		if(humanEntityList != null && humanEntityList.size()>0){
			HumanEntity entity = humanEntityList.get(0);
			entity.setGold(entity.getGold()+Long.valueOf(giveGold).longValue());
			Globals.getDaoService().getHumanDao().saveOrUpdate(entity);
			HumanInfo human = new HumanInfo(null);
			human.setCurPassportId(entity.getPassportId());
			String costDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_AGENT_GIVE_GOLD.getReasonText(),costPassportId);
			Globals.getLogService().sendGoldLog(human, LogReasons.GoldLogReason.BAZOO_AGENT_GIVE_GOLD, costDetailReason, giveGold, entity.getGold());
		}
	}
	private void costGold(long passportId,long costGold){
		List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(passportId);
		if(humanEntityList != null && humanEntityList.size()>0){
			HumanEntity entity = humanEntityList.get(0);
			entity.setGold(entity.getGold()-Long.valueOf(costGold).longValue());
			Globals.getDaoService().getHumanDao().saveOrUpdate(entity);
			HumanInfo human = new HumanInfo(null);
			human.setCurPassportId(entity.getPassportId());
			String costDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_AGENT_COST_GOLD.getReasonText(),String.valueOf(entity.getPassportId()));
			Globals.getLogService().sendGoldLog(human, LogReasons.GoldLogReason.BAZOO_AGENT_COST_GOLD, costDetailReason, costGold, entity.getGold());
		}
	}
}
