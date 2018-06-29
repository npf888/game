package com.gameserver.newbie.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;



@ExcelRowBinding
public class NoviceSlotsTemplate extends NoviceSlotsTemplateVO{
	
	@Override
	public void check() throws TemplateConfigException
	{
	
	}
	
	@Override
	public void patchUp() throws Exception 
	{
	}
}

