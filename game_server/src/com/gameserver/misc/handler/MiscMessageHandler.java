package com.gameserver.misc.handler;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.common.LogReasons;
import com.common.constants.LangConstants;
import com.common.constants.Loggers;
import com.core.util.Assert;
import com.core.util.TimeUtils;
import com.gameserver.common.CommonLogic;
import com.gameserver.common.Globals;
import com.gameserver.common.data.RandRewardData;
import com.gameserver.currency.Currency;
import com.gameserver.human.Human;
import com.gameserver.human.manager.HumanMiscManager;
import com.gameserver.misc.msg.CGFbGetReward;
import com.gameserver.misc.msg.CGFbInvite;
import com.gameserver.misc.msg.CGFbInviteGetReward;
import com.gameserver.misc.msg.CGFbThumbReward;
import com.gameserver.misc.msg.CGKoreanSb;
import com.gameserver.misc.msg.CGNewUser;
import com.gameserver.misc.msg.CGOnlineReward;
import com.gameserver.misc.msg.GCFbGetReward;
import com.gameserver.misc.msg.GCFbInvite;
import com.gameserver.misc.msg.GCFbInviteGetReward;
import com.gameserver.misc.msg.GCFbThumbReward;
import com.gameserver.misc.msg.GCKoreanSb;
import com.gameserver.misc.msg.GCMiscInfoData;
import com.gameserver.misc.msg.GCNewUser;
import com.gameserver.misc.msg.GCOnlineReward;
import com.gameserver.misc.template.FbInviteTemplate;
import com.gameserver.misc.template.OnlineRewardTemplate;
import com.gameserver.player.Player;

/**
 * misc
 * @author wayne
 *
 */
public class MiscMessageHandler  {
	
	private final int SB_KOREAN = 100;
	private Logger logger = Loggers.miscLogger;
	
	public void handleOnlineReward(Player player, CGOnlineReward cgOnlineReward) {
		// TODO Auto-generated method stub
	
		Human human = player.getHuman();
		Assert.notNull(human);
		HumanMiscManager humanMiscManager = human.getHumanMiscManager();
		humanMiscManager.checkIfRefresh();
		
		//检查在线奖励
		int currentOnlineRewardIndex = humanMiscManager.getHumanMisc().getCurrentOnlineRewardId();
		
		OnlineRewardTemplate onlineRewardTemplate = Globals.getMiscService().onlineRewardTemplate(currentOnlineRewardIndex);
	
		//没有可领的在线奖励了
		if(onlineRewardTemplate == null){
			
			logger.warn("玩家["+human.getPassportId()+"]已经领完在线奖励");
			player.sendSystemMessage(LangConstants.ONLINE_REWAR_ALREADY_TAKE);
			return;
		}
		
		//判断在线时长
		//long totalTime = Globals.getTimeService().now()- humanMiscManager.getHumanMisc().getLastGetTime()+humanMiscManager.getHumanMisc().getOnlineTime();
		long totalTime = Globals.getTimeService().now()- humanMiscManager.getHumanMisc().getLastGetTime();
		//if(totalTime<onlineRewardTemplate.getOnlineTime()*TimeUtils.MIN)
		if(totalTime < onlineRewardTemplate.getOnlineTime()*TimeUtils.MIN){
			
			logger.warn("玩家["+human.getPassportId()+"]领取奖励id["+onlineRewardTemplate.getId()+"]时间不足");
			player.sendSystemMessage(LangConstants.TIME_NO_ENOUGH);
			return;
		}
		
		//领取奖励
		humanMiscManager.getCurrentOnlineReward(onlineRewardTemplate.getId());
		
		//发送奖励物品
		List<RandRewardData> randRewarDataList = new ArrayList<RandRewardData>();
		randRewarDataList.add(onlineRewardTemplate.getReward());
		CommonLogic.getInstance().giveRandReward(human, randRewarDataList, LogReasons.GoldLogReason.ONLINE_REWARD, LogReasons.DiamondLogReason.ONLINE_REWARD, LogReasons.CharmLogReason.ONLINE_REWARD,LogReasons.ItemLogReason.ONLINE_REWARD, false);
		
		GCOnlineReward gcOnlineReward = new GCOnlineReward();
		gcOnlineReward.setReward(onlineRewardTemplate.getReward());
		player.sendMessage(gcOnlineReward);
		
		//misc info
		GCMiscInfoData gcMiscInfoData =	humanMiscManager.buildGCMiscInfoData();
		player.sendMessage(gcMiscInfoData);
		
		Globals.getLogService().sendOnlineTimeRewardLog(human, LogReasons.OnlineTimeRewardLogReason.GET_ONLINE_REWARD, LogReasons.OnlineTimeRewardLogReason.GET_ONLINE_REWARD.getReasonText(), currentOnlineRewardIndex);
	}
	
	/**
	 * 新手引导完成
	 * @param player
	 * @param cgNewUser
	 */
	public void handleNewUser(Player player, CGNewUser cgNewUser) {
		// TODO Auto-generated method stub

		Human human = player.getHuman();
		Assert.notNull(human);
		HumanMiscManager humanMiscManager = human.getHumanMiscManager();
	
		if(!humanMiscManager.ifNewUser()){
			logger.warn("玩家["+human.getPassportId()+"]不是新手，不能领取奖励");
			return;
		}
		
		int newUserReward = Globals.getConfigTempl().getNewReward();
		humanMiscManager.finishNewUser();
		String detailReason = MessageFormat.format( LogReasons.GoldLogReason.NEW_USER_REWARD.getReasonText(), newUserReward);
		human.giveMoney(newUserReward, Currency.GOLD, true, LogReasons.GoldLogReason.NEW_USER_REWARD, detailReason, -1, 1);
		GCNewUser gcNewUser = new GCNewUser();
		human.sendMessage(gcNewUser);
		human.sendMessage(humanMiscManager.buildGCMiscInfoData());
		logger.debug("玩家["+human.getPassportId()+"]领取新手奖励");
	
	}
	
	/**
	 * 韩国sb推广方式
	 * @param player
	 * @param cgKoreanSb
	 */
	public void handleKoreanSb(Player player, CGKoreanSb cgKoreanSb) {
		// TODO Auto-generated method stub
		Human human =player.getHuman();
		
		int rewardNum = cgKoreanSb.getRewardNum();
		if(rewardNum <=0)
			return;
		if(rewardNum>SB_KOREAN)
			return;
		if(human.getHumanMiscManager().getHumanMisc().getAdRewards() > SB_KOREAN){
			return;
		}
		
		human.getHumanMiscManager().getHumanMisc().setAdRewards(human.getHumanMiscManager().getHumanMisc().getAdRewards()+rewardNum);
		human.getHumanMiscManager().getHumanMisc().setModified();
		
		human.giveDiamond(rewardNum, false, LogReasons.DiamondLogReason.KOREAN_SB, LogReasons.DiamondLogReason.KOREAN_SB.getReasonText(), -1, 1);
		GCKoreanSb gcKoreanSb = new GCKoreanSb();
		gcKoreanSb.setRewardNum(rewardNum);
		player.sendMessage(gcKoreanSb);
	}
	
	/**
	 * 
	 * @param player
	 * @param cgFbInvite
	 */
	public void handleFbInvite(Player player, CGFbInvite cgFbInvite) {
		// TODO Auto-generated method stub
		Human human =player.getHuman();
		if(human ==null){
			return;
		}
		
		HumanMiscManager humanMiscManager =  human.getHumanMiscManager();
		humanMiscManager.checkIfRefresh();
		//check if add
		for(String fbId : cgFbInvite.getFbInviteIdList()){
			if(humanMiscManager.getHumanMisc().getFbInviteList().contains(fbId)){
				return;
			}
		}
		
		for(String fbId : cgFbInvite.getFbInviteIdList()){
			if(fbId != null && !fbId.trim().equals("")){
				humanMiscManager.getHumanMisc().getFbInviteList().add(fbId);
				humanMiscManager.getHumanMisc().setModified();
			}
		}
		
		GCFbInvite gcFbInvite = new GCFbInvite();
		gcFbInvite.setFbInviteIdList(cgFbInvite.getFbInviteIdList());
		player.sendMessage(gcFbInvite);
		
		player.sendMessage(humanMiscManager.buildGCMiscFBInfoData());
		/*human.getHumanAchievementManager().updateFaceBook();*/
		
		//无双吹牛 邀请好友
		Globals.getHumanBazooTaskService().inviteFriend(player);
	}
	
	/**
	 * 邀请奖励
	 * @param player
	 * @param cgFbInviteGetReward
	 */
	public void handleFbInviteGetReward(Player player,
			CGFbInviteGetReward cgFbInviteGetReward) {
		// TODO Auto-generated method stub
		Human human =player.getHuman();
		
		if(human ==null){
			return;
		}
		
		HumanMiscManager humanMiscManager =  human.getHumanMiscManager();
		humanMiscManager.checkIfRefresh();
		
		int rId = cgFbInviteGetReward.getRewardId();
		//越界
		if (rId<0 || rId>=Globals.getMiscService().getFbInviteTemplateList().size()){
			return;
		}
		
		
		//判断是否领取过奖励了
		if(humanMiscManager.ifGetInviteRewardById(rId)){
			return;
		}
		
		FbInviteTemplate fbInviteTemplate = Globals.getMiscService().getFbInviteTemplateList().get(rId);
		if(fbInviteTemplate == null){
			return;
		}
		
		//判断是否有足够人数
		if(humanMiscManager.numOfFBInvite() < fbInviteTemplate.getGamerNum()){
			return;
		}
		
		//给奖励
		List<RandRewardData> tempRandRewardDataList = new ArrayList<RandRewardData>();
		tempRandRewardDataList.add(fbInviteTemplate.getReward());
		
		CommonLogic.getInstance().giveRandReward(human, tempRandRewardDataList, LogReasons.GoldLogReason.FB_INVITE, LogReasons.DiamondLogReason.FB_INVITE, LogReasons.CharmLogReason.FB_INVITE, LogReasons.ItemLogReason.FB_INVITE, true);
		//记录领奖
		humanMiscManager.recordGetInviteRewardById(rId);
		
		GCFbInviteGetReward gcFbInviteGetReward = new GCFbInviteGetReward();
		gcFbInviteGetReward.setRandRewardList(tempRandRewardDataList.toArray(new RandRewardData[tempRandRewardDataList.size()]));
		player.sendMessage(gcFbInviteGetReward);
		
		player.sendMessage(humanMiscManager.buildGCMiscFBInfoData());
	}
	
	/**
	 * 获得奖励
	 * @param player
	 * @param cgFbGetReward
	 */
	public void handleFbGetReward(Player player, CGFbGetReward cgFbGetReward) {
		// TODO Auto-generated method stub
		Human human =player.getHuman();
		if(human ==null){
			return;
		}
		
		//判断是不是fb
		if(player.getFacebookId()==null || player.getFacebookId().length()==0)
			return;
		
		HumanMiscManager humanMiscManager =  human.getHumanMiscManager();
		humanMiscManager.checkIfRefresh();
		if(humanMiscManager.ifGetFbReward()){
			return;
		}
		//设置领奖
		humanMiscManager.getFbReward();
		player.sendMessage(humanMiscManager.buildGCMiscFBInfoData());
		//发送奖励
		CommonLogic.getInstance().giveRandReward(human, Globals.getConfigTempl().getFbRewardList(), LogReasons.GoldLogReason.FB_REWARD, LogReasons.DiamondLogReason.FB_REWARD, LogReasons.CharmLogReason.FB_REWARD, LogReasons.ItemLogReason.FB_REWARD, false);
		GCFbGetReward gcFbGetReward = new GCFbGetReward();
		gcFbGetReward.setRandRewardList(Globals.getConfigTempl().getFbRewardList().toArray(new RandRewardData[Globals.getConfigTempl().getFbRewardList().size()]));
		player.sendMessage(gcFbGetReward);
		
	}
    
	/**
	 * 点赞奖励
	 * @param player
	 * @param cgFbThumbReward
	 */
	public void handleFbThumbReward(Player player, CGFbThumbReward cgFbThumbReward) {

		Human human = player.getHuman();
		if (human == null) {
			return;
		}

		// 判断是不是fb
		if (player.getFacebookId() == null || player.getFacebookId().length() == 0)
			return;

		HumanMiscManager humanMiscManager = human.getHumanMiscManager();
		
		if (humanMiscManager.ifFbThumb()) {
			return;
		}
		// 设置领奖
		humanMiscManager.getFbThumb();
		player.sendMessage(humanMiscManager.buildGCMiscFBInfoData());
		
		// 发送奖励
		List<RandRewardData> randRewardDataList = new ArrayList<RandRewardData>();
		RandRewardData data = new RandRewardData();
		data.setRewardId(3);
		data.setRewardCount(Globals.getConfigTempl().getFbLikeReward());
		randRewardDataList.add(data);
		CommonLogic.getInstance().giveRandReward(human,randRewardDataList,LogReasons.GoldLogReason.FB_REWARD, LogReasons.DiamondLogReason.FB_REWARD,
				LogReasons.CharmLogReason.FB_REWARD, LogReasons.ItemLogReason.FB_REWARD, false);
		
		GCFbThumbReward gCFbThumbReward = new GCFbThumbReward();
		gCFbThumbReward.setRandRewardList(randRewardDataList.toArray(new RandRewardData[randRewardDataList.size()]));
		player.sendMessage(gCFbThumbReward);

	}
	

	
}
