package com.gameserver.http.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.db.model.HumanEntity;
import com.db.model.HumanItemEntity;
import com.gameserver.bazoo.data.HumanInfo;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.item.template.ItemNewTemplate;
import com.gameserver.mail.MailLogic;
import com.gameserver.player.Player;
import com.gameserver.recharge.enums.SmallCategoryEnum;
/**
 * 代理商发送红包
 * @author JavaServer
 *
 */
@SuppressWarnings("serial")
public class NoticeRedPackage  extends HttpServlet{
	private Logger logger = Loggers.BAZOO;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String costPassportId = req.getParameter("costPassportId");
		String givePassportId = req.getParameter("givePassportId");
		String num = req.getParameter("number");
		if(!NumberUtils.isNumber(costPassportId) || !NumberUtils.isNumber(givePassportId) || !NumberUtils.isNumber(num)){
			resp.getWriter().print("costPassportId  || givePassportId || gold 不正确-非数字");
			return;
		}
		int number = Integer.valueOf(num);
		Player costPlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(Integer.valueOf(costPassportId));
		Player givePlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(Integer.valueOf(givePassportId));
		
		int itemId = 0;
		//红包
		List<ItemNewTemplate> itemNewTemplateList = Globals.getItemNewService().getItemNewTemplateList();
		for(ItemNewTemplate template:itemNewTemplateList){
			if(template.getItemType() == SmallCategoryEnum.SMALLCATEGORYENUM3.getIndex()){
				itemId = template.getId();
			}
		}
		String name = "";
		ItemNewTemplate ItemNewTemplate = Globals.getItemNewService().getItemTemplateByItemId(itemId);
		long needGold = Long.valueOf(ItemNewTemplate.getNum()*number).longValue();
		if(costPlayer != null){
			//首先看看钱够不够
			if(needGold>costPlayer.getHuman().getGold()){
				logger.info("[无双吹牛]---[发送送礼物]---[失败]---[金币不足]---[用户::"+costPlayer.getPassportId()+"-itemId"+itemId+"]");
				//失败 消息
				return;
			}
			//扣掉当前用户的钱
			costPlayer.getHuman().costMoney(needGold, Currency.GOLD, true, LogReasons.GoldLogReason.BAZOO_RED_PACKAGE_COST_GOLD, LogReasons.GoldLogReason.BAZOO_RED_PACKAGE_COST_GOLD.getReasonText(), -1, 1);
			name=costPlayer.getHuman().getName();
		}else{
			List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(Long.valueOf(costPassportId));
			if(humanEntityList != null && humanEntityList.size()>0){
				HumanEntity entity = humanEntityList.get(0);
				name = entity.getName();
				//首先看看钱够不够
				if(needGold>entity.getGold()){
					logger.info("[无双吹牛]---[发送送礼物]---[失败]---[金币不足]---[用户::"+entity.getPassportId()+"-itemId"+itemId+"]");
					//失败 消息
					return;
				}
			}
			costGold(Long.valueOf(costPassportId),needGold);
		}
		
		
		
		
		//把礼物加到 目标对象身上
		if(givePlayer != null){
			givePlayer.getHuman().getHumanBagManager().addItemNew(givePlayer.getPassportId(),givePlayer,itemId,SmallCategoryEnum.SMALLCATEGORYENUM3.getIndex(), number);
			
		//用户没有在线
		}else{
			List<HumanItemEntity> HumanItemEntityList = Globals.getDaoService().getHumanItemDao().getItemsByCharId(Long.valueOf(givePassportId).longValue());
			for(HumanItemEntity entity :HumanItemEntityList){
				if(entity.getTemplateId() == itemId){
					entity.setOverlap(entity.getOverlap()+number);
					Globals.getDaoService().getHumanItemDao().saveOrUpdate(entity);
					break;
				}
			}
		}
		
		
		String title = String.valueOf(LangConstants.DICE_RED_PACKAGE_GET)+"|"+name;
		String strContent = String.valueOf(LangConstants.DICE_RED_PACKAGE_GET);
		List<Long> listId = new ArrayList<Long>();
		listId.add(Long.valueOf(givePassportId).longValue());
		//发邮件
		MailLogic.getInstance().systemSendMail(null,title ,strContent, listId, new ArrayList<RandRewardData>()); 
		
		
	}
	private void costGold(long passportId,long costGold){
		List<HumanEntity> humanEntityList = Globals.getDaoService().getHumanDao().loadHumans(passportId);
		if(humanEntityList != null && humanEntityList.size()>0){
			HumanEntity entity = humanEntityList.get(0);
			entity.setGold(entity.getGold()-Long.valueOf(costGold).longValue());
			Globals.getDaoService().getHumanDao().saveOrUpdate(entity);
			HumanInfo human = new HumanInfo(null);
			human.setCurPassportId(entity.getPassportId());
			String costDetailReason = MessageFormat.format(LogReasons.GoldLogReason.BAZOO_RED_PACKAGE_COST_GOLD.getReasonText(),String.valueOf(entity.getPassportId()));
			Globals.getLogService().sendGoldLog(human, LogReasons.GoldLogReason.BAZOO_RED_PACKAGE_COST_GOLD, costDetailReason, costGold, entity.getGold());
		}
	}
}
