package com.gameserver.vip;

import java.util.HashMap;
import java.util.Map;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.gameserver.common.Globals;
import com.gameserver.vip.template.VipRoomTemplate;
import com.gameserver.vip.template.VipTemplate;

/**
 * vip service
 * @author wayne
 *
 */
public class VipService implements InitializeRequired,AfterInitializeRequired{
	

	private Map<Integer,VipTemplate> vipTemplateMap = new HashMap<Integer,VipTemplate>();
	private Map<Integer,VipRoomTemplate> vipRoomTemplateMap = new HashMap<Integer,VipRoomTemplate>();
	
	@Override
	public void afterInit() {
		// TODO Auto-generated method stub
		for(VipTemplate vipTempalte : Globals.getTemplateService().getAll(VipTemplate.class).values()){
			vipTemplateMap.put(vipTempalte.getVipLv(), vipTempalte);
		}
		for(VipRoomTemplate vipRoomTemplate : Globals.getTemplateService().getAll(VipRoomTemplate.class).values()){
			vipRoomTemplateMap.put(vipRoomTemplate.getId(), vipRoomTemplate);
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	
	}
	
	public VipTemplate getVipTemplateByLevel(int level){
		return vipTemplateMap.get(level);
	}
	
	public VipRoomTemplate getVipRoomTemplateById(int vipId){
		return vipRoomTemplateMap.get(vipId);
	}
}
