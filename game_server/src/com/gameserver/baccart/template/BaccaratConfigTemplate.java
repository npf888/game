package com.gameserver.baccart.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class BaccaratConfigTemplate extends BaccaratConfigTemplateVO{
	@Override
	public void check() throws TemplateConfigException
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	public void patchUp() throws Exception 
	{
	}
}
