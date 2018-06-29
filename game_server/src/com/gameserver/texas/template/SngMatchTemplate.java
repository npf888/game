package com.gameserver.texas.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;

/**
 * sng match
 * @author wayne
 *
 */

@ExcelRowBinding
public class SngMatchTemplate extends SngMatchTemplateVO{
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
