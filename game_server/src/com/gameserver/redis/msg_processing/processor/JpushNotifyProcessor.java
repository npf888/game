package com.gameserver.redis.msg_processing.processor;

import com.common.constants.Loggers;
import com.gameserver.common.Globals;
import com.gameserver.player.OnlinePlayerService;
import com.gameserver.player.Player;
import com.gameserver.redis.msg_processing.ChannelProcessor;
import com.jpush.JpushClientUtil;

public class JpushNotifyProcessor implements ChannelProcessor {

	@Override
	public String getChannel() {
		return "jpush";
	}

	@Override
	public void doProcess(String data) {
		JpushContent jc = Globals.gson.fromJson(data, JpushContent.class);
		if(jc.isTooAll)
		{
			JpushClientUtil.sendToAll(jc.title, jc.title, jc.content, jc.params);
			Loggers.PUSH.info("push to all for: "+jc.id);
		}
		else
		{
			OnlinePlayerService oPlayerService = Globals.getOnlinePlayerService();
			for(Long passportId : jc.passportIds)
			{
				Player p = oPlayerService.getPlayerByPassportId(passportId);
				if(p!=null)
				{JpushClientUtil.sendToAlias(String.valueOf(passportId), jc.title, jc.title, jc.content, jc.params, true, true);}
				else
				{JpushClientUtil.sendToAlias(String.valueOf(passportId), jc.title, jc.title, jc.content, jc.params, true, false);}
				
				Loggers.PUSH.info("push to passportId: "+passportId+" for: "+jc.id);
			}
		}
	}

//	@Override
//	public void doProcess(String data) {
//		JpushData jpd = Globals.gson.fromJson(data, JpushData.class);
//		Set<Long> passportIds = new HashSet<>();
//		
//		for (Condition c : jpd.conditions) {
//			Set<Long> s = new HashSet<>();
//			if(StringUtils.isEmpty(c.value))
//			{
//				continue;
//			}
//			switch (c.type) {
//			case JpushConst.Condition_type_all:
//			{
//				if(c.value.equals("1"))
//				{
//					JpushClientUtil.sendToAll(jpd.title, jpd.title, jpd.content, jpd.params);
//					Loggers.PUSH.info("push to all for "+jpd.title);
//				}
//				return;
//			}
//			case JpushConst.Condition_type_club_gift: 
//			{
////				List<HumanWeekSignInEntity> list = Globals.getDaoService().getHumanWeekSignInDao().selectNotSignForJpush();
////				for(HumanWeekSignInEntity hwse : list)
////				{
////					s.add(hwse.getCharId());
////				}
//				break;
//			}
//			case JpushConst.Condition_type_friend_request: 
//			{
//				List<FriendRequestEntity> list = Globals.getDaoService().getHumanFriendRequestDao().selectFriendRequestForJpush();
//				for(FriendRequestEntity hwse : list)
//				{
//					s.add(hwse.getCharId());
//				}
//				break;
//			}
//			case JpushConst.Condition_type_gold_percent: 
//			{
//				for(TreasuryTemplate tt : Globals.getHumanTreasuryService().getTreasuryTemplateList())
//				{
//					List<HumanTreasuryEntity> list = Globals.getDaoService().getHumanTreasuryDao().selectPlayerByGoldPercent(tt.getTypeTreasury());
//					for(HumanTreasuryEntity hwse : list)
//					{
//						s.add(hwse.getChartId());
//					}
//				}
//				break;
//			}
//			case JpushConst.Condition_type_level: 
//			{
//				String[] levelRange = c.value.split(",");
//				List<HumanEntity> list = Globals.getDaoService().getHumanDao().selectPlayerByLevel(Integer.parseInt(levelRange[0]), Integer.parseInt(levelRange[1]));
//				for(HumanEntity he : list)
//				{
//					s.add(he.getPassportId());
//				}
//				break;
//			}
//			case JpushConst.Condition_type_logon_days: 
//			{
//				break;
//			}
//			case JpushConst.Condition_type_mail_has_reward: 
//			{
//				List<MailEntity> list = Globals.getDaoService().getMailDao().selectPlayerWithRewardMail(MailStatus.GET.getIndex());
//				for(MailEntity he : list)
//				{
//					s.add(he.getCharId());
//				}
//				break;
//			}
//			case JpushConst.Condition_type_need_sign: 
//			{
//				List<HumanWeekSignInEntity> list = Globals.getDaoService().getHumanWeekSignInDao().selectNotSignForJpush();
//				for(HumanWeekSignInEntity hwse : list)
//				{
//					s.add(hwse.getCharId());
//				}
//				break;
//			}
//			case JpushConst.Condition_type_not_logon_days: 
//			{
//				long ts = System.currentTimeMillis() - Integer.valueOf(c.value)*24*3600*1000;
//				List<HumanEntity> list = Globals.getDaoService().getHumanDao().selectPlayerByOffLineTime(ts);
//				for(HumanEntity he : list)
//				{
//					s.add(he.getPassportId());
//				}
//				break;
//			}
//			case JpushConst.Condition_type_online_reward: 
//			{
//				long now = System.currentTimeMillis();
//				HumanMiscDao hmd = Globals.getDaoService().getHumanMiscDao();
//				for(OnlineRewardTemplate ot : Globals.getMiscService().getOnlineRewardTempalteList())
//				{
//					List<HumanMiscEntity> list = hmd.selectPlayerByOnlineReward(ot.getRewardId(), now-ot.getOnlineTime()*60*1000L);
//					for(HumanMiscEntity hwse : list)
//					{
//						s.add(hwse.getCharId());
//					}
//				}
//				break;
//			}
//			case JpushConst.Condition_type_open_not_recharge_times: 
//			{
//				int times = Integer.parseInt(c.value);
//				List<HumanRechargeOrderEntity> list = Globals.getDaoService().getRechargeOrderDao().selectPlayerByOrderNotChargeTimes(times);
//				for(HumanRechargeOrderEntity hwse : list)
//				{
//					s.add(hwse.getCharId());
//				}
//				break;
//			}
//			case JpushConst.Condition_type_recharge_times: 
//			{
//				int times = Integer.parseInt(c.value);
//				List<HumanRechargeOrderEntity> list = Globals.getDaoService().getRechargeOrderDao().selectPlayerByChargeTimes(times);
//				for(HumanRechargeOrderEntity hwse : list)
//				{
//					s.add(hwse.getCharId());
//				}
//				break;
//			}
//			
//			case JpushConst.Condition_type_update_in_24hours: 
//			{
//				break;
//			}
//			case JpushConst.Condition_type_version: 
//			{
//				break;
//			}
//			}
//			
//			if(passportIds.isEmpty())
//			{
//				passportIds.addAll(s);
//			}
//			else
//			{
//				passportIds.retainAll(s);
//			}
//		}
//		OnlinePlayerService oPlayerService = Globals.getOnlinePlayerService();
//		for(Long passportId : passportIds)
//		{
//			Player p = oPlayerService.getPlayerByPassportId(passportId);
//			if(p!=null)
//			{JpushClientUtil.sendToAlias(String.valueOf(passportId), jpd.title, jpd.title, jpd.content, jpd.params, true, false);}
//			else
//			{JpushClientUtil.sendToAlias(String.valueOf(passportId), jpd.title, jpd.title, jpd.content, jpd.params, false, true);}
//			
//			Loggers.PUSH.info("push to passportId: "+passportId+" for "+jpd.title);
//		}
//	}

}
