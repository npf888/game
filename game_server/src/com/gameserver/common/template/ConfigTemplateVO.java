package com.gameserver.common.template;

import com.core.annotation.ExcelRowBinding;
import java.util.Map;
import com.google.common.collect.Maps;

import com.core.template.TemplateObject;
import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelCellBinding;

/**
 * 全局配置
 * 
 * @author CodeGenerator, don't modify this file please.
 * 
 */
@ExcelRowBinding
public abstract class ConfigTemplateVO extends TemplateObject {

	/** 初始筹码 */
	@ExcelCellBinding(offset = 1)
	protected int initialChips;

	/** 初始钻石 */
	@ExcelCellBinding(offset = 2)
	protected int initialFire;

	/** 初始魅力值 */
	@ExcelCellBinding(offset = 3)
	protected int initialCharm;

	/** 月卡id */
	@ExcelCellBinding(offset = 4)
	protected int monthCardId;

	/** 月卡初始赠送筹码 */
	@ExcelCellBinding(offset = 5)
	protected int monthCardNum;

	/** 月卡每天赠送筹码 */
	@ExcelCellBinding(offset = 6)
	protected int monthCardNumDay;

	/** 周卡id */
	@ExcelCellBinding(offset = 7)
	protected int weekCardId;

	/** 周卡初始赠送筹码 */
	@ExcelCellBinding(offset = 8)
	protected int weekCardNum;

	/** 周卡每天赠送筹码 */
	@ExcelCellBinding(offset = 9)
	protected int weekCardNumDay;

	/** 最大邮件数 */
	@ExcelCellBinding(offset = 10)
	protected int mailNum;

	/** 最长时间 */
	@ExcelCellBinding(offset = 11)
	protected int mailTime;

	/** 默认人数 */
	@ExcelCellBinding(offset = 12)
	protected int defaultNum;

	/** 自动补充筹码 */
	@ExcelCellBinding(offset = 13)
	protected int supplyChips;

	/** 好友数量 */
	@ExcelCellBinding(offset = 14)
	protected int friendsNum;

	/** 好友请求数量 */
	@ExcelCellBinding(offset = 15)
	protected int friendRequestNum;

	/** 好友请求保存时间 */
	@ExcelCellBinding(offset = 16)
	protected int friendRequestTime;

	/** 礼物id */
	@ExcelCellBinding(offset = 17)
	protected int giftId;

	/** 礼物数量 */
	@ExcelCellBinding(offset = 18)
	protected int giftNum;

	/** vip时间 */
	@ExcelCellBinding(offset = 19)
	protected int vipTime;

	/** 聊天间隔 */
	@ExcelCellBinding(offset = 20)
	protected int chatIntervalTime;

	/** 聊天字数 */
	@ExcelCellBinding(offset = 21)
	protected int chatNumLimit;

	/** 改名卡id */
	@ExcelCellBinding(offset = 22)
	protected int changeNameId;

	/** 新手奖励 */
	@ExcelCellBinding(offset = 23)
	protected int newReward;

	/** 礼物数 */
	@ExcelCellBinding(offset = 24)
	protected int dailyGiftNum;

	/** 幸运转盘 */
	@ExcelCellBinding(offset = 25)
	protected int turntableCost;

	/** 幸运匹配 */
	@ExcelCellBinding(offset = 26)
	protected int luckyMatchCost;

	/** fb奖励 */
	@ExcelCellBinding(offset = 27)
	protected int fbItemNum;

	/** 经验加成参数 */
	@ExcelCellBinding(offset = 28)
	protected int lvRatio;

	/** 排行榜vip开启等级 */
	@ExcelCellBinding(offset = 29)
	protected int rankVip;

	/** 角色最大等级 */
	@ExcelCellBinding(offset = 30)
	protected int lvLimit;

	/** fb点赞获取筹码 */
	@ExcelCellBinding(offset = 31)
	protected int fbLikeReward;

	/** 老虎机竞赛持续时间 */
	@ExcelCellBinding(offset = 32)
	protected int durationTime;

	/** 老虎机竞赛等待时间 */
	@ExcelCellBinding(offset = 33)
	protected int waitTime;

	/** FB邀请好友刷新时间 */
	@ExcelCellBinding(offset = 34)
	protected int fbInviteTime;

	/** 转盘刷新时间 */
	@ExcelCellBinding(offset = 35)
	protected int logintime;

	/** 每日任务刷新时间 */
	@ExcelCellBinding(offset = 36)
	protected int daytime;

	/** 推出老虎机时间 */
	@ExcelCellBinding(offset = 37)
	protected int timeout;

	/** 进房间对比参数 */
	@ExcelCellBinding(offset = 38)
	protected int roomcompare;

	/** 俱乐部改名卡模板id */
	@ExcelCellBinding(offset = 39)
	protected int clubChangeNameItemId;

	/** 喇叭道具id */
	@ExcelCellBinding(offset = 40)
	protected int speakerItemId;


	public int getInitialChips() {
		return this.initialChips;
	}


	public final static int getInitialChipsMinLimit() {
		return 0;
	}

	public void setInitialChips(int initialChips) {
		if (initialChips < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[初始筹码]initialChips的值不得小于0");
		}
		this.initialChips = initialChips;
	}
	
	public int getInitialFire() {
		return this.initialFire;
	}


	public final static int getInitialFireMinLimit() {
		return 0;
	}

	public void setInitialFire(int initialFire) {
		if (initialFire < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[初始钻石]initialFire的值不得小于0");
		}
		this.initialFire = initialFire;
	}
	
	public int getInitialCharm() {
		return this.initialCharm;
	}


	public final static int getInitialCharmMinLimit() {
		return 0;
	}

	public void setInitialCharm(int initialCharm) {
		if (initialCharm < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[初始魅力值]initialCharm的值不得小于0");
		}
		this.initialCharm = initialCharm;
	}
	
	public int getMonthCardId() {
		return this.monthCardId;
	}


	public final static int getMonthCardIdMinLimit() {
		return 0;
	}

	public void setMonthCardId(int monthCardId) {
		if (monthCardId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[月卡id]monthCardId的值不得小于0");
		}
		this.monthCardId = monthCardId;
	}
	
	public int getMonthCardNum() {
		return this.monthCardNum;
	}


	public final static int getMonthCardNumMinLimit() {
		return 0;
	}

	public void setMonthCardNum(int monthCardNum) {
		if (monthCardNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[月卡初始赠送筹码]monthCardNum的值不得小于0");
		}
		this.monthCardNum = monthCardNum;
	}
	
	public int getMonthCardNumDay() {
		return this.monthCardNumDay;
	}


	public final static int getMonthCardNumDayMinLimit() {
		return 0;
	}

	public void setMonthCardNumDay(int monthCardNumDay) {
		if (monthCardNumDay < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[月卡每天赠送筹码]monthCardNumDay的值不得小于0");
		}
		this.monthCardNumDay = monthCardNumDay;
	}
	
	public int getWeekCardId() {
		return this.weekCardId;
	}


	public final static int getWeekCardIdMinLimit() {
		return 0;
	}

	public void setWeekCardId(int weekCardId) {
		if (weekCardId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[周卡id]weekCardId的值不得小于0");
		}
		this.weekCardId = weekCardId;
	}
	
	public int getWeekCardNum() {
		return this.weekCardNum;
	}


	public final static int getWeekCardNumMinLimit() {
		return 0;
	}

	public void setWeekCardNum(int weekCardNum) {
		if (weekCardNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[周卡初始赠送筹码]weekCardNum的值不得小于0");
		}
		this.weekCardNum = weekCardNum;
	}
	
	public int getWeekCardNumDay() {
		return this.weekCardNumDay;
	}


	public final static int getWeekCardNumDayMinLimit() {
		return 0;
	}

	public void setWeekCardNumDay(int weekCardNumDay) {
		if (weekCardNumDay < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[周卡每天赠送筹码]weekCardNumDay的值不得小于0");
		}
		this.weekCardNumDay = weekCardNumDay;
	}
	
	public int getMailNum() {
		return this.mailNum;
	}


	public final static int getMailNumMinLimit() {
		return 1;
	}

	public void setMailNum(int mailNum) {
		if (mailNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[最大邮件数]mailNum的值不得小于1");
		}
		this.mailNum = mailNum;
	}
	
	public int getMailTime() {
		return this.mailTime;
	}


	public final static int getMailTimeMinLimit() {
		return 1;
	}

	public void setMailTime(int mailTime) {
		if (mailTime < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[最长时间]mailTime的值不得小于1");
		}
		this.mailTime = mailTime;
	}
	
	public int getDefaultNum() {
		return this.defaultNum;
	}


	public final static int getDefaultNumMinLimit() {
		return 1;
	}

	public void setDefaultNum(int defaultNum) {
		if (defaultNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[默认人数]defaultNum的值不得小于1");
		}
		this.defaultNum = defaultNum;
	}
	
	public int getSupplyChips() {
		return this.supplyChips;
	}


	public final static int getSupplyChipsMinLimit() {
		return 1;
	}

	public void setSupplyChips(int supplyChips) {
		if (supplyChips < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[自动补充筹码]supplyChips的值不得小于1");
		}
		this.supplyChips = supplyChips;
	}
	
	public int getFriendsNum() {
		return this.friendsNum;
	}


	public final static int getFriendsNumMinLimit() {
		return 1;
	}

	public void setFriendsNum(int friendsNum) {
		if (friendsNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[好友数量]friendsNum的值不得小于1");
		}
		this.friendsNum = friendsNum;
	}
	
	public int getFriendRequestNum() {
		return this.friendRequestNum;
	}


	public final static int getFriendRequestNumMinLimit() {
		return 1;
	}

	public void setFriendRequestNum(int friendRequestNum) {
		if (friendRequestNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[好友请求数量]friendRequestNum的值不得小于1");
		}
		this.friendRequestNum = friendRequestNum;
	}
	
	public int getFriendRequestTime() {
		return this.friendRequestTime;
	}


	public final static int getFriendRequestTimeMinLimit() {
		return 1;
	}

	public void setFriendRequestTime(int friendRequestTime) {
		if (friendRequestTime < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[好友请求保存时间]friendRequestTime的值不得小于1");
		}
		this.friendRequestTime = friendRequestTime;
	}
	
	public int getGiftId() {
		return this.giftId;
	}


	public final static int getGiftIdMinLimit() {
		return 1;
	}

	public void setGiftId(int giftId) {
		if (giftId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[礼物id]giftId的值不得小于1");
		}
		this.giftId = giftId;
	}
	
	public int getGiftNum() {
		return this.giftNum;
	}


	public final static int getGiftNumMinLimit() {
		return 1;
	}

	public void setGiftNum(int giftNum) {
		if (giftNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[礼物数量]giftNum的值不得小于1");
		}
		this.giftNum = giftNum;
	}
	
	public int getVipTime() {
		return this.vipTime;
	}


	public final static int getVipTimeMinLimit() {
		return 1;
	}

	public void setVipTime(int vipTime) {
		if (vipTime < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[vip时间]vipTime的值不得小于1");
		}
		this.vipTime = vipTime;
	}
	
	public int getChatIntervalTime() {
		return this.chatIntervalTime;
	}


	public final static int getChatIntervalTimeMinLimit() {
		return 1;
	}

	public void setChatIntervalTime(int chatIntervalTime) {
		if (chatIntervalTime < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[聊天间隔]chatIntervalTime的值不得小于1");
		}
		this.chatIntervalTime = chatIntervalTime;
	}
	
	public int getChatNumLimit() {
		return this.chatNumLimit;
	}


	public final static int getChatNumLimitMinLimit() {
		return 1;
	}

	public void setChatNumLimit(int chatNumLimit) {
		if (chatNumLimit < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[聊天字数]chatNumLimit的值不得小于1");
		}
		this.chatNumLimit = chatNumLimit;
	}
	
	public int getChangeNameId() {
		return this.changeNameId;
	}


	public final static int getChangeNameIdMinLimit() {
		return 1;
	}

	public void setChangeNameId(int changeNameId) {
		if (changeNameId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[改名卡id]changeNameId的值不得小于1");
		}
		this.changeNameId = changeNameId;
	}
	
	public int getNewReward() {
		return this.newReward;
	}


	public final static int getNewRewardMinLimit() {
		return 1;
	}

	public void setNewReward(int newReward) {
		if (newReward < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[新手奖励]newReward的值不得小于1");
		}
		this.newReward = newReward;
	}
	
	public int getDailyGiftNum() {
		return this.dailyGiftNum;
	}


	public final static int getDailyGiftNumMinLimit() {
		return 1;
	}

	public void setDailyGiftNum(int dailyGiftNum) {
		if (dailyGiftNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[礼物数]dailyGiftNum的值不得小于1");
		}
		this.dailyGiftNum = dailyGiftNum;
	}
	
	public int getTurntableCost() {
		return this.turntableCost;
	}


	public final static int getTurntableCostMinLimit() {
		return 1;
	}

	public void setTurntableCost(int turntableCost) {
		if (turntableCost < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					26, "[幸运转盘]turntableCost的值不得小于1");
		}
		this.turntableCost = turntableCost;
	}
	
	public int getLuckyMatchCost() {
		return this.luckyMatchCost;
	}


	public final static int getLuckyMatchCostMinLimit() {
		return 1;
	}

	public void setLuckyMatchCost(int luckyMatchCost) {
		if (luckyMatchCost < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[幸运匹配]luckyMatchCost的值不得小于1");
		}
		this.luckyMatchCost = luckyMatchCost;
	}
	
	public int getFbItemNum() {
		return this.fbItemNum;
	}


	public final static int getFbItemNumMinLimit() {
		return 1;
	}

	public void setFbItemNum(int fbItemNum) {
		if (fbItemNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[fb奖励]fbItemNum的值不得小于1");
		}
		this.fbItemNum = fbItemNum;
	}
	
	public int getLvRatio() {
		return this.lvRatio;
	}


	public final static int getLvRatioMinLimit() {
		return 1;
	}

	public void setLvRatio(int lvRatio) {
		if (lvRatio < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					29, "[经验加成参数]lvRatio的值不得小于1");
		}
		this.lvRatio = lvRatio;
	}
	
	public int getRankVip() {
		return this.rankVip;
	}


	public final static int getRankVipMinLimit() {
		return 0;
	}

	public void setRankVip(int rankVip) {
		if (rankVip < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					30, "[排行榜vip开启等级]rankVip的值不得小于0");
		}
		this.rankVip = rankVip;
	}
	
	public int getLvLimit() {
		return this.lvLimit;
	}


	public final static int getLvLimitMinLimit() {
		return 1;
	}

	public void setLvLimit(int lvLimit) {
		if (lvLimit < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					31, "[角色最大等级]lvLimit的值不得小于1");
		}
		this.lvLimit = lvLimit;
	}
	
	public int getFbLikeReward() {
		return this.fbLikeReward;
	}


	public final static int getFbLikeRewardMinLimit() {
		return 1;
	}

	public void setFbLikeReward(int fbLikeReward) {
		if (fbLikeReward < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					32, "[fb点赞获取筹码]fbLikeReward的值不得小于1");
		}
		this.fbLikeReward = fbLikeReward;
	}
	
	public int getDurationTime() {
		return this.durationTime;
	}


	public final static int getDurationTimeMinLimit() {
		return 1;
	}

	public void setDurationTime(int durationTime) {
		if (durationTime < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					33, "[老虎机竞赛持续时间]durationTime的值不得小于1");
		}
		this.durationTime = durationTime;
	}
	
	public int getWaitTime() {
		return this.waitTime;
	}


	public final static int getWaitTimeMinLimit() {
		return 1;
	}

	public void setWaitTime(int waitTime) {
		if (waitTime < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					34, "[老虎机竞赛等待时间]waitTime的值不得小于1");
		}
		this.waitTime = waitTime;
	}
	
	public int getFbInviteTime() {
		return this.fbInviteTime;
	}


	public final static int getFbInviteTimeMinLimit() {
		return 1;
	}

	public void setFbInviteTime(int fbInviteTime) {
		if (fbInviteTime < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					35, "[FB邀请好友刷新时间]fbInviteTime的值不得小于1");
		}
		this.fbInviteTime = fbInviteTime;
	}
	
	public int getLogintime() {
		return this.logintime;
	}


	public final static int getLogintimeMinLimit() {
		return 1;
	}

	public void setLogintime(int logintime) {
		if (logintime < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					36, "[转盘刷新时间]logintime的值不得小于1");
		}
		this.logintime = logintime;
	}
	
	public int getDaytime() {
		return this.daytime;
	}


	public final static int getDaytimeMinLimit() {
		return 1;
	}

	public void setDaytime(int daytime) {
		if (daytime < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					37, "[每日任务刷新时间]daytime的值不得小于1");
		}
		this.daytime = daytime;
	}
	
	public int getTimeout() {
		return this.timeout;
	}


	public final static int getTimeoutMinLimit() {
		return 1;
	}

	public void setTimeout(int timeout) {
		if (timeout < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					38, "[推出老虎机时间]timeout的值不得小于1");
		}
		this.timeout = timeout;
	}
	
	public int getRoomcompare() {
		return this.roomcompare;
	}


	public final static int getRoomcompareMinLimit() {
		return 1;
	}

	public void setRoomcompare(int roomcompare) {
		if (roomcompare < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					39, "[进房间对比参数]roomcompare的值不得小于1");
		}
		this.roomcompare = roomcompare;
	}
	
	public int getClubChangeNameItemId() {
		return this.clubChangeNameItemId;
	}


	public final static int getClubChangeNameItemIdMinLimit() {
		return 1;
	}

	public void setClubChangeNameItemId(int clubChangeNameItemId) {
		if (clubChangeNameItemId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					40, "[俱乐部改名卡模板id]clubChangeNameItemId的值不得小于1");
		}
		this.clubChangeNameItemId = clubChangeNameItemId;
	}
	
	public int getSpeakerItemId() {
		return this.speakerItemId;
	}


	public final static int getSpeakerItemIdMinLimit() {
		return 1;
	}

	public void setSpeakerItemId(int speakerItemId) {
		if (speakerItemId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					41, "[喇叭道具id]speakerItemId的值不得小于1");
		}
		this.speakerItemId = speakerItemId;
	}
	

	/** 模板字典 */
	protected final static Map<Integer, ConfigTemplateVO> _templates = Maps.newTreeMap();

	@Override
	public void check() 
		throws TemplateConfigException {
	}

	@SuppressWarnings("unchecked")
	public static <T extends ConfigTemplateVO> T getTemplate(int templateID) {
		return  (T)_templates.get(templateID);
	}

	/**
	 * 获取模板列表
	 * 
	 */
	public final static Map<Integer, ConfigTemplateVO> getTemplates() {
		return _templates;
	}

	@Override
	public String toString() {
		return "ConfigTemplateVO [  initialChips=" + initialChips + ", initialFire=" + initialFire + ", initialCharm=" + initialCharm + ", monthCardId=" + monthCardId + ", monthCardNum=" + monthCardNum + ", monthCardNumDay=" + monthCardNumDay + ", weekCardId=" + weekCardId + ", weekCardNum=" + weekCardNum + ", weekCardNumDay=" + weekCardNumDay + ", mailNum=" + mailNum + ", mailTime=" + mailTime + ", defaultNum=" + defaultNum + ", supplyChips=" + supplyChips + ", friendsNum=" + friendsNum + ", friendRequestNum=" + friendRequestNum + ", friendRequestTime=" + friendRequestTime + ", giftId=" + giftId + ", giftNum=" + giftNum + ", vipTime=" + vipTime + ", chatIntervalTime=" + chatIntervalTime + ", chatNumLimit=" + chatNumLimit + ", changeNameId=" + changeNameId + ", newReward=" + newReward + ", dailyGiftNum=" + dailyGiftNum + ", turntableCost=" + turntableCost + ", luckyMatchCost=" + luckyMatchCost + ", fbItemNum=" + fbItemNum + ", lvRatio=" + lvRatio + ", rankVip=" + rankVip + ", lvLimit=" + lvLimit + ", fbLikeReward=" + fbLikeReward + ", durationTime=" + durationTime + ", waitTime=" + waitTime + ", fbInviteTime=" + fbInviteTime + ", logintime=" + logintime + ", daytime=" + daytime + ", timeout=" + timeout + ", roomcompare=" + roomcompare + ", clubChangeNameItemId=" + clubChangeNameItemId + ", speakerItemId=" + speakerItemId + ",]";
	}
}