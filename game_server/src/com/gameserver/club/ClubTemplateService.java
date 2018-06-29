package com.gameserver.club;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.gameserver.club.template.ClubListTemplate;
import com.gameserver.club.template.ClubRechargeTemplate;
import com.gameserver.club.template.ClubTemplate;
import com.gameserver.club.template.UnlockLevelTemplate;
import com.gameserver.common.Globals;

public class ClubTemplateService implements InitializeRequired {
	private static final Logger logger = LoggerFactory.getLogger(ClubTemplateService.class);
	
	public static UnlockLevelTemplate UNLOCK = null;
	private static final Map<Integer,ClubTemplate>  LEVEL_CONFIG_PAIRS = new HashMap<Integer,ClubTemplate>();
	private static final Map<Integer,ClubRechargeTemplate>  GIFTS = new HashMap<Integer,ClubRechargeTemplate>();
	public final List<ClubListTemplate> HUO_YUE_LIST = new ArrayList<>();
	public final List<ClubListTemplate> GONG_XIAN_LIST = new ArrayList<>();
	@Override
	public void init() {}
/*	@Override
	public void init() {
		
		TemplateService templateService = Globals.getTemplateService();
		LEVEL_CONFIG_PAIRS.clear();
		LEVEL_CONFIG_PAIRS.putAll(templateService.getAll(ClubTemplate.class));
		logger.info("俱乐部模板服务加载 " + LEVEL_CONFIG_PAIRS.size() + " ClubTemplate 数据");

		GIFTS.clear();
		GIFTS.putAll(templateService.getAll(ClubRechargeTemplate.class));
		logger.info("俱乐部模板服务加载 " + GIFTS.size() + " ClubRechargeTemplate 数据");
		
		UNLOCK = Globals.getTemplateService().get(1, UnlockLevelTemplate.class);//只有一行记录， id为1
		
		Map<Integer, ClubListTemplate> map = templateService.getAll(ClubListTemplate.class);
		for(ClubListTemplate ct : map.values())
		{
			if(ct.getRankListId()==1)
			{
				HUO_YUE_LIST.add(ct);
			}
			else
			{
				GONG_XIAN_LIST.add(ct);
			}
		}
	}*/

	/**
	 *根据等级取配置
	 * @param level
	 * @return
	 */
	public ClubTemplate getClubTemplateByLevel(int level) {
		return LEVEL_CONFIG_PAIRS.get(level);
	}
	
	/**
	 * 是否到达最大等级
	 * @return
	 */
	public boolean isMaxClubLevel(int currentLevel) {
		if(currentLevel > 0 && LEVEL_CONFIG_PAIRS.get(currentLevel + 1) == null) {
			return true;
		}
		
		return false;
	}

	/**
	 * 俱乐部礼物模板
	 * @return
	 */
	public ClubRechargeTemplate getClubRechargeTemplate(int pid){
		return GIFTS.get(pid);
	}
}
