package com.gameserver.item.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class InteractiveItemTemplate extends InteractiveItemTemplateVO{
	
	@Override
	public void check() throws TemplateConfigException
	{
	
	}
	
	@Override
	public void patchUp() throws Exception 
	{
	}

}
