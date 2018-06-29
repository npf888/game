package com.robot.slotother;

import org.slf4j.Logger;

import com.common.constants.Loggers;
import com.core.msg.IMessage;
import com.gameserver.activity.data.HumanActivitySmallData;
import com.gameserver.activity.msg.CGActivityProgress;
import com.gameserver.activity.msg.CGGetActivityReward;
import com.gameserver.activity.msg.GCActivityList;
import com.gameserver.activity.msg.GCGetActivityReward;
import com.gameserver.activity.msg.GCHunamnProgress;
import com.gameserver.lobby.msg.CGNewJackpot;
import com.gameserver.mail.data.MailInfoData;
import com.gameserver.mail.enums.MailTypeEnum;
import com.gameserver.mail.msg.CGLoadMailList;
import com.gameserver.mail.msg.GCLoadMailList;
import com.gameserver.slot.msg.CGSlotEnter;
import com.robot.Robot;

/**
 * 老虎机里其他的接口
 * 
 * 例如 活动  、邮件 什么的
 * @author JavaServer
 *
 */
public class SlotOther {
	protected Logger logger = Loggers.slotOtherLogger;
	/**
	 * 邮件
	 * @param robot
	 */
	//发送获取邮件列表的请求
	public void getMailReq(Robot robot){
		CGLoadMailList message = new CGLoadMailList();
		message.setMailKind(MailTypeEnum.COMPENSATION.getIndex());
		robot.sendMessage(message);
	}
	
	//查看邮件的list
	public void getMailResp(IMessage message){
		if(message instanceof GCLoadMailList){
			GCLoadMailList gcLoadMailList = (GCLoadMailList)message;
	//		int mailKind = gcLoadMailList.getMailKind();
			MailInfoData[] mailInfoDataList = gcLoadMailList.getMailInfoDataList();
			if(mailInfoDataList != null && mailInfoDataList.length>0){
				for(int i=0;i<mailInfoDataList.length;i++){
					MailInfoData mailInfoData = mailInfoDataList[i];
					String title = mailInfoData.getMailTitle();
					int mailStatus = mailInfoData.getMailStatus();
					int isRead = mailInfoData.getIsRead();
					logger.info("获取的活动的 标题：：---------------------------"+title);
					logger.info("获取的活动的 状态：：---------------------------"+mailStatus);
					logger.info("获取的活动的 是否已读 ：：---------------------------"+isRead);
				}
			}
		
		}
	}
	/**
	 * 活动
	 * @param robot
	 */
	public void getActivityReq(Robot robot){
		CGActivityProgress message = new CGActivityProgress();
		robot.sendMessage(message);
	}
	
	//查看邮件的list
	public void getActivityResp(Robot robot ,IMessage message){
		//返回活动列表信息
		if(message instanceof GCActivityList){
			GCActivityList gCActivityList = (GCActivityList)message;
			System.out.print(gCActivityList.getActivityDataList());
		}
		//返回个人活动完成情况
		if(message instanceof GCHunamnProgress){
			GCHunamnProgress gCHunamnProgress = (GCHunamnProgress)message;
			logger.info("----活动列表------："+gCHunamnProgress);
			if(gCHunamnProgress != null && gCHunamnProgress.getHumanActivitySmallData() != null ){
				HumanActivitySmallData[] humanActivitySmallData = gCHunamnProgress.getHumanActivitySmallData();
				for(int i=0;i<humanActivitySmallData.length;i++){
					HumanActivitySmallData one = humanActivitySmallData[i];
					CGGetActivityReward cGGetActivityReward = new CGGetActivityReward();
					cGGetActivityReward.setActivityId(one.getActivityId());
					int[] smallValues = one.getSmallValue();
					for(int j=0;j<smallValues.length;j++){
						int smallValue = one.getSmallValue()[j];
						cGGetActivityReward.setIndexId(smallValue);
						robot.sendMessage(cGGetActivityReward);
					}
				}
			}
			//去领取活动
		}
		//返回活动列表信息
		if(message instanceof GCGetActivityReward){
			GCGetActivityReward gCGetActivityReward = (GCGetActivityReward)message;
			long activityId = gCGetActivityReward.getActivityId();
			int indexId = gCGetActivityReward.getIndexId();
			int result = gCGetActivityReward.getResult();
			logger.info("activityId ---- :"+activityId+" -----indexId--- : "+indexId+" -----result----- ::"+result);
		}
	}
	
	//彩金接口
	
	public void jackpotRequest(Robot robot){
		CGSlotEnter message = new CGSlotEnter();
		message.setSlotId(1);
		robot.sendMessage(message);
		
		
		
	}
	
	public void afterEnterSLot(Robot robot){
		
		CGNewJackpot cGNewJackpot = new CGNewJackpot();
		cGNewJackpot.setBet(50);
		robot.sendMessage(cGNewJackpot);
	}
}
