package com.gameserver.vip.handler;

import java.text.MessageFormat;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanTexasManager;
import com.gameserver.human.manager.HumanVipManager;
import com.gameserver.human.manager.HumanVipNewManager;
import com.gameserver.player.Player;
import com.gameserver.texas.TexasRoom;
import com.gameserver.vip.msg.CGVipBuy;
import com.gameserver.vip.msg.CGVipCreateRoom;
import com.gameserver.vip.msg.CGVipGet;
import com.gameserver.vip.msg.GCHumanVipInfoData;
import com.gameserver.vip.msg.GCVipBuy;
import com.gameserver.vip.msg.GCVipGet;
import com.gameserver.vip.template.VipRoomTemplate;
import com.gameserver.vip.template.VipTemplate;
import com.gameserver.vipnew.template.VipNewTemplate;

/**
 * vip
 * @author wayne
 *
 */
public class VipMessageHandler {
	
	private Logger logger = Loggers.vipLogger;
	private  final int PASSWORD_MAX_LENGTH =6;
	
	/**
	 * 购买vip
	 * @param player
	 * @param cgVipBuy
	 */
	public void handleVipBuy(Player player, CGVipBuy cgVipBuy) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		HumanVipManager humanVipManager = human.getHumanVipManager();
		humanVipManager.checkIfExpire();
		
		int lv = cgVipBuy.getVipLevel();
		VipTemplate vipTemplate = Globals.getVipService().getVipTemplateByLevel(lv);
		
		//不存在的vip等级
		if(vipTemplate == null)
		{
			logger.warn("玩家[" +player.getPassportId() + "]购买不存在的vip");
			player.sendSystemMessage(LangConstants.VIP_NO_EXIST);
			return;
		}
		
		//不能购买小于vip等级的
		if(lv<humanVipManager.getHumanVip().getLevel())
		{
			logger.warn("玩家[" +player.getPassportId() + "]购买低于当前的vip");
			player.sendSystemMessage(LangConstants.VIP_BUY_LESS);
			return;
		}
		
		//判断是否金钱足够
		if(!human.hasEnoughMoney(vipTemplate.getCostDiamonds(), Currency.DIAMOND))
		{
			logger.warn("玩家[" +player.getPassportId() + "]钻石不够");
			player.sendSystemMessage(LangConstants.DIAMOND_NOT_ENOUGH);
			return;
		}
		
		String diamondDetailReason = MessageFormat.format(LogReasons.DiamondLogReason.BUY_VIP.getReasonText(), vipTemplate.getCostDiamonds());
		human.costMoney(vipTemplate.getCostDiamonds(), Currency.DIAMOND, true, LogReasons.DiamondLogReason.BUY_VIP, diamondDetailReason, -1, 1);
		
		//记录vip变化日志
		String detailReason = MessageFormat.format( LogReasons.VipLogReason.BUY_VIP.getReasonText(),humanVipManager.getHumanVip().getLevel(),lv);
		
		boolean extend = false;
		if(lv == humanVipManager.getHumanVip().getLevel()){
			logger.info("玩家[" +player.getPassportId() + "]拓展当前的vip");
			extend = true;
		}
		
		if(!extend && humanVipManager.getHumanVip().getLevel()!=0)
		{
			logger.info("玩家[" +player.getPassportId() + "]给予剩余奖励");
			CommonLogic.getInstance().giveRandReward(human, humanVipManager.getRemainRewards() ,LogReasons.GoldLogReason.VIP_REMAIN,LogReasons.DiamondLogReason.VIP_REMAIN,LogReasons.CharmLogReason.VIP_REMAIN,LogReasons.ItemLogReason.VIP_REMAIN, false);
		}
		
		//更新vip等级
		humanVipManager.buyVipLevel(lv);
		
		CommonLogic.getInstance().giveRandReward(human, vipTemplate.getInitRewardList(),LogReasons.GoldLogReason.VIP_INIT,LogReasons.DiamondLogReason.VIP_INIT,LogReasons.CharmLogReason.VIP_INIT,LogReasons.ItemLogReason.VIP_INIT, false);
		GCVipBuy gcVipBuy = new GCVipBuy();
		gcVipBuy.setRandRewardList(vipTemplate.getInitRewardList().toArray(new RandRewardData[vipTemplate.getInitRewardList().size()]));
		player.sendMessage(gcVipBuy);
		
		GCHumanVipInfoData gcHumanVipInfoData = humanVipManager.buildHumanVipInfoData();
		player.sendMessage(gcHumanVipInfoData);
		
		//购买vip
		Globals.getLogService().sendVipLog(human, LogReasons.VipLogReason.BUY_VIP, detailReason, lv);
	}

	/**
	 * vip领取奖励
	 * @param player
	 * @param cgVipGet
	 */
	public void handleVipGet(Player player, CGVipGet cgVipGet) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		HumanVipManager humanVipManager = human.getHumanVipManager();
		humanVipManager.checkIfExpire();
		
		//不存在的vip等级
		if(!humanVipManager.ifVip())
		{
			logger.warn("玩家[" +player.getPassportId() + "]没有vip");
			player.sendSystemMessage(LangConstants.PLAYER_NOT_VIP);
			return;
		}
		
		//已经领过了
		if(humanVipManager.ifGet())
		{
			logger.warn("玩家[" +player.getPassportId() + "]已经领取过");
			player.sendSystemMessage(LangConstants.PLAYER_ALREADY_GET_VIP_REWARD);
			return;
		}
		
	
		//更新vip等级
		humanVipManager.getReward();
		
		VipTemplate vipTemplate = Globals.getVipService().getVipTemplateByLevel(humanVipManager.getHumanVip().getLevel());
		
		CommonLogic.getInstance().giveRandReward(human, vipTemplate.getDailyRewardList(),LogReasons.GoldLogReason.VIP_DAILY,LogReasons.DiamondLogReason.VIP_DAILY,LogReasons.CharmLogReason.VIP_DAILY,LogReasons.ItemLogReason.VIP_DAILY, false);
		
		GCVipGet gcGetVip = new GCVipGet();
		gcGetVip.setRandRewardList(vipTemplate.getDailyRewardList().toArray(new RandRewardData[vipTemplate.getDailyRewardList().size()]));
		player.sendMessage(gcGetVip);
		
		GCHumanVipInfoData gcHumanVipInfoData = humanVipManager.buildHumanVipInfoData();
		player.sendMessage(gcHumanVipInfoData);
		
		
	}
	
	/**
	 * 创建房间
	 * @param player
	 * @param cgVipCreateRoom
	 */
	public void handleVipCreateRoom(Player player,
			CGVipCreateRoom cgVipCreateRoom) {
		// TODO Auto-generated method stub
		Human human = player.getHuman();
		HumanTexasManager humanTexasManager = human.getHumanTexasManager();
		
		//判断密码长度
		String password = cgVipCreateRoom.getPassword();
		if(password.length()>PASSWORD_MAX_LENGTH){
			logger.warn("玩家[" +player.getPassportId() + "]，密码["+password+"]密码长度过长");
			return;
		}
		
		int vipId = cgVipCreateRoom.getVipId();
		//判断vip房间类型
		VipRoomTemplate vipRoomTemplate = Globals.getVipService().getVipRoomTemplateById(vipId);
		if(vipRoomTemplate == null){
			logger.warn("玩家[" +player.getPassportId() + "]，vip房间类型["+vipId+"]不存在");
			return;
		}
		
		TexasRoom texasRoom = humanTexasManager.getTexasRoom();
		//退出房间
		if(texasRoom!=null){
			texasRoom.playerExit(player);
		}
		
		//判断vip
		HumanVipNewManager vipManager = human.getHumanVipNewManager();
		int vipLevel = vipManager.getVipLv();
		VipNewTemplate vipTemplate= Globals.getVipNewServer().getVipNewTemplate(vipLevel);
		if(vipTemplate==null || vipTemplate.getVipRoom()!=1){
			logger.warn("玩家[" +player.getPassportId() + "]，vip["+vipLevel+"]等级不够，开启失败");
			return;
		}
		
		/*HumanVipManager vipManager = human.getHumanVipManager();
		int vipLevel = vipManager.getHumanVip().getLevel();
		VipTemplate vipTemplate= Globals.getVipService().getVipTemplateByLevel(vipLevel);
		if(vipTemplate==null || vipTemplate.getVipRoom()!=1){
			logger.warn("玩家[" +player.getPassportId() + "]，vip["+vipLevel+"]等级不够，开启失败");
			return;
		}*/
		
	
		TexasRoom room = Globals.getTexasService().roomForVipCreate(player, vipId, password);
		if(room ==null){
			logger.warn("玩家[" +player.getPassportId() + "]，vip["+vipLevel+"]创建 房间失败");
			return;
		}
		
		logger.debug("玩家[" +player.getPassportId() + "]，vip["+vipLevel+"]创建 房间成功");
		// 加入房间
		Globals.getTexasService().joinRoom(player,room);
		
	}

}
