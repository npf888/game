package com.gameserver.misc.data;

import com.core.util.ArrayUtils;
import com.gameserver.misc.HumanMisc;



public class HumanMiscFBInfoData {
	private int fbReward;
	private String[] fbInviteList;
	
	private int[] fbInviteRewardList;
	
	private int fbThumb;
	
	private long fbstartTime;
	
	
	/**
	 * @return the fbReward
	 */
	public int getFbReward() {
		return fbReward;
	}
	/**
	 * @param fbReward the fbReward to set
	 */
	public void setFbReward(int fbReward) {
		this.fbReward = fbReward;
	}
	/**
	 * @return the fbInviteList
	 */
	public String[] getFbInviteList() {
		return fbInviteList;
	}
	/**
	 * @param fbInviteList the fbInviteList to set
	 */
	public void setFbInviteList(String[] fbInviteList) {
		this.fbInviteList = fbInviteList;
	}
	/**
	 * @return the fbInviteRewardList
	 */
	public int[] getFbInviteRewardList() {
		return fbInviteRewardList;
	}
	/**
	 * @param fbInviteRewardList the fbInviteRewardList to set
	 */
	public void setFbInviteRewardList(int[] fbInviteRewardList) {
		this.fbInviteRewardList = fbInviteRewardList;
	}
	
	public static HumanMiscFBInfoData convertFromHumanMisc(HumanMisc humanMisc) {
		// TODO Auto-generated method stub
		HumanMiscFBInfoData tempHumanMiscFBInfoData =new HumanMiscFBInfoData();
		tempHumanMiscFBInfoData.setFbReward(humanMisc.getFbReward());
		tempHumanMiscFBInfoData.setFbInviteRewardList(ArrayUtils.intList2Array(humanMisc.getFbInviteRewardList()));
		tempHumanMiscFBInfoData.setFbInviteList(humanMisc.getFbInviteList().toArray(new String[humanMisc.getFbInviteList().size()]));
		tempHumanMiscFBInfoData.setFbThumb(humanMisc.getFbThumb());
		tempHumanMiscFBInfoData.setFbstartTime(humanMisc.getRefreshTime());
		return tempHumanMiscFBInfoData;
	}
	
	public int getFbThumb() {
		return fbThumb;
	}
	public void setFbThumb(int fbThumb) {
		this.fbThumb = fbThumb;
	}
	public long getFbstartTime() {
		return fbstartTime;
	}
	public void setFbstartTime(long fbstartTime) {
		this.fbstartTime = fbstartTime;
	}
	
	
	

}
