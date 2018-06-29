package com.gameserver.vipnew.data;

import com.gameserver.vipnew.HumanVipNew;

/**
 * 
 * @author 郭君伟
 *
 */
public class HumanVipNewInfoData {
	
	private int vipLevel;
	
	private int vipPoint;

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getVipPoint() {
		return vipPoint;
	}

	public void setVipPoint(int vipPoint) {
		this.vipPoint = vipPoint;
	}
	
	/**
	 * 返给客户端数据
	 * @param vip
	 * @return
	 */
	public static HumanVipNewInfoData getVipNewData(HumanVipNew vip){
		
		HumanVipNewInfoData info = new HumanVipNewInfoData();
		info.setVipLevel(vip.getVipLevel());
		info.setVipPoint(vip.getCurPoint());
		return info;
	}
	

}
