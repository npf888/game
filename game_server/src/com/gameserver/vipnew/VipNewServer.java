package com.gameserver.vipnew;

import java.util.HashMap;
import java.util.Map;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.vipnew.template.VipNewTemplate;

/**
 * 
 * @author 郭君伟
 *
 */
public class VipNewServer implements InitializeRequired, AfterInitializeRequired {
	
	/**
	 * kek:Vip等级      value:Vip模板数据
	 */
	private Map<Integer,VipNewTemplate> vipNewMap = new HashMap<Integer,VipNewTemplate>(); 

	

	@Override
	public void init() {
		       
		Map<Integer, VipNewTemplate> mapData = Globals.getTemplateService().getAll(VipNewTemplate.class);
		
		for(VipNewTemplate data : mapData.values()){
			vipNewMap.put(data.getVipLv(), data);
		}
		
	}
	
	public VipNewTemplate getVipNewTemplate(int vipLevel){
		return vipNewMap.get(vipLevel);
	}
	
	/**
	 * 计算VIP 点数加成
	 * @param level
	 * @param vipPoint
	 * @return
	 */
	public int getVipPointAddByLv(int level,int vipPoint){
		int vipPointRatio = vipNewMap.get(level).getVipPointRatio();
		return vipPointRatio*vipPoint;
	}
	
	/**
	 * 计算VIP 筹码加成
	 * @param level
	 * @param gold
	 * @return
	 */
	public long getBuyGoldBylv(int level,long gold){
		float buyingRatio = vipNewMap.get(level).getBuyingRatio()/100;
		Float f = gold*buyingRatio;
		return f.longValue();
	}
	
	/**
	 * 计算VIP 签到加成
	 * @param level
	 * @param gold
	 * @return
	 */
	public int getSiginRatio(int level,int gold){
		 int SiginRatio = vipNewMap.get(level).getSiginRatio();
		 return gold * SiginRatio;
	}
	
	
	/**
	 * 获取大转盘加成
	 * @param vipLevel
	 * @return
	 */
	public int getBigSpin(int vipLevel,int login){
		if(vipNewMap.containsKey(vipLevel)){
			return vipNewMap.get(vipLevel).getDailyreward()*login;
		}
		return 0;
	}
	
	
	
	
	@Override
	public void afterInit() {}

}
