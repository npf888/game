package com.gameserver.texas.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;

/**
 * 
 * @author wayne
 *
 */
@ExcelRowBinding
public class TexasRoomConfigTemplate extends TexasRoomConfigTemplateVO{

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
