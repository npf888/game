package com.gameserver.newbie.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;



@ExcelRowBinding
public class NoviceStepTemplate extends NoviceStepTemplateVO{
	
	@Override
	public void check() throws TemplateConfigException
	{
	
	}
	
	@Override
	public void patchUp() throws Exception 
	{
	}
}

