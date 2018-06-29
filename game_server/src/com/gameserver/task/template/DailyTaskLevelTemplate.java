package com.gameserver.task.template;

import com.common.exception.TemplateConfigException;
import com.core.annotation.ExcelRowBinding;

/**
 * 
 * @author 郭君伟
 *
 */
@ExcelRowBinding
public class DailyTaskLevelTemplate extends DailyTaskLevelTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		
	}

	@Override
	public void patchUp() throws Exception {
		
	}

}
