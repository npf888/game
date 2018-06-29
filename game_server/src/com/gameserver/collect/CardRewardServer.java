package com.gameserver.collect;

import java.util.HashMap;
import java.util.Map;

import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.gameserver.collect.template.CardRewardTemplate;
import com.gameserver.common.Globals;

/**
 * 兑换卡片
 * @author 郭君伟
 *
 */
public class CardRewardServer implements InitializeRequired {

	
	private Map<Integer,CardRewardTemplate>  map = new HashMap<Integer,CardRewardTemplate>();
	
	
	
	@Override
	public void init() {
		
        TemplateService templateService = Globals.getTemplateService();
		
		Map<Integer,CardRewardTemplate> map1 = templateService.getAll(CardRewardTemplate.class);
		
		for(CardRewardTemplate ct : map1.values()){
			map.put(ct.getCardType(), ct);
		}
		
		

	}
	
	public CardRewardTemplate getCardReward(int cardType){
		return map.get(cardType);
	}

}
