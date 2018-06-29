package com.gameserver.newbie;

import java.util.HashMap;
import java.util.Map;

import com.common.AfterInitializeRequired;
import com.common.InitializeRequired;
import com.core.template.TemplateService;
import com.gameserver.newbie.template.NoviceSlotsTemplate;
import com.gameserver.newbie.template.NovicebootTemplate;

public class NewbieService implements InitializeRequired,AfterInitializeRequired{
	private Map<Integer, NovicebootTemplate> stepNovicebootTemplate = new HashMap<>();
	private Map<Integer, NoviceSlotsTemplate> stepNoviceSlotsTemplate = new HashMap<>();
	
	/** 物品模板管理器,负责从模板中取得物品 */
	private TemplateService templateService;
	public NewbieService(TemplateService templateService)
	{
		this.templateService = templateService;
	}
	
	@Override
	public void init() {
		Map<Integer, NovicebootTemplate> m = templateService.getAll(NovicebootTemplate.class);
		for(NovicebootTemplate nt : m.values()){
			stepNovicebootTemplate.put(nt.getStep(), nt);
		}
		
		Map<Integer, NoviceSlotsTemplate> mm = templateService.getAll(NoviceSlotsTemplate.class);
		for(NoviceSlotsTemplate nt : mm.values()){
			stepNoviceSlotsTemplate.put(nt.getStep(), nt);
		}
	}
	@Override
	public void afterInit() {
	}
	
	public NovicebootTemplate getNovicebootTemplateByStep(int step)
	{
		return stepNovicebootTemplate.get(step);
	}
	
	public NoviceSlotsTemplate getNoviceSlotsTemplateByStep(int step)
	{
		return stepNoviceSlotsTemplate.get(step);
	}
}
