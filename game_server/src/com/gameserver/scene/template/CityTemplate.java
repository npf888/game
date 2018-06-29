package com.gameserver.scene.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;


/**
 * 城镇信息表
 * @author Thinker
 *
 */
@ExcelRowBinding
public class CityTemplate extends CityTemplateVO
{
	@Override
	public void check() throws TemplateConfigException
	{
		
	}
	@Override
	public void patchUp()
	{
	
	}
}
