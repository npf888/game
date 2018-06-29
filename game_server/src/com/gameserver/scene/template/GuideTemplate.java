package com.gameserver.scene.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;


/**
 * 新手引导信息表
 * @author Thinker
 *
 */
@ExcelRowBinding
public class GuideTemplate extends GuideTemplateVO
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
